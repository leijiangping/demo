package com.xunge.service.activity;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.xunge.model.activity.Act;

/**
 * @author zhujj
 * @date 2017年6月16日 下午5:18:24 
 * @version 1.0.0 
 */
public interface IActTaskService {
	
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
}
