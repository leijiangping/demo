package com.xunge.dao.towerrent.mobile;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.mobile.TwrRentInformationVO;
import com.xunge.model.towerrent.rentinformation.TwrRentInformationChangeVO;
import com.xunge.model.towerrent.rentmanager.TowerRentInformationHistoryVO;
import com.xunge.model.towerrent.rentmanager.TowerResourceInfoVO;
/**
 * 移动资源信息dao
 * @author xup
 *
 */
public interface ITwrRentInformationDao {
	/**
	 * 分页查询移动侧数据
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<TwrRentInformationVO> queryTwrRentInformation(Map<String, Object> map,int pageNum,int pageSize);
	/**
	 * 查询移动侧数据
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<TwrRentInformationVO> queryTwrRentInformationList(Map<String, Object> map);
	/**
	 * 修改拆分表流程状态
	 * @param map
	 * @return
	 */
	public int updateRentinformationHistory(Map<String,Object> map);
	
	/**
	 * 修改变更表流程状态
	 * @param map
	 * @return
	 */
	public int updateRentinformationChange(Map<String,Object> map);
	/**
	 * 查询移动侧数据
	 * @author yuefy
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<TowerResourceInfoVO> queryTwrRentInformation(Map<String, Object> map);
	
	
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
	 * 根据拆分表id查询拆分表信息
	 * @author xup
	 * @param id
	 * @return
	 */
	public TowerRentInformationHistoryVO queryById(String id);
	
	/**
	 * 根据拆分表对象更新起租表信息
	 * @param historyVO
	 * @return
	 */
	public int  updateTwrRentInformation(Map<String,Object> map);
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
	 * 根据站址编码和业务确认单号查询移动起租表id
	 */
	public String queryTwrRentInformationByTower(Map<String,Object> paraMap);
	/**
	 * 删除拆分表新增记录
	 * @param paraMap
	 * @return
	 */
	public int deleteTowerRentInformationHistoryVO(Map<String,Object> paraMap);
	/**
	 * 初始化拆分表数据
	 * @param paraMap
	 * @return
	 */
	public int updateTwrInfoHistory(Map<String,Object> paraMap);
	/**
	 * 根据起租表id查询拆分表id 
	 * @param paraMap
	 * @return
	 */
	public String queryByRentinformationId(Map<String,Object> paraMap);
	/**
	 * 从铁塔侧获取数据新增到移动侧
	 * @author changwq
	 */
	public int insertTwrRentInformationFromTwrRentInformationTower(Map<String,Object> paraMap);
	/**
	 * 从铁塔侧获取数据更新到移动侧
	 * @param paraMap
	 * @return
	 * @author changwq
	 */
	public int updateTwrRentInformationFromTwrRentInformationTower(Map<String,Object> paraMap);
	/**
	 * 查询新增的移动起租信息的期始和期终
	 * @param paraMap
	 * @return
	 * @author changwq
	 */
	public TwrRentInformationVO queryTime(Map<String,Object> paraMap);
	
	/**
	 * 导出移动起租信息全查
	 * @param paraMap
	 * @return
	 * @author xup
	 */
	public List<TwrRentInformationVO> queryExportTwrRentInformation(Map<String,Object> paraMap);
	/**
	 * 导出移动起租信息拆分表全查
	 * @param paraMap
	 * @return
	 * @author jiacy
	 */
	public List<TowerRentInformationHistoryVO> queryExportTwrRentInformationHistory(Map<String,Object> paraMap);
	/**
	 * 根据审核通过的铁塔信息变更数据更新移动起租表
	 * @param paraMap
	 * @return
	 */
	public int updateTwrRentInformationByBizChange(Map<String,Object> paraMap);
	
	/**
	 * 根据起租表id查询变更表信息
	 * @param paraMap
	 * @return
	 */
	public List<TwrRentInformationChangeVO> queryTwrRentInformationChange(Map<String,Object> paraMap);
	/**
	 * 查询铁塔配置id
	 * @param paraMap
	 * @return
	 */
	public String queryProConfigIdById(Map<String,Object> paraMap);
	/**
	 * 如果配置为无塔则新增一条数据到无塔参数表
	 * @param paraMap
	 * @return
	 */
	public int insertNoTowerConfig(Map<String,Object> paraMap);
	/**
	 * 根据配置id查找铁塔种类id
	 * @param paraMap
	 * @return
	 */
	public String queryTypeIdByConfigId(Map<String,Object> paraMap);
	/**
	 * 根据业务确认单号 铁塔站址编码  生效日期判断是否在服务有效期查找TwrRentInformation
	 * @param paraMap
	 * @author yuefy
	 * @return
	 */
	public TwrRentInformationVO queryTwrRentInformationByBusinessNumDate(Map<String,Object> paraMap);
	
	/**
	 * 条件查询拆分表信息
	 * @author xup
	 * @param paraMap
	 * @return
	 */
	public List<TowerRentInformationHistoryVO> queryTowerRentInformationHistoryByConditions(Map<String,Object> paraMap);
	/**
	 * 根据业务确认单号和站址编码修改移动起租信息结束时间
	 * @param paraMap
	 * @return
	 */
	public int updateEndDateByStopServer(Map<String,Object> paraMap);
	/**
	 * 根据铁塔站址编码查询移动起租信息
	 * @param paraMap
	 * @return
	 */
	public List<TwrRentInformationVO> queryMsgByTwrStaCode(Map<String,Object> paraMap);
}
