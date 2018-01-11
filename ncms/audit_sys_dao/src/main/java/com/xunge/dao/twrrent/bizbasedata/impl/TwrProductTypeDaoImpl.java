package com.xunge.dao.twrrent.bizbasedata.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.bizbasedata.ITwrProductTypeDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.bizbasedata.ProductTypeVO;
import com.xunge.model.towerrent.bizbasedata.TwrProductTypeVO;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 铁塔类型
 */
public class TwrProductTypeDaoImpl extends AbstractBaseDao implements ITwrProductTypeDao {
	//铁塔类型映射表
	final String TwrProductTypeNamespace = "com.xunge.mapping.TwrProductTypeVOMapper.";
	
	/**
	 * 查询所选铁塔的下级目录
	 */
	@Override
	public Page<List<TwrProductTypeVO>> queryTwrProductTypeVO(Map<String,Object> paraMap,int pageSize,int pageNumber) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(TwrProductTypeNamespace+"queryAllTwrProductTypeByMenuId",paraMap);
		return PageInterceptor.endPage();  
	}
	
	/**
	 * 查询所选铁塔的下级目录
	 * @author jiacy
	 */
	@Override
	public List<TwrProductTypeVO> queryAllTwrProductTypeVO() {
		return this.getSqlSession().selectList(TwrProductTypeNamespace+"queryAllTwrProductType");
	}

	/**
	 * 批量删除选中铁塔对象
	 * @author jiacy
	 */
	@Override
	public String deleteTwrById(List<String> ids) {
		Map<String,List<String>> twrIdMap = new HashMap<String,List<String>>();
		twrIdMap.put("twrIdList", ids);
		int result = this.getSqlSession().delete(TwrProductTypeNamespace+"deleteByTypeId",twrIdMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateTwrById(TwrProductTypeVO twoProductVO) {
		int result = this.getSqlSession().update(TwrProductTypeNamespace+"updateTwrVOById",twoProductVO);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertTwrById(TwrProductTypeVO twoProductVO) {
		int result = this.getSqlSession().insert(TwrProductTypeNamespace+"addTwrObj",twoProductVO);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String startOrStopTwrTypeById(List<String> ids, String twrState) {
		Map<String,Object> twrIdMap = new HashMap<String,Object>();
		twrIdMap.put("twrIdList", ids);
		twrIdMap.put("twrState", twrState);
		int result = this.getSqlSession().update(TwrProductTypeNamespace+"updateTwrState",twrIdMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}
	
	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<TwrProductTypeVO>> queryTwrVOByStateAndName(Map<String,Object> paramMap,int pageSize,int pageNumber) {
		PageInterceptor.startPage(pageNumber, pageSize);
	    this.getSqlSession().selectList(TwrProductTypeNamespace+"queryTwrVOByStateAndName",paramMap);
		return PageInterceptor.endPage();
	}

	/**
	 * @author zhaosf
	 */
	@Override
	public List<ProductTypeVO> queryTwrProductTypeName(Map<String, Object> paraMap) {		 
		return this.getSqlSession().selectList(TwrProductTypeNamespace+"queryTwrProductTypeName",paraMap);
	}

	
	/**
	 * @author zhaosf
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<List<TwrProductTypeVO>> queryTwrProductTypeInfoByCondition(
			Map<String, Object> paraMap, int pageSize, int pageNumber) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(TwrProductTypeNamespace+"queryTwrProductTypeInfoByCondition",paraMap);
		return PageInterceptor.endPage();  
	}
	
}