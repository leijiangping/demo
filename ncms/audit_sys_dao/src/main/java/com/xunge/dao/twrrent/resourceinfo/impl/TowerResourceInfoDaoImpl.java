package com.xunge.dao.twrrent.resourceinfo.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.resourceinfo.ITowerResourceInfoDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.rentmanager.TowerResourceInfoVO;
/**
 * 铁塔资源信息dao实现类
 * @author yuefy
 *
 */
public class TowerResourceInfoDaoImpl  extends AbstractBaseDao  implements ITowerResourceInfoDao {

	final String Namespace="com.xunge.dao.towerrent.rentmanager.TowerResourceInfoMapper.";
	
	/**
	 * 分页查询铁塔资源信息
	 * @author yuefy
	 */
	@Override
	public Page<TowerResourceInfoVO> queryTowerResourceInfo(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"queryTowerResourceInfo",paraMap);
		return PageInterceptor.endPage();
	}

	/**
	 * 根据id查询铁塔资源信息
	 */
	@Override
	public TowerResourceInfoVO queryTowerResourceInfoVOById(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryTowerResourceInfoVOById",paraMap);
	}

	/**
	 * 审核完成后修改状态
	 */
	@Override
	public int updateCommit(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateCommit",paraMap);
	}
	
	/**
	 * 查询铁塔资源信息
	 * @author yuefy
	 */
	@Override
	public List<TowerResourceInfoVO> queryTowerResourceInfo(Map<String,Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryTowerResourceInfo",paraMap);
	}

	/**
	 * 新增铁塔资源信息
	 * @author yuefy
	 */
	@Override
	public int insertTowerResourceInfo(TowerResourceInfoVO towerResourceInfoVO) {
		return this.getSqlSession().insert(Namespace+"insertTowerResourceInfo",towerResourceInfoVO);
	}
	
	/**
	 * 批量新增铁塔资源信息
	 * @author yuefy
	 */
	@Override
	public int insertTowerResourceInfoList(List<TowerResourceInfoVO> list) {
		return this.getSqlSession().insert(Namespace+"insertTowerResourceInfo",list);
	}

	/**
	 * 修改铁塔资源信息
	 * @author yuefy
	 */
	@Override
	public int updateTowerResourceInfo(TowerResourceInfoVO towerResourceInfoVO) {
		return this.getSqlSession().update(Namespace+"updateTowerResourceInfo",towerResourceInfoVO);
	}
	
}
