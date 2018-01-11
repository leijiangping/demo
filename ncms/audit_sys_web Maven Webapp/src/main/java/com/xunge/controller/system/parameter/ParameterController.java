package com.xunge.controller.system.parameter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunge.comm.SysLogComm;
import com.xunge.comm.system.LoginComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.FeedBackObject;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.system.parameter.SysParameterVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.parameter.ISysParameterService;
import com.xunge.service.system.role.ISysRoleService;


@Controller
@RequestMapping("/asserts/tpl/system/parameter")
public class ParameterController extends BaseException {
	private static final String feedBackObj = null;
	@Autowired
	private ISysParameterService sysParameterService;
	@Autowired
	private ISysRoleService iSysRoleService;
	@Autowired
	private ILogService log;
	/**
	 * 查询所有参数
	 * @param session
	 * @param cur_page_num
	 * @param page_count
	 * @param prvId
	 * @param paraCode
	 * @param paraValue
	 * @param paraNote
	 * @return
	 */
	@RequestMapping(value="/queryParameter", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryParameter(HttpSession session,int cur_page_num,int page_count,
			String prvId,String paraCode,String paraValue,String paraNote){
		FeedBackObject feedbackObj = new FeedBackObject();
		UserLoginInfo uf = (UserLoginInfo) session.getAttribute("user");
		if(uf == null){
			throw new BusinessException(LoginComm.LOGIN_FAIL);
		}
		//String prvId = uf.getPrv_id();
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("prvId", prvId);
		paraMap.put("paraCode", paraCode);
		paraMap.put("paraValue", paraValue);
		paraMap.put("paraNote", paraNote);
		feedbackObj.success = RESULT.SUCCESS_1;
		feedbackObj.Obj = sysParameterService.queryParameter(paraMap,cur_page_num,page_count);
		return feedbackObj; 
	}
	/**
	 * 很据id获取参数对象
	 * @param paraId
	 * @return
	 */
	@RequestMapping(value="/getParameter",method=RequestMethod.POST)
	public @ResponseBody SysParameterVO getParameter(String paraId){
		return sysParameterService.getParameter(paraId);
	}
	/**
	 * 修改参数
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateParameter",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject updateParameter(HttpSession session,HttpServletRequest request){
		SysParameterVO sysParameterVO = new SysParameterVO();
		sysParameterVO.setParaId(request.getParameter("para_id"));
		sysParameterVO.setParaCode(request.getParameter("para_code"));
		sysParameterVO.setParaValue(request.getParameter("para_value"));
		sysParameterVO.setParaNote(request.getParameter("para_note"));
		sysParameterVO.setParaState(request.getParameter("para_state"));
		FeedBackObject feedbackObj = new FeedBackObject();	
		feedbackObj.success = sysParameterService.updateParameter(sysParameterVO);
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(LoginComm.LOGIN_FAIL);
		}
		System.out.println(loginUser.getUser_loginname());
		if(feedbackObj.success.equals(RESULT.SUCCESS_1)){
			feedbackObj.msg = PromptMessageComm.OPERATION_SUSSESS+PromptMessageComm.COLON_SYMBOL+loginUser.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedbackObj.msg);
		}else{
			feedbackObj.success = RESULT.FAIL_0;
			feedbackObj.msg = PromptMessageComm.OPERATION_FAILED+PromptMessageComm.COLON_SYMBOL+loginUser.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedbackObj.msg);
		}	
		return feedbackObj;
	}
	/**
	 * 启用参数
	 * @param session
	 * @param paraId
	 * @return
	 */
	@RequestMapping(value = "openParameter",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject openParameter(HttpSession session,String paraId){
		FeedBackObject feedBackObj = new FeedBackObject();
		feedBackObj.success = sysParameterService.openParameter(paraId);
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		if(feedBackObj.success.equals(RESULT.SUCCESS_1)){
			feedBackObj.msg = PromptMessageComm.OPERATION_SUSSESS+PromptMessageComm.COLON_SYMBOL+loginUser.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedBackObj.msg);
		}else{
			feedBackObj.success = RESULT.FAIL_0;
			feedBackObj.msg = PromptMessageComm.OPERATION_FAILED+PromptMessageComm.COLON_SYMBOL+loginUser.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedBackObj.msg);
		}	
		return feedBackObj;
	}
	/**
	 * 停用参数
	 * @param session
	 * @param paraId
	 * @return
	 */
	@RequestMapping(value = "stopParameter",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject stopParameter(HttpSession session,String paraId){
		FeedBackObject feedBackObj = new FeedBackObject();
		feedBackObj.success = sysParameterService.stopParameter(paraId);
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		if(feedBackObj.success.equals(RESULT.SUCCESS_1)){
			feedBackObj.msg = PromptMessageComm.OPERATION_SUSSESS+PromptMessageComm.COLON_SYMBOL+loginUser.getUser_loginname();
			// 添加系统日志
			log.info(SysLogComm.LOG_Operate, feedBackObj.msg);
		}else{
			feedBackObj.success = RESULT.FAIL_0;
			feedBackObj.msg = PromptMessageComm.OPERATION_FAILED+PromptMessageComm.COLON_SYMBOL+loginUser.getUser_loginname();
			// 添加系统日志
			log.err(SysLogComm.LOG_Error, feedBackObj.msg);
		}	
		return feedBackObj;
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
}
