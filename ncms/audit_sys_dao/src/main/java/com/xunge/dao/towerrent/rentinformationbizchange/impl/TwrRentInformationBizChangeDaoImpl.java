package com.xunge.dao.towerrent.rentinformationbizchange.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.towerrent.rentinformationbizchange.ITwrRentInformationBizChangeDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.bizchange.TowerRentinformationBizchangeVO;

public class TwrRentInformationBizChangeDaoImpl extends AbstractBaseDao implements ITwrRentInformationBizChangeDao {
	
	final String Namespace = "com.xunge.dao.TowerRentinformationBizchangeVOMapper.";

	@Override
	public Page<TowerRentinformationBizchangeVO> queryTowerRentinformationBizchangeInfo(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"queryInformationBizChangeCheckInfo",paraMap);
		return PageInterceptor.endPage();
	}

	@Override
	public int updateBizChangeCheckState(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateBizChangeCheckState",paraMap);
	}

	@Override
	public TowerRentinformationBizchangeVO queryBizChangeById(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryBizChangeById",paraMap);
	}
	
	/**
	 * 查询所有铁塔信息变更表信息
	 * @author yuefy
	 */
	@Override
	public List<TowerRentinformationBizchangeVO> queryTowerRentinformationBizchangeInfo(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryInformationBizChangeCheckInfo",paraMap);
	}

	@Override
	public int insertBatchSelective(
			List<TowerRentinformationBizchangeVO> list) {
		return this.getSqlSession().insert(Namespace+"insertBizChangeInfo",list);
	}
}