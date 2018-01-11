package com.xunge.dao.selfrent.billaccount;

import java.util.List;
import java.util.Map;

import com.xunge.model.selfrent.billAccount.RentPaymentdetailVO;


public interface IRentPaymentdetailDao {
    /**
     * 查询房屋缴费明细
     * @author changwq
     * */
	public RentPaymentdetailVO queryPayMentDetailByBaseId(Map<String,Object> map);
	/**
	 * 添加房屋缴费明细
	 * @author changwq
	 * */
	public int insertRentPaymentdetail(Map<String,Object> map);
	/**
	 * 修改房屋缴费明细
	 * */
	public int updateRentPaymentdetail(RentPaymentdetailVO rentpaymentdetailVO);
}