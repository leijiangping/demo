package com.xunge.service.job.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import net.sf.json.JSONObject;

import com.xunge.exception.BusinessException;
import com.xunge.model.basedata.DatContractVO;
import com.xunge.model.basedata.DatSupplierVO;
import com.xunge.model.selfelec.EleContract;
import com.xunge.model.selfrent.RentContractVO;
import com.xunge.service.basedata.DatPaymentPeriod.IDatPaymentperiodService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.util.DataType;
import com.xunge.util.DateUtil;
import com.xunge.util.StrUtil;

/**
 * Contract接口工具类
 */
public class ContractUtil {

	private static IDatPaymentperiodService datPaymentperiodService;

	private static ISysRegionService sysRegionService;

	public static String convertToContractDB(List<String> columns,
			String[] columnData, DatContractVO datContract,
			RentContractVO rentContract, EleContract eleContract,
			DatSupplierVO datSupplierVO,
			String prv_id, JSONObject ProvinceCache, JSONObject RegionCache, JSONObject PaymentCache) {

		for (int i = 0, length = columns.size(); i<length; i++) {
			String keyName = columns.get(i);

			switch (keyName.toLowerCase()) {
			case "contract_num":
				datContract.setContractsysId(columnData[0]);
				datContract.setOldContractId(columnData[0]);
				datContract.setContractFlow(columnData[0]);
				break;
			case "contract_code":
				datContract.setContractCode(columnData[1]);// 合同唯一识别号码
				eleContract.setElecontractNote(columnData[0]);// 冗余主合同编号，与主合同做关联
				rentContract.setRentcontractNote(columnData[0]);// 冗余主合同编号，与主合同做关联
				break;
			case "contract_name":
				datContract.setContractName(columnData[2]);
				break;
			case "contract_dept":
				if (!StrUtil.isBlank(columnData[3])) {
					datContract.setManagerDepartment(columnData[3]);
				}
				break;
			case "province":
				if (!StrUtil.isBlank(columnData[4])) {
					
					String prvcode = columnData[4];
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
					
					datContract.setPrvId(prvId);
					datSupplierVO.setPrvId(prvId);
				} else {
					datContract.setPrvId(prv_id);// 省份传入可能为空
					datContract.setIsDownshare(1);
				}
				break;
			case "city":
				if (!StrUtil.isBlank(columnData[5])) {
					String citycode = columnData[5];
					String cityId = RegionCache.get(citycode)+"";
					if (cityId.length()>0 && RegionCache.get(citycode)!=null){
						datContract.setPregId(cityId);
						datSupplierVO.setPregId(cityId);
						break;
					}
				} 
				datContract.setIsDownshare(1);
				datSupplierVO.setIsDownshare(1);
				break;
			case "region":
				if (columnData.length>6 && !StrUtil.isBlank(columnData[6])) {
					String regcode = columnData[6];
					String regId = RegionCache.get(regcode)+"";
					if (regId.length()>0 && RegionCache.get(regcode)!=null){
						datContract.setRegId(regId);
						datSupplierVO.setRegId(regId);
						break;
					}
				}
				datContract.setIsDownshare(1);
				datSupplierVO.setIsDownshare(1);
				break;
			case "org_name":
				if (columnData.length>7 && !StrUtil.isBlank(columnData[7])) {
					datContract.setContractCheckname2(columnData[7]);
				}
				break;
			case "contract_type": // 0-房租合同1-电费合同2-综合合同
				int contract_type=2;//默认综合合同
				if (columnData.length>8 && !StrUtil.isBlank(columnData[8])) {
					contract_type=Integer.parseInt(columnData[8])-1;
				}
				datContract.setContractType(contract_type);
				break;
			case "vendor_number":
				if (columnData.length>9 && !StrUtil.isBlank(columnData[9])) {
					datSupplierVO.setSupplierCode(columnData[9]);
				}
				break;
			case "vendor_name":
				if (columnData.length>10 && !StrUtil.isBlank(columnData[10])) {
					datSupplierVO.setSupplierName(columnData[10]);
				}
				break;
			case "vendor_site":
				if (columnData.length>11 && !StrUtil.isBlank(columnData[11])) {
					datSupplierVO.setSupplierAddress(columnData[11]);
					datSupplierVO.setSupplierSite(columnData[11]);
				}
				break;
			case "signn_date":
				if (columnData.length>12 && !StrUtil.isBlank(columnData[12])) {
					Date signDate = DateUtil.parseDate(columnData[12]);
					datContract.setContractSigndate(signDate);
				}
				break;
			case "start_date":
				if (columnData.length>13 && !StrUtil.isBlank(columnData[13])) {
					Date signStartDate = DateUtil.parseDate(columnData[13]);
					datContract.setContractStartdate(signStartDate);
				}
				break;
			case "end_date":
				if (columnData.length>14 && !StrUtil.isBlank(columnData[14])) {
					Date signEndDate = DateUtil.parseDate(columnData[14]);
					datContract.setContractEnddate(signEndDate);
				}
				break;
			case "contract_amount":
				if (columnData.length>15 && !StrUtil.isBlank(columnData[15])) {// 电费合同不必填写；
					rentContract.setTotalAmount(Double
							.parseDouble(columnData[15]));
				}
				break;
			case "rent_if_include_tax":
				if (columnData.length>16 && !StrUtil.isBlank(columnData[16])) {// 电费合同不必填写；
					rentContract
							.setIncludeTax(Integer.parseInt(columnData[16]));
				}
				break;
			case "rent_tax":
				if (columnData.length>17 && !StrUtil.isBlank(columnData[17])) {// 电费合同不必填写；
					rentContract.setBillamountTaxratio(Double
							.parseDouble(columnData[17]));
				}
				break;
			case "tax_amount":
				if (columnData.length>18 && !StrUtil.isBlank(columnData[18])) {// 电费合同不必填写；
					rentContract.setTaxAmount(Double
							.parseDouble(columnData[18]));
				}
				break;
			case "contract_amount_total":
				if (columnData.length>19 && !StrUtil.isBlank(columnData[19])) {// 电费合同不必填写；
					rentContract.setTotalAmount(Double
							.parseDouble(columnData[19]));
				}
				break;
			case "contract_description":
				if (columnData.length>20 && !StrUtil.isBlank(columnData[20])) {
					datContract.setContractIntroduction(columnData[20]);
				}
				break;
			case "contract_status":
				if (columnData.length>21 && !StrUtil.isBlank(columnData[21])) {
					int state = Integer.parseInt(columnData[21]);
					if (state == 2)
						state = -1;
					else if (state == 5 || state == 6)
						state = 0;
					else if (state == 9)
						state = 2;
					else if (state == 7 || state == 10)
						state = 9;
					datContract.setContractState(state);
				}
				break;
			case "rent_year_money":
				if (columnData.length>22 && !StrUtil.isBlank(columnData[22])) {// 电费合同不必填写；
					rentContract.setYearAmount(Double
							.parseDouble(columnData[22]));
				}
				break;
			case "rent_area":
				if (columnData.length>23 && !StrUtil.isBlank(columnData[23])) {// 电费合同不必填写；
					rentContract.setPropertyArea(Double
							.parseDouble(columnData[23]));
				}
				break;
			case "rent_period":
				if (columnData.length>24 && !StrUtil.isBlank(columnData[24])) {
					String paymentVal = columnData[24];
					String paymentId = ProvinceCache.get(paymentVal)+"";
					if(ProvinceCache.get(paymentVal)==null){//查找缓存中是否存在该code
						paymentId = null;
					}
					rentContract.setPaymentperiodId(paymentId);
				}
				break;
			case "electric_type":
				if (columnData.length>25 && !StrUtil.isBlank(columnData[25])) {// 房租合同不必填写；
					// 1、直供电2、转供电
					eleContract.setSupplyMethod(Integer
							.parseInt(columnData[25]));
				}
				break;
			case "electric_contract_type":
				if (columnData.length>26 && !StrUtil.isBlank(columnData[26])) {// 房租合同不必填写；
					eleContract.setIsIncludeAll(Integer
							.parseInt(columnData[26]));
				}
				break;
			case "electric_contract_amount":
				if (columnData.length<26 || StrUtil.isBlank(columnData[26]))
					break;
				if (Integer.parseInt(columnData[26]) == 1) {// 电费合同类型为包干类型时填写
					eleContract
							.setContractMoney(new BigDecimal(columnData[27]));
				}
				break;
			case "electric_contract_tax":
				if (columnData.length<26 || StrUtil.isBlank(columnData[26]))
					break;
				if (Integer.parseInt(columnData[26]) == 1) {// 电费合同类型为包干类型时填写
					eleContract.setContractTax(new BigDecimal(columnData[28]));
				}
				break;
			case "electric_contract_amount_total":
				if (columnData.length<26 || StrUtil.isBlank(columnData[26]))
					break;
				if (Integer.parseInt(columnData[26]) == 1) {// 电费合同类型为包干类型时填写
					eleContract.setContractTotalAmount(new BigDecimal(
							columnData[29]));
				}
				break;
			case "electric_month_money":
				if (columnData.length<26 || StrUtil.isBlank(columnData[26]))
					break;
				if (Integer.parseInt(columnData[26]) == 1) {// 电费合同类型为包干类型时填写
					eleContract.setContractYearAmount(new BigDecimal(
							columnData[30]));
				}
				break;
			case "if_depend_meter":
				if (columnData.length>31 && !StrUtil.isBlank(columnData[31])) {// 房租合同不必填写；
					eleContract.setIndependentMeter(Integer
							.parseInt(columnData[31]));
				}
				break;
			case "CMCC_proportion":
				if (columnData.length>32 && !StrUtil.isBlank(columnData[32])) {// 房租合同不必填写；
					eleContract.setCmccRatio(new BigDecimal(columnData[32]));
				}
				break;
			case "CUCC_proportion":
				if (columnData.length>33 && !StrUtil.isBlank(columnData[33])) {// 房租合同不必填写；
					eleContract.setUnicomRatio(new BigDecimal(columnData[33]));
				}
				break;
			case "CTCC_proportion":
				if (columnData.length>34 && !StrUtil.isBlank(columnData[34])) {// 房租合同不必填写；
					eleContract.setTelcomRatio(new BigDecimal(columnData[34]));
				}
				break;
			case "electric_if_include_tax":
				if (columnData.length>35 && !StrUtil.isBlank(columnData[35])) {// 房租合同不必填写；
					eleContract.setIncludePriceTax(Integer
							.parseInt(columnData[35]));
				}
				break;
			case "price_type":
				if (columnData.length>36 && !StrUtil.isBlank(columnData[36])) {// 房租合同不必填写；
					eleContract.setPriceType(Integer.parseInt(columnData[36]));
				}
				break;
			case "electric_price":
				if (columnData.length>37 && !StrUtil.isBlank(columnData[37])) {// 房租合同不必填写；
					eleContract.setElecontractPrice(columnData[37]);
				}
				break;
			case "flat_price":
				if (columnData.length>38 && !StrUtil.isBlank(columnData[38])) {// 房租合同不必填写；
					eleContract.setFlatPrice(new BigDecimal(columnData[38]));
				}
				break;
			case "peak_price":
				if (columnData.length>39 && !StrUtil.isBlank(columnData[39])) {// 房租合同不必填写；
					eleContract.setPeakPrice(new BigDecimal(columnData[39]));
				}
				break;
			case "valley_price":
				if (columnData.length>40 && !StrUtil.isBlank(columnData[40])) {// 房租合同不必填写；
					eleContract.setValleyPrice(new BigDecimal(columnData[40]));
				}
				break;
			case "ace_price":
				if (columnData.length>41 && !StrUtil.isBlank(columnData[41])) {// 房租合同不必填写；
					eleContract.setTopPrice(new BigDecimal(columnData[41]));
				}
				break;
			case "electric_period":
				if (columnData.length>42 && !StrUtil.isBlank(columnData[42])) {// 房租合同不必填写；
					eleContract.setPaymentMethod(Integer
							.parseInt(columnData[42]));
				}
				break;
			case "electric_tax":
				if (columnData.length>43 && !StrUtil.isBlank(columnData[43])) {
					String taxRate = "".equals(columnData[43].trim()) ? "0"
							: columnData[43].trim();
					eleContract.setTaxRate(new BigDecimal(taxRate));
				}
				break;
			case "belong_room":
				if (columnData.length>44 && !StrUtil.isBlank(columnData[44])) {
					datContract.setContractSpaceresource(columnData[44]);
				}
				break;
			}
		}
		return "";
	}
	
