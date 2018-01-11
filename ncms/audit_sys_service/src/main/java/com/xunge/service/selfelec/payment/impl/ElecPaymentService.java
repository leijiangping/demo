package com.xunge.service.selfelec.payment.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xunge.comm.StateComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.DateTime;
import com.xunge.core.util.DateUtil;
import com.xunge.core.util.PropertiesLoader;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.basedata.DatAttachmentMapper;
import com.xunge.dao.basedata.DatPaymentperiodMapper;
import com.xunge.dao.selfelec.EleBillaccountMapper;
import com.xunge.dao.selfelec.EleBillaccountPaymentdetailMapper;
import com.xunge.dao.selfelec.ElePaymentdetailMapper;
import com.xunge.dao.selfelec.VDatElectricmeterExpMapper;
import com.xunge.dao.selfelec.VEleBillaccountInfoMapper;
import com.xunge.dao.selfelec.VEleBillaccountPaymentInfoMapper;
import com.xunge.dao.selfelec.VEleBillaccountcontractMapper;
import com.xunge.dao.selfelec.VEleContractMapper;
import com.xunge.dao.selfelec.elecaudit.IElePaymentBenchmarkDao;
import com.xunge.model.activity.Act;
import com.xunge.model.basedata.DatAttachment;
import com.xunge.model.basedata.DatAttachmentExample;
import com.xunge.model.basedata.DatPaymentperiod;
import com.xunge.model.selfelec.EleBillaccount;
import com.xunge.model.selfelec.EleBillaccountPaymentdetail;
import com.xunge.model.selfelec.ElePaymentBenchmark;
import com.xunge.model.selfelec.ElePaymentdetail;
import com.xunge.model.selfelec.VDatElectricmeter;
import com.xunge.model.selfelec.VEleBillaccountInfo;
import com.xunge.model.selfelec.VEleBillaccountInfoExample;
import com.xunge.model.selfelec.VEleBillaccountPaymentInfo;
import com.xunge.model.selfelec.VEleBillaccountPaymentInfoExample;
import com.xunge.model.selfelec.VEleBillaccountcontract;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.selfelec.payment.IElecPaymentService;

/** 
* @author yangju E-mail: 
* @version 创建时间：2017年7月10日 下午4:39:35 
* 类说明 
*/
@Service
public class ElecPaymentService implements IElecPaymentService {

	@Resource
	private VEleBillaccountInfoMapper vEleBillaccountInfoMapper;
	
	@Resource
	private VEleBillaccountcontractMapper vEleBillaccountcontractMapper;
	
	@Resource
	private VDatElectricmeterExpMapper vDatElectricmeterExpMapper;
	
	@Resource 
	VEleContractMapper vEleContractMapper;
	
	@Resource
	private ElePaymentdetailMapper elePaymentdetailMapper;
	
	@Resource
	private EleBillaccountMapper eleBillaccountMapper;
	
	@Resource
	private EleBillaccountPaymentdetailMapper eleBillaccountPaymentdetailMapper;
	
	@Resource
	private VEleBillaccountPaymentInfoMapper vEleBillaccountPaymentInfoMapper;
	
	@Resource
	private DatAttachmentMapper datAttachmentMapper;
	
	@Autowired
	private IActTaskService actTaskService;
	@Autowired
	private IElePaymentBenchmarkDao elePaymentBenchmarkDao;
	@Resource
	private DatPaymentperiodMapper datPaymentperiodMapper;
	
	@Override
	public int saveOrUpdateElePaymentdetail(ElePaymentdetail payment) {
		int result = Integer.parseInt(SelfelecComm.NUMBER_0);
		if(payment!=null){
			if(StringUtils.isNoneBlank(payment.getPaymentdetailId())){
				result = elePaymentdetailMapper.updateByPrimaryKeySelective(payment);
			}else{
				payment.setPaymentdetailId(UUID.randomUUID().toString().replace("-", ""));
				result = elePaymentdetailMapper.insertSelective(payment);
			}
		}
		return result;
	}
	
