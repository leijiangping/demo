package com.xunge.service.datacollect;

import java.util.List;

import com.xunge.model.datacollect.GrpDatacollectHistoryVO;


public interface IGrpDatacollectHistoryService {
	/**
	 * 新增升级上报交互历史记录
	 * @param grpDatacollectHistoryVO
	 * @return
	 */
	public String insertSelective(GrpDatacollectHistoryVO grpDatacollectHistoryVO);
	/**
	 * 根据省份上报编码查询历史记录
	 * @param datacollectPrvId
	 * @return
	 */
	public List<GrpDatacollectHistoryVO> queryHistoryByPrvId(String datacollectPrvId);
}
