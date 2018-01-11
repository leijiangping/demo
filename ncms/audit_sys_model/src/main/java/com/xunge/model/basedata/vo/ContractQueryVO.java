package com.xunge.model.basedata.vo;

public class ContractQueryVO extends BaseDataVO {

	private String city;
	private String district;
	private String contractReg;
	private int contractType;
	private int contractStatus;
	private int verifyStatus;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getContractReg() {
		return contractReg;
	}

	public void setContractReg(String contractReg) {
		this.contractReg = contractReg;
	}

	public int getContractType() {
		return contractType;
	}

	public void setContractType(int contractType) {
		this.contractType = contractType;
	}

	public int getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(int contractStatus) {
		this.contractStatus = contractStatus;
	}

	public int getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
}
