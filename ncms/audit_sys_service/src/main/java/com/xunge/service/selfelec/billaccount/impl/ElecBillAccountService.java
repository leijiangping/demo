package com.xunge.service.selfelec.billaccount.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xunge.comm.BillAccountComm;
import com.xunge.comm.ContractCodeComm;
import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.DateUtil;
import com.xunge.core.util.SessionUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.dao.basedata.DatBaseresourceVOMapper;
import com.xunge.dao.basedata.DatBasestationVOMapper;
import com.xunge.dao.basedata.DatElectricmeterMapper;
import com.xunge.dao.selfelec.EleBaseresourceelectricmeterMapper;
import com.xunge.dao.selfelec.EleBenchmarkParaVOMapper;
import com.xunge.dao.selfelec.EleBillaccountExpMapper;
import com.xunge.dao.selfelec.EleBillaccountMapper;
import com.xunge.dao.selfelec.EleBillaccountbaseresourceMapper;
import com.xunge.dao.selfelec.EleContractbillaccountMapper;
import com.xunge.dao.selfelec.SysSequenceMapper;
import com.xunge.dao.selfelec.VEleBaseresourceelectricmeterMapper;
import com.xunge.dao.selfelec.VEleBillaccountMapper;
import com.xunge.dao.selfelec.VEleBillaccountbaseresourceMapper;
import com.xunge.dao.selfelec.VEleBillaccountcontractMapper;
import com.xunge.dao.selfelec.electricmeter.DatElectricMeterVOMapper;
import com.xunge.model.activity.Act;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.DatBasestationVO;
import com.xunge.model.basedata.DatElectricmeter;
import com.xunge.model.selfelec.EleBaseresourceelectricmeter;
import com.xunge.model.selfelec.EleBenchmarkParaVO;
import com.xunge.model.selfelec.EleBillaccount;
import com.xunge.model.selfelec.EleBillaccountbaseresource;
import com.xunge.model.selfelec.EleContractbillaccount;
import com.xunge.model.selfelec.VDatBaseresource;
import com.xunge.model.selfelec.VDatElectricmeter;
import com.xunge.model.selfelec.VEleBaseresourceelectricmeter;
import com.xunge.model.selfelec.VEleBaseresourceelectricmeterExample;
import com.xunge.model.selfelec.VEleBillaccount;
import com.xunge.model.selfelec.VEleBillaccountExample;
import com.xunge.model.selfelec.VEleBillaccountExample.Criteria;
import com.xunge.model.selfelec.VEleBillaccountbaseresource;
import com.xunge.model.selfelec.VEleBillaccountbaseresourceExample;
import com.xunge.model.selfelec.VEleBillaccountcontract;
import com.xunge.model.selfelec.VEleBillaccountcontractExample;
import com.xunge.model.selfelec.VEleContract;
import com.xunge.model.selfelec.electricmeter.DatElectricMeterVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.util.SysSequence;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.selfelec.billaccount.IElecBillAccountService;
import com.xunge.service.selfelec.billaccount.exceldatahandler.EleBillaccountHandler;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.util.CodeGeneratorUtil;

/** 
* @author yangju E-mail: 
* @version 创建时间：2017年6月28日 下午2:54:04 
* 类说明 
*/
@Service
public class ElecBillAccountService implements IElecBillAccountService {

	@Resource
	private VEleBillaccountMapper vEleBillaccountMapper;
	
	@Resource
	private DatElectricmeterMapper datElectricmeterMapper;
	
	@Resource
	private EleBillaccountMapper eleBillaccountMapper;
	
	@Resource
	private EleBillaccountExpMapper eleBillaccountExpMapper;
	
	@Resource
	private VEleBillaccountcontractMapper vEleBillaccountcontractMapper;
	
	@Resource
	private VEleBillaccountbaseresourceMapper vEleBillaccountbaseresourceMapper;
	
	@Resource
	private VEleBaseresourceelectricmeterMapper vEleBaseresourceelectricmeterMapper;
	
	@Resource
	private EleContractbillaccountMapper eleContractbillaccountMapper;
	
	@Resource
	private EleBillaccountbaseresourceMapper eleBillaccountbaseresourceMapper; 
	
	@Resource
	private EleBaseresourceelectricmeterMapper eleBaseresourceelectricmeterMapper;
	
	@Resource
	private DatElectricMeterVOMapper electricMeter;

	@Resource
	private DatBaseresourceVOMapper baseresource;
	
	@Resource
	private DatBasestationVOMapper basestation;
	
	@Resource
	private EleBenchmarkParaVOMapper eleBenchmarkPara;
	
	@Autowired
	private IActTaskService actTaskService;
	
	@Autowired
	private ISysRegionService sysRegionService;
	
	@Resource
	private SysSequenceMapper sysSequenceMapper;

