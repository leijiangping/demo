package com.xunge.service.towerrent.mobile;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.mobile.TwrRentInformationVO;
import com.xunge.model.towerrent.rentinformation.TwrRentInformationChangeVO;
import com.xunge.model.towerrent.rentmanager.TowerRentInformationHistoryVO;
/**
 * 移动资源信息service
 * @author xup
 *
 */
public interface ITwrRentInformationService {
	/**
	 * 移动资源信息查询
	 * @author xup
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<TwrRentInformationVO> queryTwrRentInformation(Map<String, Object> map,int pageNum,int pageSize);
	/**
	 * 移动资源信息查询
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<TwrRentInformationVO> queryTwrRentInformationList(Map<String, Object> map);
	
	/**
	 * @author xup
	 * @param id
	 * @param date
	 * @return
	 */
	public int updateCommit(String id,Date date);
	
	/**
	 * 修改拆分表中的数据
	 * @author jiacy
	 * @param twrRentInformationVO
	 * @return
	 */
	public String updateTwrRentInformationTemp(Map<String,Object> paramMap);
	
	/**
	 * 根据起租表id查询拆分表历史记录
	 * @author jiacy
	 * @param twrRentInfoId
	 * @return
	 */
	public Page<List<TowerRentInformationHistoryVO>> queryTwrInfoHistoryVO(String twrRentInfoId,int pageNum,int pageSize);
	/**
	 * 根据拆分表id修改起租表信息
	 * @author xup
	 * @param id
	 * @return
	 */
	public int updateRentinformation(Map<String,Object> map);
	/**
	 *更新拆分表
	 * @author jiacy
	 * @param twrRentInfoId
	 * @return
	 */
	public String updateTwrInfoHistoryVO(TowerRentInformationHistoryVO historyVO);
	/**
	 * 新增拆分表数据
	 * @author jiacy
	 * @param twrRentInfoId
	 * @return
	 */
	public String insertTwrInfoHistoryVO(TowerRentInformationHistoryVO historyVO);
	
	/**
	 * 
	 * @author xup
	 * @param businessConfirmNumber
	 * @param towerStationCode
	 * @return
	 */
	public String queryTwrRentInformationByTower(String businessConfirmNumber,String towerStationCode);
	/**
	 * @author changwq
	 * @param paraMap
	 * @return
	 */
	public int updateToTowerRentInformationHistoryVO(Map<String,Object> paraMap);
	/**
	 * 从铁塔侧复制数据到移动侧
	 * @author changwq
	 */
	public String insertTwrRentInformationFromTwrRentInformationTower(String rentinformationId,String rentinformationtowerId);
	/**
	 * 从铁塔侧获取数据更新到移动侧
	 * @param paraMap
	 * @return
	 * @author changwq
	 */
	public String updateTwrRentInformationFromTwrRentInformationTower(String rentinformationId,String rentinformationtowerId);
	
	/**
	 * 根据拆分表id查询VO对象
	 * @author jiacy
	 * @param twrRentInfoId
	 * @return
	 */
	public TowerRentInformationHistoryVO queryHistoryVOByid(String rentHistoryId);
	/**
	 * 查询新增的移动起租信息的期始和期终
	 * @author jiacy
	 * @param paraMap
	 * @return
	 * @author changwq
	 */
	public TwrRentInformationVO queryTime(String rentinformationId);
	/**
	 * 修改变更表流程状态
	 * @author jiacy
	 * @param map
	 * @return
	 */
	public int updateRentinformationChange(Map<String,Object> map);
	/**
	 * 导出移动起租信息全查
	 * @param paraMap
	 * @return
	 * @author xup
	 */
	public List<TwrRentInformationVO> queryExportTwrRentInformation(Map<String,Object> paraMap);
	/**
	 * 导出移动起租信息全查
	 * @author jiacy
	 * @param paraMap
	 * @return
	 * @author jiacy
	 */
	public List<TowerRentInformationHistoryVO> queryExportTwrRentInformationHistory(Map<String,Object> paraMap);
	/**
	 * 根据审核通过的铁塔信息变更数据更新移动起租表
	 * @author jiacy
	 * @param paraMap
	 * @return
	 */
	public String updateTwrRentInformationByBizChange(String changeItem,String changeContent,String rentinformationId);
	/**
	 * 根据起租表id查询变更表信息
	 * @author jiacy
	 * @param paraMap
	 * @return
	 */
	public List<TwrRentInformationChangeVO> queryTwrRentInformationChange(Map<String,Object> paraMap);
	
	/**
	 * 根据id查询配置id
	 * @author jiacy
	 * @param rentinformationId
	 * @return
	 */
	public String queryProConfigIdById(String rentinformationId);
	/**
	 * 如果配置为无塔则新增一条数据到无塔参数表
	 * @author jiacy
	 * @param paraMap
	 * @return
	 */
	public String insertNoTowerConfig(String nottowerconfigId,String productTypeId,String towerStationCode,String businessConfirmNumber);
	/**
	 * 根据配置id查找铁塔种类id
	 * @author jiacy
	 * @param productconfigId
	 * @return
	 */
	public String queryTypeIdByConfigId(String productconfigId);
	/**
	 * 根据拆分表id查询对应的变更表信息
	 * @author jiacy
	 * @param paraMap
	 * @return
	 */
	public List<TwrRentInformationChangeVO> queryTwrRentInformationChangeByHistoryId(Map<String,Object> paraMap);
	
	/**
	 * 条件查询拆分表信息
	 * @author xup
	 * @param paraMap
	 * @return
	 */
	public List<TowerRentInformationHistoryVO> queryTowerRentInformationHistoryByConditions(Map<String,Object> paraMap);
	/**
	 * 根据业务确认单号和站址编码修改移动起租信息结束时间
	 * @author jiacy
	 * @param paraMap
	 * @return
	 */
	public String updateEndDateByStopServer(String rentinformationId,Date endDate);
}
