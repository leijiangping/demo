package com.xunge.dao.datacollect.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.datacollect.IGrpDatacollecttypeDao;
import com.xunge.model.datacollect.GrpDatacollecttypeVO;

public class GrpDatacollecttypeDaoImpl extends AbstractBaseDao implements IGrpDatacollecttypeDao {
	
	final String Namespace = "com.xunge.dao.GrpDatacollecttypeVOMapper.";

	@Override
	public int deleteByDCId(String datacollectId) {
		return this.getSqlSession().delete(Namespace+"deleteByDCId",datacollectId);
	}

	@Override
	public int updateTypeById(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateTypeById",paraMap);
	}

	@Override
	public int deleteIdById(String datacollectId) {
		return this.getSqlSession().delete(Namespace+"deleteIdById",datacollectId);
	}

	@Override
	public List<GrpDatacollecttypeVO> queryByGrpCollId(String datacollectId) {
		return this.getSqlSession().selectList(Namespace+"selectByGrpCollId",datacollectId);
	}

	@Override
	public int updateByPrimaryKeySelective(
			GrpDatacollecttypeVO grpDatacollecttypeVO) {
		return this.getSqlSession().update(Namespace+"updateByPrimaryKeySelective",grpDatacollecttypeVO);
	}
	
	@Override
	public int insertSelective(GrpDatacollecttypeVO grpDatacollecttypeVO) {
		return this.getSqlSession().insert(Namespace+"insertSelective",grpDatacollecttypeVO);
	}

	@Override
	public int deleteUserLessMsg() {
		return this.getSqlSession().delete(Namespace+"deleteUserLessMsg");
	}

	@Override
	public int updateFiles(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateFiles",paraMap);
	}

	@Override
	public int deleteByPrimaryKey(String datacollecttypeId) {
		return this.getSqlSession().delete(Namespace+"deleteByPrimaryKey",datacollecttypeId);
	}

	@Override
	public List<String> queryTypeBeUsed() {
		return this.getSqlSession().selectList(Namespace+"queryTypeBeUsed");
	}

	@Override
	public List<String> queryTypeBeUsedById(String datacollectId) {
		return this.getSqlSession().selectList(Namespace+"queryTypeBeUsedById",datacollectId);
	}

	@Override
	public String queryTempTypeByTypeId(Map<String, Object> map) {
		return this.getSqlSession().selectOne(Namespace+"queryTempTypeByTypeId",map);
	}
}
