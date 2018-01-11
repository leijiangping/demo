package com.xunge.service.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.HighUpVO;

/**
 * @author jiacy
 * @date 2017年7月6日
 * @description 挂高范围
 */
public interface ITwrHighUpService {
	/**
	 * 查询所有挂高集合
	 * @param jiacy
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<HighUpVO>> queryAllHighUpVO(int pageSize,int pageNumber);
	/**
	 * 删除挂高
	 * @param jiacy
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public String deleteHighUpById(List<String> ids); 
	/**
	 * 更新挂高
	 * @param jiacy
	 * @param highUpVo
	 * @return
	 */
	public String updateHighUpById(HighUpVO highUpVo); 
	/**
	 * 新增挂高
	 * @param jiacy
	 * @param highUpVo
	 * @return
	 */
	public String insertHighUpById(HighUpVO highUpVo); 
	/**
	 * 启用停用
	 * @param jiacy
	 * @param ids
	 * @param highUpState
	 * @return
	 */
	public String updateStartOrStopHighUpById(List<String> ids,String highUpState);
	/**
	 * 按条件查询
	 * @param jiacy
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<HighUpVO>> queryHighUpVOByStateAndName(Map<String,Object> paramMap,int pageSize,int pageNumber);
}

