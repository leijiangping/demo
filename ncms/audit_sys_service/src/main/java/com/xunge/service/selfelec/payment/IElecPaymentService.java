package com.xunge.service.selfelec.payment; 
/** 
* @author yangju E-mail: 
* @version 创建时间：2017年6月28日 下午2:49:14 
* 类说明 
*/

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.basedata.DatAttachment;
import com.xunge.model.selfelec.EleBillaccount;
import com.xunge.model.selfelec.EleBillaccountPaymentdetail;
import com.xunge.model.selfelec.ElePaymentdetail;
import com.xunge.model.selfelec.VDatElectricmeter;
import com.xunge.model.selfelec.VEleBillaccountInfo;
import com.xunge.model.selfelec.VEleBillaccountPaymentInfo;
import com.xunge.model.selfelec.VEleBillaccountcontract;

public interface IElecPaymentService {
	
	/**
	 * 根据条件查询报账点缴费分页列表
	 * @param loginInfo
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageInfo<VEleBillaccountPaymentInfo> queryVEleBillaccountPaymentInfo(UserLoginInfo loginInfo , VEleBillaccountPaymentInfo param,int pageNumber,int pageSize);
	
	/**
	 * 新增 、修改报账点缴费详情信息
	 * @param payment
	 * @return
	 */
	public int saveOrUpdateEleBillaccountPaymentdetail(EleBillaccountPaymentdetail payment);
	/**
	 * 判断缴费日期是否重复缴费
	 * @param payment
	 * @return
	 */
	
	public int queryEleBillaccountPaymentdetailByBillaccountId(EleBillaccountPaymentdetail detail);
	
	/**
	 * 新增 、修改电表缴费详情信息
	 * @param payment
	 * @return
	 */
	public int saveOrUpdateElePaymentdetail(ElePaymentdetail payment);
	
	/**
	 * 批量审核
	 * @param ids
	 * @param userId
	 * @param state
	 * @return
	 */
	public int batchreviewEleBillaccountPaymentdetail(List<EleBillaccountPaymentdetail> ids , String userId , Integer state);
	
	/**
	 * 根据报账点id获取合同信息
	 * @param billaccountId
	 * @return
	 */
	public VEleBillaccountcontract getVeleBillaccountContract(String billaccountId);
	
	
	/**
	 * 根据报账点id获取电表信息
	 * @param billaccountId
	 * @return
	 */
	public List<VDatElectricmeter> getElectricmeterByBillaccountId(String billaccountId);
	
	/**
	 * 根据报账点id获取电表信息只显示电表
	 * @param billaccountId
	 * @return
	 */
	public List<VDatElectricmeter> getElectricmeterByBillaccountIdShow(String billaccountId);
	
	/**
	 * 根据报账点缴费详情id 获取 电表缴费详情列表
	 * @param billaccountpaymentdetailId
	 * @return
	 */
	public List<VEleBillaccountInfo> getVEleBillaccountInfoByBPaymentdetailId(String billaccountpaymentdetailId);
	
	/**
	 * 查询标杆信息（调用存储过程prc_GetBenchmark计算标杆）
	 * @param param
	 * @return
	 */
	public void getBenchmark(Map<String,Object> param);
	/**
	 * 计算并保存标杆信息
	 * @param param
	 * @author xup
	 */
	public void insertBenchmark(Map<String,Object> param);
	/**
	 * 根据报账点id获取报账点信息
	 * @param billaccountId
	 * @return
	 */
	public EleBillaccount getEleBillaccountById(String billaccountId);
	/**
	 * 修改流程状态
	 * @param billaccountId
	 * @param state
	 * @return
	 * @author xup
	 */
	public int updateActivityCommit(List<Map<String,Object>> ids,UserLoginInfo user);
	
	/**
	 * 审核流程中的数据并进行修改
	 * @param ids
	 * @param user
	 * @return
	 * @author xup
	 */
	public int updateAuditCompelet(List<Map<String,Object>> ids,UserLoginInfo user);
	
	/**
	 * 报账点审核页面查询
	 * @param map
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author xup
	 */
	public PageInfo<VEleBillaccountPaymentInfo> queryEleBillaccountPayment(Map<String,Object> map,int pageNumber,int pageSize);
	
	public EleBillaccountPaymentdetail getEleBillaccountDetailById(String billaccountpaymentdetailId);
	
	/**
	 * 删除明细账单
	 * @param map
	 * @return
	 * @author xup
	 */
	public int deletePaymentDetail(Map<String,Object> map);
	
	 /**
     * 首页全查缴费记录信息
     * @param map
     * @return
     */
    public List<Map<String,String>> queryPaymentDetailByCondition(Map<String,Object>map);
    /**
	 * 根据报账点编码查询此最大缴费期终的信息
	 * @param billaccountId
	 * @return
	 */
	public VEleBillaccountPaymentInfo queryMaxBillAccountEnded(String billaccountId);
	/**
	 * @description 根据 billaccountpaymentdetailId 查找所有信息
	 * @author yuefy
	 * @date 创建时间：2017年9月27日
	 */
	public List<VEleBillaccountPaymentInfo> queryBillaccountPaymentInfoById(String billaccountpaymentdetailId);
	
	/**
	 * 查看合同对应的的业务附件
	 * @param record
	 * @return
	 */
	public PageInfo<DatAttachment> selectByBusiness(DatAttachment record);
	
	public String addAttach(DatAttachment record);
	
	public void delAttach(String url);
}
 