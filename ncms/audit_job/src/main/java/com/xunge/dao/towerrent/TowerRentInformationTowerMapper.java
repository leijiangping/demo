package com.xunge.dao.towerrent;

import java.util.List;
import java.util.Map;

import com.xunge.model.towerrent.TowerRentInformationBean;
import com.xunge.model.towerrent.TowerRentInformationTowerVO;

public interface TowerRentInformationTowerMapper {
	
	public int insert(TowerRentInformationTowerVO vo);
	
	public int batchInsert(List<TowerRentInformationTowerVO> list);
	
	public int batchUpdate(List<TowerRentInformationTowerVO> list);
	
	public List<TowerRentInformationTowerVO> queryDataByCondition(Map<String,Object> param);
	
	public TowerRentInformationBean queryMobileTowerRentInformation(Map<String,Object> param);
}
