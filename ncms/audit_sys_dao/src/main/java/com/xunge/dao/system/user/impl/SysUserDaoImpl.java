package com.xunge.dao.system.user.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.system.user.ISysUserDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.system.roleuser.RoleUserVO;
import com.xunge.model.system.user.SysUserVO;

/**
 * 用户管理dao实现类
 * 
 */
public class SysUserDaoImpl extends AbstractBaseDao implements ISysUserDao {

	final String Namespace = "com.xunge.dao.system.SysUserMapper.";

	/**
	 * 根据登陆用户名查找用户信息
	 */
	@Override
	public SysUserVO getByUserLoginName(String userLoginname) {
		return (SysUserVO) this.getSqlSession().selectOne(
				Namespace + "getUserByLoginName", userLoginname);
	}
	public SysUserVO getByUserLoginNamePrvFlag(Map<String,Object> map) {
		
		return (SysUserVO) this.getSqlSession().selectOne(
				Namespace + "getByUserLoginNamePrvFlag", map);
	}
	/**
	 * 根据登陆用户ID找到用户信息
	 */
	@Override
	public SysUserVO getByUserId(String userId) {
		return (SysUserVO) this.getSqlSession().selectOne(
				Namespace + "getUserById", userId);
	}

	/**
	 * 添加用户
	 */
	@Override
	public int insertSysUser(SysUserVO sysUser) {
		return this.getSqlSession().insert(Namespace + "addSysUser", sysUser);
	}

	/**
	 * 修改用户
	 */
	@Override
	public int updateSysUserByUserId(SysUserVO sysUser) {
		return this.getSqlSession().update(Namespace + "updateSysUserById",
				sysUser);
	}

	/**
	 * 查询全部用户信息
	 */
	@Override
	public Page<List<SysUserVO>> querySysUser(String prvId,int cur_page_num,int page_count) {
		PageInterceptor.startPage(cur_page_num, page_count);
		this.getSqlSession().selectList(Namespace + "queryUserMsg",prvId);
		return PageInterceptor.endPage();
	}

	/**
	 * 根据登陆用户名，用户姓名模糊查找用户信息
	 */
	@Override
	public Page<List<SysUserVO>> querySysUserByname(Map<String, Object> paramMap,int cur_page_num,int page_count) {
		PageInterceptor.startPage(cur_page_num, page_count);
		this.getSqlSession().selectList(Namespace + "queryUserPrvRegMsg", paramMap);
		
		return PageInterceptor.endPage();
	}

	/**
	 * 修改用户状态
	 */
	@Override
	public int updateUserStateBatch(Map<String, Object> paramMap) {
		return this.getSqlSession().update(Namespace + "updateUserState",
				paramMap);
	}

	/**
	 * 根据登陆用户ID查找省份编码信息
	 */
	@Override
	public SysRegionVO queryPrvIdByUser(SysUserVO user) {
		return this.getSqlSession().selectOne(Namespace + "queryPrvIdByUser", user);
	}

	@Override
	public List<String> queryRoleIdsByUser(SysUserVO user) {
		return this.getSqlSession().selectList(Namespace + "queryRoleIdsByUser", user);
	}

	/**
	 * 添加用户角色信息
	 */
	@Override
	public int insertRoleUser(Map map) {
		return this.getSqlSession().insert(Namespace + "patch", map);
	}

	/**
	 * 根据用户ID查找所有角色信息
	 */
	@Override
	public List<String> queryUserRole(Map map) {
		return this.getSqlSession().selectList(Namespace + "queryUserRole",
				map);
	}

	/**
	 * 根据用户ID删除所有角色信息
	 */
	@Override
	public void deleteRoleByUsreID(String userId) {
		this.getSqlSession().delete(Namespace + "deleteRoleByUsreID", userId);
	}

	/**
	 * 增加用户部门关系
	 */
	@Override
	public int insertDepartmentUser(Map map) {
		return this.getSqlSession().insert(Namespace + "insertDeptUser", map);
	}

	@Override
	public List<String> queryUserDepartment(Map map) {
		return this.getSqlSession().selectList(Namespace + "queryUserDept", map);
	}

	@Override
	public void deleteDepartmentByUsreID(String userId) {
		this.getSqlSession().delete(Namespace + "deleteDeptByUsreID", userId);
	}

	@Override
	public int insertUserRegion(Map<String, Object> insertUserRegionMap) {
		try {
			this.getSqlSession().insert(Namespace + "insertUserRegion", insertUserRegionMap);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<String> queryRegionId(Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryRegionId",paraMap);
	}
	//修改用户区县关系状态
	@Override
	public int updateUserRegion(Map<String, Object> updateUserRegionMap) {
		return this.getSqlSession().update(Namespace+"updateUserRegion",updateUserRegionMap);
	}
	
	@Override
	public SysUserVO queryAllUser(String userId) {
		SysUserVO sysUserVO = this.getSqlSession().selectOne(Namespace+"queryAllUser",userId);
		return sysUserVO;
	}

	@Override
	public Page<List<SysUserVO>> queryAllUserByRoleId(Map<String,Object> paraMap,int pageNumber,int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"queryAllUserByRoleId",paraMap);
		return PageInterceptor.endPage();
	}

	@Override
	public Page<List<SysUserVO>> queryUserAll(Map<String, Object> paraMap,int pageNumber,int pageSize) {
		PageInterceptor.startPage(pageNumber, pageSize);
		this.getSqlSession().selectList(Namespace+"queryUserAll",paraMap);
		return PageInterceptor.endPage();
	}

	@Override
	public int updateUserRoleState(Map<String, Object> map) {
		return this.getSqlSession().update(Namespace+"updateUserRoleState",map);
	}

	@Override
	public int updateUserDeptState(Map<String, Object> map) {
		return this.getSqlSession().update(Namespace+"updateUserDeptState",map);
	}

	@Override
	public List<RoleUserVO> queryUserRoleVOByUserId(Map<String, Object> map) {
		return this.getSqlSession().selectList(Namespace+"queryUserRoleVO",map);
	}

	@Override
	public SysUserVO queryUserInfo(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryUserInfo", paraMap);
	}

	@Override
	public int updateUserInfo(Map<String, Object> paraMap) {
		return this.getSqlSession().update(Namespace+"updateUserInfo", paraMap);
	}
	@Override
	public List<SysUserVO> queryUserByRole(Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryUserByRole",paraMap);
	}
	@Override
	public SysUserVO queryUserByUserId(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryUserByUserId",paraMap);
	}
	@Override
	public SysUserVO queryUserIdByUserId(Map<String, Object> paraMap) {
		return this.getSqlSession().selectOne(Namespace+"queryUserIdByUserId",paraMap);
	}
	@Override
	public List<SysUserVO> queryUserByLoginNameAndPrvId(
			Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryUserByLoginNameAndPrvId",paraMap);
	}

}
