package com.xunge.controller.towerrent.checkmanage;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
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
import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import com.github.pagehelper.StringUtil;
import com.google.common.collect.Lists;
import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.controller.basedata.util.OperateUtil;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.model.towerrent.checkmanage.TwrPrvCheckIndexFineVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.towerrent.checkmanage.ITwrPrvCheckIndexFineService;

/**
 * 
 * @author jiacy
 * @date 2017年7月19日
 * @description 考核指标扣罚控制器
 */
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@RequestMapping("/asserts/tpl/towerrent/checkmanage")
@Controller
public class TwrPrvCheckIndexFineController extends BaseException{
	
	@Autowired
	ITwrPrvCheckIndexFineService twrPrvCheckIndexFineService; 
	@Autowired
	private ILogService log;
	@Autowired
	private IActTaskService actTaskService;
	@Autowired
	private ISysRoleService iSysRoleService;
	
	/**
	 * 考核指标扣罚查询所有
	 * @author jiacy
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/queryrAllPrvCheckIndexFine",method=RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryrAllPrvCheckIndexFineVO(String regId, String pregId,
			String punishYearMonth, int pageNum, int pageSize,HttpServletRequest request) {
		FeedBackObject backObject = new FeedBackObject();
		UserLoginInfo loginUser = (UserLoginInfo)request.getSession().getAttribute("user");
		pregId = StringUtils.isBlank(pregId) ? loginUser.getPrv_id() : pregId;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("prvId", pregId);
		paramMap.put("regId", regId);
		paramMap.put("punishYearMonth", punishYearMonth);
		backObject.Obj = twrPrvCheckIndexFineService
				.queryrAllPrvCheckIndexFineVO(paramMap, pageSize, pageNum);
		return backObject;
	}
	
	/**
	 * 考核指标扣罚查询
	 * @author jiacy
	 * @param checkId
	 * @return
	 */
	@RequestMapping(value = "/queryCheckIndexFine", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryCheckIndexFineVOById(@RequestParam String checkId) {
		FeedBackObject backObject = new FeedBackObject();
		backObject.Obj = twrPrvCheckIndexFineService
				.queryCheckIndexFineVOById(checkId);
		return backObject;
	}
	
	/**
	 * 考核指标扣罚删除
	 * @author jiacy
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteCheckIndexFine", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject deleteTwrById(@RequestBody List<String> prvCheckIdList) {
		FeedBackObject backObject = new FeedBackObject();
		backObject.success = twrPrvCheckIndexFineService
				.deleteTwrById(prvCheckIdList);
		OperateUtil.echoBeahivor(backObject, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObject;
	}
	
	/**
	 * 考核指标扣罚更新
	 * @author jiacy
	 * @param twrPrvCheckIndexFine
	 * @return
	 */
	@RequestMapping(value = "/updateCheckIndexFine", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject updateTwrById(
			@ModelAttribute("user") UserLoginInfo loginUser,
			TwrPrvCheckIndexFineVO twrPrvCheckIndexFine) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject backObject = new FeedBackObject();
		twrPrvCheckIndexFine.setUpdateTime(new Date());
		twrPrvCheckIndexFine.setUpdateUserId(loginUser
				.getUser_id());
		backObject.success = twrPrvCheckIndexFineService
				.updateTwrById(twrPrvCheckIndexFine);
		OperateUtil.echoBeahivor(backObject, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObject;
	}
	
	/**
	 * 考核指标扣罚增加
	 * @author jiacy
	 * @param twrPrvCheckIndexFine
	 * @return
	 */
	@RequestMapping(value = "/addCheckIndexFine", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject insertTwrById(
			@ModelAttribute("user") UserLoginInfo loginUser,
			TwrPrvCheckIndexFineVO twrPrvCheckIndexFine) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject backObject = new FeedBackObject();
		twrPrvCheckIndexFine.setTwrProvincePunishId(SysUUID.generator());
		twrPrvCheckIndexFine.setCreateUserId(loginUser
				.getUser_id());
		twrPrvCheckIndexFine.setCreateTime(new Date());
		backObject.success = twrPrvCheckIndexFineService
				.insertTwrById(twrPrvCheckIndexFine);
		OperateUtil.echoBeahivor(backObject, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObject;
	}
	
	/**
	 * 考核指标扣罚提交审核
	 * @author jiacy
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/checkCheckIndexFine", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject checkTwrById(
			@ModelAttribute("user") UserLoginInfo loginUser,
			@RequestBody List<Map<String,Object>> ids) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		// StateComm.STATE_9;
		FeedBackObject backObject = new FeedBackObject();
		// 启动审核流程
		for(Map<String,Object> map:ids){
			TwrPrvCheckIndexFineVO checkVO = twrPrvCheckIndexFineService.queryCheckIndexFineVOById(map.get("id").toString());
			//判断状态为未提交的才可以提交
			if(checkVO!=null&&checkVO.getAuditState()==ActivityStateComm.STATE_UNCOMMITTED){
				//user.getPrv_code()根据省份不同调用不同流程
				actTaskService.startProcess(
						ActUtils.PD_TWRREGPRVPUNISH_INFO[0], 
						loginUser.getPrv_id(), 
						ActUtils.PD_TWRREGPRVPUNISH_INFO[1], 
						map.get("id").toString(), 
						ActUtils.PD_TWRREGPRVPUNISH_INFO[2], 
						map, loginUser.getUser_loginname());
			}
			TwrPrvCheckIndexFineVO newFineVO = new TwrPrvCheckIndexFineVO();
			newFineVO.setTwrProvincePunishId(map.get("id").toString());
			newFineVO.setUpdateUserId(loginUser.getUser_id());
			newFineVO.setUpdateTime(new Date());
			newFineVO.setAuditState(ActivityStateComm.STATE_AUDIT);
			twrPrvCheckIndexFineService.updateTwrById(newFineVO);
		}
		backObject.success = RESULT.SUCCESS_1;
		OperateUtil.echoBeahivor(backObject, log, PromptMessageComm.COMMIT_AUDIT_SUCCESS, PromptMessageComm.COMMIT_AUDIT_FAILED);
		return backObject;
	}
	
	/**
	 * 考核指标扣罚导出
	 * @author jiacy
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/export")
	public void exportPrvCheck(@RequestParam Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		FeedBackObject feedbackObj = new FeedBackObject();
		List<TwrPrvCheckIndexFineVO> priCheckList = twrPrvCheckIndexFineService
				.queryExportList(map);// 准备需要的数据处理
		for(TwrPrvCheckIndexFineVO twrPrvCheckIndexFineVO : priCheckList){
			int state = twrPrvCheckIndexFineVO.getAuditState();
			String auditState = state== ActivityStateComm.STATE_NORMAL ? ActivityStateComm.AUDIT_NORMAL : state == ActivityStateComm.STATE_AUDIT ? ActivityStateComm.AUDIT_AUDIT : state == ActivityStateComm.STATE_UNAPPROVE ? ActivityStateComm.AUDIT_UNAPPROVE : ActivityStateComm.AUDIT_UNCOMMITTED;
			twrPrvCheckIndexFineVO.setAuditStateStr(auditState);
			twrPrvCheckIndexFineVO.setRegId(twrPrvCheckIndexFineVO.getRegName());
		}
		ExportParams params = new ExportParams(PromptMessageComm.PROVINCE_SELFASSESSMENT_INDT_DT, PromptMessageComm.PROVINCE_SELFASSESSMENT_INDT_DT,
				ExcelType.XSSF);
		org.apache.poi.ss.usermodel.Workbook workBook = ExcelExportUtil
				.exportExcel(params, TwrPrvCheckIndexFineVO.class, priCheckList);
		FileUtils.downFile(workBook, PromptMessageComm.PROVINCE_SELFASSESSMENT_INDT_DT_xls, request, response);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg = PromptMessageComm.OPERATION_SUSSESS;
	}
	
	/**
	 * 考核指标扣罚审核页面查询
	 * @author jiacy
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryCheckIndexFineInfo", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryTwrRentInformationCheck(
			@ModelAttribute("user") UserLoginInfo loginUser, String regId,
			String pregId, String punishYearMonth, String prvId,String auditState, int pageNum, int pageSize,String taskDefKey) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject backObject = new FeedBackObject();
		Page<TwrPrvCheckIndexFineVO> page=new Page<TwrPrvCheckIndexFineVO>(pageNum,pageSize);
		backObject.Obj=page;
		backObject.success = RESULT.SUCCESS_1;
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String assignee = loginUser.getUser_loginname();
		List<String> list = loginUser.getRole_ids();
		List<String> assigneeNameGroup = iSysRoleService
				.queryRoleNameById(list);
		Act act = new Act();
		act.setProcDefKey(ActUtils.PD_TWRREGPRVPUNISH_INFO[0]);
		act.setBusinessTable(ActUtils.PD_TWRREGPRVPUNISH_INFO[1]);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(taskDefKey!=null){
			act.setTaskDefKey(taskDefKey);//设置根据环节Key过滤
		}
		act.setRegCode(loginUser.getPrv_code());
		List<Act> idsList = actTaskService.todoList(act);
		if (idsList.size() > 0) {
			paraMap.put("idsList", idsList);
			paraMap.put("regId", regId);
			paraMap.put("userId", loginUser.getUser_id());
			paraMap.put("state", StateComm.STATE_str0);
			paraMap.put("pregId", pregId);
			paraMap.put("auditState", auditState);
			paraMap.put("punishYearMonth", punishYearMonth);
			paraMap.put("prvId", prvId);
			Page<TwrPrvCheckIndexFineVO> prvFineList = twrPrvCheckIndexFineService
					.queryTwrRentInformationCheck(paraMap, pageNum, pageSize);
			List<TwrPrvCheckIndexFineVO> listFine = Lists.newArrayList();
			for(TwrPrvCheckIndexFineVO prvFineVO:prvFineList.getResult()){
				for(Act acts : idsList){
						if(prvFineVO.getTwrProvincePunishId()!=null&&prvFineVO.getTwrProvincePunishId().equals(acts.getBusinessId())){
							prvFineVO.setAct(acts);
							listFine.add(prvFineVO);
						}
				}
			}
			prvFineList.setResult(listFine);
			backObject.Obj = prvFineList;
			backObject.success = RESULT.SUCCESS_1;
			backObject.msg =PromptMessageComm.OPERATION_SUSSESS;
		}
		return backObject;
	}
	
	/**
	 * 对扣罚进行审核
	 * @author jiacy
	 * @param loginUser
	 * @param state
	 * @param comment
	 * @param leader
	 * @param twrProvincePunishIds
	 * @return
	 */
	@RequestMapping(value = "/saveCheckInfo", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject insertCheckInfo(
			@ModelAttribute("user") UserLoginInfo loginUser, String state,
			String comment, String leader, String twrProvincePunishId) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("state", state);
		if (StrUtil.isNotBlank(leader)) {
			vars.put("nextUserId", leader);// 指定下一环节审核人
		}
		String user_loginname = loginUser.getUser_loginname();
		if (StrUtil.isNotBlank(user_loginname)) {
			vars.put("currUserId", user_loginname);// 指定当前审核人
		}
		actTaskService.completeByBusiness(ActUtils.PD_TWRREGPRVPUNISH_INFO[1],
				twrProvincePunishId, comment,
				ActUtils.PD_TWRREGPRVPUNISH_INFO[2], vars);
		dealCheckResult(loginUser, state, feedbk, twrProvincePunishId);
		return feedbk;
	}

	/**
	 * 根据审核结果进行处理
	 * @author jiacy
	 * @param loginUser
	 * @param state
	 * @param feedbk
	 * @param punishId
	 */
	private void dealCheckResult(UserLoginInfo loginUser, String state,
			FeedBackObject feedbk, String punishId) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		TwrPrvCheckIndexFineVO finVo = twrPrvCheckIndexFineService
				.queryCheckIndexFineVOById(punishId);
		// 审核通过
		if (state.equals(ActivityStateComm.STATE_NORMAL+"")) {
			Task newtask = actTaskService.getTask(
					ActUtils.PD_TWRREGPRVPUNISH_INFO[1], punishId);
			// newtask为空,审核完成
			if (newtask == null) {
				finVo.setAuditState(ActivityStateComm.STATE_NORMAL);
				twrPrvCheckIndexFineService.updateTwrById(finVo);
			}
			feedbk.success = RESULT.SUCCESS_1;
		} else { // 审核不通过
			feedbk.success = RESULT.FAIL_0;
			finVo.setAuditState(ActivityStateComm.STATE_UNAPPROVE);
			twrPrvCheckIndexFineService.updateTwrById(finVo);
		}
		if (feedbk.success.equals(RESULT.SUCCESS_1)) {
			feedbk.msg = loginUser.getUser_loginname() + PromptMessageComm.SAVE_INFO_SUCCESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		} else {
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = loginUser.getUser_loginname() + PromptMessageComm.SAVE_INFO_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}
	}
	
	/**
	 * 获取当前审核人
	 * @author xup
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
		List<String> list=new ArrayList<String>();
			list.add(user_loginname);
			feedbk.Obj =list;
		return feedbk;
	}
	
	/**
	 * 导入省内扣罚
	 * @author jiacy
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/importFile", method=RequestMethod.POST)
    @ResponseBody
    public FeedBackObject importFile(@ModelAttribute("user") UserLoginInfo loginInfo,MultipartFile file,String suffix,HttpServletRequest request,HttpServletResponse response){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		try {
			Map<String,Object> returnMap=twrPrvCheckIndexFineService.insertExcelData(file,suffix,request);
		
			feedbackObj.Obj=returnMap;
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.IMPORT_INFO+returnMap.get("successCount")+PromptMessageComm.NUM_DATA+returnMap.get("errMsg");
		}catch (Exception e) {
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg=PromptMessageComm.NOT_VALID_EXCEL_TEMPLATE;
		}
		return feedbackObj;
	}
	
	/**
	 * @author jiacy
	 * 提交审核(增加重复提交审核校验)
	 */
	@RequestMapping(value = "/commitCheckInfo", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject updateAuditCompelet(
			@RequestBody List<Map<String, Object>> ids,
			@ModelAttribute("user") UserLoginInfo loginInfo) {
		if(loginInfo == null){
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
				String user_loginname = loginInfo.getUser_loginname();
				if(StrUtil.isNotBlank(user_loginname)){
					vars.put("currUserId", user_loginname);//指定当前审核人
				}
				actTaskService.completeByBusiness(
						ActUtils.PD_TWRREGPRVPUNISH_INFO[1], 
						map.get("twrProvincePunishId").toString(),
						map.get("auditNote").toString(), 
						ActUtils.PD_TWRREGPRVPUNISH_INFO[2],
						vars);
				Task newtask = actTaskService.getTask(ActUtils.PD_TWRREGPRVPUNISH_INFO[1], map.get("twrProvincePunishId").toString());
				//newtask为空,修改审核状态审核完成
				if(newtask==null){
					//如果是审核不通过，则直接修改业务数据审核状态为不通过
					if(map.get("auditState")!=null&&map.get("auditState").toString().equals(ActivityStateComm.STATE_UNAPPROVE.toString())){
						TwrPrvCheckIndexFineVO newFineVO = new TwrPrvCheckIndexFineVO();
						newFineVO.setTwrProvincePunishId(map.get("twrProvincePunishId").toString());
						newFineVO.setUpdateUserId(loginInfo.getUser_id());
						newFineVO.setUpdateTime(new Date());
						newFineVO.setAuditState(ActivityStateComm.STATE_UNAPPROVE);
						twrPrvCheckIndexFineService.updateTwrById(newFineVO);
					}else if(map.get("auditState")!=null&&map.get("auditState").toString().equals(ActivityStateComm.STATE_NORMAL.toString())){
						TwrPrvCheckIndexFineVO newFineVO = new TwrPrvCheckIndexFineVO();
						newFineVO.setTwrProvincePunishId(map.get("twrProvincePunishId").toString());
						newFineVO.setUpdateUserId(loginInfo.getUser_id());
						newFineVO.setUpdateTime(new Date());
						newFineVO.setAuditState(ActivityStateComm.STATE_NORMAL);
						twrPrvCheckIndexFineService.updateTwrById(newFineVO);
					}
				}
			}
		}
		feedbackObj.Obj = ids.size();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg =PromptMessageComm.COMMIT_AUDIT_SUCCESS;
		return feedbackObj;
	}
}
