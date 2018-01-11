package com.xunge.controller.selfrent.contract;

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

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;

import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.basedata.collection.ContractComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.rent.contract.ContractStateComm;
import com.xunge.comm.rent.contract.DataFromComm;
import com.xunge.comm.rent.contract.SupplierStateComm;
import com.xunge.comm.rent.contract.SupplierTypeComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.TimeUtils;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.model.activity.ActHistoicFlow;
import com.xunge.model.selfrent.contract.DatSupplierVO;
import com.xunge.model.selfrent.contract.RentContractVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.selfrent.contract.IDatPaymentPeriodService;
import com.xunge.service.selfrent.contract.ISelfRentService;
import com.xunge.service.selfrent.contract.exceldatahandler.ContractInfoHandler;
import com.xunge.service.system.dictionary.IDictionaryService;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.role.ISysRoleService;

/**
 * 合同信息维护Controller
 *
 */
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@RequestMapping("/asserts/tpl/selfrent/rentcontract_m")
@Controller
public class RentContractController extends BaseException{

	@Autowired
	private ISelfRentService selfRentService;

	@Autowired
	private ISysRegionService sysRegionService;
	
	@Autowired
	private IDictionaryService dictionaryService;
	
	@Autowired
	private IActTaskService actTaskService;
	
	@Autowired
	private ISysRoleService iSysRoleService;
	
	@Autowired
	private IDatPaymentPeriodService datPaymentPeriodService;

