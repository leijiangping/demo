package com.xunge.service.basedata;

import java.util.List;
import java.util.Map;

import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.model.job.SysProvinceVO;

public interface IBaseDataService {

	String[] getDBparms(String perfix);
	
	StringBuffer insertIntoDB(String typeName,List<String> columns,Map<String, String> map,
			SysProvinceVO sysProvinceVO,TaskInfoVO taskInfo,String delimiter, String tablename) throws Exception;
	
	int deleteFromDB(String pkName,String typeName,List<String> columns,Map<String, String> map,
			SysProvinceVO sysProvinceVO,String delimiter, String tablename) throws Exception;


}
