package com.xunge.service.system.region.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xunge.dao.region.ISysRegionDao;
import com.xunge.service.system.region.ISysRegionService;

/**
 * 区县信息维护service实现类
 *
 */
@Service
public class SysRegionServiceImpl implements ISysRegionService{
	private ISysRegionDao sysRegionDao;
	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}
	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}
	
	@Override
	public String getPrvIdByCode(String prvcode) {
		return sysRegionDao.getPrvIdByCode(prvcode);
	}
	@Override
	public List<Map<String, String>> getRegionsByPrvid(Map<String, String> paramMap) {
		return sysRegionDao.getRegionsByPrvid(paramMap);
	}
	@Override
	public String getRegIdByCode(Map<String, String> paramMap) {
		return sysRegionDao.getRegIdByCode(paramMap);
	}

}
