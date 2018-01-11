package com.xunge.comm.tower;

import java.util.List;
import java.util.Map;

import com.xunge.model.towerrent.TowerBillbalanceVO;
import com.xunge.model.towerrent.TowerConfirmBillbalanceVO;
import com.xunge.model.towerrent.TowerRentInformationTowerVO;
import com.xunge.model.towerrent.TowerRentinformationBizchangeVO;
import com.xunge.model.towerrent.TowerStopServerVO;

/**
 * @descript数据校验类
 * @author wangz
 * @date 2017-09-28 11:15:49
 */
public class ValidataUtil {
	
	/**
	 * 铁塔起租信息数据验证
	 * @param vo
	 * @return
	 */
	public static String validateTowerRentinfo(TowerRentInformationTowerVO vo, Map<Integer, String> dicValueMap, List<String> allTowerLink){
		StringBuffer sb = new StringBuffer();
		// 区县ID数字判断
		if (!CommonData.isNumeric(vo.getRegId())) {
			sb.append("[" + vo.getRegId() + "]找不到对应的区县ID").append(";");
		}
		
		// 铁塔站址编码验证
		if (!allTowerLink.contains(vo.getTowerStationCode())) {
			sb.append("[" + vo.getTowerStationCode() + "]没有做关联").append(";");
		}

		/**
		 * 共享信息
		 *  01 新建首家
		 *  02 新建共享
		 *  03 存量产权方 
		 *  04 存量既有共享
		 *  05 存量新增共享
		 *  06 暂无
		 */
		if (vo.getResourcesTypeId() != null
				&& !vo.getResourcesTypeId().trim().equals("新建首家")
				&& !vo.getResourcesTypeId().trim().equals("新建共享")
				&& !vo.getResourcesTypeId().trim().equals("存量产权方")
				&& !vo.getResourcesTypeId().trim().equals("存量既有共享")
				&& !vo.getResourcesTypeId().trim().equals("存量新增共享")
				&& !vo.getResourcesTypeId().trim().equals("暂无")) {
			sb.append("[" + vo.getResourcesTypeId() + "]共享信息不正确").append(";");
		}
		/**
		 * 场景划分
		 * 01 密集市区 
		 * 02 一般市区 
		 * 03 县城 
		 * 04 乡镇
		 * 05 农村 
		 * 2220 高铁 
		 * 2210 地铁 
		 * 2240 其他
		 * 2230 公路
		 */
		int key = (CommonData.SCENE_CLASSIFICATION + "#" + vo.getScenceType()).hashCode();
		if (!dicValueMap.containsKey(key)) {
			sb.append("[" + vo.getScenceType() + "]场景划分数据不正确").append(";");
		}
		/**
		 * 铁塔产品 
		 * 1110 普通地面塔 
		 * 1130 简易塔 
		 * 1220 楼面抱杆 
		 * 1120 景观塔 
		 * 1210 普通楼面塔 
		 * 1910 简易塔微站
		 * 1920 小杆塔微站 
		 * 1930 抱杆微站 
		 * 1940 无杆塔微站 
		 * -1 无铁塔
		 */
		if (vo.getProductTypeId() != null
				&& !vo.getProductTypeId().trim().equals("普通地面塔")
				&& !vo.getProductTypeId().trim().equals("简易塔")
				&& !vo.getProductTypeId().trim().equals("楼面抱杆")
				&& !vo.getProductTypeId().trim().equals("景观塔")
				&& !vo.getProductTypeId().trim().equals("普通楼面塔")
				&& !vo.getProductTypeId().trim().equals("简易塔微站")
				&& !vo.getProductTypeId().trim().equals("小杆塔微站")
				&& !vo.getProductTypeId().trim().equals("抱杆微站")
				&& !vo.getProductTypeId().trim().equals("无杆塔微站")
				&& !vo.getProductTypeId().trim().equals("无铁塔")) {
			sb.append("[" + vo.getProductTypeId() + "]铁塔产品不正确").append(";");
		}
		/**
		 * 机房产品 
		 * 07 RRU拉远 
		 * 01 自建砖混机房
		 * 02 自建框架机房 
		 * 03 自建彩钢板机房 
		 * 04 一体化机柜 
		 * 05 租用机房 
		 * 06 其他机房 
		 * 08 一体化机房 
		 * -1 无机房
		 */
		if (vo.getRoomTypeId() != null 
				&& !vo.getRoomTypeId().trim().equals("RRU拉远")
				&& !vo.getRoomTypeId().trim().equals("自建砖混机房")
				&& !vo.getRoomTypeId().trim().equals("自建框架机房")
				&& !vo.getRoomTypeId().trim().equals("自建彩钢板机房")
				&& !vo.getRoomTypeId().trim().equals("一体化机柜")
				&& !vo.getRoomTypeId().trim().equals("租用机房")
				&& !vo.getRoomTypeId().trim().equals("其他机房")
				&& !vo.getRoomTypeId().trim().equals("一体化机房")
				&& !vo.getRoomTypeId().trim().equals("无机房")) {
			sb.append("[" + vo.getRoomTypeId() + "]机房产品不正确").append(";");
		}
		/**
		 * 油机发电服务费模式（0包干，3按次） 
		 * 0 包干 
		 * 3 按次
		 */
		if (vo.getOilGenerateElectricMethodId() != null
				&& !vo.getOilGenerateElectricMethodId().trim().equals("包干")
				&& !vo.getOilGenerateElectricMethodId().trim().equals("按次")) {
			sb.append("[" + vo.getOilGenerateElectricMethodId() + "]油机发电服务费模式不正确").append(";");
		}
		/**
		 * 维护等级 
		 * 01 高等级
		 * 02 标准 
		 * 03 高山海岛站 
		 * 04 不考核站
		 */
		if (vo.getMaintenanceLevelId() != null
				&& !vo.getMaintenanceLevelId().trim().equals("高等级")
				&& !vo.getMaintenanceLevelId().trim().equals("标准")
				&& !vo.getMaintenanceLevelId().trim().equals("高山海岛站")
				&& !vo.getMaintenanceLevelId().trim().equals("不考核站")) {
			sb.append("[" + vo.getMaintenanceLevelId()	+ "]维护等级不正确").append(";");
		}
		/**
		 * 电力保障服务费模式 
		 * 1 协助缴费（包干） 
		 * 2 协助缴费（转售） 
		 * 3 协助缴费（传导） 
		 * 4 协助缴费（代垫）
		 */
		if (vo.getElectricProtectionMethodId() != null
				&& !vo.getElectricProtectionMethodId().trim().equals("协助缴费（包干）")
				&& !vo.getElectricProtectionMethodId().trim().equals("协助缴费（转售）")
				&& !vo.getElectricProtectionMethodId().trim().equals("协助缴费（传导）")
				&& !vo.getElectricProtectionMethodId().trim().equals("协助缴费（代垫）")) {
			sb.append("[" + vo.getElectricProtectionMethodId() + "]电力保障服务费模式不正确").append(";");
		}

		/**
		 * 场地费模式 
		 * 0 包干 
		 * 1 逐站点定价
		 */
		if (vo.getRoomFeeMethod() != null 
				&& !vo.getRoomFeeMethod().trim().equals("包干")
				&& !vo.getRoomFeeMethod().trim().equals("逐站点定价")) {
			sb.append("[" + vo.getRoomFeeMethod() + "]场地费模式 不正确").append(";");
		}
		/**
		 * 电力引入费模式 
		 * 0 包干 
		 * 1 逐站点定价
		 */
		if (vo.getElecImportFeeMethod() != null
				&& !vo.getElecImportFeeMethod().trim().equals("包干")
				&& !vo.getElecImportFeeMethod().trim().equals("逐站点定价")) {
			sb.append("[" + vo.getElecImportFeeMethod() + "]电力引入费模式不正确").append(";");
		}
		return sb.toString();
	}

