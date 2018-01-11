package com.xunge.model.towerrent.bizbasedata;

import java.io.Serializable;
/**
 * @author zhaosf
 * @date 2017年10月16日
 * @description 铁塔种类
 */
public class ProductTypeVO implements Serializable{
	
	private static final long serialVersionUID = -7483728472938729193L;
	//铁塔种类编码
	private String producttypeId;
	//铁塔种类名称
	private String producttypeName;
	//0：正常   9：停用   -1：删除
	private int producttypeState;
	//备注
	private String producttypeNote;
	
	public String getProducttypeId() {
		return producttypeId;
	}
	public void setProducttypeId(String producttypeId) {
		this.producttypeId = producttypeId;
	}
	public String getProducttypeName() {
		return producttypeName;
	}
	public void setProducttypeName(String producttypeName) {
		this.producttypeName = producttypeName;
	}
	public int getProducttypeState() {
		return producttypeState;
	}
	public void setProducttypeState(int producttypeState) {
		this.producttypeState = producttypeState;
	}
	public String getProducttypeNote() {
		return producttypeNote;
	}
	public void setProducttypeNote(String producttypeNote) {
		this.producttypeNote = producttypeNote;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
