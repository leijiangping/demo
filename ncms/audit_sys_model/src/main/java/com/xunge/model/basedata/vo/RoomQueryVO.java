package com.xunge.model.basedata.vo;

import com.xunge.core.model.UserLoginInfo;


/**
 * 机房查询 VO
 * 
 * Title: RoomQueryVO
 * @author Rob
 */
public class RoomQueryVO extends BaseDataVO {

	private String city;
	private String region;
	private String roomReg;
	private String property;
	private String auditStatus;
	private String status;
	private Integer dataFrom;
	private Integer queryType;
	// 登录用户信息
	private UserLoginInfo loginUser;
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRoomReg() {
		return roomReg;
	}

	public void setRoomReg(String roomReg) {
		this.roomReg = roomReg;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(Integer dataFrom) {
		this.dataFrom = dataFrom;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public UserLoginInfo getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(UserLoginInfo loginUser) {
		this.loginUser = loginUser;
	}
}
