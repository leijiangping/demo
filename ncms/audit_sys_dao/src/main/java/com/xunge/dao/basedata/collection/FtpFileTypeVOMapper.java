package com.xunge.dao.basedata.collection;

import java.util.List;

import com.xunge.model.basedata.colletion.FtpFileTypeVO;

public interface FtpFileTypeVOMapper {
    int deleteByPrimaryKey(Integer typeId);

    int insert(FtpFileTypeVO record);

    int insertSelective(FtpFileTypeVO record);

    FtpFileTypeVO selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(FtpFileTypeVO record);

    int updateByPrimaryKey(FtpFileTypeVO record);
    
    List<FtpFileTypeVO> getFtpFileTypebyGroupId(int typeGroupId);
}