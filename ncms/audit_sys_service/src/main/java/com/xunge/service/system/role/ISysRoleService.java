package com.xunge.service.system.role;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.model.system.province.SysProvinceVO;
import com.xunge.model.system.role.SystemRoleVO;
import com.xunge.model.system.user.SysUserVO;
/**
 * 角色service
 * @author xup
 *
 */
/**
 * 角色信息维护service接口
 *
 */
public interface ISysRoleService {  
    /**
     * 新建角色菜单关系
     * @author changwq
     * @param roleId
     * @param list
     * @return
     * 
     */
	/**
	 * 增加角色功能菜单
	 * @param roleId
	 * @param list
	 * @return
	 */
	public String insertRoleMenu(String roleId,List<String> list);
	  /**
	   * 根据角色id查询菜单id
	   * @author changwq
	   * @param roleId
	   * @return
	   */
	/**
	 * 根据角色ID查询菜单信息
	 * @param roleId
	 * @return
	 */
	public List<String> queryMenuId(String roleId);

	/**
	 * 根据角色状态查询所有角色
	 * @param roleState
	 * @param cur_page_num
	 * @param page_count
	 * @return
	 */
	public Page<List<SystemRoleVO>> queryAllRole(int roleState,int cur_page_num,int page_count);
	/**
	 * 查询所有角色信息
	 * @author changwq
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	/**
	 * 查询所有角色
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<List<SystemRoleVO>> queryAllSysRole(int pageNumber,int pageSize);
	/**
	 * 条件查询角色列表，根据传入条件
	 * @return
	 */
	public Page<List<SystemRoleVO>> querySysRoleByName(HttpServletRequest request,String roleCode,String RoleName,int cur_page_num,int page_count,String prvId);
	/**
	 * 添加角色信息
	 * @return
	 */
	public String insertRole(HttpServletRequest request);
	
	/**
	 * 删除角色信息
	 * @param sysRoleVO
	 * @return
	 */
	public String deleteRole(List<String> list);
	/**
	 * 修改角色信息
	 * @param sysRoleVO
	 * @param roleId
	 * @return
	 */
	public String updateRole(SystemRoleVO sysRoleVO,String roleId);
	/**
	 * 启用角色
	 * @param sysRoleVO
	 * @return
	 */
	public String openUse(List<String> sysRoleVO);
	/**
	 * 停用角色
	 * @param sysRoleVO
	 * @return
	 */
	public String closeUse(List<String> sysRoleVO);
	/**
	 * 给用户赋予角色
	 * @param request
	 * @return
	 */
	public String dispatchRoleToUser(HttpServletRequest request);
	/**
	 * 通过id查询角色信息
	 * @param roleId
	 * @return
	 */
	public List<SystemRoleVO> queryAllRoleById(String roleId);
	/**
	 * 查询所有用户信息
	 * @param prvId
	 * @param cur_page_num
	 * @param page_count
	 * @return
	 */
	public Page<List<SysUserVO>> queryAllUser(String prvId,int cur_page_num,int page_count);
	/**
	 * 查询所有省份信息，根据id
	 * @param prv_id
	 * @return
	 */
	public List<SysProvinceVO> queryAllProvinceRedis(String prv_id);

	/**
	 * 批量查询功能菜单ID
	 */
	public List<String> queryMenuId(Map map);
	

	/**
	 * 查询所有角色名称
	 * @return
	 */
	public List<SystemRoleVO> queryAllRoleName(Map<String, Object> paramMap);
	
	/**
	 * 根据角色id集合查询用户信息
	 * @param roleId
	 * @return
	 */
	public List<SysUserVO> queryUserByRoleName(List<String > roleIdLists,UserLoginInfo info);

	/**
	 * 根据角色id集合查询用户信息
	 * @param roleIds
	 * @param regId 数据所属区县
	 * @return
	 */
	public List<SysUserVO> queryUserByRoleName(List<String > roleIdLists,UserLoginInfo info,String regId);
	
	/**
	 * 根据角色id集合查询用户信息
	 * @param roleId
	 * @return
	 */
	public List<SysUserVO> queryUserByRoleName(List<String > roleIdLists);
	/**
	 * 根据角色ID查询角色信息
	 * @param list
	 * @return
	 */
	public List<String> queryRoleNameById(List<String > list);
	/**
	 * 根据菜单id查询角色对象集合
	 * @param menuId
	 * @return
	 */
	public List<SystemRoleVO> queryRoleByMenuId(String menuId,String prvId);
}