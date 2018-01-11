package com.xunge.core.util;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import com.xunge.core.exception.BaseException;

public class RequestUtil extends BaseException {
	/**
	 * 获取真实IP地址
	 * 
	 * @param req
	 * @return String IP地址
	 */
	public static String getRemoteHost(HttpServletRequest request)
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
