package com.xunge.service.twrrent.settlement;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ResponseBody;

import com.xunge.core.page.Page;
import com.xunge.model.FeedBackObject;
import com.xunge.model.towerrent.settlement.BillConfirmVO;
import com.xunge.model.towerrent.settlement.TowerAndMobileBillConfirmVO;
import com.xunge.model.towerrent.settlement.TowerAndMobileBillVO;
import com.xunge.model.towerrent.settlement.TowerBillbalanceVO;

public interface ITowerMobileBillCheckService {
	/**
	 * 查询铁塔侧和移动侧账单信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Page<List<TowerAndMobileBillVO>> queryTowerAndMobileFee(Map<String, Object> paraMap, int pageNumber,
			int pageSize);
	/**
	 * 账单修改
	 * @param BillConfirmList
	 * @return
	 */
	public int updateTowerMobileBillState(List<TowerBillbalanceVO> BillConfirmList);
	
	/**
	 * 账单确认
	 * @param BillConfirmList
	 * @return
	 */
	public int updateTowerMobileBillConfirmState(Map<String, Object> paraMap);
	/**
	 * 取消确认
	 * @param paraMap
	 * @return
	 */
	int  updateCancleConfirmState(Map<String, Object> paraMap);
	/**
	 * 查询未分页账单对账数据
	 * @param map
	 * @return
	 */
	List<TowerAndMobileBillVO> queryAllTowerAndMobileFee(Map<String, Object> map);
	
	/**
	 * 账单确认导出
	 * @param map
	 * @return
	 */
	List<TowerAndMobileBillConfirmVO> queryTowerAndMobileConfirmBill(
			Map<String, Object> paraMap);
	/**
	 * 
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Page<List<TowerAndMobileBillConfirmVO>> queryTowerAndMobileConfirmBalance(Map<String, Object> paraMap,
			int pageNumber, int pageSize);
	/**
	 * 根据账单id集合查询对应的账单
	 * @author xup
	 * @param paraMap
	 * @return
	 */
	public Page<List<TowerBillbalanceVO>> queryTowerBillbalanceByIds(Map<String, Object> paraMap);
}
