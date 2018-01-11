package com.xunge.model.towerrent.bizchange;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;

import com.xunge.model.activity.Act;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.rentmanager.TowerLinkVO;

public class TowerRentinformationBizchangeVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1685418625359905933L;

	//铁塔起租表业务变更表编码
    private String twrRentinformationBizchangeId;
   
//  //省份
//   @Excel(name="省份",orderNum="1",isImportField="true")
//   private String prvName;
//    
//  //地市
//   @Excel(name="地市",orderNum="2",isImportField="true")
//   private String pregName;
    
    //区域编码
     @Excel(name="区县",orderNum="3",isImportField="true")
    private String regId;
   
    //运营商
    @Excel(name="运营商",orderNum="4")
    private String operator;
     
    //业务确认单号
     @Excel(name="业务确认单号",orderNum="5",isImportField="true")
    private String businessConfirmNumber;
   
    //铁塔公司站址编码
     @Excel(name="铁塔公司站址编码",orderNum="8",isImportField="true")
    private String towerStationCode;
    
    //变更项目
     @Excel(name="变更项目",orderNum="11",isImportField="true")
    private String changeItem;
   
    //变更前内容
     @Excel(name="变更前内容",orderNum="12",isImportField="true")
    private String changeBeforeContent;
    
    //变更后内容
     @Excel(name="变更后内容",orderNum="14",isImportField="true")
    private String changeBehindContent;
   
   //是否追溯
     @Excel(name="是否追溯",orderNum="16",replace={"是_1","否_0"})
    private int ifAscend;
     
    //变更生效日期
     @Excel(name="变更生效日期",format="yyyy-MM-dd", orderNum="18",isImportField="true")
    private Date changeActiveDate;
  
    //追溯金额
     @Excel(name="追溯金额",orderNum="20")
    private BigDecimal ascendFee;
   
    //变更原因
     @Excel(name="变更原因",orderNum="22",isImportField="true")
    private String changeReason;
     
    //审核状态
    private Integer auditState;
     
	//区域信息对象
    @ExcelEntity(id="sysRegion",name="省市信息_sysRegion")
	private SysRegionVO sysRegionVO;
	
	//关联关系VO
    @ExcelEntity(id="datContract",name="关联对象_towerLink")
    private TowerLinkVO towerLinkVO;

    private Act act;//业务数据对应的流程信息
    
	public String getTwrRentinformationBizchangeId() {
		return twrRentinformationBizchangeId;
	}

	public void setTwrRentinformationBizchangeId(
			String twrRentinformationBizchangeId) {
		this.twrRentinformationBizchangeId = twrRentinformationBizchangeId;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getBusinessConfirmNumber() {
		return businessConfirmNumber;
	}

	public void setBusinessConfirmNumber(String businessConfirmNumber) {
		this.businessConfirmNumber = businessConfirmNumber;
	}

	public String getTowerStationCode() {
		return towerStationCode;
	}

	public void setTowerStationCode(String towerStationCode) {
		this.towerStationCode = towerStationCode;
	}

	public String getChangeItem() {
		return changeItem;
	}

	public void setChangeItem(String changeItem) {
		this.changeItem = changeItem;
	}

	public String getChangeBeforeContent() {
		return changeBeforeContent;
	}

	public void setChangeBeforeContent(String changeBeforeContent) {
		this.changeBeforeContent = changeBeforeContent;
	}

	public String getChangeBehindContent() {
		return changeBehindContent;
	}

	public void setChangeBehindContent(String changeBehindContent) {
		this.changeBehindContent = changeBehindContent;
	}

	public Date getChangeActiveDate() {
		return changeActiveDate;
	}

	public void setChangeActiveDate(Date changeActiveDate) {
		this.changeActiveDate = changeActiveDate;
	}

	public int getIfAscend() {
		return ifAscend;
	}

	public void setIfAscend(int ifAscend) {
		this.ifAscend = ifAscend;
	}

	public BigDecimal getAscendFee() {
		return ascendFee;
	}

	public void setAscendFee(BigDecimal ascendFee) {
		this.ascendFee = ascendFee;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
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

	public TowerLinkVO getTowerLinkVO() {
		return towerLinkVO;
	}

	public void setTowerLinkVO(TowerLinkVO towerLinkVO) {
		this.towerLinkVO = towerLinkVO;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

}