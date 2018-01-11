package com.xunge.model.towerrent.mobile;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

import com.xunge.model.activity.Act;

public class TwrRentInformationVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2185674360098197657L;
		//移动起租表编码
		private String rentinformationId;
		
		//区域编码
		@Excel(name="区县",orderNum="5",isImportField="true")
	    private String regId;
		
	    //资源类型id
	    @Excel(name="共享信息",orderNum="33")
	    private String resourcesTypeId;
		
	    //业务确认单号
	    @Excel(name="业务确认单号",orderNum="13")
	    private String businessConfirmNumber;
		
	    //场景划分
	    @Excel(name="场景划分",orderNum="15")
	    private String scenceType;
		
	    //需求确认单编号
	    @Excel(name="需求单号",orderNum="1",isImportField="true")
	    private String agreeBillCode;
		
	    //铁塔公司站址编码
	    @Excel(name="站址编码",orderNum="17")
	    private String towerStationCode;
		
	    //铁塔公司站址名称
	    @Excel(name="站址名称",orderNum="19")
	    private String towerStationName;
		
	    //运营商
	    @Excel(name="运营商",orderNum="6")
	    private String operator;
		
	    //运营商地市
	    @Excel(name="运营商地市",orderNum="7")
	    private String operatorRegName;
		
	    //需求承接地市
	    @Excel(name="需求承接地市",orderNum="9")
	    private String demandRegName;
		
	    //站址所属地市
	    @Excel(name="站址所属地市",orderNum="11")
	    private String stationRegName;
		
	    //是否铁塔与移动侧关联站址编码
	    private int ifTowerLinkOperator;
		
	    //详细地址
	    @Excel(name="详细地址",orderNum="21")
	    private String detailAddress;
		
	    //经度
	    @Excel(name="经度",orderNum="23")
	    private Double longitude;
		
	    //纬度
	    @Excel(name="纬度",orderNum="25")
	    private Double latitude;
		
	    //产品配置（33种）
	    @Excel(name="产品配置",orderNum="31")
	    private String productConfigurationId;
		
	    //铁塔产品
	    @Excel(name="铁塔产品",orderNum="35")
	    private String productTypeId;
		
	    //机房产品
	    @Excel(name="机房产品",orderNum="37")
	    private String roomTypeId;
		
	    //配套编码
	    private String supportingTypeId;
		
	    //风压系数
	    @Excel(name="风压系数",orderNum="55")
	    private Long windPressure;
		
	    //共享类型编码
	    private String shareTypeId;
		
	    //产品单元1：产品单元数（个）
	    @Excel(name="产品单元1：产品单元数（个）",orderNum="59")
	    private String product1UnitNum;
		
	    //产品单元1：挂高（米）
	    @Excel(name="产品单元1：挂高（米）",orderNum="60")
	    private String product1Height;
		
	    //产品单元1：天线数量
	    @Excel(name="产品单元1：天线数量（副）",orderNum="61")
	    private String product1AntennaNum;
		
	    //产品单元1：系统数量
	    @Excel(name="产品单元1：系统数量（套）",orderNum="62")
	    private String product1SystemNum;
		
	    //产品单元1：RRU是否上塔
	    @Excel(name="产品单元1：RRU是否上塔",orderNum="64",replace={"是_1","否_0"})
	    private int product1IsUpTowerRRU;
		
	    //产品单元1：RRU拉远时BBU是否放在铁塔机房
	    @Excel(name="产品单元1：RRU拉远时BBU是否放在铁塔机房",orderNum="63",replace={"是_1","否_0"})
	    private int product1IfBBUOnRoom;
		
	    //产品单元1：BBU安装在铁塔机房的服务费（元/年）
	    @Excel(name="产品单元1：BBU安装在铁塔机房的服务费（元/年）",orderNum="65")
	    private String product1BBUFee;
		
	    //产品单元2：产品单元数（个）
	    @Excel(name="产品单元2：产品单元数（个）",orderNum="66")
	    private String product2UnitNum;
		
	    //产品单元2：挂高（米）
	    @Excel(name="产品单元2：挂高（米）",orderNum="67")
	    private String product2Height;
		
	    //产品单元2：天线数量
	    @Excel(name="产品单元2：天线数量（副）",orderNum="68")
	    private String product2AntennaNum;
		
	    //产品单元2：系统数量
	    @Excel(name="产品单元2：系统数量（套）",orderNum="70")
	    private String product2SystemNum;
		
	    //产品单元2：RRU是否上塔
	    @Excel(name="产品单元2：RRU是否上塔",orderNum="74",replace={"是_1","否_0"})
	    private String product2IsUpTowerRRU;
		
	    //产品单元2：RRU拉远时BBU是否放在铁塔机房
	    @Excel(name="产品单元2：RRU拉远时BBU是否放在铁塔机房",orderNum="72",replace={"是_1","否_0"})
	    private int product2IfBBUOnRoom;
		
	    //产品单元2：BBU安装在铁塔机房的服务费（元/年）
	    @Excel(name="产品单元2：BBU安装在铁塔机房的服务费（元/年）",orderNum="76")
	    private int product2BBUFee;
		
	    //产品单元3：产品单元数（个）
	    @Excel(name="产品单元3：产品单元数（个）",orderNum="78")
	    private String product3UnitNum;
		
	    //产品单元3：挂高（米）
	    @Excel(name="产品单元3：挂高（米）",orderNum="80")
	    private String product3Height;
		
	    //产品单元3：天线数量
	    @Excel(name="产品单元3：天线数量（副）",orderNum="82")
	    private String product3AntennaNum;
		
	    //产品单元3：系统数量
	    @Excel(name="产品单元3：系统数量（套）",orderNum="84")
	    private String product3SystemNum;
		
	    //产品单元3：RRU是否上塔
	    @Excel(name="产品单元3：RRU是否上塔",orderNum="88",replace={"是_1","否_0"})
	    private int product3IsUpTowerRRU;
		
	    //产品单元3：RRU拉远时BBU是否放在铁塔机房
	    @Excel(name="产品单元3：RRU拉远时BBU是否放在铁塔机房",orderNum="86",replace={"是_1","否_0"})
	    private int product3IfBBUOnRoom;
		
	    //单元3：BBU安装在铁塔机房的服务费（元/年）
	    @Excel(name="产品单元3：BBU安装在铁塔机房的服务费（元/年）",orderNum="90")
	    private String product3BBUFee;
		
	    //实际最高天线挂高（米）编码
	    private String antennaHeightRangeId;
		
	    //塔高（米）
	    @Excel(name="塔高（米）",orderNum="150")
	    private String towerHeight;
		
	    //是否标准建造成本
	    @Excel(name="是否标准建造成本",orderNum="57",replace={"是_1","否_0"})
	    private int ifstandardBuildFee;
		
	    //RRU是否折扣
	    @Excel(name="RRU是否折扣",orderNum="124",replace={"是_1","否_0"})
	    private int ifRRUDis;
		
	    //当前铁塔共享客户总数
	    @Excel(name="当前铁塔共享客户数量",orderNum="92")
	    private Integer currentTowerShareNum;
		
	    //当前机房及配套存量新增共享客户总数
	    private Integer currentRoomSupportingShareNum;
		
	    //当前维护费共享客户数量
	    @Excel(name="当前维护费共享客户数量",orderNum="96")
	    private Integer currentServiceShareNum;
		
	    //当前场地费共享客户数量
	    @Excel(name="当前场地费共享客户数量",orderNum="98")
	    private Integer currentRoomFeeShareNum;
		
	    //当前电力引入费共享客户数量
	    @Excel(name="当前电力引入费共享客户数量",orderNum="100")
	    private Integer currentElecimportShareNum;
		
	    //铁塔存量新增共享客户数
	    private Integer addTowerShareNum;
		
	    //机房及配套存量新增共享客户数
	    @Excel(name="当前机房及配套共享客户数量",orderNum="94")
	    private Integer addRoomSupportingShareNum;
		
	    //0-6点是否可上站
	    @Excel(name="0-6点是否上站",orderNum="43",replace={"是_1","否_0"})
	    private int isSpecEnterStation;
		
	    //服务起始日期
	    @Excel(name="服务开始日期",orderNum="27")
	    private Date startDate;
		
	    //服务结束日期
	    @Excel(name="服务结束日期",orderNum="29")
	    private Date endDate;
		
	    //产品单元数
	    private String unitProductNumber;
		
	    //铁塔建造成本
	    @Excel(name="铁塔建造成本（元/年）",orderNum="102")
	    private Long towerPrice;
		
	    //机房建造成本
	    @Excel(name="机房建造成本（元/年）",orderNum="104")
	    private Long roomPrice;
		
	    //配套建造成本
	    @Excel(name="配套建造成本（元/年）",orderNum="106")
	    private Long supportingPrice;
		
	    //铁塔共享折扣
	    @Excel(name="铁塔共享折扣",orderNum="120")
	    private Long towerShareDis;
		
	    //机房及配套共享折扣
	    @Excel(name="机房及配套共享折扣",orderNum="122")
	    private Long roomSupportingShareDis;
		
	    //系统计算机房及配套基准价格
	    private Long computeRoomSupportingPrice;
		
	    //维护费
	    @Excel(name="维护费（元/年）",orderNum="108")
	    private Long maintenanceFee;
		
	    //维护费折扣
	    @Excel(name="维护费折扣",orderNum="114")
	    private Long maintenanceFeeDis;
		
	    //维护费原始录入值
	    private Long originalMaintenanceFee;
		
	    //系统计算维护费
	    private Long computeMaintenanceFee;
		
	    //场地费
	    @Excel(name="场地费（元/年）",orderNum="110")
	    private Long stageFee;
		
	    //场地费折扣
	    @Excel(name="场地费折扣",orderNum="116")
	    private Long stageFeeDis;
		
	    //场地费原始录入值
	    private Long originalStageFee;
		
	    //电力引入费（元/年）
	    @Excel(name="电力引入费（元/年）",orderNum="112")
	    private Long electricImportFee;
		
	    //电力引入费折扣
	    @Excel(name="电力引入费折扣",orderNum="118")
	    private Long electricImportFeeDis;
		
	    //电力引入费原始录入值
	    private Long originalElectricImportFee;
		
	    //系统计算电力引入费
	    private Long computeElectricImportFee;
		
	    //是否具备发电条件
	    @Excel(name="是否具备发电条件",orderNum="45",replace={"是_1","否_0"})
	    private int ifHasPowerCondition;
		
	    //是否选择发电服务
	    @Excel(name="是否选择发电服务",orderNum="47",replace={"是_1","否_0"})
	    private int ifSelectPowerService;
		
	    //油机发电服务费模式（0包干，3按次）
	    @Excel(name="油机发电服务费模式",orderNum="51")
	    private String oilGenerateElectricMethodId;
		
	    //油机发电服务费
	    @Excel(name="油机发电服务费",orderNum="128")
	    private Long oilGeneratorElectricFee;
		
	    //油机发电服务费原始录入值
	    private Long originalOilGenerateElectricFee;
		
	    //系统计算油机发电服务费
	    private Long computeOilGenerateElectricFee;
		
	    //其他费用（元/年）
	    @Excel(name="其他费用（元/年）（不含税）",orderNum="138")
	    private Long otherFee;
		
	    //其他费用说明
	    @Excel(name="其他费用说明",orderNum="140")
	    private String otherFeeRemark;
		
	    //其他费用原始录入值
	    private Long originalOtherFee;
		
	    //系统计算其他费用
	    private Long computeOtherFee;
		
	    //维护等级id
	    @Excel(name="维护等级",orderNum="53")
	    private String maintenanceLevelId;
		
	    //超过10%高等级维护费
	    @Excel(name="超过10%高等级服务站址额外维护服务费",orderNum="130")
	    private Long hightLevelFee;
		
	    //电力报账服务模式id
	    @Excel(name="电力保障服务费模式",orderNum="49")
	    private String electricProtectionMethodId;
		
	    //包干电费
	    @Excel(name="包干电费",orderNum="126")
	    private Long electricProtectionFee;
		
	    //后备电池时长（小时）
	    @Excel(name="后备电池（小时）",orderNum="39")
	    private Long reserveBattery;
		
	    //场地费模式
	    @Excel(name="场地费模式",orderNum="152")
	    private String roomFeeMethod;
		
	    //电力引入费模式
	    @Excel(name="电力引入费模式",orderNum="154")
	    private String elecImportFeeMethod;
		
	    //微波产品服务费（元/年）（不含税）
	    @Excel(name="微波产品服务费（元/年）（不含税）",orderNum="136")
	    private String microwaveServiceFee;
		
	    //WLAN产品服务费（元/年）（不含税）
	    @Excel(name="WLAN产品服务费（元/年）（不含税）",orderNum="134")
	    private String wlanServiceFee;
		
	    //蓄电池额外保障费
	    @Excel(name="蓄电池额外保障费",orderNum="132")
	    private Long batteryProtectionFee;
		
	    //产品服务费合计（不含税）
	    @Excel(name="塔类产品服务费（元/年）（不含税）",orderNum="142")
	    private Long totalAmount;
		
	    //产品服务费合计（含税）
	    @Excel(name="产品服务费合计（元/年）（含税）",orderNum="144")
	    private Long totalActualAmount;
		
	    //系统计算产品服务费合计（不含税）
	    private Long computeTotalAmount;
		
	    //系统计算产品服务费合计（含税）
	    private Long computeTotalActualAmount;
		
	    //系统计算机房基准价格
	    private Long computeRoomPrice;
		
	    //系统计算配套基准价格
	    private Long computeSupportingPrice;
		
	    //系统计算铁塔基准价格1
	    private Long computeTowerPrice1;
		//系统计算铁塔基准价格2
	    private Long computeTowerPrice2;
		//系统计算铁塔基准价格3
	    private Long computeTowerPrice3;
		
	    //业务单起租发起人
	    @Excel(name="业务单起租发起人",orderNum="146")
	    private String publishUser;
	    
		//业务单起租发起时间
	    @Excel(name="业务单起租发起时间",orderNum="148")
	    private Date publishTime;
		
	    //创建用户编码
	    private String createUserId;
		
	    //创建时间
	    private Date createTime;
		//更新用户编码
	    private String updateUserId;
		//更新时间
	    private Date updateTime;
		//审核状态
	    private Integer checkState;
	    
	    private Act act;
		public String getRentinformationId() {
			return rentinformationId;
		}
		public void setRentinformationId(String rentinformationId) {
			this.rentinformationId = rentinformationId;
		}
		public String getRegId() {
			return regId;
		}
		public void setRegId(String regId) {
			this.regId = regId;
		}
		public String getResourcesTypeId() {
			return resourcesTypeId;
		}
		public void setResourcesTypeId(String resourcesTypeId) {
			this.resourcesTypeId = resourcesTypeId;
		}
		public String getBusinessConfirmNumber() {
			return businessConfirmNumber;
		}
		public void setBusinessConfirmNumber(String businessConfirmNumber) {
			this.businessConfirmNumber = businessConfirmNumber;
		}
		public String getScenceType() {
			return scenceType;
		}
		public void setScenceType(String scenceType) {
			this.scenceType = scenceType;
		}
		public String getAgreeBillCode() {
			return agreeBillCode;
		}
		public void setAgreeBillCode(String agreeBillCode) {
			this.agreeBillCode = agreeBillCode;
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
		public String getOperator() {
			return operator;
		}
		public void setOperator(String operator) {
			this.operator = operator;
		}
		public String getOperatorRegName() {
			return operatorRegName;
		}
		public void setOperatorRegName(String operatorRegName) {
			this.operatorRegName = operatorRegName;
		}
		public String getDemandRegName() {
			return demandRegName;
		}
		public void setDemandRegName(String demandRegName) {
			this.demandRegName = demandRegName;
		}
		public String getStationRegName() {
			return stationRegName;
		}
		public void setStationRegName(String stationRegName) {
			this.stationRegName = stationRegName;
		}
		public int getIfTowerLinkOperator() {
			return ifTowerLinkOperator;
		}
		public void setIfTowerLinkOperator(int ifTowerLinkOperator) {
			this.ifTowerLinkOperator = ifTowerLinkOperator;
		}
		public String getDetailAddress() {
			return detailAddress;
		}
		public void setDetailAddress(String detailAddress) {
			this.detailAddress = detailAddress;
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
			this.productConfigurationId = productConfigurationId;
		}
		public String getProductTypeId() {
			return productTypeId;
		}
		public void setProductTypeId(String productTypeId) {
			this.productTypeId = productTypeId;
		}
		public String getRoomTypeId() {
			return roomTypeId;
		}
		public void setRoomTypeId(String roomTypeId) {
			this.roomTypeId = roomTypeId;
		}
		public String getSupportingTypeId() {
			return supportingTypeId;
		}
		public void setSupportingTypeId(String supportingTypeId) {
			this.supportingTypeId = supportingTypeId;
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
			this.shareTypeId = shareTypeId;
		}
		public String getProduct1UnitNum() {
			return product1UnitNum;
		}
		public void setProduct1UnitNum(String product1UnitNum) {
			this.product1UnitNum = product1UnitNum;
		}
		public String getProduct1Height() {
			return product1Height;
		}
		public void setProduct1Height(String product1Height) {
			this.product1Height = product1Height;
		}
		public String getProduct1AntennaNum() {
			return product1AntennaNum;
		}
		public void setProduct1AntennaNum(String product1AntennaNum) {
			this.product1AntennaNum = product1AntennaNum;
		}
		public String getProduct1SystemNum() {
			return product1SystemNum;
		}
		public void setProduct1SystemNum(String product1SystemNum) {
			this.product1SystemNum = product1SystemNum;
		}

		public String getProduct2UnitNum() {
			return product2UnitNum;
		}
		public void setProduct2UnitNum(String product2UnitNum) {
			this.product2UnitNum = product2UnitNum;
		}
		public String getProduct2Height() {
			return product2Height;
		}
		public void setProduct2Height(String product2Height) {
			this.product2Height = product2Height;
		}
		public String getProduct2AntennaNum() {
			return product2AntennaNum;
		}
		public void setProduct2AntennaNum(String product2AntennaNum) {
			this.product2AntennaNum = product2AntennaNum;
		}
		public String getProduct2SystemNum() {
			return product2SystemNum;
		}
		public void setProduct2SystemNum(String product2SystemNum) {
			this.product2SystemNum = product2SystemNum;
		}
		
		public String getProduct3UnitNum() {
			return product3UnitNum;
		}
		public void setProduct3UnitNum(String product3UnitNum) {
			this.product3UnitNum = product3UnitNum;
		}
		public String getProduct3Height() {
			return product3Height;
		}
		public void setProduct3Height(String product3Height) {
			this.product3Height = product3Height;
		}
		public String getProduct3AntennaNum() {
			return product3AntennaNum;
		}
		public void setProduct3AntennaNum(String product3AntennaNum) {
			this.product3AntennaNum = product3AntennaNum;
		}
		public String getProduct3SystemNum() {
			return product3SystemNum;
		}
		public void setProduct3SystemNum(String product3SystemNum) {
			this.product3SystemNum = product3SystemNum;
		}
		
		public String getAntennaHeightRangeId() {
			return antennaHeightRangeId;
		}
		public void setAntennaHeightRangeId(String antennaHeightRangeId) {
			this.antennaHeightRangeId = antennaHeightRangeId;
		}
		public String getTowerHeight() {
			return towerHeight;
		}
		public void setTowerHeight(String towerHeight) {
			this.towerHeight = towerHeight;
		}
		public int getIfstandardBuildFee() {
			return ifstandardBuildFee;
		}
		public void setIfstandardBuildFee(int ifstandardBuildFee) {
			this.ifstandardBuildFee = ifstandardBuildFee;
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
		public void setCurrentRoomSupportingShareNum(
				Integer currentRoomSupportingShareNum) {
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
		public int getIsSpecEnterStation() {
			return isSpecEnterStation;
		}
		public void setIsSpecEnterStation(int isSpecEnterStation) {
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
			this.unitProductNumber = unitProductNumber;
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
		public int getIfHasPowerCondition() {
			return ifHasPowerCondition;
		}
		public void setIfHasPowerCondition(int ifHasPowerCondition) {
			this.ifHasPowerCondition = ifHasPowerCondition;
		}
		public int getIfSelectPowerService() {
			return ifSelectPowerService;
		}
		public void setIfSelectPowerService(int ifSelectPowerService) {
			this.ifSelectPowerService = ifSelectPowerService;
		}
		public String getOilGenerateElectricMethodId() {
			return oilGenerateElectricMethodId;
		}
		public void setOilGenerateElectricMethodId(String oilGenerateElectricMethodId) {
			this.oilGenerateElectricMethodId = oilGenerateElectricMethodId;
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
		public void setOriginalOilGenerateElectricFee(	Long originalOilGenerateElectricFee) {
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
			this.otherFeeRemark = otherFeeRemark;
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
			this.maintenanceLevelId = maintenanceLevelId;
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
			this.electricProtectionMethodId = electricProtectionMethodId;
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
			this.roomFeeMethod = roomFeeMethod;
		}
		public String getElecImportFeeMethod() {
			return elecImportFeeMethod;
		}
		public void setElecImportFeeMethod(String elecImportFeeMethod) {
			this.elecImportFeeMethod = elecImportFeeMethod;
		}
		public String getMicrowaveServiceFee() {
			return microwaveServiceFee;
		}
		public void setMicrowaveServiceFee(String microwaveServiceFee) {
			this.microwaveServiceFee = microwaveServiceFee;
		}
		public String getWlanServiceFee() {
			return wlanServiceFee;
		}
		public void setWlanServiceFee(String wlanServiceFee) {
			this.wlanServiceFee = wlanServiceFee;
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
			this.publishUser = publishUser;
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
			this.createUserId = createUserId;
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
			this.updateUserId = updateUserId;
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
		public int getProduct1IsUpTowerRRU() {
			return product1IsUpTowerRRU;
		}
		public void setProduct1IsUpTowerRRU(int product1IsUpTowerRRU) {
			this.product1IsUpTowerRRU = product1IsUpTowerRRU;
		}
		public int getProduct1IfBBUOnRoom() {
			return product1IfBBUOnRoom;
		}
		public void setProduct1IfBBUOnRoom(int product1IfBBUOnRoom) {
			this.product1IfBBUOnRoom = product1IfBBUOnRoom;
		}
		public String getProduct1BBUFee() {
			return product1BBUFee;
		}
		public void setProduct1BBUFee(String product1bbuFee) {
			product1BBUFee = product1bbuFee;
		}
		public String getProduct2IsUpTowerRRU() {
			return product2IsUpTowerRRU;
		}
		public void setProduct2IsUpTowerRRU(String product2IsUpTowerRRU) {
			this.product2IsUpTowerRRU = product2IsUpTowerRRU;
		}
		public int getProduct2IfBBUOnRoom() {
			return product2IfBBUOnRoom;
		}
		public void setProduct2IfBBUOnRoom(int product2IfBBUOnRoom) {
			this.product2IfBBUOnRoom = product2IfBBUOnRoom;
		}
		public int getProduct3IsUpTowerRRU() {
			return product3IsUpTowerRRU;
		}
		public void setProduct3IsUpTowerRRU(int product3IsUpTowerRRU) {
			this.product3IsUpTowerRRU = product3IsUpTowerRRU;
		}
		public int getProduct3IfBBUOnRoom() {
			return product3IfBBUOnRoom;
		}
		public void setProduct3IfBBUOnRoom(int product3IfBBUOnRoom) {
			this.product3IfBBUOnRoom = product3IfBBUOnRoom;
		}
		public String getProduct3BBUFee() {
			return product3BBUFee;
		}
		public void setProduct3BBUFee(String product3bbuFee) {
			product3BBUFee = product3bbuFee;
		}
		public int getProduct2BBUFee() {
			return product2BBUFee;
		}
		public void setProduct2BBUFee(int product2bbuFee) {
			product2BBUFee = product2bbuFee;
		}
		public int getIfRRUDis() {
			return ifRRUDis;
		}
		public void setIfRRUDis(int ifRRUDis) {
			this.ifRRUDis = ifRRUDis;
		}
		public Act getAct() {
			return act;
		}
		public void setAct(Act act) {
			this.act = act;
		}
		
}