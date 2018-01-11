package com.xunge.service.job.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xunge.dao.job.SysProvinceVOMapper;
import com.xunge.model.job.SysProvinceVO;
import com.xunge.service.job.IProvinceService;

@Service
public class ProvinceServiceImpl implements IProvinceService{

	@Resource
	private SysProvinceVOMapper sysProvinceVOMapper;
	
	@Override
	public SysProvinceVO getProvinceByPk(String prvId) {
		
		return sysProvinceVOMapper.selectByPrimaryKey(prvId);
	}

}
