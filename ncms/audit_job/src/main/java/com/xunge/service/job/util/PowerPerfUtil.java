package com.xunge.service.job.util;


import com.xunge.dao.basedata.ring.PowerPerfVOMapper;

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
import com.xunge.model.basedata.DatBasestationVOExample;
import com.xunge.model.basedata.ring.PowerPerfVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.util.DataType;
import com.xunge.util.DateConverter;
import com.xunge.util.StrUtil;
import com.xunge.util.redis.JedisUtil;
import com.xunge.util.redis.JedisUtil.Keys;

/**
 * DatBaseSite工具类
 */
@Component
public class PowerPerfUtil {
	
	@Resource
	private DatBaseresourceVOMapper datBaseresourceVOMapper;
	
	
	private static PowerPerfUtil powerPerfUtil;
	
	@PostConstruct 
    public void init() { 
		powerPerfUtil = this; 
	    powerPerfUtil.datBaseresourceVOMapper = this.datBaseresourceVOMapper; 
	} 
	
	public static boolean isNumber(String str) {
		boolean flag = true;
		int index = str.lastIndexOf(".");
		for(int i = 0;i<str.length();i++){
			if(index==0||(!Character.isDigit(str.charAt(i))&&i!=index)){
				flag =false;
				break;
			}
		}
		return flag;
	}

	public static void dealPowerColumns(PowerPerfVO powerPerfVO,
			List<String> columns, String[] valueArray,
			SysProvinceVO sysProvinceVO) {
		int columnSize = columns.size();
		/*
		 * String cityId=""; String countyId="";
		 */
		for (int i = 0; i < columnSize; i++) {

			String columnName = columns.get(i);
			String value = valueArray[i];
			if (StringUtils.isNoneBlank(value)) {
				switch (columnName.toLowerCase()) {

				case "province_code":
					if (value.equalsIgnoreCase(sysProvinceVO.getPrvCode())) {
						powerPerfVO.setPrvId(sysProvinceVO.getPrvId());
						powerPerfVO.setPrvSname(sysProvinceVO.getPrvSname());
					}
					break;
				case "ne_class":
					powerPerfVO.setResourceType(Integer.parseInt(value));
					break;
				case "ne_name":
					powerPerfVO.setResourceName(value);
					break;
				case "ne_code":
					powerPerfVO.setResourceCode(value);
					break;
				case "code_type":
					powerPerfVO.setCodeType(Integer.parseInt(value));
					break;
				/*
				 * case "city_name": powerPerfVO.setRegId(value); break;
				 */
				case "start_time":
					Date startDate = DateConverter.converteToDateTime(value);
					if (startDate != null) {
						powerPerfVO.setStartTime(startDate);
					}
					break;
				case "stop_time":
					Date stopDate = DateConverter.converteToDateTime(value);
					if (stopDate != null) {
						powerPerfVO.setStopTime(stopDate);
					}
					break;
				case "smps_loss":
					powerPerfVO.setSmpsLoss(Float.parseFloat(value));
					break;
				case "v1":
					powerPerfVO.setV1(Float.parseFloat(value));
					break;
				case "a1":
					powerPerfVO.setA1(Float.parseFloat(value));
					break;
				case "v2":
					powerPerfVO.setV2(Float.parseFloat(value));
					break;
				case "a2":
					powerPerfVO.setA2(Float.parseFloat(value));
					break;
				case "v3":
					powerPerfVO.setV3(Float.parseFloat(value));
					break;
				case "a3":
					powerPerfVO.setA3(Float.parseFloat(value));
					break;
				case "v4":
					powerPerfVO.setV4(Float.parseFloat(value));
					break;
				case "a4":
					powerPerfVO.setA4(Float.parseFloat(value));
					break;
				case "v5":
					powerPerfVO.setV5(Float.parseFloat(value));
					break;
				case "a5":
					powerPerfVO.setA5(Float.parseFloat(value));
					break;
				case "v6":
					powerPerfVO.setV6(Float.parseFloat(value));
					break;
				case "a6":
					powerPerfVO.setA6(Float.parseFloat(value));
					break;
				case "v7":
					powerPerfVO.setV7(Float.parseFloat(value));
					break;
				case "a7":
					powerPerfVO.setA7(Float.parseFloat(value));
					break;
				case "v8":
					powerPerfVO.setV8(Float.parseFloat(value));
					break;
				case "a8":
					powerPerfVO.setA8(Float.parseFloat(value));
					break;
				case "v9":
					powerPerfVO.setV9(Float.parseFloat(value));
					break;
				case "a9":
					powerPerfVO.setA9(Float.parseFloat(value));
					break;
				case "v10":
					powerPerfVO.setV10(Float.parseFloat(value));
					break;
				case "a10":
					powerPerfVO.setA10(Float.parseFloat(value));
					break;
				case "v11":
					powerPerfVO.setV11(Float.parseFloat(value));
					break;
				case "a11":
					powerPerfVO.setA11(Float.parseFloat(value));
					break;
				case "v12":
					powerPerfVO.setV12(Float.parseFloat(value));
					break;
				case "a12":
					powerPerfVO.setA12(Float.parseFloat(value));
					break;
				case "v13":
					powerPerfVO.setV13(Float.parseFloat(value));
					break;
				case "a13":
					powerPerfVO.setA13(Float.parseFloat(value));
					break;
				case "v14":
					powerPerfVO.setV14(Float.parseFloat(value));
					break;
				case "a14":
					powerPerfVO.setA14(Float.parseFloat(value));
					break;
				case "v15":
					powerPerfVO.setV15(Float.parseFloat(value));
					break;
				case "a15":
					powerPerfVO.setA15(Float.parseFloat(value));
					break;
				case "v16":
					powerPerfVO.setV16(Float.parseFloat(value));
					break;
				case "a16":
					powerPerfVO.setA16(Float.parseFloat(value));
					break;
				case "v17":
					powerPerfVO.setV17(Float.parseFloat(value));
					break;
				case "a17":
					powerPerfVO.setA17(Float.parseFloat(value));
					break;
				case "v18":
					powerPerfVO.setV18(Float.parseFloat(value));
					break;
				case "a18":
					powerPerfVO.setA18(Float.parseFloat(value));
					break;
				case "v19":
					powerPerfVO.setV19(Float.parseFloat(value));
					break;
				case "a19":
					powerPerfVO.setA19(Float.parseFloat(value));
					break;
				case "v20":
					powerPerfVO.setV20(Float.parseFloat(value));
					break;
				case "a20":
					powerPerfVO.setA20(Float.parseFloat(value));
					break;
				case "v21":
					powerPerfVO.setV21(Float.parseFloat(value));
					break;
				case "a21":
					powerPerfVO.setA21(Float.parseFloat(value));
					break;
				case "v22":
					powerPerfVO.setV22(Float.parseFloat(value));
					break;
				case "a22":
					powerPerfVO.setA22(Float.parseFloat(value));
					break;
				case "v23":
					powerPerfVO.setV23(Float.parseFloat(value));
					break;
				case "a23":
					powerPerfVO.setA23(Float.parseFloat(value));
					break;
				case "v24":
					powerPerfVO.setV24(Float.parseFloat(value));
					break;
				case "a24":
					powerPerfVO.setA24(Float.parseFloat(value));
					break;
				}
			}
		}
		/*
		 * if(StringUtils.isNoneBlank(countyId)){
		 * powerPerfVO.setRegId(countyId); } else
		 * if(StringUtils.isNoneBlank(cityId)){ powerPerfVO.setRegId(cityId); }
		 */
	}

