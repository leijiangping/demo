package com.xunge.service.selfelec.elecaudit.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.selfelec.elecaudit.IElePaymentBenchmarkDao;
import com.xunge.model.selfelec.ElePaymentBenchmark;
import com.xunge.service.selfelec.elecaudit.IElePaymentBenchmarkService;

public class ElePaymentBenchmarkServiceImpl implements IElePaymentBenchmarkService{
	private IElePaymentBenchmarkDao elePaymentBenchmarkDao;
	
	@Override
	public List<ElePaymentBenchmark> queryAllByForginKey(
			Map<String, Object> paramMap) {
		return elePaymentBenchmarkDao.queryAllByForginKey(paramMap);
	}
	
	public IElePaymentBenchmarkDao getElePaymentBenchmarkDao() {
		return elePaymentBenchmarkDao;
	}
	public void setElePaymentBenchmarkDao(IElePaymentBenchmarkDao elePaymentBenchmarkDao) {
		this.elePaymentBenchmarkDao = elePaymentBenchmarkDao;
	}
	
}
