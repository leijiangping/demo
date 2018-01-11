package com.xunge.model.towerrent.bizbasedata;

import java.io.Serializable;

/**
 * @author jcy
 * @date 2017年7月6日
 * @description 风压
 */
public class WindPressureVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1653432326798545556L;
	//风压范围编码
    private String windpressureId;
    //风压范围名称
    private String windpressureName;
    //风压范围上限
    private double windpressureUpper;
    //风压范围下限
    private double windpressureLower;
    //风压范围状态
    private int windpressureState;
    //风压范围备注
    private String windpressureNote;
    
	public String getWindpressureId() {
		return windpressureId;
	}
	public void setWindpressureId(String windpressureId) {
		this.windpressureId = windpressureId;
	}
	public String getWindpressureName() {
		return windpressureName;
	}
	public void setWindpressureName(String windpressureName) {
		this.windpressureName = windpressureName;
	}
	public double getWindpressureUpper() {
		return windpressureUpper;
	}
	public void setWindpressureUpper(double windpressureUpper) {
		this.windpressureUpper = windpressureUpper;
	}
	public double getWindpressureLower() {
		return windpressureLower;
	}
	public void setWindpressureLower(double windpressureLower) {
		this.windpressureLower = windpressureLower;
	}
	public int getWindpressureState() {
		return windpressureState;
	}
	public void setWindpressureState(int windpressureState) {
		this.windpressureState = windpressureState;
	}
	public String getWindpressureNote() {
		return windpressureNote;
	}
	public void setWindpressureNote(String windpressureNote) {
		this.windpressureNote = windpressureNote;
	}
}
