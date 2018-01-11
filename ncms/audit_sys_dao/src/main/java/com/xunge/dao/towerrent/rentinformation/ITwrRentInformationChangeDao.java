package com.xunge.dao.towerrent.rentinformation;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.rentinformation.TwrRentInformationChangeVO;

public interface ITwrRentInformationChangeDao {
    int deleteByPrimaryKey(String rentinformationchangeId);

    int insert(TwrRentInformationChangeVO record);

    int insertSelective(TwrRentInformationChangeVO record);

    TwrRentInformationChangeVO selectByPrimaryKey(String rentinformationchangeId);

    int updateByPrimaryKeySelective(TwrRentInformationChangeVO record);

    int updateByPrimaryKey(TwrRentInformationChangeVO record);
    
    /**
     * 根据参数查询移动资源变更日志集合
     * @param params Map<String,Object》
     * @return list List<Map<String, Object>>
     */
    List<Map<String, Object>> queryTwrRentInformationChangeByCondition(Map<String, Object> params);
    
    /**
     * 根据参数查询移动变更日志分页Map集合
     * @param params
     * @return
     */
    Page<List<Map<String, Object>>> queryTwrRentInformationChangePage(Map<String,Object> params);
    
    /**
     * 根据参数查询移动变更日志分页集合分页
     * @param params
     * @return
     */
    Page<List<TwrRentInformationChangeVO>> queryTwrRentInformationChangeVoPage(Map<String,Object> params);
    
    /**
     * 根据参数查询移动资源变更日志集合
     * @param params Map<String,Object》
     * @return list List<Map<String, Object>>
     */
    List<TwrRentInformationChangeVO> queryTwrRentInformationChangeVoListByCondition(Map<String, Object> params);
}