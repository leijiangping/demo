package com.xunge.dao.datacollect;

import java.util.List;
import java.util.Map;

import com.xunge.model.datacollect.GrpDatacollectHistoryVO;


public interface IGrpDatacollectHistoryDao {
	/**
	 * 新增历史记录信息
	 * @param grpDatacollectHistoryVO
	 * @return
	 */
	public int insertSelective(GrpDatacollectHistoryVO grpDatacollectHistoryVO);
	/**
	 * 根据省份上报编码查询历史记录信息
	 * @param map
	 * @return
	 */
	public List<GrpDatacollectHistoryVO> queryHistoryByPrvId(Map<String,Object> map);
}
