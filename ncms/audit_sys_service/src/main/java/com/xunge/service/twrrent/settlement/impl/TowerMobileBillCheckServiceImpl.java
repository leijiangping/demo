package com.xunge.service.twrrent.settlement.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xunge.comm.StateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.core.page.Page;
import com.xunge.dao.twrrent.settlement.ITowerMobileBillCheckDao;
import com.xunge.model.towerrent.settlement.TowerAndMobileBillConfirmVO;
import com.xunge.model.towerrent.settlement.TowerAndMobileBillVO;
import com.xunge.model.towerrent.settlement.TowerBillbalanceVO;
import com.xunge.service.twrrent.settlement.ITowerMobileBillCheckService;

public class TowerMobileBillCheckServiceImpl implements
		ITowerMobileBillCheckService {
	
	private ITowerMobileBillCheckDao towerMobileBillCheckDao;
	@Override
	public Page<List<TowerAndMobileBillVO>> queryTowerAndMobileFee(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		
				Page<List<TowerAndMobileBillVO>> pageList = towerMobileBillCheckDao.queryTowerAndMobileFee(paraMap,pageNumber,pageSize);
				
				if(null!=pageList){
					List<?> lstvo1 = pageList.getResult();//获取page中的查询数据
					@SuppressWarnings("unchecked")
					List<TowerAndMobileBillVO> result = (List<TowerAndMobileBillVO>) lstvo1;
					if(null!=result&&result.size()>0){
							for (TowerAndMobileBillVO towerAndMobileBillVO : result) {
								if(null==towerAndMobileBillVO.getResCompare()){
									BigDecimal towertotalAmount = towerAndMobileBillVO.getTotalAmountMonthOut();
									BigDecimal mobiletotalAmount = towerAndMobileBillVO.getTotalAmountMonthOut1();
									if(towertotalAmount.compareTo(mobiletotalAmount)==StateComm.STATE_0){
										towerAndMobileBillVO.setResCompare(StateComm.STATE_0);//费用一致
										towerMobileBillCheckDao.updateCompareResult(towerAndMobileBillVO);
									}
									if(towertotalAmount.compareTo(mobiletotalAmount)==StateComm.STATE_1){//铁塔侧账单高
										towerAndMobileBillVO.setResCompare(StateComm.STATE_1);
										towerMobileBillCheckDao.updateCompareResult(towerAndMobileBillVO);
									}
									if(towertotalAmount.compareTo(mobiletotalAmount)==StateComm.STATE__1){//铁塔侧账单低
										towerAndMobileBillVO.setResCompare(SelfelecComm.NUMBER_2);
										towerMobileBillCheckDao.updateCompareResult(towerAndMobileBillVO);
									}
									
								}
								
							}
					}
				}
				return pageList;
	}
	public ITowerMobileBillCheckDao getTowerMobileBillCheckDao() {
		return towerMobileBillCheckDao;
	}
	public void setTowerMobileBillCheckDao(
			ITowerMobileBillCheckDao towerMobileBillCheckDao) {
		this.towerMobileBillCheckDao = towerMobileBillCheckDao;
	}
	@Override
	public int updateTowerMobileBillState(List<TowerBillbalanceVO> billConfirmList) {
		if(null!=billConfirmList&&billConfirmList.size()>0){
			for (TowerBillbalanceVO billConfirmVO : billConfirmList) {
				billConfirmVO.setConfirmState(StateComm.STATE_1);
			}
		}
		return towerMobileBillCheckDao.updateTowerMobileBillState(billConfirmList);
	}
	@Override
	public int updateCancleConfirmState(Map<String, Object> paraMap) {
		return towerMobileBillCheckDao.updateCancleConfirmState(paraMap);
	}
	@Override
	public List<TowerAndMobileBillVO> queryAllTowerAndMobileFee(
			Map<String, Object> map) {
		List<TowerAndMobileBillVO> towerAndMobileFee = towerMobileBillCheckDao.queryAllTowerAndMobileFee(map);
		return towerAndMobileFee;
	}
	@Override
	public List<TowerAndMobileBillConfirmVO> queryTowerAndMobileConfirmBill(
			Map<String, Object> paraMap) {
		
		return towerMobileBillCheckDao.queryTowerAndMobileConfirmBill(paraMap);
	}
	@Override
	public Page<List<TowerAndMobileBillConfirmVO>> queryTowerAndMobileConfirmBalance(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		return towerMobileBillCheckDao.queryTowerAndMobileConfirmBalance(paraMap,pageNumber,pageSize);
	}
	@Override
	public Page<List<TowerBillbalanceVO>> queryTowerBillbalanceByIds(
			Map<String, Object> paraMap) {
		String s = (String) paraMap.get("towerbillbalanceIds");
		String []towerbillbalanceId=s.split(",");
		List<String> towerbillbalanceIds=new ArrayList<>();
		for(int i=0;i<towerbillbalanceId.length;i++){
			towerbillbalanceIds.add(towerbillbalanceId[i]);
		}
		paraMap.put("towerbillbalanceIds", towerbillbalanceIds);
		return towerMobileBillCheckDao.queryTowerBillbalanceByIds(paraMap);
	}
	@Override
	public int updateTowerMobileBillConfirmState(Map<String, Object> paraMap) {
		String s = (String) paraMap.get("towerbillbalanceIds");
		String []towerbillbalanceId=s.split(",");
		List<String> towerbillbalanceIds=new ArrayList<>();
		for(int i=0;i<towerbillbalanceId.length;i++){
			towerbillbalanceIds.add(towerbillbalanceId[i]);
		}
		paraMap.put("towerbillbalanceIds", towerbillbalanceIds);
		return towerMobileBillCheckDao.updateTowerMobileBillConfirmState(paraMap);
	}
}
