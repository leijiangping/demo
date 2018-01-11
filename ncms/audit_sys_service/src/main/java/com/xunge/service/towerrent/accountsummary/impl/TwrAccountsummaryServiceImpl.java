package com.xunge.service.towerrent.accountsummary.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;

import com.google.common.collect.Lists;
import com.xunge.comm.StateComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.DateUtil;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.towerrent.accountsummary.ITwrAccountsummaryStateVODao;
import com.xunge.dao.towerrent.accountsummary.ITwrAccountsummaryVODao;
import com.xunge.dao.twrrent.settlement.ITowerBillbalanceDao;
import com.xunge.model.activity.Act;
import com.xunge.model.towerrent.accountsummary.TwrAccountsummaryStateVO;
import com.xunge.model.towerrent.accountsummary.TwrAccountsummaryVO;
import com.xunge.model.towerrent.settlement.TowerBillbalanceVO;
import com.xunge.service.activity.impl.ActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.towerrent.accountsummary.ITwrAccountsummaryService;

public class TwrAccountsummaryServiceImpl implements ITwrAccountsummaryService {

	private ITowerBillbalanceDao towerBillbalanceDao;

	private ITwrAccountsummaryStateVODao twrAccountsummaryStateVODao;

	private ITwrAccountsummaryVODao twrAccountsummaryVODao;

	private ActTaskService actTaskService;

	public ActTaskService getActTaskService() {
		return actTaskService;
	}

	public void setActTaskService(ActTaskService actTaskService) {
		this.actTaskService = actTaskService;
	}

	public ITowerBillbalanceDao getTowerBillbalanceDao() {
		return towerBillbalanceDao;
	}

	public void setTowerBillbalanceDao(ITowerBillbalanceDao towerBillbalanceDao) {
		this.towerBillbalanceDao = towerBillbalanceDao;
	}

	public ITwrAccountsummaryStateVODao getTwrAccountsummaryStateVODao() {
		return twrAccountsummaryStateVODao;
	}

	public void setTwrAccountsummaryStateVODao(
			ITwrAccountsummaryStateVODao twrAccountsummaryStateVODao) {
		this.twrAccountsummaryStateVODao = twrAccountsummaryStateVODao;
	}

	public ITwrAccountsummaryVODao getTwrAccountsummaryVODao() {
		return twrAccountsummaryVODao;
	}

	public void setTwrAccountsummaryVODao(
			ITwrAccountsummaryVODao twrAccountsummaryVODao) {
		this.twrAccountsummaryVODao = twrAccountsummaryVODao;
	}

	@Override
	public Map<String, Object> validateAccountsummaryStateExists(
			Map<String, Object> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<Map<String, Object>> accountsummaryMapList = twrAccountsummaryStateVODao
				.queryTwrAccountsummarStateyMapListByCondition(params);
		if (accountsummaryMapList != null && accountsummaryMapList.size() > 0) {
			resultMap.put("success", false);
			resultMap.put("msg", PromptMessageComm.BILL_GENERATATED_FAILE);
		} else {
			resultMap.put("success", true);
		}
		return resultMap;
	}

