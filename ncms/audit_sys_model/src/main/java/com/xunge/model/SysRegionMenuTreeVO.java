package com.xunge.model;

import java.io.Serializable;

public class SysRegionMenuTreeVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1177295532772804926L;
	
	private String id;
	private String pid;
	private String code;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "SysRegionMenuTreeVO [id=" + id + ", pid=" + pid + ", code="
				+ code + ", name=" + name + "]";
	}
	
}
