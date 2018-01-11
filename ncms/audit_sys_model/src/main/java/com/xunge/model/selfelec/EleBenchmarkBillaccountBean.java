package com.xunge.model.selfelec;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @descript 报账标杆封装bean
 * @author wagnz
 * @date 2017-08-20 11:30:27
 */
public class EleBenchmarkBillaccountBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String billaccountId;   // 报账点编码
	@Excel(name="报账点编码")
	private String billaccountCode; // 报账点代码 
	@Excel(name="报账点名称")
	private String billaccountName; // 报账点名称 
	@Excel(name="报账点状态",replace={"正常_0","暂停_9","终止_10"})
	private String billaccountState;// 报账点状态
	@Excel(name="地市")
	private String pregName; // 地市
	@Excel(name="区县")
	private String regName;  // 区县
	
	private Date colDate; // 采集日期
	private BigDecimal electricmeterDegree; // 智能电表标杆
	private BigDecimal powerloadDegree;		// 动环负载标杆
	private BigDecimal powerratingDegree;	// 额定功率标杆
	// 页面展示数据所需属性值
	@Excel(name="年份")
	private String year;			// 年份
	@Excel(name="月份")
	private String month;			// 月份
	@Excel(name="月总标杆")
	private BigDecimal monthTotal;  // 月总标杆
	@Excel(name="1")
	private BigDecimal day1;
	@Excel(name="2")
	private BigDecimal day2;
	@Excel(name="3")
	private BigDecimal day3;
	@Excel(name="4")
	private BigDecimal day4;
	@Excel(name="5")
	private BigDecimal day5;
	@Excel(name="6")
	private BigDecimal day6;
	@Excel(name="7")
	private BigDecimal day7;
	@Excel(name="8")
	private BigDecimal day8;
	@Excel(name="9")
	private BigDecimal day9;
	@Excel(name="10")
	private BigDecimal day10;
	@Excel(name="11")
	private BigDecimal day11;
	@Excel(name="12")
	private BigDecimal day12;
	@Excel(name="13")
	private BigDecimal day13;
	@Excel(name="14")
	private BigDecimal day14;
	@Excel(name="15")
	private BigDecimal day15;
	@Excel(name="16")
	private BigDecimal day16;
	@Excel(name="17")
	private BigDecimal day17;
	@Excel(name="18")
	private BigDecimal day18;
	@Excel(name="19")
	private BigDecimal day19;
	@Excel(name="20")
	private BigDecimal day20;
	@Excel(name="21")
	private BigDecimal day21;
	@Excel(name="22")
	private BigDecimal day22;
	@Excel(name="23")
	private BigDecimal day23;
	@Excel(name="24")
	private BigDecimal day24;
	@Excel(name="25")
	private BigDecimal day25;
	@Excel(name="26")
	private BigDecimal day26;
	@Excel(name="27")
	private BigDecimal day27;
	@Excel(name="28")
	private BigDecimal day28;
	@Excel(name="29")
	private BigDecimal day29;
	@Excel(name="30")
	private BigDecimal day30;
	@Excel(name="31")
	private BigDecimal day31;
	
	public String getBillaccountId() {
		return billaccountId;
	}
	public void setBillaccountId(String billaccountId) {
		this.billaccountId = billaccountId;
	}
	public String getBillaccountCode() {
		return billaccountCode;
	}
	public void setBillaccountCode(String billaccountCode) {
		this.billaccountCode = billaccountCode;
	}
	public String getBillaccountName() {
		return billaccountName;
	}
	public void setBillaccountName(String billaccountName) {
		this.billaccountName = billaccountName;
	}
	public String getBillaccountState() {
		return billaccountState;
	}
	public void setBillaccountState(String billaccountState) {
		this.billaccountState = billaccountState;
	}
	public String getPregName() {
		return pregName;
	}
	public void setPregName(String pregName) {
		this.pregName = pregName;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public Date getColDate() {
		return colDate;
	}
	public void setColDate(Date colDate) {
		this.colDate = colDate;
	}
	public BigDecimal getElectricmeterDegree() {
		return electricmeterDegree;
	}
	public void setElectricmeterDegree(BigDecimal electricmeterDegree) {
		this.electricmeterDegree = electricmeterDegree;
	}
	public BigDecimal getPowerloadDegree() {
		return powerloadDegree;
	}
	public void setPowerloadDegree(BigDecimal powerloadDegree) {
		this.powerloadDegree = powerloadDegree;
	}
	public BigDecimal getPowerratingDegree() {
		return powerratingDegree;
	}
	public void setPowerratingDegree(BigDecimal powerratingDegree) {
		this.powerratingDegree = powerratingDegree;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public BigDecimal getMonthTotal() {
		return monthTotal;
	}
	public void setMonthTotal(BigDecimal monthTotal) {
		this.monthTotal = monthTotal;
	}
	public BigDecimal getDay1() {
		return day1;
	}
	public void setDay1(BigDecimal day1) {
		this.day1 = day1;
	}
	public BigDecimal getDay2() {
		return day2;
	}
	public void setDay2(BigDecimal day2) {
		this.day2 = day2;
	}
	public BigDecimal getDay3() {
		return day3;
	}
	public void setDay3(BigDecimal day3) {
		this.day3 = day3;
	}
	public BigDecimal getDay4() {
		return day4;
	}
	public void setDay4(BigDecimal day4) {
		this.day4 = day4;
	}
	public BigDecimal getDay5() {
		return day5;
	}
	public void setDay5(BigDecimal day5) {
		this.day5 = day5;
	}
	public BigDecimal getDay6() {
		return day6;
	}
	public void setDay6(BigDecimal day6) {
		this.day6 = day6;
	}
	public BigDecimal getDay7() {
		return day7;
	}
	public void setDay7(BigDecimal day7) {
		this.day7 = day7;
	}
	public BigDecimal getDay8() {
		return day8;
	}
	public void setDay8(BigDecimal day8) {
		this.day8 = day8;
	}
	public BigDecimal getDay9() {
		return day9;
	}
	public void setDay9(BigDecimal day9) {
		this.day9 = day9;
	}
	public BigDecimal getDay10() {
		return day10;
	}
	public void setDay10(BigDecimal day10) {
		this.day10 = day10;
	}
	public BigDecimal getDay11() {
		return day11;
	}
	public void setDay11(BigDecimal day11) {
		this.day11 = day11;
	}
	public BigDecimal getDay12() {
		return day12;
	}
	public void setDay12(BigDecimal day12) {
		this.day12 = day12;
	}
	public BigDecimal getDay13() {
		return day13;
	}
	public void setDay13(BigDecimal day13) {
		this.day13 = day13;
	}
	public BigDecimal getDay14() {
		return day14;
	}
	public void setDay14(BigDecimal day14) {
		this.day14 = day14;
	}
	public BigDecimal getDay15() {
		return day15;
	}
	public void setDay15(BigDecimal day15) {
		this.day15 = day15;
	}
	public BigDecimal getDay16() {
		return day16;
	}
	public void setDay16(BigDecimal day16) {
		this.day16 = day16;
	}
	public BigDecimal getDay17() {
		return day17;
	}
	public void setDay17(BigDecimal day17) {
		this.day17 = day17;
	}
	public BigDecimal getDay18() {
		return day18;
	}
	public void setDay18(BigDecimal day18) {
		this.day18 = day18;
	}
	public BigDecimal getDay19() {
		return day19;
	}
	public void setDay19(BigDecimal day19) {
		this.day19 = day19;
	}
	public BigDecimal getDay20() {
		return day20;
	}
	public void setDay20(BigDecimal day20) {
		this.day20 = day20;
	}
	public BigDecimal getDay21() {
		return day21;
	}
	public void setDay21(BigDecimal day21) {
		this.day21 = day21;
	}
	public BigDecimal getDay22() {
		return day22;
	}
	public void setDay22(BigDecimal day22) {
		this.day22 = day22;
	}
	public BigDecimal getDay23() {
		return day23;
	}
	public void setDay23(BigDecimal day23) {
		this.day23 = day23;
	}
	public BigDecimal getDay24() {
		return day24;
	}
	public void setDay24(BigDecimal day24) {
		this.day24 = day24;
	}
	public BigDecimal getDay25() {
		return day25;
	}
	public void setDay25(BigDecimal day25) {
		this.day25 = day25;
	}
	public BigDecimal getDay26() {
		return day26;
	}
	public void setDay26(BigDecimal day26) {
		this.day26 = day26;
	}
	public BigDecimal getDay27() {
		return day27;
	}
	public void setDay27(BigDecimal day27) {
		this.day27 = day27;
	}
	public BigDecimal getDay28() {
		return day28;
	}
	public void setDay28(BigDecimal day28) {
		this.day28 = day28;
	}
	public BigDecimal getDay29() {
		return day29;
	}
	public void setDay29(BigDecimal day29) {
		this.day29 = day29;
	}
	public BigDecimal getDay30() {
		return day30;
	}
	public void setDay30(BigDecimal day30) {
		this.day30 = day30;
	}
	public BigDecimal getDay31() {
		return day31;
	}
	public void setDay31(BigDecimal day31) {
		this.day31 = day31;
	}
}
