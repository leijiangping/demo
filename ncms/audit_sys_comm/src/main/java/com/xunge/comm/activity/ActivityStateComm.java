package com.xunge.comm.activity;

/**
 * @author Yuefy
 * @date 2017年7月4日
 * @description 流程状态公有变量
 */
public class ActivityStateComm {
	/**
	 * 审核状态变量名 "state"
	 */
	public static String ACTIVITY_VARIABLE_NAME="state";

	/**
	 * 当前审核人变量名 "currUserId"
	 */
	public static String ACTIVITY_VARIABLE_CURRUSER="currUserId";

	/**
	 * 下级审核人变量名 "nextUserId"
	 */
	public static String ACTIVITY_VARIABLE_NEXTUSER="nextUserId";
	/**
	 * 审核通过、正常状态数据:0
	 */
	public static Integer STATE_NORMAL =0;
	
	/**
	 * 审核未通过、直接终止:8
	 */
	public static Integer STATE_UNAPPROVE =8;
	
	/**
	 * 审核中:9
	 */
	public static Integer STATE_AUDIT= 9;
	
	/**
	 * 未提交:-1
	 */
	public static Integer STATE_UNCOMMITTED =-1;
	
	/**
	 * 审核通过
	 */
	public static String AUDIT_NORMAL = "审核通过";
	
	/**
	 * 审核未通过
	 */
	public static String AUDIT_UNAPPROVE = "审核未通过";
	
	/**
	 * 审核中
	 */
	public static String AUDIT_AUDIT= "审核中";
	
	/**
	 * 未提交
	 */
	public static String AUDIT_UNCOMMITTED = "未提交";
	
}
