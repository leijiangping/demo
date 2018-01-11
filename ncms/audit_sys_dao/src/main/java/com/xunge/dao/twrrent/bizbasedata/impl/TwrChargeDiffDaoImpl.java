package com.xunge.dao.twrrent.bizbasedata.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.bizbasedata.ITwrChargeDiffDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.bizbasedata.CostDifferVO;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 费用差异范围
 */
public class TwrChargeDiffDaoImpl extends AbstractBaseDao implements ITwrChargeDiffDao{

	private String TwrCostDiffNamespace = "com.xunge.mapping.CostDifferVO.";
	
	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<CostDifferVO>> queryAllObj(int pageSize, int pageNumber,Map<String,Object> paramMap) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(TwrCostDiffNamespace+"queryAllObj",paramMap);
		return PageInterceptor.endPage(); 
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String deleteObjById(List<String> ids) {
		Map<String,List<String>> calParamIdMap = new HashMap<String,List<String>>();
		calParamIdMap.put("objIdList", ids);
		int result = this.getSqlSession().delete(TwrCostDiffNamespace+"deleteObjById",calParamIdMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateObjById(CostDifferVO costDiffVo) {
		int result = this.getSqlSession().update(TwrCostDiffNamespace+"updateObjById",costDiffVo);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertObjById(CostDifferVO costDiffVo) {
		costDiffVo.setChargediffId(SysUUID.generator());
		int result = this.getSqlSession().insert(TwrCostDiffNamespace+"addObj",costDiffVo);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<CostDifferVO>> queryObjByCondition(
			Map<String, Object> paramMap, int pageSize, int pageNumber) {
		PageInterceptor.startPage(pageNumber, pageSize);
	    this.getSqlSession().selectList(TwrCostDiffNamespace+"queryObjByCondition",paramMap);
		return PageInterceptor.endPage();
	}

}
