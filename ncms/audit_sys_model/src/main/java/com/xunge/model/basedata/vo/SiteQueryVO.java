package com.xunge.model.basedata.vo;

import java.io.Serializable;

import com.xunge.core.model.UserLoginInfo;


/**
 * 站点查询 VO
* 
* Title: SiteQueryVO
* @author Rob
 */
public class SiteQueryVO extends BaseDataVO implements Serializable{

	private static final long serialVersionUID = 1128476128853139318L;
	private String siteReg;
	private String prvId;
	private String city;
	private String region;
	private String property;
	private Integer auditStatus;
	private Integer status;
	private Integer dataFrom;
	private Integer queryType;
	private Integer basesiteState;
	
	public Integer getBasesiteState() {
		return basesiteState;
	}

	public void setBasesiteState(Integer basesiteState) {
		this.basesiteState = basesiteState;
	}

	// 登录用户信息
	private UserLoginInfo loginUser;

	public String getPrvId() {
		return prvId;
	}

	public void setPrvId(String prvId) {
		this.prvId = prvId;
	}

	public String getSiteReg() {
		return siteReg;
	}

	public void setSiteReg(String siteReg) {
		this.siteReg = siteReg==null?siteReg:siteReg.trim();
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

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
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
