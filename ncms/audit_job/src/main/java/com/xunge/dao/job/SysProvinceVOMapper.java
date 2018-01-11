package com.xunge.dao.job;

import com.xunge.model.job.SysProvinceVO;

public interface SysProvinceVOMapper {
    int deleteByPrimaryKey(String prvId);

    int insert(SysProvinceVO record);

    int insertSelective(SysProvinceVO record);

    SysProvinceVO selectByPrimaryKey(String prvId);

    int updateByPrimaryKeySelective(SysProvinceVO record);

    int updateByPrimaryKey(SysProvinceVO record);
}