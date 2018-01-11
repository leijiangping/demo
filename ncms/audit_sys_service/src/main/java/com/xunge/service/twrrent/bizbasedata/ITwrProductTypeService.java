package com.xunge.service.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.ProductTypeVO;
import com.xunge.model.towerrent.bizbasedata.TwrProductTypeVO;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 铁塔种类
 */
public interface ITwrProductTypeService {
	/**
	 * 查询所有铁塔类型集合
	 * @author jiacy
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<List<TwrProductTypeVO>> queryTwrProductTypeVO(Map<String,Object> paraMap,int pageSize,int pageNumber);
	
	public List<TwrProductTypeVO> queryAllTwrProductTypeVO();
	/**
	 * 删除所选铁塔
	 * @author jiacy
	 * @param ids
	 * @return
	 */
	public String deleteTwrById(List<String> ids); 
	/**
	 * 修改铁塔对象
	 * @author jiacy
	 * @param paramMap
	 * @return
	 */
	public String updateTwrById(TwrProductTypeVO twoProductVO); 
	/**
	 * 新增铁塔对象
	 * @author jiacy
	 * @param twoProductVO
	 * @return
	 */
	public String insertTwrById(TwrProductTypeVO twoProductVO); 
	/**
	 * 启用或停用
	 * @author jiacy
	 * @param ids
	 * @param twrState
	 * @return
	 */
	public String updateStartOrStopTwrTypeById(List<String> ids,String twrState);
	/**
	 * 按条件查询
	 * @author jiacy
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<TwrProductTypeVO>> queryTwrVOByStateAndName(Map<String,Object> paramMap,int pageSize,int pageNumber);
	
	/**查询铁塔信息
	 * @param producttypeName
	 * @return
	 */
	public List<ProductTypeVO> queryAllTwrProductTypeInfo(Map<String,Object> paraMap);
	
	/**根据铁塔状态以及铁塔名称查询铁塔信息
	 * @param paraMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<TwrProductTypeVO>> queryTwrProductTypeInfoByCondition(Map<String, Object> paraMap, int pageSize, int pageNumber);
}

