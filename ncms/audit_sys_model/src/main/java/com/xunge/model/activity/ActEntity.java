package com.xunge.model.activity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Activiti Entity类
 * @author zhujj
 * @version 2013-05-28
 */
public abstract class ActEntity<T>  implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6819056399126628592L;
	protected Act act; 		// 流程任务对象

	public ActEntity() {
		super();
	}
	
	
	@JsonIgnore
	public Act getAct() {
		if (act == null){
			act = new Act();
		}
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	/**
	 * 获取流程实例ID
	 * @return
	 */
	public String getProcInsId() {
		return this.getAct().getProcInsId();
	}

	/**
	 * 设置流程实例ID
	 * @param procInsId
	 */
	public void setProcInsId(String procInsId) {
		this.getAct().setProcInsId(procInsId);
	}
}
