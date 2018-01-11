package com.xunge.model.basedata.colletion;

import java.util.Date;

public class TaskHistoryInfoVO {
    String taskHistoryId; //任务历史表编码
    String taskInfoId; //任务信息表编码
    String prvId; //省份编码
    String prvName;
    Date startDatetime; //启动时间
    String comment; //反馈记录
    String taskName;
    String ftpUser;
    
	public String getTaskHistoryId() {
		return taskHistoryId;
	}
	public void setTaskHistoryId(String taskHistoryId) {
		this.taskHistoryId = taskHistoryId;
	}
	public String getTaskInfoId() {
		return taskInfoId;
	}
	public void setTaskInfoId(String taskInfoId) {
		this.taskInfoId = taskInfoId;
	}
	public String getPrvId() {
		return prvId;
	}
	public void setPrvId(String prvId) {
		this.prvId = prvId;
	}
	public Date getStartDatetime() {
		return startDatetime;
	}
	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = startDatetime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getFtpUser() {
		return ftpUser;
	}
	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	public String getPrvName() {
		return prvName;
	}
	public void setPrvName(String prvName) {
		this.prvName = prvName;
	}
}
