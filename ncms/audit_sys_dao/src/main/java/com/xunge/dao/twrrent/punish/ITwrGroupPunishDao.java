package com.xunge.dao.twrrent.punish;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.punish.TwrGroupPunishVO;

/**
 * 集团既定考核指标扣罚
 * @author changwq
 *
 */
public interface ITwrGroupPunishDao {
	/**
	 * 页面数据全查
	 * @param paraMap
	 * @return
	 */
	public Page<List<TwrGroupPunishVO>> queryGroupPunishVO(
			Map<String,Object> paraMap,int pageNumber,int pageSize);
	/**
	 * 删除集团既定扣罚信息
	 * @param paraMap
	 * @return
	 */
	public int deleteGroupPunish(Map<String,Object> paraMap);
	/**
	 * 页面数据全查(不分页)
	 * @param paraMap
	 * @return
	 */
	public List<TwrGroupPunishVO> queryGroupPunish(Map<String,Object> paraMap);
	/**
	 * 批量新增集团既定扣罚信息
	 * @param paraMap
	 * @return
	 */
	public int insert(List<TwrGroupPunishVO> list);
	/**
	 * 根据省份id查找扣罚信息
	 * @param paraMap
	 * @return
	 */
	public List<TwrGroupPunishVO> queryGroupPunishByPregId(Map<String,Object> paraMap);
	/**
	 * 根据id添加罚金
	 * @param paraMap
	 * @return
	 */
	public int updatePunishAmountById(Map<String,Object> paraMap);
	/**
	 * 根据铁塔站址编码和年月查找集团扣罚信息
	 * @param paraMap
	 * @return
	 * @author changwq
	 */
	public TwrGroupPunishVO queryIfGroupPunish(Map<String,Object> paraMap);
	/**
	 * 更新数据库已有且并未计算的数据
	 * @param twrGP
	 * @return
	 */
	public int update(List<TwrGroupPunishVO> twrGP);
}