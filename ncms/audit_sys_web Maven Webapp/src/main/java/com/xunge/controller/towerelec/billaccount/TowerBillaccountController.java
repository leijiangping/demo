package com.xunge.controller.towerelec.billaccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.xunge.comm.BillAccountComm;
import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.TypeComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.basedata.collection.ContractComm;
import com.xunge.comm.elec.MeterTypeComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.rent.contract.ContractStateComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.controller.common.BaseController;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.DateUtils;
import com.xunge.core.util.excel.ExportExcel;
import com.xunge.core.util.excel.ImportExcel;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.model.selfelec.EleBaseresourceelectricmeter;
import com.xunge.model.selfelec.EleBillaccount;
import com.xunge.model.selfelec.EleBillaccountbaseresource;
import com.xunge.model.selfelec.EleContractbillaccount;
import com.xunge.model.selfelec.VEleBillaccount;
import com.xunge.model.selfelec.VEleBillaccountbaseresource;
import com.xunge.model.selfelec.VEleBillaccountcontract;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.selfelec.billaccount.IElecBillAccountService;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.system.user.ISysUserService;

@Controller
@RequestMapping("/asserts/tpl/towerelec/billaccount")
public class TowerBillaccountController extends BaseController {

	@Autowired
	private IElecBillAccountService elecReimburseInfoService;

	@Autowired
	private ISysUserService userService;
	
	@Autowired
	private ISysRoleService iSysRoleService;
	
	@Autowired
	private IActTaskService actTaskService;
	
