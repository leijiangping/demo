package com.xunge.service.job.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xunge.dao.basedata.system.SysDictionaryMapper;
import com.xunge.dao.job.SysProvinceVOMapper;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.model.system.region.DictionaryVO;
import com.xunge.service.job.IProvinceService;

@Service
public class ProvinceServiceImpl implements IProvinceService{

	@Resource
	private SysProvinceVOMapper sysProvinceVOMapper;
	
	@Resource
	private SysDictionaryMapper dictionaryMapper;
	
	@Override
	public SysProvinceVO getProvinceByPk(String prvId) {
		
		return sysProvinceVOMapper.selectByPrimaryKey(prvId);
	}

	@Override
	public List<DictionaryVO> querySysDictionary(Map<String, Object> param) {
		return dictionaryMapper.querySysDictionary(param);
	}

}
