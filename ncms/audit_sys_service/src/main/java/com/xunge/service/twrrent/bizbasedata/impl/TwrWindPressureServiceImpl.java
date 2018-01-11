package com.xunge.service.twrrent.bizbasedata.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.twrrent.bizbasedata.ITwrWindPressureDao;
import com.xunge.model.towerrent.bizbasedata.WindPressureVO;
import com.xunge.service.twrrent.bizbasedata.ITwrWindPressureService;


/**
 * @author jcy
 * @date 2017年7月6日
 * @description风压范围
 */
public class TwrWindPressureServiceImpl implements ITwrWindPressureService{

	private ITwrWindPressureDao twrWindPressureDao;

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<WindPressureVO>> queryAllWinPressVO(int pageSize,
			int pageNumber) {
		return twrWindPressureDao.queryAllWinPressVO(pageSize, pageNumber);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String deleteWinPressById(List<String> ids) {
		return twrWindPressureDao.deleteWinPressById(ids);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateWinPressById(WindPressureVO pressureVo) {
		return twrWindPressureDao.updateWinPressById(pressureVo);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertWinPressById(WindPressureVO windPressVo) {
		return twrWindPressureDao.insertWinPressById(windPressVo);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateStartOrStopWinPressById(List<String> ids,
			String winPressState) {
		return twrWindPressureDao.startOrStopWinPressById(ids, winPressState);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<WindPressureVO>> queryWinPressVOByStateAndName(
			Map<String, Object> paramMap, int pageSize, int pageNumber) {
		return twrWindPressureDao.queryWinPressVOByStateAndName(paramMap, pageSize, pageNumber);
	}
	
	public void setTwrWindPressureDao(ITwrWindPressureDao twrWindPressureDao) {
		this.twrWindPressureDao = twrWindPressureDao;
	}

}
