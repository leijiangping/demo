package com.xunge.controller.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.TimeUtils;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.model.activity.ActHistoicFlow;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.system.role.ISysRoleService;
/**
 * 流程公共调用相关Controller
 * @author zhujj
 * @version 2013-11-03
 */
@Controller
public class ActCommonController extends BaseException {

	@Autowired
	private IActTaskService actTaskService;
	

	@Autowired
	private ISysRoleService iSysRoleService;
	
	/**
	 * 流程流转历史
	 * @param tableName
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/act/common/histoicFlowList")
	public @ResponseBody FeedBackObject histoicFlowList(String tableName,String id){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		List<Act> list = actTaskService.histoicFlowList(tableName, id, PromptMessageComm.KONG_SYMBOL, PromptMessageComm.KONG_SYMBOL);
		List<ActHistoicFlow> li=new ArrayList<ActHistoicFlow>();
		
		for (int i = 0; i < list.size(); i++) {
			
			ActHistoicFlow act=new ActHistoicFlow();
			act.setAssignee(list.get(i).getAssignee());
			act.setBeginDate(list.get(i).getBeginDate());
			act.setEndDate(list.get(i).getEndDate());
			act.setComment(list.get(i).getComment());
			act.setTitle(list.get(i).getHistIns().getActivityName());
			
			if(act.getEndDate()!=null&&act.getBeginDate()!=null)
			act.setDurationTime(TimeUtils.toTimeString(act.getEndDate().getTime()-act.getBeginDate().getTime()));
			else
			act.setDurationTime(PromptMessageComm.KONG_SYMBOL);	
				
			li.add(act);
		}
		feedbackObj.Obj=li ;
		return feedbackObj;
	}
	/**
	 * 根据角色名集合查询首环节对应用户信息
	 * @param roleIdLists
	 * @return
	 */
	@RequestMapping(value="/act/common/queryFirstUserByProcDefKey",method=RequestMethod.POST)
	@ResponseBody
	public FeedBackObject  queryFirstUserByProcDefKey(String procDefKey,String regId,HttpServletRequest request){
		UserLoginInfo info=(UserLoginInfo)request.getSession().getAttribute("user");
		if(info == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		List<String> roleIdLists=actTaskService.getFristNodeRole(procDefKey,info.getPrv_code());
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		if(roleIdLists!=null && roleIdLists.size()>0){
			feedbackObj.Obj =iSysRoleService.queryUserByRoleName(roleIdLists,info,regId);
		}
		return feedbackObj;
	}
	/**
	 * 根据角色名集合查询对应用户信息
	 * @param tableName
	 * @param id
	 * @param regId
	 * @param elName 流程参数名
	 * @return
	 */
	@RequestMapping(value="/act/common/queryUserByRoleId",method=RequestMethod.POST)
	@ResponseBody
	public FeedBackObject  queryUserByRoleId(@RequestParam Map<String,Object> map,HttpServletRequest request){
		String tableName=map.get("tableName")!=null?map.get("tableName").toString():PromptMessageComm.KONG_SYMBOL;
		String id=map.get("id")!=null?map.get("id").toString():PromptMessageComm.KONG_SYMBOL;
		String regId=map.get("regId")!=null?map.get("regId").toString():PromptMessageComm.KONG_SYMBOL;
		if(StrUtil.isBlank(tableName)){
			throw new BusinessException(PromptMessageComm.BUSINESS_NAME_EMPTY);
		}
		if(StrUtil.isBlank(id)){
			throw new BusinessException(PromptMessageComm.BUSINESS_PRIMARY_EMPTY);
		}
		//向下共享
//		if(StrUtil.isBlank(regId)){
//			throw new BusinessException("业务数据所属区县为空");
//		}
		Map<String,Object> elName=Maps.newHashMap();
		for(String key:map.keySet()){
			if(StrUtil.isNotBlank(key)&&!key.equals(PromptMessageComm.TABLENAME)&&!key.equals(PromptMessageComm.ID)&&!key.equals(PromptMessageComm.REGID)){
				elName.put(key, map.get(key));
			}
		}
		
		List<String> roleIdLists=actTaskService.getUserGroup(tableName, id,elName);
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		UserLoginInfo info=(UserLoginInfo)request.getSession().getAttribute("user");
		if(info == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		if(roleIdLists!=null && roleIdLists.size()>0){
			feedbackObj.Obj =iSysRoleService.queryUserByRoleName(roleIdLists,info,regId);
		}
		return feedbackObj;
	}
	
}
