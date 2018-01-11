package com.xunge.dao.system.dictionary.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.system.dictionary.IDictionaryDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.system.dictionary.DictionaryGroupVO;
import com.xunge.model.system.dictionary.DictionaryVO;

@SuppressWarnings("unchecked")
public class DictionaryDaoImpl  extends AbstractBaseDao implements IDictionaryDao {

	final String Namespace = "com.xunge.dao.system_conf_man.DictionaryMapper.";

	@Override
	public List<DictionaryGroupVO> queryAllDictionaryGroup(String prv_id) {
		return this.getSqlSession().selectList(Namespace+"queryAllDictionaryGroup",prv_id);
	}
	
	@Override
	public Page<List<DictionaryVO>> queryDictionary(Map<String, Object> param, int pageNumber,  
            int pageSize) {  
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"queryDictionary",param);  
		return PageInterceptor.endPage();  
	} 

	@Override
	public DictionaryVO queryDictionaryByID(Map<String, Object> param) {
		return this.getSqlSession().selectOne(Namespace+"queryDictionaryByID",param);
	}

	@Override
	public int insertDictionary(DictionaryVO dictionaryVO) {
		return this.getSqlSession().insert(Namespace+"insertDictionary", dictionaryVO);
	}

	@Override
	public int updateDictionaryStateBatch(Map<String, Object> param) {
		return this.getSqlSession().delete(Namespace+"updateDictionaryStateBatch", param);
	}

	@Override
	public int updateDictionary(DictionaryVO dictionaryVO) {
		return this.getSqlSession().update(Namespace+"updateDictionary", dictionaryVO);
	}

	@Override
	public int delDictionaryByGroupID(String dictgroup_id) {
		return this.getSqlSession().delete(Namespace+"delDictionaryByGroupID", dictgroup_id);
	}

	@Override
	public List<Map<String, Object>> queryDictionaryByCode(Map<String,Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryDictionaryByGroupCode", map);
	}

}
