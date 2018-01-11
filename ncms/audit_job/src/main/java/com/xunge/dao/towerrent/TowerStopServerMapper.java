package com.xunge.dao.towerrent;

import java.util.List;
import java.util.Map;

import com.xunge.model.towerrent.TowerStopServerVO;

public interface TowerStopServerMapper {
	
	public int batchInsert(List<TowerStopServerVO> list);
	
	public int batchUpdate(List<TowerStopServerVO> list);
	
	public List<TowerStopServerVO> queryDataByCondition(Map<String,Object> param);
}
