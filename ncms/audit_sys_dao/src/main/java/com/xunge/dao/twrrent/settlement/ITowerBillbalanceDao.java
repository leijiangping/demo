package com.xunge.dao.twrrent.settlement;


import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.settlement.TowerBillbalanceVO;
/**
 * @description 铁塔账单表
 * @author zhujj
 * @date 2017年7月6日 下午2:38:10 
 * @version 1.0.0 
 */
public interface ITowerBillbalanceDao {
	/**
	 * @description删除铁塔账单数据
	 * @param towerbillbalanceId 铁塔账单ID
	 * @return 
	 */
	public int deleteByPrimaryKey(String towerbillbalanceId);
    /**
     * @description插入数据
     * @param entity 铁塔账单
     * @return
     */
    public int insertTowerBillbalance(TowerBillbalanceVO entity);
    /**
     * @description 插入不为空的数据
     * @param entity 铁塔账单
     * @return
     */
    public int insertSelective(TowerBillbalanceVO entity);
    /**
     * @description 批量插入不为空的数据
     * @param list 租赁账单集合
     * @return
     */
    public int insertBatchSelective(List<TowerBillbalanceVO> list);
    
    /**
     * @description 查询铁塔账单
     * @param towerbillbalanceId 铁塔账单ID
     * @return
     */
    public TowerBillbalanceVO selectByPrimaryKey(String towerbillbalanceId);
    /**
     * @description 更新铁塔账单不为空的数据
     * @param entity 铁塔账单
     * @return
     */
    public int updateByPrimaryKeySelective(TowerBillbalanceVO entity);
    /**
     * @description 批量更新
     * @param list 租赁账单集合
     * @return
     */
    public int updateBatchByPrimaryKeySelective(List<TowerBillbalanceVO> list);
    /**
     * @description 更新铁塔账单
     * @param entity
     * @return
     */
    public int updateByPrimaryKey(TowerBillbalanceVO entity);
    /**
     * @description 查询铁塔账单分页数据
     * @param paramMap
     * @param paramMap:pageSize
     * @param paramMap:pageNumber
     * @param paramMap:prvId 运营商地市
     * @param paramMap:pregId 运营商县区
     * @param paramMap:accountMonth 账期月份
     * @param paramMap:businessConfirmNumber 产品业务确认单编号
     * @param paramMap:towerStationCode 站点名称或者编码
     * @return Page<List<TowerBillbalanceVO>>
     */
	public Page<TowerBillbalanceVO> queryPageTowerBillbalance(Map<String,Object> paramMap);
	/**
     * @description 查询铁塔账单数据
     * @param paramMap
     * @param paramMap:prvId 运营商地市
     * @param paramMap:pregId 运营商县区
     * @param paramMap:accountMonth 账期月份
     * @param paramMap:businessConfirmNumber 产品业务确认单编号
     * @param paramMap:towerStationCode 站点名称或者编码
     * @return List<TowerBillbalanceVO>
     */
	public List<TowerBillbalanceVO> queryTowerBillbalance(Map<String,Object> paramMap);
	
	/**
     * @description 查询移动账单分页数据
     * @author wangz
     * @param paramMap
     * @param paramMap:pageSize
     * @param paramMap:pageNumber
     * @param paramMap:prvId 运营商地市
     * @param paramMap:pregId 运营商县区
     * @param paramMap:accountMonth 账期月份
     * @param paramMap:businessConfirmNumber 产品业务确认单编号
     * @param paramMap:towerStationCode 站点名称或者编码
     * @return Page<TowerBillbalanceVO>
     */
	public Page<TowerBillbalanceVO> queryPageMobileBillbalance(Map<String, Object> paramMap);
	
	/**
     * @description 查询移动账单或者铁塔数据
     * @author wangz
     * @param paramMap
     * @param paramMap:prvId 运营商地市
     * @param paramMap:pregId 运营商县区
     * @param paramMap:accountMonth 账期月份
     * @param paramMap:businessConfirmNumber 产品业务确认单编号
     * @param paramMap:towerStationCode 站点名称或者编码
     * @return List<TowerBillbalanceVO>
     */
	public List<TowerBillbalanceVO> queryTowerOrMobileBillbalance(Map<String, Object> paramMap);
	
	/**
	 * @description 生成账单批量保存数据入库
	 * @author wangz
	 * @param collections
	 * @return
	 */
	public int insertBatchMobileBill(List<TowerBillbalanceVO> listVo);
	
	/**
	 * @description 重新生成账单批量修改
	 * @author wangz
	 * @param collections
	 * @return
	 */
	public int updateBatchMobileBill(List<TowerBillbalanceVO> listVo);
	
	/**
	 * @description 根据参数查询账单信息，用来判断账单是否存在
	 * @param paramMap
	 * @return
	 */
	public List<TowerBillbalanceVO> queryMobileBill(Map<String, Object> paramMap);
	/**
	 * @description 根据铁塔站址编码和年月查询账单
	 * @param paraMap
	 * @return
	 */
	public String queryBalance(Map<String,Object> paraMap);
	/**
	 * @description 根据铁塔站址编码查询账单的月基准参数数据
	 * @param paraMap
	 * @return
	 */
	public List<TowerBillbalanceVO> queryParameter(Map<String,Object> paraMap);
}