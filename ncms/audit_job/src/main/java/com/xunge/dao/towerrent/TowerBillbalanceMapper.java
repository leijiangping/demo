package com.xunge.dao.towerrent;

import java.util.List;
import java.util.Map;

import com.xunge.model.towerrent.TowerBillbalanceVO;

public interface TowerBillbalanceMapper {

	public int batchInsert(List<?> list);
	
	public int batchUpdate(List<?> list);
	
	public List<TowerBillbalanceVO> queryDataByCondition(Map<String,Object> param);
}
