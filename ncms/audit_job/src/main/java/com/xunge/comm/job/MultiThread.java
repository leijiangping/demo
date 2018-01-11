package com.xunge.comm.job;

import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.service.job.IJobSyncService;

/**
 * 多线程并发处理
 * 
 */
public class MultiThread implements Runnable {

	private IJobSyncService jobSyncService;
	private TaskInfoVO taskInfo;
	private String taskType;

	public MultiThread() {
	}

	public MultiThread(IJobSyncService jobSyncService,TaskInfoVO taskInfo,String taskType) {
		this.jobSyncService = jobSyncService;
		this.taskInfo = taskInfo;
		this.taskType = taskType;
	}

	@Override
	public void run() {
		if (taskType.equalsIgnoreCase("contract")){
			jobSyncService.syncContractData(taskInfo,"contract");
		}else if(taskType.equalsIgnoreCase("res")){
			jobSyncService.syncContractData(taskInfo,"res");
		}else if(taskType.equalsIgnoreCase("ring")){
			jobSyncService.syncContractData(taskInfo,"ring");
		}else if(taskType.equalsIgnoreCase("tower")){
			jobSyncService.syncTowerData(taskInfo);
		}
	}

}
