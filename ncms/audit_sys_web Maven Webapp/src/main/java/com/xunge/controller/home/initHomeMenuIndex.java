package com.xunge.controller.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.HomeComm;
import com.xunge.comm.SysUserComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.exception.ParameterException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.model.SysAutoMenuVO;
import com.xunge.service.IMenuManagementService;

@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
public class initHomeMenuIndex extends BaseException {

	@Autowired
	private IMenuManagementService menuManService;
	/**
	 * 通过角色查询菜单
	 * @param loginInfo
	 * @return
	 */
	@RequestMapping(value = "/queryMenuIndexByRole", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject queryMenuIndexByRole(@ModelAttribute("user") UserLoginInfo loginInfo) {
		
		if(loginInfo==null){
			throw new BusinessException(PromptMessageComm.LOGIN_TIMEOUT);
		}
		
		//user角色集合
		List<String> roleIds = loginInfo.getRole_ids();
		if(roleIds.size() <= Integer.parseInt(HomeComm.VALUE_str0)){
			throw new BusinessException(PromptMessageComm.USER_DOES_NOT_ASSOCIATE_ROLE);
		}
		
		//user身份类别
		String iUserType = SysUserComm.UCLASS_M01;
		if(loginInfo.getUser_class() == SysUserComm.UCLASS_1){
			iUserType = SysUserComm.UCLASS_M02;
		}
		
		FeedBackObject backObj = new FeedBackObject();
		List<SysAutoMenuVO> topMenuList = menuManService.queryMenuIndexByRoleRedis(roleIds, iUserType);
		if(topMenuList.size()>Integer.parseInt(HomeComm.VALUE_str0)){
			backObj.success = RESULT.SUCCESS_1;
			backObj.Obj = topMenuList;
		}
		else{
			backObj.success = RESULT.FAIL_0;
			backObj.msg = PromptMessageComm.ROLE_IS_NOT_ASSOCIATED_WITH_MENU;
		}
		
		return backObj;
	}
	/**
	 * 根据角色查询第二级菜单
	 * @param loginInfo
	 * @param menuParentId
	 * @param menuParentCode
	 * @return
	 */
	@RequestMapping(value = "/querySecondMenuIndexByRole", method = RequestMethod.GET)
	public @ResponseBody FeedBackObject querySecondMenuIndexByRole(@ModelAttribute("user") UserLoginInfo loginInfo, 
			String menuParentId, String menuParentCode) {
		if(menuParentId==null){
			throw new ParameterException(PromptMessageComm.PARAMETER_ACQUISITION_EXCEPTION);
		}
		if(loginInfo==null){
			throw new BusinessException(PromptMessageComm.LOGIN_TIMEOUT);
		}
		
		//user角色集合
		List<String> roleIds = loginInfo.getRole_ids();
		if(roleIds.size() <=Integer.parseInt(HomeComm.VALUE_str0)){
			throw new BusinessException(PromptMessageComm.USER_ROLE_FAILED);
		}

		FeedBackObject backObj = new FeedBackObject();
		// 通过父级菜单id、角色可操作菜单集合，获取二级子菜单
		List<SysAutoMenuVO> secMenuList = menuManService.querySecondMenuIndexByRole(roleIds, menuParentId, menuParentCode);
		if(secMenuList.size()>Integer.parseInt(HomeComm.VALUE_str0)){
			backObj.success = RESULT.SUCCESS_1;
			backObj.Obj = secMenuList;
		}
		else{
			backObj.success = RESULT.FAIL_0;
			backObj.msg = PromptMessageComm.GET_SUBMENU_FAILED;
		}
		return backObj;
	}
	
	/**
	 * 保存用户选中的菜单
	 * @param menuId
	 * @param loginInfo
	 * @return
	 */
	@RequestMapping(value = "/saveUserClickedMenuId", method = RequestMethod.POST)
	public @ResponseBody String saveUserClickedMenuId(String menuId, @ModelAttribute("user") UserLoginInfo loginInfo) {
		if(loginInfo == null) {
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		
		loginInfo.setOper_menu_id(menuId);
		return PromptMessageComm.SUCCESS;
	}
}
