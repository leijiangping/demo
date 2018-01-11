package com.xunge.model.selfrent.billamount;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * @description 租费报账汇总明细Entity
 * @author zhujj
 * @version 2017-06-26
 */
public class RentBillamountDetailVO implements Serializable{
	
	private static final long serialVersionUID = 3742397392554385905L;
	
	private String billamountdetailId;		// 电费缴费明细编码
	private String billamountId;		// 电费缴费汇总编码
	private Float billamountPrice;		// 电费则为电费单价（不含税），房费则为月租金（不含税）；
	private Double billamountNumber;		// 电费则为用电度数，房费则为月数
	private Float billamountNotax;		// 不含税金额
	private Float billamountTaxratio;		// 如无税率，则传0；            百分比转换成小数，如17%则传0.17
	private Float billamountTaxamount;		// 税额
	private Float billamountWithtax;		// 含税金额
	private Date billamountStartdate;		// 缴费期始
	private Date billamountEnddate;		// 缴费期终
	
	public RentBillamountDetailVO() {
		super();
	}

	
	public String getBillamountdetailId() {
		return billamountdetailId;
	}

	public void setBillamountdetailId(String billamountdetailId) {
		this.billamountdetailId = billamountdetailId;
	}
	
	public String getBillamountId() {
		return billamountId;
	}

	public void setBillamountId(String billamountId) {
		this.billamountId = billamountId;
	}
	
	public Float getBillamountPrice() {
		return billamountPrice;
	}

	public void setBillamountPrice(Float billamountPrice) {
		this.billamountPrice = billamountPrice;
	}
	
	public double getBillamountNumber() {
		return billamountNumber;
	}

	public void setBillamountNumber(double billamountNumber) {
		this.billamountNumber = billamountNumber;
	}
	
	public Float getBillamountNotax() {
		return billamountNotax;
	}

	public void setBillamountNotax(Float billamountNotax) {
		this.billamountNotax = billamountNotax;
	}
	
	public Float getBillamountTaxratio() {
		return billamountTaxratio;
	}

	public void setBillamountTaxratio(Float billamountTaxratio) {
		this.billamountTaxratio = billamountTaxratio;
	}
	
	public Float getBillamountTaxamount() {
		return billamountTaxamount;
	}

	public void setBillamountTaxamount(Float billamountTaxamount) {
		this.billamountTaxamount = billamountTaxamount;
	}
	
	public Float getBillamountWithtax() {
		return billamountWithtax;
	}

	public void setBillamountWithtax(Float billamountWithtax) {
		this.billamountWithtax = billamountWithtax;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBillamountStartdate() {
		return billamountStartdate;
	}

	public void setBillamountStartdate(Date billamountStartdate) {
		this.billamountStartdate = billamountStartdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBillamountEnddate() {
		return billamountEnddate;
	}

	public void setBillamountEnddate(Date billamountEnddate) {
		this.billamountEnddate = billamountEnddate;
	}
	
}