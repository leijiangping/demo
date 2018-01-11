package com.xunge.service.job.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xunge.comm.BatchControlComm;
import com.xunge.comm.StateComm;
import com.xunge.dao.basedata.DatBaseresourceVOMapper;
import com.xunge.dao.basedata.DatBasesiteVOExample;
import com.xunge.dao.basedata.DatBasesiteVOMapper;
import com.xunge.dao.basedata.DatBasestationVOMapper;
import com.xunge.dao.basedata.system.SysRegionVOMapper;
import com.xunge.dao.region.ISysRegionDao;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.DatBaseresourceVOExample;
import com.xunge.model.basedata.DatBasesiteVO;
import com.xunge.model.basedata.DatBasestationVO;
import com.xunge.model.basedata.DatBasestationVOExample;
import com.xunge.model.basedata.SysRegionVO;
import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.service.job.IResourceService;
import com.xunge.service.job.util.DatBaseResourceUtil;
import com.xunge.service.job.util.DatBaseSiteUtil;
import com.xunge.service.job.util.DatBaseStationUtil;
import com.xunge.service.job.util.SysRegionUtil;
import com.xunge.util.DataType;
import com.xunge.util.IDUtils;
import com.xunge.util.StrUtil;
import com.xunge.util.TableType;
import com.xunge.util.ValueUtil;
import com.xunge.util.branch.DBBranchUtil;
import com.xunge.util.redis.JedisUtil;
import com.xunge.util.redis.JedisUtil.Hash;
import com.xunge.util.redis.JedisUtil.Keys;

@Service
public class ResourceServiceImpl implements IResourceService {
	private static final Logger LOGGER = Logger.getLogger(ResourceServiceImpl.class);

	@Resource
	private DatBasestationVOMapper datBasestationVOMapper;
	
	@Resource
	private DatBaseresourceVOMapper datBaseresourceVOMapper;
	
	@Resource
	private DatBasesiteVOMapper datBasesiteVOMapper;
	
	@Resource
	private SysRegionVOMapper sysRegionVOMapper;

	
	@Resource
	private ISysRegionDao sysRegionDao;
	
	@Resource
    private DBBranchUtil dbUtil;


	private int iBatchCount = 500;
	
	//利用redis做集合处理
	private JedisUtil jedisUtil = null;
	private volatile JedisUtil.Sets jedis_dball_basestation=null;//数据库查询到的省内所有主合同
	private volatile JedisUtil.Hash jedis_dball_basestation_cuid=null;//数据库查询到的省内所有主合同 cuid和prvId作为key
	private volatile JedisUtil.Sets jedis_dball_basesite=null;//数据库查询到的省内所有主合同
	private volatile JedisUtil.Hash jedis_dball_basesite_cuid=null;//数据库查询到的省内所有主合同 cuid和prvId作为key
	private volatile JedisUtil.Hash jedis_files_basesite=null;//数据库查询到的省内所有主合同 cuid和prvId作为key
	private volatile JedisUtil.Hash jedis_files_basestation=null;//数据库查询到的省内所有主合同 cuid和prvId作为key
	private String DB_SYSID_ENTRY_Prex = "DB_SYSID_ENTRY_";
	private String FILE_SYSID_ENTRY_Prex = "FILE_SYSID_ENTRY_";
	private volatile JedisUtil.Keys jedis_KeysSETS=null;

	@Override
	public StringBuffer InsertBaseStation(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo,String delimiter) {
		long startTime=System.currentTimeMillis();   //获取开始时间
		StringBuffer loginfoBF = new StringBuffer(); 
		List<DatBasestationVO> datas = new ArrayList<DatBasestationVO>();
		List<DatBasestationVO> insertBasestationList = new ArrayList<DatBasestationVO>();
		List<DatBasestationVO> updateBasestationList = new ArrayList<DatBasestationVO>();
		List<String> resourceCuIds=new ArrayList<String>(); 
		Map<String,DatBasestationVO> filterRepeatRecord = new HashMap<String,DatBasestationVO>();
		String cont_rediskey = "BASESTATION_"+System.currentTimeMillis();
		String prvId = sysProvinceVO.getPrvId();
		//获取比对的数据库redis集合
		getRedisCompareSets(prvId, cont_rediskey);
		for (String key : map.keySet()) {
			DatBasestationVO datBasestationVO = dealDatBasestationVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			datBasestationVO.setBasestationId(IDUtils.getID());
			datBasestationVO.setCreate_time(new Date());
			datBasestationVO.setCreate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
			datBasestationVO.setCreate_ip(taskInfo.getOperateUserIp());
			datas.add(datBasestationVO);
			resourceCuIds.add(datBasestationVO.getBaseresourceCuid());
			//通过Map将文件内部的重复去除
			filterRepeatRecord.put(datBasestationVO.getPrvId()+datBasestationVO.getBasestationCuid(), datBasestationVO);
		}
		datas.clear();
		for(String key : filterRepeatRecord.keySet()){
			datas.add(filterRepeatRecord.get(key));
		}
		dealDatBasestationResourceId(datas,resourceCuIds,sysProvinceVO);
		Map<String, String> datcMap = new HashMap<>();
		List<String> bufferFileCom = new ArrayList<String>();
		List<String> bufferFileSysid = new ArrayList<String>();
		for(DatBasestationVO basestationVO : datas){
			//用于文件比对的文件中合同集合
			DatBasestationVO datContractCom = (DatBasestationVO) basestationVO.clone();//深度克隆
			datcMap.put(datContractCom.getBasestationCuid()+"_"+datContractCom.getPrvId(), JSONObject.fromObject(datContractCom).toString());
			bufferFileCom.add(JSONObject.fromObject(datContractCom).toString());
			bufferFileSysid.add(datContractCom.getBasestationCuid()+"_"+datContractCom.getPrvId());
		}
		jedis_dball_basestation.sadd("FILE_COM_"+cont_rediskey, bufferFileCom);
		jedis_dball_basestation.sadd("FILE_SYSID_"+cont_rediskey, bufferFileSysid);
		jedis_files_basestation.hmset(FILE_SYSID_ENTRY_Prex+cont_rediskey, datcMap);
		insertBaseStationByRedis(cont_rediskey, taskInfo,insertBasestationList);
		updateBaseStationByRedis(cont_rediskey, taskInfo,updateBasestationList);
		long endTime=System.currentTimeMillis(); //获取结束时间
		loginfoBF.append("baseStation 文件共"+map.keySet().size()+"行；"
				+ "解析为数据库字段时间： "+(endTime-startTime)/60000+"min "+(endTime-startTime)%60000+"ms");
		LOGGER.info(loginfoBF.toString());
		try{
			loginfoBF.append("baseStation 文件共新增"+insertBasestationList.size()+"行，开始入库时间为"+System.currentTimeMillis());
			String info1= dbUtil.branch(8, insertBasestationList);
			loginfoBF.append("\r\n站点新增入库："+info1);
			loginfoBF.append("baseStation 文件入库完成时间为 ："+System.currentTimeMillis());
			loginfoBF.append("baseStation 文件共更新"+updateBasestationList.size()+"行，开始更新时间为"+System.currentTimeMillis());
			String info2= dbUtil.branch(9, updateBasestationList);
			loginfoBF.append("；站点更新入库："+info2);
			loginfoBF.append("baseStation 文件更新结束时间为："+System.currentTimeMillis());
		}
		catch(Exception e){
			loginfoBF.append("\r\n"+e.toString());
		}
		finally{
			jedis_KeysSETS.batchDel(cont_rediskey);
		}
	    return new StringBuffer(loginfoBF.toString());
	}

	
	private void insertBaseStationByRedis(String cont_rediskey, TaskInfoVO taskInfo, 
			List<DatBasestationVO> datBasestationVOList){
		jedis_dball_basestation.sdiffstore("FILE_SDIFF_"+cont_rediskey, "FILE_SYSID_"+cont_rediskey, "DB_SYSID_"+cont_rediskey);
		Set<String> insertSets = jedis_dball_basestation.smembers("FILE_SDIFF_"+cont_rediskey);
		//逐条
		Iterator<String> it = insertSets.iterator();
		while (it.hasNext()) {
			String sysId = it.next();
			//反赋值原对象id
			List<String> entsets = jedis_files_basestation.hmget(FILE_SYSID_ENTRY_Prex+cont_rediskey, sysId);
			Iterator<String> entit = entsets.iterator();
			if(!entit.hasNext()) LOGGER.error("未找到综资id关联："+sysId+","+cont_rediskey);
			else{
				String next = entit.next();
				JSONObject jsonObject=JSONObject.fromObject(next);
				DatBasestationVO baseStation = (DatBasestationVO) JSONObject.toBean(jsonObject, DatBasestationVO.class);
				baseStation.setCreate_ip(StrUtil.isBlank(taskInfo.getOperateUserIp())?"admin":taskInfo.getOperateUserIp());
				baseStation.setCreate_time(new Date());
				baseStation.setCreate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
				datBasestationVOList.add(baseStation);
			}
		}
	}
	
