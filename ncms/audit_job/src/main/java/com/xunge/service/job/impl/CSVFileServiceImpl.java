package com.xunge.service.job.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Service;

import com.csvreader.CsvReader;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.service.job.IFileService;
import com.xunge.service.job.ITaskHistoryInfoService;
import com.xunge.service.job.util.ContractUtil;
import com.xunge.service.job.util.DatBaseResourceUtil;
import com.xunge.service.job.util.DatBaseSiteUtil;
import com.xunge.service.job.util.DatBaseStationUtil;
import com.xunge.service.job.util.MeterPerfUtil;
import com.xunge.service.job.util.PowerPerfUtil;
import com.xunge.service.job.util.SysRegionUtil;
import com.xunge.util.DataType;
import com.xunge.util.StrUtil;

@Service
public class CSVFileServiceImpl implements IFileService {

	// private List<String> columnList;

	// private char delimiter=',';

	ITaskHistoryInfoService itaskHistoryInfoService;

	@Override
	public Map<String, String> parseFile(InputStream is, String pkName,
			List<String> columnList, char delimiter) throws BiffException,
			IOException {
		// TODO Auto-generated method stub
		if (is == null) {
			return null;
		}
		Map<String, String> contents = new HashMap<String, String>();
		// DataInputStream in = new DataInputStream(is);

		CsvReader csvReader = new CsvReader(new InputStreamReader(
				new BOMInputStream(is), "UTF-8"), delimiter);
		csvReader.setSafetySwitch(false);
		int rowIndex = 0;
		int columnIndex = -1;
		StringBuffer errorInfo = new StringBuffer();
		if (csvReader != null) {
			while (csvReader.readRecord()) {

				// int columnCount=csvReader.getColumnCount();
				String[] values = csvReader.getValues();
				if (rowIndex == 0) {// 第一行-标题
					for (int i = 0; i < values.length; i++) {
						if (values[i] == "") {
							continue;
						}
						if (values[i] != null) {
							if (values[i].toUpperCase().equals(
									pkName.toUpperCase()))
								columnIndex = i;
						}
						columnList.add(values[i].replaceAll(" ", ""));
					}
				} else {
					if (columnIndex >= 0) {
						// 取csv文件内容最新的记录
						if (contents.containsKey(values[columnIndex])) {
							contents.remove(values[columnIndex]);
						}
						if(StrUtil.isBlank(values[columnIndex])){
							errorInfo.append("[" + csvReader.getRawRecord()+ "]错误:文件主键字段不能为空\r\n");
						}
						contents.put(values[columnIndex],
								csvReader.getRawRecord());
					}
				}
				rowIndex++;
			}
		}
		is.close();
		return contents;
	}

