package com.xunge.dao.system.parameter.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.system.parameter.ISysParameterDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.system.parameter.SysParameterVO;

public class SysParameterDaoImpl extends AbstractBaseDao implements ISysParameterDao {

	final String Namespace="com.xunge.dao.system.ParameterMapper.";
	
	@Override
	public int updateParameter(SysParameterVO sysParameterVO) {
		return this.getSqlSession().update(Namespace + "updateParameter",sysParameterVO);
	}

	@Override
	public Page<List<SysParameterVO>> queryParameter(Map<String,Object> paraMap,int pageNumber,int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"queryParameter",paraMap);
		return PageInterceptor.endPage();
	}

	@Override
	public SysParameterVO getParameter(String paraId) {
		return this.getSqlSession().selectOne(Namespace+"getParameter", paraId);
	}

	@Override
	public int openParameter(String paraId) {
		return this.getSqlSession().update(Namespace+"openParameter",paraId);
	}

	@Override
	public int stopParameter(String paraId) {
		return this.getSqlSession().update(Namespace+"stopParameter",paraId);
	}

	@Override
	public SysParameterVO queryParameter(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryParameter",paraMap);
	}

}
