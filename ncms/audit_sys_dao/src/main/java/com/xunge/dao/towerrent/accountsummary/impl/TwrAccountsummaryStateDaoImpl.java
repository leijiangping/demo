package com.xunge.dao.towerrent.accountsummary.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.towerrent.accountsummary.ITwrAccountsummaryStateVODao;
import com.xunge.model.towerrent.accountsummary.TwrAccountsummaryStateVO;

public class TwrAccountsummaryStateDaoImpl extends AbstractBaseDao implements
		ITwrAccountsummaryStateVODao {

	final String TwrRentInformationChangeNamespace = "com.xunge.dao.towerrent.accountsummary.TwrAccountsummaryStateVODao.";
	
	@Override
	public List<Map<String, Object>> queryTwrAccountsummarStateyMapListByCondition(
			Map<String, Object> params) {
		return this.getSqlSession().selectList(TwrRentInformationChangeNamespace + "queryTwrAccountsummeryStateMapListByCondition",params);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TwrAccountsummaryStateVO record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(TwrAccountsummaryStateVO record) {
		return this.getSqlSession().insert(TwrRentInformationChangeNamespace + "insertSelective",record);
	}

	@Override
	public TwrAccountsummaryStateVO selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(TwrAccountsummaryStateVO record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(TwrAccountsummaryStateVO record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteTwrAccountsummaryByCondition(Map<String, Object> params) {
		return this.getSqlSession().delete(TwrRentInformationChangeNamespace + "deleteTwrAccountsummaryByCondition",params);
	}

}
