package com.xunge.service.job;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

public interface IFileService {
	Map<String,String>  parseFile(InputStream is,String pkName,List<String> columnList,char delimiter)throws BiffException, IOException ;
	
	String unzipFile(String localDirectoryAndFileName,String destPath,String fileName);
}
