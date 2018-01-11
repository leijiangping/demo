package com.xunge.service.util;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.xunge.comm.system.UserCodeComm;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.util.DateUtil;
import com.xunge.core.util.StrUtil;
import com.xunge.dao.util.CodeGeneratorUtilDao;


/**
 * @author SongJL
 * @date 2017年5月25日
 * @description 主要放一些code生成方法
 */
public class CodeGeneratorUtil {

	private static CodeGeneratorUtilDao codeGeneratorUtilDao;
	
	/**
	 * 生成组织部门代码
	 * @param PId 传入父CODE
	 * @return
	 */
	public static String DeptCodeGet(String PId){
		try {
			return getCode(UserCodeComm.CODEPREFIX_DEPT, "sys_department", "dep_code", "pdep_id", PId, "");
		} catch (Exception e) {
			throw new BusinessException("生成组织部门代码失败");
		}
	}
	
	/**
	 * 生成功能菜单代码
	 * @param PId 传入父CODE
	 * @return
	 */
	public static String MenuCodeGet(String PId, String PCode){
		try {
			return getCode(UserCodeComm.CODEPREFIX_MENU, "sys_menu", "menu_code", "pmenu_id", PId, PCode);	
		} catch (Exception e) {
			throw new BusinessException("生成功能菜单代码失败");
		}
	}
	
	/**
	 * 生成角色代码
	 * @param PId 传入父CODE
	 * @return
	 */
	public static String RoleCodeGet(){
		try {
			return getCode(UserCodeComm.CODEPREFIX_ROLE, "sys_role", "role_code");	
		} catch (Exception e) {
			throw new BusinessException("生成角色代码失败");
		}
	}
	/**
	 * 生成租费报账汇总单号
	 * @param pre 功能省份前缀 ZDHZD-AH-20170719-00001
	 * 自维电费：ZDHZD
	 * 铁塔电费：TDHZD
	 * 自维租费：ZZHZD
	 * 铁塔租费（铁塔产品服务费）：TZHZD；
	 * @return
	 */
	public static String BillAmountCodeGet(String prvCode){
		try {
			String pre="ZZHZD-"+prvCode;
			String prex=pre+"-"+DateUtil.format(new Date(),"yyyyMMdd");
			String whereSql="billamount_code like '"+prex+"%'";
			return getAmountCode(prex, "rent_billamount", "billamount_code",5,whereSql);	
		} catch (Exception e) {
			throw new BusinessException("生成租费报账汇总单号失败");
		}
	}
	
	/**
	 * 生成电费缴费报账汇总单号
	 * @param pre 功能省份前缀 ZDHZD-AH-20170719-00001
	 * 自维电费：ZDHZD
	 * 铁塔电费：TDHZD
	 * 自维租费：ZZHZD
	 * 铁塔租费（铁塔产品服务费）：TZHZD；
	 * @return
	 */
	public static String BillElecAmountCodeGet(String prvCode){
		try {
			String pre="ZDHZD-"+prvCode;
			String prex=pre+"-"+DateUtil.format(new Date(),"yyyyMMdd");
			String whereSql="billamount_code like '"+prex+"%'";
			return getAmountCode(prex, "ele_billamount", "billamount_code",5,whereSql);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("生成电费缴费报账汇总单号失败");
		}
	}
	
