package com.xunge.dao.twrrent.resourceinfo;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.rentmanager.TowerResourceInfoVO;
/**
 * 起租管理 铁塔资源信息dao接口
 * @author yuefy
 *
 */
public interface ITowerResourceInfoDao {
	/**
	 * 查询铁塔资源信息
	 * yuefy
	 */
	public Page<TowerResourceInfoVO> queryTowerResourceInfo(Map<String, Object> paraMap,
			int pageNumber, int pageSize);
	/**
	 * 根据铁塔资源id查询铁塔起租信息
	 */
	public TowerResourceInfoVO queryTowerResourceInfoVOById(Map<String,Object> paraMap);
	
	/**
	 * 审核完成后修改状态
	 */
	public int updateCommit(Map<String,Object> paraMap);
	/**
	 * 查询铁塔资源信息
	 * yuefy
	 */
	public List<TowerResourceInfoVO> queryTowerResourceInfo(Map<String,Object> paraMap);
	/**
	 * 新增铁塔资源信息
	 * yuefy
	 */
	public int insertTowerResourceInfo(TowerResourceInfoVO towerResourceInfoVO);
	
	/**
	 * 批量新增铁塔资源信息
	 * yuefy
	 */
	public int insertTowerResourceInfoList(List<TowerResourceInfoVO> list);
	
	
	/**
	 * 修改铁塔资源信息
	 * yuefy
	 */
	public int updateTowerResourceInfo(TowerResourceInfoVO towerResourceInfoVO);
	
}
