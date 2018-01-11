package com.xunge.service.basedata.collection.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xunge.comm.StateComm;
import com.xunge.comm.TaskComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.util.SchedulerUtils;
import com.xunge.dao.basedata.collection.FtpFileConfigVOMapper;
import com.xunge.dao.basedata.collection.FtpFileTypeVOMapper;
import com.xunge.dao.basedata.collection.TaskHistoryInfoVOMapper;
import com.xunge.dao.basedata.collection.TaskInfoVOMapper;
import com.xunge.model.basedata.colletion.FtpFileConfigVO;
import com.xunge.model.basedata.colletion.FtpFileTypeVO;
import com.xunge.model.basedata.colletion.TaskHistoryInfoVO;
import com.xunge.model.basedata.colletion.TaskInfoVO;
import com.xunge.service.basedata.collection.ITaskManagerService;
import com.xunge.service.basedata.collection.job.CollectionJob;
import com.xunge.service.basedata.collection.job.OneTimeJob;
import com.xunge.service.basedata.util.IDUtils;

@Service
public class TaskManagerServiceImpl implements ITaskManagerService {
	
	@Resource
    private TaskInfoVOMapper taskInfoMapper;
	
	@Resource
	private TaskHistoryInfoVOMapper taskHistoryInfoVOMapper;
	
	@Resource
	private FtpFileConfigVOMapper ftpFileConfigVOMapper;
	
	@Resource
	private FtpFileTypeVOMapper ftpFileTypeVOMapper;
	
	@Override
	public int startTask(String taskid) {

		TaskInfoVO taskinfo=taskInfoMapper.selectByPrimaryKey(taskid);
		
		int taskPeriod=taskinfo.getTaskPeriod();
		int taskType=taskinfo.getTaskType();
		int collectionType=taskinfo.getCollectionType();
		String ftpPath=taskinfo.getFtpFilepath();
		String ftpPassword=taskinfo.getFtpPassword();
		int ftpPort=taskinfo.getFtpPort();
		String ftpUrl=taskinfo.getFtpUrl();
		String ftpUser=taskinfo.getFtpUser();
		String cron=taskinfo.getTaskCrontab();
		String taskTime=taskinfo.getTaskTime();
		int taskStatus= taskinfo.getStatus();
		String prvId=taskinfo.getPrvId();
		String collectionUrl=taskinfo.getCollectionUrl();
		if(taskStatus==StateComm.STATE_1){
			return 1;
		}
		Map<String,Object> map = new HashMap<String,Object>();
    	Scheduler scheduler = null;
    	int status = StateComm.STATE_0;
    	map.put("taskId", taskid);
    	map.put("collectionType", collectionType);
    	map.put("taskPeriod", taskPeriod);
    	map.put("taskTime", taskTime);
    	map.put("taskType", taskType);
    	map.put("ftpFilepath", ftpPath);
    	map.put("ftpPassword", ftpPassword);
    	map.put("ftpPort", ftpPort);
    	map.put("ftpUrl", ftpUrl);
    	map.put("ftpUser", ftpUser);
    	map.put("taskCrontab", cron);
    	map.put("prvId", prvId);
    	map.put("collectionUrl", collectionUrl);
		try {
			scheduler = SchedulerUtils.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
			status = StateComm.STATE_1;
		}
    	JobDetail jobDetail = SchedulerUtils.setJobs(CollectionJob.class, taskid+PromptMessageComm.JOB, taskid+PromptMessageComm.JGROUP, map,"");
    	CronTrigger trigger = SchedulerUtils.setCronTrigger(taskid+PromptMessageComm.TRIGGER, taskid+PromptMessageComm.TGROUP, jobDetail, map, 0, "", cron);
    	try {
			SchedulerUtils.runScheduler(scheduler, jobDetail, trigger);
			taskinfo.setStatus(StateComm.STATE_1);
			taskInfoMapper.updateByPrimaryKey(taskinfo);
		} catch (Exception e) {
			e.printStackTrace();
			status = StateComm.STATE_1;
		}
		return status;
	}

