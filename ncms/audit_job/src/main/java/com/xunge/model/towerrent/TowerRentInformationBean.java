package com.xunge.model.towerrent;

import java.io.Serializable;

/**
 * 移动起租表bean
 * @author wangz
 * @date 2017-10-29 13:20:26
 *
 */
public class TowerRentInformationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2185674360098197657L;
	//移动起租表编码
	private String rentinformationId;
	
    //业务确认单号
    private String businessConfirmNumber;
	
    //铁塔公司站址编码
    private String towerStationCode;

	public String getRentinformationId() {
		return rentinformationId;
	}

	public void setRentinformationId(String rentinformationId) {
		this.rentinformationId = rentinformationId;
	}

	public String getBusinessConfirmNumber() {
		return businessConfirmNumber;
	}

	public void setBusinessConfirmNumber(String businessConfirmNumber) {
		this.businessConfirmNumber = businessConfirmNumber;
	}

	public String getTowerStationCode() {
		return towerStationCode;
	}

	public void setTowerStationCode(String towerStationCode) {
		this.towerStationCode = towerStationCode;
	}
}
