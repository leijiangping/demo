package com.xunge.dao.twrrent.settlement.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.settlement.ITowerBillbalanceDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.settlement.TowerBillbalanceVO;

/**
 * @author zhujj
 * @date 2017年7月6日 下午2:07:49 
 * @version 1.0.0 
 */
public class TowerBillbalanceDaoImpl extends AbstractBaseDao implements ITowerBillbalanceDao {

	final String TowerBillbalanceNamespace = "com.xunge.mapping.TowerBillbalanceVOMapper.";
	@Override
	public int deleteByPrimaryKey(String towerbillbalanceId) {
		// TODO Auto-generated method stub
		return  this.getSqlSession().update(TowerBillbalanceNamespace+"deleteByPrimaryKey",towerbillbalanceId);
	}

	@Override
	public int insertTowerBillbalance(TowerBillbalanceVO entity) {
		// TODO Auto-generated method stub
		return  this.getSqlSession().insert(TowerBillbalanceNamespace+"insertTowerBillbalance",entity);
	}

	@Override
	public int insertSelective(TowerBillbalanceVO entity) {
		// TODO Auto-generated method stub
		return  this.getSqlSession().insert(TowerBillbalanceNamespace+"insertSelective",entity);
	}

	@Override
	public TowerBillbalanceVO selectByPrimaryKey(String towerbillbalanceId) {
		// TODO Auto-generated method stub
		return  this.getSqlSession().selectOne(TowerBillbalanceNamespace+"selectByPrimaryKey",towerbillbalanceId);
	}

	@Override
	public int updateByPrimaryKeySelective(TowerBillbalanceVO entity) {
		// TODO Auto-generated method stub
		return  this.getSqlSession().update(TowerBillbalanceNamespace+"updateByPrimaryKeySelective",entity);
	}

	@Override
	public int updateByPrimaryKey(TowerBillbalanceVO entity) {
		// TODO Auto-generated method stub
		return  this.getSqlSession().update(TowerBillbalanceNamespace+"updateByPrimaryKey",entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<TowerBillbalanceVO> queryPageTowerBillbalance(Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageInterceptor.startPage(Integer.parseInt(map.get("pageNumber").toString()),Integer.parseInt(map.get("pageSize").toString()));
		this.getSqlSession().selectList(TowerBillbalanceNamespace+"queryPageTowerBillbalance",map);
		return PageInterceptor.endPage();  
	}
	public List<TowerBillbalanceVO> queryTowerBillbalance(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(TowerBillbalanceNamespace+"queryPageTowerBillbalance",map);
	}
	
	@Override
	public int insertBatchSelective(List<TowerBillbalanceVO> list) {
		// TODO Auto-generated method stub
		return  this.getSqlSession().insert(TowerBillbalanceNamespace+"insertBatchSelective",list);
	}

	@Override
	public int updateBatchByPrimaryKeySelective(List<TowerBillbalanceVO> list) {
		// TODO Auto-generated method stub
		return  this.getSqlSession().update(TowerBillbalanceNamespace+"updateBatchByPrimaryKey",list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<TowerBillbalanceVO> queryPageMobileBillbalance(Map<String, Object> paramMap) {
		PageInterceptor.startPage(Integer.parseInt(paramMap.get("pageNumber").toString()),Integer.parseInt(paramMap.get("pageSize").toString()));
		this.getSqlSession().selectList(TowerBillbalanceNamespace+"queryPageMobileBillbalance",paramMap);
		return PageInterceptor.endPage();
	}

	@Override
	public List<TowerBillbalanceVO> queryTowerOrMobileBillbalance(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(TowerBillbalanceNamespace+"queryPageMobileBillbalance",paramMap);
	}

	@Override
	public int insertBatchMobileBill(List<TowerBillbalanceVO> listVo) {
		return this.getSqlSession().insert(TowerBillbalanceNamespace + "insertBatchMobileBill", listVo);
	}
	
	@Override
	public int updateBatchMobileBill(List<TowerBillbalanceVO> listVo) {
		return this.getSqlSession().update(TowerBillbalanceNamespace + "updateBatchMobileBill", listVo);
	}
	
	@Override
	public List<TowerBillbalanceVO> queryMobileBill(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(TowerBillbalanceNamespace+"queryMobielBill",paramMap);
	}

	@Override
	public String queryBalance(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(TowerBillbalanceNamespace+"queryBalance",paraMap);
	}

	@Override
	public List<TowerBillbalanceVO> queryParameter(Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(TowerBillbalanceNamespace+"queryParameter",paraMap);
	}
}
