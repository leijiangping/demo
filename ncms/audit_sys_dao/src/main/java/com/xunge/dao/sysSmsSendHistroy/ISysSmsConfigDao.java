package com.xunge.dao.sysSmsSendHistroy;

import java.util.Map;

import com.xunge.model.smsSendHistroy.SysSmsConfigVO;


public interface ISysSmsConfigDao {
	/**
	 * 根据短信模板编码查询模板信息
	 * @param paraMap
	 * @return
	 * @author changwq
	 */
	public SysSmsConfigVO querySmsModelMsg(Map<String,Object> paraMap);
}
