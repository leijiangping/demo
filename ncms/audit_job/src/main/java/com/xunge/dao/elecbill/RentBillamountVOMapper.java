package com.xunge.dao.elecbill;

import com.xunge.model.selfrent.billamount.RentBillamountVO;

public interface RentBillamountVOMapper {
    int deleteByPrimaryKey(String billamountId);

    int insert(RentBillamountVO record);

    int insertSelective(RentBillamountVO record);

    RentBillamountVO selectByPrimaryKey(String billamountId);

    int updateByPrimaryKeySelective(RentBillamountVO record);

    int updateByPrimaryKey(RentBillamountVO record);
    
    boolean updateStatus(RentBillamountVO record);
}