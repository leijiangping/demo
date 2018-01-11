package com.xunge.service.datacollect;

import java.util.List;
import java.util.Map;



public interface IGrpDatacollecttypeService {
	/**
	 * 集团其他文件上报保存
	 * @param paraMap
	 * @return
	 */
	public String updateFiles(Map<String,Object> paraMap);
	/**
	 * 根据集团收集数据类型主键id删除集团收集数据信息
	 * @param datacollectTypeId
	 * @return
	 */
	public String deleteByPrimaryKey(String datacollecttypeId);
	/**
	 * 查询所有尚未关联的数据类型
	 * @return
	 */
	public List<String> queryTypeBeUsed();
	/**
	 * 查询已经关联的数据模型
	 * @param datacollectId
	 * @return
	 */
	public List<String> queryTypeBeUsedById(String datacollectId);
	/**
	 * 删除已解除关联且并未再次关联的集团收集数据类型
	 * @return
	 */
	public String deleteUserLessMsg();
	/**
	 * 根据typeId查询集团模板数据类型
	 * @param datacollecttypeId
	 * @return
	 */
	public String queryTempTypeByTypeId(String datacollecttypeId);
}
