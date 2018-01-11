package com.xunge.model.towerrent;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

/**
 * 
 * @author yuefy
 * @date 2017年07月12日
 *  起租管理 站址编码关联关系表
 */
@ExcelTarget(value = "towerLink")
public class TowerLinkVO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//铁塔公司站址编码
	
	private String towerStationCode;
	//运营商物理站址编码
	@Excel(name="运营商自有物理站址编码",orderNum="9")
    private String operatorPhyStationCode;
    //运营商物理站址名称
	@Excel(name="运营商自有物理站址名称",orderNum="10")
    private String operatorPhystationName;
    //关联状态（0 正常  9 终止）
    private Integer relationState;
    //关联开始时间
    private Date relationStartdate;
    //关联结束时间
    private Date relationEnddate;

    public String getTowerStationCode() {
        return towerStationCode;
    }

    public void setTowerStationCode(String towerStationCode) {
        this.towerStationCode = towerStationCode == null ? null : towerStationCode.trim();
    }

    public String getOperatorPhyStationCode() {
        return operatorPhyStationCode;
    }

    public void setOperatorPhyStationCode(String operatorPhyStationCode) {
        this.operatorPhyStationCode = operatorPhyStationCode == null ? null : operatorPhyStationCode.trim();
    }

    public String getOperatorPhystationName() {
        return operatorPhystationName;
    }

    public void setOperatorPhystationName(String operatorPhystationName) {
        this.operatorPhystationName = operatorPhystationName == null ? null : operatorPhystationName.trim();
    }

    public Integer getRelationState() {
        return relationState;
    }

    public void setRelationState(Integer relationState) {
        this.relationState = relationState;
    }

    public Date getRelationStartdate() {
        return relationStartdate;
    }

    public void setRelationStartdate(Date relationStartdate) {
        this.relationStartdate = relationStartdate;
    }

    public Date getRelationEnddate() {
        return relationEnddate;
    }

    public void setRelationEnddate(Date relationEnddate) {
        this.relationEnddate = relationEnddate;
    }
}