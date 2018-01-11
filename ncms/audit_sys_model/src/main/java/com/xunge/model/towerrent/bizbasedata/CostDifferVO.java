package com.xunge.model.towerrent.bizbasedata;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 费用差异范围
 */
public class CostDifferVO implements Serializable{
	/**
	 * 
	 */	
	 private static final long serialVersionUID = -1000065231554637L;
	 //费用差异编码 
	 private String chargediffId;
	 //省份编码
	 private String prvId;
	//省份名称
	 private String prvName;
	 //上限值
	 private BigDecimal chargediffUpnum;
	 //下限值
	 private BigDecimal chargediffDnnum;
	//费用差异备注
	 private String chargediffNote;
	 
	 public String getChargediffId() {
		return chargediffId;
	}
	public void setChargediffId(String chargediffId) {
		this.chargediffId = chargediffId;
	}
	public String getPrvId() {
		return prvId;
	}
	public void setPrvId(String prvId) {
		this.prvId = prvId;
	}
	public BigDecimal getChargediffUpnum() {
		return chargediffUpnum;
	}
	public void setChargediffUpnum(BigDecimal chargediffUpnum) {
		this.chargediffUpnum = chargediffUpnum;
	}
	public BigDecimal getChargediffDnnum() {
		return chargediffDnnum;
	}
	public void setChargediffDnnum(BigDecimal chargediffDnnum) {
		this.chargediffDnnum = chargediffDnnum;
	}
	public String getChargediffNote() {
		return chargediffNote;
	}
	public void setChargediffNote(String chargediffNote) {
		this.chargediffNote = chargediffNote;
	}
	public String getPrvName() {
		return prvName;
	}
	public void setPrvName(String prvName) {
		this.prvName = prvName;
	}
	 
}
