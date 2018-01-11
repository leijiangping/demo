package com.xunge.model.towerrent.settlement;

import java.io.Serializable;
/**
 * 
 * @author lpw
 * 接收账单确认页面参数VO
 */
public class BillConfirmVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7964448252207636931L;
	private String towerbillbalanceId;
	private String afterAdjustmentFee;
	private Integer confirmState;
	
	public Integer getConfirmState() {
		return confirmState;
	}
	public void setConfirmState(Integer confirmState) {
		this.confirmState = confirmState;
	}
	public String getTowerbillbalanceId() {
		return towerbillbalanceId;
	}
	public void setTowerbillbalanceId(String towerbillbalanceId) {
		this.towerbillbalanceId = towerbillbalanceId;
	}
	public String getAfterAdjustmentFee() {
		return afterAdjustmentFee;
	}
	public void setAfterAdjustmentFee(String afterAdjustmentFee) {
		this.afterAdjustmentFee = afterAdjustmentFee;
	}
	@Override
	public String toString() {
		return "BillConfirmVO [towerbillbalanceId=" + towerbillbalanceId
				+ ", afterAdjustmentFee=" + afterAdjustmentFee
				+ ", confirmState=" + confirmState + "]";
	}
	

}
