package com.xunge.service.selfelec.billaccount;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunge.core.page.Page;
import com.xunge.model.selfelec.EleBenchmarkBillaccountBean;

/**
 * @descript 报账标杆接口类
 * @author wagnz
 * @date 2017-08-20 11:18:15
 */
public interface IEleBenchmarkBillaccountService {

	/**
	 * 额定功率标杆分页
	 * @param paramMap
	 * @return
	 */
	public Page<EleBenchmarkBillaccountBean> queryPagePowerRate(Map<String,Object> paramMap);
	
	/**
	 * 额定功率标杆导出
	 * @param paramMap
	 * @param request
	 * @param response
	 */
	public void exportPowerRate(Map<String,Object> paramMap, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 智能电表标杆分页
	 * @param paramMap
	 * @return
	 */
	public Page<EleBenchmarkBillaccountBean> queryPageElectricmeter(Map<String,Object> paramMap);
	
	/**
	 * 智能电表标杆导出
	 * @param paramMap
	 * @param request
	 * @param response
	 * @return
	 */
	public void exportElectricmeter(Map<String,Object> paramMap, HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 开关电源负载标杆分页
	 * @param paramMap
	 * @return
	 */
	public Page<EleBenchmarkBillaccountBean> queryPagePowerload(Map<String,Object> paramMap);
	
	/**
	 * 开关电源负载导出
	 * @param paramMap
	 * @param request
	 * @param response
	 * @return
	 */
	public void exportPowerload(Map<String,Object> paramMap, HttpServletRequest request,HttpServletResponse response);
}
