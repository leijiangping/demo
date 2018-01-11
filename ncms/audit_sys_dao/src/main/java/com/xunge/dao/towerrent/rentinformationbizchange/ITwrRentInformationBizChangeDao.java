package com.xunge.dao.towerrent.rentinformationbizchange;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.bizchange.TowerRentinformationBizchangeVO;
import com.xunge.model.towerrent.rentmanager.TowerResourceInfoVO;

public interface ITwrRentInformationBizChangeDao {
	/**
	 * 分页查询所有铁塔信息变更表
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author changwq
	 */
	public Page<TowerRentinformationBizchangeVO> queryTowerRentinformationBizchangeInfo(
			Map<String, Object> paraMap, int pageNumber, int pageSize);
	
	/**
	 * 审核完成修改审核状态
	 * @param paraMap
	 * @return
	 */
	public int updateBizChangeCheckState(Map<String,Object> paraMap);
	
	
	/**
	 * 查询所有铁塔信息变更表信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author yuefy
	 */
	public List<TowerRentinformationBizchangeVO> queryTowerRentinformationBizchangeInfo(
			Map<String, Object> paraMap);
	/**
	 * 根据id查询铁塔信息变更表信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author changwq
	 */
	public TowerRentinformationBizchangeVO queryBizChangeById(
			Map<String, Object> paraMap);

	/**
	 * 新增铁塔信息变更表信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author yuefy
	 */
	public int insertBatchSelective(
			List<TowerRentinformationBizchangeVO> list);
	
}