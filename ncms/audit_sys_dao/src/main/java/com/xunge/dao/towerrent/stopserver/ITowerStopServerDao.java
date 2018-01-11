package com.xunge.dao.towerrent.stopserver;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizchange.TowerRentinformationBizchangeVO;
import com.xunge.model.towerrent.stopserver.TowerStopServerVO;

/**
 * 终止服务 Dao  接口
 * @author yuefy
 * @date 2017.07.20
 *
 */
public interface ITowerStopServerDao {
	/** 
	 * 分页查询所有铁塔终止服务表
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author changwq
	 */
	public Page<TowerStopServerVO> queryTowerStopServer(
			Map<String, Object> paraMap, int pageNumber, int pageSize);
	
	/**
	 * 审核完成修改审核状态
	 * @param paraMap
	 * @return
	 */
	public int updateTowerStopServerCheckState(Map<String,Object> paraMap);
	
	
	/**
	 * 查询所有铁塔终止服务表信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author yuefy
	 */
	public List<TowerStopServerVO> queryTowerStopServer(
			Map<String, Object> paraMap);
	/**
	 * 根据id查询铁塔终止服务表信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author changwq
	 */
	public TowerStopServerVO queryTowerStopServerById(
			Map<String, Object> paraMap);

	/**
	 * 新增铁塔终止服务表信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author yuefy
	 */
	public int insertTowerStopServer(
			List<TowerStopServerVO> list);
	/**
	 * 审核通过修改申请信息审核状态
	 * @param paraMap
	 * @return
	 */
	public int updateCheckStateById(Map<String,Object> paraMap);
}