package com.xunge.dao.twrrent.bizbasedata.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.bizbasedata.ITwrProductCostDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.bizbasedata.CommTypeVO;
import com.xunge.model.towerrent.bizbasedata.HighUpVO;
import com.xunge.model.towerrent.bizbasedata.ProductCostVO;
import com.xunge.model.towerrent.bizbasedata.WindPressureVO;

/**
 * @author jcy
 * @date 2017年7月6日
 * @description 建造成本库
 */
public class TwrProductCostDaoImpl extends AbstractBaseDao implements ITwrProductCostDao{
	final String TwrProductCostNamespace = "com.xunge.mapping.ProductCostVOMapper.";
	
	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<ProductCostVO>> queryAllProCostVO(int pageSize,
			int pageNumber) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(TwrProductCostNamespace+"queryAllProCost");
		return PageInterceptor.endPage(); 
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String deleteProCostById(List<String> ids) {
		Map<String,List<String>> proCostIdMap = new HashMap<String,List<String>>();
		proCostIdMap.put("productCostIdList", ids);
		int result = this.getSqlSession().delete(TwrProductCostNamespace+"deleteByProCostId",proCostIdMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateProCostById(ProductCostVO proCostVO) {
		int result = this.getSqlSession().update(TwrProductCostNamespace+"updateProCostVOById",proCostVO);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertProCostById(ProductCostVO proCostVO) {
		proCostVO.setProductcostId(SysUUID.generator());
		int result = this.getSqlSession().insert(TwrProductCostNamespace+"addProCostObj",proCostVO);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String startOrStopProCostById(List<String> ids, String proCostState) {
		Map<String,Object> proCostIdMap = new HashMap<String,Object>();
		proCostIdMap.put("proCostIdList", ids);
		proCostIdMap.put("productcostState", proCostState);
		int result = this.getSqlSession().update(TwrProductCostNamespace+"updateProCostState",proCostIdMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<ProductCostVO>> queryProCostVOByStateAndName(
			Map<String, Object> paramMap, int pageSize, int pageNumber) {
		PageInterceptor.startPage(pageNumber, pageSize);
	    this.getSqlSession().selectList(TwrProductCostNamespace+"queryProCostVOByStateAndName",paramMap);
		return PageInterceptor.endPage();
	}
	
	/**
	 * @author jiacy
	 */
	@Override
	public List<ProductCostVO> querytwrTypeVO() {
	    return this.getSqlSession().selectList(TwrProductCostNamespace+"queryTwrType");
	}
	
	/**
	 * @author jiacy
	 */
	@Override
	public List<WindPressureVO> queryWindPress() {
	    return this.getSqlSession().selectList(TwrProductCostNamespace+"queryWindPress");
	}
	
	/**
	 * @author jiacy
	 */
	@Override
	public List<HighUpVO> queryHighUpVO() {
	    return this.getSqlSession().selectList(TwrProductCostNamespace+"queryHigUp");
	}

	/**
	 * @author jiacy
	 */
	@Override
	public List<CommTypeVO> queryRoomAndSuptType(
			Map<String, Object> paramMap) {
	    return this.getSqlSession().selectList(TwrProductCostNamespace+"queryAllGroupType",paramMap);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public List<String> queryRoomList() {
		return this.getSqlSession().selectList(TwrProductCostNamespace+"queryRoomList");
	}

	/**
	 * @author jiacy
	 */
	@Override
	public List<String> querySupptList() {
		return this.getSqlSession().selectList(TwrProductCostNamespace+"querySuptList");
	}

	/**
	 * @author jiacy
	 */
	@Override
	public List<String> queryTwrList() {
		return this.getSqlSession().selectList(TwrProductCostNamespace+"queryTwrTypeList");
	}
}
