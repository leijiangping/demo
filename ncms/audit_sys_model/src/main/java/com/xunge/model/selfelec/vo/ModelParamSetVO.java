package com.xunge.model.selfelec.vo;

import java.math.BigDecimal;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class ModelParamSetVO {
	private String regId; //所属区域编码
	@Excel(name="地市名称",orderNum="1")
	private String regName;//地市名称
	@Excel(name="月份",orderNum="2")
	private int monthNo; //月份
	@Excel(name="PUE值",orderNum="3")
	private BigDecimal pue; //PUE值
	@Excel(name="T值",orderNum="4")
	private BigDecimal t; //T值
	
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public BigDecimal getPue() {
		return pue;
	}
	public void setPue(BigDecimal pue) {
		this.pue = pue;
	}
	public BigDecimal getT() {
		return t;
	}
	public void setT(BigDecimal t) {
		this.t = t;
	}
	public int getMonthNo() {
		return monthNo;
	}
	public void setMonthNo(int monthNo) {
		this.monthNo = monthNo;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
}
