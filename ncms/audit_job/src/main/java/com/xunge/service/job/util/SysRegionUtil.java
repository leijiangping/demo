package com.xunge.service.job.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.xunge.comm.BatchControlComm;
import com.xunge.comm.enums.ProvinceEnum;
import com.xunge.exception.BusinessException;
import com.xunge.model.basedata.SysRegionVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.util.DataType;
import com.xunge.util.StrUtil;

/**
 * SysRegion工具类
 */
public class SysRegionUtil {
	
	public static void dealCityColumns(SysRegionVO sysRegionVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						sysRegionVO.setPrvId(sysProvinceVO.getPrvId());
					}
					break;
				case "city_cid":
					sysRegionVO.setRegId(value);
					break;
				case "city_name":
					sysRegionVO.setRegName(value);
					break;
				case "change_type":

					break;
				}
			}

		}
	}
	public static void analysisCityCSVFile(Map<String, String> errorContents,Map<String, String> contents,
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
					switch(keyName.toLowerCase()){
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
					case "city_cid":
						String city_cid = vals[1];
						if(StrUtil.isBlank(city_cid)){
							errorInfoBuffer.append("地市cid为空：[" + val + "]");
							recordHasError = true;
						}
					break;	
					case "city_name":
						String city_name = vals[2];
						if(StrUtil.isBlank(city_name)){
							errorInfoBuffer.append("地市名称为空：[" + val + "]");
							recordHasError = true;
						}
						if (!StrUtil.isBlank(city_name) && !StrUtil.isChinese(city_name)) {
							errorInfoBuffer.append("地市名称不是汉字：[" + val+ "]");
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
		    String comment = DataType.RESOURCE_CITY  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
			errorContents.put("comment", comment);
			errorInfo = allErrorInfoBuffer.toString();
			if(StrUtil.isNotEmpty(errorInfo)){
				throw new BusinessException(errorInfo);
			}
		}
	
	public static void dealRegionColumns(SysRegionVO sysRegionVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {

		for (int i = 0, length = columns.size(); i<length; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						sysRegionVO.setPrvId(sysProvinceVO.getPrvId());
					}
					break;
				case "county_cid":
					sysRegionVO.setRegId(value);
					break;
				case "county_name":
					sysRegionVO.setRegName(value);
					break;
				case "city_cid":
					sysRegionVO.setPregId(value);
					break;
				case "change_type":

					break;
				}
			}
		}
	}
	public static void analysisRegionCSVFile(Map<String, String> errorContents,Map<String, String> contents,
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
				case "county_cid":
					String county_cid = vals[1];
					if(StrUtil.isBlank(county_cid)){
						errorInfoBuffer.append("区县cid为空：[" + val + "]");
						recordHasError = true;
					}
				break;
				case "county_name":
					String county_name = vals[2];
					if(StrUtil.isBlank(county_name)){
						errorInfoBuffer.append("区县名称为空：[" + val + "]");
						recordHasError = true;
					}
				break;	
				case "city_cid":
					String city_cid = vals[3];
					if(StrUtil.isBlank(city_cid)){
						errorInfoBuffer.append("所属地市为空：[" + val + "]");
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
	    String comment = DataType.RESOURCE_REGION  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
		errorContents.put("comment", comment);
		errorInfo = allErrorInfoBuffer.toString();
		if(StrUtil.isNotEmpty(errorInfo)){
			throw new BusinessException(errorInfo);
		}
	}
}