package com.xunge.service.basedata.contract.impl;

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
import com.xunge.comm.collection.ContractComm;
import com.xunge.comm.enums.ResourceEnum.AuditStateEnum;
import com.xunge.dao.basedata.DatContractVOMapper;
import com.xunge.dao.basedata.DatPaymentperiodMapper;
import com.xunge.dao.basedata.DatSupplierVOMapper;
import com.xunge.dao.job.EleContractCollectionVOMapper;
import com.xunge.dao.job.RentContractCollectionVOMapper;
import com.xunge.dao.region.ISysRegionDao;
import com.xunge.model.basedata.DatContractVO;
import com.xunge.model.basedata.DatSupplierVO;
import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.model.selfelec.EleContract;
import com.xunge.model.selfrent.RentContractVO;
import com.xunge.service.basedata.IBaseDataService;
import com.xunge.service.job.util.ContractUtil;
import com.xunge.util.DataType;
import com.xunge.util.StrUtil;
import com.xunge.util.SysUUID;
import com.xunge.util.TableType;
import com.xunge.util.ValueUtil;
import com.xunge.util.branch.DBBranchUtil;
import com.xunge.util.redis.JedisUtil;


@Service
public class ContractService implements IBaseDataService{

	private static final Logger LOGGER = Logger.getLogger(ContractService.class);

	@Resource
	private DatSupplierVOMapper datSupplierVOMapper;

	@Resource
	private DatContractVOMapper datContractVOMapper;

	@Resource
	private EleContractCollectionVOMapper elecContractMapper;
	
	@Resource
	private RentContractCollectionVOMapper rentContractMapper;

	@Resource
	private ISysRegionDao sysRegionDao;
	
	@Resource
    private DatPaymentperiodMapper datPaymentperiodMapper;

	@Resource
    private DBBranchUtil dbUtil;
	
	//-------------------------------------------------------------------------------------------------
	/**用于做文件比对的前缀**/private String FILE_COM_Prex = "FILE_COM_";
	/**用于存放数据库[流水号key->数据实体]**/private String DB_SYSID_ENTRY_Prex = "DB_SYSID_ENTRY_";
	/**用于存放文件中[流水号key->数据实体]**/private String FILE_SYSID_ENTRY_Prex = "FILE_SYSID_ENTRY_";
	/**用于存放数据库中流水号集合**/private String DB_SYSID_Prex = "DB_SYSID_";
	/**用于存放文件中流水号集合**/private String FILE_SYSID_Prex = "FILE_SYSID_";
	/**用于存放数据库中主合同实体集合**/private String DB_CONT_Prex = "DB_CONT_";
	/**用于存放数据库中电费合同实体集合**/private String DB_ELEC_Prex = "DB_ELEC_";
	/**用于存放数据库中租费合同实体集合**/private String DB_RENT_Prex = "DB_RENT_";
	/**文件解析成的电费合同集合**/private String FILE_ELEC_Prex = "FILE_ELEC_";
	/**文件解析成的租费合同集合**/private String FILE_RENT_Prex = "FILE_RENT_";
	/**存放文件产生uuid的主合同id**/private String FILE_CONT_IDLINK_Prex = "FILE_CONT_IDLINK_";
	/**存放主合同id-[电费合同id，租费合同id]**/private String FILE_ELEC_RENT_IDLINK_Prex = "FILE_ELEC_RENT_IDLINK_";
	//利用redis做集合处理
	private JedisUtil jedisUtil = null;
	private volatile JedisUtil.Sets jedis_dball_datcontract=null;//数据库查询到的省内所有主合同
	private volatile JedisUtil.Hash jedis_dball_datcontract_sysid=null;//数据库查询到的省内所有主合同，（标识-[流水号key->数据库实体]）
	private volatile JedisUtil.Hash jedis_files_datcont=null;//文件内主合同
	private volatile JedisUtil.Hash jedis_files_rentcont=null;//文件内房租合同
	private volatile JedisUtil.Hash jedis_files_eleccont=null;//文件内电费合同
	private volatile JedisUtil.Hash jedis_idlinks_list=null;//合同编码关联集合（主合同编码-[电费合同编码，租费合同编码]）
	private volatile JedisUtil.Hash jedis_datIdlinks_list=null;//合同编码关联集合（主合同流水号-[主合同编码]）

