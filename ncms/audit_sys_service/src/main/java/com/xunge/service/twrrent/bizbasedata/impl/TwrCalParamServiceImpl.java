package com.xunge.service.twrrent.bizbasedata.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.twrrent.bizbasedata.ICalParameterDao;
import com.xunge.model.towerrent.bizbasedata.CalParameterVO;
import com.xunge.service.twrrent.bizbasedata.ITwrCalParamService;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 计算参数
 */
public class TwrCalParamServiceImpl implements ITwrCalParamService{

	private ICalParameterDao calParamDao;
	
	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<CalParameterVO>> queryAllCalParameterVO(int pageSize,
			int pageNumber,Map<String,Object> paramMap) {
		return calParamDao.queryAllCalParameterVO(pageSize, pageNumber,paramMap);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String deleteCalParameterById(List<String> ids) {
		return calParamDao.deleteCalParameterById(ids);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateCalParameterById(CalParameterVO CalParameterVo) {
		return calParamDao.updateCalParameterById(CalParameterVo);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertCalParameterById(List<CalParameterVO> CalParameterVo) {
		return calParamDao.insertCalParameterById(CalParameterVo);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateStartOrStopWinPressById(List<String> ids,
			String CalParameterState) {
		return calParamDao.startOrStopWinPressById(ids, CalParameterState);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<CalParameterVO>> queryCalParameterVOByStateAndName(
			Map<String, Object> paramMap, int pageSize, int pageNumber) {
		return calParamDao.queryCalParameterVOByStateAndName(paramMap, pageSize, pageNumber);
	}

	public ICalParameterDao getCalParamDao() {
		return calParamDao;
	}

	public void setCalParamDao(ICalParameterDao calParamDao) {
		this.calParamDao = calParamDao;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<CalParameterVO>> queryParamObj(int pageSize,int pageNumber,Map<String,Object> paramMap) {
		return calParamDao.queryAllCalParamObjVO(pageSize,pageNumber,paramMap);
	}

	/**
	 * @author zhaosf
	 */
	@Override
	public List<CalParameterVO> queryParamObj(Map<String, Object> paramMap) {
		return calParamDao.queryAllCalParamObjVO(paramMap);
	}
	
}
