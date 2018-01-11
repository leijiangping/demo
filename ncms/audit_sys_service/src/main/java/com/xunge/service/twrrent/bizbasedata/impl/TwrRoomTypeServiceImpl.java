package com.xunge.service.twrrent.bizbasedata.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.twrrent.bizbasedata.ITwrRoomTypeDao;
import com.xunge.model.towerrent.bizbasedata.CommTypeVO;
import com.xunge.service.twrrent.bizbasedata.ITwrRoomTypeService;

public class TwrRoomTypeServiceImpl implements ITwrRoomTypeService{
	
	private ITwrRoomTypeDao twrRoomTypeDao;

	@Override
	public String insertRoomType(CommTypeVO commTypeVO) {
		
		return getTwrRoomTypeDao().insertRoomType(commTypeVO);
	}

	@Override
	public String updateRoomTypeByCommTypeId(CommTypeVO commTypeVO) {
		
		return getTwrRoomTypeDao().updateRoomTypeByCommTypeId(commTypeVO);
	}

	@Override
	public String deleteRoomTypeByCommTypeId(List<String> list) {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("roomTypeIdList", list);		
		return getTwrRoomTypeDao().deleteRoomTypeByCommTypeId(paraMap);
	}

	@Override
	public Page<List<CommTypeVO>> queryAllRoomTypeByCondition(Map<String,Object> paraMap,Integer pageSize,Integer pageNum) {
		
		return getTwrRoomTypeDao().queryAllRoomTypeByCondition(paraMap,pageSize,pageNum);
	}

	@Override
	public Page<List<CommTypeVO>> queryAllRoomType(String commTypeGroup,Integer pageSize,Integer pageNum) {
		
		return getTwrRoomTypeDao().queryAllRoomType(commTypeGroup,pageSize,pageNum);
	}
	
	@Override
	public String startOrStopRoomType(List<String> roomTypeIds,int roomtypeState){
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("roomTypeIdList", roomTypeIds);
		paraMap.put("roomtypeState", roomtypeState);
		return getTwrRoomTypeDao().startOrStopRoomType(paraMap);
	}

	public ITwrRoomTypeDao getTwrRoomTypeDao() {
		return twrRoomTypeDao;
	}

	public void setTwrRoomTypeDao(ITwrRoomTypeDao twrRoomTypeDao) {
		this.twrRoomTypeDao = twrRoomTypeDao;
	}

	@Override
	public List<CommTypeVO> queryTwrCommType(Map<String, Object> paraMap) {
		return getTwrRoomTypeDao().queryTwrCommType(paraMap);
	}

}
