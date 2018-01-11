package com.xunge.service.twrrent.bizbasedata.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.twrrent.bizbasedata.ITwrProductConfDao;
import com.xunge.model.towerrent.bizbasedata.ProductConfigVO;
import com.xunge.service.twrrent.bizbasedata.ITwrProductConfService;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 产品配置库
 */
public class TwrProductConfServiceImpl implements ITwrProductConfService{

	private ITwrProductConfDao proConfDao;
	
	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<ProductConfigVO>> queryAllObj(int pageSize, int pageNumber) {
		return proConfDao.queryAllObj(pageSize, pageNumber);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String deleteObjById(List<String> ids) {
		return proConfDao.deleteObjById(ids);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateObjById(ProductConfigVO proConfVo) {
		return proConfDao.updateObjById(proConfVo);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertObjById(ProductConfigVO proConfVo) {
		return proConfDao.insertObjById(proConfVo);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String startOrStopObjById(List<String> ids, String highUpState) {
		return proConfDao.startOrStopObjById(ids, highUpState);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<ProductConfigVO>> queryObjByStateAndName(
			Map<String, Object> paramMap, int pageSize, int pageNumber) {
		return proConfDao.queryObjByStateAndName(paramMap, pageSize, pageNumber);
	}

	public ITwrProductConfDao getProConfDao() {
		return proConfDao;
	}

	public void setProConfDao(ITwrProductConfDao proConfDao) {
		this.proConfDao = proConfDao;
	}

	/**
	 * @author zhaosf
	 */
	@Override
	public List<ProductConfigVO> queryObjByCondition(
			Map<String, Object> paramMap) {
		return proConfDao.queryObjByCondition(paramMap);
	}	

}
