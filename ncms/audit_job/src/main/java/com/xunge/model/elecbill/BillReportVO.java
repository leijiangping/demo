package com.xunge.model.elecbill;

import java.math.BigDecimal;
import java.util.List;

public class BillReportVO {

	private String system_id;
	private String contract_code;
	private int payment_type;
	private String collect_num;
	private String vendor_code;
	private String vendor_name;
	private String vendor_site;
	private BigDecimal apply_amount;
	private BigDecimal document_amount;
	private BigDecimal tax_amount;
	private String user_number;
	private String user_name;
	private String abstract_content;
	private String remark;
	private List<BillReportEntityVO> entity_expend;
	
	
	public String getSystem_id() {
		return system_id;
	}
	public void setSystem_id(String system_id) {
		this.system_id = system_id;
	}
	
	public int getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(int payment_type) {
		this.payment_type = payment_type;
	}

	public String getCollect_num() {
		return collect_num;
	}
	public void setCollect_num(String collect_num) {
		this.collect_num = collect_num;
	}

	public String getContract_code() {
		return contract_code;
	}
	public void setContract_code(String contract_code) {
		this.contract_code = contract_code;
	}
	public String getVendor_code() {
		return vendor_code;
	}
	public void setVendor_code(String vendor_code) {
		this.vendor_code = vendor_code;
	}
	public String getVendor_name() {
		return vendor_name;
	}
	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}
	public String getVendor_site() {
		return vendor_site;
	}
	public void setVendor_site(String vendor_site) {
		this.vendor_site = vendor_site;
	}
	public BigDecimal getApply_amount() {
		return apply_amount;
	}
	public void setApply_amount(BigDecimal apply_amount) {
		this.apply_amount = apply_amount;
	}
	public BigDecimal getDocument_amount() {
		return document_amount;
	}
	public void setDocument_amount(BigDecimal document_amount) {
		this.document_amount = document_amount;
	}
	public BigDecimal getTax_amount() {
		return tax_amount;
	}
	public void setTax_amount(BigDecimal tax_amount) {
		this.tax_amount = tax_amount;
	}
	public String getUser_number() {
		return user_number;
	}
	public void setUser_number(String user_number) {
		this.user_number = user_number;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getAbstract_content() {
		return abstract_content;
	}
	public void setAbstract_content(String abstract_content) {
		this.abstract_content = abstract_content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<BillReportEntityVO> getEntity_expend() {
		return entity_expend;
	}
	public void setEntity_expend(List<BillReportEntityVO> entity_expend) {
		this.entity_expend = entity_expend;
	}
}
