package com.xunge.model.elecbill;

import java.io.Serializable;
import java.math.BigDecimal;

public class BillReportEntityVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8906782026030489202L;
	private BigDecimal price;//单价
	private BigDecimal count;//数量
	private BigDecimal line_amount;//报账金额
	private BigDecimal tax_rate;//税率
	private BigDecimal document_line_amount;//价款
	private BigDecimal tax_line_amount;//税额
	private String amount_date_begin;//报账日期始
	private String amount_date_end;//报账日期终
	private String check_result;//稽核结果
	private String belong_room;//明细所属机房/资源点/位置点信息
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getCount() {
		return count;
	}
	public void setCount(BigDecimal count) {
		this.count = count;
	}
	public BigDecimal getLine_amount() {
		return line_amount;
	}
	public void setLine_amount(BigDecimal line_amount) {
		this.line_amount = line_amount;
	}
	public BigDecimal getTax_rate() {
		return tax_rate;
	}
	public void setTax_rate(BigDecimal tax_rate) {
		this.tax_rate = tax_rate;
	}
	public BigDecimal getDocument_line_amount() {
		return document_line_amount;
	}
	public void setDocument_line_amount(BigDecimal document_line_amount) {
		this.document_line_amount = document_line_amount;
	}
	public BigDecimal getTax_line_amount() {
		return tax_line_amount;
	}
	public void setTax_line_amount(BigDecimal tax_line_amount) {
		this.tax_line_amount = tax_line_amount;
	}
	public String getAmount_date_begin() {
		return amount_date_begin;
	}
	public void setAmount_date_begin(String amount_date_begin) {
		this.amount_date_begin = amount_date_begin;
	}
	public String getAmount_date_end() {
		return amount_date_end;
	}
	public void setAmount_date_end(String amount_date_end) {
		this.amount_date_end = amount_date_end;
	}
	public String getCheck_result() {
		return check_result;
	}
	public void setCheck_result(String check_result) {
		this.check_result = check_result;
	}
	public String getBelong_room() {
		return belong_room;
	}
	public void setBelong_room(String belong_room) {
		this.belong_room = belong_room;
	}
}
