package com.xunge.service.towerrent.accountsummary;

import java.util.List;
import java.util.Map;

import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.model.towerrent.accountsummary.TwrAccountsummaryVO;


public interface ITwrAccountsummaryService {

	/**
	 * 校验费用汇总单
	 * @param params
	 * @return
	 */
	Map<String, Object> validateAccountsummaryStateExists(Map<String, Object> params);
	
	/**
	 * 新增费用汇总
	 * @param params
	 * @return
	 */
	int insertAccountsummary(Map<String,Object> params);
	
	/**
	 * 撤销费用汇总
	 * @param params
	 * @return
	 */
	int deleteAccountsummary(Map<String, Object> params);
	
	/**
	 * 根据accountsummaryId更新accountsummary
	 * @param record
	 * @return
	 */
	int updateAccountsummary(TwrAccountsummaryVO record);
	
	/**
	 * 根据参数分页查询费用汇总单
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Page<List<Map<String, Object>>> queryTwrTwrAccountsummaryVOPageByCondtion(Map<String,Object> paraMap,int pageNumber,int pageSize);
	
	/**
     * 提交审核，启动流程并更新审核状态为审核中
     * @param ids 费用汇总单编码集合
     * @return
     */
    public int updateAuditAndStartFlow(List<Map<String,Object>> ids,UserLoginInfo user);
	
	/**
	 * 根绝费用汇总单Id查询提交过的费用汇总单数量
	 * @param params
	 * @return
	 */
	int selectSubmitedAccountsummaryCountByCondition(Map<String, Object> params);
	
	
	/**
	 * 根据参数验证费用汇总单数据状态
	 * @param idsList 费用汇总单Id
	 * @param state 需要验证的状态
	 * @return 返回验证结果Map
	 */
	Map<String, Object> validateTwrAccountsummaryStatus(Map<String, Object> params);

	/**
	 * 费用审核列表
	 * @author wangz
	 * @param params
	 * @return
	 */
	Page<TwrAccountsummaryVO> queryPageTwrAccountsummary(Map<String,Object> params);
	
	/**
     * 根据ID编码查询费用汇总单信息
     * @param twrRegPunishId
     * @return
     */
    public TwrAccountsummaryVO selectByPrimaryKey(String accountsummaryId);
    
    
    /**
     * 根据ID查询费用汇总
     * @author wangz
     * @param accountsummaryId
     * @return
     */
    public TwrAccountsummaryVO selectByAccountId(String accountsummaryId);
    
    /**
     * @author wangz
     * @param list
     * @param user
     * @return
     */
    public int saveCheckInfo(List<Map<String,Object>> list,UserLoginInfo user);
}
