package com.xunge.dao.selfelec;

import com.xunge.model.util.SysSequence;

/**
 * @descript 编码记录表
 * @author wangz
 * @date 2017-08-30 17:12:23
 */
public interface SysSequenceMapper {

	 SysSequence selectByPrimaryKey(SysSequence key);

	 int updateByPrimaryKey(SysSequence record);
	 
	 int insertSequence(SysSequence record);
}
