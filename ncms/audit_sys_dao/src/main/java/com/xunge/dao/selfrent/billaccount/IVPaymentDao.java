package com.xunge.dao.selfrent.billaccount;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.selfrent.billAccount.VPaymentVO;


public interface IVPaymentDao {
	/**
	 * 查询报账点缴费记录
	 * @param hashMaps
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author xup
	 */
	public Page<VPaymentVO> queryPayment(Map<String,Object> hashMaps,int pageNumber,int pageSize);
	/**
	 * 查询所有报账点缴费记录
	 * @author xup
	 */
	public Page<List<VPaymentVO>> queryAllPayment(Map<String,Object> hashMaps,int pageNumber,int pageSize);
	/**
	 * 查询所有报账点缴费记录（导出）
	 * @author xup
	 */
	public List<VPaymentVO> queryAllPayment(Map<String,Object> hashMaps);
	/**
	 * 查询合同缴费记录
	 * @author xup
	 */
	public Page<List<VPaymentVO>> queryPaymentContract(Map<String,Object> hashMaps,int pageNumber,int pageSize);
	
	/**
	 * 根据id查询合同缴费记录
	 * @author xup
	 */
	public VPaymentVO queryPaymentContractById(String id);
	/**
	 * 审核页面回显
	 * @author xup
	 */
	public List<VPaymentVO> queryContractPayment(Map<String,Object> hashMaps);
	/**
	 * 提交审核
	 * @author xup
	 */
	public int updateActivityCommit(Map<String,Object> hashMaps);
	/**
	 * 更改payment缴费记录状态
	 * @author xup
	 */
	public int updateState(Map<String,Object> hashMaps);
	
	/**
	 * 修改租费缴费记录汇总id
	 * @param map 
	 * @param map: paymentId 缴费记录id
	 * @param map: billamountId 汇总id
	 * @author zhujj
	 * @return
	 */
	public int updateBillamountIdByPaymentId(Map<String, Object> hashMaps);
	/**
	 * 查询待汇总缴费信息-分页
	 * @param hashMaps
	 * @param hashMaps:pageNumber（必填）当前页
	 * @param hashMaps:pageSize（必填）每页显示多少条
	 * @param hashMaps:billType 报账类型
	 * @param hashMaps:paymentMethod 支付方式
	 * @param hashMaps:contractCode 合同编码
	 * @param hashMaps:paymentId 缴费信息主键
	 * @author zhujj
	 * @return
	 */
	public Page<List<VPaymentVO>> queryContractPaymentByNoAmount(Map<String, Object> hashMaps) ;
	/**
	 * 查询待汇总缴费信息-不分页
	 * @param hashMaps
	 * @param hashMaps:pageNumber（必填）当前页
	 * @param hashMaps:pageSize（必填）每页显示多少条
	 * @param hashMaps:billType 报账类型
	 * @param hashMaps:paymentMethod 支付方式
	 * @param hashMaps:contractCode 合同编码
	 * @param hashMaps:paymentId 缴费信息主键
	 * @author zhujj
	 * @return
	 */
	public List<VPaymentVO> queryContractPaymentByNoAmountList(Map<String, Object> hashMaps) ;
}