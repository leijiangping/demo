package com.xunge.dao.elecbill;

import com.xunge.model.elecbill.EleBillamountVO;

public interface EleBillamountVOMapper {
    int deleteByPrimaryKey(String billamountId);

    int insert(EleBillamountVO record);

    int insertSelective(EleBillamountVO record);

    EleBillamountVO selectByPrimaryKey(String billamountId);

    int updateByPrimaryKeySelective(EleBillamountVO record);

    int updateByPrimaryKey(EleBillamountVO record);
    
    boolean updateStatus(EleBillamountVO record);
}