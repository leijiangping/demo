package com.xunge.dao.selfrent.billaccount;

import java.util.List;

import com.xunge.model.selfrent.billAccount.RentPaymentVO;

public interface IRentPaymentDao {
	/**
	 * 根据报账点id查询报账点缴费记录
	 * @param billAccountId
	 * @return
	 * @author changwq
	 */
	public List<RentPaymentVO> queryRentPaymentByBillAccountId(String billAccountId);
	/**
	 * 新增报账点缴费记录
	 * @param rentPaymentVO
	 * @return
	 * @author changwq
	 */
	public int insertRentPayment(RentPaymentVO rentPaymentVO);
   /**
    * 修改报账点缴费信息
    * */
   public int updateRentPayment(RentPaymentVO rentPaymentVO);
}