	@Override
	public int insertAccountsummary(Map<String, Object> params) {
		int result = 0;
		// 1.查询符合条件的塔维账单
		Map<String, Object> queryBillBalanceParams = new HashMap<>();
		//queryBillBalanceParams.put("operatorRegId", params.get("regId"));
		// accountMonth
		queryBillBalanceParams.put("accountPeroid", params.get("yearmonth"));
		// data_type
		queryBillBalanceParams.put("dataType", params.get("dataType"));
		// state
		queryBillBalanceParams.put("state", params.get("state"));
		// userId
		queryBillBalanceParams.put("userId", params.get("userId"));

		List<TowerBillbalanceVO> towerBillBalanceVoList = towerBillbalanceDao
				.queryTowerBillbalance(queryBillBalanceParams);

		// 确认金额
		BigDecimal confirmAmount = new BigDecimal(SelfelecComm.NUMBER_FORMAT);
		// 调整金额
		BigDecimal adjustAmount = new BigDecimal(SelfelecComm.NUMBER_FORMAT);
		// 调整后金额
		BigDecimal adjustedAmount = new BigDecimal(SelfelecComm.NUMBER_FORMAT);
		// 结算金额
		BigDecimal settlementAmount = new BigDecimal(SelfelecComm.NUMBER_FORMAT);
		// 服务质量退款
		BigDecimal guaranteeAmount = new BigDecimal(SelfelecComm.NUMBER_FORMAT);
		// 合计金额(不含税)
		BigDecimal totalAmount = new BigDecimal(SelfelecComm.NUMBER_FORMAT);
		// 合计金额(含税)
		BigDecimal totalAmountTax = null;

		for (TowerBillbalanceVO towerBillbalanceVO : towerBillBalanceVoList) {

			BigDecimal adjustmentFee = towerBillbalanceVO.getAdjustmentFee();
			if (adjustmentFee.compareTo(new BigDecimal(SelfelecComm.NUMBER_FORMAT)) == StateComm.STATE_0) {
				// 出账费用 ==确认金额
				confirmAmount = confirmAmount.add(towerBillbalanceVO
						.getTotalAmountMonthOut());// total_amount_month_out
			} else {
				// 调整金额==调整金额
				// 调整后金额==调整后金额
				adjustAmount = adjustAmount.add(towerBillbalanceVO
						.getAdjustmentFee());
				adjustedAmount = adjustedAmount.add(towerBillbalanceVO
						.getAfterAdjustmentFee());
			}
		}

		// 结算金额
		settlementAmount = settlementAmount.add(confirmAmount);
		settlementAmount = settlementAmount.add(adjustedAmount);
		// 服务质量费用
		guaranteeAmount = guaranteeAmount.add(new BigDecimal(params.get(
				"guaranteeAmount").toString()));// 此处根据前台传入的指标扣款赋值
		// 合计金额不含税
		totalAmount = totalAmount.add(confirmAmount);
		totalAmount = totalAmount.add(adjustedAmount);
		totalAmount = totalAmount.subtract(guaranteeAmount);//合计（不含税）=结算金额-服务费用退款
		// 合计金额含税
		totalAmountTax = new BigDecimal(totalAmount.multiply(
				BigDecimal.valueOf(SelfelecComm.NUMBER_FORMAT_1_06)).toString());

		// 2.插入费用汇总单
		TwrAccountsummaryVO twrAccountsummaryVO = new TwrAccountsummaryVO();
		twrAccountsummaryVO.setYearmonth(params.get("yearmonth").toString());
		twrAccountsummaryVO.setRegId(params.get("regId").toString());
		twrAccountsummaryVO.setSumAmountType(params.get("sumAmountType")
				.toString());
		twrAccountsummaryVO.setSumTowerAmount(confirmAmount);// 确认金额
		twrAccountsummaryVO.setSumDiffAmount(adjustedAmount);// 调整后金额
		twrAccountsummaryVO.setSumCalcAmount(settlementAmount);// 结算金额
		twrAccountsummaryVO.setSumHistAmount(BigDecimal.valueOf(0));// 历史追溯费用
		twrAccountsummaryVO.setDebitType(params.get("debitType").toString());
		twrAccountsummaryVO.setSumBackAmount(guaranteeAmount);
		twrAccountsummaryVO.setSumTotalAmount(totalAmount);// 结算金额
		twrAccountsummaryVO.setSumTotalAmountTax(totalAmountTax);// 结算金额含税
		twrAccountsummaryVO.setState(ActivityStateComm.STATE_UNCOMMITTED);// 未提交
		twrAccountsummaryVO.setCreateUserId(params.get("createUserId")
				.toString());
		twrAccountsummaryVO.setCreateTime(DateUtil.date());
		twrAccountsummaryVO.setUpdateUserId(params.get("createUserId")
				.toString());
		twrAccountsummaryVO.setUpdateTime(DateUtil.date());
		twrAccountsummaryVO.setSumAdjustmentAmount(adjustAmount);// 调整金额
		String accountsummaryId = SysUUID.generator();
		twrAccountsummaryVO.setAccountsummaryId(accountsummaryId);
		result = twrAccountsummaryVODao.insertSelective(twrAccountsummaryVO);
		// 3.插入费用汇总状态信息
		if (result > 0) {
			TwrAccountsummaryStateVO twrAccountsummaryStateVO = new TwrAccountsummaryStateVO();
			twrAccountsummaryStateVO.setYearmonth(twrAccountsummaryVO
					.getYearmonth());
			twrAccountsummaryStateVO.setAreaid(Integer
					.parseInt(twrAccountsummaryVO.getRegId()));
			twrAccountsummaryStateVO.setFeetype(params.get("sumAmountType")
					.toString());
			twrAccountsummaryStateVO.setAccountsumid(twrAccountsummaryVO
					.getAccountsummaryId());
			twrAccountsummaryStateVO.setCreateuser(Integer.parseInt(params.get(
					"createUserId").toString()));
			twrAccountsummaryStateVO.setCreatetime(DateUtil.date());
			twrAccountsummaryStateVO.setUpdateuser(Integer.parseInt(params.get(
					"createUserId").toString()));
			twrAccountsummaryStateVO.setUpdatetime(DateUtil.date());
			twrAccountsummaryStateVODao
					.insertSelective(twrAccountsummaryStateVO);
		}

		return result;
	}

