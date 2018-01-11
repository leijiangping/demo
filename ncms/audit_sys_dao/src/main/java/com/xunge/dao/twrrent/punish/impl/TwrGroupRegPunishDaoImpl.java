package com.xunge.dao.twrrent.punish.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.punish.ITwrGroupRegPunishDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.punish.TwrGroupPunishVO;
import com.xunge.model.towerrent.punish.TwrGroupRegPunishVO;

/**
 * 集团既定考核指标扣罚
 * @author changwq
 *
 */
public class TwrGroupRegPunishDaoImpl  extends AbstractBaseDao implements ITwrGroupRegPunishDao {

	private String Namespace = "com.xunge.dao.TwrGroupRegPunishVOMapper.";

	@Override
	public int insertSelective(TwrGroupRegPunishVO twrGroupRegPunishVO) {
		return this.getSqlSession().insert(Namespace+"insertSelective",twrGroupRegPunishVO);
	}

	@Override
	public int updateByPrimaryKeySelective(
			TwrGroupRegPunishVO twrGroupRegPunishVO) {
		return this.getSqlSession().update(Namespace+"updateByPrimaryKeySelective", twrGroupRegPunishVO);
	}

	@Override
	public TwrGroupRegPunishVO queryGroupRegPunish(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryGroupRegPunish",paraMap);
	}

}
