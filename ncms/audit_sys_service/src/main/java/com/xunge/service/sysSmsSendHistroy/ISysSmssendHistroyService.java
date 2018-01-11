package com.xunge.service.sysSmsSendHistroy;

import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.smsSendHistroy.SysSmssendHistroyVO;


public interface ISysSmssendHistroyService {
	/**
	 * 新增短信下发回执信息
	 * @param sysSmssendHistroyVO
	 * @return
	 */
	public String insertSelective(SysSmssendHistroyVO sysSmssendHistroyVO);
	/**
	 * 根据集团收集编码查询短信下发状态
	 * @param grpDataCollectId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<SysSmssendHistroyVO> queryHistroyByCollId(String grpDatacollectId,
			int pageNumber,int pageSize);
	/**
	 * 集团发短信通知各省公司
	 * @param map
	 */
	public void grpSendMsg(Map<String,Object> map);
}
