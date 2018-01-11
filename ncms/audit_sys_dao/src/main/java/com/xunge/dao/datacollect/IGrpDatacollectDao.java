package com.xunge.dao.datacollect;

import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.datacollect.GrpDatacollectVO;

public interface IGrpDatacollectDao {
	/**
	 * 根据条件查寻集团收集表
	 * @param paraMap
	 * @return
	 * @author changwq
	 */
	public Page<GrpDatacollectVO> queryGrpDataCollectVO(Map<String,Object> paraMap,int pageNumber,int pageSize);
	/**
	 * 根据id删除集团收集表
	 * @param datacollectId
	 * @return
	 * @author changwq
	 */
	public int deleteByPrimaryKey(String datacollectId);
	/**
	 * 新增集团收集表
	 * @param grpDatacollectVO
	 * @return
	 * @author changwq
	 */
	public int insertSelective(GrpDatacollectVO grpDatacollectVO);
	/**
	 * 修改集团收集表
	 * @param grpDatacollectVO
	 * @return
	 * @author changwq
	 */
	public int updateByPrimaryKeySelective(GrpDatacollectVO grpDatacollectVO);
	/**
	 * 根据id查询集团收集表信息
	 * @param paraMap
	 * @return
	 */
	public GrpDatacollectVO queryGrpDataCollectById(Map<String,Object> paraMap);
	/**
	 * 集团收集表派发到省份
	 * @param paraMap
	 * @return
	 */
	public int updateStateById(Map<String,Object> paraMap);
	/**
	 * 验证是否存在相同的标题
	 * @param datacollectTitle
	 * @return
	 */
	public String querySameThing(String datacollectTitle);
	/**
	 * 根据集团收集id查询标题
	 * @param paraMap
	 * @return
	 */
	public GrpDatacollectVO queryTitleById(Map<String,Object> paraMap);
	/**
	 * 根据集团收集id查询抄送用户
	 * @param paraMap
	 * @return
	 */
	public String queryCopyUserById(Map<String,Object> paraMap);
	/**
	 * 修改集团收集状态为已完结
	 * @param paraMap
	 * @return
	 */
	public int updateDatacollectToFinish(Map<String,Object> paraMap);
	/**
	 * 根据省公司上报信息编码查询集团工单派发时间
	 * @param map
	 * @return
	 */
	public String querySendDateByPrvId(Map<String,Object> map);
}
