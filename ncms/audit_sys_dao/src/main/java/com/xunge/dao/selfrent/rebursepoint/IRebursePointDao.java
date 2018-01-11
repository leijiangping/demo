package com.xunge.dao.selfrent.rebursepoint;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.selfrent.contract.RentContractVO;
import com.xunge.model.selfrent.rebursepoint.RentBillaccountVO;
import com.xunge.model.selfrent.resource.DatBaseResourceVO;

/**
 * 
 * @author lpw
 *
 */
public interface IRebursePointDao {
	/**
	 * 查询报账点信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page<List<RentBillaccountVO>> queryRembursePointInfo(Map<String, Object> pMap,int pageNumber,int pageSize);
	/**
	 * 导出报账点信息
	 * @param map
	 * @return
	 */
	public List<RentBillaccountVO> queryRembursePointInfo(Map<String, Object> map);
	/**
	 * 查询与用户关联的合同信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<List<RentContractVO>> queryContractAgreement(Map<String, Object> pMap,
			int pageNumber, int pageSize);
	/**
	 * 查询用户关联区县的资源信息
	 * @param regIds
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<List<DatBaseResourceVO>> queryResourceInfo(Map<String, Object> pMap, int pageNumber,
			int pageSize);
	/**
	 * 添加报账点Dao
	 * @param rentBillaccount
	 */
	public void insertBillAcount(RentBillaccountVO rentBillaccount);
	/**
	 * 修改报账点数据
	 * @param billAccountId
	 */
	public void updateBillAcount(RentBillaccountVO billaccount);
	/**
	 * 删除报账点
	 * @param billAccountId
	 */
	public void deleteBillAcount(String billAccountId);
	/**
	 * 根据用户选择查询资源信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<DatBaseResourceVO> queryContractByResourceId(
			Map<String, Object> paraMap);
	/**
	 * 根据主合同ID查询房租合同信息
	 * @param paraMap
	 * @return
	 */
	public RentContractVO queryContractById(Map<String, Object> paraMap);
	/**
	 * 通过报账点ID查询资源信息
	 * @param pMap
	 * @return
	 * 2017年7月10日 lpw
	 */
	public List<DatBaseResourceVO> queryResource(Map<String, Object> pMap);
	/**
	 * 通过报账点ID查询报账点信息
	 * @param paraMap
	 * @return
	 * 2017年7月10日 lpw
	 */
	public RentBillaccountVO queryBillAccountById(Map<String, Object> paraMap);

	/**
	 * 报账点提交审核
	 * @param map
	 */
	public int billAccountSubmitAudit(Map<String, Object> map);
	/**
	 * 查询待审批的报账点
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<RentBillaccountVO> queryRembursePointVO(
			Map<String, Object> paraMap, int pageNumber, int pageSize);
	/**
	 * 根据报账点ID查询支付方式
	 * @param billAccountId
	 * @return
	 */
	public RentBillaccountVO queryPaymentMethod(String billAccountId);
	
	/**
	 * 批量插入
	 * @param list
	 */
	public int insertBatchSelective(List<RentBillaccountVO> list);
}