	@Override
	public int saveOrUpdateEleBillaccountPaymentdetail(EleBillaccountPaymentdetail payment) {
		int result = Integer.parseInt(SelfelecComm.NUMBER_0);
		if(payment!=null){
			if(StringUtils.isNotBlank(payment.getBillaccountpaymentdetailId())){
				//更新
				result = eleBillaccountPaymentdetailMapper.updateByPrimaryKeySelective(payment);
			}else{//新增
			/*	//判断缴费周期是否重复缴费
				List<EleBillaccountPaymentdetail> payMentVOlst = eleBillaccountPaymentdetailMapper.queryPaymentByBillAccountId(payment.getBillaccountId());
				if(payMentVOlst != null){
					for(int i = 0;i < payMentVOlst.size();i++){
						Date oldStartTime = payMentVOlst.get(i).getBillamountStartdate();
						Date oldEndTime = payMentVOlst.get(i).getBillamountEnddate();
						if(payment.getBillamountStartdate().getTime() > oldEndTime.getTime() || payment.getBillamountEnddate().getTime() < oldStartTime.getTime()){
							continue;
						}else{
							//throw new BusinessException("不可重复缴费！");
							result = -1;
							return result;
						}
					}
				}*/
				
				
				payment.setBillaccountpaymentdetailId(UUID.randomUUID().toString().replace("-", ""));
				result = eleBillaccountPaymentdetailMapper.insertSelective(payment);
			}
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("billAccountId", payment.getBillaccountId());
			//0 待缴费 1 已缴费
			paramMap.put("payMentState", SelfelecComm.NUMBER_1);
			if (result != Integer.parseInt(SelfelecComm.NUMBER_0)){//插入或修改成功
				eleBillaccountPaymentdetailMapper.updatePaymentState(paramMap);
			}
			updateBillaccountDate(payment);
		}
		return result;
	}
	/**
	 * 修改报账点计划缴费日期
	 */
	private void updateBillaccountDate(EleBillaccountPaymentdetail payment){
		//根据billaccountid查询报账点计划缴费日期
		String paymentperiodId=vEleBillaccountcontractMapper.queryPaymentperiodIdByBillaccountId(payment.getBillaccountId());
		if(StrUtil.isNotBlank(paymentperiodId)){
			//查询合同缴费周期
			DatPaymentperiod datPaymentperiod=	datPaymentperiodMapper.selectByPrimaryKey(paymentperiodId);
			Long long1 = datPaymentperiod.getPaymentperiodValue();
//			DateTime date = DateUtil.date(long1*30*24*60*60*1000);
//			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//            Date date = new Date(long1*30*24*60*60*1000);  
			Map<String,Object> paramMaps = new HashMap<String,Object>();
			paramMaps.put("billaccountId", payment.getBillaccountId());
			List<EleBillaccount> eleBillaccount = eleBillaccountMapper.queryEleBillaccountById(paramMaps);
			Date planDate = eleBillaccount.get(Integer.parseInt(SelfelecComm.NUMBER_0)).getPlanDate();
			//本次缴费期终+该报账点关联合同的缴费周期
			DateTime offsiteMonth = DateUtil.offsiteMonth(payment.getBillamountEnddate(),(int) (long1*SelfelecComm.NUMBER_1));
//			Date newDate=date.toDate();
			//如果当前计划缴费日期为空，则进行更新；
			if(eleBillaccount.get(Integer.parseInt(SelfelecComm.NUMBER_0)).getPlanDate()==null){
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("billAccountId", payment.getBillaccountId());
				paramMap.put("planDate", offsiteMonth);
				eleBillaccountMapper.updatePlanDateById(paramMap);
			}
			//如果该日期>当前计划缴费日期，则进行更新；
			else if(DateUtil.diff(planDate,offsiteMonth , SelfelecComm.NUMBER_60)>Integer.parseInt(SelfelecComm.NUMBER_0)){
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("billaccountId", payment.getBillaccountId());
				paramMap.put("planDate",offsiteMonth);
				eleBillaccountMapper.updatePlanDateById(paramMap);
			}
			//如果该日期<=当前计划缴费日期，则不进行更新。


		}
	}
	@Override
	public VEleBillaccountcontract getVeleBillaccountContract(String billaccountId) {
		VEleBillaccountcontract result = null;
		List<VEleBillaccountcontract> list = vDatElectricmeterExpMapper.getContractBybillaccountId(billaccountId);
		if(list!=null && list.size()>Integer.parseInt(SelfelecComm.NUMBER_0)){
			result = list.get(Integer.parseInt(SelfelecComm.NUMBER_0));
		}
		return result;
	}

