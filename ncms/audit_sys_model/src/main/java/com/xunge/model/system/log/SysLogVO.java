package com.xunge.model.system.log;

import java.io.Serializable;


/**
 * 
 * @author ChangWQ 系统日志
 */
public class SysLogVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6137019395599244116L;
	//日志编码
	private String logId;
	//日志产生省份编码
	private String prvId;
	//日志产生省份名称
	private String prvName;
	//用户编码
	private String userId;
	//用户名
	private String userName;
	//菜单编码
	private String menuId;
	//操作日志种类
	private int ilogType;
	//日志类型
	private String logType;
	//日志操作IP
	private String logIp;
	//日志操作时间
	private String logTime;
	//日志描述
	private String logNote;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getPrvId() {
		return prvId;
	}

	public void setPrvId(String prvId) {
		this.prvId = prvId;
	}

	public String getPrvName() {
		return prvName;
	}

	public void setPrvName(String prvName) {
		this.prvName = prvName;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogIp() {
		return logIp;
	}

	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getLogNote() {
		return logNote;
	}

	public void setLogNote(String logNote) {
		this.logNote = logNote;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getIlogType() {
		return ilogType;
	}

	public void setIlogType(int ilogType) {
		this.ilogType = ilogType;
	}

	public SysLogVO() {
		super();
	}

	public SysLogVO(String logId, String userName, String userId, String menuId,
			int ilogType, String logType, String logIp, String logTime, String logNote) {
		super();
		this.logId = logId;
		this.userId = userId;
		this.userName = userName;
		this.menuId = menuId;
		this.ilogType = ilogType;
		this.logType = logType;
		this.logIp = logIp;
		this.logTime = logTime;
		this.logNote = logNote;
	}

	@Override
	public String toString() {
		return "SysLogVO [logId=" + logId + ", userName=" + userName + ", userId=" + userId + ", menuId="
				+ menuId + ", ilogType=" + ilogType + ", logType=" + logType + ", logIp=" + logIp
				+ ", logTime=" + logTime + ", logNote=" + logNote + "]";
	}


}