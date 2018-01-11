package com.xunge.service.towerrent.rentinformation;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.rentinformation.TwrRentInformationChangeVO;

public interface ITwrRentInformationChangeService {
	/**
	 * 根据参数分页查询移动资源变更日志集合
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<List<Map<String, Object>>> queryTwrRentInformationChangePageByCondtion(Map<String,Object> paraMap,int pageNumber,int pageSize);
	
	/**
	 * 根据参数分页查询移动资源变更日志集合
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<List<TwrRentInformationChangeVO>> queryTwrRentInformationChangeVoPageByCondtion(Map<String,Object> paraMap,int pageNumber,int pageSize);
	
	/**
	 * 根据参数查询所有符合条件的移动资源变更日志集合
	 * @param paraMap
	 * @return
	 */
	List<TwrRentInformationChangeVO> queryTwrRentInformationChangeVoListByCondtion(Map<String,Object> paraMap);
}
