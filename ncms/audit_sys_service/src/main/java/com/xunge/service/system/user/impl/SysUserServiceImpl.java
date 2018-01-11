package com.xunge.service.system.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xunge.comm.StateComm;
import com.xunge.comm.system.LoginComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.comm.system.SystemConfComm;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.CollectionUtil;
import com.xunge.core.util.DateUtils;
import com.xunge.core.util.MD5Util;
import com.xunge.core.util.PropertiesLoader;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.sysSmsSendHistroy.ISysSmsConfigDao;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.dao.system.role.ISysRoleDao;
import com.xunge.dao.system.user.ISysUserDao;
import com.xunge.model.smsSendHistroy.SysSmsConfigVO;
import com.xunge.model.smsSendHistroy.SysSmssendHistroyVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.system.user.SysUserVO;
import com.xunge.model.system.userregion.UserRegionVO;
import com.xunge.model.util.SmsRecvBackVO;
import com.xunge.service.sysSmsSendHistroy.ISysSmssendHistroyService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.sms.ISmsSendTools;
import com.xunge.service.system.user.ISysUserService;

/**
 * 用户信息维护service实现类
 * @author yuefy
 *
 */
public class SysUserServiceImpl implements ISysUserService {

	private ISysUserDao sysUserDao;
	
	private ISysRegionDao sysRegionDao;

	private ISysRoleDao sysRoleDao;
	
	private ISysRegionService sysRegionService;
	
	private ISysSmssendHistroyService sysSmssendHistroyService;
	
	private ISmsSendTools smsSendTools;
	
	private ISysSmsConfigDao sysSmsConfigDao;

	/**
	 * 根据登陆用户名查找用户信息
	 * @throws Exception 
	 */
	public SysUserVO queryByUserLoginNameRedis(String userLoginname, String usrePassword,String prvFlag) throws Exception {
		Map<String,Object> map=Maps.newHashMap();
		map.put("userLoginname", userLoginname);
		map.put("prvFlag", prvFlag);
		SysUserVO findUser = this.sysUserDao.getByUserLoginNamePrvFlag(map);
		if(findUser == null){
    		throw new BusinessException(PromptMessageComm.USER_NOT_FIND);
    	}
    	if(!findUser.getUserPassword().equals(MD5Util.encode(usrePassword)) &&
    			!findUser.getUserPassword().equals(usrePassword)){
    		throw new BusinessException(PromptMessageComm.LOGINNAME_ERROR);
    	}
    	if((StateComm.STATE_str9).equals(findUser.getUserState())){
    		throw new BusinessException(PromptMessageComm.USER_STOP);
    	}
    	if((StateComm.STATE_str__1).equals(findUser.getUserState())){
    		throw new BusinessException(PromptMessageComm.USER_DELETE);
    	}
    	if(findUser != null && findUser.getRegId() != null){
    		if(!SystemConfComm.PRVID.equals(findUser.getRegId())){
	    		SysRegionVO sysRegionVO = sysRegionDao.getRegionById(findUser.getRegId());
	    		if(sysRegionVO != null ){
	    			if(sysRegionVO.getRegState() != null){
	    				if(sysRegionVO.getRegState() != StateComm.STATE_0){
	    					throw new BusinessException(PromptMessageComm.USER_REGION_ERROR);
	    				}
	    			}
	    		}
    		}
    	}
    	
		return findUser;
	}
	
	/**
	 * 根据登陆用户名查找用户信息 add by HuZhixiang
	 */
	public SysUserVO queryByUserLoginNameRedis(String userLoginname, String prvFlag) {
		Map<String,Object> map=Maps.newHashMap();
		map.put("userLoginname", userLoginname);
		map.put("prvFlag", prvFlag);
		SysUserVO findUser = this.sysUserDao.getByUserLoginNamePrvFlag(map);
		
    	if(findUser!=null){
			if((StateComm.STATE_str9).equals(findUser.getUserState())){
				throw new BusinessException(PromptMessageComm.USER_STOP);
			}
			if((StateComm.STATE_str__1).equals(findUser.getUserState())){
				throw new BusinessException(PromptMessageComm.USER_DELETE);
			}
		}
		return findUser;
	}

	/**
	 * 根据登陆用户ID查找用户信息
	 */
	@Override
	public Map<String , Object> queryUserAllInfoByUserIdRedis(String userId , String prvId) {
		SysUserVO user = sysUserDao.getByUserId(userId);
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("prvId", prvId);
		map.put("regId", user.getRegId());
		map.put("state", StateComm.STATE_0);
		List<SysRegionVO> sysReguinVOs = sysRegionService.getAddress(map);
		List<SysRegionVO> sysReguins = sysRegionService.getUserAddress(map);
		map.put("sysReguinVOs", sysReguinVOs);
		map.put("sysReguins", sysReguins);
		map.put("user", user);
		return map;
	}

