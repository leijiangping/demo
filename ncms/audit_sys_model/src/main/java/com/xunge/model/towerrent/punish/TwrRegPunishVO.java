package com.xunge.model.towerrent.punish;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

import com.xunge.model.activity.Act;
import com.xunge.model.system.region.SysRegionVO;
/**
 * @description 地市扣罚
 * @author zhujj
 * @date 2017-7-28
 */
public class TwrRegPunishVO  implements Serializable{


	private static final long serialVersionUID = -4051915976674309302L;

	private String twrRegPunishId;//编码

    private String accountsummaryId;//汇总编码

	public TwrRegPunishVO() {
		
	}
	public TwrRegPunishVO(String userId) {
		super();
		this.updateUserId = userId;
		this.createUserId=userId;
		this.createTime=new Date();
		this.updatetTime=new Date();
		this.state=0;
		this.auditState=9;
	}
	@Excel(name="扣罚日期",orderNum="10",isImportField="true")
    private String punishYearMonth;//扣罚年月
	
	@Excel(name="所属区县",orderNum="20")
    private String regId;//扣罚区县编码
	//扣罚区县
	private SysRegionVO sysRegionVO;
	
	@Excel(name="其他扣罚名称",orderNum="30")
    private String punishName;//扣罚名称
	
	@Excel(name="扣罚费用",orderNum="40")
    private BigDecimal punishAmount;//扣罚费用

	@Excel(name="扣罚原因详细说明",orderNum="50")
    private String punishCause;//扣罚原因详细说明

	@Excel(name="扣罚申请人",orderNum="60")
    private String punishPerson;//扣罚申请人


    private Integer state;//状态：0-正常，1-删除

    private Integer auditState;//审核状态

    private String createUserId;//创建用户编码

    private Date createTime;//创建时间

    private String updateUserId;//更新用户

    private Date updatetTime;//更新时间
    
    private Act act;//业务数据对应的流程信息
    public boolean verifyData(){
    	return true;
    }	
    public String getTwrRegPunishId() {
        return twrRegPunishId;
    }

    public void setTwrRegPunishId(String twrRegPunishId) {
        this.twrRegPunishId = twrRegPunishId == null ? null : twrRegPunishId.trim();
    }

    public String getAccountsummaryId() {
        return accountsummaryId;
    }

    public void setAccountsummaryId(String accountsummaryId) {
        this.accountsummaryId = accountsummaryId == null ? null : accountsummaryId.trim();
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public String getPunishYearMonth() {
		return punishYearMonth;
	}
	public void setPunishYearMonth(String punishYearMonth) {
		this.punishYearMonth = punishYearMonth;
	}
	public String getPunishName() {
		return punishName;
	}

	public void setPunishName(String punishName) {
		this.punishName = punishName;
	}

	public String getPunishCause() {
		return punishCause;
	}

	public void setPunishCause(String punishCause) {
		this.punishCause = punishCause;
	}

	public String getPunishPerson() {
		return punishPerson;
	}

	public void setPunishPerson(String punishPerson) {
		this.punishPerson = punishPerson;
	}

	public BigDecimal getPunishAmount() {
		return punishAmount;
	}

	public void setPunishAmount(BigDecimal punishAmount) {
		this.punishAmount = punishAmount;
	}

	public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
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

    public void setUpdateUserId(String updateUser) {
        this.updateUserId = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdatetTime() {
        return updatetTime;
    }

    public void setUpdatetTime(Date updatetTime) {
        this.updatetTime = updatetTime;
    }
	
	public SysRegionVO getSysRegionVO() {
		return sysRegionVO;
	}
	public void setSysRegionVO(SysRegionVO sysRegionVO) {
		this.sysRegionVO = sysRegionVO;
	}
	public Act getAct() {
		return act;
	}
	public void setAct(Act act) {
		this.act = act;
	}
    
}