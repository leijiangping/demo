package com.xunge.service.job;

import com.xunge.model.job.SysProvinceVO;

public interface IContractJobService {
	
	
	public boolean dealContract(String url,String dateStr,SysProvinceVO sysProvinceVO);

}
