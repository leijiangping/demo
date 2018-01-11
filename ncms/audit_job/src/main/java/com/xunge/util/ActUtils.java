package com.xunge.util;

/**
 * 流程工具
 * @author zhujj
 * @version 2013-11-03
 */
public class ActUtils {

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("properties/activity.properties");
	
	/**
	 * 定义流程定义KEY，必须以“PD_”开头
	 */
	/**
	 * 租费合同审核
	 * 组成结构：string[]{"流程标识","业务主表表名","业务显示名称"}
	 * 调用方法：ActUtils.PD_SELFRENT_AUDIT[0]、ActUtils.PD_SELFRENT_AUDIT[1]、ActUtils.PD_SELFRENT_AUDIT[2]
	 */
	public static final String[] PD_SELFRENT_AUDIT = new String[]{loader.getProperty("SelfRentAudit.procDefKey"),loader.getProperty("SelfRentAudit.businessTable"),loader.getProperty("SelfRentAudit.title")};
	/**
	 * 报账点租费审核
	 * 组成结构：string[]{"流程标识","业务主表表名","业务显示名称"}
	 * 调用方法：ActUtils.PD_REMBURSE_POINT[0]、ActUtils.PD_REMBURSE_POINT[1]、ActUtils.PD_REMBURSE_POINT[2]
	 */
	public static final String[] PD_REMBURSE_POINT = new String[]{loader.getProperty("RembursePoint.procDefKey"),loader.getProperty("RembursePoint.businessTable"),loader.getProperty("RembursePoint.title")};
	/**
	 * 报账点审核
	 * 组成结构：string[]{"流程标识","业务主表表名","业务显示名称"}
	 * 调用方法：ActUtils.PD_REMBURSE_POINT[0]、ActUtils.PD_REMBURSE_POINT[1]、ActUtils.PD_REMBURSE_POINT[2]
	 */
	public static final String[] PD_RENTBILL_ACCOUNT = new String[]{loader.getProperty("RentBillAccount.procDefKey"),loader.getProperty("RentBillAccount.businessTable"),loader.getProperty("RentBillAccount.title")};
	/**
	 * 铁塔起租信息审核
	 * 组成结构：string[]{"流程标识","业务主表表名","业务显示名称"}
	 * 调用方法：ActUtils.PD_REMBURSE_POINT[0]、ActUtils.PD_REMBURSE_POINT[1]、ActUtils.PD_REMBURSE_POINT[2]
	 */
	public static final String[] PD_RESOURCE_INFO = new String[]{loader.getProperty("TwrRentInformationTower.procDefKey"),loader.getProperty("TwrRentInformationTower.businessTable"),loader.getProperty("TwrRentInformationTower.title")};
	
	/**
	 * 移动起租信息审核
	 * 组成结构：string[]{"流程标识","业务主表表名","业务显示名称"} 
	 * 调用方法：ActUtils.PD_INFORMATION_INFO [0]、ActUtils.PD_INFORMATION_INFO [1]、ActUtils.PD_INFORMATION_INFO [2]
	 */
	public static final String[] PD_INFORMATION_INFO = new String[]{loader.getProperty("TwrRentInformation.procDefKey"),loader.getProperty("TwrRentInformation.businessTable"),loader.getProperty("TwrRentInformation.title")};
	/**
	 * 铁塔信息变更审核
	 * 组成结构：string[]{"流程标识","业务主表表名","业务显示名称"}
	 * 调用方法：ActUtils.PD_BIZCHANGE_INFO [0]、ActUtils.PD_BIZCHANGE_INFO [1]、ActUtils.PD_BIZCHANGE_INFO [2]
	 */
	public static final String[] PD_BIZCHANGE_INFO = new String[]{loader.getProperty("TwrRentInformationBizChange.procDefKey"),loader.getProperty("TwrRentInformationBizChange.businessTable"),loader.getProperty("TwrRentInformationBizChange.title")};
	
	/**
	 * 费用汇总审核
	 * 组成结构：string[]{"流程标识","业务主表表名","业务显示名称"}
	 * 调用方法：ActUtils.PD_ACCOUNTSUMMARY_INFO [0]、ActUtils.PD_INFORMATION_INFO [1]、ActUtils.PD_INFORMATION_INFO [2]
	 */
	public static final String[] PD_ACCOUNTSUMMARY_INFO = new String[]{loader.getProperty("TwrAccountsummary.procDefKey"),loader.getProperty("TwrAccountsummary.businessTable"),loader.getProperty("TwrAccountsummary.title")};
	
