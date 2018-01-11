package com.xunge.service.towerrent.rentinformationbizchange;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizchange.TowerRentinformationBizchangeVO;

public interface ITwrRentInformationBizChangeService {
	/**
	 * 分页查询铁塔信息变更表
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author changwq
	 */
	public Page<TowerRentinformationBizchangeVO> queryInformationBizChangeCheckInfo(
			Map<String, Object> paraMap, int pageNumber, int pageSize);
	/**
	 * 审核完成修改审核状态
	 * @param twrRentinformationBizchangeId
	 * @param checkState
	 * @return
	 */
	public String updateBizChangeCheckState(String twrRentinformationBizchangeId,int checkState);
	
	
	/**
	 * 查询所有铁塔信息变更表信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author yuefy
	 */
	public List<TowerRentinformationBizchangeVO> queryTowerRentinformationBizchangeInfo(
			Map<String, Object> paraMap);
	
	/**
	 * 导入铁塔变更信息
	 * @param file
	 * @param request
	 * @param paraMap
	 * @return
	 * @author yuefy
	 */
	public Map<String,Object> importTowerChangeInfo(MultipartFile file,String suffix,
			HttpServletRequest request, Map<String, Object> paraMap)  throws  Exception ;
	/**
	 * 根据id查询铁塔信息变更表信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author changwq
	 */
	public TowerRentinformationBizchangeVO queryBizChangeById(
			Map<String, Object> paraMap);
}
