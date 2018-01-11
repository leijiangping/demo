package com.xunge.itf.contractsystem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xunge.model.contractsystem.ContractParm;
import com.xunge.model.contractsystem.ContractReportResult;
import com.xunge.model.contractsystem.ContractReportVO;
import com.xunge.model.contractsystem.ContractResult;
import com.xunge.service.contractsystem.report.IContractReport;
import com.xunge.service.contractsystem.status.IContractStatus;


@RestController
@RequestMapping("/restful/contractsystem/")
public class ContractSystemRestController {
	
	private static final Logger LOGGER = Logger.getLogger(ContractSystemRestController.class);
	
	@Autowired
    private IContractStatus contractStatus;
	
	@Autowired
	private IContractReport contractReport;
	/**
	 * 上报合同平台测试
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "report", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
	public String report(HttpServletRequest request,HttpServletResponse response,@RequestBody ContractReportVO contractReportVO) {
		
		String result="";
		try{
			String prvCode=request.getHeader("prvCode");
			ContractReportResult contractReportResult=contractReport.reportToContract(prvCode, contractReportVO);
			result= JSONObject.toJSONString(contractReportResult);
		}
		catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 修改汇合同状态
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "expendApplyStatus", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
	public String expendStatus(HttpServletRequest request,HttpServletResponse response,@RequestBody ContractParm contractParm) {
		
		ContractResult contractResult=new ContractResult();
		try{
			if(contractParm!=null){
				
				contractResult.setElsys_contract_code(contractParm.getElsys_contract_code());
				contractResult.setConsys_contract_code(contractParm.getConsys_contract_code());
				boolean result=contractStatus.editContractStatus(contractParm);
				if(result){
					contractResult.setIs_succ("Y");
					contractResult.setRet_message("请求成功!");
					contractResult.setRet_code("10000");
				}
				else{
					contractResult.setIs_succ("N");
					contractResult.setRet_message("业务逻辑错误!");
					contractResult.setRet_code("30000");
				}
			}
			else{
				contractResult.setIs_succ("N");
				contractResult.setRet_message("参数对象不能为空!");
				contractResult.setRet_code("30000");
			}
			
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			contractResult.setIs_succ("N");
			contractResult.setRet_message("业务逻辑异常!");
			contractResult.setRet_code("30000");
		}
		return JSONObject.toJSONString(contractResult);
	}
}