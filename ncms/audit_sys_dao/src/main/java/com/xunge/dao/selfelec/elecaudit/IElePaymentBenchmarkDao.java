package com.xunge.dao.selfelec.elecaudit;

import java.util.List;
import java.util.Map;

import com.xunge.model.selfelec.ElePaymentBenchmark;

public interface IElePaymentBenchmarkDao {
	/**
	 * 条件查询缴费标杆信息
	 * @param paramMap
	 * @return
	 * @author zhaosf
	 */
	public List<ElePaymentBenchmark> queryAllByForginKey(Map<String,Object> paramMap);	
	/**
	 * 缴费标杆信息新增
	 * @param list
	 * @return
	 * @author xup
	 */
	public int insertBenchmarkInfo(List<ElePaymentBenchmark> paramMap);
}
