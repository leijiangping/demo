package com.xunge.comm.job;

import java.io.Serializable;

/**
 * @ClassName: BaseRet
 * @Description: 返回实体基类
 * 
 */
public class BaseRet implements Serializable {
	private static final long serialVersionUID = -6451729909524747633L;
	protected Integer status;// 响应状态码
	protected Integer total;//总数量
	protected String message;// 状态描述
	protected Object data;// 返回数据
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