	private void updateBaseStationByRedis(String cont_rediskey, TaskInfoVO taskInfo, 
			List<DatBasestationVO> datBasestationVOList){
		if(jedis_dball_basestation.scard("DB_COM_"+cont_rediskey)<=0){
			return;
		}
		jedis_dball_basestation.sdiffstore("DB_SDIFF_"+cont_rediskey, "FILE_COM_"+cont_rediskey, "DB_COM_"+cont_rediskey);
		Set<String> updateSets = jedis_dball_basestation.smembers("DB_SDIFF_"+cont_rediskey);
		//逐条
		Iterator<String> it = updateSets.iterator();
		while (it.hasNext()) {
			String next = it.next();
			JSONObject jsonObject=JSONObject.fromObject(next);
			DatBasestationVO basestationVO = (DatBasestationVO) JSONObject.toBean(jsonObject, DatBasestationVO.class);
			if(jedis_dball_basestation.sismember("FILE_SDIFF_"+cont_rediskey, basestationVO.getBasestationCuid()+"_"+basestationVO.getPrvId())){
				continue;
			}
			basestationVO.setUpdate_time(new Date());
			basestationVO.setUpdate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
			basestationVO.setUpdate_ip(StrUtil.isBlank(taskInfo.getOperateUserIp())?"admin":taskInfo.getOperateUserIp());
			datBasestationVOList.add(basestationVO);
		}
	}
	
	private void insertBaseSiteByRedis(String cont_rediskey, TaskInfoVO taskInfo, 
			List<DatBasesiteVO> datBaseiteVOList){
		jedis_dball_basesite.sdiffstore("FILE_SDIFF_"+cont_rediskey, "FILE_SYSID_"+cont_rediskey, "DB_SYSID_"+cont_rediskey);
		Set<String> insertSets = jedis_dball_basesite.smembers("FILE_SDIFF_"+cont_rediskey);
		//逐条
		Iterator<String> it = insertSets.iterator();
		while (it.hasNext()) {
			String sysId = it.next();
			//反赋值原对象id
			List<String> entsets = jedis_files_basesite.hmget(FILE_SYSID_ENTRY_Prex+cont_rediskey, sysId);
			Iterator<String> entit = entsets.iterator();
			if(!entit.hasNext()) LOGGER.error("未找到综资id关联："+sysId+","+cont_rediskey);
			else{
				String next = entit.next();
				JSONObject jsonObject=JSONObject.fromObject(next);
				DatBasesiteVO baseSite= (DatBasesiteVO) JSONObject.toBean(jsonObject, DatBasesiteVO.class);
				baseSite.setCreate_ip(StrUtil.isBlank(taskInfo.getOperateUserIp())?"admin":taskInfo.getOperateUserIp());
				baseSite.setCreate_time(new Date());
				baseSite.setCreate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
				datBaseiteVOList.add(baseSite);
			}
		}
	}
	
	private void updateBaseSiteByRedis(String cont_rediskey, TaskInfoVO taskInfo, 
			List<DatBasesiteVO> datBaseiteVOList){
		if(jedis_dball_basesite.scard("DB_COM_"+cont_rediskey)<=0){
			return;
		}
		jedis_dball_basesite.sdiffstore("DB_SDIFF_"+cont_rediskey, "FILE_COM_"+cont_rediskey, "DB_COM_"+cont_rediskey);
		Set<String> updateSets = jedis_dball_basesite.smembers("DB_SDIFF_"+cont_rediskey);
		//逐条
		Iterator<String> it = updateSets.iterator();
		while (it.hasNext()) {
			String next = it.next();
			JSONObject jsonObject=JSONObject.fromObject(next);
			DatBasesiteVO basesiteVO = (DatBasesiteVO) JSONObject.toBean(jsonObject, DatBasesiteVO.class);
			if(jedis_dball_basesite.sismember("FILE_SDIFF_"+cont_rediskey, basesiteVO.getBasesiteCuid()+"_"+basesiteVO.getPrvId())){
				continue;
			}
			basesiteVO.setUpdate_time(new Date());
			basesiteVO.setUpdate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
			basesiteVO.setUpdate_ip(StrUtil.isBlank(taskInfo.getOperateUserIp())?"admin":taskInfo.getOperateUserIp());
			datBaseiteVOList.add(basesiteVO);
		}
	}
	
	@Override
	public boolean updateBaseStation(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo,String delimiter) {

		List<DatBasestationVO> datas = new ArrayList<DatBasestationVO>();
		List<String> resourceCuIds=new ArrayList<String>(); 
		for (String key : map.keySet()) {
			DatBasestationVO datBasestationVO = dealDatBasestationVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			datBasestationVO.setUpdate_time(new Date());
			datBasestationVO.setUpdate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
			datBasestationVO.setUpdate_ip(taskInfo.getOperateUserIp());
			datas.add(datBasestationVO);
			resourceCuIds.add(datBasestationVO.getBaseresourceId());
		}
		dealDatBasestationResourceId(datas,resourceCuIds,sysProvinceVO);
		
		boolean bBatchUpdate = false;
		for(int i=0, length = datas.size(); i<length; i+=iBatchCount){
			int j=(i+500>datas.size())?datas.size():i+iBatchCount;
			List<DatBasestationVO> tmp = datas.subList(i, j);
			bBatchUpdate = datBasestationVOMapper.batchUpdate(tmp);
		}
		return bBatchUpdate;
	}

