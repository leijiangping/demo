package com.xunge.model.towerrent.checkmanage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.xunge.model.activity.Act;

import cn.afterturn.easypoi.excel.annotation.Excel;
/**
 * 
 * @author jcy
 * @date 2017年7月19日
 * @description 考核指标扣罚Model
 */
public class TwrPrvCheckIndexFineVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2343450720878740299L;
	private String twrProvincePunishId;        
	private String accountsummaryId;
	@Excel(name="所属地市",orderNum="1")
	private String regId;
	@Excel(name="扣罚时间",orderNum="2")
	private String punishYearMonth;    
	@Excel(name="扣罚指标",orderNum="3")
	private String punishName;   
	@Excel(name="扣罚原因",orderNum="4")
	private String punishCause;   
	@Excel(name="扣罚申请人",orderNum="5")
	private String punishPerson; 
	@Excel(name="扣罚金额",orderNum="6")
	private BigDecimal punishAmount;              
	private int state;
	private int auditState;
	@Excel(name="审核状态",orderNum="7")
	private String auditStateStr; 
	private String createUserId;                   
	private Date createTime;                     
	private String updateUserId;                   
	private Date updateTime;
	private String regName;
	private String prvId;
	private Act act;
	public Act getAct() {
		return act;
	}
	public void setAct(Act act) {
		this.act = act;
	}
	public String getTwrProvincePunishId() {
		return twrProvincePunishId;
	}
	public void setTwrProvincePunishId(String twrProvincePunishId) {
		this.twrProvincePunishId = twrProvincePunishId;
	}
	public String getAccountsummaryId() {
		return accountsummaryId;
	}
	public void setAccountsummaryId(String accountsummaryId) {
		this.accountsummaryId = accountsummaryId;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getPunishYearMonth() {
		return punishYearMonth;
	}
	public void setPunishYearMonth(String punishYearMonth) {
		this.punishYearMonth = punishYearMonth;
	}
	public String getPunishName() {
		return punishName;
	}
	public void setPunishName(String punishName) {
		this.punishName = punishName;
	}
	public String getPunishCause() {
		return punishCause;
	}
	public void setPunishCause(String punishCause) {
		this.punishCause = punishCause;
	}
	public String getPunishPerson() {
		return punishPerson;
	}
	public void setPunishPerson(String punishPerson) {
		this.punishPerson = punishPerson;
	}
	public BigDecimal getPunishAmount() {
		return punishAmount;
	}
	public void setPunishAmount(BigDecimal punishAmount) {
		this.punishAmount = punishAmount;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getAuditState() {
		return auditState;
	}
	public void setAuditState(int auditState) {
		this.auditState = auditState;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public String getPrvId() {
		return prvId;
	}
	public void setPrvId(String prvId) {
		this.prvId = prvId;
	}
	public String getAuditStateStr() {
		return auditStateStr;
	}
	public void setAuditStateStr(String auditStateStr) {
		this.auditStateStr = auditStateStr;
	}
}
