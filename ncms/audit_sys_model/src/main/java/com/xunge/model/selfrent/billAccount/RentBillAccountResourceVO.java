package com.xunge.model.selfrent.billAccount;

import java.io.Serializable;
import java.util.Date;

/**
 * 房租报账点资源关系VO
 * @author lpw
 * 2017年6月24日
 */
public class RentBillAccountResourceVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6688765592956301741L;
	private String billAccountResourceId;//房租报账点资源关系ID
	private String baseResourceId;//资源编码
	private String billAccountId;//报账点编码
	private Integer relationState;//合同资源关系状态（0：正常   9：停用）
	private Date relationStartdate;//关系开始日期
	private Date relationEnddate;//关系结束日期
	
	
	public String getBillAccountResourceId() {
		return billAccountResourceId;
	}
	public void setBillAccountResourceId(String billAccountResourceId) {
		this.billAccountResourceId = billAccountResourceId;
	}
	public String getBaseResourceId() {
		return baseResourceId;
	}
	public void setBaseResourceId(String baseResourceId) {
		this.baseResourceId = baseResourceId;
	}
	public String getBillAccountId() {
		return billAccountId;
	}
	public void setBillAccountId(String billAccountId) {
		this.billAccountId = billAccountId;
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
		return "RentBillAccountResourceVO [billAccountResourceId="
				+ billAccountResourceId + ", baseResourceId=" + baseResourceId
				+ ", billAccountId=" + billAccountId + ", relationState="
				+ relationState + ", relationStartdate=" + relationStartdate
				+ ", relationEnddate=" + relationEnddate + "]";
	}
	
}
