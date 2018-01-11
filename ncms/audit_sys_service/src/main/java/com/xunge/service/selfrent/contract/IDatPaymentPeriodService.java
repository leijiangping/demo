package com.xunge.service.selfrent.contract;

import java.util.List;

import com.xunge.model.selfrent.billAccount.DatPaymentPeriodVO;

/**
 * 缴费周期service接口
 * @author changwq
 */
public interface IDatPaymentPeriodService {
	/**
	 * 缴费周期全查
	 * @return
	 * @author changwq
	 */
	public List<DatPaymentPeriodVO> queryAllDatPaymentPeriod();
	/**
	 * 通过id查询缴费详情信息
	 * @param paymentperiodId
	 * @return
	 * @author changwq
	 */
	public DatPaymentPeriodVO queryDatPaymentPeriodById(String paymentperiodId);
}
