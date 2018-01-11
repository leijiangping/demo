package com.xunge.dao.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.CostDifferVO;

/**
 * @author jcy
 * @date 2017年7月6日
 * @description 费用差异范围
 */
public interface ITwrChargeDiffDao {
	/**
	 * 查询费用差异范围对象
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<CostDifferVO>> queryAllObj(int pageSize,int pageNumber,Map<String,Object> paramMap);
	/**
	 * 删除对象
	 * @param ids
	 * @return
	 */
	public String deleteObjById(List<String> ids); 
	/**
	 * 修改对象
	 * @param costDiffVo
	 * @return
	 */
	public String updateObjById(CostDifferVO costDiffVo); 
	/**
	 * 新增对象
	 * @param costDiffVo
	 * @return
	 */
	public String insertObjById(CostDifferVO costDiffVo); 
	/**
	 * 按条件过滤
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<CostDifferVO>> queryObjByCondition(Map<String,Object> paramMap,int pageSize,int pageNumber);
}
