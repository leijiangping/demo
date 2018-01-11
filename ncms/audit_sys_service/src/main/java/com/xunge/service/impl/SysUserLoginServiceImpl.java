package com.xunge.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.StateComm;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.dao.system.user.ISysUserDao;
import com.xunge.model.system.region.RegionVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.system.user.SysUserVO;
import com.xunge.service.ISysUserLoginService;

/**
 * 用户登陆 service 实现类
 */
public class SysUserLoginServiceImpl implements ISysUserLoginService {
	
	private ISysUserDao sysUserDao;
	
	private ISysRegionDao sysRegionDao;

	@Override
	public UserLoginInfo getUserLoginInfoRedis(SysUserVO user){

		SysRegionVO sysRegionVO = sysUserDao.queryPrvIdByUser(user);
		String prvId = null; 
		String prvCode = null;
		String prvFlag = null;
		if(sysRegionVO != null){
			prvId = sysRegionVO.getPrvId(); 
			prvCode = sysRegionVO.getPrvCode();
			prvFlag = sysRegionVO.getPrvFlag();
		}
		List<String> lstRoleId = sysUserDao.queryRoleIdsByUser(user);
    	Map<String,Object> paramMap = new HashMap<>();
		//用户所管线地市区县id集合
    	paramMap.put("userId", user.getUserId());
    	paramMap.put("state", StateComm.STATE_0);
		List<RegionVO> RegionVOList = sysRegionDao.queryManaRegions(paramMap);
		List<String> pregIds = new ArrayList<String>();
		List<String> regIds = new ArrayList<String>();
		if(RegionVOList != null && RegionVOList.size() > 0){
			for (RegionVO regionVO : RegionVOList) {
				regIds.add(regionVO.getRegId());
				if(!pregIds.contains(regionVO.getpRegId())){
					pregIds.add(regionVO.getpRegId());
				}
			}
			//用户没有地市区县的权限
		}else{
			regIds.add("--");
			pregIds.add("--");
		}
		
    	//用户基础信息拼接放到session
		UserLoginInfo userLoginInfo = new UserLoginInfo();
		userLoginInfo.setUser_id(user.getUserId());
		userLoginInfo.setUser_loginname(user.getUserLoginname());
		userLoginInfo.setUser_name(user.getUserName());
		userLoginInfo.setUser_class(user.getUserClass());
		userLoginInfo.setPrv_id(prvId);
		userLoginInfo.setReg_id(user.getRegId());
		userLoginInfo.setRole_ids(lstRoleId);
		userLoginInfo.setUser_code(user.getUserCode());
		userLoginInfo.setPrv_code(prvCode);
		userLoginInfo.setPrv_flag(prvFlag);
		userLoginInfo.setPreg_ids(pregIds);
		userLoginInfo.setReg_ids(regIds);
		userLoginInfo.setUser_phone(user.getUserPhone());
		return userLoginInfo;
	}

	public ISysUserDao getSysUserDao() {
		return sysUserDao;
	}

	public void setSysUserDao(ISysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}

	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}

	
}
