package com.xunge.service.job.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xunge.dao.job.TaskHistoryInfoVOMapper;
import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.model.job.TaskHistoryInfoVO;
import com.xunge.service.job.ITaskHistoryInfoService;

@Service
public class TaskHistoryInfoServiceImpl implements ITaskHistoryInfoService{
	
	@Resource
	TaskHistoryInfoVOMapper taskHistoryInfoVOMapper;

	@Override
	public List<TaskHistoryInfoVO> queryAll() {
		return null;
	}

	@Override
	public int deleteByPrimaryKey(String historyInfoId) {
		return 0;
	}

	@Override
	public int insert(TaskHistoryInfoVO record) {
		return taskHistoryInfoVOMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKey(TaskHistoryInfoVO record) {
		return 0;
	}

	@Override
	public boolean batchInsert(List<TaskHistoryInfoVO> taskHistoryList) {
		return false;
	}

	@Override
	public int recordLog(TaskInfoVO taskInfo, String message,String historyId) {
		TaskHistoryInfoVO taskHistory = new TaskHistoryInfoVO();
		String comment = message;
		if (null == comment){
			comment = "导入成功！";
		}
		taskHistory.setPrvId(taskInfo.getPrvId());
		taskHistory.setStartDatetime(new Date());
		taskHistory.setTaskInfoId(taskInfo.getTaskId());
		taskHistory.setComment(comment);
		taskHistory.setTaskHistoryId(historyId);
		
		return taskHistoryInfoVOMapper.insert(taskHistory);
	}
}
