package com.xunge.dao.selfrent.contract.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfrent.contract.IDatPaymentPeriodDao;
import com.xunge.model.selfrent.billAccount.DatPaymentPeriodVO;

public class DatPaymentPeriodDaoImpl extends AbstractBaseDao implements IDatPaymentPeriodDao {

	//付款供应商表
	final String Namespace = "com.xunge.dao.DatPaymentPeriodMapper.";
	
	@Override
	public List<DatPaymentPeriodVO> queryAllDatPaymentPeriod(
			Map<String,Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryAllDatPaymentPeriod",paraMap);
	}

	@Override
	public DatPaymentPeriodVO queryDatPaymentPeriodById(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryDatPaymentPeriodById",paraMap);
	}
	
}