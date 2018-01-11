package com.xunge.service.contractsystem.report;

import com.xunge.model.contractsystem.ContractReportResult;
import com.xunge.model.contractsystem.ContractReportVO;

public interface IContractReport {

	/**
	 * 上报给接口服务
	 * @param prvCode
	 * @param contractReportVO
	 * @return
	 */
	ContractReportResult reportToContractSystem(String prvCode,ContractReportVO contractReportVO);
	
	
	/**
	 * 上报给合同系统
	 * @param prvCode
	 * @param contractReportVO
	 * @return
	 */
	ContractReportResult reportToContract(String prvCode,ContractReportVO contractReportVO);
}
