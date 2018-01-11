package com.xunge.service.system.dictionary.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xunge.comm.StateComm;
import com.xunge.comm.system.DictionaryComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.system.dictionary.IDictionaryDao;
import com.xunge.model.system.dictionary.DictionaryGroupVO;
import com.xunge.model.system.dictionary.DictionaryVO;
import com.xunge.service.system.dictionary.IDictionaryService;


/**
 * 数据字典信息维护service实现类
 *
 */
public class DictionaryServiceImpl implements IDictionaryService {

	private IDictionaryDao dictionaryDao;
	
	public IDictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}

	public void setDictionaryDao(IDictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}
	
	@Override
	public List<DictionaryGroupVO> queryAllDictionaryGroupRedis(String prv_id) {
		return dictionaryDao.queryAllDictionaryGroup(prv_id);
	}

	@Override
	public DictionaryVO queryDictionaryByIDRedis(String ID) {
		// 拼接查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(DictionaryComm.DICT_ID, ID);
		return dictionaryDao.queryDictionaryByID(paramMap);
	}

	@Override
	public String insertDictionary(HttpServletRequest request) {
		DictionaryVO item = new DictionaryVO();
		item.setDict_id(SysUUID.generator());
		item.setDict_name(request.getParameter("dict_name"));
		item.setDict_note(request.getParameter("dict_note"));
		item.setDict_value(request.getParameter("dict_value"));
		item.setDict_state(StateComm.STATE_str0);// 默认新增启用数据
		item.setDictgroup_id(request.getParameter("dictgroup_id"));
    	// 插入一条菜单信息
		int result = dictionaryDao.insertDictionary(item);
		return (result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String updateDictionaryStateOpen(List<String> itemsId) {
		// 拼接查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(DictionaryComm.IDS_LIST, itemsId);
		paramMap.put(DictionaryComm.STATE, StateComm.STATE_0);
		int result = dictionaryDao.updateDictionaryStateBatch(paramMap);
		return (result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String updateDictionaryStateDelete(List<String> itemsId) {
		// 拼接查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(DictionaryComm.IDS_LIST, itemsId);
		paramMap.put(DictionaryComm.STATE, StateComm.STATE__1);
		int result = dictionaryDao.updateDictionaryStateBatch(paramMap);
		return (result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String updateDictionaryStateStop(List<String> itemsId) {
		// 拼接查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(DictionaryComm.IDS_LIST, itemsId);
		paramMap.put(DictionaryComm.STATE, StateComm.STATE_9);
		int result = dictionaryDao.updateDictionaryStateBatch(paramMap);
		return (result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public String updateDictionary(HttpServletRequest request) {
		DictionaryVO item = new DictionaryVO();
		item.setDict_id(request.getParameter("dict_id"));
		item.setDict_name(request.getParameter("dict_name"));
		item.setDict_note(request.getParameter("dict_note"));
		item.setDict_value(request.getParameter("dict_value"));
		item.setDictgroup_id(request.getParameter("dictgroup_id"));
    	// 插入一条菜单信息
		int result = dictionaryDao.updateDictionary(item);
		return (result == 0)?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}

	@Override
	public Page<List<DictionaryVO>> queryDictionaryRedis(Map<String, Object> param, 
			int page_number, int page_count) {
		return dictionaryDao.queryDictionary(param, page_number, page_count);
	}

	@Override
	public List<Map<String, Object>> queryDictionaryByCodeRedis(String prvId, String dictgroup_code) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dictgroup_code",dictgroup_code);
		map.put("prv_id",prvId);
		return dictionaryDao.queryDictionaryByCode(map);
	}

}
