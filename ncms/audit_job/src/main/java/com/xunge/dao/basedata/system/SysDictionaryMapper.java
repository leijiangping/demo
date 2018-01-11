package com.xunge.dao.basedata.system;

import java.util.List;
import java.util.Map;

import com.xunge.model.system.region.DictionaryVO;

public interface SysDictionaryMapper {

	public List<DictionaryVO> querySysDictionary(Map<String,Object> param);
}