	@Override
	public boolean delBaseStation(String pkName,String typeName,List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO) {
		
		List<String> list=ValueUtil.dealCuids(columns,map);
		String prvId=sysProvinceVO.getPrvId();

		HashMap<String,Object> pMap=new HashMap<String,Object>();
		pMap.put("prvId", prvId);
		pMap.put("list", list);
		return datBasestationVOMapper.delByCuidsAndPrvid(pMap);
	}

	@SuppressWarnings("null")
	@Override
	public StringBuffer InsertBaseresource(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo,String delimiter) {
		String prv_id = sysProvinceVO.getPrvId();
		//数据库存入redis集合
		JedisUtil jutil = JedisUtil.getInstance();
		JedisUtil.Keys keys= jutil.new Keys();
		StringBuffer loginfoBF = new StringBuffer(); 
		Map<String, String> errorlogMap = new HashMap<>();
		String cont_rediskey = "BASERESOURCE_"+System.currentTimeMillis();
		Map<String,DatBaseresourceVO> filterRepeatRecord = new HashMap<String,DatBaseresourceVO>();
		//手动缓存机制
		JSONObject ProvinceCache=new JSONObject(), RegionCache=getRegidlistByPrvid(prv_id);
		 try {
			 JedisUtil.Sets jedis_dball_baseresource = jutil.new Sets();
			 JedisUtil.Hash jedis_file_baseresource = jutil.new Hash();
			 List<DatBaseresourceVO> i_baseresource = new ArrayList<>();
			 List<DatBaseresourceVO> u_baseresource = new ArrayList<>();
			 
			 DatBaseresourceVOExample example=new DatBaseresourceVOExample();
			 DatBaseresourceVOExample.Criteria criteria=example.createCriteria();
			 criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
			 List<DatBaseresourceVO> dbreslist = datBaseresourceVOMapper.selectByExample(example);
			 
			 List<String> bufferDBCom = new ArrayList<String>();
			 List<String> bufferDBSysid = new ArrayList<String>();
			 for(DatBaseresourceVO dbv : dbreslist){
				 bufferDBCom.add(JSONObject.fromObject(dbv).toString());
				 bufferDBSysid.add(dbv.getPrvId()+dbv.getBaseresourceCuid());
			 }
			 jedis_dball_baseresource.sadd("DB_CUID_"+cont_rediskey, bufferDBSysid);
			 jedis_dball_baseresource.sadd("DB_ALL_"+cont_rediskey,bufferDBCom);
			 LOGGER.info("--------------------------parseRowDataInsert 开始解析文件为数据集合");
			 long startTime=System.currentTimeMillis(); 
			 
			 List<String> siteCuIds = new ArrayList<>();
			 for (String key : map.keySet()) {
				 int columnSize=columns.size();
				 String[] columnData = ValueUtil.dealValues(columnSize,map.get(key),delimiter);
				 if(ProvinceCache.size()<=0 && columnData[0]!=null){
						String prvcode = columnData[0];
						String caPrvId = sysRegionDao.getPrvIdByCode(prvcode);
						ProvinceCache.put(prvcode, caPrvId);
				 }
				 DatBaseresourceVO datBaseresourceVO = dealDatBaseresourceVO(typeName, columns, map.get(key),sysProvinceVO,delimiter,prv_id,ProvinceCache,RegionCache);
				 datBaseresourceVO.setBaseresourceId(IDUtils.getID());
				 datBaseresourceVO.setCreate_time(new Date());
				 datBaseresourceVO.setCreate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
				 datBaseresourceVO.setCreate_ip(taskInfo.getOperateUserIp());
				 //存取关联的siteid
				 siteCuIds.add(datBaseresourceVO.getBasesiteCuid());
				 //通过Map将文件内部的重复去除
				 filterRepeatRecord.put(datBaseresourceVO.getPrvId()+datBaseresourceVO.getBaseresourceCuid(), datBaseresourceVO);
			 }
			 List<DatBaseresourceVO> datas = new ArrayList<>();
			 for(String key : filterRepeatRecord.keySet()){
					datas.add(filterRepeatRecord.get(key));
			 }
			 dealDatBaseresourceSiteId(datas, siteCuIds, sysProvinceVO);
			 Map<String,String> file = new HashMap<>();
			 List<String> bufferFileCom = new ArrayList<String>();
			 List<String> bufferFileSysid = new ArrayList<String>();
			 for (DatBaseresourceVO datBaseresourceVO : datas) {
				 bufferFileCom.add(JSONObject.fromObject(datBaseresourceVO).toString());
				 bufferFileSysid.add(datBaseresourceVO.getPrvId()+datBaseresourceVO.getBaseresourceCuid());
				 file.put(datBaseresourceVO.getPrvId()+datBaseresourceVO.getBaseresourceCuid(), JSONObject.fromObject(datBaseresourceVO).toString());
			 }
			 jedis_dball_baseresource.sadd("FILE_CUID_"+cont_rediskey, bufferFileSysid);
			 jedis_dball_baseresource.sadd("FILE_ALL_"+cont_rediskey,bufferFileCom);
			 jedis_file_baseresource.hmset("FILE_"+cont_rediskey,file);
			 //生成插入list
			 LOGGER.info("--------------------------parseRowDataInsert 开始集合操作");
			 jedis_dball_baseresource.sdiffstore("DIFF_"+cont_rediskey, "FILE_CUID_"+cont_rediskey,"DB_CUID_"+cont_rediskey);
			 Set<String> diff = jedis_dball_baseresource.smembers("DIFF_"+cont_rediskey);
			 Iterator<String> iterator = diff.iterator();
			 while (iterator.hasNext()) {
				 String cuid = iterator.next();
				 boolean hexists = jedis_file_baseresource.hexists("FILE_"+cont_rediskey, cuid);
				 if(!hexists)
					 LOGGER.error("未找到cuid"+cuid+"记录");
				 else{
					 JSONObject jo = JSONObject.fromObject(jedis_file_baseresource.hmget("FILE_"+cont_rediskey, cuid).get(0));
					 DatBaseresourceVO dv = (DatBaseresourceVO)JSONObject.toBean(jo, DatBaseresourceVO.class);
					 i_baseresource.add(dv);
				 }
			 }
			 //生成更新list
			 jedis_dball_baseresource.sdiffstore("DIFF_U_"+cont_rediskey, "FILE_ALL_"+cont_rediskey,"DB_ALL_"+cont_rediskey);
			 Set<String> diffu = jedis_dball_baseresource.smembers("DIFF_U_"+cont_rediskey);
			 Iterator<String> iterator_u = diffu.iterator();
			 if(jedis_dball_baseresource.scard("DB_ALL_"+cont_rediskey)>0){
				 while(iterator_u.hasNext()){
					 String str = iterator_u.next();
					 JSONObject jo = JSONObject.fromObject(str);
					 DatBaseresourceVO dbv = (DatBaseresourceVO)JSONObject.toBean(jo, DatBaseresourceVO.class);
					 String cuid = dbv.getPrvId()+dbv.getBaseresourceCuid();
					 if(jedis_dball_baseresource.sismember("DIFF_"+cont_rediskey, cuid)){
						 continue;
					 }
					 dbv.setUpdate_ip(StrUtil.isBlank(taskInfo.getOperateUserIp())?"admin":taskInfo.getOperateUserIp());
					 dbv.setUpdate_time(new Date());
					 dbv.setUpdate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
					 u_baseresource.add(dbv);
				 }
			 }
			 long endTime=System.currentTimeMillis(); //获取结束时间
				loginfoBF.append("resource 文件共"+map.keySet().size()+"行；"
						+ "解析为数据库字段时间： "+(endTime-startTime)/60000+"min "+(endTime-startTime)%60000+"ms");
			 LOGGER.info(loginfoBF.toString());
			 LOGGER.info("resource新增"+i_baseresource.size()+" resource更新:"+u_baseresource.size());
			 
			 //开始更新插入
			 try{
					loginfoBF.append("baseResource 文件共新增"+i_baseresource.size()+"行，开始新增为"+System.currentTimeMillis());
					String info1= dbUtil.branch(10, i_baseresource);
					loginfoBF.append("baseResource 文件入库完成时间为"+System.currentTimeMillis());
					loginfoBF.append("\r\nResource新增入库："+info1);
					loginfoBF.append("baseResource 文件共更新"+u_baseresource.size()+"行，开始更新时间为"+System.currentTimeMillis());
					String info2= dbUtil.branch(11, u_baseresource);
					loginfoBF.append("baseResource更新结束时间为"+System.currentTimeMillis());
					loginfoBF.append("；Resource更新入库："+info2);
				}
				catch(Exception e){
					loginfoBF.append("\r\n"+e.toString());
				}
			 

				loginfoBF.append("\r\n--------Resource 新增Resource完成"+i_baseresource.size()+"条；更新"+u_baseresource.size()+"条");
				LOGGER.info(loginfoBF.toString());
				
				endTime=System.currentTimeMillis(); //获取结束时间
				loginfoBF.append("\r\n--------Contract 批量处理时间： "+(endTime-startTime)/60000+"min "+(endTime-startTime)%60000+"ms");
				errorlogMap.put("operateDBinfo", loginfoBF.toString());
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			keys.batchDel(cont_rediskey);
		}
		return new StringBuffer(loginfoBF.toString());
	}

