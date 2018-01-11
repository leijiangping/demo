package com.xunge.service.towerrent.mobile.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.xunge.comm.StateComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.page.Page;
import com.xunge.dao.towerrent.mobile.ITwrRentInformationDao;
import com.xunge.model.activity.Act;
import com.xunge.model.selfrent.rebursepoint.RentBillaccountVO;
import com.xunge.model.towerrent.mobile.TwrRentInformationVO;
import com.xunge.model.towerrent.rentinformation.TwrRentInformationChangeVO;
import com.xunge.model.towerrent.rentmanager.TowerRentInformationHistoryVO;
import com.xunge.service.towerrent.mobile.ITwrRentInformationService;

public class TwrRentInformationServiceImpl implements ITwrRentInformationService{
	
	private ITwrRentInformationDao twrRentInformationDao;

	@Override
	public Page<TwrRentInformationVO> queryTwrRentInformation(Map<String, Object> paraMap,int pageNum,int pageSize) {
		return twrRentInformationDao.queryTwrRentInformation(paraMap, pageNum, pageSize);
	}
	@Override
	public List<TwrRentInformationVO> queryTwrRentInformationList(
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		return twrRentInformationDao.queryTwrRentInformationList(map);
	}
	@Override
	public String queryTypeIdByConfigId(String productconfigId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("productconfigId",productconfigId);
		return twrRentInformationDao.queryTypeIdByConfigId(paraMap);
	}
	
