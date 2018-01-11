package com.xunge.dao.selfrent.billaccount.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfrent.billaccount.IBillAccountDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.selfrent.billAccount.BillAccountVO;
import com.xunge.model.selfrent.billAccount.RentPaymentVO;

public class BillAccountDaoImpl extends AbstractBaseDao implements IBillAccountDao {

	final String Namespace="com.xunge.mapping.BillAccountVOMapper.";
	
	@Override
	public Page<BillAccountVO> queryBillAccountVO(Map<String,Object> map,
			int pageNumber,int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"queryBillAccountVO",map);
		return PageInterceptor.endPage();  
	}

	@Override
	public BillAccountVO queryBillAccountById(String billAccountId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(Namespace+"queryBillAccountById",billAccountId);
	}

	@Override
	public List<Map<String, Object>> queryBillaccountRelations(
			String billaccountId) {
		return this.getSqlSession().selectList(Namespace+"queryBillaccountRelations",billaccountId);
	}
}
