package com.xunge.dao.selfrent.rebursepoint;

import java.util.Map;

import com.xunge.model.selfrent.rebursepoint.RentBillAccountContractVO;

/**
 * 报账点合同关系Dao
 * @author lpw
 * 2017年6月25日
 */
public interface IRentBillAccountContractDao {
	/**
	 * 添加报账点合同关系Dao
	 * @param rentBillAccountContract
	 */
	void insertBillAccountContract(
			RentBillAccountContractVO rentBillAccountContract);
	/**
	 * 修改合同报账点关系
	 * @param billAccountContractId
	 */
	int updateBillAccountContract(Map<String, Object> paraMap);
	/**
	 * 删除合同报账点关系
	 * @param billAccountContractId
	 */
	void deleteBillAccountContract(String billAccountContractId);
	/**
	 * 查询合同与是否已绑定报账点
	 * @param rentcontractId
	 * @return
	 */
	RentBillAccountContractVO queryContractBindBillacc(String rentcontractId);

}
