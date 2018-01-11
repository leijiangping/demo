package com.xunge.dao.system.dictionary;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.system.dictionary.DictionaryGroupVO;
import com.xunge.model.system.dictionary.DictionaryVO;

public interface IDictionaryDao {

	/**
	 * 查询数据字典分组列表，根据传入的省份ID
	 * 
	 * @return
	 */
	public List<DictionaryGroupVO> queryAllDictionaryGroup(String prv_id);

	/**
	 * 根据条件查询所有数据字典列表
	 * 
	 * @param dictionaryVO
	 * @return
	 */
	public Page<List<DictionaryVO>> queryDictionary(Map<String, Object> param, int pageNumber,  
            int pageSize);

	/**
	 * 根据条件查询所有数据字典列表
	 * 
	 * @param dictionaryVO
	 * @return
	 */
	public DictionaryVO queryDictionaryByID(Map<String, Object> param);

	/**
	 * 新增一条数据字典数据
	 * 
	 * @param dictionaryVO
	 * @return
	 */
	public int insertDictionary(DictionaryVO dictionaryVO);

	/**
	 * 批量更新数据字典状态
	 * 
	 * @param dictionaryVO
	 * @return
	 */
	public int updateDictionaryStateBatch(Map<String, Object> paramMap);

	/**
	 * 更新一条数据字典数据
	 * 
	 * @param dictionaryVO
	 * @return
	 */
	public int updateDictionary(DictionaryVO dictionaryVO);

	/**
	 * 删除一组数据字典
	 * 
	 * @param dictgroup_id
	 * @return
	 */
	public int delDictionaryByGroupID(String dictgroup_id);

	/**
	 * 根据字典编码查询所有数据字典列表
	 * 
	 * @param dictgroup_code
	 * @return
	 */
	public List<Map<String, Object>> queryDictionaryByCode(Map<String,Object> map);

}