	@Override
	public boolean updateBaseresource(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo,String delimiter) {
		

//		List<DatBaseresourceVO> datas = new ArrayList<DatBaseresourceVO>();
//		List<String> siteCuIds=new ArrayList<String>(); 
//		for (String key : map.keySet()) {
//			DatBaseresourceVO datBaseresourceVO = dealDatBaseresourceVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
//			datBaseresourceVO.setUpdate_time(new Date());
//			datBaseresourceVO.setUpdate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
//			datBaseresourceVO.setUpdate_ip(taskInfo.getOperateUserIp());
//			datas.add(datBaseresourceVO);
//			siteCuIds.add(datBaseresourceVO.getBasesiteId());
//		}
//		dealDatBaseresourceSiteId(datas,siteCuIds,sysProvinceVO);
//		dealBaseresourceRegion(datas,sysProvinceVO);


		boolean bUpdateDatas = false;
//		for(int i=0, length = datas.size(); i<length; i+=iBatchCount){
//			int j=(i+500>datas.size())?datas.size():i+iBatchCount;
//			List<DatBaseresourceVO> tmp = datas.subList(i, j);
//			bUpdateDatas = datBaseresourceVOMapper.batchUpdate(tmp);
//		}
		return bUpdateDatas;
	}

	@Override
	public boolean delBaseresource(String pkName,String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO) {
		
		List<String> list=ValueUtil.dealCuids(columns,map);
		String prvId=sysProvinceVO.getPrvId();

		HashMap<String,Object> pMap=new HashMap<String,Object>();
		pMap.put("prvId", prvId);
		pMap.put("list", list);
		
		return datBaseresourceVOMapper.delByCuidsAndPrvid(pMap);
	}

	@Override
	public StringBuffer InsertBasesite(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo,String delimiter) {
		List<DatBasesiteVO> datas = new ArrayList<DatBasesiteVO>();
		List<DatBasesiteVO> insertBasesiteList = new ArrayList<DatBasesiteVO>();
		List<DatBasesiteVO> updateBasesiteList = new ArrayList<DatBasesiteVO>();
		Map<String, String> errorlogMap = new HashMap<>();
		long startTime=System.currentTimeMillis();   //获取开始时间
		StringBuffer loginfoBF = new StringBuffer(); 
		Map<String,DatBasesiteVO> filterRepeatRecord = new HashMap<String,DatBasesiteVO>();
		String cont_rediskey = "BASESITE_"+System.currentTimeMillis();
		String prvId = sysProvinceVO.getPrvId();
		//手动缓存机制
		JSONObject ProvinceCache=new JSONObject(), RegionCache=getRegidlistByPrvid(prvId);
		getStationRedisCompareSets(prvId, cont_rediskey);
		for (String key : map.keySet()) {
			DatBasesiteVO datBasesiteVO = this.dealDatBasesiteVO(typeName, columns, map.get(key),sysProvinceVO,delimiter,RegionCache);
			datBasesiteVO.setBasesiteId(IDUtils.getID());
			datBasesiteVO.setCreate_time(new Date());
			datBasesiteVO.setCreate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
			datBasesiteVO.setCreate_ip(taskInfo.getOperateUserIp());
			datas.add(datBasesiteVO);
			//通过Map将文件内部的重复去除
			filterRepeatRecord.put(datBasesiteVO.getPrvId()+datBasesiteVO.getBasesiteCuid(), datBasesiteVO);
		}
		datas.clear();
		for(String key : filterRepeatRecord.keySet()){
			datas.add(filterRepeatRecord.get(key));
		}
		dealBasesiteRegion(datas,sysProvinceVO);
		Map<String, String> datcMap = new HashMap<>();
		 List<String> bufferFileCom = new ArrayList<String>();
		 List<String> bufferFileSysid = new ArrayList<String>();
		for(DatBasesiteVO basesiteVO : datas){
			//用于文件比对的文件中合同集合
			DatBasesiteVO basesiteCom = (DatBasesiteVO) basesiteVO.clone();//深度克隆
			bufferFileCom.add(JSONObject.fromObject(basesiteCom).toString());
			bufferFileSysid.add(basesiteCom.getBasesiteCuid()+"_"+basesiteCom.getPrvId());
			datcMap.put(basesiteCom.getBasesiteCuid()+"_"+basesiteCom.getPrvId(), JSONObject.fromObject(basesiteCom).toString());
		}
		jedis_dball_basesite.sadd("FILE_COM_"+cont_rediskey, bufferFileCom);
		jedis_dball_basesite.sadd("FILE_SYSID_"+cont_rediskey, bufferFileSysid);
		jedis_files_basesite.hmset(FILE_SYSID_ENTRY_Prex+cont_rediskey, datcMap);
		insertBaseSiteByRedis(cont_rediskey, taskInfo,insertBasesiteList);
		updateBaseSiteByRedis(cont_rediskey, taskInfo,updateBasesiteList);
		long endTime=System.currentTimeMillis(); //获取结束时间
		loginfoBF.append("baseSite文件共"+map.keySet().size()+"行；"
				+ "解析为数据库字段时间： "+(endTime-startTime)/60000+"min "+(endTime-startTime)%60000+"ms");
		LOGGER.info(loginfoBF.toString());
		try{
			loginfoBF.append("baseSite 文件共新增"+insertBasesiteList.size()+"行，开始入库时间为"+System.currentTimeMillis());
			String info1= dbUtil.branch(12, insertBasesiteList);
			loginfoBF.append("\r\n站点新增入库："+info1);
			loginfoBF.append("baseSite 文件入库完成时间为"+System.currentTimeMillis());
			loginfoBF.append("baseSite 文件共更新"+updateBasesiteList.size()+"行，开始更新时间为"+System.currentTimeMillis());
			String info2= dbUtil.branch(13, updateBasesiteList);
			loginfoBF.append("；站点更新入库："+info2);
			loginfoBF.append("baseSite 文件更新完成时间为"+System.currentTimeMillis());
		}
		catch(Exception e){
			loginfoBF.append("\r\n"+e.toString());
		}
		finally{
			jedis_KeysSETS.batchDel(cont_rediskey);
		}
	    errorlogMap.put("operateDBinfo", loginfoBF.toString());
		return new StringBuffer( loginfoBF.toString());
	}

