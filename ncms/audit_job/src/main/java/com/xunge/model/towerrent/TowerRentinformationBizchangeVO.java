package com.xunge.model.towerrent;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.afterturn.easypoi.excel.annotation.Excel;

import com.xunge.comm.StateComm;
import com.xunge.util.SysUUID;

/**
 * 业务变更单
 * @author wangz
 * @date 2017-09-20 11:18:46
 */
public class TowerRentinformationBizchangeVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1685418625359905933L;

	public TowerRentinformationBizchangeVO() {
		this.twrRentinformationBizchangeId = SysUUID.generator();
		this.auditState = StateComm.STATE__1;
	}

	//铁塔起租表业务变更表编码
    private String twrRentinformationBizchangeId;
   
    //区域编码
     @Excel(name="区县",isImportField="true")
    private String regId;
   
    //运营商
    @Excel(name="运营商")
    private String operator;
     
    //业务确认单号
     @Excel(name="业务确认单号",isImportField="true")
    private String businessConfirmNumber;
   
    //铁塔公司站址编码
     @Excel(name="铁塔公司站址编码",isImportField="true")
    private String towerStationCode;
    
    //变更项目
     @Excel(name="变更项目",isImportField="true")
    private String changeItem;
   
    //变更前内容
     @Excel(name="变更前内容",isImportField="true")
    private String changeBeforeContent;
    
    //变更后内容
     @Excel(name="变更后内容",isImportField="true")
    private String changeBehindContent;
   
   //是否追溯
     @Excel(name="是否追溯",isImportField="true",replace={"是_1","否_0"})
    private int ifAscend;
     
    //变更生效日期
     @Excel(name="变更生效日期",isImportField="true",format="yyyy-MM-dd")
    private String changeActiveDate;
  
    //追溯金额
     @Excel(name="追溯金额")
    private BigDecimal ascendFee;
   
    //变更原因
     @Excel(name="变更原因",isImportField="true")
    private String changeReason;
     
    //审核状态
    private Integer auditState;

	public String getTwrRentinformationBizchangeId() {
		return twrRentinformationBizchangeId;
	}

	public void setTwrRentinformationBizchangeId(
			String twrRentinformationBizchangeId) {
		this.twrRentinformationBizchangeId = twrRentinformationBizchangeId;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getBusinessConfirmNumber() {
		return businessConfirmNumber;
	}

	public void setBusinessConfirmNumber(String businessConfirmNumber) {
		this.businessConfirmNumber = businessConfirmNumber;
	}

	public String getTowerStationCode() {
		return towerStationCode;
	}

	public void setTowerStationCode(String towerStationCode) {
		this.towerStationCode = towerStationCode;
	}

	public String getChangeItem() {
		return changeItem;
	}

	public void setChangeItem(String changeItem) {
		this.changeItem = changeItem;
	}

	public String getChangeBeforeContent() {
		return changeBeforeContent;
	}

	public void setChangeBeforeContent(String changeBeforeContent) {
		this.changeBeforeContent = changeBeforeContent;
	}

	public String getChangeBehindContent() {
		return changeBehindContent;
	}

	public void setChangeBehindContent(String changeBehindContent) {
		this.changeBehindContent = changeBehindContent;
	}

	public String getChangeActiveDate() {
		return changeActiveDate;
	}

	public void setChangeActiveDate(String changeActiveDate) {
		this.changeActiveDate = changeActiveDate;
	}

	public int getIfAscend() {
		return ifAscend;
	}

	public void setIfAscend(int ifAscend) {
		this.ifAscend = ifAscend;
	}

	public BigDecimal getAscendFee() {
		return ascendFee;
	}

	public void setAscendFee(BigDecimal ascendFee) {
		this.ascendFee = ascendFee;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}