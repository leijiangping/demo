package com.xunge.service.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.ProductConfigVO;

/**
 * @author jiacy
 * @date 2017年7月6日
 * @description 产品配置库
 */
public interface ITwrProductConfService {
	/**
	 * 查询产品配置库
	 * @param jiacy
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<ProductConfigVO>> queryAllObj(int pageSize,int pageNumber);
	/**
	 * 删除产品配置库对象
	 * @param jiacy
	 * @param ids
	 * @return
	 */
	public String deleteObjById(List<String> ids); 
	/**
	 * 修改产品配置库
	 * @param jiacy
	 * @param proConfVo
	 * @return
	 */
	public String updateObjById(ProductConfigVO proConfVo); 
	/**
	 * 新增产品配置库对象
	 * @param jiacy
	 * @param proConfVo
	 * @return
	 */
	public String insertObjById(ProductConfigVO proConfVo); 
	/**
	 * 启用或停用
	 * @param jiacy
	 * @param ids
	 * @param highUpState
	 * @return
	 */
	public String startOrStopObjById(List<String> ids,String highUpState);
	/**
	 * 按条件查询
	 * @param jiacy
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<ProductConfigVO>> queryObjByStateAndName(Map<String,Object> paramMap,int pageSize,int pageNumber);
	
	/**按条件过滤产品配置库对象
	 * @param paramMap
	 * @return
	 */
	public List<ProductConfigVO> queryObjByCondition(Map<String,Object> paramMap);
}
