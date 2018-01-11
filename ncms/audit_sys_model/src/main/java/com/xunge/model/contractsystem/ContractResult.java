package com.xunge.model.contractsystem;

public class ContractResult {

	private String is_succ;
	private String elsys_contract_code;
	private String consys_contract_code;
	private String ret_message;
	private String ret_code;

	public String getIs_succ() {
		return is_succ;
	}

	public void setIs_succ(String is_succ) {
		this.is_succ = is_succ;
	}
	
	public String getRet_message() {
		return ret_message;
	}

	public void setRet_message(String ret_message) {
		this.ret_message = ret_message;
	}
	
	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}
	
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
}
