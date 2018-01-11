package com.xunge.model.towerrent.stopserver;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;

import com.xunge.model.activity.Act;
import com.xunge.model.system.region.SysRegionVO;

public class TowerStopServerVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2077478092218497512L;

	//终止服务表编码
    private String stopServerId;
    
    //业务确认单号
    @Excel(name="产品业务确认单编号",orderNum="6",isImportField="true")
    private String businessConfirmNumber;
    
    //区域编码
    @Excel(name="区县",orderNum="5",isImportField="true")
    private String regId;
    
    //站点名称
    @Excel(name="站点名称",orderNum="7")
    private String towerStationName;
    
    //铁塔公司站址编码
    @Excel(name="站点编号",orderNum="9",isImportField="true")
    private String towerStationCode;
    
    @ExcelCollection(name="服务期限（合同约定）",orderNum="12")
    public List<TowerStopServerDateVO> towerStopServerDateVO;
    
    //起始时间
    private Date startDate;
    
    //截止时间
    private Date endDate;
    
    //服务终止生效日期
    @Excel(name="服务终止生效日期",orderNum="16",format="yyyy-MM-dd",isImportField="true")
    private Date stopUseDate;
    
    //服务终止原因
    @Excel(name="服务终止原因",orderNum="18")
    private String stopReason;
    
    //创建用户编码
    private String createUserId;
    
    //创建时间
    private Date createTime;
    
    //审核状态
    private Integer auditState;
    
    //区县信息
    @ExcelEntity(id="sysRegion",name="省市信息_sysRegion")
    private SysRegionVO sysRegionVO;
    
    private Act act;//业务数据对应的流程信息

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStopUseDate() {
        return stopUseDate;
    }

    public void setStopUseDate(Date stopUseDate) {
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

	public SysRegionVO getSysRegionVO() {
		return sysRegionVO;
	}

	public void setSysRegionVO(SysRegionVO sysRegionVO) {
		this.sysRegionVO = sysRegionVO;
	}

	public List<TowerStopServerDateVO> getTowerStopServerDateVO() {
		return towerStopServerDateVO;
	}

	public void setTowerStopServerDateVO(
			List<TowerStopServerDateVO> towerStopServerDateVO) {
		this.towerStopServerDateVO = towerStopServerDateVO;
	}

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}
    
}