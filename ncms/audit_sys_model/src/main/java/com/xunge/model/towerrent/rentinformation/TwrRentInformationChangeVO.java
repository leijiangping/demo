package com.xunge.model.towerrent.rentinformation;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class TwrRentInformationChangeVO implements Serializable{
	
	private static final long serialVersionUID = 6815451869197151378L;
	
	//移动起租信息变更表编码
    private String rentinformationchangeId;
    
    //移动起租信息拆分表编码
    private String rentinformationhistoryId;
    
    //字段名称
    @Excel(name="参数名称",orderNum="3")
    private String fieldName;

    //旧值
    @Excel(name="原数值",orderNum="4")
    private String oldValue;

    //新值
    @Excel(name="新数值",orderNum="5")
    private String newValue;

    //用户id
    private Integer updateUserId;

    //时间
    @Excel(name="变更时间",orderNum="7",format="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    //账号
    @Excel(name="变更人",orderNum="6")
    private String userLoginname;
    
    //运营商物理站址编码
    @Excel(name="运营商物理站址编码",orderNum="1")
    private String operatorPhyStationCode;
    
    //运营商物理站址名称
    @Excel(name="运营商物理站址名称",orderNum="2")
    private String operatorPhyStationName;
    
    //审核状态
    private Integer checkState;

    //原服务起始日期
    @Excel(name="原服务起始日期",orderNum="8")
    private Date oldStartDate;
    
   // 原服务结束日期
    @Excel(name="原服务结束日期",orderNum="9")
    private Date oldEndDate;
    
    //新服务起始日期
    @Excel(name="新服务起始日期",orderNum="10")
    private Date newStartDate;
    
    //新服务结束日期
    @Excel(name="新服务结束日期",orderNum="11")
    private Date newEndDate;
    
    public String getRentinformationhistoryId() {
		return rentinformationhistoryId;
	}

	public void setRentinformationhistoryId(String rentinformationhistoryId) {
		this.rentinformationhistoryId = rentinformationhistoryId;
	}

	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getRentinformationchangeId() {
        return rentinformationchangeId;
    }

    public void setRentinformationchangeId(String rentinformationchangeId) {
        this.rentinformationchangeId = rentinformationchangeId == null ? null : rentinformationchangeId.trim();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue == null ? null : oldValue.trim();
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue == null ? null : newValue.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getUserLoginname() {
		return userLoginname;
	}

	public void setUserLoginname(String userLoginname) {
		this.userLoginname = userLoginname;
	}

	public String getOperatorPhyStationCode() {
		return operatorPhyStationCode;
	}

	public void setOperatorPhyStationCode(String operatorPhyStationCode) {
		this.operatorPhyStationCode = operatorPhyStationCode;
	}

	public String getOperatorPhyStationName() {
		return operatorPhyStationName;
	}

	public void setOperatorPhyStationName(String operatorPhyStationName) {
		this.operatorPhyStationName = operatorPhyStationName;
	}

	public Date getOldStartDate() {
		return oldStartDate;
	}

	public void setOldStartDate(Date oldStartDate) {
		this.oldStartDate = oldStartDate;
	}

	public Date getOldEndDate() {
		return oldEndDate;
	}

	public void setOldEndDate(Date oldEndDate) {
		this.oldEndDate = oldEndDate;
	}

	public Date getNewStartDate() {
		return newStartDate;
	}

	public void setNewStartDate(Date newStartDate) {
		this.newStartDate = newStartDate;
	}

	public Date getNewEndDate() {
		return newEndDate;
	}

	public void setNewEndDate(Date newEndDate) {
		this.newEndDate = newEndDate;
	}

}