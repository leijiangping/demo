package com.xunge.model.job;

import java.sql.Blob;
import java.util.Date;

public class TaskHistoryInfoVO {
    String taskHistoryId; //任务历史表编码
    String taskInfoId; //任务信息表编码
    String prvId; //省份编码
    Date startDatetime; //启动时间
    String comment; //反馈记录
//    byte[] errorInfo; //错误信息
    
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
//	public byte[] getErrorInfo() {
//		return errorInfo;
//	}
//	public void setErrorInfo(byte[] errorInfo) {
//		this.errorInfo = errorInfo;
//	}
}
