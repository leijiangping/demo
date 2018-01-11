package com.xunge.service.towerrent.rentinformationhistory.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xunge.comm.StateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.page.Page;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.dao.towerrent.rentinformationhistory.ITwrRentInformationHistoryDao;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.rentmanager.TowerRentInformationHistoryVO;
import com.xunge.service.towerrent.rentinformationhistory.ITwrRentInformationHistoryService;

public class TwrRentInformationHistoryServiceImpl implements ITwrRentInformationHistoryService{

	private ITwrRentInformationHistoryDao twrRentInformationHistoryDao;
	
	private ISysRegionDao sysRegionDao;
	
	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}

	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}

	@Override
	public String insertSelective(
			TowerRentInformationHistoryVO towerRentInformationHistoryVO) {
		try {
			twrRentInformationHistoryDao
					.insertSelective(towerRentInformationHistoryVO);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}
	
	@Override
	public String updateEndDateById(Date endDate,
			String rentinformationhistoryId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("endDate",endDate);
		paraMap.put("rentinformationhistoryId",rentinformationhistoryId);
		try {
			twrRentInformationHistoryDao.updateEndDateById(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}
	
	@Override
	public List<TowerRentInformationHistoryVO> queryAllHistoryById(
			String rentinformationId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("rentinformationId",rentinformationId);
 		return twrRentInformationHistoryDao.queryAllHistoryById(paraMap);
	}

	@Override
	public String updateChangeItemById(String changeItem,
			String rentinformationhistoryId, String changeBehindContent) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("changeItem",changeItem);
		paraMap.put("rentinformationhistoryId",rentinformationhistoryId);
		paraMap.put("changeBehindContent",changeBehindContent);
		try {
			twrRentInformationHistoryDao.updateChangeItemById(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}
	
	@Override
	public String insertRentInformationHistoryFromMobile(
			String rentinformationhistoryId, String rentinformationId) {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("rentinformationhistoryId",rentinformationhistoryId);
		paraMap.put("rentinformationId",rentinformationId);
		try {
			twrRentInformationHistoryDao.insertRentInformationHistoryFromMobile(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}

	public ITwrRentInformationHistoryDao getTwrRentInformationHistoryDao() {
		return twrRentInformationHistoryDao;
	}

	public void setTwrRentInformationHistoryDao(
			ITwrRentInformationHistoryDao twrRentInformationHistoryDao) {
		this.twrRentInformationHistoryDao = twrRentInformationHistoryDao;
	}

	@Override
	public List<TowerRentInformationHistoryVO> queryTowerRentInfoHistory(
			Map<String, Object> paraMap) {
		return twrRentInformationHistoryDao.queryTowerRentInfoHistory(paraMap);
	}

	@Override
	public Page<TowerRentInformationHistoryVO> queryPageRentInfoHistory(Map<String, Object> paraMap) {
		Map<String,Object> map=Maps.newHashMap();
		map.put("state",StateComm.STATE_str0);
		map.put("userId",paraMap.get("userId").toString());
		List<SysRegionVO> listreg = sysRegionDao.getAddress(map);//准备需要的数据处理
		List<TowerRentInformationHistoryVO> list = Lists.newArrayList();
		Page<TowerRentInformationHistoryVO> page = twrRentInformationHistoryDao.queryPageRentInfoHistory(paraMap);
		for(TowerRentInformationHistoryVO tbb:page.getResult()){
			for(SysRegionVO sreg: listreg){
				if(sreg.getRegId().equals(tbb.getOperatorRegId())){
					tbb.setOperatorSysRegion(sreg);
				}
			}
			list.add(tbb);
		}
		page.setResult(list);
		return page;
	}

	@Override
	public String deleteHistoryByDate(String rentInformationId, Date endDate) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("rentInformationId",rentInformationId);
		paraMap.put("endDate",endDate);
		try {
			twrRentInformationHistoryDao.deleteHistoryByDate(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
		
	}
}