	@Autowired
	private ILogService log;
	@Override
	public PageInfo<VEleBillaccount> queryReimburseInfo(UserLoginInfo userLoginInfo , VEleBillaccount param, int pageNumber, int pageSize) {
		VEleBillaccountExample example = new VEleBillaccountExample();
		
		if(param!=null){
			Criteria criteria = example.createCriteria();
			if(StringUtils.isNotBlank(param.getBillaccountCode())){
				criteria.andBillaccountCodeLike("%"+param.getBillaccountCode().trim()+"%");
			}
			if(StringUtils.isNotBlank(param.getBillaccountName())){
				criteria.andBillaccountNameLike("%"+param.getBillaccountName().trim()+"%");
			}
			if(StringUtils.isNotBlank(param.getPregId())){
				criteria.andPregIdEqualTo(param.getPregId());
			}
			if(StringUtils.isNotBlank(param.getRegId())){
				criteria.andRegIdEqualTo(param.getRegId());
			}
			//区分报账点自维塔维
			if(StringUtils.isNotBlank(param.getBillaccountType().toString())){
				criteria.andBillaccountTypeEqualTo(param.getBillaccountType());
			}
			if(null != param.getPaymentState() && StringUtils.isNotBlank(param.getPaymentState().toString())){
				criteria.andPaymentStateEqualTo(param.getPaymentState());
			}
//			criteria.andBillaccountStateEqualTo(0);
			
			if(param.getAuditingState()!=null){
				criteria.andAuditingStateEqualTo(param.getAuditingState());
			}
		}
		
		if(userLoginInfo!=null){
			Criteria criteria = example.createCriteria();
			if(StringUtils.isNoneBlank(userLoginInfo.getReg_id())){
				criteria.andRegIdEqualTo(userLoginInfo.getReg_id());
			}
			if(StringUtils.isNoneBlank(userLoginInfo.getPrv_id())){
				criteria.andPrvIdEqualTo(userLoginInfo.getPrv_id());
			}
		}
		
		PageHelper.startPage(pageNumber, pageSize);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("example", example);
		map.put("regIds", userLoginInfo.getReg_ids());
		List<VEleBillaccount> datas = vEleBillaccountMapper.selectByExample(map);
		
		PageInfo<VEleBillaccount> pageinfo = new PageInfo<VEleBillaccount>(datas);
		return pageinfo;
	}

	@Override
	public int saveOrUpdateEleAccount(EleBillaccount account,UserLoginInfo loginUser) {
		int result = Integer.parseInt(SelfelecComm.NUMBER_0);
		if(account!=null){
			if(StringUtils.isNotBlank(account.getBillaccountId())){
				result = eleBillaccountMapper.updateByPrimaryKeySelective(account);
			}else{
				String billaccountCode = queryMaxCode(loginUser,BillAccountComm.ZDBZD_NAME,BillAccountComm.ZDBZD);
				account.setBillaccountCode(billaccountCode);
				account.setBillaccountId(UUID.randomUUID().toString().replace("-", ""));
				result = eleBillaccountMapper.insertSelective(account);
			}
		}
		
		return result;
	}

	@Override
	public int deleteEleAccount(List<VEleBillaccount> ids) {
		int result = Integer.parseInt(SelfelecComm.NUMBER_0);
		SimpleDateFormat df = new SimpleDateFormat(SelfelecComm.FORMAT_yyyyMMdd);//设置日期格式
		String relationEnddate = df.format(new Date());
		Map<String, Object> paraMap = new HashMap<String, Object>();
			for(VEleBillaccount ele: ids){
				paraMap.put("billaccountId", ele.getBillaccountId());
				paraMap.put("relationEnddate", relationEnddate);
				List<String> baseresourceIds = eleBaseresourceelectricmeterMapper.queryBaseresourceIdByBillaccountId(ele.getBillaccountId());
				for (int i = Integer.parseInt(SelfelecComm.NUMBER_0); i < baseresourceIds.size(); i++) {
					Map<String, Object> para = new HashMap<String, Object>();
					para.put("relationEnddate", relationEnddate);
					para.put("baseresourceId", baseresourceIds.get(i));
					eleBaseresourceelectricmeterMapper.deleteRescoureMeter(para);
				}
				eleBillaccountMapper.deleteBillaccountContract(paraMap);
				eleBillaccountMapper.deleteBillaccountResource(paraMap);
				result += eleBillaccountMapper.updateStateByPrimaryKey(ele.getBillaccountId());
			}	
		return result;
	}

	@Override
	public PageInfo<VEleContract> queryUnrelationContractInfo(VEleBillaccountcontract param, int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<VEleContract> datas = eleBillaccountExpMapper.selectByContract(param);
		return new PageInfo<VEleContract>(datas);
	}

	@Override
	public PageInfo<VDatBaseresource> queryUnrelationResourceInfo(VEleBillaccountbaseresource param, int pageNumber,
			int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<VDatBaseresource> datas = eleBillaccountExpMapper.selectByResource(param);
		for(int i=Integer.parseInt(SelfelecComm.NUMBER_0);i<datas.size();i++){
			Map<String,Object> map=new HashMap<>();
			map.put("baseresourceId", datas.get(i).getBaseresourceId());
			map.put("regId", param.getRegId());
			List<DatElectricMeterVO> electricMeterVO = electricMeter.queryMeterByResourceId(map);
			datas.get(i).setElectricMeterVO(electricMeterVO);
		}
		return new PageInfo<VDatBaseresource>(datas);
	}

