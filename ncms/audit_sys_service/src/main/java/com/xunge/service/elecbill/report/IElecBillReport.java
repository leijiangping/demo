package com.xunge.service.elecbill.report;

import com.alibaba.fastjson.JSONObject;
import com.xunge.model.elecbill.BillReportVO;
import com.xunge.model.elecbill.ElecBillResult;

/**
 * 电子账单上报接口
 * @author Administrator
 *
 */
public interface IElecBillReport {

	/**
	 * 上报给接口服务
	 * @param prvCode 省份标识
	 * @param billReportVO 上报汇总单对象
	 * @return
	 */
	ElecBillResult reportToBillSystem(String prvCode,JSONObject billReportVO);
	
	/**
	 * 上报给电子报账系统
	 * @param prvCode 省份标识
	 * @param billReportVO 上报汇总单对象
	 * @return
	 */
	ElecBillResult reportToBill(String prvCode,BillReportVO billReportVO);
}
