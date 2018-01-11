package com.xunge.service.towerrent.stopserver;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.xunge.core.page.Page;
import com.xunge.model.towerrent.bizchange.TowerRentinformationBizchangeVO;
import com.xunge.model.towerrent.stopserver.TowerStopServerVO;

/**
 * 终止服务service 接口
 * @author yuefy
 * @date 2017.07.20
 *
 */
public interface ITowerStopServerService {
	/**
	 * 分页查询铁塔终止服务表
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author yuefy
	 */
	public Page<TowerStopServerVO> queryTowerStopServer(
			Map<String, Object> paraMap, int pageNumber, int pageSize);
	/**
	 * 审核完成修改审核状态
	 * @param twrRentinformationBizchangeId
	 * @param checkState
	 * @return
	 */
	public String updateTowerStopServerCheckState(String twrRentinformationBizchangeId,int checkState);
	
	
	/**
	 * 查询所有铁塔终止服务信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author yuefy
	 */
	public List<TowerStopServerVO> queryTowerStopServer(
			Map<String, Object> paraMap);
	
	/**
	 * 导入铁塔终止服务
	 * @param file
	 * @param request
	 * @param paraMap
	 * @return
	 * @author yuefy
	 */
	public Map<String,Object> importTowerStopServer(MultipartFile file,String suffix,
			HttpServletRequest request, Map<String, Object> paraMap)  throws  Exception ;
	/**
	 * 根据id查询铁塔终止服务表信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author changwq
	 */
	public TowerStopServerVO queryTowerStopServerById(String stopServerId);
	/**
	 * 审核通过修改申请信息审核状态
	 * @param paraMap
	 * @return
	 */
	public String updateCheckStateById(String stopServerId,Integer checkState);
}
