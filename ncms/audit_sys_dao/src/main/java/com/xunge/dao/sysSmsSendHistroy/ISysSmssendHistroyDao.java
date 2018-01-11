package com.xunge.dao.sysSmsSendHistroy;

import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.smsSendHistroy.SysSmssendHistroyVO;


public interface ISysSmssendHistroyDao {
	/**
	 * 新增短信下发历史记录
	 * @param grpSmssendHistroyVO
	 * @return
	 */
	public int insertSelective(SysSmssendHistroyVO grpSmssendHistroyVO);
	/**
	 * 根据集团收集编码查询短消息发送状态
	 * @param paraMap
	 * @return
	 */
	public Page<SysSmssendHistroyVO> queryHistroyByCollId(
			Map<String, Object> paraMap,int pageNumber,int pageSize);
}
