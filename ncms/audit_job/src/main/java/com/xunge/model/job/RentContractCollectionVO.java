package com.xunge.model.job;

import java.math.BigDecimal;

public class RentContractCollectionVO {
    private String rentcontractId;

    private String contractId;

    private String supplierId;

    private String paymentperiodId;

    private BigDecimal totalAmountnotax;

    private Integer includeTax;

    private BigDecimal billamountTaxratio;

    private BigDecimal taxAmount;

    private BigDecimal totalAmount;

    private BigDecimal yearAmount;

    private Integer addressType;

    private Integer houseType;

    private Integer chargeType;

    private Integer propertyType;

    private BigDecimal propertyArea;

    private String propertyAddress;

    private String propertyName;

    private String rentcontractNote;

    private String rentcontractState;

    private String supplierCode;

    private String supplierName;

    private String supplierSite;

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

    public BigDecimal getTotalAmountnotax() {
        return totalAmountnotax;
    }

    public void setTotalAmountnotax(BigDecimal totalAmountnotax) {
        this.totalAmountnotax = totalAmountnotax;
    }

    public Integer getIncludeTax() {
        return includeTax;
    }

    public void setIncludeTax(Integer includeTax) {
        this.includeTax = includeTax;
    }

    public BigDecimal getBillamountTaxratio() {
        return billamountTaxratio;
    }

    public void setBillamountTaxratio(BigDecimal billamountTaxratio) {
        this.billamountTaxratio = billamountTaxratio;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getYearAmount() {
        return yearAmount;
    }

    public void setYearAmount(BigDecimal yearAmount) {
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

    public BigDecimal getPropertyArea() {
        return propertyArea;
    }

    public void setPropertyArea(BigDecimal propertyArea) {
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

    public String getRentcontractState() {
        return rentcontractState;
    }

    public void setRentcontractState(String rentcontractState) {
        this.rentcontractState = rentcontractState == null ? null : rentcontractState.trim();
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