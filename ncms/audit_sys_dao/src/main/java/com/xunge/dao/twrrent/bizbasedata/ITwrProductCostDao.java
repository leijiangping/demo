package com.xunge.dao.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.CommTypeVO;
import com.xunge.model.towerrent.bizbasedata.HighUpVO;
import com.xunge.model.towerrent.bizbasedata.ProductCostVO;
import com.xunge.model.towerrent.bizbasedata.WindPressureVO;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 建造成本库
 */
public interface ITwrProductCostDao {
	/**
	 * 查询所有建造成本库集合
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<ProductCostVO>> queryAllProCostVO(int pageSize,int pageNumber);
	/**
	 * 删除建造成本库对象
	 * @param ids
	 * @return
	 */
	public String deleteProCostById(List<String> ids); 
	/**
	 * 修改建造成本库对象
	 * @param proCostVO
	 * @return
	 */
	public String updateProCostById(ProductCostVO proCostVO); 
	/**
	 * 增加建造成本库对象
	 * @param proCostVO
	 * @return
	 */
	public String insertProCostById(ProductCostVO proCostVO); 
	/**
	 * 启用或者停用建造成本库对象
	 * @param ids
	 * @param proCostState
	 * @return
	 */
	public String startOrStopProCostById(List<String> ids,String proCostState);
	/**
	 * 按照条件查询建造成本库对象
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<ProductCostVO>> queryProCostVOByStateAndName(Map<String,Object> paramMap,int pageSize,int pageNumber);
	/**
	 * 新增时返回铁塔列表
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<ProductCostVO> querytwrTypeVO();
	/**
	 * 新增时返回风压列表
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<WindPressureVO> queryWindPress();
	/**
	 * 新增时返回挂高列表
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<HighUpVO> queryHighUpVO();
	/**
	 * 新增时返回机房和配套类型列表
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<CommTypeVO> queryRoomAndSuptType(Map<String, Object> paramMap);
	/**
	 * 查询机房类型
	 * @return
	 */
	public List<String> queryRoomList();
	/**
	 * 查询配套类型
	 * @return
	 */
	public List<String> querySupptList();
	/**
	 * 查询铁塔列表
	 * @return
	 */
	public List<String> queryTwrList();
}
