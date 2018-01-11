package com.xunge.service.sms.send;

import java.util.Map;

import com.xunge.model.util.SmsRecvBackVO;

public interface ISmsSendTools {
	
	/**
	 * 发送短信
	 * @return
	 */
	SmsRecvBackVO sendTo(Map<String, Object> param);

}
