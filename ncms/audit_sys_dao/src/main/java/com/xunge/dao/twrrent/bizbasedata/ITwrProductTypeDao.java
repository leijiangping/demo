package com.xunge.dao.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.ProductTypeVO;
import com.xunge.model.towerrent.bizbasedata.TwrProductTypeVO;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 铁塔类型
 */
public interface ITwrProductTypeDao {
	/**
	 * 查询业务基数数据下的所有铁塔种类
	 * @param paraMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<TwrProductTypeVO>> queryTwrProductTypeVO(Map<String,Object> paraMap,int pageSize,int pageNumber);
	/**
	 * 查询所有塔类型
	 * @return
	 */
	public List<TwrProductTypeVO> queryAllTwrProductTypeVO();
	/**
	 * 删除所选塔类型
	 * @param ids
	 * @return
	 */
	public String deleteTwrById(List<String> ids); 
	/**
	 * 修改所选塔类型
	 * @param paramMap
	 * @return
	 */
	public String updateTwrById(TwrProductTypeVO twoProductVO); 
	/**
	 * 新增塔类型
	 * @param twoProductVO
	 * @return
	 */
	public String insertTwrById(TwrProductTypeVO twoProductVO); 
	/**
	 * 启用或停用所选塔类型
	 * @param ids
	 * @param twrState
	 * @return
	 */
	public String startOrStopTwrTypeById(List<String> ids,String twrState);
	/**
	 * 根据状态和名称查询符合条件的铁塔集合
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<TwrProductTypeVO>> queryTwrVOByStateAndName(Map<String,Object> paramMap,int pageSize,int pageNumber);
	
	/**查询铁塔名称
	 * @param producttypeName
	 * @return
	 */
	public List<ProductTypeVO> queryTwrProductTypeName(Map<String, Object> paraMap);
	
	/**根据状态以及铁塔名称查询铁塔信息
	 * @param paraMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<TwrProductTypeVO>> queryTwrProductTypeInfoByCondition(Map<String,Object> paraMap,int pageSize,int pageNumber);
}