	private static JSONObject getRegidlistByPrvid(String prvId){
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("prvId", prvId);
		List<Map<String, String>> list= sysRegionService.getRegionsByPrvid(paramMap);
		JSONObject jsonO = new JSONObject();
		for(Map<String,String> map :list){
			jsonO.put(map.get("reg_code"), map.get("reg_id"));
		}
		return jsonO;
	}

	public static void analysisContractCSVFile(Map<String, String> errorContents,Map<String, String> contents,
			List<String> columns, String errorInfo, char divideFlag,Map<String,String> commonValidateError) throws Exception {
		StringBuffer allErrorInfoBuffer = new StringBuffer();
		//记录总条数
		int totalRecordNum = 0;
		int errRecordNum = 0;
		List<String> errorKeyLst = new ArrayList<String>();
		for (Map.Entry<String, String> contents_entry : contents.entrySet()) {
			StringBuffer errorInfoBuffer = new StringBuffer();
			totalRecordNum = totalRecordNum + 1;
			boolean recordHasError = false;
			String key = contents_entry.getKey();
			String val = contents_entry.getValue();
			//String[] vals = val.split("\\"+divideFlag+"",-1);
			String[] vals = StrUtil.CSVtoArray(val, divideFlag+"");
			for (String keyName : columns) {

				switch (keyName.toLowerCase()) {
				case "contract_num":
					String contract_num = vals[0];
					if(StrUtil.isBlank(contract_num)){
						errorInfoBuffer.append("[" + val+ "]错误:合同流水号为空");
						recordHasError = true;
					}
					break;
				case "contract_code":
//					String contract_code = vals[1];
//					if (StrUtil.isBlank(contract_code)) {
//						errorInfoBuffer.append("[" + val+ "]错误:合同编号为空");
//						recordHasError = true;
//					}
					break;
				case "contract_name":
//					String contract_name = vals[2];
//					if (StrUtil.isBlank(contract_name)) {
//						errorInfoBuffer.append("[" + val+ "]错误:合同名称为空：");
//						recordHasError = true;
//					}
					break;
//				case "contract_dept":
//					String contract_dept = vals[3];
//					if (!StrUtil.isBlank(contract_dept) && !StrUtil.isChinese(contract_dept)) {
//						errorInfoBuffer.append("合同起草部门名称不是汉字：[" + val+ "]");
//						recordHasError = true;
//					}
//					break;
//				case "province":
//						String province = vals[4];
//						if (!StrUtil.isBlank(province) && !StrUtil.isChinese(province)) {
//							errorInfoBuffer.append("所属省份不是汉字：[" + val+ "]");
//							recordHasError = true;
//						}
//					break;
//				case "city":
//						String city = vals[5];
//						if (!StrUtil.isBlank(city) && !StrUtil.isChinese(city)) {
//							errorInfoBuffer.append("所属城市不是汉字：[" + val+ "]");
//							recordHasError = true;
//						}
//					break;
//				case "region":
//						String region = vals[6];
//						if (!StrUtil.isBlank(region) && !StrUtil.isChinese(region)) {
//							errorInfoBuffer.append("所属区县不是汉字：[" + val+ "]");
//							recordHasError = true;
//						}
//					break;
//				case "org_name":
//						String org_name = vals[7];
//						if (!StrUtil.isBlank(org_name) && !StrUtil.isChinese(org_name)) {
//							errorInfoBuffer.append("对方签约单位名称不是汉字：[" + val+ "]");
//							recordHasError = true;
//						}
//					break;
				case "contract_type": // 0-房租合同1-电费合同2-综合合同
					try {
						String contract_type = vals[8];
						if (!StrUtil.isBlank(contract_type)) {
							if(NumberUtils.isDigits(contract_type)){
								Integer i = Integer.parseInt(contract_type);
								if(i>3||i<1){
									errorInfoBuffer.append("[" + val + "]错误：合同类型范围是1-3");
									recordHasError = true;
								}
							}else{
								errorInfoBuffer.append("[" + val + "]错误：合同类型不是数字");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "vendor_number":
					break;
				case "vendor_name":
					break;
				case "vendor_site":
					break;
				case "signn_date": // 1234-67-89
					try {
						String signn_date = vals[12];
						if (!StrUtil.isBlank(signn_date)) {
							if (signn_date.length() != 10) {
								errorInfoBuffer.append("[" + val+ "]错误:合同签约日期格式不正确");
								recordHasError = true;
							} else if (signn_date.charAt(4) != '-' || signn_date.charAt(7) != '-') {
								errorInfoBuffer.append("[" + val+ "]错误:合同签约日期格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]错误:检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "start_date":
					try {
						String start_date = vals[13];
						if (!StrUtil.isBlank(start_date)) {
							if (start_date.length() != 10) {
								errorInfoBuffer.append("[" + val+ "]错误:合同开始日期格式不正确");
								recordHasError = true;
							} else if (start_date.charAt(4) != '-' || start_date.charAt(7) != '-') {
								errorInfoBuffer.append("[" + val+ "]错误:合同开始日期格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "end_date":
					try {
						String end_date = vals[14];
						if (!StrUtil.isBlank(end_date)) {
							if (end_date.length() != 10) {
								errorInfoBuffer.append("[" + val+ "]检测异常：合同终止日期格式不正确");
								recordHasError = true;
							} else if (end_date.charAt(4) != '-' || end_date.charAt(7) != '-') {
								errorInfoBuffer.append("[" + val+ "]检测异常：合同终止日期格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "contract_amount":
					try {
						String contract_amount = vals[15];
						if (!StrUtil.isBlank(contract_amount)) {
							if (contract_amount.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:房租合同价款格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "rent_if_include_tax":
					try {
						String rent_if_include_tax = vals[16];
							if (!StrUtil.isBlank(rent_if_include_tax)) {
								if(NumberUtils.isDigits(rent_if_include_tax)){
									Integer i = Integer.parseInt(rent_if_include_tax);
									if(i>3||i<1){
										errorInfoBuffer.append("[" + val + "]错误：含税类型范围是1-3");
										recordHasError = true;
									}
								}else{
									errorInfoBuffer.append("[" + val + "]错误：含税类型不是数字");
									recordHasError = true;
								}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "rent_tax":
					try {
						String rent_tax = vals[17];
						if (!StrUtil.isBlank(rent_tax)) {
							if (rent_tax.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:房租税率格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "tax_amount":
					try {
						String tax_amount = vals[18];
						if (!StrUtil.isBlank(tax_amount)) {
							if (tax_amount.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:房租增值税金额格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "contract_amount_total":
					try {
						String contract_amount_total = vals[19];
						if (!StrUtil.isBlank(contract_amount_total)) {
							if (contract_amount_total.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:房租合同总金格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "contract_description":
					break;
				case "contract_status":
					try {
						String contract_status = vals[21];
						if (!StrUtil.isBlank(contract_status)) {
							if(NumberUtils.isDigits(contract_status)){
								Integer i = Integer.parseInt(contract_status);
								if(i>10||i<1){
									errorInfoBuffer.append("[" + val + "]错误：合同状态范围是1-10");
									recordHasError = true;
								}
							}else{
								errorInfoBuffer.append("[" + val + "]错误：合同状态不是数字");
								recordHasError = true;
							}
							
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "rent_year_money":
					try {
						String rent_year_money = vals[22];
						if (!StrUtil.isBlank(rent_year_money)) {
							if (rent_year_money.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:房租年租金格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "rent_area":
					try {
						String rent_area = vals[23];
						if (!StrUtil.isBlank(rent_area)) {
							if (rent_area.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]:租赁面积格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "rent_period":
					try {
						String rent_period = vals[24];
						if (!StrUtil.isBlank(rent_period)) {
							if(NumberUtils.isDigits(rent_period)){
								Integer i = Integer.parseInt(rent_period);
								if(i<0){
									errorInfoBuffer.append("[" + val + "]错误：房租缴费周期范围错误");
									recordHasError = true;
								}
							}else{
								errorInfoBuffer.append("[" + val + "]错误：房租缴费周期不是数字");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "electric_type":
					try {
						String electric_type = vals[25];
						if (!StrUtil.isBlank(electric_type)) {
							if(NumberUtils.isDigits(electric_type)){
								Integer i = Integer.parseInt(electric_type);
								if(i<1||i>2){
									errorInfoBuffer.append("[" + val + "]错误：供电类型范围错误");
									recordHasError = true;
								}
							}else{
								errorInfoBuffer.append("[" + val + "]错误：供电类型不是数字");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "electric_contract_type":
					try {
						String rent_period = vals[26];
						if (!StrUtil.isBlank(rent_period)) {
							char num[] = rent_period.toCharArray();
							for (int i = 0; i < num.length; i++) {
								if (Character.isDigit(num[i])) {
									System.out.println("是整数");
								}else{
									errorInfoBuffer.append("[" + val+ "]错误:电费合同类型不是整数");
									recordHasError = true;
								}
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "electric_contract_amount":
					try {
						String electric_contract_amount = vals[27];
						if (!StrUtil.isBlank(electric_contract_amount)) {
							if (electric_contract_amount.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:电费包干价款格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "electric_contract_tax":
					try {
						String electric_contract_tax = vals[28];
						if (!StrUtil.isBlank(electric_contract_tax)) {
							if (electric_contract_tax.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:电费包干税款格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "electric_contract_amount_total":
					try {
						String electric_contract_amount_total = vals[29];
						if (!StrUtil.isBlank(electric_contract_amount_total)) {
							if (electric_contract_amount_total.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:电费包干总金额格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "electric_month_money":
					try {
						String electric_month_money = vals[30];
						if (!StrUtil.isBlank(electric_month_money)) {
							if (electric_month_money.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:电费包干月支付价格格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "if_depend_meter":
					try {
						String if_depend_meter = vals[31];
						if (!StrUtil.isBlank(if_depend_meter)) {
							if (!StrUtil.isBlank(if_depend_meter)) {
								if(NumberUtils.isDigits(if_depend_meter)){
									Integer i = Integer.parseInt(if_depend_meter);
									if(i<1||i>2){
										errorInfoBuffer.append("[" + val + "]错误：是否挂表范围错误");
										recordHasError = true;
									}
								}else{
									errorInfoBuffer.append("[" + val + "]错误：是否挂表不是数字");
									recordHasError = true;
								}
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "CMCC_proportion":
					try {
						String CMCC_proportion = vals[32];
						if (!StrUtil.isBlank(CMCC_proportion)) {
							if (!CMCC_proportion.contains("%")) {
								errorInfoBuffer.append("[" + val+ "]错误:移动分摊比例格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "CUCC_proportion":
					try {
						String CUCC_proportion = vals[33];
						if (!StrUtil.isBlank(CUCC_proportion)) {
							if (!CUCC_proportion.contains("%")) {
								errorInfoBuffer.append("[" + val+ "]错误:联通分摊比例格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "CTCC_proportion":
					try {
						String CTCC_proportion = vals[34];
						if (!StrUtil.isBlank(CTCC_proportion)) {
							if (!CTCC_proportion.contains("%")) {
								errorInfoBuffer.append("[" + val+ "]错误:电信分摊比例格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "electric_if_include_tax":
					try {
						String electric_if_include_tax = vals[35];
						if (!StrUtil.isBlank(electric_if_include_tax)) {
							if (!StrUtil.isBlank(electric_if_include_tax)) {
								if(NumberUtils.isDigits(electric_if_include_tax)){
									Integer i = Integer.parseInt(electric_if_include_tax);
									if(i<1||i>2){
										errorInfoBuffer.append("[" + val + "]错误：电费单价是否含税范围错误");
										recordHasError = true;
									}
								}else{
									errorInfoBuffer.append("[" + val + "]错误：电费单价是否含税不是数字");
									recordHasError = true;
								}
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："
								+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "price_type":
					try {
						String price_type = vals[36];
						if (!StrUtil.isBlank(price_type)) {
							if (!StrUtil.isBlank(price_type)) {
								if(NumberUtils.isDigits(price_type)){
									Integer i = Integer.parseInt(price_type);
									if(i<1||i>2){
										errorInfoBuffer.append("[" + val + "]错误：单价类型范围错误");
										recordHasError = true;
									}
								}else{
									errorInfoBuffer.append("[" + val + "]错误：单价类型不是数字");
									recordHasError = true;
								}
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "electric_price":
					break;
				case "flat_price":
					try {
						String flat_price = vals[38];
						if (!StrUtil.isBlank(flat_price)) {
							if (flat_price.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:平单价格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "peak_price":
					try {
						String peak_price = vals[39];
						if (!StrUtil.isBlank(peak_price)) {
							if (peak_price.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:峰单价格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "valley_price":
					try {
						String valley_price = vals[40];
						if (!StrUtil.isBlank(valley_price)) {
							if (valley_price.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:谷单价格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "ace_price":
					try {
						String ace_price = vals[41];
						if (!StrUtil.isBlank(ace_price)) {
							if (ace_price.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:尖单价格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "electric_period":
					try {
						String electric_period = vals[42];
						if (!StrUtil.isBlank(electric_period)) {
							if (!StrUtil.isBlank(electric_period)) {
								if(NumberUtils.isDigits(electric_period)){
									errorInfoBuffer.append("[" + val + "]错误：房租缴费周期不是整数");
									recordHasError = true;
								}
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "electric_tax":
					try {
						String electric_tax = vals[43];
						if (!StrUtil.isBlank(electric_tax)) {
							if (electric_tax.indexOf(String.valueOf("\\.")) != -1) {
								errorInfoBuffer.append("[" + val+ "]错误:电费税率格式不正确");
								recordHasError = true;
							}
						}
					} catch (Exception e) {
						errorInfoBuffer.append("[" + val + "]检测异常："+ e.getMessage());
						recordHasError = true;
					}
					break;
				case "belong_room":
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
		String comment = DataType.PK_CONTRACT + commonValidateError.get("commonValidate")==null?"":commonValidateError.get("commonValidate")
				+" 校验成功"+(totalRecordNum-errRecordNum)+"条,校验失败"+errRecordNum+"条。";
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
		ContractUtil.sysRegionService = sysRegionService;
	}

	public static IDatPaymentperiodService getDatPaymentperiodService() {
		return datPaymentperiodService;
	}

	public static void setDatPaymentperiodService(
			IDatPaymentperiodService datPaymentperiodService) {
		ContractUtil.datPaymentperiodService = datPaymentperiodService;
	}

}
