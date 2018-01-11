package com.xunge.model.towerrent.punish;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 集团既定考核扣罚信息汇总
 * @author changwq
 *
 */
public class TwrGroupRegPunishVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3718607337233942281L;
	//编码
    //@Excel(name="",orderNum="")
    private String twrGroupRegPunishId;
    //汇总编码
    //@Excel(name="",orderNum="")
    private String accountsummaryId;
    //年月
    //@Excel(name="",orderNum="")
    private String punishYearMonth;
    //地市
    //@Excel(name="",orderNum="")
    private String regId;
    //罚金
    //@Excel(name="",orderNum="")
    private BigDecimal punishamount;
    //扣罚次数
    //@Excel(name="",orderNum="")
    private Integer towerbasestationcount;
    //平均扣罚金额
    //@Excel(name="",orderNum="")
    private BigDecimal avgpunishamount;
    //状态 0:正常 1:删除
    //@Excel(name="",orderNum="")
    private Integer state;
    //创建用户编码
    //@Excel(name="",orderNum="")
    private String createUserId;
    //创建时间
    //@Excel(name="",orderNum="")
    private Date createTime;
    //更新用户编码
    //@Excel(name="",orderNum="")
    private String updateUserId;
    //更新时间
    //@Excel(name="",orderNum="")
    private Date updateTime;
    
    public String getTwrGroupRegPunishId() {
        return twrGroupRegPunishId;
    }

    public void setTwrGroupRegPunishId(String twrGroupRegPunishId) {
        this.twrGroupRegPunishId = twrGroupRegPunishId == null ? null : twrGroupRegPunishId.trim();
    }

    public String getAccountsummaryId() {
        return accountsummaryId;
    }

    public void setAccountsummaryId(String accountsummaryId) {
        this.accountsummaryId = accountsummaryId == null ? null : accountsummaryId.trim();
    }

    public String getPunishYearMonth() {
        return punishYearMonth;
    }

    public void setPunishYearMonth(String punishYearMonth) {
        this.punishYearMonth = punishYearMonth == null ? null : punishYearMonth.trim();
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public BigDecimal getPunishamount() {
        return punishamount;
    }

    public void setPunishamount(BigDecimal punishamount) {
        this.punishamount = punishamount;
    }

    public Integer getTowerbasestationcount() {
        return towerbasestationcount;
    }

    public void setTowerbasestationcount(Integer towerbasestationcount) {
        this.towerbasestationcount = towerbasestationcount;
    }

    public BigDecimal getAvgpunishamount() {
        return avgpunishamount;
    }

    public void setAvgpunishamount(BigDecimal avgpunishamount) {
        this.avgpunishamount = avgpunishamount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}