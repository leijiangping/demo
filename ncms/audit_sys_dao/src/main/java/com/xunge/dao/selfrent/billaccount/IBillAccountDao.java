package com.xunge.dao.selfrent.billaccount;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.selfrent.billAccount.BillAccountVO;
import com.xunge.model.selfrent.billAccount.RentPaymentVO;

public interface IBillAccountDao {
	/**
	 * 查询所有报账点信息并显示分页
	 * @author changwq
	 */
	public Page<BillAccountVO> queryBillAccountVO(Map<String,Object> map,int pageNumber,int pageSize);
	/**
	 * 根据报账点id查询报账点对象
	 * @author changwq
	 */
	public BillAccountVO queryBillAccountById(String billAccountId);
	/**
	 * 查询租费报账点关系图
	 * @param billaccountId
	 * @return
	 */
	public List<Map<String,Object>> queryBillaccountRelations(String billaccountId);
}