	@Autowired
	private ILogService log;
	/**
	 * 查询并返回主合同、房屋租赁合同以及供应商信息
	 * @param rentcontractId
	 * @return
	 */
	@RequestMapping(value ="/queryAllOfContract",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllOfContract(String rentcontractId){
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		RentContractVO rentContractVO = null;
		if(!StrUtil.isBlank(rentcontractId)){
			rentContractVO = selfRentService.queryAllOfContract(rentcontractId);
		}
		feedbk.Obj = rentContractVO;
		return feedbk;
	}
	
	/**
	 * 获取用户区域信息
	 */
	@RequestMapping(value = "/getAddressRent", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject getAddressRent(@ModelAttribute("user") UserLoginInfo loginInfo) {
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
	 *  查询所有信息并显示审核
	 * @param loginUser
	 * @param contractName
	 * @param regId
	 * @param pregId
	 * @param contractState
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryRentContractVO", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryRentContractVO(@ModelAttribute("user") UserLoginInfo loginInfo, String contractName,
			String regId, String pregId,String contractState, int pageNumber, int pageSize,String taskDefKey) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		contractName = contractName.replaceAll(" ", "");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String assignee=loginInfo.getUser_loginname();
		List<String> list = loginInfo.getRole_ids();
		paraMap.put("regIds", loginInfo.getReg_ids());
		paraMap.put("pregIds", loginInfo.getPreg_ids());
		paraMap.put("prvId", loginInfo.getPrv_id());
		paraMap.put("isDownShare", ContractComm.IS_DOWNSHAR_YES);
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_SELFRENT_AUDIT[0]);
		act.setBusinessTable(ActUtils.PD_SELFRENT_AUDIT[1]);
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
			paraMap.put("contractName", contractName);
			paraMap.put("regId", regId);
			paraMap.put("userId", loginInfo.getUser_id());
			paraMap.put("state", ContractStateComm.STATE_0);
			paraMap.put("pregId", pregId);
			paraMap.put("contractState", contractState);
			paraMap.put("rentcontractState", ActivityStateComm.STATE_AUDIT);
			fdback.success = RESULT.SUCCESS_1;
			fdback.Obj = selfRentService.queryRentContractVO(paraMap, pageNumber, pageSize);
		}
		return fdback;
	}
	/**
	 *  查询租费合同信息补录
	 * @param loginUser
	 * @param contractName
	 * @param regId
	 * @param pregId
	 * @param contractState
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryRentContractList", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryRentContractList(@ModelAttribute("user") UserLoginInfo loginInfo,
			String contractName,String regId, String pregId,String contractState,Integer dataFrom, int pageNumber, int pageSize) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		contractName = contractName.replaceAll(" ", "");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("regIds", loginInfo.getReg_ids());
		paraMap.put("pregIds", loginInfo.getPreg_ids());
		paraMap.put("prvId", loginInfo.getPrv_id());
		paraMap.put("isDownShare", ContractComm.IS_DOWNSHAR_YES);
		FeedBackObject fdback = new FeedBackObject();
		if(dataFrom != null){
			paraMap.put("dataFrom", dataFrom);
		}
		paraMap.put("contractName", contractName);
		paraMap.put("regId", regId);
		paraMap.put("state", ContractStateComm.STATE_0);
		paraMap.put("pregId", pregId);
		paraMap.put("userId", loginInfo.getUser_id());
		paraMap.put("contractState", contractState);
		paraMap.put("rentcontractState", ActivityStateComm.STATE_UNCOMMITTED);
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = selfRentService.queryRentContractVO(paraMap, pageNumber, pageSize);
		return fdback;
	}

	/**
	 *  查询所有租费合同信息并显示
	 * @param loginUser
	 * @param contractName
	 * @param regId
	 * @param rentcontractState
	 * @param pregId
	 * @param contractState
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryRentContractVOList", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryRentContractVOList(@ModelAttribute("user") UserLoginInfo loginInfo, String contractName,
			String regId, String auditingState,String pregId,String contractState,Integer dataFrom, int pageNumber, int pageSize) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		contractName = contractName.replaceAll(" ", "");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("regIds", loginInfo.getReg_ids());
		paraMap.put("pregIds", loginInfo.getPreg_ids());
		paraMap.put("prvId", loginInfo.getPrv_id());
		paraMap.put("isDownShare", ContractComm.IS_DOWNSHAR_YES);
		FeedBackObject fdback = new FeedBackObject();
		paraMap.put("contractName", contractName);
		//如果dataFrom  不为空 说明为维护 或者补录 0-维护(维护为系统新增 和 导入)，  2- 补录 只能是系统新增 和导入的数据可以维护
		if(dataFrom != null){
			paraMap.put("dataFrom", dataFrom);
			if(dataFrom == DataFromComm.STATE_0){
				paraMap.put("dataFrom1", DataFromComm.STATE_1);
			}
		}
		paraMap.put("regId", regId);
		paraMap.put("userId", loginInfo.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("pregId", pregId);
		paraMap.put("contractState", contractState);
		paraMap.put("rentcontractState", auditingState);
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = selfRentService.queryRentContractVO(paraMap, pageNumber, pageSize);
		return fdback;
	}

	/**
	 * 根据数据code查询数据字典
	 * @param dictgroup_code
	 * @return
	 */
	@RequestMapping(value ="/queryDictionaryByCode",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryDictionaryByCode(
			@ModelAttribute("user") UserLoginInfo loginInfo, String dictgroup_code){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.Obj = dictionaryService.queryDictionaryByCodeRedis(loginInfo.getPrv_id(), dictgroup_code);
		return feedbk;
	}
	

	/**
	 * 续签合同信息保存
	 * @author yuefy
	 * @return	
	 */
	
	@RequestMapping(value ="/renewContractMsg",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject renewContractMsg(@ModelAttribute("user") UserLoginInfo loginInfo, RentContractVO rentContractVO){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String ,Object> parmMap = new HashMap<String ,Object>();
		parmMap.put("rentContractVO", rentContractVO);
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		parmMap.put("prvId", loginInfo.getPrv_id());
		feedbk.Obj = selfRentService.insertRentContract(parmMap);
		if(feedbk.success.equals(RESULT.SUCCESS_1)){
			feedbk.msg = loginInfo.getUser_loginname()+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		}else{
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = loginInfo.getUser_loginname()+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}	
		return feedbk;
	}
	/**
	 * 审核流程中提交审核
	 * @param loginUser
	 * @param state
	 * @param comment
	 * @param leader
	 * @param rentcontractId
	 * @return
	 */
	@RequestMapping(value ="/saveCheckInfo",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertCheckInfo(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestBody List<Map<String,Object>> ids){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		int i  = selfRentService.rentContractCheckAudit(ids, loginInfo);
		feedbk.success = i+"";
		if(feedbk.success.equals(RESULT.SUCCESS_1)){
			feedbk.msg = loginInfo.getUser_loginname() + PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		}else{
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = loginInfo.getUser_loginname() + PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}
		return feedbk;
	
	}
	
	/**
	 * 终止合同stopContract
	 * @author yuefy
	 */
	@RequestMapping(value ="/stopContract",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject stopContract(@ModelAttribute("user") UserLoginInfo loginInfo,String contractIds){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String[] contractId = contractIds.split(",");
		List<String> contractIdlst = new ArrayList<String>();
		for (int i = Integer.parseInt(SelfelecComm.NUMBER_0); i < contractId.length; i++) {
			contractIdlst.add(contractId[i]);
		}
		Map<String ,Object> parmMap = new HashMap<String ,Object>();
		parmMap.put("contractIdsList", contractIdlst);
		parmMap.put("state", ContractStateComm.STATE_9);
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = selfRentService.stopContract(parmMap);
		if(feedbk.success.equals(RESULT.SUCCESS_1)){
			feedbk.msg = loginInfo.getUser_loginname() + PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		}else{
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = loginInfo.getUser_loginname() + PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}	
		return feedbk;
	}
	
	/**
	 * 删除合同deleteContract
	 * @author yuefy
	 */
	@RequestMapping(value ="/deleteContract",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject deleteContract(@ModelAttribute("user") UserLoginInfo loginInfo,
			String contractIds,String rentcontractIds){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String[] contractId = contractIds.split(",");
		String[] rentcontractId = rentcontractIds.split(",");
		List<String> contractIdlst = new ArrayList<String>();
		List<String> rentcontractIdlst = new ArrayList<String>();
		for (int i = Integer.parseInt(SelfelecComm.NUMBER_0); i < contractId.length; i++) {
			contractIdlst.add(contractId[i]);
		}
		for (int i = Integer.parseInt(SelfelecComm.NUMBER_0); i < rentcontractId.length; i++) {
			rentcontractIdlst.add(rentcontractId[i]);
		}
		Map<String ,Object> parmMap = new HashMap<String ,Object>();
		parmMap.put("contractIdsList", contractIdlst);
		parmMap.put("rentcontractIdlst", rentcontractIdlst);
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = selfRentService.deleteContract(parmMap);
		if(feedbk.success.equals(RESULT.SUCCESS_1)){
			feedbk.msg = loginInfo.getUser_loginname()+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		}else{
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = loginInfo.getUser_loginname()+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}	
		return feedbk;
	}
	
	/**
	 * 合同信息补录
	 * @return	
	 */
	@RequestMapping(value ="/updateContract",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject updateContract(@ModelAttribute("user") UserLoginInfo loginInfo,
			RentContractVO rentContractVO){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String ,Object> parmMap = new HashMap<String ,Object>();
		parmMap.put("rentContractVO", rentContractVO);
		FeedBackObject feedbk = new FeedBackObject();
		parmMap.put("prvId", loginInfo.getPrv_id());
		feedbk.success = selfRentService.updateRentContract(parmMap);
		if(feedbk.success.equals(RESULT.SUCCESS_1)){
			feedbk.msg = loginInfo.getUser_loginname()+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		}else{
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = loginInfo.getUser_loginname()+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}	
		return feedbk;
	}
	
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
	 * 提交审核
	 * @param roleIdLists
	 * @return
	 */
	@RequestMapping(value="/SubmitAudit",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject SubmitAudit(@RequestBody List<Map<String,Object>> ids,@ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		try{
			feedbackObj.Obj =selfRentService.updateCommit(ids,loginInfo);
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.COMMIT_AUDIT_SUCCESS;
			log.info(SysLogComm.LOG_Operate, feedbackObj.msg);
		}catch(Exception e){
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg =PromptMessageComm.COMMIT_AUDIT_FAILED;
			log.err(SysLogComm.LOG_Error, feedbackObj.msg);
		}
		return feedbackObj;
	}
	
	/**
	 * 判断合同代码唯一性校验
	 * @author yuefy
	 */
	@RequestMapping(value="/checkContractCode",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject checkContractCode(String contractCode){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractCode", contractCode);
		map.put("state", StateComm.STATE_0);
		feedbackObj.Obj =selfRentService.checkContractCode(map);
		return feedbackObj;
	}
	/**
	 * 审核
	 * @param rentcontractId
	 * @return
	 */
	@RequestMapping(value="/checkInfo",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject checkInfo(String rentcontractId){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		List<Act> list = actTaskService.histoicFlowList(ActUtils.PD_SELFRENT_AUDIT[1], rentcontractId, "", "");
		List<ActHistoicFlow> li=new ArrayList<ActHistoicFlow>();
		
		for (int i = Integer.parseInt(SelfelecComm.NUMBER_0); i < list.size(); i++) {
			
			ActHistoicFlow act=new ActHistoicFlow();
			act.setAssignee(list.get(i).getAssignee());
			act.setBeginDate(list.get(i).getBeginDate());
			act.setEndDate(list.get(i).getEndDate());
			act.setComment(list.get(i).getComment());
			act.setTitle(list.get(i).getHistIns().getActivityName());
			
			if(act.getEndDate()!=null&&act.getBeginDate()!=null)
			act.setDurationTime(TimeUtils.toTimeString(act.getEndDate().getTime()-act.getBeginDate().getTime()));
			else
			act.setDurationTime("");	
				
			li.add(act);
		}
		feedbackObj.Obj=li ;
		return feedbackObj;
	}
	
	/**
	 * 查询供应商信息
	 * @author yuefy
	 */
	@RequestMapping(value="/querySupplierInfo",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject querySupplierInfo(@ModelAttribute("user") UserLoginInfo loginInfo,String regId,String sRegId,String pregId,
			 int pageNumber, int pageSize,String supplierName){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		Map<String ,Object> parmMap = new HashMap<String ,Object>();
		parmMap.put("supplierState", SupplierStateComm.STATE_0);
		parmMap.put("prvId", loginInfo.getPrv_id());
		//regId 合同区县 id  sRegId 供应商区县Id
		if(regId != null && regId != ""){
			sRegId = regId;
		}
		parmMap.put("regId", sRegId);
		parmMap.put("pregId", pregId);
		parmMap.put("pregIds", loginInfo.getPreg_ids());
		parmMap.put("regIds", loginInfo.getReg_ids());
		parmMap.put("isDownShare", ContractComm.IS_DOWNSHAR_YES);
//		parmMap.put("supplierType", SupplierTypeComm.STATE_1);
		parmMap.put("supplierName", supplierName);
		Page<List<DatSupplierVO>> list = selfRentService.queryDatSupplierByPrvID(parmMap,pageNumber,pageSize);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = list;
		return feedbackObj;
	}
	
	/**
	 * 查询供应商代码唯一性
	 * @author yuefy
	 */
	@RequestMapping(value="/checkSupplierCode",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject checkSupplierCode( String supplierCode){
		FeedBackObject feedbackObj = new FeedBackObject();
		Map<String ,Object> parmMap = new HashMap<String ,Object>();
		parmMap.put("supplierState", SupplierStateComm.STATE_0);
		parmMap.put("supplierType", SupplierTypeComm.STATE_1);
		parmMap.put("supplierCode", supplierCode);
		feedbackObj.Obj = selfRentService.queryDatSupplierByPrvID(parmMap);
		feedbackObj.success = RESULT.SUCCESS_1;
		return feedbackObj;
	}
	
	/**
	 * 导出合同信息数据
	 * @author yuefy
	 */
	@RequestMapping(value="/exportContractInfo")
	public void exportContractInfo(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestParam Map<String,Object> paraMap,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		
		feedbackObj.success = RESULT.SUCCESS_1;
		
		feedbackObj.msg=PromptMessageComm.SELECT_INFO_SUCCESS;
		
		paraMap.put("userId", loginInfo.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("prvId", loginInfo.getPrv_id());
		
		List<SysRegionVO> listreg=sysRegionService.getAddress(paraMap);//准备需要的数据处理
		//系统录入及导入数据
		if((DataFromComm.STATE_0+"").equals(paraMap.get("dataFrom"))){
			paraMap.put("dataFrom1",DataFromComm.STATE_1);
			//接口采集数据
		}
		paraMap.put("contractName", request.getParameter("contractName").replaceAll(" ", ""));
		paraMap.put("regIds", loginInfo.getReg_ids());
		paraMap.put("pregIds", loginInfo.getPreg_ids());
		paraMap.put("isDownShare", ContractComm.IS_DOWNSHAR_YES);
		List<RentContractVO> list = selfRentService.queryRentContractVO(paraMap);
		ContractInfoHandler tbh=new ContractInfoHandler(listreg);//塔维变更信息自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.BELONG_REGION});//需要处理数据的列名 
		
		ExportParams params = new ExportParams();
		String[]  outData = {DateDisposeComm.PAYMENT_ENDDATE,DateDisposeComm.PLAY_DATE,DateDisposeComm.CONTRACT_ALL_MOUNTH,DateDisposeComm.RESIDUE_DAYS};
		params.setExclusions(outData);
		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, RentContractVO.class,list);
       
		FileUtils.downFile(workBook, DateDisposeComm.RENT_CONTRACT_INFO_XLS, request, response);
	}
	
	/**
	 * @description 查询缴费周期
	 * @author yuefy
	 * @date 创建时间：2017年8月17日
	 */
	@RequestMapping(value ="/queryDatPaymentPeriod")
	public @ResponseBody FeedBackObject queryDatPaymentPeriod(){
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.Obj = datPaymentPeriodService.queryAllDatPaymentPeriod();
		return feedbk;
	}
	
}