	@Autowired
	private ILogService log;
	/**
	 * 查询报账点信息并显示
	 * 
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryReimburseInfo", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryRentContractVO(HttpSession session, VEleBillaccount param, int pageNumber, int pageSize) {
		UserLoginInfo loginInfo = (UserLoginInfo) session.getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject fdback = new FeedBackObject();
		param.setBillaccountType(TypeComm.TOWERELEC_BILLACCOUNTTYPE);
		fdback.Obj = elecReimburseInfoService.queryReimburseInfo(loginInfo , param, pageNumber, pageSize);
		fdback.success = RESULT.SUCCESS_1;
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
	@RequestMapping(value="/queryReimburseInfoVO",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryBillAccountVO(HttpSession session,int pageNumber,int pageSize,
            String billaccountCode,String billaccountName,String pregId,String regId,String billaccountState,String taskDefKey){
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		String assignee=loginUser.getUser_loginname();
		List<String> list = loginUser.getRole_ids();
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_ELEBILLACCOUNT[0]);
		act.setBusinessTable(ActUtils.PD_ELEBILLACCOUNT[1]);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(taskDefKey!=null){
			act.setTaskDefKey(taskDefKey);//设置根据环节Key过滤
		}
		act.setRegCode(loginUser.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		FeedBackObject feedbackObj = new FeedBackObject();
		Page<?> page = new Page<>(pageNumber, pageSize);
		feedbackObj.Obj=page;
		if(idsList.size()>0){
			map.put("idsList", idsList);
			map.put("billaccountName",billaccountName.trim());
			map.put("billaccountState", billaccountState);
			map.put("pregId",pregId);
			map.put("regId",regId);
			map.put("regIds",loginUser.getReg_ids());
			map.put("billaccountCode",billaccountCode.trim());
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.Obj =elecReimburseInfoService.queryEleBillaccount(map, pageNumber, pageSize);
		}
		return feedbackObj;
	}
	@RequestMapping(value = "/selectResourceRelations", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject selectResourceRelations(String billaccountId) {
		FeedBackObject fdback = new FeedBackObject();
		List<Map<String, Object>> mapList = elecReimburseInfoService.selectResourceRelations( billaccountId);
		
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
	
	/**
	 * 导出数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void exportFile(HttpSession session, VEleBillaccount param, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			UserLoginInfo loginInfo = (UserLoginInfo) session.getAttribute("user");
			if(loginInfo == null){
				throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
			}
			
			String fileName = DateDisposeComm.REPORTING_POINT_DATA + DateUtils.getDate(SelfelecComm.FORMAT_yyyyMMddHHmmss) + DateDisposeComm.XLSX;

			List<VEleBillaccount> list = elecReimburseInfoService.queryReimburseInfoForExport(loginInfo , param);
			for (VEleBillaccount vEleBillaccount : list) {
				int state = vEleBillaccount.getAuditingState();
				String auditState = state== ActivityStateComm.STATE_NORMAL ? ActivityStateComm.AUDIT_NORMAL : state == ActivityStateComm.STATE_AUDIT ? ActivityStateComm.AUDIT_AUDIT : state == ActivityStateComm.STATE_UNAPPROVE ? ActivityStateComm.AUDIT_UNAPPROVE : ActivityStateComm.AUDIT_UNCOMMITTED;
				vEleBillaccount.setAuditingStateStr(auditState);
				vEleBillaccount.setBillaccountStateStr(vEleBillaccount.getBillaccountState() == StateComm.STATE_0 ? StateComm.NORMAL_STR : StateComm.STOP_STR);
			}
			new ExportExcel(DateDisposeComm.REPORTING_POINT_DATA, VEleBillaccount.class).setDataList(list).write(request,response, fileName).dispose();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导入数据
	 * 
	 * @author yangju
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<VEleBillaccount> list = ei.getDataList(VEleBillaccount.class);

			System.out.println(PromptMessageComm.NUMBER_OF_IMPORTED_DATA + list == null ? null : list.size());
			if (failureNum > 0) {
				failureMsg.insert(0, PromptMessageComm.FAILURE + failureNum + PromptMessageComm.IMPORT_INFORMATION);
			}
			// logger.info(redirectAttributes, "已成功导入 " + successNum + " 个设备" +
			// failureMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PromptMessageComm.REDIRECT;
	}

	/**
	 * 查询关联合同信息并显示
	 * 
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryContractInfo", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryContractInfo(VEleBillaccountcontract param, int pageNumber, int pageSize) {

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecReimburseInfoService.queryRelationedContractInfo(param, pageNumber, pageSize);

		return fdback;
	}

	@RequestMapping(value = "/queryResourceTree", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryResourceTree(VEleBillaccountbaseresource resource) {

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecReimburseInfoService.queryResourceTree(resource);

		return fdback;
	}
	
	
	@RequestMapping(value = "/queryResourceMeterTree", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryResourceMeterTree(VEleBillaccountbaseresource resource,int pageNumber,
			int pageSize) {

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecReimburseInfoService.queryResourceMeterTree(resource, pageNumber, pageSize);

		return fdback;
	}

	/**
	 * 查询关联资源信息并显示
	 * 
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryResourceInfo", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryResourceInfo(VEleBillaccountbaseresource param, int pageNumber,
			int pageSize) {

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecReimburseInfoService.queryRelationedResourceInfo(param, pageNumber, pageSize);

		return fdback;
	}

	/**
	 * 查询关联电表信息并显示
	 * 
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryElectricmeterInfo", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryElectricmeterInfo(VEleBillaccountbaseresource param, int pageNumber,
			int pageSize) {
		// List<VDatElectricmeter>
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecReimburseInfoService.queryRelationedElectricmeterInfo(param);

		return fdback;
	}

	/**
	 * 查询未关联合同信息并显示
	 * 
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryUnrelationContractInfo", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryUnrelationContractInfo(HttpSession session,VEleBillaccountcontract param, int pageNumber,
			int pageSize,String contractName) {
		UserLoginInfo loginInfo = (UserLoginInfo) session.getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		param.setPrvId(loginInfo.getPrv_id());
		param.setIsDownshare(ContractComm.IS_DOWNSHAR_YES);
		param.setContractState(ContractStateComm.STATE_0);
		param.setAuditingState(ActivityStateComm.STATE_NORMAL);
		param.setRelationState(StateComm.STATE_0);
		param.setContractName(contractName);
		param.setContractEnddate(new Date());
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecReimburseInfoService.queryUnrelationContractInfo(param, pageNumber, pageSize);
		return fdback;
	}

	/**
	 * 查询未关联资源信息并显示
	 * 
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryUnrelationResourceInfo", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryUnrelationResourceInfo(VEleBillaccountbaseresource param, int pageNumber,
			int pageSize,String baseresourceName,HttpServletRequest requesrt) {

		FeedBackObject fdback = new FeedBackObject();
		param.setBaseresourceName(baseresourceName);
		String souArr = requesrt.getParameter("souArr");
		if(souArr.length()>0){
			String[] ids = souArr.split(",");
			List<String> resourceids = new ArrayList<>();
			for(int i = 0 ; i < ids.length ; i++){
				resourceids.add(ids[i]);
			}
			param.setResourceIdList(resourceids);
		}
		fdback.Obj = elecReimburseInfoService.queryUnrelationResourceInfo(param, pageNumber, pageSize);
		fdback.success = RESULT.SUCCESS_1;
		return fdback;
	}

	/**
	 * 查询未关联电表信息并显示
	 * 
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryUnrelationElectricmeterInfo", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryUnrelationElectricmeterInfo(VEleBillaccountbaseresource param,
			int pageNumber, int pageSize,String meterCode,String priceType,HttpServletRequest request) {

		FeedBackObject fdback = new FeedBackObject();
		if("0".equals(priceType)){//普通表
			param.setMeterType(MeterTypeComm.Meter_type_1);
		}else if("1".equals(priceType)){//平峰谷表
			param.setMeterType(MeterTypeComm.Meter_type_2);
		}
		String souArr = request.getParameter("meterIds");
		if(souArr.length()>0){
			String[] ids = souArr.split(",");
			List<String> resourceids = new ArrayList<>();
			for(int i = 0 ; i < ids.length ; i++){
				resourceids.add(ids[i]);
			}
			param.setMeterIds(resourceids);
		}
		param.setMeterCode(meterCode);
		fdback.Obj = elecReimburseInfoService.queryUnrelationElectricmeterInfo(param , pageNumber , pageSize);
		fdback.success = RESULT.SUCCESS_1;

		return fdback;
	}

	@RequestMapping(value = "/queryAuditinguserInfo", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAuditinguserInfo(Map<String, Object> param, int pageNumber, int pageSize) {

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;

		fdback.Obj = userService.queryUserAll(pageNumber, pageSize);

		return fdback;
	}

	// 保存、更新 合同关联
	@RequestMapping(value = "/relationContractSave", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject relationContractSave(EleContractbillaccount param) {

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecReimburseInfoService.saveOrUpdateRelationContract(param);

		return fdback;
	}

	// 取消 合同关联
	@RequestMapping(value = "/relationContractCancel", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject relationContractCancel(EleContractbillaccount param) {

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecReimburseInfoService.saveOrUpdateRelationContract(param);

		return fdback;
	}

	// 取消资源关联
	@RequestMapping(value = "/relationResourceCancel", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject relationResourceCancel(EleBillaccountbaseresource param) {

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecReimburseInfoService.saveOrUpdateRelationResource(param);

		return fdback;
	}
	
	// 取消电表关联
		@RequestMapping(value = "/relationElectricmeterCancel", method = RequestMethod.POST)
		public @ResponseBody FeedBackObject relationElectricmeterCancel(EleBaseresourceelectricmeter param) {

			FeedBackObject fdback = new FeedBackObject();
			fdback.success = RESULT.SUCCESS_1;
			fdback.Obj = elecReimburseInfoService.saveOrUpdateElectricmeterContract(param);

			return fdback;
		}

	// 保存、更新 资源关联
	@RequestMapping(value = "/relationResourceSave", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject relationResourceSave(EleBillaccountbaseresource param) {

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecReimburseInfoService.saveOrUpdateRelationResource(param);

		return fdback;
	}

	// 保存、更新电表关联
	@RequestMapping(value = "/relationElectricmeterSave", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject relationElectricmeterSave(@RequestBody List<Map<String,Object>> list) {
		FeedBackObject fdback = new FeedBackObject();
		for(Map<String,Object> map:list){
			EleBaseresourceelectricmeter param=new EleBaseresourceelectricmeter();
			param.setBaseresourceId((String) map.get("baseresourceId"));
			param.setBillaccountId((String)map.get("billaccountId"));
			param.setMeterId((String)map.get("meterId"));
			fdback.Obj = elecReimburseInfoService.saveOrUpdateElectricmeterContract(param);
		}
		fdback.success = RESULT.SUCCESS_1;

		return fdback;
	}

	// 保存、更新
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject saveOrUpdate(EleBillaccount param,HttpServletRequest request) {
		FeedBackObject fdback = new FeedBackObject();
		param.setBillaccountType(TypeComm.TOWERELEC_BILLACCOUNTTYPE);
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		elecReimburseInfoService.saveOrUpdateEleAccount(param,loginUser);
		fdback.Obj =param;
		fdback.msg=PromptMessageComm.OPERATION_INSERT_SUCCESS;
		fdback.success = RESULT.SUCCESS_1;

		return fdback;
	}
	
	/**
	 * 审批提交流程
	 * @param loginUser
	 * @param ids
	 * @return
	 * @author xup
	 */
	@RequestMapping(value="/commitProcess",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject commitProcess( HttpSession session,@RequestBody List<Map<String,Object>> ids){
		FeedBackObject feedbackObj = new FeedBackObject();
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		feedbackObj.Obj =elecReimburseInfoService.updateActivityCommit(ids,loginUser);
		feedbackObj.msg=PromptMessageComm.SUBMITTED_SUCCESS;
		feedbackObj.success = RESULT.SUCCESS_1;
		return feedbackObj;
	}
	
	/**
	 * 查询当前审核人
	 * @param request
	 * @return
	 * @author xup
	 */
	@RequestMapping(value="/queryCurUser",method=RequestMethod.GET)
	public @ResponseBody FeedBackObject queryCurUser(HttpServletRequest request){
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String user_loginname = loginInfo.getUser_loginname();
		List<String> list=new ArrayList<String>();
			list.add(user_loginname);
			feedbk.Obj =list;
		return feedbk;
	}

	/**
	 *  保存流程审核
	 * @param ids
	 * @param request
	 * @return
	 * @author xup
	 */
	@RequestMapping(value ="/reviewBillAccountAudit",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertCheckInfo(@RequestBody List<Map<String,Object>> ids,HttpServletRequest request){
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.Obj =elecReimburseInfoService.updateAuditCompelet(ids, loginUser);
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.msg =ActivityStateComm.AUDIT_NORMAL;
		return feedbk;
	}
	/*// 审核
	@RequestMapping(value = "/review", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject review(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String jsonStr = request.getParameter("params");
		List<VEleBillaccount> ids = JSON.parseArray(jsonStr, VEleBillaccount.class);
		String userId = request.getParameter("userId");
		String stateStr = request.getParameter("state");
		Integer state = null;
		if (stateStr != null) {
			state = Integer.parseInt(stateStr);
		}

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecReimburseInfoService.reviewEleAccount(ids, userId, state);

		return fdback;
	}
*/
	// 保存并审核
	@RequestMapping(value = "/saveAndReview", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject saveAndReview(EleBillaccount param,HttpServletRequest request) {
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = elecReimburseInfoService.saveOrUpdateEleAccount(param,loginUser);

		return fdback;
	}

	// 删除
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject del(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String jsonStr = request.getParameter("params");

		List<VEleBillaccount> ids = JSON.parseArray(jsonStr, VEleBillaccount.class);

		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.msg=PromptMessageComm.OPERATION_DELETE_SUCCESS;
		fdback.Obj = elecReimburseInfoService.deleteEleAccount(ids);

		return fdback;
	}
	
	/**
	 * @description 判断报账点编码唯一性
	 * @author yuefy
	 * @date 创建时间：2017年8月17日
	 */
	@RequestMapping(value = "/checkBillaccountCode")
	public @ResponseBody FeedBackObject checkBillaccountCode(String billaccountCode,VEleBillaccount param) {
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.msg=PromptMessageComm.SELECT_INFO_SUCCESS;
		Map<String,Object> map = new HashMap<>();
		map.put("billaccountCode", billaccountCode);
		map.put("param", param);
		fdback.Obj = elecReimburseInfoService.checkBillaccountCode(map);
		return fdback;
	}
	
	@RequestMapping(value = "/queryBillaccountById")
	public @ResponseBody FeedBackObject queryBillaccountById(String billaccountId,String regId) {
		FeedBackObject fdback = new FeedBackObject();
		Map<String,Object> map=new HashMap<>();
		map.put("billaccountId",billaccountId );
		map.put("regId", regId);
		fdback.Obj =elecReimburseInfoService.queryBillaccountById(map);
		fdback.success = RESULT.SUCCESS_1;
		return fdback;
	}
	
	/**
	 * 导入
	 * @param file
	 * @param suffix
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/importFile", method=RequestMethod.POST)
    @ResponseBody
    public FeedBackObject importFile(MultipartFile file,String suffix,HttpServletRequest request,HttpServletResponse response){
    	FeedBackObject feedbackObj = new FeedBackObject();
		try {
			Map<String,Object> returnMap = elecReimburseInfoService.insertExcelData(file,suffix,request);
		
			feedbackObj.Obj = returnMap;
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.IMPORT_BILLING_POINT_INFO_SUCCESS+returnMap.get("successCount")+PromptMessageComm.DATAS+returnMap.get("errMsg");

			log.info(SysLogComm.LOG_Operate, feedbackObj.msg);
		} catch (Exception e) {
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg = PromptMessageComm.NOT_VALID_EXCEL_TEMPLATE;
			log.err(SysLogComm.LOG_Error, feedbackObj.msg);
		}
		return feedbackObj;
	}
	
	/**
	 * 查询报账点编码
	 * @author wagnz
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryBillaccountCode", method=RequestMethod.POST)
    @ResponseBody
    public FeedBackObject queryBillaccountCode(HttpServletRequest request){
		FeedBackObject feedbackObj = new FeedBackObject();
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = elecReimburseInfoService.queryMaxCode(loginUser,BillAccountComm.TDBZD_NAME,BillAccountComm.TDBZD);
		return feedbackObj;
	}
}
