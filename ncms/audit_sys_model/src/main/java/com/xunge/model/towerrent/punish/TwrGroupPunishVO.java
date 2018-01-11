package com.xunge.model.towerrent.punish;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

import com.xunge.model.system.region.SysRegionVO;

/**
 * 集团既定考核指标扣罚
 * @author changwq
 *
 */
public class TwrGroupPunishVO implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -6607322985364889278L;
	//编码
    //@excel(name="",orderNum="")
    private String twrGroupPunishId;
    //地市编码
    //@excel(name="",orderNum="")
    private String regId;
    //地市扣罚编码
    //@excel(name="",orderNum="")
    private String twrGroupRegPunishId;
    //地市账单编码
    //@excel(name="",orderNum="")
    private String towerbillbalanceId;
    //年月
    @Excel(name="年月",orderNum="2")
    private String punishYearMonth;
    //铁塔站址编码
    @Excel(name="铁塔站点编码",orderNum="4")
    private String towerstationcode;
    //月断电退服总时
    @Excel(name="单站址断电退服月总时长（分钟）",orderNum="10")
    private BigDecimal takebacktime;
    //罚金
    //@excel(name="",orderNum="")
    private BigDecimal punishamount;
    //维护等级
    //@excel(name="",orderNum="")
    private Integer maintenancelevelid;
    //是否免责
    @Excel(name="是否免责",orderNum="6",replace={"免责_1","不免责_0"})
    private int ifduty;
    //覆盖场景
    @Excel(name="覆盖场景",orderNum="8",replace={"高铁_1","非高铁_0"})
    private Integer coversceneid;
    //状态
    //@excel(name="",orderNum="")
    private Integer state;
    //创建用户编码
    //@excel(name="",orderNum="")
    private String saveUserId;
    //创建时间
    //@excel(name="",orderNum="")
    private Date saveTime;
    //更新用户编码
    //@excel(name="",orderNum="")
    private String updateUserId;
    //更新时间
    //@excel(name="",orderNum="")
    private Date updateTime;
    
    private SysRegionVO sysRegionVO;

    public String getTwrGroupPunishId() {
        return twrGroupPunishId;
    }

    public void setTwrGroupPunishId(String twrGroupPunishId) {
        this.twrGroupPunishId = twrGroupPunishId == null ? null : twrGroupPunishId.trim();
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public String getTwrGroupRegPunishId() {
        return twrGroupRegPunishId;
    }

    public void setTwrGroupRegPunishId(String twrGroupRegPunishId) {
        this.twrGroupRegPunishId = twrGroupRegPunishId == null ? null : twrGroupRegPunishId.trim();
    }

    public String getTowerbillbalanceId() {
        return towerbillbalanceId;
    }

    public void setTowerbillbalanceId(String towerbillbalanceId) {
        this.towerbillbalanceId = towerbillbalanceId == null ? null : towerbillbalanceId.trim();
    }

    public String getPunishYearMonth() {
        return punishYearMonth;
    }

    public void setPunishYearMonth(String punishYearMonth) {
        this.punishYearMonth = punishYearMonth == null ? null : punishYearMonth.trim();
    }

    public String getTowerstationcode() {
        return towerstationcode;
    }

    public void setTowerstationcode(String towerstationcode) {
        this.towerstationcode = towerstationcode == null ? null : towerstationcode.trim();
    }

    public BigDecimal getTakebacktime() {
        return takebacktime;
    }

    public void setTakebacktime(BigDecimal takebacktime) {
        this.takebacktime = takebacktime;
    }

    public BigDecimal getPunishamount() {
        return punishamount;
    }

    public void setPunishamount(BigDecimal punishamount) {
        this.punishamount = punishamount;
    }

    public Integer getMaintenancelevelid() {
        return maintenancelevelid;
    }

    public void setMaintenancelevelid(Integer maintenancelevelid) {
        this.maintenancelevelid = maintenancelevelid;
    }

    public int getIfduty() {
        return ifduty;
    }

    public void setIfduty(int ifduty) {
        this.ifduty = ifduty;
    }

    public Integer getCoversceneid() {
        return coversceneid;
    }

    public void setCoversceneid(Integer coversceneid) {
        this.coversceneid = coversceneid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSaveUserId() {
        return saveUserId;
    }

    public void setSaveUserId(String saveUserId) {
        this.saveUserId = saveUserId == null ? null : saveUserId.trim();
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
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

	public SysRegionVO getSysRegionVO() {
		return sysRegionVO;
	}

	public void setSysRegionVO(SysRegionVO sysRegionVO) {
		this.sysRegionVO = sysRegionVO;
	}
}