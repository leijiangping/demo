package com.xunge.service.job;

import java.util.List;

import com.xunge.model.basedata.colletion.FtpFileConfigVO;
import com.xunge.model.colletion.TaskInfoVO;


public interface ITaskInfoService {
	
	TaskInfoVO getTaskInfo(String taskId);
	
	List<FtpFileConfigVO> getFtpFileConfigByTaskId(String taskid);
}