	@Override
	public List<VDatElectricmeter> getElectricmeterByBillaccountId(String billaccountId) {
		return vDatElectricmeterExpMapper.getVDatElectricmeterBybillaccountId(billaccountId);
	}
	
	@Override
	public List<VDatElectricmeter> getElectricmeterByBillaccountIdShow(String billaccountId) {
		return vDatElectricmeterExpMapper.getVDatElectricmeterBybillaccountIdShow(billaccountId);
	}

	@Override
	public PageInfo<VEleBillaccountPaymentInfo> queryVEleBillaccountPaymentInfo(UserLoginInfo loginInfo , VEleBillaccountPaymentInfo param,
			int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber , pageSize);
		
		VEleBillaccountPaymentInfoExample example = new VEleBillaccountPaymentInfoExample();
		
		if(param != null){
			com.xunge.model.selfelec.VEleBillaccountPaymentInfoExample.Criteria criteria =  example.createCriteria();
			
			if(StringUtils.isNoneBlank(param.getBillaccountCode())){
				criteria.andBillaccountCodeLike("%"+param.getBillaccountCode()+"%");
			}
			
			if(StringUtils.isNoneBlank(param.getBillaccountName())){
				criteria.andBillaccountNameLike("%"+param.getBillaccountName()+"%");
			}
			
			if(StringUtils.isNoneBlank(param.getRegId())){
				criteria.andRegIdEqualTo(param.getRegId());
			}
			
			if(StringUtils.isNoneBlank(param.getPregId())){
				criteria.andPregIdEqualTo(param.getPregId());
			}
			
			if(param.getSubmitState()!=null){
				criteria.andSubmitStateEqualTo(param.getSubmitState());
			}
			
			if(param.getAuditingState()!=null){
				criteria.andAuditingStateEqualTo(param.getAuditingState());
			}
		}
		
		if(loginInfo!=null){
			com.xunge.model.selfelec.VEleBillaccountPaymentInfoExample.Criteria  criteria1 = example.createCriteria();
			com.xunge.model.selfelec.VEleBillaccountPaymentInfoExample.Criteria  criteria2 = example.createCriteria();
			
			if(loginInfo.getPreg_ids()!=null && loginInfo.getPreg_ids().size()>Integer.parseInt(SelfelecComm.NUMBER_0)){
				criteria1.andPregIdIn(loginInfo.getPreg_ids());
			}
			if(loginInfo.getReg_ids()!=null && loginInfo.getReg_ids().size()>Integer.parseInt(SelfelecComm.NUMBER_0)){
				criteria2.andRegIdIn(loginInfo.getReg_ids());
			}
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("example", example);
		map.put("regIds", loginInfo.getReg_ids());
		List<VEleBillaccountPaymentInfo> datas = null;
		if(loginInfo.getReg_ids() != null && loginInfo.getReg_ids().size() > Integer.parseInt(SelfelecComm.NUMBER_0)){
			datas = vEleBillaccountPaymentInfoMapper.selectByExample(map);
		}
		return new PageInfo<VEleBillaccountPaymentInfo>(datas);
	}

	@Override
	public List<VEleBillaccountInfo> getVEleBillaccountInfoByBPaymentdetailId(String billaccountpaymentdetailId) {
		VEleBillaccountInfoExample example = new VEleBillaccountInfoExample();
		
		if(StringUtils.isNoneBlank(billaccountpaymentdetailId)){
			com.xunge.model.selfelec.VEleBillaccountInfoExample.Criteria criteria = example.createCriteria();
			
			criteria.andBillaccountpaymentdetailIdEqualTo(billaccountpaymentdetailId);
		}
		
		return vEleBillaccountInfoMapper.selectByExample(example);
	}

