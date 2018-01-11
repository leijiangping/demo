package com.xunge.dao.selfelec.elecaudit.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfelec.elecaudit.IElePaymentBenchmarkDao;
import com.xunge.model.selfelec.ElePaymentBenchmark;

public class ElePaymentBenchmarkDaoImpl extends AbstractBaseDao implements IElePaymentBenchmarkDao{

	final String namespace = "com.xunge.dao.selfelec.ElePaymentBenchmarkMapper.";
	
	@Override
	public List<ElePaymentBenchmark> queryAllByForginKey(Map<String,Object> paramMap) {
		List<ElePaymentBenchmark> elePayBenchMarkList = 
				this.getSqlSession().selectList(namespace+"queryAllByForginKey",paramMap);
		return elePayBenchMarkList;
	}

	@Override
	public int insertBenchmarkInfo(List<ElePaymentBenchmark> paramMap) {
		return this.getSqlSession().insert(namespace+"insertBenchmarkInfo",paramMap);
	}
}
