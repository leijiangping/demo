package com.xunge.service.twrrent.bizbasedata.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.twrrent.bizbasedata.ITwrHighUpDao;
import com.xunge.model.towerrent.bizbasedata.HighUpVO;
import com.xunge.service.twrrent.bizbasedata.ITwrHighUpService;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 挂高范围
 */
public class TwrHighUpServiceImpl implements ITwrHighUpService{

	private ITwrHighUpDao twrHighUpDao;

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<HighUpVO>> queryAllHighUpVO(int pageSize, int pageNumber) {
		return twrHighUpDao.queryAllHighUpVO(pageSize, pageNumber);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String deleteHighUpById(List<String> ids) {
		return twrHighUpDao.deleteHighUpById(ids);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateHighUpById(HighUpVO highUpVo) {
		return twrHighUpDao.updateHighUpById(highUpVo);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertHighUpById(HighUpVO highUpVo) {
		return twrHighUpDao.insertHighUpById(highUpVo);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateStartOrStopHighUpById(List<String> ids,
			String highUpState) {
		return twrHighUpDao.startOrStopHighUpById(ids, highUpState);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<HighUpVO>> queryHighUpVOByStateAndName(
			Map<String, Object> paramMap, int pageSize, int pageNumber) {
		return twrHighUpDao.queryHighUpVOByStateAndName(paramMap, pageSize, pageNumber);
	}

	public ITwrHighUpDao getTwrHighUpDao() {
		return twrHighUpDao;
	}

	public void setTwrHighUpDao(ITwrHighUpDao twrHighUpDao) {
		this.twrHighUpDao = twrHighUpDao;
	}
	

}
