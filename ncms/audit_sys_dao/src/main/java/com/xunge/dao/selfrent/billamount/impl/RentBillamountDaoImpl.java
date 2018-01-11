package com.xunge.dao.selfrent.billamount.impl;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.selfrent.billamount.RentBillamountDao;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.selfrent.billamount.RentBillamountVO;

/**
 * @author zhujj
 * @date 2017年6月27日 上午10:14:11 
 * @version 1.0.0 
 */
public class RentBillamountDaoImpl  extends AbstractBaseDao implements RentBillamountDao<RentBillamountVO>{

	final String Namespace="com.xunge.dao.RentBillamountVOMapper.";
	public Page<List<RentBillamountVO>> queryRentBillamountPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		PageInterceptor.startPage(Integer.parseInt(map.get("pageNumber").toString()),Integer.parseInt(map.get("pageSize").toString()));
		this.getSqlSession().selectList(Namespace+"queryRentBillamountPage",map);
		return PageInterceptor.endPage();  
	}


	/**
	 * 插入租费报账汇总
	 * @param rentBillamountVO
	 * @return
	 */
	public int insertRentBillamountVO(RentBillamountVO rentBillamountVO){
		
		return this.getSqlSession().insert(Namespace+"insert",rentBillamountVO);
	}
	

	public RentBillamountVO queryRentBillamountById(Map<String, Object> map){

		return this.getSqlSession().selectOne(Namespace+"queryRentBillamountById",map);
	}

}