	@Override
	public int reviewEleAccount(List<VEleBillaccount> ids, String userId, Integer state) {
		int result = Integer.parseInt(SelfelecComm.NUMBER_0);
		for(VEleBillaccount vele : ids){
			EleBillaccount ele = new EleBillaccount();
			ele.setBillaccountId(vele.getBillaccountId());
			ele.setAuditingDate(new Date());
			ele.setAuditingState(state);
			ele.setAuditingUserId(userId);
			result += eleBillaccountMapper.updateByPrimaryKeySelective(ele);
		}
		return result;
	}

	@Override
	public PageInfo<VDatElectricmeter> queryUnrelationElectricmeterInfo(VEleBillaccountbaseresource resource , int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<VDatElectricmeter> datas = eleBillaccountExpMapper.selectByElectricmeter(resource);
		return new PageInfo<VDatElectricmeter>(datas);
	}

	@Override
	public PageInfo<VEleBillaccountcontract> queryRelationedContractInfo(VEleBillaccountcontract param,
			int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		VEleBillaccountcontractExample example = new VEleBillaccountcontractExample();
		com.xunge.model.selfelec.VEleBillaccountcontractExample.Criteria criteria = example.createCriteria();
		if(param!=null && StringUtils.isNoneBlank(param.getBillaccountId())){
			criteria.andBillaccountIdEqualTo(param.getBillaccountId());
		}
		criteria.andRelationStateEqualTo(Integer.parseInt(SelfelecComm.NUMBER_0));
//		criteria.andRelationEnddateIsNull();
		List<VEleBillaccountcontract> datas = vEleBillaccountcontractMapper.selectByExample(example);
		return new PageInfo<VEleBillaccountcontract>(datas);
	}

	@Override
	public PageInfo<VEleBillaccountbaseresource> queryRelationedResourceInfo(VEleBillaccountbaseresource param,
			int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		VEleBillaccountbaseresourceExample example = new VEleBillaccountbaseresourceExample();
		com.xunge.model.selfelec.VEleBillaccountbaseresourceExample.Criteria criteria = example.createCriteria();
		if(param!=null && StringUtils.isNoneBlank(param.getBillaccountId())){
			criteria.andBillaccountIdEqualTo(param.getBillaccountId());
		}
		if(param!=null && StringUtils.isNoneBlank(param.getRegId())){
			criteria.andRegIdEqualTo(param.getRegId());
		}
		criteria.andRelationStateEqualTo(Integer.parseInt(SelfelecComm.NUMBER_0));
//		criteria.andRelationEnddateIsNull();
		
		List<VEleBillaccountbaseresource> datas = vEleBillaccountbaseresourceMapper.selectByExample(example);
		
		return new PageInfo<VEleBillaccountbaseresource>(datas);
	}

	@Override
	public List<VEleBaseresourceelectricmeter> queryRelationedElectricmeterInfo(VEleBillaccountbaseresource resource) {
		VEleBaseresourceelectricmeterExample example = new VEleBaseresourceelectricmeterExample();
		com.xunge.model.selfelec.VEleBaseresourceelectricmeterExample.Criteria criteria = example.createCriteria();
		if(resource !=null && StringUtils.isNoneBlank(resource.getBillaccountId())){
			criteria.andBillaccountIdEqualTo(resource.getBillaccountId());
		}
		
		if(resource !=null && StringUtils.isNoneBlank(resource.getBaseresourceId())){
			criteria.andBaseresourceIdEqualTo(resource.getBaseresourceId());
		}
		
		criteria.andRelationStateEqualTo(Integer.parseInt(SelfelecComm.NUMBER_0));
//		criteria.andRelationEnddateIsNull();
		
		return vEleBaseresourceelectricmeterMapper.selectByExample(example);
	}

	/*@Override
	public int saveOrUpdateRelationContract(EleContractbillaccount contract) {
		int result = 0;
		if(contract!=null){
			if(StringUtils.isNoneBlank(contract.getElebillaccountcontractId())){
				//更新
				contract.setRelationEnddate(new Date());
				contract.setRelationState(9);
				result = eleContractbillaccountMapper.updateByPrimaryKeySelective(contract);
			}else{
				contract.setElebillaccountcontractId(UUID.randomUUID().toString().replace("-", ""));
				
				contract.setRelationStartdate(new Date());
				contract.setRelationState(0);
				result = eleContractbillaccountMapper.insertSelective(contract);
			}
			
		}
		
		return result;
	}*/
	/**
	 * 修改方法：报账点只能关联一个电费合同
	 */
	@Override
	public int saveOrUpdateRelationContract(EleContractbillaccount contract) {
		int result = Integer.parseInt(SelfelecComm.NUMBER_0);
		if(contract!=null){
			if(StringUtils.isNoneBlank(contract.getElebillaccountcontractId())){
				EleContractbillaccount contractbillaccount=eleContractbillaccountMapper.selectByPrimaryKey(contract.getElebillaccountcontractId());
				//更新
				contractbillaccount.setRelationEnddate(new Date());
				contractbillaccount.setRelationState(StateComm.STATE_9);
				result = eleContractbillaccountMapper.updateByPrimaryKeySelective(contractbillaccount);
			}
				contract.setElebillaccountcontractId(UUID.randomUUID().toString().replace("-", ""));
				contract.setRelationStartdate(new Date());
				contract.setRelationState(StateComm.STATE_0);
				result = eleContractbillaccountMapper.insertSelective(contract);
			
		}
		
		return result;
	}

