package com.xunge.controller.towerrent.punish;

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
import org.springframework.web.multipart.MultipartFile;

import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.model.towerrent.punish.TwrRegPunishVO;
import com.xunge.service.activity.impl.ActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.twrrent.punish.ITwrRegPunishService;

/**
 * @description 地市扣罚控制层
 * @author zhujj
 * @date 2017-7-20 10:28:37 
 */
@Controller
@RequestMapping(value = "/asserts/tpl/towerrent/assessment")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
public class TwrRegPunishController  extends BaseException{

	@Autowired
	private ITwrRegPunishService twrRegPunishService;

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private ISysRoleService iSysRoleService;
	

	@Autowired 
	private ISysRegionService sysRegionService;
	

	@Autowired
	private ILogService log;
	
	/**
	 * @description 查询地市扣罚分页数据/查询地市扣罚分页数据审核
	 * @param map
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value="/queryTwrRegPunishList")
	@ResponseBody
	public  FeedBackObject queryTwrRegPunishList(@RequestParam Map<String,Object> map,@ModelAttribute("user")UserLoginInfo loginUser){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();

		//获取当前用户所属地区,权限控制
		map.put("alias",PromptMessageComm.ALIAS_NAME_REG);//别名
		map.put("regIds",loginUser.getReg_ids());
		//判断是否查询审核中的数据
		if(null!=map.get("auditState")&&map.get("auditState").toString().equals(ActivityStateComm.STATE_AUDIT+"")){
			String assignee=loginUser.getUser_loginname();
			List<String> list = loginUser.getRole_ids();
			List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
			Act act=new Act();
			act.setProcDefKey(ActUtils.PD_TWRREGPUNISH_INFO[0]);
			act.setBusinessTable(ActUtils.PD_TWRREGPUNISH_INFO[1]);
			act.setAssignee(assignee);
			act.setAssigneeNameGroup(assigneeNameGroup);
			if(map.get("taskDefKey")!=null){
				act.setTaskDefKey(map.get("taskDefKey").toString());//设置根据环节Key过滤
			}
			act.setRegCode(loginUser.getPrv_code());//区分省份
			List<Act> idsList = actTaskService.todoList(act);
			map.put("ids", idsList);
		}
		Page<TwrRegPunishVO> page=twrRegPunishService.selectTwrRegPunishPage(map);
		feedbackObj.Obj=page;
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg =PromptMessageComm.OPERATION_SUSSESS;
		return feedbackObj;
	}
	/**
	 * @description 根据编码查询地市扣罚
	 * @param map
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value="/queryTwrRegPunishId")
	@ResponseBody
	public  FeedBackObject queryTwrRegPunishId(String twrRegPunishId){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.Obj =twrRegPunishService.selectByPrimaryKey(twrRegPunishId);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg =PromptMessageComm.OPERATION_SUSSESS;
		return feedbackObj;
	}
	/**
	 * @description 查询地市扣罚信息
	 * @param TwrRegPunishVO punishYearMonth-扣罚年月
	 * @param TwrRegPunishVO regId-扣罚区县编码
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value="/queryTwrRegPunish")
	@ResponseBody
	public  FeedBackObject queryTwrRegPunish(TwrRegPunishVO twrRegPunish){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.Obj =twrRegPunishService.selectByTwrRegPunish(twrRegPunish);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.msg =PromptMessageComm.OPERATION_SUSSESS;
		return feedbackObj;
	}
	/**
	 * @description 保存地市扣罚
	 * @param map
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value="/insertTwrRegPunish")
	@ResponseBody
	public FeedBackObject insertTwrRegPunish(TwrRegPunishVO entity,@ModelAttribute("user")UserLoginInfo loginUser){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();

		entity.setCreateUserId(loginUser.getUser_id());
		entity.setUpdateUserId(loginUser.getUser_id());
		try{
			feedbackObj.Obj =twrRegPunishService.insertTwrRegPunishVO(entity);
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.OPERATION_SUSSESS;
			log.info(SysLogComm.LOG_Operate, feedbackObj.msg);
		}catch(Exception e){
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.OPERATION_FAILED;
			log.err(SysLogComm.LOG_Error, feedbackObj.msg);
		}
		return feedbackObj;
	}
	/**
	 * @description 修改地市扣罚
	 * @param map
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value="/updateTwrRegPunish")
	@ResponseBody
	public  FeedBackObject updateTwrRegPunish(TwrRegPunishVO entity,@ModelAttribute("user")UserLoginInfo loginUser){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		entity.setUpdateUserId(loginUser.getUser_id());
		try{
			feedbackObj.Obj =twrRegPunishService.updateByPrimaryKeySelective(entity);
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.OPERATION_SUSSESS;
			log.info(SysLogComm.LOG_Operate, feedbackObj.msg);
		}catch(Exception e){
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg =PromptMessageComm.OPERATION_FAILED;
			log.err(SysLogComm.LOG_Error, feedbackObj.msg);
		}
		return feedbackObj;
	}
	/**
	 * @description 删除地市扣罚
	 * @param map
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value="/deleteTwrRegPunish")
	@ResponseBody
	public  FeedBackObject deleteTwrRegPunish(@RequestBody List<String> id){
		FeedBackObject feedbackObj = new FeedBackObject();
		if(id!=null&&!id.equals(PromptMessageComm.KONG_SYMBOL)){
			feedbackObj.Obj =twrRegPunishService.deleteByPrimaryKey(id);
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.OPERATION_SUSSESS;
			log.info(SysLogComm.LOG_Operate, feedbackObj.msg);
		}else{
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg =PromptMessageComm.OPERATION_FAILED;
			log.err(SysLogComm.LOG_Error, feedbackObj.msg);
		}
		return feedbackObj;
	}
	/**
	 * @description 导入地市扣罚
	 * @param file
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value = "/importFile", method=RequestMethod.POST)
    @ResponseBody
    public FeedBackObject importFile(@ModelAttribute("user")UserLoginInfo loginUser,MultipartFile file,String suffix,HttpServletRequest request,HttpServletResponse response){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		try {
			Map<String,Object> returnMap=twrRegPunishService.insertExcelData(file,suffix,request);
		
			feedbackObj.Obj=returnMap;
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.IMPORT_COUNTY_DEDUCTION_INFO_SUCCESS+returnMap.get("successCount")+PromptMessageComm.DATAS+returnMap.get("errMsg");
			log.info(SysLogComm.LOG_Operate, feedbackObj.msg);
		}catch (Exception e) {
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg=PromptMessageComm.NOT_VALID_EXCEL_TEMPLATE;
			log.err(SysLogComm.LOG_Error, feedbackObj.msg);
		}
		return feedbackObj;
	}
	/**
	 * @description 导出地市扣罚数据
	 * @param Map 
	 * @author zhujj
	 */
	@RequestMapping(value="/export")
	public void export(@ModelAttribute("user")UserLoginInfo loginUser,@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;

		//获取当前用户所属地区,权限控制
		map.put("alias",PromptMessageComm.ALIAS_NAME_REG);//别名
		map.put("regIds",loginUser.getReg_ids());
//		//获取当前用户所属地区,权限控制
//		map.put("state",StateComm.STATE_0+"");
//		map.put("userId",loginUser.getUser_id());
		//判断是否查询审核中的数据
		if(null!=map.get("auditState")&&map.get("auditState").toString().equals(ActivityStateComm.STATE_AUDIT)){
			String assignee=loginUser.getUser_loginname();
			List<String> list = loginUser.getRole_ids();
			List<String> assigneeNameGroup=iSysRoleService.queryRoleNameById(list);
			Act act=new Act();
			act.setProcDefKey(ActUtils.PD_TWRREGPUNISH_INFO[0]);
			act.setBusinessTable(ActUtils.PD_TWRREGPUNISH_INFO[0]);
			act.setAssignee(assignee);
			act.setAssigneeNameGroup(assigneeNameGroup);
			List<Act> idsList = actTaskService.todoList(act);
			map.put("ids", idsList);
			
		}
		twrRegPunishService.exportExcelData(map, request, response);
		feedbackObj.msg=PromptMessageComm.OPERATION_SUSSESS;
	}
	/**
	 * @description 导出地市扣罚导入模板
	 * @param Map 
	 * @author zhujj
	 */
	@RequestMapping(value="/exportTemplate")
	public void exportTemplate(@ModelAttribute("user")UserLoginInfo loginUser,@RequestParam Map<String,Object> map,HttpServletRequest request,HttpServletResponse response){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		twrRegPunishService.exportTemplate(map, request, response);
		feedbackObj.msg=PromptMessageComm.OPERATION_SUSSESS;
	}
	/**
	 * @description 开始流程
	 * @param map
	 * @param map ids 需要提交审核的编码集合
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value="/updateTwrRegPunishStartFlow")
	@ResponseBody
	public  FeedBackObject updateTwrRegPunishStartFlow(@RequestBody List<Map<String,Object>> ids,@ModelAttribute("user")UserLoginInfo loginUser){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		try{
			feedbackObj.Obj =twrRegPunishService.updateAuditAndStartFlow(ids,loginUser);
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
	 * @description 提交审核
	 * @param map
	 * @param map ids 需要审核的业务编码集合和任务ID
	 * @return
	 * @author zhujj
	 */
	@RequestMapping(value="/updateCompleteTwrRegPunish")
	@ResponseBody
	public  FeedBackObject updateCompleteTwrRegPunish(@RequestBody List<Map<String,Object>> ids,@ModelAttribute("user")UserLoginInfo loginUser){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		try{
			feedbackObj.Obj =twrRegPunishService.updateAuditCompelet(ids, loginUser);
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.msg =PromptMessageComm.COMMIT_AUDIT_SUCCESS;
			log.err(SysLogComm.LOG_Operate, feedbackObj.msg);
		}catch(Exception e){
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg =PromptMessageComm.COMMIT_AUDIT_FAILED;
			log.err(SysLogComm.LOG_Error, feedbackObj.msg);
		}
		return feedbackObj;
	}
}
