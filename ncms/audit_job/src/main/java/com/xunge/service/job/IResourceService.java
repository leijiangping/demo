package com.xunge.service.job;

import java.util.List;
import java.util.Map;

import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.service.basedata.IBaseDataService;

public interface IResourceService extends IBaseDataService{

	StringBuffer InsertBaseStation(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo,String delimiter);
	
	boolean updateBaseStation(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo,String delimiter);
	
	boolean delBaseStation(String pkName,String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO);
	
    StringBuffer InsertBaseresource(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo,String delimiter);
	
	boolean updateBaseresource(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo,String delimiter);
	
	boolean delBaseresource(String pkName,String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO);
	
	StringBuffer InsertBasesite(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo,String delimiter);
	
	boolean updateBasesite(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO, TaskInfoVO taskInfo,String delimiter);
	
	boolean delBasesite(String pkName,String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO);
	
	boolean InsertSysRegion(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter);
		
	boolean updateSysRegion(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter);
		
	boolean delSysRegion(String pkName,String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO);

	
}
