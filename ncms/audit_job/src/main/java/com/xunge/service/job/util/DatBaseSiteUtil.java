package com.xunge.service.job.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;







import net.sf.json.JSONObject;

import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import com.xunge.comm.enums.ProvinceEnum;
import com.xunge.exception.BusinessException;
import com.xunge.model.basedata.DatBasesiteVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.util.DataType;
import com.xunge.util.DateConverter;
import com.xunge.util.StrUtil;

/**
 * DatBaseSite工具类
 */
public class DatBaseSiteUtil {
	private static ISysRegionService sysRegionService;
	
	public static void dealSiteColumns(DatBasesiteVO datBasesiteVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO,JSONObject regionCache) {
		int columnSize=columns.size();
		for (int i = 0; i < columnSize; i++) {

			String columnName = columns.get(i);
			
			//数组越界value为null
			String value;
			try{
				 value = valueArray[i];
			}catch (Exception e){
				 value = null;
			}
			
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasesiteVO.setPrvId(sysProvinceVO.getPrvId());
						datBasesiteVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "site_cid":
					datBasesiteVO.setBasesiteCuid(value);
					break;
				case "site_code":
					datBasesiteVO.setBasesiteCode(value);
					break;
				case "site_maintenance":
					datBasesiteVO.setBasesiteMaintenance(Integer.parseInt(value));
					break;
				case "tower_site_code":
					datBasesiteVO.setTowerSiteCode(value);
					break;
				case "site_name":
					datBasesiteVO.setBasesiteName(value);
					break;
				case "city_cid":
					datBasesiteVO.setPregId(value);
					break;
				case "county_cid":
					if (value!=null && value.length()>0){
//						Map<String,String> paramMap = new HashMap<String,String>();
//						paramMap.put("prvId", sysProvinceVO.getPrvId());
//						paramMap.put("regcode", value);
//						String regId = sysRegionService.getRegIdByCode(paramMap);
						String regId = "";
						if (regionCache.containsKey(value)){
							regId = regionCache.get(value).toString();
						}
						if (regId!=null && regId.length()>0){
							datBasesiteVO.setRegId(regId);
						}
					}
					break;
				case "site_address":
					datBasesiteVO.setBasesiteAddress(value);
					break;
				case "site_type":
					datBasesiteVO.setBasesiteType(Integer.parseInt(value));
					break;
				case "status":
					datBasesiteVO.setBasesiteState(Integer.parseInt(value));
					break;
				case "admission_date":
					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBasesiteVO.setBasesiteOpendate(d);
					}
					break;
				case "site_owner":
					datBasesiteVO.setBasesiteBelong(value);
					break;
				case "site_property":
					datBasesiteVO.setBasesiteProperty(Integer.parseInt(value));
					break;
				case "share_attribute":
					datBasesiteVO.setBasesiteShare(value);
					break;
				case "longitude":
					datBasesiteVO.setBasesiteLongitude(new BigDecimal(value));
					break;
				case "latitude":
					datBasesiteVO.setBasesiteLatitude(new BigDecimal(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}
	
	public static void analysisSiteCSVFile(Map<String, String> errorContents,Map<String, String> contents,
			List<String> columns, String errorInfo, char divideFlag,Map<String,String> commonValidateError) throws Exception {
		
		StringBuffer allErrorInfoBuffer = new StringBuffer();
		int totalRecordNum = 0;
		int errRecordNum = 0;
		List<String> errorKeyLst = new ArrayList<String>();	  
		for (Map.Entry<String, String> contents_entry : contents.entrySet()) {
			StringBuffer errorInfoBuffer = new StringBuffer();
			String key = contents_entry.getKey();
			String val = contents_entry.getValue();
			totalRecordNum = totalRecordNum + 1; 
			boolean recordHasError = false;
			String[] vals = val.split("\\"+divideFlag+"",-1);
			for (String keyName : columns) {

				switch (keyName.toLowerCase()) {
				case "province_code":
					String province_code = vals[0];
					if(StrUtil.isBlank(province_code)){
						errorInfoBuffer.append("省份代码为空：[" + val + "]");
						recordHasError = true;
					}
					else if(!ProvinceEnum.self.getAllEnum().containsValue(province_code)){
						errorInfoBuffer.append("省网标识不能识别：[" + val+ "]");
						recordHasError = true;
					}
				break;	
				case "site_cid":
					String site_cid = vals[1];
					if(StrUtil.isBlank(site_cid)){
						errorInfoBuffer.append("站点cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "site_code":
					String site_code = vals[2];
					if(StrUtil.isBlank(site_code)){
						errorInfoBuffer.append("站点编码为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "site_maintenance":
					String site_maintenance = vals[3];
					if (StrUtil.isNotBlank(site_maintenance) && 
							(!NumberUtils.isNumber(site_maintenance)||Integer.parseInt(site_maintenance)>3 || Integer.parseInt(site_maintenance)<1)){
						errorInfoBuffer.append("是否移交铁塔不符合规范（1~3）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "tower_site_code":
					String site_maintenance_ = vals[3];
					String tower_site_code = vals[4];
					if(StrUtil.isBlank(tower_site_code) && StrUtil.isBlank(site_maintenance_)){
						errorInfoBuffer.append("是否移交铁塔、铁塔侧站点编码，两个字段最少填写一个：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "site_name":
					String site_name = vals[5];
					if(StrUtil.isBlank(site_name)){
						errorInfoBuffer.append("站点名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "city_cid":
					String city_cid = vals[6];
					if(StrUtil.isBlank(city_cid)){
						errorInfoBuffer.append("所属城市为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "county_cid":
					String county_cid = vals[7];
					if(StrUtil.isBlank(county_cid)){
						errorInfoBuffer.append("所属区县为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "site_address":
				break;	
				case "site_type":
					String site_type = vals[9];
					if(StrUtil.isBlank(site_type)){
						errorInfoBuffer.append("站点类型为空：[" + val + "]");
						recordHasError = true;
					}
					else if (!NumberUtils.isNumber(site_type)||Integer.parseInt(site_type)>8 || Integer.parseInt(site_type)<1){
						errorInfoBuffer.append("站点类型不符合规范（1~8）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "status":
					String status = vals[10];
					if(StrUtil.isBlank(status)){
						errorInfoBuffer.append("生命周期状态为空：[" + val + "]");
						recordHasError = true;
					}
					else if (!NumberUtils.isNumber(status)||Integer.parseInt(status)>6 || Integer.parseInt(status)<1){
						errorInfoBuffer.append("生命周期状态不符合规范（1~6）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "admission_date":
					try {
						String admission_date = vals[11];
						if (!StrUtil.isBlank(admission_date)) {
							if (admission_date.length() != 10) {
								errorInfoBuffer.append("[" + val+ "]检测异常：入网时间格式不正确");
								recordHasError = true;
							} else if (admission_date.charAt(4) != '-' || admission_date.charAt(7) != '-') {
								errorInfoBuffer.append("[" + val+ "]检测异常：入网时间格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
				break;	
				case "site_owner":
				break;	
				case "site_property":
					String site_property = vals[13];
					if (StrUtil.isNotBlank(site_property)
							&& (!NumberUtils.isNumber(site_property)||Integer.parseInt(site_property)>8 || Integer.parseInt(site_property)<1
							|| Integer.parseInt(site_property)==4)){
						errorInfoBuffer.append("产权性质不符合规范（1~3,5~8）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "share_attribute":
					String share_attribute = vals[13];
					if (StrUtil.isNotBlank(share_attribute)
							&& (!NumberUtils.isNumber(share_attribute)||Integer.parseInt(share_attribute)>8 || Integer.parseInt(share_attribute)<1)){
						errorInfoBuffer.append("使用单位不符合规范（1~8）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "longitude":
					String longitude = vals[15];
					if(StrUtil.isBlank(longitude)){
						errorInfoBuffer.append("经度为空：[" + val + "]");
						recordHasError = true;
					}else if(!NumberUtils.isNumber(longitude)){
						errorInfoBuffer.append("经度不为数字：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "latitude":
					String latitude = vals[16];
					if(StrUtil.isBlank(latitude)){
						errorInfoBuffer.append("纬度为空：[" + val + "]");
						recordHasError = true;
					}else if(!NumberUtils.isNumber(latitude)){
						errorInfoBuffer.append("纬度不为数字：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "change_type":
				break;	
				}
			}
			if (recordHasError){
				errRecordNum = errRecordNum + 1;
				errorKeyLst.add(key);
			}
			//每行错误日志进行换行处理
			if (StrUtil.isNotEmpty(errorInfoBuffer.toString())){
				allErrorInfoBuffer.append(errorInfoBuffer).append('\n');
			}
		}
		//从内容中删去错误记录
		for (String errorKey : errorKeyLst){
			contents.remove(errorKey);
		}
		String comment = DataType.RESOURCE_SITE  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
			errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}

	public static ISysRegionService getSysRegionService() {
		return sysRegionService;
	}

	public static void setSysRegionService(ISysRegionService sysRegionService) {
		DatBaseSiteUtil.sysRegionService = sysRegionService;
	}
}