package com.xunge.model.elecbill;

import java.math.BigDecimal;
import java.util.Date;

public class EleBillamountVO {
    private String billamountId;

    private String elecontractId;

    private String supplierId;

    private String supplierCode;

    private String supplierName;

    private String supplierAddress;

    private String supplierContact;

    private String supplierTelephone;

    private String bankUser;

    private String depositBank;

    private String bankAccount;

    private String billamountCode;

    private Date billamountDate;

    private BigDecimal billamountWithtax;

    private BigDecimal billamountNotax;

    private BigDecimal billamountTaxamount;

    private Integer billamountState;

    private String billamountNote;

    public String getBillamountId() {
        return billamountId;
    }

    public void setBillamountId(String billamountId) {
        this.billamountId = billamountId == null ? null : billamountId.trim();
    }

    public String getElecontractId() {
        return elecontractId;
    }

    public void setElecontractId(String elecontractId) {
        this.elecontractId = elecontractId == null ? null : elecontractId.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
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

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress == null ? null : supplierAddress.trim();
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact == null ? null : supplierContact.trim();
    }

    public String getSupplierTelephone() {
        return supplierTelephone;
    }

    public void setSupplierTelephone(String supplierTelephone) {
        this.supplierTelephone = supplierTelephone == null ? null : supplierTelephone.trim();
    }

    public String getBankUser() {
        return bankUser;
    }

    public void setBankUser(String bankUser) {
        this.bankUser = bankUser == null ? null : bankUser.trim();
    }

    public String getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank == null ? null : depositBank.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getBillamountCode() {
        return billamountCode;
    }

    public void setBillamountCode(String billamountCode) {
        this.billamountCode = billamountCode == null ? null : billamountCode.trim();
    }

    public Date getBillamountDate() {
        return billamountDate;
    }

    public void setBillamountDate(Date billamountDate) {
        this.billamountDate = billamountDate;
    }

    public BigDecimal getBillamountWithtax() {
        return billamountWithtax;
    }

    public void setBillamountWithtax(BigDecimal billamountWithtax) {
        this.billamountWithtax = billamountWithtax;
    }

    public BigDecimal getBillamountNotax() {
        return billamountNotax;
    }

    public void setBillamountNotax(BigDecimal billamountNotax) {
        this.billamountNotax = billamountNotax;
    }

    public BigDecimal getBillamountTaxamount() {
        return billamountTaxamount;
    }

    public void setBillamountTaxamount(BigDecimal billamountTaxamount) {
        this.billamountTaxamount = billamountTaxamount;
    }

    public Integer getBillamountState() {
        return billamountState;
    }

    public void setBillamountState(Integer billamountState) {
        this.billamountState = billamountState;
    }

    public String getBillamountNote() {
        return billamountNote;
    }

    public void setBillamountNote(String billamountNote) {
        this.billamountNote = billamountNote == null ? null : billamountNote.trim();
    }
}