package com.xunge.service.twrrent.punish.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.StateComm;
import com.xunge.core.page.Page;
import com.xunge.dao.twrrent.punish.ITwrSummaryPunishDao;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.punish.TwrSummaryPunishVO;
import com.xunge.service.twrrent.punish.ITwrSummaryPunishService;

/**
 * 扣罚汇总
 * @author changwq
 * 
 */
public class TwrSummaryPunishServiceImpl implements ITwrSummaryPunishService {

	private ITwrSummaryPunishDao twrSummaryPunishDao;
	
	@Override
	public Page<List<TwrSummaryPunishVO>> querySumPunish(String regId,
			String punishYearMonth, int pageNumber, int pageSize,String prvId) {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("regId",regId);
		paraMap.put("prvId",prvId);
		paraMap.put("punishYearMonth",punishYearMonth);
		return getList(paraMap, pageNumber, pageSize);
	}

	private Page<List<TwrSummaryPunishVO>> getList(Map<String, Object> paraMap,
			int pageNumber,int pageSize){
		List<TwrSummaryPunishVO> sumList = new ArrayList<TwrSummaryPunishVO>();
		Page<List<?>> listPage = null;
		//如果能查到省份信息
		if(paraMap.get("regId") == null || paraMap.get("regId") == ""){
			paraMap.put("pregId",paraMap.get("prvId"));
			paraMap.put("state",StateComm.STATE_0);
			listPage = twrSummaryPunishDao.queryRegMsgById(paraMap, pageNumber, pageSize);
			for(int i=0;i<listPage.getResult().size();i++){
				SysRegionVO region = (SysRegionVO)listPage.getResult().get(i);
				TwrSummaryPunishVO twrSummaryPunishVO = new TwrSummaryPunishVO();
				twrSummaryPunishVO.setRegName(region.getRegName());
				twrSummaryPunishVO.setPunishYearMonth((String)paraMap.get("punishYearMonth"));
				paraMap.put("regId",region.getRegId());
				//集团扣罚罚金合计
				BigDecimal groupPunishAmount = twrSummaryPunishDao.queryGroupAmount(paraMap);
				groupPunishAmount = getAmount(groupPunishAmount);
				twrSummaryPunishVO.setGroupPunishAmount(groupPunishAmount);
				//地市扣罚罚金合计
				BigDecimal regionPunishAmount = twrSummaryPunishDao.queryRegAmount(paraMap);
				regionPunishAmount = getAmount(regionPunishAmount);
				twrSummaryPunishVO.setRegionPunishAmount(regionPunishAmount);
				//省级扣罚罚金合计
				BigDecimal provincePunishAmount = twrSummaryPunishDao.queryPrvAmount(paraMap);
				provincePunishAmount = getAmount(provincePunishAmount);
				twrSummaryPunishVO.setProvincePunishAmount(provincePunishAmount);
				//罚金合计
				BigDecimal sumPunishAmount = groupPunishAmount.add(regionPunishAmount).add(provincePunishAmount);
				twrSummaryPunishVO.setSumPunishAmount(sumPunishAmount);
				sumList.add(twrSummaryPunishVO);
			}
		}else{
			paraMap.put("state",StateComm.STATE_0);
			listPage = twrSummaryPunishDao.queryRegMsgById(paraMap, pageNumber, pageSize);
			
			TwrSummaryPunishVO twrSummaryPunishVO = new TwrSummaryPunishVO();
			String regName = twrSummaryPunishDao.queryNameById(paraMap);
			twrSummaryPunishVO.setRegName(regName);
			twrSummaryPunishVO.setPunishYearMonth((String)paraMap.get("punishYearMonth"));
			//集团扣罚罚金合计
			BigDecimal groupPunishAmount = twrSummaryPunishDao.queryGroupAmount(paraMap);
			groupPunishAmount = getAmount(groupPunishAmount);
			twrSummaryPunishVO.setGroupPunishAmount(groupPunishAmount);
			//地市扣罚罚金合计
			BigDecimal regionPunishAmount = twrSummaryPunishDao.queryRegAmount(paraMap);
			regionPunishAmount = getAmount(regionPunishAmount);
			twrSummaryPunishVO.setRegionPunishAmount(regionPunishAmount);
			//省级扣罚罚金合计
			BigDecimal provincePunishAmount = twrSummaryPunishDao.queryPrvAmount(paraMap);
			provincePunishAmount = getAmount(provincePunishAmount);
			twrSummaryPunishVO.setProvincePunishAmount(provincePunishAmount);
			//罚金合计
			BigDecimal sumPunishAmount = groupPunishAmount.add(regionPunishAmount).add(provincePunishAmount);
			twrSummaryPunishVO.setSumPunishAmount(sumPunishAmount);
			sumList.add(twrSummaryPunishVO);
		}
		Page<List<TwrSummaryPunishVO>> sumListPage = (Page<List<TwrSummaryPunishVO>>) listPage.cloneNoResult(listPage);
		sumListPage.setResultAny(sumList);
		return sumListPage;
	}

	/**
	 * 如果获取的BigDecimal为null，则返回0
	 * @param a
	 * @return
	 */
	private BigDecimal getAmount(BigDecimal a){
		if(a == null){
			a = new BigDecimal(0);
		}
		return a;
	}
	
	public ITwrSummaryPunishDao getTwrSummaryPunishDao() {
		return twrSummaryPunishDao;
	}

	public void setTwrSummaryPunishDao(ITwrSummaryPunishDao twrSummaryPunishDao) {
		this.twrSummaryPunishDao = twrSummaryPunishDao;
	}
	
}