	@Override
	public int stopTask(String taskid) {
		
		TaskInfoVO taskinfo=taskInfoMapper.selectByPrimaryKey(taskid);
		
		int taskStatus= taskinfo.getStatus();
		if(taskStatus==StateComm.STATE_0){
			return 1;
		}
		
		Scheduler scheduler = null;
		int status = StateComm.STATE_0;
		try {
			scheduler = SchedulerUtils.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
			status = StateComm.STATE_1;
		}
		
		try {
			JobDetail jobDetail = scheduler.getJobDetail(new JobKey(taskid+PromptMessageComm.JOB, taskid+PromptMessageComm.JGROUP));
			Trigger trigger = scheduler.getTrigger(new TriggerKey(taskid+PromptMessageComm.TRIGGER, taskid+PromptMessageComm.TGROUP));
			if(jobDetail==null||trigger==null){
				return 1;
			}
			SchedulerUtils.pauseTigger(scheduler, trigger);
			SchedulerUtils.deleteTigger(scheduler, trigger);
			SchedulerUtils.delJob(scheduler, jobDetail);
			taskinfo.setStatus(StateComm.STATE_0);
			taskInfoMapper.updateByPrimaryKey(taskinfo);
		} catch (Exception e) {
			e.printStackTrace();
			status = StateComm.STATE_1;
		}	
		return status;
	}

	@Override
	public int runTask(String taskid,String user, String operateIP) {

		TaskInfoVO taskinfo=taskInfoMapper.selectByPrimaryKey(taskid);
		
		int taskPeriod=taskinfo.getTaskPeriod();
		int taskType=taskinfo.getTaskType();
		int collectionType=taskinfo.getCollectionType();
		String ftpPath=taskinfo.getFtpFilepath();
		String ftpPassword=taskinfo.getFtpPassword();
		int ftpPort=taskinfo.getFtpPort();
		String ftpUrl=taskinfo.getFtpUrl();
		String ftpUser=taskinfo.getFtpUser();
		String cron=taskinfo.getTaskCrontab();
		String taskTime=taskinfo.getTaskTime();
		String prvId=taskinfo.getPrvId();
		String collectionUrl=taskinfo.getCollectionUrl();
		
		Map<String,Object> map = new HashMap<String,Object>();
    	int status = StateComm.STATE_0;
    	map.put("taskId", taskid);
    	map.put("collectionType", collectionType);
    	map.put("taskPeriod", taskPeriod);
    	map.put("taskTime", taskTime);
    	map.put("taskType", taskType);
    	map.put("ftpFilepath", ftpPath);
    	map.put("ftpPassword", ftpPassword);
    	map.put("ftpPort", ftpPort);
    	map.put("ftpUrl", ftpUrl);
    	map.put("ftpUser", ftpUser);
    	map.put("taskCrontab", cron);
    	map.put("prvId", prvId);
    	map.put("collectionUrl", collectionUrl);
    	map.put("operateUser", user);
    	map.put("operateUserIp", operateIP);
    	try {
    		OneTimeJob oneTimeJob=new OneTimeJob();
    		boolean reslut= oneTimeJob.execute(map);
    		if(reslut){
    			status = StateComm.STATE_0;
    		}
    		else{
    			status = StateComm.STATE_1;
    		}
		} catch (Exception e) {
			status = StateComm.STATE_1;
		}
		return status;
	}

	@Override
	public boolean editTask(TaskInfoVO task) {
		
		int taskPeriod=task.getTaskPeriod();
		
		String taskTime=task.getTaskTime();
		
		String[] str=taskTime.split(PromptMessageComm.SYMBOL1);
		String hour=str[0];
		String min=str[1];
		String cron="0 "+min+" "+hour+" ";
		if(0==taskPeriod){
			cron+=PromptMessageComm.CRON1;
		}
		else if(1==taskPeriod){
			cron+=PromptMessageComm.CRON2;
		}
		else if(2==taskPeriod){
			cron+=PromptMessageComm.CRON3;
		}
		task.setTaskCrontab(cron);
		return taskInfoMapper.updateByPrimaryKeySelective(task);
	}

	@Override
	public TaskInfoVO getTaskByCollectionType(int collectionType,String prvId) {
		
		TaskInfoVO task = new TaskInfoVO();
		task.setCollectionType(collectionType);
		task.setPrvId(prvId);
		List<TaskInfoVO> tasks = taskInfoMapper.getTaskInfoByCollectionType(task);
		if (tasks.size() > 0) {
			task = tasks.get(0);
		}else if(tasks.size()==0){
			createTask(task);
		}
		return task;
	}

