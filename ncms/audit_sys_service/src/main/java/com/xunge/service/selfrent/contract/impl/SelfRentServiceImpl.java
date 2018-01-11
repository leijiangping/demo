package com.xunge.service.selfrent.contract.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.xunge.comm.StateComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.rent.contract.ChargeTypeComm;
import com.xunge.comm.rent.contract.ContractStateComm;
import com.xunge.comm.rent.contract.ContractTypeComm;
import com.xunge.comm.rent.contract.DataFromComm;
import com.xunge.comm.rent.warning.WarningComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.DateUtil;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.selfrent.contract.IDatPaymentPeriodDao;
import com.xunge.dao.selfrent.contract.ISelfRentDao;
import com.xunge.dao.system.parameter.ISysParameterDao;
import com.xunge.model.activity.Act;
import com.xunge.model.selfrent.billAccount.DatPaymentPeriodVO;
import com.xunge.model.selfrent.contract.DatContractVO;
import com.xunge.model.selfrent.contract.DatSupplierVO;
import com.xunge.model.selfrent.contract.RentContractVO;
import com.xunge.model.system.parameter.SysParameterVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.selfrent.contract.ISelfRentService;
/**
 * @description 房租合同维护service实现类
 * @author yuefy
 * @date 创建时间：2017年8月2日
 */
public class SelfRentServiceImpl implements ISelfRentService {

	private ISysParameterDao sysParameterDao;

	private ISelfRentDao selfRentDao;
	@Autowired
	private IActTaskService actTaskService;
	//缴费周期
	@Resource
	private IDatPaymentPeriodDao datPaymentPeriodDao;

	/**
	  * @description 分页查询所有主合同信息  合同预警信息查询
	  * @author yuefy
	  * @date 创建时间：2017年8月2日 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<RentContractVO> queryRentContractVO(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		
		Page<RentContractVO> pagelist = null;
		pagelist = selfRentDao.queryRentContractVO(paraMap, pageNumber, pageSize);
//		List<?> lstvo1 = pagelist.getResult();
		List<RentContractVO> lstvo2 = pagelist.getResult();
		if(paraMap.get("idsList") != null){
			List<RentContractVO> list=Lists.newArrayList();
			for(RentContractVO t:lstvo2){
				for(Act actTemp : (List<Act>) paraMap.get("idsList")){
					if(t.getRentcontractId()!=null&&t.getRentcontractId().equals(actTemp.getBusinessId())){
						t.setAct(actTemp);
						list.add(t);
					}
				}
			}
			pagelist.setResult(list);
		}
		return pagelist;
	}
	
	
	/**
	 * @description 根据条件对租费预警信息进行筛选，查询出符合条件的
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	public void chooseRentContractVORentWarning(List<RentContractVO> lstvo2,Map<String, Object> paraMap){
		String warning = (String) paraMap.get("warningDate");
		if (null != lstvo2 && lstvo2.size() > 0) {
			Iterator<RentContractVO> it = lstvo2.iterator();
			SysParameterVO parameter =  sysParameterDao.queryParameter(paraMap);
			while (it.hasNext()) {
				RentContractVO rentContractVO = (RentContractVO) it.next();
				String rentcontractId = rentContractVO.getRentcontractId();
				paraMap.put("paymentState", StateComm.STATE_0);
				paraMap.put("paymentperiodState", StateComm.STATE_0);
				paraMap.put("rentcontractId", rentcontractId);
				//缴费终期集合
				List<String> rentContractEndDateList = selfRentDao.queryRentContractEndDate(paraMap);
				//缴费周期对象
				DatPaymentPeriodVO datPaymentPeriodVO = null;
				//缴费周期id有值 调数据库
				if(rentContractVO.getPaymentperiodId() != null){
					paraMap.put("paymentperiodId", rentContractVO.getPaymentperiodId());
					datPaymentPeriodVO = datPaymentPeriodDao.queryDatPaymentPeriodById(paraMap);
				}
				//最后缴费日期
				String resultEndDate = null;
				if(rentContractEndDateList != null && rentContractEndDateList.size() > 0){
					//缴费日期将序排列 取第一个为最近缴费日期
					resultEndDate = rentContractEndDateList.get(0);
				}
				Date date = null;
				if(resultEndDate != null){
					date =  DateUtil.parse(resultEndDate); 
				}
				// 缴费周期
				int paymentperiodValue = 0;
				if(datPaymentPeriodVO != null){
					paymentperiodValue = datPaymentPeriodVO.getPaymentperiodValue() * 30;
				}
				//计划缴费日期
				String playPaymentDate = "";
			
				if (resultEndDate != null && rentContractEndDateList.size() > 0) {
					//存放缴费终期
					rentContractVO.setPaymentEnddate(resultEndDate);
					//存放计划缴费周期
					playPaymentDate = DateUtil.formatDate(new Date(date.getTime() + paymentperiodValue * DateUtil.DAY_MS));
					rentContractVO.setPlayPaymentDate(playPaymentDate);
				}else{
					//合同起始日期转换为毫秒数
					long startDate = DateUtil.parse(rentContractVO.getDatContractVO().getContractStartdate()).getTime(); 
					//计划缴费日期
					playPaymentDate = DateUtil.formatDate(new Date(startDate + paymentperiodValue * DateUtil.DAY_MS));
					rentContractVO.setPlayPaymentDate(playPaymentDate);
				}
				
				if (warning != null && warning != "") {
					Date endDate = null, nowDate;
					//缴费周期
					long period = paymentperiodValue * DateUtil.DAY_MS;
					//租费预警时间
					long warningTime = 0;
					if(parameter != null){
						warningTime = Long.parseLong(parameter.getParaValue()) * DateUtil.DAY_MS;
					}
					//现在时间
					nowDate = new Date();
					//有最后缴费时间
					if (resultEndDate != null && rentContractEndDateList.size() > 0) {
						endDate = DateUtil.parse(resultEndDate);
						if (StateComm.STATE_str0.equals(warning)) {
							//超过预警期
							if (endDate.getTime() + period - nowDate.getTime() >= warningTime) {
								it.remove();
							}
						} else {
							if (endDate.getTime() + period - nowDate.getTime() <= warningTime) {
								it.remove();
							}
						}
					}else{
						//没有最后缴费时间 合同开始日期 + 缴费周期来判断是否超过预警期
						String sDate = rentContractVO.getDatContractVO().getContractStartdate();
						Date startDate =  DateUtil.parse(sDate);
						
						if (StateComm.STATE_str0.equals(warning)) {
							if (startDate.getTime() + period - nowDate.getTime() >= warningTime) {
								it.remove();
							}
						} else {
							if (startDate.getTime() + period - nowDate.getTime() <= warningTime) {
								it.remove();
							}
						}
					}
				}
			}
		}
	}
	

	/**
	 * 租费预警信息查询
	 */
	@Override
	public Page<RentContractVO> queryRentContractVORentWarning(
			Map<String, Object> paraMap, int pageNumber, int pageSize){
		Page<RentContractVO> pagelist = selfRentDao.queryRentContractVO(paraMap, pageNumber,
				pageSize);
//		List<?> lstvo1 = pagelist.getResult();
		List<RentContractVO> lstvo2 = pagelist.getResult();
		chooseRentContractVORentWarning(lstvo2,paraMap);
		return pagelist;
	}

