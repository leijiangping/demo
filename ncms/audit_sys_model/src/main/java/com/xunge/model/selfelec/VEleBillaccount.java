package com.xunge.model.selfelec;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import cn.afterturn.easypoi.excel.annotation.Excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xunge.core.util.SysUUID;
import com.xunge.core.util.excel.ExcelField;

public class VEleBillaccount implements Serializable {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VEleBillaccount() {
		this.billaccountId = SysUUID.generator();
	}

	private String billaccountId;

    private String prvId;
    
    private String prvSname;
    
    @Excel(name="所属地市",isImportField="true")
    private String pregId;
    
    private String pregName;

    @Excel(name="所属区县",isImportField="true")
    private String regId;
    
    private String regName;

    private String billaccountCode;
    
    @Excel(name="报账点名称",isImportField="true")
    private String billaccountName;

    @Excel(name="报账点状态",isImportField="true",replace={"正常_0","暂停_9","终止_10"})
    private Integer billaccountState;
    
    @Excel(name="报账点类型",isImportField="true",replace={"电费报账点_1"})
    private Integer billaccountType;

    private Integer auditingState;

    private String auditingUserId;

    private Date auditingDate;

    private Date planDate;

    private BigDecimal calcMulti;

    private Integer paymentState;
    
    @Excel(name="备注",isImportField="true")
    private String billaccountNote;

    private String userName;

    private String auditingStateStr;
    
    private String billaccountStateStr;
    
    public String getBillaccountId() {
        return billaccountId;
    }

    public void setBillaccountId(String billaccountId) {
        this.billaccountId = billaccountId == null ? null : billaccountId.trim();
    }

    public String getPrvId() {
        return prvId;
    }

    public void setPrvId(String prvId) {
        this.prvId = prvId == null ? null : prvId.trim();
    }

    public String getPrvSname() {
        return prvSname;
    }

    public void setPrvSname(String prvSname) {
        this.prvSname = prvSname == null ? null : prvSname.trim();
    }

    public String getPregId() {
        return pregId;
    }

    public void setPregId(String pregId) {
        this.pregId = pregId == null ? null : pregId.trim();
    }

    @ExcelField(title="所属地市", align=2, sort=4)
    public String getPregName() {
        return pregName;
    }

    public void setPregName(String pregName) {
        this.pregName = pregName == null ? null : pregName.trim();
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    @ExcelField(title="所属区县", align=2, sort=5)
    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName == null ? null : regName.trim();
    }

    @ExcelField(title="报账点编码", align=2, sort=2)
    public String getBillaccountCode() {
        return billaccountCode;
    }

    public void setBillaccountCode(String billaccountCode) {
        this.billaccountCode = billaccountCode == null ? null : billaccountCode.trim();
    }

    @ExcelField(title="报账点名称", align=2, sort=3)
    public String getBillaccountName() {
        return billaccountName;
    }

    public void setBillaccountName(String billaccountName) {
        this.billaccountName = billaccountName == null ? null : billaccountName.trim();
    }

    public Integer getBillaccountState() {
        return billaccountState;
    }

    public void setBillaccountState(Integer billaccountState) {
        this.billaccountState = billaccountState;
    }

    public Integer getBillaccountType() {
        return billaccountType;
    }

    public void setBillaccountType(Integer billaccountType) {
        this.billaccountType = billaccountType;
    }

    public Integer getAuditingState() {
        return auditingState;
    }

    public void setAuditingState(Integer auditingState) {
        this.auditingState = auditingState;
    }

    public String getAuditingUserId() {
        return auditingUserId;
    }

    public void setAuditingUserId(String auditingUserId) {
        this.auditingUserId = auditingUserId == null ? null : auditingUserId.trim();
    }

    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8") 
    public Date getAuditingDate() {
        return auditingDate;
    }

    public void setAuditingDate(Date auditingDate) {
        this.auditingDate = auditingDate;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8") 
    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public BigDecimal getCalcMulti() {
        return calcMulti;
    }

    public void setCalcMulti(BigDecimal calcMulti) {
        this.calcMulti = calcMulti;
    }

    public Integer getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(Integer paymentState) {
        this.paymentState = paymentState;
    }

    public String getBillaccountNote() {
        return billaccountNote;
    }

    public void setBillaccountNote(String billaccountNote) {
        this.billaccountNote = billaccountNote == null ? null : billaccountNote.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    @ExcelField(title="审核状态", align=2, sort=1)
	public String getAuditingStateStr() {
		return auditingStateStr;
	}

	public void setAuditingStateStr(String auditingStateStr) {
		this.auditingStateStr = auditingStateStr;
	}

	@ExcelField(title="报账点状态", align=2, sort=6)
	public String getBillaccountStateStr() {
		return billaccountStateStr;
	}

	public void setBillaccountStateStr(String billaccountStateStr) {
		this.billaccountStateStr = billaccountStateStr;
	}
}