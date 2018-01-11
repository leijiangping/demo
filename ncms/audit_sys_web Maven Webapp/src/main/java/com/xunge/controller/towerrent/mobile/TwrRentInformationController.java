package com.xunge.controller.towerrent.mobile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.mobile.TwrRentInformationVO;
import com.xunge.model.towerrent.rentinformation.TwrRentInformationChangeVO;
import com.xunge.model.towerrent.rentmanager.TowerRentInformationHistoryVO;
import com.xunge.model.towerrent.stopserver.TowerStopServerDateVO;
import com.xunge.model.towerrent.stopserver.TowerStopServerVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.towerrent.mobile.ITwrRentInformationService;
import com.xunge.service.towerrent.rentinformationhistory.ITwrRentInformationHistoryService;
import com.xunge.service.towerrent.stopserver.exceldatahandler.TowerStopServerHandler;
import com.xunge.service.twrrent.resourceinfo.ITowerResourceInfoService;

/**
 * 
 * @author xup
 *
 */
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@RequestMapping("/asserts/tpl/towerrent/mobilemanage")
@Controller
public class TwrRentInformationController extends BaseException{
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

	private Object String ;
	
	@Autowired
	private ISysRegionService sysRegionService;
	
	@Autowired
	private ILogService log;
	
	/**
	 * 移动资源信息查询
	 * @author xup
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/queryTwrRentInformation",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryTwrRentInformation(@ModelAttribute("user") UserLoginInfo loginUser,
			String regId, String pregId,String resourcesTypeId,String checkState,int pageNum,int pageSize){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject backObject=new FeedBackObject();
		Map<String,Object> paraMap = new HashMap<String,Object>();
			List<String> regIds = loginUser.getReg_ids();
			paraMap.put("regIds", regIds);
			paraMap.put("alias", PromptMessageComm.ALIAS_NAME);
			paraMap.put("regId", regId);
			paraMap.put("userId", loginUser.getUser_id());
			paraMap.put("state", StateComm.STATE_str0);
			paraMap.put("pregId", pregId);
			paraMap.put("checkState", checkState);
			paraMap.put("resourcesTypeId", resourcesTypeId);
			backObject.Obj=twrRentInformationService.queryTwrRentInformation(paraMap, pageNum, pageSize);
		return backObject;
	}
	/**
	 * 导出移动资源信息
	 *//*
	@RequestMapping(value="/exportTwrRentInformation")
	public void exportTwrRentInformation(@ModelAttribute("user") UserLoginInfo loginUser,@RequestParam Map<String,Object> paraMap,HttpServletRequest request,HttpServletResponse response){
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
			paraMap.put("state", StateComm.STATE_str0);
		List<TwrRentInformationVO> list = twrRentInformationService.queryTwrRentInformationList(paraMap);
		ExportParams params = new ExportParams();
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TwrRentInformationVO.class,list);
       
		FileUtils.downFile(workBook, PromptMessageComm.RENT_BILLS_XLS, request, response);
	}*/
	/**
	 * 移动资源信息审核页面查询
	 * @author xup
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/queryTwrRentInformationCheck",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryTwrRentInformationCheck(@ModelAttribute("user") UserLoginInfo loginUser,
			String regId, String pregId,String checkState,int pageNum,int pageSize,String taskDefKey){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject backObject=new FeedBackObject();
		Map<String,Object> paraMap = new HashMap<String,Object>();
		String assignee=loginUser.getUser_loginname();
		List<String> lists = loginUser.getRole_ids();
		List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(lists);
		Act act=new Act();
		act.setProcDefKey(ActUtils.PD_INFORMATION_INFO [0]);
		act.setBusinessTable(ActUtils.PD_INFORMATION_INFO [1]);
		act.setAssignee(assignee);
		/**
		 * 添加区域编码区分流程
		 *@author xup  2017/7/27 17:42
		 *
		 */
		act.setRegCode(loginUser.getPrv_code());//区分省份
		act.setAssigneeNameGroup(assigneeNameGroup);
		if(StrUtil.isNotBlank(taskDefKey)){
			act.setTaskDefKey(taskDefKey);//设置根据环节Key过滤
		}
		List<Act> idsList = actTaskService.todoList(act);
		Page<?> pages=new Page<>(pageNum, pageSize);
		backObject.Obj=pages;
		if(idsList.size()>0){
			List<String> regIds = loginUser.getReg_ids();
			paraMap.put("regIds", regIds);
			paraMap.put("alias", PromptMessageComm.ALIAS_NAME);
			paraMap.put("idsList", idsList);
			paraMap.put("regId", regId);
			paraMap.put("userId", loginUser.getUser_id());
			paraMap.put("state", StateComm.STATE_str0);
			paraMap.put("pregId", pregId);
			paraMap.put("checkState", checkState);
			
			Page<TwrRentInformationVO> page = twrRentInformationService.queryTwrRentInformation(paraMap, pageNum, pageSize);
			if(paraMap.get("idsList")!=null){
				List<TwrRentInformationVO> list=Lists.newArrayList();
				for(TwrRentInformationVO rentInformation:page.getResult()){
					for(Act acts:(List<Act>)paraMap.get("idsList")){
							if(rentInformation.getRentinformationId()!=null&&rentInformation.getRentinformationId().equals(acts.getBusinessId())){
								rentInformation.setAct(acts);
								list.add(rentInformation);
							}
					}
				}
				page.setResult(list);
			}
			
			backObject.Obj=page;
		}
		
