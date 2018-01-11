package com.xunge.service.tool.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.xunge.model.system.user.SysUserVO;
import com.xunge.service.tool.IAuditorService;

@Service
public class AuditorService implements IAuditorService{

	@Override
	public List<SysUserVO> getAuditor(String id) {
		List<SysUserVO> list = new ArrayList<SysUserVO>();
		SysUserVO vo = new SysUserVO();
		vo.setUserId("123");
		vo.setUserName("测试审核人");
		list.add(vo);
		return list;
	}
}