	@Override
	public int batchreviewEleBillaccountPaymentdetail(List<EleBillaccountPaymentdetail> ids, String userId,
			Integer state) {
		int result = Integer.parseInt(SelfelecComm.NUMBER_0);
		
		for(EleBillaccountPaymentdetail detail : ids){
			detail.setAuditingState(state);
			detail.setAuditingUserId(userId);
			detail.setAuditingDate(new Date());
			result += eleBillaccountPaymentdetailMapper.updateByPrimaryKeySelective(detail);
		}
		
		return result;
	}

	@Override
	@Transactional(propagation=Propagation.NEVER)
	public void getBenchmark(Map<String, Object> param) {
		vDatElectricmeterExpMapper.getBenchmark(param);
	}
	
	@Override
	public void insertBenchmark(Map<String, Object> param) {  
		String totalDegreeActualStr=(String) param.get("totalDegreeActual");
		String billaccountpaymentdetailId=(String) param.get("billaccountpaymentdetailId");
		Double totalDegreeActual = Double.parseDouble(totalDegreeActualStr);
		vDatElectricmeterExpMapper.getBenchmark(param);
		Map<String, Object> resultMap = param;
		Object errCodeObj = resultMap.get("errCode");
		if (errCodeObj != null && (int) errCodeObj == Integer.parseInt(SelfelecComm.NUMBER_0)) {
			NumberFormat nFormat = NumberFormat.getNumberInstance();
			nFormat.setMaximumFractionDigits(SelfelecComm.NUMBER_2);// 设置小数点后面位数为
			// 智能电表标杆
			BigDecimal electricmeterBenchmark = (BigDecimal) resultMap.get("electricmeterBenchmark");
			// 动环负载标杆
			BigDecimal powerloadBenchmark = (BigDecimal) resultMap.get("powerloadBenchmark");
			// 额定功率标杆
			BigDecimal powerratingBenchmark = (BigDecimal) resultMap.get("powerratingBenchmark");
			// 环比历史电量标杆
			BigDecimal hisNowBenchmark = (BigDecimal) resultMap.get("hisNowBenchmark");
			// 同比历史电量标杆
			BigDecimal hisLastBenchmark = (BigDecimal) resultMap.get("hisLastBenchmark");
			List<ElePaymentBenchmark> list=new ArrayList<>();
			// 同比历史电量标杆
			 if (hisLastBenchmark != null) {
				 ElePaymentBenchmark paymentBenchmark=new ElePaymentBenchmark();
				 paymentBenchmark.setPaybenchmarkId(SysUUID.generator());
				 if (totalDegreeActual > hisLastBenchmark.doubleValue()) {
					 paymentBenchmark.setBillaccountpaymentdetailId(billaccountpaymentdetailId);
					 paymentBenchmark.setPaybenchmarkType(Integer.parseInt(SelfelecComm.NUMBER_0));
					 paymentBenchmark.setPaybenchmarkValue((totalDegreeActual - hisLastBenchmark.doubleValue()));
					 paymentBenchmark.setPaybenchmarkOverflow(SelfelecComm.NUMBER_1);
					 paymentBenchmark.setPaybenchmarkRate(((totalDegreeActual - hisLastBenchmark.doubleValue()) * SelfelecComm.NUMBER_100
							/ hisLastBenchmark.doubleValue()));
				} else {
					 paymentBenchmark.setBillaccountpaymentdetailId(billaccountpaymentdetailId);
					 paymentBenchmark.setPaybenchmarkType(Integer.parseInt(SelfelecComm.NUMBER_0));
					 paymentBenchmark.setPaybenchmarkOverflow(Integer.parseInt(SelfelecComm.NUMBER_0));
					 paymentBenchmark.setPaybenchmarkRate(Double.parseDouble(SelfelecComm.NUMBER_0));
				}
				 list.add(paymentBenchmark);
			}

			// 环比历史电量标杆
			if (hisNowBenchmark != null){
				 ElePaymentBenchmark paymentBenchmark=new ElePaymentBenchmark();
				 paymentBenchmark.setPaybenchmarkId(SysUUID.generator());
				 if (totalDegreeActual > hisNowBenchmark.doubleValue()) {
					 paymentBenchmark.setBillaccountpaymentdetailId(billaccountpaymentdetailId);
					 paymentBenchmark.setPaybenchmarkType(SelfelecComm.NUMBER_1);
					 paymentBenchmark.setPaybenchmarkValue((totalDegreeActual - hisNowBenchmark.doubleValue()));
					 paymentBenchmark.setPaybenchmarkOverflow(SelfelecComm.NUMBER_1);
					 paymentBenchmark.setPaybenchmarkRate(((totalDegreeActual - hisNowBenchmark.doubleValue()) * SelfelecComm.NUMBER_100
							/ hisNowBenchmark.doubleValue()));
				} else {
					 paymentBenchmark.setBillaccountpaymentdetailId(billaccountpaymentdetailId);
					 paymentBenchmark.setPaybenchmarkType(SelfelecComm.NUMBER_1);
					 paymentBenchmark.setPaybenchmarkOverflow(Integer.parseInt(SelfelecComm.NUMBER_0));
					 paymentBenchmark.setPaybenchmarkRate(Double.parseDouble(SelfelecComm.NUMBER_0));
				}
				 list.add(paymentBenchmark);
			}

			// 额定功率标杆
			if (powerratingBenchmark != null)  {
				 ElePaymentBenchmark paymentBenchmark=new ElePaymentBenchmark();
				 paymentBenchmark.setPaybenchmarkId(SysUUID.generator());
				 if (totalDegreeActual > powerratingBenchmark.doubleValue()) {
					 paymentBenchmark.setBillaccountpaymentdetailId(billaccountpaymentdetailId);
					 paymentBenchmark.setPaybenchmarkType(SelfelecComm.NUMBER_2);
					 paymentBenchmark.setPaybenchmarkValue((totalDegreeActual - powerratingBenchmark.doubleValue()));
					 paymentBenchmark.setPaybenchmarkOverflow(SelfelecComm.NUMBER_1);
					 paymentBenchmark.setPaybenchmarkRate(((totalDegreeActual - powerratingBenchmark.doubleValue()) * SelfelecComm.NUMBER_100
							/ powerratingBenchmark.doubleValue()));
				} else {
					 paymentBenchmark.setBillaccountpaymentdetailId(billaccountpaymentdetailId);
					 paymentBenchmark.setPaybenchmarkType(SelfelecComm.NUMBER_2);
					 paymentBenchmark.setPaybenchmarkOverflow(Integer.parseInt(SelfelecComm.NUMBER_0));
					 paymentBenchmark.setPaybenchmarkRate(Double.parseDouble(SelfelecComm.NUMBER_0));
				}
				 list.add(paymentBenchmark);
			}

			// 智能电表标杆
			 if (electricmeterBenchmark != null) {
				 ElePaymentBenchmark paymentBenchmark=new ElePaymentBenchmark();
				 paymentBenchmark.setPaybenchmarkId(SysUUID.generator());
				 if (totalDegreeActual > electricmeterBenchmark.doubleValue()) {
					 paymentBenchmark.setBillaccountpaymentdetailId(billaccountpaymentdetailId);
					 paymentBenchmark.setPaybenchmarkType(SelfelecComm.NUMBER_3);
					 paymentBenchmark.setPaybenchmarkValue((totalDegreeActual - electricmeterBenchmark.doubleValue()));
					 paymentBenchmark.setPaybenchmarkOverflow(SelfelecComm.NUMBER_1);
					 paymentBenchmark.setPaybenchmarkRate(((totalDegreeActual - electricmeterBenchmark.doubleValue()) * SelfelecComm.NUMBER_100
							/ electricmeterBenchmark.doubleValue()));
				} else {
					 paymentBenchmark.setBillaccountpaymentdetailId(billaccountpaymentdetailId);
					 paymentBenchmark.setPaybenchmarkType(SelfelecComm.NUMBER_3);
					 paymentBenchmark.setPaybenchmarkOverflow(Integer.parseInt(SelfelecComm.NUMBER_0));
					 paymentBenchmark.setPaybenchmarkRate(Double.parseDouble(SelfelecComm.NUMBER_0));
				}
				 list.add(paymentBenchmark);
			}

			// 动环负载标杆
			 if (powerloadBenchmark != null) {
				 ElePaymentBenchmark paymentBenchmark=new ElePaymentBenchmark();
				 paymentBenchmark.setPaybenchmarkId(SysUUID.generator());
				 if (totalDegreeActual > powerloadBenchmark.doubleValue()) {
					 paymentBenchmark.setBillaccountpaymentdetailId(billaccountpaymentdetailId);
					 paymentBenchmark.setPaybenchmarkType(SelfelecComm.NUMBER_3);
					 paymentBenchmark.setPaybenchmarkValue((totalDegreeActual - powerloadBenchmark.doubleValue()));
					 paymentBenchmark.setPaybenchmarkOverflow(SelfelecComm.NUMBER_1);
					 paymentBenchmark.setPaybenchmarkRate(((totalDegreeActual - powerloadBenchmark.doubleValue()) * SelfelecComm.NUMBER_100
							/ powerloadBenchmark.doubleValue()));
				} else {
					 paymentBenchmark.setBillaccountpaymentdetailId(billaccountpaymentdetailId);
					 paymentBenchmark.setPaybenchmarkType(SelfelecComm.NUMBER_4);
					 paymentBenchmark.setPaybenchmarkOverflow(Integer.parseInt(SelfelecComm.NUMBER_0));
					 paymentBenchmark.setPaybenchmarkRate(Double.parseDouble(SelfelecComm.NUMBER_0));
					}
				 list.add(paymentBenchmark);
				}
			/* Map<String, Object> resultMaps = new HashMap<String, Object>();
			 resultMaps.put("list", list);*/
			 if(list.size()>Integer.parseInt(SelfelecComm.NUMBER_0) && list!=null){
				 elePaymentBenchmarkDao.insertBenchmarkInfo(list);
			 }
			}
	}
	
