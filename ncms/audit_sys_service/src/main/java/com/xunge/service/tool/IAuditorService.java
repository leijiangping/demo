package com.xunge.service.tool;

import java.util.List;

import com.xunge.model.system.user.SysUserVO;

public interface IAuditorService {

	public List<SysUserVO> getAuditor(String id);
}
