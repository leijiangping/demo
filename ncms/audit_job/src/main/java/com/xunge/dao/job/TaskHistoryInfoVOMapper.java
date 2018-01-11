package com.xunge.dao.job;

import java.util.List;
import com.xunge.model.job.TaskHistoryInfoVO;

public interface TaskHistoryInfoVOMapper {
	List<TaskHistoryInfoVO> queryAll();
	
    int deleteByPrimaryKey(String historyInfoId);

    int insert(TaskHistoryInfoVO record);

    int updateByPrimaryKey(TaskHistoryInfoVO record);
    
    boolean batchInsert(List<TaskHistoryInfoVO> taskHistoryList);
}
