package com.xunge.service.contractsystem.status;

import com.xunge.model.contractsystem.ContractParm;


public interface IContractStatus {

	/**
	 * 修改合同状态
	 * @param contractParm
	 * @return
	 */
	boolean editContractStatus(ContractParm contractParm);
}
