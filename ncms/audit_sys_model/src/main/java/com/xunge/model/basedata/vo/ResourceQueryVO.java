package com.xunge.model.basedata.vo;

import com.xunge.core.model.UserLoginInfo;


/**
 * 机房查询 VO
 * 
 * Title: RoomQueryVO
 * @author Rob
 */
public class ResourceQueryVO extends BaseDataVO {

	private String prvId;
	private String city;
	private String region;
	private String reg;
	private Integer resType;
	private Integer property;
	private Integer auditStatus;
	private Integer status;
	private Integer queryType;
	// 登录用户信息
	private UserLoginInfo loginUser;
	
	public String getPrvId() {
		return prvId;
	}

	public void setPrvId(String prvId) {
		this.prvId = prvId;
	}

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

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg =reg==null?reg:reg.trim();
	}

	public Integer getResType() {
		return resType;
	}

	public void setResType(Integer resType) {
		this.resType = resType;
	}

	public Integer getProperty() {
		return property;
	}

	public void setProperty(Integer property) {
		this.property = property;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
