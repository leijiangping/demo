package com.xunge.service.activity.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.NoneStartEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.javax.el.ValueExpression;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfoQuery;
import org.activiti.engine.task.TaskQuery;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xunge.comm.StateComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.page.Page;
import com.xunge.core.util.StrUtil;
import com.xunge.model.activity.Act;
import com.xunge.model.activity.ActProcessVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.service.cmd.CreateAndTakeTransitionCmd;
import com.xunge.service.activity.service.cmd.JumpTaskCmd;
import com.xunge.service.activity.service.creator.ChainedActivitiesCreator;
import com.xunge.service.activity.service.creator.MultiInstanceActivityCreator;
import com.xunge.service.activity.service.creator.RuntimeActivityDefinitionEntityIntepreter;
import com.xunge.service.activity.service.creator.SimpleRuntimeActivityDefinitionEntity;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.activity.utils.ProcessDefCache;
import com.xunge.service.activity.utils.ProcessDefUtils;

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
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;

	private TaskInfoQuery<HistoricTaskInstanceQuery, HistoricTaskInstance> todoTaskQuery;
	
	/**
	 * 获取待办列表(区分区域)
	 * @param procDefKey 流程定义标识
	 * @param regCode 区分区域，为空则不区分区域
	 * @param BusinessTable 业务表名
	 * @param Assignee 用户登陆名
	 * @param assigneeNameGroup 用户角色
	 * @return
	 */
	public List<Act> todoList(Act act){
		//检查用户关系，如果不存在用户关系则增加或者更新。
		this.updateUserRole(act.getAssignee(),act.getAssigneeNameGroup());
		
		if(StrUtil.isNotEmpty(act.getProcDefKey())&&StrUtil.isNotEmpty(act.getRegCode())){
			 List<ProcessDefinition> list=repositoryService.createProcessDefinitionQuery().processDefinitionKey(act.getProcDefKey()+"_"+act.getRegCode())
	                .orderByProcessDefinitionVersion().desc().list();
			if(list.size()>0){
				ProcessDefinition processDefinition=list.get(0);
				//如果有区域自定义模型，则使用自定义模型
				if(processDefinition!=null){
					act.setProcDefKey(act.getProcDefKey()+"_"+act.getRegCode());
				}
			}
		 }
		List<Act> result = new ArrayList<Act>();
		// =============== 已经签收的任务  ===============
		//-----------------------查询使用角色、有候选人的待办-------------------------
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskCandidateGroupIn(act.getAssigneeNameGroup()).active()
				.includeProcessVariables().orderByTaskCreateTime().desc();
		// 设置查询条件
		if (StrUtil.isNotBlank(act.getProcDefKey())){
			todoTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		// 公用的模型标识时根据表名过滤待办任务
		if (StrUtil.isNotBlank(act.getBusinessTable())){
			todoTaskQuery.processInstanceBusinessKeyLike(act.getBusinessTable()+"%");
		}
		
		//根据不同环节名称过滤
		if (StrUtil.isNotBlank(act.getTaskDefKey())){
			todoTaskQuery.taskDefinitionKey(act.getTaskDefKey());
		}
		if (act.getBeginDate() != null){
			todoTaskQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			todoTaskQuery.taskCreatedBefore(act.getEndDate());
		}
		// 查询列表
		List<Task> todoList = todoTaskQuery.list();
		for (Task task : todoList) {
			Act e = new Act();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
			e.setBusinessId(e.getProcIns().getBusinessKey().split(PromptMessageComm.SYMBOL1)[1]);
			e.setStatus(PromptMessageComm.TO_DO);
			e.setTaskId(task.getId());
			e.setTaskName(task.getName());//任务环节名称
			e.setTaskDefKey(task.getTaskDefinitionKey());//任务环节标识
			result.add(e);
		}
		//-----------------------查询有指定人的待办-------------------------
		todoTaskQuery = taskService.createTaskQuery().taskAssignee(act.getAssignee()).active()
				.includeProcessVariables().orderByTaskCreateTime().desc();
		// 设置查询条件
		if (StrUtil.isNotBlank(act.getProcDefKey())){
			todoTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		// 公用的模型标识时根据表名过滤待办任务
		if (StrUtil.isNotBlank(act.getBusinessTable())){
			todoTaskQuery.processInstanceBusinessKeyLike(act.getBusinessTable()+"%");
		}
		//根据不同环节名称过滤
		if (StrUtil.isNotBlank(act.getTaskDefKey())){
			todoTaskQuery.taskDefinitionKey(act.getTaskDefKey());
		}
		
		if (act.getBeginDate() != null){
			todoTaskQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			todoTaskQuery.taskCreatedBefore(act.getEndDate());
		}
		// 查询列表
		todoList = todoTaskQuery.list();
		for (Task task : todoList) {
			Act e = new Act();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
			e.setBusinessId(e.getProcIns().getBusinessKey().split(PromptMessageComm.SYMBOL1)[1]);
			e.setStatus(PromptMessageComm.TO_DO);
			e.setTaskId(task.getId());
			e.setTaskName(task.getName());//任务环节名称
			e.setTaskDefKey(task.getTaskDefinitionKey());//任务环节标识
			result.add(e);
		}
		return result;
	}
	
	/**
	 * 获取待办列表(区分区域)(分页)
	 * @param procDefKey 流程定义标识
	 * @param regCode 区分区域，为空则不区分区域
	 * @param BusinessTable 业务表名
	 * @param Assignee 用户登陆名
	 * @param assigneeNameGroup 用户角色
	 * @return
	 */
	public Page<Act> todoListPage(Page<Act> page,Act act){
		//检查用户关系，如果不存在用户关系则增加或者更新。
		this.updateUserRole(act.getAssignee(),act.getAssigneeNameGroup());
		
		if(StrUtil.isNotEmpty(act.getProcDefKey())&&StrUtil.isNotEmpty(act.getRegCode())){
			 List<ProcessDefinition> list=repositoryService.createProcessDefinitionQuery().processDefinitionKey(act.getProcDefKey()+"_"+act.getRegCode())
	                .orderByProcessDefinitionVersion().desc().list();
			if(list.size()>0){
				ProcessDefinition processDefinition=list.get(0);
				//如果有区域自定义模型，则使用自定义模型
				if(processDefinition!=null){
					act.setProcDefKey(act.getProcDefKey()+"_"+act.getRegCode());
				}
			}
		 }
		List<Act> result = new ArrayList<Act>();
		// =============== 已经签收的任务  ===============
		
		//-----------------------查询有指定人的待办-------------------------
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(act.getAssignee()).active()
				.includeProcessVariables().orderByTaskCreateTime().desc();
		// 设置查询条件
		if (StrUtil.isNotBlank(act.getProcDefKey())){
			todoTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		// 公用的模型标识时根据表名过滤待办任务
		if (StrUtil.isNotBlank(act.getBusinessTable())){
			todoTaskQuery.processInstanceBusinessKeyLike(act.getBusinessTable()+"%");
		}
		//根据不同环节名称过滤
		if (StrUtil.isNotBlank(act.getTaskDefKey())){
			todoTaskQuery.taskDefinitionKey(act.getTaskDefKey());
		}
		
		if (act.getBeginDate() != null){
			todoTaskQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			todoTaskQuery.taskCreatedBefore(act.getEndDate());
		}

		// 查询总数
		page.setTotal(todoTaskQuery.count());
		// 查询列表
		List<Task> todoList = todoTaskQuery.listPage(page.getFirstResult(), page.getPageSize());
		for (Task task : todoList) {
			Act e = new Act();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
			e.setBusinessId(e.getProcIns().getBusinessKey().split(PromptMessageComm.SYMBOL1)[1]);
			e.setStatus(StateComm.STATE_str0);
			e.setTaskId(task.getId());
			e.setTaskName(task.getName());//任务环节名称
			e.setTaskDefKey(task.getTaskDefinitionKey());//任务环节标识
			Map<String,Object> obj=task.getProcessVariables();
			e.setTitle(obj.get("title").toString());
			e.setBeginDate(task.getCreateTime());
			result.add(e);
		}
		page.setResult(result);
		return page;
	}
	/**
	 * 获取已办任务
	 * @param page
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	public Page<Act> historicList(Page<Act> page, Act act){
		HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().finished()
				.includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();
		
		// 设置查询条件
		if (StrUtil.isNotBlank(act.getProcDefKey())){
			histTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getAssignee() != null){
			histTaskQuery.taskAssignee(act.getAssignee());
		}
		if (act.getBeginDate() != null){
			histTaskQuery.taskCompletedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			histTaskQuery.taskCompletedBefore(act.getEndDate());
		}
		// 公用的模型标识时根据表名过滤待办任务
		if (StrUtil.isNotBlank(act.getBusinessTable())){
			histTaskQuery.processInstanceBusinessKeyLike(act.getBusinessTable()+"%");
		}
		
		if (act.getBeginDate() != null){
			histTaskQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			histTaskQuery.taskCreatedBefore(act.getEndDate());
		}
		// 查询总数
		page.setTotal(histTaskQuery.count());
		
		// 查询列表
		List<HistoricTaskInstance> histList = histTaskQuery.listPage(page.getFirstResult(), page.getPageSize());
		//处理分页问题
		List<Act> actList=Lists.newArrayList();
		for (HistoricTaskInstance histTask : histList) {
			Act e = new Act();
			e.setHistTask(histTask);
			e.setVars(histTask.getProcessVariables());
			e.setTitle(histTask.getProcessVariables().get("title").toString());
			e.setTaskName(histTask.getName());
			e.setBeginDate(histTask.getStartTime());
			e.setEndDate(histTask.getEndTime());
			e.setStatus(StateComm.STATE_str1);
			actList.add(e);
		}
		page.setResult(actList);
		return page;
	}
	
	/**
	 * 获取流转历史列表
	 * @param procInsId 流程实例
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	public List<Act> histoicFlowList(String procInsId, String startAct, String endAct){
		List<Act> actList = Lists.newArrayList();
		List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId)
				.orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();
		
		boolean start = false;
		Map<String, Integer> actMap = Maps.newHashMap();
		
		for (int i=0; i<list.size(); i++){
			
			HistoricActivityInstance histIns = list.get(i);
			
			// 过滤开始节点前的节点
			if (StrUtil.isNotBlank(startAct) && startAct.equals(histIns.getActivityId())){
				start = true;
			}
			if (StrUtil.isNotBlank(startAct) && !start){
				continue;
			}
			
			// 只显示开始节点和结束节点，并且执行人不为空的任务
			if (StrUtil.isNotBlank(histIns.getAssignee())
					 || PromptMessageComm.START_EVENT.equals(histIns.getActivityType())
					 || PromptMessageComm.END_EVENT.equals(histIns.getActivityType())){
				
				// 给节点增加一个序号
				Integer actNum = actMap.get(histIns.getActivityId());
				if (actNum == null){
					actMap.put(histIns.getActivityId(), actMap.size());
				}
				
				Act e = new Act();
				e.setHistIns(histIns);
				// 获取流程发起人名称
				if (PromptMessageComm.START_EVENT.equals(histIns.getActivityType())){
					List<HistoricProcessInstance> il = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInsId).orderByProcessInstanceStartTime().asc().list();
					if (il.size() > 0){
						if (StrUtil.isNotBlank(il.get(0).getStartUserId())){
							if (il.get(0).getStartUserId() != null){
								e.setAssignee(il.get(0).getStartUserId());
								e.setAssigneeName(il.get(0).getStartUserId());
							}
						}
					}
				}
				// 获取任务执行人名称
				if (StrUtil.isNotEmpty(histIns.getAssignee())){
					if (histIns.getAssignee() != null){
						e.setAssignee(histIns.getAssignee());
						e.setAssigneeName(histIns.getAssignee());
					}
				}
				// 获取意见评论内容
				if (StrUtil.isNotBlank(histIns.getTaskId())){
					List<Comment> commentList = taskService.getTaskComments(histIns.getTaskId());
					if (commentList.size()>0){
						e.setComment(commentList.get(0).getFullMessage());
					}
				}
				actList.add(e);
			}
			
			// 过滤结束节点后的节点
			if (StrUtil.isNotBlank(endAct) && endAct.equals(histIns.getActivityId())){
				boolean bl = false;
				Integer actNum = actMap.get(histIns.getActivityId());
				// 该活动节点，后续节点是否在结束节点之前，在后续节点中是否存在
				for (int j=i+1; j<list.size(); j++){
					HistoricActivityInstance hi = list.get(j);
					Integer actNumA = actMap.get(hi.getActivityId());
					if ((actNumA != null && actNumA < actNum) || org.apache.commons.lang3.StringUtils.equals(hi.getActivityId(), histIns.getActivityId())){
						bl = true;
					}
				}
				if (!bl){
					break;
				}
			}
		}
		return actList;
	}
	/**
	 * 根据业务表名和ID获取流转历史列表
	 * @param businessKeyTable 业务表名
	 * @param businessKeyID 业务ID
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	public List<Act> histoicFlowList(String businessKeyTable,String businessKeyID, String startAct, String endAct){
		List<HistoricProcessInstance> listhpi=historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKeyTable+PromptMessageComm.COLON_SYMBOL+businessKeyID).orderByProcessInstanceId().desc().list();
		
		if(listhpi.size()==0){
			return  Lists.newArrayList();
		}
		List<Act> actList = Lists.newArrayList();
		for(HistoricProcessInstance hpi:listhpi){

			String procInsId=hpi.getId();
			List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId)
					.orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();//.orderByHistoricActivityInstanceStartTime().asc()
			
			boolean start = false;
			Map<String, Integer> actMap = Maps.newHashMap();
			
			for (int i=0; i<list.size(); i++){
				
				HistoricActivityInstance histIns = list.get(i);
				
				// 过滤开始节点前的节点
				if (StrUtil.isNotBlank(startAct) && startAct.equals(histIns.getActivityId())){
					start = true;
				}
				if (StrUtil.isNotBlank(startAct) && !start){
					continue;
				}
				//候选组没有指定审核人处理
				List<HistoricIdentityLink> currList=null;
				if(StrUtil.isNotBlank(histIns.getTaskId())){
					currList=historyService.getHistoricIdentityLinksForTask(histIns.getTaskId());
				}
				// 只显示开始节点和结束节点，并且执行人不为空的任务
				if (StrUtil.isNotBlank(histIns.getAssignee())
						 || (currList!=null&&currList.size()>0)
						 || PromptMessageComm.START_EVENT.equals(histIns.getActivityType())
						 || PromptMessageComm.END_EVENT.equals(histIns.getActivityType())){
					
					// 给节点增加一个序号
					Integer actNum = actMap.get(histIns.getActivityId());
					if (actNum == null){
						actMap.put(histIns.getActivityId(), actMap.size());
					}
					
					Act e = new Act();
					e.setHistIns(histIns);
					// 获取流程发起人名称
					if (PromptMessageComm.START_EVENT.equals(histIns.getActivityType())){
						List<HistoricProcessInstance> il = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInsId).orderByProcessInstanceStartTime().asc().list();
						if (il.size() > 0){
							if (StrUtil.isNotBlank(il.get(0).getStartUserId())){
								if (il.get(0).getStartUserId() != null){
									e.setAssignee(il.get(0).getStartUserId());
									e.setComment(PromptMessageComm.AUDIT_BEGIN);
								}
							}
						}
					}
					//给结束事件指定上一步参与的人员执行人
					if(PromptMessageComm.END_EVENT.equals(histIns.getActivityType())){
						int upAudit=i-1;//如果是排他网管，则找他前两级的userType
						if(PromptMessageComm.EXCLUSIVE_GATEWAY.equals(list.get(i-1).getActivityType())&&PromptMessageComm.USER_TYPE.equals(list.get(i-2).getActivityType())){
							upAudit=i-2;
						}
						if(list.get(upAudit).getAssignee()!=null){
							e.setAssignee(list.get(upAudit).getAssignee());
							e.setComment(PromptMessageComm.AUDIT_END);
						}else{
							e.setAssignee(PromptMessageComm.ENGINE);
							e.setComment(PromptMessageComm.AUDIT_END);
						}
					}
					// 获取任务执行人名称
					if (StrUtil.isNotEmpty(histIns.getAssignee())){
						if (histIns.getAssignee() != null){
							e.setAssignee(histIns.getAssignee());
						}
					}else if(currList!=null&&currList.size()>0){//候补组人员填入
						String sb = PromptMessageComm.KONG_SYMBOL;
						for(HistoricIdentityLink hil:currList){
							if(StrUtil.isNotBlank(hil.getGroupId()))
								sb+=(hil.getGroupId()+PromptMessageComm.COMMA_SYMBOL);
							else if(PromptMessageComm.CANDIDATE.equals(hil.getType())&&StrUtil.isNotBlank(hil.getUserId())){
								sb+=(hil.getUserId()+PromptMessageComm.COMMA_SYMBOL);
							}
						}
						e.setAssignee(sb.toString());
					}
					// 获取意见评论内容
					if (StrUtil.isNotBlank(histIns.getTaskId())){
						List<Comment> commentList = taskService.getTaskComments(histIns.getTaskId());
						if (commentList.size()>0){
							e.setComment(commentList.get(0).getFullMessage());
						}
					}
					// 获取任务开始时间
					if (histIns.getStartTime() != null){
						e.setBeginDate(histIns.getStartTime());
					}
					// 获取任务结束时间
					if (histIns.getEndTime()!= null){
						e.setEndDate(histIns.getEndTime());
					}
					actList.add(e);
				}
				
				// 过滤结束节点后的节点
				if (StrUtil.isNotBlank(endAct) && endAct.equals(histIns.getActivityId())){
					boolean bl = false;
					Integer actNum = actMap.get(histIns.getActivityId());
					// 该活动节点，后续节点是否在结束节点之前，在后续节点中是否存在
					for (int j=i+1; j<list.size(); j++){
						HistoricActivityInstance hi = list.get(j);
						Integer actNumA = actMap.get(hi.getActivityId());
						if ((actNumA != null && actNumA < actNum) || org.apache.commons.lang3.StringUtils.equals(hi.getActivityId(), histIns.getActivityId())){
							bl = true;
						}
					}
					if (!bl){
						break;
					}
				}
			}
		}
		return actList;
	}
	/**
	 * 获取流程列表
	 * @param category 流程分类
	 */
	public Page<Object[]> processList(Page<Object[]> page, String category) {
		/*
		 * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
	    ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
	    		.latestVersion().active().orderByProcessDefinitionKey().asc();
	    
	    if (StrUtil.isNotEmpty(category)){
	    	processDefinitionQuery.processDefinitionCategory(category);
		}
	    
	    page.setTotal(processDefinitionQuery.count());
	    
	    List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(page.getFirstResult(), page.getPageSize());
	    List<ActProcessVO> list=new ArrayList<ActProcessVO>();
	    for (ProcessDefinition processDefinition : processDefinitionList) {
	      String deploymentId = processDefinition.getDeploymentId();
	      Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
	      ActProcessVO obj=new ActProcessVO();

	      obj.setCategory( processDefinition.getCategory());
	      obj.setId(processDefinition.getId());
	      obj.setKey(processDefinition.getKey());
	      obj.setName(processDefinition.getName());
	      obj.setVersion(PromptMessageComm.KONG_SYMBOL+processDefinition.getVersion());
	      obj.setDeploymentTime(deployment.getDeploymentTime());
	      list.add(obj);
	    }
		return page;
	}
	
	/**
	 * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
	 * @return
	 */
	public String getFormKey(String procDefId, String taskDefKey){
		String formKey = PromptMessageComm.KONG_SYMBOL;
		if (StrUtil.isNotBlank(procDefId)){
			if (StrUtil.isNotBlank(taskDefKey)){
				try{
					formKey = formService.getTaskFormKey(procDefId, taskDefKey);
				}catch (Exception e) {
					formKey = PromptMessageComm.KONG_SYMBOL;
				}
			}
			if (StrUtil.isBlank(formKey)){
				formKey = formService.getStartFormKey(procDefId);
			}
			if (StrUtil.isBlank(formKey)){
				formKey = PromptMessageComm.FORM_KEY_404;
			}
		}
		return formKey;
	}
	
	/**
	 * 获取流程实例对象
	 * @param procInsId
	 * @return
	 */
	@Transactional(readOnly = false)
	public ProcessInstance getProcIns(String procInsId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
	}
	/**
	 * 获取流程实例对象
	 * @param businessKeyTable 业务表名
	 * @param businessKeyID 业务ID
	 * @return
	 */
	@Transactional(readOnly = false)
	public Execution getExecution(String businessKeyTable,String businessKeyID) {
		return runtimeService.createExecutionQuery().processInstanceBusinessKey(businessKeyTable+PromptMessageComm.COLON_SYMBOL+businessKeyID).singleResult();
	}
	/**
	 * 获取流程任务对象
	 * @param businessKeyTable 业务表名
	 * @param businessKeyID 业务ID
	 * @return
	 */
	@Transactional(readOnly = false)
	public Task getTask(String businessKeyTable,String businessKeyID) {
		List<Task> list=taskService.createTaskQuery().processInstanceBusinessKey(businessKeyTable+PromptMessageComm.COLON_SYMBOL+businessKeyID).list();
		return list.size()>0?list.get(0):null;
	}
	/**
	 * 获取流程下一步角色组
	 * @param businessKeyTable 业务表名
	 * @param businessKeyID 业务ID
	 * @return
	 */
	@Transactional(readOnly = false)
	public Task getNextGroup(String businessKeyTable,String businessKeyID) {
		Map<String,Object> map=Maps.newHashMap();
		Execution exe=this.getExecution(businessKeyTable,businessKeyID);
	    Task task=this.getTask(businessKeyTable, businessKeyID);
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(exe.getProcessInstanceId()).list(); 
		
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)runtimeService).getDeployedProcessDefinition(task.getProcessDefinitionId());

		List<ActivityImpl> activitiList = def.getActivities();

	    String excId = task.getExecutionId();
	    ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId).singleResult();
	    String activitiId = execution.getActivityId(); 

		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (activitiId.equals(id)) {
				System.out.println(PromptMessageComm.CURR_TASK + activityImpl.getProperty("name")); // 输出某个节点的某种属性
				List<PvmTransition> outTransitions = activityImpl
						.getOutgoingTransitions();// 获取从某个节点出来的所有线路
				for (PvmTransition tr : outTransitions) {
					PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
					map.put("user", ac.getProperty("activiti:assignee"));
				}
				break;
			}
		}
		
		return taskService.createTaskQuery().processInstanceBusinessKey(businessKeyTable+PromptMessageComm.COLON_SYMBOL+businessKeyID).singleResult();
	}
	
    public List<String> getUserGroup(String businessKeyTable,String businessKeyID,Map<String,Object> elName){
    	Task task=this.getTask(businessKeyTable, businessKeyID);
    	Set<Expression> set;
		List<String> roleList=new ArrayList<String>();
		try {
			set = getNextTaskGroup(task.getId(),elName);
			if(set!=null){
				 for(Expression expression:set){
			         roleList.add(expression.getExpressionText());
			     }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        return roleList;
    }
    /** 
     * 获取下一个用户任务用户组信息  
     * @param String taskId     任务Id信息  
     * @return  下一个用户任务用户组信息  
     * @throws Exception 
     */  
    public Set<Expression> getNextTaskGroup(String taskId,Map<String,Object> elName) throws Exception {  
          
        ProcessDefinitionEntity processDefinitionEntity = null;  
          
        String id = null;  
          
        TaskDefinition task = null;  
          
        //获取流程实例Id信息   
        String processInstanceId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();  
          
        //获取流程发布Id信息   
        String definitionId = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult().getProcessDefinitionId();  
          
        processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                .getDeployedProcessDefinition(definitionId);  
          
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();  
          
        //当前流程节点Id信息   
        String activitiId = execution.getActivityId();    
          
        //获取流程所有节点信息   
        List<ActivityImpl> activitiList = processDefinitionEntity.getActivities();   
          
        //遍历所有节点信息   
        for(ActivityImpl activityImpl : activitiList){      
            id = activityImpl.getId();     
              
            // 找到当前节点信息  
            if (activitiId.equals(id)) {  
                  
                //获取下一个节点信息   ActivityStateComm.STATE_NORMAL+""
                task = nextTaskDefinition(activityImpl, activityImpl.getId() , processInstanceId,elName); //等于0为审核通过，找审核通过的下一步审核人。 
  
                break;  
            }  
        }
          
        if(task!=null){
        	return task.getCandidateGroupIdExpressions();  
        }else{
        	return null;//如果是最后一步，下级用户信息为空
        }
    }  
    /**  
     * 下一个任务节点信息,  
     * 如果下一个节点为用户任务则直接返回,  
     * 如果下一个节点为排他网关, 获取排他网关Id信息, 根据排他网关Id信息和execution获取流程实例排他网关Id为key的变量值,  
     * 根据变量值分别执行排他网关后线路中的el表达式, 并找到el表达式通过的线路后的用户任务信息  
     * @param ActivityImpl activityImpl     流程节点信息  
     * @param String activityId             当前流程节点Id信息  
     * @param String processInstanceId      流程实例Id信息  
     * @param String elString               排他网关顺序流线段判断条件, 例如排他网关顺序留线段判断条件为${money>1000}, 若满足流程启动时设置variables中的money>1000, 则流程流向该顺序流信息  
     * @return  
     */    
    private TaskDefinition nextTaskDefinition(ActivityImpl activityImpl, String activityId, String processInstanceId,Map<String,Object> elName){   
              
        PvmActivity ac = null;  
          
        Object s = null;  
          
        //如果遍历节点为用户任务并且节点不是当前节点信息   
            if("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())){    
                //获取该节点下一个节点信息   
                TaskDefinition taskDefinition = ((UserTaskActivityBehavior)activityImpl.getActivityBehavior()).getTaskDefinition();    
                return taskDefinition;    
            }else{    
                //获取节点所有流向线路信息   
                List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();    
                List<PvmTransition> outTransitionsTemp = null;    
                for(PvmTransition tr : outTransitions){      
                    ac = tr.getDestination(); //获取线路的终点节点      
                    //如果流向线路为排他网关   
                    if(PromptMessageComm.EXCLUSIVE_GATEWAY.equals(ac.getProperty("type"))){    
                        outTransitionsTemp = ac.getOutgoingTransitions();  
                      
                          
                        //如果排他网关只有一条线路信息   
                        if(outTransitionsTemp.size() == 1){    
                            return nextTaskDefinition((ActivityImpl)outTransitionsTemp.get(0).getDestination(), activityId, processInstanceId, elName);    
                        }else if(outTransitionsTemp.size() > 1){  //如果排他网关有多条线路信息   
                            for(PvmTransition tr1 : outTransitionsTemp){    
                                s = tr1.getProperty("conditionText");  //获取排他网关线路判断条件信息   
                                
                                if(elName==null||(elName!=null&&elName.size()==0)){//设置兼容
                                	String elStringName=ActivityStateComm.ACTIVITY_VARIABLE_NAME;
                                	String elStringValue=ActivityStateComm.STATE_NORMAL+"";
                                	Map<String,Object> map=Maps.newHashMap();
	                                if(StrUtil.isNotBlank(elStringName)&&StrUtil.isNotBlank(elStringValue))
	                                map.put(elStringName, elStringValue);
	                                
	                                if(isCondition(map, StrUtil.trim(s.toString()))){ //流程变量定义为state；   
	                                    return nextTaskDefinition((ActivityImpl)tr1.getDestination(), activityId, processInstanceId, elName);    
	                                } 
                                }else{
	                                //判断el表达式是否成立    isCondition(ac.getId(), StrUtil.trim(s.toString()), elString)
	                                //获取流程启动时设置的网关判断条件信息   
                                	Map<String,Object> map=Maps.newHashMap();
                                	for (Map.Entry<String, Object> m :elName.entrySet())  {
                                		String name=m.getKey();
                                		//String elStringValue =  getGatewayCondition(name, processInstanceId);  
                                		String elStringValue = m.getValue().toString();  
		                                if(StrUtil.isNotBlank(name)&&StrUtil.isNotBlank(elStringValue))
		                                map.put(name, elStringValue);
		                                  
                                    }  

	                                if(isCondition(map, StrUtil.trim(s.toString()))){ //流程变量定义为state；   
	                                    return nextTaskDefinition((ActivityImpl)tr1.getDestination(), activityId, processInstanceId, elName);    
	                                } 
                              }
                            }    
                        }    
                    }else if("userTask".equals(ac.getProperty("type"))){    
                        return ((UserTaskActivityBehavior)((ActivityImpl)ac).getActivityBehavior()).getTaskDefinition();    
                    }else{    
                    }    
                }     
            return null;    
        }    
    }
    /** 
     * 查询流程启动时设置排他网关判断条件信息  
     * @param String gatewayId          排他网关Name信息, 流程启动时设置网关路线判断条件key为网关Name信息  
     * @param String processInstanceId  流程实例Id信息  
     * @return 
     */  
    public String getGatewayCondition(String gatewayId, String processInstanceId) {  
        Execution execution = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).singleResult();  
        if(runtimeService.getVariable(execution.getId(), gatewayId)!=null){
            return runtimeService.getVariable(execution.getId(), gatewayId).toString();
        }else{
        	return "";
        }
         
    }  
      
    /** 
     * 根据key和value判断el表达式是否通过信息  
     * @param String key    el表达式key信息  
     * @param String el     el表达式信息  
     * @param String value  el表达式传入值信息  
     * @return 
     */  
    public boolean isCondition(Map<String,Object> map, String el) {  
        ExpressionFactory factory = new ExpressionFactoryImpl();    
        SimpleContext context = new SimpleContext(); 
        for (String key : map.keySet()) {
               context.setVariable(key, factory.createValueExpression(map.get(key).toString(), String.class)); 
	    }   
        ValueExpression e = factory.createValueExpression(context, el, boolean.class);    
        return (Boolean) e.getValue(context);  
    }
    /**
     * 获取首环节处理角色（区分区域）
     * @param regCode 区域编码
     * @param procDefKey 模型标识
     * @return
     */
    public List<String> getFristNodeRole(String procDefKey,String regCode){

		 if(StrUtil.isNotEmpty(regCode)){
			 List<ProcessDefinition> list=repositoryService.createProcessDefinitionQuery().processDefinitionKey(procDefKey+"_"+regCode)
	                .orderByProcessDefinitionVersion().desc().list();
			if(list.size()>0){
				ProcessDefinition processDefinition=list.get(0);
				//如果有区域自定义模型，则使用自定义模型
				if(processDefinition!=null){
					procDefKey=procDefKey+"_"+regCode;
				}else{
					regCode=null;//如果使用的是公共类型，reCode置为null
				}
			}
		 }
         List<String> roleList=new ArrayList<String>();
         ProcessDefinitionQuery  processDefinitionQuery =repositoryService.createProcessDefinitionQuery().processDefinitionKey(procDefKey)
                                            .orderByProcessDefinitionVersion().desc();
         
         
         ProcessDefinition processDefinition=processDefinitionQuery.list().get(0);
              ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                                            .getDeployedProcessDefinition(processDefinition.getId());
              List<ActivityImpl> activityList=def.getActivities();
              for(ActivityImpl activityImpl : activityList){
                  ActivityBehavior activityBehavior = activityImpl.getActivityBehavior();
                  if (activityBehavior instanceof NoneStartEventActivityBehavior) {
                      PvmActivity fristTask=activityImpl.getOutgoingTransitions().get(0).getDestination();
                      TaskDefinition taskDefinition=(TaskDefinition)fristTask.getProperty("taskDefinition");
                      Set<Expression>  roleSet=taskDefinition.getCandidateGroupIdExpressions();
                      for(Expression expression:roleSet){
                        roleList.add(expression.getExpressionText());
                      }
                     break;
                  }
              }
        return roleList;
    }
    /**
     * 获取首环节处理角色
     * @param procDefKey 模型标识
     * @return
     */
    public List<String> getFristNodeRole(String procDefKey){
         List<String> roleList=new ArrayList<String>();
         ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().processDefinitionKey(procDefKey)
                                            .orderByProcessDefinitionVersion().desc().list().get(0);
              ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                                            .getDeployedProcessDefinition(processDefinition.getId());
              List<ActivityImpl> activityList=def.getActivities();
              for(ActivityImpl activityImpl : activityList){
                  ActivityBehavior activityBehavior = activityImpl.getActivityBehavior();
                  if (activityBehavior instanceof NoneStartEventActivityBehavior) {
                      PvmActivity fristTask=activityImpl.getOutgoingTransitions().get(0).getDestination();
                      TaskDefinition taskDefinition=(TaskDefinition)fristTask.getProperty("taskDefinition");
                      Set<Expression>  roleSet=taskDefinition.getCandidateGroupIdExpressions();
                      for(Expression expression:roleSet){
                        roleList.add(expression.getExpressionText());
                      }
                     break;
                  }
              }
        return roleList;
    }
	
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
			Task nextTask=this.getTask(businessTable, businessId);
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
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessTable+PromptMessageComm.COLON_SYMBOL+businessId, vars);
		
		//指定下一步审核人
		//将任务指定给当前班里人
		if(vars.containsKey("nextUserId")){
			Task nextTask=this.getTask(businessTable, businessId);
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
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(act.getProcDefKey(), act.getBusinessTable()+PromptMessageComm.COLON_SYMBOL+act.getBusinessId(), vars);
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
	 * 删除任务
	 * @param taskId 任务ID
	 * @param deleteReason 删除原因
	 */
	@Transactional(readOnly = false)
	public void deleteTask(String taskId, String deleteReason){
		taskService.deleteTask(taskId, deleteReason);
	}
	
	/**
	 * 签收任务
	 * @param taskId 任务ID
	 * @param userId 签收用户ID（用户登录名）
	 */
	@Transactional(readOnly = false)
	public void claim(String taskId, String userId){
		taskService.claim(taskId, userId);
	}
	
	/**
	 * 提交任务, 并保存意见
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param vars 任务变量
	 */
	@Transactional(readOnly = false)
	public void complete(String taskId, String procInsId, String comment, Map<String, Object> vars){
		complete(taskId, procInsId, comment, PromptMessageComm.KONG_SYMBOL, vars);
	}
	
	/**
	 * 提交任务, 并保存意见
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param title	程标题，显示在待办任务标题
	 * @param vars 任务变量
	 */
	@Transactional(readOnly = false)
	public void complete(String taskId, String procInsId, String comment, String title, Map<String, Object> vars){
		// 添加意见
		if (StrUtil.isNotBlank(procInsId) && StrUtil.isNotBlank(comment)){
			taskService.addComment(taskId, procInsId, comment);
		}
		
		// 设置流程变量
		if (vars == null){
			vars = Maps.newHashMap();
		}
		
		// 设置流程标题
		if (StrUtil.isNotBlank(title)){
			vars.put("title", title);
		}
		
		// 提交任务
		taskService.complete(taskId, vars);
	}
	/**
	 * 提交任务, 并保存意见
	 * @param businessKeyTable 业务表名称
	 * @param businessKeyID 业务数据主键
	 * @param comment 任务提交意见的内容
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars 任务变量
	 */
	@Transactional(readOnly = false)
	public void completeByBusiness(String businessKeyTable, String businessKeyID, String comment, String title, Map<String, Object> map){

		Map<String,Object> vars=Maps.newHashMap();
		for(String key:map.keySet()){
			if(StrUtil.isNotBlank(key)&&!key.equals("tableName")&&!key.equals("id")&&!key.equals("regId")){
				vars.put(key, map.get(key));
			}
		}
		
		//将任务指定给当前班里人
		List<String> list=this.getUserGroup(businessKeyTable, businessKeyID,vars);
		
		Task task=this.getTask(businessKeyTable, businessKeyID);
		String procInsId=task.getProcessInstanceId();
		String taskId=task.getId();
		// 添加意见
		if (StrUtil.isNotBlank(procInsId) && StrUtil.isNotBlank(comment)){
			taskService.addComment(taskId, procInsId, comment);
		}
		
		// 设置流程变量
		if (vars == null){
			vars = Maps.newHashMap();
		}
		
		// 设置流程标题
		if (StrUtil.isNotBlank(title)){
			vars.put("title", title);
		}
		//将任务指定给当前登陆人
		if(vars.containsKey(ActivityStateComm.ACTIVITY_VARIABLE_CURRUSER)){
			taskService.setAssignee(taskId, vars.get(ActivityStateComm.ACTIVITY_VARIABLE_CURRUSER).toString());
		}
		// 提交任务
		taskService.complete(taskId, vars);

		Task nextTask=this.getTask(businessKeyTable, businessKeyID);
		//指定下一步审核人
		if(list.size()>0&&vars.containsKey(ActivityStateComm.ACTIVITY_VARIABLE_NEXTUSER)&&nextTask!=null){
			taskService.setAssignee(nextTask.getId(), vars.get(ActivityStateComm.ACTIVITY_VARIABLE_NEXTUSER).toString());
		}
	}
	

	/**
	 * 委派任务
	 * @param taskId 任务ID
	 * @param userId 被委派人
	 */
	public void delegateTask(String taskId, String userId){
		taskService.delegateTask(taskId, userId);
	}
//	
//	/**
//	 * 被委派人完成任务
//	 * @param taskId 任务ID
//	 */
//	public void resolveTask(String taskId){
//		taskService.resolveTask(taskId);
//	}
//	
//	/**
//	 * 回退任务
//	 * @param taskId
//	 */
//	public void backTask(String taskId){
//		taskService.
//	}
	
	/**
	 * 添加任务意见
	 */
	public void addTaskComment(String taskId, String procInsId, String comment){
		taskService.addComment(taskId, procInsId, comment);
	}
	
	//////////////////  回退、前进、跳转、前加签、后加签、分裂 移植  https://github.com/bluejoe2008/openwebflow  ////////////////////////////////////////////////// 

	/**
	 * 任务后退一步
	 */
	public void taskBack(String procInsId, Map<String, Object> variables) {
		taskBack(getCurrentTask(procInsId), variables);
	}

	/**
	 * 任务后退至指定活动
	 */
	public void taskBack(TaskEntity currentTaskEntity, Map<String, Object> variables) {
		ActivityImpl activity = (ActivityImpl) ProcessDefUtils
				.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(), currentTaskEntity.getTaskDefinitionKey())
				.getIncomingTransitions().get(0).getSource();
		jumpTask(currentTaskEntity, activity, variables);
	}

	/**
	 * 任务前进一步
	 */
	public void taskForward(String procInsId, Map<String, Object> variables) {
		taskForward(getCurrentTask(procInsId), variables);
	}

	/**
	 * 任务前进至指定活动
	 */
	public void taskForward(TaskEntity currentTaskEntity, Map<String, Object> variables) {
		ActivityImpl activity = (ActivityImpl) ProcessDefUtils
				.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(), currentTaskEntity.getTaskDefinitionKey())
				.getOutgoingTransitions().get(0).getDestination();

		jumpTask(currentTaskEntity, activity, variables);
	}
	
	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 */
	public void jumpTask(String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables) {
		jumpTask(getCurrentTask(procInsId), targetTaskDefinitionKey, variables);
	}

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 */
	public void jumpTask(String procInsId, String currentTaskId, String targetTaskDefinitionKey, Map<String, Object> variables) {
		jumpTask(getTaskEntity(currentTaskId), targetTaskDefinitionKey, variables);
	}

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 * @param currentTaskEntity 当前任务节点
	 * @param targetTaskDefinitionKey 目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	public void jumpTask(TaskEntity currentTaskEntity, String targetTaskDefinitionKey, Map<String, Object> variables) {
		ActivityImpl activity = ProcessDefUtils.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(),
				targetTaskDefinitionKey);
		jumpTask(currentTaskEntity, activity, variables);
	}

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 * @param currentTaskEntity 当前任务节点
	 * @param targetActivity 目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	private void jumpTask(TaskEntity currentTaskEntity, ActivityImpl targetActivity, Map<String, Object> variables) {
		CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		commandExecutor.execute(new JumpTaskCmd(currentTaskEntity, targetActivity, variables));
	}
	
	/**
	 * 后加签
	 */
	@SuppressWarnings("unchecked")
	public ActivityImpl[] insertTasksAfter(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignees) {
		List<String> assigneeList = new ArrayList<String>();
		assigneeList.add(Authentication.getAuthenticatedUserId());
		assigneeList.addAll(CollectionUtils.arrayToList(assignees));
		String[] newAssignees = assigneeList.toArray(new String[0]);
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(procDefId);
		ActivityImpl prototypeActivity = ProcessDefUtils.getActivity(processEngine, processDefinition.getId(), targetTaskDefinitionKey);
		return cloneAndMakeChain(processDefinition, procInsId, targetTaskDefinitionKey, prototypeActivity.getOutgoingTransitions().get(0).getDestination().getId(), variables, newAssignees);
	}

	/**
	 * 前加签
	 */
	public ActivityImpl[] insertTasksBefore(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignees) {
		ProcessDefinitionEntity procDef = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(procDefId);
		return cloneAndMakeChain(procDef, procInsId, targetTaskDefinitionKey, targetTaskDefinitionKey, variables, assignees);
	}

	/**
	 * 分裂某节点为多实例节点
	 */
	public ActivityImpl splitTask(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignee) {
		return splitTask(procDefId, procInsId, targetTaskDefinitionKey, variables, true, assignee);
	}
	
	/**
	 * 分裂某节点为多实例节点
	 */
	@SuppressWarnings("unchecked")
	public ActivityImpl splitTask(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, boolean isSequential, String... assignees) {
		SimpleRuntimeActivityDefinitionEntity info = new SimpleRuntimeActivityDefinitionEntity();
		info.setProcessDefinitionId(procDefId);
		info.setProcessInstanceId(procInsId);

		RuntimeActivityDefinitionEntityIntepreter radei = new RuntimeActivityDefinitionEntityIntepreter(info);

		radei.setPrototypeActivityId(targetTaskDefinitionKey);
		radei.setAssignees(CollectionUtils.arrayToList(assignees));
		radei.setSequential(isSequential);
		
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(procDefId);
		ActivityImpl clone = new MultiInstanceActivityCreator().createActivities(processEngine, processDefinition, info)[0];

		TaskEntity currentTaskEntity = this.getCurrentTask(procInsId);
		
		CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		commandExecutor.execute(new CreateAndTakeTransitionCmd(currentTaskEntity, clone, variables));

		return clone;
	}

	private TaskEntity getCurrentTask(String procInsId) {
		return (TaskEntity) taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
	}

	private TaskEntity getTaskEntity(String taskId) {
		return (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
	}

	@SuppressWarnings("unchecked")
	private ActivityImpl[] cloneAndMakeChain(ProcessDefinitionEntity procDef, String procInsId, String prototypeActivityId, String nextActivityId, Map<String, Object> variables, String... assignees) {
		SimpleRuntimeActivityDefinitionEntity info = new SimpleRuntimeActivityDefinitionEntity();
		info.setProcessDefinitionId(procDef.getId());
		info.setProcessInstanceId(procInsId);

		RuntimeActivityDefinitionEntityIntepreter radei = new RuntimeActivityDefinitionEntityIntepreter(info);
		radei.setPrototypeActivityId(prototypeActivityId);
		radei.setAssignees(CollectionUtils.arrayToList(assignees));
		radei.setNextActivityId(nextActivityId);

		ActivityImpl[] activities = new ChainedActivitiesCreator().createActivities(processEngine, procDef, info);

		jumpTask(procInsId, activities[0].getId(), variables);

		return activities;
	}
	
	/**
	 * 读取带跟踪的图片
	 * @param executionId	环节ID
	 * @return	封装了各种节点信息
	 */
	public InputStream tracePhoto(String processDefinitionId, String executionId) {
//		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		
		List<String> activeActivityIds = Lists.newArrayList();
		if (runtimeService.createExecutionQuery().executionId(executionId).count() > 0){
			activeActivityIds = runtimeService.getActiveActivityIds(executionId);
		}
		

		// 使用spring注入引擎请使用下面的这行代码
		Context.setProcessEngineConfiguration(processEngineFactory.getProcessEngineConfiguration());
		return processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator()
				.generateDiagram(bpmnModel, PromptMessageComm.PNG, activeActivityIds);
	}
	
	/**
	 * 流程跟踪图信息
	 * @param processInstanceId		流程实例ID
	 * @return	封装了各种节点信息
	 */
	public List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception {
		Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();//执行实例
		Object property = PropertyUtils.getProperty(execution, "activityId");
		String activityId = PromptMessageComm.KONG_SYMBOL;
		if (property != null) {
			activityId = property.toString();
		}
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		List<ActivityImpl> activitiList = processDefinition.getActivities();//获得当前任务的所有节点

		List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
		for (ActivityImpl activity : activitiList) {

			boolean currentActiviti = false;
			String id = activity.getId();

			// 当前节点
			if (id.equals(activityId)) {
				currentActiviti = true;
			}

			Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, processInstance, currentActiviti);

			activityInfos.add(activityImageInfo);
		}

		return activityInfos;
	}
	

	/**
	 * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
	 * @param activity
	 * @param processInstance
	 * @param currentActiviti
	 * @return
	 */
	private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
			boolean currentActiviti) throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		Map<String, Object> activityInfo = new HashMap<String, Object>();
		activityInfo.put("currentActiviti", currentActiviti);
		setPosition(activity, activityInfo);
		setWidthAndHeight(activity, activityInfo);

		Map<String, Object> properties = activity.getProperties();
		vars.put(PromptMessageComm.NODE_NAME, properties.get("name"));
		vars.put(PromptMessageComm.TASK_TYPE, ActUtils.parseToZhType(properties.get("type").toString()));

		ActivityBehavior activityBehavior = activity.getActivityBehavior();
		if (activityBehavior instanceof UserTaskActivityBehavior) {

			Task currentTask = null;

			// 当前节点的task
			if (currentActiviti) {
				currentTask = getCurrentTaskInfo(processInstance);
			}

			// 当前任务的分配角色
			UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
			TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
			Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
			if (!candidateGroupIdExpressions.isEmpty()) {

				// 任务的处理角色
				setTaskGroup(vars, candidateGroupIdExpressions);

				// 当前处理人
				if (currentTask != null) {
					setCurrentTaskAssignee(vars, currentTask);
				}
			}
		}

		vars.put(PromptMessageComm.NODE_DESC, properties.get("documentation"));

		String description = activity.getProcessDefinition().getDescription();
		vars.put(PromptMessageComm.DESCIPTION, description);

		activityInfo.put("vars", vars);
		return activityInfo;
	}

	/**
	 * 设置任务组
	 * @param vars
	 * @param candidateGroupIdExpressions
	 */
	private void setTaskGroup(Map<String, Object> vars, Set<Expression> candidateGroupIdExpressions) {
		String roles = "";
		for (Expression expression : candidateGroupIdExpressions) {
			String expressionText = expression.getExpressionText();
			String roleName = identityService.createGroupQuery().groupId(expressionText).singleResult().getName();
			roles += roleName;
		}
		vars.put(PromptMessageComm.TASK_OF_ROLE, roles);
	}

	/**
	 * 设置当前处理人信息
	 * @param vars
	 * @param currentTask
	 */
	private void setCurrentTaskAssignee(Map<String, Object> vars, Task currentTask) {
		String assignee = currentTask.getAssignee();
		if (assignee != null) {
			org.activiti.engine.identity.User assigneeUser = identityService.createUserQuery().userId(assignee).singleResult();
			String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
			vars.put(PromptMessageComm.CURR_HANDLER, userInfo);
		}
	}

	/**
	 * 获取当前节点信息
	 * @param processInstance
	 * @return
	 */
	private Task getCurrentTaskInfo(ProcessInstance processInstance) {
		Task currentTask = null;
		try {
			String activitiId = (String) PropertyUtils.getProperty(processInstance, "activityId");

			currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey(activitiId)
					.singleResult();

		} catch (Exception e) {
			System.out.println(PromptMessageComm.SYSO_CAN_NOT_GET_ACTIVITY_ID + processInstance);
		}
		return currentTask;
	}

	/**
	 * 设置宽度、高度属性
	 * @param activity
	 * @param activityInfo
	 */
	private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put(PromptMessageComm.WIDTH, activity.getWidth());
		activityInfo.put(PromptMessageComm.HEIGHT, activity.getHeight());
	}

	/**
	 * 设置坐标位置
	 * @param activity
	 * @param activityInfo
	 */
	private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put(PromptMessageComm.NODE_X, activity.getX());
		activityInfo.put(PromptMessageComm.NODE_Y, activity.getY());
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}
	/**
	 * 检查用户是否存在,如果不存在，则添加
	 */
	public void updateUserRole(String userId,List<String> roleList){
		if(StrUtil.isNotBlank(userId)){
			//检查用户是否存在。如果不存在，则添加
			User user=identityService.createUserQuery().userId(userId).singleResult();
			if(user==null){
				User newUser=identityService.newUser(userId);
				identityService.saveUser(newUser);
			}
			
			for(String groupId:roleList){
				//检查用户组是否存在，不存在则添加
				Group group=identityService.createGroupQuery().groupId(groupId).singleResult();
				if(group==null){
					group=identityService.newGroup(groupId);
					identityService.saveGroup(group);
				}
				//检查关联关联是否存在。如果不存在，则添加
				Group groupUser=identityService.createGroupQuery().groupId(groupId).groupMember(userId).singleResult();
				if(groupUser==null){

					identityService.createMembership(userId, groupId);
				}
				
			}
		}
		
		
	}
	
}