	@Override
	public int saveOrUpdateRelationResource(EleBillaccountbaseresource resource) {
		int result = Integer.parseInt(SelfelecComm.NUMBER_0);
		if(resource!=null){
			if(StringUtils.isNotBlank(resource.getBillaccountbaseresourceId())){
				String[] bsids = resource.getBillaccountbaseresourceId().split(",");
				for(String id:bsids){
					EleBillaccountbaseresource tmp = new EleBillaccountbaseresource();
					tmp.setBillaccountbaseresourceId(id);
					tmp.setRelationState(StateComm.STATE_9);
					
					tmp.setRelationEnddate(new Date());
					result += eleBillaccountbaseresourceMapper.updateByPrimaryKeySelective(tmp);
				}
			}else{
				if(resource!=null && resource.getBaseresourceId()!=null){
					String[] bsids = resource.getBaseresourceId().split(",");
					for(String id:bsids){
						EleBillaccountbaseresource tmp = new EleBillaccountbaseresource();
						tmp.setBaseresourceId(id);
						tmp.setBillaccountId(resource.getBillaccountId());
						
						tmp.setRelationState(StateComm.STATE_0);
						
						tmp.setRelationStartdate(new Date());
						tmp.setBillaccountbaseresourceId(UUID.randomUUID().toString().replace("-", ""));
						result += eleBillaccountbaseresourceMapper.insertSelective(tmp);
					}
				}
				
			}
		}
		
		return result;
	}

	@Override
	public int saveOrUpdateElectricmeterContract(EleBaseresourceelectricmeter electricmeter) {
		int result = Integer.parseInt(SelfelecComm.NUMBER_0);
		if(electricmeter!=null){
			if(StringUtils.isNotBlank(electricmeter.getBaseresourceelectricmeterId())){
				String[] baseresourceelectricmeterId = electricmeter.getBaseresourceelectricmeterId().split(",");
				String[] baseresourceId = electricmeter.getBaseresourceId().split(",");
				String[] meterId = electricmeter.getMeterId().split(",");
				for(int i=Integer.parseInt(SelfelecComm.NUMBER_0);i<meterId.length;i++){
					electricmeter.setBaseresourceelectricmeterId(baseresourceelectricmeterId[i]);
					electricmeter.setBaseresourceId(baseresourceId[i]);
					electricmeter.setMeterId(meterId[i]);
					electricmeter.setRelationEnddate(new Date());
					electricmeter.setRelationState(StateComm.STATE_9);
					result = eleBaseresourceelectricmeterMapper.updateByPrimaryKeySelective(electricmeter);
					
					//同时  更新电表 报账点id
					DatElectricmeter record = datElectricmeterMapper.selectByPrimaryKey(electricmeter.getMeterId());
					record.setBillaccountId(null);
					datElectricmeterMapper.updateByPrimaryKey(record);
				}
				
			}else{
				if(electricmeter!=null && electricmeter.getMeterId()!=null){
					String[] mids = electricmeter.getMeterId().split(",");
					for(String id:mids){
						EleBaseresourceelectricmeter tmp = new EleBaseresourceelectricmeter();
						tmp.setBaseresourceId(electricmeter.getBaseresourceId());
						tmp.setBillaccountId(electricmeter.getBillaccountId());
						tmp.setMeterId(id);
						
						tmp.setRelationStartdate(new Date());
						tmp.setRelationState(StateComm.STATE_0);
						tmp.setBaseresourceelectricmeterId(UUID.randomUUID().toString().replace("-", ""));
						result += eleBaseresourceelectricmeterMapper.insertSelective(tmp);
						
						//同时  更新电表 报账点id
						DatElectricmeter record = new DatElectricmeter();
						record.setMeterId(id);
						record.setBillaccountId(electricmeter.getBillaccountId());
						datElectricmeterMapper.updateByPrimaryKeySelective(record);
					}
				}
				
			}
		}
		
		return result;
	}

//	@Override
//	public int cancelRelationContract(EleContractbillaccount contract) {
//		int result = 0;
//		if(contract!=null){
//			contract.setRelationEnddate(new Date());
//			contract.setRelationState(9);
//			result = eleContractbillaccountMapper.updateByPrimaryKey(contract);
//		}
//		
//		return result;
//	}

