package com.xunge.service.twrrent.bizbasedata.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.twrrent.bizbasedata.ITwrProductCostDao;
import com.xunge.model.towerrent.bizbasedata.CommTypeVO;
import com.xunge.model.towerrent.bizbasedata.HighUpVO;
import com.xunge.model.towerrent.bizbasedata.ProductCostVO;
import com.xunge.model.towerrent.bizbasedata.WindPressureVO;
import com.xunge.service.twrrent.bizbasedata.ITwrProductCostService;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 建造成本库
 */
public class TwrProductCostServiceImpl implements ITwrProductCostService{

	ITwrProductCostDao proCostDao;
	
	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<ProductCostVO>> queryAllProCostVO(int pageSize,
			int pageNumber) {
		return proCostDao.queryAllProCostVO(pageSize, pageNumber);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String deleteProCostById(List<String> ids) {
		return proCostDao.deleteProCostById(ids);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateProCostById(ProductCostVO proCostVO) {
		return proCostDao.updateProCostById(proCostVO);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertProCostById(ProductCostVO proCostVO) {
		return proCostDao.insertProCostById(proCostVO);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String startOrStopProCostById(List<String> ids, String proCostState) {
		return proCostDao.startOrStopProCostById(ids, proCostState);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<ProductCostVO>> queryProCostVOByStateAndName(
			Map<String, Object> paramMap, int pageSize, int pageNumber) {
		return proCostDao.queryProCostVOByStateAndName(paramMap, pageSize, pageNumber);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public List<String> queryRoomList() {
		return proCostDao.queryRoomList();
	}

	/**
	 * @author jiacy
	 */
	@Override
	public List<String> querySupptList() {
		return proCostDao.querySupptList();
	}

	/**
	 * @author jiacy
	 */
	@Override
	public List<String> queryTwrList() {
		return proCostDao.queryTwrList();
	}

	public ITwrProductCostDao getProCostDao() {
		return proCostDao;
	}

	public void setProCostDao(ITwrProductCostDao proCostDao) {
		this.proCostDao = proCostDao;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public List<ProductCostVO> querytwrTypeVO() {
		return proCostDao.querytwrTypeVO();
	}

	/**
	 * @author jiacy
	 */
	@Override
	public List<WindPressureVO> queryWindPress() {
		return proCostDao.queryWindPress();
	}

	/**
	 * @author jiacy
	 */
	@Override
	public List<HighUpVO> queryHighUpVO() {
		return proCostDao.queryHighUpVO();
	}

	/**
	 * @author jiacy
	 */
	@Override
	public List<CommTypeVO> queryRoomAndSuptType(Map<String,Object> paramMap) {
		return proCostDao.queryRoomAndSuptType(paramMap);
	}

}
