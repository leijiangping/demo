package com.xunge.service.system.log.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.system.LoginComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.RequestUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.system.log.ISysLogDao;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.model.system.log.SysLogVO;
import com.xunge.service.system.log.ILogService;
/**
 * 日志维护service实现类
 *
 */
public class LogServiceImpl implements ILogService {
		
	private ISysLogDao sysLogDao;
	
	@Override
	public Page<SysLogVO> queryLogMsg(Map<String, Object> paraMap,
			int pageNumber, int pageSize) {
		Page<SysLogVO> page = PageHelper.startPage(pageNumber, pageSize);
		sysLogDao.queryLogMsg(paraMap);
		return page;
	}

	@Override
	public String deleteLogMsg(Map<String, Object> paraMap) {
		try {
			// TODO Auto-generated method stub
			sysLogDao.deleteLogMsg(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			// TODO: handle exception
			return RESULT.FAIL_0;
		}
		
	}
	
	@Override
	public void info(int logType, String message){
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
		//IP
		String logIp = null;
		try {
			logIp = RequestUtil.getRemoteHost(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 取session-用户信息
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(LoginComm.LOGIN_FAILED);
		}
		
		SysLogVO log = new SysLogVO();
		log.setLogId(SysUUID.generator());
		log.setPrvId(loginInfo.getPrv_id());
		log.setIlogType(logType);
		log.setUserId(loginInfo.getUser_id());
		log.setUserName(loginInfo.getUser_loginname());
		log.setMenuId(loginInfo.getOper_menu_id());
		log.setLogIp(logIp);
		log.setLogNote(message);
		sysLogDao.insertLog(log);
	}

	@Override
	public void infoNoLogin(int logType, String message,String prvId,String sessionData) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
		//IP
		String logIp = null;
		try {
			logIp = RequestUtil.getRemoteHost(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SysLogVO log = new SysLogVO();
		log.setLogId(SysUUID.generator());
		log.setPrvId(prvId);
		log.setUserId(sessionData);
		log.setLogIp(logIp);
		log.setIlogType(logType);
		log.setLogNote(message);
		sysLogDao.insertLog(log);
	}

	@Override
	public void err(int logType, String message) {
		this.info(logType+SysLogComm.ERROR_Suffix, message);
	}

	@Override
	public void errNoLogin(int logType, String message,String prvId,String sessionData) {
		this.infoNoLogin(logType+SysLogComm.ERROR_Suffix, message,prvId,sessionData);
	}

	public ISysLogDao getSysLogDao() {
		return sysLogDao;
	}

	public void setSysLogDao(ISysLogDao sysLogDao) {
		this.sysLogDao = sysLogDao;
	}
}
