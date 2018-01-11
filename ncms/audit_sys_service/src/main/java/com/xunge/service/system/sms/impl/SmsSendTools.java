package com.xunge.service.system.sms.impl;


import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xunge.comm.job.HttpTookit;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.util.PropertiesLoader;
import com.xunge.model.util.SmsRecvBackVO;
import com.xunge.model.util.SmsSendVO;
import com.xunge.model.util.SmsType;
import com.xunge.service.system.sms.ISmsSendTools;

/**
 * 短信下发接口
 * @author SongJL
 */
public class SmsSendTools implements ISmsSendTools {
	
	private final static Logger log = LoggerFactory.getLogger(SmsSendTools.class);

	@Override
	public SmsRecvBackVO JTDataCollectSmsSend(Map<String, Object> param) {
		SmsRecvBackVO backO = SendSmsToITF(param);
		return backO;
	}

	@Override
	public SmsRecvBackVO CreateUserSmsSend(Map<String, Object> param) {
		SmsRecvBackVO backO = SendSmsToITF(param);
		return backO;
	}

	@Override
	public SmsRecvBackVO ChangeUserPswdSmsSend(Map<String, Object> param) {
		SmsRecvBackVO backO = SendSmsToITF(param);
		return backO;
	}
	
	/**
	 * 向接口服务器发送短信
	 * @return
	 */
	private SmsRecvBackVO SendSmsToITF(Map<String, Object> param) {
	    PropertiesLoader prop = new PropertiesLoader(PromptMessageComm.URL_SYSCONFIG);
		//配置文件中获取上报url
		String url =  prop.getProperty("SmsSendUrl");
		SmsRecvBackVO backO = null;
		log.info("JTDataCollectSmsSend   Url:"+url);
		if(StringUtils.isNoneBlank(url)){
			SmsSendVO smsVo = new SmsSendVO();
			smsVo.setSmsType(SmsType.DataCollect);
			smsVo.setParam(param);
			String params = JSONObject.toJSONString(smsVo);
			try {
				//使用http工具发起上报请求
				String jsonstr = HttpTookit.doPostWithJson(url, params, null);
				log.info(PromptMessageComm.GROUP_SEND_SMS_RETURN+jsonstr);
		        backO = smsResultTransform(jsonstr);
			} catch (Exception e) {
				backO.setBackResult(false);
				backO.setBackInfo(e.getMessage());
			}
		}
		return backO;
	}

	/**
	 * 10701	下发处理成功,resultInfo会返回下发流水号
	 * 10702	下发处理失败,resultInfo会描述具体失败原因,此次下发请求被放弃
	 * 10703	发送内容有敏感词,errorInfo中会返回具体的敏感词,此次下发请求被放弃
	 * 10704	下发号码包含黑名单,errorInfo会返回处于主叫黑名单的下发号码,此次下发请求被放弃
	 * 10705	接收号码包含黑名单,errorInfo会返回处于被叫黑名单的接收号码,此次下发请求被放弃
	 * 10706	接收号码中包含非法号码,errorInfo会返回非法的接收号码,此次下发请求被放弃
	 * 10708	SI鉴权失败,resultInfo中返回具体SI鉴权失败原因,如:SI不存在,此次下发请求被放弃
	 * 10709	余额不足
	 * 10712	参数异常,resultInfo返回出现异常的参数,此次下发请求被放弃
	 * 10713	模板不存在或未审核通过
	 * 10714	缺少模板参数
	 */
	private SmsRecvBackVO smsResultTransform(String returnStr){
		// JSON串转对象  
        SmsRecvBackVO backO = JSON.parseObject(returnStr, SmsRecvBackVO.class);  

        /**
         * 错误返回状态
         */
		switch(backO.getResult()){
		case "10701":
			backO.setBackResult(true);
			break;
		case "0":
			return backO;
		default:
			backO.setBackResult(false);
			break;
		}

        /**
         * 错误返回信息
         */
		switch(backO.getResult()){
		case "10701":
			backO.setBackInfo(PromptMessageComm.SEND_SERIAL_NUMBER_SUCCESS+backO.getResultInfo());
			break;
		case "10702":
		case "10708":
		case "10712":
			backO.setBackInfo(backO.getResultInfo());
			break;
		case "10703":
		case "10704":
		case "10705":
		case "10706":
			backO.setBackInfo(backO.getErrorInfo());
			break;
		case "10707":
		case "10709":
		case "10710":
		case "10711":
		case "10713":
		case "10714":
		default:
			backO.setBackInfo(PromptMessageComm.SYSTEM_EXCEPTION);
			break;
		}
		return backO;
	}
}
