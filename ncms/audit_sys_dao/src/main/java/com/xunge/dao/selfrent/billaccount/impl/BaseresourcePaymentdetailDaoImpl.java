package com.xunge.dao.selfrent.billaccount.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfrent.billaccount.IVBaseresourcePaymentdetailDao;
import com.xunge.model.selfrent.billAccount.VBaseresourcePaymentdetailVO;

public class BaseresourcePaymentdetailDaoImpl extends AbstractBaseDao implements IVBaseresourcePaymentdetailDao{
	
	final String Namespace="com.xunge.mapping.VBaseresourcePaymentdetailVOMapper.";
	@Override
	public List<VBaseresourcePaymentdetailVO> queryBaseresourcePaymentdetail(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryBaseresourcePaymentdetail",map);
	}

}
