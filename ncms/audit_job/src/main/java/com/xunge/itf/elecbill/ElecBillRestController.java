package com.xunge.itf.elecbill;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xunge.comm.SysLogComm;
import com.xunge.model.elecbill.BillReportVO;
import com.xunge.model.elecbill.ElecBillParm;
import com.xunge.model.elecbill.ElecBillResult;
import com.xunge.service.elecbill.report.IElecBillReport;
import com.xunge.service.elecbill.status.IElecBillStatus;
import com.xunge.service.system.log.ILogService;


@RestController
@RequestMapping("/restful/elecbill/")
public class ElecBillRestController {
	
	private static final Logger LOGGER = Logger.getLogger(ElecBillRestController.class);
	
	@Autowired
    private IElecBillStatus elecBillStatus;
	
	
	@Autowired
	private IElecBillReport elecBillReport;
	

	@Autowired
	private ILogService log;
	/**
	 * 上报电子报账平台测试
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "report", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
	public String report(HttpServletRequest request,HttpServletResponse response,@RequestBody BillReportVO billReportVO) {
		
		String result="";
		try{
			String prvCode=request.getHeader("prvCode");
			ElecBillResult elecBillResult=elecBillReport.reportToBill(prvCode, billReportVO);
			result= JSONObject.toJSONString(elecBillResult);
		}
		catch(Exception e){
			LOGGER.error(e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 修改汇总单状态
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "expendApplyStatus", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
	public String expendStatus(HttpServletRequest request,HttpServletResponse response,@RequestBody ElecBillParm elecBillParm) {
		
		ElecBillResult elecBillResult=new ElecBillResult();
		try{
			if(elecBillParm!=null){
				
				elecBillResult.setCollect_num(elecBillParm.getCollect_num());
				elecBillResult.setBoe_num(elecBillParm.getBoe_num());
				log.info(SysLogComm.LOG_Operate, "修改汇总单状态-开始：汇总单编号-"+elecBillParm.getCollect_num()+"  状态="+elecBillParm.getStatus());

				LOGGER.info("-----job修改汇总单状态：汇总单编号-"+elecBillParm.getCollect_num()+"  状态-"+elecBillParm.getStatus());
				boolean result=elecBillStatus.editBillStatus(elecBillParm);
				if(result){
					log.info(SysLogComm.LOG_Operate, "修改汇总单状态-成功：汇总单编号-"+elecBillParm.getCollect_num()+"  状态="+elecBillParm.getStatus());
					LOGGER.info("-----job修改汇总单状态：汇总单编号-"+elecBillParm.getCollect_num()+"  状态="+elecBillParm.getStatus());
					elecBillResult.setIs_succ("Y");
					elecBillResult.setRet_message("请求成功!");
					elecBillResult.setRet_code("10000");
				}
				else{
					log.info(SysLogComm.LOG_Operate, "修改汇总单业务状态-逻辑错误：汇总单编号-"+elecBillParm.getCollect_num()+"  状态="+elecBillParm.getStatus());
					LOGGER.info("-------------业务逻辑错误!--------------");
					elecBillResult.setIs_succ("N");
					elecBillResult.setRet_message("业务逻辑错误!");
					elecBillResult.setRet_code("30000");
				}
			}
			else{
				log.info(SysLogComm.LOG_Operate, "修改汇总单业务状态-参数对象不能为空！");
				LOGGER.info("-------------参数对象不能为空!--------------");
				elecBillResult.setIs_succ("N");
				elecBillResult.setRet_message("参数对象不能为空!");
				elecBillResult.setRet_code("30000");
			}
			
		}catch(Exception e){
			log.info(SysLogComm.LOG_Error, "修改汇总单业务状态-出错："+e.getMessage());
			LOGGER.error(e.getMessage());
			elecBillResult.setIs_succ("N");
			elecBillResult.setRet_message("业务逻辑错误!");
			elecBillResult.setRet_code("30000");
		}
		return JSONObject.toJSONString(elecBillResult);
	}
}