	/**
	 * 
	 * 根据表名和列名获取code 格式如：ZDHZD-AH-20170719-00001
	 * @param prex 前缀
	 * @param tableName 表名
	 * @param colName 字段名
	 * @param length  序号长度
	 * @return 日期+序号 例如：ZDHZD-AH-20170719-00001
	 */
	private static synchronized String getAmountCode(String prex, 
			String tableName, String colName, int length,String whereSql){
		String minCode = getMaxCodeAllInfo(tableName, colName, "","",whereSql);
		
		if(minCode == null || "".equals(minCode)||minCode.split("-").length<4){
			minCode=StrUtil.codeAddZero("1", length);
		}else{

			String[] strSplit=minCode.split("-");
			
			int num=Integer.parseInt(strSplit[3])+1;
			minCode=StrUtil.codeAddZero(""+num, length);
		}
		
		return prex +"-"+ minCode;
	}
	/**
	 * 
	 * 根据表名和列名获取code
	 * @param prex 前缀
	 * @param tableName
	 * @param colName
	 * @param pcolName
	 * @param PId
	 * @return
	 */
	@SuppressWarnings("unused")
	private static synchronized String getCode(String prex, String tableName, 
			String colName, String pcolName, String PId, String PCode){
		String minCode = getMinCode(tableName, colName, pcolName, PId);
		if(minCode == null || "".equals(minCode)){
			if(PId!=null || PId!="")
				return PCode + "01";
			else
				return prex + "01";
		}
		if(minCode.length() <= 1){
			return prex + "01";
		}
		if(minCode.length()%2 == 1){//少位
			return prex + "0" + minCode;
		}
		return prex + minCode;
	}
	/**
	 * 
	 * 获取自增角色code
	 * @param prex 前缀
	 * @param tableName
	 * @param colName
	 * @param pcolName
	 * @return
	 */
	private static synchronized String getCode(String prex, String tableName, 
			String colName){
		String maxCode = getMaxCode(tableName, colName);
		if(maxCode == null || maxCode.length() <= 1 || maxCode.length()%2 == 1){//少位
			return prex + "0" + maxCode;
		}
		return prex + maxCode;
	}
	/**
	 * 根据表名和列名获取不连续最小code
	 * @param tableName
	 * @param colName
	 * @return
	 */
	private static String getMaxCode(String tableName, String colName){
		Map<String, String> param = new HashMap<>();
		param.put("tableName", tableName);
		param.put("colName", colName);
		return codeGeneratorUtilDao.selectMaxCodeFromTable(param);
	}
	/**
	 * 根据表名和列名获取已存在最大code
	 * @param tableName
	 * @param colName
	 * @return
	 */
	private static String getMinCode(String tableName, String colName, String pcolName, String PId){
		Map<String, String> param = new HashMap<>();
		param.put("tableName", tableName);
		param.put("colName", colName);
		param.put("pcolName", pcolName);
		param.put("parentId", PId);
		return codeGeneratorUtilDao.selectMinCodeFromTable(param);
	}
	/**
	 * 根据表名和列名获取已存在最大code完整信息
	 * @param tableName
	 * @param colName
	 * @author zhujj
	 * @return
	 */
	private static String getMaxCodeAllInfo(String tableName, String colName, String pcolName, String PId,String whereSql){
		Map<String, String> param = new HashMap<>();
		param.put("tableName", tableName);
		param.put("colName", colName);
		param.put("pcolName", pcolName);
		param.put("parentId", PId);
		param.put("whereSql", whereSql);
		return codeGeneratorUtilDao.selectMaxCodeAllInfoFromTable(param);
	}
	
	/**
	 * 
	 * @param type
	 * @param provinceCode
	 * @param number
	 * @return
	 */
	public static String buildBillAccountCode(String type,String provinceCode,long number){
		StringBuffer sb = new StringBuffer(type).append("-");
		sb.append(provinceCode).append("-");
		sb.append(DateUtil.format(new Date(), "yyyy")).append("-");
		DecimalFormat df = new DecimalFormat("000000");
		sb.append(df.format(number));
		return sb.toString();
	}
	public static CodeGeneratorUtilDao getCodeGeneratorUtilDao() {
		return codeGeneratorUtilDao;
	}

	public static void setCodeGeneratorUtilDao(
			CodeGeneratorUtilDao codeGeneratorUtilDao) {
		CodeGeneratorUtil.codeGeneratorUtilDao = codeGeneratorUtilDao;
	}
}
