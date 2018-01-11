package com.xunge.dao.basedata.collection;

import java.util.List;

import com.xunge.model.basedata.colletion.FtpFileConfigVO;

public interface FtpFileConfigVOMapper {
	boolean deleteByPrimaryKey(String fileId);

    boolean insert(FtpFileConfigVO record);

    boolean insertSelective(FtpFileConfigVO record);

    FtpFileConfigVO selectByPrimaryKey(String fileId);

    boolean updateByPrimaryKeySelective(FtpFileConfigVO record);

    boolean updateByPrimaryKey(FtpFileConfigVO record);
    
    List<FtpFileConfigVO> getByTaskId(String taskId); 
    
	boolean deleteFtpFileConfigs(String[] ids);
}