	/**
	 * 插入前判断库里是否存在
	 * @author jiacy
	 * @param record
	 * @return
	 */
	public List<? extends Object> hasContainsRecord(List<? extends Object> record,SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo){
		if (record.size() > 0){
			if (record.get(0) instanceof DatBasesiteVO){
				DatBasesiteVOExample example=new DatBasesiteVOExample();
				DatBasesiteVOExample.Criteria criteria=example.createCriteria();
				criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
				List<DatBasesiteVO> dataBaseRecList = datBasesiteVOMapper.selectByExample(example);
				//重复记录列表
				List<DatBasesiteVO> dulipteRecord = new ArrayList<DatBasesiteVO>();
				for (Object obj : record){
					for (DatBasesiteVO dbRecord : dataBaseRecList){
						if (null != dbRecord){
							if(null != dbRecord.getPrvId() && null != dbRecord.getBasesiteCuid() && ((DatBasesiteVO) obj)
									.getBasesiteCuid() != null && ((DatBasesiteVO) obj)
									.getPrvId() != null){
								if (dbRecord.getPrvId().equalsIgnoreCase(
										((DatBasesiteVO) obj).getPrvId())
										&& dbRecord.getBasesiteCuid()
										.equalsIgnoreCase(
												((DatBasesiteVO) obj)
												.getBasesiteCuid())) {
									dbRecord.setUpdate_time(new Date());
									dbRecord.setUpdate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
									dbRecord.setUpdate_ip(taskInfo.getOperateUserIp());
									dulipteRecord.add((DatBasesiteVO) obj);
									break;
								}
							}
						}
					}
				}
				if (dulipteRecord.size() > 0){
					//已存在的批量修改
					dealBasesiteRegion(dulipteRecord,sysProvinceVO);
					datBasesiteVOMapper.batchUpdate(dulipteRecord);
					//从List中删除已存在的
					return dulipteRecord;
				}
			}
			if (record.get(0) instanceof DatBaseresourceVO){
				DatBaseresourceVOExample example = new DatBaseresourceVOExample();
				DatBaseresourceVOExample.Criteria criteria=example.createCriteria();
				criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
				List<DatBaseresourceVO> dataBaseRecList = datBaseresourceVOMapper.selectByExample(example);
				//重复记录列表
				List<DatBaseresourceVO> dulipteRecord = new ArrayList<DatBaseresourceVO>();
				List<String> siteCuIds=new ArrayList<String>(); 
				for (Object obj : record){
					for (DatBaseresourceVO dbRecord : dataBaseRecList){
						if (null != dbRecord){
							if(null != dbRecord.getPrvId() && null != dbRecord.getBaseresourceCuid()&& ((DatBaseresourceVO) obj)
									.getBaseresourceCuid() != null && ((DatBaseresourceVO) obj)
									.getPrvId() != null){
								if (dbRecord.getPrvId().equalsIgnoreCase(
										((DatBaseresourceVO) obj).getPrvId())
										&& dbRecord.getBaseresourceCuid()
										.equalsIgnoreCase(
												((DatBaseresourceVO) obj)
												.getBaseresourceCuid())) {
									dbRecord.setUpdate_time(new Date());
									dbRecord.setUpdate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
									dbRecord.setUpdate_ip(taskInfo.getOperateUserIp());
									dulipteRecord.add((DatBaseresourceVO) obj);
									siteCuIds.add(dbRecord.getBaseresourceCuid());
									break;
								}
							}
						}
					}
				}
				if (dulipteRecord.size() > 0){
					//已存在的批量修改
					dealDatBaseresourceSiteId(dulipteRecord,siteCuIds,sysProvinceVO);
					dealBaseresourceRegion(dulipteRecord,sysProvinceVO);
					datBaseresourceVOMapper.batchUpdate(dulipteRecord);
					//从List中删除已存在的
					return dulipteRecord;
				}
			}
			if (record.get(0) instanceof DatBasestationVO){
				DatBasestationVOExample example = new DatBasestationVOExample();
				DatBasestationVOExample.Criteria criteria=example.createCriteria();
				criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
				List<DatBasestationVO> dataBaseRecList = datBasestationVOMapper.selectByExample(example);
				//重复记录列表
				List<DatBasestationVO> dulipteRecord = new ArrayList<DatBasestationVO>();
				List<String> siteCuIds=new ArrayList<String>(); 
				for (Object obj : record){
					for (DatBasestationVO dbRecord : dataBaseRecList){
						if (null != dbRecord){
							if(null != dbRecord.getPrvId() && null != dbRecord.getBasestationCuid()&& ((DatBasestationVO) obj)
									.getBasestationCuid() != null && ((DatBasestationVO) obj)
									.getPrvId() != null){
								if (dbRecord.getPrvId().equalsIgnoreCase(
										((DatBasestationVO) obj).getPrvId())
										&& dbRecord.getBasestationCuid()
										.equalsIgnoreCase(
												((DatBasestationVO) obj)
												.getBasestationCuid())) {
									dbRecord.setUpdate_time(new Date());
									dbRecord.setUpdate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
									dbRecord.setUpdate_ip(taskInfo.getOperateUserIp());
									dulipteRecord.add((DatBasestationVO) obj);
									siteCuIds.add(dbRecord.getBaseresourceCuid());
									break;
								}
							}
						}
					}
				}
				if (dulipteRecord.size() > 0){
					//已存在的批量修改
					dealDatBasestationResourceId(dulipteRecord,siteCuIds,sysProvinceVO);
					datBasestationVOMapper.batchUpdate(dulipteRecord);
					//从List中删除已存在的
			        return dulipteRecord;
				}
			}
		}
		return null;
	}
	
