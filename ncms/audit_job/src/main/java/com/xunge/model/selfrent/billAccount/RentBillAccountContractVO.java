package com.xunge.model.selfrent.billAccount;

import java.io.Serializable;
import java.util.List;

/**
 * 报账点 合同关系信息
 * @author ZMZ
 *
 */
public class RentBillAccountContractVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4270760459320929649L;
	//
    private String billaccountcontractId;
    //报账点编码
    private String billAccountId;
    //房租合同编码
    private String rentcontractId;
    //合同资源关系状态（0：正常   9：停用）
    private int relationState;
    //关系开始日期
    private String relation_startdate;
    //关系结束日期
    private String relation_enddate;
    //房租费缴费记录对象
    private List<RentPaymentVO> rentPaymentVO;
    
	public String getBillaccountcontractId() {
		return billaccountcontractId;
	}
	public void setBillaccountcontractId(String billaccountcontractId) {
		this.billaccountcontractId = billaccountcontractId;
	}
	public String getBillAccountId() {
		return billAccountId;
	}
	public void setBillAccountId(String billAccountId) {
		this.billAccountId = billAccountId;
	}
	public String getRentcontractId() {
		return rentcontractId;
	}
	public void setRentcontractId(String rentcontractId) {
		this.rentcontractId = rentcontractId;
	}
	public int getRelationState() {
		return relationState;
	}
	public void setRelationState(int relationState) {
		this.relationState = relationState;
	}
	public String getRelation_startdate() {
		return relation_startdate;
	}
	public void setRelation_startdate(String relation_startdate) {
		this.relation_startdate = relation_startdate;
	}
	public String getRelation_enddate() {
		return relation_enddate;
	}
	public void setRelation_enddate(String relation_enddate) {
		this.relation_enddate = relation_enddate;
	}
	public List<RentPaymentVO> getRentPaymentVO() {
		return rentPaymentVO;
	}
	public void setRentPaymentVO(List<RentPaymentVO> rentPaymentVO) {
		this.rentPaymentVO = rentPaymentVO;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
	
	
}