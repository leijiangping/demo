package com.xunge.dao.basedata.collection;

import java.util.List;
import java.util.Map;

import com.xunge.model.basedata.colletion.TaskHistoryInfoVO;

public interface TaskHistoryInfoVOMapper {
	List<TaskHistoryInfoVO> queryAllHistoryLogVO(Map<String,Object> paramMap);
	int deleteHistoryLogVO(Map<String,Object> paramMap);
}
