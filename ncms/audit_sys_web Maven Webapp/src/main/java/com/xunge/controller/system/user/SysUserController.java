package com.xunge.controller.system.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.SysUserComm;
import com.xunge.comm.system.LoginComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.comm.system.SystemConfComm;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.CollectionUtil;
import com.xunge.core.util.MD5Util;
import com.xunge.core.util.SysUUID;
import com.xunge.model.FeedBackObject;
import com.xunge.model.system.department.SysDepartmentVO;
import com.xunge.model.system.departmentuser.SysDepartmentUserVO;
import com.xunge.model.system.role.SystemRoleVO;
import com.xunge.model.system.roleuser.RoleUserVO;
import com.xunge.model.system.user.SysUserVO;
import com.xunge.service.sysSmsSendHistroy.ISysSmssendHistroyService;
import com.xunge.service.system.department.ISysDepartmentService;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.system.sms.ISmsSendTools;
import com.xunge.service.system.user.ISysUserService;


/**
 * 用户管理控制层
 * 
 */
@Controller
@RequestMapping("/asserts/tpl/system/user")
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
public class SysUserController extends BaseException{
	
	@Autowired
	private ISmsSendTools smsSendTools;

	@Autowired
	private ISysUserService userService;

	@Autowired
	private ISysRoleService roleService;

	@Autowired
	private ISysDepartmentService deptService;
	
	@Autowired
	private ISysRegionService sysRegionService;
	
	@Autowired
	private ILogService log;
	
	@Autowired
	private ISysSmssendHistroyService sysSmssendHistroyService;
	