	/**
	 * 铁塔账单数据验证
	 * @param vo
	 * @return
	 */
	public static String validateTowerBalance(TowerBillbalanceVO vo){
		StringBuffer sb = new StringBuffer();
		// 运营商区县ID不是数字
		if (!CommonData.isNumeric(vo.getOperatorRegId())) {
			sb.append("["+vo.getOperatorRegId()+"]无法找到对应的区县ID").append(";");
		}
		// 业务属性  0:塔 1:非标
		if (vo.getServiceAttribute() != null
				&& !vo.getServiceAttribute().trim().equals("塔") 
				&& !vo.getServiceAttribute().trim().equals("非标")) {
			sb.append("["+vo.getServiceAttribute()+"]业务属性不正确").append(";");
		}
		/**
		 * 产品类型 
		 * 	1110	普通地面塔
			1130	简易塔
			1220	楼面抱杆
			1120	景观塔
			1210	普通楼面塔
			1910	简易塔微站
			1920	小杆塔微站
			1930	抱杆微站
			1940	无杆塔微站
			-1	无铁塔
		 */
		if (vo.getProductTypeId() != null
				&& !vo.getProductTypeId().trim().equals("普通地面塔") 
				&& !vo.getProductTypeId().trim().equals("简易塔")
				&& !vo.getProductTypeId().trim().equals("楼面抱杆") 
				&& !vo.getProductTypeId().trim().equals("景观塔")
				&& !vo.getProductTypeId().trim().equals("普通楼面塔") 
				&& !vo.getProductTypeId().trim().equals("简易塔微站")
				&& !vo.getProductTypeId().trim().equals("小杆塔微站") 
				&& !vo.getProductTypeId().trim().equals("抱杆微站")
				&& !vo.getProductTypeId().trim().equals("无杆塔微站") 
				&& !vo.getProductTypeId().trim().equals("无铁塔")
			) {
			sb.append("["+vo.getProductTypeId()+"]产品类型不正确").append(";");
		}
		
		/**
		 * 机房产品 
		 * 07 RRU拉远 
		 * 01 自建砖混机房
		 * 02 自建框架机房 
		 * 03 自建彩钢板机房 
		 * 04 一体化机柜 
		 * 05 租用机房 
		 * 06 其他机房 
		 * 08 一体化机房 
		 * -1 无机房
		 */
		if (!vo.getRoomTypeId().trim().equals("RRU拉远")
				&& !vo.getRoomTypeId().trim().equals("自建砖混机房")
				&& !vo.getRoomTypeId().trim().equals("自建框架机房")
				&& !vo.getRoomTypeId().trim().equals("自建彩钢板机房")
				&& !vo.getRoomTypeId().trim().equals("一体化机柜")
				&& !vo.getRoomTypeId().trim().equals("租用机房")
				&& !vo.getRoomTypeId().trim().equals("其他机房")
				&& !vo.getRoomTypeId().trim().equals("一体化机房")
				&& !vo.getRoomTypeId().trim().equals("无机房")) {
			sb.append("[" + vo.getRoomTypeId() + "]机房产品不正确").append(";");
		}
		
		/**
		 *  油机发电模式
		 *  0	包干
			1	按次
		 */
		if (vo.getOilGenerateElectricMethodId() != null
				&& !vo.getOilGenerateElectricMethodId().trim().equals("包干") 
				&& !vo.getOilGenerateElectricMethodId().trim().equals("按次")) {
			sb.append("["+vo.getOilGenerateElectricMethodId()+"]油机发电模式不正确").append(";");
		}
		/**
		 *  订单属性 orderProp
		 *  0	原产权方
			1	既有共享
			2	新建
			3	存量改造
		 */ 
		if (vo.getOrderProp() != null 
				&& !vo.getOrderProp().trim().equals("原产权方") 
				&& !vo.getOrderProp( ).trim().equals("既有共享")
				&& !vo.getOrderProp().trim().equals("新建") 
				&& !vo.getOrderProp().trim().equals("存量改造")
			) {
			sb.append("["+vo.getOrderProp()+"]订单属性不正确").append(";");
		}
		/*
		 *  产权属性 rightProp
		 *  0	注入
			1	自建
		 */
		if (vo.getRightProp() != null 
				&& !vo.getRightProp().trim().equals("注入") 
				&& !vo.getRightProp().trim().equals("自建")) {
			sb.append("["+vo.getRightProp()+"]产权属性不正确").append(";");
		}
		// 原产权方 oriRight 移动,联通,电信
		if (vo.getOriRight() != null 
				&& !vo.getOriRight().trim().equals("移动") 
				&& !vo.getOriRight().trim().equals("联通")
				&& !vo.getOriRight().trim().equals("电信")) {
			sb.append("["+vo.getOriRight()+"]原产权方不正确").append(";");
		}
		
		/**
		 *  确认状态
		 *  0	确认
			1	调整
		 */
		if (vo.getConfirmStateStr() != null
				&& !vo.getConfirmStateStr().equals("确认") 
				&& !vo.getConfirmStateStr().equals("调整")) {
			sb.append("["+vo.getConfirmStateStr()+"确认状态不正确").append(";");
		}
		return sb.toString();
	}
	
