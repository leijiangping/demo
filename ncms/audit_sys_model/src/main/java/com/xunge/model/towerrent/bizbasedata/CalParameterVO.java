package com.xunge.model.towerrent.bizbasedata;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jcy
 * @date 2017年7月6日
 * @description 计算参数
 */
public class CalParameterVO implements Serializable{
	/**
	 * 
	 */	
	private static final long serialVersionUID = -2556654951578937582L;
	//计算参数分区编码
	private String calcparameterregionId;
	//区域编码
	private String regId;
	//区域名称
	private String regName;
	//计算参数编码
	private String calcparameterId;
	//计算参数数值
	private BigDecimal calcparameterValue;
	//计算参数名称
	private String  calcparameterName;
	//计算参数关键字
	private String  calcparameterCode;
	//计算参数状态 0：正常   9：停用    -1：删除
	private int calcparameterState;
	//父级区域名称
	private String pregName;
	//父级区域ID
	private String pregId;
	//计算参数备注
	private String calcparameterNote;
	
	public String getCalcparameterregionId() {
		return calcparameterregionId;
	}
	public void setCalcparameterregionId(String calcparameterregionId) {
		this.calcparameterregionId = calcparameterregionId;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getCalcparameterId() {
		return calcparameterId;
	}
	public void setCalcparameterId(String calcparameterId) {
		this.calcparameterId = calcparameterId;
	}
	public BigDecimal getCalcparameterValue() {
		return calcparameterValue;
	}
	public void setCalcparameterValue(BigDecimal calcparameterValue) {
		this.calcparameterValue = calcparameterValue;
	}
	public int getCalcparameterState() {
		return calcparameterState;
	}
	public void setCalcparameterState(int calcparameterState) {
		this.calcparameterState = calcparameterState;
	}
	public String getCalcparameterNote() {
		return calcparameterNote;
	}
	public void setCalcparameterNote(String calcparameterNote) {
		this.calcparameterNote = calcparameterNote;
	}
	public String getCalcparameterName() {
		return calcparameterName;
	}
	public void setCalcparameter_name(String calcparameterName) {
		this.calcparameterName = calcparameterName;
	}
	public String getCalcparameterCode() {
		return calcparameterCode;
	}
	public void setCalcparameterCode(String calcparameterCode) {
		this.calcparameterCode = calcparameterCode;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public String getPregName() {
		return pregName;
	}
	public void setPregName(String pregName) {
		this.pregName = pregName;
	}
	public void setCalcparameterName(String calcparameterName) {
		this.calcparameterName = calcparameterName;
	}
	public String getPregId() {
		return pregId;
	}
	public void setPregId(String pregId) {
		this.pregId = pregId;
	}
}
