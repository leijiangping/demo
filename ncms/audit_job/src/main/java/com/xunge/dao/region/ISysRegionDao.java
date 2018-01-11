package com.xunge.dao.region;

import java.util.List;
import java.util.Map;


public interface ISysRegionDao {
	/**
	 * 根据省份code查询省份id
	 * @author xup
	 */
	public String getPrvIdByCode(String prvcode);
	public String getRegIdByCode(Map<String,String> paramMap);
	public List<Map<String, String>> getRegionsByPrvid(Map<String, String> paramMap);
	
}