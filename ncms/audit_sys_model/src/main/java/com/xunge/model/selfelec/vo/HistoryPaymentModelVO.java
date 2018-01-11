package com.xunge.model.selfelec.vo;

import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class HistoryPaymentModelVO {
	String billaccountId;
	@Excel(name="报账点编码",orderNum="1")
	String billaccountCode;
	@Excel(name="报账点名称",orderNum="2")
	String billaccountName;
	int billaccountState;
	@Excel(name="报账点状态",orderNum="3")
	String billaccountStateStr;
	String pregId;
	@Excel(name="地市",orderNum="4")
	String pregName;
	String regId;
	@Excel(name="区县",orderNum="5")
	String regName;
	Date colDate;
	BigDecimal nowDegree;
	@Excel(name="年份",orderNum="6")
	String year;
	@Excel(name="月份",orderNum="7")
	String month;
	String day;
	@Excel(name="月总标杆",orderNum="8")
	BigDecimal monthSummary;
	@Excel(name="1",orderNum="9")
	BigDecimal day01;
	@Excel(name="2",orderNum="10")
	BigDecimal day02;
	@Excel(name="3",orderNum="11")
	BigDecimal day03;
	@Excel(name="4",orderNum="12")
	BigDecimal day04;
	@Excel(name="5",orderNum="13")
	BigDecimal day05;
	@Excel(name="6",orderNum="14")
	BigDecimal day06;
	@Excel(name="7",orderNum="15")
	BigDecimal day07;
	@Excel(name="8",orderNum="16")
	BigDecimal day08;
	@Excel(name="9",orderNum="17")
	BigDecimal day09;
	@Excel(name="10",orderNum="18")
	BigDecimal day10;
	@Excel(name="11",orderNum="19")
	BigDecimal day11;
	@Excel(name="12",orderNum="20")
	BigDecimal day12;
	@Excel(name="13",orderNum="21")
	BigDecimal day13;
	@Excel(name="14",orderNum="22")
	BigDecimal day14;
	@Excel(name="15",orderNum="23")
	BigDecimal day15;
	@Excel(name="16",orderNum="24")
	BigDecimal day16;
	@Excel(name="17",orderNum="25")
	BigDecimal day17;
	@Excel(name="18",orderNum="26")
	BigDecimal day18;
	@Excel(name="19",orderNum="27")
	BigDecimal day19;
	@Excel(name="20",orderNum="28")
	BigDecimal day20;
	@Excel(name="21",orderNum="29")
	BigDecimal day21;
	@Excel(name="22",orderNum="30")
	BigDecimal day22;
	@Excel(name="23",orderNum="31")
	BigDecimal day23;
	@Excel(name="24",orderNum="32")
	BigDecimal day24;
	@Excel(name="25",orderNum="33")
	BigDecimal day25;
	@Excel(name="26",orderNum="34")
	BigDecimal day26;
	@Excel(name="27",orderNum="35")
	BigDecimal day27;
	@Excel(name="28",orderNum="36")
	BigDecimal day28;
	@Excel(name="29",orderNum="37")
	BigDecimal day29;
	@Excel(name="30",orderNum="38")
	BigDecimal day30;
	@Excel(name="31",orderNum="39")
	BigDecimal day31;
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
	public int getBillaccountState() {
		return billaccountState;
	}
	public void setBillaccountState(int billaccountState) {
		this.billaccountState = billaccountState;
	}
	public String getPregId() {
		return pregId;
	}
	public void setPregId(String pregId) {
		this.pregId = pregId;
	}
	public String getPregName() {
		return pregName;
	}
	public void setPregName(String pregName) {
		this.pregName = pregName;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
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
	public BigDecimal getNowDegree() {
		return nowDegree;
	}
	public void setNowDegree(BigDecimal nowDegree) {
		this.nowDegree = nowDegree;
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
	public BigDecimal getMonthSummary() {
		return monthSummary;
	}
	public void setMonthSummary(BigDecimal monthSummary) {
		this.monthSummary = monthSummary;
	}
	public BigDecimal getDay01() {
		return day01;
	}
	public void setDay01(BigDecimal day01) {
		this.day01 = day01;
	}
	public BigDecimal getDay02() {
		return day02;
	}
	public void setDay02(BigDecimal day02) {
		this.day02 = day02;
	}
	public BigDecimal getDay03() {
		return day03;
	}
	public void setDay03(BigDecimal day03) {
		this.day03 = day03;
	}
	public BigDecimal getDay04() {
		return day04;
	}
	public void setDay04(BigDecimal day04) {
		this.day04 = day04;
	}
	public BigDecimal getDay05() {
		return day05;
	}
	public void setDay05(BigDecimal day05) {
		this.day05 = day05;
	}
	public BigDecimal getDay06() {
		return day06;
	}
	public void setDay06(BigDecimal day06) {
		this.day06 = day06;
	}
	public BigDecimal getDay07() {
		return day07;
	}
	public void setDay07(BigDecimal day07) {
		this.day07 = day07;
	}
	public BigDecimal getDay08() {
		return day08;
	}
	public void setDay08(BigDecimal day08) {
		this.day08 = day08;
	}
	public BigDecimal getDay09() {
		return day09;
	}
	public void setDay09(BigDecimal day09) {
		this.day09 = day09;
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
	public String getBillaccountId() {
		return billaccountId;
	}
	public void setBillaccountId(String billaccountId) {
		this.billaccountId = billaccountId;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getBillaccountStateStr() {
		return billaccountStateStr;
	}
	public void setBillaccountStateStr(String billaccountStateStr) {
		this.billaccountStateStr = billaccountStateStr;
	}
}
