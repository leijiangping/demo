package com.xunge.dao.util;

import java.util.Map;

public interface CodeGeneratorUtilDao {
	/**
	 * 查询最小不连续code
	 * @author SongJL
	 * @param param
	 * @return
	 */
	public String selectMinCodeFromTable(Map<?, ?> param);
	/**
	 * 查询自增最大code
	 * @author SongJL
	 * @param param
	 * @return
	 */
	public String selectMaxCodeFromTable(Map<?, ?> param);
	/**
	 * 获取最大可用编码完整信息
	 * @author zhujj
	 * @param param
	 * @return
	 */
	public String selectMaxCodeAllInfoFromTable(Map<?, ?> param);
	
}
