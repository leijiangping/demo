package com.xunge.dao.selfelec.elecaudit.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.core.util.StrUtil;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfelec.elecaudit.IHistoryPaymentModelDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.selfelec.vo.HistoryPaymentModelVO;

public class HistoryPaymentModelDaoImpl extends AbstractBaseDao implements IHistoryPaymentModelDao{

	final String HistoryPaymentNamespace = "com.xunge.mapping.HistoryPaymentModelVOMapper.";
	
	@Override
	public Page<HistoryPaymentModelVO> queryAll(Map<String,Object> paramMap) {
		int pageNumber = Integer.parseInt(paramMap.get("pageNumber").toString());
		int pageSize = Integer.parseInt(paramMap.get("pageSize").toString());
		PageInterceptor.startPage(pageNumber, pageSize);
		List<HistoryPaymentModelVO> historyLst = this.getSqlSession().selectList(HistoryPaymentNamespace+"queryAll",paramMap);
		return PageInterceptor.endPage(); 
	}
	
	@Override
	public List<HistoryPaymentModelVO> queryAllNoPage(Map<String,Object> paramMap) {
		return this.getSqlSession().selectList(HistoryPaymentNamespace+"queryAll",paramMap);
	}

	@Override
	public void export() {
		
	}

}
