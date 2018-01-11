package com.xunge.model.selfrent.billAccount;

import java.io.Serializable;

import com.xunge.model.basedata.SysRegionVO;

/**
 * 报账点实体类
 * @author changwq
 *
 */
public class BillAccountVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6841052814597623435L;
	//报账点编码
	private String billAccountId;
	//区域编码
	private String regId;
	//报账点代码
	private String billAccountCode;
	//报账点名称
	private String billAccountName;
	//报账点状态
	private Integer billAccountState;
	//报账点备注
	private String billAccountNote;
	//区县对象
	private SysRegionVO sysRegionVO;
	
	//缴费状态字段
	private String isPay;
	
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
	public BillAccountVO() {
		super();
	}
	public SysRegionVO getSysRegionVO() {
		return sysRegionVO;
	}
	public void setSysRegionVO(SysRegionVO sysRegionVO) {
		this.sysRegionVO = sysRegionVO;
	}
	public BillAccountVO(String billAccountId, String regId,
			String billAccountCode, String billAccountName,
			Integer billAccountState, String billAccountNote,
			SysRegionVO sysRegionVO) {
		super();
		this.billAccountId = billAccountId;
		this.regId = regId;
		this.billAccountCode = billAccountCode;
		this.billAccountName = billAccountName;
		this.billAccountState = billAccountState;
		this.billAccountNote = billAccountNote;
		this.sysRegionVO = sysRegionVO;
	}
	public String getIsPay() {
		return isPay;
	}
	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}
}