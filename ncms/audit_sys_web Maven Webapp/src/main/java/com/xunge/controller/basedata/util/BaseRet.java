package com.xunge.controller.basedata.util;

import java.io.Serializable;

/**
 * @ClassName: BaseRet
 * 
 */
public class BaseRet implements Serializable {
	private static final long serialVersionUID = -6451729909524747633L;
	protected Integer status;
	protected Integer total;
	protected String message;
	protected Object data;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
	
	
}
