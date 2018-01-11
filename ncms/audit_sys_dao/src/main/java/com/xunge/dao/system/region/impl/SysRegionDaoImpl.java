package com.xunge.dao.system.region.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.model.system.province.SysProvinceGroupVO;
import com.xunge.model.system.province.SysProvinceVO;
import com.xunge.model.system.region.RegionVO;
import com.xunge.model.system.region.SysRegionVO;


public class SysRegionDaoImpl extends AbstractBaseDao implements ISysRegionDao{
	
	final String Namespace="com.xunge.dao.system.SysRegionVOMapper.";

	@Override
	public List<SysRegionVO> selectByConditions(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryByCons", map);
	}
	public List<SysProvinceGroupVO> selectByPrvGroup(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"selectByPrvGroup", map);
	}
	public List<SysProvinceVO> selectProvinceByIds(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"selectProvinceByIds", map);
	}

	@Override
	public List<SysRegionVO> queryRegionByConditions(
			Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(Namespace+"queryRegionByConditions", paramMap);
	}

	@Override
	public int delRegion(Map<String, Object> paramMap) {
		
		return this.getSqlSession().update(Namespace+"deleteByCons", paramMap);
	}

	@Override
	public int addRegion(SysRegionVO sysRegionVO) {
		return this.getSqlSession().update(Namespace+"insertRegion", sysRegionVO);
	}

	@Override
	public int updateRegion(SysRegionVO sysRegionVO, String regId) {
		return this.getSqlSession().update(Namespace+"updateRegion", sysRegionVO);
	}

	@Override
	public SysRegionVO getRegionById(String regId) {
		return this.getSqlSession().selectOne(Namespace+"getRegionById", regId);
	}

	@Override
	public String getPrvNameById(String regId) {
		return this.getSqlSession().selectOne(Namespace+"getPrvNameById", regId);
	}

	@Override
	public String getPrvIdByCode(String prvcode) {
		return this.getSqlSession().selectOne(Namespace+"getPrvIdByCode", prvcode);
	}

	@Override
	public String getRegIdByCode(String regcode) {
		return this.getSqlSession().selectOne(Namespace+"getRegIdByCode", regcode);
	}
	
	@Override
	public List<String> getRegIdByprvId(String prvId) {
		return this.getSqlSession().selectList(Namespace+"getRegIdByprvId", prvId);
	}
	
	/**
	 * 获取所有区域信息
	 */
	@Override
	public List<SysRegionVO> getAddress(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"getAddress",map);
	}
	
	/**
	 * 获取区域信息
	 */
	@Override
	public List<SysRegionVO> getUserAddress(Map map) {
		List<SysRegionVO> lstsysRegionVO = this.getSqlSession().selectList(Namespace+"getUserAddress",map);
		return lstsysRegionVO;
	}

	@Override
	public List<RegionVO> queryManaProvs(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryManaProvs",map);
	}

	@Override
	public List<RegionVO> queryManaRegions(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryManaRegions",map);
	}

	@Override
	public SysRegionVO getRegionIdByName(String regName) {
		return this.getSqlSession().selectOne(Namespace+"getRegionIdByName",regName);
	}
	@Override
	public List<String> queryPrvRegionIdsByPrvId(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryPrvRegionIdsByPrvId",map);
	}
	@Override
	public List<String> queryRegionIdsByPrvId(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryRegionIdsByPrvId",map);
	}
	@Override
	public List<String> queryPRegionIdsByPrvId(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryPRegionIdsByPrvId",map);
	}
}