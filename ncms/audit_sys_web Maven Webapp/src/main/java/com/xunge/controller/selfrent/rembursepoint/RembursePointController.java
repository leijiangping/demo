package com.xunge.controller.selfrent.rembursepoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.xunge.comm.basedata.collection.ContractComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.rent.contract.ContractStateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.model.selfrent.billAccount.RentBillAccountResourceVO;
import com.xunge.model.selfrent.rebursepoint.RentBillAccountContractVO;
import com.xunge.model.selfrent.rebursepoint.RentBillaccountVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.selfrent.billaccount.IBillAccountService;
import com.xunge.service.selfrent.contract.ISelfRentService;
import com.xunge.service.selfrent.rebursepoint.IRebursePointService;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.role.ISysRoleService;

/**
 * 
 * @author lpw
 * 
 */
@RequestMapping("/asserts/tpl/selfrent/reimburseInfo_m")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
public class RembursePointController extends BaseException{
	@Autowired
	private ISysRegionService sysRegionService;
	@Autowired
	private IRebursePointService rebursePointService;
	@Autowired
	private ILogService log;
	@Autowired
	private ISelfRentService selfRentService;
	@Autowired
	private IBillAccountService billAccountService;
	@Autowired
	private IActTaskService actTaskService;
	@Autowired
	private ISysRoleService iSysRoleService;
	
