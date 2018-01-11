package com.xunge.model.towerrent.settlement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.xunge.core.util.SysUUID;
/**
 * 移动账单实体
 * @author wangz
 *
 */
public class MobileBillVO implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MobileBillVO() {
		this.towerbillbalanceId = SysUUID.generator();
	}
	public MobileBillVO(String updateUserId) {
		this.towerbillbalanceId = SysUUID.generator();
		this.updateTime=new Date();
		this.updateUserId=updateUserId;
	}

	private String towerbillbalanceId;

    private String resourcesTypeId;

    private String accountPeroid;

    private String dataType;

    private String businessConfirmNumber;

    private String operator;

    private String operatorRegId;

    private String agreeAreaName;

    private String towerStationAreaName;

    private String towerStationCode;

    private String towerStationName;

    private String agreeBillCode;

    private String serviceAttribute;

    private Date startDate;

    private String productTypeId;

    private String roomTypeId;

    private String oilGenerateElectricMethodId;

    private BigDecimal usePowerServiceFeeOut;

    private BigDecimal oilGeneratorElectricFeeDeductible;

    private BigDecimal oilGeneratorElectricFee;

    private BigDecimal oilGeneratorElectricFeeWaitReturn;

    private BigDecimal hightLevelFeeOut;

    private BigDecimal hightLevelFeeDeductible;

    private BigDecimal hightLevelFee;

    private BigDecimal hightLevelFeeWaitReturn;

    private BigDecimal batteryprotectionfeeOut;

    private BigDecimal batteryprotectionfeeDeductible;

    private BigDecimal batteryprotectionfee;

    private BigDecimal batteryprotectionfeeWaitReturn;

    private Long unitProductNumber1;

    private String height1;

    private Boolean ifbbuOnRoom1;

    private BigDecimal otherDis1;

    private BigDecimal towerPrice1;

    private Long unitProductNumber2;

    private String height2;

    private Boolean ifbbuOnRoom2;

    private BigDecimal otherDis2;

    private BigDecimal towerPrice2;

    private Long unitProductNumber3;

    private String height3;

    private Boolean ifbbuOnRoom3;

    private BigDecimal otherDis3;

    private BigDecimal towerPrice3;

    private Integer currentTowerShareNum;

    private Date towerOperatorStartdate1;

    private BigDecimal towerSupportingShareDis1;

    private Date towerOperatorStartdate2;

    private BigDecimal towerSupportingShareDis2;

    private BigDecimal currentTowerShareSumPriceOut;

    private BigDecimal currentTowerShareSumPriceDeductible;

    private BigDecimal currentTowerShareSumPrice;

    private BigDecimal currentTowerShareSumPriceWaitReturn;

    private BigDecimal roomBasePrice1;

    private BigDecimal roomBasePrice2;

    private BigDecimal roomBasePrice3;

    private Integer currentRoomShareNum;

    private Date roomOperatorStartdate1;

    private BigDecimal roomSupportingShareDis1;

    private Date roomOperatorStartdate2;

    private BigDecimal roomSupportingShareDis2;

    private BigDecimal currentRoomShareSumPriceOut;

    private BigDecimal currentRoomShareSumPriceDeductible;

    private BigDecimal currentRoomShareSumPrice;

    private BigDecimal currentRoomShareSumPriceWaitReturn;

    private BigDecimal supportingBasePrice1;

    private BigDecimal supportingBasePrice2;

    private BigDecimal supportingBasePrice3;

    private Integer currentSupportingShareNum;

    private Date supportingOperatorStartdate1;

    private BigDecimal supportingShareDis1;

    private Date supportingOperatorStartdate2;

    private BigDecimal supportingShareDis2;

    private BigDecimal currentSupportingShareSumPriceOut;

    private BigDecimal currentSupportingShareSumPriceDeductible;

    private BigDecimal currentSupportingShareSumPrice;

    private BigDecimal currentSupportingShareSumPriceWaitReturn;

    private BigDecimal bbuOnRoomFeeBack;

    private BigDecimal bbuOnRoomFeeDeductible;

    private BigDecimal bbuOnRoomFee;

    private BigDecimal bbuOnRoomFeeWaitReturn;

    private BigDecimal managerFee1;

    private BigDecimal managerFee2;

    private BigDecimal managerFee3;

    private Integer managerFeeShareNum;

    private Date managerFeeOperatorStartdate1;

    private BigDecimal managerFeeShareDis1;

    private Date managerFeeOperatorStartdate2;

    private BigDecimal managerFeeShareDis2;

    private BigDecimal currentManagerFeeShareSumPriceOut;

    private BigDecimal currentManagerFeeShareSumPriceDeductible;

    private BigDecimal currentManagerFeeShareSumPrice;

    private BigDecimal currentManagerFeeShareSumPriceWaitReturn;

    private BigDecimal praceFee;

    private Integer praceFeeShareNum;

    private Date praceFeeOperatorStartdate1;

    private BigDecimal praceFeeshareDis1;

    private Date praceFeeOperatorStartdate2;

    private BigDecimal praceFeeShareDis2;

    private BigDecimal currentPraceFeeShareSumPriceOut;

    private BigDecimal currentPraceFeeShareSumPriceDeductible;

    private BigDecimal currentPraceFeeShareSumPrice;

    private BigDecimal currentPraceFeeShareSumPriceWaitReturn;

    private BigDecimal powerInFee;

    private Integer powerInFeeShareNum;

    private Date powerInFeeOperatorStartdate1;

    private BigDecimal powerInFeeShareDis1;

    private Date powerInFeeOperatorStartdate2;

    private BigDecimal powerInFeeShareDis2;

    private BigDecimal currentPowerInFeeShareSumPriceOut;

    private BigDecimal currentPowerInFeeShareSumPriceDeductible;

    private BigDecimal currentPowerInFeeShareSumPrice;

    private BigDecimal currentPowerInFeeShareSumPriceWaitDeductible;

    private BigDecimal wlanFeeOut;

    private BigDecimal wlanFeeDeductible;

    private BigDecimal wlanFee;

    private BigDecimal wlanFeeWaitReturn;

    private BigDecimal microwaveFeeOut;

    private BigDecimal microwaveFeeDeductible;

    private BigDecimal microwaveFee;

    private BigDecimal microwaveFeeWaitReturn;

    private BigDecimal otherFee1Out;

    private BigDecimal otherFee1Deductible;

    private BigDecimal otherFee1;

    private BigDecimal otherFee1WaitReturn;

    private BigDecimal totalAmountMonthOut;

    private BigDecimal totalAmountMonthDeductible;

    private BigDecimal totalAmountMonth;

    private BigDecimal totalAmountMonthWaitReturn;

    private BigDecimal ifServicefeeChange;

    private BigDecimal servicefeeChange;

    private BigDecimal confirmState;

    private BigDecimal elecFeeOut;

    private BigDecimal elecFeeDeductible;

    private BigDecimal elecFee;

    private BigDecimal elecFeeWaitReturn;

    private BigDecimal usePowerServiceFeeOut2;

    private BigDecimal oilGeneratorElectricFeeDeductible2;

    private BigDecimal oilGeneratorElectricFee2;

    private BigDecimal oilGeneratorElectricFeeWaitReturn2;

    private BigDecimal totalActualAmount;

    private String adjustmentFeeType;

    private BigDecimal adjustmentFee;

    private BigDecimal afterAdjustmentFee;

    private String adjustmentFeeNote;

    private BigDecimal disputeFee;

    private Boolean locaOperator;

    private Integer reimbursementState;

    private Integer checkState;

    private String orderProp;

    private String rightProp;

    private String oriRight;

    private Integer resCompare;

    private String createUserId;		// 创建用户编码
    
    private Date createTime;			// 创建时间

    private String updateUserId;		// 更新用户编码
    
	private Date updateTime;			// 修改时间

    public String getTowerbillbalanceId() {
        return towerbillbalanceId;
    }

    public void setTowerbillbalanceId(String towerbillbalanceId) {
        this.towerbillbalanceId = towerbillbalanceId == null ? null : towerbillbalanceId.trim();
    }

    public String getResourcesTypeId() {
        return resourcesTypeId;
    }

    public void setResourcesTypeId(String resourcesTypeId) {
        this.resourcesTypeId = resourcesTypeId == null ? null : resourcesTypeId.trim();
    }

    public String getAccountPeroid() {
        return accountPeroid;
    }

    public void setAccountPeroid(String accountPeroid) {
        this.accountPeroid = accountPeroid == null ? null : accountPeroid.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getBusinessConfirmNumber() {
        return businessConfirmNumber;
    }

    public void setBusinessConfirmNumber(String businessConfirmNumber) {
        this.businessConfirmNumber = businessConfirmNumber == null ? null : businessConfirmNumber.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getOperatorRegId() {
        return operatorRegId;
    }

    public void setOperatorRegId(String operatorRegId) {
        this.operatorRegId = operatorRegId == null ? null : operatorRegId.trim();
    }

    public String getAgreeAreaName() {
        return agreeAreaName;
    }

    public void setAgreeAreaName(String agreeAreaName) {
        this.agreeAreaName = agreeAreaName == null ? null : agreeAreaName.trim();
    }

    public String getTowerStationAreaName() {
        return towerStationAreaName;
    }

    public void setTowerStationAreaName(String towerStationAreaName) {
        this.towerStationAreaName = towerStationAreaName == null ? null : towerStationAreaName.trim();
    }

    public String getTowerStationCode() {
        return towerStationCode;
    }

    public void setTowerStationCode(String towerStationCode) {
        this.towerStationCode = towerStationCode == null ? null : towerStationCode.trim();
    }

    public String getTowerStationName() {
        return towerStationName;
    }

    public void setTowerStationName(String towerStationName) {
        this.towerStationName = towerStationName == null ? null : towerStationName.trim();
    }

    public String getAgreeBillCode() {
        return agreeBillCode;
    }

    public void setAgreeBillCode(String agreeBillCode) {
        this.agreeBillCode = agreeBillCode == null ? null : agreeBillCode.trim();
    }

    public String getServiceAttribute() {
        return serviceAttribute;
    }

    public void setServiceAttribute(String serviceAttribute) {
        this.serviceAttribute = serviceAttribute == null ? null : serviceAttribute.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId == null ? null : productTypeId.trim();
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId == null ? null : roomTypeId.trim();
    }

    public String getOilGenerateElectricMethodId() {
        return oilGenerateElectricMethodId;
    }

    public void setOilGenerateElectricMethodId(String oilGenerateElectricMethodId) {
        this.oilGenerateElectricMethodId = oilGenerateElectricMethodId == null ? null : oilGenerateElectricMethodId.trim();
    }

    public BigDecimal getUsePowerServiceFeeOut() {
        return usePowerServiceFeeOut;
    }

    public void setUsePowerServiceFeeOut(BigDecimal usePowerServiceFeeOut) {
        this.usePowerServiceFeeOut = usePowerServiceFeeOut;
    }

    public BigDecimal getOilGeneratorElectricFeeDeductible() {
        return oilGeneratorElectricFeeDeductible;
    }

    public void setOilGeneratorElectricFeeDeductible(BigDecimal oilGeneratorElectricFeeDeductible) {
        this.oilGeneratorElectricFeeDeductible = oilGeneratorElectricFeeDeductible;
    }

    public BigDecimal getOilGeneratorElectricFee() {
        return oilGeneratorElectricFee;
    }

    public void setOilGeneratorElectricFee(BigDecimal oilGeneratorElectricFee) {
        this.oilGeneratorElectricFee = oilGeneratorElectricFee;
    }

    public BigDecimal getOilGeneratorElectricFeeWaitReturn() {
        return oilGeneratorElectricFeeWaitReturn;
    }

    public void setOilGeneratorElectricFeeWaitReturn(BigDecimal oilGeneratorElectricFeeWaitReturn) {
        this.oilGeneratorElectricFeeWaitReturn = oilGeneratorElectricFeeWaitReturn;
    }

    public BigDecimal getHightLevelFeeOut() {
        return hightLevelFeeOut;
    }

    public void setHightLevelFeeOut(BigDecimal hightLevelFeeOut) {
        this.hightLevelFeeOut = hightLevelFeeOut;
    }

    public BigDecimal getHightLevelFeeDeductible() {
        return hightLevelFeeDeductible;
    }

    public void setHightLevelFeeDeductible(BigDecimal hightLevelFeeDeductible) {
        this.hightLevelFeeDeductible = hightLevelFeeDeductible;
    }

    public BigDecimal getHightLevelFee() {
        return hightLevelFee;
    }

    public void setHightLevelFee(BigDecimal hightLevelFee) {
        this.hightLevelFee = hightLevelFee;
    }

    public BigDecimal getHightLevelFeeWaitReturn() {
        return hightLevelFeeWaitReturn;
    }

    public void setHightLevelFeeWaitReturn(BigDecimal hightLevelFeeWaitReturn) {
        this.hightLevelFeeWaitReturn = hightLevelFeeWaitReturn;
    }

    public BigDecimal getBatteryprotectionfeeOut() {
        return batteryprotectionfeeOut;
    }

    public void setBatteryprotectionfeeOut(BigDecimal batteryprotectionfeeOut) {
        this.batteryprotectionfeeOut = batteryprotectionfeeOut;
    }

    public BigDecimal getBatteryprotectionfeeDeductible() {
        return batteryprotectionfeeDeductible;
    }

    public void setBatteryprotectionfeeDeductible(BigDecimal batteryprotectionfeeDeductible) {
        this.batteryprotectionfeeDeductible = batteryprotectionfeeDeductible;
    }

    public BigDecimal getBatteryprotectionfee() {
        return batteryprotectionfee;
    }

    public void setBatteryprotectionfee(BigDecimal batteryprotectionfee) {
        this.batteryprotectionfee = batteryprotectionfee;
    }

    public BigDecimal getBatteryprotectionfeeWaitReturn() {
        return batteryprotectionfeeWaitReturn;
    }

    public void setBatteryprotectionfeeWaitReturn(BigDecimal batteryprotectionfeeWaitReturn) {
        this.batteryprotectionfeeWaitReturn = batteryprotectionfeeWaitReturn;
    }

    public Long getUnitProductNumber1() {
        return unitProductNumber1;
    }

    public void setUnitProductNumber1(Long unitProductNumber1) {
        this.unitProductNumber1 = unitProductNumber1;
    }

    public String getHeight1() {
        return height1;
    }

    public void setHeight1(String height1) {
        this.height1 = height1 == null ? null : height1.trim();
    }

    public Boolean getIfbbuOnRoom1() {
        return ifbbuOnRoom1;
    }

    public void setIfbbuOnRoom1(Boolean ifbbuOnRoom1) {
        this.ifbbuOnRoom1 = ifbbuOnRoom1;
    }

    public BigDecimal getOtherDis1() {
        return otherDis1;
    }

    public void setOtherDis1(BigDecimal otherDis1) {
        this.otherDis1 = otherDis1;
    }

    public BigDecimal getTowerPrice1() {
        return towerPrice1;
    }

    public void setTowerPrice1(BigDecimal towerPrice1) {
        this.towerPrice1 = towerPrice1;
    }

    public Long getUnitProductNumber2() {
        return unitProductNumber2;
    }

    public void setUnitProductNumber2(Long unitProductNumber2) {
        this.unitProductNumber2 = unitProductNumber2;
    }

    public String getHeight2() {
        return height2;
    }

    public void setHeight2(String height2) {
        this.height2 = height2 == null ? null : height2.trim();
    }

    public Boolean getIfbbuOnRoom2() {
        return ifbbuOnRoom2;
    }

    public void setIfbbuOnRoom2(Boolean ifbbuOnRoom2) {
        this.ifbbuOnRoom2 = ifbbuOnRoom2;
    }

    public BigDecimal getOtherDis2() {
        return otherDis2;
    }

    public void setOtherDis2(BigDecimal otherDis2) {
        this.otherDis2 = otherDis2;
    }

    public BigDecimal getTowerPrice2() {
        return towerPrice2;
    }

    public void setTowerPrice2(BigDecimal towerPrice2) {
        this.towerPrice2 = towerPrice2;
    }

    public Long getUnitProductNumber3() {
        return unitProductNumber3;
    }

    public void setUnitProductNumber3(Long unitProductNumber3) {
        this.unitProductNumber3 = unitProductNumber3;
    }

    public String getHeight3() {
        return height3;
    }

    public void setHeight3(String height3) {
        this.height3 = height3 == null ? null : height3.trim();
    }

    public Boolean getIfbbuOnRoom3() {
        return ifbbuOnRoom3;
    }

    public void setIfbbuOnRoom3(Boolean ifbbuOnRoom3) {
        this.ifbbuOnRoom3 = ifbbuOnRoom3;
    }

    public BigDecimal getOtherDis3() {
        return otherDis3;
    }

    public void setOtherDis3(BigDecimal otherDis3) {
        this.otherDis3 = otherDis3;
    }

    public BigDecimal getTowerPrice3() {
        return towerPrice3;
    }

    public void setTowerPrice3(BigDecimal towerPrice3) {
        this.towerPrice3 = towerPrice3;
    }

    public Integer getCurrentTowerShareNum() {
        return currentTowerShareNum;
    }

    public void setCurrentTowerShareNum(Integer currentTowerShareNum) {
        this.currentTowerShareNum = currentTowerShareNum;
    }

    public Date getTowerOperatorStartdate1() {
        return towerOperatorStartdate1;
    }

    public void setTowerOperatorStartdate1(Date towerOperatorStartdate1) {
        this.towerOperatorStartdate1 = towerOperatorStartdate1;
    }

    public BigDecimal getTowerSupportingShareDis1() {
        return towerSupportingShareDis1;
    }

    public void setTowerSupportingShareDis1(BigDecimal towerSupportingShareDis1) {
        this.towerSupportingShareDis1 = towerSupportingShareDis1;
    }

    public Date getTowerOperatorStartdate2() {
        return towerOperatorStartdate2;
    }

    public void setTowerOperatorStartdate2(Date towerOperatorStartdate2) {
        this.towerOperatorStartdate2 = towerOperatorStartdate2;
    }

    public BigDecimal getTowerSupportingShareDis2() {
        return towerSupportingShareDis2;
    }

    public void setTowerSupportingShareDis2(BigDecimal towerSupportingShareDis2) {
        this.towerSupportingShareDis2 = towerSupportingShareDis2;
    }

    public BigDecimal getCurrentTowerShareSumPriceOut() {
        return currentTowerShareSumPriceOut;
    }

    public void setCurrentTowerShareSumPriceOut(BigDecimal currentTowerShareSumPriceOut) {
        this.currentTowerShareSumPriceOut = currentTowerShareSumPriceOut;
    }

    public BigDecimal getCurrentTowerShareSumPriceDeductible() {
        return currentTowerShareSumPriceDeductible;
    }

    public void setCurrentTowerShareSumPriceDeductible(BigDecimal currentTowerShareSumPriceDeductible) {
        this.currentTowerShareSumPriceDeductible = currentTowerShareSumPriceDeductible;
    }

    public BigDecimal getCurrentTowerShareSumPrice() {
        return currentTowerShareSumPrice;
    }

    public void setCurrentTowerShareSumPrice(BigDecimal currentTowerShareSumPrice) {
        this.currentTowerShareSumPrice = currentTowerShareSumPrice;
    }

    public BigDecimal getCurrentTowerShareSumPriceWaitReturn() {
        return currentTowerShareSumPriceWaitReturn;
    }

    public void setCurrentTowerShareSumPriceWaitReturn(BigDecimal currentTowerShareSumPriceWaitReturn) {
        this.currentTowerShareSumPriceWaitReturn = currentTowerShareSumPriceWaitReturn;
    }

    public BigDecimal getRoomBasePrice1() {
        return roomBasePrice1;
    }

    public void setRoomBasePrice1(BigDecimal roomBasePrice1) {
        this.roomBasePrice1 = roomBasePrice1;
    }

    public BigDecimal getRoomBasePrice2() {
        return roomBasePrice2;
    }

    public void setRoomBasePrice2(BigDecimal roomBasePrice2) {
        this.roomBasePrice2 = roomBasePrice2;
    }

    public BigDecimal getRoomBasePrice3() {
        return roomBasePrice3;
    }

    public void setRoomBasePrice3(BigDecimal roomBasePrice3) {
        this.roomBasePrice3 = roomBasePrice3;
    }

    public Integer getCurrentRoomShareNum() {
        return currentRoomShareNum;
    }

    public void setCurrentRoomShareNum(Integer currentRoomShareNum) {
        this.currentRoomShareNum = currentRoomShareNum;
    }

    public Date getRoomOperatorStartdate1() {
        return roomOperatorStartdate1;
    }

    public void setRoomOperatorStartdate1(Date roomOperatorStartdate1) {
        this.roomOperatorStartdate1 = roomOperatorStartdate1;
    }

    public BigDecimal getRoomSupportingShareDis1() {
        return roomSupportingShareDis1;
    }

    public void setRoomSupportingShareDis1(BigDecimal roomSupportingShareDis1) {
        this.roomSupportingShareDis1 = roomSupportingShareDis1;
    }

    public Date getRoomOperatorStartdate2() {
        return roomOperatorStartdate2;
    }

    public void setRoomOperatorStartdate2(Date roomOperatorStartdate2) {
        this.roomOperatorStartdate2 = roomOperatorStartdate2;
    }

    public BigDecimal getRoomSupportingShareDis2() {
        return roomSupportingShareDis2;
    }

    public void setRoomSupportingShareDis2(BigDecimal roomSupportingShareDis2) {
        this.roomSupportingShareDis2 = roomSupportingShareDis2;
    }

    public BigDecimal getCurrentRoomShareSumPriceOut() {
        return currentRoomShareSumPriceOut;
    }

    public void setCurrentRoomShareSumPriceOut(BigDecimal currentRoomShareSumPriceOut) {
        this.currentRoomShareSumPriceOut = currentRoomShareSumPriceOut;
    }

    public BigDecimal getCurrentRoomShareSumPriceDeductible() {
        return currentRoomShareSumPriceDeductible;
    }

    public void setCurrentRoomShareSumPriceDeductible(BigDecimal currentRoomShareSumPriceDeductible) {
        this.currentRoomShareSumPriceDeductible = currentRoomShareSumPriceDeductible;
    }

    public BigDecimal getCurrentRoomShareSumPrice() {
        return currentRoomShareSumPrice;
    }

    public void setCurrentRoomShareSumPrice(BigDecimal currentRoomShareSumPrice) {
        this.currentRoomShareSumPrice = currentRoomShareSumPrice;
    }

    public BigDecimal getCurrentRoomShareSumPriceWaitReturn() {
        return currentRoomShareSumPriceWaitReturn;
    }

    public void setCurrentRoomShareSumPriceWaitReturn(BigDecimal currentRoomShareSumPriceWaitReturn) {
        this.currentRoomShareSumPriceWaitReturn = currentRoomShareSumPriceWaitReturn;
    }

    public BigDecimal getSupportingBasePrice1() {
        return supportingBasePrice1;
    }

    public void setSupportingBasePrice1(BigDecimal supportingBasePrice1) {
        this.supportingBasePrice1 = supportingBasePrice1;
    }

    public BigDecimal getSupportingBasePrice2() {
        return supportingBasePrice2;
    }

    public void setSupportingBasePrice2(BigDecimal supportingBasePrice2) {
        this.supportingBasePrice2 = supportingBasePrice2;
    }

    public BigDecimal getSupportingBasePrice3() {
        return supportingBasePrice3;
    }

    public void setSupportingBasePrice3(BigDecimal supportingBasePrice3) {
        this.supportingBasePrice3 = supportingBasePrice3;
    }

    public Integer getCurrentSupportingShareNum() {
        return currentSupportingShareNum;
    }

    public void setCurrentSupportingShareNum(Integer currentSupportingShareNum) {
        this.currentSupportingShareNum = currentSupportingShareNum;
    }

    public Date getSupportingOperatorStartdate1() {
        return supportingOperatorStartdate1;
    }

    public void setSupportingOperatorStartdate1(Date supportingOperatorStartdate1) {
        this.supportingOperatorStartdate1 = supportingOperatorStartdate1;
    }

    public BigDecimal getSupportingShareDis1() {
        return supportingShareDis1;
    }

    public void setSupportingShareDis1(BigDecimal supportingShareDis1) {
        this.supportingShareDis1 = supportingShareDis1;
    }

    public Date getSupportingOperatorStartdate2() {
        return supportingOperatorStartdate2;
    }

    public void setSupportingOperatorStartdate2(Date supportingOperatorStartdate2) {
        this.supportingOperatorStartdate2 = supportingOperatorStartdate2;
    }

    public BigDecimal getSupportingShareDis2() {
        return supportingShareDis2;
    }

    public void setSupportingShareDis2(BigDecimal supportingShareDis2) {
        this.supportingShareDis2 = supportingShareDis2;
    }

    public BigDecimal getCurrentSupportingShareSumPriceOut() {
        return currentSupportingShareSumPriceOut;
    }

    public void setCurrentSupportingShareSumPriceOut(BigDecimal currentSupportingShareSumPriceOut) {
        this.currentSupportingShareSumPriceOut = currentSupportingShareSumPriceOut;
    }

    public BigDecimal getCurrentSupportingShareSumPriceDeductible() {
        return currentSupportingShareSumPriceDeductible;
    }

    public void setCurrentSupportingShareSumPriceDeductible(BigDecimal currentSupportingShareSumPriceDeductible) {
        this.currentSupportingShareSumPriceDeductible = currentSupportingShareSumPriceDeductible;
    }

    public BigDecimal getCurrentSupportingShareSumPrice() {
        return currentSupportingShareSumPrice;
    }

    public void setCurrentSupportingShareSumPrice(BigDecimal currentSupportingShareSumPrice) {
        this.currentSupportingShareSumPrice = currentSupportingShareSumPrice;
    }

    public BigDecimal getCurrentSupportingShareSumPriceWaitReturn() {
        return currentSupportingShareSumPriceWaitReturn;
    }

    public void setCurrentSupportingShareSumPriceWaitReturn(BigDecimal currentSupportingShareSumPriceWaitReturn) {
        this.currentSupportingShareSumPriceWaitReturn = currentSupportingShareSumPriceWaitReturn;
    }

    public BigDecimal getBbuOnRoomFeeBack() {
        return bbuOnRoomFeeBack;
    }

    public void setBbuOnRoomFeeBack(BigDecimal bbuOnRoomFeeBack) {
        this.bbuOnRoomFeeBack = bbuOnRoomFeeBack;
    }

    public BigDecimal getBbuOnRoomFeeDeductible() {
        return bbuOnRoomFeeDeductible;
    }

    public void setBbuOnRoomFeeDeductible(BigDecimal bbuOnRoomFeeDeductible) {
        this.bbuOnRoomFeeDeductible = bbuOnRoomFeeDeductible;
    }

    public BigDecimal getBbuOnRoomFee() {
        return bbuOnRoomFee;
    }

    public void setBbuOnRoomFee(BigDecimal bbuOnRoomFee) {
        this.bbuOnRoomFee = bbuOnRoomFee;
    }

    public BigDecimal getBbuOnRoomFeeWaitReturn() {
        return bbuOnRoomFeeWaitReturn;
    }

    public void setBbuOnRoomFeeWaitReturn(BigDecimal bbuOnRoomFeeWaitReturn) {
        this.bbuOnRoomFeeWaitReturn = bbuOnRoomFeeWaitReturn;
    }

    public BigDecimal getManagerFee1() {
        return managerFee1;
    }

    public void setManagerFee1(BigDecimal managerFee1) {
        this.managerFee1 = managerFee1;
    }

    public BigDecimal getManagerFee2() {
        return managerFee2;
    }

    public void setManagerFee2(BigDecimal managerFee2) {
        this.managerFee2 = managerFee2;
    }

    public BigDecimal getManagerFee3() {
        return managerFee3;
    }

    public void setManagerFee3(BigDecimal managerFee3) {
        this.managerFee3 = managerFee3;
    }

    public Integer getManagerFeeShareNum() {
        return managerFeeShareNum;
    }

    public void setManagerFeeShareNum(Integer managerFeeShareNum) {
        this.managerFeeShareNum = managerFeeShareNum;
    }

    public Date getManagerFeeOperatorStartdate1() {
        return managerFeeOperatorStartdate1;
    }

    public void setManagerFeeOperatorStartdate1(Date managerFeeOperatorStartdate1) {
        this.managerFeeOperatorStartdate1 = managerFeeOperatorStartdate1;
    }

    public BigDecimal getManagerFeeShareDis1() {
        return managerFeeShareDis1;
    }

    public void setManagerFeeShareDis1(BigDecimal managerFeeShareDis1) {
        this.managerFeeShareDis1 = managerFeeShareDis1;
    }

    public Date getManagerFeeOperatorStartdate2() {
        return managerFeeOperatorStartdate2;
    }

    public void setManagerFeeOperatorStartdate2(Date managerFeeOperatorStartdate2) {
        this.managerFeeOperatorStartdate2 = managerFeeOperatorStartdate2;
    }

    public BigDecimal getManagerFeeShareDis2() {
        return managerFeeShareDis2;
    }

    public void setManagerFeeShareDis2(BigDecimal managerFeeShareDis2) {
        this.managerFeeShareDis2 = managerFeeShareDis2;
    }

    public BigDecimal getCurrentManagerFeeShareSumPriceOut() {
        return currentManagerFeeShareSumPriceOut;
    }

    public void setCurrentManagerFeeShareSumPriceOut(BigDecimal currentManagerFeeShareSumPriceOut) {
        this.currentManagerFeeShareSumPriceOut = currentManagerFeeShareSumPriceOut;
    }

    public BigDecimal getCurrentManagerFeeShareSumPriceDeductible() {
        return currentManagerFeeShareSumPriceDeductible;
    }

    public void setCurrentManagerFeeShareSumPriceDeductible(BigDecimal currentManagerFeeShareSumPriceDeductible) {
        this.currentManagerFeeShareSumPriceDeductible = currentManagerFeeShareSumPriceDeductible;
    }

    public BigDecimal getCurrentManagerFeeShareSumPrice() {
        return currentManagerFeeShareSumPrice;
    }

    public void setCurrentManagerFeeShareSumPrice(BigDecimal currentManagerFeeShareSumPrice) {
        this.currentManagerFeeShareSumPrice = currentManagerFeeShareSumPrice;
    }

    public BigDecimal getCurrentManagerFeeShareSumPriceWaitReturn() {
        return currentManagerFeeShareSumPriceWaitReturn;
    }

    public void setCurrentManagerFeeShareSumPriceWaitReturn(BigDecimal currentManagerFeeShareSumPriceWaitReturn) {
        this.currentManagerFeeShareSumPriceWaitReturn = currentManagerFeeShareSumPriceWaitReturn;
    }

    public BigDecimal getPraceFee() {
        return praceFee;
    }

    public void setPraceFee(BigDecimal praceFee) {
        this.praceFee = praceFee;
    }

    public Integer getPraceFeeShareNum() {
        return praceFeeShareNum;
    }

    public void setPraceFeeShareNum(Integer praceFeeShareNum) {
        this.praceFeeShareNum = praceFeeShareNum;
    }

    public Date getPraceFeeOperatorStartdate1() {
        return praceFeeOperatorStartdate1;
    }

    public void setPraceFeeOperatorStartdate1(Date praceFeeOperatorStartdate1) {
        this.praceFeeOperatorStartdate1 = praceFeeOperatorStartdate1;
    }

    public BigDecimal getPraceFeeshareDis1() {
        return praceFeeshareDis1;
    }

    public void setPraceFeeshareDis1(BigDecimal praceFeeshareDis1) {
        this.praceFeeshareDis1 = praceFeeshareDis1;
    }

    public Date getPraceFeeOperatorStartdate2() {
        return praceFeeOperatorStartdate2;
    }

    public void setPraceFeeOperatorStartdate2(Date praceFeeOperatorStartdate2) {
        this.praceFeeOperatorStartdate2 = praceFeeOperatorStartdate2;
    }

    public BigDecimal getPraceFeeShareDis2() {
        return praceFeeShareDis2;
    }

    public void setPraceFeeShareDis2(BigDecimal praceFeeShareDis2) {
        this.praceFeeShareDis2 = praceFeeShareDis2;
    }

    public BigDecimal getCurrentPraceFeeShareSumPriceOut() {
        return currentPraceFeeShareSumPriceOut;
    }

    public void setCurrentPraceFeeShareSumPriceOut(BigDecimal currentPraceFeeShareSumPriceOut) {
        this.currentPraceFeeShareSumPriceOut = currentPraceFeeShareSumPriceOut;
    }

    public BigDecimal getCurrentPraceFeeShareSumPriceDeductible() {
        return currentPraceFeeShareSumPriceDeductible;
    }

    public void setCurrentPraceFeeShareSumPriceDeductible(BigDecimal currentPraceFeeShareSumPriceDeductible) {
        this.currentPraceFeeShareSumPriceDeductible = currentPraceFeeShareSumPriceDeductible;
    }

    public BigDecimal getCurrentPraceFeeShareSumPrice() {
        return currentPraceFeeShareSumPrice;
    }

    public void setCurrentPraceFeeShareSumPrice(BigDecimal currentPraceFeeShareSumPrice) {
        this.currentPraceFeeShareSumPrice = currentPraceFeeShareSumPrice;
    }

    public BigDecimal getCurrentPraceFeeShareSumPriceWaitReturn() {
        return currentPraceFeeShareSumPriceWaitReturn;
    }

    public void setCurrentPraceFeeShareSumPriceWaitReturn(BigDecimal currentPraceFeeShareSumPriceWaitReturn) {
        this.currentPraceFeeShareSumPriceWaitReturn = currentPraceFeeShareSumPriceWaitReturn;
    }

    public BigDecimal getPowerInFee() {
        return powerInFee;
    }

    public void setPowerInFee(BigDecimal powerInFee) {
        this.powerInFee = powerInFee;
    }

    public Integer getPowerInFeeShareNum() {
        return powerInFeeShareNum;
    }

    public void setPowerInFeeShareNum(Integer powerInFeeShareNum) {
        this.powerInFeeShareNum = powerInFeeShareNum;
    }

    public Date getPowerInFeeOperatorStartdate1() {
        return powerInFeeOperatorStartdate1;
    }

    public void setPowerInFeeOperatorStartdate1(Date powerInFeeOperatorStartdate1) {
        this.powerInFeeOperatorStartdate1 = powerInFeeOperatorStartdate1;
    }

    public BigDecimal getPowerInFeeShareDis1() {
        return powerInFeeShareDis1;
    }

    public void setPowerInFeeShareDis1(BigDecimal powerInFeeShareDis1) {
        this.powerInFeeShareDis1 = powerInFeeShareDis1;
    }

    public Date getPowerInFeeOperatorStartdate2() {
        return powerInFeeOperatorStartdate2;
    }

    public void setPowerInFeeOperatorStartdate2(Date powerInFeeOperatorStartdate2) {
        this.powerInFeeOperatorStartdate2 = powerInFeeOperatorStartdate2;
    }

    public BigDecimal getPowerInFeeShareDis2() {
        return powerInFeeShareDis2;
    }

    public void setPowerInFeeShareDis2(BigDecimal powerInFeeShareDis2) {
        this.powerInFeeShareDis2 = powerInFeeShareDis2;
    }

    public BigDecimal getCurrentPowerInFeeShareSumPriceOut() {
        return currentPowerInFeeShareSumPriceOut;
    }

    public void setCurrentPowerInFeeShareSumPriceOut(BigDecimal currentPowerInFeeShareSumPriceOut) {
        this.currentPowerInFeeShareSumPriceOut = currentPowerInFeeShareSumPriceOut;
    }

    public BigDecimal getCurrentPowerInFeeShareSumPriceDeductible() {
        return currentPowerInFeeShareSumPriceDeductible;
    }

    public void setCurrentPowerInFeeShareSumPriceDeductible(BigDecimal currentPowerInFeeShareSumPriceDeductible) {
        this.currentPowerInFeeShareSumPriceDeductible = currentPowerInFeeShareSumPriceDeductible;
    }

    public BigDecimal getCurrentPowerInFeeShareSumPrice() {
        return currentPowerInFeeShareSumPrice;
    }

    public void setCurrentPowerInFeeShareSumPrice(BigDecimal currentPowerInFeeShareSumPrice) {
        this.currentPowerInFeeShareSumPrice = currentPowerInFeeShareSumPrice;
    }

    public BigDecimal getCurrentPowerInFeeShareSumPriceWaitDeductible() {
        return currentPowerInFeeShareSumPriceWaitDeductible;
    }

    public void setCurrentPowerInFeeShareSumPriceWaitDeductible(BigDecimal currentPowerInFeeShareSumPriceWaitDeductible) {
        this.currentPowerInFeeShareSumPriceWaitDeductible = currentPowerInFeeShareSumPriceWaitDeductible;
    }

    public BigDecimal getWlanFeeOut() {
        return wlanFeeOut;
    }

    public void setWlanFeeOut(BigDecimal wlanFeeOut) {
        this.wlanFeeOut = wlanFeeOut;
    }

    public BigDecimal getWlanFeeDeductible() {
        return wlanFeeDeductible;
    }

    public void setWlanFeeDeductible(BigDecimal wlanFeeDeductible) {
        this.wlanFeeDeductible = wlanFeeDeductible;
    }

    public BigDecimal getWlanFee() {
        return wlanFee;
    }

    public void setWlanFee(BigDecimal wlanFee) {
        this.wlanFee = wlanFee;
    }

    public BigDecimal getWlanFeeWaitReturn() {
        return wlanFeeWaitReturn;
    }

    public void setWlanFeeWaitReturn(BigDecimal wlanFeeWaitReturn) {
        this.wlanFeeWaitReturn = wlanFeeWaitReturn;
    }

    public BigDecimal getMicrowaveFeeOut() {
        return microwaveFeeOut;
    }

    public void setMicrowaveFeeOut(BigDecimal microwaveFeeOut) {
        this.microwaveFeeOut = microwaveFeeOut;
    }

    public BigDecimal getMicrowaveFeeDeductible() {
        return microwaveFeeDeductible;
    }

    public void setMicrowaveFeeDeductible(BigDecimal microwaveFeeDeductible) {
        this.microwaveFeeDeductible = microwaveFeeDeductible;
    }

    public BigDecimal getMicrowaveFee() {
        return microwaveFee;
    }

    public void setMicrowaveFee(BigDecimal microwaveFee) {
        this.microwaveFee = microwaveFee;
    }

    public BigDecimal getMicrowaveFeeWaitReturn() {
        return microwaveFeeWaitReturn;
    }

    public void setMicrowaveFeeWaitReturn(BigDecimal microwaveFeeWaitReturn) {
        this.microwaveFeeWaitReturn = microwaveFeeWaitReturn;
    }

    public BigDecimal getOtherFee1Out() {
        return otherFee1Out;
    }

    public void setOtherFee1Out(BigDecimal otherFee1Out) {
        this.otherFee1Out = otherFee1Out;
    }

    public BigDecimal getOtherFee1Deductible() {
        return otherFee1Deductible;
    }

    public void setOtherFee1Deductible(BigDecimal otherFee1Deductible) {
        this.otherFee1Deductible = otherFee1Deductible;
    }

    public BigDecimal getOtherFee1() {
        return otherFee1;
    }

    public void setOtherFee1(BigDecimal otherFee1) {
        this.otherFee1 = otherFee1;
    }

    public BigDecimal getOtherFee1WaitReturn() {
        return otherFee1WaitReturn;
    }

    public void setOtherFee1WaitReturn(BigDecimal otherFee1WaitReturn) {
        this.otherFee1WaitReturn = otherFee1WaitReturn;
    }

    public BigDecimal getTotalAmountMonthOut() {
        return totalAmountMonthOut;
    }

    public void setTotalAmountMonthOut(BigDecimal totalAmountMonthOut) {
        this.totalAmountMonthOut = totalAmountMonthOut;
    }

    public BigDecimal getTotalAmountMonthDeductible() {
        return totalAmountMonthDeductible;
    }

    public void setTotalAmountMonthDeductible(BigDecimal totalAmountMonthDeductible) {
        this.totalAmountMonthDeductible = totalAmountMonthDeductible;
    }

    public BigDecimal getTotalAmountMonth() {
        return totalAmountMonth;
    }

    public void setTotalAmountMonth(BigDecimal totalAmountMonth) {
        this.totalAmountMonth = totalAmountMonth;
    }

    public BigDecimal getTotalAmountMonthWaitReturn() {
        return totalAmountMonthWaitReturn;
    }

    public void setTotalAmountMonthWaitReturn(BigDecimal totalAmountMonthWaitReturn) {
        this.totalAmountMonthWaitReturn = totalAmountMonthWaitReturn;
    }

    public BigDecimal getIfServicefeeChange() {
        return ifServicefeeChange;
    }

    public void setIfServicefeeChange(BigDecimal ifServicefeeChange) {
        this.ifServicefeeChange = ifServicefeeChange;
    }

    public BigDecimal getServicefeeChange() {
        return servicefeeChange;
    }

    public void setServicefeeChange(BigDecimal servicefeeChange) {
        this.servicefeeChange = servicefeeChange;
    }

    public BigDecimal getConfirmState() {
        return confirmState;
    }

    public void setConfirmState(BigDecimal confirmState) {
        this.confirmState = confirmState;
    }

    public BigDecimal getElecFeeOut() {
        return elecFeeOut;
    }

    public void setElecFeeOut(BigDecimal elecFeeOut) {
        this.elecFeeOut = elecFeeOut;
    }

    public BigDecimal getElecFeeDeductible() {
        return elecFeeDeductible;
    }

    public void setElecFeeDeductible(BigDecimal elecFeeDeductible) {
        this.elecFeeDeductible = elecFeeDeductible;
    }

    public BigDecimal getElecFee() {
        return elecFee;
    }

    public void setElecFee(BigDecimal elecFee) {
        this.elecFee = elecFee;
    }

    public BigDecimal getElecFeeWaitReturn() {
        return elecFeeWaitReturn;
    }

    public void setElecFeeWaitReturn(BigDecimal elecFeeWaitReturn) {
        this.elecFeeWaitReturn = elecFeeWaitReturn;
    }

    public BigDecimal getUsePowerServiceFeeOut2() {
        return usePowerServiceFeeOut2;
    }

    public void setUsePowerServiceFeeOut2(BigDecimal usePowerServiceFeeOut2) {
        this.usePowerServiceFeeOut2 = usePowerServiceFeeOut2;
    }

    public BigDecimal getOilGeneratorElectricFeeDeductible2() {
        return oilGeneratorElectricFeeDeductible2;
    }

    public void setOilGeneratorElectricFeeDeductible2(BigDecimal oilGeneratorElectricFeeDeductible2) {
        this.oilGeneratorElectricFeeDeductible2 = oilGeneratorElectricFeeDeductible2;
    }

    public BigDecimal getOilGeneratorElectricFee2() {
        return oilGeneratorElectricFee2;
    }

    public void setOilGeneratorElectricFee2(BigDecimal oilGeneratorElectricFee2) {
        this.oilGeneratorElectricFee2 = oilGeneratorElectricFee2;
    }

    public BigDecimal getOilGeneratorElectricFeeWaitReturn2() {
        return oilGeneratorElectricFeeWaitReturn2;
    }

    public void setOilGeneratorElectricFeeWaitReturn2(BigDecimal oilGeneratorElectricFeeWaitReturn2) {
        this.oilGeneratorElectricFeeWaitReturn2 = oilGeneratorElectricFeeWaitReturn2;
    }

    public BigDecimal getTotalActualAmount() {
        return totalActualAmount;
    }

    public void setTotalActualAmount(BigDecimal totalActualAmount) {
        this.totalActualAmount = totalActualAmount;
    }

    public String getAdjustmentFeeType() {
        return adjustmentFeeType;
    }

    public void setAdjustmentFeeType(String adjustmentFeeType) {
        this.adjustmentFeeType = adjustmentFeeType == null ? null : adjustmentFeeType.trim();
    }

    public BigDecimal getAdjustmentFee() {
        return adjustmentFee;
    }

    public void setAdjustmentFee(BigDecimal adjustmentFee) {
        this.adjustmentFee = adjustmentFee;
    }

    public BigDecimal getAfterAdjustmentFee() {
        return afterAdjustmentFee;
    }

    public void setAfterAdjustmentFee(BigDecimal afterAdjustmentFee) {
        this.afterAdjustmentFee = afterAdjustmentFee;
    }

    public String getAdjustmentFeeNote() {
        return adjustmentFeeNote;
    }

    public void setAdjustmentFeeNote(String adjustmentFeeNote) {
        this.adjustmentFeeNote = adjustmentFeeNote == null ? null : adjustmentFeeNote.trim();
    }

    public BigDecimal getDisputeFee() {
        return disputeFee;
    }

    public void setDisputeFee(BigDecimal disputeFee) {
        this.disputeFee = disputeFee;
    }

    public Boolean getLocaOperator() {
        return locaOperator;
    }

    public void setLocaOperator(Boolean locaOperator) {
        this.locaOperator = locaOperator;
    }

    public Integer getReimbursementState() {
        return reimbursementState;
    }

    public void setReimbursementState(Integer reimbursementState) {
        this.reimbursementState = reimbursementState;
    }

    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    public String getOrderProp() {
        return orderProp;
    }

    public void setOrderProp(String orderProp) {
        this.orderProp = orderProp == null ? null : orderProp.trim();
    }

    public String getRightProp() {
        return rightProp;
    }

    public void setRightProp(String rightProp) {
        this.rightProp = rightProp == null ? null : rightProp.trim();
    }

    public String getOriRight() {
        return oriRight;
    }

    public void setOriRight(String oriRight) {
        this.oriRight = oriRight == null ? null : oriRight.trim();
    }

    public Integer getResCompare() {
        return resCompare;
    }

    public void setResCompare(Integer resCompare) {
        this.resCompare = resCompare;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
}