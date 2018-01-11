package com.xunge.model.job;

import java.math.BigDecimal;

public class EleContractCollectionVO {
    private String elecontractId;

    private String contractId;

    private String supplierId;

    private String paymentperiodId;

    private Integer priceType;

    private String elecontractPrice;

    private Integer includePriceTax;

    private BigDecimal flatPrice;

    private BigDecimal peakPrice;

    private BigDecimal valleyPrice;

    private BigDecimal topPrice;

    private Integer supplyMethod;

    private Integer buyMethod;

    private Integer paymentMethod;

    private Integer includeTax;

    private BigDecimal taxRate;

    private Integer isIncludeAll;

    private Integer paySign;

    private BigDecimal paySignAccount;

    private BigDecimal contractMoney;

    private BigDecimal contractTax;

    private BigDecimal contractTotalAmount;

    private BigDecimal contractYearAmount;

    private Integer independentMeter;

    private BigDecimal cmccRatio;

    private BigDecimal unicomRatio;

    private BigDecimal telcomRatio;

    private Integer includeLoss;

    private Integer lossType;

    private String paymentUser;

    private String paymentTelphone;

    private String elecontractNote;

    private String supplierCode;

    private String supplierName;

    private String supplierSite;

    public String getElecontractId() {
        return elecontractId;
    }

    public void setElecontractId(String elecontractId) {
        this.elecontractId = elecontractId == null ? null : elecontractId.trim();
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

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public String getElecontractPrice() {
        return elecontractPrice;
    }

    public void setElecontractPrice(String elecontractPrice) {
        this.elecontractPrice = elecontractPrice == null ? null : elecontractPrice.trim();
    }

    public Integer getIncludePriceTax() {
        return includePriceTax;
    }

    public void setIncludePriceTax(Integer includePriceTax) {
        this.includePriceTax = includePriceTax;
    }

    public BigDecimal getFlatPrice() {
        return flatPrice;
    }

    public void setFlatPrice(BigDecimal flatPrice) {
        this.flatPrice = flatPrice;
    }

    public BigDecimal getPeakPrice() {
        return peakPrice;
    }

    public void setPeakPrice(BigDecimal peakPrice) {
        this.peakPrice = peakPrice;
    }

    public BigDecimal getValleyPrice() {
        return valleyPrice;
    }

    public void setValleyPrice(BigDecimal valleyPrice) {
        this.valleyPrice = valleyPrice;
    }

    public BigDecimal getTopPrice() {
        return topPrice;
    }

    public void setTopPrice(BigDecimal topPrice) {
        this.topPrice = topPrice;
    }

    public Integer getSupplyMethod() {
        return supplyMethod;
    }

    public void setSupplyMethod(Integer supplyMethod) {
        this.supplyMethod = supplyMethod;
    }

    public Integer getBuyMethod() {
        return buyMethod;
    }

    public void setBuyMethod(Integer buyMethod) {
        this.buyMethod = buyMethod;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getIncludeTax() {
        return includeTax;
    }

    public void setIncludeTax(Integer includeTax) {
        this.includeTax = includeTax;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getIsIncludeAll() {
        return isIncludeAll;
    }

    public void setIsIncludeAll(Integer isIncludeAll) {
        this.isIncludeAll = isIncludeAll;
    }

    public Integer getPaySign() {
        return paySign;
    }

    public void setPaySign(Integer paySign) {
        this.paySign = paySign;
    }

    public BigDecimal getPaySignAccount() {
        return paySignAccount;
    }

    public void setPaySignAccount(BigDecimal paySignAccount) {
        this.paySignAccount = paySignAccount;
    }

    public BigDecimal getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(BigDecimal contractMoney) {
        this.contractMoney = contractMoney;
    }

    public BigDecimal getContractTax() {
        return contractTax;
    }

    public void setContractTax(BigDecimal contractTax) {
        this.contractTax = contractTax;
    }

    public BigDecimal getContractTotalAmount() {
        return contractTotalAmount;
    }

    public void setContractTotalAmount(BigDecimal contractTotalAmount) {
        this.contractTotalAmount = contractTotalAmount;
    }

    public BigDecimal getContractYearAmount() {
        return contractYearAmount;
    }

    public void setContractYearAmount(BigDecimal contractYearAmount) {
        this.contractYearAmount = contractYearAmount;
    }

    public Integer getIndependentMeter() {
        return independentMeter;
    }

    public void setIndependentMeter(Integer independentMeter) {
        this.independentMeter = independentMeter;
    }

    public BigDecimal getCmccRatio() {
        return cmccRatio;
    }

    public void setCmccRatio(BigDecimal cmccRatio) {
        this.cmccRatio = cmccRatio;
    }

    public BigDecimal getUnicomRatio() {
        return unicomRatio;
    }

    public void setUnicomRatio(BigDecimal unicomRatio) {
        this.unicomRatio = unicomRatio;
    }

    public BigDecimal getTelcomRatio() {
        return telcomRatio;
    }

    public void setTelcomRatio(BigDecimal telcomRatio) {
        this.telcomRatio = telcomRatio;
    }

    public Integer getIncludeLoss() {
        return includeLoss;
    }

    public void setIncludeLoss(Integer includeLoss) {
        this.includeLoss = includeLoss;
    }

    public Integer getLossType() {
        return lossType;
    }

    public void setLossType(Integer lossType) {
        this.lossType = lossType;
    }

    public String getPaymentUser() {
        return paymentUser;
    }

    public void setPaymentUser(String paymentUser) {
        this.paymentUser = paymentUser == null ? null : paymentUser.trim();
    }

    public String getPaymentTelphone() {
        return paymentTelphone;
    }

    public void setPaymentTelphone(String paymentTelphone) {
        this.paymentTelphone = paymentTelphone == null ? null : paymentTelphone.trim();
    }

    public String getElecontractNote() {
        return elecontractNote;
    }

    public void setElecontractNote(String elecontractNote) {
        this.elecontractNote = elecontractNote == null ? null : elecontractNote.trim();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getSupplierSite() {
        return supplierSite;
    }

    public void setSupplierSite(String supplierSite) {
        this.supplierSite = supplierSite == null ? null : supplierSite.trim();
    }
}