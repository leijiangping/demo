package com.xunge.dao.twrrent.resourceinfo;

import java.util.List;
import java.util.Map;

import com.xunge.model.towerrent.rentmanager.TowerLinkVO;
/**
 * 起租管理 站址编码关联关系表dao接口
 * @author yuefy
 *
 */
public interface ITowerLinkDao {
	/**
	 * 查询关联关系信息
	 */
	public List<String> queryAllTowerLink(Map<String, Object> paraMap);
	
}
