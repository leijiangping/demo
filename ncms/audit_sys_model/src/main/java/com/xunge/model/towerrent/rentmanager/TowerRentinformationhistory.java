package com.xunge.model.towerrent.rentmanager;


import java.io.Serializable;
import java.util.Date;

public class TowerRentinformationhistory implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String rentinformationhistoryId;

    private String rentinformationId;

    private String regId;

    private String resourcesTypeId;

    private String businessConfirmNumber;

    private String scenceType;

    private String agreeBillCode;

    private String towerStationCode;

    private String towerStationName;

    private String operator;

    private String operatorRegId;

    private String demandRegId;

    private String stationRegId;

    private Boolean ifTowerLinkOperator;

    private String detailAddress;

    private Double longitude;

    private Double latitude;

    private String productConfigurationId;

    private String productTypeId;

    private String roomTypeId;

    private String supportingTypeId;

    private Long windPressure;

    private String shareTypeId;

    private String product1UnitNum;

    private String product1Height;

    private String product1AntennaNum;

    private String product1SystemNum;

    private Boolean product1IsUpTowerrru;

    private Boolean product1IfbbuOnRoom;

    private String product1BbuFee;

    private String product2UnitNum;

    private String product2Height;

    private String product2AntennaNum;

    private String product2SystemNum;

    private String product2IsUpTowerrru;

    private Boolean product2IfbbuOnRoom;

    private Boolean product2BbuFee;

    private String product3UnitNum;

    private String product3Height;

    private String product3AntennaNum;

    private String product3SystemNum;

    private Boolean product3IsUpTowerrru;

    private Boolean product3IfbbuOnRoom;

    private String product3BbuFee;

    private String antennaHeightRangeId;

    private String towerHeight;

    private Boolean ifstandardBuildFee;

    private Boolean ifrruDis;

    private Integer currentTowerShareNum;

    private Integer currentRoomSupportingShareNum;

    private Integer currentServiceShareNum;

    private Integer currentRoomFeeShareNum;

    private Integer currentElecimportShareNum;

    private Integer addTowerShareNum;

    private Integer addRoomSupportingShareNum;

    private Boolean isSpecEnterStation;

    private Date startDate;

    private Date endDate;

    private String unitProductNumber;

    private Long towerPrice;

    private Long roomPrice;

    private Long supportingPrice;

    private Long towerShareDis;

    private Long roomSupportingShareDis;

    private Long computeRoomSupportingPrice;

    private Long maintenanceFee;

    private Long maintenanceFeeDis;

    private Long originalMaintenanceFee;

    private Long computeMaintenanceFee;

    private Long stageFee;

    private Long stageFeeDis;

    private Long originalStageFee;

    private Long electricImportFee;

    private Long electricImportFeeDis;

    private Long originalElectricImportFee;

    private Long computeElectricImportFee;

    private Boolean ifHasPowerCondition;

    private Boolean ifSelectPowerService;

    private String oilGenerateElectricMethodId;

    private Long oilGeneratorElectricFee;

    private Long originalOilGenerateElectricFee;

    private Long computeOilGenerateElectricFee;

    private Long otherFee;

    private String otherFeeRemark;

    private Long originalOtherFee;

    private Long computeOtherFee;

    private String maintenanceLevelId;

    private Long hightLevelFee;

    private String electricProtectionMethodId;

    private Long electricProtectionFee;

    private Long reserveBattery;

    private String roomFeeMethod;

    private String elecImportFeeMethod;

    private String microwaveServiceFee;

    private String wlanServiceFee;

    private Long batteryProtectionFee;

    private Long totalAmount;

    private Long totalActualAmount;

    private Long computeTotalAmount;

    private Long computeTotalActualAmount;

    private Long computeRoomPrice;

    private Long computeSupportingPrice;

    private Long computeTowerPrice1;

    private Long computeTowerPrice2;

    private Long computeTowerPrice3;

    private String publishUser;

    private Date publishTime;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;

    private Integer checkState;

    public String getRentinformationhistoryId() {
        return rentinformationhistoryId;
    }

    public void setRentinformationhistoryId(String rentinformationhistoryId) {
        this.rentinformationhistoryId = rentinformationhistoryId == null ? null : rentinformationhistoryId.trim();
    }

    public String getRentinformationId() {
        return rentinformationId;
    }

    public void setRentinformationId(String rentinformationId) {
        this.rentinformationId = rentinformationId == null ? null : rentinformationId.trim();
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public String getResourcesTypeId() {
        return resourcesTypeId;
    }

    public void setResourcesTypeId(String resourcesTypeId) {
        this.resourcesTypeId = resourcesTypeId == null ? null : resourcesTypeId.trim();
    }

    public String getBusinessConfirmNumber() {
        return businessConfirmNumber;
    }

    public void setBusinessConfirmNumber(String businessConfirmNumber) {
        this.businessConfirmNumber = businessConfirmNumber == null ? null : businessConfirmNumber.trim();
    }

    public String getScenceType() {
        return scenceType;
    }

    public void setScenceType(String scenceType) {
        this.scenceType = scenceType == null ? null : scenceType.trim();
    }

    public String getAgreeBillCode() {
        return agreeBillCode;
    }

    public void setAgreeBillCode(String agreeBillCode) {
        this.agreeBillCode = agreeBillCode == null ? null : agreeBillCode.trim();
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

    public String getDemandRegId() {
        return demandRegId;
    }

    public void setDemandRegId(String demandRegId) {
        this.demandRegId = demandRegId == null ? null : demandRegId.trim();
    }

    public String getStationRegId() {
        return stationRegId;
    }

    public void setStationRegId(String stationRegId) {
        this.stationRegId = stationRegId == null ? null : stationRegId.trim();
    }

    public Boolean getIfTowerLinkOperator() {
        return ifTowerLinkOperator;
    }

    public void setIfTowerLinkOperator(Boolean ifTowerLinkOperator) {
        this.ifTowerLinkOperator = ifTowerLinkOperator;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress == null ? null : detailAddress.trim();
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getProductConfigurationId() {
        return productConfigurationId;
    }

    public void setProductConfigurationId(String productConfigurationId) {
        this.productConfigurationId = productConfigurationId == null ? null : productConfigurationId.trim();
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

    public String getSupportingTypeId() {
        return supportingTypeId;
    }

    public void setSupportingTypeId(String supportingTypeId) {
        this.supportingTypeId = supportingTypeId == null ? null : supportingTypeId.trim();
    }

    public Long getWindPressure() {
        return windPressure;
    }

    public void setWindPressure(Long windPressure) {
        this.windPressure = windPressure;
    }

    public String getShareTypeId() {
        return shareTypeId;
    }

    public void setShareTypeId(String shareTypeId) {
        this.shareTypeId = shareTypeId == null ? null : shareTypeId.trim();
    }

    public String getProduct1UnitNum() {
        return product1UnitNum;
    }

    public void setProduct1UnitNum(String product1UnitNum) {
        this.product1UnitNum = product1UnitNum == null ? null : product1UnitNum.trim();
    }

    public String getProduct1Height() {
        return product1Height;
    }

    public void setProduct1Height(String product1Height) {
        this.product1Height = product1Height == null ? null : product1Height.trim();
    }

    public String getProduct1AntennaNum() {
        return product1AntennaNum;
    }

    public void setProduct1AntennaNum(String product1AntennaNum) {
        this.product1AntennaNum = product1AntennaNum == null ? null : product1AntennaNum.trim();
    }

    public String getProduct1SystemNum() {
        return product1SystemNum;
    }

    public void setProduct1SystemNum(String product1SystemNum) {
        this.product1SystemNum = product1SystemNum == null ? null : product1SystemNum.trim();
    }

    public Boolean getProduct1IsUpTowerrru() {
        return product1IsUpTowerrru;
    }

    public void setProduct1IsUpTowerrru(Boolean product1IsUpTowerrru) {
        this.product1IsUpTowerrru = product1IsUpTowerrru;
    }

    public Boolean getProduct1IfbbuOnRoom() {
        return product1IfbbuOnRoom;
    }

    public void setProduct1IfbbuOnRoom(Boolean product1IfbbuOnRoom) {
        this.product1IfbbuOnRoom = product1IfbbuOnRoom;
    }

    public String getProduct1BbuFee() {
        return product1BbuFee;
    }

    public void setProduct1BbuFee(String product1BbuFee) {
        this.product1BbuFee = product1BbuFee == null ? null : product1BbuFee.trim();
    }

    public String getProduct2UnitNum() {
        return product2UnitNum;
    }

    public void setProduct2UnitNum(String product2UnitNum) {
        this.product2UnitNum = product2UnitNum == null ? null : product2UnitNum.trim();
    }

    public String getProduct2Height() {
        return product2Height;
    }

    public void setProduct2Height(String product2Height) {
        this.product2Height = product2Height == null ? null : product2Height.trim();
    }

    public String getProduct2AntennaNum() {
        return product2AntennaNum;
    }

    public void setProduct2AntennaNum(String product2AntennaNum) {
        this.product2AntennaNum = product2AntennaNum == null ? null : product2AntennaNum.trim();
    }

    public String getProduct2SystemNum() {
        return product2SystemNum;
    }

    public void setProduct2SystemNum(String product2SystemNum) {
        this.product2SystemNum = product2SystemNum == null ? null : product2SystemNum.trim();
    }

    public String getProduct2IsUpTowerrru() {
        return product2IsUpTowerrru;
    }

    public void setProduct2IsUpTowerrru(String product2IsUpTowerrru) {
        this.product2IsUpTowerrru = product2IsUpTowerrru == null ? null : product2IsUpTowerrru.trim();
    }

    public Boolean getProduct2IfbbuOnRoom() {
        return product2IfbbuOnRoom;
    }

    public void setProduct2IfbbuOnRoom(Boolean product2IfbbuOnRoom) {
        this.product2IfbbuOnRoom = product2IfbbuOnRoom;
    }

    public Boolean getProduct2BbuFee() {
        return product2BbuFee;
    }

    public void setProduct2BbuFee(Boolean product2BbuFee) {
        this.product2BbuFee = product2BbuFee;
    }

    public String getProduct3UnitNum() {
        return product3UnitNum;
    }

    public void setProduct3UnitNum(String product3UnitNum) {
        this.product3UnitNum = product3UnitNum == null ? null : product3UnitNum.trim();
    }

    public String getProduct3Height() {
        return product3Height;
    }

    public void setProduct3Height(String product3Height) {
        this.product3Height = product3Height == null ? null : product3Height.trim();
    }

    public String getProduct3AntennaNum() {
        return product3AntennaNum;
    }

    public void setProduct3AntennaNum(String product3AntennaNum) {
        this.product3AntennaNum = product3AntennaNum == null ? null : product3AntennaNum.trim();
    }

    public String getProduct3SystemNum() {
        return product3SystemNum;
    }

    public void setProduct3SystemNum(String product3SystemNum) {
        this.product3SystemNum = product3SystemNum == null ? null : product3SystemNum.trim();
    }

    public Boolean getProduct3IsUpTowerrru() {
        return product3IsUpTowerrru;
    }

    public void setProduct3IsUpTowerrru(Boolean product3IsUpTowerrru) {
        this.product3IsUpTowerrru = product3IsUpTowerrru;
    }

    public Boolean getProduct3IfbbuOnRoom() {
        return product3IfbbuOnRoom;
    }

    public void setProduct3IfbbuOnRoom(Boolean product3IfbbuOnRoom) {
        this.product3IfbbuOnRoom = product3IfbbuOnRoom;
    }

    public String getProduct3BbuFee() {
        return product3BbuFee;
    }

    public void setProduct3BbuFee(String product3BbuFee) {
        this.product3BbuFee = product3BbuFee == null ? null : product3BbuFee.trim();
    }

    public String getAntennaHeightRangeId() {
        return antennaHeightRangeId;
    }

    public void setAntennaHeightRangeId(String antennaHeightRangeId) {
        this.antennaHeightRangeId = antennaHeightRangeId == null ? null : antennaHeightRangeId.trim();
    }

    public String getTowerHeight() {
        return towerHeight;
    }

    public void setTowerHeight(String towerHeight) {
        this.towerHeight = towerHeight == null ? null : towerHeight.trim();
    }

    public Boolean getIfstandardBuildFee() {
        return ifstandardBuildFee;
    }

    public void setIfstandardBuildFee(Boolean ifstandardBuildFee) {
        this.ifstandardBuildFee = ifstandardBuildFee;
    }

    public Boolean getIfrruDis() {
        return ifrruDis;
    }

    public void setIfrruDis(Boolean ifrruDis) {
        this.ifrruDis = ifrruDis;
    }

    public Integer getCurrentTowerShareNum() {
        return currentTowerShareNum;
    }

    public void setCurrentTowerShareNum(Integer currentTowerShareNum) {
        this.currentTowerShareNum = currentTowerShareNum;
    }

    public Integer getCurrentRoomSupportingShareNum() {
        return currentRoomSupportingShareNum;
    }

    public void setCurrentRoomSupportingShareNum(Integer currentRoomSupportingShareNum) {
        this.currentRoomSupportingShareNum = currentRoomSupportingShareNum;
    }

    public Integer getCurrentServiceShareNum() {
        return currentServiceShareNum;
    }

    public void setCurrentServiceShareNum(Integer currentServiceShareNum) {
        this.currentServiceShareNum = currentServiceShareNum;
    }

    public Integer getCurrentRoomFeeShareNum() {
        return currentRoomFeeShareNum;
    }

    public void setCurrentRoomFeeShareNum(Integer currentRoomFeeShareNum) {
        this.currentRoomFeeShareNum = currentRoomFeeShareNum;
    }

    public Integer getCurrentElecimportShareNum() {
        return currentElecimportShareNum;
    }

    public void setCurrentElecimportShareNum(Integer currentElecimportShareNum) {
        this.currentElecimportShareNum = currentElecimportShareNum;
    }

    public Integer getAddTowerShareNum() {
        return addTowerShareNum;
    }

    public void setAddTowerShareNum(Integer addTowerShareNum) {
        this.addTowerShareNum = addTowerShareNum;
    }

    public Integer getAddRoomSupportingShareNum() {
        return addRoomSupportingShareNum;
    }

    public void setAddRoomSupportingShareNum(Integer addRoomSupportingShareNum) {
        this.addRoomSupportingShareNum = addRoomSupportingShareNum;
    }

    public Boolean getIsSpecEnterStation() {
        return isSpecEnterStation;
    }

    public void setIsSpecEnterStation(Boolean isSpecEnterStation) {
        this.isSpecEnterStation = isSpecEnterStation;
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

    public String getUnitProductNumber() {
        return unitProductNumber;
    }

    public void setUnitProductNumber(String unitProductNumber) {
        this.unitProductNumber = unitProductNumber == null ? null : unitProductNumber.trim();
    }

    public Long getTowerPrice() {
        return towerPrice;
    }

    public void setTowerPrice(Long towerPrice) {
        this.towerPrice = towerPrice;
    }

    public Long getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Long roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Long getSupportingPrice() {
        return supportingPrice;
    }

    public void setSupportingPrice(Long supportingPrice) {
        this.supportingPrice = supportingPrice;
    }

    public Long getTowerShareDis() {
        return towerShareDis;
    }

    public void setTowerShareDis(Long towerShareDis) {
        this.towerShareDis = towerShareDis;
    }

    public Long getRoomSupportingShareDis() {
        return roomSupportingShareDis;
    }

    public void setRoomSupportingShareDis(Long roomSupportingShareDis) {
        this.roomSupportingShareDis = roomSupportingShareDis;
    }

    public Long getComputeRoomSupportingPrice() {
        return computeRoomSupportingPrice;
    }

    public void setComputeRoomSupportingPrice(Long computeRoomSupportingPrice) {
        this.computeRoomSupportingPrice = computeRoomSupportingPrice;
    }

    public Long getMaintenanceFee() {
        return maintenanceFee;
    }

    public void setMaintenanceFee(Long maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
    }

    public Long getMaintenanceFeeDis() {
        return maintenanceFeeDis;
    }

    public void setMaintenanceFeeDis(Long maintenanceFeeDis) {
        this.maintenanceFeeDis = maintenanceFeeDis;
    }

    public Long getOriginalMaintenanceFee() {
        return originalMaintenanceFee;
    }

    public void setOriginalMaintenanceFee(Long originalMaintenanceFee) {
        this.originalMaintenanceFee = originalMaintenanceFee;
    }

    public Long getComputeMaintenanceFee() {
        return computeMaintenanceFee;
    }

    public void setComputeMaintenanceFee(Long computeMaintenanceFee) {
        this.computeMaintenanceFee = computeMaintenanceFee;
    }

    public Long getStageFee() {
        return stageFee;
    }

    public void setStageFee(Long stageFee) {
        this.stageFee = stageFee;
    }

    public Long getStageFeeDis() {
        return stageFeeDis;
    }

    public void setStageFeeDis(Long stageFeeDis) {
        this.stageFeeDis = stageFeeDis;
    }

    public Long getOriginalStageFee() {
        return originalStageFee;
    }

    public void setOriginalStageFee(Long originalStageFee) {
        this.originalStageFee = originalStageFee;
    }

    public Long getElectricImportFee() {
        return electricImportFee;
    }

    public void setElectricImportFee(Long electricImportFee) {
        this.electricImportFee = electricImportFee;
    }

    public Long getElectricImportFeeDis() {
        return electricImportFeeDis;
    }

    public void setElectricImportFeeDis(Long electricImportFeeDis) {
        this.electricImportFeeDis = electricImportFeeDis;
    }

    public Long getOriginalElectricImportFee() {
        return originalElectricImportFee;
    }

    public void setOriginalElectricImportFee(Long originalElectricImportFee) {
        this.originalElectricImportFee = originalElectricImportFee;
    }

    public Long getComputeElectricImportFee() {
        return computeElectricImportFee;
    }

    public void setComputeElectricImportFee(Long computeElectricImportFee) {
        this.computeElectricImportFee = computeElectricImportFee;
    }

    public Boolean getIfHasPowerCondition() {
        return ifHasPowerCondition;
    }

    public void setIfHasPowerCondition(Boolean ifHasPowerCondition) {
        this.ifHasPowerCondition = ifHasPowerCondition;
    }

    public Boolean getIfSelectPowerService() {
        return ifSelectPowerService;
    }

    public void setIfSelectPowerService(Boolean ifSelectPowerService) {
        this.ifSelectPowerService = ifSelectPowerService;
    }

    public String getOilGenerateElectricMethodId() {
        return oilGenerateElectricMethodId;
    }

    public void setOilGenerateElectricMethodId(String oilGenerateElectricMethodId) {
        this.oilGenerateElectricMethodId = oilGenerateElectricMethodId == null ? null : oilGenerateElectricMethodId.trim();
    }

    public Long getOilGeneratorElectricFee() {
        return oilGeneratorElectricFee;
    }

    public void setOilGeneratorElectricFee(Long oilGeneratorElectricFee) {
        this.oilGeneratorElectricFee = oilGeneratorElectricFee;
    }

    public Long getOriginalOilGenerateElectricFee() {
        return originalOilGenerateElectricFee;
    }

    public void setOriginalOilGenerateElectricFee(Long originalOilGenerateElectricFee) {
        this.originalOilGenerateElectricFee = originalOilGenerateElectricFee;
    }

    public Long getComputeOilGenerateElectricFee() {
        return computeOilGenerateElectricFee;
    }

    public void setComputeOilGenerateElectricFee(Long computeOilGenerateElectricFee) {
        this.computeOilGenerateElectricFee = computeOilGenerateElectricFee;
    }

    public Long getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(Long otherFee) {
        this.otherFee = otherFee;
    }

    public String getOtherFeeRemark() {
        return otherFeeRemark;
    }

    public void setOtherFeeRemark(String otherFeeRemark) {
        this.otherFeeRemark = otherFeeRemark == null ? null : otherFeeRemark.trim();
    }

    public Long getOriginalOtherFee() {
        return originalOtherFee;
    }

    public void setOriginalOtherFee(Long originalOtherFee) {
        this.originalOtherFee = originalOtherFee;
    }

    public Long getComputeOtherFee() {
        return computeOtherFee;
    }

    public void setComputeOtherFee(Long computeOtherFee) {
        this.computeOtherFee = computeOtherFee;
    }

    public String getMaintenanceLevelId() {
        return maintenanceLevelId;
    }

    public void setMaintenanceLevelId(String maintenanceLevelId) {
        this.maintenanceLevelId = maintenanceLevelId == null ? null : maintenanceLevelId.trim();
    }

    public Long getHightLevelFee() {
        return hightLevelFee;
    }

    public void setHightLevelFee(Long hightLevelFee) {
        this.hightLevelFee = hightLevelFee;
    }

    public String getElectricProtectionMethodId() {
        return electricProtectionMethodId;
    }

    public void setElectricProtectionMethodId(String electricProtectionMethodId) {
        this.electricProtectionMethodId = electricProtectionMethodId == null ? null : electricProtectionMethodId.trim();
    }

    public Long getElectricProtectionFee() {
        return electricProtectionFee;
    }

    public void setElectricProtectionFee(Long electricProtectionFee) {
        this.electricProtectionFee = electricProtectionFee;
    }

    public Long getReserveBattery() {
        return reserveBattery;
    }

    public void setReserveBattery(Long reserveBattery) {
        this.reserveBattery = reserveBattery;
    }

    public String getRoomFeeMethod() {
        return roomFeeMethod;
    }

    public void setRoomFeeMethod(String roomFeeMethod) {
        this.roomFeeMethod = roomFeeMethod == null ? null : roomFeeMethod.trim();
    }

    public String getElecImportFeeMethod() {
        return elecImportFeeMethod;
    }

    public void setElecImportFeeMethod(String elecImportFeeMethod) {
        this.elecImportFeeMethod = elecImportFeeMethod == null ? null : elecImportFeeMethod.trim();
    }

    public String getMicrowaveServiceFee() {
        return microwaveServiceFee;
    }

    public void setMicrowaveServiceFee(String microwaveServiceFee) {
        this.microwaveServiceFee = microwaveServiceFee == null ? null : microwaveServiceFee.trim();
    }

    public String getWlanServiceFee() {
        return wlanServiceFee;
    }

    public void setWlanServiceFee(String wlanServiceFee) {
        this.wlanServiceFee = wlanServiceFee == null ? null : wlanServiceFee.trim();
    }

    public Long getBatteryProtectionFee() {
        return batteryProtectionFee;
    }

    public void setBatteryProtectionFee(Long batteryProtectionFee) {
        this.batteryProtectionFee = batteryProtectionFee;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTotalActualAmount() {
        return totalActualAmount;
    }

    public void setTotalActualAmount(Long totalActualAmount) {
        this.totalActualAmount = totalActualAmount;
    }

    public Long getComputeTotalAmount() {
        return computeTotalAmount;
    }

    public void setComputeTotalAmount(Long computeTotalAmount) {
        this.computeTotalAmount = computeTotalAmount;
    }

    public Long getComputeTotalActualAmount() {
        return computeTotalActualAmount;
    }

    public void setComputeTotalActualAmount(Long computeTotalActualAmount) {
        this.computeTotalActualAmount = computeTotalActualAmount;
    }

    public Long getComputeRoomPrice() {
        return computeRoomPrice;
    }

    public void setComputeRoomPrice(Long computeRoomPrice) {
        this.computeRoomPrice = computeRoomPrice;
    }

    public Long getComputeSupportingPrice() {
        return computeSupportingPrice;
    }

    public void setComputeSupportingPrice(Long computeSupportingPrice) {
        this.computeSupportingPrice = computeSupportingPrice;
    }

    public Long getComputeTowerPrice1() {
        return computeTowerPrice1;
    }

    public void setComputeTowerPrice1(Long computeTowerPrice1) {
        this.computeTowerPrice1 = computeTowerPrice1;
    }

    public Long getComputeTowerPrice2() {
        return computeTowerPrice2;
    }

    public void setComputeTowerPrice2(Long computeTowerPrice2) {
        this.computeTowerPrice2 = computeTowerPrice2;
    }

    public Long getComputeTowerPrice3() {
        return computeTowerPrice3;
    }

    public void setComputeTowerPrice3(Long computeTowerPrice3) {
        this.computeTowerPrice3 = computeTowerPrice3;
    }

    public String getPublishUser() {
        return publishUser;
    }

    public void setPublishUser(String publishUser) {
        this.publishUser = publishUser == null ? null : publishUser.trim();
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
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

    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }
}
