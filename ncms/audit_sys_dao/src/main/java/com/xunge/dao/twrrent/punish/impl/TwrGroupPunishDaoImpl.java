package com.xunge.dao.twrrent.punish.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.punish.ITwrGroupPunishDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.punish.TwrGroupPunishVO;

/**
 * 集团既定考核指标扣罚
 * @author changwq
 *
 */
public class TwrGroupPunishDaoImpl  extends AbstractBaseDao implements ITwrGroupPunishDao {

	private String Namespace = "com.xunge.dao.twrGroupPunishMapper.";

	@Override
	public Page<List<TwrGroupPunishVO>> queryGroupPunishVO(
			Map<String, Object> paraMap,int pageNumber,int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"queryGroupPunishVO",paraMap);
		return PageInterceptor.endPage(); 
	}

	@Override
	public int deleteGroupPunish(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"deleteGroupPunish",paraMap);
	}

	@Override
	public List<TwrGroupPunishVO> queryGroupPunish(Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryGroupPunish",paraMap);
	}

	@Override
	public int insert(List<TwrGroupPunishVO> list) {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("list",list);
		return this.getSqlSession().insert(Namespace+"insert",paraMap);
	}

	@Override
	public List<TwrGroupPunishVO> queryGroupPunishByPregId(
			Map<String, Object> params) {
		return this.getSqlSession().selectList(Namespace + "queryGroupPunishByPregId", params);
	}

	@Override
	public int updatePunishAmountById(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updatePunishAmountById", paraMap);
	}

	@Override
	public TwrGroupPunishVO queryIfGroupPunish(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryIfGroupPunish",paraMap);
	}

	@Override
	public int update(List<TwrGroupPunishVO> list) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("list",list);
		return this.getSqlSession().update(Namespace+"update",paraMap);
	}
}
