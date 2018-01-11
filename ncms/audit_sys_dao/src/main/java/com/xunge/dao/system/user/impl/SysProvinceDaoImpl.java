package com.xunge.dao.system.user.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.system.user.ISysUserDao;
import com.xunge.dao.system.user.ISysProvinceDao;
import com.xunge.model.SysProvinceTreeVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.system.roleuser.RoleUserVO;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.system.user.SysUserVO;

/**
 * 用户管理dao实现类
 * 
 */
public class SysProvinceDaoImpl extends AbstractBaseDao implements ISysProvinceDao {

	final String Namespace = "com.xunge.dao.SysProvinceDao.";

	@Override
	public List<SysProvinceTreeVO> queryOnePro(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(Namespace+"queryOnePro",paraMap);
	}

	@Override
	public List<SysProvinceTreeVO> queryAllSimpleProvince() {
		return this.getSqlSession().selectList(Namespace+"queryAllSimpleProvince");
	}

}
