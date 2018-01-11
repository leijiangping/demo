package com.xunge.service.twrrent.bizbasedata.impl;

import java.util.List;
import java.util.Map;






import com.xunge.core.page.Page;
import com.xunge.dao.twrrent.bizbasedata.ITwrProductTypeDao;
import com.xunge.model.towerrent.bizbasedata.ProductTypeVO;
import com.xunge.model.towerrent.bizbasedata.TwrProductTypeVO;
import com.xunge.service.twrrent.bizbasedata.ITwrProductTypeService;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 铁塔类型
 */
public class TwrProductTypeServiceImpl implements ITwrProductTypeService{
	
	ITwrProductTypeDao twrProductTypeDao;
	
	/**
	 * 查询所选铁塔下级子目录
	 * @author jiacy
	 */
	@Override
	public Page<List<TwrProductTypeVO>> queryTwrProductTypeVO(
			Map<String, Object> paraMap,int pageSize,int pageNumber) {
		return twrProductTypeDao.queryTwrProductTypeVO(paraMap,pageSize,pageNumber);
	}
	
	/**
	 * 查询所选铁塔下级子目录
	 * @author jiacy
	 */
	@Override
	public List<TwrProductTypeVO> queryAllTwrProductTypeVO() {
		return twrProductTypeDao.queryAllTwrProductTypeVO();
	}
	
	

	public void setTwrProductTypeDao(ITwrProductTypeDao twrProductTypeDao) {
		this.twrProductTypeDao = twrProductTypeDao;
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String deleteTwrById(List<String> ids) {
		return twrProductTypeDao.deleteTwrById(ids);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateTwrById(TwrProductTypeVO twoProductVO) {
		return twrProductTypeDao.updateTwrById(twoProductVO);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String insertTwrById(TwrProductTypeVO twoProductVO) {
		return twrProductTypeDao.insertTwrById(twoProductVO);
	}

	/**
	 * @author jiacy
	 */
	@Override
	public String updateStartOrStopTwrTypeById(List<String> ids,String twrState) {
		return twrProductTypeDao.startOrStopTwrTypeById(ids, twrState);
	}
	
	/**
	 * @author jiacy
	 */
	@Override
	public Page<List<TwrProductTypeVO>> queryTwrVOByStateAndName(Map<String,Object> paramMap,int pageSize,int pageNumber) {
		return twrProductTypeDao.queryTwrVOByStateAndName(paramMap,pageSize,pageNumber);
	}
	
	/**
	 * @author zhaosf	 
	 */

	@Override
	public List<ProductTypeVO> queryAllTwrProductTypeInfo(Map<String, Object> paraMap) {
		return twrProductTypeDao.queryTwrProductTypeName(paraMap);
	}

	/**
	 * @author zhaosf	 
	 */
	@Override
	public Page<List<TwrProductTypeVO>> queryTwrProductTypeInfoByCondition(
			Map<String, Object> paraMap, int pageSize, int pageNumber) {
		return twrProductTypeDao.queryTwrProductTypeInfoByCondition(paraMap, pageSize, pageNumber);
	}
	
	
}
