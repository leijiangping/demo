package com.xunge.service.selfrent.billamount;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.selfrent.billAccount.VPaymentVO;
import com.xunge.model.selfrent.billamount.RentBillamountVO;


/**
 * @description  报账汇总
 * @author zhujj
 * @date 2017年6月27日 下午1:09:29 
 * @version 1.0.0 
 */
public interface IRentBillamountService<T>{
	/**
	 * @description 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity);
	
	/**
	 * @description 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<List<RentBillamountVO>> queryRentBillamountPage(Map<String,Object> map);
	/**
	 * @description 查询租费报账汇总
	 * @param map billamountId 租费报账汇总ID
	 * @return
	 */
	public RentBillamountVO queryRentBillamountById(Map<String,Object> map);
	
	/**
	 * @description 插入租费报账汇总
	 * @param List<VPaymentVO> 待汇总数据集合
	 * @param Map<String,Object> 
	 * @param Map<String,Object> 存入当前登陆用户信息
	 * @return
	 */
	public Map<String,Object> insertRentBillamount(List<VPaymentVO> list,Map<String,Object> map);
}
