package com.xunge.dao.selfrent.billamount.impl;

import java.util.List;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfrent.billamount.RentBillamountDetailDao;
import com.xunge.model.selfrent.billamount.RentBillamountDetailVO;

/**
 * @author zhujj
 * @date 2017年6月27日 上午10:14:34 
 * @version 1.0.0 
 */
public class RentBillAmountDetailDaoImpl extends AbstractBaseDao implements RentBillamountDetailDao{

	final String Namespace="com.xunge.dao.RentBillamountDetailVOMapper.";
	public int insertRentBillamountDetail(RentBillamountDetailVO rentBillamountDetail) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(Namespace+"insertRentBillamountDetail",rentBillamountDetail);
	}


	public int insertRentBillamountDetailList(List<RentBillamountDetailVO> rentBillamountDetails) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(Namespace+"insertRentBillamountDetailList",rentBillamountDetails);
	}

	public List<RentBillamountDetailVO> selectByBillamountId(String billamountId){

		return this.getSqlSession().selectList(Namespace+"selectByBillamountId",billamountId);
	}
}
