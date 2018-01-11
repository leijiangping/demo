package com.xunge.dao.sysSmsSendHistroy.impl;

import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.sysSmsSendHistroy.ISysSmssendHistroyDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.smsSendHistroy.SysSmssendHistroyVO;

public class SysSmssendHistroyDaoImpl extends AbstractBaseDao implements ISysSmssendHistroyDao {

	final String Namespace = "com.xunge.dao.SysSmssendHistroyVOMapper.";

	@Override
	public int insertSelective(SysSmssendHistroyVO sysSmssendHistroyVO) {
		return this.getSqlSession().insert(Namespace+"insertSelective",sysSmssendHistroyVO);
	}

	@Override
	public Page<SysSmssendHistroyVO> queryHistroyByCollId(
			Map<String, Object> paraMap,int pageNumber,int pageSize) {
		PageInterceptor.startPage(pageNumber,pageSize);
		this.getSqlSession().selectList(Namespace+"queryHistroyByCollId",paraMap);
		return PageInterceptor.endPage();
	}
	
}
