package com.xunge.service.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.CommTypeVO;
import com.xunge.model.towerrent.bizbasedata.HighUpVO;
import com.xunge.model.towerrent.bizbasedata.ProductCostVO;
import com.xunge.model.towerrent.bizbasedata.WindPressureVO;
/**
 * @author jiacy
 * @date 2017年7月6日
 * @description 建造成本库
 */
public interface ITwrProductCostService {
	/**
	 * 查询所有建造成本库集合
	 * @author jiacy
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<ProductCostVO>> queryAllProCostVO(int pageSize,int pageNumber);
	/**
	 * 删除建造成本对象
	 * @author jiacy
	 * @param ids
	 * @return
	 */
	public String deleteProCostById(List<String> ids); 
	/**
	 * 修改建造成本库对象
	 * @author jiacy
	 * @param proCostVO
	 * @return
	 */
	public String updateProCostById(ProductCostVO proCostVO); 
	/**
	 * 增加建造成本库对象
	 * @author jiacy
	 * @param proCostVO
	 * @return
	 */
	public String insertProCostById(ProductCostVO proCostVO); 
	/**
	 * 启用或者停用建造成本库对象
	 * @author jiacy
	 * @param ids
	 * @param proCostState
	 * @return
	 */
	public String startOrStopProCostById(List<String> ids,String proCostState);
	/**
	 * 按照条件查询建造成本库对象
	 * @author jiacy
	 * @param paramMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Page<List<ProductCostVO>> queryProCostVOByStateAndName(Map<String,Object> paramMap,int pageSize,int pageNumber);
	/**
	 * 查询机房列表
	 * @author jiacy
	 * @return
	 */
	public List<String> queryRoomList();
	/**
	 * 查询配置列表
	 * @author jiacy
	 * @return
	 */
	public List<String> querySupptList();
	/**
	 * 查询铁塔类型列表
	 * @author jiacy
	 * @return
	 */
	public List<String> queryTwrList();
	
	/**
	 * 查询铁塔类型列表
	 * @author jiacy
	 * @return
	 */
	public List<ProductCostVO> querytwrTypeVO();
	/**·
	 * 查询所有风压
	 * @author jiacy
	 * @return
	 */
	public List<WindPressureVO> queryWindPress();
	/**
	 * 查询所有挂高
	 * @author jiacy
	 * @return
	 */
	public List<HighUpVO> queryHighUpVO();
	/**
	 * 查询机房配套类型
	 * @author jiacy
	 * @param paramMap
	 * @return
	 */
	public List<CommTypeVO> queryRoomAndSuptType(Map<String,Object> paramMap);
	
	
}