	/**
	 * 供应商信息查询
	 */
	public List<DatSupplierVO>  queryDatSupplierByPrvID(Map<String, Object> paraMap){
		return selfRentDao.queryDatSupplierByPrvID(paraMap);
	}
	
	/**
	 * 分页查询供应商信息
	 * @author yuefy
	 */
	public Page<List<DatSupplierVO>> queryDatSupplierByPrvID(Map<String,Object> paraMap,
			int pageNumber,int pageSize){
		return selfRentDao.queryDatSupplierByPrvID(paraMap, pageNumber, pageSize);
	}
	
	/**
	 * 根据房租合同id查询房租合同对象、主合同对象、供应商对象
	 */
	@Override
	public RentContractVO queryAllOfContract(String rentcontractId) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("rentContractId", rentcontractId);
		RentContractVO rentContractVO = selfRentDao.queryRentContractById(paraMap);
		paraMap.put("supplierId", rentContractVO.getSupplierId());
		paraMap.put("datContractId", rentContractVO.getContractId());
		DatContractVO datContractVO = selfRentDao.queryDatContractById(paraMap);
		DatSupplierVO datSupplierVO = selfRentDao.queryDatSupplierById(paraMap);
		rentContractVO.setDatContractVO(datContractVO);
		rentContractVO.setDatSupplierVO(datSupplierVO);
		return rentContractVO;
	}

	/**
	 * 新增、续签合同
	 */
	@Override
	public int insertRentContract(Map<String, Object> parmMap) {
		try {
			RentContractVO rentContractVO = (RentContractVO) parmMap.get("rentContractVO");
			String rentcontractId = SysUUID.generator();
			String contractId = SysUUID.generator();
			//查询的供应商id
			
			rentContractVO.setRentcontractId(rentcontractId);
			rentContractVO.setContractId(contractId);
			rentContractVO.setChargeType(ChargeTypeComm.STATE_0);
			rentContractVO.getDatContractVO().setAuditingState(ActivityStateComm.STATE_UNCOMMITTED);
			rentContractVO.getDatContractVO().setContractId(contractId);
			rentContractVO.getDatContractVO().setContractState(ContractStateComm.STATE_0);
			rentContractVO.getDatContractVO().setContractType(ContractTypeComm.STATE_0);
			rentContractVO.getDatContractVO().setDataFrom(DataFromComm.STATE_0);
			rentContractVO.getDatContractVO().setPrvId((String)parmMap.get("prvId"));
			parmMap.put("datContractVO", rentContractVO.getDatContractVO());
			parmMap.put("rentContractVO", rentContractVO);
			
			selfRentDao.insertDatContractVO((DatContractVO)parmMap.get("datContractVO"));
			selfRentDao.insertRentContractVO((RentContractVO)parmMap.get("rentContractVO"));
			return Integer.parseInt(RESULT.SUCCESS_1);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}

	/**
	 * 修改、补录合同
	 */
	@Override
	public String updateRentContract(Map<String, Object> parmMap) {
		try {
			RentContractVO rentContractVO = (RentContractVO) parmMap.get("rentContractVO");
			rentContractVO.getDatContractVO().setContractId(rentContractVO.getContractId());
			//查询的供应商id
			rentContractVO.setChargeType(ChargeTypeComm.STATE_0);
			rentContractVO.getDatContractVO().setContractState(ContractStateComm.STATE_0);
			rentContractVO.getDatContractVO().setAuditingState(ActivityStateComm.STATE_UNCOMMITTED);
			rentContractVO.getDatContractVO().setContractType(ContractTypeComm.STATE_0);
			rentContractVO.getDatContractVO().setDataFrom(DataFromComm.STATE_0);
			rentContractVO.getDatContractVO().setPrvId((String)parmMap.get("prvId"));
			
			parmMap.put("datContractVO", rentContractVO.getDatContractVO());
			parmMap.put("rentContractVO", rentContractVO);
			
			selfRentDao.updateDatContractVO((DatContractVO)parmMap.get("datContractVO"));
			selfRentDao.updateRentContractVO((RentContractVO)parmMap.get("rentContractVO"));
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}

	/**
	 * 补录页面提交审批
	 */
	@Override
	public int updateCommit(List<Map<String,Object>> list, UserLoginInfo user) {
		
		Map<String,Object> maps = new HashMap<>();
		for(Map<String,Object> map:list){
			RentContractVO rentContractVO=this.queryAllOfContract(map.get("id").toString());
			// 启动流程,判断状态为未提交的才可以提交
			if(rentContractVO != null && rentContractVO.getDatContractVO().getAuditingState() == ActivityStateComm.STATE_UNCOMMITTED){
				//user.getPrv_code()根据省份不同调用不同流程
				actTaskService.startProcess(ActUtils.PD_SELFRENT_AUDIT[0],user.getPrv_code(), ActUtils.PD_SELFRENT_AUDIT[1], map.get("id").toString(),ActUtils.PD_SELFRENT_AUDIT[2],map,user.getUser_loginname());
				maps.put("state", ActivityStateComm.STATE_AUDIT);
				maps.put("contractId", rentContractVO.getDatContractVO().getContractId());
				selfRentDao.updateCommit(maps);
			}
		}
		return list.size();
	}

	/**
	 * 删除合同
	 */
	@Override
	public String deleteContract(Map<String ,Object> parmMap) {
		try {
			selfRentDao.deleteRentContract(parmMap);
			selfRentDao.deleteContract(parmMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}

	/**
	 * 终止合同
	 * @param map
	 * @return
	 */
	@Override
	public String stopContract(Map<String, Object> map) {
		int result = selfRentDao.stopContract(map);
		return (result == SelfelecComm.STATUS_0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * 判断合同代码唯一性
	 * @param contractCode
	 * @param sTATE_0
	 * @return
	 */
	@Override
	public List<DatContractVO> checkContractCode(Map<String,Object> map) {
		
		return selfRentDao.checkContractCode(map);
	}

	/**
	 * 查询合同最后缴费日期
	 */
	@Override

	public List<String> queryRentContractEndDate(Map<String, Object> paraMap) {
		return selfRentDao.queryRentContractEndDate(paraMap);
	}
	
	/**
	 * 模糊查询租费合同信息 用于租费合同预警信息导出
	 */
	@Override
	public List<RentContractVO> queryRentContractVO(Map<String, Object> paraMap) {
		// 当前省份合同预警期
		// 各省份租费 电费 合同 预警期 常量值
		if(paraMap.get("exportContractWarning") != null){
			String paraCode = WarningComm.CONT_ALERT;
			paraMap.put("paraCode", paraCode);
			paraMap.put("paymentState", StateComm.STATE_1);
			SysParameterVO parameter = sysParameterDao.queryParameter(paraMap);
			String warningDate = "";
			if(parameter != null){
				warningDate = parameter.getParaValue();
			}
			paraMap.put("warningDate", warningDate);
			Date nowDateTime = new Date();
			paraMap.put("nowDateTime", nowDateTime);
			paraMap.put("paymentState", StateComm.STATE_1);
		}
		List<RentContractVO> list = selfRentDao.queryRentContractVO(paraMap);
		return list;
	}

	/**
	 * @description 合同流程审核
	 * @author yuefy
	 * @date 创建时间：2017年8月2日
	 */
	@Override
	public int rentContractCheckAudit(List<Map<String, Object>> list,
			UserLoginInfo loginInfo) {
		for(Map<String,Object> map:list){
			String taskid=map.get("taskId").toString();
			Task t=actTaskService.getTask(taskid);
			//增加判断当前业务数据是不是已经提交，需要根据ID重新查询数据库。
			if(t!=null){
				Map<String,Object> vars=new HashMap<String,Object>();
				vars.put("state",map.get("state").toString());
				if(map.get("leader")!=null&&StrUtil.isNotBlank(map.get("leader").toString())){
					vars.put("nextUserId", map.get("leader").toString());//指定下一环节审核人
				}
				String user_loginname = loginInfo.getUser_loginname();
				if(StrUtil.isNotBlank(user_loginname)){
					vars.put("currUserId", user_loginname);//指定当前审核人
				}
				actTaskService.completeByBusiness(ActUtils.PD_SELFRENT_AUDIT[1], map.get("rentcontractId").toString(),map.get("comment").toString() , ActUtils.PD_SELFRENT_AUDIT[2], vars);
				
				Task newtask = actTaskService.getTask(ActUtils.PD_SELFRENT_AUDIT[1],  map.get("rentcontractId").toString());
//				newtask为空,审核完成
				if(newtask==null){
					//如果是审核不通过，则直接修改业务数据审核状态为不通过
					if(map.get("state")!=null&&map.get("state").toString().equals(ActivityStateComm.STATE_UNAPPROVE+"")){
						Map<String,Object> maps=new HashMap<>();
						maps.put("state", ActivityStateComm.STATE_UNAPPROVE);
						maps.put("contractId", map.get("contractId").toString());
						selfRentDao.updateCommit(maps);
					}else if(map.get("state")!=null&&map.get("state").toString().equals(ActivityStateComm.STATE_NORMAL+"")){
						Map<String,Object> maps=new HashMap<>();
						maps.put("state", ActivityStateComm.STATE_NORMAL);
						maps.put("contractId", map.get("contractId").toString());
						selfRentDao.updateCommit(maps);
					}
				}
			}
		}
		return Integer.parseInt(RESULT.SUCCESS_1);
	}
	
	public List<Map<String, String>> queryAllRegionRentContractNumRedis(Map<String,Object> paraMap){
		//拼接为"崇明区：92"
		List<Map<String, String>> list=selfRentDao.selectContractNumByCondition(paraMap);
		return list;
	}
	
	
	@Override
	public Page<RentContractVO> queryRentContractVOWarning(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		// 当前省份合同预警期
		// 各省份租费 电费 合同 预警期 常量值
		SysParameterVO parameter = sysParameterDao.queryParameter(paraMap);
		String warningDate = "";
		if(parameter != null){
			warningDate = parameter.getParaValue();
		}
		paraMap.put("warningDate", warningDate);
		Date nowDateTime = new Date();
		paraMap.put("nowDateTime", nowDateTime);
		paraMap.put("paymentState", StateComm.STATE_1);
		Page<RentContractVO> pagelist = null;
		pagelist = selfRentDao.queryRentContractVO(paraMap, pageNumber, pageSize);
		return pagelist;
	}
	
	public IActTaskService getActTaskService() {
		return actTaskService;
	}

	public void setActTaskService(IActTaskService actTaskService) {
		this.actTaskService = actTaskService;
	}

	public ISelfRentDao getSelfRentDao() {
		return selfRentDao;
	}

	public void setSelfRentDao(ISelfRentDao selfRentDao) {
		this.selfRentDao = selfRentDao;
	}

	public ISysParameterDao getSysParameterDao() {
		return sysParameterDao;
	}

	public void setSysParameterDao(ISysParameterDao sysParameterDao) {
		this.sysParameterDao = sysParameterDao;
	}

	public IDatPaymentPeriodDao getDatPaymentPeriodDao() {
		return datPaymentPeriodDao;
	}

	public void setDatPaymentPeriodDao(IDatPaymentPeriodDao datPaymentPeriodDao) {
		this.datPaymentPeriodDao = datPaymentPeriodDao;
	}


}
