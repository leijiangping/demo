package com.xunge.service.system.region;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xunge.core.model.UserLoginInfo;
import com.xunge.model.system.province.SysProvinceGroupVO;
import com.xunge.model.system.province.SysProvinceVO;
import com.xunge.model.system.region.RegionVO;
import com.xunge.model.system.region.SysRegionVO;

/**
 * 区县信息维护service接口
 *
 */
public interface ISysRegionService {
	/**
	 * 条件查询
	 * @author xup
	 */
	public List<SysRegionVO> selectByConditions(String prvid,String regCode,String regName);
	/**
	 * 查询省份分组
	 * @author zhujj
	 * @return
	 */
	public List<SysProvinceGroupVO> selectByPrvGroupRedis();
	public List<SysProvinceGroupVO> selectByPrvGroup();
	
	/**
	 * 查询省份列表
	 * @author zhujj
	 * @return
	 */
	public List<SysProvinceVO> selectProvinceByIds(UserLoginInfo userLoginInfo);
	
	/**
	 * 根据条件进行查询
	 * @param paramMap
	 * @return
	 */
	public List<Object> queryRegionByConditions(String prvId);

	/**
	 * 删除区县信息
	 * @param sysRegionVO
	 * @return
	 */
	public String delRegion(List<String> sysRegionVO);

	/**
	 * 增加区县信息
	 * @param request
	 * @return
	 */
	public String insertRegion(HttpServletRequest request);

	/**
	 * 修改区县信息
	 * @param request
	 * @return
	 */
	public String updateRegion(HttpServletRequest request);
	
	/**
	 * 根据区县Id查询区县信息
	 * @param regId
	 * @return
	 */
	public SysRegionVO getRegionById(String regId);
	/**
	 * 根据区县Name查询区县Id
	 * @param regId
	 * @return
	 */
	public SysRegionVO getRegionIdByName(String regName);

	/**
	 * 根据区县id查询省份信息
	 * @param regId
	 * @return
	 */
	public String getPrvNameById(String regId);
	/**
	 * 根据省份code查询省份id
	 * @param prvcode
	 * @return
	 */
	public String getPrvIdByCode(String prvcode);
	/**
	 * 根据市区code查询市区id
	 * @param citycode
	 * @return
	 */
	public String getCityIdByCode(String citycode);
	/**
	 * 根据地区code查询地区id
	 * @param regcode
	 * @return
	 */
	public String getRegIdByCode(String regcode);
	
	/**
	 * 获取用户区域信息
	 */
	public List<SysRegionVO> getUserAddress(Map<String, Object> map);
	
	/**
	 * 获取所有区域信息
	 */
	public List<SysRegionVO> getAddress(Map<String, Object> map);

	/**
	 * 获取用户所有管理权限区
	 * @author SongJL
	 * @return
	 */
	public List<RegionVO> queryManaRegions(Map<String, Object> map);
	
	/**
	 * 根据省份id查询所有市区id（直辖市查询所有区级id）
	 * @param map
	 * @return
	 */
	public List<String> queryRegionIdsByPrvId(Map<String, Object> map);
}
