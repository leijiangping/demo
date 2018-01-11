package com.xunge.service.job.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.xunge.comm.BatchControlComm;
import com.xunge.comm.enums.ProvinceEnum;
import com.xunge.dao.basedata.DatBaseresourceVOMapper;
import com.xunge.exception.BusinessException;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.DatBaseresourceVOExample;
import com.xunge.model.basedata.DatBaseresourceVOExample.Criteria;
import com.xunge.model.basedata.ring.MeterPerfVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.util.DataType;
import com.xunge.util.DateConverter;
import com.xunge.util.StrUtil;
import com.xunge.util.redis.JedisUtil;
import com.xunge.util.redis.JedisUtil.Hash;
import com.xunge.util.redis.JedisUtil.Keys;

/**
 * DatBaseSite工具类
 */
@Component
public class MeterPerfUtil {
	
	
	@Resource
	private DatBaseresourceVOMapper datBaseresourceVOMapper;
	
	
	private static MeterPerfUtil meterPerfUtil;
	
	@PostConstruct 
    public void init() { 
		meterPerfUtil = this; 
		meterPerfUtil.datBaseresourceVOMapper = this.datBaseresourceVOMapper; 
	} 
	
	
	public static void dealMeterColumns(MeterPerfVO meterPerfVO, List<String> columns,
			String[] valueArray,SysProvinceVO sysProvinceVO) {
		int columnSize=columns.size();
		/*String cityId="";
		String countyId="";*/
		for (int i = 0; i < columnSize; i++) {

			String columnName = columns.get(i);
			String value;
			
			//数组越界value为null
			try{
				 value = valueArray[i];
			}catch (Exception e){
				 value = null;
			}

			
			if(StringUtils.isNoneBlank(value)){
				switch (columnName.toLowerCase()) {

				case "province_code":
					if(value.equalsIgnoreCase(sysProvinceVO.getPrvCode())){
						meterPerfVO.setPrvId(sysProvinceVO.getPrvId());
						meterPerfVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "ne_class":
					meterPerfVO.setResourceType(Integer.parseInt(value));
					break;
				case "ne_name":
					meterPerfVO.setResourceName(value);
					break;
				case "ne_code":
					meterPerfVO.setResourceCode(value);
					break;
				case "code_type":
					meterPerfVO.setCodeType(Integer.parseInt(value));
					break;
				/*case "city_name":
					meterPerfVO.setRegId(value);
					break;*/
				case "start_time":
					Date startDate =DateConverter.converteToDateTime(value);
					if(startDate!=null){
						meterPerfVO.setStartTime(startDate);
					}
					break;
				case "stop_time":
					Date stopDate=DateConverter.converteToDateTime(value);
					if(stopDate!=null){
						meterPerfVO.setStopTime(stopDate);
					}
					break;
				case "total_degree":
					meterPerfVO.setTotalDegree(Float.parseFloat(value));
					break;
				case "total_state":
					meterPerfVO.setTotalState(Integer.parseInt(value));
					break;
				case "equipment_degree":
					meterPerfVO.setEquipmentDegree(Float.parseFloat(value));
					break;
				case "equipment_state":
					meterPerfVO.setEquipmentState(Integer.parseInt(value));
					break;
				case "ac_degree":
					meterPerfVO.setAcDegree(Float.parseFloat(value));
					break;
				case "ac_state":
					meterPerfVO.setAcState(Integer.parseInt(value));
					break;
				case "ex_tempreture":
					meterPerfVO.setExTempreture(Float.parseFloat(value));
					break;
				case "in_tempreture":
					meterPerfVO.setInTempreture(Float.parseFloat(value));
					break;
				}
			}		
		}
		/*if(StringUtils.isNoneBlank(countyId)){
			meterPerfVO.setRegId(countyId);
		}
		else if(StringUtils.isNoneBlank(cityId)){
			meterPerfVO.setRegId(cityId);
		}*/
	}
	public static void analysisMeterCSVFile(Map<String, String> errorContents,Map<String, String> contents,
			List<String> columns, String errorInfo, char divideFlag,Map<String,String> commonValidateError,SysProvinceVO sysProvinceVO) throws Exception {
		StringBuffer allErrorInfoBuffer = new StringBuffer();
		int totalRecordNum = 0;
		int errRecordNum = 0;
		
		JedisUtil jutil = JedisUtil.getInstance();
		JedisUtil.Keys keys= jutil.new Keys();
		JedisUtil.Hash baseresource_cuidcode = jutil.new Hash();
		DatBaseresourceVOMapper dbv = meterPerfUtil.datBaseresourceVOMapper;
		DatBaseresourceVOExample example = new DatBaseresourceVOExample();
		Criteria criteria = example.createCriteria();
		criteria.andPrvIdEqualTo(sysProvinceVO.getPrvId());
		List<DatBaseresourceVO> baseresource = dbv.selectByExample(example);
		Map<String,String> cuidcode = new HashMap<String, String>();
		for(DatBaseresourceVO d :baseresource){
			cuidcode.put(d.getBaseresourceCuid(), d.getBaseresourceCode());
		}
		String cont_rediskey = "BASERESOURCE_"+System.currentTimeMillis();
		baseresource_cuidcode.hmset("CUIDCODE_"+cont_rediskey, cuidcode);
		
		
		List<String> errorKeyLst = new ArrayList<String>();	  
		try {
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
							errorInfoBuffer.append("省网标识为空：[" + val + "]");
							recordHasError = true;
						}else if(!ProvinceEnum.self.getAllEnum().containsValue(province_code)){
							errorInfoBuffer.append("省网标识不存在：[" + val + "]");
							recordHasError = true;
						}else if(!sysProvinceVO.getPrvCode().equalsIgnoreCase(province_code)){
							errorInfoBuffer.append("非本省数据：[" + val + "]");
							recordHasError = true;
						}
					break;	
					case "ne_class":
						String ne_class = vals[1];
						if(StrUtil.isBlank(ne_class)){
							errorInfoBuffer.append("资源类型为空：[" + val + "]");
							recordHasError = true;
						}else if(!ne_class.equals("10005")||!ne_class.equals("10006")){
							errorInfoBuffer.append("资源类型不正确：[" + val + "]");
							recordHasError = true;
						}
					break;	
					case "ne_name":
						String ne_name = vals[2];
						if(StrUtil.isBlank(ne_name)){
							errorInfoBuffer.append("资源名称为空：[" + val + "]");
							recordHasError = true;
						}
					break;	
					case "ne_code":
						String ne_code = vals[3];
						if(StrUtil.isBlank(ne_code)){
							errorInfoBuffer.append("资源标识为空：[" + val + "]");
							recordHasError = true;
						}else{
							if(vals[4].equals("1")){
								boolean hexists = baseresource_cuidcode.hexists("CUIDCODE_"+cont_rediskey, ne_code);
								if(!hexists){
									errorInfoBuffer.append("无法关联baseresource：[" + val + "]");
									recordHasError = true;
								}
							}else if(vals[4].equals("2")){
								List<String> hvals = baseresource_cuidcode.hvals("CUIDCODE_"+cont_rediskey);
								if(!hvals.contains(ne_code)){
									errorInfoBuffer.append("无法关联baseresource：[" + val + "]");
									recordHasError = true;
								}
							}
						}
					break;	
					case "code_type":
						String code_type = vals[4];
						if(StrUtil.isBlank(code_type)){
							errorInfoBuffer.append("标识类型为空：[" + val + "]");
							recordHasError = true;
						}else if(code_type.equals("1")){
							System.out.println("取的是cid");
						}else if(code_type.equals("2")){
							System.out.println("取的是编码");
						}else{
							errorInfoBuffer.append("标识类型不正确：[" + val + "]");
							recordHasError = true;
						}
					break;	
					case "city_name":
						String city_name = vals[5];
						if(StrUtil.isBlank(city_name)){
							errorInfoBuffer.append("归属地市/区为空：[" + val + "]");
							recordHasError = true;
						}
					break;	
					case "start_time":
						String start_time = vals[6];
						if(StrUtil.isBlank(start_time)){
							errorInfoBuffer.append("记录起始时间为空：[" + val + "]");
							recordHasError = true;
						}
					break;	
					case "stop_time":
						String stop_time = vals[7];
						if(StrUtil.isBlank(stop_time)){
							errorInfoBuffer.append("记录结束时间");
							recordHasError = true;
						}
					break;	
					case "total_degree":
						String total_degree = vals[8];
						if(StrUtil.isBlank(total_degree)){
							errorInfoBuffer.append("总表读数为空：[" + val + "]");
							recordHasError = true;
						}
					break;	
					case "total_state":
						String total_state = vals[9];
						if(StrUtil.isBlank(total_state)){
							errorInfoBuffer.append("总电表状态位为空：[" + val + "]");
							recordHasError = true;
						}else if(total_state.length() == 1 && total_state.charAt(0) == 1){
							System.out.println("正常");
						}else if(total_state.length() == 1 && total_state.charAt(0) == 2){
							System.out.println("换表");
						}else if(total_state.length() == 1 && total_state.charAt(0) == 3){
							System.out.println("清零");
						}
					break;	
					case "equipment_degree":
						try {
							String equipment_degree = vals[10];
							if (!StrUtil.isBlank(equipment_degree)) {
								if (equipment_degree.indexOf(String.valueOf("\\.")) != -1) {
									errorInfoBuffer.append("主设备电表读数格式不正确[" + val+ "]");
									recordHasError = true;
								}
							}
						} catch (Exception e) {
							errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
							recordHasError = true;
						}
					break;	
					case "equipment_state":
						try {
							String equipment_state = vals[11];
								if (!StrUtil.isBlank(equipment_state)) {
									char num[] = equipment_state.toCharArray();
									for (int i = 0; i < num.length; i++) {
										if (Character.isDigit(num[i])) {
											if (equipment_state.length() == 1 && equipment_state.charAt(0) == 0) {
												System.out.println("正常");
											}else if (equipment_state.length() == 1 && equipment_state.charAt(0) == 1) {
												System.out.println("换表");
											}else if (equipment_state.length() == 1 && equipment_state.charAt(0) == 2) {
												System.out.println("清零");
											}
										}else{
											errorInfoBuffer.append("主设备电表状态位不是整数：[" + val+ "]");
											recordHasError = true;
										}
									}
							}
						} catch (Exception e) {
							errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
							recordHasError = true;
						}
					break;	
					case "ac_degree":
						try {
							String ac_degree = vals[12];
							if (!StrUtil.isBlank(ac_degree)) {
								if (ac_degree.indexOf(String.valueOf("\\.")) != -1) {
									errorInfoBuffer.append("空调系统电表读数格式不正确[" + val+ "]");
									recordHasError = true;
								}
							}
						} catch (Exception e) {
							errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
							recordHasError = true;
						}
					break;	
					case "ac_state":
						try {
							String ac_state = vals[13];
								if (!StrUtil.isBlank(ac_state)) {
									char num[] = ac_state.toCharArray();
									for (int i = 0; i < num.length; i++) {
										if (Character.isDigit(num[i])) {
											if (ac_state.length() == 1 && ac_state.charAt(0) == 0) {
												System.out.println("正常");
											}else if (ac_state.length() == 1 && ac_state.charAt(0) == 1) {
												System.out.println("换表");
											}else if (ac_state.length() == 1 && ac_state.charAt(0) == 2) {
												System.out.println("清零");
											}
										}else{
											recordHasError = true;
											errorInfoBuffer.append("空调系统电表状态位不是整数：[" + val+ "]");
										}
									}
							}
						} catch (Exception e) {
							errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
							recordHasError = true;
						}
					break;	
					case "ex_tempreture":
						try {
							String ex_tempreture = vals[14];
							if (!StrUtil.isBlank(ex_tempreture)) {
								if (ex_tempreture.indexOf(String.valueOf("\\.")) != -1) {
									errorInfoBuffer.append("基站室内日平均温度格式不正确[" + val+ "]");
									recordHasError = true;
								}
							}
						} catch (Exception e) {
							errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
							recordHasError = true;
						}
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
			String comment = DataType.RING_BTSROOM_METER +"或"+DataType.RING_MACHROOM_METER  + commonValidateError.get("commonValidate") +" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
				errorContents.put("comment", comment);
			errorInfo = allErrorInfoBuffer.toString();
			if(StrUtil.isNotEmpty(errorInfo)){
				throw new BusinessException(errorInfo);
			}
		} finally{
			keys.batchDel(cont_rediskey);
		}
	}
}