	private volatile Map<String,String> dball_subppliers=null;//数据库查询到的省内所有供应商
	private volatile JedisUtil.Keys jedis_KeysSETS=null;
	//-------------------------------------------------------------------------------------------------
	
	
	@Override
	public StringBuffer insertIntoDB(String typeName,List<String> columns,Map<String, String> map,
			SysProvinceVO sysProvinceVO,TaskInfoVO taskInfo,String delimiter,String tableName)
			throws Exception{
		StringBuffer loginfoBF = new StringBuffer(); 
		
		List<DatContractVO> u_datContractList = new ArrayList<DatContractVO>();
		List<EleContract> u_eleContractList = new ArrayList<EleContract>();
		List<RentContractVO> u_rentContractList = new ArrayList<RentContractVO>();
		List<DatContractVO> i_datContractList = new ArrayList<DatContractVO>();
		List<EleContract> i_eleContractList = new ArrayList<EleContract>();
		List<RentContractVO> i_rentContractList = new ArrayList<RentContractVO>();
		List<DatSupplierVO> u_datSupplierVOList = new ArrayList<DatSupplierVO>();
		List<DatSupplierVO> i_datSupplierVOList = new ArrayList<DatSupplierVO>();

		long startTime=System.currentTimeMillis();   //获取开始时间
		// 行解析
		parseRowDataInsert(columns, map, 
				u_datContractList, u_eleContractList, u_rentContractList, 
				i_datContractList, i_eleContractList, i_rentContractList, 
				u_datSupplierVOList, i_datSupplierVOList, 
				sysProvinceVO, taskInfo, delimiter);
		long endTime=System.currentTimeMillis(); //获取结束时间
		loginfoBF.append("Contract 文件共"+map.keySet().size()+"行；"
				+ "解析为数据库字段时间： "+(endTime-startTime)/60000+"min "+(endTime-startTime)%60000+"ms");
		LOGGER.debug(loginfoBF.toString());
		
		try{
			String info1= dbUtil.branch(0, i_datSupplierVOList);
			loginfoBF.append("\r\n供应商新增："+info1);
			String info2= dbUtil.branch(1, u_datSupplierVOList);
			loginfoBF.append("；供应商更新："+info2);
			String info3= dbUtil.branch(2, i_datContractList);
			loginfoBF.append("；主合同新增："+info3);
			String info4= dbUtil.branch(3, u_datContractList);
			loginfoBF.append("；主合同更新："+info4);
			String info5= dbUtil.branch(4, i_eleContractList);
			loginfoBF.append("；电费合同新增："+info5);
			String info6= dbUtil.branch(5, u_eleContractList);
			loginfoBF.append("；电费合同更新："+info6);
			String info7= dbUtil.branch(6, i_rentContractList);
			loginfoBF.append("；租费合同新增："+info7);
			String info8= dbUtil.branch(7, u_rentContractList);
			loginfoBF.append("；租费合同商更新："+info8);
		}
		catch(Exception e){
			loginfoBF.append("\r\n"+e.toString());
		}

		loginfoBF.append("\r\n--------Contract 新增合同完成：供应商"+i_datSupplierVOList.size()+"条；主合同"+i_datContractList.size()+"条；电费合同"+i_eleContractList.size()+"条；房租合同"+i_rentContractList.size()+"条；"+
				"更新合同完成：供应商"+u_datSupplierVOList.size()+"条；主合同"+u_datContractList.size()+"条；电费合同"+u_eleContractList.size()+"条；房租合同"+u_rentContractList.size()+"条");
		LOGGER.debug(loginfoBF.toString());
		
		endTime=System.currentTimeMillis(); //获取结束时间
		loginfoBF.append("\r\n--------Contract 批量处理时间： "+(endTime-startTime)/60000+"min "+(endTime-startTime)%60000+"ms");
		
		return loginfoBF;
	}

