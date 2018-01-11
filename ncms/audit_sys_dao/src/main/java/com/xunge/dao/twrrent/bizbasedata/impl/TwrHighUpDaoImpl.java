package com.xunge.dao.twrrent.bizbasedata.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.bizbasedata.ITwrHighUpDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.bizbasedata.HighUpVO;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 挂高范围
 */
public class TwrHighUpDaoImpl extends AbstractBaseDao implements ITwrHighUpDao{

	final String TwrHighUpNamespace = "com.xunge.mapping.HighUpVOMapper.";
	
	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<HighUpVO>> queryAllHighUpVO(int pageSize, int pageNumber) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(TwrHighUpNamespace+"queryAllHighUp");
		return PageInterceptor.endPage(); 
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String deleteHighUpById(List<String> ids) {
		Map<String,List<String>> twrIdMap = new HashMap<String,List<String>>();
		twrIdMap.put("highUpIdList", ids);
		int result = this.getSqlSession().delete(TwrHighUpNamespace+"deleteByHighUpId",twrIdMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateHighUpById(HighUpVO highUpVo) {
		int result = this.getSqlSession().update(TwrHighUpNamespace+"updateHighVOById",highUpVo);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertHighUpById(HighUpVO highUpVo) {
		highUpVo.setHighupId(SysUUID.generator());
		int result = this.getSqlSession().insert(TwrHighUpNamespace+"addHighUpObj",highUpVo);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String startOrStopHighUpById(List<String> ids, String highUpState) {
		Map<String,Object> twrIdMap = new HashMap<String,Object>();
		twrIdMap.put("highUpIdList", ids);
		twrIdMap.put("highupState", highUpState);
		int result = this.getSqlSession().update(TwrHighUpNamespace+"updateHighUpState",twrIdMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<HighUpVO>> queryHighUpVOByStateAndName(
			Map<String, Object> paramMap, int pageSize, int pageNumber) {
		PageInterceptor.startPage(pageNumber, pageSize);
	    this.getSqlSession().selectList(TwrHighUpNamespace+"queryHighUpVOByStateAndName",paramMap);
		return PageInterceptor.endPage();
	}
}
