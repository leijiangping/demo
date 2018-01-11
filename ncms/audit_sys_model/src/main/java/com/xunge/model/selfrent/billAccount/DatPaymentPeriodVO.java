package com.xunge.model.selfrent.billAccount;

import java.io.Serializable;

/**
 * 缴费周期信息
 * @author ZMZ
 *
 */
public class DatPaymentPeriodVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 743357228583591887L;
	//缴费周期编码
    private String paymentperiodId;
    //缴费周期名称
    private String paymentperiodName;
    //缴费周期数值
    private int paymentperiodValue;
    //缴费周期单位  0：按天  1：按月 2：按季度 3：按年
    private Integer paymentperiodUnit;
    //缴费周期序号
    private Integer paymentperiodOrder;
    //缴费周期状态（0：正常 9：停用）
    private Integer paymentperiodState;
    
	public String getPaymentperiodId() {
		return paymentperiodId;
	}
	public void setPaymentperiodId(String paymentperiodId) {
		this.paymentperiodId = paymentperiodId;
	}
	public String getPaymentperiodName() {
		return paymentperiodName;
	}
	public void setPaymentperiodName(String paymentperiodName) {
		this.paymentperiodName = paymentperiodName;
	}
	
	public int getPaymentperiodValue() {
		return paymentperiodValue;
	}
	public void setPaymentperiodValue(int paymentperiodValue) {
		this.paymentperiodValue = paymentperiodValue;
	}
	public Integer getPaymentperiodUnit() {
		return paymentperiodUnit;
	}
	public void setPaymentperiodUnit(Integer paymentperiodUnit) {
		this.paymentperiodUnit = paymentperiodUnit;
	}
	public Integer getPaymentperiodOrder() {
		return paymentperiodOrder;
	}
	public void setPaymentperiodOrder(Integer paymentperiodOrder) {
		this.paymentperiodOrder = paymentperiodOrder;
	}
	public Integer getPaymentperiodState() {
		return paymentperiodState;
	}
	public void setPaymentperiodState(Integer paymentperiodState) {
		this.paymentperiodState = paymentperiodState;
	}
    
}