package com.xunge.service.basedata.ring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.xunge.model.basedata.ring.MeterPerfVO;
import com.xunge.model.basedata.ring.PowerPerfVO;

public interface IRingService {
	
	/**
	 * 获取所有的电表性能数据
	 * @return
	 */
	public PageInfo<MeterPerfVO> findAllMeterPerf(String userId,String prvid,String pregid,String regid,String resourcename,String resourcetype,String date,Integer pageNumber,Integer pageSize);
	
	
	/**
	 * 获取所有的开关电源性能数据
	 * @return
	 */
	public PageInfo<PowerPerfVO> findAllPowerPerf(String userId,String prvid,String pregid,String regid,String resourcename,String resourcetype,String date,Integer pageNumber,Integer pageSize);
	
	/**
	 * 导出所有的电表性能数据
	 * @return
	 */
	public String exportAllMeterPerf(String userId,String prvid,String path,String pregid,
			String regid,String resourcename,String resourcetype,String date,HttpServletRequest request, HttpServletResponse response);
	
	
	/**
	 * 导出所有的开关电源性能数据
	 * @return
	 */
	public String exportAllPowerPerf(String userId,String prvid,String path,
			String pregid,String regid,String resourcename,String resourcetype,String date,HttpServletRequest request, HttpServletResponse response);
}
