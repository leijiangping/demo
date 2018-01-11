package com.xunge.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.xunge.comm.StateComm;
import com.xunge.dao.system.role.ISysDataAuthMenuTreeDao;
import com.xunge.model.SysDataAuthMenuTreeVO;
import com.xunge.service.ISysDataAuthMenuTreeService;
/**
 * 角色功能权限树形结构维护 service实现类
 *
 */
public class SysDataAuthMenuTreeServiceImpl implements ISysDataAuthMenuTreeService {

	@Autowired
	private ISysDataAuthMenuTreeDao sysDataAuthMenuTreeDao;
	
	@Override
	public List<SysDataAuthMenuTreeVO> queryAllMenuTreeRedis() {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("state",StateComm.STATE_0);
		List<SysDataAuthMenuTreeVO> lsmt = sysDataAuthMenuTreeDao.queryAllMenuTree(paraMap);
		return lsmt;
		
	}

}