	@Override
	public String unzipFile(String localDirectoryAndFileName, String destPath,
			String fileName) {

		String result = "";

		try {
			ZipFile zFile = new ZipFile(localDirectoryAndFileName); // 首先创建ZipFile指向磁盘上的.zip文件
			// zFile.setFileNameCharset("UTF-8"); // 设置文件名编码，在GBK系统中需要设置
			if (!zFile.isValidZipFile()) { // 验证.zip文件是否合法，包括文件是否存在、是否为zip文件、是否被损坏等
				throw new ZipException("压缩文件不合法,可能被损坏.");
			}
			File destDir = new File(destPath); // 解压目录
			if (destDir.isDirectory() && !destDir.exists()) {
				destDir.mkdir();
			}
			// if (zFile.isEncrypted()) {
			// zFile.setPassword(passwd.toCharArray()); // 设置密码
			// }
			zFile.extractAll(destPath); // 将文件抽出到解压目录(解压)

			List<FileHeader> fileHeaders = zFile.getFileHeaders();
			if (fileHeaders.size() > 0) {
				result = fileHeaders.get(0).getFileName();
			}
			// result=fileName.substring(0,fileName.indexOf(".zip"))+".csv";
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Map<String, String> analysis(File file,
			Map<String, String> contents, List<String> columns, String perfix,
			String errorInfo, char divideFlag,SysProvinceVO sysProvinceVO) throws Exception {

		// 经检测后，错误文件内容
		Map<String, String> errorContents = new HashMap<String, String>();
		StringBuffer strb = new StringBuffer();

		try {
			// 基础检测
			Map<String, String> commonValidateError = analysisCommon(file,
					contents, columns, perfix, errorInfo, divideFlag);
			/**
			 * 根据文件类型，分文件解析
			 */
			if (DataType.PK_CONTRACT.equalsIgnoreCase(perfix)) {
				// 列校验
				ContractUtil.analysisContractCSVFile(errorContents, contents,
						columns, errorInfo, divideFlag, commonValidateError);
			} else if (DataType.RESOURCE_AP.equalsIgnoreCase(perfix)) {
				DatBaseStationUtil.analysisAPCSVFile(errorContents, contents,
						columns, errorInfo, divideFlag, commonValidateError);
			} else if (DataType.RESOURCE_CITY.equalsIgnoreCase(perfix)) {
				SysRegionUtil.analysisCityCSVFile(errorContents, contents,
						columns, errorInfo, divideFlag, commonValidateError);
			} else if (DataType.RESOURCE_DISTRIBUTION.equalsIgnoreCase(perfix)) {
				DatBaseStationUtil.analysisDistributionCSVFile(errorContents,
						contents, columns, errorInfo, divideFlag,
						commonValidateError);
			} else if (DataType.RESOURCE_RESOURCESPOT.equalsIgnoreCase(perfix)) {
				DatBaseResourceUtil.analysisResourceSpotCSVFile(errorContents,
						contents, columns, errorInfo, divideFlag,
						commonValidateError);
			} else if (DataType.RESOURCE_HOTSPOT.equalsIgnoreCase(perfix)) {
				DatBaseResourceUtil.analysisHotspotCSVFile(errorContents,
						contents, columns, errorInfo, divideFlag,
						commonValidateError);
			} else if (DataType.RESOURCE_HOTSPOTSWITCH.equalsIgnoreCase(perfix)) {
				DatBaseStationUtil.analysisHotspotSwitchCSVFile(errorContents,
						contents, columns, errorInfo, divideFlag,
						commonValidateError);
			} else if (DataType.RESOURCE_OLT.equalsIgnoreCase(perfix)) {
				DatBaseStationUtil.analysisOltCSVFile(errorContents, contents,
						columns, errorInfo, divideFlag, commonValidateError);
			} else if (DataType.RESOURCE_ONU.equalsIgnoreCase(perfix)) {
				DatBaseStationUtil.analysisOnuCSVFile(errorContents, contents,
						columns, errorInfo, divideFlag, commonValidateError);
			} else if (DataType.RESOURCE_OTN.equalsIgnoreCase(perfix)) {
				DatBaseStationUtil.analysisOtnCSVFile(errorContents, contents,
						columns, errorInfo, divideFlag, commonValidateError);
			} else if (DataType.RESOURCE_POSITION.equalsIgnoreCase(perfix)) {
				DatBaseResourceUtil.analysisPositionCSVFile(errorContents,
						contents, columns, errorInfo, divideFlag,
						commonValidateError);
			} else if (DataType.RESOURCE_PTN.equalsIgnoreCase(perfix)) {//
				DatBaseStationUtil.analysisPtnCSVFile(errorContents, contents,
						columns, errorInfo, divideFlag, commonValidateError);
			} else if (DataType.RESOURCE_REGION.equalsIgnoreCase(perfix)) {
				SysRegionUtil.analysisRegionCSVFile(errorContents, contents,
						columns, errorInfo, divideFlag, commonValidateError);
			} else if (DataType.RESOURCE_REPEATER.equalsIgnoreCase(perfix)) {
				DatBaseStationUtil.analysisRepeaterCSVFile(errorContents,
						contents, columns, errorInfo, divideFlag,
						commonValidateError);
			} else if (DataType.RESOURCE_ROOM.equalsIgnoreCase(perfix)) {
				DatBaseResourceUtil.analysisRoomCSVFile(errorContents,
						contents, columns, errorInfo, divideFlag,
						commonValidateError);
			} else if (DataType.RESOURCE_RRU.equalsIgnoreCase(perfix)) {
				DatBaseStationUtil.analysisRruCSVFile(errorContents, contents,
						columns, errorInfo, divideFlag, commonValidateError);
			} else if (DataType.RESOURCE_SITE.equalsIgnoreCase(perfix)) {
				DatBaseSiteUtil.analysisSiteCSVFile(errorContents, contents,
						columns, errorInfo, divideFlag, commonValidateError);
			} else if (DataType.RESOURCE_WIRELESS2G.equalsIgnoreCase(perfix)) {
				DatBaseStationUtil.analysisWireless2GCSVFile(errorContents,
						contents, columns, errorInfo, divideFlag,
						commonValidateError);
			} else if (DataType.RESOURCE_WIRELESS3G.equalsIgnoreCase(perfix)) {
				DatBaseStationUtil.analysisWireless3GCSVFile(errorContents,
						contents, columns, errorInfo, divideFlag,
						commonValidateError);
			} else if (DataType.RESOURCE_WIRELESS4G.equalsIgnoreCase(perfix)) {
				DatBaseStationUtil.analysisWireless4GCSVFile(errorContents,
						contents, columns, errorInfo, divideFlag,
						commonValidateError);
			} else if (DataType.RING_BTSROOM_METER.equalsIgnoreCase(perfix)
					|| DataType.RING_MACHROOM_METER.equalsIgnoreCase(perfix)) {
				MeterPerfUtil.analysisMeterCSVFile(errorContents, contents,
						columns, errorInfo, divideFlag, commonValidateError,sysProvinceVO);
			} else if (DataType.RING_BTSROOM_POWER.equalsIgnoreCase(perfix)
					|| DataType.RING_MACHROOM_POWER.equalsIgnoreCase(perfix)) {
				PowerPerfUtil.analysisPowerCSVFile(errorContents, contents,
						columns, errorInfo, divideFlag, commonValidateError,sysProvinceVO);
			} else {
				// 文件类型不识别
				// 将所有内容放入错误数据，并清空内容map
				errorContents.putAll(contents);
				contents.clear();
				throw new Exception("文件类型不识别");
			}
		} catch (Exception e) {
			// 出错日志记录
			strb.append(e.getMessage());
		} finally {
			errorInfo += strb.toString();
			if (StrUtil.isEmpty(errorInfo)) {
				errorInfo += "文件验证完成!";
			}
			errorContents.put("errorInfo", errorInfo);
		}
		return errorContents;
	}

	private Map<String, String> analysisCommon(File file,
			Map<String, String> contents, List<String> columns, String perfix,
			String errorInfo, char divideFlag) throws Exception {
		Map<String, String> errorContents = new HashMap<String, String>();
		// 检测空文件

		if(contents.size()<1){
			throw new Exception("文件为空或相比前一天无变化："+file.getName());
		}

		// 检测文件编码
		InputStream in= new java.io.FileInputStream(file);
		byte[] b = new byte[3];
        in.read(b);
        in.close();
        if (b[0] == -17 && b[1] == -69 && b[2] == -65){}
        else{
			throw new Exception("文件编码不合法：非utf-8");
        }
		return errorContents;
	}
}
