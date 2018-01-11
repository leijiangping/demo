package com.xunge.dao.twrrent.punish;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.punish.TwrGroupPunishVO;
import com.xunge.model.towerrent.punish.TwrGroupRegPunishVO;

/**
 * 集团既定考核指标扣罚汇总
 * @author changwq
 *
 */
public interface ITwrGroupRegPunishDao {
	/**
	 * 新增集团考核扣罚汇总信息
	 * @param twrGroupRegPunishVO
	 * @return
	 */
	public int insertSelective(TwrGroupRegPunishVO twrGroupRegPunishVO);
	/**
	 * 根据id修改集团既定考核汇总信息
	 * @param twrGroupRegPunishVO
	 * @return
	 */
	public int updateByPrimaryKeySelective(TwrGroupRegPunishVO twrGroupRegPunishVO);
	/**
	 * 根据年月和地市查询集团扣罚汇总信息
	 * @param paraMap
	 * @return
	 */
	public TwrGroupRegPunishVO queryGroupRegPunish(Map<String,Object> paraMap);
}