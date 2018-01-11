package com.xunge.model.selfrent.rebursepoint;

import java.io.Serializable;

import cn.afterturn.easypoi.excel.annotation.Excel;

import com.xunge.model.activity.Act;
import com.xunge.model.selfrent.billAccount.RentPaymentVO;
import com.xunge.model.system.region.SysRegionVO;

/**
 * 报账点VO
 * @author lpw
 * 2017年6月24日
 */
public class RentBillaccountVO implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5373325145307362933L;
	
	private String billAccountId;//报账点编码
	private String regId;//区域编码
	private String billAccountCode;//报账点代码	
	@Excel(name="报账点名称",orderNum="3",isImportField="true")
	private String billAccountName;//报账点名称
	private Integer billAccountState;//报账点状态	
	private String billAccountNote;//报账点备注
	@Excel(name="报账点审核状态",orderNum="5",isImportField="true")
	private Integer auditState;//报账点审核状态
	 //区域信息对象
    private SysRegionVO sysRegionVO;//区域信息对象
    private RentPaymentVO rentPaymentVO;
    private Act act;
	public String getBillAccountId() {
		return billAccountId;
	}
	public void setBillAccountId(String billAccountId) {
		this.billAccountId = billAccountId;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getBillAccountCode() {
		return billAccountCode;
	}
	public void setBillAccountCode(String billAccountCode) {
		this.billAccountCode = billAccountCode;
	}
	public String getBillAccountName() {
		return billAccountName;
	}
	public void setBillAccountName(String billAccountName) {
		this.billAccountName = billAccountName;
	}
	
	public Integer getBillAccountState() {
		return billAccountState;
	}
	public void setBillAccountState(Integer billAccountState) {
		this.billAccountState = billAccountState;
	}
	public String getBillAccountNote() {
		return billAccountNote;
	}
	public void setBillAccountNote(String billAccountNote) {
		this.billAccountNote = billAccountNote;
	}

	public SysRegionVO getSysRegionVO() {
		return sysRegionVO;
	}
	public void setSysRegionVO(SysRegionVO sysRegionVO) {
		this.sysRegionVO = sysRegionVO;
	}
	public RentPaymentVO getRentPaymentVO() {
		return rentPaymentVO;
	}
	public void setRentPaymentVO(RentPaymentVO rentPaymentVO) {
		this.rentPaymentVO = rentPaymentVO;
	}
	public Integer getAuditState() {
		return auditState;
	}
	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}
	public Act getAct() {
		return act;
	}
	public void setAct(Act act) {
		this.act = act;
	}
	
	/**
	 * 验证数据是不是符合规则
	 * @return
	 */
	public boolean verifyData(){
		
		return true;
	}
}
