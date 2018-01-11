package com.xunge.dao.system.region;

import java.util.List;
import java.util.Map;

import com.xunge.model.system.province.SysProvinceGroupVO;
import com.xunge.model.system.province.SysProvinceVO;
import com.xunge.model.system.region.RegionVO;
import com.xunge.model.system.region.SysRegionVO;

public interface ISysRegionDao {
	/**
	 * 条件查询
	 * @author xup
	 */
	public List<SysRegionVO> selectByConditions(Map<String, Object> map );
	/**
	 * 查询省份分组
	 * @authorzhujj
	 * @return
	 */
	public List<SysProvinceGroupVO> selectByPrvGroup(Map<String, Object> map);
	
	/**
	 * 查询省份列表
	 * @param map
	 * @return
	 */
	public List<SysProvinceVO> selectProvinceByIds(Map<String, Object> map);
	
	/**
	 * 删除
	 * @author xup
	 */
	public int delRegion(Map<String, Object> paramMap);
	/**
	 * 新增
	 * @author xup
	 */
	public int addRegion(SysRegionVO sysRegionVO);
	/**
	 * 修改
	 * @author xup
	 */
	public int updateRegion(SysRegionVO sysRegionVO,String regId);
	/**
	 * 根据id查询
	 * @author xup
	 */
	public SysRegionVO getRegionById(String regId);
	/**
	 * 根据name查询
	 * @author SongJL
	 */
	public SysRegionVO getRegionIdByName(String regName);
	/**
	 * 根据省份id查询
	 * @author xup
	 */
	public List<String> getRegIdByprvId(String prvId) ;
	/**
	 * 根据条件进行查询省份节点下的所有市和区
	 * @param paramMap
	 * @return
	 */
	List<SysRegionVO> queryRegionByConditions(Map<String, Object> paramMap);
	/**
	 * 根据区县id查询省分名称
	 * @author xup
	 */
	public String getPrvNameById(String regId);
	/**
	 * 根据省份code查询省份id
	 * @author xup
	 */
	public String getPrvIdByCode(String prvcode);
	public String getRegIdByCode(String code);
	
	/**
	 * 获取所有区域信息
	 */
	public List<SysRegionVO> getAddress(Map<String, Object> map);

	/**
	 * 获取用户区域信息
	 */
	public List<SysRegionVO> getUserAddress(Map<String, Object> map);
	/**
	 * 获取用户所有管理权限省
	 */
	public List<RegionVO> queryManaProvs(Map<String, Object> map);
	/**
	 * 获取用户所有管理权限区
	 */
	public List<RegionVO> queryManaRegions(Map<String, Object> map);
	/**
	 * 根据省份id查询所有市区id
	 * @param map
	 * @return
	 */
	public List<String> queryPRegionIdsByPrvId(Map<String, Object> map);
	/**
	 * 根据直辖市省份id查询所有区级id
	 * @param map
	 * @return
	 */
	public List<String> queryRegionIdsByPrvId(Map<String, Object> map);
	/**
	 * 根据集团省份id查询所有省级id
	 * @param map
	 * @return
	 */
	public List<String> queryPrvRegionIdsByPrvId(Map<String, Object> map);
	
}