package com.xunge.model.towerrent.punish;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

import com.xunge.model.system.region.SysRegionVO;

/**
 * 扣罚汇总
 * @author changwq
 *
 */
public class TwrSummaryPunishVO implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -845681972879819425L;
    //地市编码
    //@excel(name="",orderNum="")
    private String regName;
    //年月
    //@Excel(name="年月",orderNum="2")
    private String punishYearMonth;
    //集团罚金
    //@excel(name="",orderNum="")
    private BigDecimal groupPunishAmount;
    //省级罚金
    //@excel(name="",orderNum="")
    private BigDecimal provincePunishAmount;
    //地市罚金
    //@excel(name="",orderNum="")
    private BigDecimal regionPunishAmount;
    //罚金合计
    //@excel(name="",orderNum="")
    private BigDecimal sumPunishAmount;
    

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getPunishYearMonth() {
		return punishYearMonth;
	}

	public void setPunishYearMonth(String punishYearMonth) {
		this.punishYearMonth = punishYearMonth;
	}

	public BigDecimal getGroupPunishAmount() {
		return groupPunishAmount;
	}

	public void setGroupPunishAmount(BigDecimal groupPunishAmount) {
		this.groupPunishAmount = groupPunishAmount;
	}

	public BigDecimal getProvincePunishAmount() {
		return provincePunishAmount;
	}

	public void setProvincePunishAmount(BigDecimal provincePunishAmount) {
		this.provincePunishAmount = provincePunishAmount;
	}

	public BigDecimal getRegionPunishAmount() {
		return regionPunishAmount;
	}

	public void setRegionPunishAmount(BigDecimal regionPunishAmount) {
		this.regionPunishAmount = regionPunishAmount;
	}

	public BigDecimal getSumPunishAmount() {
		return sumPunishAmount;
	}

	public void setSumPunishAmount(BigDecimal sumPunishAmount) {
		this.sumPunishAmount = sumPunishAmount;
	}
}