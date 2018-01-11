
package com.xunge.service.activity.utils;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.apache.commons.lang3.StringUtils;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.util.Encodes;
import com.xunge.core.util.PropertiesLoader;
import com.xunge.model.activity.Act;
import com.xunge.model.system.user.SysRoleVO;
import com.xunge.model.system.user.SysUserVO;

/**
 * 流程工具
 * @author zhujj
 * @version 2013-11-03
 */
public class ActUtils {

//	private static Logger logger = LoggerFactory.getLogger(ActUtils.class);
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
	/**
	 * 获取流程表单URL
	 * @param formKey
	 * @param act 表单传递参数
	 * @return
	 */
	public static String getFormUrl(String formKey, Act act){
		StringBuilder formUrl = new StringBuilder();
		
//		String formServerUrl = Global.getConfig("activiti.form.server.url");
//		if (StringUtils.isBlank(formServerUrl)){
//			formUrl.append(Global.getAdminPath());
//		}else{
//			formUrl.append(formServerUrl);
//		}
		
		formUrl.append(formKey).append(formUrl.indexOf("?") == -1 ? "?" : "&");
		formUrl.append("act.taskId=").append(act.getTaskId() != null ? act.getTaskId() : "");
		formUrl.append("&act.taskName=").append(act.getTaskName() != null ? Encodes.urlEncode(act.getTaskName()) : "");
		formUrl.append("&act.taskDefKey=").append(act.getTaskDefKey() != null ? act.getTaskDefKey() : "");
		formUrl.append("&act.procInsId=").append(act.getProcInsId() != null ? act.getProcInsId() : "");
		formUrl.append("&act.procDefId=").append(act.getProcDefId() != null ? act.getProcDefId() : "");
		formUrl.append("&act.status=").append(act.getStatus() != null ? act.getStatus() : "");
		formUrl.append("&id=").append(act.getBusinessId() != null ? act.getBusinessId() : "");
		
		return formUrl.toString();
	}
	
	/**
	 * 转换流程节点类型为中文说明
	 * @param type 英文名称
	 * @return 翻译后的中文名称
	 */
	public static String parseToZhType(String type) {
		Map<String, String> types = new HashMap<String, String>();
		types.put("userTask", PromptMessageComm.USER_TASK);
		types.put("serviceTask", PromptMessageComm.SERVICE_TASK);
		types.put("startEvent", PromptMessageComm.START_NODE);
		types.put("endEvent", PromptMessageComm.END_NODE);
		types.put("exclusiveGateway", PromptMessageComm.EXCLUSIVE_GATE);
		types.put("inclusiveGateway", PromptMessageComm.INCLUSIVE_GATEWAY);
		types.put("callActivity", PromptMessageComm.CALL_ACTIVITY);
		return types.get(type) == null ? type : types.get(type);
	}

	public static UserEntity toActivitiUser(SysUserVO user){
		if (user == null){
			return null;
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setId(user.getUserLoginname());
		userEntity.setFirstName(user.getUserName());
		userEntity.setLastName(StringUtils.EMPTY);
		userEntity.setPassword(user.getUserPassword());
		userEntity.setEmail(user.getUserEmail());
		userEntity.setRevision(1);
		return userEntity;
	}
	
	public static GroupEntity toActivitiGroup(SysRoleVO role){
		if (role == null){
			return null;
		}
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setId(role.getRoleId());
		groupEntity.setName(role.getRoleName());
		groupEntity.setType(role.getRoleNote());
		groupEntity.setRevision(1);
		return groupEntity;
	}
	
}
