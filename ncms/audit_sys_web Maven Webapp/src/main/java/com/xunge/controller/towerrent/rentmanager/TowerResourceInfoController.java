package com.xunge.controller.towerrent.rentmanager;

import java.util.ArrayList;
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
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.comm.tower.resourceinfo.TowerComm;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.rentmanager.TowerResourceInfoVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.towerrent.mobile.ITwrRentInformationService;
import com.xunge.service.towerrent.rentinformationhistory.ITwrRentInformationHistoryService;
import com.xunge.service.towerrent.stopserver.exceldatahandler.TowerStopServerHandler;
import com.xunge.service.twrrent.resourceinfo.ITowerResourceInfoService;
import com.xunge.service.twrrent.resourceinfo.exceldatahandler.TowerResourceInfoHandler;
/**
 * @description 铁塔资源信息controller
 * @author yuefy
 * @date 创建时间：2017年8月1日
 */
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@RequestMapping("/asserts/tpl/towerrent/rentmanage")
@Controller
public class TowerResourceInfoController extends BaseException {
	
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
	
	@Autowired
	private ISysRegionService sysRegionService;
	
	@Autowired
	private ILogService log;
	
	/**
	 * 提交审核
	 * @return
	 */
	@RequestMapping(value ="/insertCheckInfo",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertCheckInfo(@ModelAttribute("user") UserLoginInfo loginUser, 
			@RequestBody List<Map<String,Object>> ids,HttpServletRequest request){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		int sucCount = 0;
		int errCount = 0;
		for(Map<String,Object> map:ids){
			String taskid=map.get("taskId").toString();
			Task t=actTaskService.getTask(taskid);
			if(t != null){
				String rentinformationtowerId = (String) map.get("rentinformationtowerId");
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
				actTaskService.completeByBusiness(ActUtils.PD_RESOURCE_INFO[1], rentinformationtowerId,map.get("auditRemark").toString(), ActUtils.PD_RESOURCE_INFO[2], vars);
				//newtask为空,审核完成
				Task newtask = actTaskService.getTask(ActUtils.PD_RESOURCE_INFO[1], rentinformationtowerId);
				//判断审核是否通过
				//审核通过
				if(newtask==null){
					if(isverify.equals(ActivityStateComm.STATE_NORMAL+PromptMessageComm.KONG_SYMBOL)){
						sucCount += 1;
						//审核完成修改铁塔侧该数据状态为审核完成
						towerResourceInfoService.updateCommit(rentinformationtowerId,ActivityStateComm.STATE_NORMAL);
						//新建一个移动侧信息id
						String rentinformationId = SysUUID.generator();
						//复制铁塔侧信息到移动侧
						twrRentInformationService.insertTwrRentInformationFromTwrRentInformationTower(rentinformationId,rentinformationtowerId);
						//新建拆分信息表id
						String rentinformationhistoryId = SysUUID.generator();
						//复制移动起租信息到拆分信息表
						twrRentInformationHistoryService.insertRentInformationHistoryFromMobile(rentinformationhistoryId, rentinformationId);
						//26-33无塔id数组
						String[] list = TowerComm.NUMBER_LIST;
						//根据移动起租信息id查询配置id
						String configId = twrRentInformationService.queryProConfigIdById(rentinformationId);
						//判断有无配置参数
						if(configId != null && configId != PromptMessageComm.KONG_SYMBOL){
							//判断该条起租信息有没有塔
							for(int i = 0;i < list.length;i++){
								if(configId.equals(list[i])){
									//如果有塔，则根据配置id查询铁塔种类id
									String typeId = twrRentInformationService.queryTypeIdByConfigId(configId);
									//准备数据
									String nottowerconfigId = SysUUID.generator();
									//向无塔配置表中插入数据（站址编码，业务确认单号，铁塔种类id）
									twrRentInformationService.insertNoTowerConfig(nottowerconfigId, typeId,map.get("towerStationCode").toString(),map.get("businessConfirmNumber").toString());
								}
							}
						}
					}
					else{
						//审核不通过
						errCount += 1;
						//审核完成修改铁塔侧该数据状态为审核退回
						towerResourceInfoService.updateCommit(rentinformationtowerId,ActivityStateComm.STATE_UNAPPROVE);
						feedbk.success = RESULT.FAIL_0;
					}
				}
			}
		}
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.msg = loginUser.getUser_loginname()+PromptMessageComm.COMMIT_AUDIT_SUCCESS+sucCount+PromptMessageComm.DATAS+PromptMessageComm.COMMIT_AUDIT_FAILED+errCount+PromptMessageComm.DATAS;
		// 添加系统日志
		log.info(SysLogComm.LOG_Operate, feedbk.msg);
		/*if(feedbk.success.equals(RESULT.SUCCESS_1)){
			feedbk.msg = loginUser.getUser_loginname()+"：铁塔信息审核通过";
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		}else{
			feedbk.msg = loginUser.getUser_loginname()+"：铁塔信息审核未通过";
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}	*/
		return feedbk;
	}
	
	
	/**
	 * 根据id查询铁塔资源信息
	 */
	@RequestMapping(value = "/queryTowerResourceInfoVOById", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryTowerResourceInfoVOById(String rentinformationtowerId) {
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = towerResourceInfoService.queryTowerResourceInfoVOById(rentinformationtowerId);
		return fdback;
	}

	/**
	 * @description 查询所有铁塔资源信息并显示
	 * @author yuefy
	 * @date 创建时间：2017年8月1日
	 */
	@RequestMapping(value = "/queryTowerResourceInfo", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryTowerResourceInfo(@ModelAttribute("user") UserLoginInfo loginUser,
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
		fdback.Obj = towerResourceInfoService.queryTowerResourceInfo(paraMap,pageNumber,pageSize);
		return fdback;
	}

	/**
	 * 
	 * @description 导入铁塔资源信息
	 * @author yuefy
	 * @date 创建时间：2017年8月1日
	 */
	@RequestMapping(value = "/importTowerResourceInfo", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject importTowerResourceInfo(@ModelAttribute("user") UserLoginInfo loginUser,HttpServletRequest request,String suffix,
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
		List<String> regIds = loginUser.getReg_ids();
		paraMap.put("regIds", regIds);
		paraMap.put("alias", PromptMessageComm.ALIAS_NAME);
		feedbackObj.success = RESULT.SUCCESS_1;
		try {
			Map<String,Object> returnMap= towerResourceInfoService.importTowerResourceInfo(file,suffix,request,paraMap);
			feedbackObj.Obj = returnMap;
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.IMPORT_TOWERRENT_RESOURCE_SUCCESS+returnMap.get("successCount")+PromptMessageComm.DATAS+returnMap.get("errMsg");
		}catch (Exception e) {
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg=PromptMessageComm.NOT_VALID_EXCEL_TEMPLATE;
			e.printStackTrace();
		}
		return feedbackObj;
	}
	
	/**
	 * @description 导出铁塔账单数据
	 * @author yuefy
	 * @date 创建时间：2017年8月1日
	 */
	@RequestMapping(value="/exportTowerResourceInfo")
	public void exportTowerResourceInfo(@ModelAttribute("user") UserLoginInfo loginUser,@RequestParam Map<String,Object> paraMap,HttpServletRequest request,HttpServletResponse response){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		
		feedbackObj.success = RESULT.SUCCESS_1;
		
		feedbackObj.msg=PromptMessageComm.SELECT_INFO_SUCCESS;
		List<String> regIds = loginUser.getReg_ids();
		paraMap.put("regIds", regIds);
		paraMap.put("alias", PromptMessageComm.ALIAS_NAME);
		paraMap.put("userId", loginUser.getUser_id());
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("prvId", loginUser.getPrv_id());
		List<SysRegionVO> listreg=sysRegionService.getAddress(paraMap);//准备需要的数据处理
		paraMap.put("regId", request.getParameter("regId"));
		paraMap.put("pregId", request.getParameter("pregId"));
		List<TowerResourceInfoVO> list = towerResourceInfoService.queryTowerResourceInfo(paraMap);
		TowerResourceInfoHandler tbh=new TowerResourceInfoHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{PromptMessageComm.COUNTY,DateDisposeComm.SHARE_INFO});//需要处理数据的列名 
		ExportParams params = new ExportParams();

		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TowerResourceInfoVO.class,list);
        FileUtils.downFile(workBook, PromptMessageComm.RENT_BILLS_XLS, request, response);
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
	@RequestMapping(value = "/queryTowerResourceCheckInfo", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryTowerResourceCheckInfo(@ModelAttribute("user") UserLoginInfo loginUser,
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
		act.setProcDefKey(ActUtils.PD_RESOURCE_INFO[0]);
		act.setBusinessTable(ActUtils.PD_RESOURCE_INFO[1]);
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
			paraMap.put("checkState", ActivityStateComm.STATE_AUDIT);
			paraMap.put("pregId", pregId);
			fdback.success = RESULT.SUCCESS_1;
			fdback.Obj = towerResourceInfoService.queryTowerResourceInfo(paraMap, pageNumber, pageSize);
		}else{
			fdback.success = RESULT.FAIL_0;
			fdback.msg = PromptMessageComm.NOT_FIND_NEED_AUDIT_PROJECTS;
		}
		return fdback;
	}
	/**
	 * @description 导出铁塔账单审核数据
	 */
	@RequestMapping(value="/exportTowerResourceCheckInfo")
	public void exportTowerResourceCheckInfo(@ModelAttribute("user") UserLoginInfo loginUser,
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
		paraMap.put("alias", PromptMessageComm.ALIAS_NAME);
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_RESOURCE_INFO[0]);
		act.setBusinessTable(ActUtils.PD_RESOURCE_INFO[1]);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		/**
		 * 添加区域编码区分流程
		 */
		act.setRegCode(loginUser.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		FeedBackObject fdback = new FeedBackObject();
		fdback.msg =PromptMessageComm.NO_INFO;
		List<TowerResourceInfoVO> TowerResourceInfoVOlist = new ArrayList<TowerResourceInfoVO>();
		if(idsList.size() > 0){
			paraMap.put("idsList", idsList);
			paraMap.put("userId", loginUser.getUser_id());
			paraMap.put("checkState", ActivityStateComm.STATE_AUDIT);
			fdback.success = RESULT.SUCCESS_1;
			TowerResourceInfoVOlist = towerResourceInfoService.queryTowerResourceInfo(paraMap);
		}
		TowerResourceInfoHandler tbh=new TowerResourceInfoHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{PromptMessageComm.COUNTY,DateDisposeComm.SHARE_INFO});//需要处理数据的列名
		ExportParams params = new ExportParams();
		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TowerResourceInfoVO.class,TowerResourceInfoVOlist);
        FileUtils.downFile(workBook, PromptMessageComm.RENT_BILLS_XLS, request, response);
	}
	/**
	 * 获取用户区域信息
	 */
	@RequestMapping(value = "/getAddressComCheck", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject getAddressComCheck(@ModelAttribute("user") UserLoginInfo loginUser) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String userId = loginUser.getUser_id();
		paraMap.put("state", StateComm.STATE_0);
		paraMap.put("userId", userId);
		FeedBackObject fdback = new FeedBackObject();
		fdback.Obj = sysRegionService.queryManaRegions(paraMap);
		return fdback;
	}
}