	/**
	 * 查询与用户相关的地市信息
	 * @param session
	 * @param pregId
	 * @param regId
	 * @return
	 */
	@RequestMapping(value = "/getAddressReburse", method = RequestMethod.POST)
 	@ResponseBody
	 public FeedBackObject getAddressReburse(@ModelAttribute("user") UserLoginInfo loginInfo) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback = new FeedBackObject();
		String userId = loginInfo.getUser_id();
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("userId", userId);
		fdback.Obj = sysRegionService.queryManaRegions(paraMap);
		return fdback;
	}
	/**
	 * 查询用户关联区域的所有的报账点信息
	 * @param session
	 * @param billAccountState
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryRembursePointInfo", method = RequestMethod.POST)
	public @ResponseBody																					   	
	FeedBackObject queryRembursePointInfo(@ModelAttribute("user") UserLoginInfo loginInfo,String billAccountCodeOrName,String pregId,String regId,String billAccountState,String billaccountAuditState,int pageNumber, int pageSize) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}	
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback = new FeedBackObject();
		List<String> regIds = loginInfo.getReg_ids();
		paraMap.put("regIds", regIds);
//		paraMap.put("alias", "vsr");
		paraMap.put("userId",loginInfo.getUser_id() );
		paraMap.put("regId", regId);
		paraMap.put("pregId", pregId);
		paraMap.put("billAccountCodeOrName", billAccountCodeOrName);
		paraMap.put("billAccountState", billAccountState);
		paraMap.put("billaccountAuditState",billaccountAuditState );
		paraMap.put("state", StateComm.STATE_0);
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = rebursePointService.queryRembursePointInfo(paraMap, pageNumber, pageSize);
		return fdback;
	}
	/**
	 * 根据报账点ID查询报账点
	 * @param billAccountId
	 * @return
	 * 2017年7月10日 lpw
	 */
	@RequestMapping(value="/queryBillAccountById",method=RequestMethod.POST)
	@ResponseBody                                     
	public FeedBackObject queryBillAccountById(String billAccountId){
		FeedBackObject fdback = new FeedBackObject();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(billAccountId)){
			paraMap.put("billAccountId", billAccountId);
		}
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj=rebursePointService.queryBillAccountById(paraMap);
		return fdback;
	}
	/**
	 * 查询与用户区域相关联的合同信息
	 * @param session
	 * @param contractName
	 * @param pageNumber
	 * @param pageSize
	 * @param state
	 * @return
	 */
	@RequestMapping(value = "/queryContractAgreement", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryContractAgreement(@ModelAttribute("user") UserLoginInfo loginInfo,String contractCodeOrName,String regId,String pregId,int pageNumber, int pageSize) { 
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback = new FeedBackObject();
		paraMap.put("userId",loginInfo.getUser_id() );
		paraMap.put("regId", regId);
		paraMap.put("pregId", pregId);
		paraMap.put("contractCodeOrName", contractCodeOrName);
		paraMap.put("contractState", ContractStateComm.STATE_0);
		paraMap.put("state", StateComm.STATE_0);
		fdback.success = RESULT.SUCCESS_1;
		paraMap.put("regIds", loginInfo.getReg_ids());
		paraMap.put("pregIds", loginInfo.getPreg_ids());
		paraMap.put("prvId", loginInfo.getPrv_id());
		paraMap.put("isDownShare", ContractComm.IS_DOWNSHAR_YES);
		fdback.Obj = rebursePointService.queryContractAgreement(paraMap, pageNumber, pageSize);
		return fdback;
	}
	
	/**
	 * 查询用户关联区县的资源信息
	 * @param rentcontractId
	 * @return
	 */
	@RequestMapping(value="/queryResourceInfo",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryResourceInfo(@ModelAttribute("user") UserLoginInfo loginInfo,
			String resourceCodeOrName,String regId,String pregId,int pageNumber, int pageSize){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback = new FeedBackObject();
		List<String> regIds = loginInfo.getReg_ids();
		paraMap.put("regIds", regIds);
		paraMap.put("userId",loginInfo.getUser_id() );
		paraMap.put("regId", regId);
		paraMap.put("pregId", pregId);
		paraMap.put("resourceCodeOrName", resourceCodeOrName);
		paraMap.put("baseResourceState", StateComm.STATE_0);
		paraMap.put("state", StateComm.STATE_0);
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = rebursePointService.queryResourceInfo(paraMap, pageNumber, pageSize);
 		return fdback;
	}
	
	/**
	 * 根据报账点ID查询关联的资源点信息
	 * @param billaccountId
	 * @return
	 * 2017年7月10日 lpw
	 */
	@RequestMapping(value="/queryResource",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryResource(String billaccountId){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback = new FeedBackObject();
		if(StringUtils.isNotBlank(billaccountId)){
			paraMap.put("billaccountId", billaccountId);
		}
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = rebursePointService.queryResource(paraMap);
		return fdback;
	}
	/**
	 * 查询合同是否已经绑定了资源点
	 * @param rentcontractId
	 * @return
	 */
	@RequestMapping(value="/queryContractBindBillacc",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryContractBindBillacc(String rentcontractId){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback = new FeedBackObject();
		if(StringUtils.isNoneBlank(rentcontractId)){
			paraMap.put("rentcontractId", rentcontractId);
		}
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = rebursePointService.queryContractBindBillacc(rentcontractId);
		return fdback;
	}
	/**
	 * 查询资源点是否已经绑定了资源点
	 * @param rentcontractId
	 * @return
	 */
	@RequestMapping(value="/queryResourceBindBillacc")
	public @ResponseBody FeedBackObject queryResourceBindBillacc(String baseresourceIds){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback = new FeedBackObject();
		if(StringUtils.isNotBlank(baseresourceIds)){
			paraMap.put("resourceIds", baseresourceIds.split(","));
		}
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = rebursePointService.queryResourceBindBillacc(paraMap);
		return fdback;
	}
	/**
	 * 新增报账点
	 * @param rentBillaccount
	 * @param rentBillAccountContract
	 * @param rentBillAccountResource
	 */
	@RequestMapping(value="/insertBillAcount",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertBillAcount(RentBillaccountVO billaccount,String rentcontractId,String baseresourceIds){
		FeedBackObject fdback = new FeedBackObject();
		//新增报账点
		String billAccountId = SysUUID.generator();
		billaccount.setBillAccountId(billAccountId);
		billaccount.setBillAccountState(StateComm.STATE_0);
		/**
		 * 修改实体类参数名
		 *原： billaccount.setBillaccountAuditState(StateComm.STATE__1);
		 *@author xup  2017/7/28 11:10
		 */
		
		billaccount.setAuditState(StateComm.STATE__1);
		//新增报账点合同关系表
		String billaccountcontractId = SysUUID.generator();
		RentBillAccountContractVO billAccountContract = new RentBillAccountContractVO();
		if(StringUtils.isNotBlank(rentcontractId)){
				billAccountContract.setBillAccountContractId(billaccountcontractId);
				billAccountContract.setBillAccountId(billAccountId);
				billAccountContract.setRentContractId(rentcontractId);
				billAccountContract.setRelationState(StateComm.STATE_0);;
		}
		List<RentBillAccountResourceVO> rentResourceList = new ArrayList<RentBillAccountResourceVO>();
		//新增报账点资源表
		if(StringUtils.isNotBlank(baseresourceIds)){
				for (String  baseresourceId : baseresourceIds.split(",")) {
					String billAccountResourceId = SysUUID.generator();
					RentBillAccountResourceVO billAccountResource = new RentBillAccountResourceVO();
					billAccountResource.setBaseResourceId(baseresourceId);
					billAccountResource.setBillAccountId(billAccountId);
					billAccountResource.setBillAccountResourceId(billAccountResourceId);
					billAccountResource.setRelationState(StateComm.STATE_0);
					rentResourceList.add(billAccountResource);
			}
		}
		fdback.Obj = rebursePointService.insertBillAcount(billaccount, billAccountContract, rentResourceList);
		return fdback;
	}
	/**
	 * 修改报账点
	 * @param billAccountId
	 * @param billAccountContractId
	 * @param billAccountResourceId
	    	
	 */
	@RequestMapping(value="/updateBillAcount",method = RequestMethod.POST)
	public@ResponseBody FeedBackObject updateBillAcount(RentBillaccountVO billaccount,String rentcontractId, String baseresourceIds){
		FeedBackObject fdback = new FeedBackObject();
		RentBillAccountContractVO billAccountContract = new RentBillAccountContractVO();
		billAccountContract.setRentContractId(rentcontractId);
		billAccountContract.setBillAccountId(billaccount.getBillAccountId());
		rebursePointService.updateBillAcount(billaccount,rentcontractId,baseresourceIds);
		return fdback;
	}
	/**
	 * 删除报账点
	 * @param billAccountId
	 * @param billAccountContractId
	 * @param billAccountResourceId
	 */
	@RequestMapping(value="/deleteBillAcount",method = RequestMethod.POST)
	public@ResponseBody FeedBackObject deleteBillAcount(String billAccountId){
		FeedBackObject fdback = new FeedBackObject();
		fdback.Obj = rebursePointService.deleteBillAcount(billAccountId);
		return fdback;
	}
	/**
	 * 查询用户合同相关的信息
	 * @param contractId
	 * @param regId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/queryContractByContractId",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryContractByContractId(String contractId,String regId,int pageNumber, int pageSize){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback = new FeedBackObject();
		if(StringUtils.isNoneBlank(contractId)){
			paraMap.put("contractId", contractId);
		}
		if(StringUtils.isNoneBlank(regId)){
			paraMap.put("regId", regId);
		}
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = rebursePointService.queryContractByContractId(paraMap, pageNumber, pageSize);
		return fdback;
	}
	/**
	 * 查询用户资源相关的信息
	 * @param contractId
	 * @param regId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/queryContractByResourceId",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryContractByResourceId(String resourceIds){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback = new FeedBackObject();
		if(StringUtils.isNotBlank(resourceIds)){
			paraMap.put("resourceIds", resourceIds.split(","));
		}
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = rebursePointService.queryContractByResourceId(paraMap);
		return fdback;
	}
	/**
	 * 根据合同ID查询所有信息
	 * @param contractId
	 * @param regId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/queryContractById",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryContractById(String contractId,String regId){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback = new FeedBackObject();
		if(StringUtils.isNotBlank(contractId)){
			paraMap.put("contractId", contractId);
		}
		if(StringUtils.isNotBlank(regId)){
			paraMap.put("regId", regId);
		}
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = rebursePointService.queryContractById(paraMap);
		return fdback;
	}
	/**
	 * 通过房租合同ID查询合同和供应商信息
	 * @param rentcontractId
	 * @param regId
	 * @return
	 * 2017年7月10日 lpw
	 */
	@RequestMapping(value="/queryContractAndSupplier",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryContractAndSupplier(String rentcontractId,String regId){
		Map<String, Object> paraMap = new HashMap<String, Object>();    
		FeedBackObject fdback = new FeedBackObject();
		if(StringUtils.isNotBlank(rentcontractId)){
			paraMap.put("rentcontractId", rentcontractId);
		}
		if(StringUtils.isNotBlank(regId)){
			paraMap.put("regId", regId);
		}
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = rebursePointService.queryContractAndSupplier(paraMap);
		return fdback;
	}
	/**
	 * 根据报账点ID查询合同和供应商信息
	 * @param billaccountId
	 * @return
	 * 2017年7月10日 lpw
	 */
	@RequestMapping(value="/queryContAndSupByBillId",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryContAndSupByBillId(String billaccountId){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback = new FeedBackObject();
		if(StringUtils.isNotBlank(billaccountId)){
			paraMap.put("billaccountId", billaccountId);
		}
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = rebursePointService.queryContAndSupByBillId(paraMap);
		return fdback;
	}
	/**
	 * 查询用户相关信息
	 * @param session
	 * @param paraMap
	 */
	public void queryUserInfo(@ModelAttribute("user")UserLoginInfo loginInfo,Map<String,Object> paraMap){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String userId = loginInfo.getUser_id();
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("userId", userId);
	}
	/**
	 * 报账点页面提交审核
	 * @param roleIdLists
	 * @return
	 */
	@RequestMapping(value="/billAccountSubmitAudit",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject billAccountSubmitAudit(@RequestBody List<Map<String,Object>> ids,@ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.Obj =rebursePointService.billAccountSubmitAudit(ids,loginInfo);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg =PromptMessageComm.COMMIT_AUDIT_SUCCESS;
		return feedbackObj;
	}
	/**
	 * 查询待审批的报账点	
	 * @param loginUser
	 * @param billAccountCodeOrName
	 * @param regId
	 * @param billAccountState
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryRembursePointVO", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryRembursePointVO(@ModelAttribute("user")UserLoginInfo loginInfo, String billAccountCodeOrName,String regId,
			int pageNumber, int pageSize,String taskDefKey) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String assignee=loginInfo.getUser_loginname();
		List<String> list = loginInfo.getRole_ids();
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_RENTBILL_ACCOUNT[0]);
		act.setBusinessTable(ActUtils.PD_RENTBILL_ACCOUNT[1]);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(StrUtil.isNotBlank(taskDefKey)){
			act.setTaskDefKey(taskDefKey);//设置根据环节Key过滤
		}
		/**
		 * 添加区域编码区分流程
		 *@author xup  2017/7/27 17:42
		 *
		 */
		act.setRegCode(loginInfo.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		FeedBackObject fdback = new FeedBackObject();
		Page<?> page=new Page<>(pageNumber, pageSize);
		fdback.Obj=page;
		if(idsList.size()>Integer.parseInt(SelfelecComm.NUMBER_0)){
			paraMap.put("idsList", idsList);
			List<String> regIds = loginInfo.getReg_ids();
			paraMap.put("regIds", regIds);
			paraMap.put("billAccountCodeOrName", billAccountCodeOrName);
			paraMap.put("regId", regId);
			paraMap.put("userId", loginInfo.getUser_id());
			paraMap.put("state", StateComm.STATE_0);
			paraMap.put("billaccountAuditState", StateComm.STATE_9);
			fdback.success = RESULT.SUCCESS_1;
			fdback.Obj = rebursePointService.queryRembursePointVO(paraMap, pageNumber, pageSize);
		}
		return fdback;
	}
	/**
	 * 
	 * @param state 通过-
	 * @param leader
	 * @param comment
	 * @return
	 */
	@RequestMapping(value = "/saveBillAccountAudit", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject saveBillAccountAudit(@RequestBody List<Map<String,Object>> ids,@ModelAttribute("user")UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.Obj = rebursePointService.billAccountCheckAudit(ids, loginInfo);
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}
	
	/**
	 * 查询当前用户
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryCurUser",method=RequestMethod.GET)
	public @ResponseBody FeedBackObject queryCurUser(@ModelAttribute("user")UserLoginInfo loginInfo){
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
	 * 删除报账点绑定的资源点
	 * @param baseresourceId
	 * @return
	 * lpw
	 */
	@RequestMapping(value="/deleteResourcePoint",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject deleteResourcePoint(String baseresourceId){
		FeedBackObject fdback = new FeedBackObject();
		fdback.Obj = rebursePointService.deleteResourcePoint(baseresourceId);
		return fdback;
	}
	/**
	 * 根据报账点ID支付方式
	 * @param billaccountId
	 * @return
	 * 2017年7月10日 lpw
	 */
	@RequestMapping(value="/queryPaymentMethod",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryPaymentMethod(String billAccountId){
		FeedBackObject fdback = new FeedBackObject();
		fdback.Obj = rebursePointService.queryPaymentMethod(billAccountId);
		fdback.success = RESULT.SUCCESS_1;
		return fdback;
	}
	/**
	 * 获取用户区域信息
	 */
	@RequestMapping(value = "/getAddressRent", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject getAddressRent(@ModelAttribute("user")UserLoginInfo loginInfo) {
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
	 * 导出报账点信息
	 */
	@RequestMapping(value="/exportRembursePointInfo")
	public @ResponseBody FeedBackObject export(@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response) {
		FeedBackObject feedbackObj = new FeedBackObject();
		
		UserLoginInfo loginUser = (UserLoginInfo)request.getSession().getAttribute("user");
		//获取当前用户所属地区,权限控制
		map.put("prvId", loginUser.getPrv_id());
		rebursePointService.exportRembursePointInfo(map, request, response);
		feedbackObj.msg=PromptMessageComm.EXPORT_INFO_SUCCESS;
		return feedbackObj;
	}

}
