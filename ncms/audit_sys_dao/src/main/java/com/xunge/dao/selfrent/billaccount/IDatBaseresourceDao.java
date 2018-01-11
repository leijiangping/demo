package com.xunge.dao.selfrent.billaccount;

import java.util.List;

import com.xunge.model.selfrent.billAccount.DatBaseresourceVO;

public interface IDatBaseresourceDao {
	/**
	 * 根据报账点id查询资源点信息集合
	 * @param billAccountId
	 * @return
	 * @author changwq
	 */
	public List<DatBaseresourceVO> queryDatBaseresourceByBillAccountId(String billAccountId);
}