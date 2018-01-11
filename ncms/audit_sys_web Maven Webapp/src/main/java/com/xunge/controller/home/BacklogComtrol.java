package com.xunge.controller.home;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.HomeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.StrUtil;
import com.xunge.model.FeedBackObject;
import com.xunge.model.activity.Act;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.datacollect.IGrpDatacollectPrvService;
import com.xunge.service.system.role.ISysRoleService;

/**
 * 待办事项控制器
 * @author zhujj
 * @date 2017年9月25日 下午2:14:52 
 * @version 1.0.0 
 */

@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
public class BacklogComtrol  extends BaseException{

	@Autowired
	private ISysRoleService iSysRoleService;
	@Autowired
	private IActTaskService actTaskService;
	
	@Autowired
	private IGrpDatacollectPrvService grpDatacollectPrvService;
	
	/**
	 * 获取待办事项
	 * @param loginInfo
	 * agentClass:$('#agentClass').val(), //代办类别
					treatmentState:$('#treatmentState').val(),//处理状态
					startTime:startTime,   //产生日期----开始
					endTime:endTime        //产生日期----结束
	 * @return
	 */
    @RequestMapping("/asserts/tpl/main/backlogList")
    public @ResponseBody FeedBackObject backlogList(String agentClass,String treatmentState,Date startTime,Date endTime,int pageNumber,int pageSize,@ModelAttribute("user") UserLoginInfo loginInfo) {
    	
    	if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
    	
    	Act act = new Act();
		String assignee = loginInfo.getUser_loginname();
		List<String> roleIds = loginInfo.getRole_ids();
		List<String> assigneeNameGroup = iSysRoleService.queryRoleNameById(roleIds);
		act.setAssignee(assignee);
		act.setAssigneeNameGroup(assigneeNameGroup);
		
		act.setRegCode(loginInfo.getPrv_code());// 区分省份
		act.setBeginDate(startTime);
		act.setEndDate(endTime);
		act.setBusinessTable(agentClass);
		
		Page<Act> page=new Page<Act>(pageNumber,pageSize);
    	if(StrUtil.isNotBlank(treatmentState)&&treatmentState.equals(HomeComm.STATE_str0)){
	    	//获取待办事项数量-------开始-------
    		if(agentClass.equals("")||agentClass==null){//如果没有指定具体分类，则全查
    			page=actTaskService.todoListPage(page,act);
    			List<Act> listTemp=page.getResult();
    	    	List<Act> listData=grpDatacollectPrvService.queryWiteToDoReject(loginInfo,treatmentState,startTime,endTime);
    	    	listTemp.addAll(listData);//合并待办的31省派发任务
        		page.setResult(listTemp);
        		page.setPageSize(page.getPageSize()+listData.size());
        		page.setTotal(page.getTotal()+listData.size());
    		}
    		else if(!agentClass.equals(PromptMessageComm.GRP_DATA_COLLECT)){//如果不是集团上报，则不查询集团上报
    			page=actTaskService.todoListPage(page,act);
    		}
    		else if(agentClass.equals(PromptMessageComm.GRP_DATA_COLLECT)){//如果是集团上报，则不查询流程相关
    	    	List<Act> listData=grpDatacollectPrvService.queryWiteToDoReject(loginInfo,treatmentState,startTime,endTime);
    	    	page.setResult(listData);
        		page.setPageSize(listData.size());
        		page.setTotal(listData.size());
    		}
	    	
	    	//获取待办事项数量--------结束----------
    	}else if(StrUtil.isNotBlank(treatmentState)&&treatmentState.equals(HomeComm.STATE_str1)){
	    	//获取已办事项数量--------开始---------
    		if(agentClass.equals("")||agentClass==null){//如果没有指定具体分类，则全查
    			page=actTaskService.historicList(page,act);
    			List<Act> listTemp=page.getResult();
    	    	List<Act> listData=grpDatacollectPrvService.queryWiteToDoReject(loginInfo,treatmentState,startTime,endTime);
    	    	listTemp.addAll(listData);//合并待办的31省派发任务
        		page.setResult(listTemp);
        		page.setPageSize(page.getPageSize()+listData.size());
        		page.setTotal(page.getTotal()+listData.size());
    		}
    		else if(!agentClass.equals(PromptMessageComm.GRP_DATA_COLLECT)){//如果指定的是流程审核类，则不查询流程相关
    			page=actTaskService.historicList(page,act);
    		}else if(agentClass.equals(PromptMessageComm.GRP_DATA_COLLECT)){//如果是集团上报，则不查询流程相关
    			List<Act> listTemp=new ArrayList<Act>();
    	    	List<Act> listData=grpDatacollectPrvService.queryWiteToDoReject(loginInfo,treatmentState,startTime,endTime);
    	    	listTemp.addAll(listData);//合并待办的31省派发任务
        		page.setResult(listTemp);
        		page.setPageSize(page.getPageSize()+listData.size());
        		page.setTotal(page.getTotal()+listData.size());
    		}
	    	
	    	//获取已办事项数量--------结束---------
    	
    	}
    	FeedBackObject backObj = new FeedBackObject();
		backObj.success=RESULT.SUCCESS_1;
		backObj.Obj=page;
		return backObj;
    }
}
