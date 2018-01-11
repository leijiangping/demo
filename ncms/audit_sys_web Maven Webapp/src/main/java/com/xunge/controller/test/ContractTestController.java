package com.xunge.controller.test;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xunge.model.contractsystem.ContractReportResult;
import com.xunge.model.contractsystem.ContractReportVO;
import com.xunge.service.contractsystem.report.IContractReport;


@RestController
@RequestMapping("/test/contract/")
public class ContractTestController {
	
	private static final Logger LOGGER = Logger.getLogger(ContractTestController.class);
	
	@Autowired
	private IContractReport contractReport;
	/**
	 * 上报合同平台测试
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "report/{prvId}", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
	public String report(HttpServletRequest request,HttpServletResponse response,@PathVariable String prvId,
			@RequestBody ContractReportVO contractReportVO) {
		
		String result="";
		try{
			//520000是贵州省编码，测试其他省份时需要修改
			ContractReportResult contractReportResult=contractReport.reportToContractSystem("520000", contractReportVO);
			result= JSONObject.toJSONString(contractReportResult);
			if("{}".equalsIgnoreCase(result)){
				result="{上报"+prvId+"地址访问失败}";
			}
		}
		catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		return result;
	}
	public static void main(String[] args) {
		Map<String,Object> map=Maps.newHashMap();
		Map<String,Object> map2=Maps.newHashMap();
		map.put("ff", 123);
		map2.put("ff2", 2222);
		map2.put("ff3", 333);
		
		map.put("pp",map2);
		
		Map<String,Object> map3=(Map<String, Object>) map.get("pp");
		System.out.println();
		
	}
}