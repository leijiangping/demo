package com.xunge.model.smsSendHistroy;

import java.util.Date;

public class SysSmssendHistroyVO {
    //集团数据收集短信下发历史表编码
    private String sysSmssendHistoryId;
    //集团数据收集表编码
    private String bussTableId;
    //短信业务名称
    private String bussName;
    //所属省份编码
    private String prvId;
    //所属省份名称
    private String prvSname;
    //发信人编码
    private String smsSenderId;
    //发信人名称
    private String smsSenderName;
    //收件人角色编码
    private String smsRecvRoleId;
    //短信接收人
    private String smsRecvUserId;
    //短信模板ID
    private String smsTempId;
    //是否发送成功（0-成功，-1-失败）
    private Integer isSendSuccess;
    //发送回执信息
    private String sendBackMsg;
    //发送时间
    private String sendDatetime;
    //发送次数
    private Integer resendTimes;
    
    private String roleName;
    private String userName;
	public String getSysSmssendHistoryId() {
		return sysSmssendHistoryId;
	}
	public void setSysSmssendHistoryId(String sysSmssendHistoryId) {
		this.sysSmssendHistoryId = sysSmssendHistoryId;
	}
	public String getBussTableId() {
		return bussTableId;
	}
	public void setBussTableId(String bussTableId) {
		this.bussTableId = bussTableId;
	}
	public String getPrvId() {
		return prvId;
	}
	public void setPrvId(String prvId) {
		this.prvId = prvId;
	}
	public String getSmsRecvRoleId() {
		return smsRecvRoleId;
	}
	public void setSmsRecvRoleId(String smsRecvRoleId) {
		this.smsRecvRoleId = smsRecvRoleId;
	}
	public String getSmsRecvUserId() {
		return smsRecvUserId;
	}
	public void setSmsRecvUserId(String smsRecvUserId) {
		this.smsRecvUserId = smsRecvUserId;
	}
	public Integer getIsSendSuccess() {
		return isSendSuccess;
	}
	public void setIsSendSuccess(Integer isSendSuccess) {
		this.isSendSuccess = isSendSuccess;
	}
	public String getSendBackMsg() {
		return sendBackMsg;
	}
	public void setSendBackMsg(String sendBackMsg) {
		this.sendBackMsg = sendBackMsg;
	}
	public String getSendDatetime() {
		return sendDatetime;
	}
	public void setSendDatetime(String sendDatetime) {
		this.sendDatetime = sendDatetime;
	}
	public Integer getResendTimes() {
		return resendTimes;
	}
	public void setResendTimes(Integer resendTimes) {
		this.resendTimes = resendTimes;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBussName() {
		return bussName;
	}
	public void setBussName(String bussName) {
		this.bussName = bussName;
	}
	public String getPrvSname() {
		return prvSname;
	}
	public void setPrvSname(String prvSname) {
		this.prvSname = prvSname;
	}
	public String getSmsSenderId() {
		return smsSenderId;
	}
	public void setSmsSenderId(String smsSenderId) {
		this.smsSenderId = smsSenderId;
	}
	public String getSmsSenderName() {
		return smsSenderName;
	}
	public void setSmsSenderName(String smsSenderName) {
		this.smsSenderName = smsSenderName;
	}
	public String getSmsTempId() {
		return smsTempId;
	}
	public void setSmsTempId(String smsTempId) {
		this.smsTempId = smsTempId;
	}

}