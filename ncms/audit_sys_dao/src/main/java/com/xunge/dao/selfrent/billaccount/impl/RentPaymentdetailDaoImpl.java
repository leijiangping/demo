package com.xunge.dao.selfrent.billaccount.impl;

import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfrent.billaccount.IRentPaymentdetailDao;
import com.xunge.model.selfrent.billAccount.RentPaymentdetailVO;


public class RentPaymentdetailDaoImpl extends AbstractBaseDao implements IRentPaymentdetailDao {

	final String Namespace="com.xunge.dao.RentPaymentdetailVOMapper.";

	@Override
	public RentPaymentdetailVO queryPayMentDetailByBaseId(Map<String,Object> map) {
		return this.getSqlSession().selectOne(Namespace+"queryPayMentDetailByBaseId",map);
	}

	@Override
	public int insertRentPaymentdetail(Map<String, Object> map) {
		return this.getSqlSession().insert(Namespace+"insertRentPaymentdetail",map);
	}

	@Override
	public int updateRentPaymentdetail(RentPaymentdetailVO rentpaymentdetailVO) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(Namespace+"updateRentPaymentdetail",rentpaymentdetailVO);
	}

}