	public static void analysisPowerCSVFile(Map<String, String> errorContents,
			Map<String, String> contents, List<String> columns,
			String errorInfo, char divideFlag,
			Map<String, String> commonValidateError,SysProvinceVO sysProvinceVO) throws Exception {
		StringBuffer allErrorInfoBuffer = new StringBuffer();
		int totalRecordNum = 0;
		int errRecordNum = 0;
		
		JedisUtil jutil = JedisUtil.getInstance();
		JedisUtil.Keys keys= jutil.new Keys();
		JedisUtil.Hash baseresource_cuidcode = jutil.new Hash();
		DatBaseresourceVOMapper dbv = powerPerfUtil.datBaseresourceVOMapper;
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
						if (StrUtil.isBlank(province_code)) {
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
						if (StrUtil.isBlank(ne_class)) {
							errorInfoBuffer.append("资源类型为空：[" + val + "]");
							recordHasError = true;
						}else if(!ne_class.equals("10005")||!ne_class.equals("10006")){
							errorInfoBuffer.append("资源类型不正确：[" + val + "]");
							recordHasError = true;
						}
						break;
					case "ne_name":
						String ne_name = vals[2];
						if (StrUtil.isBlank(ne_name)) {
							errorInfoBuffer.append("资源名称为空：[" + val + "]");
							recordHasError = true;
						}
						break;
					case "ne_code":
						String ne_code = vals[3];
						if (StrUtil.isBlank(ne_code)) {
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
						if (StrUtil.isBlank(code_type)) {
							errorInfoBuffer.append("标识类型为空：[" + val + "]");
							recordHasError = true;
						} else if (code_type.equals("1")) {
							System.out.println("取的cid");
						} else if (code_type.equals("2")) {
							System.out.println("取的编码");
						}else{
							errorInfoBuffer.append("标识类型不正确：[" + val + "]");
							recordHasError = true;
						}
						break;
					case "city_name":
						String city_name = vals[5];
						if (StrUtil.isBlank(city_name)) {
							errorInfoBuffer.append("归属地市/区为空：[" + val + "]");
							recordHasError = true;
						}
						break;
					case "start_time":
						String start_time = vals[6];
						if (StrUtil.isBlank(start_time)) {
							errorInfoBuffer.append("记录起始时间为空：[" + val + "]");
							recordHasError = true;
						}
						break;
					case "stop_time":
						String stop_time = vals[7];
						if (StrUtil.isBlank(stop_time)) {
							errorInfoBuffer.append("记录结束时间为空：[" + val + "]");
							recordHasError = true;
						}
						break;
					case "v1":
						String V1 = vals[8];
						if (StrUtil.isBlank(V1)) {
							errorInfoBuffer.append("从0点到1点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V1)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}

						break;
					case "a1":
						String A1 = vals[9];
						if (StrUtil.isBlank(A1)) {
							errorInfoBuffer.append("从0点到1点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A1)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v2":
						String V2 = vals[10];
						if (StrUtil.isBlank(V2)) {
							errorInfoBuffer.append("从1点到2点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V2)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a2":
						String A2 = vals[11];
						if (StrUtil.isBlank(A2)) {
							errorInfoBuffer.append("从1点到2点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A2)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v3":
						String V3 = vals[12];
						if (StrUtil.isBlank(V3)) {
							errorInfoBuffer.append("从2点到3点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V3)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a3":
						String A3 = vals[13];
						if (StrUtil.isBlank(A3)) {
							errorInfoBuffer.append("从2点到3点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A3)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v4":
						String V4 = vals[14];
						if (StrUtil.isBlank(V4)) {
							errorInfoBuffer.append("从3点到4点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V4)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a4":
						String A4 = vals[15];
						if (StrUtil.isBlank(A4)) {
							errorInfoBuffer.append("从3点到4点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A4)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v5":
						String V5 = vals[16];
						if (StrUtil.isBlank(V5)) {
							errorInfoBuffer.append("从4点到5点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V5)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a5":
						String A5 = vals[17];
						if (StrUtil.isBlank(A5)) {
							errorInfoBuffer.append("从4点到5点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A5)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v6":
						String V6 = vals[18];
						if (StrUtil.isBlank(V6)) {
							errorInfoBuffer.append("从5点到6点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V6)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a6":
						String A6 = vals[19];
						if (StrUtil.isBlank(A6)) {
							errorInfoBuffer.append("从5点到6点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A6)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v7":
						String V7 = vals[20];
						if (StrUtil.isBlank(V7)) {
							errorInfoBuffer.append("从6点到7点采集到的开关电源输出电压平均值为空：" + val
									+ "]");
							recordHasError = true;
						}else if(!isNumber(V7)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a7":
						String A7 = vals[21];
						if (StrUtil.isBlank(A7)) {
							errorInfoBuffer.append("从6点到7点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A7)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v8":
						String V8 = vals[22];
						if (StrUtil.isBlank(V8)) {
							errorInfoBuffer.append("从7点到8点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V8)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a8":
						String A8 = vals[23];
						if (StrUtil.isBlank(A8)) {
							errorInfoBuffer.append("从7点到8点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A8)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v9":
						String V9 = vals[24];
						if (StrUtil.isBlank(V9)) {
							errorInfoBuffer.append("从8点到9点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V9)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a9":
						String A9 = vals[25];
						if (StrUtil.isBlank(A9)) {
							errorInfoBuffer.append("从8点到9点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A9)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v10":
						String V10 = vals[26];
						if (StrUtil.isBlank(V10)) {
							errorInfoBuffer.append("从9点到10点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V10)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a10":
						String A10 = vals[27];
						if (StrUtil.isBlank(A10)) {
							errorInfoBuffer.append("从9点到10点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A10)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v11":
						String V11 = vals[28];
						if (StrUtil.isBlank(V11)) {
							errorInfoBuffer.append("从10点到11点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V11)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a11":
						String A11 = vals[29];
						if (StrUtil.isBlank(A11)) {
							errorInfoBuffer.append("从10点到11点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A11)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v12":
						String V12 = vals[30];
						if (StrUtil.isBlank(V12)) {
							errorInfoBuffer.append("从11点到12点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V12)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a12":
						String A12 = vals[31];
						if (StrUtil.isBlank(A12)) {
							errorInfoBuffer.append("从11点到12点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A12)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v13":
						String V13 = vals[32];
						if (StrUtil.isBlank(V13)) {
							errorInfoBuffer.append("从12点到13点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V13)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a13":
						String A13 = vals[33];
						if (StrUtil.isBlank(A13)) {
							errorInfoBuffer.append("从12点到13点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A13)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v14":
						String V14 = vals[34];
						if (StrUtil.isBlank(V14)) {
							errorInfoBuffer.append("从13点到14点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V14)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a14":
						String A14 = vals[35];
						if (StrUtil.isBlank(A14)) {
							errorInfoBuffer.append("从13点到14点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A14)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v15":
						String V15 = vals[36];
						if (StrUtil.isBlank(V15)) {
							errorInfoBuffer.append("从14点到15点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V15)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a15":
						String A15 = vals[37];
						if (StrUtil.isBlank(A15)) {
							errorInfoBuffer.append("从14点到15点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A15)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v16":
						String V16 = vals[38];
						if (StrUtil.isBlank(V16)) {
							errorInfoBuffer.append("从15点到16点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V16)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a16":
						String A16 = vals[39];
						if (StrUtil.isBlank(A16)) {
							errorInfoBuffer.append("从15点到16点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A16)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v17":
						String V17 = vals[40];
						if (StrUtil.isBlank(V17)) {
							errorInfoBuffer.append("从16点到17点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V17)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a17":
						String A17 = vals[41];
						if (StrUtil.isBlank(A17)) {
							errorInfoBuffer.append("从16点到17点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A17)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v18":
						String V18 = vals[42];
						if (StrUtil.isBlank(V18)) {
							errorInfoBuffer.append("从17点到18点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V18)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a18":
						String A18 = vals[43];
						if (StrUtil.isBlank(A18)) {
							errorInfoBuffer.append("从17点到18点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A18)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v19":
						String V19 = vals[44];
						if (StrUtil.isBlank(V19)) {
							errorInfoBuffer.append("从18点到19点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V19)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a19":
						String A19 = vals[45];
						if (StrUtil.isBlank(A19)) {
							errorInfoBuffer.append("从18点到19点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A19)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v20":
						String V20 = vals[46];
						if (StrUtil.isBlank(V20)) {
							errorInfoBuffer.append("从19点到20点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V20)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a20":
						String A20 = vals[47];
						if (StrUtil.isBlank(A20)) {
							errorInfoBuffer.append("从19点到20点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A20)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v21":
						String V21 = vals[48];
						if (StrUtil.isBlank(V21)) {
							errorInfoBuffer.append("从20点到21点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V21)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a21":
						String A21 = vals[49];
						if (StrUtil.isBlank(A21)) {
							errorInfoBuffer.append("从20点到21点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A21)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v22":
						String V22 = vals[50];
						if (StrUtil.isBlank(V22)) {
							errorInfoBuffer.append("从21点到22点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V22)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a22":
						String A22 = vals[51];
						if (StrUtil.isBlank(A22)) {
							errorInfoBuffer.append("从21点到22点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A22)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v23":
						String V23 = vals[52];
						if (StrUtil.isBlank(V23)) {
							errorInfoBuffer.append("从22点到23点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V23)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a23":
						String A23 = vals[53];
						if (StrUtil.isBlank(A23)) {
							errorInfoBuffer.append("从22点到23点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A23)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "v24":
						String V24 = vals[54];
						if (StrUtil.isBlank(V24)) {
							errorInfoBuffer.append("从23点到24点采集到的开关电源输出电压平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(V24)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "a24":
						String A24 = vals[55];
						if (StrUtil.isBlank(A24)) {
							errorInfoBuffer.append("从23点到24点采集到的开关电源输出电流平均值为空：["
									+ val + "]");
							recordHasError = true;
						}else if(!isNumber(A24)){ 
							errorInfoBuffer.append("非法数据：["
									+ val + "]");
							recordHasError = true;
						}
						break;
					case "smps_loss":
						String smps_loss = vals[56];
						if (StrUtil.isBlank(smps_loss)) {
							errorInfoBuffer.append("开关电源转换损耗为空：[" + val + "]");
							recordHasError = true;
						}
						break;

					}
				}
				if (recordHasError) {
					errRecordNum = errRecordNum + 1;
					errorKeyLst.add(key);
				}
				// 每行错误日志进行换行处理
				if (StrUtil.isNotEmpty(errorInfoBuffer.toString())) {
					allErrorInfoBuffer.append(errorInfoBuffer).append('\n');
				}
			}
			// 从内容中删去错误记录
			for (String errorKey : errorKeyLst) {
				contents.remove(errorKey);
			}
			String comment = DataType.RING_BTSROOM_POWER + "或"
					+ DataType.RING_MACHROOM_POWER
					+ commonValidateError.get("commonValidate") + " 校验成功"
					+ (totalRecordNum - errRecordNum) + "条,校验失败" + errRecordNum
					+ "条。";
			errorContents.put("comment", comment);
			errorInfo = allErrorInfoBuffer.toString();
			if (StrUtil.isNotEmpty(errorInfo)) {
				throw new BusinessException(errorInfo);
			}
		}finally{
			keys.batchDel(cont_rediskey);
		}
	}
}