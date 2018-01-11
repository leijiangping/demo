package com.xunge.model.elecbill;

public class ElecBillResult {

	private String is_succ;
	private String collect_num;
	private String boe_num;
	private String ret_message;
	private String ret_code;

	public String getIs_succ() {
		return is_succ;
	}

	public void setIs_succ(String is_succ) {
		this.is_succ = is_succ;
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

	@Override
	public String toString() {
		String mesg="";
		mesg="Y".equals(is_succ)?"汇总单编号："+collect_num+",推送成功！":"推送失败："+ret_message;
		
		return mesg;
	}
	
}