	@Override
	public List<VEleBillaccount> queryReimburseInfoForExport(UserLoginInfo userLoginInfo ,VEleBillaccount param) {
		VEleBillaccountExample example = new VEleBillaccountExample();
		
		if(param!=null){
			Criteria criteria = example.createCriteria();
			if(StringUtils.isNotBlank(param.getBillaccountCode())){
				criteria.andBillaccountCodeLike("%" + param.getBillaccountCode().trim() + "%");
			}
			if(StringUtils.isNotBlank(param.getBillaccountName())){
				criteria.andBillaccountNameLike("%" + param.getBillaccountName().trim() + "%");
			}
			if(StringUtils.isNotBlank(param.getPregId())){
				criteria.andPregIdEqualTo(param.getPregId());
			}
			if(StringUtils.isNotBlank(param.getRegId())){
				criteria.andRegIdEqualTo(param.getRegId());
			}
			
			if(param.getBillaccountState()!=null){
				criteria.andBillaccountStateEqualTo(param.getBillaccountState());
			}
			
			if(param.getAuditingState()!=null){
				criteria.andAuditingStateEqualTo(param.getAuditingState());
			}
			
		}
		
		if(userLoginInfo!=null){
			Criteria criteria = example.createCriteria();
			if(StringUtils.isNoneBlank(userLoginInfo.getReg_id())){
				criteria.andRegIdEqualTo(userLoginInfo.getReg_id());
			}
			if(StringUtils.isNoneBlank(userLoginInfo.getPrv_id())){
				criteria.andPrvIdEqualTo(userLoginInfo.getPrv_id());
			}
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("example", example);
		map.put("regIds", userLoginInfo.getReg_ids());
		return vEleBillaccountMapper.selectByExample(map);
	}

	@Override
	public List<Map<String, Object>> queryResourceTree(VEleBillaccountbaseresource resource) {
		List<Map<String, Object>> data = eleBillaccountExpMapper.selectResourceTree(resource);
		/**
		 * 按分组 排序
		 */
		Map<String , Map<String,Object>> map = new HashMap<>();
		for(Map<String, Object> m : data){
			map.put(m.get("nodeId").toString() , m);
		}
		
		List<Map<String, Object>> result = new ArrayList<>();
		for(Map<String, Object> m : data){
			if(m.get("parentId")==null){
				result.add(m);
			}
		}
		
		Map<String, Object> ptemp;
		int index = Integer.parseInt(SelfelecComm.NUMBER_0);
		for(Map<String, Object> m : data){
			if(m.get("parentId")!=null){
				ptemp = map.get(m.get("parentId").toString());
				if(result.contains(ptemp)){
					index = result.indexOf(ptemp);
					result.add(index+SelfelecComm.NUMBER_1, m);
				}else{
					result.add(m);
				}
			}
		}
		
		return result;
	}

	@Override
	public PageInfo<Map<String, Object> > queryResourceMeterTree(VEleBillaccountbaseresource resource, int pageNumber,
			int pageSize){
	
		PageHelper.startPage(pageNumber, pageSize);
		List<Map<String, Object>> data = eleBillaccountExpMapper.selectResourceTree(resource);
		/**
		 * 按分组 排序
		 */
		Map<String , Map<String,Object>> map = new HashMap<>();
		for(Map<String, Object> m : data){
			map.put(m.get("nodeId").toString() , m);
		}
		
		List<Map<String, Object>> result = new ArrayList<>();
		for(Map<String, Object> m : data){
			if(m.get("parentId")==null){
				result.add(m);
			}
		}
		
		Map<String, Object> ptemp;
		int index = Integer.parseInt(SelfelecComm.NUMBER_0);
		for(Map<String, Object> m : data){
			if(m.get("parentId")!=null){
				ptemp = map.get(m.get("parentId").toString());
				if(result.contains(ptemp)){
					index = result.indexOf(ptemp);
					result.add(index+SelfelecComm.NUMBER_1, m);
				}else{
					result.add(m);
				}
			}
		}
		
		return new PageInfo<Map<String, Object> >(result);
	}
	
	@Override
	public List<Map<String, Object>> selectResourceRelations(String billaccountId) {
		return eleBillaccountExpMapper.selectResourceRelations(billaccountId);
	}
	/**
	 * 提出业务方法
	 * state：审核状态 、paymentState：业务表提交状态、id：业务表主键id
	 */
	private void updateBusinessState(int state,String id){
		 Map<String,Object> maps=new HashMap<String,Object>();
			maps.put("state",state );
			maps.put("billaccountId", id);
			eleBillaccountMapper.updateActivityCommit(maps);
	}
	@Override
	public int updateActivityCommit(List<Map<String, Object>> list,
			UserLoginInfo loginUser) {
		for(Map<String,Object> map:list){
			EleBillaccount billaccount = eleBillaccountMapper.selectByPrimaryKey(map.get("billaccountId").toString());
			if(billaccount!=null&&billaccount.getAuditingState()==ActivityStateComm.STATE_UNCOMMITTED){
				 actTaskService.startProcess(ActUtils.PD_ELEBILLACCOUNT[0],loginUser.getPrv_code(), ActUtils.PD_ELEBILLACCOUNT[1], map.get("billaccountId").toString(),ActUtils.PD_ELEBILLACCOUNT[2],map,loginUser.getUser_loginname());
				 this.updateBusinessState(ActivityStateComm.STATE_AUDIT,map.get("billaccountId").toString());
			}
		}
		return list.size();
	}

	@Override
	public int updateAuditCompelet(List<Map<String, Object>> list, UserLoginInfo loginInfo) {
		for(Map<String,Object> map:list){
			String taskid=map.get("taskId").toString();
			
			Task t=actTaskService.getTask(taskid);
			//增加判断当前业务数据是不是已经提交，需要根据ID重新查询数据库。
			if(t!=null){
				Map<String,Object> vars=new HashMap<String,Object>();
				vars.put("state", map.get("state").toString());
				if(StrUtil.isNotBlank(map.get("leader").toString())){
					vars.put("nextUserId", map.get("leader").toString());//指定下一环节审核人
				}
				String user_loginname = loginInfo.getUser_loginname();
				if(StrUtil.isNotBlank(user_loginname)){
					vars.put("currUserId", user_loginname);//指定当前审核人
				}
				actTaskService.completeByBusiness(ActUtils.PD_ELEBILLACCOUNT[1], map.get("billaccountId").toString(), map.get("comment").toString(), ActUtils.PD_ELEBILLACCOUNT[2], vars);
//				if(state.equals(ActivityStateComm.STATE_NORMAL)){
					Task newtask = actTaskService.getTask(ActUtils.PD_ELEBILLACCOUNT[1],map.get("billaccountId").toString() );
//				newtask为空,审核完成
					if(newtask==null){
						//如果是审核不通过，则直接修改业务数据审核状态为不通过
						if(map.get("state")!=null&&map.get("state").toString().equals(ActivityStateComm.STATE_UNAPPROVE+"")){
							 this.updateBusinessState(ActivityStateComm.STATE_UNAPPROVE,map.get("billaccountId").toString());
						}else if(map.get("state")!=null&&map.get("state").toString().equals(ActivityStateComm.STATE_NORMAL+"")){
							 this.updateBusinessState(ActivityStateComm.STATE_NORMAL,map.get("billaccountId").toString());
						}
						 EleBillaccount record = eleBillaccountMapper.selectByPrimaryKey(map.get("billaccountId").toString());
						 record.setAuditingDate(new Date());
						 record.setAuditingUserId(loginInfo.getUser_id());
						 eleBillaccountMapper.updateByPrimaryKey(record);
						 
						 
						SimpleDateFormat sdf= new SimpleDateFormat(SelfelecComm.FORMAT_yyyyMMdd);  
						
						try {
							Map<String, Object> param = new HashMap<>();
							param.put("billaccountId", map.get("billaccountId").toString());
							param.put("startdate", sdf.parse(DateUtil.today()));
							param.put("regid", map.get("pregId").toString());
							int days=(int) DateUtil.diff(DateUtil.parse(PromptMessageComm.DATE_20150101, SelfelecComm.FORMAT_yyyyMMdd), sdf.parse(DateUtil.today()), PromptMessageComm.NUMBER_DAYS);
							param.put("sdays", days+SelfelecComm.NUMBER_1);
							eleBenchmarkPara.addBillaccountHistoryBenchmark(param);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							log.err(SysLogComm.LOG_Error, PromptMessageComm.BILL_ACCOUNT_ID +map.get("billaccountId")+ PromptMessageComm.REG_ID +map.get("pregId")+"------"+e.getMessage());
							e.printStackTrace();

						}
						
						
					}
			}
		}
		
		return list.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<EleBillaccount> queryEleBillaccount(
			Map<String, Object> map, int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<EleBillaccount> datas = eleBillaccountMapper.queryEleBillaccount(map);
		PageInfo<EleBillaccount> page= new PageInfo<EleBillaccount>(datas);
		List<EleBillaccount> list=Lists.newArrayList();
		if(map.get("idsList")!=null){
			for(EleBillaccount t:page.getList()){
				for(Act actTemp:(List<Act>)map.get("idsList")){
					if(t.getBillaccountId()!=null&&t.getBillaccountId().equals(actTemp.getBusinessId())){
						t.setAct(actTemp);
						list.add(t);
					}
				}
			}
			page.setList(list);
		}
		return page;
	}
	
	@Override
	public List<VEleBillaccount> checkBillaccountCode(Map<String,Object> map){
		VEleBillaccountExample example = new VEleBillaccountExample();
		VEleBillaccount param = (VEleBillaccount)map.get("param");
		if(param!=null){
			Criteria criteria = example.createCriteria();
			if(StringUtils.isNotBlank(param.getBillaccountCode())){
				criteria.andBillaccountCodeEqualTo(param.getBillaccountCode());
			}
		}
		map.put("example", example);
		List<VEleBillaccount> datas = vEleBillaccountMapper.selectByExample(map);
		return datas;
	}

	@Override
	public List<EleBillaccount> queryBillaccountById(Map<String, Object> map) {
		
		return eleBillaccountMapper.queryBillaccountById(map);
	}
	
	public Map<String,Object> insertExcelData(MultipartFile file,String suffix,HttpServletRequest request) throws Exception{
		//_EleBillaccount这个是标识Session Name的，每个功能建议唯一
		SessionUtils.setProcessSession(suffix,DateDisposeComm.BEGIN_ANALAYSIS_FILE,5, request);
		
		//设置自定义数据处理
		Map<String,Object> map = Maps.newHashMap();
		map.put("state",SelfelecComm.NUMBER_0);
		//获取当前用户所属地区
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		map.put("prvId",loginInfo.getPrv_id());
		List<SysRegionVO> listreg = sysRegionService.getAddress(map);//准备需要的数据处理
		
		//导入参数
		ImportParams iparams = new ImportParams();
		EleBillaccountHandler tbh = new EleBillaccountHandler(listreg);//报账点信息自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.BELONG_CITY,DateDisposeComm.BELONG_REGION});//需要处理数据的列名
		iparams.setDataHanlder(tbh);
		
		List<VEleBillaccount> dataList = ExcelImportUtil.importExcel(file.getInputStream(), VEleBillaccount.class, iparams);
//		/**
//		 * 验证数据在数据库里面是否存在
//		 */
//		Map<String,Object> ebMap = Maps.newHashMap();
//		EleBillaccountExample example = new EleBillaccountExample();
//		
//		com.xunge.model.selfelec.EleBillaccountExample.Criteria criteria = example.createCriteria();;
//		criteria.andPrvIdEqualTo(loginInfo.getPrv_id());
//		List<EleBillaccount> listt = eleBillaccountMapper.selectByExample(example);
//		if (!listt.isEmpty()) {
//			for (EleBillaccount eb : listt) {
//				String key = eb.getBillaccountCode() + "#" + eb.getBillaccountName()
//						+ "#" + eb.getPregId() + "#" + eb.getRegId();
//				ebMap.put(key, eb);
//			}
//		}
//		
//		List<VEleBillaccount> list = Lists.newArrayList();
//		List<VEleBillaccount> listExist = Lists.newArrayList();
//		for (int index = 0; index < dataList.size(); index++) {
//			VEleBillaccount vb = dataList.get(index);
//			vb.setPrvId(loginInfo.getPrv_id());
//			vb.setAuditingState(-1);
//			
//			String key = vb.getBillaccountCode() + "#" + vb.getBillaccountName()
//					+ "#" + vb.getPregId() + "#" + vb.getRegId();
//			if (ebMap.containsKey(key)) {//如果这条数据已经存在
//				listExist.add(vb);//保存已经存在数据
//			} else {
//				
//				list.add(vb);
//			}
//		}
		
		List<VEleBillaccount> listExist = Lists.newArrayList();
		List<VEleBillaccount> list = Lists.newArrayList();
		Map<String,Object> ebMap = Maps.newHashMap();
		Long number = queryMaxNumber(loginInfo,BillAccountComm.ZDBZD);
		for (int i = Integer.parseInt(SelfelecComm.NUMBER_0); i < dataList.size(); i++) {
			VEleBillaccount vb = dataList.get(i);
			String code = CodeGeneratorUtil.buildBillAccountCode(BillAccountComm.ZDBZD, loginInfo.getPrv_code(), number + i);
			vb.setBillaccountCode(code);
			vb.setPrvId(loginInfo.getPrv_id());
			vb.setAuditingState(StateComm.STATE__1);
			
			String key = vb.getBillaccountName() + "#" + vb.getPregId() + "#" + vb.getRegId();
			if (!ebMap.containsKey(key)) {
				map.put(key, vb);
				list.add(vb);
				number++;
			} 
		}
		// 更新sys_sequence表
		SysSequence record = new SysSequence(BillAccountComm.ZDBZD, DateUtil.format(new Date(), SelfelecComm.FORMAT_yyyy), number.intValue());
		sysSequenceMapper.updateByPrimaryKey(record);
		
		SessionUtils.setProcessSession(suffix,DateDisposeComm.ANALAYSIS_FILE_DONE,Integer.parseInt(StateComm.PAGE_SIZE_STR), request);
		//大批量数据分批插入，每次插入500条
		int a = SelfelecComm.NUMBER_500;//每次提交2000条
		int loop = (int) Math.ceil(list.size() / (double) a);//批次
		int count = list.size();
		int percent = (int) Math.ceil(SelfelecComm.NUMBER_90 / (double) loop);//计算每批占的百分比保存到数据库占80%的进度
		int sumPercent=SelfelecComm.NUMBER_20;//方便每次计算
		for (int i = Integer.parseInt(SelfelecComm.NUMBER_0); i < loop; i++) {
			 int stop = Math.min(a - SelfelecComm.NUMBER_1, list.size());//判断结束的序号
			 
			 vEleBillaccountMapper.batchInsert(list.subList(Integer.parseInt(SelfelecComm.NUMBER_0), stop));
			 list.subList(Integer.parseInt(SelfelecComm.NUMBER_0), stop).clear();//清除已经插入的数据
			 
			 sumPercent+=percent;
			 sumPercent=(sumPercent>SelfelecComm.NUMBER_100?SelfelecComm.NUMBER_100:sumPercent);//如果大于100，按100计算
			 SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
		}
		SessionUtils.setProcessSession(suffix,PromptMessageComm.IMPORT_SUCCESS + PromptMessageComm.IMPORT_SUM + count + PromptMessageComm.IMPORT_SUCCESS_NUMBER,SelfelecComm.NUMBER_100,request);
		
		
		Map<String,Object> returnMap=Maps.newHashMap();
		returnMap.put("errMsg", listExist.size() > Integer.parseInt(SelfelecComm.NUMBER_0) ? PromptMessageComm.ALREADY_EXIST_NUMBER+listExist.size()+PromptMessageComm.STRIP:"");
		returnMap.put("msg",PromptMessageComm.IMPORT_SUM + count+PromptMessageComm.IMPORT_SUCCESS_NUMBER);
		returnMap.put("isExist", listExist);
		returnMap.put("successCount", count);
		return returnMap;
	}
	/**
	 * 查询自动生成编码
	 * @param loginUser,seqName:序列名称,seqCode:序列编码
	 * @author xup
	 */
	@Override
	public String queryMaxCode(UserLoginInfo loginUser,String seqName,String seqCode) {
		Long number = queryMaxNumber(loginUser,seqCode);
		if(ContractCodeComm.ZDGH.equals(seqCode) || 
				ContractCodeComm.TDGH.equals(seqCode) || 
				ContractCodeComm.ZZGH.equals(seqCode)){
			number = queryContractMaxNumber(loginUser,seqCode);
		}
		// 更新sys_sequence表
		SysSequence record = new SysSequence(seqCode, DateUtil.format(new Date(), SelfelecComm.FORMAT_yyyy), number.intValue());
		record.setSeqName(seqName +"-"+loginUser.getPrv_code()+"-"+DateUtil.format(new Date(), SelfelecComm.FORMAT_yyyy));
		if(number.intValue()==SelfelecComm.NUMBER_1){
			sysSequenceMapper.insertSequence(record);
		}else{
			
			sysSequenceMapper.updateByPrimaryKey(record);
		}
		String code = CodeGeneratorUtil.buildBillAccountCode(seqCode, loginUser.getPrv_code(), number);
		return code;
	}
	
	private Long queryMaxNumber(UserLoginInfo loginUser,String seqCode){
		Map<String,Object> param = Maps.newHashMap();
		param.put("prvId", loginUser.getPrv_id());
		param.put("prefix", seqCode +"-"+loginUser.getPrv_code()+"-"+DateUtil.format(new Date(), SelfelecComm.FORMAT_yyyy));
		Map<String,Object> map = vEleBillaccountMapper.queryMaxCode(param);
		Long number = Long.parseLong(StateComm.STATE_str0);
		if (null == map) {
			number = Long.parseLong(StateComm.STATE_str1);
		} else {
			String billCode = map.get("code").toString();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
			String[] strs = billCode.split("-");
			if (strs.length > Integer.parseInt(SelfelecComm.NUMBER_0)) {
				if(strs[2].equals(DateUtil.format(new Date(), SelfelecComm.FORMAT_yyyy))){
					Long num = Long.parseLong(strs[3]);
					number = num + SelfelecComm.NUMBER_1;
				} else {
					number = Long.parseLong(StateComm.STATE_str1);
				}
			}
		}
		return number;
	}
	
	/**
	 * @description 查询电费古话信息最大代码
	 * @author yuefy
	 * @date 创建时间：2017年10月11日
	 */
	private Long queryContractMaxNumber(UserLoginInfo loginUser,String seqCode){
		Map<String,Object> param = Maps.newHashMap();
		param.put("prvId", loginUser.getPrv_id());
		param.put("prefix", seqCode +"-"+loginUser.getPrv_code()+"-"+DateUtil.format(new Date(), SelfelecComm.FORMAT_yyyy));
		Map<String,Object> map = vEleBillaccountMapper.queryContractMaxCode(param);
		Long number = Long.parseLong(StateComm.STATE_str0);
		if (null == map) {
			number = Long.parseLong(StateComm.STATE_str1);
		} else {
			String contractCode = map.get("code").toString();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
			String[] strs = contractCode.split("-");
			if (strs.length > Integer.parseInt(SelfelecComm.NUMBER_0)) {
				if(strs[2].equals(DateUtil.format(new Date(), SelfelecComm.FORMAT_yyyy))){
					Long num = Long.parseLong(strs[3]);
					number = num + SelfelecComm.NUMBER_1;
				} else {
					number = Long.parseLong(StateComm.STATE_str1);
				}
			}
		}
		return number;
	}

	@Override
	public List<VEleBillaccountcontract> queryStateByBillaccountId(
			String billaccountId) {
		return vEleBillaccountcontractMapper.queryStateByBillaccountId(billaccountId);
	}

	@Override
	public void updateById(Map<String, Object> map) {
		vEleBillaccountMapper.updateById(map);
		
	}

	@Override
	public Map<String, Object> queryBillaccountNum(
			Map<String, Object> paramMap) {
		int allaccount = vEleBillaccountMapper.selectBillaccountNum(paramMap);//已关联合同数
		Map<String, Object> resmap = new HashMap<>();
		resmap.put("allaccount", allaccount);
		return resmap;
	}

	@Override
	public List<DatBaseresourceVO> queryBenchmark(String billaccountId, String regId) {
		Map<String, Object> map = new HashMap<>();
		map.put("billaccountId", billaccountId);
		List<DatBaseresourceVO> list = baseresource.queryBaseresourceByBillaccountId(map);
		Map<String, Object> resmap = new HashMap<>();
		resmap.put("list", list);
		List<DatBasestationVO> list2 = basestation.queryBasestationById(resmap);
		EleBenchmarkParaVO key=new EleBenchmarkParaVO();
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH )+SelfelecComm.NUMBER_1;
		key.setRegId(regId);
		key.setMonthNo(month);
		EleBenchmarkParaVO paraVO = eleBenchmarkPara.selectByPrimaryKey(key);
		for (int i = Integer.parseInt(SelfelecComm.NUMBER_0); i < list.size(); i++) {
			list.get(i).setDatBasestationVO(list2);
			list.get(i).setEleBenchmark(paraVO);
		}
		return list;
	}
}