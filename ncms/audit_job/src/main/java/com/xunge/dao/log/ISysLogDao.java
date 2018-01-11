package com.xunge.dao.log;



import java.util.List;
import java.util.Map;

import com.xunge.model.system.log.SysLogVO;


public interface ISysLogDao {
	/**
	 * 日志记录模糊查询
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author changwq
	 */
	public List<SysLogVO> queryLogMsg(Map<String,Object> paraMap);
	/**
	 * 删除日志记录
	 * @param paraMap
	 * @return
	 * @author changwq
	 */
	public int deleteLogMsg(Map<String,Object> paraMap);

	/**
	 * 日志记录入库
	 * @param sysLog
	 * @return
	 */
	public int insertLog(SysLogVO sysLog);
}