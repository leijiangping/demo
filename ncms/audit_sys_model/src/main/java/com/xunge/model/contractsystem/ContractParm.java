package com.xunge.model.contractsystem;

public class ContractParm {

	private String elsys_contract_code;
	private String consys_contract_code;
	private String change_date;
	private int status;
	
	public String getElsys_contract_code() {
		return elsys_contract_code;
	}

	public void setElsys_contract_code(String elsys_contract_code) {
		this.elsys_contract_code = elsys_contract_code;
	}

	public String getConsys_contract_code() {
		return consys_contract_code;
	}

	public void setConsys_contract_code(String consys_contract_code) {
		this.consys_contract_code = consys_contract_code;
	}

	public String getChange_date() {
		return change_date;
	}

	public void setChange_date(String change_date) {
		this.change_date = change_date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
