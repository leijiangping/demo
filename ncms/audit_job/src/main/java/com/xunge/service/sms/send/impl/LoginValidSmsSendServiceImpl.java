package com.xunge.service.sms.send.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xunge.model.util.SmsRecvBackVO;
import com.xunge.service.sms.send.ISmsSendTools;
import com.xunge.util.HttpClientUtil;
import com.xunge.util.PropertiesLoader;

@Service
public class LoginValidSmsSendServiceImpl implements ISmsSendTools {

	@Override
	public SmsRecvBackVO sendTo(Map<String, Object> param) {
		PropertiesLoader prop = new PropertiesLoader("\\properties\\sysConfig.properties");
		
		SmsRecvBackVO backO = new SmsRecvBackVO();
		//模板短信请求地址
        String smsUrl=prop.getProperty("ProjectSendSmsUrl");
        
		String sid = prop.getProperty("sms_sicode");
		param.put("sicode", sid);
		
		//模板短信参数
		String smsGetParam = HttpClientUtil.getUrlParamsByMap(param);
		
		try {
			String smsGetPath = smsUrl+"?"+smsGetParam;
			// 发送get
			String returnStr = HttpClientUtil.get(smsGetPath);
			backO = JSON.parseObject(returnStr, SmsRecvBackVO.class); 
		} catch (Exception e) {
			backO.setResult("0");
			backO.setErrorInfo(e.toString());
			e.printStackTrace();
		}
		return backO;
	}

}
