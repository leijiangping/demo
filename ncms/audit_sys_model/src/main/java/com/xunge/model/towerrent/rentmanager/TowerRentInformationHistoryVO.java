package com.xunge.model.towerrent.rentmanager;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.xunge.model.system.region.SysRegionVO;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class TowerRentInformationHistoryVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//移动起租信息拆分表编码
    private String rentinformationhistoryId;
    //移动起租表对应编码
    private String rentinformationId;
    //区域编码   
    @Excel(name="区县",orderNum="5",isImportField="true")
    private String regId;
    //资源类型id
    @Excel(name="共享信息",orderNum="33")
    private String resourcesTypeId;
    /*
     * 业务确认单号
     * 确认唯一性
     * */
    @Excel(name="业务确认单号",orderNum="13")
    private String businessConfirmNumber;
    //场景划分
    @Excel(name="场景划分",orderNum="15")
    private String scenceType;
    //需求确认单编号
    @Excel(name="需求单号",orderNum="1",isImportField="true")
    private String agreeBillCode;
    /*
     * 铁塔公司站址编码
     * 确认唯一性
     * */
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
    private String operatorRegId;
    //需求承接地市
    @Excel(name="需求承接地市",orderNum="9")
    private String demandRegId;
    //站址所属地市
    @Excel(name="站址所属地市",orderNum="11")
    private String stationRegId;
    //是否铁塔与移动侧关联站址编码
    private Boolean ifTowerLinkOperator;
    //详细地址
    @Excel(name="详细地址",orderNum="21")
    private String detailAddress;
    //经度
    @Excel(name="经度",orderNum="23")
    private double longitude;
    //纬度
    @Excel(name="纬度",orderNum="25")
    private double latitude;
    //产品配置编码
    @Excel(name="产品配置",orderNum="31")
    private String productConfigurationId;
    //铁塔产品
    @Excel(name="铁塔产品",orderNum="35")
    private String productTypeId;
    //机房铲平
    @Excel(name="机房产品",orderNum="37")
    private String roomTypeId;
    //配套编码
    private String supportingTypeId;
    //风压系数
    @Excel(name="风压系数",orderNum="55")
    private BigDecimal windPressure;
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
    private Boolean product1IsUpTowerrru;
    //产品单元1：RRU拉远时BBU是否放在铁塔机房
    @Excel(name="产品单元1：RRU拉远时BBU是否放在铁塔机房",orderNum="63",replace={"是_1","否_0"})
    private Boolean product1IfbbuOnRoom;
    //产品单元1：BBU安装在铁塔机房的服务费（元/年）
    @Excel(name="产品单元1：BBU安装在铁塔机房的服务费（元/年）",orderNum="65")
    private String product1BbuFee;
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
    private String product2IsUpTowerrru;
    //产品单元2：RRU拉远时BBU是否放在铁塔机房
    @Excel(name="产品单元2：RRU拉远时BBU是否放在铁塔机房",orderNum="72",replace={"是_1","否_0"})
    private Boolean product2IfbbuOnRoom;
    //产品单元2：BBU安装在铁塔机房的服务费（元/年）
    @Excel(name="产品单元2：BBU安装在铁塔机房的服务费（元/年）",orderNum="76")
    private Boolean product2BbuFee;
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
    private Boolean product3IsUpTowerrru;
    //产品单元3：RRU拉远时BBU是否放在铁塔机房
    @Excel(name="产品单元3：RRU拉远时BBU是否放在铁塔机房",orderNum="86",replace={"是_1","否_0"})
    private Boolean product3IfbbuOnRoom;
    //产品单元3：BBU安装在铁塔机房的服务费（元/年）
    @Excel(name="产品单元3：BBU安装在铁塔机房的服务费（元/年）",orderNum="90")
    private String product3BbuFee;
    //实际最高天线挂高（米）编码
    private String antennaHeightRangeId;
    //塔高（米）
    @Excel(name="塔高（米）",orderNum="150")
    private String towerHeight;
    //是否标准建造成本
    @Excel(name="是否标准建造成本",orderNum="57",replace={"是_1","否_0"})
    private Boolean ifstandardBuildFee;
    //RRU是否折扣
    @Excel(name="RRU是否折扣",orderNum="124",replace={"是_1","否_0"})
    private Boolean ifrruDis;
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
    private Boolean isSpecEnterStation;
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
    private BigDecimal towerPrice;
    //机房建造成本
    @Excel(name="机房建造成本（元/年）",orderNum="104")
    private BigDecimal roomPrice;
    //配套建造成本
    @Excel(name="配套建造成本（元/年）",orderNum="106")
    private BigDecimal supportingPrice;
    //铁塔共享折扣
    @Excel(name="铁塔共享折扣",orderNum="120")
    private BigDecimal towerShareDis;
    //机房及配套共享折扣
    @Excel(name="机房及配套共享折扣",orderNum="122")
    private BigDecimal roomSupportingShareDis;
    //系统计算机房及配套基准价格
    private BigDecimal computeRoomSupportingPrice;
    //维护费
    @Excel(name="维护费（元/年）",orderNum="108")
    private BigDecimal maintenanceFee;
    //维护费折扣
    @Excel(name="维护费折扣",orderNum="114")
    private BigDecimal maintenanceFeeDis;
    //维护费原始录入值
    private BigDecimal originalMaintenanceFee;
    //系统计算维护费
    private BigDecimal computeMaintenanceFee;
    //场地费
    @Excel(name="场地费（元/年）",orderNum="110")
    private BigDecimal stageFee;
    //场地费折扣
    @Excel(name="场地费折扣",orderNum="116")
    private BigDecimal stageFeeDis;
    //场地费原始录入值
    private BigDecimal originalStageFee;
    //电力引入费（元/年）
    @Excel(name="电力引入费（元/年）",orderNum="112")
    private BigDecimal electricImportFee;
    //电力引入费折扣
    @Excel(name="电力引入费折扣",orderNum="118")
    private BigDecimal electricImportFeeDis;
    //电力引入费原始录入值
    private BigDecimal originalElectricImportFee;
    //系统计算电力引入费
    private BigDecimal computeElectricImportFee;
    //是否具备发电条件
    @Excel(name="是否具备发电条件",orderNum="45",replace={"是_1","否_0"})
    private Boolean ifHasPowerCondition;
    //是否选择发电服务
    @Excel(name="是否选择发电服务",orderNum="47",replace={"是_1","否_0"})
    private Boolean ifSelectPowerService;
    //油机发电服务费模式（0包干，3按次）
    @Excel(name="油机发电服务费模式",orderNum="51")
    private String oilGenerateElectricMethodId;
    //油机发电服务费
    @Excel(name="油机发电服务费",orderNum="128")
    private BigDecimal oilGeneratorElectricFee;
    //油机发电服务费原始录入值
    private BigDecimal originalOilGenerateElectricFee;
    //系统计算油机发电服务费
    private BigDecimal computeOilGenerateElectricFee;
    //其他费用（元/年）
    @Excel(name="其他费用（元/年）（不含税）",orderNum="138")
    private BigDecimal otherFee;
    //其他费用说明
    @Excel(name="其他费用说明",orderNum="140")
    private String otherFeeRemark;
    //其他费用原始录入值
    private BigDecimal originalOtherFee;
    //系统计算其他费用
    private BigDecimal computeOtherFee;
    //维护等级id
    @Excel(name="维护等级",orderNum="53")
    private String maintenanceLevelId;
    //超过10%高等级维护费
    @Excel(name="超过10%高等级服务站址额外维护服务费",orderNum="130")
    private BigDecimal hightLevelFee;
    //电力报账服务模式id
    @Excel(name="电力保障服务费模式",orderNum="49")
    private String electricProtectionMethodId;
    //包干电费
    @Excel(name="包干电费",orderNum="126")
    private BigDecimal electricProtectionFee;
    //后备电池时长（小时）
    @Excel(name="后备电池（小时）",orderNum="39")
    private BigDecimal reserveBattery;
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
    private BigDecimal batteryProtectionFee;
    //产品服务费合计（不含税）
    @Excel(name="塔类产品服务费（元/年）（不含税）",orderNum="142")
    private BigDecimal totalAmount;
    //产品服务费合计（含税）
    @Excel(name="产品服务费合计（元/年）（含税）",orderNum="144")
    private BigDecimal totalActualAmount;
    //系统计算产品服务费合计（不含税）
    private BigDecimal computeTotalAmount;
    //系统计算产品服务费合计（含税）
    private BigDecimal computeTotalActualAmount;
    //系统计算机房基准价格
    private BigDecimal computeRoomPrice;
    //系统计算配套基准价格
    private BigDecimal computeSupportingPrice;
    //系统计算铁塔基准价格1
    private BigDecimal computeTowerPrice1;
    //系统计算铁塔基准价格2
    private BigDecimal computeTowerPrice2;
    //系统计算铁塔基准价格3
    private BigDecimal computeTowerPrice3;
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
    
    //关联运营商区县   wangz
  	private SysRegionVO operatorSysRegion;
  	
	public String getRentinformationhistoryId() {
		return rentinformationhistoryId;
	}
	public void setRentinformationhistoryId(String rentinformationhistoryId) {
		this.rentinformationhistoryId = rentinformationhistoryId;
	}
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
	public String getOperatorRegId() {
		return operatorRegId;
	}
	public void setOperatorRegId(String operatorRegId) {
		this.operatorRegId = operatorRegId;
	}
	public String getDemandRegId() {
		return demandRegId;
	}
	public void setDemandRegId(String demandRegId) {
		this.demandRegId = demandRegId;
	}
	public String getStationRegId() {
		return stationRegId;
	}
	public void setStationRegId(String stationRegId) {
		this.stationRegId = stationRegId;
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
		this.detailAddress = detailAddress;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
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
	public BigDecimal getWindPressure() {
		return windPressure;
	}
	public void setWindPressure(BigDecimal windPressure) {
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
		this.product1BbuFee = product1BbuFee;
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
	public String getProduct2IsUpTowerrru() {
		return product2IsUpTowerrru;
	}
	public void setProduct2IsUpTowerrru(String product2IsUpTowerrru) {
		this.product2IsUpTowerrru = product2IsUpTowerrru;
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
		this.product3BbuFee = product3BbuFee;
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
		this.unitProductNumber = unitProductNumber;
	}
	public BigDecimal getTowerPrice() {
		return towerPrice;
	}
	public void setTowerPrice(BigDecimal towerPrice) {
		this.towerPrice = towerPrice;
	}
	public BigDecimal getRoomPrice() {
		return roomPrice;
	}
	public void setRoomPrice(BigDecimal roomPrice) {
		this.roomPrice = roomPrice;
	}
	public BigDecimal getSupportingPrice() {
		return supportingPrice;
	}
	public void setSupportingPrice(BigDecimal supportingPrice) {
		this.supportingPrice = supportingPrice;
	}
	public BigDecimal getTowerShareDis() {
		return towerShareDis;
	}
	public void setTowerShareDis(BigDecimal towerShareDis) {
		this.towerShareDis = towerShareDis;
	}
	public BigDecimal getRoomSupportingShareDis() {
		return roomSupportingShareDis;
	}
	public void setRoomSupportingShareDis(BigDecimal roomSupportingShareDis) {
		this.roomSupportingShareDis = roomSupportingShareDis;
	}
	public BigDecimal getComputeRoomSupportingPrice() {
		return computeRoomSupportingPrice;
	}
	public void setComputeRoomSupportingPrice(BigDecimal computeRoomSupportingPrice) {
		this.computeRoomSupportingPrice = computeRoomSupportingPrice;
	}
	public BigDecimal getMaintenanceFee() {
		return maintenanceFee;
	}
	public void setMaintenanceFee(BigDecimal maintenanceFee) {
		this.maintenanceFee = maintenanceFee;
	}
	public BigDecimal getMaintenanceFeeDis() {
		return maintenanceFeeDis;
	}
	public void setMaintenanceFeeDis(BigDecimal maintenanceFeeDis) {
		this.maintenanceFeeDis = maintenanceFeeDis;
	}
	public BigDecimal getOriginalMaintenanceFee() {
		return originalMaintenanceFee;
	}
	public void setOriginalMaintenanceFee(BigDecimal originalMaintenanceFee) {
		this.originalMaintenanceFee = originalMaintenanceFee;
	}
	public BigDecimal getComputeMaintenanceFee() {
		return computeMaintenanceFee;
	}
	public void setComputeMaintenanceFee(BigDecimal computeMaintenanceFee) {
		this.computeMaintenanceFee = computeMaintenanceFee;
	}
	public BigDecimal getStageFee() {
		return stageFee;
	}
	public void setStageFee(BigDecimal stageFee) {
		this.stageFee = stageFee;
	}
	public BigDecimal getStageFeeDis() {
		return stageFeeDis;
	}
	public void setStageFeeDis(BigDecimal stageFeeDis) {
		this.stageFeeDis = stageFeeDis;
	}
	public BigDecimal getOriginalStageFee() {
		return originalStageFee;
	}
	public void setOriginalStageFee(BigDecimal originalStageFee) {
		this.originalStageFee = originalStageFee;
	}
	public BigDecimal getElectricImportFee() {
		return electricImportFee;
	}
	public void setElectricImportFee(BigDecimal electricImportFee) {
		this.electricImportFee = electricImportFee;
	}
	public BigDecimal getElectricImportFeeDis() {
		return electricImportFeeDis;
	}
	public void setElectricImportFeeDis(BigDecimal electricImportFeeDis) {
		this.electricImportFeeDis = electricImportFeeDis;
	}
	public BigDecimal getOriginalElectricImportFee() {
		return originalElectricImportFee;
	}
	public void setOriginalElectricImportFee(BigDecimal originalElectricImportFee) {
		this.originalElectricImportFee = originalElectricImportFee;
	}
	public BigDecimal getComputeElectricImportFee() {
		return computeElectricImportFee;
	}
	public void setComputeElectricImportFee(BigDecimal computeElectricImportFee) {
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
		this.oilGenerateElectricMethodId = oilGenerateElectricMethodId;
	}
	public BigDecimal getOilGeneratorElectricFee() {
		return oilGeneratorElectricFee;
	}
	public void setOilGeneratorElectricFee(BigDecimal oilGeneratorElectricFee) {
		this.oilGeneratorElectricFee = oilGeneratorElectricFee;
	}
	public BigDecimal getOriginalOilGenerateElectricFee() {
		return originalOilGenerateElectricFee;
	}
	public void setOriginalOilGenerateElectricFee(
			BigDecimal originalOilGenerateElectricFee) {
		this.originalOilGenerateElectricFee = originalOilGenerateElectricFee;
	}
	public BigDecimal getComputeOilGenerateElectricFee() {
		return computeOilGenerateElectricFee;
	}
	public void setComputeOilGenerateElectricFee(
			BigDecimal computeOilGenerateElectricFee) {
		this.computeOilGenerateElectricFee = computeOilGenerateElectricFee;
	}
	public BigDecimal getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}
	public String getOtherFeeRemark() {
		return otherFeeRemark;
	}
	public void setOtherFeeRemark(String otherFeeRemark) {
		this.otherFeeRemark = otherFeeRemark;
	}
	public BigDecimal getOriginalOtherFee() {
		return originalOtherFee;
	}
	public void setOriginalOtherFee(BigDecimal originalOtherFee) {
		this.originalOtherFee = originalOtherFee;
	}
	public BigDecimal getComputeOtherFee() {
		return computeOtherFee;
	}
	public void setComputeOtherFee(BigDecimal computeOtherFee) {
		this.computeOtherFee = computeOtherFee;
	}
	public String getMaintenanceLevelId() {
		return maintenanceLevelId;
	}
	public void setMaintenanceLevelId(String maintenanceLevelId) {
		this.maintenanceLevelId = maintenanceLevelId;
	}
	public BigDecimal getHightLevelFee() {
		return hightLevelFee;
	}
	public void setHightLevelFee(BigDecimal hightLevelFee) {
		this.hightLevelFee = hightLevelFee;
	}
	public String getElectricProtectionMethodId() {
		return electricProtectionMethodId;
	}
	public void setElectricProtectionMethodId(String electricProtectionMethodId) {
		this.electricProtectionMethodId = electricProtectionMethodId;
	}
	public BigDecimal getElectricProtectionFee() {
		return electricProtectionFee;
	}
	public void setElectricProtectionFee(BigDecimal electricProtectionFee) {
		this.electricProtectionFee = electricProtectionFee;
	}
	public BigDecimal getReserveBattery() {
		return reserveBattery;
	}
	public void setReserveBattery(BigDecimal reserveBattery) {
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
	public BigDecimal getBatteryProtectionFee() {
		return batteryProtectionFee;
	}
	public void setBatteryProtectionFee(BigDecimal batteryProtectionFee) {
		this.batteryProtectionFee = batteryProtectionFee;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getTotalActualAmount() {
		return totalActualAmount;
	}
	public void setTotalActualAmount(BigDecimal totalActualAmount) {
		this.totalActualAmount = totalActualAmount;
	}
	public BigDecimal getComputeTotalAmount() {
		return computeTotalAmount;
	}
	public void setComputeTotalAmount(BigDecimal computeTotalAmount) {
		this.computeTotalAmount = computeTotalAmount;
	}
	public BigDecimal getComputeTotalActualAmount() {
		return computeTotalActualAmount;
	}
	public void setComputeTotalActualAmount(BigDecimal computeTotalActualAmount) {
		this.computeTotalActualAmount = computeTotalActualAmount;
	}
	public BigDecimal getComputeRoomPrice() {
		return computeRoomPrice;
	}
	public void setComputeRoomPrice(BigDecimal computeRoomPrice) {
		this.computeRoomPrice = computeRoomPrice;
	}
	public BigDecimal getComputeSupportingPrice() {
		return computeSupportingPrice;
	}
	public void setComputeSupportingPrice(BigDecimal computeSupportingPrice) {
		this.computeSupportingPrice = computeSupportingPrice;
	}
	public BigDecimal getComputeTowerPrice1() {
		return computeTowerPrice1;
	}
	public void setComputeTowerPrice1(BigDecimal computeTowerPrice1) {
		this.computeTowerPrice1 = computeTowerPrice1;
	}
	public BigDecimal getComputeTowerPrice2() {
		return computeTowerPrice2;
	}
	public void setComputeTowerPrice2(BigDecimal computeTowerPrice2) {
		this.computeTowerPrice2 = computeTowerPrice2;
	}
	public BigDecimal getComputeTowerPrice3() {
		return computeTowerPrice3;
	}
	public void setComputeTowerPrice3(BigDecimal computeTowerPrice3) {
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

	public SysRegionVO getOperatorSysRegion() {
		return operatorSysRegion;
	}
	public void setOperatorSysRegion(SysRegionVO operatorSysRegion) {
		this.operatorSysRegion = operatorSysRegion;
	}
}