	/**
	 * 铁塔服务终止审核
	 * 组成结构：string[]{"流程标识","业务主表表名","业务显示名称"}
	 * 调用方法：ActUtils.PD_STOPSERVER_INFO [0]、ActUtils.PD_STOPSERVER_INFO [1]、ActUtils.PD_STOPSERVER_INFO [2]
	 */
	public static final String[] PD_STOPSERVER_INFO = new String[]{loader.getProperty("TwrStopServer.procDefKey"),loader.getProperty("TwrStopServer.businessTable"),loader.getProperty("TwrStopServer.title")};
	/**
	 * 地市扣罚审核
	 * 组成结构：string[]{"流程标识","业务主表表名","业务显示名称"}
	 * 调用方法：ActUtils.PD_ACCOUNTSUMMARY_INFO [0]、ActUtils.PD_INFORMATION_INFO [1]、ActUtils.PD_INFORMATION_INFO [2]
	 */
	public static final String[] PD_TWRREGPUNISH_INFO = new String[]{loader.getProperty("TwrRegPunish.procDefKey"),loader.getProperty("TwrRegPunish.businessTable"),loader.getProperty("TwrRegPunish.title")};
	/**
	 * 省内扣罚审核
	 * 组成结构：string[]{"流程标识","业务主表表名","业务显示名称"}
	 * 调用方法：ActUtils.PD_ACCOUNTSUMMARY_INFO [0]、ActUtils.PD_INFORMATION_INFO [1]、ActUtils.PD_INFORMATION_INFO [2]
	 */
	public static final String[] PD_TWRREGPRVPUNISH_INFO = new String[]{loader.getProperty("TwrProvincePunish.procDefKey"),loader.getProperty("TwrProvincePunish.businessTable"),loader.getProperty("TwrProvincePunish.title")};
	/**
	 * 电费合同审核
	 * 组成结构：string[]{"流程标识","业务主表表名","业务显示名称"}
	 * 调用方法：ActUtils.PD_SELFELEC_AUDIT[0]、ActUtils.PD_SELFELEC_AUDIT[1]、ActUtils.PD_SELFELEC_AUDIT[2]
	 */
	public static final String[] PD_SELFELEC_AUDIT = new String[]{loader.getProperty("SelfElecAudit.procDefKey"),loader.getProperty("SelfElecAudit.businessTable"),loader.getProperty("SelfElecAudit.title")};
	/**
	 * 站点审核
	 * 组成结构：string[]{"流程标识","业务主表表名","业务显示名称"}
	 * 调用方法：ActUtils.PD_SITE_AUDIT[0]、ActUtils.PD_SITE_AUDIT[1]、ActUtils.PD_SITE_AUDIT[2]
	 */
	public static final String[] PD_SITE_AUDIT = new String[]{loader.getProperty("SiteAudit.procDefKey"),loader.getProperty("SiteAudit.businessTable"),loader.getProperty("SiteAudit.title")};
	/**
	 * 基础资源审核
	 * 组成结构：string[]{"流程标识","业务主表表名","业务显示名称"}
	 * 调用方法：ActUtils.PD_BASERESOURCE_AUDIT[0]、ActUtils.PD_BASERESOURCE_AUDIT[1]、ActUtils.PD_BASERESOURCE_AUDIT[2]
	 */
	public static final String[] PD_BASERESOURCE_AUDIT = new String[]{loader.getProperty("BaseResourceAudit.procDefKey"),loader.getProperty("BaseResourceAudit.businessTable"),loader.getProperty("BaseResourceAudit.title")};
	
	/**
	 * 电费报账点租费管理审核
	 */
	public static final String[] PD_ELEBILLACCOUNT = new String[]{loader.getProperty("EleBillaccount.procDefKey"),loader.getProperty("EleBillaccount.businessTable"),loader.getProperty("EleBillaccount.title")};
	
	/**
	 * 电费报账点缴费信息审核
	 */
	public static final String[] EleBillaccountPaymentdetail = new String[]{loader.getProperty("EleBillaccountPaymentdetail.procDefKey"),loader.getProperty("EleBillaccountPaymentdetail.businessTable"),loader.getProperty("EleBillaccountPaymentdetail.title")};
}
