package com.xunge.service.selfrent.billaccount;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.model.selfrent.billAccount.VPaymentVO;
/**
 * 报账点缴费记录视图service
 *
 */
public interface IPaymentService {
	/**
	 * 审核主页面查询
	 * @param map
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<VPaymentVO> queryPayment(Map<String,Object> map,int pageNumber,int pageSize);
	/**
	 * 缴费记录查询
	 * @param map
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	/**
	 * 查询所有缴费信息
	 * @param map
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<List<VPaymentVO>> queryAllPayment(Map<String,Object> map,int pageNumber,int pageSize);
	/**
	 * 报账点租费维护主页面查询
	 * @param map
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	/**
	 * 查询合同缴费信息
	 * @param map
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<List<VPaymentVO>> queryPaymentContract(Map<String,Object> map,int pageNumber,int pageSize);
	/**
	 * 审核内页面查询
	 * @param map
	 * @param billAccountId
	 * @param paymentId
	 * @return
	 */
	public List<VPaymentVO> queryContractPayment(Map<String,Object> map,String billAccountId,String paymentId);
	
	/**
	 * 修改流程状态
	 * @param billaccountId
	 * @param state
	 * @return
	 */
	public int updateActivityCommit(List<Map<String,Object>> ids,UserLoginInfo user);

	/**
	 * 增加修改缴费记录汇总id
	 * @param map 
	 * @param map: List<String> paymentId 缴费记录id集合
	 * @param map: billamountId 汇总id
	 * @author zhujj
	 * @return
	 */
	public int updateBillamountIdByPaymentId(Map<String,Object> map);
	
	
	/**
	 * 审核流程中的数据并进行修改
	 * @param ids
	 * @param user
	 * @param state
	 * @param paymentState
	 * @return
	 * @author xup
	 */
	public int updateAuditCompelet(List<Map<String,Object>> ids,UserLoginInfo user);
	/**
	 * 查询待汇总缴费信息
	 * @param hashMaps
	 * @param hashMaps:pageNumber（必填）当前页
	 * @param hashMaps:pageSize（必填）每页显示多少条
	 * @param hashMaps:billType 报账类型
	 * @param hashMaps:paymentMethod 支付方式
	 * @param hashMaps:contractCode 合同编码
	 * @param hashMaps:paymentIds 租费报账汇总信息主键
	 * @author zhujj
	 * @return
	 */
	public Page<List<VPaymentVO>> queryContractPaymentByNoAmount(Map<String,Object> map);
	/**
	 * 查询待汇总缴费信息
	 * @param hashMaps
	 * @param hashMaps:billType 报账类型
	 * @param hashMaps:paymentMethod 支付方式
	 * @param hashMaps:contractCode 合同编码
	 * @param hashMaps:paymentIds 租费报账汇总信息主键
	 * @author zhujj
	 * @return
	 */
	public List<VPaymentVO> queryContractPaymentByNoAmountList(Map<String, Object> map);
	/**
	 * 导出缴费记录信息信息
	 * @param paramMap
	 * @param request
	 * @param response
	 * @author xup
	 */
	public void exportPaymentDetail(Map<String, Object> paramMap,HttpServletRequest request,HttpServletResponse response);
}
