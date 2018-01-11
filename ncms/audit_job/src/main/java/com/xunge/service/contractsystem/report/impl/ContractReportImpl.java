package com.xunge.service.contractsystem.report.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xunge.comm.job.HttpTookit;
import com.xunge.model.contractsystem.ContractReportResult;
import com.xunge.model.contractsystem.ContractReportVO;
import com.xunge.service.contractsystem.report.IContractReport;
import com.xunge.util.PropertiesLoader;
import com.xunge.util.SysConfigPropertiesUtil;

@Service
public class ContractReportImpl implements IContractReport{
	@Override
	public ContractReportResult reportToContractSystem(String prvCode,ContractReportVO contractReportVO) {
		
		ContractReportResult reportResult= new ContractReportResult();
		if(contractReportVO!=null){
			//String url =  PropertiesUtil.getProperty("ContractReportUrl");
			String url = new PropertiesLoader("\\properties\\sysConfig.properties").getProperty("ContractReportUrl");
			if(StringUtils.isNoneBlank(url)){
				
				Map<String, String> headers=new HashMap<String, String>();
				headers.put("prvCode", prvCode);
				String params = JSONObject.toJSONString(contractReportVO);
				try {
					String jsonstr = HttpTookit.doPostWithJson(url, params, headers);
					if(StringUtils.isNoneBlank(jsonstr)){
						reportResult=JSONObject.toJavaObject(JSONObject.parseObject(jsonstr), ContractReportResult.class);
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
	public ContractReportResult reportToContract(String prvCode, ContractReportVO contractReportVO) {
		
		ContractReportResult reportResult= new ContractReportResult();
		if(contractReportVO!=null){
			String url =  SysConfigPropertiesUtil.getProperty(prvCode+"_ContractReportUrl");
			if(StringUtils.isNoneBlank(url)){
				String params = JSONObject.toJSONString(contractReportVO);
				try {
					String jsonstr = HttpTookit.doPostWithJson(url, params, null);
					if(StringUtils.isNoneBlank(jsonstr)){
						reportResult=JSONObject.toJavaObject(JSONObject.parseObject(jsonstr), ContractReportResult.class);
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
