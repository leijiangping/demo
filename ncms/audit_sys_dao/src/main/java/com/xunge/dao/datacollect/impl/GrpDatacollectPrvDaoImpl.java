package com.xunge.dao.datacollect.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.datacollect.IGrpDatacollectPrvDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.activity.Act;
import com.xunge.model.datacollect.GrpDatacollectPrvVO;

public class GrpDatacollectPrvDaoImpl extends AbstractBaseDao implements IGrpDatacollectPrvDao{

	final String Namespace = "com.xunge.dao.GrpDatacollectPrvVOMapper.";
	
	@Override
	public int deleteDataPrvById(String datacollectId) {
		return this.getSqlSession().delete(Namespace+"deleteDataPrvById",datacollectId);
	}

	@Override
	public int updateDataPrvById(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateDataPrvById",paraMap);
	}

	@Override
	public int insertSelective(GrpDatacollectPrvVO grpDatacollectPrvVO) {
		return this.getSqlSession().insert(Namespace+"insertSelective",grpDatacollectPrvVO);
	}

	@Override
	public Page<GrpDatacollectPrvVO> queryDataCollectPrvVO(
			Map<String, Object> paraMap,int pageNumber,int pageSize) {
		PageInterceptor.startPage(pageNumber,pageSize);
		this.getSqlSession().selectList(Namespace+"queryDataCollectPrvVO",paraMap);
		return PageInterceptor.endPage();
	}

	@Override
	public List<GrpDatacollectPrvVO> queryDownPrvIdByCollId(String datacollectId) {
		return this.getSqlSession().selectList(Namespace+"queryDownPrvIdByCollId",datacollectId);
	}

	@Override
	public List<String> queryPrvIdByUpCollId(Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryPrvIdByUpCollId",paraMap);
	}

	@Override
	public GrpDatacollectPrvVO queryPrvCollByPrvId(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryPrvCollByPrvId",paraMap);
	}

	@Override
	public int updatePrvFileAndNote(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updatePrvFileAndNote",paraMap);
	}

	@Override
	public GrpDatacollectPrvVO queryPrvSelfPathAndName(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryPrvSelfPathAndName",paraMap);
	}

	@Override
	public int updatePrvOtherFile(Map<String,Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updatePrvOtherFile",paraMap);
	}

	@Override
	public int updateStateReject(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateStateReject",paraMap);
	}

	@Override
	public int updatePrvStateToFinish(Map<String, Object> map) {
		return this.getSqlSession().update(Namespace+"updatePrvStateToFinish",map);
	}

	@Override
	public List<String> queryPrvIdBycollId(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryPrvIdBycollId",map);
	}

	@Override
	public int updateGrpToUserSelf(Map<String, Object> map) {
		return this.getSqlSession().update(Namespace+"updateGrpToUserSelf",map);
	}

	@Override
	public GrpDatacollectPrvVO queryIdAndPath(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryIdAndPath",paraMap);
	}

	@Override
	public String queryOtherPathById(Map<String, Object> map) {
		return this.getSqlSession().selectOne(Namespace+"queryOtherPathById",map);
	}

	@Override
	public String queryUserIdByPrvId(String datacollectPrvId) {
		return this.getSqlSession().selectOne(Namespace+"queryUserIdByPrvId",datacollectPrvId);
	}

	@Override
	public List<Act> queryWiteToDoReject(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryWiteToDoReject",map);
	}
}
