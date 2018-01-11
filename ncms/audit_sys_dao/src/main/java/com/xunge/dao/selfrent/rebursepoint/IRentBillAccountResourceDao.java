package com.xunge.dao.selfrent.rebursepoint;

import java.util.List;
import java.util.Map;

import com.xunge.model.selfrent.billAccount.RentBillAccountResourceVO;
/**
 * 报账点资源关系dao
 * @author lpw
 * 2017年6月25日
 */
public interface IRentBillAccountResourceDao {
	/**
	 * 添加报账点资源关系数据
	 * @param rentBillAccountResource
	 */
	void insertBillAccountResource(
			List<RentBillAccountResourceVO> rentResourceList);
	/**
	 * 修改报账点资源关系
	 * @param billAccountResourceId
	 */
	void updateBillAccountResource(Map<String, Object> paramMap);
	/**
	 * 删除报账点资源关系
	 * @param billAccountResourceId
	 */
	void deleteBillAccountResource(String billAccountId);
	/**
	 * 查询报账点是否已绑定报账点
	 * @param resourceIds
	 * @return
	 */
	List<RentBillAccountResourceVO> queryResourceBindBillacc(
			Map<String, Object> paraMap);
	/**
	 * 删除资源绑定的报账点
	 * @param baseresourceId
	 * @return
	 */
	int deleteResourcePoint(String baseresourceId);

}
