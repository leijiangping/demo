package com.xunge.model.selfrent.billAccount;

import java.io.Serializable;
import java.math.BigDecimal;

import com.xunge.model.selfrent.billamount.RentBillamountVO;

/**
 * 报账点缴费记录实体类
 * @author changwq
 *
 */
public class RentPaymentVO implements Serializable  {
	
	private static final long serialVersionUID = -2656853132953657723L;
	private String paymentId;
    //报账点编码
    private String billaccountId;
    //电费缴费汇总编码
    private String billamountId;
    //报账类型
    private Integer billType;
    //缴费申请日期
    private String paymentDate;
    //业务类型
    private Integer businessType;
    //费用类型
    private Integer amountType;
    //费用类别
    private Integer chargeType;
    //支付方式
    private Integer paymentMethod;
    //票据类型
    private Integer invoiceType;
    //缴费期始
    private String paymentStartdate;
    //缴费期终
    private String paymentEnddate;
    //是否含税
    private Integer includeTax;
    //如无税率，则传0；
    //百分比转换成小数，如17%则传0.17
    private double billamountTaxratio;
    //税金
    private BigDecimal billamountTaxamount;
    //税金承担方式
    private Integer taxMethod;
    //增值税抵扣
    private Integer taxDeduction;
    //不含税金额
    private BigDecimal dueamount;
    //计算报账金额
    private BigDecimal payCalcamount;
    //实际报账金额
    private BigDecimal payActamount;
    //房租缴费备注
    private String paymentNote;
    //0：未提交
    //1：已提交
    private Integer paymentState;
    //未审批
    //审批中
    //已审批
    //跟流程状态挂钩
    private Integer paymentFlowstate;
    
    private BillAccountVO billAccount;//报账点

    private RentBillamountVO rentBillamount;//租费汇总
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public String getBillaccountId() {
        return billaccountId;
    }

    public void setBillaccountId(String billaccountId) {
        this.billaccountId = billaccountId == null ? null : billaccountId.trim();
    }

    public String getBillamountId() {
        return billamountId;
    }

    public void setBillamountId(String billamountId) {
        this.billamountId = billamountId == null ? null : billamountId.trim();
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

  
    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getAmountType() {
        return amountType;
    }

    public void setAmountType(Integer amountType) {
        this.amountType = amountType;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentStartdate() {
		return paymentStartdate;
	}

	public void setPaymentStartdate(String paymentStartdate) {
		this.paymentStartdate = paymentStartdate;
	}

	public String getPaymentEnddate() {
		return paymentEnddate;
	}

	public void setPaymentEnddate(String paymentEnddate) {
		this.paymentEnddate = paymentEnddate;
	}

	public Integer getIncludeTax() {
        return includeTax;
    }

    public void setIncludeTax(Integer includeTax) {
        this.includeTax = includeTax;
    }

    public double getBillamountTaxratio() {
        return billamountTaxratio;
    }

    public void setBillamountTaxratio(double d) {
        this.billamountTaxratio = d;
    }

    public BigDecimal getBillamountTaxamount() {
        return billamountTaxamount;
    }

    public void setBillamountTaxamount(BigDecimal billamountTaxamount) {
        this.billamountTaxamount = billamountTaxamount;
    }

    public Integer getTaxMethod() {
        return taxMethod;
    }

    public void setTaxMethod(Integer taxMethod) {
        this.taxMethod = taxMethod;
    }

    public Integer getTaxDeduction() {
        return taxDeduction;
    }

    public void setTaxDeduction(Integer taxDeduction) {
        this.taxDeduction = taxDeduction;
    }

    public BigDecimal getDueamount() {
        return dueamount;
    }

    public void setDueamount(BigDecimal dueamount) {
        this.dueamount = dueamount;
    }


    public String getPaymentNote() {
        return paymentNote;
    }

    public void setPaymentNote(String paymentNote) {
        this.paymentNote = paymentNote == null ? null : paymentNote.trim();
    }

    public Integer getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(Integer paymentState) {
        this.paymentState = paymentState;
    }

    public Integer getPaymentFlowstate() {
        return paymentFlowstate;
    }

    public void setPaymentFlowstate(Integer paymentFlowstate) {
        this.paymentFlowstate = paymentFlowstate;
    }

	public BigDecimal getPayCalcamount() {
		return payCalcamount;
	}

	public void setPayCalcamount(BigDecimal payCalcamount) {
		this.payCalcamount = payCalcamount;
	}

	public BigDecimal getPayActamount() {
		return payActamount;
	}

	public void setPayActamount(BigDecimal payActamount) {
		this.payActamount = payActamount;
	}

	public BillAccountVO getBillAccount() {
		return billAccount;
	}
 
	public void setBillAccount(BillAccountVO billAccount) {
		this.billAccount = billAccount;
	}

	public RentBillamountVO getRentBillamount() {
		return rentBillamount;
	}

	public void setRentBillamount(RentBillamountVO rentBillamount) {
		this.rentBillamount = rentBillamount;
	}

	
}