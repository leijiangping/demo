package com.xunge.model.activity;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程管理VO类
 */
public class ActProcessVO  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8245244988830346163L;
	private String category;
	private String id;
	private String Key;
	private String name;
	private String version;
	private Date deploymentTime;
	private String deploymentId;
	private boolean suspended;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Date getDeploymentTime() {
		return deploymentTime;
	}
	public void setDeploymentTime(Date deploymentTime) {
		this.deploymentTime = deploymentTime;
	}
	public String getKey() {
		return Key;
	}
	public void setKey(String key) {
		Key = key;
	}
	
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	
	public boolean isSuspended() {
		return suspended;
	}
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}
	@Override
	public String toString() {
		return "ActProcessVO [category=" + category + ", id=" + id + ", Key="
				+ Key + ", name=" + name + ", version=" + version
				+ ", deploymentTime=" + deploymentTime + "]";
	}

    
}