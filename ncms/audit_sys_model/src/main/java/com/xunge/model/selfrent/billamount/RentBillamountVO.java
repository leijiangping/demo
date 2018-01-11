package com.xunge.model.selfrent.billamount;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xunge.model.selfrent.contract.RentContractVO;
/**
 * @description 房租缴费汇总
 * @author zhujj
 *
 */
public class RentBillamountVO  implements Serializable{
	private static final long serialVersionUID = 3018196907027222533L;
	
	private String billamountId;		// 电费缴费汇总编码
	private String rentcontractId;		// 房租合同编码
	private String supplierCode;		// 供应商代码
	private String supplierName;		// 供应商名称
	private String supplierAddress;		// 供应商地址
	private String supplierContact;		// 供应商联系人
	private String supplierTelephone;		// 供应商联系电话
	private String bankUser;		// 银行账号
	private String depositBank;		// 开户银行
	private String bankAccount;		// 银行账户
	private String billamountCode;		// 汇总单号
	private Date billamountDate;		// 汇总日期
	private Float billamountWithtax;		// 汇总金额（含税）
	private Float billamountNotax;		// 汇总金额（不含税）
	private Float billamountTaxamount;		// 汇总税额
	private Integer billamountState;		// 报账状态            0：未报账            1：已报账
	private String billamountNote;		// 汇总摘要
	private String userNumber;		// 报账人工号
	private String userName;		// 报账人用户名
	private Date beginBillamountDate;		// 开始 汇总日期
	private Date endBillamountDate;		// 结束 汇总日期
	
	private RentContractVO rentContractVO;//房租合同
	
	//租费报账汇总明细列表
	private List<RentBillamountDetailVO> rentBillamountDetail=new ArrayList<RentBillamountDetailVO>();
	public String getBillamountId() {
		return billamountId;
	}

	public void setBillamountId(String billamountId) {
		this.billamountId = billamountId;
	}
	
	public String getRentcontractId() {
		return rentcontractId;
	}

	public void setRentcontractId(String rentcontractId) {
		this.rentcontractId = rentcontractId;
	}
	
	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
	
	public String getBillamountCode() {
		return billamountCode;
	}

	public void setBillamountCode(String billamountCode) {
		this.billamountCode = billamountCode;
	}
	
	public Date getBillamountDate() {
		return billamountDate;
	}

	public void setBillamountDate(Date billamountDate) {
		this.billamountDate = billamountDate;
	}
	
	public Float getBillamountWithtax() {
		return billamountWithtax;
	}

	public void setBillamountWithtax(Float billamountWithtax) {
		this.billamountWithtax = billamountWithtax;
	}
	
	public Float getBillamountNotax() {
		return billamountNotax;
	}

	public void setBillamountNotax(Float billamountNotax) {
		this.billamountNotax = billamountNotax;
	}
	
	public Float getBillamountTaxamount() {
		return billamountTaxamount;
	}

	public void setBillamountTaxamount(Float billamountTaxamount) {
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
		this.billamountNote = billamountNote;
	}
	
	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Date getBeginBillamountDate() {
		return beginBillamountDate;
	}

	public void setBeginBillamountDate(Date beginBillamountDate) {
		this.beginBillamountDate = beginBillamountDate;
	}
	
	public Date getEndBillamountDate() {
		return endBillamountDate;
	}

	public void setEndBillamountDate(Date endBillamountDate) {
		this.endBillamountDate = endBillamountDate;
	}

	public List<RentBillamountDetailVO> getRentBillamountDetail() {
		return rentBillamountDetail;
	}
	/**
	 * 增加对应的汇总缴费信息详细记录
	 * @param rentBillamountDetailVO
	 */
	public void addRentBillamountDetail(RentBillamountDetailVO rentBillamountDetailVO) {
		rentBillamountDetail.add(rentBillamountDetailVO);
	}
	public void setRentBillamountDetail(List<RentBillamountDetailVO> rentBillamountDetail) {
		this.rentBillamountDetail = rentBillamountDetail;
	}

	public RentContractVO getRentContractVO() {
		return rentContractVO;
	}

	public void setRentContractVO(RentContractVO rentContractVO) {
		this.rentContractVO = rentContractVO;
	}
	
	
}