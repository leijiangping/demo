package com.xunge.service.job;

import java.util.List;
import java.util.Map;

import com.xunge.model.job.SysProvinceVO;
import com.xunge.model.system.region.DictionaryVO;

public interface IProvinceService {
	
	SysProvinceVO getProvinceByPk(String prvId);
	
	List<DictionaryVO> querySysDictionary(Map<String,Object> param);
}
