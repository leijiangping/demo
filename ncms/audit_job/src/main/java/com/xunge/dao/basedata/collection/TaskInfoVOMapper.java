package com.xunge.dao.basedata.collection;

import java.util.List;

import com.xunge.model.colletion.TaskInfoVO;

public interface TaskInfoVOMapper {
	boolean deleteByPrimaryKey(String taskId);

    boolean insert(TaskInfoVO record);

    boolean insertSelective(TaskInfoVO record);

    TaskInfoVO selectByPrimaryKey(String taskId);

    boolean updateByPrimaryKeySelective(TaskInfoVO record);

    boolean updateByPrimaryKey(TaskInfoVO record);
    
    /**
     * 根据条件获取任务
     */
    List<TaskInfoVO> getTaskInfoByCollectionType(TaskInfoVO task);
}