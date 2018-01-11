package com.xunge.dao.twrrent.bizbasedata.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.bizbasedata.ITwrWindPressureDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.bizbasedata.WindPressureVO;

/**
 * @author jcy
 * @date 2017年7月6日
 * @description 风压范围
 */
public class TwrWindPressureDaoImpl extends AbstractBaseDao  implements ITwrWindPressureDao{

	//风压映射表
	final String TwrWindPressureNamespace = "com.xunge.mapping.WindPressureVOMapper.";
	
	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<WindPressureVO>> queryAllWinPressVO(int pageSize,
			int pageNumber) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(TwrWindPressureNamespace+"queryAllWindPressureObj");
		return PageInterceptor.endPage();  
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String deleteWinPressById(List<String> ids) {
		Map<String,List<String>> twrIdMap = new HashMap<String,List<String>>();
		twrIdMap.put("windPressIdList", ids);
		int result = this.getSqlSession().delete(TwrWindPressureNamespace+"deleteByTypeId",twrIdMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateWinPressById(WindPressureVO pressureVo) {
		int result = this.getSqlSession().update(TwrWindPressureNamespace+"updateWindPressureVOById",pressureVo);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertWinPressById(WindPressureVO windPressVo) {
		windPressVo.setWindpressureId(SysUUID.generator());
		int result = this.getSqlSession().insert(TwrWindPressureNamespace+"addWindPressureVO",windPressVo);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String startOrStopWinPressById(List<String> ids,
			String winPressState) {
		Map<String,Object> twrIdMap = new HashMap<String,Object>();
		twrIdMap.put("windPressIdList", ids);
		twrIdMap.put("windpressureState", winPressState);
		int result = this.getSqlSession().update(TwrWindPressureNamespace+"updateWindPressureState",twrIdMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<WindPressureVO>> queryWinPressVOByStateAndName(
			Map<String, Object> paramMap, int pageSize, int pageNumber) {
		PageInterceptor.startPage(pageNumber, pageSize);
	    this.getSqlSession().selectList(TwrWindPressureNamespace+"queryWindPressureVOByStateAndName",paramMap);
		return PageInterceptor.endPage();
	}

}
