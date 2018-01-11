package com.xunge.dao.selfrent.billaccount;

import java.util.List;
import java.util.Map;

import com.xunge.model.selfrent.billAccount.VBaseresourcePaymentdetailVO;


public interface IVBaseresourcePaymentdetailDao {
	/**
	 * 查询资源点以及资源点缴费记录
	 * @param billaccountId
	 * @return
	 * @author xup
	 */
	public List<VBaseresourcePaymentdetailVO> queryBaseresourcePaymentdetail(Map<String, Object> map);
}