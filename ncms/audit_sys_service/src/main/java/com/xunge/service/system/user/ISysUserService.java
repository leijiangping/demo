package com.xunge.service.system.user;

import java.util.List;
import java.util.Map;

import com.xunge.model.system.region.SysRegionVO;
import com.xunge.core.page.Page;
import com.xunge.model.system.user.SysUserVO;

/**
 * 用户信息维护service接口
 *
 */
public interface ISysUserService {
	/**
	 * 根据roleId查找user对象
	 */
	public Page<List<SysUserVO>> queryAllUserByRoleId(String roleId,int pageNumber,int pageSize);
	/**
	 * 根据用户id查找区县id
	 */
	public List<String> queryRegionId(String userId);
	/**
	 * 新增用户区县关系
	 */
	public String insertUserRegion(String roleId, List<String> list);
	/**
	 * 根据登陆用户名查找用户
	 */
	public SysUserVO queryByUserLoginNameRedis(String userLoginname, String usrePassword,String prvFlag) throws Exception ;
	/**
	 * 根据登陆用户名查找用户 add by HuZhixiang
	 */
	public SysUserVO queryByUserLoginNameRedis(String userLoginname, String prvFlag);
	/**
	 * 根据登陆用户名查找用户
	 */
	public SysUserVO queryByUserLoginNameRedis(String userLoginname);

	/**
	 * 根据登陆用户ID找到用户信息
	 */
	public Map queryUserAllInfoByUserIdRedis(String userId,String prvId);

	/**
	 * 添加用户
	 */
	public String insertSysUser(SysUserVO user,Map map);

	/**
	 * 根据用户ID修改用户信息
	 */
	public String updateSysUserByUserId(SysUserVO user,Map map);

	/**
	 * 根据用户账号 用户名 模糊查找用户
	 */
	public Page<List<SysUserVO>> querySysUserByname(String prvId,String userLoginName, String userName, int cur_page_num,int page_count);

	/**
	 * 批量停用用户
	 */
	public String closeUse(List<String> userIds);

	/**
	 * 批量启用用户
	 */
	public String openUse(List<String> userIds);

	/**
	 * 删除用户
	 */
	public String deleteUser(List<String> userIds);
	
	/**
	 * 添加角色关系
	 */
	public int insertRoleUser(Map map);
	
	/**
	 * 根据用户Id获得其所有的角色roleId数组
	 */
	public List<String> queryUserRole(Map map);

	/**
	 * 删除此用户下的所有角色
	 */
	public void deleteRoleByUsreID(String userId);
	
	/**
	 * 增加用户部门关系
	 */
	public int insertDepartmentUser(Map map);
	
	/**
	 * 通过用户ID查找用户所有的部门
	 */
	public List<String> queryUserDepartment(Map map);
	
	/**
	 * 删除此用户下的所有部门
	 */
	public void deleteDepartmentByUsreID(String userId);
	/**
	 * 查询所有用户以及相关部门、角色
	 */
	public Page<List<SysUserVO>> queryUserAll(int pageNumber,int pageSize);
		
	/**
	 * 修改用户角色关系状态
	 */
	public int updateUserRoleState(Map<String, Object> map);
	
	/**
	 * 修改用户部门关系状态
	 */
	public int updateUserDeptState(Map<String, Object> map);
	
	/**
	 * 获取用户信息
	 * @author SongJL
	 */
	public SysUserVO queryUserInfoRedis(String uId);
	/**
	 * 更新用户密码
	 * @author SongJL
	 */
	public int updateUserPswd(Map<String, Object> paraMap);
	/**
	 * 更新用户信息
	 * @author SongJL
	 */
	int updateUserInfo(Map<String, Object> paraMap);
	/**
	 * 根据角色id查询用户集合
	 * @param roleId
	 * @return
	 */
	public List<SysUserVO> queryUserByRole(String roleId);
	/**
	 * 根据用户id查询用户名称和电话
	 * @param userId
	 * @return
	 */
	public SysUserVO queryUserByUserId(String userId);
	/**
	 * 新增用户发送短消息
	 * @param paraMap
	 * @author changwq
	 */
	public void sendMsg(Map<String, Object> paraMap);
	
	/**
	 * 根据登陆用户名及省份ID查找用户判断相同省份有无相同用户
	 */
	public List<SysUserVO> queryUserByLoginNameAndPrvId(Map<String, Object> paraMap);
}