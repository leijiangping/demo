package com.xunge.dao.system.role;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.system.province.SysProvinceVO;
import com.xunge.model.system.role.SystemRoleVO;
import com.xunge.model.system.user.SysUserVO;



public interface ISysRoleDao {
	
	/**
	 * 添加角色信息
	 * @param sysRoleVO
	 * @return
	 */
	public int insertRole(SystemRoleVO sysRoleVO);
	/**
	 * 删除角色信息
	 * @param paramMap
	 * @return
	 */
	public int deleteRole(Map<String, Object> paramMap);
	/**
	 * 修改角色信息
	 * @param sysRoleVO
	 * @param roleId
	 * @return
	 */
	public int updateRole(SystemRoleVO sysRoleVO,String roleId);
	
	/**
	 * 根据角色状态查询所有角色
	 * @param roleState
	 * @param cur_page_num
	 * @param page_count
	 * @return
	 */
	public Page<List<SystemRoleVO>> queryAllRole(int roleState,int cur_page_num,int page_count);
	
	/**
	 * 修改用户角色关系状态
	 * 原：无此方法  2017/7/28 10:00
	 */
	public int updateUserRoleState(Map<String, Object> map);
	

	/**
	 * 	查询省份信息
	 */
	public List<SysProvinceVO> queryAllProvince(Map<String, Object> paramMap);
	/**
	 * 根据状态查询角色
	 * @author xup
	 */
	public Page<List<SystemRoleVO>> queryAllSysRole(int state,int pageNumber,int pageSize);
	/**
	 * 根据条件查询角色信息
	 * @param paramMap
	 * @param cur_page_num
	 * @param page_count
	 * @return
	 */
	public Page<List<SystemRoleVO>> querySysRoleByName(Map<String, Object> paramMap,int cur_page_num,int page_count);
	/**
	 * 启用、停用
	 * @param paramMap
	 * @return
	 */
	public int updateState(Map<String, Object> paramMap);
	/**
	 * 通过角色id查询用户信息
	 * @param roleId
	 * @return
	 */
	public List<String> queryAllUserId(String roleId);
	/**
	 * 通过角色id查询角色
	 * @param roleId
	 * @return
	 */
	public List<SystemRoleVO> queryAllRoleById(String roleId);
	/**
	 * 批量添加roleuser中间表信息
	 * @param paramMap
	 * @return
	 */
	public int insertPatchRoleUser(Map<String, Object> paramMap);
	/**
	 * 批量修改roleuser中间表信息
	 * @param paramMap
	 * @return
	 */
	public int deletePatchRoleUser(Map<String, Object> paramMap);
	/**
	 * 添加角色菜单
	 * @param rolemenuMap
	 * @return
	 */
	public int insertRoleMenu(Map<String, Object> rolemenuMap);
	/**
	 * 删除角色菜单
	 * @param roleId
	 */
	public void delRoleMenu(String roleId);
	/**
	 * 根据角色id查询菜单信息
	 * @param roleId
	 * @return
	 */
	public List<String> queryMenuId(String roleId);
	/**
	 * 通过角色id查询roleuser中间表信息
	 * @param roleId
	 * @return
	 */
	public List<String> queryAllRoleUserById(String roleId);
	/**
	 * 查询所有角色名
	 * @return
	 */
	public List<SystemRoleVO> queryAllRoleName(Map<String, Object> paramMap);
	/**
	 * 批量查询功能菜单ID
	 */
	public List<String> queryMenuId(Map map);
	/**
	 * 通过角色id集合查询用户信息
	 * @param roleIdMap
	 * @return
	 */
	public List<SysUserVO> queryUserByRoleName(Map<String, Object> roleIdMap);
	/**
	 * 根据角色名称查询角色
	 * @author xup
	 */
	public List<SystemRoleVO> queryRoleByRoleName(Map<String, Object> paramMaps);
	/**
	 * 根据id查询角色名称
	 * @author xup
	 */
	public List<String> queryRoleNameById(Map<String, Object> map);
	/**
	 * 根据菜单id查询角色id和省份id
	 * @param paraMap
	 * @return
	 */
	public List<SystemRoleVO> queryRoleByMenuId(Map<String,Object> paraMap);
	
}
