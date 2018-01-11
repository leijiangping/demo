package com.xunge.controller.selfelec.eleccontract;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.xunge.comm.StateComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.basedata.collection.ContractComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.rent.contract.ContractStateComm;
import com.xunge.comm.rent.contract.DataFromComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.enums.ResourceEnum.DataFromEnum;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.core.util.UploadUtils;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.model.basedata.DatAttachment;
import com.xunge.model.selfelec.VEleContract;
import com.xunge.model.selfelec.vo.ElecContractQueryVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.selfelec.eleccontract.IEleccontractService;
import com.xunge.service.selfrent.contract.IDatPaymentPeriodService;
import com.xunge.service.selfrent.contract.ISelfRentService;
import com.xunge.service.system.role.ISysRoleService;

@RestController
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@RequestMapping("/asserts/tpl/selfelec/eleccontract")
public class ElecContractController extends BaseException {

	@Autowired
	private IEleccontractService eleccontractService;
	@Autowired
	private IActTaskService actTaskService;
	@Autowired
	private ISysRoleService iSysRoleService;
	
	@Autowired
	private IDatPaymentPeriodService datPaymentPeriodService;

	@Autowired
	private ISelfRentService selfRentService;
	
	/**
	 * @description 查询 合同列表 
	 * @author yuefy
	 * @date 创建时间：2017年9月5日
	 */
	@RequestMapping(value = "/queryEleccontractList", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryEleccontractList(@ModelAttribute("user")UserLoginInfo loginUser,@RequestParam Map<String,Object> map,HttpServletRequest request,Integer dataFrom) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		map.put("prvId", loginUser.getPrv_id());
		map.put("regIds",loginUser.getReg_ids());
		map.put("pregIds",loginUser.getPreg_ids());
		map.put("isDownShare",ContractComm.IS_DOWNSHAR_YES);
		//系统录入及导入数据
		if(DataFromComm.STATE_0 == dataFrom){
			map.put("dataFrom",dataFrom);
			map.put("dataFrom1",DataFromComm.STATE_1);
			//接口采集数据
		}else if(DataFromComm.STATE_2 == dataFrom){
			map.put("dataFrom",dataFrom);
		}
		FeedBackObject backObject = new FeedBackObject();
		backObject.Obj = eleccontractService.queryAllContract(map);
		backObject.success = RESULT.SUCCESS_1;
		return backObject;
	}
	
	
	
