package com.xunge.service.selfelec.elecaudit;

import java.util.List;
import java.util.Map;

import com.xunge.model.selfelec.ElePaymentBenchmark;

public interface IElePaymentBenchmarkService {
	
	public List<ElePaymentBenchmark> queryAllByForginKey(Map<String,Object> paramMap);
}
