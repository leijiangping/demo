package com.xunge.dao.towerrent.rentinformationhistory.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.towerrent.rentinformationhistory.ITwrRentInformationHistoryDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.rentmanager.TowerRentInformationHistoryVO;

public class TwrRentInformationHistoryDaoImpl extends AbstractBaseDao implements
		ITwrRentInformationHistoryDao {

	final String Namespace = "com.xunge.dao.TowerRentInformationHistoryVOMapper.";

	@Override
	public int insertRentInformationHistoryFromMobile(
			Map<String, Object> paraMap) {
		return this.getSqlSession().insert(Namespace+"insertRentInformationHistoryFromMobile",paraMap);
	}

	@Override
	public List<TowerRentInformationHistoryVO> queryTowerRentInfoHistory(Map<String, Object> paraMap){
		return this.getSqlSession().selectList(Namespace + "queryTowerRentInfoHistory", paraMap);
	}

	@Override
	public List<TowerRentInformationHistoryVO> queryAllHistoryById(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryAllHistoryById",paraMap);
	}

	@Override
	public int updateChangeItemById(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateChangeItemById",paraMap);
	}

	@Override
	public int updateEndDateById(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateEndDateById",paraMap);
	}

	@Override
	public int insertSelective(
			TowerRentInformationHistoryVO towerRentInformationHistoryVO) {
		return this.getSqlSession().insert(Namespace+"insertSelective",towerRentInformationHistoryVO);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<TowerRentInformationHistoryVO> queryPageRentInfoHistory(
			Map<String, Object> paraMap) {
		PageInterceptor.startPage(Integer.parseInt(paraMap.get("pageNumber").toString()),Integer.parseInt(paraMap.get("pageSize").toString()));
		this.getSqlSession().selectList(Namespace+"queryTowerRentInfoHistory",paraMap);
		return PageInterceptor.endPage();
	}

	@Override
	public int deleteHistoryByDate(Map<String, Object> paraMap) {
		return this.getSqlSession().delete(Namespace+"deleteHistoryByDate",paraMap);
	}
}
