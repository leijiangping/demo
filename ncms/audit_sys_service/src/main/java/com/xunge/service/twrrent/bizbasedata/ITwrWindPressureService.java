package com.xunge.service.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.WindPressureVO;
/**
 * @author jiacy
 * @date 2017年7月6日
 * @description 风压
 */
public interface ITwrWindPressureService {
	/**
	 * 查询所有风压对象
	 * @author jiacy
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<WindPressureVO>> queryAllWinPressVO(int pageSize,int pageNumber);
	/**
	 * 根据风压Id删除风压对象
	 * @author jiacy
	 * @param ids
	 * @return
	 */
	public String deleteWinPressById(List<String> ids); 
	/**
	 * 修改风压对象
	 * @author jiacy
	 * @param pressureVo
	 * @return
	 */
	public String updateWinPressById(WindPressureVO pressureVo); 
	/**
	 * 新增风压对象
	 * @author jiacy
	 * @param windPressVo
	 * @return
	 */
	public String insertWinPressById(WindPressureVO windPressVo); 
	/**
	 * 启用或停用风压对象
	 * @author jiacy
	 * @param ids
	 * @param winPressState
	 * @return
	 */
	public String updateStartOrStopWinPressById(List<String> ids,String winPressState);
	/**
	 * 按条件查询
	 * @author jiacy
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<WindPressureVO>> queryWinPressVOByStateAndName(Map<String,Object> paramMap,int pageSize,int pageNumber);
}
