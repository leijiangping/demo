package com.xunge.model.selfrent.rebursepoint;

import java.io.Serializable;
import java.util.Date;

/**
 * 房租合同报账点关系VO
 * @author lpw
 * 2017年6月24日
 */
public class RentBillAccountContractVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3973443933336246137L;
	private String billAccountContractId;//房租合同报账点关系ID
	private String billAccountId;//报账点编码
	private String rentContractId;//房租合同编码
	private Integer relationState;//合同资源关系状态（0：正常   9：停用）
	private Date relationStartdate;//关系开始日期
	private Date relationEnddate;//关系结束日期
	public String getBillAccountContractId() {
		return billAccountContractId;
	}
	public void setBillAccountContractId(String billAccountContractId) {
		this.billAccountContractId = billAccountContractId;
	}
	public String getBillAccountId() {
		return billAccountId;
	}
	public void setBillAccountId(String billAccountId) {
		this.billAccountId = billAccountId;
	}
	public String getRentContractId() {
		return rentContractId;
	}
	public void setRentContractId(String rentContractId) {
		this.rentContractId = rentContractId;
	}
	public Integer getRelationState() {
		return relationState;
	}
	public void setRelationState(Integer relationState) {
		this.relationState = relationState;
	}
	public Date getRelationStartdate() {
		return relationStartdate;
	}
	public void setRelationStartdate(Date relationStartdate) {
		this.relationStartdate = relationStartdate;
	}
	public Date getRelationEnddate() {
		return relationEnddate;
	}
	public void setRelationEnddate(Date relationEnddate) {
		this.relationEnddate = relationEnddate;
	}
	@Override
	public String toString() {
		return "RentBillAccountContractVO [billAccountContractId="
				+ billAccountContractId + ", billAccountId=" + billAccountId
				+ ", rentContractId=" + rentContractId + ", relationState="
				+ relationState + ", relationStartdate=" + relationStartdate
				+ ", relationEnddate=" + relationEnddate + "]";
	}
	
	
}
