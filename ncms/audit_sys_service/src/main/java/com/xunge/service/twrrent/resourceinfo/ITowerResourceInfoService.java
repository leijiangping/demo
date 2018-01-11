package com.xunge.service.twrrent.resourceinfo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.xunge.core.page.Page;
import com.xunge.model.FeedBackObject;
import com.xunge.model.towerrent.rentmanager.TowerResourceInfoVO;
/**
 * 起租管理 铁塔资源信息service接口
 * @author yuefy
 *
 */
public interface ITowerResourceInfoService {
	/**
	 * 查询铁塔资源信息
	 * yuefy
	 */
	public Page<TowerResourceInfoVO> queryTowerResourceInfo(Map<String, Object> paraMap,
			int pageNumber, int pageSize);
	/**
	 * 根据id查询铁塔资源信息
	 */
	public TowerResourceInfoVO queryTowerResourceInfoVOById(String rentinformationtowerId);
	/**
	 * 审核完成后修改状态
	 */
	public String updateCommit(String rentinformationtowerId,Integer checkState);
	/**
	 * 查询所有铁塔信息
	 * yuefy
	 * @return
	 */
	public List<TowerResourceInfoVO> queryTowerResourceInfo( Map<String, Object> paraMap);

	/**
	 * 导入铁塔资源信息
	 * yuefy
	 * @return
	 */
	public Map<String, Object> importTowerResourceInfo(MultipartFile file,String suffix,HttpServletRequest request,Map<String, Object> paraMap) throws Exception;
	
}
