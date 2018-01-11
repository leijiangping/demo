package com.xunge.service.basedata.ring;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xunge.model.basedata.ring.MeterPerfVO;
import com.xunge.model.basedata.ring.PowerPerfVO;
import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.service.basedata.IBaseDataService;

public interface IRingService extends IBaseDataService{
	

	StringBuffer InsertMeter(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,TaskInfoVO taskInfo,String delimiter);
	
	boolean updateMeter(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,TaskInfoVO taskInfo,String delimiter);
	
	boolean delMeter(String pkName,String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO);
	
	StringBuffer InsertPower(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,TaskInfoVO taskInfo,String delimiter);
	
	boolean updatePower(String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO,TaskInfoVO taskInfo,String delimiter);
	
	boolean delPower(String pkName,String typeName,List<String> columns,Map<String, String> map,SysProvinceVO sysProvinceVO);
	
	String[] getDBparms(String perfix);
	
	/**
	 * 获取所有的电表性能数据
	 * @return
	 */
	public PageInfo<MeterPerfVO> findAllMeterPerf(String userId,String prvid,String pregid,String regid,String resourcename,String resourcetype,String date,int pageNumber,int pageSize);
	
	
	/**
	 * 获取所有的开关电源性能数据
	 * @return
	 */
	public PageInfo<PowerPerfVO> findAllPowerPerf(String userId,String prvid,String pregid,String regid,String resourcename,String resourcetype,String date,int pageNumber,int pageSize);
	
	/**
	 * 导出所有的电表性能数据
	 * @return
	 */
	public String exportAllMeterPerf(String userId,String prvid,String path,String pregid,String regid,String resourcename,String resourcetype,String date);
	
	
	/**
	 * 导出所有的开关电源性能数据
	 * @return
	 */
	public String exportAllPowerPerf(String userId,String prvid,String path,String pregid,String regid,String resourcename,String resourcetype,String date);
}