	@Override
	public int deleteAccountsummary(Map<String, Object> params) {
		int result = 1;
		twrAccountsummaryStateVODao.deleteTwrAccountsummaryByCondition(params);
		result = twrAccountsummaryVODao
				.deleteTwrAccountsummaryByCondition(params);
		return result;
	}

	@Override
	public int updateAccountsummary(TwrAccountsummaryVO record) {
		return twrAccountsummaryVODao.updateByPrimaryKeySelective(record);
	}

	@Override
	public Page<List<Map<String, Object>>> queryTwrTwrAccountsummaryVOPageByCondtion(
			Map<String, Object> paramMap, int pageNumber, int pageSize) {
		paramMap.put("pageNumber", pageNumber);
		paramMap.put("pageSize", pageSize);
		return twrAccountsummaryVODao.queryTwrAccountsummaryMapPage(paramMap);
	}

	public int updateAuditAndStartFlow1(List<String> list, UserLoginInfo user) {
		TwrAccountsummaryVO twrAccountsummaryVO = new TwrAccountsummaryVO();
		for (String id : list) {
			// 启动流程,判断状态为未提交的才可以提交
//			actTaskService.startProcess(ActUtils.PD_ACCOUNTSUMMARY_INFO[0],
//					ActUtils.PD_ACCOUNTSUMMARY_INFO[1], id,
//					ActUtils.PD_ACCOUNTSUMMARY_INFO[2], null,
//					user.getUser_loginname());
			 
//			actTaskService.startProcess(ActUtils.PD_ACCOUNTSUMMARY_INFO[0],user.getPrv_code(), ActUtils.PD_ACCOUNTSUMMARY_INFO[1], id,ActUtils.PD_ACCOUNTSUMMARY_INFO[2],map,user.getUser_loginname());
			twrAccountsummaryVO.setAccountsummaryId(id);
			twrAccountsummaryVO.setState(ActivityStateComm.STATE_AUDIT);// 审核中
			twrAccountsummaryVODao
					.updateByPrimaryKeySelective(twrAccountsummaryVO);
		}
		return list.size();
	}

	@Override
	public int selectSubmitedAccountsummaryCountByCondition(
			Map<String, Object> params) {
		return twrAccountsummaryVODao
				.selectSubmitedAccountsummaryCountByCondition(params);
	}

