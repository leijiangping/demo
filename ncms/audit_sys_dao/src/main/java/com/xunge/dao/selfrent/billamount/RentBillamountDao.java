package com.xunge.dao.selfrent.billamount;


import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.selfrent.billamount.RentBillamountVO;

/**
 * @description 租费报账汇总DAO接口
 * @author zhujj
 * @version 2017-06-26
 */
public interface RentBillamountDao<T> {
	/**
	 * @description 查询列表数据
	 * @param entity
	 * @return
	 */
	public Page<List<T>> queryRentBillamountPage(Map<String, Object> map);
	
	/**
	 * @description 插入租费报账汇总
	 * @param rentBillamountVO
	 * @return
	 */
	public int insertRentBillamountVO(RentBillamountVO rentBillamountVO);
	
	/**
	 * @description 查询租费报账汇总数据
	 * @param map
	 * @param map billamountId 租费报账汇总Id
	 * @return
	 */
	public RentBillamountVO queryRentBillamountById(Map<String, Object> map);
}