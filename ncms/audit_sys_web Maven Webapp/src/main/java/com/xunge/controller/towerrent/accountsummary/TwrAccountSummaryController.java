package com.xunge.controller.towerrent.accountsummary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.StateComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.comm.tower.resourceinfo.TowweDebitTypStreComm;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.FeedBackObject;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.DateUtil;
import com.xunge.model.activity.Act;
import com.xunge.model.towerrent.punish.TwrGroupPunishVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.towerrent.accountsummary.ITwrAccountsummaryService;
import com.xunge.service.towerrent.checkmanage.ITwrPrvCheckIndexFineService;
import com.xunge.service.twrrent.punish.ITwrGroupPunishService;
import com.xunge.service.twrrent.punish.ITwrRegPunishService;
/**
 * @descript费用汇总控制类
 * @author quzl
 * @date 2017-08-01 09:02:13
 */
@Controller
@RequestMapping("/twrAccountsummary")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
public class TwrAccountSummaryController extends BaseException{
	
	@Autowired
	private ITwrAccountsummaryService twrAccuntsummaryService;
	
	@Autowired
	private IActTaskService actTaskService;
	
	@Autowired
	private ISysRoleService iSysRoleService;
	
	@Autowired
	private ITwrRegPunishService twrRegPunishService;
	
	@Autowired
	private ITwrGroupPunishService twrGroupPunishService;
	
	@Autowired
	private ITwrPrvCheckIndexFineService twrPrvCheckIndexFineService;
	
