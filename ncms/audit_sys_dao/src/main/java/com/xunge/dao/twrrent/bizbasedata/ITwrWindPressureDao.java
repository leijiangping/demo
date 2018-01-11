package com.xunge.dao.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.WindPressureVO;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 风压范围
 */
public interface ITwrWindPressureDao {
	/**
	 * 查询所有风压集合
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<WindPressureVO>> queryAllWinPressVO(int pageSize,int pageNumber);
	/**
	 * 根据风压Id删除风压对象
	 * @param ids
	 * @return
	 */
	public String deleteWinPressById(List<String> ids); 
	/**
	 *  修改所选风压对象
	 * @param pressureVo
	 * @return
	 */
	public String updateWinPressById(WindPressureVO pressureVo); 
	/**
	 * 新增风压对象
	 * @param windPressVo
	 * @return
	 */
	public String insertWinPressById(WindPressureVO windPressVo); 
	/**
	 * 启用或停用风压状态
	 * @param ids
	 * @param winPressState
	 * @return
	 */
	public String startOrStopWinPressById(List<String> ids,String winPressState);
	/**
	 * 根据状态和名称查询符合条件的风压集合
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<WindPressureVO>> queryWinPressVOByStateAndName(Map<String,Object> paramMap,int pageSize,int pageNumber);
}
