package com.xunge.dao.twrrent.settlement.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.settlement.ITowerMobileBillCheckDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.settlement.BillConfirmVO;
import com.xunge.model.towerrent.settlement.TowerAndMobileBillConfirmVO;
import com.xunge.model.towerrent.settlement.TowerAndMobileBillVO;
import com.xunge.model.towerrent.settlement.TowerBillbalanceVO;

@SuppressWarnings("unchecked")
public class TowerMobileBillCheckDaoImpl extends AbstractBaseDao implements ITowerMobileBillCheckDao {
	final String TowerMobilerBillbalanceVOMapper = "com.xunge.dao.TowerMobilerBillbalanceVOMapper.";
	final String TowerMobilerBillbalanceConfirmVOMapper = "com.xunge.dao.TowerMobilerBillbalanceConfirmVOMapper.";
	final String NameSpace ="com.xunge.mapping.TowerBillbalanceVOMapper.";
	
	@Override
	public Page<List<TowerAndMobileBillVO>> queryTowerAndMobileFee(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(TowerMobilerBillbalanceVOMapper+"queryTowerAndMobileFee",paraMap);
		return PageInterceptor.endPage(); 
	}
	@Override
	public int updateCompareResult(TowerAndMobileBillVO towerAndMobileBillVO) {
		return this.getSqlSession().update(TowerMobilerBillbalanceVOMapper+"updateCompareResult",towerAndMobileBillVO);
	}
	@Override
	public int updateTowerMobileBillState(List<TowerBillbalanceVO> billConfirmList) {
		return this.getSqlSession().update(TowerMobilerBillbalanceConfirmVOMapper+"updateTowerMobileBillState",billConfirmList);
	}
	@Override
	public int updateCancleConfirmState(Map<String, Object> paraMap) {
		return this.getSqlSession().update(TowerMobilerBillbalanceConfirmVOMapper+"updateCancleConfirmState",paraMap);
	}
	@Override
	public List<TowerAndMobileBillVO> queryAllTowerAndMobileFee(
			Map<String, Object> map) {
		return this.getSqlSession().selectList(TowerMobilerBillbalanceVOMapper+"queryTowerAndMobileFee",map);
	}
	@Override
	public List<TowerAndMobileBillConfirmVO> queryTowerAndMobileConfirmBill(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(TowerMobilerBillbalanceConfirmVOMapper+"queryTowerAndMobileConfirmBill",paraMap);
	}
	@Override
	public Page<List<TowerAndMobileBillConfirmVO>> queryTowerAndMobileConfirmBalance(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(TowerMobilerBillbalanceConfirmVOMapper+"queryTowerAndMobileConfirmBill",paraMap);
		return PageInterceptor.endPage();
	}
	@Override
	public Page<List<TowerBillbalanceVO>> queryTowerBillbalanceByIds(
			Map<String, Object> map) {
		PageInterceptor.startPage(Integer.parseInt(map.get("pageNumber").toString()),Integer.parseInt(map.get("pageSize").toString()));
		this.getSqlSession().selectList(NameSpace+"queryTowerBillbalanceByIds",map);
		return PageInterceptor.endPage();
	}
	@Override
	public int updateTowerMobileBillConfirmState(Map<String, Object> paraMap) {
		return this.getSqlSession().update(TowerMobilerBillbalanceConfirmVOMapper+"updateTowerMobileBillConfirmState", paraMap);
	}
}
