package com.xunge.service.system.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xunge.dao.basedata.system.SysUserMapper;
import com.xunge.model.system.SysUserVO;
import com.xunge.service.system.user.ISysUserService;

@Service
public class SysUserServiceImpl implements ISysUserService {

	@Resource
	SysUserMapper userMapper;
	
	@Override
	public List<SysUserVO> queryAllAdminUser() {
		return userMapper.queryAllAdminUser();
	}
}
