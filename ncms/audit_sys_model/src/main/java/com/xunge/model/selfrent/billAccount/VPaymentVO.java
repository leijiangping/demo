package com.xunge.model.selfrent.billAccount;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import com.xunge.model.activity.Act;
import com.xunge.model.system.region.SysRegionVO;
@ExcelTarget("VPaymentVO")
/**
 * 缴费记录视图
 * @author xup
 *
 */
public class VPaymentVO implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8524902604457390340L;
	//报账点合同关系表id
	private String billaccountcontractId;
	//区域id
    private String regId;
	//报账点id
    private String billaccountId;
	//房租合同id
    private String rentcontractId;
	//报账点编码
    private String billaccountCode;
	//报账点名称
    private String billaccountName;
	//报账点状态
    private Integer billaccountState;
	//报账点审核状态
    private Integer auditState;
	//报账点缴费记录日期
    private Date paymentDate;
	//报账点缴费记录开始日期
    private Date paymentStartdate;
	//报账点缴费记录终止日期
    private Date paymentEnddate;
	//是否含税
    private Integer includeTax;
	//税金
    private Float billamountTaxratio;
    //报账点缴费记录id
    private String paymentId;
    //报账点缴费记录提交状态
    private Integer paymentState;
	//主合同id
    private String contractId;
	//主合同编码
    private String contractCode;
	//主合同名称
    private String contractName;
    //税金
    private Float billamountTaxamount;
    //报账类型
    private Integer billType;
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
    //税金承担方式
    private Integer taxMethod;
    //增值税抵扣
    private Integer taxDeduction;
    //合同开始日期
    private Date contractStartdate;
    //合同终止日期
    private Date contractEnddate;
    //合同年限
    private Float contractYearquantity;
    //不含税金额
    private Float dueamount;

    @Excel(name = "供应商编码" )
	private String supplierCode;
    @Excel(name = "供应商名称" )
    private String supplierName;
    // 供应商地址
	private String supplierAddress;	
	// 供应商联系人
	private String supplierContact;		
	// 供应商联系电话
	private String supplierTelephone;	
	// 银行账号
	private String bankUser;	
	// 开户银行
	private String depositBank;		
	// 银行账户
	private String bankAccount;		
	//年租金
    private Float yearAmount;
    //合同价款
    private Float totalAmount;
    //实际保障金额
    private Float payActamount;
    //计算报账金额
    private Float payCalcamount;
    //报账点缴费记录
    private String paymentNote;
    //区域视图
    private SysRegionVO sysRegionVO;
    //资源点名称
    private String baseresourceName;
    //资源点ID
    private String baseresourceId;
    //流程id
    private Integer paymentFlowstate;
    //资源点缴费记录视图
    private List<VBaseresourcePaymentdetailVO> baseresourcePaymentdetail;
    //房租缴费明细
    private List<RentPaymentdetailVO> rentPaymentdetails;
    private Act act;
    
    public String getSupplierName() {
    	return supplierName;
    }
    
    public void setSupplierName(String supplierName) {
    	this.supplierName = supplierName;
    }
    
    public String getSupplierCode() {
    	return supplierCode;
    }
    
    public void setSupplierCode(String supplierCode) {
    	this.supplierCode = supplierCode;
    }
    public String getBillaccountcontractId() {
        return billaccountcontractId;
    }

    public void setBillaccountcontractId(String billaccountcontractId) {
        this.billaccountcontractId = billaccountcontractId == null ? null : billaccountcontractId.trim();
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public String getBillaccountId() {
        return billaccountId;
    }

    public void setBillaccountId(String billaccountId) {
        this.billaccountId = billaccountId == null ? null : billaccountId.trim();
    }

    public String getRentcontractId() {
        return rentcontractId;
    }

    public void setRentcontractId(String rentcontractId) {
        this.rentcontractId = rentcontractId == null ? null : rentcontractId.trim();
    }

    public String getBillaccountCode() {
        return billaccountCode;
    }

    public void setBillaccountCode(String billaccountCode) {
        this.billaccountCode = billaccountCode == null ? null : billaccountCode.trim();
    }

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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getPaymentStartdate() {
        return paymentStartdate;
    }

    public void setPaymentStartdate(Date paymentStartdate) {
        this.paymentStartdate = paymentStartdate;
    }

    public Date getPaymentEnddate() {
        return paymentEnddate;
    }

    public void setPaymentEnddate(Date paymentEnddate) {
        this.paymentEnddate = paymentEnddate;
    }

    public Integer getIncludeTax() {
        return includeTax;
    }

    public void setIncludeTax(Integer includeTax) {
        this.includeTax = includeTax;
    }

    public Float getBillamountTaxratio() {
        return billamountTaxratio;
    }

    public void setBillamountTaxratio(Float billamountTaxratio) {
        this.billamountTaxratio = billamountTaxratio;
    }

    public Integer getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(Integer paymentState) {
        this.paymentState = paymentState;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

	public SysRegionVO getSysRegionVO() {
		return sysRegionVO;
	}

	public void setSysRegionVO(SysRegionVO sysRegionVO) {
		this.sysRegionVO = sysRegionVO;
	}



	public Float getDueamount() {
		return dueamount;
	}

	public void setDueamount(Float dueamount) {
		this.dueamount = dueamount;
	}

	public Float getPayActamount() {
		return payActamount;
	}

	public void setPayActamount(Float payActamount) {
		this.payActamount = payActamount;
	}

	public Date getContractStartdate() {
		return contractStartdate;
	}

	public void setContractStartdate(Date contractStartdate) {
		this.contractStartdate = contractStartdate;
	}

	public Date getContractEnddate() {
		return contractEnddate;
	}

	public void setContractEnddate(Date contractEnddate) {
		this.contractEnddate = contractEnddate;
	}

	public Float getContractYearquantity() {
		return contractYearquantity;
	}

	public void setContractYearquantity(Float contractYearquantity) {
		this.contractYearquantity = contractYearquantity;
	}

	public Float getBillamountTaxamount() {
		return billamountTaxamount;
	}

	public void setBillamountTaxamount(Float billamountTaxamount) {
		this.billamountTaxamount = billamountTaxamount;
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

	public Float getPayCalcamount() {
		return payCalcamount;
	}

	public void setPayCalcamount(Float payCalcamount) {
		this.payCalcamount = payCalcamount;
	}

	public String getPaymentNote() {
		return paymentNote;
	}

	public void setPaymentNote(String paymentNote) {
		this.paymentNote = paymentNote;
	}

	public Float getYearAmount() {
		return yearAmount;
	}

	public void setYearAmount(Float yearAmount) {
		this.yearAmount = yearAmount;
	}

	public Float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<VBaseresourcePaymentdetailVO> getBaseresourcePaymentdetail() {
		return baseresourcePaymentdetail;
	}

	public void setBaseresourcePaymentdetail(
			List<VBaseresourcePaymentdetailVO> baseresourcePaymentdetail) {
		this.baseresourcePaymentdetail = baseresourcePaymentdetail;
	}
	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getSupplierContact() {
		return supplierContact;
	}

	public void setSupplierContact(String supplierContact) {
		this.supplierContact = supplierContact;
	}

	public String getSupplierTelephone() {
		return supplierTelephone;
	}

	public void setSupplierTelephone(String supplierTelephone) {
		this.supplierTelephone = supplierTelephone;
	}

	public List<RentPaymentdetailVO> getRentPaymentdetails() {
		return rentPaymentdetails;
	}

	public void setRentPaymentdetails(List<RentPaymentdetailVO> rentPaymentdetails) {
		this.rentPaymentdetails = rentPaymentdetails;
	}

	public String getBankUser() {
		return bankUser;
	}

	public void setBankUser(String bankUser) {
		this.bankUser = bankUser;
	}

	public String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getBaseresourceName() {
		return baseresourceName;
	}

	public void setBaseresourceName(String baseresourceName) {
		this.baseresourceName = baseresourceName;
	}

	public String getBaseresourceId() {
		return baseresourceId;
	}

	public void setBaseresourceId(String baseresourceId) {
		this.baseresourceId = baseresourceId;
	}

	public Integer getPaymentFlowstate() {
		return paymentFlowstate;
	}

	public void setPaymentFlowstate(Integer paymentFlowstate) {
		this.paymentFlowstate = paymentFlowstate;
	}

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	
}