	private void parseRowDataInsert(List<String> columns,Map<String, String> map,
			List<DatContractVO> u_datContractList, List<EleContract> u_eleContractList, List<RentContractVO> u_rentContractList,
			List<DatContractVO> i_datContractList, List<EleContract> i_eleContractList, List<RentContractVO> i_rentContractList,
			List<DatSupplierVO> u_datSupplierVOList,List<DatSupplierVO> i_datSupplierVOList,SysProvinceVO sysProvinceVO,
			TaskInfoVO taskInfo, String delimiter){
		String prvId = sysProvinceVO.getPrvId();
		String cont_rediskey = "CONTRACT_"+System.currentTimeMillis();
		
		try{
			//手动缓存机制
			JSONObject ProvinceCache=new JSONObject(), RegionCache=getRegidlistByPrvid(prvId), PaymentCache=getPaymentidlist(prvId);
			//获取比对的redis集合
			getRedisCompareSets(sysProvinceVO.getPrvId(), cont_rediskey);

			LOGGER.debug("--------------------------parseRowDataInsert 开始解析文件为数据集合");
			long startTime=System.currentTimeMillis();   //获取开始时间
			List<String> dbbuffer1=new ArrayList<>(),dbbuffer2=new ArrayList<>();
			Map<String, String> datcMap = new HashMap<>();
			Map<String, String> elecMap = new HashMap<>();
			Map<String, String> rentMap = new HashMap<>();
			for (String rowId : map.keySet()) {

				String contractId = SysUUID.generator();
				DatContractVO datContract = new DatContractVO();
				datContract.setContractId(contractId);
				datContract.setDataFrom(2);//数据来源（0：系统录入  1:系统导入   2：接口采集）
				
				DatSupplierVO datSupplierVO = new DatSupplierVO();
				
				String elecontractId = SysUUID.generator();
				EleContract eleContract = new EleContract();
				eleContract.setElecontractId(elecontractId);
				eleContract.setContractId(contractId);
				
				String rentcontractId = SysUUID.generator();
				RentContractVO rentContract = new RentContractVO();
				rentContract.setRentcontractId(rentcontractId);
				rentContract.setContractId(contractId);
				

				//取列值转化db字段拼接为合同实体
				int columnSize=columns.size();
				String[] columnData = ValueUtil.dealValues(columnSize,map.get(rowId),delimiter);
				//手动添加省份缓存
				if(ProvinceCache.size()<=0 && columnData[4]!=null){
					String prvcode = columnData[4];
					String caPrvId = sysRegionDao.getPrvIdByCode(prvcode);
					ProvinceCache.put(prvcode, caPrvId);
				}
				ContractUtil.convertToContractDB(columns, columnData, 
						datContract, rentContract, eleContract, datSupplierVO, 
						prvId, ProvinceCache, RegionCache, PaymentCache);

				dealDatSupplierVO(datContract.getContractType()+"", datSupplierVO, datContract, eleContract, rentContract, 
						u_datSupplierVOList, i_datSupplierVOList, taskInfo, cont_rediskey);
				
				//用于文件比对的文件中合同集合
				DatContractVO datContractCom = (DatContractVO) datContract.clone();//深度克隆
				datContractCom.setContractId(null);
				dbbuffer1.add(JSONObject.fromObject(datContractCom).toString());
				dbbuffer2.add(datContract.getContractsysId());

				datcMap.put(datContract.getContractsysId(), JSONObject.fromObject(datContractCom).toString());
				elecMap.put(elecontractId, JSONObject.fromObject(eleContract).toString());
				rentMap.put(rentcontractId, JSONObject.fromObject(rentContract).toString());
				//存储redis合同关联集合:jedis_idlinks_list
				setIdLinkSets(sysProvinceVO.getPrvId(), contractId, elecontractId, rentcontractId, cont_rediskey);
				//存储redis合同关联集合:jedis_datIdlinks_list
				setDatIdLinkSets(datContract, cont_rediskey);
			}
			jedis_dball_datcontract.sadd(FILE_COM_Prex+cont_rediskey, dbbuffer1);
			jedis_dball_datcontract.sadd(FILE_SYSID_Prex+cont_rediskey, dbbuffer2);
			jedis_files_datcont.hmset(FILE_SYSID_ENTRY_Prex+cont_rediskey, datcMap);
			jedis_files_eleccont.hmset(FILE_ELEC_Prex+cont_rediskey, elecMap);
			jedis_files_rentcont.hmset(FILE_RENT_Prex+cont_rediskey, rentMap);

			long endTime=System.currentTimeMillis(); //获取结束时间
			LOGGER.debug("-------------------------- 解析文件为数据集合耗时："+(endTime-startTime)/60000+"min "+(endTime-startTime)%60000+"ms；开始集合操作");
			insertContractByRedis(cont_rediskey, taskInfo, i_datContractList, i_eleContractList, i_rentContractList);
			updateContractByRedis(cont_rediskey, taskInfo, u_datContractList, u_eleContractList, u_rentContractList);
//			Set<String> deleteSets = jedis_dball_datcontract.sdiff(DB_CONT_Prex+cont_rediskey, "FILE_"+cont_rediskey);// 差集求delete
		}
		finally{
			jedis_KeysSETS.batchDel(cont_rediskey);
			LOGGER.debug("i.s:"+i_datSupplierVOList.size()+" u.s:"+u_datSupplierVOList.size());
			LOGGER.debug("i.c:"+i_datContractList.size()+" u.c:"+u_datContractList.size());
			LOGGER.debug("i.e:"+i_eleContractList.size()+" u.e:"+u_eleContractList.size());
			LOGGER.debug("i.r:"+i_rentContractList.size()+" u.r:"+u_rentContractList.size());
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
	
	private JSONObject getPaymentidlist(String prvId){
		List<Map<String, String>> list= datPaymentperiodMapper.getPaymentidlist();
		JSONObject jsonO = new JSONObject();
		for(Map<String,String> map :list){
			jsonO.put(map.get("paymentperiod_value"), map.get("paymentperiod_id"));
		}
		return jsonO;
	}
	
	/**
	 * 根据解析后内容获取insert集合
	 * @param cont_rediskey
	 * @param taskInfo
	 */
	private void insertContractByRedis(String cont_rediskey, TaskInfoVO taskInfo, 
			List<DatContractVO> i_datContractList,
			List<EleContract> i_eleContractList,
			List<RentContractVO> i_rentContractList){
		LOGGER.debug("--------------------------insertContractByRedis 开始生成批量集合");

		jedis_dball_datcontract.sdiffstore("FILE_SDIFF_"+cont_rediskey, FILE_SYSID_Prex+cont_rediskey, DB_SYSID_Prex+cont_rediskey);
		Set<String> insertSets = jedis_dball_datcontract.smembers("FILE_SDIFF_"+cont_rediskey);
		//逐条
		Iterator<String> it = insertSets.iterator();
		while (it.hasNext()) {
			String sysId = it.next();
			//反赋值原对象id
			List<String> entsets = jedis_files_datcont.hmget(FILE_SYSID_ENTRY_Prex+cont_rediskey, sysId);
			List<String> idsets = jedis_datIdlinks_list.hmget(FILE_CONT_IDLINK_Prex+cont_rediskey,sysId+"_IDLINK");
			if(idsets==null || entsets==null) LOGGER.error("未找到主合同id关联："+sysId+","+cont_rediskey);
			else{
				Iterator<String> entit = entsets.iterator();
				String next = entit.next();
				JSONObject jsonObject=JSONObject.fromObject(next);
				DatContractVO datContract = (DatContractVO) JSONObject.toBean(jsonObject, DatContractVO.class);
				//id集合
				Iterator<String> idit = idsets.iterator();
				datContract.setContractId(idit.next());
				//合同状态未提交
				datContract.setAuditingState(AuditStateEnum.PREAUDIT);
				//生成主合同
				datContract.setCreate_time(new Date());
				datContract.setCreate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
				datContract.setCreate_ip(StrUtil.isBlank(taskInfo.getOperateUserIp())?"admin":taskInfo.getOperateUserIp());
				i_datContractList.add(datContract);

				int contType = datContract.getContractType();
				String contId = datContract.getContractId();
				//根据主合同生成房租、电费合同
				genBranchContractList(cont_rediskey, contType, contId, i_eleContractList, i_rentContractList);
			}
		}
	}
	/**
	 * 根据解析后内容获取update集合
	 * @param cont_rediskey
	 * @param taskInfo
	 */
	private void updateContractByRedis(String cont_rediskey, TaskInfoVO taskInfo, 
			List<DatContractVO> u_datContractList,
			List<EleContract> u_eleContractList,
			List<RentContractVO> u_rentContractList){
		LOGGER.debug("--------------------------updateContractByRedis 开始生成批量集合");

		//全量insert
		if(jedis_dball_datcontract.scard(DB_CONT_Prex+cont_rediskey)<=0){
			return;
		}

		jedis_dball_datcontract.sdiffstore("DB_SDIFF_"+cont_rediskey, FILE_COM_Prex+cont_rediskey, DB_CONT_Prex+cont_rediskey);
		Set<String> updateSets = jedis_dball_datcontract.smembers("DB_SDIFF_"+cont_rediskey);
		//逐条
		Iterator<String> it = updateSets.iterator();
		while (it.hasNext()) {
			String next = it.next();
			JSONObject jsonObject=JSONObject.fromObject(next);
			DatContractVO datContract = (DatContractVO) JSONObject.toBean(jsonObject, DatContractVO.class);

			if(jedis_dball_datcontract.sismember("FILE_SDIFF_"+cont_rediskey, datContract.getContractsysId())){
				continue;
			}
			//审核中不更新
			DatContractVO cont = getContractBySysidRedis(datContract.getContractsysId(), cont_rediskey);
			if(cont==null){
				continue;
			}
			datContract.setAuditingState(cont.getAuditingState());
			//生成主合同
			datContract.setUpdate_time(new Date());
			datContract.setUpdate_user(StrUtil.isBlank(taskInfo.getOperateUser())?"admin":taskInfo.getOperateUser());
			datContract.setUpdate_ip(StrUtil.isBlank(taskInfo.getOperateUserIp())?"admin":taskInfo.getOperateUserIp());
			u_datContractList.add(datContract);

			int contType = datContract.getContractType();
			String contId = datContract.getContractId();
			//根据主合同生成房租、电费合同
			genBranchContractList(cont_rediskey, contType, contId, u_eleContractList, u_rentContractList);
		}
	}
	/**
	 * 根据主合同生成房租、电费合同
	 * @param contType
	 * @param pkey
	 * @param i_eleContractList
	 * @param u_eleContractList
	 * @param i_rentContractList
	 * @param u_rentContractList
	 */
	private void genBranchContractList(String cont_rediskey, int contType, String pkey, 
			List<EleContract> eleContractList, List<RentContractVO> rentContractList){
		List<String> idlinkSets = jedis_idlinks_list.hmget(FILE_ELEC_RENT_IDLINK_Prex+cont_rediskey, pkey);
		if(idlinkSets==null){
			return;
		}
		Iterator<String> it = idlinkSets.iterator();
		while(it.hasNext()){
			//遍历合同->电费合同|租费合同关联关系，取出合同实体并放入insert集合
			String next = it.next();
			String[] tmp = next.split(BatchControlComm.contDelimiterStr);
			String eleContractId = tmp[0].replace("\\", "");
			String rentContractId = tmp[1];
			
			if(ContractComm.contract_type_1.equals(contType)){//电费合同
				List<EleContract> list = getElecContractByRedis(eleContractId, cont_rediskey);
				eleContractList.addAll(list);
			}
			else if(ContractComm.contract_type_2.equals(contType) ){//租费合同
				List<RentContractVO> list = getRentContractByRedis(rentContractId, cont_rediskey);
				rentContractList.addAll(list);
			}else{//综合合同
				List<EleContract> elist = getElecContractByRedis(eleContractId, cont_rediskey);
				eleContractList.addAll(elist);
				List<RentContractVO> rlist = getRentContractByRedis(rentContractId, cont_rediskey);
				rentContractList.addAll(rlist);
			}
		}
		
	}

	/**
	 * 同步mysql到redis做比对集合
	 * @param prv_id
	 * @param cid
	 * @param eid
	 * @param rid
	 */
	private void getRedisCompareSets(String prv_id,String cont_rediskey){
		
		if(jedisUtil==null){
			jedisUtil= JedisUtil.getInstance();
		}
		if(jedis_KeysSETS==null) {
			jedis_KeysSETS = jedisUtil.new Keys();
		}

		// 总的主合同集合：用于比对是否存在
		List<DatContractVO> datcontractRedisList = datContractVOMapper.selectRedisComDatContractVOByPrvId(prv_id); 
		jedis_dball_datcontract=jedisUtil.new Sets();
		jedis_dball_datcontract_sysid=jedisUtil.new Hash();
		jedis_files_datcont = jedisUtil.new Hash();
		jedis_files_eleccont = jedisUtil.new Hash();
		jedis_files_rentcont = jedisUtil.new Hash();
		Map<String, String> sysnumEntryMap = new HashMap<>();
		List<String> dbbuffer1=new ArrayList<>(), dbbuffer2=new ArrayList<>();
		for(DatContractVO datcontractRedis : datcontractRedisList){
			sysnumEntryMap.put(datcontractRedis.getContractsysId(), JSONObject.fromObject(datcontractRedis).toString());
			DatContractVO datContractTmp = (DatContractVO) datcontractRedis.clone();//娣卞害鍏嬮殕
			datContractTmp.setAuditingState(0);
			dbbuffer1.add(JSONObject.fromObject(datContractTmp).toString());
			dbbuffer2.add(datcontractRedis.getContractsysId());
		}
		if(datcontractRedisList.size()>0){
			jedis_dball_datcontract.sadd(DB_CONT_Prex+cont_rediskey, dbbuffer1);
			jedis_dball_datcontract.sadd(DB_SYSID_Prex+cont_rediskey, dbbuffer2);
			jedis_dball_datcontract_sysid.hmset(DB_SYSID_ENTRY_Prex+cont_rediskey, sysnumEntryMap);
		}
		LOGGER.debug("---------DB_CONT_Prex:"+dbbuffer1.size());
		// 总的供应商集合：用于比对是否存在
		List<Map<String, String>> suppliersRedisList = datSupplierVOMapper.selectRedisComStringByPrvId(prv_id); 
		dball_subppliers=new HashMap<>();
		for(Map<String, String> map : suppliersRedisList){
			dball_subppliers.put(map.get("comkey"), map.get("comval"));
		}
		LOGGER.debug("---------dball_subppliers:"+dball_subppliers.keySet().size());
	}
	/**
	 * 存储合同关联id到集合SETS
	 * @param prv_id
	 * @param cid
	 * @param eid
	 * @param rid
	 */
	private void setIdLinkSets(String prv_id, String cid, String eid, String rid, String cont_rediskey){
		if(jedis_idlinks_list==null){
			jedis_idlinks_list=jedisUtil.new Hash();
		}
		Map<String, String> map = new HashMap<>();
		map.put(cid, eid+BatchControlComm.contDelimiterStr+rid);
		if(map.keySet().size()>0){
			jedis_idlinks_list.hmset(FILE_ELEC_RENT_IDLINK_Prex+cont_rediskey, map);
		}
	}
	private void setDatIdLinkSets(DatContractVO datContract, String cont_rediskey){
		if(jedis_datIdlinks_list==null){
			jedis_datIdlinks_list=jedisUtil.new Hash();
		}
		String sysid = datContract.getContractsysId();
		Map<String, String> map = new HashMap<>();
		map.put(sysid+"_IDLINK", datContract.getContractId());
		map.put(sysid, JSONObject.fromObject(datContract).toString());
		if(map.keySet().size()>0){
			jedis_datIdlinks_list.hmset(FILE_CONT_IDLINK_Prex+cont_rediskey, map);
		}
	}
	/**
	 * 根据主合同流水号获取redis中的实体
	 * @param eleContractId
	 * @return
	 */
	private DatContractVO getContractBySysidRedis(String sysNum, String cont_rediskey){
		DatContractVO obj = null;
		if(!jedis_dball_datcontract_sysid.hexists(DB_SYSID_ENTRY_Prex+cont_rediskey, sysNum)){
			return null;
		}
		List<String> entList = jedis_dball_datcontract_sysid.hmget(DB_SYSID_ENTRY_Prex+cont_rediskey, sysNum);
		if(entList==null){
			return null;
		}
		Iterator<String> contIt = entList.iterator();
		if(contIt.hasNext()){
			String next = contIt.next();
			JSONObject jsonObject=JSONObject.fromObject(next);
			obj = (DatContractVO) JSONObject.toBean(jsonObject, DatContractVO.class);
		}
		return obj;
	}
	/**
	 * 根据电费合同id获取redis中的实体
	 * @param eleContractId
	 * @return
	 */
	private List<EleContract> getElecContractByRedis(String contractId,String cont_rediskey){
		List<EleContract> list = new ArrayList<>();
		if(!jedis_dball_datcontract_sysid.hexists(FILE_ELEC_Prex+cont_rediskey, contractId)){
			return null;
		}
		List<String> elecEntList = jedis_files_eleccont.hmget(FILE_ELEC_Prex+cont_rediskey, contractId);
		if(elecEntList==null){
			return null;
		}
		Iterator<String> elecIt = elecEntList.iterator();
		while(elecIt.hasNext()){
			String next = elecIt.next();
			JSONObject jsonObject=JSONObject.fromObject(next);
			EleContract obj = (EleContract) JSONObject.toBean(jsonObject, EleContract.class);
			list.add(obj);
		}
		return list;
	}
	/**
	 * 根据房租合同id获取redis中的实体
	 * @param eleContractId
	 * @return
	 */
	private List<RentContractVO> getRentContractByRedis(String contractId,String cont_rediskey){
		List<RentContractVO> list = new ArrayList<>();
		List<String> rentEntList = jedis_files_rentcont.hmget(FILE_RENT_Prex+cont_rediskey, contractId);
		if(rentEntList==null){
			return null;
		}
		Iterator<String> rentIt = rentEntList.iterator();
		while(rentIt.hasNext()){
			String next = rentIt.next();
			JSONObject jsonObject=JSONObject.fromObject(next);
			RentContractVO obj = (RentContractVO) JSONObject.toBean(jsonObject, RentContractVO.class);
			obj.setAuditState(AuditStateEnum.PREAUDIT);
			list.add(obj);
		}
		return list;
	}
	
	// 生成供应商信息
	private void dealDatSupplierVO(String contractType, DatSupplierVO datSupplierVO, 
			DatContractVO datContract, EleContract eleContract, RentContractVO rentContract,
			List<DatSupplierVO> u_datSupplierVOList,List<DatSupplierVO> i_datSupplierVOList, 
			TaskInfoVO taskInfo, String contKey){
		
		datSupplierVO.setDataFrom(2);//数据来源（0：系统录入  1:系统导入   2：接口采集）
		datSupplierVO.setSupplierState(0);
		
		String comkey = datSupplierVO.getPrvId()+BatchControlComm.contDelimiterStr
				+datSupplierVO.getSupplierCode()
				+BatchControlComm.contDelimiterStr+datSupplierVO.getSupplierSite();
		//redis集合不存在元素key
		if(!dball_subppliers.containsKey(comkey)){
			
			//文件中重复供应商，已存在
			if(dball_subppliers.containsKey("SUPP_REPLY_"+comkey)){
				String replySupplierId = dball_subppliers.get("SUPP_REPLY_"+comkey);
				eleContract.setSupplierId(replySupplierId);
				rentContract.setSupplierId(replySupplierId);
			}
			else{
				
				//供应商code为null时不入库
				if(!StrUtil.isBlank(datSupplierVO.getSupplierCode())){

					// 关联电费、租费合同的供应商id
					String supplierId = SysUUID.generator();
					eleContract.setSupplierId(supplierId);
					rentContract.setSupplierId(supplierId);
					datSupplierVO.setSupplierId(supplierId);
					datSupplierVO.setPrvId(datContract.getPrvId());
					// 供应商其他信息
					datSupplierVO.setCreate_user(taskInfo.getOperateUser());
					datSupplierVO.setCreate_ip(taskInfo.getOperateUserIp());
					datSupplierVO.setCreate_time(new Date());
					i_datSupplierVOList.add(datSupplierVO);
					//存入redis
					dball_subppliers.put("SUPP_REPLY_"+comkey, supplierId);//文件重复供应商key
				}
				else{
					eleContract.setSupplierId(null);
					rentContract.setSupplierId(null);
				}
				
			}
		}
		else{  
			String next = dball_subppliers.get(comkey);  
			String[] tmp = next.split(BatchControlComm.contDelimiterStr);
			String replySupplierId = tmp[0].replace("\\", "");
			eleContract.setSupplierId(replySupplierId);
			rentContract.setSupplierId(replySupplierId);
			// 判断是否更新了供应商姓名
			if(!datSupplierVO.getSupplierName().equals(tmp[1])){
				datSupplierVO.setSupplierId(replySupplierId);
				datSupplierVO.setUpdate_user(taskInfo.getOperateUser());
				datSupplierVO.setUpdate_ip(taskInfo.getOperateUserIp());
				datSupplierVO.setUpdate_time(new Date());
				u_datSupplierVOList.add(datSupplierVO);
			}
		}
	}

	@Override
	public int deleteFromDB(String pkName,String typeName,List<String> columns,Map<String, String> map,
			SysProvinceVO sysProvinceVO,String delimiter,String tableName) throws Exception{

		String prvId=sysProvinceVO.getPrvId();
		List<String> datalist = new ArrayList<String>();
		for (String key : map.keySet()) {
			datalist.add(key);
		}
		HashMap<String,Object> pMap=new HashMap<String,Object>();
		pMap.put("prvId", prvId);
		pMap.put("datalist", datalist);
		return datContractVOMapper.delByContractsysId(pMap);
	}
	
	@Override
	public String[] getDBparms(String perfix) {

		String[] result=new String[3];
		String pkName="";
		String tableName="";
		if(perfix.equalsIgnoreCase(DataType.PK_CONTRACT)){
			//改为流水号判重
			pkName="contract_num";
			tableName=TableType.CONTRACT;
		}
		result[0]=pkName;
		result[1]=perfix;
		result[2]=tableName;
		return result;
	}

	
}
