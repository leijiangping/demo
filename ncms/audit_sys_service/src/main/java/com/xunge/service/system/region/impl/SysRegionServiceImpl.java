package com.xunge.service.system.region.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xunge.comm.StateComm;
import com.xunge.comm.SysUserComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.PrvComm;
import com.xunge.comm.system.RESULT;
import com.xunge.comm.system.SystemConfComm;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.model.system.province.SysProvinceGroupVO;
import com.xunge.model.system.province.SysProvinceVO;
import com.xunge.model.system.region.RegionVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.system.region.ISysRegionService;

/**
 * 区县信息维护service实现类
 *
 */
public class SysRegionServiceImpl implements ISysRegionService{
	private ISysRegionDao sysRegionDao;
	
	@Override
	public List<SysRegionVO> selectByConditions(String prvId,String regCode, String regName) {
		System.out.println(regCode);
		Map<String, Object> map=new HashMap<>();
		map.put("regCode", regCode);
		map.put("regName", regName);
		map.put("regState",StateComm.STATE_0);
		map.put("prvId", prvId);
		List<SysRegionVO> list = sysRegionDao.selectByConditions(map);
		
		return list;
	}
	@Override
	public List<SysProvinceGroupVO> selectByPrvGroupRedis() {
		Map<String, Object> map=new HashMap<>();
		map.put("regState",StateComm.STATE_0);
		List<SysProvinceGroupVO> list = sysRegionDao.selectByPrvGroup(map);
		return list;
	}
	public List<SysProvinceGroupVO> selectByPrvGroup() {
		Map<String, Object> map=new HashMap<>();
		List<SysProvinceGroupVO> list = sysRegionDao.selectByPrvGroup(map);
		return list;
	}
	public List<SysProvinceVO> selectProvinceByIds(UserLoginInfo userLoginInfo) {
		Map<String, Object> map=new HashMap<>();
		map.put("regState",StateComm.STATE_0);
//		map.put("prv_ids", userLoginInfo.getPrv_id());
		List<SysProvinceVO> list = sysRegionDao.selectProvinceByIds(map);
		return list;
	}
	
	@Override
	public String delRegion(List<String> sysRegionVO) {
		Map<String, Object> paramMap=new HashMap<>();
		List<String> idsList = new ArrayList<String>();
		for (int i = 0; i < sysRegionVO.size(); i++) {
			idsList.add(sysRegionVO.get(i));
		}
		paramMap.put("idsList", idsList);
		paramMap.put("state", StateComm.STATE__1);
		sysRegionDao.delRegion(paramMap);
		int result = sysRegionDao.delRegion(paramMap);
		return (result == Integer.parseInt(RESULT.FAIL_0))?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}
	
	
	@Override
	public String insertRegion(HttpServletRequest request) {
		String regName=request.getParameter("regName");
		String regCode=request.getParameter("regCode");
		String regOrder=request.getParameter("regOrder");
		String pregId=request.getParameter("pregId");
		
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		SysRegionVO sysRegionVO=new SysRegionVO();
		sysRegionVO.setPrvId(loginInfo.getPrv_id());
		sysRegionVO.setRegId(SysUUID.generator());
		sysRegionVO.setRegName(regName);
		sysRegionVO.setRegCode(regCode);
		sysRegionVO.setPregId(pregId);
		sysRegionVO.setRegOrder(Integer.parseInt(regOrder));
		sysRegionVO.setRegState(StateComm.STATE_0);
		int result  = sysRegionDao.addRegion(sysRegionVO);
		return (result == Integer.parseInt(RESULT.FAIL_0))?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}
	
	
	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}
	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}


	@Override
	public  List<Object> queryRegionByConditions(String prvId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("prvId",prvId);
		paramMap.put("prvState",PrvComm.UPLINE_9);
		List<SysRegionVO> sysRegionVOs = sysRegionDao.queryRegionByConditions(paramMap);
		return menuList(sysRegionVOs);
	}
	
	private List<Object> menuList(List<SysRegionVO> regionVOs){
		 List<Object> list = new ArrayList<Object>();  
	        for (SysRegionVO x : regionVOs) {     
	            Map<String,Object> mapArr = new LinkedHashMap<String, Object>();  
	                mapArr.put("regId", x.getRegId());  
	                mapArr.put("rCode", x.getrCode()); 
	                mapArr.put("rName", x.getrName()); 
	                mapArr.put("pRegId", x.getPregId()); 
	                mapArr.put("pRegName", x.getpRegName()); 
	                mapArr.put("regOrder", x.getRegOrder());
	                mapArr.put("regState", x.getRegState());
	                mapArr.put("open", true);
	                list.add(mapArr);  
	        }     
	        return list;  
	}

	@Override
	public String updateRegion(HttpServletRequest request) {
		SysRegionVO sysRegionVO=new SysRegionVO();
		String regName=request.getParameter("regName");
		String regCode=request.getParameter("regCode");
		String regOrder=request.getParameter("regOrder");
		String regId=request.getParameter("regId");
		sysRegionVO.setRegId(regId);
		sysRegionVO.setRegName(regName);
		sysRegionVO.setRegCode(regCode);
		sysRegionVO.setRegOrder(Integer.parseInt(regOrder));
		int result = sysRegionDao.updateRegion(sysRegionVO, regId);
		return (result == Integer.parseInt(RESULT.FAIL_0))?RESULT.FAIL_0:RESULT.SUCCESS_1;
	}


	@Override
	public SysRegionVO getRegionById(String regId) {
		return sysRegionDao.getRegionById(regId);
	}


	@Override
	public SysRegionVO getRegionIdByName(String regName) {
		return sysRegionDao.getRegionIdByName(regName);
	}


	@Override
	public String getPrvNameById(String regId) {
		return sysRegionDao.getPrvNameById(regId);
	}

	@Override
	public String getPrvIdByCode(String prvcode) {
		return sysRegionDao.getPrvIdByCode(prvcode);
	}

	@Override
	public String getCityIdByCode(String code) {
		return sysRegionDao.getRegIdByCode(code);
	}

	@Override
	public String getRegIdByCode(String code) {
		return sysRegionDao.getRegIdByCode(code);
	}
	
	@Override
	public List<SysRegionVO> getAddress(Map<String, Object> map) {
		List<SysRegionVO> sysRegionVOs = sysRegionDao.getAddress(map);
		return sysRegionVOs;
	}
	
	/**
	 * 获取区域信息
	 */
	@Override
	public List<SysRegionVO> getUserAddress(Map<String, Object> map) {
		return sysRegionDao.getUserAddress(map);
	}
	
	@Override
	public List<RegionVO> queryManaRegions(Map<String, Object> map) {
		// 查取所有市
		List<RegionVO> regionList = sysRegionDao.queryManaProvs(map);
		for(RegionVO region : regionList){
			map.put("cityId", region.getRegId());
			// 拼接可管理市下的区集合
			region.setChild(sysRegionDao.queryManaRegions(map));
		}
		return regionList;
	}
	@Override
	public List<String> queryRegionIdsByPrvId(Map<String, Object> map) {
		String prv_id = (String)map.get("prv_id");

		if(prv_id==null || SystemConfComm.PRVID.equals(prv_id)){//集团用户
			map.put("prv_ids", sysRegionDao.queryPrvRegionIdsByPrvId(map));
			return null;
    	}
		else if(SysUserComm.provLevelCityList.contains(prv_id)){
    		return sysRegionDao.queryRegionIdsByPrvId(map);
    	}
    	else{
    		return sysRegionDao.queryPRegionIdsByPrvId(map);
    	}
	}

}
