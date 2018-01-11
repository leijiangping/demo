package com.xunge.model.towerrent.bizbasedata;

import java.io.Serializable;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 铁塔种类
 */
public class TwrProductTypeVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7483728472938729193L;
	//铁塔种类编码
	private String producttypeId;
	//铁塔种类名称
	private String producttypeName;
	//0：正常   9：停用   -1：删除
	private int producttypeState;
	//备注
	private String producttypeNote;
	//父级铁塔种类编码
	private String producttypePid;
	//父级铁塔种类名称
	private String pproductName;
	
	private boolean open = true;
	
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
	public String getProducttypePid() {
		return producttypePid;
	}
	public void setProducttypePid(String producttypePid) {
		this.producttypePid = producttypePid;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public String getPproductName() {
		return pproductName;
	}
	public void setPproductName(String pproductName) {
		this.pproductName = pproductName;
	}
}