	//获取比对的redis集合
	private void getRedisCompareSets(String prv_id,String cont_rediskey){
		if(jedisUtil==null){
			jedisUtil= JedisUtil.getInstance();
		}
		Map<String, String> sysnumEntryMap = new HashMap<>();
		if(jedis_KeysSETS==null) {
			jedis_KeysSETS = jedisUtil.new Keys();
		}
		jedis_dball_basestation=jedisUtil.new Sets();
		jedis_dball_basestation_cuid=jedisUtil.new Hash();
		jedis_files_basestation=jedisUtil.new Hash();
		DatBasestationVOExample example = new DatBasestationVOExample();
		DatBasestationVOExample.Criteria criteria=example.createCriteria();
		criteria.andPrvIdEqualTo(prv_id);
		List<DatBasestationVO> dataBaseRecList = datBasestationVOMapper.selectByExample(example);
		List<String> bufferDBCom = new ArrayList<String>();
		List<String> bufferDBSysid = new ArrayList<String>();
		for (DatBasestationVO datBasestationRedis : dataBaseRecList){
			sysnumEntryMap.put(datBasestationRedis.getBasestationCuid()+"_"+datBasestationRedis.getPrvId(), JSONObject.fromObject(datBasestationRedis).toString());
			DatBasestationVO datBasestationTmp = (DatBasestationVO)datBasestationRedis.clone();
			bufferDBCom.add(JSONObject.fromObject(datBasestationTmp).toString());
			bufferDBSysid.add(datBasestationRedis.getBasestationCuid()+"_"+datBasestationRedis.getPrvId());
		}
		jedis_dball_basestation.sadd("DB_SYSID_"+cont_rediskey, bufferDBSysid);
		jedis_dball_basestation.sadd("DB_COM_"+cont_rediskey, bufferDBCom);
		if(sysnumEntryMap.keySet().size()>0){
			jedis_dball_basestation_cuid.hmset(DB_SYSID_ENTRY_Prex+cont_rediskey, sysnumEntryMap);
		}
	}
	//获取比对的redis集合
	private void getStationRedisCompareSets(String prv_id,String cont_rediskey){
		if(jedisUtil==null){
			jedisUtil= JedisUtil.getInstance();
		}
		Map<String, String> sysnumEntryMap = new HashMap<>();
		if(jedis_KeysSETS==null) {
			jedis_KeysSETS = jedisUtil.new Keys();
		}
		jedis_dball_basesite=jedisUtil.new Sets();
		jedis_dball_basesite_cuid=jedisUtil.new Hash();
		jedis_files_basesite = jedisUtil.new Hash();
		DatBasesiteVOExample example=new DatBasesiteVOExample();
		DatBasesiteVOExample.Criteria criteria=example.createCriteria();
		criteria.andPrvIdEqualTo(prv_id);
		List<DatBasesiteVO> siteList= datBasesiteVOMapper.selectByExample(example);
		 List<String> bufferDBCom = new ArrayList<String>();
		 List<String> bufferDBSysid = new ArrayList<String>();
		for (DatBasesiteVO datBasesiteRedis : siteList){
			sysnumEntryMap.put(datBasesiteRedis.getBasesiteCuid()+"_"+datBasesiteRedis.getPrvId(), JSONObject.fromObject(datBasesiteRedis).toString());
			DatBasesiteVO datBasesiteTmp = (DatBasesiteVO)datBasesiteRedis.clone();
			bufferDBCom.add(JSONObject.fromObject(datBasesiteTmp).toString());
			bufferDBSysid.add(datBasesiteRedis.getBasesiteCuid()+"_"+datBasesiteRedis.getPrvId());
		}
		jedis_dball_basesite.sadd("DB_COM_"+cont_rediskey, bufferDBCom);
		jedis_dball_basesite.sadd("DB_SYSID_"+cont_rediskey, bufferDBSysid);
		if(sysnumEntryMap.keySet().size()>0){
			jedis_dball_basesite_cuid.hmset(DB_SYSID_ENTRY_Prex+cont_rediskey, sysnumEntryMap);
		}
	}
	
	private JSONObject getRegidlistByPrvid(String prvId){
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("prvId", prvId);
		List<Map<String, String>> list= sysRegionDao.getRegionsByPrvid(paramMap);
		JSONObject jsonO = new JSONObject();
		for(Map<String,String> map :list){
			jsonO.put(map.get("reg_code"), map.get("reg_id"));
		}
		return jsonO;
	}
	
	@Override
	public boolean updateBasesite(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo,String delimiter) {
		List<DatBasesiteVO> datas = new ArrayList<DatBasesiteVO>();
		for (String key : map.keySet()) {
			DatBasesiteVO datBasesiteVO = this.dealDatBasesiteVO(typeName, columns, map.get(key),sysProvinceVO,delimiter,null);
			datBasesiteVO.setUpdate_time(new Date());
			datBasesiteVO.setUpdate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
			datBasesiteVO.setUpdate_ip(taskInfo.getOperateUserIp());
			datas.add(datBasesiteVO);
		}
		dealBasesiteRegion(datas,sysProvinceVO);

		boolean bBatchUpdate = false;
		for(int i=0, length = datas.size(); i<length; i+=iBatchCount){
			int j=(i+500>datas.size())?datas.size():i+iBatchCount;
			List<DatBasesiteVO> tmp = datas.subList(i, j);
			bBatchUpdate = datBasesiteVOMapper.batchUpdate(tmp);
		}
		return bBatchUpdate;
	}

	@Override
	public boolean delBasesite(String pkName,String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO) {
		List<String> list=ValueUtil.dealCuids(columns,map);
		String prvId=sysProvinceVO.getPrvId();

		HashMap<String,Object> pMap=new HashMap<String,Object>();
		pMap.put("prvId", prvId);
		pMap.put("list", list);
		return datBasesiteVOMapper.delByCuidsAndPrvid(pMap);
	}

	@Override
	public boolean InsertSysRegion(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter) {
		List<SysRegionVO> datas = new ArrayList<SysRegionVO>();
		for (String key : map.keySet()) {
			SysRegionVO sysRegionVO = this.dealSysRegionVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			datas.add(sysRegionVO);
		}
		boolean bBatchInsert = false;
		for(int i=0, length = datas.size(); i<length; i+=iBatchCount){
			int j=(i+500>datas.size())?datas.size():i+iBatchCount;
			List<SysRegionVO> tmp = datas.subList(i, j);
			bBatchInsert = sysRegionVOMapper.batchUpdate(tmp);
		}
		return bBatchInsert;
	}

	@Override
	public boolean updateSysRegion(String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter) {
		List<SysRegionVO> datas = new ArrayList<SysRegionVO>();
		for (String key : map.keySet()) {
			SysRegionVO sysRegionVO = this.dealSysRegionVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			datas.add(sysRegionVO);
		}
		boolean bBatchUpdate = false;
		for(int i=0, length = datas.size(); i<length; i+=iBatchCount){
			int j=(i+500>datas.size())?datas.size():i+iBatchCount;
			List<SysRegionVO> tmp = datas.subList(i, j);
			bBatchUpdate = sysRegionVOMapper.batchUpdate(tmp);
		}
		return bBatchUpdate;
	}

	@Override
	public boolean delSysRegion(String pkName,String typeName, List<String> columns, Map<String, String> map,SysProvinceVO sysProvinceVO) {
		List<String> list=ValueUtil.dealCuids(columns,map);
		String prvId=sysProvinceVO.getPrvId();
		HashMap<String,Object> pMap=new HashMap<String,Object>();
		pMap.put("prvId", prvId);
		pMap.put("list", list);
		String[] a=null;
		return sysRegionVOMapper.deByCuids(list.toArray(a));
	}