	/**
	 * 添加新用户信息
	 */
	@Override
	public String insertSysUser(SysUserVO user,Map map) {
		int result = 0;
		if(user != null){
			result = sysUserDao.insertSysUser(user);
		}
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * 修改用户信息
	 */
	@Override
	public String updateSysUserByUserId(SysUserVO user,Map map) {
		int result = sysUserDao.updateSysUserByUserId(user);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}
	
	
	/**
	 * 根据登陆用户名，用户姓名模糊查找用户信息
	 */
	@Override
	public Page<List<SysUserVO>> querySysUserByname(String prvId,String userLoginName,
			String userName,int cur_page_num,int page_count) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("prvId", prvId);
		paramMap.put("userLoginName", userLoginName);
		paramMap.put("userName", userName);
		paramMap.put("userState", StateComm.STATE__1);
		
		return sysUserDao.querySysUserByname(paramMap,cur_page_num,page_count);
	}

	/**
	 * 删除用户 设置用户状态为已删除
	 */
	@Override
	public String deleteUser(List<String> userIds) {
		// 拼接查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userIdsList", userIds);
		paramMap.put("userState", StateComm.STATE__1);
		paramMap.put("relationState", StateComm.STATE_9);
		sysUserDao.updateUserDeptState(paramMap);
		sysUserDao.updateUserRoleState(paramMap);
		int result = sysUserDao.updateUserStateBatch(paramMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * 停用用户 设置用户状态为停用
	 */
	@Override
	public String closeUse(List<String> userIds) {
		
		// 拼接查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userIdsList", userIds);
		paramMap.put("userState", StateComm.STATE_9);
		paramMap.put("relationState", StateComm.STATE_9);
		sysUserDao.updateUserRoleState(paramMap);
		sysUserDao.updateUserDeptState(paramMap);
		int result = sysUserDao.updateUserStateBatch(paramMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * 开启用户 设置用户状态为启用
	 */
	@Override
	public String openUse(List<String> userIds) {
		
		// 拼接查询条件
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userIdsList", userIds);
		paramMap.put("userState", StateComm.STATE_0);
		
		int result = sysUserDao.updateUserStateBatch(paramMap);
		return (result == 0) ? RESULT.FAIL_0 : RESULT.SUCCESS_1;
	}

	/**
	 * 添加用户角色信息
	 */
	@Override
	public int insertRoleUser(Map map) {
		return sysUserDao.insertRoleUser(map);
	}

	/**
	 * 根据用户ID查找所有角色信息
	 */
	@Override
	public List<String> queryUserRole(Map map) {
		return sysUserDao.queryUserRole(map);
	}

	/**
	 * 删除此用户下的所有角色
	 */
	@Override
	public void deleteRoleByUsreID(String userId) {
		sysUserDao.deleteRoleByUsreID(userId);
	}

	/**
	 * 增加用户部门关系
	 */
	@Override
	public int insertDepartmentUser(Map map) {
		return this.sysUserDao.insertDepartmentUser(map);
	}

	/**
	 * 通过用户ID查找用户所有的部门
	 */
	@Override
	public List<String> queryUserDepartment(Map map) {
		return sysUserDao.queryUserDepartment(map);
	}

	/**
	 * 通过用户ID删除用户所有的部门
	 */
	@Override
	public void deleteDepartmentByUsreID(String userId) {
		sysUserDao.deleteDepartmentByUsreID(userId);
	}

	public ISysUserDao getSysUserDao() {
		return sysUserDao;
	}

	public void setSysUserDao(ISysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	public ISysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}

	public void setSysRoleDao(ISysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
	}
	

	/**
	 * 修改用户区县关系
	 */
	@Override
	public String insertUserRegion(String userId, List<String> list) {
		int state = StateComm.STATE_0;
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("state", state);
		paraMap.put("userId", userId);
		List<String> oldlist = sysUserDao.queryRegionId(paraMap);
		
		List<String> deleteList = CollectionUtil.getDifferentList(oldlist, list);
		List<String> insertList = CollectionUtil.getDifferentList(list, oldlist);
		
		try {
			Map<String, Object> userRegionMap = new HashMap<String, Object>();
			List<UserRegionVO> insertUserRegionList = new ArrayList<UserRegionVO>();
			
			userRegionMap.put("oldState", StateComm.STATE_0);
			userRegionMap.put("deleteList",deleteList);
			userRegionMap.put("userId",userId);
			userRegionMap.put("newState", StateComm.STATE_9);
			if(deleteList != null && deleteList.size() > 0){
				sysUserDao.updateUserRegion(userRegionMap);
			}
			for (int i = 0; i < insertList.size(); i++) {
				String userRegionId = SysUUID.generator();
				UserRegionVO userRegionVO = new UserRegionVO(userRegionId,userId,insertList.get(i));
				userRegionVO.setRelationState(StateComm.STATE_0);
				insertUserRegionList.add(userRegionVO);
			}
			userRegionMap.put("insertUserRegionList",insertUserRegionList);
			sysUserDao.insertUserRegion(userRegionMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			return RESULT.FAIL_0;
		}
			
	
	}
	/**
	 * 根据用户id查找区县id
	 */
	@Override
	
	public List<String> queryRegionId(String userId) {
		int state = StateComm.STATE_0;
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("state", state);
		paraMap.put("userId", userId);
		return sysUserDao.queryRegionId(paraMap);
	}

	@Override
	public Page<List<SysUserVO>> queryAllUserByRoleId(String roleId,int pageNumber,
			int pageSize) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		int state = StateComm.STATE_0;
		paraMap.put("state", state);
		paraMap.put("roleId", roleId);
		return sysUserDao.queryAllUserByRoleId(paraMap, pageNumber, pageSize);
	}

	@Override
	public Page<List<SysUserVO>> queryUserAll(int pageNumber,
			int pageSize) {
		int onState = StateComm.STATE_0;
		int downState = StateComm.STATE_9;
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("onState", onState);
		paraMap.put("downState", downState);
		return sysUserDao.queryUserAll(paraMap, pageNumber, pageSize);
	}

	
	
	/**
	 * 修改用户角色关系状态
	 */
	@Override
	public int updateUserRoleState(Map<String, Object> map) {
		return sysUserDao.updateUserRoleState(map);
	}
	
	/**
	 * 修改用户部门关系状态
	 */
	@Override
	public int updateUserDeptState(Map<String, Object> map) {
		return sysUserDao.updateUserDeptState(map);
	}

	@Override
	public SysUserVO queryUserInfoRedis(String uId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("userId", uId);
		return sysUserDao.queryUserInfo(paraMap);
	}

	@Override
	public int updateUserPswd(Map<String, Object> paraMap) {
		
		int result = sysUserDao.updateUserInfo(paraMap);

		//派发完成短信提醒 
		PropertiesLoader prop = new PropertiesLoader("\\properties\\sysConfig.properties");
		String tempid = prop.getProperty("sys_user_update_tempid");
		UserLoginInfo user = (UserLoginInfo) paraMap.get("user");
		Map<String,Object> smsMap = new HashMap<String,Object>();
		smsMap.put("mobiles",user.getUser_phone());
		smsMap.put("tempid",tempid);
		smsMap.put("login_name",user.getUser_name());
		smsMap.put("login_pswd",paraMap.get("newUserpswd"));
		SmsRecvBackVO smsbkObj = smsSendTools.ChangeUserPswdSmsSend(smsMap);
		if(smsbkObj != null){
			//查询短信模板信息
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("tempCode",tempid);
			SysSmsConfigVO ssc = sysSmsConfigDao.querySmsModelMsg(map);
			//创建短信下发回执信息对象
			SysSmssendHistroyVO sysSmssendHistroyVO = new SysSmssendHistroyVO();
			sysSmssendHistroyVO.setSysSmssendHistoryId(SysUUID.generator());
			sysSmssendHistroyVO.setBussTableId(paraMap.get("user_id")+"");
			if(ssc != null){
				sysSmssendHistroyVO.setBussName(ssc.getTempName());
			}
			sysSmssendHistroyVO.setPrvId(user.getPrv_id());
			sysSmssendHistroyVO.setSmsSenderId(user.getUser_id());
			sysSmssendHistroyVO.setSmsSenderName(user.getUser_name());
			sysSmssendHistroyVO.setSmsRecvUserId(user.getUser_id());
			sysSmssendHistroyVO.setSmsTempId(tempid);
			String date = DateUtils.formatDateTime(new Date());
			sysSmssendHistroyVO.setSendDatetime(date);
			sysSmssendHistroyVO.setSendBackMsg(smsbkObj.getBackInfo());
			sysSmssendHistroyVO.setIsSendSuccess(smsbkObj.getBackResult()?0:1);
			//新增短信下发回执信息
			sysSmssendHistroyService.insertSelective(sysSmssendHistroyVO);
		}
		return result;
	}

	@Override
	public int updateUserInfo(Map<String,Object> paraMap) {
		return sysUserDao.updateUserInfo(paraMap);
	}

	@Override
	public SysUserVO queryByUserLoginNameRedis(String userLoginname) {
		return sysUserDao.getByUserLoginName(userLoginname);
	}

	public ISysRegionService getSysRegionService() {
		return sysRegionService;
	}

	public void setSysRegionService(ISysRegionService sysRegionService) {
		this.sysRegionService = sysRegionService;
	}

	@Override
	public List<SysUserVO> queryUserByRole(String roleId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("roleId",roleId);
		paraMap.put("relationState",StateComm.STATE_0);
		paraMap.put("userState",StateComm.STATE_0);
		return sysUserDao.queryUserByRole(paraMap);
	}

	@Override
	public SysUserVO queryUserByUserId(String userId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("userId",userId);
		return sysUserDao.queryUserByUserId(paraMap);
	}
	
	@Override
	public void sendMsg(Map<String, Object> paraMap) {
		PropertiesLoader prop = new PropertiesLoader(PromptMessageComm.URL_SYSCONFIG);
		String tempid = prop.getProperty("sys_user_insert_tempid");
		UserLoginInfo user = (UserLoginInfo) paraMap.get("user");
		if(user == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		//派发完成短信提醒 
		Map<String,Object> smsMap = new HashMap<String,Object>();
		smsMap.put("mobiles",paraMap.get("mobiles")+"");
		smsMap.put("tempid",tempid);
		smsMap.put("login_name",paraMap.get("login_name")+"");
		smsMap.put("login_pswd",paraMap.get("login_pswd")+"");
		SmsRecvBackVO smsbkObj = smsSendTools.ChangeUserPswdSmsSend(smsMap);
		if(smsbkObj != null){
			//查询短信模板信息
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("tempCode",tempid);
			SysSmsConfigVO ssc = sysSmsConfigDao.querySmsModelMsg(map);
			//创建短信下发回执信息对象
			SysSmssendHistroyVO sysSmssendHistroyVO = new SysSmssendHistroyVO();
			sysSmssendHistroyVO.setSysSmssendHistoryId(SysUUID.generator());
			sysSmssendHistroyVO.setBussTableId(paraMap.get("user_id")+"");
			if(ssc != null){
				sysSmssendHistroyVO.setBussName(ssc.getTempName());
			}
			sysSmssendHistroyVO.setPrvId(user.getPrv_id());
			sysSmssendHistroyVO.setSmsSenderId(user.getUser_id());
			sysSmssendHistroyVO.setSmsSenderName(user.getUser_name());
			sysSmssendHistroyVO.setSmsRecvUserId(user.getUser_id());
			sysSmssendHistroyVO.setSmsTempId(tempid);
			String date = DateUtils.formatDateTime(new Date());
			sysSmssendHistroyVO.setSendDatetime(date);
			sysSmssendHistroyVO.setSendBackMsg(smsbkObj.getBackInfo());
			sysSmssendHistroyVO.setIsSendSuccess(smsbkObj.getBackResult()?0:1);
			//新增短信下发回执信息
			sysSmssendHistroyService.insertSelective(sysSmssendHistroyVO);
		}
	}
	
	@Override
	public List<SysUserVO> queryUserByLoginNameAndPrvId(Map<String, Object> paraMap){
		
		return sysUserDao.queryUserByLoginNameAndPrvId(paraMap);
	}

	public ISysSmssendHistroyService getSysSmssendHistroyService() {
		return sysSmssendHistroyService;
	}

	public void setSysSmssendHistroyService(
			ISysSmssendHistroyService sysSmssendHistroyService) {
		this.sysSmssendHistroyService = sysSmssendHistroyService;
	}

	public ISmsSendTools getSmsSendTools() {
		return smsSendTools;
	}

	public void setSmsSendTools(ISmsSendTools smsSendTools) {
		this.smsSendTools = smsSendTools;
	}

	public ISysSmsConfigDao getSysSmsConfigDao() {
		return sysSmsConfigDao;
	}

	public void setSysSmsConfigDao(ISysSmsConfigDao sysSmsConfigDao) {
		this.sysSmsConfigDao = sysSmsConfigDao;
	}

	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}

	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}

}