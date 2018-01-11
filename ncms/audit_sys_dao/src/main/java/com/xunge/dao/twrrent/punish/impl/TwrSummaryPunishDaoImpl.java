package com.xunge.dao.twrrent.punish.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xunge.comm.StateComm;
import com.xunge.core.page.Page;
import com.xunge.core.util.StrUtil;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.punish.ITwrSummaryPunishDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.punish.TwrSummaryPunishVO;

/**
 * 集团既定考核指标扣罚
 * @author changwq
 *
 */
public class TwrSummaryPunishDaoImpl  extends AbstractBaseDao implements ITwrSummaryPunishDao {

	private String GroupNamespace = "com.xunge.dao.TwrGroupRegPunishVOMapper.";
	private String RegNamespace = "com.xunge.dao.towerrent.punish.TwrRegPunishVOMapper.";
	private String PrvNamespace = "com.xunge.dao.TowerPrvCheckIndexFineVOMapper.";
	private String RegionNamespace = "com.xunge.dao.system.SysRegionVOMapper.";
	
	@Override
	public Page<List<?>> queryRegMsgById(Map<String, Object> paraMap,
			int pageNumber,int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(RegionNamespace+"queryRegMsgById",paraMap);
		return PageInterceptor.endPage(); 
	}
	@Override
	public String queryPrvCodeById(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(GroupNamespace+"queryPrvCodeById",paraMap);
	}
	@Override
	public BigDecimal queryGroupAmount(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(GroupNamespace+"queryGroupAmount",paraMap);
	}
	@Override
	public BigDecimal queryRegAmount(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(RegNamespace+"queryRegAmount",paraMap);
	}
	@Override
	public BigDecimal queryPrvAmount(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(PrvNamespace+"queryPrvAmount",paraMap);
	}
	@Override
	public String queryNameById(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(RegionNamespace+"queryNameById",paraMap);
	}

}
