package com.xunge.comm;

import java.math.BigDecimal;

/**
 * @author changwq
 * @date 2017年7月27日
 * @description 扣罚信息共有变量
 */
public class PunishComm {
	
	/**
	 * 标准维护等级
	 */
	public static int LEVEL_0 = 0;
	public static String STR_LEVEL_0 = "0";
	/**
	 * 高维护等级
	 */
	public static int LEVEL_1 = 1;
	public static String STR_LEVEL_1 = "1";
	/**
	 * 高铁
	 */
	public static int COVER_1 = 1;
	/**
	 * 非高铁
	 */
	public static int COVER_0 = 0;
	/**
	 * 正常
	 */
	public static int STATE_0 = 0;
	/**
	 * 删除
	 */
	public static int STATE_1 = 1;
	/**
	 * 免责
	 */
	public static int DUTY_1 = 1;
	/**
	 * 不免责
	 */
	public static int DUTY_0 = 1;
	/**
	 * 标准维护等级停电退服标准
	 */
	public static BigDecimal NORMAL_TIME = new BigDecimal(12.55);
	/**
	 * 高维护等级停电退服标准
	 */
	public static BigDecimal HIGH_TIME = new BigDecimal(8.6);
}