	@Override
	public EleBillaccount getEleBillaccountById(String billaccountId) {
		return eleBillaccountMapper.selectByPrimaryKey(billaccountId);
	}
	/**
	 * 提出业务方法
	 * state：审核状态 、paymentState：业务表提交状态、submitState:提交状态、id：业务表主键id
	 */
	private void updateBusinessState(int state,int submitState,String id){
		 Map<String,Object> maps=new HashMap<String,Object>();
			maps.put("state",state );
			maps.put("billaccountpaymentdetailId", id);
			maps.put("submitState", submitState);
			eleBillaccountPaymentdetailMapper.updateActivityCommit(maps);
	}
	@Override
	public int updateActivityCommit(List<Map<String, Object>> list,
			UserLoginInfo loginUser) {
		for(Map<String,Object> map:list){
			EleBillaccountPaymentdetail paymentdetail = eleBillaccountPaymentdetailMapper.queryById(map.get("billaccountpaymentdetailId").toString());
			if(paymentdetail!=null&&paymentdetail.getAuditingState()==ActivityStateComm.STATE_UNCOMMITTED){
				 actTaskService.startProcess(ActUtils.EleBillaccountPaymentdetail[0],loginUser.getPrv_code(), ActUtils.EleBillaccountPaymentdetail[1], map.get("billaccountpaymentdetailId").toString(),ActUtils.EleBillaccountPaymentdetail[2],map,loginUser.getUser_loginname());
				 this.updateBusinessState(ActivityStateComm.STATE_AUDIT,StateComm.STATE_1,map.get("billaccountpaymentdetailId").toString());
			}
			
		}
		return list.size();
	}
	
