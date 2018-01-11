package com.xunge.service.elecbill.report.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.job.HttpTookit;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.util.PropertiesLoader;
import com.xunge.model.elecbill.BillReportVO;
import com.xunge.model.elecbill.ElecBillResult;
import com.xunge.service.elecbill.report.IElecBillReport;
import com.xunge.service.system.log.ILogService;

@Service
public class ElecBillReportImpl implements IElecBillReport{

	protected Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ILogService log;

	@Override
	public ElecBillResult reportToBillSystem(String prvCode,JSONObject billReportVO) {

	    PropertiesLoader prop = new PropertiesLoader(PromptMessageComm.URL_SYSCONFIG);
	    LOGGER.info("reportToBillSystem   prvCode:"+prvCode);
	    LOGGER.info("reportToBillSystem   billReportVO:"+billReportVO.toJSONString());
		ElecBillResult reportResult= new ElecBillResult();
		if(billReportVO!=null){
			//配置文件中获取上报url
			String url =  prop.getProperty("BillReportUrl");
			LOGGER.info("reportToBillSystem   BillReportUrl:"+url);
			if(StringUtils.isNoneBlank(url)){
				//设置系统ID,取值都是CMCC_NCMP
				billReportVO.put("system_id", "CMCC_NCMP");
				Map<String, String> headers=new HashMap<String, String>();
				headers.put("prvCode", prvCode);
				String params = JSONObject.toJSONString(billReportVO);
				try {
					//使用http工具发起上报请求

					// 添加系统日志
					log.info(SysLogComm.LOG_Operate, params);
					String jsonstr = HttpTookit.doPostWithJson(url, params, headers);
					LOGGER.info("reportToBillSystem   jsonstr:"+jsonstr);
					if(StringUtils.isNoneBlank(jsonstr)){
						//上报返回的json串不为空时，将结果转换为ElecBillResult对象
						reportResult=JSONObject.toJavaObject(JSONObject.parseObject(jsonstr), ElecBillResult.class);
					}else{
						reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
						reportResult.setRet_message("推送返回信息为空!");
						reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
						log.info(SysLogComm.LOG_Operate, "推送返回信息为空");
						throw new BusinessException("推送返回信息为空!");
					}
				} catch (Exception e) {
					reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
					reportResult.setRet_message(PromptMessageComm.ERR_NET_INTERFACE);
					reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
					e.printStackTrace();
					log.info(SysLogComm.LOG_Operate, PromptMessageComm.ERR_NET_INTERFACE+e.getMessage());
					throw new BusinessException(e.toString());
				}
			}
			else{
				reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
				reportResult.setRet_message("接口服务地址错误!");
				reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
				log.info(SysLogComm.LOG_Operate, "接口服务地址错误!");
				throw new BusinessException("接口服务地址错误!");
			}
		}else{
			reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
			reportResult.setRet_message(PromptMessageComm.ERR_REPORT_NULL_OBJECT);
			reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
			log.info(SysLogComm.LOG_Operate, PromptMessageComm.ERR_REPORT_NULL_OBJECT);
			throw new BusinessException(PromptMessageComm.ERR_REPORT_NULL_OBJECT);
		}
		return reportResult;
	}

	@Override
	public ElecBillResult reportToBill(String prvCode, BillReportVO billReportVO) {
		
		ElecBillResult reportResult= new ElecBillResult();
		LOGGER.info("reportToBill   prvCode:"+prvCode);
		LOGGER.info("reportToBill   billReportVO:"+billReportVO);
		if(billReportVO!=null){
		    PropertiesLoader prop = new PropertiesLoader(PromptMessageComm.URL_SYSCONFIG);
			//根据省编码从配置文件中获取上报url
			String url = prop.getProperty("BillReportUrl");
			LOGGER.info("reportToBill   "+prvCode+"_BillReportUrl:"+url);
			if(StringUtils.isNoneBlank(url)){
				//设置系统ID,取值都是CMCC_NCMP
				billReportVO.setSystem_id("CMCC_NCMP");
				String params = JSONObject.toJSONString(billReportVO);
				try {
					log.info(SysLogComm.LOG_Operate, params);
					//使用http工具发起上报请求
					String jsonstr = HttpTookit.doPostWithJson(url, params, null);
					LOGGER.info("reportToBill   jsonstr:"+jsonstr);
					if(StringUtils.isNoneBlank(jsonstr)){
						//上报返回的json串不为空时，将结果转换为ElecBillResult对象
						reportResult=JSONObject.toJavaObject(JSONObject.parseObject(jsonstr), ElecBillResult.class);
					}
					else{
						reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
						reportResult.setRet_message(PromptMessageComm.ERR_REPORT_MSG);
						reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
						log.info(SysLogComm.LOG_Operate, PromptMessageComm.ERR_REPORT_MSG);
						throw new BusinessException(PromptMessageComm.ERR_REPORT_MSG);
					}
				} catch (Exception e) {
					reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
					reportResult.setRet_message(PromptMessageComm.ERR_NET_INTERFACE);
					reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
					log.info(SysLogComm.LOG_Operate, PromptMessageComm.ERR_NET_INTERFACE+e.getMessage());
					throw new BusinessException(PromptMessageComm.ERR_NET_INTERFACE);
				}
			}
			else{
				reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
				reportResult.setRet_message(PromptMessageComm.ERR_REPORT_ADDRESS);
				reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
				log.info(SysLogComm.LOG_Operate, PromptMessageComm.ERR_REPORT_ADDRESS);
				throw new BusinessException(PromptMessageComm.ERR_REPORT_ADDRESS);
			}
		}
		else{
			reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
			reportResult.setRet_message(PromptMessageComm.ERR_REPORT_NULL_OBJECT);
			reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
			log.info(SysLogComm.LOG_Operate, PromptMessageComm.ERR_REPORT_NULL_OBJECT);
			throw new BusinessException(PromptMessageComm.ERR_REPORT_NULL_OBJECT);
		}
		return reportResult;
	}

}