	/**
	 * 铁塔终止服务数据验证
	 * @param vo
	 * @return
	 */
	public static String validateTowerStopServer(TowerStopServerVO vo){
		StringBuffer sb = new StringBuffer();
		// 区县id数字判断
		if (!CommonData.isNumeric(vo.getRegId())) {
			sb.append("["+ vo.getRegId() + "]无法找到对应的区县ID").append(";");
		}
		return sb.toString();
	}
	
	/**
	 * 铁塔业务变更数据验证
	 * @param vo
	 * @return
	 */
	public static String validateTowerBizChange(TowerRentinformationBizchangeVO vo, Map<Integer, String> dicValueMap){
		StringBuffer sb = new StringBuffer();
		// 区县id数字判断
		if (!CommonData.isNumeric(vo.getRegId())) {
			sb.append("["+ vo.getRegId() + "]无法找到对应的区县ID").append(";");
		}
		
		/**
		 * 变更项目
		 */
		int key = (CommonData.CHANGE_ITEM + "#" + vo.getChangeItem()).hashCode();
		if (!dicValueMap.containsKey(key)) {
			sb.append("["+ vo.getChangeItem() + "变更项目数据不正确").append(";");
		}
		return sb.toString();
	}
	
	/**
	 * 确认费用清单数据验证
	 * @param vo
	 * @return
	 */
	public static String validateTowerConfirmBalance(TowerConfirmBillbalanceVO vo){
		StringBuffer sb = new StringBuffer();
		// 运营商区县ID不是数字
		if (!CommonData.isNumeric(vo.getOperatorRegId())) {
			sb.append("["+vo.getOperatorRegId()+"]无法找到对应的区县ID").append(";");
		}
		/**
		 *  确认状态
		 *  0	确认
		 *  1	调整
		 */
		if (vo.getConfirmStateStr() != null 
				&& !vo.getConfirmStateStr().equals("确认")
				&& !vo.getConfirmStateStr().equals("调整")) {
			sb.append("["+vo.getConfirmStateStr()+"]确认状态不正确").append(";");
		}
		/**
		 * 产品大类
		 *  0	塔
			1	楼宇室分
			2	隧道室分
			3	传输类
			4	非标类
			5	微站类
		 */
		if (vo.getProductBigType() != null
				&& !vo.getProductBigType().equals("塔")
				&& !vo.getProductBigType().equals("楼宇室分")
				&& !vo.getProductBigType().equals("隧道室分")
				&& !vo.getProductBigType().equals("传输类")
				&& !vo.getProductBigType().equals("非标类")
				&& !vo.getProductBigType().equals("微站类")
				) {
			sb.append("["+vo.getProductBigType()+"]产品大类不正确").append(";");
		}
		return sb.toString();
	}
}
