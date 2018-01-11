package com.xunge.model.elecbill;

import java.math.BigDecimal;

public class BillReportEntityVO {

	private BigDecimal price;
	private BigDecimal count;
	private BigDecimal line_amount;
	private BigDecimal tax_rate;
	private BigDecimal document_line_amount;
	private BigDecimal tax_line_amount;
	private String amount_date_begin;
	private String amount_date_end;
	private String check_result;
	private String belong_room;
	
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
