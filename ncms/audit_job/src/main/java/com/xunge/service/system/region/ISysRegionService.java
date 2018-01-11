package com.xunge.service.system.region;

import java.util.List;
import java.util.Map;


/**
 * 区县信息维护service接口
 *
 */
public interface ISysRegionService {
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
	public List<Map<String,String>> getRegionsByPrvid(Map<String,String> paramMap);
	/**
	 * 根据地区code查询地区id
	 * @param regcode
	 * @return
	 */
	public String getRegIdByCode(Map<String,String> paramMap);
}