	@Override
	public int updateAuditCompelet(List<Map<String, Object>> list, UserLoginInfo loginInfo) {
		// TODO Auto-generated method stub
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
				if (map.get("minBenchmark")!=null&&StrUtil.isNotBlank(map.get("minBenchmark").toString())) {
					vars.put("minBenchmark", map.get("minBenchmark").toString());// 指定最大电费单价
				}
				
				actTaskService.completeByBusiness(ActUtils.EleBillaccountPaymentdetail[1], map.get("billaccountpaymentdetailId").toString(), map.get("comment").toString(), ActUtils.EleBillaccountPaymentdetail[2], vars);
//				if(state.equals(ActivityStateComm.STATE_NORMAL)){
					Task newtask = actTaskService.getTask(ActUtils.EleBillaccountPaymentdetail[1],map.get("billaccountpaymentdetailId").toString() );
//				newtask为空,审核完成
					if(newtask==null){
						//如果是审核不通过，则直接修改业务数据审核状态为不通过
						if(map.get("state")!=null&&map.get("state").toString().equals(ActivityStateComm.STATE_UNAPPROVE+"")){
							 this.updateBusinessState(ActivityStateComm.STATE_UNAPPROVE,StateComm.STATE_0,map.get("billaccountpaymentdetailId").toString());
						}else if(map.get("state")!=null&&map.get("state").toString().equals(ActivityStateComm.STATE_NORMAL+"")){
							 this.updateBusinessState(ActivityStateComm.STATE_NORMAL,StateComm.STATE_1,map.get("billaccountpaymentdetailId").toString());
						}
						 EleBillaccountPaymentdetail ep = eleBillaccountPaymentdetailMapper.selectByPrimaryKey(map.get("billaccountpaymentdetailId").toString());
						 ep.setAuditingDate(new Date());
						 ep.setAuditingUserId(loginInfo.getUser_id());
						 eleBillaccountPaymentdetailMapper.updateByPrimaryKey(ep);
					}
			}
		}
		
		return list.size();
	}
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<VEleBillaccountPaymentInfo> queryEleBillaccountPayment(
			Map<String, Object> map, int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<VEleBillaccountPaymentInfo> datas = vEleBillaccountPaymentInfoMapper.queryEleBillaccountPayment(map);
		PageInfo<VEleBillaccountPaymentInfo> page= new PageInfo<VEleBillaccountPaymentInfo>(datas);
		
		if(map.get("idsList")!=null){
			List<VEleBillaccountPaymentInfo> list=Lists.newArrayList();
			for(VEleBillaccountPaymentInfo t:page.getList()){
				for(Act actTemp:(List<Act>)map.get("idsList")){
					if(t.getBillaccountpaymentdetailId()!=null&&t.getBillaccountpaymentdetailId().equals(actTemp.getBusinessId())){
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
	public EleBillaccountPaymentdetail getEleBillaccountDetailById(String billaccountpaymentdetailId) {
		return eleBillaccountPaymentdetailMapper.selectByPrimaryKey(billaccountpaymentdetailId);
	}

	@Override
	public int deletePaymentDetail(Map<String, Object> map) {
		return vEleBillaccountPaymentInfoMapper.deletePaymentDetail(map);
	}

	@Override
	public int queryEleBillaccountPaymentdetailByBillaccountId(EleBillaccountPaymentdetail payment) {
		List<EleBillaccountPaymentdetail> payMentVOlst = eleBillaccountPaymentdetailMapper.queryPaymentByBillAccountId(payment.getBillaccountId());
		int result=Integer.parseInt(SelfelecComm.NUMBER_0);
		if(payMentVOlst != null){
			for(int i = Integer.parseInt(SelfelecComm.NUMBER_0);i < payMentVOlst.size();i++){
				Date oldStartTime = payMentVOlst.get(i).getBillamountStartdate();
				Date oldEndTime = payMentVOlst.get(i).getBillamountEnddate();
				if(payment.getBillamountStartdate().getTime() > oldEndTime.getTime() || payment.getBillamountEnddate().getTime() < oldStartTime.getTime()){
					continue;
				}else{
					//throw new BusinessException("不可重复缴费！");
					result = StateComm.STATE__1;
					return result;
				}
			}
		}
		return result;
	}
	
	@Override
    public List<Map<String,String>> queryPaymentDetailByCondition(Map<String,Object>maps){
		/*String reg_name=(String) maps.get("regName");
		List<String> reg_names=new ArrayList<>();
		if(StrUtil.isNotBlank(reg_name)){
			if(reg_name.equals("黄浦区")){
				reg_names.add("黄浦区");
				reg_names.add("卢湾区");
				maps.put("reg_name", reg_names);
			}else if(reg_name.equals("浦东新区")){
				reg_names.add("浦东新区");
				reg_names.add("南汇区");
				maps.put("reg_name", reg_names);
			}else{
				reg_names.add(reg_name);
				maps.put("reg_name", reg_names);
			}
		}*/
		if(maps.get("prv_id")!=null){
			String regName=(String) maps.get("regName");
			List<String> reg_ids=new ArrayList<>();
			if(StrUtil.isNotBlank(regName)){
				PropertiesLoader prop = new PropertiesLoader(PromptMessageComm.PROPERTIES_LOADER);
				String reg_id=prop.getProperty(regName);
				String[] strings = reg_id.split(",");
				for (int i = Integer.parseInt(SelfelecComm.NUMBER_0); i < strings.length; i++) {
					reg_ids.add(strings[i]);
				}
			}
			maps.put("reg_ids", reg_ids);
		}
		List<Map<String, String>> selfElecPaymentDetail = new ArrayList<>();
		selfElecPaymentDetail=vEleBillaccountcontractMapper.queryPaymentDetailByCondition(maps);
		List<Map<String, String>> selfElecPaymentDetail_num = Lists.newArrayList();
		for(int i=SelfelecComm.NUMBER_1;i<=SelfelecComm.NUMBER_12;i++){
			String s="";
			if(i<SelfelecComm.NUMBER_10){
				s=SelfelecComm.NUMBER_0+i+"";
			}else{
				s=i+"";
			}
			Map<String, String> map=Maps.newHashMap();
			boolean flag=false;
			for(int j=Integer.parseInt(SelfelecComm.NUMBER_0);j<selfElecPaymentDetail.size();j++){
				if(selfElecPaymentDetail.get(j).get("name").equals(s)){
					selfElecPaymentDetail_num.add(selfElecPaymentDetail.get(j));
					flag=true;
					j=selfElecPaymentDetail.size();
				}
			}
			if(flag==false){
				map.put("name", s);
				map.put("value", SelfelecComm.NUMBER_0);
				selfElecPaymentDetail_num.add(map);
			}
		}
		
    	return selfElecPaymentDetail_num;
    }

	@Override
	public VEleBillaccountPaymentInfo queryMaxBillAccountEnded(String billaccountId) {
		return vEleBillaccountPaymentInfoMapper.queryMaxBillAccountEnded(billaccountId);
	}

	@Override
	public List<VEleBillaccountPaymentInfo> queryBillaccountPaymentInfoById(
			String billaccountpaymentdetailId) {
		Map<String,Object> map = new HashMap<>();
		map.put("billaccountpaymentdetailId", billaccountpaymentdetailId);
		return vEleBillaccountPaymentInfoMapper.selectBillamountPaymentDetails(map);
	}

	@Override
	public PageInfo<DatAttachment> selectByBusiness(DatAttachment record) {
		DatAttachmentExample example = new DatAttachmentExample();

		com.xunge.model.basedata.DatAttachmentExample.Criteria criteria1 = example
				.createCriteria();
		criteria1.andBusinessIdEqualTo(record.getBusinessId());
		criteria1.andBusinessTypeEqualTo(record.getBusinessType());

		PageInfo<DatAttachment> page = new PageInfo<DatAttachment>(
				datAttachmentMapper.selectByExample(example));
		return page;
	}

	@Override
	public String addAttach(DatAttachment record) {
		if (StringUtils.isEmpty(record.getAttachmentId())) {
			record.setAttachmentId(PromptMessageComm.ATTACHMENT_ID + System.currentTimeMillis());
		}
		String id = record.getAttachmentId();
		datAttachmentMapper.insert(record);
		return id;
	}

	@Override
	public void delAttach(String url) {
		DatAttachmentExample example = new DatAttachmentExample();
		com.xunge.model.basedata.DatAttachmentExample.Criteria criteria = example
				.createCriteria();
		criteria.andAttachmentUrlEqualTo(url);
		datAttachmentMapper.deleteByExample(example);
	}
	
	
}