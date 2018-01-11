package com.xunge.dao.towerrent.accountsummary.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.towerrent.accountsummary.ITwrAccountsummaryVODao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.accountsummary.TwrAccountsummaryVO;

public class TwrAccountsummaryDaoImpl extends AbstractBaseDao implements ITwrAccountsummaryVODao {

	final String TwrAccountsummaryNamespace = "com.xunge.dao.towerrent.accountsummary.TwrAccountsummaryVODao.";
	
	@Override
	public int deleteByPrimaryKey(String accountsummaryId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TwrAccountsummaryVO record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TwrAccountsummaryVO record) {
		return this.getSqlSession().insert(TwrAccountsummaryNamespace + "insertSelective",record);
	}

	@Override
	public TwrAccountsummaryVO selectByPrimaryKey(String accountsummaryId) {
		return this.getSqlSession().selectOne(TwrAccountsummaryNamespace + "selectByPrimaryKey",accountsummaryId);
	}

	@Override
	public int updateByPrimaryKeySelective(TwrAccountsummaryVO record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(TwrAccountsummaryNamespace + "updateByPrimaryKeySelective",record);
	}

	@Override
	public int updateByPrimaryKey(TwrAccountsummaryVO record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Map<String, Object>> queryTwrAccountsummaryMapListByCondition(
			Map<String, Object> params) {
		return this.getSqlSession().selectList(TwrAccountsummaryNamespace + "queryTwrAccountsummeryMapListByCondition",params);
	}

	@Override
	public Page<List<Map<String, Object>>> queryTwrAccountsummaryMapPage(
			Map<String, Object> paramMap) {
		PageInterceptor.startPage(Integer.parseInt(paramMap.get("pageNumber").toString()),Integer.parseInt(paramMap.get("pageSize").toString()));
		this.getSqlSession().selectList(TwrAccountsummaryNamespace + "queryTwrAccountsummeryMapListByCondition",paramMap);
		return PageInterceptor.endPage();  
	}

	@Override
	public int deleteTwrAccountsummaryByCondition(Map<String, Object> params) {
		return this.getSqlSession().delete(TwrAccountsummaryNamespace + "deleteTwrAccountsummaryByCondition",params);
	}

	@Override
	public int selectSubmitedAccountsummaryCountByCondition(
			Map<String, Object> params) {
		return this.getSqlSession().selectOne(TwrAccountsummaryNamespace + "selectSubmitedAccountsummaryCountByCondition",params);
	}

	@Override
	public List<Map<String, Object>> queryTwrAccountsummeryMapListByCondition1(
			Map<String, Object> params) {
		return this.getSqlSession().selectList(TwrAccountsummaryNamespace + "queryTwrAccountsummeryMapListByCondition1",params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<TwrAccountsummaryVO> queryPageAccountsummery(Map<String, Object> params) {
		PageInterceptor.startPage(Integer.parseInt(params.get("pageNumber").toString()),Integer.parseInt(params.get("pageSize").toString()));
		this.getSqlSession().selectList(TwrAccountsummaryNamespace + "queryPageTwrAccountsummery",params);
		return PageInterceptor.endPage();
	}

	@Override
	public TwrAccountsummaryVO selectByAccountId(Map<String,Object> params) {
		return this.getSqlSession().selectOne(TwrAccountsummaryNamespace + "queryAccountsummeryById",params);
	}
}
