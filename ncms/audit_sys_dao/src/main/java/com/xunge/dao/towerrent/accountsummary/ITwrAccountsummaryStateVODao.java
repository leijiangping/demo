package com.xunge.dao.towerrent.accountsummary;

import java.util.List;
import java.util.Map;

import com.xunge.model.towerrent.accountsummary.TwrAccountsummaryStateVO;

public interface ITwrAccountsummaryStateVODao {

	int deleteByPrimaryKey(Integer id);

    int insert(TwrAccountsummaryStateVO record);

    int insertSelective(TwrAccountsummaryStateVO record);

    TwrAccountsummaryStateVO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TwrAccountsummaryStateVO record);

    int updateByPrimaryKey(TwrAccountsummaryStateVO record);
	/**
     * 根据参数查询费用汇总状态集合
     * @param params Map<String,Object》
     * @return list List<Map<String, Object>>
     */
    List<Map<String, Object>> queryTwrAccountsummarStateyMapListByCondition(Map<String, Object> params);
    
    /**
     * 根据查询条件删除费用汇总状态
     * @param params
     * @return
     */
    int deleteTwrAccountsummaryByCondition(Map<String, Object> params);
}
