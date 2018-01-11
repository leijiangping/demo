package com.xunge.dao.twrrent.bizbasedata.impl;

import java.util.List;
import java.util.Map;

import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.bizbasedata.ITwrRoomTypeDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.towerrent.bizbasedata.CommTypeVO;

/**
 * @description 机房类型
 * @author zhaosf
 */
public class TwrRoomTypeDaoImpl extends AbstractBaseDao implements ITwrRoomTypeDao{
	final String roomTypeNamespace = "com.xunge.dao.twrrent.bizbasedata.ITwrRoomTypeDao.";

	@Override
	public String insertRoomType(CommTypeVO commTypeVO) {
		int result = this.getSqlSession().insert(roomTypeNamespace +"insertRoomType",commTypeVO);
		return (result == 0 ? RESULT.FAIL_0 : RESULT.SUCCESS_1);
	}

	@Override
	public String updateRoomTypeByCommTypeId(CommTypeVO commTypeVO) {
		int result = this.getSqlSession().update(roomTypeNamespace +"updateRoomTypeByCommTypeId",commTypeVO);
		return (result == 0 ? RESULT.FAIL_0 : RESULT.SUCCESS_1);
	}

	@Override
	public String deleteRoomTypeByCommTypeId(Map<String, Object> paraMap) {
		int result = this.getSqlSession().update(roomTypeNamespace +"deleteRoomTypeByCommTypeId",paraMap);
		return (result == 0 ? RESULT.FAIL_0 : RESULT.SUCCESS_1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<List<CommTypeVO>> queryAllRoomTypeByCondition(Map<String, Object> paraMap,
			Integer pageSize,Integer pageNum){
		PageInterceptor.startPage(pageNum, pageSize);
		this.getSqlSession().selectList(roomTypeNamespace + "queryAllRoomTypeByCondition",paraMap);		
		return PageInterceptor.endPage();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<List<CommTypeVO>> queryAllRoomType(String commTypeGroup,
			Integer pageSize, Integer pageNum) {
		PageInterceptor.startPage(pageNum, pageSize);
		this.getSqlSession().selectList(roomTypeNamespace+"queryAllRoomType",commTypeGroup);
		return PageInterceptor.endPage();
	}

	@Override
	public String startOrStopRoomType(Map<String, Object> paraMap) {
		int result = this.getSqlSession().update(roomTypeNamespace +"startOrStopRoomType",paraMap);
		return (result == 0 ? RESULT.FAIL_0 : RESULT.SUCCESS_1);
	}

	@Override
	public List<CommTypeVO> queryTwrCommType(Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(roomTypeNamespace+"queryTwrCommType",paraMap);
	}

}
