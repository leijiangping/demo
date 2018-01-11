package com.xunge.dao.system.user;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.system.roleuser.RoleUserVO;
import com.xunge.model.system.user.SysUserVO;

public interface ISysUserDao {
	/**
	 * 根据roleid查询用户
	 */
	public Page<List<SysUserVO>> queryAllUserByRoleId(Map<String,Object> paraMap,int pageNumber,int pageSize);
	/**
	 * 根据userid查询用户
	 */
	public SysUserVO queryAllUser(String userId);
	/**
	 * 根据登陆用户名查找到用户信息
	 */
	SysUserVO getByUserLoginName(String userLoginname);
	/**
	 * 根据登陆用户名和省份标记查询用户信息
	 * @param userLoginname
	 * @param prvFlag
	 * @return
	 */
	SysUserVO getByUserLoginNamePrvFlag(Map<String,Object> map);
	
	/**
	 * 根据登陆用户ID找到用户信息
	 */
	SysUserVO getByUserId(String userId);
	
	/**
	 * 查询全部用户信息
	 */
	 Page<List<SysUserVO>> querySysUser(String prvId,int pageNumber,int pageSize);
	
	/**
	 * 添加用户
	 */
	int insertSysUser(SysUserVO sysUser);

	/**
	 * 根据用户ID修改用户信息
	 */
	int updateSysUserByUserId(SysUserVO sysUser);
	
	/**
	 * 根据用户账号 用户名 模糊查找用户
	 */
	Page<List<SysUserVO>> querySysUserByname(Map<String, Object> paramMap,int cur_page_num,int page_count);
	
	/**
	 * 修改用户状态
	 */
	int updateUserStateBatch(Map<String, Object> paramMap);
	
	/**
	 * 根据登陆用户查找省份编码信息
	 */
	SysRegionVO queryPrvIdByUser(SysUserVO user);
	
	/**
	 * 根据登陆用户查找角色关联信息
	 */
	List<String> queryRoleIdsByUser(SysUserVO user);
	
	/**
	 * 添加用户角色信息
	 */
	public int insertRoleUser(Map map);
	
	/**
	 * 根据用户ID查找所有角色信息
	 */
	public List<String> queryUserRole(Map map);
	
	/**
	 * 根据用户ID查找所有角色用户关系信息对象
	 */
	public List<RoleUserVO> queryUserRoleVOByUserId(Map<String, Object> map);
	
	/**
	 * 根据用户ID删除所有角色信息
	 */
	public void deleteRoleByUsreID(String userId); 
	
	/**
	 * 增加用户部门关系
	 */
	int insertDepartmentUser(Map map );
	
	/**
	 * 通过用户ID查找用户所有的部门
	 */
	public List<String> queryUserDepartment(Map map);
	
	/**
	 * 通过用户ID删除用户所有的部门
	 */
	public void deleteDepartmentByUsreID(String userId); 
	/**
	 * 新增用户区县关系
	 */
	public int insertUserRegion(Map<String, Object> userRegionMap);
	
	/**
	 * 修改用户角色关系状态
	 */
	public int updateUserRoleState(Map<String, Object> map);
	
	/**
	 * 修改用户部门关系状态
	 */
	public int updateUserDeptState(Map<String, Object> map);
	
	/**
	 * 根据用户id查询所有区县id
	 */
	public List<String> queryRegionId(Map<String, Object> paraMap);
	/**
	 * 修改用户区县状态
	 */
	public int updateUserRegion(Map<String, Object> userRegionMap);

	/**
	 * 查询所有用户以及相关部门、角色
	 */
	public Page<List<SysUserVO>> queryUserAll(Map<String, Object> paraMap,int pageNumber,int pageSize);

	/**
	 * 获取用户信息
	 * @author SongJL
	 */
	public SysUserVO queryUserInfo(Map<String, Object> paraMap);
	/**
	 * 修改用户信息
	 * @author SongJL
	 */
	public int updateUserInfo(Map<String, Object> paraMap);
	/**
	 * 根据角色id查询用户信息
	 * @param paraMap
	 * @return
	 */
	public List<SysUserVO> queryUserByRole(Map<String,Object> paraMap);
	/**
	 * 根据用户id查询用户名称和电话
	 * @param paraMap
	 * @return
	 */
	public SysUserVO queryUserByUserId(Map<String,Object> paraMap);
	/**
	 * 根据用户id查询用户名称和用户id
	 * @param paraMap
	 * @return
	 */
	public SysUserVO queryUserIdByUserId(Map<String,Object> paraMap);
	
	/**
	 * 根据登陆用户名及省份ID查找用户判断相同省份有无相同用户
	 */
	public List<SysUserVO> queryUserByLoginNameAndPrvId(Map<String, Object> paraMap);
}
