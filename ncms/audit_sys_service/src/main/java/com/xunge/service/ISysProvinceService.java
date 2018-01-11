package com.xunge.service;

import java.util.List;

import com.xunge.model.SysDataAuthMenuTreeVO;
import com.xunge.model.SysProvinceTreeVO;

/**
 * 区县树结构 service 接口
 *
 */
public interface ISysProvinceService {
	/**
	 * 根据省份id查询地区树
	 * @param prvId
	 * @return
	 */
	public List<SysDataAuthMenuTreeVO> queryOneProRedis(String prvId);
	/**
	 * 集团收集表-新增页面查询所有省份
	 * @return
	 * @author changwq
	 */
	public List<SysProvinceTreeVO> queryAllSimpleProvince();
}