	public String[] getDBparms(String perfix){

		String[] result=new String[3];
		String pkName="";
		String tableName="";
		if(perfix.equalsIgnoreCase(DataType.RESOURCE_CITY)){
			pkName="city_cid";
			tableName=TableType.RESOURCE_SYS_REGION;
		}
		else if(perfix.equalsIgnoreCase(DataType.RESOURCE_REGION)){
			pkName="county_cid";
			tableName=TableType.RESOURCE_SYS_REGION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_SITE)){
        	pkName="site_cid";
        	tableName=TableType.RESOURCE_DAT_BASESITE;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_ROOM)){
        	pkName="room_cid";
        	tableName=TableType.RESOURCE_DAT_BASERESOURCE;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_RESOURCESPOT)){
        	pkName="resource_cid";
        	tableName=TableType.RESOURCE_DAT_BASERESOURCE;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_HOTSPOT)){
        	pkName="hotspot_cid";
        	tableName=TableType.RESOURCE_DAT_BASERESOURCE;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_POSITION)){
        	pkName="position_cid";
        	tableName=TableType.RESOURCE_DAT_BASERESOURCE;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_WIRELESS2G)){
        	pkName="bts_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_WIRELESS3G)){
        	pkName="nodeb_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_WIRELESS4G)){
        	pkName="enodeb_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_AP)){
        	pkName="ap_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_HOTSPOTSWITCH)){
        	pkName="hotspotswitch_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_RRU)){
        	pkName="rru_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_REPEATER)){
        	pkName="repeater_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_DISTRIBUTION)){
        	pkName="distribution_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_ONU)){
        	pkName="onu_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_OLT)){
        	pkName="olt_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
		}
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_PTN)){
        	pkName="ptn_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
        }
        else if(perfix.equalsIgnoreCase(DataType.RESOURCE_OTN)){
        	pkName="otn_cid";
        	tableName=TableType.RESOURCE_DAT_BASESTATION;
        }
		result[0]=pkName;
		result[1]=perfix;
		result[2]=tableName;
		return result;
	}
	
	
	private SysRegionVO dealSysRegionVO(String typeName, List<String> columns, String values,SysProvinceVO sysProvinceVO,String delimiter) {

		SysRegionVO sysRegionVO = new SysRegionVO();
		int columnSize=columns.size();
		String[] valueArray=ValueUtil.dealValues(columnSize,values,delimiter);
		switch (typeName) {

		case DataType.RESOURCE_CITY:
			sysRegionVO.setLevel(1);
			SysRegionUtil.dealCityColumns(sysRegionVO, columns, valueArray,sysProvinceVO);
			break;
		case DataType.RESOURCE_REGION:
			sysRegionVO.setLevel(2);
			SysRegionUtil.dealRegionColumns(sysRegionVO, columns, valueArray,sysProvinceVO);
			break;
		}
		//sysRegionVO.setRegId(IDUtils.getID());
		sysRegionVO.setIsValid(0);
		sysRegionVO.setRegState(0);
		return sysRegionVO;
	}
	 

	private DatBasesiteVO dealDatBasesiteVO(String typeName, List<String> columns, String values,SysProvinceVO sysProvinceVO,String delimiter,JSONObject regionCache) {

		DatBasesiteVO datBasesiteVO = new DatBasesiteVO();
		int columnSize=columns.size();
		String[] valueArray=ValueUtil.dealValues(columnSize,values,delimiter);
		DatBaseSiteUtil.dealSiteColumns(datBasesiteVO, columns, valueArray,sysProvinceVO,regionCache);
		datBasesiteVO.setDataFrom(2);
		datBasesiteVO.setAuditingState(0);
		datBasesiteVO.setBasesiteState(dealStatus(datBasesiteVO.getBasesiteState()));
		return datBasesiteVO;
	}


	private DatBaseresourceVO dealDatBaseresourceVO(String typeName, List<String> columns, String values,SysProvinceVO sysProvinceVO,String delimiter,String prv_id, JSONObject ProvinceCache, JSONObject RegionCache) {


		DatBaseresourceVO datBaseresourceVO = new DatBaseresourceVO();
		int baseresource_type = 0;
		int columnSize=columns.size();
		String[] valueArray=ValueUtil.dealValues(columnSize,values,delimiter);
		
		if(DataType.RESOURCE_ROOM.equalsIgnoreCase(typeName)){
			baseresource_type = 0;

			DatBaseResourceUtil.dealRoomColumns(datBaseresourceVO, columns, valueArray,prv_id,ProvinceCache,RegionCache);

		}
		if(DataType.RESOURCE_RESOURCESPOT.equalsIgnoreCase(typeName)){
			baseresource_type = 1;

			DatBaseResourceUtil.dealResourceSpotColumns(datBaseresourceVO, columns, valueArray,prv_id,ProvinceCache,RegionCache);

		}
		if(DataType.RESOURCE_HOTSPOT.equalsIgnoreCase(typeName)){
			baseresource_type = 2;

			DatBaseResourceUtil.dealHotspotColumns(datBaseresourceVO, columns, valueArray,prv_id,ProvinceCache,RegionCache);

		}
		if(DataType.RESOURCE_POSITION.equalsIgnoreCase(typeName)){
			baseresource_type = 3;

			DatBaseResourceUtil.dealPositionColumns(datBaseresourceVO, columns, valueArray,prv_id,ProvinceCache,RegionCache);

		}
		datBaseresourceVO.setDataFrom(2);
		datBaseresourceVO.setAuditingState(0);
		datBaseresourceVO.setBaseresourceState(dealStatus(datBaseresourceVO.getBaseresourceState()));
		datBaseresourceVO.setBaseresourceType(baseresource_type);
		return datBaseresourceVO;
	}

	private DatBasestationVO dealDatBasestationVO(String typeName, List<String> columns, String values,SysProvinceVO sysProvinceVO,String delimiter) {

		DatBasestationVO datBasestationVO = new DatBasestationVO();
		int basestation_category = 1;
		int columnSize=columns.size();
		String[] valueArray=ValueUtil.dealValues(columnSize,values,delimiter);

		if(DataType.RESOURCE_AP.equalsIgnoreCase(typeName)){
			basestation_category = 1;
			DatBaseStationUtil.dealApColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_WIRELESS2G.equalsIgnoreCase(typeName)){
			basestation_category = 2;
			DatBaseStationUtil.dealWireless2GColumns(datBasestationVO,columns,valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_WIRELESS3G.equalsIgnoreCase(typeName)){
			basestation_category = 3;
			DatBaseStationUtil.dealWireless3GColumns(datBasestationVO,columns,valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_WIRELESS4G.equalsIgnoreCase(typeName)){
			basestation_category = 4;
			DatBaseStationUtil.dealWireless4GColumns(datBasestationVO,columns,valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_HOTSPOTSWITCH.equalsIgnoreCase(typeName)){
			basestation_category = 5;
			DatBaseStationUtil.dealHotspotSwitchColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_RRU.equalsIgnoreCase(typeName)){
			basestation_category = 6;
			DatBaseStationUtil.dealRruColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_REPEATER.equalsIgnoreCase(typeName)){
			basestation_category = 7;
			DatBaseStationUtil.dealRepeaterColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_DISTRIBUTION.equalsIgnoreCase(typeName)){
			basestation_category = 8;
			DatBaseStationUtil.dealDistributionColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_ONU.equalsIgnoreCase(typeName)){
			basestation_category = 9;
			DatBaseStationUtil.dealOnuColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_OLT.equalsIgnoreCase(typeName)){
			basestation_category = 10;
			DatBaseStationUtil.dealOLTColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_PTN.equalsIgnoreCase(typeName)){
			basestation_category = 11;
			DatBaseStationUtil.dealPTNColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		if(DataType.RESOURCE_OTN.equalsIgnoreCase(typeName)){
			basestation_category = 12;
			DatBaseStationUtil.dealOTNColumns(datBasestationVO, columns, valueArray,sysProvinceVO);
		}
		datBasestationVO.setDataFrom(2);
		datBasestationVO.setBasestationState(dealStatus(datBasestationVO.getBasestationState()));
		datBasestationVO.setBasestationCategory(basestation_category);
		return datBasestationVO;
	}

	
	private void dealDatBasestationResourceId(List<DatBasestationVO> datas,List<String> resourceCuIds,SysProvinceVO sysProvinceVO) {
		
		DatBaseresourceVOExample example=new DatBaseresourceVOExample();
	    com.xunge.model.basedata.DatBaseresourceVOExample.Criteria criteria=example.createCriteria();
		criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
		criteria.andBaseresourceCuidIn(resourceCuIds);
		List<DatBaseresourceVO> resourceList= datBaseresourceVOMapper.selectByExample(example);
		for(DatBasestationVO station:datas){
			for(DatBaseresourceVO source:resourceList){
				if (null != station && null != source && null != station.getBaseresourceCuid() && null != source.getBaseresourceCuid()){
					if(station.getBaseresourceCuid().equalsIgnoreCase(source.getBaseresourceCuid())){
						station.setBaseresourceId(source.getBaseresourceId());
						break;
					}
				}
			}
		}
	}
	
	
	private void dealDatBaseresourceSiteId(List<DatBaseresourceVO> datas,List<String> siteCuIds,SysProvinceVO sysProvinceVO) {
		
		DatBasesiteVOExample example=new DatBasesiteVOExample();
		DatBasesiteVOExample.Criteria criteria=example.createCriteria();
		criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
//		criteria.andBaseresourceCuidIn(siteCuIds);
		List<DatBasesiteVO> siteList= datBasesiteVOMapper.selectByExample(example);
		for(DatBaseresourceVO resource:datas){
			for(DatBasesiteVO site:siteList){
				if (null != resource && null != site && null != resource.getBasesiteCuid() && null != site.getBasesiteCuid()){
					if(resource.getBasesiteCuid().equalsIgnoreCase(site.getBasesiteCuid())){
						resource.setBasesiteId(site.getBasesiteId());
						break;
					}
				}
			}
		}
	}
	
	private int dealStatus(int status){
		
		int result=status;
		switch(status){
		case 1:
			result=4;
		    break;
		case 2:
			result=6;
			break;
		case 3:
			result=2;
			break;
		case 4:
			result=1;
			break;
		case 5:
			result=5;
			break;
		case 6:
			result=3;
			break;
		case 7:
			result=7;
			break;
		case 8:
			result=8;
			break;
		}
		return result;
	}
	
	private List<SysRegionVO> getSysRegion(SysProvinceVO sysProvinceVO){
		
		List<SysRegionVO> listreg=new ArrayList<SysRegionVO>();
		SysRegionVO sysRegionVO=new SysRegionVO();
		sysRegionVO.setPrvId(sysProvinceVO.getPrvId());
		sysRegionVO.setRegState(StateComm.STATE_0);
		listreg=sysRegionVOMapper.getAddress(sysRegionVO);
		return listreg;
	}
	
	private void dealBaseresourceRegion(List<DatBaseresourceVO> datas,SysProvinceVO sysProvinceVO){
		List<SysRegionVO> listreg=getSysRegion(sysProvinceVO);
		for(DatBaseresourceVO datBaseresourceVO:datas){
			String pregId=datBaseresourceVO.getPregId();
			String regId=datBaseresourceVO.getRegId();
			for(SysRegionVO region:listreg){
				if(region.getRegCode().equalsIgnoreCase(regId)){
					datBaseresourceVO.setRegId(region.getRegId());
					datBaseresourceVO.setRegName(region.getRegName());
				}
				if(region.getRegCode().equalsIgnoreCase(pregId)){
					datBaseresourceVO.setPregId(region.getRegId());
					datBaseresourceVO.setPregName(region.getRegName());
				}
			}
		}
	}
	
	private void dealBasesiteRegion(List<DatBasesiteVO> datas,SysProvinceVO sysProvinceVO){
		List<SysRegionVO> listreg=getSysRegion(sysProvinceVO);
		for(DatBasesiteVO datBasesiteVO:datas){
			String pregId=datBasesiteVO.getPregId();
			String regId=datBasesiteVO.getRegId();
			for(SysRegionVO region:listreg){
				if(region.getRegCode().equalsIgnoreCase(regId)){
					datBasesiteVO.setRegId(region.getRegId());
					datBasesiteVO.setRegName(region.getRegName());
				}
				if(region.getRegCode().equalsIgnoreCase(pregId)){
					datBasesiteVO.setPregId(region.getRegId());
					datBasesiteVO.setPregName(region.getRegName());
				}
			}
		}
	}

	@Override
	public StringBuffer insertIntoDB(String typeName,
			List<String> columns, Map<String, String> map,
			SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo, String delimiter,String tableName)
			throws Exception {
		StringBuffer result = null;
		StringBuffer sb = new StringBuffer();
		switch(tableName.toLowerCase()){
		
		/*	case TableType.RESOURCE_SYS_REGION:
				result=resourceService.InsertSysRegion(typeName,columns,map,sysProvinceVO,String.valueOf(BatchControlComm.resDelimiter));
				break;*/
			case TableType.RESOURCE_DAT_BASESITE:
				
				result=InsertBasesite(typeName,columns,map,sysProvinceVO, taskInfo,BatchControlComm.resDelimiterStr);
				break;
			case TableType.RESOURCE_DAT_BASERESOURCE:
			
				result=InsertBaseresource(typeName,columns,map,sysProvinceVO, taskInfo,BatchControlComm.resDelimiterStr);
				
				break;
			case TableType.RESOURCE_DAT_BASESTATION:
				
				result=InsertBaseStation(typeName,columns,map,sysProvinceVO, taskInfo,BatchControlComm.resDelimiterStr);
				break;
			}
		return result;

	}

	@Override
	public int deleteFromDB(String pkName, String typeName,
			List<String> columns, Map<String, String> map,
			SysProvinceVO sysProvinceVO, String delimiter,String tableName) throws Exception {
		boolean result=false;
		switch(tableName.toLowerCase()){
		
		/*case TableType.RESOURCE_SYS_REGION:
			result=resourceService.delSysRegion(typeName,columns,map,sysProvinceVO);
			break;*/
		case TableType.RESOURCE_DAT_BASESITE:
			
			result=delBasesite(pkName,typeName,columns,map,sysProvinceVO);
			break;
		case TableType.RESOURCE_DAT_BASERESOURCE:
		
			result=delBaseresource(pkName,typeName,columns,map,sysProvinceVO);
			
			break;
		case TableType.RESOURCE_DAT_BASESTATION:
			
			result=delBaseStation(pkName,typeName,columns,map,sysProvinceVO);
			break;
		}
		return 0;
	}
}
