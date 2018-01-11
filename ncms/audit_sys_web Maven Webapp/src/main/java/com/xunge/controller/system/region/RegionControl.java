package com.xunge.controller.system.region;

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

import com.xunge.comm.SysLogComm;
import com.xunge.comm.system.LoginComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.model.system.province.SysProvinceGroupVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.region.ISysRegionService;

@Controller
@RequestMapping("/asserts/tpl/system/region")
public class RegionControl extends BaseException {
	@Autowired
	private ISysRegionService sysRegionService;
	
	@Autowired
	private ILogService log;
	/**
	 * 条件查询
	 * @param request
	 * @param regCode
	 * @param regName
	 * @param cur_page_num
	 * @param page_count
	 * @return
	 */
	@RequestMapping("/queryByConditions")
	public @ResponseBody FeedBackObject queryByConditions(HttpServletRequest request,@RequestParam("regCode") String regCode,@RequestParam("regName") String regName){
		FeedBackObject feedBackObj = new FeedBackObject();
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		feedBackObj.success = RESULT.SUCCESS_1;
		feedBackObj.Obj = sysRegionService.selectByConditions(loginInfo.getPrv_id(),regCode, regName);
		if(RESULT.SUCCESS_1.equals(feedBackObj.success)){
			feedBackObj.msg=PromptMessageComm.OPERATION_SUSSESS;
    	}
    	else{
    		feedBackObj.msg=PromptMessageComm.OPERATION_FAILED;
    	}
		return feedBackObj;
	}
	/**
	 * 获取分组省份
	 * @author zhujj
	 * @return
	 */
	@RequestMapping("/queryByGroup")
	public @ResponseBody FeedBackObject queryByPrgGroup(HttpSession session){
		FeedBackObject feedBackObj = new FeedBackObject();
		feedBackObj.success = RESULT.SUCCESS_1;
		@SuppressWarnings("unchecked")
		List<SysProvinceGroupVO> prvGroups=(List<SysProvinceGroupVO>)session.getAttribute("prvGroup");
		if(prvGroups==null){
			feedBackObj.Obj = sysRegionService.selectByPrvGroup();
		}else{
			feedBackObj.Obj = prvGroups;
		}
		if(RESULT.SUCCESS_1.equals(feedBackObj.success)){
			feedBackObj.msg=PromptMessageComm.OPERATION_SUSSESS;
    	}else{
    		feedBackObj.msg=PromptMessageComm.OPERATION_FAILED;
    	}
		return feedBackObj;
	}
	/**
	 * 获取省份列表
	 * @author zhujj
	 * @return
	 */
	@RequestMapping("/selectProvinceByIds")
	public @ResponseBody FeedBackObject selectProvinceByIds(HttpSession session){
		FeedBackObject feedBackObj = new FeedBackObject();
		feedBackObj.success = RESULT.SUCCESS_1;

		UserLoginInfo loginInfo = (UserLoginInfo) session.getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		feedBackObj.Obj=sysRegionService.selectProvinceByIds(loginInfo);
		if(RESULT.SUCCESS_1.equals(feedBackObj.success)){
			feedBackObj.msg=PromptMessageComm.OPERATION_SUSSESS;
    	}else{
    		feedBackObj.msg=PromptMessageComm.OPERATION_FAILED;
    	}
		return feedBackObj;
	}
	/**
	 * 删除区县信息
	 * @param items
	 * @param session
	 * @return
	 */
	@RequestMapping("/delRegion")
	public @ResponseBody FeedBackObject delRegion(@RequestBody List<String> items
			,HttpSession session){
	    	FeedBackObject backObj = new FeedBackObject();
	    	backObj.success = sysRegionService.delRegion(items);
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
	 * 新增区县信息
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/insertRegion")
	public @ResponseBody FeedBackObject insertRegion(HttpServletRequest request
			,HttpSession session){
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.success = sysRegionService.insertRegion(request);
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
	 * 根据id获取区县对象
	 * @param regId
	 * @return
	 */
	@RequestMapping("/getRegion")
	public @ResponseBody SysRegionVO getRegionById(String regId){
		SysRegionVO region=sysRegionService.getRegionById(regId);
    	return region;

	}
	/**
	 * 修改区县信息
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/updateRegion")
	public @ResponseBody FeedBackObject updateRegion(HttpServletRequest request
			,HttpSession session){
    	FeedBackObject backObj = new FeedBackObject();
    	backObj.success = sysRegionService.updateRegion(request);
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
	
	public ISysRegionService getSysRegionService() {
		return sysRegionService;
	}

	public void setSysRegionService(ISysRegionService sysRegionService) {
		this.sysRegionService = sysRegionService;
	}

	/**
	 * 获取省份下的所有子节点
	 * @param prv_id
	 * @return
	 */
	@RequestMapping(value="/getSysRegionManage",method = RequestMethod.POST)
	public @ResponseBody List<Object> getDepartmentManage(HttpServletRequest request){
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		List<Object> sysRegionTreeNodeList = sysRegionService.queryRegionByConditions(loginInfo.getPrv_id());
		return sysRegionTreeNodeList;
	}
	
}
