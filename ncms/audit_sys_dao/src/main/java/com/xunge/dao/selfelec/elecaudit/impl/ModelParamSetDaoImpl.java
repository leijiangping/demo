package com.xunge.dao.selfelec.elecaudit.impl;

import java.util.List;
import java.util.Map;

import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfelec.elecaudit.IModelParamSetDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.selfelec.vo.ModelParamSetVO;

public class ModelParamSetDaoImpl extends AbstractBaseDao implements IModelParamSetDao{
	
	final String ModelParamSetNamespace = "com.xunge.mapping.ModelParamSetVOMapper.";

	@Override
	public Page<ModelParamSetVO> queryAll(Map<String, Object> paramMap) {
		int pageNumber = Integer.parseInt(paramMap.get("pageNumber").toString());
		int pageSize = Integer.parseInt(paramMap.get("pageSize").toString());
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(ModelParamSetNamespace+"queryAllModel",paramMap);
		return PageInterceptor.endPage(); 
	}
	
	@Override
	public List<ModelParamSetVO> queryAllNoPage(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(ModelParamSetNamespace+"queryAllModel",paramMap);
	}

	@Override
	public String updateById(Map<String, Object> paramMap) {
		int result = this.getSqlSession().update(ModelParamSetNamespace+"updateModelParam",paramMap);
		return result == 1? RESULT.SUCCESS_1 : RESULT.FAIL_0;
	}

	@Override
	public void export() {
		
	}

	@Override
	public String insertModel(Map<String, Object> paramMap) {
		int result = this.getSqlSession().insert(ModelParamSetNamespace+"insertModel",paramMap);
		return result == 1? RESULT.SUCCESS_1 : RESULT.FAIL_0;
	}

}
