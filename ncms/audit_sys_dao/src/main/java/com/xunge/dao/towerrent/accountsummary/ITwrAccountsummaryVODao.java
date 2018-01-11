package com.xunge.dao.towerrent.accountsummary;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.accountsummary.TwrAccountsummaryVO;

public interface ITwrAccountsummaryVODao {

	int deleteByPrimaryKey(String accountsummaryId);

    int insert(TwrAccountsummaryVO record);

    int insertSelective(TwrAccountsummaryVO record);

    TwrAccountsummaryVO selectByPrimaryKey(String accountsummaryId);

    int updateByPrimaryKeySelective(TwrAccountsummaryVO record);

    int updateByPrimaryKey(TwrAccountsummaryVO record);
    
    /**
     * 根据参数查询费用汇总集合
     * @param params Map<String,Object》
     * @return list List<Map<String, Object>>
     */
    List<Map<String, Object>> queryTwrAccountsummaryMapListByCondition(Map<String, Object> params);
    
    /**
     * 根据参数查询费用汇总Map分页
     * @param params
     * @return
     */
    Page<List<Map<String, Object>>> queryTwrAccountsummaryMapPage(Map<String,Object> params);
    
    /**
     * 根据参数条件删除费用汇总
     * @param params
     * @return
     */
    int deleteTwrAccountsummaryByCondition(Map<String, Object> params);
    
    /**
     * 根据费用汇总单Id查询验证提交过的费用汇总单数量 
     * @param params
     * @return
     */
    int selectSubmitedAccountsummaryCountByCondition(Map<String, Object> params);
    
    /**
     * 根据参数查询费用汇总集合(此方法不含针对regId == null的查询)
     * @param params Map<String,Object》
     * @return list List<Map<String, Object>>
     */
    List<Map<String, Object>> queryTwrAccountsummeryMapListByCondition1(Map<String, Object> params);
    
    /**
     * 查询汇总费用审核列表
     * @author wangz
     * @param params
     * @return
     */
    Page<TwrAccountsummaryVO> queryPageAccountsummery(Map<String, Object> params);
    
    /**
     * 根据ID编码查询信息
     * @param params
     * @return
     */
    public TwrAccountsummaryVO selectByAccountId(Map<String,Object> params);
}
