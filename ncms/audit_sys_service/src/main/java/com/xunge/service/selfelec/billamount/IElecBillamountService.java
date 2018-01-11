package com.xunge.service.selfelec.billamount;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.selfelec.VEleBillaccountPaymentInfo;
import com.xunge.model.selfelec.VEleBillamount;
import com.xunge.model.selfelec.VEleBillamountPayment;


/**
 * 
 * @author DanielYang
 *
 * @param <T>
 */
public interface IElecBillamountService{
	
	/**
	 * 查询 汇总信息
	 * @param loginInfo
	 * @param obj
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<VEleBillamount> queryVEleBillamountPage(UserLoginInfo loginInfo ,VEleBillamount obj,int pageNumber, int pageSize);
	
	/**
	 * 根据汇总id查详情
	 * @param loginInfo
	 * @param obj
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public VEleBillamount queryVEleBillamountById(String billamountId);
	
	/**
	 * 查询 待汇总缴费信息
	 * @param loginInfo
	 * @param obj
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<VEleBillamountPayment> queryVEleBillamountPaymentPage(UserLoginInfo loginInfo ,VEleBillamountPayment obj,int pageNumber, int pageSize);
	
	/**
	 * 查询 具体汇总详情列表
	 * @param loginInfo
	 * @param obj
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<VEleBillamountPayment> queryEleBillamountdetailPage(UserLoginInfo loginInfo ,VEleBillamountPayment obj,int pageNumber, int pageSize);
	
	/**
	 * 生成汇总单
	 * @param ids  报账点缴费id
	 * @return
	 */
	public String saveAmountInfo(List<String> ids,UserLoginInfo userInfo);
	
	
	/**
	 * 删除汇总单
	 * @param ids  删除汇总单id
	 */
	public int deleteBillamountById(List<String> ids);
	
	
	/**
	 * 推送汇总单
	 * @param loginInfo
	 * @param ids
	 * @return
	 */
	public String pushBillamount(UserLoginInfo loginInfo, List<String> ids);
	
	/**
	 * 查询汇总明细
	 * @author wangz
	 * @param loginInfo
	 * @param billAmountId
	 * @return
	 */
	public List<VEleBillamountPayment> queryEleBillamountdetail(UserLoginInfo loginInfo ,String billAmountId);
	
	public Map<String,Object> queryPushedBillLv(Map<String,Object> map);
	
	/**
     * @description 查询报账点及报账点缴费详细信息
     * @author yuefy
     * @date 创建时间：2017年9月21日
     */
	public List<VEleBillaccountPaymentInfo> selectBillamountPaymentDetails(Map<String,Object> map);
	
}
