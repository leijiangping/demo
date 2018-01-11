package com.xunge.model.selfrent.contract;

import java.io.Serializable;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;

import com.xunge.model.activity.Act;
import com.xunge.model.selfrent.billAccount.DatPaymentPeriodVO;
import com.xunge.model.selfrent.billAccount.RentBillAccountContractVO;

public class RentContractVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7545377303729654465L;
	//房租合同编码
    private String rentcontractId;
    //主合同编码
    private String contractId;
    //供应商编码
    private String supplierId;
    //缴费周期编码
    private String paymentperiodId;
    //合同价款（不含税）
    private Long totalAmountnotax;
    //是否含税是否含税 0：否  1：是
    private Integer includeTax;
    //税率=合同总金额*税率
    private double billamountTaxratio;
    //增值税金额
    private double taxAmount;
    //合同价款（不含税）+增值税金额=合同总金额（含税）
    @Excel(name="合同总金额",orderNum="20")
    private double totalAmount;
    //年租金（含税）
    private double yearAmount;
    //1.密集市区 2.一般市区 3.县城 4.乡镇 5.农村
    private Integer addressType;
    //房屋类型  板房  砖房 砖混
    private Integer houseType;
    //费用类别  租金
    private Integer chargeType;
    //物业分类
    private Integer propertyType;
    //面积
    private double propertyArea;
    //物业地址
    private String propertyAddress;
    //物业名称
    private String propertyName;
    //房租合同备注
    private String rentcontractNote;
    //流程状态
    //审核状态(状态：0-审核通过，8- 审核不通过 ，9-审核中，-1-未提交) 已经移动到主合同 暂时不用
    private Integer auditState;
    //主合同对象
    @ExcelEntity(id="datContract",name="主合同对象_datContract")
    private DatContractVO datContractVO;
    //供应商对象
    private DatSupplierVO datSupplierVO;
    //最后缴费日期
    @Excel(name="上次缴费期终",orderNum="6")
    private String paymentEnddate;
    //计划缴费日期
    @Excel(name="计划缴费日期",orderNum="6")
    private String playPaymentDate;
    
    //合同报账点关系对象
    private RentBillAccountContractVO rentBillAccountContractVO;
    //缴费周期对象
    private DatPaymentPeriodVO datPaymentPeriodVO;
    
    private Act act;

    public DatContractVO getDatContractVO() {
		return datContractVO;
	}

	public void setDatContractVO(DatContractVO datContractVO) {
		this.datContractVO = datContractVO;
	}

	public String getRentcontractId() {
        return rentcontractId;
    }

    public void setRentcontractId(String rentcontractId) {
        this.rentcontractId = rentcontractId == null ? null : rentcontractId.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getPaymentperiodId() {
        return paymentperiodId;
    }

    public void setPaymentperiodId(String paymentperiodId) {
        this.paymentperiodId = paymentperiodId == null ? null : paymentperiodId.trim();
    }

    public Long getTotalAmountnotax() {
        return totalAmountnotax;
    }

    public void setTotalAmountnotax(Long totalAmountnotax) {
        this.totalAmountnotax = totalAmountnotax;
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

    public void setBillamountTaxratio(double billamountTaxratio) {
        this.billamountTaxratio = billamountTaxratio;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getYearAmount() {
        return yearAmount;
    }

    public void setYearAmount(double yearAmount) {
        this.yearAmount = yearAmount;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    public Integer getHouseType() {
        return houseType;
    }

    public void setHouseType(Integer houseType) {
        this.houseType = houseType;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public Integer getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Integer propertyType) {
        this.propertyType = propertyType;
    }

    public double getPropertyArea() {
        return propertyArea;
    }

    public void setPropertyArea(double propertyArea) {
        this.propertyArea = propertyArea;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress == null ? null : propertyAddress.trim();
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName == null ? null : propertyName.trim();
    }

    public String getRentcontractNote() {
        return rentcontractNote;
    }

    public void setRentcontractNote(String rentcontractNote) {
        this.rentcontractNote = rentcontractNote == null ? null : rentcontractNote.trim();
    }

	public DatSupplierVO getDatSupplierVO() {
		return datSupplierVO;
	}

	public void setDatSupplierVO(DatSupplierVO datSupplierVO) {
		this.datSupplierVO = datSupplierVO;
	}

	

	public RentBillAccountContractVO getRentBillAccountContractVO() {
		return rentBillAccountContractVO;
	}

	public void setRentBillAccountContractVO(
			RentBillAccountContractVO rentBillAccountContractVO) {
		this.rentBillAccountContractVO = rentBillAccountContractVO;
	}

	public DatPaymentPeriodVO getDatPaymentPeriodVO() {
		return datPaymentPeriodVO;
	}

	public void setDatPaymentPeriodVO(DatPaymentPeriodVO datPaymentPeriodVO) {
		this.datPaymentPeriodVO = datPaymentPeriodVO;
	}

	public String getPaymentEnddate() {
		return paymentEnddate;
	}

	public void setPaymentEnddate(String paymentEnddate) {
		this.paymentEnddate = paymentEnddate;
	}

	public String getPlayPaymentDate() {
		return playPaymentDate;
	}

	public void setPlayPaymentDate(String playPaymentDate) {
		this.playPaymentDate = playPaymentDate;
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