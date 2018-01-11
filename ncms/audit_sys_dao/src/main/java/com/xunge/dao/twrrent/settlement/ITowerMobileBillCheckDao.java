package com.xunge.dao.twrrent.settlement;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.settlement.TowerAndMobileBillConfirmVO;
import com.xunge.model.towerrent.settlement.TowerAndMobileBillVO;
import com.xunge.model.towerrent.settlement.TowerBillbalanceVO;

public interface ITowerMobileBillCheckDao {
	/**
	 * 查询铁塔侧和移动侧账单数据
	 * @param paraMap
	 * @return
	 * 2017年7月10日 lpw
	 */
	Page<List<TowerAndMobileBillVO>> queryTowerAndMobileFee(Map<String, Object> paraMap,int pageNumber, int pageSize);
	
	/**
	 * 将对账结果更新入铁塔侧账单表中
	 * @param towerAndMobileBillVO
	 * @return 
	 */
	int updateCompareResult(TowerAndMobileBillVO towerAndMobileBillVO);
	/**
	 *	修改确认结果
	 * @param billConfirmList
	 * @return
	 */
	public int updateTowerMobileBillState(List<TowerBillbalanceVO> billConfirmList);
	/**
	 * 确认结果
	 * @param paraMap
	 * @return
	 */
	public int updateTowerMobileBillConfirmState(Map<String, Object> paraMap);
	/**
	 * 取消确认
	 * @param paraMap
	 * @return
	 */
	int updateCancleConfirmState(Map<String, Object> paraMap);
	
	/**
	 * 查询所有数据
	 * @param map
	 * @return
	 */
	List<TowerAndMobileBillVO> queryAllTowerAndMobileFee(Map<String, Object> map);
	/**
	 * 查询账单确认数据
	 * @param paraMap
	 * @return
	 */
	List<TowerAndMobileBillConfirmVO> queryTowerAndMobileConfirmBill(
			Map<String, Object> paraMap);
	/**
	 * 查询账单确认数据
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Page<List<TowerAndMobileBillConfirmVO>> queryTowerAndMobileConfirmBalance(
			Map<String, Object> paraMap, int pageNumber, int pageSize);
	
	/**
	 * 根据账单id集合查询对应的账单
	 * @author xup
	 * @param paraMap
	 * @return
	 */
	public Page<List<TowerBillbalanceVO>> queryTowerBillbalanceByIds(Map<String, Object> paraMap);
}
