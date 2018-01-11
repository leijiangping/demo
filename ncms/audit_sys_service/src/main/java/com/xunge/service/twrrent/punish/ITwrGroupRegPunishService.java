package com.xunge.service.twrrent.punish;

import com.xunge.model.towerrent.punish.TwrGroupRegPunishVO;


/**
 * 集团既定考核指标扣罚汇总
 * @author changwq
 *
 */
public interface ITwrGroupRegPunishService {
	/**
	 * 新增集团考核扣罚汇总信息
	 * @param twrGroupRegPunishVO
	 * @return
	 */
	public String insertSelective(TwrGroupRegPunishVO twrGroupRegPunishVO);
	/**
	 * 根据id修改集团既定考核汇总信息
	 * @param twrGroupRegPunishVO
	 * @return
	 */
	public String updateByPrimaryKeySelective(TwrGroupRegPunishVO twrGroupRegPunishVO);
	/**
	 * 根据年月和地市查询集团扣罚汇总信息
	 * @param paraMap
	 * @return
	 */
	public TwrGroupRegPunishVO queryGroupRegPunish(String regId,String punishYearMonth,Integer state);
}