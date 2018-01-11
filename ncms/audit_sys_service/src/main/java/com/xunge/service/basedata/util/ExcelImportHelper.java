package com.xunge.service.basedata.util;

import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xunge.comm.system.PromptMessageComm;

public class ExcelImportHelper {

	/** 
	 * Excel 2003 
	 */
	private final static String XLS = "xls";
	/** 
	 * Excel 2007 
	 */
	private final static String XLSX = "xlsx";

	private static Logger logger = LoggerFactory.getLogger(ExcelImportHelper.class);

	public static Workbook getWorkbook(InputStream in, String fileName) throws Exception {
		String extensionName = FilenameUtils.getExtension(fileName);
		Workbook workbook = null;
		if (extensionName.toLowerCase().equals(XLS)) {
			workbook = new HSSFWorkbook(in);
		} else if (extensionName.toLowerCase().equals(XLSX)) {
			workbook = new XSSFWorkbook(in);
		}
		return workbook;
	}

	public static String buildMsg(List<Integer> errRowList) {
		Collections.sort(errRowList, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if (((Integer) o1).intValue() > ((Integer) o2).intValue()) {
					return 1;
				} else {
					return -1;
				}
			}

		});
		if (errRowList == null || errRowList.size() == 0) {
			return PromptMessageComm.IMPORT_SUCCESS;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(PromptMessageComm.WORDS_FIRST);
		int lastNumber = errRowList.get(errRowList.size() - 1);
		for (Integer in : errRowList) {
			if (in != lastNumber) {
				buffer.append(in + PromptMessageComm.SYMBOL_COMMA);
			} else {
				buffer.append(in);
			}
		}
		buffer.append(PromptMessageComm.WORDS_THE_LINE_DATA_HAS_ERROR);
		logger.error(buffer.toString());
		return PromptMessageComm.IMPORTED_SUCCESS;
	}

	// 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了  
	// 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html
	public static Object getObjectCellValue(FormulaEvaluator evaluator, Cell cell) {
		CellValue cellValue = evaluator.evaluate(cell);
		if (cellValue == null) {
			return null;
		}
		Object value = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC://数字  
			if (DateUtil.isCellDateFormatted(cell)) {
				value = cell.getDateCellValue();
			} else {
				value = cell.getNumericCellValue();
			}
			break;
		case Cell.CELL_TYPE_STRING://字符串   
			value = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_BLANK:
			break;
		case Cell.CELL_TYPE_FORMULA:
			break;
		case Cell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return value;
	}

	public static String getCellValue(FormulaEvaluator evaluator, Cell cell) {
		CellValue cellValue = evaluator.evaluate(cell);
		if (cellValue == null) {
			return null;
		}
		String value = null;
		//简单的查检列类型   
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING://字符串   
			value = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC://数字   
			long dd = (long) cell.getNumericCellValue();
			value = dd + "";
			break;
		case Cell.CELL_TYPE_BLANK:
			break;
		case Cell.CELL_TYPE_FORMULA:
			value = String.valueOf(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_BOOLEAN://boolean型值   
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			value = String.valueOf(cell.getErrorCellValue());
			break;
		default:
			break;
		}
		return value;
	}
}
