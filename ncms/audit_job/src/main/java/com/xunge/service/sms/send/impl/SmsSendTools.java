package com.xunge.service.sms.send.impl;

import com.xunge.model.util.SmsRecvBackVO;
import com.xunge.model.util.SmsSendVO;
import com.xunge.service.sms.send.ISmsSendTools;

public class SmsSendTools {

	public static SmsRecvBackVO sendTo(SmsSendVO sendVO) {
		SmsRecvBackVO recvVo = new SmsRecvBackVO();
		ISmsSendTools smssend = null;
		//根据短信类型
		switch (sendVO.getSmsType()) {
		case DataCollect:
			//集团文件下发短信提醒
			smssend = new JTDataCollectionSmsSendServiceImpl();
			recvVo = smssend.sendTo(sendVO.getParam());
			break;
			
		case LoginValidBySms:
			//登录验证短信
			smssend = new LoginValidSmsSendServiceImpl();
			recvVo = smssend.sendTo(sendVO.getParam());
			break;

		default:
			break;
		}
		return recvVo;
	}

}