	/**
     * 查询所有省份
     * @param list
     * @return
     */
	@RequestMapping(value="/queryAllProvince", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllProvince(HttpServletRequest request) {
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = RESULT.SUCCESS_1;
		String prv_id = loginInfo.getPrv_id();
		backObj.Obj = roleService.queryAllProvinceRedis(prv_id);
		return backObj;
	}
	
	
	/**
	 * 添加用户
	 */
	@RequestMapping("/add")
	public @ResponseBody FeedBackObject addSysUser(@ModelAttribute("user") UserLoginInfo loginUser,HttpServletRequest request) {
		if(loginUser == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		String userId = SysUUID.generator();
		String roleIds = request.getParameter("selectRoleIds");
		String depIds = request.getParameter("selectDepIds");
		SysUserVO user = new SysUserVO();
		user.setUserId(userId);
		String userPassword = SysUserComm.UPASSWORD;
		//发送短信并存储记录
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("login_pswd",userPassword);
		try {
			userPassword = MD5Util.encode(userPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setUserPassword(userPassword);
		user.setUserLoginname(request.getParameter("userLoginname").replace(" ", ""));
		user.setUserName(request.getParameter("userName").replace(" ", ""));
		user.setUserCode(request.getParameter("userCode").replace(" ", ""));
		user.setUserPhone(request.getParameter("userPhone").replace(" ", ""));
		user.setUserEmail(request.getParameter("userEmail"));
		user.setUserState(StateComm.STATE_0);
		//判断是省级用户还是集团用户
		String regId = request.getParameter("Village");
		if(regId != null && regId != ""){
			user.setUserClass(SysUserComm.UCLASS_1);
		}else{
			user.setUserClass(SysUserComm.UCLASS_0);
			regId = PromptMessageComm.SYMBOL_STR000000;
		}
		user.setRegId(regId);
		user.setUserNote(request.getParameter("userNote"));
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = userService.insertSysUser(user,paramMap);
		String[] roleId = {};
		String[] depId = {};
		if(roleIds != null){
			roleId = roleIds.split(",");
		}
		if(depIds != null){
			depId = depIds.split(",");
		}
		List<RoleUserVO> idsList = new ArrayList<RoleUserVO>();
		if(roleIds != null && roleIds != ""){
			RoleUserVO roleUserVO=null;
			for (int i = 0; i < roleId.length; i++) {
				roleUserVO=new RoleUserVO();
				roleUserVO.setRoleUserId(SysUUID.generator());
				roleUserVO.setRelationState(StateComm.STATE_0);
				roleUserVO.setRoleId(roleId[i]);
				roleUserVO.setUserId(userId);
				idsList.add(roleUserVO);
			}
			paramMap.put("idsList", idsList);
			userService.insertRoleUser(paramMap);
		}
		
		List<SysDepartmentUserVO> depidsList = new ArrayList<SysDepartmentUserVO>();
		
		if(depIds != null && depIds != ""){
			SysDepartmentUserVO departmentUserVO = null;
			for (int i = 0; i < depId.length; i++) {
				departmentUserVO = new SysDepartmentUserVO();
				departmentUserVO.setDepUserId(SysUUID.generator());
				departmentUserVO.setRelationState(StateComm.STATE_0);
				departmentUserVO.setDepId(depId[i]);
				departmentUserVO.setUserId(userId);
				depidsList.add(departmentUserVO);
			}
			paramMap.put("depidsList", depidsList);
			userService.insertDepartmentUser(paramMap);
		}
		paraMap.put("mobiles",user.getUserPhone());
		paraMap.put("login_name",user.getUserLoginname());
		paraMap.put("user",loginUser);
		userService.sendMsg(paraMap);
		
		if(backObj.success.equals(RESULT.SUCCESS_1)){
			backObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, backObj.msg);
		}else{
			backObj.success = RESULT.FAIL_0;
			backObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, backObj.msg);
		}	
		return backObj;
	}
	
	/**
	 * 修改用户信息
	 */
	@RequestMapping("/updateUser")
	public @ResponseBody
	FeedBackObject updateSysUser(@ModelAttribute("user") UserLoginInfo loginUser,HttpServletRequest request) {
		if(loginUser == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		SysUserVO sysUser = new SysUserVO();
		sysUser.setUserLoginname(request.getParameter("userLoginname"));
		sysUser.setUserName(request.getParameter("userName"));
		sysUser.setUserCode(request.getParameter("userCode"));
		String prvId = request.getParameter("userProvince1");
		if(!SystemConfComm.PRVID.equals(prvId)){
			sysUser.setRegId(request.getParameter("Village"));
		}
		sysUser.setUserId(request.getParameter("userId"));
		sysUser.setUserPhone(request.getParameter("userPhone"));
		sysUser.setUserEmail(request.getParameter("userEmail"));
		sysUser.setUserNote(request.getParameter("userNote"));
		String roleIds = request.getParameter("selectRoleIds");
		String depIds = request.getParameter("selectDepIds");
		String[] roleId = {};
		String[] depId = {};
		if(roleIds != null){
			roleId = roleIds.split(",");
		}
		if(depIds != null){
			depId = depIds.split(",");
		}
		List<String> roleIdlst = new ArrayList<String>();
		List<String> depIdlst = new ArrayList<String>();
		if(roleIds != null && roleIds != ""){
			for (String r : roleId) {
				roleIdlst.add(r);
			}
		}
		if(depIds != null && depIds != ""){
			for (String d : depId) {
				depIdlst.add(d);
			}
		}
		
		List<String> userIdsList = new ArrayList<String>();
		userIdsList.add(request.getParameter("userId"));
		map.put("userId", request.getParameter("userId"));
		map.put("userIdsList", userIdsList);
		map.put("state", StateComm.STATE_0);
		
		List<String> oldlistRole = userService.queryUserRole(map);
		List<String> oldlistDept = userService.queryUserDepartment(map);
		List<String> updateRoleList = CollectionUtil.getDifferentList(oldlistRole,roleIdlst);
		List<String> insertRoleList = CollectionUtil.getDifferentList(roleIdlst,oldlistRole);
		List<String> updateDeptList = CollectionUtil.getDifferentList(oldlistDept,depIdlst);
		List<String> insertDeptList = CollectionUtil.getDifferentList(depIdlst,oldlistDept);
		
		map.put("idsListRole", updateRoleList);
		map.put("idsListDept", updateDeptList);
		
		if(updateRoleList != null && updateRoleList.size() > 0){
			map.put("userState", StateComm.STATE_9);
			userService.updateUserRoleState(map);
		}
		
		if(updateDeptList != null && updateDeptList.size() > 0){
			map.put("userState", StateComm.STATE_9);
			userService.updateUserDeptState(map);
		}
		
		List<RoleUserVO> idsList = new ArrayList<RoleUserVO>();
		if(insertRoleList != null && insertRoleList.size() > 0){
			RoleUserVO roleUserVO = null;
			for (int i = 0; i < insertRoleList.size(); i++) {
				roleUserVO=new RoleUserVO();
				roleUserVO.setRoleUserId(SysUUID.generator());
				roleUserVO.setRelationState(StateComm.STATE_0);
				roleUserVO.setRoleId(insertRoleList.get(i));
				roleUserVO.setUserId(request.getParameter("userId"));
				idsList.add(roleUserVO);
			}
			map.put("idsList", idsList);
			map.put("userState", StateComm.STATE_0);
			userService.insertRoleUser(map);
		}
		
		List<SysDepartmentUserVO> depidsList = new ArrayList<SysDepartmentUserVO>();
		if(insertDeptList != null && insertDeptList.size() > 0){
			SysDepartmentUserVO departmentUserVO = null;
			for (int i = 0; i < insertDeptList.size(); i++) {
				departmentUserVO = new SysDepartmentUserVO();
				departmentUserVO.setDepUserId(SysUUID.generator());
				departmentUserVO.setRelationState(StateComm.STATE_0);
				departmentUserVO.setDepId(insertDeptList.get(i));
				departmentUserVO.setUserId(request.getParameter("userId"));
				depidsList.add(departmentUserVO);
			}
			map.put("depidsList", depidsList);
			map.put("userState", StateComm.STATE_0);
			userService.insertDepartmentUser(map);
		}
		
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = userService.updateSysUserByUserId(sysUser,map);
		if(backObj.success.equals(RESULT.SUCCESS_1)){
			backObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, backObj.msg);
		}else{
			backObj.success = RESULT.FAIL_0;
			backObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, backObj.msg);
		}	
		return backObj;
	}

	/**
	 * 查询所有角色名称
	 */
	@RequestMapping("/findRoleName")
	public @ResponseBody
	FeedBackObject queryAllRoleName(HttpServletRequest request) {
		
		String userId = request.getParameter("userId");
		String prvId = request.getParameter("prvId");
		if(prvId == null || prvId == ""){
			prvId = SystemConfComm.PRVID;
		}
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("userId", userId);
		map.put("prvId", prvId);
		map.put("state", StateComm.STATE_0);
		List<SystemRoleVO> roles = roleService.queryAllRoleName(map);
		List<String> allroleIds = new ArrayList<String>();
		for (SystemRoleVO id : roles) {
			allroleIds.add(id.getRoleId());
		}
		map.put("allroleIds", allroleIds);
		List<String> roleIds = null;
		if(userId != null){
			roleIds = userService.queryUserRole(map);
		}
		map.put("roleIds", roleIds);
		map.put("roles", roles);
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = map;
		
		return feedbackObj;
	}

	/**
	 * 查询所有部门名称
	 */
	@RequestMapping("/findDeptName")
	public @ResponseBody
	FeedBackObject queryAllDeptName(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String prvId = request.getParameter("prvId");
		if(prvId == null || prvId == ""){
			prvId = SystemConfComm.PRVID;
		}
		Map<String ,Object> map = new HashMap<String ,Object>();
		map.put("userId", userId);
		map.put("prvId", prvId);
		map.put("state", StateComm.STATE_0);
		List<SysDepartmentVO> departments = deptService.queryAllDepartment(map);
		List<String> alldeptIds = new ArrayList<String>();
		for (SysDepartmentVO id : departments) {
			alldeptIds.add(id.getDepId());
		}
		map.put("alldeptIds", alldeptIds);
		List<String> deptIds = null;
		if(userId != null){
			deptIds = userService.queryUserDepartment(map);
		}
		map.put("deptIds", deptIds);
		map.put("departments", departments);
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = map;
		
		return feedbackObj;
	}
	/**
	 * 查询所有部门及其相关信息
	 * @param cur_page_num
	 * @param page_count
	 * @return
	 */
	@RequestMapping("/queryUserAll")
	public @ResponseBody
	FeedBackObject queryUserAll(int cur_page_num,int page_count) {
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = userService.queryUserAll(cur_page_num,page_count);
		return feedbackObj;
	}

	/**
	 * 用户ID查找用户所有信息
	 */
	@RequestMapping("/findUserById")
	public @ResponseBody
	FeedBackObject findUserById(@ModelAttribute("user")UserLoginInfo loginInfo,String userId,String prvId) {
		if(loginInfo == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		prvId = loginInfo.getPrv_id();
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.Obj = userService.queryUserAllInfoByUserIdRedis(userId,prvId);
		return feedbackObj;
	}
	
	/**
	 * 用户登录账号查找用户所有信息
	 */
	@RequestMapping(value="/findUserByLoginName")
	public @ResponseBody
	FeedBackObject findUserByLoginName(String userLoginname,@ModelAttribute("user")UserLoginInfo loginInfo) {
		if(loginInfo == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userLoginName", userLoginname);
		map.put("prvId", loginInfo.getPrv_id());
		feedbackObj.Obj = userService.queryUserByLoginNameAndPrvId(map);
		return feedbackObj;
	}

	/**
	 * 按条件查找用户
	 * 
	 */
	@RequestMapping("/findUser")
	public @ResponseBody
	FeedBackObject querySysUserByname(String prvId,String userLoginName, String userName,int cur_page_num,int page_count) {
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = userService.querySysUserByname(prvId,userLoginName, userName,cur_page_num,page_count);
		return feedbackObj;
	}

	/**
	 * 删除用户，设置用户状态为已删除
	 */
	@RequestMapping(value = "/user/delete", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject deleteUse(@RequestBody List<String> userIds,HttpSession session) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = userService.deleteUser(userIds);
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		if(backObj.success.equals(RESULT.SUCCESS_1)){
			backObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, backObj.msg);
		}else{
			backObj.success = RESULT.FAIL_0;
			backObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, backObj.msg);
		}	
		return backObj;
	}

	/**
	 * 启用用户
	 */
	@RequestMapping(value = "/user/open", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject openUse(@RequestBody List<String> userIds,HttpSession session) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = userService.openUse(userIds);
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		if(backObj.success.equals(RESULT.SUCCESS_1)){
			backObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, backObj.msg);
		}else{
			backObj.success = RESULT.FAIL_0;
			backObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, backObj.msg);
		}	
		return backObj;
	}
	
	/**
	 * 停用用户
	 * 
	 */
	@RequestMapping(value = "/user/stop", method = RequestMethod.POST)
	public @ResponseBody
	FeedBackObject closeUse(@RequestBody List<String> userIds,HttpSession session) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = userService.closeUse(userIds);
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		if(backObj.success.equals(RESULT.SUCCESS_1)){
			backObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, backObj.msg);
		}else{
			backObj.success = RESULT.FAIL_0;
			backObj.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, backObj.msg);
		}	
		return backObj;
	}
	
	/**
	 * 获得区域信息
	 */
	@RequestMapping("/getAddress")
	public @ResponseBody FeedBackObject getUserAddress(String prvId,String pregId,String regId) {
		Map<String , Object> map = new HashMap<String , Object>();
		map.put("regId", regId);
		map.put("prvId", prvId);
		map.put("pregId", pregId);
		map.put("state", StateComm.STATE_0);
		FeedBackObject backObj = new FeedBackObject();
		backObj.Obj = sysRegionService.getUserAddress(map);
		return backObj;
	}
	
	/**
	 * 新增用户区县关系
	 * 
	 */
	@RequestMapping(value="insertUserRegion",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertUserRegion(String userId,String msg,HttpSession session){
		FeedBackObject fdback = new FeedBackObject();
        String[] msgs = msg.split(",");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < msgs.length; i++) {
        	list.add(msgs[i]);
        }
		fdback.success = userService.insertUserRegion(userId, list);
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		if(fdback.success.equals(RESULT.SUCCESS_1)){
			fdback.msg = PromptMessageComm.OPERATION_SUSSESS+PromptMessageComm.COLON_SYMBOL+loginUser.getUser_loginname()+PromptMessageComm.PLEASE_RELOAD;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, fdback.msg);
		}else{
			fdback.success = RESULT.FAIL_0;
			fdback.msg = PromptMessageComm.OPERATION_FAILED+PromptMessageComm.COLON_SYMBOL+loginUser.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, fdback.msg);
		}
    	return fdback;
	}
	/**
	 * 根据用户id查找区县id
	 * */
	@RequestMapping(value="queryRegionId",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryRegionId(String userId){
		FeedBackObject feedBackObject = new FeedBackObject();
		feedBackObject.success = RESULT.SUCCESS_1;
		feedBackObject.Obj = userService.queryRegionId(userId);
    	return feedBackObject;
	}
	
	
	/**
	 * 重置密码
	 */
	@RequestMapping("/updatePassword")
	public @ResponseBody
	FeedBackObject updatePassword(@ModelAttribute("user") UserLoginInfo loginUser,@RequestBody List<String> userIds) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if(userIds.size() > 0){
			for(int i = 0 ; i < userIds.size() ; i++){
				try {
					map.put("new_pswd", MD5Util.encode(SysUserComm.UPASSWORD));
					map.put("user_id", userIds.get(i));
					count += userService.updateUserInfo(map);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		FeedBackObject backObj = new FeedBackObject();
		if(count > 0){
			backObj.success = RESULT.SUCCESS_1;
			backObj.msg = loginUser.getUser_loginname() + PromptMessageComm.PASSWORD_RESET_SUCCESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, backObj.msg);
		}else{
			backObj.success = RESULT.FAIL_0;
			backObj.msg = loginUser.getUser_loginname() + PromptMessageComm.PASSWORD_RESET_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, backObj.msg);
		}	
		return backObj;
	}
}
