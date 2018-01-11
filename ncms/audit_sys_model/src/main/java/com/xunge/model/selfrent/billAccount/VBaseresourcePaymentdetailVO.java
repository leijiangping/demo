package com.xunge.model.selfrent.billAccount;

import java.io.Serializable;
/**
 * 资源点缴费记录视图
 * @author xup
 *
 */
public class VBaseresourcePaymentdetailVO implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7213707897879479957L;
    //不含税金额
	private Long dueamount;
	//税金
    private Long billamountTaxamount;
	//总金额
    private Long payamount;
	//资源点编码
    private String baseresourceCode;
	//资源点名称
    private String baseresourceName;
	//缴费记录明细id
    private String paymentdetailId;

    public Long getDueamount() {
        return dueamount;
    }

    public void setDueamount(Long dueamount) {
        this.dueamount = dueamount;
    }

    public Long getBillamountTaxamount() {
        return billamountTaxamount;
    }

    public void setBillamountTaxamount(Long billamountTaxamount) {
        this.billamountTaxamount = billamountTaxamount;
    }

    public Long getPayamount() {
        return payamount;
    }

    public void setPayamount(Long payamount) {
        this.payamount = payamount;
    }

    public String getBaseresourceCode() {
        return baseresourceCode;
    }

    public void setBaseresourceCode(String baseresourceCode) {
        this.baseresourceCode = baseresourceCode == null ? null : baseresourceCode.trim();
    }

    public String getBaseresourceName() {
        return baseresourceName;
    }

    public void setBaseresourceName(String baseresourceName) {
        this.baseresourceName = baseresourceName == null ? null : baseresourceName.trim();
    }

    public String getPaymentdetailId() {
        return paymentdetailId;
    }

    public void setPaymentdetailId(String paymentdetailId) {
        this.paymentdetailId = paymentdetailId == null ? null : paymentdetailId.trim();
    }
}