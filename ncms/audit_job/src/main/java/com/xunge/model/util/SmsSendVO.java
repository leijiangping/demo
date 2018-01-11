package com.xunge.model.util;

import java.util.Map;

/**
 * 短信接口发送请求实体
 * @author SongJL
 * @date 2017-8-28 11:44:52
 */
public class SmsSendVO {
	
	private SmsType smsType;
	
	private Map<String, Object> param;

	public SmsType getSmsType() {
		return smsType;
	}

	public void setSmsType(SmsType smsType) {
		this.smsType = smsType;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	@Override
	public String toString() {
		return "SmsSendVO [smsType=" + smsType + ", param=" + param + "]";
	}
}