	@Override
	public String insertNoTowerConfig(String nottowerconfigId,
			String productTypeId,String towerStationCode,String businessConfirmNumber) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("nottowerconfigId",nottowerconfigId);
		paraMap.put("productTypeId",productTypeId);
		paraMap.put("towerStationCode",towerStationCode);
		paraMap.put("businessConfirmNumber",businessConfirmNumber);
		try {
			twrRentInformationDao.insertNoTowerConfig(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}
	
	@Override
	public String queryProConfigIdById(String rentinformationId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("rentinformationId",rentinformationId);
		return twrRentInformationDao.queryProConfigIdById(paraMap);
	}
	
	@Override
	public String updateTwrRentInformationByBizChange(String changeItem,
			String changeContent, String rentinformationId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("changeItem",changeItem);
		paraMap.put("changeContent",changeContent);
		paraMap.put("rentinformationId",rentinformationId);
		try {
			twrRentInformationDao.updateTwrRentInformationByBizChange(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}

	@Override
	public String updateTwrRentInformationFromTwrRentInformationTower(
			String rentinformationId, String rentinformationtowerId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("rentinformationId",rentinformationId);
		paraMap.put("rentinformationtowerId",rentinformationtowerId);
		try {
			twrRentInformationDao.updateTwrRentInformationFromTwrRentInformationTower(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}
	
	@Override
	public int updateCommit(String id,Date endDate) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("rentinformationId", id);
		maps.put("state", ActivityStateComm.STATE_AUDIT);
		maps.put("endDate", endDate);
		try {
			String rentinformationhistoryId=twrRentInformationDao.queryByRentinformationId(maps);
			Map<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("rentinformationhistoryId", rentinformationhistoryId);
			hashMap.put("state", ActivityStateComm.STATE_AUDIT);
			hashMap.put("checkState", ActivityStateComm.STATE_NORMAL);
			twrRentInformationDao.updateRentinformationChange(hashMap);
			twrRentInformationDao.updateRentinformationHistory(maps);
			return StateComm.STATE_1;
		} catch (Exception e) {
			return StateComm.STATE_0;
		}
	}
	
	@Override
	public String insertTwrRentInformationFromTwrRentInformationTower(
			String rentinformationId,String rentinformationtowerId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("rentinformationId",rentinformationId);
		paraMap.put("rentinformationtowerId",rentinformationtowerId);
		try {
			twrRentInformationDao.insertTwrRentInformationFromTwrRentInformationTower(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}

	@Override
	public String updateTwrRentInformationTemp(Map<String,Object> paramMap) {
		return twrRentInformationDao.updateTwrRentInformationTemp(paramMap);
	}

	@Override
	public Page<List<TowerRentInformationHistoryVO>> queryTwrInfoHistoryVO(String twrRentInfoId,int pageNum,int pageSize) {
		return twrRentInformationDao.queryTwrInfoHistoryVO(twrRentInfoId,pageNum,pageSize);
	}

	@Override
	public String updateTwrInfoHistoryVO(TowerRentInformationHistoryVO historyVO) {
		return twrRentInformationDao.updateTwrInfoHistoryVO(historyVO);
	}

	@Override
	public String insertTwrInfoHistoryVO(TowerRentInformationHistoryVO historyVO) {
		return twrRentInformationDao.insertTwrInfoHistoryVO(historyVO);
	}

	@Override
	public String queryTwrRentInformationByTower(String businessConfirmNumber,
			String towerStationCode) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("businessConfirmNumber",businessConfirmNumber);
		paraMap.put("towerStationCode",towerStationCode);
		return twrRentInformationDao.queryTwrRentInformationByTower(paraMap);
	}
	
	public ITwrRentInformationDao getTwrRentInformationDao() {
		return twrRentInformationDao;
	}
	
	public void setTwrRentInformationDao(ITwrRentInformationDao twrRentInformationDao) {
		this.twrRentInformationDao = twrRentInformationDao;
	}

	@Override
	public int updateRentinformation(Map<String,Object> map) {
		return twrRentInformationDao.updateTwrRentInformation(map);
	}
	@Override
	public TowerRentInformationHistoryVO queryHistoryVOByid(
			String rentHistoryId) {
		return twrRentInformationDao.queryById(rentHistoryId);
	}


	@Override
	public TwrRentInformationVO queryTime(String rentinformationId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("rentinformationId",rentinformationId);
		return twrRentInformationDao.queryTime(paraMap);
	}
	@Override
	public int updateToTowerRentInformationHistoryVO(Map<String, Object> paraMap) {
		try {
			//删除新增拆分表数据
			twrRentInformationDao.deleteTowerRentInformationHistoryVO(paraMap);
			//初始化数据操作
			twrRentInformationDao.updateTwrInfoHistory(paraMap);
			return StateComm.STATE_1;
		} catch (Exception e) {
			return StateComm.STATE_0;
		}
		
	}


	@Override
	public int updateRentinformationChange(Map<String,Object> map) {
		String rentinformationhistoryId=twrRentInformationDao.queryByRentinformationId(map);
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("rentinformationhistoryId", rentinformationhistoryId);
		hashMap.put("checkState", ActivityStateComm.STATE_UNAPPROVE);
		return twrRentInformationDao.updateRentinformationChange(hashMap);
	}


	@Override
	public List<TwrRentInformationVO> queryExportTwrRentInformation(
			Map<String, Object> paraMap) {
		return twrRentInformationDao.queryExportTwrRentInformation(paraMap);
	}
	@Override
	public List<TwrRentInformationChangeVO> queryTwrRentInformationChange(
			Map<String, Object> paraMap) {
		String rentinformationhistoryId=twrRentInformationDao.queryByRentinformationId(paraMap);
		paraMap.put("rentinformationhistoryId", rentinformationhistoryId);
		return twrRentInformationDao.queryTwrRentInformationChange(paraMap);
	}

	@Override
	public List<TwrRentInformationChangeVO> queryTwrRentInformationChangeByHistoryId(
			Map<String, Object> paraMap) {
		return twrRentInformationDao.queryTwrRentInformationChange(paraMap);
	}


	@Override
	public List<TowerRentInformationHistoryVO> queryExportTwrRentInformationHistory(
			Map<String, Object> paraMap) {
		return twrRentInformationDao.queryExportTwrRentInformationHistory(paraMap);
	}


	@Override
	public List<TowerRentInformationHistoryVO> queryTowerRentInformationHistoryByConditions(
			Map<String, Object> paraMap) {
		return twrRentInformationDao.queryTowerRentInformationHistoryByConditions(paraMap);
	}


	@Override
	public String updateEndDateByStopServer(String rentinformationId,Date endDate) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("rentinformationId",rentinformationId);
		paraMap.put("endDate",endDate);
		try {
			twrRentInformationDao.updateEndDateByStopServer(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}
}
