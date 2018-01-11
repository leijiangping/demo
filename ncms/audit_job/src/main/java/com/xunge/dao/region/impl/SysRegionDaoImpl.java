package com.xunge.dao.region.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.region.ISysRegionDao;

public class SysRegionDaoImpl extends AbstractBaseDao implements ISysRegionDao{
	
	final String Namespace="com.xunge.dao.system.sysRegionDaoImpl.";

	@Override
	public String getPrvIdByCode(String prvcode) {
		return this.getSqlSession().selectOne(Namespace+"getPrvIdByCode", prvcode);
	}

	@Override
	public String getRegIdByCode(Map<String,String> paramMap) {
		return this.getSqlSession().selectOne(Namespace+"getRegIdByCode", paramMap);
	}

	@Override
	public List<Map<String, String>> getRegionsByPrvid(Map<String, String> paramMap) {
		return this.getSqlSession().selectList(Namespace+"getRegionsByPrvid", paramMap);
	}
	
}