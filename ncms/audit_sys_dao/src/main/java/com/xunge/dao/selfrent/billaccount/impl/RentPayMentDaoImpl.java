package com.xunge.dao.selfrent.billaccount.impl;

import java.util.List;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfrent.billaccount.IRentPaymentDao;
import com.xunge.model.selfrent.billAccount.RentPaymentVO;


public class RentPayMentDaoImpl extends AbstractBaseDao implements IRentPaymentDao {

	final String Namespace="com.xunge.dao.RentPaymentVOMapper.";

	@Override
	public List<RentPaymentVO> queryRentPaymentByBillAccountId(String billAccountId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(Namespace+"queryRentPaymentByBillAccountId",billAccountId);
	}

	@Override
	public int insertRentPayment(RentPaymentVO rentPaymentVO) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(Namespace+"insertRentPayment",rentPaymentVO);
	}

	@Override
	public int updateRentPayment(RentPaymentVO rentPaymentVO) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(Namespace+"updateRentPayment",rentPaymentVO);
	}
	
}
