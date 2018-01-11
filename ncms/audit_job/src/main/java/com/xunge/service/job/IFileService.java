package com.xunge.service.job;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.xunge.model.job.SysProvinceVO;

import jxl.read.biff.BiffException;

public interface IFileService {
	Map<String,String>  parseFile(InputStream is,String pkName,List<String> columnList,char delimiter)throws BiffException, IOException ;
	
	String unzipFile(String localDirectoryAndFileName,String destPath,String fileName);
	
	/**
	 * 文件合法性检测
	 * @param contents
	 */
	public Map<String, String> analysis(File files, Map<String, String> contents,
			List<String> columns, String perfix, String errorInfo,char divideFlag,SysProvinceVO sysProvinceVO) throws Exception;
}
