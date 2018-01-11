package com.xunge.model.towerrent.bizbasedata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jcy
 * @date 2017年7月6日
 * @description 挂高
 */
public class HighUpVO implements Serializable{
	/**
	 * 
	 */	
	private static final long serialVersionUID = -3336548135454569682L;
	//挂高范围编码
	private String highupId;
	//铁塔种类编码
	private String producttypeId;
	//铁塔种类名称
	private String producttypeName;
	//挂高范围名称
	private String highupName;
	//挂高范围上限
	private BigDecimal highupUpper;
	//挂高范围下限
	private BigDecimal highupLower;
	//挂高范围状态
	private Integer highupState;
	//挂高范围备注
	private String highupNote;
	
	public String getHighupId() {
		return highupId;
	}
	public void setHighupId(String highupId) {
		this.highupId = highupId;
	}
	public String getProducttypeId() {
		return producttypeId;
	}
	public void setProducttypeId(String producttypeId) {
		this.producttypeId = producttypeId;
	}
	public String getHighupNote() {
		return highupNote;
	}
	public void setHighupNote(String highupNote) {
		this.highupNote = highupNote;
	}
	public String getHighupName() {
		return highupName;
	}
	public void setHighupName(String highupName) {
		this.highupName = highupName;
	}
	public BigDecimal getHighupUpper() {
		return highupUpper;
	}
	public void setHighupUpper(BigDecimal highupUpper) {
		this.highupUpper = highupUpper;
	}
	public BigDecimal getHighupLower() {
		return highupLower;
	}
	public void setHighupLower(BigDecimal highupLower) {
		this.highupLower = highupLower;
	}
	public Integer getHighupState() {
		return highupState;
	}
	public void setHighupState(Integer highupState) {
		this.highupState = highupState;
	}
	public String getProducttypeName() {
		return producttypeName;
	}
	public void setProducttypeName(String producttypeName) {
		this.producttypeName = producttypeName;
	}
}
