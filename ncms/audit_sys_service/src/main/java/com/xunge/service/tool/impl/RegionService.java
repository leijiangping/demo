package com.xunge.service.tool.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xunge.dao.basedata.SysRegionVOMapper;
import com.xunge.model.basedata.SysRegionVO;
import com.xunge.model.basedata.SysRegionVOExample;
import com.xunge.model.basedata.SysRegionVOExample.Criteria;
import com.xunge.service.tool.IRegionService;

@Service
public class RegionService implements IRegionService{

	@Resource
	private SysRegionVOMapper sysRegionVOMapper;
	
	@Override
	public List<SysRegionVO> getCitys(String prvId) {
		SysRegionVOExample example = new SysRegionVOExample();
		Criteria criteria = example.createCriteria();
		criteria.andRegStateEqualTo(0);
		criteria.andLevelEqualTo(1);
		if(!StringUtils.isEmpty(prvId)){
			criteria.andPrvIdEqualTo(prvId);
		}
		return sysRegionVOMapper.selectByExample(example);
	}

	@Override
	public List<SysRegionVO> getRegion(String pregId) {
		SysRegionVOExample example = new SysRegionVOExample();
		Criteria criteria = example.createCriteria();
		criteria.andRegStateEqualTo(0);
		criteria.andLevelEqualTo(2);
		if(!StringUtils.isEmpty(pregId)){
			criteria.andPregIdEqualTo(pregId);
		}
		return sysRegionVOMapper.selectByExample(example);
	}

}
