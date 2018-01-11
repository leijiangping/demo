package com.xunge.controller.system.role;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.system.LoginComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.model.system.role.SystemRoleVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.role.ISysRoleService;
import com.xunge.service.system.user.ISysUserService;
@Controller
@RequestMapping("/asserts/tpl/system/role")
/**
 * 角色controller
 * @author xup
 *
 */
public class RoleControl extends BaseException {

	@Autowired
	private ISysRoleService iSysRoleService;
	@Autowired
	private ISysUserService userService;
	@Autowired
	private ILogService log;

	 /**
    * 查询所有角色信息
    * @param count
    * @return
    */
	@RequestMapping(value="/queryAllSysRole", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllSysRole(int cur_page_num,int page_count){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj =iSysRoleService.queryAllSysRole(cur_page_num,page_count);
		return feedbackObj;	
	}
	/**
	 * 根据状态查询角色
	 * @param cur_page_num
	 * @param page_count
	 * @return
	 */
	@RequestMapping(value="/queryAll", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject queryAllRole(int cur_page_num,int page_count){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj =iSysRoleService.queryAllRole(StateComm.STATE__1,cur_page_num,page_count);
		return feedbackObj;
	
	}
	 /**
	    * 条件角色信息
	    * @param roleCode roleName
	    * @return
	    */
	@RequestMapping("/querySysRole")
	public @ResponseBody FeedBackObject querySysRole(HttpServletRequest request,String roleCode,String roleName,int cur_page_num,int page_count,String prvId){
		FeedBackObject feedbackObj = new FeedBackObject();
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = iSysRoleService.querySysRoleByName(request,roleCode, roleName,cur_page_num,page_count,prvId);
		return feedbackObj; 
	}	

	 /**
    * 添加角色信息
    * @return
    */
	@RequestMapping(value="/insertRole", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject insertRole(HttpServletRequest request,HttpSession session) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = iSysRoleService.insertRole(request);
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
	    * 查询所有省份
	    * @param list
	    * @return
	    */
	@RequestMapping(value="/queryAllProvince", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllProvince(HttpServletRequest request) {
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = RESULT.SUCCESS_1;
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		String prv_id = loginInfo.getPrv_id();
		backObj.Obj = iSysRoleService.queryAllProvinceRedis(prv_id);
		return backObj;
	}
	

	 /**
    * 删除角色信息
    * @param list
    * @return
    */
	@RequestMapping(value="/deleteRole", method = RequestMethod.POST)
	 public @ResponseBody FeedBackObject deleteRole(@RequestBody List<String> items,HttpSession session) {
	    	FeedBackObject backObj = new FeedBackObject();
	    	backObj.success = iSysRoleService.deleteRole(items);
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
	    * 分配角色给用户
	    * @param list
	    * @return
	    */
	@RequestMapping(value="/grantPrivilege", method = RequestMethod.POST)
	 public @ResponseBody FeedBackObject dispatchRoelToUser(HttpServletRequest request,HttpSession session) {
	    	FeedBackObject backObj = new FeedBackObject();
	    	backObj.success = iSysRoleService.dispatchRoleToUser(request);
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
     * 启用菜单项
     * @param list
     * @return
     */
    @RequestMapping(value="/openUse", method = RequestMethod.POST)
    public @ResponseBody FeedBackObject openUse(@RequestBody List<String> items,HttpSession session) {
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.success = iSysRoleService.openUse(items);
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
     * 停用菜单项
     * @param list
     * @return
     */
    @RequestMapping(value="/closeUse", method = RequestMethod.POST)
    public @ResponseBody FeedBackObject closeUse(@RequestBody List<String> items,HttpSession session) {
    	for (int i = 0; i < items.size(); i++) {
    		items.get(i).toString();
		}
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.success = iSysRoleService.closeUse(items);
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
     * 修改角色信息
     * @param SystemRoleVO roleid
     * @return
     */
		@RequestMapping(value="/updateRole", method = RequestMethod.POST)
		public  @ResponseBody FeedBackObject updateRole(SystemRoleVO sysRoleVO,@RequestParam("roleId") String roleId,
				HttpSession session){
			FeedBackObject backObj = new FeedBackObject();
	    	backObj.success =iSysRoleService.updateRole(sysRoleVO, roleId);
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
		
		@RequestMapping("/queryRoleById")
		public @ResponseBody List<SystemRoleVO> queryRoleById(String roleId){
			List<SystemRoleVO> list = iSysRoleService.queryAllRoleById(roleId);
			return list; 
		}	
		
		 /**
		    * 查询所有用户信息
		    * @param count
		    * @return
		    */
		@RequestMapping(value="/queryAllUser")
		public @ResponseBody FeedBackObject queryAllUser(String prvId,int cur_page_num,int page_count){
			FeedBackObject feedbackObj = new FeedBackObject();
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.Obj =iSysRoleService.queryAllUser(prvId,cur_page_num,page_count);
			return feedbackObj;
		}
		@RequestMapping(value="/insertRoleMenu",method = RequestMethod.POST)
		public @ResponseBody FeedBackObject insertRoleMenu(String roleId,String msg,
				HttpSession session){
			FeedBackObject fdback = new FeedBackObject();
			String[] msgs = msg.split(",");
	        List<String> list = new ArrayList<String>();
	        for (int i = 0; i < msgs.length; i++) {
	        	list.add(msgs[i]);
	        }
			fdback.success = iSysRoleService.insertRoleMenu(roleId, list);
			UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
			if(loginUser == null){
				throw new BusinessException(LoginComm.LOGIN_FAILED);
			}
			if(fdback.success.equals(RESULT.SUCCESS_1)){
				fdback.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_SUSSESS;
				// 添加系统日志
				log.info(SysLogComm.LOG_Operate, fdback.msg);
			}else{
				fdback.success = RESULT.FAIL_0;
				fdback.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_FAILED;
				// 添加系统日志
				log.err(SysLogComm.LOG_Error, fdback.msg);
			}
			return fdback;
		}
		@RequestMapping(value="queryMenuId",method=RequestMethod.POST)
		public @ResponseBody FeedBackObject queryMenuId(String roleId){
			FeedBackObject feedBackObject = new FeedBackObject();
			feedBackObject.success = RESULT.SUCCESS_1;
			feedBackObject.Obj = iSysRoleService.queryMenuId(roleId);
			return feedBackObject;
		}
		
		/**
		 * 根据roleId查询User对象
		 * */
		@RequestMapping(value="queryAllUserByRoleId",method=RequestMethod.POST)
		public @ResponseBody FeedBackObject queryAllUserByRoleId(String roleId,int cur_page_num,int page_count){
			FeedBackObject feedbackObj = new FeedBackObject();
			feedbackObj.success = RESULT.SUCCESS_1;
			feedbackObj.Obj = userService.queryAllUserByRoleId(roleId,cur_page_num,page_count);
			return feedbackObj;
		}
		
}
