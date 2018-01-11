package com.xunge.service.towerrent.rentinformation.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.towerrent.rentinformation.ITwrRentInformationChangeDao;
import com.xunge.model.towerrent.rentinformation.TwrRentInformationChangeVO;
import com.xunge.service.towerrent.rentinformation.ITwrRentInformationChangeService;

public class TwrRentInformationChangeServiceImpl implements
		ITwrRentInformationChangeService {

	private ITwrRentInformationChangeDao twrRentInformationChangeDao;
	
	public ITwrRentInformationChangeDao getTwrRentInformationChangeDao() {
		return twrRentInformationChangeDao;
	}

	public void setTwrRentInformationChangeDao(
			ITwrRentInformationChangeDao twrRentInformationChangeDao) {
		this.twrRentInformationChangeDao = twrRentInformationChangeDao;
	}

	@Override
	public Page<List<Map<String, Object>>> queryTwrRentInformationChangePageByCondtion(
			Map<String, Object> paramMap, int pageNumber, int pageSize) {
		paramMap.put("pageNumber", pageNumber);
		paramMap.put("pageSize", pageSize);
		return twrRentInformationChangeDao.queryTwrRentInformationChangePage(paramMap);
	}

	@Override
	public Page<List<TwrRentInformationChangeVO>> queryTwrRentInformationChangeVoPageByCondtion(
			Map<String, Object> paramMap, int pageNumber, int pageSize) {
		paramMap.put("pageNumber", pageNumber);
		paramMap.put("pageSize", pageSize);
		return twrRentInformationChangeDao.queryTwrRentInformationChangeVoPage(paramMap);
	}

	@Override
	public List<TwrRentInformationChangeVO> queryTwrRentInformationChangeVoListByCondtion(
			Map<String, Object> paraMap) {
		return twrRentInformationChangeDao.queryTwrRentInformationChangeVoListByCondition(paraMap);
	}

}
