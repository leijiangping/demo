package com.xunge.service.twrrent.punish;

import java.util.List;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.punish.TwrSummaryPunishVO;



/**
 * 扣罚汇总
 * @author changwq
 *
 */
public interface ITwrSummaryPunishService {
	/**
	 * 根据地市和年月查询所有扣罚信息汇总
	 * @param regId
	 * @param punishYearMonth
	 * @return
	 */
	public Page<List<TwrSummaryPunishVO>> querySumPunish(String regId,String punishYearMonth,
			int pageNumber,int pageSize,String prvId);
}