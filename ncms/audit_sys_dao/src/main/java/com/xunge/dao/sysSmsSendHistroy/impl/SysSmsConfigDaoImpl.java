package com.xunge.dao.sysSmsSendHistroy.impl;

import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.sysSmsSendHistroy.ISysSmsConfigDao;
import com.xunge.model.smsSendHistroy.SysSmsConfigVO;

public class SysSmsConfigDaoImpl extends AbstractBaseDao implements ISysSmsConfigDao {

	final String Namespace = "com.xunge.dao.SysSmsConfigVOMapper.";

	@Override
	public SysSmsConfigVO querySmsModelMsg(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"querySmsModelMsg",paraMap);
	}
}
