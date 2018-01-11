package com.xunge.dao.selfrent.contract;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.selfrent.contract.DatContractVO;
import com.xunge.model.selfrent.contract.DatSupplierVO;
import com.xunge.model.selfrent.contract.RentContractVO;

public interface ISelfRentDao {
	/**
	 * 查询所有主合同信息
	 * @author changwq
	 */
	public Page<RentContractVO> queryRentContractVO(Map<String,Object> paraMap,int pageNumber,int pageSize);
	/**
	 * 查询所有主合同信息
	 * @author yuefy
	 */
	public List<RentContractVO> queryRentContractVO(Map<String,Object> paraMap);
	
	/**
	 * 根据房屋租赁合同表中的供应商id查询供应商对象
	 * @author changwq
	 */
	public DatSupplierVO queryDatSupplierById(Map<String,Object> paraMap);
	/**
	 * 根据省份查询供应商
	 * @param paraMap 
	 * @param paraMap -prvId 省份id 
	 * @return
	 */
	public List<DatSupplierVO> queryDatSupplierByPrvID(Map<String, Object> paraMap);
	
	/**
	 * 分页查询供应商信息
	 * @param rentcontractId
	 * @author yuefy
	 * @return
	 */
	public Page<List<DatSupplierVO>> queryDatSupplierByPrvID(Map<String,Object> paraMap,
			int pageNumber,int pageSize) ;
	
	/**
	 * 根据房屋租赁合同表中的主合同id查询主合同对象
	 * @author changwq
	 */
	public DatContractVO queryDatContractById(Map<String,Object> paraMap);
	/**
	 * 根据房屋租赁合同id查询房租对象
	 * @author changwq
	 */
	public RentContractVO queryRentContractById(Map<String,Object> paraMap);
	/**
	 * 根据房屋租赁合同id查询房租合同、主合同以及区域信息对象
	 * @param rentContractId 屋租赁合同id
	 * @author zjj
	 * @return
	 */
	public RentContractVO queryAllRentContractById(String rentContractId);
	
	/**
	 * 新增主合同信息
	 * @author changwq
	 */
	public int insertDatContractVO(DatContractVO datContractVO);
	/**
	 * 修改主合同信息
	 * @param datContractVO
	 * @return
	 * @author changwq
	 */
	public int updateDatContractVO(DatContractVO datContractVO);

	/**
	 * 新增房屋租租赁合同
	 * @param rentContractVO
	 * @return
	 * @author changwq
	 */
	public int insertRentContractVO(RentContractVO rentContractVO);
	/**
	 * 修改房租合同信息
	 * @param rentContractVO
	 * @return
	 * @author changwq
	 */
	public int updateRentContractVO(RentContractVO rentContractVO);
	/**
	 * 新增供应商信息
	 * @param datSupplierVO
	 * @return
	 * @author changwq
	 */
	public int insertDatSupplierVO(DatSupplierVO datSupplierVO);
	/**
	 * 修改供应商信息
	 * @param datSupplierVO
	 * @return
	 * @author changwq
	 */
	public int updateDatSupplierVO(DatSupplierVO datSupplierVO);
	/**
	 * 补录页面提交审核
	 * @param map
	 * @return
	 * @author changwq
	 */
	public int updateCommit(Map<String,Object> map);

	/**
	 * 查询合同代码
	 * @param map
	 * @return
	 */
	public List<DatContractVO> checkContractCode(Map<String, Object> map);
	
	/**
	 * 批量终止合同
	 */
	public int stopContract(Map<String, Object> map);
	
	/**
	 * 批量删除房租主合同信息
	 */
	public int deleteContract(Map<String, Object> map);
	
	/**
	 * 批量 删除房租合同
	 * @param parmMap
	 * @return
	 */
	public int deleteRentContract(Map<String, Object> parmMap);
	
	/**
	 * 查询合同最后缴费日期
	 */
	public List<String> queryRentContractEndDate(Map<String,Object> paraMap);
	
	/**
	 * 查询租费合同缴费周期
	 */
	public RentContractVO getPaymentPeriodDate(Map<String,Object> paraMap);
	
	public RentContractVO queryRentContractByBillAccountId(String billAccountId);
	/**
	 * 查询房租合同信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public RentContractVO queryContractByContractId(
			Map<String, Object> paraMap, int pageNumber, int pageSize);
	/**
	 * 查询供应商和合同信息
	 * @param paraMap
	 * @return
	 */
	public RentContractVO queryContractAndSupplier(Map<String, Object> paraMap);
	/**
	 * 根据报账点id查询供应商和合同信息
	 * @param paraMap
	 * @return
	 */
	public RentContractVO queryContAndSupByBillId(Map<String, Object> paraMap);
	/**
	 * 根据省份、用户权限汇总各地区合同数量
	 * @param paraMap
	 * @return
	 */
	public List<Map<String, String>> selectContractNumByCondition(Map<String, Object> paraMap);
}