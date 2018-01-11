package com.xunge.dao.basedata.system;

import java.util.List;

import com.xunge.model.system.SysUserVO;

public interface SysUserMapper {

	public List<SysUserVO> queryAllAdminUser();
}
