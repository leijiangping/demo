package com.xunge.service.basedata.ring.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xunge.comm.BatchControlComm;
import com.xunge.dao.basedata.DatBaseresourceVOMapper;
import com.xunge.dao.basedata.ring.MeterPerfVOMapper;
import com.xunge.dao.basedata.ring.PowerPerfVOMapper;
import com.xunge.dao.region.ISysRegionDao;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.DatBaseresourceVOExample;
import com.xunge.model.basedata.DatBaseresourceVOExample.Criteria;
import com.xunge.model.basedata.ring.MeterPerfVO;
import com.xunge.model.basedata.ring.MeterPerfVOExample;
import com.xunge.model.basedata.ring.PowerPerfVO;
import com.xunge.model.basedata.ring.PowerPerfVOExample;
import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.service.basedata.ring.IRingService;
import com.xunge.service.job.util.MeterPerfUtil;
import com.xunge.service.job.util.PowerPerfUtil;
import com.xunge.util.DataType;
import com.xunge.util.ExcelExportHelper;
import com.xunge.util.IDUtils;
import com.xunge.util.StrUtil;
import com.xunge.util.TableType;
import com.xunge.util.ValueUtil;
import com.xunge.util.branch.DBBranchUtil;
import com.xunge.util.redis.JedisUtil;
import com.xunge.util.redis.JedisUtil.Keys;

@Service
public class RingServiceImpl implements IRingService{

	private static final Logger LOGGER = Logger.getLogger(RingServiceImpl.class);
	
	@Resource
	private MeterPerfVOMapper meterPerfVOMapper;
	
	@Resource
	private PowerPerfVOMapper powerPerfVOMapper;
	
	@Resource
	private DatBaseresourceVOMapper datBaseresourceVOMapper;
	
	@Resource
	private ISysRegionDao sysRegionDao;
	
	@Resource
    private DBBranchUtil dbUtil;
	
	private int pageSize=5000;
	
	private JedisUtil jedisUtil = null;
	private volatile JedisUtil.Sets Jedis_Set=null;
	private volatile JedisUtil.Hash jedis_Hash=null;
	private volatile JedisUtil.Keys jedis_KeysSETS=null;
	
	private String DBPOWER ="SET_DB_POWER_" ;
	private String FILEPOWER ="SET_FILE_POWER_";
	private String FILEPOWERID ="SET_FILE_POWERID_";
	private String DBPOWERID = "SET_DB_POWERID_";
	private String HASHFILEPOWER = "HASH_FILE_POWER_";

	private String DBMETER ="SET_DB_METER_";
	private String FILEMETER ="SET_FILE_METER_";
	private String FILEMETERID ="SET_FILE_METERID_";
	private String DBMETERID = "SET_DB_METERID_";
	private String HASHFILEMETER="HASH_FILE_METER_";
	
	private String DIFFMETERID = "SET_DIFFID_METER_";
	private String DIFFPOWERID = "SET_DIFFID_POWER_";
	
	private String HASHRESCUID = "HASH_RESOURCECUID_";
	private String HASHRESCODE = "HASH_RESOURCECODE_";
	
	private String UPDATEMETER="HASH_UPDATE_METER_";
	private String UPDATEPOWER="HASH_UPDATE_POWER_";
	
