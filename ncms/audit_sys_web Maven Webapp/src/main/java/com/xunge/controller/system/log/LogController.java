package com.xunge.controller.system.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.system.LoginComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BaseException;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.FeedBackObject;
import com.xunge.model.system.log.SysLogVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.service.system.role.ISysRoleService;

@Controller
@RequestMapping("/asserts/tpl/system/log")
public class LogController extends BaseException {
	
	@Autowired
	private ILogService logService;

	@Autowired
	private ISysRoleService iSysRoleService;
	/**
	 * 查询所有日志信息
	 * @param logType
	 * @param startTime
	 * @param endTime
	 * @param keyNote
	 * @param cur_page_num
	 * @param page_count
	 * @return
	 */
	@RequestMapping(value = "/queryLogMsg", method = RequestMethod.POST)
	public @ResponseBody FeedBackObject queryLogMsg(String prvId,Integer logType,String startTime,String endTime,
			String keyNote,int cur_page_num,int page_count){
		FeedBackObject fdbObj = new FeedBackObject();
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("prvId",prvId);
		paraMap.put("logType",logType);
		paraMap.put("startTime",startTime);
		paraMap.put("endTime",endTime);
		paraMap.put("keyNote",keyNote);
		fdbObj.success = RESULT.SUCCESS_1;
		Page<SysLogVO> list = logService.queryLogMsg(paraMap, cur_page_num, page_count);
		fdbObj.Obj = list.toPageInfo();
		return fdbObj;
	}
	/**
	 * 删除日志信息
	 * @param session
	 * @param resquest
	 * @return
	 */
	@RequestMapping(value = "/deleteLogMsg",method=RequestMethod.POST)
	public @ResponseBody FeedBackObject deleteLogMsg(HttpSession session,HttpServletRequest resquest){
		FeedBackObject fdbObj = new FeedBackObject();
		String logId = resquest.getParameter("logIds");
		String[] logIdss = logId.split(",");
		List<String> logIds = new ArrayList<String>();
		for (String id : logIdss) {
			logIds.add(id);
		}
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("logIds",logIds);
		fdbObj.success = logService.deleteLogMsg(paraMap);
		UserLoginInfo loginUser = (UserLoginInfo) session.getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(LoginComm.LOGIN_FAIL);
		}
		if(fdbObj.success.equals(RESULT.SUCCESS_1)){
			fdbObj.msg = PromptMessageComm.OPERATION_SUSSESS+PromptMessageComm.COLON_SYMBOL+loginUser.getUser_loginname();
			// 添加系统日志
			logService.info(SysLogComm.LOG_Operate, fdbObj.msg);
		}else{
			fdbObj.success = RESULT.FAIL_0;
			fdbObj.msg = PromptMessageComm.OPERATION_FAILED+PromptMessageComm.COLON_SYMBOL+loginUser.getUser_loginname();
			// 添加系统日志
			logService.err(SysLogComm.LOG_Error, fdbObj.msg);
		}	
		return fdbObj;
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
