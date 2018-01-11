 package com.xunge.controller.towerrent.rentinformationbizchange;

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

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;

import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.PropertiesLoader;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.bizchange.TowerRentinformationBizchangeVO;
import com.xunge.model.towerrent.rentmanager.TowerRentInformationHistoryVO;
import com.xunge.model.towerrent.rentmanager.TowerResourceInfoVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.towerrent.mobile.ITwrRentInformationService;
import com.xunge.service.towerrent.rentinformationbizchange.ITwrRentInformationBizChangeService;
import com.xunge.service.towerrent.rentinformationhistory.ITwrRentInformationHistoryService;
import com.xunge.service.towerrent.stopserver.exceldatahandler.TowerStopServerHandler;
import com.xunge.service.twrrent.rentinformationbizchange.exceldatahandler.TowerChangeInfoHandler;
import com.xunge.service.twrrent.resourceinfo.ITowerResourceInfoService;

/**
 * 铁塔信息变更表
 * @author changwq
 *
 */
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
@RequestMapping("/asserts/tpl/towerrent/rentmanage")
public class TwrRentInformationbizChangeController extends BaseException{

	@Autowired
	private ITwrRentInformationBizChangeService twrRentInformationBizChangeService;
	
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
	
	private String item;
	/**
	 * 铁塔信息变更表提交审核
	 * @param loginUser
	 * @param state
	 * @param comment
	 * @param leader
	 * @param rentcontractId
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value ="/updateCheckInfo",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject updateCheckInfo(@ModelAttribute("user") UserLoginInfo loginUser, 
			@RequestBody List<Map<String,Object>> ids,HttpServletRequest request) throws ParseException{
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
				String twrRentinformationBizchangeId = (String) map.get("twrRentinformationBizchangeId");
				Map<String,Object> vars=new HashMap<String,Object>();
				String checkState = (String) map.get("checkState");
				vars.put("state", checkState);
				String leader = (String) map.get("leader");
				if(StrUtil.isNotBlank(leader)){
					vars.put("nextUserId", leader);//指定下一环节审核人
				}
				String user_loginname = loginUser.getUser_loginname();
				if(StrUtil.isNotBlank(user_loginname)){
					vars.put("currUserId", user_loginname);//指定当前审核人
				}
				actTaskService.completeByBusiness(ActUtils.PD_BIZCHANGE_INFO[1], twrRentinformationBizchangeId, map.get("auditRemark").toString(), ActUtils.PD_BIZCHANGE_INFO[2], vars);
				//如果newtask为null，审核完成
				Task newtask = actTaskService.getTask(ActUtils.PD_BIZCHANGE_INFO[1], twrRentinformationBizchangeId);
				if(newtask==null){
					if(checkState.equals(ActivityStateComm.STATE_NORMAL+"")){
						sucCount += 1;
						SimpleDateFormat sdf = new SimpleDateFormat(PromptMessageComm.DATE_TYPE_24H);
						Date startDate = sdf.parse((String) map.get("changeActiveDate"));
						//审核完成修改铁塔信息变更表该数据状态为审核完成
						twrRentInformationBizChangeService.updateBizChangeCheckState(twrRentinformationBizchangeId,ActivityStateComm.STATE_NORMAL);
						//根据信息变更单站址编码和业务确认单号获取移动起租信息id
						String rentinformationId = twrRentInformationService.queryTwrRentInformationByTower(map.get("businessConfirmNumber").toString(), map.get("towerStationCode").toString());
						//根据铁塔信息变更表chengeitem字段获取数据库字段
					    PropertiesLoader prop = new PropertiesLoader(PromptMessageComm.TWR_BIZCHANGE_ITEM_PROPERTIES);
						item = prop.getProperty(map.get("changeItem").toString()); 
						//根据移动起租id更新移动起租信息
						twrRentInformationService.updateTwrRentInformationByBizChange(item, map.get("changeBehindContent").toString(), rentinformationId);
						//获取上一条拆分表结束时间
						Calendar cal=Calendar.getInstance();
						cal.setTime(startDate);
						cal.add(Calendar.DATE, -1);  //减1天
						Date endDate = cal.getTime();
						//查找所有拆分记录
						List<TowerRentInformationHistoryVO> triVOList = twrRentInformationHistoryService.queryAllHistoryById(rentinformationId);
						for(int i=0;i<triVOList.size();i++){
							//如果生效日期等于某一条拆分信息的期始日期
							if(triVOList.get(i).getStartDate() == startDate){
								//修改这一条数据的变更字段
								twrRentInformationHistoryService.updateChangeItemById(item,triVOList.get(i).getRentinformationhistoryId(), map.get("changeBehindContent").toString());
							}
							//如果生效日期在某条拆分信息期间
							else if(startDate.getTime() > triVOList.get(i).getStartDate().getTime() &&
									startDate.getTime() < triVOList.get(i).getEndDate().getTime()){
								//获取数据日期重复的拆分纪录对象
								TowerRentInformationHistoryVO towerRentInformationHistoryVO = triVOList.get(i);
								//修改当前记录终止日期
								twrRentInformationHistoryService.updateEndDateById(endDate, towerRentInformationHistoryVO.getRentinformationhistoryId());
								//新建一个拆分记录的Id
								String historyId = SysUUID.generator();
								towerRentInformationHistoryVO.setRentinformationhistoryId(historyId);
								towerRentInformationHistoryVO.setStartDate(startDate);
								//复制一条之前修改的数据到拆分记录
								twrRentInformationHistoryService.insertSelective(towerRentInformationHistoryVO);
								//修改变更字段
								twrRentInformationHistoryService.updateChangeItemById(item,historyId,map.get("changeBehindContent").toString());
							}
						}
					}else{
						errCount += 1;
						//审核完成修改铁塔信息变更表该数据状态为审核退后
						twrRentInformationBizChangeService.updateBizChangeCheckState(twrRentinformationBizchangeId,ActivityStateComm.STATE_UNAPPROVE);
					}
				}
			}
		}
		feedbk.success = RESULT.SUCCESS_1;
		feedbk.msg = loginUser.getUser_loginname()+PromptMessageComm.COMMIT_AUDIT_SUCCESS+sucCount+PromptMessageComm.DATAS+PromptMessageComm.COMMIT_AUDIT_FAILED+errCount+PromptMessageComm.DATAS;
		// 添加系统日志
		log.info(SysLogComm.LOG_Operate, feedbk.msg);
		/*if(feedbk.success.equals(RESULT.SUCCESS_1)){
			feedbk.msg = loginUser.getUser_loginname()+"：铁塔信息变更审核通过";
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		}else{
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = loginUser.getUser_loginname()+"：铁塔信息变更审核未通过";
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}	*/
		return feedbk;
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
	@RequestMapping(value = "/queryTwrRentInformationBizChangeCheckInfo", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryTwrRentInformationBizChangeCheckInfo(@ModelAttribute("user") UserLoginInfo loginUser,
			String regId, String pregId, int pageNumber, int pageSize,String taskDefKey) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String assignee=loginUser.getUser_loginname();
		List<String> list = loginUser.getRole_ids();
		List<String> regIds = loginUser.getReg_ids();
		paraMap.put("alias", PromptMessageComm.ALIAS_NAME);
		paraMap.put("regIds", regIds);
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_BIZCHANGE_INFO[0]);
		act.setBusinessTable(ActUtils.PD_BIZCHANGE_INFO[1]);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(StrUtil.isNotBlank(taskDefKey)){
			act.setTaskDefKey(taskDefKey);//设置根据环节Key过滤
		}
		/**
		 * 添加区域编码区分流程
		 */
		FeedBackObject fdback = new FeedBackObject();
		act.setRegCode(loginUser.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		fdback.msg =PromptMessageComm.NO_INFO;
		if(idsList.size() > 0){
			paraMap.put("idsList", idsList);
			paraMap.put("regId", regId);
			paraMap.put("userId", loginUser.getUser_id());
			paraMap.put("checkState", StateComm.STATE_9);
			paraMap.put("pregId", pregId);
			fdback.success = RESULT.SUCCESS_1;
			fdback.Obj = twrRentInformationBizChangeService.queryInformationBizChangeCheckInfo(paraMap, pageNumber, pageSize);
		}else{
			fdback.success = RESULT.FAIL_0;
			fdback.msg = PromptMessageComm.NOT_FIND_AUDIT_OBDATAS;
		}
		return fdback;
	}
	/**
	 * @description 导出资源变更审核数据
	 */
	@RequestMapping(value="/exportTwrRentInformationBizChangeCheckInfo")
	public void exportTwrRentInformationBizChangeCheckInfo(@ModelAttribute("user") UserLoginInfo loginUser,
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
		paraMap.put("alias", "v_sys_region");
		paraMap.put("regIds", regIds);
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_BIZCHANGE_INFO[0]);
		act.setBusinessTable(ActUtils.PD_BIZCHANGE_INFO[1]);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		/**
		 * 添加区域编码区分流程
		 */
		FeedBackObject fdback = new FeedBackObject();
		act.setRegCode(loginUser.getPrv_code());//区分省份
		List<Act> idsList = actTaskService.todoList(act);
		fdback.msg =PromptMessageComm.NO_INFO;
		List<TowerRentinformationBizchangeVO> TowerRentinformationBizchangeVOlist = new ArrayList<TowerRentinformationBizchangeVO>();
		if(idsList.size() > 0){
			paraMap.put("idsList", idsList);
			paraMap.put("userId", loginUser.getUser_id());
			paraMap.put("checkState", StateComm.STATE_9);
			fdback.success = RESULT.SUCCESS_1;
			TowerRentinformationBizchangeVOlist = twrRentInformationBizChangeService.queryTowerRentinformationBizchangeInfo(paraMap);
		}
		TowerChangeInfoHandler tbh=new TowerChangeInfoHandler(listreg);//塔维变更信息自定义数据处理
		tbh.setNeedHandlerFields(new String[]{PromptMessageComm.COUNTY});//需要处理数据的列名 
		ExportParams params = new ExportParams();
		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TowerRentinformationBizchangeVO.class,TowerRentinformationBizchangeVOlist);
        FileUtils.downFile(workBook, DateDisposeComm.BUSINESS_UPDATE_REVIEW_SINGLE, request, response);
	}
	/**
	 *  根据id查询信息
	 * @return
	 */
	@RequestMapping(value = "/queryBizChangeById", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryBizChangeById(String twrRentinformationBizchangeId) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("twrRentinformationBizchangeId",twrRentinformationBizchangeId);
		FeedBackObject fdback = new FeedBackObject();
		fdback.success = RESULT.SUCCESS_1;
		fdback.Obj = twrRentInformationBizChangeService.queryBizChangeById(paraMap);
		return fdback;
	}
	/**
	 * 获取用户区域信息
	 */
	@RequestMapping(value = "/getAddressBizCheck", method = RequestMethod.POST)
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
