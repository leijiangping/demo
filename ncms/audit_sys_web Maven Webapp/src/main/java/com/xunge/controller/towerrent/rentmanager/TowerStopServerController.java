package com.xunge.controller.towerrent.rentmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
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

import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.rent.contract.ContractStateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.rentmanager.TowerRentInformationHistoryVO;
import com.xunge.model.towerrent.rentmanager.TowerResourceInfoVO;
import com.xunge.model.towerrent.stopserver.TowerStopServerDateVO;
import com.xunge.model.towerrent.stopserver.TowerStopServerVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.towerrent.mobile.ITwrRentInformationService;
import com.xunge.service.towerrent.rentinformationhistory.ITwrRentInformationHistoryService;
import com.xunge.service.towerrent.stopserver.ITowerStopServerService;
import com.xunge.service.towerrent.stopserver.exceldatahandler.TowerStopServerHandler;
import com.xunge.service.twrrent.resourceinfo.ITowerResourceInfoService;

/**
 * 终止服务controller
 * @author yuefy
 * @date 2017.07.20
 *
 */
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@RequestMapping("/asserts/tpl/towerrent/rentmanage")
@Controller
public class TowerStopServerController extends BaseException {
	
	@Autowired
	private ISysRegionService sysRegionService;
	
	@Autowired
	private ITowerStopServerService towerStopServerService;
	
	@Autowired
	private ILogService log;
	
	@Autowired
	private ITwrRentInformationService twrRentInformationService;
	
	@Autowired
	private ITwrRentInformationHistoryService twrRentInformationHistoryService;
	
	@Autowired
	private ITowerResourceInfoService towerResourceInfoService;

	@Autowired
	private IActTaskService actTaskService;
	
	@Autowired
	private ISysRoleService iSysRoleService;
	
