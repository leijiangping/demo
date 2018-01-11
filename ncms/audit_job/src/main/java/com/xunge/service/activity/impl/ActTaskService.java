package com.xunge.service.activity.impl;

import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.xunge.model.activity.Act;
import com.xunge.service.activity.IActTaskService;
import com.xunge.util.StrUtil;

/**
 * @description 流程定义相关Service
 * @author zhujj
 * @date 2013-11-03
 */
@Service
public class ActTaskService implements IActTaskService{

	@Autowired
	private ProcessEngineFactoryBean processEngineFactory;
	
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;

	public static String COLON_SYMBOL= ":";
	
	/**
	 * 启动流程（区分区域）
	 * @param procDefKey 流程定义KEY
	 * @param regCode 区域编码（目前传省份Code），每个省份Code
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars			流程变量
	 * @param assignee		登陆名
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey,String regCode, String businessTable, String businessId, String title, Map<String, Object> vars,String assignee) {
		
		if(StrUtil.isNotEmpty(regCode)){
			 List<ProcessDefinition> list=repositoryService.createProcessDefinitionQuery().processDefinitionKey(procDefKey+"_"+regCode)
	                .orderByProcessDefinitionVersion().desc().list();
			if(list.size()>0){
				ProcessDefinition processDefinition=list.get(0);
				//如果有区域自定义模型，则使用自定义模型
				if(processDefinition!=null){
					procDefKey=procDefKey+"_"+regCode;
				}
			}
		 }
		
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(assignee);
		
		// 设置流程变量
		if (vars == null){
			vars = Maps.newHashMap();
		}
		
		// 设置流程标题
		if (StrUtil.isNotBlank(title)){
			vars.put("title", title);
		}
		
		// 启动流程
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessTable+":"+businessId, vars);
		//指定下一步审核人
		if(vars.containsKey("nextUserId")){
			Task nextTask = this.getTask(businessTable, businessId);
			taskService.setAssignee(nextTask.getId(), vars.get("nextUserId").toString());
		}
		return procIns.getId();
	}
	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param regCode 区域编码（目前传省份Code），每个省份Code
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars			流程变量
	 * @param assignee		登陆名
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId, String title, Map<String, Object> vars,String assignee) {
		
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(assignee);
		
		// 设置流程变量
		if (vars == null){
			vars = Maps.newHashMap();
		}
		
		// 设置流程标题
		if (StrUtil.isNotBlank(title)){
			vars.put("title", title);
		}
		
		// 启动流程
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessTable+COLON_SYMBOL+businessId, vars);
		
		//指定下一步审核人
		//将任务指定给当前班里人
		if(vars.containsKey("nextUserId")){
			Task nextTask = this.getTask(businessTable, businessId);
			taskService.setAssignee(nextTask.getId(), vars.get("nextUserId").toString());
		}
		return procIns.getId();
	}
	/**
	 * 启动流程
	 * @param Assignee 用户登陆名
	 * @param assigneeNameGroup 用户角色
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars			流程变量
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(Act act) {
		//String userId = UserUtils.getUser().getUser_loginname();//ObjectUtils.toString(UserUtils.getUser().getId())
		
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(act.getAssignee());
		Map<String,Object> vars=Maps.newHashMap();
		
		// 设置流程标题
		if (StrUtil.isNotBlank(act.getTitle())){
			vars.put("title", act.getTitle());
		}
		
		// 启动流程
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(act.getProcDefKey(), act.getBusinessTable()+COLON_SYMBOL+act.getBusinessId(), vars);
		return procIns.getId();
	}
	
	/**
	 * 获取任务
	 * @param taskId 任务ID
	 */
	public Task getTask(String taskId){
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}
	
	/**
	 * 获取流程任务对象
	 * @param businessKeyTable 业务表名
	 * @param businessKeyID 业务ID
	 * @return
	 */
	@Transactional(readOnly = false)
	public Task getTask(String businessKeyTable,String businessKeyID) {
		List<Task> list=taskService.createTaskQuery().processInstanceBusinessKey(businessKeyTable+COLON_SYMBOL+businessKeyID).list();
		return list.size()>0?list.get(0):null;
	}
}
