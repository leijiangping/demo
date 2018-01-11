package com.xunge.dao.twrrent.punish;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.system.region.SysRegionVO;


/**
 * 扣罚汇总表
 * @author changwq
 *
 */
public interface ITwrSummaryPunishDao {
	/**
	 * 根据pregId查询省份信息
	 * @param paraMap
	 * @return
	 */
	public String queryPrvCodeById(Map<String,Object> paraMap);
	/**
	 * 根据id查询地市信息
	 * @param paraMap
	 * @return
	 */
	public Page<List<?>> queryRegMsgById(Map<String, Object> paraMap,
			int pageNumber,int pageSize);
	/**
	 * 根据年月和地市id查询集团罚金合计
	 * @param paraMap
	 * @return
	 */
	public BigDecimal queryGroupAmount(Map<String,Object> paraMap);
	/**
	 * 根据年月和地市id查询地市罚金合计
	 * @param paraMap
	 * @return
	 */
	public BigDecimal queryRegAmount(Map<String,Object> paraMap);
	/**
	 * 根据年月和地市id查询省级罚金合计
	 * @param paraMap
	 * @return
	 */
	public BigDecimal queryPrvAmount(Map<String,Object> paraMap);
	/**
	 * 根据regId查询name
	 * @param paraMap
	 * @return
	 */
	public String queryNameById(Map<String,Object> paraMap);
}