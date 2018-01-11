package com.xunge.service.system.log;

import java.util.Map;

import com.github.pagehelper.Page;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.model.system.log.SysLogVO;


public interface ILogService {
	/**
	 * 模糊查询
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<SysLogVO> queryLogMsg(Map<String,Object> paraMap,int pageNumber,int pageSize);

	/**
	 * 删除日志
	 * @param paraMap
	 * @return
	 */
	public String deleteLogMsg(Map<String,Object> paraMap);
	
	/**
	 * 普通日志记录
	 * @param message
	 */
	public void info(int logType, String message);
	/**
	 * 普通日志记录（无登录）
	 * @param message
	 */
	public void infoNoLogin(int logType, String message,String prvId,String sessionData) ;
	
	/**
	 * 错误日志记录
	 * @param message
	 */
	public void err(int logType, String message);
	/**
	 * 错误日志记录（无登录）
	 * @param message
	 */
	public void errNoLogin(int logType, String message,String prvId,String sessionData) ;
}
