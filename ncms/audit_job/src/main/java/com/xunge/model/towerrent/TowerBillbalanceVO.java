package com.xunge.model.towerrent;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

import com.xunge.comm.StateComm;
import com.xunge.util.SysUUID;

/**
 * 塔维、移动账单表
 * @author wz
 * @version 2017-09-20 11:19:33
 */
public class TowerBillbalanceVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public TowerBillbalanceVO() {
		this.towerbillbalanceId = SysUUID.generator();
		this.createTime = new Date();
		this.checkState = StateComm.STATE_str__1;
	}
	
	private String towerbillbalanceId;		// 租赁费账单表编码
	
	private String resourcesTypeId;		// 资源类型（1：原产权方，2：既有共享，3：存量自改，4：存量改造，5：新建铁塔）
	@Excel(name="账期月份",orderNum="1",format="yyyyMM",databaseFormat="yyyyMM",isImportField="true")
	private String accountPeroid;		// 账期月份
	
	private String dataType;		// 数据类型（1：铁塔账单 2：移动账单）
	
	@Excel(name="产品业务确认单编号",orderNum="3",isImportField="true")
	private String businessConfirmNumber;		// 业务确认单编号
	
	@Excel(name="运营商",orderNum="5")
	private String operator;		// 运营商
	
	@Excel(name="运营商区县",orderNum="7")
	private String operatorRegId;		// 运营商地区编码
	
	@Excel(name="需求承接地市",orderNum="9")
	private String agreeAreaName;		// 需求承接地市
	
	@Excel(name="站址所属地市",orderNum="11")
	private String towerStationAreaName;		// 站址所属地市
	
	@Excel(name="站址编码",orderNum="15")
	private String towerStationCode;		// 铁塔站址编码
	
	@Excel(name="站址名称",orderNum="13")
	private String towerStationName;		// 铁塔站址名称
	
	@Excel(name="需求确认单编号",orderNum="17")
	private String agreeBillCode;		// 需求确认单编号

	@Excel(name="业务属性",orderNum="19")
	private String serviceAttribute;		// 业务属性

	@Excel(name="服务起始日期",orderNum="21",format="yyyy-MM-dd")
	private Date startDate;		// 服务起始日期

	@Excel(name="产品类型",orderNum="23")
	private String productTypeId;		// 产品类型

	@Excel(name="机房类型",orderNum="25")
	private String roomTypeId;		// 机房类型

	@Excel(name="油机发电模式",orderNum="29")
	private String oilGenerateElectricMethodId;		// 油机发电模式

	@Excel(name="油机发电服务费（包干）（出账费用）",orderNum="31")
	private BigDecimal usePowerServiceFeeOut;		// 油机发电服务费（包干）（出账费用）
	@Excel(name="油机发电服务费（包干）（历史调增/已抵扣费用）")
	private BigDecimal oilGeneratorElectricFeeDeductible;		// 油机发电服务费（包干）（历史调增/已抵扣费用）
	@Excel(name="油机发电服务费（包干）（出账费用+历史调增/已抵扣费用）")
	private BigDecimal oilGeneratorElectricFee;		// 油机发电服务费（包干）（出账费用+历史调增/已抵扣费用）
	@Excel(name="油机发电服务费（包干）（历史待退回费用）")
	private BigDecimal oilGeneratorElectricFeeWaitReturn;		// 油机发电服务费（包干）（历史待退回费用）
	

	@Excel(name="超过10%高等级服务站址额外维护服务费（出账费用）",orderNum="33")
	private BigDecimal hightLevelFeeOut;		// 超过10%高等级服务站址额外维护服务费（出账费用）
	@Excel(name="超过10%高等级服务站址额外维护服务费（历史调增/已抵扣费用）")
	private BigDecimal hightLevelFeeDeductible;		// 超过10%高等级服务站址额外维护服务费（历史调增/已抵扣费用）
	@Excel(name="超过10%高等级服务站址额外维护服务费（出账费用+历史调增/已抵扣费用）")
	private BigDecimal hightLevelFee;		// 超过10%高等级服务站址额外维护服务费（出账费用+历史调增/已抵扣费用）
	@Excel(name="超过10%高等级服务站址额外维护服务费（历史待退回费用）")
	private BigDecimal hightLevelFeeWaitReturn;		// 超过10%高等级服务站址额外维护服务费（历史待退回费用）

	@Excel(name="蓄电池额外保障费（出账费用）",orderNum="35")
	private BigDecimal batteryprotectionfeeOut;		// 蓄电池额外保障费（出账费用）
	@Excel(name="蓄电池额外保障费（历史调增/已抵扣费用）")
	private BigDecimal batteryprotectionfeeDeductible;		// 蓄电池额外保障费（历史调增/已抵扣费用）
	@Excel(name="蓄电池额外保障费（出账费用+历史调增/已抵扣费用）")
	private BigDecimal batteryprotectionfee;		// 蓄电池额外保障费（出账费用+历史调增/已抵扣费用）
	@Excel(name="蓄电池额外保障费（历史待退回费用）")
	private BigDecimal batteryprotectionfeeWaitReturn;		// 蓄电池额外保障费（历史待退回费用）
	

	@Excel(name="产品单元数1",orderNum="37")
	private String unitProductNumber1;		// 产品单元数1
	@Excel(name="对应实际最高天线挂高（米）1",orderNum="39")
	private String height1;		// 对应实际最高天线挂高（米）1
	
	@Excel(name="RRU拉远时BBU是否放在铁塔公司机房1",orderNum="41",replace={"是_1","否_0"})
	private int ifbbuOnRoom1;		// RRU拉远时BBU是否放在铁塔公司机房1

	@Excel(name="其他折扣1",orderNum="43")
	private String otherDis1;		// 其他折扣1
	
	@Excel(name="对应铁塔基准价格1",orderNum="45")
	private String towerPrice1;		// 对应铁塔基准价格1

	@Excel(name="产品单元数2",orderNum="47")
	private String unitProductNumber2;		// 产品单元数2
	@Excel(name="实际最高天线挂高（米）2",orderNum="49")
	private String height2;		// 实际最高天线挂高（米）2

	@Excel(name="RRU拉远时BBU是否放在铁塔公司机房2",orderNum="51",replace={"是_1","否_0"})
	private Integer ifbbuOnRoom2;		// RRU拉远时BBU是否放在铁塔公司机房2
	
	@Excel(name="其他折扣2",orderNum="53")
	private String otherDis2;		// 其他折扣2

	@Excel(name="对应铁塔基准价格2",orderNum="57")
	private String towerPrice2;		// 对应铁塔基准价格2

	@Excel(name="产品单元数3",orderNum="59")
	private String unitProductNumber3;		// 产品单元数3

	@Excel(name="实际最高天线挂高（米）3",orderNum="61")
	private String height3;		// 实际最高天线挂高（米）3

	@Excel(name="RRU拉远时BBU是否放在铁塔公司机房3",orderNum="63",replace={"是_1","否_0"})
	private Integer ifbbuOnRoom3;		// RRU拉远时BBU是否放在铁塔公司机房3

	@Excel(name="其他折扣3",orderNum="65")
	private String otherDis3;		// 其他折扣3

	@Excel(name="对应铁塔基准价格3",orderNum="67")
	private BigDecimal towerPrice3;		// 对应铁塔基准价格3
	
	@Excel(name="期末铁塔共享用户数",orderNum="69")
	private Integer currentTowerShareNum;		// 期末铁塔共享用户数

	@Excel(name="铁塔共享运营商1的起租日期",orderNum="71",format="yyyy-MM-dd")
	private Date towerOperatorStartdate1;		// 铁塔共享运营商1的起租日期

	@Excel(name="铁塔共享运营商1起租后的共享折扣",orderNum="73")
	private String towerSupportingShareDis1;		// 铁塔共享运营商1起租后的共享折扣

	@Excel(name="铁塔共享运营商2的起租日期",orderNum="75",format="yyyy-MM-dd")
	private Date towerOperatorStartdate2;		// 铁塔共享运营商2的起租日期

	@Excel(name="铁塔共享运营商2起租后的共享折扣",orderNum="77")
	private String towerSupportingShareDis2;		// 铁塔共享运营商2起租后的共享折扣

	@Excel(name="期末铁塔共享后基准价格1+2+3（出账费用）",orderNum="79")
	private BigDecimal currentTowerShareSumPriceOut;		// 期末铁塔共享后基准价格1+2+3（出账费用）
	@Excel(name="期末铁塔共享后基准价格1+2+3（历史调增/已抵扣费用）")
	private BigDecimal currentTowerShareSumPriceDeductible;		// 期末铁塔共享后基准价格1+2+3（历史调增/已抵扣费用）
	@Excel(name="期末铁塔共享后基准价格1+2+3（出账费用+历史调增/已抵扣费用）")
	private BigDecimal currentTowerShareSumPrice;		// 期末铁塔共享后基准价格1+2+3（出账费用+历史调增/已抵扣费用）
	@Excel(name="期末铁塔共享后基准价格1+2+3（历史待退回费用）")
	private BigDecimal currentTowerShareSumPriceWaitReturn;		// 期末铁塔共享后基准价格1+2+3（历史待退回费用）、

	@Excel(name="对应机房基准价格1",orderNum="81")
	private BigDecimal roomBasePrice1;		// 对应机房基准价格1
	
	@Excel(name="对应机房基准价格2",orderNum="83")
	private BigDecimal roomBasePrice2;		// 对应机房基准价格2

	@Excel(name="对应机房基准价格3",orderNum="85")
	private BigDecimal roomBasePrice3;		// 对应机房基准价格3

	@Excel(name="期末机房共享用户数",orderNum="87")
	private Integer currentRoomShareNum;		// 期末机房共享用户数

	@Excel(name="机房共享运营商1的起租日期",orderNum="89",format="yyyy-MM-dd")
	private Date roomOperatorStartdate1;		// 机房共享运营商1的起租日期

	@Excel(name="机房共享运营商1起租后的共享折扣",orderNum="91")
	private String roomSupportingShareDis1;		// 机房共享运营商1起租后的共享折扣

	@Excel(name="机房共享运营商2的起租日期",orderNum="93",format="yyyy-MM-dd")
	private Date roomOperatorStartdate2;		// 机房共享运营商2的起租日期

	@Excel(name="机房共享运营商2起租后的共享折扣",orderNum="95")
	private String roomSupportingShareDis2;		// 机房共享运营商2起租后的共享折扣

	@Excel(name="期末机房共享后基准价格1+2+3（出账费用）")
	private BigDecimal currentRoomShareSumPriceOut;		// 期末机房共享后基准价格1+2+3（出账费用）
	@Excel(name="期末机房共享后基准价格1+2+3（历史调增/已抵扣费用）")
	private BigDecimal currentRoomShareSumPriceDeductible;		// 期末机房共享后基准价格1+2+3（历史调增/已抵扣费用）期末机房共享后基准价格1+2+3（历史调增/已抵扣费用）
	@Excel(name="期末机房共享后基准价格1+2+3（出账费用+历史调增/已抵扣费用）")
	private BigDecimal currentRoomShareSumPrice;		// 期末机房共享后基准价格1+2+3（出账费用+历史调增/已抵扣费用）
	@Excel(name="期末机房共享后基准价格1+2+3（历史待退回费用）")
	private BigDecimal currentRoomShareSumPriceWaitReturn;		// 期末机房共享后基准价格1+2+3（历史待退回费用）

	@Excel(name="对应配套基准价格1",orderNum="99")
	private BigDecimal supportingBasePrice1;		// 对应配套基准价格1

	@Excel(name="对应配套基准价格2",orderNum="101")
	private BigDecimal supportingBasePrice2;		// 对应配套基准价格2

	@Excel(name="对应配套基准价格3",orderNum="103")
	private BigDecimal supportingBasePrice3;		// 对应配套基准价格3

	@Excel(name="配套共享用户数",orderNum="105")
	private Integer currentSupportingShareNum;		// 配套共享用户数

	@Excel(name="配套共享运营商1的起租日期",orderNum="107",format="yyyy-MM-dd")
	private Date supportingOperatorStartdate1;		// 配套共享运营商1的起租日期

	@Excel(name="配套共享运营商1起租后的共享折扣",orderNum="109")
	private String supportingShareDis1;		// 配套共享运营商1起租后的共享折扣

	@Excel(name="配套共享运营商2的起租日期",orderNum="111",format="yyyy-MM-dd")
	private Date supportingOperatorStartdate2;		// 配套共享运营商2的起租日期

	@Excel(name="配套共享运营商2起租后的共享折扣",orderNum="113")
	private String supportingShareDis2;		// 配套共享运营商2起租后的共享折扣

	@Excel(name="配套共享后基准价格1+2+3（出账费用）",orderNum="115")
	private BigDecimal currentSupportingShareSumPriceOut;	// 配套共享后基准价格1+2+3（出账费用）
	@Excel(name="配套共享后基准价格1+2+3（历史调增/已抵扣费用）")
	private BigDecimal currentSupportingShareSumPriceDeductible;// 配套共享后基准价格1+2+3（历史调增/已抵扣费用）
	@Excel(name="配套共享后基准价格1+2+3（出账费用+历史调增/已抵扣费用）")
	private BigDecimal currentSupportingShareSumPrice;		// 配套共享后基准价格1+2+3（出账费用+历史调增/已抵扣费用）
	@Excel(name="配套共享后基准价格1+2+3（历史待退回费用）")
	private BigDecimal currentSupportingShareSumPriceWaitReturn;// 配套共享后基准价格1+2+3（历史待退回费用）

	@Excel(name="bbu安装在铁塔机房费（出账费用）",orderNum="117")
	private BigDecimal bbuOnRoomFeeBack;		// bbu安装在铁塔机房费（出账费用）
	@Excel(name="bbu安装在铁塔机房费（历史调增/已抵扣费用）")
	private BigDecimal bbuOnRoomFeeDeductible;		// bbu安装在铁塔机房费（历史调增/已抵扣费用）
	@Excel(name="bbu安装在铁塔机房费（出账费用+历史调增/已抵扣费用）")
	private BigDecimal bbuOnRoomFee;		// bbu安装在铁塔机房费（出账费用+历史调增/已抵扣费用）
	@Excel(name="bbu安装在铁塔机房费（历史待退回费用）")
	private BigDecimal bbuOnRoomFeeWaitReturn;		// bbu安装在铁塔机房费（历史待退回费用）

	@Excel(name="对应维护费1",orderNum="119")
	private BigDecimal managerFee1;		// 对应维护费1
	
	@Excel(name="对应维护费2",orderNum="121")
	private BigDecimal managerFee2;		// 对应维护费2
	
	@Excel(name="对应维护费3",orderNum="123")
	private BigDecimal managerFee3;		// 对应维护费3

	@Excel(name="维护费共享用户数",orderNum="125")
	private Integer managerFeeShareNum;		// 维护费共享用户数

	@Excel(name="维护费共享运营商1的起租日期",orderNum="127",format="yyyy-MM-dd")
	private Date managerFeeOperatorStartdate1;		// 维护费共享运营商1的起租日期

	@Excel(name="维护费共享运营商1起租后的共享折扣",orderNum="129")
	private String managerFeeShareDis1;		// 维护费共享运营商1起租后的共享折扣

	@Excel(name="维护费共享运营商2的起租日期",orderNum="131",format="yyyy-MM-dd")
	private Date managerFeeOperatorStartdate2;		// 维护费共享运营商2的起租日期

	@Excel(name="维护费共享运营商2起租后的共享折扣",orderNum="133")
	private String managerFeeShareDis2;		// 维护费共享运营商2起租后的共享折扣

	@Excel(name="维护费折扣后金额1+2+3（出账费用）",orderNum="135")
	private BigDecimal currentManagerFeeShareSumPriceOut;		// 维护费折扣后金额1+2+3（出账费用）
	@Excel(name="维护费折扣后金额1+2+3（历史调增/已抵扣费用）")
	private BigDecimal currentManagerFeeShareSumPriceDeductible;// 维护费折扣后金额1+2+3（历史调增/已抵扣费用）
	@Excel(name="维护费折扣后金额1+2+3（出账费用+历史调增/已抵扣费用）")
	private BigDecimal currentManagerFeeShareSumPrice;		// 维护费折扣后金额1+2+3（出账费用+历史调增/已抵扣费用）
	@Excel(name="维护费折扣后金额1+2+3（历史待退回费用）")
	private BigDecimal currentManagerFeeShareSumPriceWaitReturn;// 维护费折扣后金额1+2+3（历史待退回费用）
	

	@Excel(name="场地费",orderNum="137")
	private BigDecimal praceFee;		// 场地费

	@Excel(name="场地费共享用户数",orderNum="139")
	private Integer praceFeeShareNum;		// 场地费共享用户数

	@Excel(name="场地费共享运营商1的起租日期",orderNum="141",format="yyyy-MM-dd")
	private Date praceFeeOperatorStartdate1;		// 场地费共享运营商1的起租日期

	@Excel(name="场地费共享运营商1起租后的共享折扣",orderNum="143")
	private String praceFeeshareDis1;		// 场地费共享运营商1起租后的共享折扣

	@Excel(name="场地费共享运营商2的起租日期",orderNum="145",format="yyyy-MM-dd")
	private Date praceFeeOperatorStartdate2;		// 场地费共享运营商2的起租日期

	@Excel(name="场地费共享运营商2起租后的共享折扣",orderNum="147")
	private String praceFeeShareDis2;		// 场地费共享运营商2起租后的共享折扣

	@Excel(name="场地费折扣后金额（出账费用）",orderNum="149")
	private BigDecimal currentPraceFeeShareSumPriceOut;		// 场地费折扣后金额（出账费用）
	@Excel(name="场地费折扣后金额（历史调增/已抵扣费用）")
	private BigDecimal currentPraceFeeShareSumPriceDeductible;		// 场地费折扣后金额（历史调增/已抵扣费用）
	@Excel(name="场地费折扣后金额（出账费用+历史调增/已抵扣费用）")
	private BigDecimal currentPraceFeeShareSumPrice;		// 场地费折扣后金额（出账费用+历史调增/已抵扣费用）
	@Excel(name="场地费折扣后金额（历史待退回费用）")
	private BigDecimal currentPraceFeeShareSumPriceWaitReturn;		// 场地费折扣后金额（历史待退回费用）

	@Excel(name="电力引入费",orderNum="151")
	private BigDecimal powerInFee;		// 电力引入费

	@Excel(name="电力引入费共享用户数",orderNum="153")
	private Integer powerInFeeShareNum;		// 电力引入费共享用户数

	@Excel(name="电力引入费共享运营商1的起租日期",orderNum="155",format="yyyy-MM-dd")
	private Date powerInFeeOperatorStartdate1;		// 电力引入费共享运营商1的起租日期

	@Excel(name="电力引入费共享运营商1起租后的共享折扣",orderNum="157")
	private String powerInFeeShareDis1;		// 电力引入费共享运营商1起租后的共享折扣

	@Excel(name="电力引入费共享运营商2的起租日期",orderNum="159",format="yyyy-MM-dd")
	private Date powerInFeeOperatorStartdate2;		// 电力引入费共享运营商2的起租日期

	@Excel(name="电力引入费共享运营商2起租后的共享折扣",orderNum="161")
	private String powerInFeeShareDis2;		// 电力引入费共享运营商2起租后的共享折扣

	@Excel(name="电力引入费折扣后金额（出账费用）",orderNum="163")
	private BigDecimal currentPowerInFeeShareSumPriceOut;		// 电力引入费折扣后金额（出账费用）
	@Excel(name="电力引入费折扣后金额（历史调增/已抵扣费用）")
	private BigDecimal currentPowerInFeeShareSumPriceDeductible;		// 电力引入费折扣后金额（历史调增/已抵扣费用）
	@Excel(name="电力引入费折扣后金额（出账费用+历史调增/已抵扣费用）")
	private BigDecimal currentPowerInFeeShareSumPrice;		// 电力引入费折扣后金额（出账费用+历史调增/已抵扣费用）
	@Excel(name="电力引入费折扣后金额（历史待退回费用）")
	private BigDecimal currentPowerInFeeShareSumPriceWaitDeductible;		// 电力引入费折扣后金额（历史待退回费用）

	@Excel(name="WLAN费用（出账费用）",orderNum="165")
	private BigDecimal wlanFeeOut;		// WLAN费用（出账费用）
	@Excel(name="WLAN费用（历史调增/已抵扣费用）")
	private BigDecimal wlanFeeDeductible;		// WLAN费用（历史调增/已抵扣费用）
	@Excel(name="WLAN费用（出账费用+历史调增/已抵扣费用）")
	private BigDecimal wlanFee;		// WLAN费用（出账费用+历史调增/已抵扣费用）
	@Excel(name="WLAN费用（历史待退回费用）")
	private BigDecimal wlanFeeWaitReturn;		// WLAN费用（历史待退回费用）

	@Excel(name="微波费用",orderNum="167")
	private BigDecimal microwaveFeeOut;		// 微波费用（出账费用）
	@Excel(name="微波费用（历史调增/已抵扣费用）")
	private BigDecimal microwaveFeeDeductible;		// 微波费用（历史调增/已抵扣费用）
	@Excel(name="微波费用（出账费用+历史调增/已抵扣费用）")
	private BigDecimal microwaveFee;		// 微波费用（出账费用+历史调增/已抵扣费用）
	@Excel(name="微波费用（历史待退回费用）")
	private BigDecimal microwaveFeeWaitReturn;		// 微波费用（历史待退回费用）
	@Excel(name="其他费用1（出账费用）",orderNum="169")
	private BigDecimal otherFee1Out;		// 其他费用1（出账费用）
	@Excel(name="其他费用1（历史调增/已抵扣费用）")
	private BigDecimal otherFee1Deductible;		// 其他费用1（历史调增/已抵扣费用）
	@Excel(name="其他费用1（出账费用+历史调增/已抵扣费用）")
	private BigDecimal otherFee1;		// 其他费用1（出账费用+历史调增/已抵扣费用）
	@Excel(name="其他费用1（历史待退回费用）")
	private BigDecimal otherFee1WaitReturn;		// 其他费用1（历史待退回费用）

	@Excel(name="产品服务费合计（出账费用）（不含税）",orderNum="171")
	private BigDecimal totalAmountMonthOut;		// 产品服务费合计（出账费用）（不含税）
	@Excel(name="产品服务费合计（历史调增/已抵扣费用）（不含税）")
	private BigDecimal totalAmountMonthDeductible;		// 产品服务费合计（历史调增/已抵扣费用）（不含税）
	@Excel(name="产品服务费合计（出账费用+历史调增/已抵扣费用）（不含税）")
	private BigDecimal totalAmountMonth;		// 产品服务费合计（出账费用+历史调增/已抵扣费用）（不含税）
	@Excel(name="产品服务费合计（历史待退回费用）（不含税）")
	private BigDecimal totalAmountMonthWaitReturn;		// 产品服务费合计（历史待退回费用）（不含税）
	@Excel(name="产品服务费与上月相比是否变化")
	private String ifServicefeeChange;		// 产品服务费与上月相比是否变化
	@Excel(name="产品服务费与上月相比的变化量")
	private String servicefeeChange;		// 产品服务费与上月相比的变化量

	private Integer confirmState;		// 确认状态（1：正常 2:争议账单3：调整确认后账单）
	@Excel(name="确认状态（调整）",orderNum="173")
	private String confirmStateStr;
	@Excel(name="电费（包干）（出账费用）")
	private BigDecimal elecFeeOut;		// 电费（包干）（出账费用）
	@Excel(name="电费（包干）（历史调增/已抵扣费用）")
	private BigDecimal elecFeeDeductible;		// 电费（包干）（历史调增/已抵扣费用）
	@Excel(name="电费（包干）（出账费用+历史调增/已抵扣费用）")
	private BigDecimal elecFee;		// 电费（包干）（出账费用+历史调增/已抵扣费用）
	@Excel(name="电费（包干）（历史待退回费用）")
	private BigDecimal elecFeeWaitReturn;		// 电费（包干）（历史待退回费用）
	@Excel(name="油机发电服务费（非包干）（出账费用）")
	private BigDecimal usePowerServiceFeeOut2;		// 油机发电服务费（非包干）（出账费用）
	@Excel(name="油机发电服务费（非包干）（历史调增/已抵扣费用）")
	private BigDecimal oilGeneratorElectricFeeDeductible2;		// 油机发电服务费（非包干）（历史调增/已抵扣费用）
	@Excel(name="油机发电服务费（非包干）（出账费用+历史调增/已抵扣费用）")
	private BigDecimal oilGeneratorElectricFee2;		// 油机发电服务费（非包干）（出账费用+历史调增/已抵扣费用）
	@Excel(name="油机发电服务费（非包干）（历史待退回费用）")
	private BigDecimal oilGeneratorElectricFeeWaitReturn2;		// 油机发电服务费（非包干）（历史待退回费用）
	
	private BigDecimal totalActualAmount;		// 产品服务费月费用合计（含税）

	@Excel(name="调整费用项目",orderNum="175")
	private String adjustmentFeeType;		// 调整费用项目
	
	@Excel(name="费项的调整金额（正负表示）（不含税）",orderNum="177")
	private BigDecimal adjustmentFee;		// 费用项目的调整金额（正负表示）（不含税）

	@Excel(name="费项的调整后金额（不含税）",orderNum="179")
	private BigDecimal afterAdjustmentFee;		// 费用项目的调整后金额（不含税）

	@Excel(name="费项调整原因",orderNum="181")
	private String adjustmentFeeNote;		// 费用项目调整原因

	@Excel(name="费项的争议金额（不含税）",orderNum="183")
	private BigDecimal disputeFee;		// 费项的争议金额

	@Excel(name="是否为锚定用户",orderNum="185",replace={"是_1","否_0"})
	private Integer locaOperator;		// 是否为锚定用户
	
	private String reimbursementState;		// 汇总状态
	private String checkState;		// 审核状态
	@Excel(name="订单属性")
	private String orderProp;		// 订单属性
	@Excel(name="产权属性")
	private String rightProp;		// 产权属性
	@Excel(name="原产权方")
	private String oriRight;		// 原产权方
	private String resCompare;		// 对账结果（0费用一致，1铁塔侧账单高，2铁塔侧账单低）
	private String createUserId;		// 创建用户编码
	private Date createTime;		// 创建时间
	private String updateUserId;		// 更新用户编码
	private Date updateTime;		// 更新时间
	@Excel(name="罚责赠费合计（不含税）")
	private BigDecimal punishTotal;//罚责赠费合计（不含税）
	private String productBigType;// 产品大类
	
	public String getTowerbillbalanceId() {
		return towerbillbalanceId;
	}
	public void setTowerbillbalanceId(String towerbillbalanceId) {
		this.towerbillbalanceId = towerbillbalanceId;
	}
	public String getResourcesTypeId() {
		return resourcesTypeId;
	}
	public void setResourcesTypeId(String resourcesTypeId) {
		this.resourcesTypeId = resourcesTypeId;
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
	public String getAgreeAreaName() {
		return agreeAreaName;
	}
	public void setAgreeAreaName(String agreeAreaName) {
		this.agreeAreaName = agreeAreaName;
	}
	public String getTowerStationAreaName() {
		return towerStationAreaName;
	}
	public void setTowerStationAreaName(String towerStationAreaName) {
		this.towerStationAreaName = towerStationAreaName;
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
	public String getServiceAttribute() {
		return serviceAttribute;
	}
	public void setServiceAttribute(String serviceAttribute) {
		this.serviceAttribute = serviceAttribute;
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
		this.productTypeId = productTypeId;
	}
	public String getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getOilGenerateElectricMethodId() {
		return oilGenerateElectricMethodId;
	}
	public void setOilGenerateElectricMethodId(String oilGenerateElectricMethodId) {
		this.oilGenerateElectricMethodId = oilGenerateElectricMethodId;
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
	public void setOilGeneratorElectricFeeDeductible(
			BigDecimal oilGeneratorElectricFeeDeductible) {
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
	public void setOilGeneratorElectricFeeWaitReturn(
			BigDecimal oilGeneratorElectricFeeWaitReturn) {
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
	public void setBatteryprotectionfeeDeductible(
			BigDecimal batteryprotectionfeeDeductible) {
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
	public void setBatteryprotectionfeeWaitReturn(
			BigDecimal batteryprotectionfeeWaitReturn) {
		this.batteryprotectionfeeWaitReturn = batteryprotectionfeeWaitReturn;
	}
	public String getUnitProductNumber1() {
		return unitProductNumber1;
	}
	public void setUnitProductNumber1(String unitProductNumber1) {
		this.unitProductNumber1 = unitProductNumber1;
	}
	public String getHeight1() {
		return height1;
	}
	public void setHeight1(String height1) {
		this.height1 = height1;
	}
	public Integer getIfbbuOnRoom1() {
		return ifbbuOnRoom1;
	}
	public void setIfbbuOnRoom1(Integer ifbbuOnRoom1) {
		this.ifbbuOnRoom1 = ifbbuOnRoom1;
	}
	public String getOtherDis1() {
		return otherDis1;
	}
	public void setOtherDis1(String otherDis1) {
		this.otherDis1 = otherDis1;
	}
	public String getTowerPrice1() {
		return towerPrice1;
	}
	public void setTowerPrice1(String towerPrice1) {
		this.towerPrice1 = towerPrice1;
	}
	public String getUnitProductNumber2() {
		return unitProductNumber2;
	}
	public void setUnitProductNumber2(String unitProductNumber2) {
		this.unitProductNumber2 = unitProductNumber2;
	}
	public String getHeight2() {
		return height2;
	}
	public void setHeight2(String height2) {
		this.height2 = height2;
	}
	public Integer getIfbbuOnRoom2() {
		return ifbbuOnRoom2;
	}
	public void setIfbbuOnRoom2(Integer ifbbuOnRoom2) {
		this.ifbbuOnRoom2 = ifbbuOnRoom2;
	}
	public String getOtherDis2() {
		return otherDis2;
	}
	public void setOtherDis2(String otherDis2) {
		this.otherDis2 = otherDis2;
	}
	public String getTowerPrice2() {
		return towerPrice2;
	}
	public void setTowerPrice2(String towerPrice2) {
		this.towerPrice2 = towerPrice2;
	}
	public String getUnitProductNumber3() {
		return unitProductNumber3;
	}
	public void setUnitProductNumber3(String unitProductNumber3) {
		this.unitProductNumber3 = unitProductNumber3;
	}
	public String getHeight3() {
		return height3;
	}
	public void setHeight3(String height3) {
		this.height3 = height3;
	}
	public Integer getIfbbuOnRoom3() {
		return ifbbuOnRoom3;
	}
	public void setIfbbuOnRoom3(Integer ifbbuOnRoom3) {
		this.ifbbuOnRoom3 = ifbbuOnRoom3;
	}
	public String getOtherDis3() {
		return otherDis3;
	}
	public void setOtherDis3(String otherDis3) {
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
	public String getTowerSupportingShareDis1() {
		return towerSupportingShareDis1;
	}
	public void setTowerSupportingShareDis1(String towerSupportingShareDis1) {
		this.towerSupportingShareDis1 = towerSupportingShareDis1;
	}
	public Date getTowerOperatorStartdate2() {
		return towerOperatorStartdate2;
	}
	public void setTowerOperatorStartdate2(Date towerOperatorStartdate2) {
		this.towerOperatorStartdate2 = towerOperatorStartdate2;
	}
	public String getTowerSupportingShareDis2() {
		return towerSupportingShareDis2;
	}
	public void setTowerSupportingShareDis2(String towerSupportingShareDis2) {
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
	public void setCurrentTowerShareSumPriceDeductible(
			BigDecimal currentTowerShareSumPriceDeductible) {
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
	public void setCurrentTowerShareSumPriceWaitReturn(
			BigDecimal currentTowerShareSumPriceWaitReturn) {
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
	public String getRoomSupportingShareDis1() {
		return roomSupportingShareDis1;
	}
	public void setRoomSupportingShareDis1(String roomSupportingShareDis1) {
		this.roomSupportingShareDis1 = roomSupportingShareDis1;
	}
	public Date getRoomOperatorStartdate2() {
		return roomOperatorStartdate2;
	}
	public void setRoomOperatorStartdate2(Date roomOperatorStartdate2) {
		this.roomOperatorStartdate2 = roomOperatorStartdate2;
	}
	public String getRoomSupportingShareDis2() {
		return roomSupportingShareDis2;
	}
	public void setRoomSupportingShareDis2(String roomSupportingShareDis2) {
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
	public void setCurrentRoomShareSumPriceDeductible(
			BigDecimal currentRoomShareSumPriceDeductible) {
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
	public void setCurrentRoomShareSumPriceWaitReturn(
			BigDecimal currentRoomShareSumPriceWaitReturn) {
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
	public String getSupportingShareDis1() {
		return supportingShareDis1;
	}
	public void setSupportingShareDis1(String supportingShareDis1) {
		this.supportingShareDis1 = supportingShareDis1;
	}
	public Date getSupportingOperatorStartdate2() {
		return supportingOperatorStartdate2;
	}
	public void setSupportingOperatorStartdate2(Date supportingOperatorStartdate2) {
		this.supportingOperatorStartdate2 = supportingOperatorStartdate2;
	}
	public String getSupportingShareDis2() {
		return supportingShareDis2;
	}
	public void setSupportingShareDis2(String supportingShareDis2) {
		this.supportingShareDis2 = supportingShareDis2;
	}
	public BigDecimal getCurrentSupportingShareSumPriceOut() {
		return currentSupportingShareSumPriceOut;
	}
	public void setCurrentSupportingShareSumPriceOut(
			BigDecimal currentSupportingShareSumPriceOut) {
		this.currentSupportingShareSumPriceOut = currentSupportingShareSumPriceOut;
	}
	public BigDecimal getCurrentSupportingShareSumPriceDeductible() {
		return currentSupportingShareSumPriceDeductible;
	}
	public void setCurrentSupportingShareSumPriceDeductible(
			BigDecimal currentSupportingShareSumPriceDeductible) {
		this.currentSupportingShareSumPriceDeductible = currentSupportingShareSumPriceDeductible;
	}
	public BigDecimal getCurrentSupportingShareSumPrice() {
		return currentSupportingShareSumPrice;
	}
	public void setCurrentSupportingShareSumPrice(
			BigDecimal currentSupportingShareSumPrice) {
		this.currentSupportingShareSumPrice = currentSupportingShareSumPrice;
	}
	public BigDecimal getCurrentSupportingShareSumPriceWaitReturn() {
		return currentSupportingShareSumPriceWaitReturn;
	}
	public void setCurrentSupportingShareSumPriceWaitReturn(
			BigDecimal currentSupportingShareSumPriceWaitReturn) {
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
	public String getManagerFeeShareDis1() {
		return managerFeeShareDis1;
	}
	public void setManagerFeeShareDis1(String managerFeeShareDis1) {
		this.managerFeeShareDis1 = managerFeeShareDis1;
	}
	public Date getManagerFeeOperatorStartdate2() {
		return managerFeeOperatorStartdate2;
	}
	public void setManagerFeeOperatorStartdate2(Date managerFeeOperatorStartdate2) {
		this.managerFeeOperatorStartdate2 = managerFeeOperatorStartdate2;
	}
	public String getManagerFeeShareDis2() {
		return managerFeeShareDis2;
	}
	public void setManagerFeeShareDis2(String managerFeeShareDis2) {
		this.managerFeeShareDis2 = managerFeeShareDis2;
	}
	public BigDecimal getCurrentManagerFeeShareSumPriceOut() {
		return currentManagerFeeShareSumPriceOut;
	}
	public void setCurrentManagerFeeShareSumPriceOut(
			BigDecimal currentManagerFeeShareSumPriceOut) {
		this.currentManagerFeeShareSumPriceOut = currentManagerFeeShareSumPriceOut;
	}
	public BigDecimal getCurrentManagerFeeShareSumPriceDeductible() {
		return currentManagerFeeShareSumPriceDeductible;
	}
	public void setCurrentManagerFeeShareSumPriceDeductible(
			BigDecimal currentManagerFeeShareSumPriceDeductible) {
		this.currentManagerFeeShareSumPriceDeductible = currentManagerFeeShareSumPriceDeductible;
	}
	public BigDecimal getCurrentManagerFeeShareSumPrice() {
		return currentManagerFeeShareSumPrice;
	}
	public void setCurrentManagerFeeShareSumPrice(
			BigDecimal currentManagerFeeShareSumPrice) {
		this.currentManagerFeeShareSumPrice = currentManagerFeeShareSumPrice;
	}
	public BigDecimal getCurrentManagerFeeShareSumPriceWaitReturn() {
		return currentManagerFeeShareSumPriceWaitReturn;
	}
	public void setCurrentManagerFeeShareSumPriceWaitReturn(
			BigDecimal currentManagerFeeShareSumPriceWaitReturn) {
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
	public String getPraceFeeshareDis1() {
		return praceFeeshareDis1;
	}
	public void setPraceFeeshareDis1(String praceFeeshareDis1) {
		this.praceFeeshareDis1 = praceFeeshareDis1;
	}
	public Date getPraceFeeOperatorStartdate2() {
		return praceFeeOperatorStartdate2;
	}
	public void setPraceFeeOperatorStartdate2(Date praceFeeOperatorStartdate2) {
		this.praceFeeOperatorStartdate2 = praceFeeOperatorStartdate2;
	}
	public String getPraceFeeShareDis2() {
		return praceFeeShareDis2;
	}
	public void setPraceFeeShareDis2(String praceFeeShareDis2) {
		this.praceFeeShareDis2 = praceFeeShareDis2;
	}
	public BigDecimal getCurrentPraceFeeShareSumPriceOut() {
		return currentPraceFeeShareSumPriceOut;
	}
	public void setCurrentPraceFeeShareSumPriceOut(
			BigDecimal currentPraceFeeShareSumPriceOut) {
		this.currentPraceFeeShareSumPriceOut = currentPraceFeeShareSumPriceOut;
	}
	public BigDecimal getCurrentPraceFeeShareSumPriceDeductible() {
		return currentPraceFeeShareSumPriceDeductible;
	}
	public void setCurrentPraceFeeShareSumPriceDeductible(
			BigDecimal currentPraceFeeShareSumPriceDeductible) {
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
	public void setCurrentPraceFeeShareSumPriceWaitReturn(
			BigDecimal currentPraceFeeShareSumPriceWaitReturn) {
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
	public String getPowerInFeeShareDis1() {
		return powerInFeeShareDis1;
	}
	public void setPowerInFeeShareDis1(String powerInFeeShareDis1) {
		this.powerInFeeShareDis1 = powerInFeeShareDis1;
	}
	public Date getPowerInFeeOperatorStartdate2() {
		return powerInFeeOperatorStartdate2;
	}
	public void setPowerInFeeOperatorStartdate2(Date powerInFeeOperatorStartdate2) {
		this.powerInFeeOperatorStartdate2 = powerInFeeOperatorStartdate2;
	}
	public String getPowerInFeeShareDis2() {
		return powerInFeeShareDis2;
	}
	public void setPowerInFeeShareDis2(String powerInFeeShareDis2) {
		this.powerInFeeShareDis2 = powerInFeeShareDis2;
	}
	public BigDecimal getCurrentPowerInFeeShareSumPriceOut() {
		return currentPowerInFeeShareSumPriceOut;
	}
	public void setCurrentPowerInFeeShareSumPriceOut(
			BigDecimal currentPowerInFeeShareSumPriceOut) {
		this.currentPowerInFeeShareSumPriceOut = currentPowerInFeeShareSumPriceOut;
	}
	public BigDecimal getCurrentPowerInFeeShareSumPriceDeductible() {
		return currentPowerInFeeShareSumPriceDeductible;
	}
	public void setCurrentPowerInFeeShareSumPriceDeductible(
			BigDecimal currentPowerInFeeShareSumPriceDeductible) {
		this.currentPowerInFeeShareSumPriceDeductible = currentPowerInFeeShareSumPriceDeductible;
	}
	public BigDecimal getCurrentPowerInFeeShareSumPrice() {
		return currentPowerInFeeShareSumPrice;
	}
	public void setCurrentPowerInFeeShareSumPrice(
			BigDecimal currentPowerInFeeShareSumPrice) {
		this.currentPowerInFeeShareSumPrice = currentPowerInFeeShareSumPrice;
	}
	public BigDecimal getCurrentPowerInFeeShareSumPriceWaitDeductible() {
		return currentPowerInFeeShareSumPriceWaitDeductible;
	}
	public void setCurrentPowerInFeeShareSumPriceWaitDeductible(
			BigDecimal currentPowerInFeeShareSumPriceWaitDeductible) {
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
	public String getIfServicefeeChange() {
		return ifServicefeeChange;
	}
	public void setIfServicefeeChange(String ifServicefeeChange) {
		this.ifServicefeeChange = ifServicefeeChange;
	}
	public String getServicefeeChange() {
		return servicefeeChange;
	}
	public void setServicefeeChange(String servicefeeChange) {
		this.servicefeeChange = servicefeeChange;
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
	public void setOilGeneratorElectricFeeDeductible2(
			BigDecimal oilGeneratorElectricFeeDeductible2) {
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
	public void setOilGeneratorElectricFeeWaitReturn2(
			BigDecimal oilGeneratorElectricFeeWaitReturn2) {
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
		this.adjustmentFeeType = adjustmentFeeType;
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
		this.adjustmentFeeNote = adjustmentFeeNote;
	}
	public BigDecimal getDisputeFee() {
		return disputeFee;
	}
	public void setDisputeFee(BigDecimal disputeFee) {
		this.disputeFee = disputeFee;
	}
	public Integer getLocaOperator() {
		return locaOperator;
	}
	public void setLocaOperator(Integer locaOperator) {
		this.locaOperator = locaOperator;
	}
	public String getReimbursementState() {
		return reimbursementState;
	}
	public void setReimbursementState(String reimbursementState) {
		this.reimbursementState = reimbursementState;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
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
	public String getResCompare() {
		return resCompare;
	}
	public void setResCompare(String resCompare) {
		this.resCompare = resCompare;
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
	
	@Override
	public String toString() {
		return "TowerBillbalanceVO [businessConfirmNumber="
				+ businessConfirmNumber + ", towerStationCode="
				+ towerStationCode + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((businessConfirmNumber == null) ? 0 : businessConfirmNumber
						.hashCode());
		result = prime
				* result
				+ ((towerStationCode == null) ? 0 : towerStationCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TowerBillbalanceVO other = (TowerBillbalanceVO) obj;
		if (businessConfirmNumber == null) {
			if (other.businessConfirmNumber != null)
				return false;
		} else if (!businessConfirmNumber.equals(other.businessConfirmNumber))
			return false;
		if (towerStationCode == null) {
			if (other.towerStationCode != null)
				return false;
		} else if (!towerStationCode.equals(other.towerStationCode))
			return false;
		return true;
	}

	/**
	 * 验证数据是不是符合规则
	 * @return
	 */
	public boolean verifyData(){
		
		return true;
	}
	public Integer getConfirmState() {
		return confirmState;
	}
	public void setConfirmState(Integer confirmState) {
		this.confirmState = confirmState;
	}
	public BigDecimal getPunishTotal() {
		return punishTotal;
	}
	public void setPunishTotal(BigDecimal punishTotal) {
		this.punishTotal = punishTotal;
	}
	public String getProductBigType() {
		return productBigType;
	}
	public void setProductBigType(String productBigType) {
		this.productBigType = productBigType;
	}
	public String getConfirmStateStr() {
		return confirmStateStr;
	}
	public void setConfirmStateStr(String confirmStateStr) {
		this.confirmStateStr = confirmStateStr;
	}
}