	@Override
	public Map<String, Object> validateTwrAccountsummaryStatus(
			Map<String, Object> params) {
		Map<String, Object> validateResultMap = new HashMap<>();
		validateResultMap.put("success", true);
		validateResultMap.put("msg", PromptMessageComm.BILL_VERIFY_SECCUSS);
		String op = (String) params.get("op");
		if (op != null && !"".equals(op)) {
			List<Map<String, Object>> twrAccountsummaryMapList = twrAccountsummaryVODao
					.queryTwrAccountsummeryMapListByCondition1(params);
			if (twrAccountsummaryMapList != null
					&& twrAccountsummaryMapList.size() > 0) {
				for (Map<String, Object> obj : twrAccountsummaryMapList) {
					if(obj.get("state") != null){
						String state = obj.get("state").toString();
						if ("del".equals(op)) {
							if ((ActivityStateComm.STATE_AUDIT+"").equals(state) || (ActivityStateComm.STATE_NORMAL+"").equals(state)) {
								validateResultMap.put("success", false);
								validateResultMap.put("msg",PromptMessageComm.BILL_VERIFY_FAILE+PromptMessageComm.BILL_REQUEST_ERROR);
							}
						}
					}
				}
			} else {
				validateResultMap.put("success", false);
				validateResultMap.put("msg", PromptMessageComm.BILL_VERIFY_FAILE+PromptMessageComm.BILL_SELECT_FAILED);
			}
		} else {
			validateResultMap.put("success", false);
			validateResultMap.put("msg", PromptMessageComm.BILL_VERIFY_FAILE+PromptMessageComm.PARAMETER_ERROR);
		}
		return validateResultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<TwrAccountsummaryVO> queryPageTwrAccountsummary(Map<String, Object> params) {
		Page<TwrAccountsummaryVO> page = twrAccountsummaryVODao.queryPageAccountsummery(params);
		if(params.get("ids") != null){
			List<TwrAccountsummaryVO> list=Lists.newArrayList();
			for(TwrAccountsummaryVO t: page.getResult()){
				for(Act actTemp:(List<Act>)params.get("ids")){
					if(t.getAccountsummaryId() != null && t.getAccountsummaryId().equals(actTemp.getBusinessId())){
						t.setAct(actTemp);
						list.add(t);
					}
				}
			}
			page.setResult(list);
		}
		return page;
	}

	@Override
	public int updateAuditAndStartFlow(List<Map<String, Object>> ids,
			UserLoginInfo user) {
		for(Map<String,Object> map: ids){
			TwrAccountsummaryVO twrAccountsummaryVO= this.selectByPrimaryKey(map.get("id").toString());
			// 启动流程,判断状态为未提交的才可以提交
			if(twrAccountsummaryVO !=null && (twrAccountsummaryVO.getState() == ActivityStateComm.STATE_UNCOMMITTED || twrAccountsummaryVO.getState() == ActivityStateComm.STATE_UNAPPROVE)){
				//user.getPrv_code()根据省份不同调用不同流程
				actTaskService.startProcess(ActUtils.PD_ACCOUNTSUMMARY_INFO[0],user.getPrv_code(), ActUtils.PD_ACCOUNTSUMMARY_INFO[1], map.get("id").toString(),ActUtils.PD_ACCOUNTSUMMARY_INFO[2],map,user.getUser_loginname());
				twrAccountsummaryVO.setState(ActivityStateComm.STATE_AUDIT);// 审核中
				twrAccountsummaryVODao
						.updateByPrimaryKeySelective(twrAccountsummaryVO);
			}
		}
		return ids.size();
//		TwrAccountsummaryVO twrAccountsummaryVO = new TwrAccountsummaryVO();
//		for (String id : list) {
//			// 启动流程,判断状态为未提交的才可以提交
////			actTaskService.startProcess(ActUtils.PD_ACCOUNTSUMMARY_INFO[0],
////					ActUtils.PD_ACCOUNTSUMMARY_INFO[1], id,
////					ActUtils.PD_ACCOUNTSUMMARY_INFO[2], null,
////					user.getUser_loginname());
//			 
////			actTaskService.startProcess(ActUtils.PD_ACCOUNTSUMMARY_INFO[0],user.getPrv_code(), ActUtils.PD_ACCOUNTSUMMARY_INFO[1], id,ActUtils.PD_ACCOUNTSUMMARY_INFO[2],map,user.getUser_loginname());
//			twrAccountsummaryVO.setAccountsummaryId(id);
//			twrAccountsummaryVO.setState(ActivityStateComm.STATE_AUDIT);// 审核中
//			twrAccountsummaryVODao
//					.updateByPrimaryKeySelective(twrAccountsummaryVO);
//		}
	}

	@Override
	public TwrAccountsummaryVO selectByPrimaryKey(String accountsummaryId) {
		return twrAccountsummaryVODao.selectByPrimaryKey(accountsummaryId);
	}
	
	@Override
	public int saveCheckInfo(List<Map<String,Object>> list,UserLoginInfo user) {
		for(Map<String,Object> map : list){
			String taskid = map.get("taskId").toString();
			Task t=actTaskService.getTask(taskid);
			//增加判断当前业务数据是不是已经提交，需要根据ID重新查询数据库。
			if(t!=null){
				
				Map<String,Object> vars=new HashMap<String,Object>();
				vars.put("state", map.get("auditState").toString());
				if(map.get("nextAuditUserId")!=null&&StrUtil.isNotBlank(map.get("nextAuditUserId").toString())){
					vars.put("nextUserId", map.get("nextAuditUserId").toString());//指定下一环节审核人
				}
	
				String user_loginname = user.getUser_loginname();
				if(StrUtil.isNotBlank(user_loginname)){
					vars.put("currUserId", user_loginname);//指定当前审核人
				}
				actTaskService.completeByBusiness(ActUtils.PD_ACCOUNTSUMMARY_INFO[1], map.get("accountId").toString(),map.get("auditNote").toString(), ActUtils.PD_ACCOUNTSUMMARY_INFO[2], vars);
				Task newtask = actTaskService.getTask(ActUtils.PD_ACCOUNTSUMMARY_INFO[1], map.get("accountId").toString());
				
				//newtask为空,修改审核状态审核完成
				if(newtask == null){
					//如果是审核不通过，则直接修改业务数据审核状态为不通过
					TwrAccountsummaryVO bean = new TwrAccountsummaryVO();
					bean.setAccountsummaryId(map.get("accountId").toString());
					bean.setUpdateTime(new Date());
					bean.setUpdateUserId(user.getUser_id());
					
					if(map.get("auditState") != null && map.get("auditState").toString().equals(ActivityStateComm.STATE_UNAPPROVE)){
						bean.setState(ActivityStateComm.STATE_UNAPPROVE);
					} else if (map.get("auditState") != null && map.get("auditState").toString().equals(ActivityStateComm.STATE_NORMAL)){
						bean.setState(ActivityStateComm.STATE_NORMAL);
					}
					twrAccountsummaryVODao.updateByPrimaryKey(bean);
				}
			}
		}
		return list.size();
	}

	@Override
	public TwrAccountsummaryVO selectByAccountId(String accountsummaryId) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("accountId", accountsummaryId);
		params.put("state", StateComm.STATE_str9);
		return twrAccountsummaryVODao.selectByAccountId(params);
	}

}
