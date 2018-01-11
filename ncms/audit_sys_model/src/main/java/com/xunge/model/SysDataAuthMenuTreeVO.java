package com.xunge.model;

import java.io.Serializable;

/**
 * 树节点父类
 * @author Administrator
 * @date 2017年6月7日
 * @description
 */
public class SysDataAuthMenuTreeVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6763250077312445412L;
	
	private String id;
	private String pid;
	private String name;
	private boolean open = true;
	
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
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	@Override
	public String toString() {
		return "SysDataAuthMenuTreeVO [id=" + id + ", pid=" + pid + ", name="
				+ name + ", open=" + open + "]";
	}
	
}
