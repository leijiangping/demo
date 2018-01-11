package com.xunge.model.towerrent.settlement;

import java.io.Serializable;
import java.math.BigDecimal;

import com.xunge.model.system.region.SysRegionVO;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 移动侧和铁塔侧信息VO
 * @author lpw
 *
 */
public class TowerAndMobileBillConfirmVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Excel(name="确认状态（调整）",orderNum="1")
	private Integer confirmState;		// 确认状态（1：正常 2:争议账单3：调整确认后账单）
	private String towerbillbalanceId;		// 租赁费账单表编码
	@Excel(name="账期月份",orderNum="3",format="yyyyMM",databaseFormat="yyyyMM",isImportField="true")
	private String accountPeroid;		// 账期月份
	
	private String dataType;		// 数据类型（1：铁塔账单 2：移动账单）
	
	@Excel(name="产品业务确认单编号",orderNum="4",isImportField="true")
	private String businessConfirmNumber;		// 业务确认单编号
	
	@Excel(name="运营商地市",orderNum="5")
	private String operatorRegId;		// 运营商地区编码

	@Excel(name="铁塔站址编码",orderNum="11")
	private String towerStationCode;		// 铁塔站址编码
	
	@Excel(name="铁塔站址名称",orderNum="10")
	private String towerStationName;		// 铁塔站址名称
	
	@Excel(name="需求确认单编号",orderNum="12")
	private String agreeBillCode;		// 需求确认单编号

	@Excel(name="铁塔侧油机发电服务费（包干）",orderNum="17")
	private BigDecimal usePowerServiceFeeOut;		// 油机发电服务费（包干）（出账费用）
	@Excel(name="铁塔侧超过10%高等级服务站址额外维护服务费",orderNum="19")
	private BigDecimal hightLevelFeeOut;		// 超过10%高等级服务站址额外维护服务费（出账费用）
	@Excel(name="铁塔侧蓄电池额外保障费",orderNum="21")
	private BigDecimal batteryprotectionfeeOut;		// 蓄电池额外保障费（出账费用）
	@Excel(name="铁塔侧期末铁塔共享后基准价格1+2+3",orderNum="23")
	private BigDecimal currentTowerShareSumPriceOut;		// 期末铁塔共享后基准价格1+2+3（出账费用）
	@Excel(name="铁塔侧期末机房共享后基准价格1+2+3",orderNum="25")
	private BigDecimal currentRoomShareSumPriceOut;		// 期末机房共享后基准价格1+2+3（出账费用）

	@Excel(name="铁塔侧配套共享后基准价格1+2+3",orderNum="27")
	private BigDecimal currentSupportingShareSumPriceOut;		// 配套共享后基准价格1+2+3（出账费用）
	@Excel(name="铁塔侧bbu安装在铁塔机房费",orderNum="29")
	private BigDecimal bbuOnRoomFeeBack;		// bbu安装在铁塔机房费（出账费用）
	@Excel(name="铁塔侧维护费折扣后金额1+2+3",orderNum="31")
	private BigDecimal currentManagerFeeShareSumPriceOut;		// 维护费折扣后金额1+2+3（出账费用）
	@Excel(name="铁塔侧场地费折扣后金额",orderNum="33")
	private BigDecimal currentPraceFeeShareSumPriceOut;		// 场地费折扣后金额（出账费用）
	@Excel(name="铁塔侧电力引入费折扣后金额",orderNum="35")
	private BigDecimal currentPowerInFeeShareSumPriceOut;		// 电力引入费折扣后金额（出账费用）
	@Excel(name="铁塔侧WLAN费用",orderNum="37")
	private BigDecimal wlanFeeOut;		// WLAN费用（出账费用）
	@Excel(name="铁塔侧微波费用",orderNum="39")
	private BigDecimal microwaveFeeOut;		// 微波费用（出账费用）
	@Excel(name="铁塔侧其他费用1",orderNum="41")
	private BigDecimal otherFee1Out;		// 其他费用1（出账费用）
