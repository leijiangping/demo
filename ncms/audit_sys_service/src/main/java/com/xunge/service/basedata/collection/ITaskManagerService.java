package com.xunge.service.basedata.collection;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.xunge.model.basedata.colletion.FtpFileConfigVO;
import com.xunge.model.basedata.colletion.FtpFileTypeVO;
import com.xunge.model.basedata.colletion.TaskHistoryInfoVO;
import com.xunge.model.basedata.colletion.TaskInfoVO;

public interface ITaskManagerService {
	
	
	int startTask(String taskid);

	int stopTask(String taskid);

	int runTask(String taskid,String user, String operateIP);
	
	boolean editTask(TaskInfoVO task);
	
	TaskInfoVO getTaskByCollectionType(int collectionType,String prvId);
	
	List<FtpFileConfigVO> getFtpFileConfigByTaskId(String taskid);

	boolean addFtpConfig(FtpFileConfigVO ftpFileConfig);
	
	boolean editFtpConfig(FtpFileConfigVO ftpFileConfig);
	
	boolean delFtpConfig(String[] ids);
	
	PageInfo<TaskHistoryInfoVO> queryTaskHistoryLog(Map<String,Object> paramMp);
	
	int clearHistoryLog(Map<String,Object> paramMap);
	
	List<FtpFileTypeVO> getFtpFileTypebyGroupId(int typeGroupId);
	
	// 立即执行铁塔任务
	int runTowerTask(String taskid, String user, String accountPeriod);
}