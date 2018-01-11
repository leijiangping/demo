package com.xunge.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 进度条Session处理
 * @author zhujj
 * @date 2017年7月14日 上午11:44:28 
 * @version 1.0.0 
 */
public class SessionUtils {
	/**
	 * 设置Session
	 * @param name 名称
	 * @param content 内容
	 * @param request
	 */
	public static void setSession(String name,String content,HttpServletRequest request){

		request.getSession().setAttribute(name,content);
	}
	/**
	 * 设置Session
	 * @param name 名称
	 * @param object 保存的对象
	 * @param request
	 */
	public static void setSession(String name,Object object,HttpServletRequest request){

		request.getSession().setAttribute(name,object);
	}
	/**
	 * 
	 * @param name 根据Key找到对应的session
	 * @param request
	 * @return Object
	 */
	public static Object getSession(String name,HttpServletRequest request){
		return request.getSession().getAttribute(name);
	}
	/**
	 * 
	 * @param name 根据Key找到对应的session
	 * @param request
	 * @return String
	 */
	public static String getSessionString(String name,HttpServletRequest request){
		return request.getSession().getAttribute(name).toString();
	}
	/**
	 * 设置进度条Session
	 * @param suffix 标识那个模块的Session，每个功能调用要加的不能一样（必填）
	 * @param importProcess 提示消息
	 * @param importProcessMsg 进度
	 * @param response
	 */
	public static void setProcessSession(String suffix,String msg,int process,HttpServletRequest request){
		request.getSession().setAttribute("importProcess"+suffix,""+process);
		request.getSession().setAttribute("importProcessMsg"+suffix,msg);
    }
}
