package com.xunge.service.system.log.impl;

import java.net.InetAddress;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xunge.comm.SysLogComm;
import com.xunge.dao.log.ISysLogDao;
import com.xunge.exception.BusinessException;
import com.xunge.model.UserLoginInfo;
import com.xunge.model.system.log.SysLogVO;
import com.xunge.service.system.log.ILogService;
import com.xunge.util.SysUUID;
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
			return "1";
		} catch (Exception e) {
			// TODO: handle exception
			return "0";
		}
		
	}
	
	@Override
	public void info(int logType, String message){
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
		//IP
		String logIp = null;
		try {
			logIp = this.getRemoteHost(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SysLogVO log = new SysLogVO();
		log.setLogId(SysUUID.generator());
//		log.setPrvId("000000");
		log.setIlogType(logType);
//		log.setUserId("000000");
//		log.setUserName("000000");
//		log.setMenuId("000000");
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
			logIp = this.getRemoteHost(request);
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
	
	/**
	 * 获取真实IP地址
	 * 
	 * @param req
	 * @return String IP地址
	 */
	public String getRemoteHost(HttpServletRequest request)
			throws Exception {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "内网："+getLocalIP() : "外网："+ip;
	}
	/**
	 * 获取本地IP地址
	 * 
	 * @param req
	 * @return String IP地址
	 */
	public static String getLocalIP() throws Exception {
		String localIP = "";
		InetAddress addr = (InetAddress) InetAddress.getLocalHost();
		// 获取本机IP
		localIP = addr.getHostAddress().toString();
		return localIP;
	}
}
