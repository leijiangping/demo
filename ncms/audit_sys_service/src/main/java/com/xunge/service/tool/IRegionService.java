package com.xunge.service.tool;

import java.util.List;

import com.xunge.model.basedata.SysRegionVO;

public interface IRegionService {

	public List<SysRegionVO> getCitys(String prvId);
	
	public List<SysRegionVO> getRegion(String pregId);
}