		return backObject;
	}
	
	/**
	 * 获取当前审核人
	 * @author xup
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
	 * 审核流程中提交审核
	 * @author xup
	 * @param loginUser
	 * @param state
	 * @param comment
	 * @param leader
	 * @param rentcontractId
	 * @return
	 */
	@RequestMapping(value ="/saveCheckInfo",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertCheckInfo(@ModelAttribute("user") UserLoginInfo loginInfo,@RequestBody List<Map<String,Object>> list){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbk = new FeedBackObject();
		for(Map<String,Object> map:list){
			String taskid=map.get("taskId").toString();
			Task t=actTaskService.getTask(taskid);
			if(t!=null){
				Map<String,Object> vars=new HashMap<String,Object>();
				vars.put("state",map.get("state").toString());
				SimpleDateFormat  fmt =new SimpleDateFormat(PromptMessageComm.DATE_TYPE_24H);
				Date date=null;
				try {
					date = fmt.parse(map.get("endDate").toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(map.get("leader")!=null&&StrUtil.isNotBlank(map.get("leader").toString())){
					vars.put("nextUserId", map.get("leader").toString());//指定下一环节审核人
				}
				String user_loginname = loginInfo.getUser_loginname();
				if(StrUtil.isNotBlank(user_loginname)){
					vars.put("currUserId", user_loginname);//指定当前审核人
				}
				actTaskService.completeByBusiness(ActUtils.PD_INFORMATION_INFO[1], map.get("rentinformationId").toString(), map.get("comment").toString(), ActUtils.PD_INFORMATION_INFO[2], vars);
				Task newtask = actTaskService.getTask(ActUtils.PD_INFORMATION_INFO[1], map.get("rentinformationId").toString());
				feedbk.success = RESULT.SUCCESS_1;
				if(newtask==null){
					if(map.get("state")!=null&&map.get("state").toString().equals(ActivityStateComm.STATE_UNAPPROVE+"")){
						Map<String, Object> hashMap = new HashMap<String, Object>();
						hashMap.put("rentinformationId", map.get("rentinformationId").toString());
						hashMap.put("state", ActivityStateComm.STATE_AUDIT);
						hashMap.put("endDate", date);
						//修改变更表审核状态为未通过
						twrRentInformationService.updateRentinformationChange(hashMap);
						// 还原拆分表数据
						twrRentInformationService.updateToTowerRentInformationHistoryVO(hashMap);
					}else if(map.get("state")!=null&&map.get("state").toString().equals(ActivityStateComm.STATE_NORMAL+"")){
						//修改变更表、拆分表审核状态
						twrRentInformationService.updateCommit(map.get("rentinformationId").toString(),date);
						//修改起租表信息
						Map<String,Object> maps=new HashMap<String,Object>();
						maps.put("rentinformationId", map.get("rentinformationId").toString());
						maps.put("state", ActivityStateComm.STATE_AUDIT);
						maps.put("endDate", date);
						twrRentInformationService.updateRentinformation(maps);
					}
				}
				
			}
			
		}
		if(feedbk.success.equals(RESULT.SUCCESS_1)){
			feedbk.msg = loginInfo.getUser_loginname()+PromptMessageComm.SAVE_INFO_SUCCESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbk.msg);
		}else{
			feedbk.success = RESULT.FAIL_0;
			feedbk.msg = loginInfo.getUser_loginname()+PromptMessageComm.SAVE_INFO_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbk.msg);
		}	
		return feedbk;
	}
	
	

	
	/**
	 * 修改起租信息，修改的表是history表，如果生效日期发生变化则新增一条
	 * 如果不变则对原数据进行更新
	 * @author jiacy
	 * @param loginInfo
	 * @param twrRentinfoVO
	 * @return
	 */
	@RequestMapping(value = "/updateTwrRentInfoChange", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject updateTwrRentInformation(
			@ModelAttribute("user") UserLoginInfo loginInfo,
			List<TwrRentInformationChangeVO> twrRentInfoChangeVO) {
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject backObject = new FeedBackObject();
		// 1.判断生效日期是否更改过
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("twrRentInfoChangeList", twrRentInfoChangeVO);
		backObject.success = twrRentInformationService
				.updateTwrRentInformationTemp(paramMap);
		OperateUtil.echoBeahivor(backObject, log, PromptMessageComm.OPERATION_SUSSESS, PromptMessageComm.OPERATION_FAILED);
		return backObject;
	}
	
	/**
	 * 查询起租表下边挂的拆分表信息
	 * @author jiacy
	 * @param loginInfo
	 * @param twrRentInfoId
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value="/queryTwrHistoryByrentId",method=RequestMethod.POST)
	public @ResponseBody
	FeedBackObject queryTwrHistoryByrentId(
			@RequestParam String twrRentInfoId, int pageSize, int pageNum) {
		FeedBackObject backObject = new FeedBackObject();
		backObject.Obj = twrRentInformationService.queryTwrInfoHistoryVO(
				twrRentInfoId, pageNum, pageSize);
		return backObject;
	}
	
	/**
	 * 根据ID查询拆分表信息
	 * @author jiacy
	 * @param loginInfo
	 * @param twrRentInfoId
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value="/queryTwrHistoryById",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryTwrHistoryById(@RequestParam String twrRentHistoryId){
		FeedBackObject backObject = new FeedBackObject();
		backObject.Obj = twrRentInformationService.queryHistoryVOByid(twrRentHistoryId);
		return backObject;
	}
	
	/**
	 * 修改移资源信息表历史记录，此处暂定传递VO和List<VO>若无法实现则拆分为两个接口
	 * 拆成两个接口的话则如果有拆分的记录，新拆分的那条的id需要从前台传入可以保证两个接口的一致性
	 * @author jiacy
	 * @param loginInfo
	 * @param historyVO
	 * @param effectDateChange
	 * @return
	 */
	@RequestMapping(value = "/addRentInfoHistoryVO", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject addRentInfoHistoryVO(@ModelAttribute("user") UserLoginInfo loginUser,
			@RequestParam boolean effectDateChange,
			@RequestBody Map paramMap) {
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		Object objHistory = paramMap.get("historyVO");
		Object objChange = paramMap.get("twrRentInfoChangeVO");
		Object jsonHistory = JSONObject.toJSON(objHistory);
		Object jsonChange = JSONObject.toJSON(objChange);
		TowerRentInformationHistoryVO historyVO = JSONObject.toJavaObject((JSON)jsonHistory, TowerRentInformationHistoryVO.class);
		List<TwrRentInformationChangeVO> twrRentInfoChangeVO = JSONArray.parseArray(jsonChange.toString(), TwrRentInformationChangeVO.class);
		FeedBackObject backObject = new FeedBackObject();
		// 启动审核流程
		TowerRentInformationHistoryVO historyBackVO = twrRentInformationService.queryHistoryVOByid(historyVO.getRentinformationhistoryId());
		String processInstanceId = PromptMessageComm.KONG_SYMBOL;
		if (null != historyBackVO && historyBackVO.getCheckState() != ActivityStateComm.STATE_AUDIT){
			processInstanceId = actTaskService.startProcess(ActUtils.PD_INFORMATION_INFO[0], 
					loginUser.getPrv_code(), 
					ActUtils.PD_INFORMATION_INFO[1], 
					historyVO.getRentinformationId(), 
					ActUtils.PD_INFORMATION_INFO[2], 
					null, 
					loginUser.getUser_loginname());
			if (!StrUtil.isEmpty(processInstanceId)) {
				// 将字段审核状态设置为审核中
				historyVO.setCheckState(ActivityStateComm.STATE_AUDIT);
				historyVO.setUpdateTime(new Date());
				historyVO.setUpdateUserId(loginUser.getUser_id());
				String oldRentId = historyVO.getRentinformationhistoryId();
				String newRentId = StrUtil.EMPTY;
				// 判断生效日期是否更改过
				if (effectDateChange) {
					newRentId = SysUUID.generator();
					dealWhenDateChange(historyVO, oldRentId, newRentId, backObject);
				} else {
					// 更新拆分表
					String updateResult = twrRentInformationService.updateTwrInfoHistoryVO(historyVO);
					backObject.success = updateResult;
				}
				// 初始化changeVo信息如主键
				initChangeVO(twrRentInfoChangeVO, oldRentId, newRentId);
				//将changeVO对象入库
				String result = addTwrRentInformation(loginUser, twrRentInfoChangeVO);
				backObject.success = result;
				backObject.msg = PromptMessageComm.COMMIT_AUDIT_SUCCESS;
			}else{
				backObject.msg = PromptMessageComm.COMMIT_AUDIT_FAILED;
				log.info(SysLogComm.LOG_Error, PromptMessageComm.OPERATION_FAILED);
				throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
			}
		}else{
			backObject.success = RESULT.FAIL_0;
			backObject.msg = PromptMessageComm.COMMIT_AUDIT_FAILED;
		}
		return backObject;
	}

	/**
	 * 初始化主键等信息
	 * @author jiacy
	 * @param twrRentInfoChangeVO
	 * @param newRentId
	 */
	private void initChangeVO(
			List<TwrRentInformationChangeVO> twrRentInfoChangeVO,
			String oldRentId, String newRentId) {
		if (!StrUtil.isEmpty(newRentId)) {
			for (TwrRentInformationChangeVO changeVO : twrRentInfoChangeVO) {
				changeVO.setRentinformationhistoryId(newRentId);
				changeVO.setRentinformationchangeId(SysUUID.generator());
				changeVO.setCheckState(ActivityStateComm.STATE_AUDIT);
			}
		} else {
			for (TwrRentInformationChangeVO changeVO : twrRentInfoChangeVO) {
				changeVO.setRentinformationchangeId(SysUUID.generator());
				changeVO.setRentinformationhistoryId(oldRentId);
				changeVO.setCheckState(ActivityStateComm.STATE_AUDIT);
			}
		}
	}

	/**
	 * 当生效日期有变动的时候进行处理的函数
	 * @author jiacy
	 * @param historyVO
	 * @param oldRentId
	 * @return
	 */
	private void dealWhenDateChange(
			TowerRentInformationHistoryVO newHistoryVO, String oldRentId,String newRentId, FeedBackObject backObject) {
		// 新增记录，由于修改的时候提交过来的表单不是所有的字段，所以需要先将没有传过来的字段查一遍，设置到VO中再insert

		newHistoryVO.setRentinformationhistoryId(newRentId);
		Date startDate = newHistoryVO.getStartDate();
		TowerRentInformationHistoryVO oldHistoryVo = twrRentInformationService.queryHistoryVOByid(oldRentId);
		//补全HistoryVO对象的信息
		newHistoryVO = completeNewHistoryVO(oldHistoryVo,newHistoryVO);
		String insertResult = twrRentInformationService.insertTwrInfoHistoryVO(newHistoryVO);
		backObject.success = insertResult;
		// 在修改后的日期上减去一天作为旧的记录的服务结束日期
		Date oldEndDate = new Date(startDate.getTime() - PromptMessageComm.DAY);
		// 更新拆分表
		newHistoryVO = new TowerRentInformationHistoryVO();
		newHistoryVO.setEndDate(oldEndDate);
		newHistoryVO.setCheckState(ActivityStateComm.STATE_AUDIT);
		newHistoryVO.setRentinformationhistoryId(oldRentId);
		String updateResult = twrRentInformationService.updateTwrInfoHistoryVO(newHistoryVO);
		backObject.msg = updateResult;
	}
	
	/**
	 * 将变更记录插入到变更表
	 * @author jiacy
	 * @param loginInfo
	 * @param twrChangeVO
	 * @return
	 */
	private String addTwrRentInformation(UserLoginInfo loginInfo,
			List<TwrRentInformationChangeVO> twrRentInfoChangeVO) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("twrRentInfoChangeList", twrRentInfoChangeVO);
		return twrRentInformationService.updateTwrRentInformationTemp(paramMap);
	}
	
	/**
	 * 补全新增的拆分表VO对象
	 * @author jiacy
	 * @param oldHistoryVo
	 * @param newHistoryVO
	 */
	private TowerRentInformationHistoryVO completeNewHistoryVO(
			TowerRentInformationHistoryVO oldHistoryVo,
			TowerRentInformationHistoryVO newHistoryVO) {
		TowerRentInformationHistoryVO tempHistoryVo = oldHistoryVo;
		tempHistoryVo.setCheckState(newHistoryVO.getCheckState());
		tempHistoryVo.setRentinformationhistoryId(newHistoryVO.getRentinformationhistoryId());
		tempHistoryVo.setProductConfigurationId(newHistoryVO.getProductConfigurationId());
		tempHistoryVo.setAntennaHeightRangeId(newHistoryVO.getAntennaHeightRangeId());
		tempHistoryVo.setIsSpecEnterStation(newHistoryVO.getIsSpecEnterStation());
		tempHistoryVo.setMaintenanceLevelId(newHistoryVO.getMaintenanceLevelId());
		tempHistoryVo.setElectricProtectionMethodId(newHistoryVO.getElectricProtectionMethodId());
		tempHistoryVo.setIfSelectPowerService(newHistoryVO.getIfSelectPowerService());
		tempHistoryVo.setOilGenerateElectricMethodId(newHistoryVO.getOilGenerateElectricMethodId());
		tempHistoryVo.setUnitProductNumber(newHistoryVO.getUnitProductNumber());
		tempHistoryVo.setMaintenanceFeeDis(newHistoryVO.getMaintenanceFeeDis());
		tempHistoryVo.setStageFeeDis(newHistoryVO.getStageFeeDis());
		tempHistoryVo.setElectricImportFeeDis(newHistoryVO.getElectricImportFeeDis());
		tempHistoryVo.setTowerShareDis(newHistoryVO.getTowerShareDis());
		tempHistoryVo.setRoomSupportingShareDis(newHistoryVO.getRoomSupportingShareDis());
		tempHistoryVo.setUpdateUserId(newHistoryVO.getUpdateUserId());
		tempHistoryVo.setUpdateTime(newHistoryVO.getUpdateTime());
		return tempHistoryVo;
	}
	
	/**
	 * @author xup
	 * 资源变更审核导出
	 * @return
	 */
	@RequestMapping(value="/exportRentInformation")
	public void exportRentInformation(@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		FeedBackObject feedbackObj = new FeedBackObject();
		String rentinformationIds= (java.lang.String) map.get("rentinformationIds");
		String[] strings= rentinformationIds.split(PromptMessageComm.COMMA_SYMBOL);
		List<String>  listNew=new ArrayList<>();
		for(int i=0;i<strings.length;i++){
			listNew.add(strings[i]);
		}
		Map<String,Object> maps=new HashMap<>();
		maps.put("rentinformationIds", listNew);
		
		/*List<SysRegionVO> listreg=sysRegionService.getAddress(map);//准备需要的数据处理
		TowerBillbalanceHandler tbh=new TowerBillbalanceHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{"运营商地市","需求承接地市","站址所属地市"});//需要处理数据的列名 
		ExportParams params = new ExportParams("铁塔账单", "铁塔账单", ExcelType.XSSF);

		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TowerBillbalanceVO.class,list);
        FileUtils.downFile(workBook, "铁塔账单.xls", request, response);*/
		
		
		List<TwrRentInformationVO> list = twrRentInformationService.queryExportTwrRentInformation(maps);
		ExportParams params = new ExportParams(PromptMessageComm.RENT_BILLS, PromptMessageComm.RENT_BILLS, ExcelType.XSSF);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TwrRentInformationVO.class, list);
        FileUtils.downFile(workBook, PromptMessageComm.RENT_BILLS_XLS, request, response);
        feedbackObj.success = RESULT.SUCCESS_1;
        feedbackObj.msg=PromptMessageComm.OPERATION_SUSSESS;
	}
	
	
	/**
	 * @author xup
	 * 资源变更审核导出
	 * @return
	 */
	@RequestMapping(value="/exportRentInformationHistory")
	public void exportRentInformationHistory(@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		FeedBackObject feedbackObj = new FeedBackObject();
		String rentinformationIds= (java.lang.String) map.get("rentinformationHistoryIds");
		String[] strings= rentinformationIds.split(PromptMessageComm.COMMA_SYMBOL);
		List<String>  listNew=new ArrayList<>();
		for(int i=0;i<strings.length;i++){
			listNew.add(strings[i]);
		}
		Map<String,Object> maps=new HashMap<>();
		maps.put("historyIds", listNew);
		List<TowerRentInformationHistoryVO> list = twrRentInformationService.queryExportTwrRentInformationHistory(maps);
		ExportParams params = new ExportParams(PromptMessageComm.MBILE_RESOURCE_DETAILS, PromptMessageComm.MBILE_RESOURCE_DETAILS, ExcelType.XSSF);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TowerRentInformationHistoryVO.class, list);
        FileUtils.downFile(workBook, PromptMessageComm.MBILE_RESOURCE_DETAILS_XLS, request, response);
        feedbackObj.success = RESULT.SUCCESS_1;
        feedbackObj.msg=PromptMessageComm.OPERATION_SUSSESS;
	}
	
	
	
	
	/**
	 * @author xup 
	 * 审核内页面变更记录查询
	 * @return
	 */
	@RequestMapping(value="/queryTwrRentInformationChangeCheck",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryTwrRentInformationChangeCheck(String rentinformationId,String  endDate){
		FeedBackObject feedbackObj = new FeedBackObject();
		Map<String, Object> maps = new HashMap<String, Object>();
		SimpleDateFormat  fmt =new SimpleDateFormat(PromptMessageComm.DATE_TYPE_24H);
		Date date=null;
		try {
			date = fmt.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		maps.put("rentinformationId",rentinformationId );
		maps.put("state", ActivityStateComm.STATE_AUDIT);
		maps.put("endDate", date);
		feedbackObj.Obj=twrRentInformationService.queryTwrRentInformationChange(maps);
		feedbackObj.success = RESULT.SUCCESS_1;
		return feedbackObj;
	}
	

	/**
	 * @author xup 
	 * 审核内页面拆分记录查询
	 * @return
	 */
	@RequestMapping(value="/queryTwrRentInformationHistoryCheck",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryTwrRentInformationHistoryCheck(String rentinformationId,String  endDate){
		FeedBackObject feedbackObj = new FeedBackObject();
		Map<String, Object> maps = new HashMap<String, Object>();
		SimpleDateFormat  fmt =new SimpleDateFormat(PromptMessageComm.DATE_TYPE_24H);
		Date date=null;
		try {
			date = fmt.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		maps.put("rentinformationId",rentinformationId );
		maps.put("state",ActivityStateComm.STATE_AUDIT);
		maps.put("endDate", date);
		feedbackObj.Obj=twrRentInformationService.queryTowerRentInformationHistoryByConditions(maps);
		feedbackObj.success = RESULT.SUCCESS_1;
		return feedbackObj;
	}
	/**
	 *根据拆分表id查询变更表记录 
	 * @author xup
	 * @param maps
	 * @return
	 */
	@RequestMapping(value="/queryTwrRentInformationChangeByHistoryId",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject queryTwrRentInformationChangeByHistoryId(Map<String, Object> maps){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.Obj=twrRentInformationService.queryTwrRentInformationChange(maps);
		feedbackObj.success = RESULT.SUCCESS_1;
		return feedbackObj;
	}
	
}
