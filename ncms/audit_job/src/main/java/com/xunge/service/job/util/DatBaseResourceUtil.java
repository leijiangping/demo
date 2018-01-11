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
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.util.DataType;
import com.xunge.util.DateConverter;
import com.xunge.util.StrUtil;

/**
 * DatBaseResource工具类
 */
public class DatBaseResourceUtil {
	private static ISysRegionService sysRegionService;
	
	public static void dealRoomColumns(DatBaseresourceVO datBaseresourceVO, List<String> columns,
			String[] valueArray,String prv_id, JSONObject ProvinceCache, JSONObject RegionCache) {


		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){ 
				switch (columnName.toLowerCase()) {

				case "province_code":
					if (!StrUtil.isBlank(value)) {
						
						String prvcode = value;
						String prvId = ProvinceCache.get(prvcode)+"";
						if(ProvinceCache.get(prvcode)==null){//查找缓存中是否存在该code
							prvId = sysRegionService.getPrvIdByCode(prvcode);
							//重新缓存
							ProvinceCache.put(prvcode, prvId);
						}
						if(StrUtil.isBlank(prvId)){
							prvId = prv_id;
							//重新缓存
							ProvinceCache.put(prvcode, prvId);
						}
						datBaseresourceVO.setPrvId(prvId);
					}else{
						datBaseresourceVO.setPrvId(prv_id);
					}
					break;
				case "room_cid":
					datBaseresourceVO.setBaseresourceCuid(value);
					break;
				case "room_code":
					datBaseresourceVO.setBaseresourceCode(value);
					break;
				case "room_maintenance":
					datBaseresourceVO.setBaseresourceMaintenance(Integer.parseInt(value));
					
					break;
				case "tower_site_code":
					datBaseresourceVO.setTowerSiteCode(value);
					break;
				case "room_name":
					datBaseresourceVO.setBaseresourceName(value);
					break;
				case "city_cid":
					if (!StrUtil.isBlank(value)) {
						String citycode = value;
						String cityId = RegionCache.get(citycode)+"";
						if (cityId.length()>0 && RegionCache.get(citycode)!=null){
							datBaseresourceVO.setPregId(cityId);
							break;
						}
					} 
					break;
				case "county_cid":
					if (!StrUtil.isBlank(value)) {
						String regcode = value;
						String regId = RegionCache.get(regcode)+"";
						if (regId.length()>0 && RegionCache.get(regcode)!=null){

							datBaseresourceVO.setRegId(regId);
							break;
						}
					}
					break;
				case "site_cid":
					datBaseresourceVO.setBasesiteCuid(value);
					break;
				case "room_address":
					datBaseresourceVO.setBaseresourceAddress(value);
					break;
				case "room_area":
					value = StrUtil.isBlank(value)?"0.00":value;
					datBaseresourceVO.setBaseresourceArea(new BigDecimal(value));
					break;
				case "room_type":
					datBaseresourceVO.setBaseresourceCategory(value);
					break;
				case "status":
					if(value.equals("4") || value.equals("5")){
						datBaseresourceVO.setBaseresourceState(1);
					}else if(value.equals("1") || value.equals("2") || value.equals("3")){
						datBaseresourceVO.setBaseresourceState(2);
					}else{
						datBaseresourceVO.setBaseresourceState(3);
					}
					break;
				case "admission_date":
					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBaseresourceVO.setBaseresourceOpendate(d);
					}
					break;
				case "room_owner":
					datBaseresourceVO.setRoomOwner(value);
					break;
				case "room_property":
					datBaseresourceVO.setRoomProperty(Integer.parseInt(value));
					break;
				case "share_attribute":
					datBaseresourceVO.setRoomShare(value);
					break;
				case "longitude":
					if(NumberUtils.isNumber(value)){
						datBaseresourceVO.setBaseresourceLongitude(new BigDecimal(value));
					}
					break;
				case "latitude":
					if(NumberUtils.isNumber(value)){
						datBaseresourceVO.setBaseresourceLatitude(new BigDecimal(value));
					}
					break;
				case "airconditioner_power":
					if(NumberUtils.isNumber(value)){
						datBaseresourceVO.setAirconditionerPower(new BigDecimal(value));
					}
					break;
				case "change_type":

					break;
				}
			}
			
		}
	}
	public static void analysisRoomCSVFile(Map<String, String> errorContents,Map<String, String> contents,
			List<String> columns, String errorInfo, char divideFlag,Map<String,String> commonValidateError) throws Exception {
		StringBuffer allErrorInfoBuffer = new StringBuffer();
		int totalRecordNum = 0;
		int errRecordNum = 0;
		List<String> errorKeyLst = new ArrayList<String>();	  
		for (Map.Entry<String, String> contents_entry : contents.entrySet()) {
			StringBuffer errorInfoBuffer = new StringBuffer();
			totalRecordNum = totalRecordNum + 1; 
			boolean recordHasError = false;
			String key = contents_entry.getKey();
			String val = contents_entry.getValue();
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
				case "room_cid":
					String room_cid = vals[1];
					if(StrUtil.isBlank(room_cid)){
						errorInfoBuffer.append("机房cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "room_code":
					String room_code = vals[2];
					if(StrUtil.isBlank(room_code)){
						errorInfoBuffer.append("机房编码为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "room_maintenance":
					String room_maintenance = vals[3];
					if(!StrUtil.isBlank(room_maintenance)){
						if(!NumberUtils.isNumber(room_maintenance)){
							errorInfoBuffer.append("是否移交铁塔不是数字）：[" + val + "]");
							recordHasError = true;
						}else if(Integer.parseInt(room_maintenance)>3 || Integer.parseInt(room_maintenance)<1){
							errorInfoBuffer.append("是否移交铁塔不符合规范（1~3）：[" + val + "]");
							recordHasError = true;
						}
					}
					
				break;	
				case "tower_site_code":
					String room_maintenance_ = vals[3];
					String tower_site_code = vals[4];
					if(StrUtil.isBlank(tower_site_code) && StrUtil.isBlank(room_maintenance_)){
						errorInfoBuffer.append("是否移交铁塔、铁塔侧站点编码，两个字段最少填写一个：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "room_name":
					String room_name = vals[5];
					if(StrUtil.isBlank(room_name)){
						errorInfoBuffer.append("机房名称为空：[" + vals + "]");
						recordHasError = true;
					}
				break;	
				case "city_cid":
					String city_cid = vals[6];
					if(StrUtil.isBlank(city_cid)){
						errorInfoBuffer.append("所属地市为空：[" + val + "]");
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
				case "site_cid":
					String site_cid = vals[8];
					if(StrUtil.isBlank(site_cid)){
						errorInfoBuffer.append("所属站点/位置点为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "room_address":
				break;	
				case "room_area":
					String room_area = vals[10];
					if(StrUtil.isNotBlank(room_area) && !NumberUtils.isNumber(room_area)){
						errorInfoBuffer.append("机房格式不正确：[" + val+ "]");
						recordHasError = true;
					}
				break;	
				case "room_type":
					String room_type = vals[11];
					if(StrUtil.isBlank(room_type)){
						errorInfoBuffer.append("机房类型为空：[" + val + "]");
						recordHasError = true;
					}else if(!NumberUtils.isNumber(room_type)){
						errorInfoBuffer.append("机房类型不是数字：[" + val + "]");
						recordHasError = true;
					}else if (Integer.parseInt(room_type)>32 || Integer.parseInt(room_type)<2){
						errorInfoBuffer.append("是否移交铁塔不符合规范（2~32）：[" + val + "]");
						recordHasError = true;
					}
					else if (Integer.parseInt(room_type)>17 && Integer.parseInt(room_type)<30){
						errorInfoBuffer.append("是否移交铁塔不符合规范（17~30）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "status":
					String status = vals[12];
					if(StrUtil.isBlank(status)){
						errorInfoBuffer.append("生命周期状态为空：[" + val + "]");
						recordHasError = true;
					}else if(!NumberUtils.isNumber(status)){
						errorInfoBuffer.append("生命周期不是数字：[" + val + "]");
						recordHasError = true;
					}else if (Integer.parseInt(status)>6 || Integer.parseInt(status)<1){
						errorInfoBuffer.append("生命周期状态不符合规范（1~6）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "admission_date":
					try {
						String admission_date = vals[13];
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
				case "room_owner":
					String room_owner = vals[14];
					if(StrUtil.isNotBlank(room_owner)){
						if(!NumberUtils.isNumber(room_owner)){
							errorInfoBuffer.append("产权单位不是数字：[" + val + "]");
							recordHasError = true;
						}else if((Integer.parseInt(room_owner)>9 || Integer.parseInt(room_owner)<1)){
							errorInfoBuffer.append("产权单位不符合规范（1~9）：[" + val + "]");
							recordHasError = true;
						}
					}
					
				break;	
				case "room_property":
					String room_property = vals[15];
					if(StrUtil.isNotBlank(room_property)){
						if(!NumberUtils.isNumber(room_property)){
							errorInfoBuffer.append("产权性质不是数字（1~8）：[" + val + "]");
							recordHasError = true;
						}else if((Integer.parseInt(room_property)>8 || Integer.parseInt(room_property)<1)){
							errorInfoBuffer.append("产权性质不符合规范（1~8）：[" + val + "]");
							recordHasError = true;
						}
					}
				
				break;	
				case "share_attribute":
					String share_attribute = vals[16];
					if(StrUtil.isNotBlank(share_attribute)){
						if(!NumberUtils.isNumber(share_attribute)){
							errorInfoBuffer.append("使用单位不是数字（1~8）：[" + val + "]");
							recordHasError = true;
						}else if((Integer.parseInt(share_attribute)>8 || Integer.parseInt(share_attribute)<1)){
							errorInfoBuffer.append("使用单位不符合规范（1~8）：[" + val + "]");
							recordHasError = true;
						}
					}
					
				break;	
				case "longitude":
					String longitude = vals[17];
					if(StrUtil.isBlank(longitude)){
						errorInfoBuffer.append("经度为空：[" + val + "]");
						recordHasError = true;
					}else if(!NumberUtils.isNumber(longitude)){
						errorInfoBuffer.append("经度不为数字：[" + val + "]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(longitude)){
						errorInfoBuffer.append("经度格式不正确：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "latitude":
					String latitude = vals[18];
					if(StrUtil.isBlank(latitude)){
						errorInfoBuffer.append("纬度为空：[" + val + "]");
						recordHasError = true;
					}else if(!NumberUtils.isNumber(latitude)){
						errorInfoBuffer.append("纬度不为数字：[" + val + "]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(latitude)){
						errorInfoBuffer.append("纬度格式不正确：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "airconditioner_power":
					String airconditioner_power = vals[19];
					if(StrUtil.isBlank(airconditioner_power)){
						errorInfoBuffer.append("空调总额定功率为空：[" + val + "]");
						recordHasError = true;
					}else if(!NumberUtils.isNumber(airconditioner_power)){
						errorInfoBuffer.append("空调总额定功率不为数字：[" + val + "]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(airconditioner_power)){
						errorInfoBuffer.append("空调总额定功率格式不正确：[" + val + "]");
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
		String comment = DataType.RESOURCE_ROOM  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}
	
	public static void dealResourceSpotColumns(DatBaseresourceVO datBaseresourceVO, List<String> columns,

			String[] valueArray,String prv_id, JSONObject ProvinceCache, JSONObject RegionCache) {


		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
	                if (!StrUtil.isBlank(value)) {
						
						String prvcode = value;
						String prvId = ProvinceCache.get(prvcode)+"";
						if(ProvinceCache.get(prvcode)==null){//查找缓存中是否存在该code
							prvId = sysRegionService.getPrvIdByCode(prvcode);
							//重新缓存
							ProvinceCache.put(prvcode, prvId);
						}
						if(StrUtil.isBlank(prvId)){
							prvId = prv_id;
							//重新缓存
							ProvinceCache.put(prvcode, prvId);
						}
						datBaseresourceVO.setPrvId(prvId);
					}else{
						datBaseresourceVO.setPrvId(prv_id);
					}
					break;
				case "resource_cid":
					datBaseresourceVO.setBaseresourceCuid(value);
					break;
				case "resource_code":
					datBaseresourceVO.setBaseresourceCode(value);
					break;
				case "resource_maintenance":
					datBaseresourceVO.setBaseresourceMaintenance(Integer.parseInt(value));
					break;
				case "tower_site_code":
					datBaseresourceVO.setTowerSiteCode(value);
					break;
				case "resource_name":
					datBaseresourceVO.setBaseresourceName(value);
					break;
				case "city_cid":
					if (!StrUtil.isBlank(value)) {
						String citycode = value;
						String cityId = RegionCache.get(citycode)+"";
						if (cityId.length()>0 && RegionCache.get(citycode)!=null){
							datBaseresourceVO.setPregId(cityId);
							break;
						}
					} 
					break;
				case "county_cid":
					if (!StrUtil.isBlank(value)) {
						String regcode = value;
						String regId = RegionCache.get(regcode)+"";
						if (regId.length()>0 && RegionCache.get(regcode)!=null){
							datBaseresourceVO.setRegId(regId);
							break;
						}
					}

					break;
				case "site_cid":
					datBaseresourceVO.setBasesiteCuid(value);
					break;
				case "resource_address":
					datBaseresourceVO.setBaseresourceAddress(value);
					break;
				case "resource_type":
					datBaseresourceVO.setBaseresourceCategory(value);
					break;
				case "status":
					if(value.equals("4") || value.equals("5")){
						datBaseresourceVO.setBaseresourceState(1);
					}else if(value.equals("1") || value.equals("2") || value.equals("3")){
						datBaseresourceVO.setBaseresourceState(2);
					}else{
						datBaseresourceVO.setBaseresourceState(3);
					}
					break;
				case "admission_date":
					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBaseresourceVO.setBaseresourceOpendate(d);
					}
					break;
				case "resource_owner":
					datBaseresourceVO.setRoomOwner(value);
					break;
				case "longitude":
					if(NumberUtils.isNumber(value)){
						datBaseresourceVO.setBaseresourceLongitude(new BigDecimal(value));
					}
					break;
				case "latitude":
					if(NumberUtils.isNumber(value)){
						datBaseresourceVO.setBaseresourceLatitude(new BigDecimal(value));
					}
					break;
				case "change_type":

					break;
				}
			}
		}
	}
	public static void analysisResourceSpotCSVFile(Map<String, String> errorContents,Map<String, String> contents,
			List<String> columns, String errorInfo, char divideFlag,Map<String,String> commonValidateError) throws Exception {
		StringBuffer allErrorInfoBuffer = new StringBuffer();
		int totalRecordNum = 0;
		int errRecordNum = 0;
		List<String> errorKeyLst = new ArrayList<String>();
		for(Map.Entry<String,String> contents_entry : contents.entrySet()){
			StringBuffer errorInfoBuffer = new StringBuffer();
			totalRecordNum = totalRecordNum + 1; 
			boolean recordHasError = false;
			String key = contents_entry.getKey();
			String val = contents_entry.getValue();
			String[] vals = val.split("\\"+divideFlag+"",-1);
			for (String keyName : columns) {
				switch (keyName.toLowerCase()){
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
				case "resource_cid":
					String resource_cid = vals[1];
					if(StrUtil.isBlank(resource_cid)){
						errorInfoBuffer.append("资源点cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "resource_code":
					String resource_code = vals[2];
					if(StrUtil.isBlank(resource_code)){
						errorInfoBuffer.append("资源点编码为空：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "resource_maintenance":
					String resource_maintenance = vals[3];
					if(StrUtil.isNotBlank(resource_maintenance) && !NumberUtils.isNumber(resource_maintenance)){
						errorInfoBuffer.append("是否移交铁塔不是整数：[" + val + "]");
						recordHasError = true;
					}
					else if(StrUtil.isNotBlank(resource_maintenance) &&(Integer.parseInt(resource_maintenance)>3 || Integer.parseInt(resource_maintenance)<1)){
						errorInfoBuffer.append("是否移交铁塔值不能解析（1~3）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "tower_site_code":
					String tower_site_code = vals[4];
					if(!StrUtil.isBlank(tower_site_code) && tower_site_code.length() == 1 && tower_site_code.charAt(0) == 4){
						System.out.println("是否移交铁塔");
					}else if(!StrUtil.isBlank(tower_site_code) && tower_site_code.length() == 1 && tower_site_code.charAt(0) == 5){
						System.out.println("铁塔测站点编码");
					}
				break;	
				case "resource_name":
					String resource_name = vals[5];
					if(StrUtil.isBlank(resource_name)){
						errorInfoBuffer.append("资源点名称为空： [" + val + "]");
						recordHasError = true;
					}
				break;
				case "city_cid":
					String city_cid = vals[6];
					if(StrUtil.isBlank(city_cid)){
						errorInfoBuffer.append("所属地市为空：[" + val + "]");
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
				case "site_cid":
				break;
				case "resource_address":
				break;	
				case "resource_type":
					String resource_type = vals[10];
					if(StrUtil.isBlank(resource_type)){
						errorInfoBuffer.append("资源点类型为空：[" + val + "]");
						recordHasError = true;
					}
					else if(Integer.parseInt(resource_type)>4 || Integer.parseInt(resource_type)<1){
						errorInfoBuffer.append("资源点类型值不识别：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "status":
					String status = vals[11];
					if(StrUtil.isBlank(status)){
						errorInfoBuffer.append("生命周期状态为空：[" + val + "]");
					}else if(!NumberUtils.isNumber(status)||(Integer.parseInt(status)>6 || Integer.parseInt(status)<1)){
						errorInfoBuffer.append("生命周期状态值不识别：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "admission_date":
					try {
						String admission_date = vals[12];
						if (!StrUtil.isBlank(admission_date)) {
							if (admission_date.length() != 10) {
								errorInfoBuffer.append("入网时间日期格式不正确：[" + val+ "]");
								recordHasError = true;
							} else if (admission_date.charAt(4) != '-' || admission_date.charAt(7) != '-') {
								errorInfoBuffer.append("入网时间日期格式不正确：[" + val+ "]");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
				break;
				case "longitude":
					String longitude = vals[13];
					if(StrUtil.isBlank(longitude)){
						errorInfoBuffer.append("经度为空：[" + val + "]");
						recordHasError = true;
					}else if(!NumberUtils.isNumber(longitude)){
						errorInfoBuffer.append("经度不为数字：[" + val + "]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(longitude)){
						errorInfoBuffer.append("经度格式不正确：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "latitude":
					String latitude = vals[14];
					if(StrUtil.isBlank(latitude)){
						errorInfoBuffer.append("纬度为空：[" + val + "]");
						recordHasError = true;
					}else if(!NumberUtils.isNumber(latitude)){
						errorInfoBuffer.append("纬度不为数字：[" + val + "]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(latitude)){
						errorInfoBuffer.append("纬度格式不正确：[" + val + "]");
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
	    String comment = DataType.RESOURCE_RESOURCESPOT  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}
	
	public static void dealHotspotColumns(DatBaseresourceVO datBaseresourceVO, List<String> columns,

			String[] valueArray,String prv_id, JSONObject ProvinceCache, JSONObject RegionCache) {


		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if (!StrUtil.isBlank(value)) {
						
						String prvcode = value;
						String prvId = ProvinceCache.get(prvcode)+"";
						if(ProvinceCache.get(prvcode)==null){//查找缓存中是否存在该code
							prvId = sysRegionService.getPrvIdByCode(prvcode);
							//重新缓存
							ProvinceCache.put(prvcode, prvId);
						}
						if(StrUtil.isBlank(prvId)){
							prvId = prv_id;
							//重新缓存
							ProvinceCache.put(prvcode, prvId);
						}
						datBaseresourceVO.setPrvId(prvId);
					}else{
						datBaseresourceVO.setPrvId(prv_id);
					}
					break;
				case "hotspot_cid":
					datBaseresourceVO.setBaseresourceCuid(value);
					break;
				case "hotspot_code":
					datBaseresourceVO.setBaseresourceCode(value);
					break;
				case "hotspot_maintenance":
					datBaseresourceVO.setBaseresourceMaintenance(Integer.parseInt(value));
					break;
				case "tower_site_code":
					datBaseresourceVO.setTowerSiteCode(value);
					break;
				case "hotspot_name":
					datBaseresourceVO.setBaseresourceName(value);
					break;
				case "city_cid":
					if (!StrUtil.isBlank(value)) {
						String citycode = value;
						String cityId = RegionCache.get(citycode)+"";
						if (cityId.length()>0 && RegionCache.get(citycode)!=null){
							datBaseresourceVO.setPregId(cityId);
							break;
						}
					} 
					break;
				case "county_cid":
					if (!StrUtil.isBlank(value)) {
						String regcode = value;
						String regId = RegionCache.get(regcode)+"";
						if (regId.length()>0 && RegionCache.get(regcode)!=null){
							datBaseresourceVO.setRegId(regId);
							break;
						}
					}

					break;
				case "status":
					if(value.equals("4") || value.equals("5")){
						datBaseresourceVO.setBaseresourceState(1);
					}else if(value.equals("1") || value.equals("2") || value.equals("3")){
						datBaseresourceVO.setBaseresourceState(2);
					}else{
						datBaseresourceVO.setBaseresourceState(3);
					}
					break;
				case "hotspot_address":
					datBaseresourceVO.setBaseresourceAddress(value);
					break;
				case "longitude":
					if(NumberUtils.isNumber(value)){
						datBaseresourceVO.setBaseresourceLongitude(new BigDecimal(value));
					}
					break;
				case "latitude":
					if(NumberUtils.isNumber(value)){
						datBaseresourceVO.setBaseresourceLatitude(new BigDecimal(value));
					}
					break;
				case "change_type":

					break;
				}
			}
		}
	}
	public static void analysisHotspotCSVFile(Map<String, String> errorContents,Map<String, String> contents,
			List<String> columns, String errorInfo, char divideFlag,Map<String,String> commonValidateError) throws Exception {
		StringBuffer allErrorInfoBuffer = new StringBuffer();
		int totalRecordNum = 0;
		int errRecordNum = 0;
		List<String> errorKeyLst = new ArrayList<String>();
		for (Map.Entry<String,String> contents_entry : contents.entrySet()) {
			StringBuffer errorInfoBuffer = new StringBuffer();
			totalRecordNum = totalRecordNum + 1; 
			boolean recordHasError = false;
			String key = contents_entry.getKey();
			String val = contents_entry.getValue();
			String[] vals = val.split("\\"+divideFlag+"",-1);
			for(String keyName : columns){
				switch (keyName.toLowerCase()){
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
				case "hotspot_cid":
					String hotspot_cid = vals[1];
					if(StrUtil.isBlank(hotspot_cid)){
						errorInfoBuffer.append("热点cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "hotspot_code":
					String hotspot_code = vals[2];
					if(StrUtil.isBlank(hotspot_code)){
						errorInfoBuffer.append("热点编码为空：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "hotspot_maintenance":
					String hotspot_maintenance = vals[3];
					if (StrUtil.isNotBlank(hotspot_maintenance) && !NumberUtils.isNumber(hotspot_maintenance)) {
						errorInfoBuffer.append("是否移交铁塔格式不是数字：[" + val + "]");
						recordHasError = true;
					}
					else if (StrUtil.isNotBlank(hotspot_maintenance) &&(Integer.parseInt(hotspot_maintenance)>3 || Integer.parseInt(hotspot_maintenance)<1)) {
						errorInfoBuffer.append("是否移交铁塔值不匹配（1,2,3）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "tower_site_code":
					String hotspot_maintenance_ = vals[3];
					String tower_site_code = vals[4];
					if(StrUtil.isBlank(tower_site_code) && StrUtil.isBlank(hotspot_maintenance_)){
						errorInfoBuffer.append("是否移交铁塔、铁塔侧站点编码，两个字段最少填写一个：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "hotspot_name":
					String hotspot_name = vals[5];
					if(StrUtil.isBlank(hotspot_name)){
						errorInfoBuffer.append("热点名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "city_cid":
					String city_cid = vals[6];
					if(StrUtil.isBlank(city_cid)){
						errorInfoBuffer.append("所属地市为空：[" + val + "]");
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
				case "status":
					String status = vals[8];
					if(StrUtil.isBlank(status)){
						errorInfoBuffer.append("生命周期状态为空[" + val + "]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(status)||Integer.parseInt(status)>7 || Integer.parseInt(status)<1){
						errorInfoBuffer.append("全生命周期状态值不匹配（1~7）：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "hotspot_address":
				break;	
				case "longitude":
					String longitude = vals[10];
					if(StrUtil.isBlank(longitude)){
						errorInfoBuffer.append("经度为空：[" + val + "]");
						recordHasError = true;
					}else if(!NumberUtils.isNumber(longitude)){
						errorInfoBuffer.append("经度不为数字：[" + val + "]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(longitude)){
						errorInfoBuffer.append("经度格式不正确：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "latitude":
					String latitude = vals[11];
					if(StrUtil.isBlank(latitude)){
						errorInfoBuffer.append("纬度为空：[" + val +"]");
						recordHasError = true;
					}else if(!NumberUtils.isNumber(latitude)){
						errorInfoBuffer.append("纬度不为数字：[" + val + "]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(latitude)){
						errorInfoBuffer.append("纬度格式不正确：[" + val + "]");
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
	    String comment = DataType.RESOURCE_HOTSPOT  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}
	
	public static void dealPositionColumns(DatBaseresourceVO datBaseresourceVO, List<String> columns,

			String[] valueArray,String prv_id, JSONObject ProvinceCache, JSONObject RegionCache) {


		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){ 
				switch (columnName.toLowerCase()) {

				case "province_code":
					if (!StrUtil.isBlank(value)) {
						
						String prvcode = value;
						String prvId = ProvinceCache.get(prvcode)+"";
						if(ProvinceCache.get(prvcode)==null){//查找缓存中是否存在该code
							prvId = sysRegionService.getPrvIdByCode(prvcode);
							//重新缓存
							ProvinceCache.put(prvcode, prvId);
						}
						if(StrUtil.isBlank(prvId)){
							prvId = prv_id;
							//重新缓存
							ProvinceCache.put(prvcode, prvId);
						}
						datBaseresourceVO.setPrvId(prvId);
					}else{
						datBaseresourceVO.setPrvId(prv_id);
					}
					break;
				case "position_cid":
					datBaseresourceVO.setBaseresourceCuid(value);
					break;
				case "position_code":
					datBaseresourceVO.setBaseresourceCode(value);
					break;
				case "position_maintenance":
					datBaseresourceVO.setBaseresourceMaintenance(Integer.parseInt(value));
					break;
				case "tower_site_code":
					datBaseresourceVO.setTowerSiteCode(value);
					break;
				case "position_name":
					datBaseresourceVO.setBaseresourceName(value);
					break;
				case "city_cid":
					if (!StrUtil.isBlank(value)) {
						String citycode = value;
						String cityId = RegionCache.get(citycode)+"";
						if (cityId.length()>0 && RegionCache.get(citycode)!=null){
							datBaseresourceVO.setPregId(cityId);
							break;
						}
					} 
					break;
				case "county_cid":
					if (!StrUtil.isBlank(value)) {
						String regcode = value;
						String regId = RegionCache.get(regcode)+"";
						if (regId.length()>0 && RegionCache.get(regcode)!=null){
							datBaseresourceVO.setRegId(regId);
							break;
						}
					}

					break;
				case "site_cid":
					datBaseresourceVO.setBasesiteCuid(value);
					break;
				case "position_address":
					datBaseresourceVO.setBaseresourceAddress(value);
					break;
				case "position_type":
					datBaseresourceVO.setBaseresourceCategory(value);
					break;
				case "status":
					if(value.equals("4") || value.equals("5")){
						datBaseresourceVO.setBaseresourceState(1);
					}else if(value.equals("1") || value.equals("2") || value.equals("3")){
						datBaseresourceVO.setBaseresourceState(2);
					}else{
						datBaseresourceVO.setBaseresourceState(3);
					}
					break;
				case "admission_date":
					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBaseresourceVO.setBaseresourceOpendate(d);
					}
					break;
				case "position_owner":
					datBaseresourceVO.setRoomOwner(value);
					break;
				case "position_property":
					datBaseresourceVO.setRoomProperty(Integer.parseInt(value));
					break;
				case "share_attribute":
					datBaseresourceVO.setRoomShare(value);
					break;
				case "longitude":
					if(NumberUtils.isNumber(value)){
						datBaseresourceVO.setBaseresourceLongitude(new BigDecimal(value));
					}
					break;
				case "latitude":
					if(NumberUtils.isNumber(value)){
						datBaseresourceVO.setBaseresourceLatitude(new BigDecimal(value));
					}
					break;
				case "change_type":

					break;
				}
			}
		}
	}
	public static void analysisPositionCSVFile(Map<String, String> errorContents,Map<String, String> contents,
			List<String> columns, String errorInfo, char divideFlag,Map<String,String> commonValidateError) throws Exception {
		StringBuffer allErrorInfoBuffer = new StringBuffer();
		int totalRecordNum = 0;
		int errRecordNum = 0;
		List<String> errorKeyLst = new ArrayList<String>();
		for (Map.Entry<String, String> contents_entry : contents.entrySet()) {
			StringBuffer errorInfoBuffer = new StringBuffer();
			totalRecordNum = totalRecordNum + 1; 
			boolean recordHasError = false;
			String key = contents_entry.getKey();
			String val = contents_entry.getValue();
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
				case "position_cid":
					String position_cid = vals[1];
					if(StrUtil.isBlank(position_cid)){
						errorInfoBuffer.append("位置点cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "position_code":
					String position_code = vals[2];
					if(StrUtil.isBlank(position_code)){
						errorInfoBuffer.append("位置点编码为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "position_maintenance":
					String position_maintenance = vals[3];
					if(StrUtil.isNotBlank(position_maintenance)){
						if(!NumberUtils.isNumber(position_maintenance)||Integer.parseInt(position_maintenance)>3 
							|| Integer.parseInt(position_maintenance)<1){
							errorInfoBuffer.append("是否移交铁塔不符合规范（1~3）：[" + val + "]");
							recordHasError = true;
						}
					}
				break;
				case "tower_site_code":
					String position_maintenance_ = vals[3];
					String tower_site_code = vals[4];
					if(StrUtil.isBlank(tower_site_code) && StrUtil.isBlank(position_maintenance_)){
						errorInfoBuffer.append("是否移交铁塔、铁塔侧站点编码，两个字段最少填写一个：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "position_name":
					String position_name =vals[5];
					if(StrUtil.isBlank(position_name)){
						errorInfoBuffer.append("位置点名称为空：[" + vals + "]");
						recordHasError = true;
					}
				break;
				case "city_cid":
					String city_cid = vals[6];
					if(StrUtil.isBlank(city_cid)){
						errorInfoBuffer.append("所属地市为空：[" + val + "]");
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
				case "position_address":
				break;	
				case "position_type":
					String position_type = vals[9];
					if(StrUtil.isBlank(position_type)){
						errorInfoBuffer.append("位置点类型为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "status":
					String status = vals[10];
					if(StrUtil.isBlank(status)){
						errorInfoBuffer.append("生命周期状态为空：[" + val + "]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(status)||Integer.parseInt(status)>6 || Integer.parseInt(status)<1){
						errorInfoBuffer.append("生命周期状态不符合规范（1~6）：[" + val + "]");
					}
				break;
				case "admission_date":
					try {
						String admission_date = vals[11];
						if (!StrUtil.isBlank(admission_date)) {
							if (admission_date.length() != 10) {
								errorInfoBuffer.append("入网时间日期格式不正确：[" + val+ "]");
								recordHasError = true;
							} else if (admission_date.charAt(4) != '-' || admission_date.charAt(7) != '-') {
								errorInfoBuffer.append("入网时间日期格式不正确：[" + val+ "]");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
				break;	
				case "position_owner":
					String position_owner = vals[12];
					if(StrUtil.isNotBlank(position_owner) 
							&& (!NumberUtils.isNumber(position_owner)||Integer.parseInt(position_owner)>9 || Integer.parseInt(position_owner)<1)){
						errorInfoBuffer.append("产权单位不符合规范（1~9）：[" + val + "]");
					}
				break;	
				case "position_property":
					String position_property = vals[13];
					if(StrUtil.isNotBlank(position_property) 
							&& (!NumberUtils.isNumber(position_property)||Integer.parseInt(position_property)>8 || Integer.parseInt(position_property)<1)){
						errorInfoBuffer.append("产权性质不符合规范（1~8）：[" + val + "]");
					}
				break;	
				case "share_attribute":
					String share_attribute = vals[13];
					if(StrUtil.isNotBlank(share_attribute) 
							&& (!NumberUtils.isNumber(share_attribute)||Integer.parseInt(share_attribute)>8 || Integer.parseInt(share_attribute)<1)){
						errorInfoBuffer.append("使用单位不符合规范（1~8）：[" + val + "]");
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
					else if(!NumberUtils.isNumber(longitude)){
						errorInfoBuffer.append("经度格式不正确：[" + val + "]");
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
					else if(!NumberUtils.isNumber(latitude)){
						errorInfoBuffer.append("纬度格式不正确：[" + val + "]");
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
	    String comment = DataType.RESOURCE_POSITION  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
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
		DatBaseResourceUtil.sysRegionService = sysRegionService;
	}
}