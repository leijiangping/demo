package com.xunge.dao.datacollect.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.datacollect.IGrpDatacollectHistoryDao;
import com.xunge.model.datacollect.GrpDatacollectHistoryVO;

public class GrpDatacollectHistoryDaoImpl extends AbstractBaseDao implements IGrpDatacollectHistoryDao {

	final String Namespace = "com.xunge.dao.GrpDatacollectHistoryVOMapper.";

	@Override
	public int insertSelective(GrpDatacollectHistoryVO grpDatacollectHistoryVO) {
		return this.getSqlSession().insert(Namespace+"insertSelective",grpDatacollectHistoryVO);
	}

	@Override
	public List<GrpDatacollectHistoryVO> queryHistoryByPrvId(
			Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryHistoryByPrvId",map);
	}
	
}
