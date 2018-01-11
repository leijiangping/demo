package com.xunge.service.sms.send.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xunge.itf.sms.SmsSendRestController;
import com.xunge.model.util.SmsRecvBackVO;
import com.xunge.service.sms.send.ISmsSendTools;
import com.xunge.util.HttpClientUtil;
import com.xunge.util.PropertiesLoader;
import com.xunge.util.StrUtil;

@Service
public class JTDataCollectionSmsSendServiceImpl implements ISmsSendTools {
	
	private static final Logger LOGGER = Logger.getLogger(JTDataCollectionSmsSendServiceImpl.class);

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
			LOGGER.info("send sms----------"+smsGetPath);
			// 发送get
			String returnStr = HttpClientUtil.get(smsGetPath);
			if(StrUtil.isBlank(returnStr)) return new SmsRecvBackVO();
			backO = JSON.parseObject(returnStr, SmsRecvBackVO.class); 
		} catch (Exception e) {
			backO.setResult("0");
			backO.setErrorInfo(e.toString());
			e.printStackTrace();
		}
		return backO;
	}
}
