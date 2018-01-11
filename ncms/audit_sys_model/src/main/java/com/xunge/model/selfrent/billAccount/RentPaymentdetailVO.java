package com.xunge.model.selfrent.billAccount;

import java.io.Serializable;
import java.math.BigDecimal;

import com.xunge.model.selfrent.contract.RentContractVO;
/**
 * 报账点缴费信息明细实体类
 * @author changwq
 *
 */
public class RentPaymentdetailVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5295572280055098061L;
	//房租缴费明细编码
    private String paymentdetailId;
    //报账点资源点中间表id
    private String billaccountresourceId;
    //报账单缴费记录id
    private String paymentId;
    //不含税金额
    private BigDecimal dueamount;
    //税金
    private BigDecimal billamountTaxamount;
    //报账金额
    private BigDecimal payamount;
    //合同信息对象
    private RentContractVO rentContractVO;
    //房租费缴费记录对象
    private RentPaymentVO rentPaymentVO;
    
	public String getPaymentdetailId() {
		return paymentdetailId;
	}
	public void setPaymentdetailId(String paymentdetailId) {
		this.paymentdetailId = paymentdetailId;
	}
	public String getBillaccountresourceId() {
		return billaccountresourceId;
	}
	public void setBillaccountresourceId(String billaccountresourceId) {
		this.billaccountresourceId = billaccountresourceId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public BigDecimal getDueamount() {
		return dueamount;
	}
	public void setDueamount(BigDecimal dueamount) {
		this.dueamount = dueamount;
	}
	public BigDecimal getBillamountTaxamount() {
		return billamountTaxamount;
	}
	public void setBillamountTaxamount(BigDecimal billamountTaxamount) {
		this.billamountTaxamount = billamountTaxamount;
	}
	public BigDecimal getPayamount() {
		return payamount;
	}
	public void setPayamount(BigDecimal payamount) {
		this.payamount = payamount;
	}
	public RentContractVO getRentContractVO() {
		return rentContractVO;
	}
	public void setRentContractVO(RentContractVO rentContractVO) {
		this.rentContractVO = rentContractVO;
	}
	public RentPaymentVO getRentPaymentVO() {
		return rentPaymentVO;
	}
	public void setRentPaymentVO(RentPaymentVO rentPaymentVO) {
		this.rentPaymentVO = rentPaymentVO;
	}
}