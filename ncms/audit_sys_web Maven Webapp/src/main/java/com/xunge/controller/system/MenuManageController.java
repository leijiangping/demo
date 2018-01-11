package com.xunge.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.StateComm;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.SysUserComm;
import com.xunge.comm.system.LoginComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.SysUUID;
import com.xunge.model.FeedBackObject;
import com.xunge.model.SysMenuVO;
import com.xunge.model.system.mannage.MenuTreeNodeVO;
import com.xunge.service.IMenuManagementService;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.util.CodeGeneratorUtil;


@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
@RequestMapping("/asserts/tpl/system")
public class MenuManageController extends BaseException {

    @Autowired
    private IMenuManagementService menuManService;
	
	@Autowired
	private ILogService log;
    
    /**
	 * 根据条件进行查询
	 * @param menuCode menuName
	 * @return
	 */
    @RequestMapping(value="/queryMenuitemByCode", method = RequestMethod.GET)
    public @ResponseBody FeedBackObject queryMenuitemByCode(String code)  {
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.Obj = menuManService.queryMenuitemByCodeRedis(code);
		if(backObj.Obj != null){
			backObj.success = RESULT.SUCCESS_1;
		}else{
			backObj.success = RESULT.FAIL_0;
			backObj.msg = PromptMessageComm.NO_INFO;
		}
    	return backObj;
    }
    
    /**
	 * 根据条件进行查询
	 * @param menuCode menuName
	 * @return
	 */
    @RequestMapping(value="/queryMenuByConditions", method = RequestMethod.GET)
    public @ResponseBody  List<MenuTreeNodeVO> queryMenuByConditions(@RequestParam("menuCode") String menuCode,
    		@RequestParam("menuName") String menuName)  {
    	List<MenuTreeNodeVO> list = menuManService.queryMenuByConditionsRedis(menuCode, menuName);
    	return list;
    }
    
    /**
     * 获取菜单树结点
     * @param menuId
     * @return
     */
    @RequestMapping(value="/queryFunctionMenuTree", method = RequestMethod.GET)
    public @ResponseBody FeedBackObject queryFunctionMenuTree(@ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		
		// user身份类别
		String strUserType = SysUserComm.UCLASS_M01;
		// 身份类别	0：省级用户 ;	1：集团用户
		if(loginInfo.getUser_class() == 1){
			strUserType = SysUserComm.UCLASS_M02;
		}
		
		FeedBackObject backObj = new FeedBackObject();
    	List<MenuTreeNodeVO> MenuTreeNodeList = menuManService.queryFunctionMenuTreeRedis(strUserType);
		backObj.Obj = MenuTreeNodeList;
		if(MenuTreeNodeList.size()>0){
			backObj.success = RESULT.SUCCESS_1;
		}else{
			backObj.success = RESULT.FAIL_0;
			backObj.msg = PromptMessageComm.NO_INFO;
		}
    	return backObj;
    }
    
    /**
     * 新增菜单项
     * @param menuCode
     * @param menuName
     * @return
     */
    @RequestMapping(value="/addNewMenuNode", method = RequestMethod.POST)
    public @ResponseBody FeedBackObject addNewMenuNode(HttpServletRequest request
    		,HttpSession session)  {
    	SysMenuVO item = new SysMenuVO();
    	String pMenuId = request.getParameter("pmenuId");
    	String pMenuCode = request.getParameter("frontFuncCode");
    	item.setMenuCode(CodeGeneratorUtil.MenuCodeGet(pMenuId, pMenuCode));
    	item.setMenuId(SysUUID.generator());
    	item.setMenuName(request.getParameter("menuName"));
    	item.setMenuNote(request.getParameter("infos"));
    	item.setMenuOrder(Integer.parseInt(request.getParameter("order")));
    	item.setMenuState(StateComm.STATE_0);
    	item.setMenuUrl(request.getParameter("menuUrl"));
    	item.setPmenuId(request.getParameter("pmenuId"));

    	//创建返回实体
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.success = menuManService.insertMenuNode(item);
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
     * 修改菜单项
     * @param menuCode
     * @param menuName
     * @return
     */
    @RequestMapping(value="/modifyMenuNode", method = RequestMethod.POST)
    public @ResponseBody FeedBackObject modifyMenuNode(HttpServletRequest request
    		,HttpSession session)  {
    	SysMenuVO item = new SysMenuVO();
    	item.setMenuCode(request.getParameter("menuCode"));
    	item.setMenuId(request.getParameter("menuId"));
    	item.setMenuName(request.getParameter("menuName"));
    	item.setMenuNote(request.getParameter("infos"));
    	item.setMenuOrder(Integer.parseInt(request.getParameter("order")));
    	item.setMenuState(Integer.parseInt(request.getParameter("menuState")));
    	item.setMenuUrl(request.getParameter("menuUrl"));
    	
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.success = menuManService.updateMenuNode(item);
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
     * 删除菜单项
     * @param menuId
     * @return
     */
    @RequestMapping(value="/menu/deleteUse", method = RequestMethod.POST)
    public @ResponseBody FeedBackObject deleteUse(@RequestBody List<String> items
    		,HttpSession session) {
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.success = menuManService.deleteMenu(items);
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
     * @param menuId
     * @return
     */
    @RequestMapping(value="/menu/openUse", method = RequestMethod.POST)
    public @ResponseBody FeedBackObject openUse(@RequestBody List<String> items
    		,HttpSession session) {
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.success = menuManService.updateStateOpenUse(items);
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
     * @param menuId
     * @return
     */
    @RequestMapping(value="/menu/stopUse", method = RequestMethod.POST)
    public @ResponseBody FeedBackObject closeUse(@RequestBody List<String> items
    		,HttpSession session) {
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.success = menuManService.updateStateCloseUse(items);
    	UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
    	if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
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
}
