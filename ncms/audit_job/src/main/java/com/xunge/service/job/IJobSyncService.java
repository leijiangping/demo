package com.xunge.service.job;

import com.xunge.model.colletion.TaskInfoVO;

public interface IJobSyncService {

	void syncContractData(TaskInfoVO taskInfo,String type);
	
	void syncTowerData(TaskInfoVO taskInfo);
}
