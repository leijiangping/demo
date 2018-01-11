package com.xunge.service.system.sms;

import java.util.Map;

import com.xunge.model.util.SmsRecvBackVO;


public interface ISmsSendTools {
	
	/**
	 * 集团下发收集短信：发送短信
	 * @param param 参数map
	 * @return
	 */
	public SmsRecvBackVO JTDataCollectSmsSend(Map<String, Object> param);
	
	/**
	 * 创建新用户下发短信
	 * @param param 参数map
	 * @return
	 */
	public SmsRecvBackVO CreateUserSmsSend(Map<String, Object> param);
	
	/**
	 * 修改用户密码下发短信
	 * @param param 参数map
	 * @return
	 */
	public SmsRecvBackVO ChangeUserPswdSmsSend(Map<String, Object> param);

}
