package com.xunge.model.elecbill;

public class ElecBillParm {

	private String collect_num;
	private String boe_num;
	private String payment_date;
	private String check_date;
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCollect_num() {
		return collect_num;
	}

	public void setCollect_num(String collect_num) {
		this.collect_num = collect_num;
	}
	
	public String getBoe_num() {
		return boe_num;
	}

	public void setBoe_num(String boe_num) {
		this.boe_num = boe_num;
	}
	
	public String getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}
	
	public String getCheck_date() {
		return check_date;
	}

	public void setCheck_date(String check_date) {
		this.check_date = check_date;
	}
}
