package com.xunge.model.basedata.vo;

import java.io.Serializable;

import com.xunge.core.model.UserLoginInfo;



/**
 * 供应商查询 VO
* 
* Title: SupplierQueryVO
* @author Rob
 */
public class SupplierQueryVO extends BaseDataVO implements Serializable{

	private static final long serialVersionUID = 4779790854632941595L;
	private String prvId;
	private String city;
	private String region;
	private String supplierReg;
	private Integer state;
	private Integer queryType;
	private Integer DataFrom;
	public Integer getDataFrom() {
		return DataFrom;
	}

	public void setDataFrom(Integer dataFrom) {
		DataFrom = dataFrom;
	}

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

	public String getSupplierReg() {
		return supplierReg;
	}

	public void setSupplierReg(String supplierReg) {
		this.supplierReg = supplierReg;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public UserLoginInfo getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(UserLoginInfo loginUser) {
		this.loginUser = loginUser;
	}
}
