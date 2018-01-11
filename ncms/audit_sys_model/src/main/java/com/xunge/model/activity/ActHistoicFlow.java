package com.xunge.model.activity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.TimeUtils;

/**
 * 工作流Entity
 * @author xup
 */
public class ActHistoicFlow  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 781304658798409233L;
	private String title; 		// 任务标题
	private String comment; 	// 任务意见
	private String assignee; // 任务执行人名称
	private Date beginDate;	// 开始查询日期
	private Date endDate;// 结束查询日期
	private String durationTime;//历时
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment == null ? "无" : comment;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public ActHistoicFlow(String title, String comment, String assignee,
			Date beginDate, Date endDate,String durationTime) {
		super();
		this.title = title;
		this.comment = comment;
		this.assignee = assignee;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.durationTime = durationTime;
	}
	public ActHistoicFlow() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDurationTime() {
		return durationTime;
	}
	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	


}