	/**
	 * 查询合同列表
	 * 
	 * @param count
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject queryAllEleccontract(@Validated ElecContractQueryVO vo,HttpServletRequest request) {
		FeedBackObject feedbackObj = new FeedBackObject();
		UserLoginInfo loginUser = (UserLoginInfo)request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		//获取当前用户所属地区,权限控制
		map.put("alias",PromptMessageComm.ALIAS_NAME_REG);//别名
		map.put("regIds",loginUser.getReg_ids());
		List<String> list = loginUser.getRole_ids();
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_SELFELEC_AUDIT[0]);
		act.setBusinessTable(ActUtils.PD_SELFELEC_AUDIT[1]);
		act.setAssignee(loginUser.getUser_loginname());
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(map.get("taskDefKey")!=null){
			act.setTaskDefKey(map.get("taskDefKey").toString());//设置根据环节Key过滤
		}
		act.setRegCode(loginUser.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		map.put("ids", idsList);
		vo.setPregIds(loginUser.getPreg_ids());
		vo.setRegIds(loginUser.getReg_ids());
		vo.setPrvId(loginUser.getPrv_id());
		vo.setIsDownShare(ContractComm.IS_DOWNSHAR_YES);
		feedbackObj.Obj = eleccontractService.findAllVEleContract(vo,map);
		feedbackObj.success = RESULT.SUCCESS_1;
		return feedbackObj;
	}
	
	@RequestMapping(value = "/listAll")
	public @ResponseBody FeedBackObject queryAllEleccontractRecord(@Validated ElecContractQueryVO vo,HttpServletRequest request) {
		FeedBackObject feedbackObj = new FeedBackObject();
		UserLoginInfo loginUser = (UserLoginInfo)request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		//获取当前用户所属地区,权限控制
		map.put("alias",PromptMessageComm.ALIAS_NAME_REG);//别名
		map.put("regIds",loginUser.getReg_ids());
		vo.setPregIds(loginUser.getPreg_ids());
		vo.setRegIds(loginUser.getReg_ids());
		vo.setPrvId(loginUser.getPrv_id());
		vo.setIsDownShare(ContractComm.IS_DOWNSHAR_YES);
		feedbackObj.Obj = eleccontractService.findAllVEleContract(vo,map);
		feedbackObj.success = RESULT.SUCCESS_1;
		return feedbackObj;
	}
	/**
	 * 查询合同信息
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value="/one", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryEleccontract(String elecontractId, HttpServletRequest request) {
		String start = request.getParameter("start");
		String length = request.getParameter("length");
		start = start == null ? SelfelecComm.NUMBER_0 : start;
		length = length == null ? SelfelecComm.NUMBER_STR_10 : length;
		Page<VEleContract> page = new Page<>(Integer.parseInt(start), Integer.parseInt(length));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", page.getResult());
		map.put("recordsTotal", Integer.parseInt(length));// pageSize
		map.put("recordsFiltered", Integer.parseInt(SelfelecComm.NUMBER_STR_10));// count
		return map;
	}
	
	@RequestMapping(value = "/queryAll", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryAllElec(@RequestParam Map<String,Object> map,HttpServletRequest request,Integer dataFrom) {
		UserLoginInfo loginUser = (UserLoginInfo)request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		//获取当前用户所属地区,权限控制
		map.put("alias",PromptMessageComm.ALIAS_NAME_REG);//别名
		map.put("regIds",loginUser.getReg_ids());
		//判断是否查询审核中的数据
		String assignee=loginUser.getUser_loginname();
		List<String> roleIds = loginUser.getRole_ids();
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(roleIds);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_SELFELEC_AUDIT[0]);
		act.setBusinessTable(ActUtils.PD_SELFELEC_AUDIT[1]);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(map.get("taskDefKey")!=null){
			act.setTaskDefKey(map.get("taskDefKey").toString());//设置根据环节Key过滤
		}
		act.setRegCode(loginUser.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		
		PageInfo<VEleContract> page= new PageInfo<>();
		FeedBackObject backObject = new FeedBackObject();
		
		if(map.get("auditingState").equals(ActivityStateComm.STATE_AUDIT.toString())){
			map.put("ids",idsList);
		}else{
			//mapper中 使用的 userid 值传给auditing_user_id
			map.put("userId", loginUser.getUser_id());
		}
		
		
			map.put("prvId", loginUser.getPrv_id());
			map.put("regId",map.get("regId"));
			map.put("pregId",map.get("pregId"));
			map.put("regIds",loginUser.getReg_ids());
			map.put("pregIds",loginUser.getPreg_ids());
			map.put("isDownShare",ContractComm.IS_DOWNSHAR_YES);
			map.put("auditState", map.get("auditingState"));
			//系统录入及导入数据
			if(DataFromComm.STATE_0 == dataFrom){
				map.put("dataFrom",dataFrom);
				map.put("dataFrom1",DataFromComm.STATE_1);
				//接口采集数据
			}else if(DataFromComm.STATE_2 == dataFrom){
				map.put("dataFrom",dataFrom);
			}
			page = eleccontractService.queryAllContract(map);
			List<VEleContract> list = Lists.newArrayList();
			for(VEleContract t : page.getList()){
				if (map.get("ids")!=null) {
					for (Act actTemp : (List<Act>) map.get("ids")) {
						if (t.getContractId() != null
								&& t.getContractId().equals(
										actTemp.getBusinessId())) {
							t.setAct(actTemp);
							list.add(t);
						}
					}
				}else{
					list.add(t);
				}
			}
			page.setList(list);
	
		backObject.Obj=page;
		backObject.success = RESULT.SUCCESS_1;
		return backObject;
	}
	
	
	/**
	 * 新增电费合同
	 * 
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject add(HttpServletRequest request) {
		UserLoginInfo loginUser = (UserLoginInfo)request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		VEleContract record = null;
		String param = request.getParameter("param");
		if(StringUtils.isNoneBlank(param)){
			record = JSON.parseObject(param,VEleContract.class);
		}
		FeedBackObject backObj = new FeedBackObject();
		// 填充默认值
		if(StringUtils.isEmpty(record.getElecontractId())){
			record.setElecontractId(PromptMessageComm.ELECONTRACT+System.currentTimeMillis());
		}
//		record.setContractId("CONTRACT-"+System.currentTimeMillis());
		record.setContractState(StateComm.STATE_0);
		// 用于判断合同续签 还是固话信息续签 是 数据来源取值
		if(record.getDataFrom() == null){
			record.setDataFrom(DataFromEnum.SYSTEM);
		}
		record.setPrvId(loginUser.getPrv_id());
		int result = eleccontractService.add(record,loginUser);
		if (result > Integer.parseInt(SelfelecComm.NUMBER_0)) {
			backObj.success = RESULT.SUCCESS_1;
			backObj.msg = PromptMessageComm.OPERATION_INSERT_SUCCESS;
		}else {
			backObj.msg = PromptMessageComm.OPERATION_INSERT_FAILED;
		}
		return backObj;
	}
	/**
	 * 修改电费合同
	 * @param request
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject modify(HttpServletRequest request) {
		VEleContract record = null;
		String param = request.getParameter("param");
		if(StringUtils.isNoneBlank(param)){
			record = JSON.parseObject(param,VEleContract.class);
		}
		FeedBackObject backObj = new FeedBackObject();
		int result = eleccontractService.modify(record);
		if (result > Integer.parseInt(SelfelecComm.NUMBER_0)) {
			backObj.success = RESULT.SUCCESS_1;
		}
		if (RESULT.SUCCESS_1.equals(backObj.success)) {
			backObj.msg = PromptMessageComm.OPERATION_SUSSESS;
		} else {
			backObj.msg = PromptMessageComm.OPERATION_FAILED;
		}
		return backObj;
	}
	/**
	 * 删除电费合同
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/remove")
	@ResponseBody
	public FeedBackObject remove(String contractIds) {
		FeedBackObject backObj = new FeedBackObject();
		try{
			String[] idArray = contractIds.split(",");
	    	Map<String, Object> paraMap = new HashMap<String, Object>();
	    	paraMap.put("contractIds", idArray);
	    	paraMap.put("contractState", ContractStateComm.DELETE__1);
			boolean flag = eleccontractService.updateContractState(paraMap);
			if (flag) {
				backObj.success = RESULT.SUCCESS_1;
				backObj.msg = PromptMessageComm.OPERATION_DELETE_SUCCESS;
			}else {
				backObj.success = RESULT.FAIL_0;
				backObj.msg = PromptMessageComm.OPERATION_DELETE_FAILED;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(PromptMessageComm.OPERATION_DELETE_FAILED);
		}
		return backObj;
	}
	
	/**
	 * 提交审核
	 * @param contractId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/submitAudit",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject submitAudit(@RequestBody List<Map<String,Object>> ids,HttpServletRequest request){
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.Obj = eleccontractService.submitAudit(ids, loginUser);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg = PromptMessageComm.COMMIT_AUDIT_SUCCESS;
		return feedbackObj;
	}
	

	@RequestMapping(value="/queryOneContract",method=RequestMethod.GET)
	public @ResponseBody
	FeedBackObject queryOneContract(String contractId) {
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = eleccontractService.queryByPrimaryKey(contractId);
		return feedbackObj;
	}
	
	
	/**
	 * 审核
	 * @param contractId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/auditContract",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject auditContract(@RequestBody List<Map<String,Object>> ids,HttpServletRequest request){
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.Obj =eleccontractService.audit(ids, loginUser);
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.msg = ActivityStateComm.AUDIT_NORMAL;
		return feedbk;
	}
	
	/**
	 * 获取当前审核人
	 * @author xup
	 * @param request
	 * @return
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
	
	@RequestMapping(value = "/commitCheckInfo", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject updateAuditCompelet(
			@RequestBody List<Map<String, Object>> ids,
			@ModelAttribute("user") UserLoginInfo user) {
		if(user == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		for(Map<String,Object> map:ids){
			String taskid=map.get("taskId").toString();
			Task t=actTaskService.getTask(taskid);
			//增加判断当前业务数据是不是已经提交，需要根据ID重新查询数据库。
			if(t!=null){
				Map<String,Object> vars=new HashMap<String,Object>();
				vars.put(ActivityStateComm.ACTIVITY_VARIABLE_NAME, map.get("auditState").toString());
				if(map.get("nextAuditUserId")!=null&&StrUtil.isNotBlank(map.get("nextAuditUserId").toString())){
					vars.put(ActivityStateComm.ACTIVITY_VARIABLE_NEXTUSER, map.get("nextAuditUserId").toString());//指定下一环节审核人
				}
				String user_loginname = user.getUser_loginname();
				if(StrUtil.isNotBlank(user_loginname)){
					vars.put("currUserId", user_loginname);//指定当前审核人
				}
				actTaskService.completeByBusiness(
						ActUtils.PD_SELFELEC_AUDIT[1], 
						map.get("contractId").toString(),
						map.get("auditNote").toString(), 
						ActUtils.PD_SELFELEC_AUDIT[2],
						vars);
				Task newtask = actTaskService.getTask(ActUtils.PD_SELFELEC_AUDIT[1], map.get("contractId").toString());
				//newtask为空,修改审核状态审核完成
				if(newtask==null){
					//如果是审核不通过，则直接修改业务数据审核状态为不通过
					if(map.get("auditState")!=null&&map.get("auditState").toString().equals(ActivityStateComm.STATE_UNAPPROVE.toString())){
						    Map<String,Object> paramUnprove = new HashMap<String,Object>();
						    paramUnprove.put("contractId", map.get("contractId").toString());
						    paramUnprove.put("auditState", ActivityStateComm.STATE_UNAPPROVE);
							eleccontractService.updateContractVO(paramUnprove);
					}else if(map.get("auditState")!=null&&map.get("auditState").toString().equals(ActivityStateComm.STATE_NORMAL.toString())){
						Map<String,Object> paramSuccess = new HashMap<String,Object>();
						paramSuccess.put("contractId", map.get("contractId").toString());
						paramSuccess.put("auditState", ActivityStateComm.STATE_NORMAL);
						eleccontractService.updateContractVO(paramSuccess);
					}
				}
			}
		}
		feedbackObj.Obj = ids.size();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg = ActivityStateComm.AUDIT_NORMAL;
		return feedbackObj;
	}
	
	/**
	 * 导出
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/export")
	public @ResponseBody FeedBackObject export(@ModelAttribute("user") UserLoginInfo loginUser,@Validated ElecContractQueryVO vo, HttpServletRequest request,HttpServletResponse response,Integer activityFlag) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedBackObject = new FeedBackObject();
		vo.setPregIds(loginUser.getPreg_ids());
		vo.setRegIds(loginUser.getReg_ids());
		vo.setPrvId(loginUser.getPrv_id());
		vo.setIsDownShare(ContractComm.IS_DOWNSHAR_YES);
		String requestUrl = request.getRequestURL().toString();
		String url = requestUrl.substring(Integer.parseInt(SelfelecComm.NUMBER_0), requestUrl.indexOf(PromptMessageComm.WAY_OF_ASSERTS));
		String path = request.getSession().getServletContext().getRealPath("/") + File.separator + PromptMessageComm.WAY_OF_WEB_INF;
		String fileName = eleccontractService.export(vo, path,request,response,loginUser,activityFlag);
		if (!fileName.equals("")) {
			feedBackObject.success = RESULT.SUCCESS_1;
			feedBackObject.msg = PromptMessageComm.MAKE_OUT_FILE_SUCCESS;
			feedBackObject.Obj = url + PromptMessageComm.WAY_OF_ASSERTS_EXPORT + fileName;
		} else {
			feedBackObject.success = RESULT.FAIL_0;
			feedBackObject.msg = PromptMessageComm.MAKE_OUT_FILE_FAILED;
			feedBackObject.Obj = null;
		}
		return feedBackObject;
	}
	/**
	 * 判断合同代码唯一性校验
	 */
	@RequestMapping(value="/checkContractCode",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject checkContractCode(String contractCode,String contractId){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractCode", contractCode);
		map.put("state", StateComm.STATE_0);
		map.put("contractId", contractId);
		feedbackObj.Obj = selfRentService.checkContractCode(map);
		return feedbackObj;
	}
	
	/**
	 * @description 查询 合同列附件列表
	 * @author zhujj
	 * @date 创建时间：2017年9月5日
	 */
	@RequestMapping(value = "/selectByBusiness", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject selectByBusiness(@ModelAttribute("user")UserLoginInfo loginUser,DatAttachment record,HttpServletRequest request) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject backObject = new FeedBackObject();
		backObject.Obj = eleccontractService.selectByBusiness(record);;
		backObject.success = RESULT.SUCCESS_1;
		return backObject;
	}
	@RequestMapping(value="/uploadFile")
	public @ResponseBody FeedBackObject uploadFile(HttpServletRequest request){
		FeedBackObject feedbk = new FeedBackObject();
		UserLoginInfo userLoginInfo = (UserLoginInfo) request.getSession()
				.getAttribute("user");
		if (request.getSession().getAttribute("user") == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String prvCode = userLoginInfo.getPrv_code();
		if (StrUtil.isBlank(prvCode)) {
			prvCode = PromptMessageComm.PRV_CODE_JT;
		}

		UploadUtils upload = new UploadUtils();
		upload.setDirName(PromptMessageComm.UPLOAD_FILES);

		Map<String, Object> map = upload.uploadFile(request, null, prvCode);
		if (map != null && map.get(PromptMessageComm.UPLOAD_ERR) != null
				&& !map.get(PromptMessageComm.UPLOAD_ERR).toString().equals(PromptMessageComm.RESULT_TRUE)) {// 文件验证失败
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = map.get(PromptMessageComm.UPLOAD_ERR).toString();
	        return feedbk;
		}
		if (map != null && map.get(PromptMessageComm.UPLOAD_SAVE_ERR) != null
				&& !map.get(PromptMessageComm.UPLOAD_SAVE_ERR).toString().equals(PromptMessageComm.RESULT_TRUE)) {// 文件上传保存失败
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = map.get(PromptMessageComm.UPLOAD_SAVE_ERR).toString();
	        return feedbk;
		}

		DatAttachment attach = new DatAttachment();
		attach.setAttachmentName(map.get("name").toString());
		attach.setAttachmentUrl(map.get("path").toString());
		
		Map<String, Object> mapFields = upload.getFields();
		if (mapFields != null && mapFields.get("contractId") != null) {
			attach.setBusinessId(mapFields.get("contractId").toString());
		}

		attach.setBusinessType(PromptMessageComm.BUSINESS_TYPE);
		eleccontractService.addAttach(attach);
		feedbk.Obj=attach;
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.msg = PromptMessageComm.UPLOAD_ATTACHMENT+attach.getAttachmentName()+PromptMessageComm.WORD_SUCCESS;
        return feedbk;
    }
	@RequestMapping(value="/delFile")
	public @ResponseBody FeedBackObject delFile(String fileUrl, HttpServletRequest request) {
		FeedBackObject feedbackObj = new FeedBackObject();
		try{
			fileUrl = request.getParameter("fileUrl");
			UploadUtils upload = new UploadUtils();
			upload.setDirName(PromptMessageComm.UPLOAD_FILES);
			String realFileUrl=upload.getBasePath()+fileUrl;
			File file = new File(realFileUrl);
			if(file.exists()&&!file.delete()){
				throw new Exception(PromptMessageComm.DELETE_FILE_EXCEPTION+ fileUrl);
			}
			eleccontractService.delAttach(fileUrl);
			feedbackObj.success = RESULT.SUCCESS_1;
	        feedbackObj.msg = PromptMessageComm.OPERATION_SUSSESS;
		}catch (Exception e) {
			feedbackObj.success = RESULT.FAIL_0;
	        feedbackObj.msg = PromptMessageComm.OPERATION_FAILED;
		}
        return feedbackObj;
	}
	
    private String getFileMB(long byteFile){  
        if(byteFile==Integer.parseInt(SelfelecComm.NUMBER_0)) {
        	return PromptMessageComm.SIZE_0MB; 
        }
        long mb = SelfelecComm.NUMBER_1024;
        return ""+ byteFile/mb + PromptMessageComm.SIZE_MB;  
    }  
    
	/**
	 * 查询合同信息
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value="/queryOne", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject queryOneElecContract(String elecontractId, HttpServletRequest request) {
		FeedBackObject feedbackObj = new FeedBackObject();
		try {
			feedbackObj.Obj = eleccontractService.queryOneElecContractById(elecontractId);
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg = PromptMessageComm.SELECT_INFO_SUCCESS;
		}catch(Exception e){
			feedbackObj.success = RESULT.FAIL_0;
		}
		return feedbackObj;
	}
	
	/**
	 * @description 查询缴费周期
	 * @author yuefy
	 * @date 创建时间：2017年8月17日
	 */
	@RequestMapping(value ="/queryDictionaryByCode")
	public @ResponseBody FeedBackObject queryDictionaryByCode(){
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.Obj = datPaymentPeriodService.queryAllDatPaymentPeriod();
		return feedbk;
	}
	
	/**
	 * @description 查询合同和报账点关联表
	 * @author yuefy
	 * @date 创建时间：2017年8月17日
	 */
	@RequestMapping(value ="/queryBillaccountContract")
	public @ResponseBody FeedBackObject queryBillaccountContract(String elecontractId){
		FeedBackObject feedbk = new FeedBackObject();
		Map<String, Object> map = new HashMap<>();
		map.put("elecontractId", elecontractId);
		map.put("state", StateComm.STATE_0);
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.Obj = eleccontractService.queryBillaccountContract(map);
		return feedbk;
	}
	
	
	/**
	 * @description 获取新增合同Id
	 * @author yuefy
	 * @date 创建时间：2017年9月12日
	 */
	@RequestMapping(value ="/getContractId")
	public @ResponseBody FeedBackObject getContractId(){
		FeedBackObject feedbk = new FeedBackObject();
		feedbk.Obj = SysUUID.generator();
		return feedbk;
	}
	
	/**
	 * @description 审核通过合同退回
	 * @author zhaol
	 * @date 创建时间：2017年9月17日
	 */
	@RequestMapping(value ="/sendback")
	public @ResponseBody FeedBackObject sendback(HttpServletRequest request){
		FeedBackObject feedbk = new FeedBackObject();
		Map<String,Object> map = new HashMap<>();
		map.put("contractId", request.getParameter("contractId"));
		map.put("auditState",ActivityStateComm.STATE_UNAPPROVE);
		eleccontractService.updateContractVO(map);
		feedbk.success = RESULT.SUCCESS_1;
		return feedbk;
	}
    
}