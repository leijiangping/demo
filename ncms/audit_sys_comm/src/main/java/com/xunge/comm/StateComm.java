package com.xunge.comm;

/**
 * @author SongJL
 * @date 2017年6月5日
 * @description 返回公有变量
 */
public class StateComm {
	
	/**
	 * 可用
	 */
	public static int STATE_0 = 0;
	public static String STATE_str0 = "0";
	/**
	 * 审核可用
	 */
	public static int STATE_1 = 1;
	public static String STATE_str1 = "1";
	
	/**
	 * 已删除、未审核
	 */
	public static int STATE__1 = -1;
	public static String STATE_str__1 = "-1";
	
	/**
	 * 停用
	 */
	public static int STATE_9 = 9;
	public static String STATE_str9 = "9";
	/**
	 * checkbox:可用
	 */
	public static String CHECKBOX_STATE_0 = "0";
	/**
	 * checkbox:不可用
	 */
	public static String CHECKBOX_STATE_1 = "1";
	
	/**
	 * 分页_1
	 */
	public static String PAGE_NUMBER_STR = "1";
	
	/**
	 * 每页显示数目_10
	 */
	public static String PAGE_SIZE_STR = "10";
	
	/**
	 * 文字--正常
	 */
	public static String NORMAL_STR = "正常";
	
	/**
	 * 文字--停用
	 */
	public static String STOP_STR = "停用";
	
	
}
