package com.xunge.service.towerrent.rentinformationhistory;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.rentmanager.TowerRentInformationHistoryVO;

import java.util.Date;

public interface ITwrRentInformationHistoryService {
	/**
	 * 复制移动起租表到拆分表
	 * @param rentinformationhistoryId
	 * @param rentinformationId
	 * @return
	 */
	public String insertRentInformationHistoryFromMobile(String rentinformationhistoryId,String rentinformationId);
	/**
	 * 根据移动起租信息id查找所有拆分信息
	 * @param rentinformationId
	 * @return
	 */
	public List<TowerRentInformationHistoryVO> queryAllHistoryById(String rentinformationId);
	/**
	 * 修改生效日期重叠的拆分数据
	 * @param changeItem
	 * @param rentinformationhistoryId
	 * @param changeBehindContent
	 * @return
	 */
	public String updateChangeItemById(String changeItem,String rentinformationhistoryId,String changeBehindContent);
	/**
	 * 修改最后一条拆分数据终止日期
	 * @param endDate
	 * @param rentinformationhistoryId
	 * @return
	 */
	public String updateEndDateById(Date endDate,String rentinformationhistoryId);
	/**
	 * 新增拆分数据，根据日期重叠的拆分纪录
	 * @param towerRentInformationHistoryVO
	 * @return
	 */
	public String insertSelective(TowerRentInformationHistoryVO towerRentInformationHistoryVO);
	
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
	 * 删除超出服务时间的拆分信息
	 * @param rentInformationId
	 * @param endDate
	 * @return
	 */
	public String deleteHistoryByDate(String rentInformationId,Date endDate);
}
