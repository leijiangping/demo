package com.xunge.dao.system.role;

import java.util.List;
import java.util.Map;

import com.xunge.model.SysDataAuthMenuTreeVO;

public interface ISysDataAuthMenuTreeDao {
	public List<SysDataAuthMenuTreeVO> queryAllMenuTree(Map<String,Object> paraMap);
}
