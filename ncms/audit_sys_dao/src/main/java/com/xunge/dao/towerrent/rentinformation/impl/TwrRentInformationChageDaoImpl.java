package com.xunge.dao.towerrent.rentinformation.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.towerrent.rentinformation.ITwrRentInformationChangeDao;
import com.xunge.model.towerrent.rentinformation.TwrRentInformationChangeVO;
import com.xunge.filter.PageInterceptor;

public class TwrRentInformationChageDaoImpl extends AbstractBaseDao implements
		ITwrRentInformationChangeDao {

	final String TwrRentInformationChangeNamespace = "com.xunge.dao.towerrent.rentinformation.TwrRentInformationChangeVODao.";
	
	@Override
	public int deleteByPrimaryKey(String rentinformationchangeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TwrRentInformationChangeVO record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TwrRentInformationChangeVO record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TwrRentInformationChangeVO selectByPrimaryKey(
			String rentinformationchangeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(TwrRentInformationChangeVO record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(TwrRentInformationChangeVO record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> queryTwrRentInformationChangeByCondition(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<List<Map<String, Object>>> queryTwrRentInformationChangePage(
			Map<String, Object> paramMap) {
		PageInterceptor.startPage(Integer.parseInt(paramMap.get("pageNumber").toString()),Integer.parseInt(paramMap.get("pageSize").toString()));
		this.getSqlSession().selectList(TwrRentInformationChangeNamespace + "queryTwrRentInformationChangeByCondition",paramMap);
		return PageInterceptor.endPage();  
	}

	@Override
	public Page<List<TwrRentInformationChangeVO>> queryTwrRentInformationChangeVoPage(
			Map<String, Object> paramMap) {
		PageInterceptor.startPage(Integer.parseInt(paramMap.get("pageNumber").toString()),Integer.parseInt(paramMap.get("pageSize").toString()));
		this.getSqlSession().selectList(TwrRentInformationChangeNamespace + "queryTwrRentInformationChangeVoByCondition",paramMap);
		return PageInterceptor.endPage();  
	}

	@Override
	public List<TwrRentInformationChangeVO> queryTwrRentInformationChangeVoListByCondition(
			Map<String, Object> params) {
		return this.getSqlSession().selectList(TwrRentInformationChangeNamespace + "queryTwrRentInformationChangeVoByCondition",params);
	}

}