	/**
	 * 查询所有终止信息信息并显示
	 * @param pregId
	 * @param regId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/queryTowerStopServer", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryTowerStopServer(@ModelAttribute("user") UserLoginInfo loginUser,
			String pregId,String regId, Integer pageNumber, Integer pageSize) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		FeedBackObject fdback = new FeedBackObject();
		paraMap.put("regId", regId);
		paraMap.put("pregId", pregId);
		paraMap.put("userId", loginUser.getUser_id());
		paraMap.put("state", ContractStateComm.STATE_0);
		List<String> regIds = loginUser.getReg_ids();
		paraMap.put("regIds", regIds);
		paraMap.put("alias", PromptMessageComm.ALIAS_NAME);
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = towerStopServerService.queryTowerStopServer(paraMap,pageNumber,pageSize);
		return fdback;
	}
	
	/**
	 * 导入终止信息
	 * @return
	 */
	@RequestMapping(value = "/importTowerStopServer", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject importTowerStopServer(@ModelAttribute("user") UserLoginInfo loginUser,HttpServletRequest request,String suffix,
			MultipartFile file) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", loginUser.getUser_id());
		paraMap.put("prvId", loginUser.getPrv_id());
		paraMap.put("userLoginname", loginUser.getUser_loginname());
		paraMap.put("state", StateComm.STATE_0);
		feedbackObj.success = RESULT.SUCCESS_1;
		try {
			Map<String,Object> returnMap= towerStopServerService.importTowerStopServer(file,suffix,request,paraMap);
			feedbackObj.Obj = returnMap;
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.IMPORT_TOWER_TERMINATION_SERVICE_INFO_SUCCESS+returnMap.get("successCount")+PromptMessageComm.DATAS+returnMap.get("errMsg");
		}catch (Exception e) {
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg=PromptMessageComm.NOT_VALID_EXCEL_TEMPLATE;
			e.printStackTrace();
		}
		return feedbackObj;
	}
	
	
	/**
	 * 导出终止信息
	 * @author yuefy
	 */
	@RequestMapping(value="/exportTowerStopServer")
	public void exportTowerStopServer(@ModelAttribute("user") UserLoginInfo loginUser,@RequestParam Map<String,Object> paraMap,HttpServletRequest request,HttpServletResponse response){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		
		feedbackObj.success = RESULT.SUCCESS_1;
		
		feedbackObj.msg=PromptMessageComm.SELECT_INFO_SUCCESS;

		paraMap.put("userId", loginUser.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("prvId", loginUser.getPrv_id());
		List<SysRegionVO> listreg=sysRegionService.getAddress(paraMap);//准备需要的数据处理
		paraMap.put("regId", request.getParameter("regId"));
		paraMap.put("pregId", request.getParameter("pregId"));
		List<String> regIds = loginUser.getReg_ids();
		paraMap.put("regIds", regIds);
		paraMap.put("alias", PromptMessageComm.ALIAS_NAME);
		List<TowerStopServerVO> list = towerStopServerService.queryTowerStopServer(paraMap);
		//注入的为list
		for(int i = 0 ; i < list.size() ; i++){
			List<TowerStopServerDateVO> towerStopServerDateVOList = new ArrayList<TowerStopServerDateVO>();
			TowerStopServerDateVO towerStopServerDateVO = new TowerStopServerDateVO();
			towerStopServerDateVO.setStartDate(list.get(i).getStartDate());
			towerStopServerDateVO.setEndDate(list.get(i).getEndDate());
			towerStopServerDateVOList.add(towerStopServerDateVO);
			list.get(i).setTowerStopServerDateVO(towerStopServerDateVOList);
		}
		TowerStopServerHandler tbh=new TowerStopServerHandler(listreg);//塔维终止服务自定义数据处理
		tbh.setNeedHandlerFields(new String[]{PromptMessageComm.COUNTY});//需要处理数据的列名 
		
		ExportParams params = new ExportParams();
		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TowerStopServerVO.class,list);
       
		FileUtils.downFile(workBook, PromptMessageComm.TERMINATE_SERVICE_ORDER_XLS, request, response);
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
	@RequestMapping(value = "/queryStopServerCheckInfo", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryStopServerCheckInfo(@ModelAttribute("user") UserLoginInfo loginUser,
			String regId, String pregId, int pageNumber, int pageSize,String taskDefKey) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String assignee=loginUser.getUser_loginname();
		List<String> list = loginUser.getRole_ids();
		List<String> regIds = loginUser.getReg_ids();
		paraMap.put("regIds", regIds);
		paraMap.put("alias", PromptMessageComm.ALIAS_NAME);
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_STOPSERVER_INFO[0]);
		act.setBusinessTable(ActUtils.PD_STOPSERVER_INFO[1]);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(StrUtil.isNotBlank(taskDefKey)){
			act.setTaskDefKey(taskDefKey);//设置根据环节Key过滤
		}
		/**
		 * 添加区域编码区分流程
		 */
		act.setRegCode(loginUser.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		FeedBackObject fdback = new FeedBackObject();
		fdback.msg =PromptMessageComm.NO_INFO;
		if(idsList.size() > 0){
			paraMap.put("idsList", idsList);
			paraMap.put("regId", regId);
			paraMap.put("userId", loginUser.getUser_id());
			paraMap.put("checkState", ContractStateComm.STATE_9);
			paraMap.put("pregId", pregId);
			fdback.success = RESULT.SUCCESS_1;
			//查询所有提交审核的
			fdback.Obj = towerStopServerService.queryTowerStopServer(paraMap, pageNumber, pageSize);
		}else{
			fdback.success = RESULT.FAIL_0;
			fdback.msg = PromptMessageComm.NOT_FIND_NEED_AUDIT_PROJECTS;
		}
		return fdback;
	}
	/**
	 * @description 导出服务终止审核数据
	 */
	@RequestMapping(value="/exportStopServerCheckInfo")
	public void exportStopServerCheckInfo(@ModelAttribute("user") UserLoginInfo loginUser,
			@RequestParam Map<String,Object> paraMap,HttpServletRequest request,HttpServletResponse response){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String assignee=loginUser.getUser_loginname();
		paraMap.put("userId", loginUser.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("prvId", loginUser.getPrv_id());
		List<SysRegionVO> listreg=sysRegionService.getAddress(paraMap);//准备需要的数据处理
		List<String> list = loginUser.getRole_ids();
		List<String> regIds = loginUser.getReg_ids();
		paraMap.put("regIds", regIds);
		paraMap.put("alias", "v_sys_region");
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_STOPSERVER_INFO[0]);
		act.setBusinessTable(ActUtils.PD_STOPSERVER_INFO[1]);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		/**
		 * 添加区域编码区分流程
		 */
		act.setRegCode(loginUser.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		FeedBackObject fdback = new FeedBackObject();
		fdback.msg =PromptMessageComm.NO_INFO;
		List<TowerStopServerVO>  TowerStopServerVOlist = new ArrayList<TowerStopServerVO>();
		if(idsList.size() > 0){
			paraMap.put("idsList", idsList);
			paraMap.put("userId", loginUser.getUser_id());
			paraMap.put("checkState", ContractStateComm.STATE_9);
			fdback.success = RESULT.SUCCESS_1;
			//查询所有提交审核的
			TowerStopServerVOlist = towerStopServerService.queryTowerStopServer(paraMap);
			//注入的为list
			for(int i = 0 ; i < TowerStopServerVOlist.size() ; i++){
				List<TowerStopServerDateVO> towerStopServerDateVOList = new ArrayList<TowerStopServerDateVO>();
				TowerStopServerDateVO towerStopServerDateVO = new TowerStopServerDateVO();
				towerStopServerDateVO.setStartDate(TowerStopServerVOlist.get(i).getStartDate());
				towerStopServerDateVO.setEndDate(TowerStopServerVOlist.get(i).getEndDate());
				towerStopServerDateVOList.add(towerStopServerDateVO);
				TowerStopServerVOlist.get(i).setTowerStopServerDateVO(towerStopServerDateVOList);
			}
		}
		TowerStopServerHandler tbh=new TowerStopServerHandler(listreg);//塔维终止服务自定义数据处理
		tbh.setNeedHandlerFields(new String[]{PromptMessageComm.COUNTY});//需要处理数据的列名 
		ExportParams params = new ExportParams();
		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TowerStopServerVO.class,TowerStopServerVOlist);
        FileUtils.downFile(workBook, PromptMessageComm.TERMINATE_SERVICE_ORDER_XLS, request, response);
	}
	/**
	 * 提交审核
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value ="/deleteCheckInfo",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject deleteCheckInfo(@ModelAttribute("user") UserLoginInfo loginUser, 
			@RequestBody List<Map<String,Object>> ids,HttpServletRequest request) throws ParseException{
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(PromptMessageComm.DATE_TYPE_24H);
		FeedBackObject feedbk = new FeedBackObject();
		int sucCount = 0;
		int errCount = 0;
		for(Map<String,Object> map:ids){
			String taskid=map.get("taskId").toString();
			Task t=actTaskService.getTask(taskid);
			if(t != null){
				Date EndDate = sdf.parse(map.get("endDate")+PromptMessageComm.PARAMETER_TIME_BEFORE);
				String stopServerId = (String) map.get("stopServerId");
				Map<String,Object> vars=new HashMap<String,Object>();
				String isverify = (String) map.get("isverify");
				vars.put("state", isverify);
				String auditor = (String) map.get("auditor");
				if(StrUtil.isNotBlank(auditor)){
					vars.put("nextUserId", auditor);//指定下一环节审核人
				}
				String user_loginname = loginUser.getUser_loginname();
				if(StrUtil.isNotBlank(user_loginname)){
					vars.put("currUserId", user_loginname);//指定当前审核人
				}
				actTaskService.completeByBusiness(ActUtils.PD_STOPSERVER_INFO[1], stopServerId, map.get("auditRemark").toString(), ActUtils.PD_STOPSERVER_INFO[2], vars);
				Task newtask = actTaskService.getTask(ActUtils.PD_STOPSERVER_INFO[1], stopServerId);
				//newtask为空,审核完成
				if(newtask==null){
					//判断审核是否通过
					//审核通过
					if(isverify.equals(ActivityStateComm.STATE_NORMAL+"")){
						sucCount += 1;
						//审核完成修改铁塔服务终止数据状态为审核完成
						towerStopServerService.updateCheckStateById(stopServerId,ActivityStateComm.STATE_NORMAL);
						//根据站址编码和业务确认单号查询移动起租信息id
						String rentInformationId = twrRentInformationService.queryTwrRentInformationByTower(map.get("businessConfirmNumber").toString(), map.get("towerStationCode").toString());
						//根据id修改起租信息endDate
						twrRentInformationService.updateEndDateByStopServer(rentInformationId, EndDate);
						//根据移动起租信息id查询拆分表信息
						List<TowerRentInformationHistoryVO> trihList = twrRentInformationHistoryService.queryAllHistoryById(rentInformationId);
						//获取仍然生效的最后日期
						Calendar cal=Calendar.getInstance();
						cal.setTime(EndDate);
						cal.add(Calendar.DATE, StateComm.STATE__1);  //减1天
						Date newEndDate = cal.getTime();
						//删除生效日期在终止时间之后的拆分数据
						twrRentInformationHistoryService.deleteHistoryByDate(rentInformationId, EndDate);
						//判断服务终止日期在哪个拆分数据区间
						for(int  i = 0;i < trihList.size();i++){
							//如果在某条拆分数据期间内
							if(EndDate.getTime() < trihList.get(i).getEndDate().getTime() &&
									EndDate.getTime() >= trihList.get(i).getStartDate().getTime()){
								//修改当前记录终止日期
								twrRentInformationHistoryService.updateEndDateById(newEndDate,trihList.get(i).getRentinformationhistoryId());
							}
						}
					}else{
						//审核不通过
						errCount += 1;
						//审核完成修改铁塔侧该数据状态为审核退回
						towerStopServerService.updateCheckStateById(stopServerId,ActivityStateComm.STATE_UNAPPROVE);
					}
				}
			}
		}
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.msg = loginUser.getUser_loginname()+PromptMessageComm.COMMIT_AUDIT_SUCCESS+sucCount+PromptMessageComm.DATAS+PromptMessageComm.COMMIT_AUDIT_FAILED+errCount+PromptMessageComm.DATAS;
		// 添加系统日志
		log.info(SysLogComm.LOG_Operate, feedbk.msg);
		/*if(feedbk.success.equals(RESULT.SUCCESS_1)){
			feedbk.msg = loginUser.getUser_loginname()+"：铁塔服务终止审核通过";
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		}else{
			feedbk.msg = loginUser.getUser_loginname()+"：铁塔服务终止审核未通过";
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}	*/
		return feedbk;
	}
	/**
	 *  根据id查询信息
	 * @return
	 */
	@RequestMapping(value = "/queryStopServerById", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryStopServerById(String stopServerId) {
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = towerStopServerService.queryTowerStopServerById(stopServerId);
		return fdback;
	}
}
