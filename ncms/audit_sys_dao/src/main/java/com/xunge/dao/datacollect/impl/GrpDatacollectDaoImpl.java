package com.xunge.dao.datacollect.impl;

import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.datacollect.IGrpDatacollectDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.datacollect.GrpDatacollectVO;

public class GrpDatacollectDaoImpl extends AbstractBaseDao implements IGrpDatacollectDao {

	final String Namespace = "com.xunge.dao.GrpDatacollectVOMapper.";
	
	@Override
	public int deleteByPrimaryKey(String datacollectId) {
		return this.getSqlSession().delete(Namespace+"deleteByPrimaryKey",datacollectId);
	}

	@Override
	public Page<GrpDatacollectVO> queryGrpDataCollectVO(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		PageInterceptor.startPage(pageNumber,pageSize);
		this.getSqlSession().selectList(Namespace+"queryGrpDataCollectVO",paraMap);
		return PageInterceptor.endPage();
	}

	@Override
	public int insertSelective(GrpDatacollectVO grpDatacollectVO) {
		return this.getSqlSession().insert(Namespace+"insertSelective",grpDatacollectVO);
	}

	@Override
	public int updateByPrimaryKeySelective(GrpDatacollectVO grpDatacollectVO) {
		return this.getSqlSession().update(Namespace+"updateByPrimaryKeySelective",grpDatacollectVO);
	}

	@Override
	public GrpDatacollectVO queryGrpDataCollectById(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryGrpDataCollectById",paraMap);
	}

	@Override
	public int updateStateById(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateStateById",paraMap);
	}

	@Override
	public String querySameThing(String datacollectTitle) {
		return this.getSqlSession().selectOne(Namespace+"querySameThing",datacollectTitle);
	}

	@Override
	public GrpDatacollectVO queryTitleById(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryTitleById",paraMap);
	}

	@Override
	public String queryCopyUserById(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryCopyUserById",paraMap);
	}

	@Override
	public int updateDatacollectToFinish(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateDatacollectToFinish",paraMap);
	}

	@Override
	public String querySendDateByPrvId(Map<String, Object> map) {
		return this.getSqlSession().selectOne(Namespace+"querySendDateByPrvId",map);
	}
}
