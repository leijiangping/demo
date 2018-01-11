package com.xunge.dao.log.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.log.ISysLogDao;
import com.xunge.model.system.log.SysLogVO;

public class SysLogDaoImpl extends AbstractBaseDao implements ISysLogDao {

	final String Namespace="com.xunge.dao.system.LogMapper.";

	@Override
	public List<SysLogVO> queryLogMsg(Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryLogMsg",paraMap);
	}

	@Override
	public int deleteLogMsg(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete(Namespace+"deleteLogMsg",paraMap);
	}

	@Override
	public int insertLog(SysLogVO sysLog) {
		return this.getSqlSession().insert(Namespace+"insertLog", sysLog);
	}
	
}
