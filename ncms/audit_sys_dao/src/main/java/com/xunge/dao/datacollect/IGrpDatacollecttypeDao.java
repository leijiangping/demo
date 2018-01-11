package com.xunge.dao.datacollect;

import java.util.List;
import java.util.Map;

import com.xunge.model.datacollect.GrpDatacollecttypeVO;

public interface IGrpDatacollecttypeDao {
	/**
	 * 根据集团收集表id删除集团收集数据类型
	 * @param datacollectId
	 * @return
	 * @author changwq
	 */
	public int deleteByDCId(String datacollectId);
	/**
	 * 新增集团收集数据类型信息
	 * @param grpDatacollecttypeVO
	 * @return
	 * @author changwq
	 */
	public int insertSelective(GrpDatacollecttypeVO grpDatacollecttypeVO);
	/**
	 * 关联集团收集表和集团收集数据信息
	 * @param paraMap
	 * @return
	 * @author changwq
	 */
	public int updateTypeById(Map<String,Object> paraMap);
	/**
	 * 根据集团收集id删除集团收集数据类型关联
	 * @param datacollectId
	 * @return
	 * @author changwq
	 */
	public int deleteIdById(String datacollectId);
	/**
	 * 根据集团收集表id查询数据类型集合
	 * @param datacollectId
	 * @return
	 */
	public List<GrpDatacollecttypeVO> queryByGrpCollId(String datacollectId);
	/**
	 * 修改集团收集数据类型
	 * @param grpDatacollecttypeVO
	 * @return
	 */
	public int updateByPrimaryKeySelective(GrpDatacollecttypeVO grpDatacollecttypeVO);
	/**
	 * 删除已解除关联且并未再次关联的集团收集数据类型
	 * @return
	 */
	public int deleteUserLessMsg();
	/**
	 * 集团其他文件上传保存
	 * @param paraMap
	 * @return
	 */
	public int updateFiles(Map<String,Object> paraMap);
	/**
	 * 根据集团收集数据id删除集团收集数据信息
	 * @param datacollecttypeId
	 * @return
	 */
	public int deleteByPrimaryKey(String datacollecttypeId);
	/**
	 * 查询所有尚未关联的数据类型
	 * @return
	 */
	public List<String> queryTypeBeUsed();
	/**
	 * 查询已经关联的数据类型
	 * @param datacollectId
	 * @return
	 */
	public List<String> queryTypeBeUsedById(String datacollectId);
	/**
	 * 根据typeId查询模板数据类型
	 * @param map
	 * @return
	 */
	public String queryTempTypeByTypeId(Map<String,Object> map);
}
