package com.xunge.service.job;

import java.util.List;
import java.util.Map;

import com.xunge.model.job.SysProvinceVO;

public interface IResourceService {

	boolean InsertBaseStation(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter);
	
	boolean updateBaseStation(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter);
	
	boolean delBaseStation(String pkName,String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO);
	
    boolean InsertBaseresource(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter);
	
	boolean updateBaseresource(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter);
	
	boolean delBaseresource(String pkName,String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO);
	
    boolean InsertBasesite(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter);
	
	boolean updateBasesite(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter);
	
	boolean delBasesite(String pkName,String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO);
	
	boolean InsertSysRegion(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter);
		
	boolean updateSysRegion(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,String delimiter);
		
	boolean delSysRegion(String pkName,String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO);
	
	String[] getDBparms(String perfix);
}