//	@Excel(name="确认状态（调整）",orderNum="173")
//	private Integer confirmState;		// 确认状态（1：正常 2:争议账单3：调整确认后账单）
	@Excel(name="订单属性",orderNum="7")
	private String orderProp;		// 订单属性
	@Excel(name="产权属性",orderNum="8")
	private String rightProp;		// 产权属性
	@Excel(name="原产权方",orderNum="9")
	private String oriRight;		// 原产权方
	@Excel(name="对账结果",orderNum="2")
	private Integer resCompare;		// 对账结果（0费用一致，1铁塔侧账单高，2铁塔侧账单低）
	@Excel(name="铁塔侧产品服务费月费用合计（不含税）",orderNum="13")
	private BigDecimal totalAmountMonthOut;		// 产品服务费合计（出账费用）（不含税）
	@Excel(name="铁塔侧用电服务费（包干）",orderNum="15")
	private BigDecimal elecFeeOut;		// 电费（包干）（出账费用）
	private SysRegionVO sysRegionVO;//区域信息对象;//区域信息对象

	private String dataType1;		// 数据类型（1：铁塔账单 2：移动账单）
	@Excel(name="移动侧油机发电服务费（包干）",orderNum="18")
	private BigDecimal usePowerServiceFeeOut1;		// 油机发电服务费（包干）（出账费用）
	@Excel(name="移动侧超过10%高等级服务站址额外维护服务费",orderNum="20")
	private BigDecimal hightLevelFeeOut1;		// 超过10%高等级服务站址额外维护服务费（出账费用）
	@Excel(name="移动侧蓄电池额外保障费",orderNum="22")
	private BigDecimal batteryprotectionfeeOut1;		// 蓄电池额外保障费（出账费用）
	@Excel(name="移动侧期末铁塔共享后基准价格1+2+3",orderNum="24")
	private BigDecimal currentTowerShareSumPriceOut1;		// 期末铁塔共享后基准价格1+2+3（出账费用）
	@Excel(name="移动期末机房共享后基准价格1+2+3",orderNum="26")
	private BigDecimal currentRoomShareSumPriceOut1;		// 期末机房共享后基准价格1+2+3（出账费用）
	@Excel(name="移动侧移动侧配套共享后基准价格1+2+3",orderNum="28")
	private BigDecimal currentSupportingShareSumPriceOut1;		// 配套共享后基准价格1+2+3（出账费用）
	@Excel(name="移动侧移动侧bbu安装在铁塔机房费",orderNum="30")
	private BigDecimal bbuOnRoomFeeBack1;		// bbu安装在铁塔机房费（出账费用）
	@Excel(name="移动侧维护费折扣后金额1+2+3",orderNum="32")
	private BigDecimal currentManagerFeeShareSumPriceOut1;		// 维护费折扣后金额1+2+3（出账费用）
	@Excel(name="移动侧场地费折扣后金额",orderNum="34")
	private BigDecimal currentPraceFeeShareSumPriceOut1;		// 场地费折扣后金额（出账费用）
	@Excel(name="移动侧电力引入费折扣后金额",orderNum="36")
	private BigDecimal currentPowerInFeeShareSumPriceOut1;		// 电力引入费折扣后金额（出账费用）
	@Excel(name="移动侧WLAN费用",orderNum="38")
	private BigDecimal wlanFeeOut1;		// WLAN费用（出账费用）
	@Excel(name="移动侧微波费用",orderNum="40")
	private BigDecimal microwaveFeeOut1;		// 微波费用（出账费用）
	@Excel(name="移动侧其他费用1",orderNum="42")
	private BigDecimal otherFee1Out1;		// 其他费用1（出账费用）
	@Excel(name="移动侧产品服务费月费用合计（不含税）",orderNum="13")
	private BigDecimal totalAmountMonthOut1;		// 产品服务费合计（出账费用）（不含税）
	@Excel(name="移动侧用电服务费（包干）",orderNum="16")
	private BigDecimal elecFeeOut1;		// 电费（包干）（出账费用）
	public String getTowerbillbalanceId() {
		return towerbillbalanceId;
	}
	public void setTowerbillbalanceId(String towerbillbalanceId) {
		this.towerbillbalanceId = towerbillbalanceId;
	}
	public String getAccountPeroid() {
		return accountPeroid;
	}
	public void setAccountPeroid(String accountPeroid) {
		this.accountPeroid = accountPeroid;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getBusinessConfirmNumber() {
		return businessConfirmNumber;
	}
	public void setBusinessConfirmNumber(String businessConfirmNumber) {
		this.businessConfirmNumber = businessConfirmNumber;
	}
	public String getOperatorRegId() {
		return operatorRegId;
	}
	public void setOperatorRegId(String operatorRegId) {
		this.operatorRegId = operatorRegId;
	}
	public String getTowerStationCode() {
		return towerStationCode;
	}
	public void setTowerStationCode(String towerStationCode) {
		this.towerStationCode = towerStationCode;
	}
	public String getTowerStationName() {
		return towerStationName;
	}
	public void setTowerStationName(String towerStationName) {
		this.towerStationName = towerStationName;
	}
	public String getAgreeBillCode() {
		return agreeBillCode;
	}
	public void setAgreeBillCode(String agreeBillCode) {
		this.agreeBillCode = agreeBillCode;
	}
	public BigDecimal getUsePowerServiceFeeOut() {
		return usePowerServiceFeeOut;
	}
	public void setUsePowerServiceFeeOut(BigDecimal usePowerServiceFeeOut) {
		this.usePowerServiceFeeOut = usePowerServiceFeeOut;
	}
	public BigDecimal getHightLevelFeeOut() {
		return hightLevelFeeOut;
	}
	public void setHightLevelFeeOut(BigDecimal hightLevelFeeOut) {
		this.hightLevelFeeOut = hightLevelFeeOut;
	}
	public BigDecimal getBatteryprotectionfeeOut() {
		return batteryprotectionfeeOut;
	}
	public void setBatteryprotectionfeeOut(BigDecimal batteryprotectionfeeOut) {
		this.batteryprotectionfeeOut = batteryprotectionfeeOut;
	}
	public BigDecimal getCurrentTowerShareSumPriceOut() {
		return currentTowerShareSumPriceOut;
	}
	public void setCurrentTowerShareSumPriceOut(
			BigDecimal currentTowerShareSumPriceOut) {
		this.currentTowerShareSumPriceOut = currentTowerShareSumPriceOut;
	}
	public BigDecimal getCurrentRoomShareSumPriceOut() {
		return currentRoomShareSumPriceOut;
	}
	public void setCurrentRoomShareSumPriceOut(
			BigDecimal currentRoomShareSumPriceOut) {
		this.currentRoomShareSumPriceOut = currentRoomShareSumPriceOut;
	}
	public BigDecimal getCurrentSupportingShareSumPriceOut() {
		return currentSupportingShareSumPriceOut;
	}
	public void setCurrentSupportingShareSumPriceOut(
			BigDecimal currentSupportingShareSumPriceOut) {
		this.currentSupportingShareSumPriceOut = currentSupportingShareSumPriceOut;
	}
	public BigDecimal getBbuOnRoomFeeBack() {
		return bbuOnRoomFeeBack;
	}
	public void setBbuOnRoomFeeBack(BigDecimal bbuOnRoomFeeBack) {
		this.bbuOnRoomFeeBack = bbuOnRoomFeeBack;
	}
	public BigDecimal getCurrentManagerFeeShareSumPriceOut() {
		return currentManagerFeeShareSumPriceOut;
	}
	public void setCurrentManagerFeeShareSumPriceOut(
			BigDecimal currentManagerFeeShareSumPriceOut) {
		this.currentManagerFeeShareSumPriceOut = currentManagerFeeShareSumPriceOut;
	}
	public BigDecimal getCurrentPraceFeeShareSumPriceOut() {
		return currentPraceFeeShareSumPriceOut;
	}
	public void setCurrentPraceFeeShareSumPriceOut(
			BigDecimal currentPraceFeeShareSumPriceOut) {
		this.currentPraceFeeShareSumPriceOut = currentPraceFeeShareSumPriceOut;
	}
	public BigDecimal getCurrentPowerInFeeShareSumPriceOut() {
		return currentPowerInFeeShareSumPriceOut;
	}
	public void setCurrentPowerInFeeShareSumPriceOut(
			BigDecimal currentPowerInFeeShareSumPriceOut) {
		this.currentPowerInFeeShareSumPriceOut = currentPowerInFeeShareSumPriceOut;
	}
	public BigDecimal getWlanFeeOut() {
		return wlanFeeOut;
	}
	public void setWlanFeeOut(BigDecimal wlanFeeOut) {
		this.wlanFeeOut = wlanFeeOut;
	}
	public BigDecimal getMicrowaveFeeOut() {
		return microwaveFeeOut;
	}
	public void setMicrowaveFeeOut(BigDecimal microwaveFeeOut) {
		this.microwaveFeeOut = microwaveFeeOut;
	}
	public BigDecimal getOtherFee1Out() {
		return otherFee1Out;
	}
	public void setOtherFee1Out(BigDecimal otherFee1Out) {
		this.otherFee1Out = otherFee1Out;
	}
	
	public String getOrderProp() {
		return orderProp;
	}
	public void setOrderProp(String orderProp) {
		this.orderProp = orderProp;
	}
	public String getRightProp() {
		return rightProp;
	}
	public void setRightProp(String rightProp) {
		this.rightProp = rightProp;
	}
	public String getOriRight() {
		return oriRight;
	}
	public void setOriRight(String oriRight) {
		this.oriRight = oriRight;
	}
	public Integer getResCompare() {
		return resCompare;
	}
	public void setResCompare(Integer resCompare) {
		this.resCompare = resCompare;
	}
	public BigDecimal getTotalAmountMonthOut() {
		return totalAmountMonthOut;
	}
	public void setTotalAmountMonthOut(BigDecimal totalAmountMonthOut) {
		this.totalAmountMonthOut = totalAmountMonthOut;
	}
	public BigDecimal getElecFeeOut() {
		return elecFeeOut;
	}
	public void setElecFeeOut(BigDecimal elecFeeOut) {
		this.elecFeeOut = elecFeeOut;
	}
	public String getDataType1() {
		return dataType1;
	}
	public void setDataType1(String dataType1) {
		this.dataType1 = dataType1;
	}
	public BigDecimal getUsePowerServiceFeeOut1() {
		return usePowerServiceFeeOut1;
	}
	public void setUsePowerServiceFeeOut1(BigDecimal usePowerServiceFeeOut1) {
		this.usePowerServiceFeeOut1 = usePowerServiceFeeOut1;
	}
	public BigDecimal getHightLevelFeeOut1() {
		return hightLevelFeeOut1;
	}
	public void setHightLevelFeeOut1(BigDecimal hightLevelFeeOut1) {
		this.hightLevelFeeOut1 = hightLevelFeeOut1;
	}
	public BigDecimal getBatteryprotectionfeeOut1() {
		return batteryprotectionfeeOut1;
	}
	public void setBatteryprotectionfeeOut1(BigDecimal batteryprotectionfeeOut1) {
		this.batteryprotectionfeeOut1 = batteryprotectionfeeOut1;
	}
	public BigDecimal getCurrentTowerShareSumPriceOut1() {
		return currentTowerShareSumPriceOut1;
	}
	public void setCurrentTowerShareSumPriceOut1(
			BigDecimal currentTowerShareSumPriceOut1) {
		this.currentTowerShareSumPriceOut1 = currentTowerShareSumPriceOut1;
	}
	public BigDecimal getCurrentRoomShareSumPriceOut1() {
		return currentRoomShareSumPriceOut1;
	}
	public void setCurrentRoomShareSumPriceOut1(
			BigDecimal currentRoomShareSumPriceOut1) {
		this.currentRoomShareSumPriceOut1 = currentRoomShareSumPriceOut1;
	}
	public BigDecimal getCurrentSupportingShareSumPriceOut1() {
		return currentSupportingShareSumPriceOut1;
	}
	public void setCurrentSupportingShareSumPriceOut1(
			BigDecimal currentSupportingShareSumPriceOut1) {
		this.currentSupportingShareSumPriceOut1 = currentSupportingShareSumPriceOut1;
	}
	public BigDecimal getBbuOnRoomFeeBack1() {
		return bbuOnRoomFeeBack1;
	}
	public void setBbuOnRoomFeeBack1(BigDecimal bbuOnRoomFeeBack1) {
		this.bbuOnRoomFeeBack1 = bbuOnRoomFeeBack1;
	}
	public BigDecimal getCurrentManagerFeeShareSumPriceOut1() {
		return currentManagerFeeShareSumPriceOut1;
	}
	public void setCurrentManagerFeeShareSumPriceOut1(
			BigDecimal currentManagerFeeShareSumPriceOut1) {
		this.currentManagerFeeShareSumPriceOut1 = currentManagerFeeShareSumPriceOut1;
	}
	public BigDecimal getCurrentPraceFeeShareSumPriceOut1() {
		return currentPraceFeeShareSumPriceOut1;
	}
	public void setCurrentPraceFeeShareSumPriceOut1(
			BigDecimal currentPraceFeeShareSumPriceOut1) {
		this.currentPraceFeeShareSumPriceOut1 = currentPraceFeeShareSumPriceOut1;
	}
	public BigDecimal getCurrentPowerInFeeShareSumPriceOut1() {
		return currentPowerInFeeShareSumPriceOut1;
	}
	public void setCurrentPowerInFeeShareSumPriceOut1(
			BigDecimal currentPowerInFeeShareSumPriceOut1) {
		this.currentPowerInFeeShareSumPriceOut1 = currentPowerInFeeShareSumPriceOut1;
	}
	public BigDecimal getWlanFeeOut1() {
		return wlanFeeOut1;
	}
	public void setWlanFeeOut1(BigDecimal wlanFeeOut1) {
		this.wlanFeeOut1 = wlanFeeOut1;
	}
	public BigDecimal getMicrowaveFeeOut1() {
		return microwaveFeeOut1;
	}
	public void setMicrowaveFeeOut1(BigDecimal microwaveFeeOut1) {
		this.microwaveFeeOut1 = microwaveFeeOut1;
	}
	public BigDecimal getOtherFee1Out1() {
		return otherFee1Out1;
	}
	public void setOtherFee1Out1(BigDecimal otherFee1Out1) {
		this.otherFee1Out1 = otherFee1Out1;
	}
	public BigDecimal getTotalAmountMonthOut1() {
		return totalAmountMonthOut1;
	}
	public void setTotalAmountMonthOut1(BigDecimal totalAmountMonthOut1) {
		this.totalAmountMonthOut1 = totalAmountMonthOut1;
	}
	public BigDecimal getElecFeeOut1() {
		return elecFeeOut1;
	}
	public void setElecFeeOut1(BigDecimal elecFeeOut1) {
		this.elecFeeOut1 = elecFeeOut1;
	}
	public SysRegionVO getSysRegionVO() {
		return sysRegionVO;
	}
	public void setSysRegionVO(SysRegionVO sysRegionVO) {
		this.sysRegionVO = sysRegionVO;
	}
	public Integer getConfirmState() {
		return confirmState;
	}

	public void setConfirmState(Integer confirmState) {
		this.confirmState = confirmState;
	}
	
}
