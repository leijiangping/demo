package com.xunge.controller.basedata.util;

import com.xunge.comm.SysLogComm;
import com.xunge.comm.system.RESULT;
import com.xunge.model.FeedBackObject;
import com.xunge.service.system.log.ILogService;

/**
* @author 
* @date 2017年6月5日
*/
public class OperateUtil {
	
	/**
	 * 判断数据库操作是否成功
	 * @param backObj
	 * @return
	 */
	public static boolean handleSuccess(FeedBackObject backObj)
	{
		return backObj.success.equals(RESULT.SUCCESS_1);
	}
	
	/**
	 * 针对数据库成功和失败的情况对前台进行消息回显并且记录日志
	 * @param backObj
	 * @param messageSuccess
	 * @param messageError
	 */
	public static void echoBeahivor(FeedBackObject backObj,ILogService log, String messageSuccess, String messageFailed) {
		   if (handleSuccess(backObj)) {
			   backObj.msg = messageSuccess;
			   log.info(SysLogComm.LOG_Operate, backObj.msg);
		   }
		   else {
			   backObj.msg = messageFailed;
			   log.info(SysLogComm.LOG_Error, backObj.msg);
		   }
	 }
}
