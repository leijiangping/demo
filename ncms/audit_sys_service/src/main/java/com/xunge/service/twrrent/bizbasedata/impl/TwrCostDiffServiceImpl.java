package com.xunge.service.twrrent.bizbasedata.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.twrrent.bizbasedata.ITwrChargeDiffDao;
import com.xunge.model.towerrent.bizbasedata.CostDifferVO;
import com.xunge.service.twrrent.bizbasedata.ITwrCostDiffService;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 费用差异范围
 */
public class TwrCostDiffServiceImpl implements ITwrCostDiffService{

	private ITwrChargeDiffDao chargeDao;
	
	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<CostDifferVO>> queryAllObj(int pageSize, int pageNumber,Map<String,Object> paramMap) {
		return chargeDao.queryAllObj(pageSize, pageNumber,paramMap);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String deleteObjById(List<String> ids) {
		return chargeDao.deleteObjById(ids);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateObjById(CostDifferVO costDiffVo) {
		return chargeDao.updateObjById(costDiffVo);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertObjById(CostDifferVO costDiffVo) {
		return chargeDao.insertObjById(costDiffVo);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<CostDifferVO>> queryObjByCondition(
			Map<String, Object> paramMap, int pageSize, int pageNumber) {
		return chargeDao.queryObjByCondition(paramMap, pageSize, pageNumber);
	}

	public ITwrChargeDiffDao getChargeDao() {
		return chargeDao;
	}

	public void setChargeDao(ITwrChargeDiffDao chargeDao) {
		this.chargeDao = chargeDao;
	}

}
