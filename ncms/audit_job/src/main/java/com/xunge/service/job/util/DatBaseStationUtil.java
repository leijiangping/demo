package com.xunge.service.job.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import com.xunge.comm.enums.ProvinceEnum;
import com.xunge.comm.enums.VendorEnum;
import com.xunge.exception.BusinessException;
import com.xunge.model.basedata.DatBasestationVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.util.DataType;
import com.xunge.util.DateConverter;
import com.xunge.util.StrUtil;

/**
 * DatBasestation工具类
 */
public class DatBaseStationUtil {

	public static void dealWireless2GColumns(DatBasestationVO datBasestationVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "bts_cid":
					datBasestationVO.setBasestationCuid(value);
					break;
				case "bts_code":
					datBasestationVO.setBasestationCode(value);
					break;
				case "bts_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "admission_date":

					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBasestationVO.setBasestationOpendate(d);
					}
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "trx_count":
					datBasestationVO.setBasestationCarrier(Integer.parseInt(value));
					break;
				case "related_room":
					// 与外键约束字段不要放值
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "coverage":
					datBasestationVO.setBasestationCovertype(Integer.parseInt(value));
					break;
		        //数据表无该字段
				case "change_type":
					break;
				}
			}
			
		}
	}
	public static void analysisWireless2GCSVFile(Map<String, String> errorContents,Map<String, String> contents,
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
				case "bts_cid":
					String bts_cid = vals[1];
					if(StrUtil.isBlank(bts_cid)){
						errorInfoBuffer.append("基站cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "bts_code":
					String bts_code = vals[2];
					if(StrUtil.isBlank(bts_code)){
						errorInfoBuffer.append("基站编码为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "bts_name":
					String bts_name = vals[3];
					if(StrUtil.isBlank(bts_name)){
						errorInfoBuffer.append("基站名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "admission_date":
					String admission_date = vals[4];
					if(StrUtil.isBlank(admission_date)){
						errorInfoBuffer.append("入网时间为空：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "vendor":
					String vendor = vals[5];
					if(StrUtil.isBlank(vendor)){
						errorInfoBuffer.append("设备制造为空：[" + val +"]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(vendor)){
						errorInfoBuffer.append("设备制造商不是数字类型：[" + val + "]");
						recordHasError = true;
					}
					else if(!VendorEnum.self.getAllEnum().containsValue(vendor)){
						errorInfoBuffer.append("设备制造商值不符合规范：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_model":
					String ne_model = vals[6];
					if(StrUtil.isBlank(ne_model)){
						errorInfoBuffer.append("设备型号为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_power":
					try {
						String ne_power = vals[7];
						if (!StrUtil.isBlank(ne_power)) {
							if (ne_power.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("设备功率格式不正确[" + val+ "]");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
				break;	
				case "status":
					String status = vals[8];
					if(StrUtil.isBlank(status)){
						errorInfoBuffer.append("生命周期状态为空：[" + val + "]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(status)||Integer.parseInt(status)>8 || Integer.parseInt(status)<1){
						errorInfoBuffer.append("生命周期状态不符合规范（1~8）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "trx_count":
					String trx_count = vals[9];
					if(StrUtil.isNotBlank(trx_count) && !NumberUtils.isNumber(trx_count)){
						errorInfoBuffer.append("载频数不为数字：[" + val + "]");
						recordHasError = true;
						
					}
				break;	
				case "related_room":
					String related_room = vals[10];
					if(StrUtil.isBlank(related_room)){
						errorInfoBuffer.append("所属机房/资源点/位置点为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "coverage":
					String coverage = vals[11];
					if(StrUtil.isBlank(coverage)){
						errorInfoBuffer.append("覆盖类型为空：[" + val + "]");
					}
					else if (!NumberUtils.isNumber(coverage)||Integer.parseInt(coverage)>3 || Integer.parseInt(coverage)<1){
						errorInfoBuffer.append("覆盖类型不符合规范（1~3）：[" + val + "]");
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
		String comment = DataType.RESOURCE_WIRELESS2G  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}

	public static void dealWireless3GColumns(DatBasestationVO datBasestationVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "nodeb_cid":
					datBasestationVO.setBasestationCuid(value);
					break;
				case "nodeb_code":
					datBasestationVO.setBasestationCode(value);
					break;
				case "nodeb_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "admission_date":
					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBasestationVO.setBasestationOpendate(d);
					}
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "trx_count":
					datBasestationVO.setBasestationCarrier(Integer.parseInt(value));
					break;
				case "related_room":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "coverage":
					datBasestationVO.setBasestationCovertype(Integer.parseInt(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}
	public static void analysisWireless3GCSVFile(Map<String, String> errorContents,Map<String, String> contents,
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
				case "nodeb_cid":
					String nodeb_cid = vals[1];
					if(StrUtil.isBlank(nodeb_cid)){
						errorInfoBuffer.append("基站cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "nodeb_code":
					String nodeb_code = vals[2];
					if(StrUtil.isBlank(nodeb_code)){
						errorInfoBuffer.append("基站编码为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "nodeb_name":
					String nodeb_name = vals[3];
					if(StrUtil.isBlank(nodeb_name)){
						errorInfoBuffer.append("基站名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "admission_date":
					String admission_date = vals[4];
					if(StrUtil.isBlank(admission_date)){
						errorInfoBuffer.append("入网时间为空：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "vendor":
					String vendor = vals[4];
					if(StrUtil.isBlank(vendor)){
						errorInfoBuffer.append("设备制造为空：[" + val +"]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(vendor)){
						errorInfoBuffer.append("设备制造商不是数字类型：[" + val + "]");
						recordHasError = true;
					}
					else if(!VendorEnum.self.getAllEnum().containsValue(vendor)){
						errorInfoBuffer.append("设备制造商值不符合规范：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "ne_model":
					String ne_model = vals[6];
					if(StrUtil.isBlank(ne_model)){
						errorInfoBuffer.append("设备型号为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_power":
					String ne_power = vals[7];
					if(StrUtil.isNotBlank(ne_power) && !NumberUtils.isNumber(ne_power)){
						errorInfoBuffer.append("设备功率格式不正确：[" + val+ "]");
						recordHasError = true;
					}
				break;	
				case "status":
					String status = vals[8];
					if(StrUtil.isBlank(status)){
						errorInfoBuffer.append("生命周期状态为空：[" + val +"]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(status)||Integer.parseInt(status)>8 || Integer.parseInt(status)<1){
						errorInfoBuffer.append("生命周期状态不符合规范（1~8）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "trx_count":
					String trx_count = vals[9];
					if(StrUtil.isNotBlank(trx_count) && !NumberUtils.isNumber(trx_count)){
						errorInfoBuffer.append("载频数不为数字：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "related_room":
					String related_room = vals[10];
					if(StrUtil.isBlank(related_room)){
						errorInfoBuffer.append("所属机房/资源点/位置点为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "coverage":
					String coverage = vals[11];
					if(StrUtil.isBlank(coverage)){
						errorInfoBuffer.append("覆盖类型为空：[" + val + "]");
						recordHasError = true;
					}
					if (StrUtil.isNotBlank(coverage) && !NumberUtils.isNumber(coverage)) {
						errorInfoBuffer.append("覆盖类型不是整数：[" + val+ "]");
						recordHasError = true;
					}
					else if (Integer.parseInt(coverage)>3 || Integer.parseInt(coverage)<1) {
						errorInfoBuffer.append("覆盖类型值不匹配（1~3）：[" + val+ "]");
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
		String comment = DataType.RESOURCE_WIRELESS3G  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}

	public static void dealWireless4GColumns(DatBasestationVO datBasestationVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "enodeb_cid":
					datBasestationVO.setBasestationCuid(value);
					break;
				case "enodeb_code":
					datBasestationVO.setBasestationCode(value);
					break;
				case "enodeb_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "admission_date":
					Date d=DateConverter.converteToDate(value);
					if(d!=null){
						datBasestationVO.setBasestationOpendate(d);
					}
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				//数据表无该字段
				case "base_station":

					break;
				case "trx_count":
					datBasestationVO.setBasestationCarrier(Integer.parseInt(value));
					break;
				case "related_room":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "coverage":
					datBasestationVO.setBasestationCovertype(Integer.parseInt(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}
	public static void analysisWireless4GCSVFile(Map<String, String> errorContents,Map<String, String> contents,
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
				case "enodeb_cid":
					String enodeb_cid = vals[1];
					if(StrUtil.isBlank(enodeb_cid)){
						errorInfoBuffer.append("基站cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "enodeb_code":
					String enodeb_code = vals[2];
					if(StrUtil.isBlank(enodeb_code)){
						errorInfoBuffer.append("基站编码为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "enodeb_name":
					String enodeb_name = vals[3];
					if(StrUtil.isBlank(enodeb_name)){
						errorInfoBuffer.append("基站名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "admission_date":
					String admission_date = vals[4];
					if(StrUtil.isBlank(admission_date)){
						errorInfoBuffer.append("入网时间为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "vendor":
					String vendor = vals[5];
					if(StrUtil.isBlank(vendor)){
						errorInfoBuffer.append("设备制造为空：[" + val +"]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(vendor)){
						errorInfoBuffer.append("设备制造商不是数字类型：[" + val + "]");
						recordHasError = true;
					}
					else if(!VendorEnum.self.getAllEnum().containsValue(vendor)){
						errorInfoBuffer.append("设备制造商值不符合规范：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_model":
					String ne_model = vals[6];
					if(StrUtil.isBlank(ne_model)){
						errorInfoBuffer.append("设备型号为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_power":
					String ne_power = vals[7];
					if(StrUtil.isNotBlank(ne_power) && !NumberUtils.isNumber(ne_power)){
						errorInfoBuffer.append("设备功率格式不正确：[" + val+ "]");
						recordHasError = true;
					}
				break;	
				case "status":
					String status = vals[8];
					if(StrUtil.isBlank(status)){
						errorInfoBuffer.append("生命周期状态为空：[" + val +"]");
						recordHasError = true;
					}
					else if (!NumberUtils.isNumber(status)||Integer.parseInt(status)>8 || Integer.parseInt(status)<1) {
						errorInfoBuffer.append("生命周期状态值不匹配（1~8）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "trx_count":
					String trx_count = vals[9];
					if(StrUtil.isNotBlank(trx_count) && !NumberUtils.isNumber(trx_count)){
						errorInfoBuffer.append("载频数不为数字：[" + val + "]");
						recordHasError = true;
						
					}
				break;	
				case "related_room":
					String related_room = vals[10];
					if(StrUtil.isBlank(related_room)){
						errorInfoBuffer.append("所属机房/资源点/位置点为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "coverage":
					String coverage = vals[11];
					if(StrUtil.isBlank(coverage)){
						errorInfoBuffer.append("覆盖类型为空：[" + val + "]");
						recordHasError = true;
					}else if(!NumberUtils.isNumber(coverage)){
						errorInfoBuffer.append("覆盖类型不是数字类型：[" + val + "]");
						recordHasError = true;
					}
					else if (Integer.parseInt(coverage)>3 || Integer.parseInt(coverage)<1){
						errorInfoBuffer.append("覆盖类型不符合规范（1~3）：[" + val + "]");
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
	    String comment = DataType.RESOURCE_WIRELESS4G  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}

	public static void dealApColumns(DatBasestationVO datBasestationVO, List<String> columns, String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "ap_cid":
					datBasestationVO.setBasestationCuid(value);
					break;
				case "ap_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_hotspot":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}
	public static void analysisAPCSVFile(Map<String, String> errorContents,Map<String, String> contents,
			List<String> columns, String errorInfo, char divideFlag,Map<String,String> commonValidateError) throws Exception {
		    StringBuffer allErrorInfoBuffer = new StringBuffer();
			int totalRecordNum = 0;
			int errRecordNum = 0;
			List<String> errorKeyLst = new ArrayList<String>();
			for(Map.Entry<String, String> contents_entry : contents.entrySet()){
				StringBuffer errorInfoBuffer = new StringBuffer();
				totalRecordNum = totalRecordNum + 1; 
				boolean recordHasError = false;
				String key = contents_entry.getKey();
				String val = contents_entry.getValue();
				String[] vals = val.split("\\"+divideFlag+"",-1);
				for(String keyName : columns){
					switch(keyName.toLowerCase()){
					case "province_code":
						String province_code = vals[0];
						if(StrUtil.isBlank(province_code)){
							errorInfoBuffer.append("省份代码为空：[" + val+ "]");
							recordHasError = true;
						}
						else if(!ProvinceEnum.self.getAllEnum().containsValue(province_code)){
							errorInfoBuffer.append("省网标识不能识别：[" + val+ "]");
							recordHasError = true;
						}
						break;
					case "ap_cid":
						String ap_cid = vals[1];
						if(StrUtil.isBlank(ap_cid)){
							errorInfoBuffer.append("APcid为空：[" + val + "]");
							recordHasError = true;
						}
						break;
					case "ap_name":
						String ap_name = vals[2];
						if(StrUtil.isBlank(ap_name)){
							errorInfoBuffer.append("AP名称为空：[" + val + "]");
							recordHasError = true;
						}
						break;
					case "related_hotspot":
						String related_hotspot = vals[3];
						if(StrUtil.isBlank(related_hotspot)){
							errorInfoBuffer.append("所属热点为空：[" + val + "]");
							recordHasError = true;
						}
						break;
					case "status":
						String status = vals[4];
						if(StrUtil.isBlank(status)){
							errorInfoBuffer.append("AP状态为空：[" + val + "]");
							recordHasError = true;
						}
						else if(StrUtil.isNotBlank(status) && !NumberUtils.isNumber(status)){
							errorInfoBuffer.append("AP状态不是数字类型：[" + val + "]");
							recordHasError = true;
						}
						else if(StrUtil.isNotBlank(status) && Integer.parseInt(status)>8 && Integer.parseInt(status)<1){
							errorInfoBuffer.append("AP状态值不符合规范：[" + val + "]");
							recordHasError = true;
						}
						break;
					case "vendor":
						String vendor = vals[5];
						if(StrUtil.isNotBlank(vendor) && !NumberUtils.isNumber(vendor)){
							errorInfoBuffer.append("设备制造商不是数字类型：[" + val + "]");
							recordHasError = true;
						}
						else if(StrUtil.isNotBlank(vendor) && !VendorEnum.self.getAllEnum().containsValue(vendor)){
							errorInfoBuffer.append("设备制造商值不符合规范：[" + val + "]");
							recordHasError = true;
						}
						break;
					case "ne_model":
						String ne_model = vals[6];
						if(StrUtil.isBlank(ne_model)){
							errorInfoBuffer.append("设备型号为空：[" + val + "]");
							recordHasError = true;
						}
						break;
					case "ne_power":
						String ne_power = vals[7];
						if(StrUtil.isNotBlank(ne_power) && !NumberUtils.isNumber(ne_power)){
							errorInfoBuffer.append("设备功率不是数字类型：[" + val + "]");
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
		    String comment = DataType.RESOURCE_AP  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
			errorContents.put("comment", comment);
			errorInfo = allErrorInfoBuffer.toString();
			if(StrUtil.isNotEmpty(errorInfo)){
				throw new BusinessException(errorInfo);
			}
		}
	
	public static void dealHotspotSwitchColumns(DatBasestationVO datBasestationVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "hotspotswitch_cid":
					datBasestationVO.setBasestationCuid(value);
					break;
				case "hotspotswitch_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_hotspot":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}
	public static void analysisHotspotSwitchCSVFile(Map<String, String> errorContents,Map<String, String> contents,
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
				case "hotspotswitch_cid":
					String hotspotswitch_cid = vals[1];
					if(StrUtil.isBlank(hotspotswitch_cid)){
						errorInfoBuffer.append("WLAN交换机cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "hotspotswitch_name":
					String hotspotswitch_name = vals[2];
					if(StrUtil.isBlank(hotspotswitch_name)){
						errorInfoBuffer.append("WLAN交换机名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "related_hotspot":
				break;
				case "status":
					String status = vals[4];
					if(StrUtil.isBlank(status)){
						errorInfoBuffer.append("生命周期状态为空：[" + val + "]");
						recordHasError = true;
					}
					else if (!NumberUtils.isNumber(status)||Integer.parseInt(status)>8 || Integer.parseInt(status)<1) {
						errorInfoBuffer.append("生命周期状态值不匹配（1~8）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "vendor":
					String vendor = vals[5];
					if(StrUtil.isBlank(vendor)){
						errorInfoBuffer.append("设备制造商为空：[" + val + "]");
						recordHasError = true;
					}
					else if(StrUtil.isNotBlank(vendor) && !VendorEnum.self.getAllEnum().containsValue(vendor)){
						errorInfoBuffer.append("设备制造商值不符合规范：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "ne_model":
					String ne_model = vals[6];
					if(StrUtil.isBlank(ne_model)){
						errorInfoBuffer.append("设备型号为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_power":
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
	    String comment = DataType.RESOURCE_HOTSPOTSWITCH  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}

	public static void dealRruColumns(DatBasestationVO datBasestationVO, List<String> columns, String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "rru_cid":
					datBasestationVO.setBasestationCuid(value);
					break;
				case "rru_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_ne_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}
	public static void analysisRruCSVFile(Map<String, String> errorContents,Map<String, String> contents,
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
				case "rru_cid":
					String rru_cid = vals[1];
					if(StrUtil.isBlank(rru_cid)){
						errorInfoBuffer.append("RRUcid为空;[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "rru_name":
					String rru_name = vals[2];
					if(StrUtil.isBlank(rru_name)){
						errorInfoBuffer.append("RRU名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "related_ne_cid":
					String related_ne_cid = vals[3];
					if(StrUtil.isBlank(related_ne_cid)){
						errorInfoBuffer.append("所属机房/资源点/位置点为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "status":
					String status = vals[4];
					if(StrUtil.isBlank(status)){
						errorInfoBuffer.append("生命周期为空：[" + val + "]");
						recordHasError = true;
					}
					else if (!NumberUtils.isNumber(status)||Integer.parseInt(status)>8 || Integer.parseInt(status)<1){
						errorInfoBuffer.append("生命周期状态不符合规范（1~8）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "vendor":
					String vendor = vals[5];
					if(StrUtil.isBlank(vendor)){
						errorInfoBuffer.append("设备制造为空：[" + val +"]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(vendor)){
						errorInfoBuffer.append("设备制造商不是数字类型：[" + val + "]");
						recordHasError = true;
					}
					else if(!VendorEnum.self.getAllEnum().containsValue(vendor)){
						errorInfoBuffer.append("设备制造商值不符合规范：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_model":
					String ne_model = vals[6];
					if(StrUtil.isBlank(ne_model)){
						errorInfoBuffer.append("设备型号为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_power":
					String ne_power = vals[7];
					if(StrUtil.isNotBlank(ne_power) && !NumberUtils.isNumber(ne_power)){
						errorInfoBuffer.append("设备功率格式不正确：[" + val+ "]");
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
		String comment = DataType.RESOURCE_RRU  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
			errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}

	public static void dealRepeaterColumns(DatBasestationVO datBasestationVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "repeater_cid":
					datBasestationVO.setBasestationCuid(value);
					break;
				case "repeater_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_resource":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "rpt_type":
					datBasestationVO.setBasestationType(Integer.parseInt(value));
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
				//4:在网
				datBasestationVO.setBasestationState(4);
			}
		}
	}
	public static void analysisRepeaterCSVFile(Map<String, String> errorContents,Map<String, String> contents,
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
				case "repeater_cid":
					String repeater_cid = vals[1];
					if(StrUtil.isBlank(repeater_cid)){
						errorInfoBuffer.append("直放站cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "repeater_name":
					String repeater_name = vals[2];
					if(StrUtil.isBlank(repeater_name)){
						errorInfoBuffer.append("直放站名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "related_resource":
					String related_resource = vals[3];
					if(StrUtil.isBlank(related_resource)){
						errorInfoBuffer.append("所属资源点/位置点为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "status":
					String status = vals[4];
					if(StrUtil.isBlank(status)){
						errorInfoBuffer.append("生命周期为空：[" + val + "]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(status)||Integer.parseInt(status)>8 || Integer.parseInt(status)<1){
						errorInfoBuffer.append("是否移交铁塔不符合规范（1~8）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "rpt_type":
					String rpt_type = vals[5];
					if(StrUtil.isBlank(rpt_type)){
						errorInfoBuffer.append("直放站类型为空：[" + val + "]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(rpt_type)||Integer.parseInt(rpt_type)>7 || Integer.parseInt(rpt_type)<1){
						errorInfoBuffer.append("是否移交铁塔不符合规范（1~8）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "vendor":
					String vendor = vals[6];
					if(StrUtil.isBlank(vendor)){
						errorInfoBuffer.append("设备制造为空：[" + val +"]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(vendor)){
						errorInfoBuffer.append("设备制造商不是数字类型：[" + val + "]");
						recordHasError = true;
					}
					else if(!VendorEnum.self.getAllEnum().containsValue(vendor)){
						errorInfoBuffer.append("设备制造商值不符合规范：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_model":
					String ne_model = vals[7];
					if(StrUtil.isBlank(ne_model)){
						errorInfoBuffer.append("设备型号为空：[" +val +"]");
						recordHasError = true;
					}
				break;	
				case "ne_power":
					String ne_power = vals[8];
					if(StrUtil.isNotBlank(ne_power) && !NumberUtils.isNumber(ne_power)){
						errorInfoBuffer.append("设备功率格式不正确：[" + val+ "]");
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
	    String comment = DataType.RESOURCE_REPEATER  + errorInfo +"校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}

	public static void dealDistributionColumns(DatBasestationVO datBasestationVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "distribution_cid":
					datBasestationVO.setBasestationCuid(value);
					break;
				case "distribution_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_ne_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
				//4:在网
				datBasestationVO.setBasestationState(4);
			}
		}
	}
	public static void analysisDistributionCSVFile(Map<String, String> errorContents,Map<String, String> contents,
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
				case "distribution_cid":
					String distribution_cid = vals[1];
					if(StrUtil.isBlank(distribution_cid)){
						errorInfoBuffer.append("分布系统cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "distribution_name":
					String distribution_name = vals[2];
					if(StrUtil.isBlank(distribution_name)){
						errorInfoBuffer.append("分布系统名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "related_ne_cid":
					String related_ne_cid = vals[3];
					if(StrUtil.isBlank(related_ne_cid)){
						errorInfoBuffer.append("所属机房或资源点或位置点为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "vendor":
					String vendor = vals[4];
					if(StrUtil.isBlank(vendor)){
						errorInfoBuffer.append("集成厂家为空：[" + val + "]");
						recordHasError = true;
					}
					else if(StrUtil.isNotBlank(vendor) && !VendorEnum.self.getAllEnum().containsValue(vendor)){
						errorInfoBuffer.append("集成厂家值不符合规范：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "ne_power":
					String ne_power = vals[5];
					if(StrUtil.isBlank(ne_power)){
						errorInfoBuffer.append("室分总功率为空：[" + val + "]");
						recordHasError = true;
					}
					if(StrUtil.isNotBlank(ne_power) && !NumberUtils.isNumber(ne_power)){
						errorInfoBuffer.append("室分总功率不是数字类型：[" + val + "]");
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
	    String comment = DataType.RESOURCE_DISTRIBUTION  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}

	public static void dealOnuColumns(DatBasestationVO datBasestationVO, List<String> columns, String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "onu_cid":
					datBasestationVO.setBasestationCuid(value);
					break;
				case "onu_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_ne_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "access_type":
					datBasestationVO.setBasestationType(Integer.parseInt(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}

	public static void analysisOnuCSVFile(Map<String, String> errorContents,Map<String, String> contents,
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
				case "onu_cid":
					String onu_cid = vals[1];
					if(StrUtil.isBlank(onu_cid)){
						errorInfoBuffer.append("ONUcid为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "onu_name":
					String onu_name = vals[2];
					if(StrUtil.isBlank(onu_name)){
						errorInfoBuffer.append("ONU名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "related_ne_cid":
					String related_ne_cid = vals[3];
					if(StrUtil.isBlank(related_ne_cid)){
						errorInfoBuffer.append("所属机房或资源点为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "status":
					String status = vals[4];
					if(StrUtil.isBlank(status)){
						errorInfoBuffer.append("生命周期状态为空：[" + val + "]");
						recordHasError = true;
					}
					else if(Integer.parseInt(status)>8 || Integer.parseInt(status)<1){
						errorInfoBuffer.append("生命周期状态值不能解析（1~8）：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "vendor":
					String vendor = vals[5];
					if(StrUtil.isBlank(vendor)){
						errorInfoBuffer.append("设备制造商为空：[" + val + "]");
						recordHasError = true;
					}
					else if(StrUtil.isNotBlank(vendor) && !VendorEnum.self.getAllEnum().containsValue(vendor)){
						errorInfoBuffer.append("设备制造商值不符合规范：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_model":
					String ne_model = vals[6];
					if(StrUtil.isBlank(ne_model)){
						errorInfoBuffer.append("设备型号为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_power":
					String ne_power = vals[7];
					if(StrUtil.isNotBlank(ne_power) && !NumberUtils.isNumber(ne_power)){
						errorInfoBuffer.append("设备功率格式不正确：[" + val+ "]");
						recordHasError = true;
					}
				break;
				case "access_type":
					String access_type = vals[8];
					if(StrUtil.isBlank(access_type)){
						errorInfoBuffer.append("接入方式为空：[" + val + "]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(access_type)){
						errorInfoBuffer.append("接入方式格式不正确：[" + val+ "]");
						recordHasError = true;
					}else if(Integer.parseInt(access_type)!=1 && Integer.parseInt(access_type)!=2){
						errorInfoBuffer.append("接入方式值不匹配（1,2）：[" + val+ "]");
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
	    String comment = DataType.RESOURCE_ONU  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}
	
	public static void dealOLTColumns(DatBasestationVO datBasestationVO, List<String> columns, String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "olt_cid":
					datBasestationVO.setBasestationCuid(value);
					break;
				case "olt_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_ne_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "status":
					datBasestationVO.setBasestationState(Integer.parseInt(value));
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
			}
		}
	}

	public static void analysisOltCSVFile(Map<String, String> errorContents,Map<String, String> contents,
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
				case "olt_cid":
					String olt_cid = vals[1];
					if(StrUtil.isBlank(olt_cid)){
						errorInfoBuffer.append("网元cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "olt_name":
					String olt_name = vals[2];
					if(StrUtil.isBlank(olt_name)){
						errorInfoBuffer.append("网元名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "related_ne_cid":
					String related_ne_cid = vals[3];
					if(StrUtil.isBlank(related_ne_cid)){
						errorInfoBuffer.append("所属机房或资源点或位置点为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "status":
					String status = vals[4];
					if(StrUtil.isBlank(status)){
						errorInfoBuffer.append("生命周期状态为空：[" + val + "]");
						recordHasError = true;
					}
					else if (Integer.parseInt(status)>8 || Integer.parseInt(status)<1) {
						errorInfoBuffer.append("生命周期状态值不匹配（1~8）：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "vendor":
					String vendor = vals[5];
					if(StrUtil.isBlank(vendor)){
						errorInfoBuffer.append("设备制造商为空：[" + val + "]");
						recordHasError = true;
					}
					else if(StrUtil.isNotBlank(vendor) && !VendorEnum.self.getAllEnum().containsValue(vendor)){
						errorInfoBuffer.append("设备制造商值不符合规范：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_model":
					String ne_model = vals[6];
					if(StrUtil.isBlank(ne_model)){
						errorInfoBuffer.append("设备型号为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_power":
					String ne_power = vals[7];
					if(StrUtil.isNotBlank(ne_power) && !NumberUtils.isNumber(ne_power)){
						errorInfoBuffer.append("设备功率格式不正确：[" + val+ "]");
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
	    String comment = DataType.RESOURCE_OLT  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}
	
	
	public static void dealPTNColumns(DatBasestationVO datBasestationVO, List<String> columns, String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "ptn_cid":
					datBasestationVO.setBasestationCuid(value);
					break;
				case "ptn_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_ne_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
				//4:在网
				datBasestationVO.setBasestationState(4);
			}
		}
	}
	
	public static void analysisPtnCSVFile(Map<String, String> errorContents,Map<String, String> contents,
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
				case "ptn_cid":
					String ptn_cid = vals[1];
					if(StrUtil.isBlank(ptn_cid)){
						errorInfoBuffer.append("网元cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ptn_name":
					String ptn_name = vals[2];
					if(StrUtil.isBlank(ptn_name)){
						errorInfoBuffer.append("网元名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "related_ne_cid":
					String related_ne_cid = vals[3];
					if(StrUtil.isBlank(related_ne_cid)){
						errorInfoBuffer.append("所属资源或资源点或位置点为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "vendor":
					String vendor = vals[4];
					if(StrUtil.isBlank(vendor)){
						errorInfoBuffer.append("设备制造为空：[" + val +"]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(vendor)){
						errorInfoBuffer.append("设备制造商不是数字类型：[" + val + "]");
						recordHasError = true;
					}
					else if(!VendorEnum.self.getAllEnum().containsValue(vendor)){
						errorInfoBuffer.append("设备制造商值不符合规范：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_model":
					String ne_model = vals[5];
					if(StrUtil.isBlank(ne_model)){
						errorInfoBuffer.append("设备型号为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_power":
					String ne_power = vals[6];
					if(StrUtil.isNotBlank(ne_power) && !NumberUtils.isNumber(ne_power)){
						errorInfoBuffer.append("设备功率格式不正确：[" + val + "]");
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
	    String comment = DataType.RESOURCE_PTN  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}

	public static void dealOTNColumns(DatBasestationVO datBasestationVO, List<String> columns, String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						datBasestationVO.setPrvId(sysProvinceVO.getPrvId());
						datBasestationVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "otn_cid":
					datBasestationVO.setBasestationCuid(value);
					break;
				case "otn_name":
					datBasestationVO.setBasestationName(value);
					break;
				case "related_ne_cid":
					datBasestationVO.setBaseresourceCuid(value);
					break;
				case "vendor":
					datBasestationVO.setBasestationVendor(Integer.parseInt(value));
					break;
				case "ne_model":
					datBasestationVO.setBasestationModel(value);
					break;
				case "ne_power":
					datBasestationVO.setBasestationPower(Float.parseFloat(value));
					break;
				case "change_type":

					break;
				}
				//4:在网
				datBasestationVO.setBasestationState(4);
			}
		}
	}
	
	public static void analysisOtnCSVFile(Map<String, String> errorContents,Map<String, String> contents,
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
				case "otn_cid":
					String otn_cid = vals[1];
					if(StrUtil.isBlank(otn_cid)){
						errorInfoBuffer.append("网元cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "otn_name":
					String otn_name = vals[2];
					if(StrUtil.isBlank(otn_name)){
						errorInfoBuffer.append("网元名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "related_ne_ci":
					String related_ne_ci = vals[3];
					if(StrUtil.isBlank(related_ne_ci)){
						errorInfoBuffer.append("所属机房或资源点或位置点为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "vendor":
					String vendor = vals[4];
					if(StrUtil.isBlank(vendor)){
						errorInfoBuffer.append("设备制造为空：[" + val +"]");
						recordHasError = true;
					}
					else if(!NumberUtils.isNumber(vendor)){
						errorInfoBuffer.append("设备制造商不是数字类型：[" + val + "]");
						recordHasError = true;
					}
					else if(!VendorEnum.self.getAllEnum().containsValue(vendor)){
						errorInfoBuffer.append("设备制造商值不符合规范：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_model":
					String ne_model = vals[5];
					if(StrUtil.isBlank(ne_model)){
						errorInfoBuffer.append("设备型号为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "ne_power":
					String ne_power = vals[6];
					if(StrUtil.isNotBlank(ne_power) && !NumberUtils.isNumber(ne_power)){
						errorInfoBuffer.append("设备功率格式不正确：[" + val + "]");
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
	    String comment = DataType.RESOURCE_OTN  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}
}