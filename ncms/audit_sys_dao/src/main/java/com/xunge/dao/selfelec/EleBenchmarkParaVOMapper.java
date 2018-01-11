package com.xunge.dao.selfelec;

import java.util.Map;

import com.xunge.model.selfelec.EleBenchmarkParaVO;

public interface EleBenchmarkParaVOMapper {
	
	EleBenchmarkParaVO selectByPrimaryKey(EleBenchmarkParaVO key);
	/**
	 * 返回结果集
	 * @param map
	 * @return
	 * @author xup
	 */
	void  addBillaccountHistoryBenchmark(Map<String,Object> map);
}