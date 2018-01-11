package com.xunge.model.towerrent.rentmanager;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;

import com.xunge.model.activity.Act;
import com.xunge.model.system.region.SysRegionVO;

/**
 * 
 * @author yuefy
 * @date 2017年07月11日
 *  起租管理 铁塔资源信息
 */
public class TowerResourceInfoVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1571713288768158161L;
	
	 //需求确认单编号
    @Excel(name="需求单号",orderNum="4",isImportField="true")
    private String agreeBillCode;
    //区域id
  	@Excel(name="区县",orderNum="3",isImportField="true")
    private String regId;
    //运营商
    @Excel(name="运营商",orderNum="7",isImportField="true")
    private String operator;
    //运营商地市
    @Excel(name="运营商地市",orderNum="8")
    private String operatorRegName;
    //需求承接地市
    @Excel(name="需求承接地市",orderNum="10")
    private String demandRegName;
    //站址所属地市
    @Excel(name="站址所属地市",orderNum="12")
    private String stationRegName;
    //业务确认单号
    @Excel(name="业务确认单号",orderNum="14",isImportField="true")
    private String businessConfirmNumber;
    //场景划分
    @Excel(name="场景划分",orderNum="15")
    private String scenceType;
    //铁塔公司站址编码
    @Excel(name="站址编码",orderNum="17",isImportField="true")
    private String towerStationCode;
    //铁塔公司站址名称
    @Excel(name="站址名称",orderNum="18")
    private String towerStationName;
    //详细地址
    @Excel(name="详细地址",orderNum="20")
    private String detailAddress; 
    //经度
    @Excel(name="经度",orderNum="22")
    private BigDecimal longitude;
    //纬度
    @Excel(name="纬度",orderNum="23")
    private BigDecimal latitude;
    //服务开始日期
    @Excel(name="服务开始日期",orderNum="25",format="yyyy-MM-dd")
    private Date startDate;
    //服务结束日期
    @Excel(name="服务结束日期",orderNum="26",format="yyyy-MM-dd")
    private Date endDate;
    //产品配置（33种）
    @Excel(name="产品配置",orderNum="28")
    private String productConfigurationId;
    //资源类型id（1：原原产权方，2：既有共享，3：存量自改，4：存量改造，5：新建铁塔）
    //excel 为共享信息
    @Excel(name="共享信息",orderNum="30")
    private String resourcesTypeId;
    //铁塔产品
    @Excel(name="铁塔产品",orderNum="32")
    private String productTypeId;
    //机房产品
    @Excel(name="机房产品",orderNum="34")
    private String roomTypeId;
    //后备电池时长（小时）
    @Excel(name="后备电池（小时）",orderNum="36")
    private BigDecimal reserveBattery;
    //是否具备发电条件
    @Excel(name="是否具备发电条件",orderNum="38",replace={"是_1","否_0"})
    private int ifHasPowerCondition;
    //0-6点是否可上站
    @Excel(name="0-6点是否上站",orderNum="39",replace={"是_1","否_0"})
    private int isSpecEnterStation;
    //是否选择发电服务
    @Excel(name="是否选择发电服务",orderNum="41",replace={"是_1","否_0"})
    private int ifSelectPowerService;
    //电力保障服务模式id
    @Excel(name="电力保障服务费模式",orderNum="42")
    private String electricProtectionMethodId;
    //油机发电服务费模式（0包干，3按次）
    @Excel(name="油机发电服务费模式",orderNum="44")
    private String oilGenerateElectricMethodId;
    //维护等级id
    @Excel(name="维护等级",orderNum="46")
    private String maintenanceLevelId;
    //风压系数
    @Excel(name="风压系数",orderNum="48")
    private BigDecimal windPressure;
    //是否非标准建造成本  与正常是否相反
    @Excel(name="是否非标准建造成本",orderNum="50",replace={"否_1","是_0"})
    private int ifstandardBuildFee;
    //产品单元1：产品单元数（个）
    @Excel(name="产品单元1：产品单元数（个）",orderNum="52")
    private String product1UnitNum;
    //产品单元1：挂高（米）
    @Excel(name="产品单元1：挂高（米）",orderNum="54")
    private String product1Height;
    //产品单元1：天线数量
    @Excel(name="产品单元1：天线数量（副）",orderNum="56")
    private String product1AntennaNum;
    //产品单元1：系统数量
    @Excel(name="产品单元1：系统数量（套）",orderNum="58")
    private String product1SystemNum;
    //产品单元1：RRU拉远时BBU是否放在铁塔机房
    @Excel(name="产品单元1：RRU拉远时BBU是否放在铁塔机房",orderNum="60",replace={"是_1","否_0"})
    private int product1IfBBUOnRoom;
    //产品单元1：RRU是否上塔
    @Excel(name="产品单元1：RRU是否上塔",orderNum="62",replace={"是_1","否_0"})
    private int product1IsUpTowerRRU;
    //产品单元1：BBU安装在铁塔机房的服务费（元/年）
    @Excel(name="产品单元1：BBU安装在铁塔机房的服务费（元/年）",orderNum="64")
    private String product1BBUFee;
	//产品单元2：产品单元数（个）
    @Excel(name="产品单元2：产品单元数（个）",orderNum="64")
    private String product2UnitNum;
    //产品单元2：挂高（米）
    @Excel(name="产品单元2：挂高（米）",orderNum="66")
    private String product2Height;
    //产品单元2：天线数量
    @Excel(name="产品单元2：天线数量（副）",orderNum="68")
    private String product2AntennaNum;
    //产品单元2：系统数量
    @Excel(name="产品单元2：系统数量（套）",orderNum="70")
    private String product2SystemNum;
    //产品单元2：RRU拉远时BBU是否放在铁塔机房
    @Excel(name="产品单元2：RRU拉远时BBU是否放在铁塔机房",orderNum="72",replace={"是_1","否_0"})
    private int product2IfBBUOnRoom;
    //产品单元2：RRU是否上塔
    @Excel(name="产品单元2：RRU是否上塔",orderNum="74",replace={"是_1","否_0"})
    private int product2IsUpTowerRRU;
    //产品单元2：BBU安装在铁塔机房的服务费（元/年）
    @Excel(name="产品单元2：BBU安装在铁塔机房的服务费（元/年）",orderNum="76")
    private BigDecimal product2BBUFee;
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
    //产品单元3：RRU拉远时BBU是否放在铁塔机房
    @Excel(name="产品单元3：RRU拉远时BBU是否放在铁塔机房",orderNum="86",replace={"是_1","否_0"})
    private int product3IfBBUOnRoom;
    //产品单元3：RRU是否上塔
    @Excel(name="产品单元3：RRU是否上塔",orderNum="88",replace={"是_1","否_0"})
    private int product3IsUpTowerRRU;
    //产品单元3：BBU安装在铁塔机房的服务费（元/年）
    @Excel(name="产品单元3：BBU安装在铁塔机房的服务费（元/年）",orderNum="90")
    private BigDecimal product3BBUFee;
    //当前铁塔共享客户总数
    @Excel(name="当前铁塔共享客户数量",orderNum="92")
    private Integer currentTowerShareNum;
    //机房及配套存量新增共享客户数
    @Excel(name="当前机房及配套共享客户数量",orderNum="94")
    private Integer addRoomSupportingShareNum;
    //当前维护费共享客户数量
    @Excel(name="当前维护费共享客户数量",orderNum="96")
    private Integer currentServiceShareNum;
    //当前场地费共享客户数量
    @Excel(name="当前场地费共享客户数量",orderNum="98")
    private Integer currentRoomFeeShareNum;
    //当前电力引入费共享客户数量
    @Excel(name="当前电力引入费共享客户数量",orderNum="100")
    private Integer currentElecimportShareNum;
    //铁塔建造成本
    @Excel(name="铁塔建造成本（元/年）",orderNum="102")
    private BigDecimal towerPrice;
    //机房建造成本
    @Excel(name="机房建造成本（元/年）",orderNum="104")
    private BigDecimal roomPrice;
    //配套建造成本
    @Excel(name="配套建造成本（元/年）",orderNum="106")
    private BigDecimal supportingPrice;
    //维护费
    @Excel(name="维护费（元/年）",orderNum="108")
    private BigDecimal maintenanceFee;
    //场地费
    @Excel(name="场地费（元/年）",orderNum="110")
    private BigDecimal stageFee;
    //电力引入费（元/年）
    @Excel(name="电力引入费（元/年）",orderNum="112")
    private BigDecimal electricImportFee;
    //维护费折扣
    @Excel(name="维护费折扣",orderNum="114")
    private BigDecimal maintenanceFeeDis;
    //场地费折扣
    @Excel(name="场地费折扣",orderNum="116")
    private BigDecimal stageFeeDis;
    //电力引入费折扣
    @Excel(name="电力引入费折扣",orderNum="118")
    private BigDecimal electricImportFeeDis;
    //铁塔共享折扣
    @Excel(name="铁塔共享折扣",orderNum="120")
    private BigDecimal towerShareDis;
    //机房及配套共享折扣
    @Excel(name="机房及配套共享折扣",orderNum="122")
    private BigDecimal roomSupportingShareDis;
    //RRU是否折扣
    @Excel(name="RRU是否折扣",orderNum="124",replace={"是_1","否_0"})
    private int ifRRUDis;
    //包干电费
    @Excel(name="包干电费",orderNum="126")
    private BigDecimal electricProtectionFee;
    //油机发电服务费
    @Excel(name="油机发电服务费",orderNum="128")
    private BigDecimal oilGeneratorElectricFee;
    //超过10%高等级维护费
    @Excel(name="超过10%高等级服务站址额外维护服务费",orderNum="130")
    private BigDecimal hightLevelFee;
    //蓄电池额外保障费
    @Excel(name="蓄电池额外保障费",orderNum="132")
    private BigDecimal batteryProtectionFee;
    //WLAN产品服务费（元/年）（不含税）
    @Excel(name="WLAN产品服务费（元/年）（不含税）",orderNum="134")
    private String wlanServiceFee;
    //微波产品服务费（元/年）（不含税）
    @Excel(name="微波产品服务费（元/年）（不含税）",orderNum="136")
    private String microwaveServiceFee;
    //其他费用（元/年）
    @Excel(name="其他费用（元/年）（不含税）",orderNum="138")
    private BigDecimal otherFee;
    //其他费用说明
    @Excel(name="其他费用说明",orderNum="140")
    private String otherFeeRemark;
    //塔类产品服务费（元/年）（不含税）
    //产品服务费合计（不含税）
    @Excel(name="塔类产品服务费（元/年）（不含税）",orderNum="142")
    private BigDecimal totalAmount;
    //产品服务费合计（含税）
    @Excel(name="产品服务费合计（元/年）（含税）",orderNum="144")
    private BigDecimal totalActualAmount;
    //业务单起租发起人
    @Excel(name="业务单起租发起人",orderNum="146")
    private String publishUser;
    //业务单起租发起时间
    @Excel(name="业务单起租发起时间",orderNum="148")
    private Date publishTime;
    //塔高（米）
    @Excel(name="塔高（米）",orderNum="150")
    private String towerHeight;
    //场地费模式
    @Excel(name="场地费模式",orderNum="152")
    private String roomFeeMethod;
    //电力引入费模式
    @Excel(name="电力引入费模式",orderNum="154")
    private String elecImportFeeMethod;
    
    
	//铁塔起租表编码
    private String rentinformationtowerId;
    //是否铁塔与移动侧关联站址编码
    private int ifTowerLinkOperator;
    //配套id
    private String supportingTypeId;
    //共享类型编码
    private String shareTypeId;
    //实际最高天线挂高（米）编码
    private String antennaHeightRangeId;
    //当前机房及配套存量新增共享客户总数
    private Integer currentRoomSupportingShareNum;
    //铁塔存量新增共享客户数
    private Integer addTowerShareNum;
    //产品单元数
    private String unitProductNumber;
    //维护费原始录入值
    private BigDecimal originalMaintenanceFee;
    //场地费原始录入值
    private String originalStageFee;
    //电力引入费原始录入值
    private BigDecimal originalElectricImportFee;
    //油机发电服务费原始录入值
    private BigDecimal originalOilGenerateElectricFee;
    //其他费用原始录入值
    private BigDecimal originalOtherFee;
    //铁塔共享折扣系数一
    private BigDecimal changeTowerShareDisValue1;
    //铁塔共享变化日期一
    private Date changeTowerShareDisStartDate1;
    //机房及配套共享折扣系数一
    private BigDecimal changeRoomSupportingShareDisValue1;
    //机房及配套共享折扣变化日期一
    private Date changeRoomSupportingShareDisStartDate1;
    //铁塔共享折扣系数二
    private BigDecimal changeTowerShareDisValue2;
    //铁塔共享变化日期二
    private Date changeTowerShareDisStartDate2;
    //机房及配套共享折扣系数二
    private BigDecimal changeRoomSupportingShareDisValue2;
    //机房及配套共享折扣变化日期二
    private Date changeRoomSupportingShareDisStartDate2;
    //铁塔共享折扣系数三
    private BigDecimal changeTowerShareDisValue3;
    //铁塔共享变化日期三
    private Date changeTowerShareDisStartDate3;
    //机房及配套共享折扣系数三
    private BigDecimal changeRoomSupportingShareDisValue3;
    //机房及配套共享折扣变化日期三
    private Date changeRoomSupportingShareDisStartDate3;
    //维护费共享折扣系数一
    private BigDecimal changeMaintenanceFeeDisValue1;
    //维护费共享变化日期一
    private Date changeMaintenanceFeeDisStartDate1;
    //场地费共享折扣系数一
    private BigDecimal changeStageFeeDisValue1;
    //场地费共享变化日期一
    private Date changeStageFeeDisStartDate1;
    //电力引入费共享折扣系数一
    private BigDecimal changeElectricImportDisValue1;
    //电力引入费共享变化日期一
    private Date changeElectricImportDisStartDate1;
    //维护费共享折扣系数二
    private BigDecimal changeMaintenanceFeeDisValue2;
    //维护费共享变化日期二
    private Date changeMaintenanceFeeDisStartDate2;
    //场地费共享折扣系数二
    private BigDecimal changeStageFeeDisValue2;
    //场地费共享变化日期二
    private Date changeStageFeeDisStartDate2;
    //电力引入费共享折扣系数二
    private BigDecimal changeElectricImportDisValue2;
    //电力引入费共享变化日期二
    private Date changeElectricImportDisStartDate2;
    //维护费共享折扣系数三
    private BigDecimal changeMaintenanceFeeDisValue3;
    //维护费共享变化日期三
    private Date changeMaintenanceFeeDisStartDate3;
    //场地费共享折扣系数三
    private BigDecimal changeStageFeeDisValue3;
    //场地费共享变化日期三
    private Date changeStageFeeDisStartDate3;
    //电力引入费共享折扣系数三
    private BigDecimal changeElectricImportDisValue3;
    //电力引入费共享变化日期三
    private Date changeElectricImportDisStartDate3;
    //创建用户编码
    private String createUserId;
    //创建时间
    private Date createTime;
    //更新用户编码
    private String updateUserId;
    //更新时间
    private Date updateTime;
    //审核状态(0 审核通过 8 审核未通过  9 审核中  -1未提交)
    private Integer auditState;
    //区域信息对象
    @ExcelEntity(id="sysRegion",name="省市信息_sysRegion")
    private SysRegionVO sysRegionVO;
    
    private Act act;//业务数据对应的流程信息
    
	public String getAgreeBillCode() {
		return agreeBillCode;
	}
	public void setAgreeBillCode(String agreeBillCode) {
		this.agreeBillCode = agreeBillCode;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
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
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
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
	public String getProductConfigurationId() {
		return productConfigurationId;
	}
	public void setProductConfigurationId(String productConfigurationId) {
		this.productConfigurationId = productConfigurationId;
	}
	public String getResourcesTypeId() {
		return resourcesTypeId;
	}
	public void setResourcesTypeId(String resourcesTypeId) {
		this.resourcesTypeId = resourcesTypeId;
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
	public BigDecimal getReserveBattery() {
		return reserveBattery;
	}
	public void setReserveBattery(BigDecimal reserveBattery) {
		this.reserveBattery = reserveBattery;
	}
	public int getIfHasPowerCondition() {
		return ifHasPowerCondition;
	}
	public void setIfHasPowerCondition(int ifHasPowerCondition) {
		this.ifHasPowerCondition = ifHasPowerCondition;
	}
	public int getIsSpecEnterStation() {
		return isSpecEnterStation;
	}
	public void setIsSpecEnterStation(int isSpecEnterStation) {
		this.isSpecEnterStation = isSpecEnterStation;
	}
	public int getIfSelectPowerService() {
		return ifSelectPowerService;
	}
	public void setIfSelectPowerService(int ifSelectPowerService) {
		this.ifSelectPowerService = ifSelectPowerService;
	}
	public String getElectricProtectionMethodId() {
		return electricProtectionMethodId;
	}
	public void setElectricProtectionMethodId(String electricProtectionMethodId) {
		this.electricProtectionMethodId = electricProtectionMethodId;
	}
	public String getOilGenerateElectricMethodId() {
		return oilGenerateElectricMethodId;
	}
	public void setOilGenerateElectricMethodId(String oilGenerateElectricMethodId) {
		this.oilGenerateElectricMethodId = oilGenerateElectricMethodId;
	}
	public String getMaintenanceLevelId() {
		return maintenanceLevelId;
	}
	public void setMaintenanceLevelId(String maintenanceLevelId) {
		this.maintenanceLevelId = maintenanceLevelId;
	}
	public BigDecimal getWindPressure() {
		return windPressure;
	}
	public void setWindPressure(BigDecimal windPressure) {
		this.windPressure = windPressure;
	}
	public int getIfstandardBuildFee() {
		return ifstandardBuildFee;
	}
	public void setIfstandardBuildFee(int ifstandardBuildFee) {
		this.ifstandardBuildFee = ifstandardBuildFee;
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
	public int getProduct1IfBBUOnRoom() {
		return product1IfBBUOnRoom;
	}
	public void setProduct1IfBBUOnRoom(int product1IfBBUOnRoom) {
		this.product1IfBBUOnRoom = product1IfBBUOnRoom;
	}
	public int getProduct1IsUpTowerRRU() {
		return product1IsUpTowerRRU;
	}
	public void setProduct1IsUpTowerRRU(int product1IsUpTowerRRU) {
		this.product1IsUpTowerRRU = product1IsUpTowerRRU;
	}
	public String getProduct1BBUFee() {
		return product1BBUFee;
	}
	public void setProduct1BBUFee(String product1bbuFee) {
		product1BBUFee = product1bbuFee;
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
	public int getProduct2IfBBUOnRoom() {
		return product2IfBBUOnRoom;
	}
	public void setProduct2IfBBUOnRoom(int product2IfBBUOnRoom) {
		this.product2IfBBUOnRoom = product2IfBBUOnRoom;
	}
	public int getProduct2IsUpTowerRRU() {
		return product2IsUpTowerRRU;
	}
	public void setProduct2IsUpTowerRRU(int product2IsUpTowerRRU) {
		this.product2IsUpTowerRRU = product2IsUpTowerRRU;
	}
	public BigDecimal getProduct2BBUFee() {
		return product2BBUFee;
	}
	public void setProduct2BBUFee(BigDecimal product2bbuFee) {
		product2BBUFee = product2bbuFee;
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
	public int getProduct3IfBBUOnRoom() {
		return product3IfBBUOnRoom;
	}
	public void setProduct3IfBBUOnRoom(int product3IfBBUOnRoom) {
		this.product3IfBBUOnRoom = product3IfBBUOnRoom;
	}
	public int getProduct3IsUpTowerRRU() {
		return product3IsUpTowerRRU;
	}
	public void setProduct3IsUpTowerRRU(int product3IsUpTowerRRU) {
		this.product3IsUpTowerRRU = product3IsUpTowerRRU;
	}
	public BigDecimal getProduct3BBUFee() {
		return product3BBUFee;
	}
	public void setProduct3BBUFee(BigDecimal product3bbuFee) {
		product3BBUFee = product3bbuFee;
	}
	public Integer getCurrentTowerShareNum() {
		return currentTowerShareNum;
	}
	public void setCurrentTowerShareNum(Integer currentTowerShareNum) {
		this.currentTowerShareNum = currentTowerShareNum;
	}
	public Integer getAddRoomSupportingShareNum() {
		return addRoomSupportingShareNum;
	}
	public void setAddRoomSupportingShareNum(Integer addRoomSupportingShareNum) {
		this.addRoomSupportingShareNum = addRoomSupportingShareNum;
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
	public BigDecimal getMaintenanceFee() {
		return maintenanceFee;
	}
	public void setMaintenanceFee(BigDecimal maintenanceFee) {
		this.maintenanceFee = maintenanceFee;
	}
	public BigDecimal getStageFee() {
		return stageFee;
	}
	public void setStageFee(BigDecimal stageFee) {
		this.stageFee = stageFee;
	}
	public BigDecimal getElectricImportFee() {
		return electricImportFee;
	}
	public void setElectricImportFee(BigDecimal electricImportFee) {
		this.electricImportFee = electricImportFee;
	}
	public BigDecimal getMaintenanceFeeDis() {
		return maintenanceFeeDis;
	}
	public void setMaintenanceFeeDis(BigDecimal maintenanceFeeDis) {
		this.maintenanceFeeDis = maintenanceFeeDis;
	}
	public BigDecimal getStageFeeDis() {
		return stageFeeDis;
	}
	public void setStageFeeDis(BigDecimal stageFeeDis) {
		this.stageFeeDis = stageFeeDis;
	}
	public BigDecimal getElectricImportFeeDis() {
		return electricImportFeeDis;
	}
	public void setElectricImportFeeDis(BigDecimal electricImportFeeDis) {
		this.electricImportFeeDis = electricImportFeeDis;
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
	public int getIfRRUDis() {
		return ifRRUDis;
	}
	public void setIfRRUDis(int ifRRUDis) {
		this.ifRRUDis = ifRRUDis;
	}
	public BigDecimal getElectricProtectionFee() {
		return electricProtectionFee;
	}
	public void setElectricProtectionFee(BigDecimal electricProtectionFee) {
		this.electricProtectionFee = electricProtectionFee;
	}
	public BigDecimal getOilGeneratorElectricFee() {
		return oilGeneratorElectricFee;
	}
	public void setOilGeneratorElectricFee(BigDecimal oilGeneratorElectricFee) {
		this.oilGeneratorElectricFee = oilGeneratorElectricFee;
	}
	public BigDecimal getHightLevelFee() {
		return hightLevelFee;
	}
	public void setHightLevelFee(BigDecimal hightLevelFee) {
		this.hightLevelFee = hightLevelFee;
	}
	public BigDecimal getBatteryProtectionFee() {
		return batteryProtectionFee;
	}
	public void setBatteryProtectionFee(BigDecimal batteryProtectionFee) {
		this.batteryProtectionFee = batteryProtectionFee;
	}
	public String getWlanServiceFee() {
		return wlanServiceFee;
	}
	public void setWlanServiceFee(String wlanServiceFee) {
		this.wlanServiceFee = wlanServiceFee;
	}
	public String getMicrowaveServiceFee() {
		return microwaveServiceFee;
	}
	public void setMicrowaveServiceFee(String microwaveServiceFee) {
		this.microwaveServiceFee = microwaveServiceFee;
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
	public String getTowerHeight() {
		return towerHeight;
	}
	public void setTowerHeight(String towerHeight) {
		this.towerHeight = towerHeight;
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
	public String getRentinformationtowerId() {
		return rentinformationtowerId;
	}
	public void setRentinformationtowerId(String rentinformationtowerId) {
		this.rentinformationtowerId = rentinformationtowerId;
	}
	public int getIfTowerLinkOperator() {
		return ifTowerLinkOperator;
	}
	public void setIfTowerLinkOperator(int ifTowerLinkOperator) {
		this.ifTowerLinkOperator = ifTowerLinkOperator;
	}
	public String getSupportingTypeId() {
		return supportingTypeId;
	}
	public void setSupportingTypeId(String supportingTypeId) {
		this.supportingTypeId = supportingTypeId;
	}
	public String getShareTypeId() {
		return shareTypeId;
	}
	public void setShareTypeId(String shareTypeId) {
		this.shareTypeId = shareTypeId;
	}
	public String getAntennaHeightRangeId() {
		return antennaHeightRangeId;
	}
	public void setAntennaHeightRangeId(String antennaHeightRangeId) {
		this.antennaHeightRangeId = antennaHeightRangeId;
	}
	public Integer getCurrentRoomSupportingShareNum() {
		return currentRoomSupportingShareNum;
	}
	public void setCurrentRoomSupportingShareNum(
			Integer currentRoomSupportingShareNum) {
		this.currentRoomSupportingShareNum = currentRoomSupportingShareNum;
	}
	public Integer getAddTowerShareNum() {
		return addTowerShareNum;
	}
	public void setAddTowerShareNum(Integer addTowerShareNum) {
		this.addTowerShareNum = addTowerShareNum;
	}
	public String getUnitProductNumber() {
		return unitProductNumber;
	}
	public void setUnitProductNumber(String unitProductNumber) {
		this.unitProductNumber = unitProductNumber;
	}
	public BigDecimal getOriginalMaintenanceFee() {
		return originalMaintenanceFee;
	}
	public void setOriginalMaintenanceFee(BigDecimal originalMaintenanceFee) {
		this.originalMaintenanceFee = originalMaintenanceFee;
	}
	public String getOriginalStageFee() {
		return originalStageFee;
	}
	public void setOriginalStageFee(String originalStageFee) {
		this.originalStageFee = originalStageFee;
	}
	public BigDecimal getOriginalElectricImportFee() {
		return originalElectricImportFee;
	}
	public void setOriginalElectricImportFee(BigDecimal originalElectricImportFee) {
		this.originalElectricImportFee = originalElectricImportFee;
	}
	public BigDecimal getOriginalOilGenerateElectricFee() {
		return originalOilGenerateElectricFee;
	}
	public void setOriginalOilGenerateElectricFee(
			BigDecimal originalOilGenerateElectricFee) {
		this.originalOilGenerateElectricFee = originalOilGenerateElectricFee;
	}
	public BigDecimal getOriginalOtherFee() {
		return originalOtherFee;
	}
	public void setOriginalOtherFee(BigDecimal originalOtherFee) {
		this.originalOtherFee = originalOtherFee;
	}
	public BigDecimal getChangeTowerShareDisValue1() {
		return changeTowerShareDisValue1;
	}
	public void setChangeTowerShareDisValue1(BigDecimal changeTowerShareDisValue1) {
		this.changeTowerShareDisValue1 = changeTowerShareDisValue1;
	}
	public Date getChangeTowerShareDisStartDate1() {
		return changeTowerShareDisStartDate1;
	}
	public void setChangeTowerShareDisStartDate1(Date changeTowerShareDisStartDate1) {
		this.changeTowerShareDisStartDate1 = changeTowerShareDisStartDate1;
	}
	public BigDecimal getChangeRoomSupportingShareDisValue1() {
		return changeRoomSupportingShareDisValue1;
	}
	public void setChangeRoomSupportingShareDisValue1(
			BigDecimal changeRoomSupportingShareDisValue1) {
		this.changeRoomSupportingShareDisValue1 = changeRoomSupportingShareDisValue1;
	}
	public Date getChangeRoomSupportingShareDisStartDate1() {
		return changeRoomSupportingShareDisStartDate1;
	}
	public void setChangeRoomSupportingShareDisStartDate1(
			Date changeRoomSupportingShareDisStartDate1) {
		this.changeRoomSupportingShareDisStartDate1 = changeRoomSupportingShareDisStartDate1;
	}
	public BigDecimal getChangeTowerShareDisValue2() {
		return changeTowerShareDisValue2;
	}
	public void setChangeTowerShareDisValue2(BigDecimal changeTowerShareDisValue2) {
		this.changeTowerShareDisValue2 = changeTowerShareDisValue2;
	}
	public Date getChangeTowerShareDisStartDate2() {
		return changeTowerShareDisStartDate2;
	}
	public void setChangeTowerShareDisStartDate2(Date changeTowerShareDisStartDate2) {
		this.changeTowerShareDisStartDate2 = changeTowerShareDisStartDate2;
	}
	public BigDecimal getChangeRoomSupportingShareDisValue2() {
		return changeRoomSupportingShareDisValue2;
	}
	public void setChangeRoomSupportingShareDisValue2(
			BigDecimal changeRoomSupportingShareDisValue2) {
		this.changeRoomSupportingShareDisValue2 = changeRoomSupportingShareDisValue2;
	}
	public Date getChangeRoomSupportingShareDisStartDate2() {
		return changeRoomSupportingShareDisStartDate2;
	}
	public void setChangeRoomSupportingShareDisStartDate2(
			Date changeRoomSupportingShareDisStartDate2) {
		this.changeRoomSupportingShareDisStartDate2 = changeRoomSupportingShareDisStartDate2;
	}
	public BigDecimal getChangeTowerShareDisValue3() {
		return changeTowerShareDisValue3;
	}
	public void setChangeTowerShareDisValue3(BigDecimal changeTowerShareDisValue3) {
		this.changeTowerShareDisValue3 = changeTowerShareDisValue3;
	}
	public Date getChangeTowerShareDisStartDate3() {
		return changeTowerShareDisStartDate3;
	}
	public void setChangeTowerShareDisStartDate3(Date changeTowerShareDisStartDate3) {
		this.changeTowerShareDisStartDate3 = changeTowerShareDisStartDate3;
	}
	public BigDecimal getChangeRoomSupportingShareDisValue3() {
		return changeRoomSupportingShareDisValue3;
	}
	public void setChangeRoomSupportingShareDisValue3(
			BigDecimal changeRoomSupportingShareDisValue3) {
		this.changeRoomSupportingShareDisValue3 = changeRoomSupportingShareDisValue3;
	}
	public Date getChangeRoomSupportingShareDisStartDate3() {
		return changeRoomSupportingShareDisStartDate3;
	}
	public void setChangeRoomSupportingShareDisStartDate3(
			Date changeRoomSupportingShareDisStartDate3) {
		this.changeRoomSupportingShareDisStartDate3 = changeRoomSupportingShareDisStartDate3;
	}
	public BigDecimal getChangeMaintenanceFeeDisValue1() {
		return changeMaintenanceFeeDisValue1;
	}
	public void setChangeMaintenanceFeeDisValue1(
			BigDecimal changeMaintenanceFeeDisValue1) {
		this.changeMaintenanceFeeDisValue1 = changeMaintenanceFeeDisValue1;
	}
	public Date getChangeMaintenanceFeeDisStartDate1() {
		return changeMaintenanceFeeDisStartDate1;
	}
	public void setChangeMaintenanceFeeDisStartDate1(
			Date changeMaintenanceFeeDisStartDate1) {
		this.changeMaintenanceFeeDisStartDate1 = changeMaintenanceFeeDisStartDate1;
	}
	public BigDecimal getChangeStageFeeDisValue1() {
		return changeStageFeeDisValue1;
	}
	public void setChangeStageFeeDisValue1(BigDecimal changeStageFeeDisValue1) {
		this.changeStageFeeDisValue1 = changeStageFeeDisValue1;
	}
	public Date getChangeStageFeeDisStartDate1() {
		return changeStageFeeDisStartDate1;
	}
	public void setChangeStageFeeDisStartDate1(Date changeStageFeeDisStartDate1) {
		this.changeStageFeeDisStartDate1 = changeStageFeeDisStartDate1;
	}
	public BigDecimal getChangeElectricImportDisValue1() {
		return changeElectricImportDisValue1;
	}
	public void setChangeElectricImportDisValue1(
			BigDecimal changeElectricImportDisValue1) {
		this.changeElectricImportDisValue1 = changeElectricImportDisValue1;
	}
	public Date getChangeElectricImportDisStartDate1() {
		return changeElectricImportDisStartDate1;
	}
	public void setChangeElectricImportDisStartDate1(
			Date changeElectricImportDisStartDate1) {
		this.changeElectricImportDisStartDate1 = changeElectricImportDisStartDate1;
	}
	public BigDecimal getChangeMaintenanceFeeDisValue2() {
		return changeMaintenanceFeeDisValue2;
	}
	public void setChangeMaintenanceFeeDisValue2(
			BigDecimal changeMaintenanceFeeDisValue2) {
		this.changeMaintenanceFeeDisValue2 = changeMaintenanceFeeDisValue2;
	}
	public Date getChangeMaintenanceFeeDisStartDate2() {
		return changeMaintenanceFeeDisStartDate2;
	}
	public void setChangeMaintenanceFeeDisStartDate2(
			Date changeMaintenanceFeeDisStartDate2) {
		this.changeMaintenanceFeeDisStartDate2 = changeMaintenanceFeeDisStartDate2;
	}
	public BigDecimal getChangeStageFeeDisValue2() {
		return changeStageFeeDisValue2;
	}
	public void setChangeStageFeeDisValue2(BigDecimal changeStageFeeDisValue2) {
		this.changeStageFeeDisValue2 = changeStageFeeDisValue2;
	}
	public Date getChangeStageFeeDisStartDate2() {
		return changeStageFeeDisStartDate2;
	}
	public void setChangeStageFeeDisStartDate2(Date changeStageFeeDisStartDate2) {
		this.changeStageFeeDisStartDate2 = changeStageFeeDisStartDate2;
	}
	public BigDecimal getChangeElectricImportDisValue2() {
		return changeElectricImportDisValue2;
	}
	public void setChangeElectricImportDisValue2(
			BigDecimal changeElectricImportDisValue2) {
		this.changeElectricImportDisValue2 = changeElectricImportDisValue2;
	}
	public Date getChangeElectricImportDisStartDate2() {
		return changeElectricImportDisStartDate2;
	}
	public void setChangeElectricImportDisStartDate2(
			Date changeElectricImportDisStartDate2) {
		this.changeElectricImportDisStartDate2 = changeElectricImportDisStartDate2;
	}
	public BigDecimal getChangeMaintenanceFeeDisValue3() {
		return changeMaintenanceFeeDisValue3;
	}
	public void setChangeMaintenanceFeeDisValue3(
			BigDecimal changeMaintenanceFeeDisValue3) {
		this.changeMaintenanceFeeDisValue3 = changeMaintenanceFeeDisValue3;
	}
	public Date getChangeMaintenanceFeeDisStartDate3() {
		return changeMaintenanceFeeDisStartDate3;
	}
	public void setChangeMaintenanceFeeDisStartDate3(
			Date changeMaintenanceFeeDisStartDate3) {
		this.changeMaintenanceFeeDisStartDate3 = changeMaintenanceFeeDisStartDate3;
	}
	public BigDecimal getChangeStageFeeDisValue3() {
		return changeStageFeeDisValue3;
	}
	public void setChangeStageFeeDisValue3(BigDecimal changeStageFeeDisValue3) {
		this.changeStageFeeDisValue3 = changeStageFeeDisValue3;
	}
	public Date getChangeStageFeeDisStartDate3() {
		return changeStageFeeDisStartDate3;
	}
	public void setChangeStageFeeDisStartDate3(Date changeStageFeeDisStartDate3) {
		this.changeStageFeeDisStartDate3 = changeStageFeeDisStartDate3;
	}
	public BigDecimal getChangeElectricImportDisValue3() {
		return changeElectricImportDisValue3;
	}
	public void setChangeElectricImportDisValue3(
			BigDecimal changeElectricImportDisValue3) {
		this.changeElectricImportDisValue3 = changeElectricImportDisValue3;
	}
	public Date getChangeElectricImportDisStartDate3() {
		return changeElectricImportDisStartDate3;
	}
	public void setChangeElectricImportDisStartDate3(
			Date changeElectricImportDisStartDate3) {
		this.changeElectricImportDisStartDate3 = changeElectricImportDisStartDate3;
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
	public Act getAct() {
		return act;
	}
	public void setAct(Act act) {
		this.act = act;
	}
}