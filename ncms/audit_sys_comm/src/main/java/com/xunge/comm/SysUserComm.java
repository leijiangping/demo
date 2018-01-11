package com.xunge.comm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SongJL
 * @date 2017年6月5日
 * @description 用户相关信息常量类
 */
public class SysUserComm {

	/**
	 * M01：集团用户
	 */
	public static String UCLASS_M01 = "M01";

	/**
	 * M02：省级用户
	 */
	public static String UCLASS_M02 = "M02";
	
	/**
	 * 0：集团用户
	 */
	public static int UCLASS_0 = 0;
	
	/**
	 * 1：省级用户
	 */
	public static int UCLASS_1 = 1;
	
	/**
	 * 默认密码
	 */
	
	public static String UPASSWORD = "Cmcc@10086";
	
	/**
	 * 直辖市:北京 天津 上海 重庆
	 */
	public static List<String> provLevelCityList = 
			new ArrayList<String>(Arrays.asList("110000", "120000", "310000", "500000"));
}
