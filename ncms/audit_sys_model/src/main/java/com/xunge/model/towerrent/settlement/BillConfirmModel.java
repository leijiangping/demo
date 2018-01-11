package com.xunge.model.towerrent.settlement;

import java.io.Serializable;
import java.util.List;

public class BillConfirmModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<TowerBillbalanceVO> billConfirmList;

	public List<TowerBillbalanceVO> getBillConfirmList() {
		return billConfirmList;
	}

	public void setBillConfirmList(List<TowerBillbalanceVO> billConfirmList) {
		this.billConfirmList = billConfirmList;
	}

	

	

}
