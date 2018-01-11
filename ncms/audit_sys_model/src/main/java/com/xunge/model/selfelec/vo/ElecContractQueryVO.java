package com.xunge.model.selfelec.vo;

import java.util.List;

import com.xunge.model.basedata.vo.BaseDataVO;

public class ElecContractQueryVO extends BaseDataVO {

	private String elecontractId;
	private String prvId;
	private String city;
	private String region;
	private String reg;
	private Integer status;
	private Integer auditStatus;
	private Integer opType;
	//权限内 区县id集合
	private List<String> regIds;
	//权限内省份id集合
	private List<String> pregIds;
	//是否向下共享
	private Integer isDownShare;
	private Integer dataFrom;
	//主合同id
	private String contractId;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public Integer getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(Integer dataFrom) {
		this.dataFrom = dataFrom;
	}

	public List<String> getRegIds() {
		return regIds;
	}

	public void setRegIds(List<String> regIds) {
		this.regIds = regIds;
	}

	public List<String> getPregIds() {
		return pregIds;
	}

	public void setPregIds(List<String> pregIds) {
		this.pregIds = pregIds;
	}
	
	public Integer getIsDownShare() {
		return isDownShare;
	}

	public void setIsDownShare(Integer isDownShare) {
		this.isDownShare = isDownShare;
	}

	public String getElecontractId() {
		return elecontractId;
	}

	public void setElecontractId(String elecontractId) {
		this.elecontractId = elecontractId;
	}

	public String getPrvId() {
		return prvId;
	}

	public void setPrvId(String prvId) {
		this.prvId = prvId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getOpType() {
		return opType;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}
}
