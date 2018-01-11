package com.xunge.dao.towerrent;

import java.util.List;
import java.util.Map;

import com.xunge.model.towerrent.TowerRentinformationBizchangeVO;

public interface TowerRentinformationBizchangeMapper {
	
	public int batchInsert(List<TowerRentinformationBizchangeVO> list);
	
	public int batchUpdate(List<TowerRentinformationBizchangeVO> list);
	
	public List<TowerRentinformationBizchangeVO> queryDataByCondition(Map<String,Object> param);
}
