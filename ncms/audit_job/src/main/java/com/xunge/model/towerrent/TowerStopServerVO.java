package com.xunge.model.towerrent;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

import com.xunge.comm.StateComm;
import com.xunge.util.SysUUID;
/**
 * 服务终止单
 * @author wangz
 * @date 2017-09-20 11:19:33
 */
public class TowerStopServerVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2077478092218497512L;
	
	public TowerStopServerVO() {
		this.stopServerId = SysUUID.generator();
		this.auditState = StateComm.STATE__1;
		this.createTime = new Date();
	}

	//终止服务表编码
    private String stopServerId;
    
    //业务确认单号
    @Excel(name="产品业务确认单编号",orderNum="6",isImportField="true",needMerge=true)
    private String businessConfirmNumber;
    
    //区域编码
    @Excel(name="区县",orderNum="5",isImportField="true",needMerge=true)
    private String regId;
    
    //站点名称
    @Excel(name="站点名称",orderNum="7",needMerge=true)
    private String towerStationName;
    
    //铁塔公司站址编码
    @Excel(name="站点编号",orderNum="9",isImportField="true",needMerge=true)
    private String towerStationCode;
    
    @Excel(name="服务期限（合同约定）")
    public String serviceDate;
    
    //起始时间
    @Excel(name="服务期限（合同约定）_起始时间",orderNum="12",format="yyyy-MM-dd")
    private String startDate;
    
    //截止时间
    @Excel(name="截止时间",orderNum="14",format="yyyy-MM-dd")
    private String endDate;
    
    //服务终止生效日期
    @Excel(name="服务终止生效日期",orderNum="16",format="yyyy-MM-dd",isImportField="true",needMerge=true)
    private String stopUseDate;
    
    //服务终止原因
    @Excel(name="服务终止原因",orderNum="18",needMerge=true)
    private String stopReason;
    
    //创建用户编码
    private String createUserId;
    
    //创建时间
    private Date createTime;
    
    //审核状态
    private Integer auditState;

    public String getStopServerId() {
        return stopServerId;
    }

    public void setStopServerId(String stopServerId) {
        this.stopServerId = stopServerId == null ? null : stopServerId.trim();
    }

    public String getBusinessConfirmNumber() {
        return businessConfirmNumber;
    }

    public void setBusinessConfirmNumber(String businessConfirmNumber) {
        this.businessConfirmNumber = businessConfirmNumber == null ? null : businessConfirmNumber.trim();
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public String getTowerStationName() {
        return towerStationName;
    }

    public void setTowerStationName(String towerStationName) {
        this.towerStationName = towerStationName == null ? null : towerStationName.trim();
    }

    public String getTowerStationCode() {
        return towerStationCode;
    }

    public void setTowerStationCode(String towerStationCode) {
        this.towerStationCode = towerStationCode == null ? null : towerStationCode.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStopUseDate() {
        return stopUseDate;
    }

    public void setStopUseDate(String stopUseDate) {
        this.stopUseDate = stopUseDate;
    }

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason == null ? null : stopReason.trim();
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

	public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}
}