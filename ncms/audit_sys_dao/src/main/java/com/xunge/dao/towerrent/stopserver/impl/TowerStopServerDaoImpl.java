package com.xunge.dao.towerrent.stopserver.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.towerrent.stopserver.ITowerStopServerDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.stopserver.TowerStopServerVO;

/**
 * 终止服务 Dao  实现类
 * @author yuefy
 * @date 2017.07.20
 *
 */
public class TowerStopServerDaoImpl extends AbstractBaseDao implements ITowerStopServerDao {
	
	final String Namespace = "com.xunge.dao.TowerStopServerMapper.";

	@Override
	public Page<TowerStopServerVO> queryTowerStopServer(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"queryTowerStopServer",paraMap);
		return PageInterceptor.endPage();
	}

	@Override
	public int updateTowerStopServerCheckState(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateTowerStopServerCheckState",paraMap);
	}

	@Override
	public TowerStopServerVO queryTowerStopServerById(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryTowerStopServerById",paraMap);
	}
	

	@Override
	public int updateCheckStateById(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateCheckStateById",paraMap);
	}
	
	/**
	 * 查询所有铁塔信息变更表信息
	 * @author yuefy
	 */
	@Override
	public List<TowerStopServerVO> queryTowerStopServer(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryTowerStopServer",paraMap);
	}

	@Override
	public int insertTowerStopServer(
			List<TowerStopServerVO> list) {
		return this.getSqlSession().insert(Namespace+"insertTowerStopServer",list);
	}

}