package com.xunge.dao.twrrent.punish.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.punish.ITwrRegPunishDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.punish.TwrRegPunishVO;


/**
 * @author zhujj
 * @date 2017年7月20日 上午9:21:38 
 * @version 1.0.0 
 */
public class TwrRegPunishDaoImpl  extends AbstractBaseDao implements ITwrRegPunishDao {

	private String Namespace = "com.xunge.dao.towerrent.punish.TwrRegPunishVOMapper.";
	@Override
	public int deleteByPrimaryKey(List<String> list) {
		// TODO Auto-generated method stub
		int delCount=0;
		for(String id:list){
			delCount=this.getSqlSession().delete(Namespace+"deleteByPrimaryKey", id);
		}
		return delCount;
	}

	@Override
	public int insertTwrRegPunishVO(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(Namespace+"insertTwrRegPunishVO", record);
	}
	@Override
	public int insertBatchTwrRegPunishVO(List<TwrRegPunishVO> record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(Namespace+"insertBatchTwrRegPunishVO", record);
	}
	@Override
	public int insertSelective(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(Namespace+"insertSelective", record);
	}
	@Override
	public int updateByPrimaryKeySelective(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(Namespace+"updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(Namespace+"updateByPrimaryKey", record);
	}

	@Override
	public int updateStateByPrimaryKey(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(Namespace+"updateStateByPrimaryKey", record);
	}

	@Override
	public int updateAuditStateByPrimaryKey(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(Namespace+"updateAuditStateByPrimaryKey", record);
	}

	@Override
	public int updateAccountsummaryIDByPrimaryKey(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(Namespace+"updateAccountsummaryIDByPrimaryKey", record);
	}

	@Override
	public int updateAccountsummaryIDByBatchID(List<TwrRegPunishVO> record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(Namespace+"updateAccountsummaryIDByBatchID", record);
	}


	@Override
	public TwrRegPunishVO selectByPrimaryKey(String twrRegPunishId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(Namespace+"selectByPrimaryKey", twrRegPunishId);
	}
	
	@Override
	public List<TwrRegPunishVO> selectByTwrRegPunish(TwrRegPunishVO twrRegPunishId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(Namespace+"selectByTwrRegPunish", twrRegPunishId);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Page<TwrRegPunishVO> selectTwrRegPunishPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageInterceptor.startPage(Integer.parseInt(map.get("pageNumber").toString()),Integer.parseInt(map.get("pageSize").toString()));
		this.getSqlSession().selectList(Namespace+"selectTwrRegPunishPage",map);
		return PageInterceptor.endPage();  
	}

	@Override
	public List<TwrRegPunishVO> selectTwrRegPunishList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(Namespace+"selectTwrRegPunishPage",map);
	}

	@Override
	public List<Map<String, Object>> queryTwrRegPunishMapListByCondition(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(Namespace + "queryTwrRegPunishMapListByCondition", params);
	}

}
