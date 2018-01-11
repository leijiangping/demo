package com.xunge.service.activity.utils;

import java.util.LinkedHashSet;
import java.util.Set;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;

import com.xunge.comm.system.PromptMessageComm;

/**
 * 流程定义相关操作的封装
 * @author bluejoe2008@gmail.com
 */
public abstract class ProcessDefUtils {
	
	public static ActivityImpl getActivity(ProcessEngine processEngine, String processDefId, String activityId) {
		ProcessDefinitionEntity pde = getProcessDefinition(processEngine, processDefId);
		return (ActivityImpl) pde.findActivity(activityId);
	}

	public static ProcessDefinitionEntity getProcessDefinition(ProcessEngine processEngine, String processDefId) {
		return (ProcessDefinitionEntity) ((RepositoryServiceImpl) processEngine.getRepositoryService()).getDeployedProcessDefinition(processDefId);
	}

	public static void grantPermission(ActivityImpl activity, String assigneeExpression, String candidateGroupIdExpressions,
			String candidateUserIdExpressions) throws Exception {
		TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activity.getActivityBehavior()).getTaskDefinition();
		taskDefinition.setAssigneeExpression(assigneeExpression == null ? null : new FixedValue(assigneeExpression));
		FieldUtils.writeField(taskDefinition, PromptMessageComm.CANDIDATE_USERID_EXPRESS, ExpressionUtils.stringToExpressionSet(candidateUserIdExpressions), true);
		FieldUtils
				.writeField(taskDefinition, PromptMessageComm.CANDIDATE_GROUPID_EXPRESS, ExpressionUtils.stringToExpressionSet(candidateGroupIdExpressions), true);

		Logger.getLogger(ProcessDefUtils.class).info(
				String.format(PromptMessageComm.FORMAT, assigneeExpression, candidateGroupIdExpressions,
						candidateUserIdExpressions, activity.getProcessDefinition().getKey(), activity.getProperty("name")));
	}

	/**
	 * 实现常见类型的expression的包装和转换
	 * 
	 * @author bluejoe2008@gmail.com
	 * 
	 */
	public static class ExpressionUtils {
		public static Expression stringToExpression(ProcessEngineConfigurationImpl conf, String expr) {
			return conf.getExpressionManager().createExpression(expr);
		}

		public static Expression stringToExpression(String expr) {
			return new FixedValue(expr);
		}

		public static Set<Expression> stringToExpressionSet(String exprs) {
			Set<Expression> set = new LinkedHashSet<Expression>();
			for (String expr : exprs.split(";")) {
				set.add(stringToExpression(expr));
			}

			return set;
		}
	}
}