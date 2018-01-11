package com.xunge.dao.twrrent.resourceinfo.impl;

import java.util.List;
import java.util.Map;

import com.xunge.dao.AbstractBaseDao;
import com.xunge.dao.twrrent.resourceinfo.ITowerLinkDao;
import com.xunge.model.towerrent.rentmanager.TowerLinkVO;
/**
 * 站址编码关联关系表dao实现类
 * @author yuefy
 *
 */
public class TowerLinkDaoImpl  extends AbstractBaseDao  implements ITowerLinkDao {

	final String Namespace="com.xunge.dao.towerrent.rentmanager.TowerLinkVOMapper.";
	
	/**
	 * 查询关联关系信息
	 */
	@Override
	public List<String> queryAllTowerLink(Map<String, Object> paraMap) {
		return this.getSqlSession().selectList(Namespace+"queryAllTowerLink",paraMap);
	}
	
}
