package com.xunge.service;

import java.util.List;

import com.xunge.model.SysDataAuthMenuTreeVO;
/**
 * 角色功能权限树形结构维护 service 接口
 *
 */
public interface ISysDataAuthMenuTreeService {
	public List<SysDataAuthMenuTreeVO> queryAllMenuTreeRedis();
}