	@Override
	public List<FtpFileConfigVO> getFtpFileConfigByTaskId(String taskid) {
		// TODO Auto-generated method stub
		return ftpFileConfigVOMapper.getByTaskId(taskid);
	}

	@Override
	public boolean addFtpConfig(FtpFileConfigVO ftpFileConfig) {
		// TODO Auto-generated method stub
		
		ftpFileConfig.setFileId(IDUtils.getID());
		return ftpFileConfigVOMapper.insert(ftpFileConfig);
	}

	@Override
	public boolean editFtpConfig(FtpFileConfigVO ftpFileConfig) {
		// TODO Auto-generated method stub
		return ftpFileConfigVOMapper.updateByPrimaryKey(ftpFileConfig);
	}

	@Override
	public boolean delFtpConfig(String[] ids) {
		// TODO Auto-generated method stub
		return ftpFileConfigVOMapper.deleteFtpFileConfigs(ids);
	}

	@Override
	public List<FtpFileTypeVO> getFtpFileTypebyGroupId(int typeGroupId) {
		
		return ftpFileTypeVOMapper.getFtpFileTypebyGroupId(typeGroupId);
	}
	
	private void createTask(TaskInfoVO task){
		
		Date d=new Date();
		SimpleDateFormat dateFm = new SimpleDateFormat(PromptMessageComm.DATE_TYPE_24H);
		String createTime=dateFm.format(d);
		task.setTaskId(IDUtils.getID());
		task.setTaskType(StateComm.STATE_0);
		task.setCreateTime(createTime);
		task.setTaskPeriod(0);
		task.setTaskTime(PromptMessageComm.TASK_TIME);
		task.setTaskCrontab(PromptMessageComm.TASK_CRONTAB);
		task.setStatus(StateComm.STATE_0);
		int collectionType=task.getCollectionType();
		if(collectionType==TaskComm.CONT_COLL_0){
			task.setTaskName(PromptMessageComm.CONTRACT_DATA_COLL_TASK);
			task.setTaskDesc(PromptMessageComm.CONTRACT_DATA_COLL_TASK);
		}
		else if(collectionType==TaskComm.RES_COLL_1){
			task.setTaskName(PromptMessageComm.INTEGRAT_RESOURCE_COLL_TASK);
			task.setTaskDesc(PromptMessageComm.INTEGRAT_RESOURCE_COLL_TASK);
			task.setFtpPort(21);
		}
		else if(collectionType==TaskComm.RING_COLL_2){
			task.setTaskName(PromptMessageComm.DYNAMIC_RING_COLL_TASK);
			task.setTaskDesc(PromptMessageComm.DYNAMIC_RING_COLL_TASK);
			task.setFtpPort(21);
		}
		taskInfoMapper.insertSelective(task);
	}
	
	@Override
	public int clearHistoryLog(Map<String,Object> paramMap) {
		int result = taskHistoryInfoVOMapper.deleteHistoryLogVO(paramMap);
		return result;
	}

	@Override
	public PageInfo<TaskHistoryInfoVO> queryTaskHistoryLog(Map<String,Object> paramMap) {
		int pageSize = Integer.parseInt(paramMap.get("pageSize").toString());
		int pageNum = Integer.parseInt(paramMap.get("pageNum").toString());
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<TaskHistoryInfoVO> page = new PageInfo<TaskHistoryInfoVO>(taskHistoryInfoVOMapper.queryAllHistoryLogVO(paramMap));
		return page; 
	}

	@Override
	public int runTowerTask(String taskid, String user, String accountPeriod) {
		
		TaskInfoVO taskinfo = taskInfoMapper.selectByPrimaryKey(taskid);
		
		String prvId = taskinfo.getPrvId();
		String collectionUrl = taskinfo.getCollectionUrl();
		
		Map<String,Object> map = new HashMap<String,Object>();
    	int status = StateComm.STATE_0;
    	map.put("taskId", taskid);
    	map.put("collectionUrl", collectionUrl);
    	map.put("prvId", prvId);
    	map.put("taskTime", accountPeriod);
    	map.put("operateUser", user);
    	try {
    		OneTimeJob oneTimeJob = new OneTimeJob();
    		boolean reslut= oneTimeJob.execute(map);
    		if(reslut){
    			status = StateComm.STATE_0;
    		} else {
    			status = StateComm.STATE_1;
    		}
		} catch (Exception e) {
			status = StateComm.STATE_1;
		}
		return status;
	}
}