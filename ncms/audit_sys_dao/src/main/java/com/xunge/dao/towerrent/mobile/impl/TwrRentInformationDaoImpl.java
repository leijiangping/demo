package com.xunge.dao.towerrent.mobile.impl;

import java.util.List;
import java.util.Map;

import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.towerrent.mobile.ITwrRentInformationDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.mobile.TwrRentInformationVO;
import com.xunge.model.towerrent.rentinformation.TwrRentInformationChangeVO;
import com.xunge.model.towerrent.rentmanager.TowerRentInformationHistoryVO;
import com.xunge.model.towerrent.rentmanager.TowerResourceInfoVO;

public class TwrRentInformationDaoImpl extends AbstractBaseDao implements ITwrRentInformationDao{
	final String Namespace="com.xunge.dao.TwrRentInformationVOMapper.";
	final String NapmspaceHistory = "com.xunge.dao.TowerRentInformationHistoryVOMapper.";
	final String NapmspaceChange = "com.xunge.dao.towerrent.rentinformation.TwrRentInformationChangeVODao.";

	@SuppressWarnings("unchecked")
	@Override
	public Page<TwrRentInformationVO> queryTwrRentInformation(Map<String, Object> map,int pageNum,int pageSize) {
		PageInterceptor.startPage(pageNum, pageSize);
		this.getSqlSession().selectList(Namespace+"queryTwrRentInformation",map);
		return PageInterceptor.endPage();	
	}
	@Override
	public List<TwrRentInformationVO> queryTwrRentInformationList(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(Namespace+"queryTwrRentInformation",map);
	}
	@Override
	public String queryTypeIdByConfigId(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryTypeIdByConfigId",paraMap);
	}
	
	@Override
	public int insertNoTowerConfig(Map<String, Object> paraMap) {
		return this.getSqlSession().insert(Namespace+"insertNoTowerConfig",paraMap);
	}

	@Override
	public String queryProConfigIdById(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryProConfigIdById",paraMap);
	}

	@Override
	public int updateTwrRentInformationByBizChange(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateTwrRentInformationByBizChange",paraMap);
	}
	
	@Override
	public int updateRentinformationHistory(Map<String, Object> map) {
		return this.getSqlSession().update(NapmspaceHistory+"updaterentinformationhistory",map);
	}
	
	@Override
	public int updateRentinformationChange(Map<String, Object> map) {
		return this.getSqlSession().update(NapmspaceChange+"updaterentinformationchange",map);
	}
	
	@Override
	public List<TowerResourceInfoVO> queryTwrRentInformation( 
			Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryTowerResourceInfo",map);
	}
	
	@Override
	public String updateTwrRentInformationTemp(Map<String,Object> paramMap) {
		int result = this.getSqlSession().update(NapmspaceChange+"updateTempTwrRentInfo",paramMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	@Override
	public Page<List<TowerRentInformationHistoryVO>> queryTwrInfoHistoryVO(String twrRentInfoId,int pageNum,int pageSize) {
		PageInterceptor.startPage(pageNum, pageSize);
		this.getSqlSession().selectList(NapmspaceHistory+"selectByTwrInfoId",twrRentInfoId);
		return PageInterceptor.endPage();	
	}

	@Override
	public String updateTwrInfoHistoryVO(TowerRentInformationHistoryVO historyVO) {
		int result = this.getSqlSession().update(NapmspaceHistory+"updateByPrimaryKeySelective",historyVO);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	@Override
	public String insertTwrInfoHistoryVO(TowerRentInformationHistoryVO historyVO) {
		int result = this.getSqlSession().insert(NapmspaceHistory+"insert",historyVO);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	@Override
	public String queryTwrRentInformationByTower(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryTwrRentInformationByTower",paraMap);
	}

	@Override
	public TowerRentInformationHistoryVO queryById(String id) {
		return this.getSqlSession().selectOne(NapmspaceHistory+"selectByPrimaryKey", id);
	}

	@Override
	public int updateTwrRentInformation(Map<String,Object> map) {
		return this.getSqlSession().update(Namespace+"updateTwrRentInformation", map);
	}

	@Override
	public int deleteTowerRentInformationHistoryVO(Map<String, Object> paraMap) {
		return this.getSqlSession().delete(NapmspaceHistory+"deleteinformationhistory", paraMap);
	}

	@Override
	public int updateTwrInfoHistory(Map<String, Object> paraMap) {
		return this.getSqlSession().update(NapmspaceHistory+"updateInitialization", paraMap);
	}

	@Override
	public String queryByRentinformationId(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(NapmspaceHistory+"queryByRentinformationId",paraMap);
	}

	@Override
	public int insertTwrRentInformationFromTwrRentInformationTower(
			Map<String, Object> paraMap) {
		return this.getSqlSession().insert(Namespace+"insertTwrRentInformationFromTwrRentInformationTower",paraMap);
	}

	@Override
	public int updateTwrRentInformationFromTwrRentInformationTower(
			Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateTwrRentInformationFromTwrRentInformationTower",paraMap);
	}

	@Override
	public TwrRentInformationVO queryTime(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"selectTime",paraMap);
	}

	@Override
	public List<TwrRentInformationVO> queryExportTwrRentInformation(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryExportTwrRentInformation",paraMap);
	}
	
	@Override
	public List<TowerRentInformationHistoryVO> queryExportTwrRentInformationHistory(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(NapmspaceHistory+"exportByTwrInfoHistoryId",paraMap);
	}
	
	@Override
	public List<TwrRentInformationChangeVO> queryTwrRentInformationChange(Map<String,Object> paraMap){
		return this.getSqlSession().selectList(NapmspaceChange+"queryTwrRentInformationChange", paraMap);
	}
	
	

	/**
	 * 根据业务确认单号 铁塔站址编码  生效日期判断是否在服务有效期查找TwrRentInformation
	 * @param paraMap
	 * @author yuefy
	 * @return
	 */
	@Override
	public TwrRentInformationVO queryTwrRentInformationByBusinessNumDate(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryTwrRentInformationBybusinessConfirmNumber",paraMap);
	}


	@Override
	public List<TowerRentInformationHistoryVO> queryTowerRentInformationHistoryByConditions(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(NapmspaceHistory+"queryTowerRentInformationHistoryByConditions",paraMap);
	}
	

	@Override
	public int updateEndDateByStopServer(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateEndDateByStopServer", paraMap);
	}


	@Override
	public List<TwrRentInformationVO> queryMsgByTwrStaCode(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryMsgByTwrStaCode",paraMap);
	}
}
