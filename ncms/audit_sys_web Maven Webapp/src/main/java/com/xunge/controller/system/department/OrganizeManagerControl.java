package com.xunge.controller.system.department;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.SysLogComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.model.system.department.SysDepartmentVO;
import com.xunge.service.system.department.ISysDepartmentService;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.role.ISysRoleService;

/**
 * @author Administrator
 * @date 2017年5月23日 下午5:36:56
 * @describe
 */
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@Controller
@RequestMapping("/asserts/tpl/system")
public class OrganizeManagerControl extends BaseException {
	@Autowired
	private ISysDepartmentService departmentService;

	@Autowired
	private ISysRoleService roleService;
	
	@Autowired
	private ILogService log;
	/**
	 * 通过code查询部门
	 * @param deptCode
	 * @return
	 */
	@RequestMapping(value="/queryDeptitemByCode",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryDeptitemByCode(String deptCode){
		FeedBackObject backObject = new FeedBackObject();
		backObject.success = RESULT.SUCCESS_1;
		backObject.Obj = departmentService.queryDeptitemByCodeRedis(deptCode);
		if(backObject.Obj == null){
			throw new BusinessException(PromptMessageComm.NO_DEPARTMENT_INFO);
    	}
    	return backObject;
	}
	
	/**
	 * 获取机构树节点
	 * @param prv_id
	 * @return
	 */
	@RequestMapping(value="/getDepartmentManage",method = RequestMethod.POST)
	public @ResponseBody List<Object> getDepartmentManage(
			@ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String userPrvId = loginInfo.getPrv_id();
		List<Object> departmentTreeNodeList = departmentService.queryMenuByConditionsRedis(userPrvId);
		return departmentTreeNodeList;
	}

	/**
	 * 获取所有省份
	 * @param prv_id
	 * @return
	 */
	@RequestMapping(value="/queryAllProvince",method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryAllProvince(
			@ModelAttribute("user") UserLoginInfo loginInfo){
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		String prv_id = loginInfo.getPrv_id();
		FeedBackObject backObj = new FeedBackObject();
		backObj.success = RESULT.SUCCESS_1;
		backObj.Obj = roleService.queryAllProvinceRedis(prv_id);
		return backObj;
	}
	/**
	 * 新增组织结构
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addNewDepartNode", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject addNewDepartNode(HttpServletRequest request
			, @ModelAttribute("user") UserLoginInfo loginUser){
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		FeedBackObject backObject = new FeedBackObject();
		backObject.success = departmentService.insertDepartNode(request);
		if(backObject.success.equals(RESULT.SUCCESS_1)){
			backObject.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_SUSSESS;
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, backObject.msg);
		}else{
			backObject.success = RESULT.FAIL_0;
			backObject.msg = loginUser.getUser_loginname()+PromptMessageComm.COLON_SYMBOL+PromptMessageComm.OPERATION_FAILED;
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, backObject.msg);
		}	
    	return backObject;
	}
	
	/**
     * 修改组织结构
     * @param request
     * @return
     */
    @RequestMapping(value="/modifyDepartNode", method = RequestMethod.POST)
    public @ResponseBody FeedBackObject modifyMenuNode(HttpServletRequest request
    		, @ModelAttribute("user") UserLoginInfo loginUser){
    	if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.success = departmentService.updateDepartNode(request);
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
     * 删除机构组织项
     * @param menuId
     * @return
     */
    @RequestMapping(value="/depart/deleteUse", method = RequestMethod.POST)
    public @ResponseBody FeedBackObject deleteUse(@RequestBody List<SysDepartmentVO> items
    		, @ModelAttribute("user") UserLoginInfo loginUser){
    	if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.success = departmentService.deleteDepart(items);
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
    @RequestMapping(value="/depart/openUse", method = RequestMethod.POST)
    public @ResponseBody FeedBackObject openUse(@RequestBody List<SysDepartmentVO> items
    		, @ModelAttribute("user") UserLoginInfo loginUser){
    	if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.success = departmentService.updateOpenUse(items);
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
    @RequestMapping(value="/depart/stopUse", method = RequestMethod.POST)
    public @ResponseBody FeedBackObject closeUse(@RequestBody List<SysDepartmentVO> items
    		, @ModelAttribute("user") UserLoginInfo loginUser){
    	if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.success = departmentService.updateCloseUse(items);
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
	 * 根据条件进行查询
	 * @param menuCode menuName
	 * @return
	 */
    @RequestMapping(value="/queryDepartByConditions", method = RequestMethod.POST)
    public @ResponseBody  List<Object> queryMenuByConditions(@RequestParam("funcCode") String funcCode,
    		@RequestParam("funcName") String funcName,
    		@RequestParam("provSelect") String provSelect)  {
    	List<Object> list = departmentService.queryDepartByConditionsRedis(funcCode, funcName, provSelect);
    	return list;
    }

	public ISysDepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(ISysDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public ISysRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(ISysRoleService roleService) {
		this.roleService = roleService;
	}
	
}
