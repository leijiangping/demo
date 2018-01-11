package com.xunge.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xunge.comm.collection.ContractComm;
import com.xunge.comm.enums.ResourceEnum;
import com.xunge.model.basedata.DatBaseresourceVO;
import com.xunge.model.basedata.DatBasesiteVO;
import com.xunge.model.basedata.DatBasestationVO;
import com.xunge.model.basedata.DatSupplierVO;
import com.xunge.model.basedata.ring.MeterPerfVO;
import com.xunge.model.basedata.ring.PowerPerfVO;
import com.xunge.model.selfelec.DatElectricMeterVO;
import com.xunge.model.selfelec.VEleContract;

public class ExcelExportHelper {
	private static final Logger logger = LoggerFactory.getLogger(ExcelExportHelper.class);
	public final static int EXPORT_PAGESIZE = 5000;

	public static Object[] createMeterExcelSheet(){
		Object[] result=new Object[2];
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("智能电表数据");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		int cellIndex = 0;
		HSSFCell cell = row.createCell(cellIndex++);
		cell.setCellValue("智能电表ID");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("资源名称");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("资源标识");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("标识类型");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("所属地市");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("所属区县");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("记录起始时间");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("记录结束时间");
		cell.setCellStyle(style);

		cell = row.createCell(cellIndex++);
		cell.setCellValue("总表读数");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("总电表状态位");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("主设备电表读数");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("主设备电表状态位");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("空调系统电表读数");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("空调系统电表状态位");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("基站室外日平均温度");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex);
		cell.setCellValue("基站室内日平均温度");
		cell.setCellStyle(style);
		
