package com.xunge.model.basedata.vo;

import java.io.Serializable;

import com.xunge.core.model.UserLoginInfo;

/**
 * 设备查询 VO
 * 
 * Title: DeviceQueryVO
 * 
 * @author Rob
 */
public class DeviceQueryVO extends BaseDataVO implements Serializable {

	private static final long serialVersionUID = 4779790854632941595L;
	private Integer devType;
	private String reg;
	private Integer status;
	// 登录用户信息
	private UserLoginInfo loginUser;

	public Integer getDevType() {
		return devType;
	}

	public void setDevType(Integer devType) {
		this.devType = devType;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public UserLoginInfo getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(UserLoginInfo loginUser) {
		this.loginUser = loginUser;
	}
}