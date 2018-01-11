package com.xunge.itf.sms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xunge.model.util.SmsRecvBackVO;
import com.xunge.model.util.SmsSendVO;
import com.xunge.service.sms.send.impl.SmsSendTools;

@RestController
@RequestMapping("/restful/sms/")
public class SmsSendRestController {
	
	private static final Logger LOGGER = Logger.getLogger(SmsSendRestController.class);

	/**
	 * 短信发送接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "send", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
	public String send(HttpServletRequest request,HttpServletResponse response,@RequestBody SmsSendVO sendVO) {

		String result="";
		try{
			LOGGER.info("-------短信下发接收请求----------"+sendVO.toString());
			SmsRecvBackVO recvVo = SmsSendTools.sendTo(sendVO);
			result= JSONObject.toJSONString(recvVo);
			LOGGER.info("-------短信下发返回：----------"+result);
		}
		catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		return result;
	}
}
