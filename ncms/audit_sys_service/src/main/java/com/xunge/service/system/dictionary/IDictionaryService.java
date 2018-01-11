package com.xunge.service.system.dictionary;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xunge.core.page.Page;
import com.xunge.model.system.dictionary.DictionaryGroupVO;
import com.xunge.model.system.dictionary.DictionaryVO;

/**
 * Described 数据字典信息维护service接口
 * @author SongJL
 */
public interface IDictionaryService {

	/**
	 * 查询数据字典分组列表，根据传入的省份ID
	 * @return
	 */
	public List<DictionaryGroupVO> queryAllDictionaryGroupRedis(String prv_id);
	
	/**
	 * 根据数据字典ID查询单条字典详情
	 * @param dictgroup_id
	 * @return
	 */
	public DictionaryVO queryDictionaryByIDRedis(String ID);
	
	/**
	 * 根据数据字典分组ID，取得该分组下的数据字典列表
	 * @param dict_group_id 数据字典分组
	 * @param dict_name
	 * @param dict_value
	 * @return
	 */
	public Page<List<DictionaryVO>> queryDictionaryRedis(Map<String, Object> param, int page_number, int page_count);
	
	/**
	 * 新增一条数据字典数据
	 * @param dictionaryVO
	 * @return
	 */
	public String insertDictionary(HttpServletRequest request);

	/**
	 * 启用一条数据字典数据
	 * @param dictionaryVO
	 * @return
	 */
	public String updateDictionaryStateOpen(List<String> itemsId);
	/**
	 * 停用一条数据字典数据
	 * @param dictionaryVO
	 * @return
	 */
	public String updateDictionaryStateStop(List<String> itemsId);
	/**
	 * 删除一条数据字典数据
	 * @param dictionaryVO
	 * @return
	 */
	public String updateDictionaryStateDelete(List<String> itemsId);
	
	/**
	 * 更新一条数据字典数据
	 * @param dictionaryVO
	 * @return
	 */
	public String updateDictionary(HttpServletRequest request);

	/**
	 * 根据字典编码查询所有数据字典列表
	 * 
	 * @param dictgroup_code
	 * @return
	 */
	List<Map<String, Object>> queryDictionaryByCodeRedis(String prvId, String dictgroup_code);
}
