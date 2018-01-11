package com.xunge.comm.tower;


/**
 * @descript常量数据
 * @author wagnz
 * @date 2017-09-25 11:15:16
 */
public class CommonData {
	
	/**
	 *  确认状态
	 */
	public static final String CONFIRM_STATE = "COMMITTED_STATE";
	/**
	 *  产品大类
	 */
	public static final String PRODUCT_BIG_TYPE = "PRODUCT_CATEGORY";
	/**
	 * 业务属性
	 */
	public static final String SERVICE_ATTRIBUTE = "SERVICE_ATTRIBUTE";// 业务属性
	/**
	 * 机房产品
	 */
	public static final String COMPUTER_PRODUCTS = "COMPUTER_PRODUCTS";//机房产品
	/**
	 * 油机发电模式
	 */
	public static final String OIL_MACHINE_MODE = "OIL_MACHINE_MODE"; //油机发电模式
	/**
	 * 订单属性
	 */
	public static final String ORDER_ATTRIBUTE = "ORDER_ATTRIBUTE";	//订单属性
	/**
	 * 产权属性
	 */
	public static final String PROPERTY_RIGHTS = "PROPERTY_RIGHTS";//产权属性
	/**
	 * 原产权方
	 */
	public static final String CARRIER_CATEGORY = "CARRIER_CATEGORY";//原产权方
	/**
	 * 产品类型|铁塔产品
	 */
	public static final String TOWER_PRODUCTS = "TOWER_PRODUCTS";//	铁塔产品
	
	/**
	 * 变更项目
	 */
	public static final String CHANGE_ITEM = "CHANGE_ITEM";
	
	/**
	 * 共享信息
	 */
	public static final String SHARE_INFO = "SHARE_INFO";
	/**
	 * 场景划分
	 */
	public static final String SCENE_CLASSIFICATION = "SCENE_CLASSIFICATION";

	/**
	 * 油机发电服务费模式
	 */
	public static final String OIL_GENERATOR = "OIL_GENERATOR";
	/**
	 * 维护等级
	 */
	public static final String MAINTENANCE_ECHELON = "MAINTENANCE_ECHELON";
	/**
	 * 电力保障服务费模式
	 */
	public static final String POWER_PROTECTE_MODE = "POWER_PROTECTE_MODE";
	/**
	 * 场地费模式 
	 */
	public static final String SITE_FEE_MODEL = "SITE_FEE_MODEL";
	/**
	 * 电力引入费模式 
	 */
	public static final String POWER_INTRODUCE_FEED = "POWER_INTRODUCE_FEED";

	/**
	 * 响应成功编码 
	 */
	public static final String RESPONSE_CODE = "000000";
	
	/**
	 * 下载登记
	 */
	public static final String DOWNLOAD_REGISTER = "2";
	
	/**
	 * 下载完成
	 */
	public static final String DOWNLOAD_FINISH = "4";
	
	/**
	 * 交易结果 成功
	 */
	public static final String STATUS_SUCCESS = "1";
	
	/**
	 * 交易结果 失败
	 */
	public static final String STATUS_FAILED = "2";
	
	/**
	 * 账单类型 1 铁塔账单 2移动塔账单
	 */
	public static final String TOWER_BILL = "1";
	
	/**
	 * 区域状态（0：正常   9：停用）
	 */
	public static final int REGION_STATE = 0;
	
	/**
	 * 数据字典中的文件类型查询条件
	 */
	public static final String DIC_FILE_TYPE = "001";
	
	/**
	 * 默认发起流程用户
	 */
	public static final String DEFAULT_USER = "jtadmin";
	
	public static final String ADMIN_USER = "admin";
	
	/**
	 * 判断是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