	/**
	 * 根据参数校验指定费用率欸型的费用汇总单是否已存在
	 * @param feedType 汇总费用类型  例如：  铁塔价格，机房价格
	 * @param yearmonth 账期 例如： 201707
	 * @param regId 地区ID 例如：110000  如若地区为空，则默认查询当前用户所在区域
	 * @return
	 */
	@RequestMapping(value = "/queryAccountsummaryState", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject queryAccountsummaryState(@ModelAttribute("user") UserLoginInfo loginInfo, @RequestParam Map<String, Object> params){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		
		String feedTypeStr = (String) params.get("feedTypeStr");
		if(feedTypeStr != null && !"".equals(feedTypeStr)){
			params.put("feedType", Arrays.asList(feedTypeStr.split(PromptMessageComm.COMMA_SYMBOL)));
		}
		
		//获取当前用户所属地区
		String regId = (String) params.get("regId");
		if(regId == null){
			params.put("state",StateComm.STATE_0);
			params.put("userId",loginInfo.getUser_id());
		}
		
		feedbackObj.Obj = twrAccuntsummaryService.validateAccountsummaryStateExists(params);
		feedbackObj.success = RESULT.SUCCESS_1;
		
		return feedbackObj;
	}
	
	/**
	 * 根据参数校验指定费用率欸型的费用汇总单分页
	 * @param yearmonth 账期 例如： 201707
	 * @param regId 地区ID 例如：110000  如若地区为空，则默认查询当前用户所在区域
	 * @return
	 */
	@RequestMapping(value = "/queryAccountsummaryPage", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject queryAccountsummaryPage(@RequestParam Map<String, Object> params, @ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		//获取当前用户所属地区
		String regId = (String) params.get("regId");
		if(regId == null || "".equals(regId)){
			params.put("state",StateComm.STATE_0);
			params.put("userId",loginInfo.getUser_id());
			params.put("regId", null);
		}
		
		String pageNumberStr = (String) params.get("pageNumber");
		pageNumberStr = pageNumberStr != null ? pageNumberStr : StateComm.PAGE_NUMBER_STR;
		
		String pageSizeStr = (String) params.get("pageSize");
		pageSizeStr = pageSizeStr != null ? pageSizeStr : StateComm.PAGE_SIZE_STR;
		
		Integer pageNumber = Integer.parseInt(pageNumberStr);
		Integer pageSize = Integer.parseInt(pageSizeStr);
		
		feedbackObj.Obj = twrAccuntsummaryService.queryTwrTwrAccountsummaryVOPageByCondtion(params, pageNumber, pageSize);
		feedbackObj.success = RESULT.SUCCESS_1;
		
		return feedbackObj;
	}
	
	/**
	 * 新增汇总账单
	 * @param <Pre>
	 * 			twrAccountsummaryVO实体
	 * 				param: feeTypeStr 汇总费用类型  例如：  铁塔价格，机房价格
	 * 				param: yearmonth 账期 例如： 201707
	 * 				param: regId 地区ID 例如：110000
	 * 				param: debitTypStre 扣款类型  例如：0,1,2 0-集团指标扣款 1-省指标扣款 2地市指标扣款
	 * 			</Pre>
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addAccountsummary", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject addAccountsummary(HttpServletRequest request,@ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("regId", request.getParameter("regId"));
		params.put("feeTypeStr", request.getParameter("feeTypeStr"));
		params.put("yearmonth", request.getParameter("yearmonth"));
		params.put("debitTypeStr", request.getParameter("debitTypeStr"));
		
		String yearMonth = request.getParameter("yearmonth");
		String regId = request.getParameter("regId");
		String feeTypeStr = request.getParameter("feeTypeStr");
		String debitTypeStr = request.getParameter("debitTypeStr");
		//模拟630222_测试用
		//params.put("regId", "630101");
		//regId = "630101";
		
		FeedBackObject feedBackObject = new FeedBackObject();
		
		params.put("state",StateComm.STATE_0);
		params.put("userId",loginInfo.getUser_id());
		if(feeTypeStr != null && "all".equals(feeTypeStr) ){
			//一期默认账单整体汇总
			params.put("feeType", PromptMessageComm.TWSERViCR_CHARGE);
			params.put("sumAmountType", PromptMessageComm.TWSERViCR_CHARGE);
		}else{
			//预留，后期可能会汇总费用类型
			params.put("feeType", Arrays.asList(feeTypeStr.split(",")));
			params.put("sumAmountType", Arrays.asList(feeTypeStr.split(",")));
		}
//		if(feeTypeStr != null && !"".equals(feeTypeStr)){
//			params.put("feeType", Arrays.asList(feeTypeStr.split(",")));
//		}
		
		//指标扣款
		StringBuilder debitStr = new StringBuilder();
		if(debitTypeStr != null && !PromptMessageComm.KONG_SYMBOL.equals(debitTypeStr)){
			BigDecimal guaranteeAmount = new BigDecimal(0.0000);
			String[] debits = debitTypeStr.split(PromptMessageComm.COMMA_SYMBOL);
			if(debits != null && debits.length > 0){
				for(int i=0; i < debits.length; i++){
					String debit = debits[i];
					if(TowweDebitTypStreComm.DEBITYOSTRE_0.equals(debit)){
						//集团指标扣款
						debitStr.append(PromptMessageComm.GROUP_INDT_DT);
						if(i < (debits.length -1)){
							debitStr.append(PromptMessageComm.COMMA_SYMBOL);
						}
						
						//此处查询集团指标扣款金额
						//param1: punish_year_month
						//param2: reg_id
						//pregId630222 ->630200
						List<TwrGroupPunishVO> twrGroupPunishVOList = twrGroupPunishService.queryGroupPunishByPregId(null, yearMonth, regId);
						if(twrGroupPunishVOList != null && twrGroupPunishVOList.size() > 0){
							for(TwrGroupPunishVO twrGroupPunishVO: twrGroupPunishVOList){
								guaranteeAmount = guaranteeAmount.add(twrGroupPunishVO.getPunishamount());
							}
						}
					}else if(TowweDebitTypStreComm.DEBITYOSTRE_1.equals(debit)){
						//省指标扣款
						debitStr.append(PromptMessageComm.PROVINCE_INDT_DT);
						if(i < (debits.length -1)){
							debitStr.append(PromptMessageComm.COMMA_SYMBOL);
						}
						
						//此处查询省指标扣款金额
						//params1:year_month 201707需转为2017/07
						String punishYearMonth = yearMonth.substring(0,4) + "/" + yearMonth.substring(4,yearMonth.length());
						//params2:state
						//params3:userId
						//params4:reg_id
						//params5:auditState
						Map<String, Object> twrProvincePublishParams = new HashMap();
						twrProvincePublishParams.put("punishYearMonth", punishYearMonth);
						twrProvincePublishParams.put("state",StateComm.STATE_0);//0
						twrProvincePublishParams.put("auditState",StateComm.STATE_0);//0
						twrProvincePublishParams.put("userId",loginInfo.getUser_id());
						twrProvincePublishParams.put("regId", regId);
						List<Map<String, Object>> twrPrvCheckIndexFineMapList = twrPrvCheckIndexFineService.queryTwrPrvCheckIndexFineMapList(twrProvincePublishParams);
						for(Map<String, Object> twrPrvCheckIndexFineMap: twrPrvCheckIndexFineMapList){
							guaranteeAmount = guaranteeAmount.add((BigDecimal) twrPrvCheckIndexFineMap.get("punishAmount"));
						}
						
						
					}else if(TowweDebitTypStreComm.DEBITYOSTRE_2.equals(debit)){
						//地市指标扣款
						debitStr.append(PromptMessageComm.COUNTY_INDT_DT);
						if(i < (debits.length -1)){
							debitStr.append(PromptMessageComm.COMMA_SYMBOL);
						}
						
						//此处查询地市指标扣款金额
						//params1:year_month
						//params2:state
						//params3:userId
						//params4:reg_id
						Map<String, Object> twrRegPublishParams = new HashMap();
						String punishYearMonth = yearMonth.substring(0,4) + PromptMessageComm.SLASH_SYMBOL + yearMonth.substring(4,yearMonth.length());
						twrRegPublishParams.put("punishYearMonth", punishYearMonth);
						twrRegPublishParams.put("state",StateComm.STATE_0 );
						twrRegPublishParams.put("auditState",StateComm.STATE_0 + PromptMessageComm.KONG_SYMBOL);//0
						twrRegPublishParams.put("userId",loginInfo.getUser_id());
						twrRegPublishParams.put("regId", regId);
						List<Map<String, Object>> twrRegPublishMapList = twrRegPunishService.queryTwrRegPunishMapListByCondition(twrRegPublishParams);
						if(twrRegPublishMapList != null && twrRegPublishMapList.size() > 0){
							for(Map<String, Object> twrRegPunishMap: twrRegPublishMapList){
								guaranteeAmount = guaranteeAmount.add((BigDecimal) twrRegPunishMap.get("punishAmount"));
							}
						}
					}
					
				}
			}
			
			//统计对应指标扣款金额
			params.put("guaranteeAmount", guaranteeAmount);
		}
		params.put("debitType", debitStr);
		//账单类型
		params.put("dataType", TowweDebitTypStreComm.DEBITYOSTRE_2);
		
		Map<String, Object> validateAccountsummaryMap = twrAccuntsummaryService.validateAccountsummaryStateExists(params);
		
		if(validateAccountsummaryMap != null){
			boolean isExists = (Boolean) validateAccountsummaryMap.get("success");
			if(isExists){
				params.put("createUserId", loginInfo.getUser_id());
				params.put("createTime", DateUtil.date());
				
				params.put("state",StateComm.STATE_0);
				params.put("userId",loginInfo.getUser_id());
				
				feedBackObject.Obj = twrAccuntsummaryService.insertAccountsummary(params);
				feedBackObject.success = RESULT.SUCCESS_1;
				feedBackObject.msg = PromptMessageComm.BILL_GENERATATED_SUCCESS;
			}else{
				feedBackObject.success = RESULT.FAIL_0;
				feedBackObject.msg = PromptMessageComm.BILL_GENERATATED_FAILE;
			}
		}else{
			feedBackObject.success = RESULT.FAIL_0;
			feedBackObject.msg = PromptMessageComm.BILL_VERIFY_FAILE;
		}
		
		return feedBackObject;
	}
	
	/**
	 * 撤销汇总账单
	 * @param <Pre>
	 * 			twrAccountsummaryVO实体
	 * 				param1: feedType 汇总费用类型  例如：  铁塔价格，机房价格
	 * 				param2:	yearmonth 账期 例如： 201707
	 * 				param3: regId 地区ID 例如：110000
	 * 			</Pre>
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/revertAccountsummary", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject revertAccountsummary(@RequestParam Map<String, Object> params){
		FeedBackObject feedBackObject = new FeedBackObject();
		
		String accountsummaryIds = (String) params.get("accountsummaryIds");
		
		List<String> idsList = null;
		if(accountsummaryIds != null && !PromptMessageComm.KONG_SYMBOL.equals(accountsummaryIds)){
			idsList = new ArrayList<String>();
			String[] values = accountsummaryIds.split(PromptMessageComm.COMMA_SYMBOL);
			idsList = Arrays.asList(values);
			if(idsList.size() > 0){
				params.put("idsList", idsList);
			}
		}
		//检验需要删除汇总单的状态
		params.put("op", "del");
		Map<String, Object> validateResultMap = twrAccuntsummaryService.validateTwrAccountsummaryStatus(params);
		boolean success = (Boolean) validateResultMap.get("success");
		if(success){
			int deleteAccountsummary = twrAccuntsummaryService.deleteAccountsummary(params);
			if(deleteAccountsummary > 0){
				feedBackObject.success = RESULT.SUCCESS_1;
				feedBackObject.msg = PromptMessageComm.BILL_DELETE_SUCCESS;
			}else{
				feedBackObject.success = RESULT.FAIL_0;
				feedBackObject.msg = PromptMessageComm.BILL_DELETE_FAILE;
			}
		}else{
			feedBackObject.success = RESULT.FAIL_0;
			feedBackObject.msg = PromptMessageComm.BILL_DELETE_VERIFY_FAILE;
		}
		
		return feedBackObject;
	}
	
	/**
	 * 批量提交费用汇总审核
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/applyAccountsummaryApproval", method = RequestMethod.POST)
	@ResponseBody
	public FeedBackObject applyAccountsummaryApproval(@RequestBody List<Map<String,Object>> ids,@ModelAttribute("user") UserLoginInfo loginInfo){
//		FeedBackObject feedBackObject = new FeedBackObject();
//		
//		String accountsummaryIds = (String) params.get("accountsummaryIds");
//		
//		if(accountsummaryIds != null && !"".equals(accountsummaryIds)){
//			List<String> accountSummaryIdList = new ArrayList<>();
//			String[] values = accountsummaryIds.split(",");
//			
//			UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
//			if(values != null && values.length > 0){
//				accountSummaryIdList = Arrays.asList(values);
//				//校验费用汇总单状态
//				Map<String, Object> submitedAccountsummaryParams = new HashMap<>();
//				submitedAccountsummaryParams.put("idsList", accountSummaryIdList);
//				int existsCount = twrAccuntsummaryService.selectSubmitedAccountsummaryCountByCondition(submitedAccountsummaryParams);
//				if(existsCount == 0){
//					// 启动流程
//					twrAccuntsummaryService.updateAuditAndStartFlow(accountSummaryIdList, loginUser);
//					feedBackObject.success = RESULT.SUCCESS_1;
//					feedBackObject.msg = "提交费用汇总单成功";
//				}else{
//					feedBackObject.success = RESULT.FAIL_0;
//					feedBackObject.msg = "提交费用汇总单失败，已存在提交过的费用汇总单";
//				}
//				
//			}else{
//				feedBackObject.success = RESULT.FAIL_0;
//				feedBackObject.msg = "提交费用汇总单失败，参数异常";
//			}
//			
//		}else{
//			feedBackObject.success = RESULT.FAIL_0;
//			feedBackObject.msg = "提交费用汇总单失败，参数异常";
//		}
//		
//		return feedBackObject;
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
	
		feedbackObj.Obj = twrAccuntsummaryService.updateAuditAndStartFlow(ids,loginInfo);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg = PromptMessageComm.COMMIT_AUDIT_SUCCESS;
		return feedbackObj;
	}
	
	/**
	 * 当前用户费用审核列表
	 * @author wangz
	 * @param params
	 * @return FeedBackObject
	 */
	@RequestMapping(value = "/queryTwrAccountSummeryList", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryTwrAccountSummeryList(@RequestParam Map<String, Object> params,@ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedBackObject = new FeedBackObject();
		
		//获取当前用户所属地区,权限控制
		params.put("alias", PromptMessageComm.ALIAS_NAME_REG);
		params.put("regIds",loginInfo.getReg_ids());
		
		// 审核中的数据
		if(null != params.get("state") && params.get("state").toString().equals(ActivityStateComm.STATE_AUDIT+PromptMessageComm.KONG_SYMBOL)){
			String assignee = loginInfo.getUser_loginname();
			List<String> list = loginInfo.getRole_ids();
			List<String> assigneeNameGroup = iSysRoleService.queryRoleNameById(list);
			Act act = new Act();
			act.setProcDefKey(ActUtils.PD_ACCOUNTSUMMARY_INFO[0]);
			act.setBusinessTable(ActUtils.PD_ACCOUNTSUMMARY_INFO[1]);
			act.setAssignee(assignee);
			act.setAssigneeNameGroup(assigneeNameGroup);
			act.setRegCode(loginInfo.getPrv_code());//区分省份
			List<Act> idsList = actTaskService.todoList(act);
			params.put("ids", idsList);
			String[] ids = new String[idsList.size()]; 
			for (int i = 0; i < idsList.size(); i++) {
				ids[i] = idsList.get(i).getBusinessId();
			}
			params.put("accountIds", ids.length == 0 ? PromptMessageComm.KONG_SYMBOL : ids);
		}
		
		String year = params.get("year").toString();
		String month = params.get("month").toString();
		String yearmonth = year + month;
		params.put("yearmonth", yearmonth);
		
		feedBackObject.Obj = twrAccuntsummaryService.queryPageTwrAccountsummary(params);
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.msg = PromptMessageComm.SELECT_INFO_SUCCESS;
		return feedBackObject;
	}
	
	
	/**
	 * 查询当前审核人
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryCurUser",method=RequestMethod.GET)
	public @ResponseBody FeedBackObject queryCurUser(@ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		String user_loginname = loginInfo.getUser_loginname();
		List<String> list = new ArrayList<String>();
		list.add(user_loginname);
		feedbk.Obj =list;
		return feedbk;
	}
	
	@RequestMapping(value="/queryAccountSummaryById")
	public @ResponseBody FeedBackObject queryAccountSummaryById(@RequestParam String accountId){ 
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.Obj = twrAccuntsummaryService.selectByAccountId(accountId);
		feedBackObject.msg=PromptMessageComm.SEARCH_SUCCESS;
		feedBackObject.success=RESULT.SUCCESS_1;
		return feedBackObject;
	}
	
	/**
	 * 批量审核汇总费用
	 * @author wangz
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/updateComplete")
	@ResponseBody
	public FeedBackObject updateComplete(@RequestBody List<Map<String,Object>> ids,@ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.Obj =twrAccuntsummaryService.saveCheckInfo(ids, loginInfo);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg = PromptMessageComm.COMMIT_AUDIT_SUCCESS;
		return feedbackObj;
	}
}
