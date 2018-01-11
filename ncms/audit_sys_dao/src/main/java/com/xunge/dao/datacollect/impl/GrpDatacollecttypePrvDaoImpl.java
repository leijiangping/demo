package com.xunge.dao.datacollect.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.datacollect.IGrpDatacollecttypePrvDao;
import com.xunge.model.datacollect.GrpDatacollecttypePrvVO;

public class GrpDatacollecttypePrvDaoImpl extends AbstractBaseDao implements IGrpDatacollecttypePrvDao {

	final String Namespace = "com.xunge.dao.GrpDatacollecttypePrvVOMapper.";
	
	@Override
	public int deleteTypePrvById(String datacollectId) {
		return this.getSqlSession().delete(Namespace+"deleteTypePrvById",datacollectId);
	}

	@Override
	public int insertSelective(GrpDatacollecttypePrvVO grpDatacollecttypePrvVO) {
		return this.getSqlSession().insert(Namespace+"insertSelective",grpDatacollecttypePrvVO);
	}

	@Override
	public List<GrpDatacollecttypePrvVO> queryEveryPrvMsg(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryEveryPrvMsg",paraMap);
	}

	@Override
	public List<GrpDatacollecttypePrvVO> queryEveryPrvMsgByPk(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryEveryPrvMsgByPk",paraMap);
	}

	@Override
	public int updatePrvType(GrpDatacollecttypePrvVO grpDatacollecttypePrvVO) {
		return this.getSqlSession().update(Namespace+"updatePrvType",grpDatacollecttypePrvVO);
	}

	@Override
	public int updatePrvFiles(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updatePrvFiles",paraMap);
	}

	@Override
	public int deleteTypePrvByTypeId(Map<String, Object> paraMap) {
		return this.getSqlSession().delete(Namespace+"deleteTypePrvByTypeId",paraMap);
	}

	@Override
	public List<GrpDatacollecttypePrvVO> queryPrvUploadNameById(Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryPrvUploadNameById",paraMap);
	}

	@Override
	public List<String> queryPrvFilePath(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryPrvFilePath",map);
	}
	
}
