package com.xunge.service.job.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xunge.dao.basedata.collection.FtpFileConfigVOMapper;
import com.xunge.dao.basedata.collection.TaskInfoVOMapper;
import com.xunge.model.basedata.colletion.FtpFileConfigVO;
import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.service.job.ITaskInfoService;

@Service
public class TaskInfoServiceImpl implements ITaskInfoService{

	
	@Resource
	private TaskInfoVOMapper taskInfoVOMapper;
	
	@Resource
	private FtpFileConfigVOMapper ftpFileConfigVOMapper;
	
	@Override
	public TaskInfoVO getTaskInfo(String taskId) {
		
		return taskInfoVOMapper.selectByPrimaryKey(taskId);
	}
	@Override
	public List<FtpFileConfigVO> getFtpFileConfigByTaskId(String taskid) {
		
		return ftpFileConfigVOMapper.getByTaskId(taskid);
	}

}
