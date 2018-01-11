package com.xunge.dao.selfrent.billaccount.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfrent.billaccount.IBillAccountDao;
import com.xunge.dao.selfrent.billaccount.IDatBaseresourceDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.selfrent.billAccount.BillAccountVO;
import com.xunge.model.selfrent.billAccount.DatBaseresourceVO;

public class DatBaseresourceDaoImpl extends AbstractBaseDao implements IDatBaseresourceDao {

	final String Namespace="com.xunge.mapping.DatBaseresourceVOMapper.";

	@Override
	public List<DatBaseresourceVO> queryDatBaseresourceByBillAccountId(
			String billAccountId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(Namespace+"queryDatBaseresourceByBillAccountId",billAccountId);
	}
	
		
}
