/**
 * 
 */
package com.xunge.service.twrrent.bizbasedata;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.CommTypeVO;

/**
 * @description 实现机房类型的增删查改
 * @author zhaosf
 * @date 日期：2017年9月19日 时间：下午1:45:01
 */
public interface ITwrRoomTypeService {
	
	/**
	 * @description 增加铁塔字典数据
	 * @param commTypeVO 铁塔字典
	 * @return 
	 */
	public String insertRoomType(CommTypeVO commTypeVO);
	/**
	 * @description 修改铁塔字典数据
	 * @param commTypeId 铁塔字典Id
	 * @return 
	 */
	public String updateRoomTypeByCommTypeId(CommTypeVO commTypeVO);
	/**
	 * @description 删除铁塔字典数据
	 * @param paraMap 铁塔字典Id
	 * @return 
	 */
	public String deleteRoomTypeByCommTypeId(List<String> roomTypeIdList);
	/**
	 * @description 根据机房名称查询机房类型数据
	 * @param commTypeName 机房名称
	 * @return 
	 */
	public Page<List<CommTypeVO>> queryAllRoomTypeByCondition(Map<String, Object> paraMap,Integer pageSize,Integer pageNum);
	/**
	 * @description 查询所有机房信息
	 * @param commTypeGroup 数据字典类型
	 * @return 
	 */
	public Page<List<CommTypeVO>> queryAllRoomType(String commTypeGroup,Integer pageSize,Integer pageNum);
	/**
	 * @description 根据机房类型修改机房状态
	 * @param paraMap 数据字典类型
	 * @return 
	 */
	public String startOrStopRoomType(List<String> roomTypeIds,int roomtypeState);
	
	/**根据条件查询铁塔公共字典信息
	 * @param paraMap
	 * @return
	 */
	public List<CommTypeVO> queryTwrCommType(Map<String, Object> paraMap);
	
}
