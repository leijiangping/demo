package com.xunge.dao.selfrent.billamount;

import java.util.List;

import com.xunge.model.selfrent.billamount.RentBillamountDetailVO;



/**
 * @description 租费报账汇总明细DAO接口
 * @author zhujj
 * @version 2017-06-26
 */
public interface RentBillamountDetailDao {
	/**
	 * @description 保存租费报账汇总明细
	 * @param rentBillamountDetail 租费报账汇总明细
	 * @return
	 */
	public int insertRentBillamountDetail(RentBillamountDetailVO rentBillamountDetail);
	/**
	 * @description 批量保存租费报账汇总明细
	 * @param rentBillamountDetail
	 * @return
	 */
	public int insertRentBillamountDetailList(List<RentBillamountDetailVO> rentBillamountDetails);
	/**
	 * @description 根据租费汇总表ID查询租费汇总明细
	 * @param billamountId 租费汇总Id
	 * @return
	 */
	public List<RentBillamountDetailVO> selectByBillamountId(String billamountId);
	
}