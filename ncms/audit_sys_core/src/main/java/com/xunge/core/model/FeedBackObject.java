package com.xunge.core.model;

import java.io.Serializable;

/**
 * @author SongJL
 * @Description 前后台交互用，Obj放置返回实体
 */
public class FeedBackObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9043692838434502270L;
	
	public String success;
	public String msg;
	public Object Obj;
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return Obj;
	}
	public void setObj(Object obj) {
		Obj = obj;
	}
	
	@Override
	public String toString() {
		return "[success=" + success + ", msg=" + msg + ", Obj="
				+ Obj + "]";
	}
	
}
