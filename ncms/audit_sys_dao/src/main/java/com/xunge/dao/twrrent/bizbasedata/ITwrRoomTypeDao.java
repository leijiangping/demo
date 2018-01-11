package com.xunge.dao.twrrent.bizbasedata;

import java.util.List;



import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizbasedata.CommTypeVO;

/**
 * @description 铁塔账单表
 * @author zhaosf
 * @date 2017年7月19日 上午10:52:10 
 */
public interface ITwrRoomTypeDao {
	/**
	 * @description增加铁塔字典数据
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
	public String deleteRoomTypeByCommTypeId(Map<String,Object> paraMap);
	/**
	 * @description 根据机房名称查询机房类型数据
	 * @param commTypeName 机房名称
	 * @return 
	 */
	public Page<List<CommTypeVO>> queryAllRoomTypeByCondition(Map<String, Object> paraMap,Integer pageSize,Integer pageNum);
	/**
	 * @description 根据机房类型查询所有数据字典数据
	 * @param commTypeGroup 数据字典类型
	 * @return 
	 */
	public Page<List<CommTypeVO>> queryAllRoomType(String commTypeGroup,Integer pageSize,Integer pageNum);
	/**
	 * @description 根据机房类型修改机房状态
	 * @param paraMap 数据字典类型
	 * @return 
	 */
	public String startOrStopRoomType(Map<String,Object> paraMap);
	/**根据类型查询所有数据字典数据
	 * @param commTypeGroup
	 * @return
	 */
	public List<CommTypeVO> queryTwrCommType(Map<String,Object> paraMap);
}
