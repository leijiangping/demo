package com.xunge.service.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.CostDifferVO;
/**
 * @author jiacy
 * @date 2017年7月6日
 * @description 费用差异范围
 */
public interface ITwrCostDiffService {
	/**
	 * 查询
	 * @param jiacy
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<CostDifferVO>> queryAllObj(int pageSize,int pageNumber,Map<String,Object> paramMap);
	/**
	 * 删除
	 * @param jiacy
	 * @param ids
	 * @return
	 */
	public String deleteObjById(List<String> ids); 
	/**
	 * 修改
	 * @param jiacy
	 * @param costDiffVo
	 * @return
	 */
	public String updateObjById(CostDifferVO costDiffVo); 
	/**
	 * 新增
	 * @param jiacy
	 * @param costDiffVo
	 * @return
	 */
	public String insertObjById(CostDifferVO costDiffVo); 
	/**
	 * 过滤
	 * @param jiacy
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<CostDifferVO>> queryObjByCondition(Map<String,Object> paramMap,int pageSize,int pageNumber);
}
