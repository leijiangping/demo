package com.xunge.service.elecbill.report.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xunge.comm.SysLogComm;
import com.xunge.comm.job.HttpTookit;
import com.xunge.model.elecbill.BillReportVO;
import com.xunge.model.elecbill.ElecBillResult;
import com.xunge.service.elecbill.report.IElecBillReport;
import com.xunge.service.system.log.ILogService;
import com.xunge.util.PropertiesLoader;

@Service
public class ElecBillReportImpl implements IElecBillReport{
	
	private static final Logger LOGGER = Logger.getLogger(ElecBillReportImpl.class);

	@Autowired
	private ILogService log;
	
	@Override
	public ElecBillResult reportToBillSystem(String prvCode,BillReportVO billReportVO) {

	    PropertiesLoader prop = new PropertiesLoader("\\properties\\sysConfig.properties");
	    LOGGER.info("reportToBillSystem   prvCode:"+prvCode);
	    LOGGER.info("reportToBillSystem   billReportVO:"+billReportVO);
		ElecBillResult reportResult= new ElecBillResult();
		if(billReportVO!=null){
			//配置文件中获取上报url
			String url =  prop.getProperty("BillReportUrl");
			LOGGER.info("reportToBillSystem   BillReportUrl:"+url);
			if(StringUtils.isNoneBlank(url)){
				//设置系统ID,取值都是CMCC_NCMP
				billReportVO.setSystem_id("CMCC_NCMP");
				Map<String, String> headers=new HashMap<String, String>();
				headers.put("prvCode", prvCode);
				String params = JSONObject.toJSONString(billReportVO);
				try {
					//使用http工具发起上报请求
					String jsonstr = HttpTookit.doPostWithJson(url, params, headers);
					LOGGER.info("reportToBillSystem   jsonstr:"+jsonstr);
					if(StringUtils.isNoneBlank(jsonstr)){
						//上报返回的json串不为空时，将结果转换为ElecBillResult对象
						reportResult=JSONObject.toJavaObject(JSONObject.parseObject(jsonstr), ElecBillResult.class);
					}
					else{
						reportResult.setIs_succ("N");
						reportResult.setRet_message("上报异常!");
						reportResult.setRet_code("30000");
					}
				} catch (Exception e) {
					reportResult.setIs_succ("N");
					reportResult.setRet_message("上报异常!");
					reportResult.setRet_code("30000");
				}
			}
			else{
				reportResult.setIs_succ("N");
				reportResult.setRet_message("接口服务地址错误!");
				reportResult.setRet_code("30000");
			}
		}
		else{
			reportResult.setIs_succ("N");
			reportResult.setRet_message("上报对象不能为空!");
			reportResult.setRet_code("30000");
		}
		return reportResult;
	}

	@Override
	public ElecBillResult reportToBill(String prvCode, BillReportVO billReportVO) {
		
		ElecBillResult reportResult= new ElecBillResult();
		
		if(billReportVO!=null){
		    PropertiesLoader prop = new PropertiesLoader("\\properties\\sysConfig.properties");
			//根据省编码从配置文件中获取上报url
			String url = prop.getProperty(prvCode+"_BillReportUrl");
			log.info(SysLogComm.LOG_Operate, "上报给电子报账系统-开始:省份编码-"+prvCode+"，上报路径-"+url);
			LOGGER.info("reportToBill   "+prvCode+"_BillReportUrl:"+url);
			if(StringUtils.isNoneBlank(url)){
				//设置系统ID,取值都是CMCC_NCMP
				billReportVO.setSystem_id("CMCC_NCMP");
				String params = JSONObject.toJSONString(billReportVO);
				log.info(SysLogComm.LOG_Operate, "上报给电子报账系统-报文："+params);
				try {
					//使用http工具发起上报请求
					String jsonstr = HttpTookit.doPostWithJson(url, params, null);
					LOGGER.info("reportToBill   jsonstr:"+jsonstr);
					log.info(SysLogComm.LOG_Operate, "上报给电子报账系统-返回数据:"+jsonstr);
					if(StringUtils.isNoneBlank(jsonstr)){
						//上报返回的json串不为空时，将结果转换为ElecBillResult对象
						reportResult=JSONObject.toJavaObject(JSONObject.parseObject(jsonstr), ElecBillResult.class);
					}
					else{
						reportResult.setIs_succ("N");
						reportResult.setRet_message("上报异常!");
						reportResult.setRet_code("30000");
					}
				} catch (Exception e) {
					reportResult.setIs_succ("N");
					reportResult.setRet_message("上报异常!");
					reportResult.setRet_code("30000");
				}
			}
			else{
				reportResult.setIs_succ("N");
				reportResult.setRet_message("上报地址错误!");
				reportResult.setRet_code("30000");
			}
		}
		else{
			reportResult.setIs_succ("N");
			reportResult.setRet_message("上报对象不能为空!");
			reportResult.setRet_code("30000");
		}
		return reportResult;
	}

}
