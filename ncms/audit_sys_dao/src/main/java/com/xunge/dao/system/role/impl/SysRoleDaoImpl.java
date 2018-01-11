package com.xunge.dao.system.role.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.system.role.ISysRoleDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.system.province.SysProvinceVO;
import com.xunge.model.system.role.SystemRoleVO;
import com.xunge.model.system.rolemenu.RoleMenuVO;
import com.xunge.model.system.roleuser.RoleUserVO;
import com.xunge.model.system.user.SysRoleVO;
import com.xunge.model.system.user.SysUserVO;



public class SysRoleDaoImpl extends AbstractBaseDao implements ISysRoleDao {


	final String Namespace = "com.xunge.dao.system.SysRoleVOMapper.";
	final String Namespace2 = "com.xunge.dao.system.SysUserMapper.";

	
	public Page<List<SystemRoleVO>> queryAllSysRole(int state,int pageNumber,int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"queryAllSysRole",state);
		return PageInterceptor.endPage();
	}
	@Override
	public Page<List<SystemRoleVO>> querySysRoleByName(Map<String, Object> paramMap,int pageNumber,int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"querySysRole",paramMap);
		return PageInterceptor.endPage();

	}

	@Override
	public int insertRole(SystemRoleVO sysRoleVO) {
		
		return this.getSqlSession().insert(Namespace+"insertRole", sysRoleVO);
	}
	
	@Override
	public int updateRole(SystemRoleVO sysRoleVO, String roleId) {
		return this.getSqlSession().update(Namespace+"updateRole", sysRoleVO);
		
		
	}
	
	/**
	 * 修改用户角色关系状态
	 */
	@Override
	public int updateUserRoleState(Map<String, Object> map) {
		return this.getSqlSession().update(Namespace+"updateUserRoleState",map);
	}

	@Override
	public int deleteRole(Map<String, Object> paramMap) {
		return this.getSqlSession().update(Namespace+"deleteByPrimaryKey", paramMap);
		
	}
	
	@Override
	public int updateState(Map<String, Object> paramMap) {
		return this.getSqlSession().update(Namespace+"updateState", paramMap);
	}


	@Override
	public List<String> queryAllUserId(String roleId) {
		List<String> lsst = this.getSqlSession().selectList(Namespace+"queryAllUserId",roleId);
		return lsst;
	}
	
	@Override
	public List<SysProvinceVO> queryAllProvince(Map<String, Object> paramMap) {
		return this.getSqlSession().selectList(Namespace+"queryAllProvince", paramMap);
	}
/**
 * 根据id查询对应user
 */
	@Override
	public List<SystemRoleVO> queryAllRoleById(String roleId) {
		return this.getSqlSession().selectList(Namespace+"queryRoleById",roleId);
	}

	
	

	@Override
	public int insertPatchRoleUser(Map<String, Object> paramMap) {
		return this.getSqlSession().insert(Namespace2+"patch", paramMap);
	}

	@Override
	public int deletePatchRoleUser(Map<String, Object> paramMap) {
		return this.getSqlSession().delete(Namespace2+"deleteArray", paramMap);
	}
	
	//用户菜单关系
	@Override
	public int insertRoleMenu(Map<String, Object> rolemenuMap) {
		return this.getSqlSession().insert(Namespace + "insert", rolemenuMap);
	}

	@Override
	public void delRoleMenu(String roleId) {
		this.getSqlSession().delete(Namespace+"delRoleMenu",roleId);
		
	}

	@Override
	public List<String> queryMenuId(String roleId) {
		List<String> lsst = this.getSqlSession().selectList(Namespace+"queryMenuId",roleId);
		return lsst;
	}
	
	@Override
	public List<String> queryMenuId(Map map) {
		List<String> lsst = this.getSqlSession().selectList(Namespace+"queryAllRoleByIdMap",map);
		return lsst;
	}

	@Override
	public List<String> queryAllRoleUserById(String roleId) {
		return  this.getSqlSession().selectList(Namespace+"queryAllRoleUserById",roleId);
	}

	@Override
	public List<SystemRoleVO> queryAllRoleName(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(Namespace+"queryAllRoleName",paramMap);
	}

	@Override
	public Page<List<SystemRoleVO>> queryAllRole(int roleState,int pageNumber,int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"querySysRole",roleState);
		return PageInterceptor.endPage();
	}
	@Override
	public List<SysUserVO> queryUserByRoleName(Map<String, Object> roleIdMap) {
		return this.getSqlSession().selectList(Namespace2+"queryUserByRoleName",roleIdMap);
	}
	@Override
	public List<SystemRoleVO> queryRoleByRoleName(Map<String, Object> paramMaps) {
		return this.getSqlSession().selectList(Namespace+"queryRoleByRoleName",paramMaps);
	}
	@Override
	public List<String> queryRoleNameById(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryRoleNameById",map);
	}
	@Override
	public List<SystemRoleVO> queryRoleByMenuId(Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryRoleByMenuId",paraMap);
	}

}
