package com.xunge.model.selfrent.billAccount;

import java.io.Serializable;
import java.util.List;

import com.xunge.model.selfrent.contract.DatContractVO;
import com.xunge.model.selfrent.contract.DatSupplierVO;
import com.xunge.model.selfrent.contract.RentContractVO;
/**
 * 报账点及其相关信息实体类
 * @author changwq
 *
 */
public class CoverAllVO implements Serializable  {
	
	private static final long serialVersionUID = -38999328501868198L;
	//报账点对象
	private BillAccountVO billAccountVO;
	//报账点缴费记录对象
	private RentPaymentVO rentPayMentVO;
	//资源点集合
	private List<DatBaseresourceVO> lstDatbase;
	//房租合同对象
	private RentContractVO rentContractVO;
	//主合同对象
	private DatContractVO datContractVO;
	//供应商对象
	private DatSupplierVO datSupplierVO;
	public BillAccountVO getBillAccountVO() {
		return billAccountVO;
	}
	public void setBillAccountVO(BillAccountVO billAccountVO) {
		this.billAccountVO = billAccountVO;
	}
	public RentPaymentVO getRentPayMentVO() {
		return rentPayMentVO;
	}
	public void setRentPayMentVO(RentPaymentVO rentPayMentVO) {
		this.rentPayMentVO = rentPayMentVO;
	}
	public List<DatBaseresourceVO> getLstDatbase() {
		return lstDatbase;
	}
	public void setLstDatbase(List<DatBaseresourceVO> lstDatbase) {
		this.lstDatbase = lstDatbase;
	}
	public RentContractVO getRentContractVO() {
		return rentContractVO;
	}
	public void setRentContractVO(RentContractVO rentContractVO) {
		this.rentContractVO = rentContractVO;
	}
	public DatContractVO getDatContractVO() {
		return datContractVO;
	}
	public void setDatContractVO(DatContractVO datContractVO) {
		this.datContractVO = datContractVO;
	}
	public DatSupplierVO getDatSupplierVO() {
		return datSupplierVO;
	}
	public void setDatSupplierVO(DatSupplierVO datSupplierVO) {
		this.datSupplierVO = datSupplierVO;
	}
	
}