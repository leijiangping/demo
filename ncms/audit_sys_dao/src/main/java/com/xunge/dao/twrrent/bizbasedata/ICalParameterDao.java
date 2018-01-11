package com.xunge.dao.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.CalParameterVO;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 计算参数
 */
public interface ICalParameterDao {
	/**
	 * 查询所有计算参数集合
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<CalParameterVO>> queryAllCalParameterVO(int pageSize,int pageNumber,Map<String,Object> paramMap);
	/**
	 * 查询计算参数表中的参数
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<CalParameterVO>> queryAllCalParamObjVO(int pageSize,int pageNumber,Map<String,Object> paramMap);
	
	/**查询计算参数表中的参数
	 * @param paramMap
	 * @return
	 */
	public List<CalParameterVO> queryAllCalParamObjVO(Map<String,Object> paramMap);
	/**
	 * 根据风压Id删除计算参数对象
	 * @param ids
	 * @return
	 */
	public String deleteCalParameterById(List<String> ids); 
	/**
	 * 修改计算参数对象
	 * @param CalParameterVo
	 * @return
	 */
	public String updateCalParameterById(CalParameterVO CalParameterVo); 
	/**
	 * 新增计算参数对象
	 * @param CalParameterVo
	 * @return
	 */
	public String insertCalParameterById(List<CalParameterVO> CalParameterVo); 
	/**
	 * 启用或停用计算参数状态
	 * @param ids
	 * @param CalParameterState
	 * @return
	 */
	public String startOrStopWinPressById(List<String> ids,String CalParameterState);
	/**
	 * 根据状态和名称查询符合条件的计算参数集合
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<CalParameterVO>> queryCalParameterVOByStateAndName(Map<String,Object> paramMap,int pageSize,int pageNumber);
}
