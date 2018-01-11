package com.xunge.service.job;

import java.util.List;

import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.model.job.TaskHistoryInfoVO;

public interface ITaskHistoryInfoService {
	List<TaskHistoryInfoVO> queryAll();
	
    int deleteByPrimaryKey(String historyInfoId);

    int insert(TaskHistoryInfoVO record);

    int updateByPrimaryKey(TaskHistoryInfoVO record);
    
    boolean batchInsert(List<TaskHistoryInfoVO> taskHistoryList);
    
    int recordLog(TaskInfoVO taskInfo,String message,String historyId);
}
