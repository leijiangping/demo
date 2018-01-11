package com.xunge.service.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.CalParameterVO;

/**
 * @author jiacy
 * @date 2017年7月6日
 * @description 计算参数
 */
public interface ITwrCalParamService {
	/**
	 * 查询计算参数区域对象
	 * @author jiacy
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<CalParameterVO>> queryAllCalParameterVO(int pageSize,int pageNumber,Map<String,Object> paramMap);
	/**
	 * 查询计算参数对象
	 * @author jiacy
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<CalParameterVO>> queryParamObj(int pageSize,int pageNumber,Map<String,Object> paramMap);
	/**查询计算参数对象
	 * @param paramMap
	 * @return
	 */
	public List<CalParameterVO> queryParamObj(Map<String,Object> paramMap);
	/**
	 * 删除计算参数对象
	 * @author jiacy
	 * @param ids
	 * @return
	 */
	public String deleteCalParameterById(List<String> ids); 
	/**
	 * 修改计算参数对象
	 * @author jiacy
	 * @param CalParameterVo
	 * @return
	 */
	public String updateCalParameterById(CalParameterVO CalParameterVo); 
	/**
	 * 新增计算参数对象
	 * @author jiacy
	 * @param CalParameterVo
	 * @return
	 */
	public String insertCalParameterById(List<CalParameterVO> CalParameterVo); 
	/**
	 * 启用或停用计算参数对象
	 * @author jiacy
	 * @param ids
	 * @param CalParameterState
	 * @return
	 */
	public String updateStartOrStopWinPressById(List<String> ids,String CalParameterState);
	/**
	 * 按条件过滤计算参数对象
	 * @author jiacy
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<CalParameterVO>> queryCalParameterVOByStateAndName(Map<String,Object> paramMap,int pageSize,int pageNumber);
}
