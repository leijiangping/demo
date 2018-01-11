package com.xunge.controller.selfrent.billaccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.xunge.comm.SysLogComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.model.selfrent.billAccount.CoverAllVO;
import com.xunge.model.selfrent.billAccount.DatBaseresourceVO;
import com.xunge.model.selfrent.billAccount.RentPaymentVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.selfrent.billaccount.IBillAccountService;
import com.xunge.service.selfrent.billaccount.IPaymentService;
import com.xunge.service.system.dictionary.IDictionaryService;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.role.ISysRoleService;
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@RequestMapping("/asserts/tpl/selfrent/billaccount_m")
@Controller
public class PaymentController extends BaseException{

	@Autowired
	private IPaymentService paymentService;
	
	@Autowired
	private IActTaskService actTaskService;
	
	@Autowired
	private ISysRoleService iSysRoleService;
	
	@Autowired
	private IBillAccountService billAccountService;
	
	@Autowired
	private ILogService log;

	
	@Autowired
	private IDictionaryService dictionaryService;

	
	@Autowired
	private ISysRegionService sysRegionService;

	
	/**
	 * 获取用户区域信息
	 */
	@RequestMapping(value = "/getAddressPay", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject getAddress(@ModelAttribute("user") UserLoginInfo loginInfo) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String userId = loginInfo.getUser_id();
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("userId", userId);
		FeedBackObject fdback = new FeedBackObject();
		fdback.Obj = sysRegionService.queryManaRegions(paraMap);
		return fdback;
	}
	/**
	 * 审核主页面查询
	 * @param loginUser
	 * @param pageNumber
	 * @param pageSize
	 * @param billAccountName
	 * @param contractName
	 * @param pregId
	 * @param regId
	 * @param billState
	 * @return
	 * @author xup
	 */
	@RequestMapping(value="/queryPayment",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryBillAccountVO(@ModelAttribute("user") UserLoginInfo loginInfo,int pageNumber,int pageSize,
            String billAccountName,String contractName,String pregId,String regId,String taskDefKey){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		String assignee=loginInfo.getUser_loginname();
		List<String> list = loginInfo.getRole_ids();
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_REMBURSE_POINT[0]);
		act.setBusinessTable(ActUtils.PD_REMBURSE_POINT[1]);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(taskDefKey!=null){
			act.setTaskDefKey(taskDefKey);//设置根据环节Key过滤
		}
		/**
		 * 添加区域编码区分流程
		 *@author xup  2017/7/27 17:42
		 *
		 */
		act.setRegCode(loginInfo.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		FeedBackObject feedbackObj = new FeedBackObject();
		Page<?> page=new Page<>(pageNumber, pageSize);
		feedbackObj.Obj=page;
		if(idsList.size()>Integer.parseInt(SelfelecComm.NUMBER_0)){
			String userId = loginInfo.getUser_id();
			List<String> regIds = loginInfo.getReg_ids();
			map.put("regIds", regIds);
			map.put("alias", PromptMessageComm.ALIAS_NAME);
			map.put("state", StateComm.STATE_0);
			map.put("userId", userId);
			map.put("idsList", idsList);
			map.put("billaccountName",billAccountName);
			map.put("pregId",pregId);
			map.put("regId",regId);
			map.put("contractName",contractName);
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.Obj =paymentService.queryPayment(map, pageNumber, pageSize);
			
		}
		return feedbackObj;
	}
	
	/**
	 * 缴费记录查询
	 * @param pageNumber
	 * @param pageSize
	 * @param billAccountName
	 * @param contractName
	 * @param pregId
	 * @param regId
	 * @param billState
	 * @return
	 * @author xup
	 */
	@RequestMapping(value="/queryAllPayment",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryBillAccount(@ModelAttribute("user") UserLoginInfo loginInfo,int pageNumber,int pageSize,
            String billAccountName,String contractName,String pregId,String regId){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		String userId = loginInfo.getUser_id();
		List<String> regIds = loginInfo.getReg_ids();
		map.put("regIds", regIds);
		map.put("alias", PromptMessageComm.ALIAS_NAME);
		map.put("state", StateComm.STATE_0);
		map.put("userId", userId);
		map.put("billaccountName",billAccountName);
		map.put("pregId",pregId);
		map.put("regId",regId);
		map.put("contractName",contractName);
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj =paymentService.queryAllPayment(map, pageNumber, pageSize);
		return feedbackObj;
	}
	/**
	 * 审核内页面查询
	 * @param paymentId
	 * @param billaccountId
	 * @return
	 * @author xup
	 */
	@RequestMapping(value="/queryContractPayment",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryBillAccountVO(String paymentId,String billaccountId,String billaccountcontractId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("paymentId",paymentId);
		map.put("billaccountcontractId",billaccountcontractId);
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj =paymentService.queryContractPayment(map, billaccountId,paymentId);
		return feedbackObj;
	}
	/**
	 * 报账点租费维护主页面查询
	 * @param pageNumber
	 * @param pageSize
	 * @param billAccountName
	 * @param pregId
	 * @param regId
	 * @param billState
	 * @param paymentState
	 * @return
	 * @author xup
	 */
	@RequestMapping(value="/queryPaymentContract",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryPaymentContract(@ModelAttribute("user") UserLoginInfo loginInfo,int pageNumber,int pageSize,String billAccountName,
			String pregId,String regId,String billState,String paymentState){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		String userId = loginInfo.getUser_id();
		List<String> regIds = loginInfo.getReg_ids();
		map.put("regIds", regIds);
		map.put("alias", PromptMessageComm.ALIAS_NAME);
		map.put("state", StateComm.STATE_0);
		map.put("userId", userId);
		map.put("billaccountName",billAccountName);
		map.put("pregId",pregId);
		map.put("regId",regId);
		map.put("paymentState",paymentState);
		map.put("paymentFlowstate",billState);
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj =paymentService.queryPaymentContract(map, pageNumber, pageSize);
		return feedbackObj;
	}
	
	/**
	 * 指定下级审核人
	 * @param procDefKey
	 * @param request
	 * @since 2017/7/26
	 * @author xup
	 * @return
	 */
	@RequestMapping(value="/queryFirstUserByProcDefKey",method=RequestMethod.POST)
	@ResponseBody
	public FeedBackObject  queryFirstUserByProcDefKey(@ModelAttribute("user") UserLoginInfo loginInfo,String procDefKey){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		List<String> roleIdLists=actTaskService.getFristNodeRole(procDefKey);
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		if(roleIdLists!=null && roleIdLists.size()>Integer.parseInt(SelfelecComm.NUMBER_0)){
			feedbackObj.Obj =iSysRoleService.queryUserByRoleName(roleIdLists,loginInfo);
		}
		return feedbackObj;
	}
	
	/**
	 * 查询当前审核人
	 * @param request
	 * @return
	 * @author xup
	 */
	@RequestMapping(value="/queryCurUser",method=RequestMethod.GET)
	public @ResponseBody FeedBackObject queryCurUser(@ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		String user_loginname = loginInfo.getUser_loginname();
		List<String> list=new ArrayList<String>();
			list.add(user_loginname);
			feedbk.Obj =list;
		return feedbk;
	}
	/**
	 * 保存流程审核
	 * @param state
	 * @param comment
	 * @param leader
	 * @param paymentId
	 * @param request
	 * @return
	 * @author xup
	 */
	@RequestMapping(value ="/saveCheckInfo",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertCheckInfo(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestBody List<Map<String,Object>> ids){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.Obj =paymentService.updateAuditCompelet(ids, loginInfo);
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.msg =PromptMessageComm.COMMIT_AUDIT_SUCCESS;
		return feedbk;
	}
	/**
	 * 审批提交流程
	 * @param paymentId
	 * @param billaccountId
	 * @return
	 * @author xup
	 */
	@RequestMapping(value="/commitProcess",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject commitProcess(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestBody List<Map<String,Object>> ids){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.Obj =paymentService.updateActivityCommit(ids,loginInfo);
		feedbackObj.msg=PromptMessageComm.SUBMITTED_SUCCESS;
		feedbackObj.success = RESULT.SUCCESS_1;
		return feedbackObj;
	}
	/**
	 * 维护页面保存按钮
	 * @param submitData
	 * @return
	 */
	
	@RequestMapping(value="/updateAllForm",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryBillAccountVO(@ModelAttribute("user") UserLoginInfo loginInfo,
				CoverAllVO ca){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedBackObject = new FeedBackObject();
		List<DatBaseresourceVO> dbsList = ca.getLstDatbase();
		RentPaymentVO rentpaymentVO = ca.getRentPayMentVO();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dbsList",dbsList);
		map.put("rentpaymentVO",rentpaymentVO);
		feedBackObject.success = billAccountService.updateAllForm(map);
		if(feedBackObject.success.equals(RESULT.SUCCESS_1)){
			feedBackObject.msg = loginInfo.getUser_loginname()+PromptMessageComm.OPERATION_UPDATE_SUCCESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedBackObject.msg);
		}else{
			feedBackObject.success = RESULT.FAIL_0;
			feedBackObject.msg = loginInfo.getUser_loginname()+PromptMessageComm.OPERATION_UPDATE_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedBackObject.msg);
		}	
		return feedBackObject;
	}
	
	/**
	 * 数据字典查询
	 * @param dictgroup_code
	 * @return
	 */
	/*@RequestMapping(value ="/rent/common/queryDictionaryByCode",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryDictionaryByCode(String dictgroup_code){
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.Obj = dictionaryService.queryDictionaryByCodeRedis(dictgroup_code);
		return feedbk;
	}*/
	
	/**
	 * 导出报账点信息
	 * @author xup
	 */
	@RequestMapping(value="/exportPayment")
	public void export(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		
		feedbackObj.success = RESULT.SUCCESS_1;
		map.put("loginInfo", loginInfo);
		
		feedbackObj.msg=PromptMessageComm.EXPORT_INFO_SUCCESS;
		paymentService.exportPaymentDetail(map, request, response);
	}
	@RequestMapping(value = "/queryBillaccountRelations", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryBillaccountRelations(String billaccountId) {
		FeedBackObject fdback = new FeedBackObject();
		List<Map<String, Object>> mapList = billAccountService.queryBillaccountRelations(billaccountId);
		
		Map<String , Map<String, Object>> mapData = new HashMap<String, Map<String,Object>>();
		
		for(Map<String , Object> map : mapList){
			mapData.put(map.get("id").toString(), map);
		}
		
		Map<String, Object> tmpMap;
		List<Map<String, Object>> tmpList;
		Object childrenObj = null;
		List<Map<String, Object>> resultList = new ArrayList<>();
		for(Map<String , Object> map : mapList){
			if(map.get("pId")!=null){
				tmpMap = mapData.get(map.get("pId").toString());
				if(tmpMap!=null){
					childrenObj = tmpMap.get("children");
					if(childrenObj == null){
						tmpList = new ArrayList<>();
					}else{
						tmpList = (List<Map<String, Object>>)childrenObj;
					}
					tmpList.add(map);
					tmpMap.put("children", tmpList);
				}
			}else{
				resultList.add(map);
			}
		}
		
		
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = resultList;

		return fdback;
	}
}
