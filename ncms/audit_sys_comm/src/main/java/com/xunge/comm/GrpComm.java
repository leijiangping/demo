package com.xunge.comm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author changwq
 * @date 2017年8月11日
 * @description 信息收集公用变量
 */
public class GrpComm {
	//集团派发状态
	/**
	 * 0：草稿
	 */
	public static int SEND_0 = 0;
	/**
	 * 1：集团收集中
	 */
	public static int SEND_1 = 1;
	/**
	 * 11：已完结
	 */
	public static int SEND_11 = 11;
	
	//省级上报状态
	/**
	 * 11：已完结
	 */
	public static int COMMIT_11 = 11;
	/**
	 * 1：集团审批中
	 */
	public static int COMMIT_1 = 1;
	/**
	 * 0：待签收
	 */
	public static int COMMIT_0 = 0;
	/**
	 * -1：已驳回
	 */
	public static int COMMIT__1 = -1;
	/**
	 * 9：已签收
	 */
	public static int COMMIT_9 = 9;
	/**
	 * 8：已被他人签收
	 */
	public static int COMMIT_8 = 8;
	
	//入库状态
	/**
	 * 0：未入库
	 */
	public static int IN_0 = 0;
	/**
	 * 1：已入库
	 */
	public static int IN_1 = 1;
	
	
	//按钮操作状态
	/**
	 * 0：可操作
	 */
	public static int DO_0 = 0;
	/**
	 * 1：不可操作
	 */
	public static int DO_1 = 1;
	//集团数据类型模板
	/**
	 * 集团模板_数据类型_站点信息统计表 附件1：站点信息统计表-XX省-XX月-V2.xlsx
	 */
	public static String[] SUMBOINT = {"站点信息统计表-20170930-V2","/model/附件1：站点信息统计表-XX省-XX月-20170930-V2.xlsx","附件1：站点信息统计表-XX省-XX月-20170930-V2.xlsx"};
	/**
	 * 集团模板_数据类型_站点租赁费月统计报表
	 */
	public static String[] BOINTFEE = {"站点租赁费月统计报表-20170930-V2","/model/附件2：站点租赁费月统计报表-XX省-XX月-20170930-V2.xlsx","附件2：站点租赁费月统计报表-XX省-XX月-20170930-V2.xlsx"};
	/**
	 * 集团模板_数据类型_铁塔服务费月统计报表
	 */
	public static String[] TOWERFEEMONTH = {"铁塔服务费月统计报表-20170930-V2","/model/附件3：铁塔服务费月统计报表-XX省-XX月-20170930-V2.xlsx","附件3：铁塔服务费月统计报表-XX省-XX月-20170930-V2.xlsx"};
	/**
	 * 集团模板_数据类型_电费月统计报表
	 */
	public static String[] SUMELEFEEMONTH = {"电费月统计报表-20170930-V2","/model/附件4：电费月统计报表-XX省-XX月-20170930-V2.xls","附件4：电费月统计报表-XX省-XX月-20170930-V2.xls"};
	/**
	 * 集团模板_数据类型_其他
	 */
	public static String[] SUMOTHER = {"其他","",""};
	/**
	 * 集团模板_数据类型、路径、文件名集合
	 */
	public static List<String[]> DATATYPE = getDataType();
	
	private static List<String[]> getDataType(){
		List<String[]> a = new ArrayList<String[]>();
		a.add(SUMBOINT);
		a.add(BOINTFEE);
		a.add(TOWERFEEMONTH);
		a.add(SUMELEFEEMONTH);
		a.add(SUMOTHER);
		return a;
	}
}
