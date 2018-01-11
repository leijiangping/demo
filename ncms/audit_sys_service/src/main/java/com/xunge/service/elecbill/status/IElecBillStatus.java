package com.xunge.service.elecbill.status;

import com.xunge.model.elecbill.ElecBillParm;

public interface IElecBillStatus {

	/**
	 * 修改汇总单状态
	 * @param elecBillParm
	 * @return
	 */
	boolean editBillStatus(ElecBillParm elecBillParm);
}
