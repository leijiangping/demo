package com.xunge.dao.selfrent.contract;

import java.util.List;
import java.util.Map;

import com.xunge.model.selfrent.billAccount.DatPaymentPeriodVO;


public interface IDatPaymentPeriodDao {
	/**
	 * 查询所有缴费周期信息
	 * @return
	 * @author changwq
	 */
	public List<DatPaymentPeriodVO> queryAllDatPaymentPeriod(Map<String,Object> paraMap);
	/**
	 * 根据id查询缴费周期信息
	 * @param paraMap
	 * @return
	 * @author changwq
	 */
	public DatPaymentPeriodVO queryDatPaymentPeriodById(Map<String,Object> paraMap);
}	