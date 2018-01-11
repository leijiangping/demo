package com.xunge.service.activity;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.transaction.annotation.Transactional;

import com.xunge.core.page.Page;
import com.xunge.model.activity.Act;

/**
 * @author zhujj
 * @date 2017年6月16日 下午5:18:24 
 * @version 1.0.0 
 */
public interface IActTaskService {
	/**
	 * 获取待办列表
	 * @param procDefKey 流程定义标识
	 * @param Assignee 用户登陆名
	 * @param assigneeNameGroup 用户角色
	 * @return
	 */
	public List<Act> todoList(Act act);
	/**
	 * 获取待办列表(区分区域)(分页)
	 * @param procDefKey 流程定义标识
	 * @param regCode 区分区域，为空则不区分区域
	 * @param BusinessTable 业务表名
	 * @param Assignee 用户登陆名
	 * @param assigneeNameGroup 用户角色
	 * @return
	 */
	public Page<Act> todoListPage(Page<Act> page,Act act);
	/**
	 * 获取已办任务
	 * @param page
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	public Page<Act> historicList(Page<Act> page, Act act);
	/**
	 * 获取流转历史列表
	 * @param procInsId 流程实例
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	public List<Act> histoicFlowList(String procInsId, String startAct, String endAct);

	/**
	 * 根据业务表名和ID获取流转历史列表
	 * @param businessKeyTable 业务表名
	 * @param businessKeyID 业务ID
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	public List<Act> histoicFlowList(String businessKeyTable,String businessKeyID, String startAct, String endAct);
	/**
	 * 获取流程列表
	 * @param category 流程分类
	 */
	public Page<Object[]> processList(Page<Object[]> page, String category);
	
	/**
	 * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
	 * @return
	 */
	public String getFormKey(String procDefId, String taskDefKey);
	
	/**
	 * 获取流程实例对象
	 * @param procInsId
	 * @return
	 */
	@Transactional(readOnly = false)
	public ProcessInstance getProcIns(String procInsId) ;
	/**
	 * 获取流程实例对象
	 * @param businessKeyTable 业务表名
	 * @param businessKeyID 业务ID
	 * @return
	 */
	@Transactional(readOnly = false)
	public Execution getExecution(String businessKeyTable,String businessKeyID);
	/**
	 * 获取流程实例对象
	 * @param businessKeyTable 业务表名
	 * @param businessKeyID 业务ID
	 * @return
	 */
	@Transactional(readOnly = false)
	public Task getTask(String businessKeyTable,String businessKeyID);
	
	/**
     * 查询任务对应的下一步审批角色
	 * @param businessKeyTable 业务表名
	 * @param businessKeyID 业务ID
     * @return List<String> 角色列表
     */
    public List<String> getUserGroup(String businessKeyTable,String businessKeyID,Map<String,Object> elName);
	/**
	 * 获取流程下一步角色组
	 * @param businessKeyTable 业务表名
	 * @param businessKeyID 业务ID
	 * @return
	 */
	@Transactional(readOnly = false)
	public Task getNextGroup(String businessKeyTable,String businessKeyID);
	
	
	 /**
     * 获取首环节处理角色
     * @param procDefKey 模型标识
     * @return
     */
    public List<String> getFristNodeRole(String procDefKey);
    /**
     * 获取首环节处理角色（区分区域）
     * @param regCode 区域编码
     * @param procDefKey 模型标识
     * @return
     */
    public List<String> getFristNodeRole(String procDefKey,String regCode);
	
	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars			流程变量
	 * @param assignee	审核人登陆名
	 * @param assigneeNameGroup 用户角色 
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId, String title, Map<String, Object> vars,String assignee);
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
	public String startProcess(String procDefKey,String regCode, String businessTable, String businessId, String title, Map<String, Object> vars,String assignee);
	
	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars			流程变量
	 * @param assignee	用户登陆名
	 * @param assigneeNameGroup 用户角色 
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(Act act);

	/**
	 * 获取任务
	 * @param taskId 任务ID
	 */
	public Task getTask(String taskId);
	
	/**
	 * 删除任务
	 * @param taskId 任务ID
	 * @param deleteReason 删除原因
	 */
	@Transactional(readOnly = false)
	public void deleteTask(String taskId, String deleteReason);
	
	/**
	 * 签收任务
	 * @param taskId 任务ID
	 * @param userId 签收用户ID（用户登录名）
	 */
	@Transactional(readOnly = false)
	public void claim(String taskId, String userId);
	
	/**
	 * 提交任务, 并保存意见
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param vars 任务变量
	 */
	@Transactional(readOnly = false)
	public void complete(String taskId, String procInsId, String comment, Map<String, Object> vars);
	
	/**
	 * 提交任务, 并保存意见
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars 任务变量
	 */
	@Transactional(readOnly = false)
	public void complete(String taskId, String procInsId, String comment, String title, Map<String, Object> vars);
	
	/**
	 * 提交任务, 并保存意见
	 * @param businessKeyTable 业务表名称
	 * @param businessKeyID 业务数据主键
	 * @param comment 任务提交意见的内容
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars 任务变量
	 */
	@Transactional(readOnly = false)
	public void completeByBusiness(String businessKeyTable, String businessKeyID, String comment, String title, Map<String, Object> vars);


	/**
	 * 添加任务意见
	 */
	public void addTaskComment(String taskId, String procInsId, String comment);
	
	/**
	 * 任务后退一步
	 */
	public void taskBack(String procInsId, Map<String, Object> variables) ;

	/**
	 * 任务后退至指定活动
	 */
	public void taskBack(TaskEntity currentTaskEntity, Map<String, Object> variables) ;

	/**
	 * 任务前进一步
	 */
	public void taskForward(String procInsId, Map<String, Object> variables) ;

	/**
	 * 任务前进至指定活动
	 */
	public void taskForward(TaskEntity currentTaskEntity, Map<String, Object> variables) ;
	
	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 */
	public void jumpTask(String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables);

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 */
	public void jumpTask(String procInsId, String currentTaskId, String targetTaskDefinitionKey, Map<String, Object> variables);

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 * @param currentTaskEntity 当前任务节点
	 * @param targetTaskDefinitionKey 目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	public void jumpTask(TaskEntity currentTaskEntity, String targetTaskDefinitionKey, Map<String, Object> variables);

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 * @param currentTaskEntity 当前任务节点
	 * @param targetActivity 目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	//private void jumpTask(TaskEntity currentTaskEntity, ActivityImpl targetActivity, Map<String, Object> variables);
	
	/**
	 * 后加签
	 */
	public ActivityImpl[] insertTasksAfter(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignees) ;

	/**
	 * 前加签
	 */
	public ActivityImpl[] insertTasksBefore(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignees);

	/**
	 * 分裂某节点为多实例节点
	 */
	public ActivityImpl splitTask(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignee) ;
	
	/**
	 * 分裂某节点为多实例节点
	 */
	public ActivityImpl splitTask(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, boolean isSequential, String... assignees) ;

	/**
	 * 读取带跟踪的图片
	 * @param executionId	环节ID
	 * @return	封装了各种节点信息
	 */
	public InputStream tracePhoto(String processDefinitionId, String executionId) ;
	
	/**
	 * 流程跟踪图信息
	 * @param processInstanceId		流程实例ID
	 * @return	封装了各种节点信息
	 */
	public List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception ;
	
	public ProcessEngine getProcessEngine() ;
}