	@Override
	public StringBuffer InsertMeter(String typeName, List<String> columns, Map<String, String> map,
			SysProvinceVO sysProvinceVO,TaskInfoVO taskInfo,String delimiter) {
		//初始化redis
		initredis();
		StringBuffer loginfoBF= new StringBuffer(); 
		List<MeterPerfVO> insert = new ArrayList<>();
		List<MeterPerfVO> update = new ArrayList<>();
		String cont_rediskey = "Ring_"+System.currentTimeMillis();
		long start = System.currentTimeMillis();
		try {
			String prvId = sysProvinceVO.getPrvId();
			
			LOGGER.info("-----------------------开始读取数据库并解析为数据库集合\r\n");
			long startprase = System.currentTimeMillis();
			List<MeterPerfVO> datas = meterPerfVOMapper.selectForRedis(prvId);
			List<String> dbmeter = new ArrayList<>();
			List<String> dbmeterid = new ArrayList<>();
			for(MeterPerfVO mpf : datas){
				dbmeter.add(JSONObject.fromObject(mpf).toString());
				dbmeter.add(mpf.getCodeType()+mpf.getResourceCode());
			}
			Jedis_Set.sadd(DBMETER+cont_rediskey,dbmeter);
			Jedis_Set.sadd(DBMETERID+cont_rediskey,dbmeterid);
			long endprase = System.currentTimeMillis();
			LOGGER.info("-----------------------解析数据库即可完成:耗时"+(endprase-startprase)/60000+"min\r\n ");
			//获取关联的baseresource
			LOGGER.info("-----------------------开始解析关联res为数据库集合");
			long startresprase = System.currentTimeMillis();
			 DatBaseresourceVOExample example=new DatBaseresourceVOExample();
			 DatBaseresourceVOExample.Criteria criteria=example.createCriteria();
			 criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
			 List<DatBaseresourceVO> dbreslist = datBaseresourceVOMapper.selectByExample(example);
			 Map<String, String> cuidmap = new HashMap<>();
			 Map<String, String> codemap = new HashMap<>();
			 for(DatBaseresourceVO dbv : dbreslist){
				 cuidmap.put(dbv.getBaseresourceCuid(),JSONObject.fromObject(dbv).toString());
				 codemap.put(dbv.getBaseresourceCode(),JSONObject.fromObject(dbv).toString());
			 }
			 jedis_Hash.hmset(HASHRESCUID+cont_rediskey, cuidmap);
			 jedis_Hash.hmset(HASHRESCODE+cont_rediskey, codemap);
			 long endresprase = System.currentTimeMillis();
			 LOGGER.info("-----------------------解析关联res为数据库集合完成:耗时"+(endresprase-startresprase)/60000+"min\r\n ");
			 //获取文件的内容的集合
			long startTime=System.currentTimeMillis();
			LOGGER.info("-----------------------开始解析文件为数据库集合--\r\n");
			Map<String,MeterPerfVO> filter = new HashMap<>();
			for (String key : map.keySet()) {
				MeterPerfVO meterPerfVO = dealMeterPerfVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
				filter.put(meterPerfVO.getCodeType()+meterPerfVO.getResourceCode(), meterPerfVO);
			}
			Map<String,String> redismap = new HashMap<>();
			List<String> filemeter = new ArrayList<>();
			List<String> filemeterid = new ArrayList<>();
			for(String key:filter.keySet()){
				filemeter.add(JSONObject.fromObject(filter.get(key)).toString());
				filemeterid.add(filter.get(key).getCodeType()+filter.get(key).getResourceCode());
				redismap.put(filter.get(key).getCodeType()+filter.get(key).getResourceCode(),JSONObject.fromObject(filter.get(key)).toString());
			}
			Jedis_Set.sadd(FILEMETER+cont_rediskey,filemeter);
			Jedis_Set.sadd(FILEMETERID+cont_rediskey, filemeterid);
			jedis_Hash.hmset(HASHFILEMETER+cont_rediskey, redismap);
			long endTime=System.currentTimeMillis();
			LOGGER.info("-----------------------解析文件为数据库集合完成:耗时"+(endTime-startTime)/60000+"min\r\n ");
			loginfoBF.append("解析文件共"+redismap.size()+"行,耗时"+(endTime-startTime)/60000+"min\r\n ");
			//生成新增list
			LOGGER.info("-----------------------开始生成插入更新集合--- \r\n");
			long startdifftime=System.currentTimeMillis();
			Jedis_Set.sdiffstore(DIFFMETERID+cont_rediskey,FILEMETERID+cont_rediskey,DBMETERID+cont_rediskey);
			Set<String> diffmeter = Jedis_Set.smembers(DIFFMETERID+cont_rediskey);
			Iterator<String> diffiterator = diffmeter.iterator();
			while(diffiterator.hasNext()){
				String meterid = diffiterator.next();
				if(jedis_Hash.hexists(HASHFILEMETER+cont_rediskey,meterid)){
					JSONObject model =JSONObject.fromObject(jedis_Hash.hmget(HASHFILEMETER+cont_rediskey, meterid).get(0));
					MeterPerfVO m = (MeterPerfVO) JSONObject.toBean(model, MeterPerfVO.class);
					m.setMeterId(IDUtils.getID());
					m.setCreate_user(taskInfo.getOperateUser());
					m.setCreate_time(new Date());
					m.setCreate_ip(taskInfo.getOperateUserIp());
					insert.add(m);
				}
			}
			
			//生成更新list
			Jedis_Set.sdiffstore(UPDATEMETER+cont_rediskey, FILEMETER+cont_rediskey,DBMETER+cont_rediskey);
			Set<String> updatemeter = Jedis_Set.smembers(UPDATEMETER+cont_rediskey);
			Iterator<String> updateiterator = updatemeter.iterator();
			while(updateiterator.hasNext()){
				if(Jedis_Set.scard(DBMETER+cont_rediskey)>0){
					JSONObject model =JSONObject.fromObject(updateiterator.next());
					MeterPerfVO m =  (MeterPerfVO) JSONObject.toBean(model, MeterPerfVO.class);
					if(!diffmeter.contains(m.getCodeType()+m.getResourceCode())){
						m.setUpdate_user(taskInfo.getOperateUser());
						m.setUpdate_time(new Date());
						m.setUpdate_ip(taskInfo.getOperateUserIp());
						update.add(m);
					}
				}
			}
			dealRegInfo(insert, MeterPerfVO.class, cont_rediskey);
			long enddifftime=System.currentTimeMillis();
			LOGGER.info("-----------------------生成集合完成:耗时"+(enddifftime-startdifftime)/60000+"min \r\n");
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally{
			jedis_KeysSETS.batchDel(cont_rediskey);
		}
		
		try{
				String info1= dbUtil.branch(14, insert);
				loginfoBF.append("\r\n动环新增新增入库："+info1);
				String info2= dbUtil.branch(15, update);
				loginfoBF.append(";动环更新入库："+info2);
			}
			catch(Exception e){
				loginfoBF.append("\r\n"+e.toString());
			}
		long end=System.currentTimeMillis();
		
		LOGGER.info("总耗时:"+(end-start)/60000+"新增动环数据数据成功：智能电表数据"+insert.size() + "条；更新动环数据数据成功：智能电表数据"+update.size()+"条.\r\n");
		return loginfoBF;
	}

	@Override
	public boolean updateMeter(String typeName, List<String> columns, Map<String, String> map,
			SysProvinceVO sysProvinceVO,TaskInfoVO taskInfo,String delimiter) {
		
		List<MeterPerfVO> datas = new ArrayList<MeterPerfVO>();
		List<MeterPerfVO> cuidDatas = new ArrayList<MeterPerfVO>();
		List<MeterPerfVO> codeDatas = new ArrayList<MeterPerfVO>();
		List<String>cuids=new ArrayList<String>();  
		List<String>codes=new ArrayList<String>();
		for (String key : map.keySet()) {
			MeterPerfVO meterPerfVO = dealMeterPerfVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			if(1==meterPerfVO.getCodeType()){
				cuidDatas.add(meterPerfVO);
				cuids.add(meterPerfVO.getResourceCode()+"");
			}
			else if(2==meterPerfVO.getCodeType()){
				codeDatas.add(meterPerfVO);
				codes.add(meterPerfVO.getResourceCode()+"");
			}
			meterPerfVO.setUpdate_user(taskInfo.getOperateUser());
			meterPerfVO.setUpdate_time(new Date());
			meterPerfVO.setUpdate_ip(taskInfo.getOperateUserIp());
		}
		dealMeterRegInfo(cuidDatas,codeDatas,cuids,codes,sysProvinceVO);
		datas.addAll(cuidDatas);
		datas.addAll(codeDatas);

		boolean bBatchUpdate = false;
		for(int i=0, length = datas.size(); i<length; i+=BatchControlComm.iBatchCount){
			int j=(i+BatchControlComm.iBatchCount>datas.size())?datas.size():i+BatchControlComm.iBatchCount;
			List<MeterPerfVO> tmp = datas.subList(i, j);
			bBatchUpdate = meterPerfVOMapper.batchUpdate(tmp);
		}
		return bBatchUpdate;
	}

	@Override
	public boolean delMeter(String pkName, String typeName, List<String> columns, Map<String, String> map,
			SysProvinceVO sysProvinceVO) {
		List<String> list=ValueUtil.dealCuids(columns,map);
		String prvId=sysProvinceVO.getPrvId();

		HashMap<String,Object> pMap=new HashMap<String,Object>();
		pMap.put("prvId", prvId);
		pMap.put("list", list);
		return meterPerfVOMapper.delByCuidsAndPrvid(pMap);
	}

	@Override
	public StringBuffer InsertPower(String typeName, List<String> columns, Map<String, String> map,
			SysProvinceVO sysProvinceVO,TaskInfoVO taskInfo,String delimiter) {
		      //初始化redis
				initredis();
				StringBuffer loginfoBF= new StringBuffer(); 
				List<PowerPerfVO> insert = new ArrayList<>();
				List<PowerPerfVO> update = new ArrayList<>();
				String cont_rediskey = "Ring_"+System.currentTimeMillis();
				long start = System.currentTimeMillis();
				try {
					String prvId = sysProvinceVO.getPrvId();
					
					LOGGER.info("-----------------------开始读取数据库并解析为数据库集合\r\n");
					long startprase = System.currentTimeMillis();
					List<PowerPerfVO> datas = powerPerfVOMapper.selectForRedis(prvId);
					List<String> dbpower = new ArrayList<>();
					List<String> dbpowerid = new ArrayList<>();
					for(PowerPerfVO ppf : datas){
						dbpower.add(JSONObject.fromObject(ppf).toString());
						dbpowerid.add(ppf.getCodeType()+ppf.getResourceCode());
					}
					Jedis_Set.sadd(DBPOWER+cont_rediskey,dbpower);
					Jedis_Set.sadd(DBPOWERID+cont_rediskey,dbpowerid);
					long endprase = System.currentTimeMillis();
					LOGGER.info("-----------------------解析数据库即可完成:耗时"+(endprase-startprase)/60000+"min\r\n ");
					//获取关联的baseresource
					LOGGER.info("-----------------------开始解析关联res为数据库集合");
					long startresprase = System.currentTimeMillis();
					 DatBaseresourceVOExample example=new DatBaseresourceVOExample();
					 DatBaseresourceVOExample.Criteria criteria=example.createCriteria();
					 criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
					 List<DatBaseresourceVO> dbreslist = datBaseresourceVOMapper.selectByExample(example);
					 Map<String, String> cuidmap = new HashMap<>();
					 Map<String, String> codemap = new HashMap<>();
					 for(DatBaseresourceVO dbv : dbreslist){
						 cuidmap.put(dbv.getBaseresourceCuid(),JSONObject.fromObject(dbv).toString());
						 codemap.put(dbv.getBaseresourceCode(),JSONObject.fromObject(dbv).toString());
					 }
					 jedis_Hash.hmset(HASHRESCUID+cont_rediskey, cuidmap);
					 jedis_Hash.hmset(HASHRESCODE+cont_rediskey, codemap);
					 long endresprase = System.currentTimeMillis();
					 LOGGER.info("-----------------------解析关联res为数据库集合完成:耗时"+(endresprase-startresprase)/60000+"min\r\n ");
					 //获取文件的内容的集合
					long startTime=System.currentTimeMillis();
					LOGGER.info("-----------------------开始解析文件为数据库集合--\r\n");
					Map<String,PowerPerfVO> filter = new HashMap<>();
					for (String key : map.keySet()) {
						PowerPerfVO powerPerfVO = dealPowerPerfVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
						filter.put(powerPerfVO.getCodeType()+powerPerfVO.getResourceCode(), powerPerfVO);
					}
					Map<String,String> redismap = new HashMap<>();
					List<String> filepower = new ArrayList<>();
					List<String> filepowerid = new ArrayList<>();
					for(String key:filter.keySet()){
						filepower.add(JSONObject.fromObject(filter.get(key)).toString());
						filepowerid.add(filter.get(key).getCodeType()+filter.get(key).getResourceCode());
						redismap.put(filter.get(key).getCodeType()+filter.get(key).getResourceCode(),JSONObject.fromObject(filter.get(key)).toString());
					}
					Jedis_Set.sadd(FILEPOWER+cont_rediskey,filepower);
					Jedis_Set.sadd(FILEPOWERID+cont_rediskey, filepowerid);
					jedis_Hash.hmset(HASHFILEPOWER+cont_rediskey, redismap);
					long endTime=System.currentTimeMillis();
					LOGGER.info("-----------------------解析文件为数据库集合完成:耗时"+(endTime-startTime)/60000+"min\r\n ");
					loginfoBF.append("解析文件共"+redismap.size()+"行,耗时"+(endTime-startTime)/60000+"min\r\n ");
					//生成新增list
					LOGGER.info("-----------------------开始生成插入更新集合--- \r\n");
					long startdifftime=System.currentTimeMillis();
					Jedis_Set.sdiffstore(DIFFPOWERID+cont_rediskey,FILEPOWERID+cont_rediskey,DBPOWERID+cont_rediskey);
					Set<String> diffpower = Jedis_Set.smembers(DIFFPOWERID+cont_rediskey);
					Iterator<String> diffiterator = diffpower.iterator();
					while(diffiterator.hasNext()){
						String powerid = diffiterator.next();
						if(jedis_Hash.hexists(HASHFILEPOWER+cont_rediskey,powerid)){
							JSONObject model =JSONObject.fromObject(jedis_Hash.hmget(HASHFILEPOWER+cont_rediskey, powerid).get(0));
							PowerPerfVO m = (PowerPerfVO) JSONObject.toBean(model, PowerPerfVO.class);
							m.setPowerId(IDUtils.getID());
							m.setCreate_user(taskInfo.getOperateUser());
							m.setCreate_time(new Date());
							m.setCreate_ip(taskInfo.getOperateUserIp());
							insert.add(m);
						}
					}
					
					//生成更新list
					Jedis_Set.sdiffstore(UPDATEPOWER+cont_rediskey, FILEPOWER+cont_rediskey,DBPOWER+cont_rediskey);
					Set<String> updatepower = Jedis_Set.smembers(UPDATEPOWER+cont_rediskey);
					Iterator<String> updateiterator = updatepower.iterator();
					if(Jedis_Set.scard(DBPOWER+cont_rediskey)>0){
						while(updateiterator.hasNext()){
							JSONObject model =JSONObject.fromObject(updateiterator.next());
							PowerPerfVO m =  (PowerPerfVO) JSONObject.toBean(model, PowerPerfVO.class);
							if(!diffpower.contains(m.getCodeType()+m.getResourceCode())){
								m.setUpdate_user(taskInfo.getOperateUser());
								m.setUpdate_time(new Date());
								m.setUpdate_ip(taskInfo.getOperateUserIp());
								update.add(m);
							}
						}
					}
					dealRegInfo(insert, PowerPerfVO.class, cont_rediskey);
					long enddifftime=System.currentTimeMillis();
					LOGGER.info("-----------------------生成集合完成:耗时"+(enddifftime-startdifftime)/60000+"min \r\n");
				} catch (Exception e1) {
					e1.printStackTrace();
				}finally{
					jedis_KeysSETS.batchDel(cont_rediskey);
				}
				
				try{
						String info1= dbUtil.branch(16, insert);
						loginfoBF.append("\r\n动环新增新增入库："+info1);
						String info2= dbUtil.branch(17, update);
						loginfoBF.append(";动环更新入库："+info2);
					}
					catch(Exception e){
						loginfoBF.append("\r\n"+e.toString());
					}
				long end=System.currentTimeMillis();
				
				LOGGER.info("总耗时:"+(end-start)/60000+"新增动环数据数据成功：智能电表开关数据"+insert.size() + "条；更新动环数据数据成功：智能电表开关数据"+update.size()+"条.\r\n");
				return loginfoBF;
	}

	@Override
	public boolean updatePower(String typeName, List<String> columns, Map<String, String> map,
			SysProvinceVO sysProvinceVO,TaskInfoVO taskInfo,String delimiter) {
		List<PowerPerfVO> datas = new ArrayList<PowerPerfVO>();
		List<PowerPerfVO> cuidDatas = new ArrayList<PowerPerfVO>();
		List<PowerPerfVO> codeDatas = new ArrayList<PowerPerfVO>();
		List<String>cuids=new ArrayList<String>();  
		List<String>codes=new ArrayList<String>();
		for (String key : map.keySet()) {
			PowerPerfVO powerPerfVO = dealPowerPerfVO(typeName, columns, map.get(key),sysProvinceVO,delimiter);
			if(1==powerPerfVO.getCodeType()){
				cuidDatas.add(powerPerfVO);
				cuids.add(powerPerfVO.getResourceCode()+"");
			}
			else if(2==powerPerfVO.getCodeType()){
				codeDatas.add(powerPerfVO);
				codes.add(powerPerfVO.getResourceCode()+"");
			}
			powerPerfVO.setUpdate_user(taskInfo.getOperateUser());
			powerPerfVO.setUpdate_time(new Date());
			powerPerfVO.setUpdate_ip(taskInfo.getOperateUserIp());
		}
		dealPowerRegInfo(cuidDatas,codeDatas,cuids,codes,sysProvinceVO);
		datas.addAll(cuidDatas);
		datas.addAll(codeDatas);
		
		boolean bBatchUpdate = false;
		for(int i=0, length = datas.size(); i<length; i+=BatchControlComm.iBatchCount){
			int j=(i+BatchControlComm.iBatchCount>datas.size())?datas.size():i+BatchControlComm.iBatchCount;
			List<PowerPerfVO> tmp = datas.subList(i, j);
			bBatchUpdate = powerPerfVOMapper.batchUpdate(tmp);
		}
		return bBatchUpdate;
	}

	@Override
	public boolean delPower(String pkName, String typeName, List<String> columns, Map<String, String> map,
			SysProvinceVO sysProvinceVO) {
		List<String> list=ValueUtil.dealCuids(columns,map);
		String prvId=sysProvinceVO.getPrvId();

		HashMap<String,Object> pMap=new HashMap<String,Object>();
		pMap.put("prvId", prvId);
		pMap.put("list", list);
		return powerPerfVOMapper.delByCuidsAndPrvid(pMap);
	}

	@Override
	public String[] getDBparms(String perfix) {

		String[] result=new String[3];
		String pkName="";
		String tableName="";
		if(perfix.equalsIgnoreCase(DataType.RING_BTSROOM_METER)){
			pkName="ne_code";
			tableName=TableType.RING_METER;
		}
		else if(perfix.equalsIgnoreCase(DataType.RING_BTSROOM_POWER)){
			pkName="ne_code";
			tableName=TableType.RING_POWER;
		}
        else if(perfix.equalsIgnoreCase(DataType.RING_MACHROOM_METER)){
        	pkName="ne_code";
        	tableName=TableType.RING_METER;
		}
        else if(perfix.equalsIgnoreCase(DataType.RING_MACHROOM_POWER)){
        	pkName="ne_code";
        	tableName=TableType.RING_POWER;
        }
		result[0]=pkName;
		result[1]=perfix;
		result[2]=tableName;
		return result;
	}

	
	@Override
	public PageInfo<MeterPerfVO> findAllMeterPerf(String userId,String prvid,String pregid,String regid,String resourcename,String resourcetype,String date,int pageNumber, int pageSize) {
		//PageInterceptor.startPage(pageNumber, pageSize);
		MeterPerfVO meterPerfVO=new MeterPerfVO();
		
		if(StringUtils.isNoneBlank(prvid)){
			meterPerfVO.setPrvId(prvid);
		}
		if(StringUtils.isNoneBlank(pregid)){
			meterPerfVO.setPregId(pregid);
		}
		if(StringUtils.isNoneBlank(regid)){
			meterPerfVO.setRegId(regid);
		}
		if("10006".equalsIgnoreCase(resourcetype)||"10005".equalsIgnoreCase(resourcetype)){
			meterPerfVO.setResourceType(Integer.parseInt(resourcetype));
		}
		if(StringUtils.isNoneBlank(resourcename)){
			meterPerfVO.setResourceName("%"+resourcename+"%");
			//meterPerfVO.setResourceCode("%"+resourcename+"%");
		}
		if(StringUtils.isNoneBlank(date)){
			meterPerfVO.setQueryDate(date+"%");
		}
		if(StringUtils.isNoneBlank(userId)){
			meterPerfVO.setUserId(userId);
		}
		PageHelper.startPage(pageNumber, pageSize);
		List<MeterPerfVO> datas=meterPerfVOMapper.selectByCondition(meterPerfVO);
		PageInfo<MeterPerfVO> pageinfo = new PageInfo<MeterPerfVO>(datas);
		return pageinfo;
	}

	@Override
	public PageInfo<PowerPerfVO> findAllPowerPerf(String userId,String prvid,String pregid,String regid,String resourcename,String resourcetype,String date,int pageNumber, int pageSize) {
		//PageInterceptor.startPage(pageNumber, pageSize);
		PowerPerfVO powerPerfVO=new PowerPerfVO();
		
		if(StringUtils.isNoneBlank(prvid)){
			powerPerfVO.setPrvId(prvid);
		}
		if(StringUtils.isNoneBlank(pregid)){
			powerPerfVO.setPregId(pregid);
		}
		if(StringUtils.isNoneBlank(regid)){
			powerPerfVO.setRegId(regid);
		}
		if("10006".equalsIgnoreCase(resourcetype)||"10005".equalsIgnoreCase(resourcetype)){
			powerPerfVO.setResourceType(Integer.parseInt(resourcetype));
		}
		if(StringUtils.isNoneBlank(resourcename)){
			powerPerfVO.setResourceName("%"+resourcename+"%");
			//powerPerfVO.setResourceCode("%"+resourcename+"%");
		}
		if(StringUtils.isNoneBlank(date)){
			powerPerfVO.setQueryDate(date+"%");
		}
		
		if(StringUtils.isNoneBlank(userId)){
			powerPerfVO.setUserId(userId);
		}
		PageHelper.startPage(pageNumber, pageSize);
		List<PowerPerfVO> datas=powerPerfVOMapper.selectByCondition(powerPerfVO);
		PageInfo<PowerPerfVO> pageinfo = new PageInfo<PowerPerfVO>(datas);
		return pageinfo;
	}
	
	private MeterPerfVO dealMeterPerfVO(String typeName, List<String> columns, String values,SysProvinceVO sysProvinceVO,String delimiter) {

		MeterPerfVO meterPerfVO = new MeterPerfVO();
		int columnSize=columns.size();
		String[] valueArray=ValueUtil.dealValues(columnSize,values,delimiter);
		MeterPerfUtil.dealMeterColumns(meterPerfVO, columns, valueArray,sysProvinceVO);
		return meterPerfVO;
	}
	
	private PowerPerfVO dealPowerPerfVO(String typeName, List<String> columns, String values,SysProvinceVO sysProvinceVO,String delimiter) {

		PowerPerfVO powerPerfVO = new PowerPerfVO();
		int columnSize=columns.size();
		String[] valueArray=ValueUtil.dealValues(columnSize,values,delimiter);
		PowerPerfUtil.dealPowerColumns(powerPerfVO, columns, valueArray,sysProvinceVO);
		return powerPerfVO;
	}
	
	private void dealMeterRegInfo(List<MeterPerfVO> cuidDatas,List<MeterPerfVO> codeDatas, List<String> cuids, List<String> codes,SysProvinceVO sysProvinceVO) {
		
		if (cuids != null && cuids.size() > 0) {
			DatBaseresourceVOExample example = new DatBaseresourceVOExample();
			Criteria criteria = example.createCriteria();
			criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
			criteria.andBaseresourceCuidIn(cuids);
			List<DatBaseresourceVO> resources = datBaseresourceVOMapper.selectByExample(example);
			for(MeterPerfVO meterPerfVO:cuidDatas){
				String resourceCode=meterPerfVO.getResourceCode();
				for(DatBaseresourceVO datBaseresourceVO:resources){
					if(datBaseresourceVO.getBaseresourceCuid().equalsIgnoreCase(resourceCode)){
						meterPerfVO.setPregId(datBaseresourceVO.getPregId());
						meterPerfVO.setPregName(datBaseresourceVO.getPregName());
						meterPerfVO.setRegId(datBaseresourceVO.getRegId());
						meterPerfVO.setRegName(datBaseresourceVO.getRegName());
						break;
					}
				}
			}
		}
		if (codes != null && codes.size() > 0) {
			DatBaseresourceVOExample example = new DatBaseresourceVOExample();
			Criteria criteria = example.createCriteria();
			criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
			criteria.andBaseresourceCodeIn(codes);
			List<DatBaseresourceVO> resources = datBaseresourceVOMapper.selectByExample(example);
			for(MeterPerfVO meterPerfVO:codeDatas){
				String resourceCode=meterPerfVO.getResourceCode();
				for(DatBaseresourceVO datBaseresourceVO:resources){
					if(datBaseresourceVO.getBaseresourceCode().equalsIgnoreCase(resourceCode)){
						meterPerfVO.setPregId(datBaseresourceVO.getPregId());
						meterPerfVO.setPregName(datBaseresourceVO.getPregName());
						meterPerfVO.setRegId(datBaseresourceVO.getRegId());
						meterPerfVO.setRegName(datBaseresourceVO.getRegName());
						break;
					}
				}
			}
		}
	}
	
	private void dealPowerRegInfo(List<PowerPerfVO> cuidDatas,List<PowerPerfVO> codeDatas,List<String>cuids,List<String>codes,SysProvinceVO sysProvinceVO){
		
		if (cuids != null && cuids.size() > 0) {
			DatBaseresourceVOExample example = new DatBaseresourceVOExample();
			Criteria criteria = example.createCriteria();
			criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
			criteria.andBaseresourceCuidIn(cuids);
			List<DatBaseresourceVO> resources = datBaseresourceVOMapper.selectByExample(example);
			for(PowerPerfVO powerPerfVO:cuidDatas){
				String resourceCode=powerPerfVO.getResourceCode();
				for(DatBaseresourceVO datBaseresourceVO:resources){
					if(datBaseresourceVO.getBaseresourceCuid().equalsIgnoreCase(resourceCode)){
						powerPerfVO.setPregId(datBaseresourceVO.getPregId());
						powerPerfVO.setPregName(datBaseresourceVO.getPregName());
						powerPerfVO.setRegId(datBaseresourceVO.getRegId());
						powerPerfVO.setRegName(datBaseresourceVO.getRegName());
						break;
					}
				}
			}
		}
		if (codes != null && codes.size() > 0) {
			DatBaseresourceVOExample example = new DatBaseresourceVOExample();
			Criteria criteria = example.createCriteria();
			criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
			criteria.andBaseresourceCodeIn(codes);
			List<DatBaseresourceVO> resources = datBaseresourceVOMapper.selectByExample(example);
			for(PowerPerfVO powerPerfVO:codeDatas){
				String resourceCode=powerPerfVO.getResourceCode();
				for(DatBaseresourceVO datBaseresourceVO:resources){
					if(datBaseresourceVO.getBaseresourceCode().equalsIgnoreCase(resourceCode)){
						powerPerfVO.setPregId(datBaseresourceVO.getPregId());
						powerPerfVO.setPregName(datBaseresourceVO.getPregName());
						powerPerfVO.setRegId(datBaseresourceVO.getRegId());
						powerPerfVO.setRegName(datBaseresourceVO.getRegName());
						break;
					}
				}
			}
		}
	}

	@Override
	public String exportAllMeterPerf(String userId,String prvid,String path,String pregid, String regid, String resourcename, String resourcetype,String date) {
		
		String result="1";
		//String localPath=RestServerUtils.getTomcatRootPath()+File.separator+"meter";
		long currenttime = System.currentTimeMillis();
		String fileName = currenttime + ".xls";
		
		HSSFWorkbook wb=null;
		HSSFSheet sheet=null;
		Object[] sheetObj=ExcelExportHelper.createMeterExcelSheet();
		if(sheetObj!=null&&sheetObj.length==2){
			wb=(HSSFWorkbook)sheetObj[0];
			sheet=(HSSFSheet)sheetObj[1];
		}
		if(wb!=null&&sheet!=null){
			PageInfo<MeterPerfVO> firstPage=findAllMeterPerf(userId,prvid,pregid,regid,resourcename,resourcetype,date,1,pageSize);
			int pages=firstPage.getPages();
			//int firstPageSize=firstPage.getPageSize();
			for(int i=0;i<pages;i++){
				if(0==i){
					ExcelExportHelper.setMeterExcelProperty(sheet, firstPage.getList(), i);
				}
				else{
					PageInfo<MeterPerfVO> page=findAllMeterPerf(userId,prvid,pregid,regid,resourcename,resourcetype,date,++i,pageSize);
					List<MeterPerfVO> meterList=page.getList();
					ExcelExportHelper.setMeterExcelProperty(sheet, meterList, i*pageSize);
				}
			}
			boolean exportResult=ExcelExportHelper.exportFile(wb, path, fileName);
			if(exportResult){
				result=fileName;
			}
		}
		
		return result;
	}

	@Override
	public String exportAllPowerPerf(String userId,String prvid,String path,String pregid, String regid, String resourcename, String resourcetype,String date) {
		
		String result="1";
		//String localPath=RestServerUtils.getTomcatRootPath()+File.separator+"meter";
		long currenttime = System.currentTimeMillis();
		String fileName = currenttime + ".xls";
		
		HSSFWorkbook wb=null;
		HSSFSheet sheet=null;
		Object[] sheetObj=ExcelExportHelper.createPowerExcelSheet();
		if(sheetObj!=null&&sheetObj.length==2){
			wb=(HSSFWorkbook)sheetObj[0];
			sheet=(HSSFSheet)sheetObj[1];
		}
		if(wb!=null&&sheet!=null){
			PageInfo<PowerPerfVO> firstPage=findAllPowerPerf(userId,prvid,pregid,regid,resourcename,resourcetype,date,1,pageSize);
			int pages=firstPage.getPages();
			//int firstPageSize=firstPage.getPageSize();
			for(int i=0;i<pages;i++){
				if(0==i){
					ExcelExportHelper.setPowerExcelProperty(sheet, firstPage.getList(), i);
				}
				else{
					PageInfo<PowerPerfVO> page=findAllPowerPerf(userId,prvid,pregid,regid,resourcename,resourcetype,date,++i,pageSize);
					List<PowerPerfVO> meterList=page.getList();
					ExcelExportHelper.setPowerExcelProperty(sheet, meterList, i*pageSize);
				}
			}
			boolean exportResult=ExcelExportHelper.exportFile(wb, path, fileName);
			if(exportResult){
				result=fileName;
			}
		}
		
		return result;
	}
	
	/**
	 * 查询某个省的智能电表数据分装map
	 * key：省ID+code哈希码
	 * vlaue：封装javabean
	 * @param sysProvinceVO
	 * @return
	 */
	private Map<Integer,MeterPerfVO> getMeterMap(SysProvinceVO sysProvinceVO){
		Map<Integer,MeterPerfVO> map = new HashMap<Integer,MeterPerfVO>();
		MeterPerfVOExample example = new MeterPerfVOExample();
		MeterPerfVOExample.Criteria criteria = example.createCriteria();
		criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
		List<MeterPerfVO> metterList = meterPerfVOMapper.selectByExample(example);
		for(MeterPerfVO vo : metterList){
			Integer key = (vo.getPrvId() + "#" + vo.getResourceCode()).hashCode();
			map.put(key, vo);
		}
		return map;
	}
	
	/**
	 * 查询某个省的开关电源数据分装map
	 * key：省ID+code哈希码
	 * vlaue：封装javabean
	 * @param sysProvinceVO
	 * @return
	 */
	private Map<Integer,PowerPerfVO> getPowerMap(SysProvinceVO sysProvinceVO){
		Map<Integer,PowerPerfVO> map = new HashMap<Integer,PowerPerfVO>();
		PowerPerfVOExample example = new PowerPerfVOExample();
		PowerPerfVOExample.Criteria criteria = example.createCriteria();
		criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
		List<PowerPerfVO> powerList = powerPerfVOMapper.selectByExample(example);
		for(PowerPerfVO vo : powerList){
			Integer key = (vo.getPrvId() + "#" + vo.getResourceCode()).hashCode();
			map.put(key, vo);
		}
		return map;
	}

	@Override
	public StringBuffer insertIntoDB(String typeName,
			List<String> columns, Map<String, String> map,
			SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo, String delimiter,
			String tablename) throws Exception {
		StringBuffer result = null;
		switch(tablename.toLowerCase()){
		
		case TableType.RING_METER:
		
			result=InsertMeter(typeName,columns,map,sysProvinceVO,taskInfo,BatchControlComm.ringDelimiterStr);
			
			break;
		case TableType.RING_POWER:
			
			result=InsertPower(typeName,columns,map,sysProvinceVO,taskInfo,BatchControlComm.ringDelimiterStr);
			break;
		}
		return result;
	}

	@Override
	public int deleteFromDB(String pkName, String typeName,
			List<String> columns, Map<String, String> map,
			SysProvinceVO sysProvinceVO, String delimiter, String tablename)
			throws Exception {
		switch(tablename.toLowerCase()){
		
		case TableType.RING_METER:
		
			delMeter(pkName,typeName,columns,map,sysProvinceVO);
			
			break;
		case TableType.RING_POWER:
			
			delPower(pkName,typeName,columns,map,sysProvinceVO);
			break;
		}
		
		return 0;
	}
	
	private void initredis(){
		if(jedisUtil==null){
			jedisUtil= JedisUtil.getInstance();
		}
		if(jedis_KeysSETS==null) {
			jedis_KeysSETS = jedisUtil.new Keys();
		}
		jedis_Hash = jedisUtil.new Hash();
		Jedis_Set = jedisUtil.new Sets();
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

	private void dealRegInfo(List<?> list,Class clazz,String cont_rediskey) throws Exception{
		for(Object obj :list){
			Method gtype = clazz.getMethod("getCodeType");
			Method gcode = clazz.getMethod("getResourceCode");
			Method setPregId = clazz.getMethod("setPregId",String.class);
			Method setPregName = clazz.getMethod("setPregName",String.class);
			Method setRegId = clazz.getMethod("setRegId",String.class);
			Method setRegName = clazz.getMethod("setRegName",String.class);
			String codetype = gtype.invoke(clazz.cast(obj))+"";
			String code = (String)gcode.invoke(clazz.cast(obj));
			DatBaseresourceVO dbv = null;
			JSONObject jsonobj = null;
			if(codetype.equals("1")){
				jsonobj = JSONObject.fromObject(jedis_Hash.hmget(HASHRESCUID+cont_rediskey, code).get(0));
			}else{
				jsonobj = JSONObject.fromObject(jedis_Hash.hmget(HASHRESCODE+cont_rediskey, code).get(0));
			}
				dbv = (DatBaseresourceVO)jsonobj.toBean(jsonobj, DatBaseresourceVO.class);
				setPregId.invoke(clazz.cast(obj), dbv.getPregId());
				setPregName.invoke(clazz.cast(obj), dbv.getPregName());
				setRegId.invoke(clazz.cast(obj), dbv.getRegId());
				setRegName.invoke(clazz.cast(obj), dbv.getRegName());
		}
	}
	

}
