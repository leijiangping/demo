package com.xunge.model.contractsystem;

import java.math.BigDecimal;

public class ContractReportVO {

	private String elsys_contract_code; 
	private String contract_name;
	private String user_numer;
	private String user_name;
	private String contract_type;
	private String start_date;
	private String end_date;
	private BigDecimal contract_amount_total;
	private BigDecimal rent_area;
	private String related_room;
	
	public String getElsys_contract_code() {
		return elsys_contract_code;
	}
	public void setElsys_contract_code(String elsys_contract_code) {
		this.elsys_contract_code = elsys_contract_code;
	}
	public String getContract_name() {
		return contract_name;
	}
	public void setContract_name(String contract_name) {
		this.contract_name = contract_name;
	}
	public String getUser_numer() {
		return user_numer;
	}
	public void setUser_numer(String user_numer) {
		this.user_numer = user_numer;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getContract_type() {
		return contract_type;
	}
	public void setContract_type(String contract_type) {
		this.contract_type = contract_type;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public BigDecimal getContract_amount_total() {
		return contract_amount_total;
	}
	public void setContract_amount_total(BigDecimal contract_amount_total) {
		this.contract_amount_total = contract_amount_total;
	}
	public BigDecimal getRent_area() {
		return rent_area;
	}
	public void setRent_area(BigDecimal rent_area) {
		this.rent_area = rent_area;
	}
	public String getRelated_room() {
		return related_room;
	}
	public void setRelated_room(String related_room) {
		this.related_room = related_room;
	}
}