		result[0]=wb;
		result[1]=sheet;
		return result;
	}
	
	public static Object[]  createPowerExcelSheet(){
		Object[] result=new Object[2];
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("开关电源数据");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		int cellIndex = 0;
		HSSFCell cell = row.createCell(cellIndex++);
		cell.setCellValue("开关电源ID");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("资源名称");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("资源标识");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("标识类型");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("所属地市");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("所属区县");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("记录起始时间");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("记录结束时间");
		cell.setCellStyle(style);

		cell = row.createCell(cellIndex++);
		cell.setCellValue("第1小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第1小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第2小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第2小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第3小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第3小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第4小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第4小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第5小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第5小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第6小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第6小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第7小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第7小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第8小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第8小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第9小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第9小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第10小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第10小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第11小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第11小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第12小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第12小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第13小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第13小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第14小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第14小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第15小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第15小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第16小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第16小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第17小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第17小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第18小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第18小时电流(A)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第19小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第19小时电流(A)");
		cell.setCellStyle(style);
		
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第20小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第20小时电流(A)");
		cell.setCellStyle(style);
		
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第21小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第21小时电流(A)");
		cell.setCellStyle(style);
		
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第22小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第22小时电流(A)");
		cell.setCellStyle(style);
		
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第23小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第23小时电流(A)");
		cell.setCellStyle(style);
		
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("第24小时电压(V)");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex);
		cell.setCellValue("第24小时电流(A)");
		cell.setCellStyle(style);
		
		result[0]=wb;
		result[1]=sheet;
		return result;
	}
	
	
	public static Object[] createElectricMeterExcelSheet(){
		Object[] result=new Object[2];
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("电表数据");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		int cellIndex = 0;
		HSSFCell cell = row.createCell(cellIndex++);
		cell.setCellValue("电表标识");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("电表编码");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("电表类型");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("省ID");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("省简称");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("地市ID");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("所属地市");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("县区ID");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("所属县区");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("电表状态");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("户号");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("电表倍率");
		cell.setCellStyle(style);

		cell = row.createCell(cellIndex++);
		cell.setCellValue("多机房公用");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("安装日期");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("电表初始值");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("平初始值");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("尖初始值");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("峰初始值");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("谷初始值");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("电表上限");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("平上限值");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("尖上限值");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("峰上限值");
		cell.setCellStyle(style);

		cell = row.createCell(cellIndex++);
		cell.setCellValue("谷上限值");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex);
		cell.setCellValue("电表备注");
		cell.setCellStyle(style);
		
		result[0]=wb;
		result[1]=sheet;
		return result;
	}
	
	public static void setMeterExcelProperty(HSSFSheet sheet,List<MeterPerfVO> meterList,int startIndex) {

		for (int i = startIndex; i < (meterList.size()+startIndex); i++) {
			HSSFRow row = sheet.createRow((int) i + 1);
			MeterPerfVO meterPerfVO =meterList.get(i);
			// 第四步，创建单元格，并设置值
			
			String meterId=meterPerfVO.getMeterId();
			String resourceName=meterPerfVO.getResourceName();
			String resourceCode=meterPerfVO.getResourceCode();
			int codeType=meterPerfVO.getCodeType();
			String pregName=meterPerfVO.getPregName();
			String regName=meterPerfVO.getRegName();
			Date startTime=meterPerfVO.getStartTime();
			Date stopTime=meterPerfVO.getStopTime();
			//float totalDegree=meterPerfVO.getTotalDegree();
			int totalState=meterPerfVO.getTotalState();
			//float equipmentDegree=meterPerfVO.getEquipmentDegree();
			int equipmentState=meterPerfVO.getEquipmentState();
			//float acDegree=meterPerfVO.getAcDegree();
			int acState=meterPerfVO.getAcState();
			
			if(StringUtils.isNoneBlank(meterId)){
				row.createCell(0).setCellValue(meterId);
			}
			else{
				row.createCell(0).setCellValue("");
			}
			if(StringUtils.isNoneBlank(resourceName)){
				row.createCell(1).setCellValue(resourceName);
			}
			else{
				row.createCell(1).setCellValue("");
			}
			if(StringUtils.isNoneBlank(resourceCode)){
				row.createCell(2).setCellValue(resourceCode);
			}
			else{
				row.createCell(2).setCellValue("");
			}
			row.createCell(3).setCellValue(dealCodeType(codeType));
			
			if(StringUtils.isNoneBlank(pregName)){
				row.createCell(4).setCellValue(pregName);
			}
			else{
				row.createCell(4).setCellValue("");
			}
			if(StringUtils.isNoneBlank(regName)){
				row.createCell(5).setCellValue(regName);
			}
			else{
				row.createCell(5).setCellValue("");
			}
			row.createCell(6).setCellValue(dealDate(startTime));
			row.createCell(7).setCellValue(dealDate(stopTime));
			if(!Float.isNaN(meterPerfVO.getTotalDegree())){
				row.createCell(8).setCellValue(meterPerfVO.getTotalDegree());
			}
			else{
				row.createCell(8).setCellValue("");
			}
			row.createCell(9).setCellValue(dealState(totalState));
			if(!Float.isNaN(meterPerfVO.getEquipmentDegree())){
				row.createCell(10).setCellValue(meterPerfVO.getEquipmentDegree());
			}
			else{
				row.createCell(10).setCellValue("");
			} 
			row.createCell(11).setCellValue(dealState(equipmentState));
			if(!Float.isNaN(meterPerfVO.getAcDegree())){
				row.createCell(12).setCellValue(meterPerfVO.getAcDegree());
			}
			else{
				row.createCell(12).setCellValue("");
			}
			row.createCell(13).setCellValue(dealState(acState));
			if(!Float.isNaN(meterPerfVO.getExTempreture())){
				row.createCell(14).setCellValue(meterPerfVO.getExTempreture());
			}
			else{
				row.createCell(14).setCellValue("");
			}
			if(!Float.isNaN(meterPerfVO.getInTempreture())){
				row.createCell(15).setCellValue(meterPerfVO.getInTempreture());
			}
			else{
				row.createCell(15).setCellValue("");
			}
		}

	}
	
	public static void setPowerExcelProperty(HSSFSheet sheet,List<PowerPerfVO> powerList,int startIndex) {

		for (int i = startIndex; i < (powerList.size()+startIndex); i++) {
			HSSFRow row = sheet.createRow((int) i + 1);
			PowerPerfVO powerPerfVO =powerList.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell(0).setCellValue(powerPerfVO.getPowerId());
			row.createCell(1).setCellValue(powerPerfVO.getResourceName());
			row.createCell(2).setCellValue(powerPerfVO.getResourceCode());
			row.createCell(3).setCellValue(dealCodeType(powerPerfVO.getCodeType()));
			row.createCell(4).setCellValue(powerPerfVO.getPregName());
			row.createCell(5).setCellValue(powerPerfVO.getRegName());
			row.createCell(6).setCellValue(dealDate(powerPerfVO.getStartTime()));
			row.createCell(7).setCellValue(dealDate(powerPerfVO.getStopTime()));
			row.createCell(8).setCellValue(powerPerfVO.getV1());
			row.createCell(9).setCellValue(powerPerfVO.getA1());
			row.createCell(10).setCellValue(powerPerfVO.getV2());
			row.createCell(11).setCellValue(powerPerfVO.getA2());
			row.createCell(12).setCellValue(powerPerfVO.getV3());
			row.createCell(13).setCellValue(powerPerfVO.getA3());
			row.createCell(14).setCellValue(powerPerfVO.getV4());
			row.createCell(15).setCellValue(powerPerfVO.getA4());
			row.createCell(16).setCellValue(powerPerfVO.getV5());
			row.createCell(17).setCellValue(powerPerfVO.getA5());
			row.createCell(18).setCellValue(powerPerfVO.getV6());
			row.createCell(19).setCellValue(powerPerfVO.getA6());
			row.createCell(20).setCellValue(powerPerfVO.getV7());
			row.createCell(21).setCellValue(powerPerfVO.getA7());
			row.createCell(22).setCellValue(powerPerfVO.getV8());
			row.createCell(23).setCellValue(powerPerfVO.getA8());
			row.createCell(24).setCellValue(powerPerfVO.getV9());
			row.createCell(25).setCellValue(powerPerfVO.getA9());
			row.createCell(26).setCellValue(powerPerfVO.getV10());
			row.createCell(27).setCellValue(powerPerfVO.getA10());
			row.createCell(28).setCellValue(powerPerfVO.getV11());
			row.createCell(29).setCellValue(powerPerfVO.getA11());
			row.createCell(30).setCellValue(powerPerfVO.getV12());
			row.createCell(31).setCellValue(powerPerfVO.getA12());
			row.createCell(32).setCellValue(powerPerfVO.getV13());
			row.createCell(33).setCellValue(powerPerfVO.getA13());
			row.createCell(34).setCellValue(powerPerfVO.getV14());
			row.createCell(35).setCellValue(powerPerfVO.getA14());
			row.createCell(36).setCellValue(powerPerfVO.getV15());
			row.createCell(37).setCellValue(powerPerfVO.getA15());
			row.createCell(38).setCellValue(powerPerfVO.getV16());
			row.createCell(39).setCellValue(powerPerfVO.getA16());
			row.createCell(40).setCellValue(powerPerfVO.getV17());
			row.createCell(41).setCellValue(powerPerfVO.getA17());
			row.createCell(42).setCellValue(powerPerfVO.getV18());
			row.createCell(43).setCellValue(powerPerfVO.getA18());
			row.createCell(44).setCellValue(powerPerfVO.getV19());
			row.createCell(45).setCellValue(powerPerfVO.getA19());
			row.createCell(46).setCellValue(powerPerfVO.getV20());
			row.createCell(47).setCellValue(powerPerfVO.getA20());
			row.createCell(48).setCellValue(powerPerfVO.getV21());
			row.createCell(49).setCellValue(powerPerfVO.getA21());
			row.createCell(50).setCellValue(powerPerfVO.getV22());
			row.createCell(51).setCellValue(powerPerfVO.getA22());
			row.createCell(52).setCellValue(powerPerfVO.getV23());
			row.createCell(53).setCellValue(powerPerfVO.getA23());
			row.createCell(54).setCellValue(powerPerfVO.getV24());
			row.createCell(55).setCellValue(powerPerfVO.getA24());
		}
	}
	
	public static void setElectricMeterExcelProperty(HSSFSheet sheet,List<DatElectricMeterVO> meterList) {

		for (int i = 0, length = meterList.size(); i<length; i++) {
			HSSFRow row = sheet.createRow(i + 1);
			if (null != meterList.get(i)) {
				DatElectricMeterVO datElectricMeterVO =meterList.get(i);
				// 第四步，创建单元格，并设置值
				row.createCell(0).setCellValue(StrUtil.isEmpty(datElectricMeterVO.getMeterId()) ? "" : datElectricMeterVO.getMeterId());
				row.createCell(1).setCellValue(StrUtil.isEmpty(datElectricMeterVO.getMeterCode()) ? "" : datElectricMeterVO.getMeterCode());
				row.createCell(2).setCellValue(datElectricMeterVO.getMeterType() == null ? "0" : dealMeterType(datElectricMeterVO.getMeterType()));
				row.createCell(3).setCellValue(StrUtil.isEmpty(datElectricMeterVO.getPrvId())?"":datElectricMeterVO.getPrvId());
				row.createCell(4).setCellValue(StrUtil.isEmpty(datElectricMeterVO.getPrvSname())?"":datElectricMeterVO.getPrvSname());
				row.createCell(5).setCellValue(StrUtil.isEmpty(datElectricMeterVO.getPregId())?"":datElectricMeterVO.getPregId());
				row.createCell(6).setCellValue(StrUtil.isEmpty(datElectricMeterVO.getPregName())?"":datElectricMeterVO.getPregName());
				row.createCell(7).setCellValue(StrUtil.isEmpty(datElectricMeterVO.getRegId())?"":datElectricMeterVO.getRegId());
				row.createCell(8).setCellValue(StrUtil.isEmpty(datElectricMeterVO.getRegName())?"":datElectricMeterVO.getRegName());
				row.createCell(9).setCellValue(datElectricMeterVO.getMeterState() == null ? "" : dealMeterState(datElectricMeterVO.getMeterState()));
				row.createCell(10).setCellValue(StrUtil.isEmpty(datElectricMeterVO.getAccountNumber()) ? "": datElectricMeterVO.getAccountNumber());
				row.createCell(11).setCellValue(datElectricMeterVO.getElectricmeterMultiply() == null? "0.0" : datElectricMeterVO.getElectricmeterMultiply()+"");
				row.createCell(12).setCellValue(datElectricMeterVO.getIsShare() == null ? "0" : dealIsShare(datElectricMeterVO.getIsShare()));
				row.createCell(13).setCellValue(datElectricMeterVO.getInstallDate() == null ? "" : dealDate(datElectricMeterVO.getInstallDate()));
				row.createCell(14).setCellValue(datElectricMeterVO.getInitialValue() == null ? "0.0" : datElectricMeterVO.getInitialValue()+"");
				row.createCell(15).setCellValue(datElectricMeterVO.getFlatValue() == null ? "0.0" : datElectricMeterVO.getFlatValue()+"");
				row.createCell(16).setCellValue(datElectricMeterVO.getTopValue() == null ? "0.0" : datElectricMeterVO.getTopValue()+"");
				row.createCell(17).setCellValue(datElectricMeterVO.getPeakValue() == null ? "0.0" : datElectricMeterVO.getPeakValue()+"");
				row.createCell(18).setCellValue(datElectricMeterVO.getValleyValue() == null ? "0.0" : datElectricMeterVO.getValleyValue()+"");
				row.createCell(19).setCellValue(datElectricMeterVO.getUpperValue() == null ? "0.0" : datElectricMeterVO.getUpperValue()+"");
				row.createCell(20).setCellValue(datElectricMeterVO.getFlatUpperValue() == null ? "0.0" : datElectricMeterVO.getFlatUpperValue()+"");
				row.createCell(21).setCellValue(datElectricMeterVO.getTopUpperValue() == null ? "0.0" : datElectricMeterVO.getTopUpperValue()+"");
				row.createCell(22).setCellValue(datElectricMeterVO.getPeakUpperValue() == null ? "0.0" : datElectricMeterVO.getPeakUpperValue()+"");
				row.createCell(23).setCellValue(datElectricMeterVO.getValleyUpperValue() == null ? "0.0" : datElectricMeterVO.getValleyUpperValue()+"");
				row.createCell(24).setCellValue(StrUtil.isEmpty(datElectricMeterVO.getMeterNote())?"":datElectricMeterVO.getMeterNote());
			}
		}

	}

	public static Object[]  createDeviceExcelSheet(String sheetDesc){
		Object[] result=new Object[2];
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetDesc);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		int cellIndex = 0;
		HSSFCell cell = row.createCell(cellIndex++);
		cell.setCellValue("设备类型");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("设备CID");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("设备编码");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("设备名称");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("入网时间");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("所属机房/资源点/热点名称");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("设备制造商");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("设备型号");
		cell.setCellStyle(style);

		cell = row.createCell(cellIndex++);
		cell.setCellValue("设备功率");
		cell.setCellStyle(style);
		
		result[0]=wb;
		result[1]=sheet;
		return result;
	}
	
	public static void setDeviceExcelProperty(HSSFSheet sheet,List<DatBasestationVO> list,int startIndex) {
		for (int i = startIndex; i < (list.size()+startIndex); i++) {
			HSSFRow row = sheet.createRow((int) i + 1);
			DatBasestationVO vo = list.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell(0).setCellValue(vo.getBasestationCategory());
			row.createCell(1).setCellValue(vo.getBaseresourceCuid());
			row.createCell(2).setCellValue(vo.getBasestationCode());
			row.createCell(3).setCellValue(vo.getBasestationName());
			row.createCell(4).setCellValue(dealDate(vo.getBasestationOpendate()));
			row.createCell(5).setCellValue(vo.getBaseresourceId());
			row.createCell(6).setCellValue(vo.getBasestationVendor());
			row.createCell(7).setCellValue(vo.getBasestationModel());
			row.createCell(8).setCellValue(vo.getBasestationPower());
		}
	}
	
	public static Object[]  createSupplierExcelSheet(String sheetDesc,int queryType){
		Object[] result=new Object[2];
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetDesc);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		int cellIndex = 0;
		HSSFCell cell = row.createCell(cellIndex++);
		cell.setCellValue("供应商编码");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("供应商名称");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("所属地市");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("所属区县");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("地点");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("联系人");
		cell.setCellStyle(style);

		cell = row.createCell(cellIndex++);
		cell.setCellValue("联系电话");
		cell.setCellStyle(style);
		
		if(queryType == 1){
			cell = row.createCell(cellIndex++);
			cell.setCellValue("数据来源");
			cell.setCellStyle(style);
		}
		
		result[0]=wb;
		result[1]=sheet;
		return result;
	}
	
	public static void setSupplierExcelProperty(HSSFSheet sheet,List<DatSupplierVO> list,int startIndex, int queryType) {
		for (int i = startIndex; i < (list.size()+startIndex); i++) {
			HSSFRow row = sheet.createRow((int) i + 1);
			DatSupplierVO vo = list.get(i);
			// 第四步，创建单元格，并设置值
			row.createCell(0).setCellValue(vo.getSupplierCode());
			row.createCell(1).setCellValue(vo.getSupplierName());
			row.createCell(2).setCellValue(vo.getPregName());
			row.createCell(3).setCellValue(vo.getRegName());
			row.createCell(4).setCellValue(vo.getSupplierAddress());
			row.createCell(5).setCellValue(vo.getSupplierContact());
			row.createCell(6).setCellValue(vo.getSupplierTelephone());
			if(queryType == 1){
				row.createCell(7).setCellValue(ResourceEnum.datafromEnum.getName(vo.getDataFrom()==null?0:vo.getDataFrom()));
			}
		}
	}

	public static Object[]  createElecontractExcelSheet(String sheetDesc, Integer opType){
		Object[] result=new Object[2];
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetDesc);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		int cellIndex = 0;
		HSSFCell cell = row.createCell(cellIndex++);
		
		cell.setCellValue("合同编码");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("合同名称");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("所属地市");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("所属区县");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("是否向下共享");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("合同状态");
		cell.setCellStyle(style);
		
		if(opType != 1){
			cell = row.createCell(cellIndex++);
			cell.setCellValue("审核状态");
			cell.setCellStyle(style);
		}
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("合同期始");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("合同终止");
		cell.setCellStyle(style);
		if(opType == 1){
			cell = row.createCell(cellIndex++);
			cell.setCellValue("合同年限");
			cell.setCellStyle(style);
		}
		result[0] = wb;
		result[1] = sheet;
		return result;
	}
	
	public static void setElecontractExcelProperty(HSSFSheet sheet,List<VEleContract> list,int startIndex, Integer opType) {
		for (int i = startIndex; i < (list.size()+startIndex); i++) {
			HSSFRow row = sheet.createRow((int) i + 1);
			VEleContract vo = list.get(i);
			// 第四步，创建单元格，并设置值
			if(opType != 1){
				
				row.createCell(0).setCellValue(vo.getContractCode());
				row.createCell(1).setCellValue(vo.getContractName());
				row.createCell(2).setCellValue(vo.getPregName());
				row.createCell(3).setCellValue(vo.getRegName());
				row.createCell(4).setCellValue((ContractComm.IS_DOWNSHAR_YES == vo.getIsDownshare())?"是":"否");
				row.createCell(5).setCellValue(ResourceEnum.contractStateEnum.getName(vo.getContractState()));
				row.createCell(6).setCellValue(ResourceEnum.auditStateEnum.getName(vo.getAuditingState()));
				row.createCell(7).setCellValue(dealDate(vo.getContractStartdate()));
				row.createCell(8).setCellValue(dealDate(vo.getContractEnddate()));
			}else{
				row.createCell(0).setCellValue(vo.getContractCode());
				row.createCell(1).setCellValue(vo.getContractName());
				row.createCell(2).setCellValue(vo.getPregName());
				row.createCell(3).setCellValue(vo.getRegName());
				row.createCell(4).setCellValue(ResourceEnum.contractStateEnum.getName(vo.getContractState()));
				row.createCell(5).setCellValue(dealDate(vo.getContractStartdate()));
				row.createCell(6).setCellValue(dealDate(vo.getContractEnddate()));
				row.createCell(7).setCellValue(vo.getContractYearquantity().setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
			}
		}
	}
	
	public static Object[]  createResourceExcelSheet(String sheetDesc, Integer opType, String res){
		Object[] result=new Object[2];
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetDesc);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		int cellIndex = 0;
		HSSFCell cell = row.createCell(cellIndex++);
		cell.setCellValue("审核状态");
		cell.setCellStyle(style);
			
		cell = row.createCell(cellIndex++);
		cell.setCellValue(res+"编码");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue(res+"名称");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("所属地市");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("所属区县");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue(res+"归属");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue(res+"状态");
		cell.setCellStyle(style);
		
		cell = row.createCell(cellIndex++);
		cell.setCellValue("资源类型");
		cell.setCellStyle(style);
		if(opType == 1){
			cell = row.createCell(cellIndex++);
			cell.setCellValue("数据来源");
			cell.setCellStyle(style);
		}
		result[0] = wb;
		result[1] = sheet;
		return result;
	}
	
	public static void setSiteExcelProperty(HSSFSheet sheet,List<DatBasesiteVO> list,int startIndex, Integer opType) {
		for (int i = startIndex; i < (list.size()+startIndex); i++) {
			HSSFRow row = sheet.createRow((int) i + 1);
			DatBasesiteVO vo = list.get(i-startIndex);
			// 第四步，创建单元格，并设置值
			if(vo.getAuditingState() != null){
				row.createCell(0).setCellValue(ResourceEnum.auditStateEnum.getName(vo.getAuditingState()));
			}
			row.createCell(1).setCellValue(vo.getBasesiteCode());
			row.createCell(2).setCellValue(vo.getBasesiteName());
			row.createCell(3).setCellValue(vo.getPregName());
			row.createCell(4).setCellValue(vo.getRegName());
			if(vo.getBasesiteBelong() != null){
				row.createCell(5).setCellValue(ResourceEnum.resBelongEnum.getName(vo.getBasesiteBelong()));
			}
			if(vo.getBasesiteState() != null){
				row.createCell(6).setCellValue(ResourceEnum.resStateEnum.getName(vo.getBasesiteState()));
			}
			row.createCell(7).setCellValue("站点");
			if(opType == 1){
				if(vo.getDataFrom() != null){
					row.createCell(8).setCellValue(ResourceEnum.datafromEnum.getName(vo.getDataFrom()));
				}
			}
		}
	}
	
	public static void setResourceExcelProperty(HSSFSheet sheet,List<DatBaseresourceVO> list,int startIndex, Integer opType, String res) {
		for (int i = startIndex; i < (list.size()+startIndex); i++) {
			HSSFRow row = sheet.createRow((int) i + 1);
			DatBaseresourceVO vo = list.get(i-startIndex);
			// 第四步，创建单元格，并设置值
			if(vo.getAuditingState() != null){
				row.createCell(0).setCellValue(ResourceEnum.auditStateEnum.getName(vo.getAuditingState()));
			}
			row.createCell(1).setCellValue(vo.getBaseresourceCode());
			row.createCell(2).setCellValue(vo.getBaseresourceName());
			row.createCell(3).setCellValue(vo.getPregName());
			row.createCell(4).setCellValue(vo.getRegName());
			row.createCell(5).setCellValue(ResourceEnum.resBelongEnum.getName(vo.getRoomOwner()==null?0:vo.getRoomOwner()));
			row.createCell(6).setCellValue(ResourceEnum.resStateEnum.getName(vo.getBaseresourceState()==null?0:vo.getBaseresourceState()));
			row.createCell(7).setCellValue(res);
			if(opType == 1){
				row.createCell(8).setCellValue(ResourceEnum.datafromEnum.getName(vo.getDataFrom()==null?0:vo.getDataFrom()));
			}
		}
	}
	
	public static boolean exportFile(HSSFWorkbook wb,String path,String fileName){
		boolean result=false;
		// 第六步，将文件存到指定位置
		FileOutputStream os=null;
		try {
			File dir = new File(path);
			if(!dir.exists()){
				dir.mkdirs();
			}
			File file = new File(path + File.separator + fileName);
			os = new FileOutputStream(file);
			wb.write(os);
			os.flush();
			result=true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result=false;
		}
		finally {
			if(os!=null){
				try {
					os.close();
					wb.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
					result=false;
				}
			}
		}
		return result;
	}
	
	private static String dealMeterType(int meterType){
		
		String result="普通表";
		if(1==meterType){
			result="普通表";
		}
		else if(2==meterType){
			result="平峰谷表";
		}
		return result;
	}
	
	private static String dealMeterState(int meterState){
		
		String result="正常";
		if(0==meterState){
			result="正常";
		}
		else if(9==meterState){
			result="停用";
		}
		return result;
	}
	
	private static String dealIsShare(int isShare){
		
		String result="否";
		if(0==isShare){
			result="否";
		}
		else if(1==isShare){
			result="是";
		}
		return result;
	}
	
	private static String dealCodeType(int codeType){
		
		String result="CID";
		if(1==codeType){
			result="CID";
		}
		else if(2==codeType){
			result="编码";
		}
		return result;
	}
	private static String dealDate(Date date){
		if(date!=null){
			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			return dateFm.format(date);
		}
		else{
			return "";
		}
	}
	
	private static String dealState(int state){
		String totals=state+"";
		if(0==state){
			totals="正常";
    	}
    	else if(1==state){
    		totals="换表";
    	}
    	else if(2==state){
    		totals="清零";
    	}
		return totals;
	}
}
