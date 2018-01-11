package com.xunge.dao.system.user;

import java.util.List;
import java.util.Map;

import com.xunge.model.SysProvinceTreeVO;

public interface ISysProvinceDao {
	public List<SysProvinceTreeVO> queryOnePro(Map<String,Object> paraMap);
	/**
	 * 集团收集表新增功能-查询所有省份
	 * @return
	 * @author changwq
	 */
	public List<SysProvinceTreeVO> queryAllSimpleProvince();
}
