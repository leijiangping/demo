package com.xunge.service.contractsystem.report.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xunge.comm.job.HttpTookit;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.util.SysConfigPropertiesUtil;
import com.xunge.model.contractsystem.ContractReportResult;
import com.xunge.model.contractsystem.ContractReportVO;
import com.xunge.service.contractsystem.report.IContractReport;

@Service
public class ContractReportImpl implements IContractReport{

	@Override
	public ContractReportResult reportToContractSystem(String prvCode,ContractReportVO contractReportVO) {
		/*
		ContractReportResult reportResult= new ContractReportResult();
		if(contractReportVO!=null){
			String url =  PropertiesUtil.getProperty("ContractReportUrl");
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
						reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
						reportResult.setRet_message(PromptMessageComm.ERR_REPORT_MSG);
						reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
					}
				} catch (Exception e) {
					reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
					reportResult.setRet_message(PromptMessageComm.ERR_REPORT_MSG);
					reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
				}
			}
			else{
				reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
				reportResult.setRet_message(PromptMessageComm.ERR_SERVICE_ADDRESS);
				reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
			}
		}
		else{
			reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
			reportResult.setRet_message(PromptMessageComm.ERR_REPORT_NULL_OBJECT);
			reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
		}
		return reportResult;*/
		return null;
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
						reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
						reportResult.setRet_message(PromptMessageComm.ERR_REPORT_MSG);
						reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
					}
				} catch (Exception e) {
					reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
					reportResult.setRet_message(PromptMessageComm.ERR_REPORT_MSG);
					reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
				}
			}
			else{
				reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
				reportResult.setRet_message(PromptMessageComm.ERR_REPORT_ADDRESS);
				reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
			}
		}
		else{
			reportResult.setIs_succ(PromptMessageComm.SUCCESS_NO_STR);
			reportResult.setRet_message(PromptMessageComm.ERR_REPORT_NULL_OBJECT);
			reportResult.setRet_code(PromptMessageComm.ERR_REPORT_CODE);
		}
		return reportResult;
	}

}
