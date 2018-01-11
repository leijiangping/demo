package com.xunge.dao.towerrent.rentinformationhistory;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.rentmanager.TowerRentInformationHistoryVO;


public interface ITwrRentInformationHistoryDao {
	/**
	 * 移动侧新增数据复制到拆分信息
	 * @param paraMap
	 * @return
	 */
	public int insertRentInformationHistoryFromMobile(Map<String,Object> paraMap);
	/**
	 * 根据移动起租表id查询所有拆分表信息
	 */
	public List<TowerRentInformationHistoryVO> queryAllHistoryById(Map<String,Object> paraMap);
	/**
	 * 根据变更表id和变更字段修改日期重叠的拆分信息
	 * @param paraMap
	 * @return
	 */
	public int updateChangeItemById(Map<String,Object> paraMap);
	/**
	 * 修改最后一条拆分纪录期终时间
	 * @param paraMap
	 * @return
	 */
	public int updateEndDateById(Map<String,Object> paraMap);
	/**
	 * 新增一条拆分纪录
	 * @param towerRentInformationHistoryVO
	 * @return
	 */
	public int insertSelective(TowerRentInformationHistoryVO towerRentInformationHistoryVO);
	

	/**
	 * 查询拆分信息计算移动账单
	 * @author wangz
	 * @param paraMap
	 * @return
	 */
	public List<TowerRentInformationHistoryVO> queryTowerRentInfoHistory(Map<String, Object> paraMap);
	
	/**
	 * 查询拆分信息分页列表
	 * @author wangz
	 * @param paramMap
     * @param paramMap:pageSize
     * @param paramMap:pageNumber
     * @param paramMap:prvId 运营商地市
     * @param paramMap:pregId 运营商县区
     * @param paramMap:accountMonth 账期月份
     * @param paramMap:businessConfirmNumber 产品业务确认单编号
     * @param paramMap:towerStationCode 站点名称或者编码
     * @return Page<TowerRentInformationHistoryVO>
	 */
	public Page<TowerRentInformationHistoryVO> queryPageRentInfoHistory(Map<String, Object> paraMap);
	/**
	 * 删除超出服务期限的拆分纪录
	 * @param paraMap
	 * @return
	 */
	public int deleteHistoryByDate(Map<String,Object> paraMap);
}