package com.xunge.model.selfrent.contract;

import java.io.Serializable;

public class DatSupplierVO implements Serializable  {
	
/**
	 * 
	 */
	private static final long serialVersionUID = -3840307816901932151L;
//	供应商编码
    private String supplierId;
//  供应商代码
    private String supplierCode;
//	供应商名称
    private String supplierName;
//	供应商地址
    private String supplierAddress;
//	供应商联系人
    private String supplierContact;
//	供应商联系电话
    private String supplierTelephone;
//	供应商类别
    private Integer supplierType;
//	供应商状态（0：正常   9：停用）
    private Integer supplierState;
//	供应商备注
    private String supplierNote;
//  省份 id   
    private String prvId;
    //区县编码
    private String regId;
    //区县名称
    private String regName;
    
    private String pregId;
    private String pregName;
    private String prvName;
//  账户类型     
	private Integer accountType;
//  银行账号
    private String bankUser;
//  开户银行 
    private String depositBank;
//  银行账户 
    private String bankAccount;
//	数据来源（0：系统录入  1:系统导入   2：接口采集）    
    private Integer dataFrom;
    //是否相信共享
    private Integer isDownShare;
    
    public Integer getIsDownShare() {
		return isDownShare;
	}

	public void setIsDownShare(Integer isDownShare) {
		this.isDownShare = isDownShare;
	}

	public String getPrvId() {
		return prvId;
	}

	public void setPrvId(String prvId) {
		this.prvId = prvId;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
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

    public Integer getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(Integer supplierType) {
        this.supplierType = supplierType;
    }

    public Integer getSupplierState() {
        return supplierState;
    }

    public void setSupplierState(Integer supplierState) {
        this.supplierState = supplierState;
    }

    public String getSupplierNote() {
        return supplierNote;
    }

    public void setSupplierNote(String supplierNote) {
        this.supplierNote = supplierNote == null ? null : supplierNote.trim();
    }

    public Integer getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(Integer dataFrom) {
        this.dataFrom = dataFrom;
    }

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getPregId() {
		return pregId;
	}

	public void setPregId(String pregId) {
		this.pregId = pregId;
	}

	public String getPregName() {
		return pregName;
	}

	public void setPregName(String pregName) {
		this.pregName = pregName;
	}

	public String getPrvName() {
		return prvName;
	}

	public void setPrvName(String prvName) {
		this.prvName = prvName;
	}
    
}