package com.xunge.dao.twrrent.bizbasedata.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.bizbasedata.ICalParameterDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.bizbasedata.CalParameterVO;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 计算参数
 */
public class CalParameterDaoImpl extends AbstractBaseDao implements ICalParameterDao{

	
	final String TwrCalParamNamespace = "com.xunge.mapping.CalParameterVOMapper.";
	
	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<CalParameterVO>> queryAllCalParameterVO(int pageSize,
			int pageNumber,Map<String,Object> paramMap) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(TwrCalParamNamespace+"queryAllCalParameter",paramMap);
		return PageInterceptor.endPage(); 
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String deleteCalParameterById(List<String> ids) {
		Map<String,List<String>> calParamIdMap = new HashMap<String,List<String>>();
		calParamIdMap.put("calParamIdList", ids);
		int result = this.getSqlSession().delete(TwrCalParamNamespace+"deleteByCalParameterId",calParamIdMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateCalParameterById(CalParameterVO CalParameterVo) {
		int result = this.getSqlSession().update(TwrCalParamNamespace+"updateCalParameterVOById",CalParameterVo);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertCalParameterById(List<CalParameterVO> calParameterVo) {
		for (CalParameterVO calParam : calParameterVo) {
			calParam.setCalcparameterregionId(SysUUID.generator());
		}
		Map<String,List<CalParameterVO>> calParamMap = new HashMap<String,List<CalParameterVO>>(); 
		calParamMap.put("calParamList", calParameterVo);
		int result = this.getSqlSession().insert(TwrCalParamNamespace+"addCalParameterObj",calParamMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String startOrStopWinPressById(List<String> ids,
			String CalParameterState) {
		Map<String,Object> twrIdMap = new HashMap<String,Object>();
		twrIdMap.put("calParamIdList", ids);
		twrIdMap.put("calcparameterState", CalParameterState);
		int result = this.getSqlSession().update(TwrCalParamNamespace+"updateCalParameterState",twrIdMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<CalParameterVO>> queryCalParameterVOByStateAndName(
			Map<String, Object> paramMap, int pageSize, int pageNumber) {
		PageInterceptor.startPage(pageNumber, pageSize);
	    this.getSqlSession().selectList(TwrCalParamNamespace+"queryCalParameterVOByStateAndName",paramMap);
		return PageInterceptor.endPage();
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<CalParameterVO>> queryAllCalParamObjVO(int pageSize,int pageNumber,Map<String,Object> paramMap) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(TwrCalParamNamespace+"queryParameterObj",paramMap);
		return PageInterceptor.endPage();
	}

	/**
	 * @author zhaosf
	 */
	@Override
	public List<CalParameterVO> queryAllCalParamObjVO(
			Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(TwrCalParamNamespace+"queryParameterObj",paramMap);
	}
	

}
