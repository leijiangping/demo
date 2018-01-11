package com.xunge.service.datacollect;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.datacollect.GrpDatacollectVO;
import com.xunge.model.datacollect.GrpDatacollecttypePrvVO;
import com.xunge.model.datacollect.GrpDatacollecttypeVO;

public interface IGrpDatacollectService {
	/**
	 * 根据条件查寻集团收集表
	 * @param paraMap
	 * @return
	 * @author changwq
	 */
	public Page<GrpDatacollectVO> queryGrpDataCollectVO(Map<String,Object> paraMap,
			int pageNumber,int pageSize);
	/**
	 * 根据id删除集团收集表
	 * @param datacollectId
	 * @return
	 * @author changwq
	 */
	public String deleteByPrimaryKey(String datacollectId);
	/**
	 * 新增集团收集表
	 * @param grpDatacollectVO
	 * @return
	 * @author changwq
	 */
	public String insertSelective(GrpDatacollectVO grpDatacollectVO,List<String> prvIds,
			List<String> typeIds);
	/**
	 * 修改集团收集表
	 * @param grpDatacollectVO
	 * @return
	 * @author changwq
	 */
	public String updateByPrimaryKeySelective(GrpDatacollectVO grpDatacollectVO,
			List<String> prvIds,List<String> typeIds);
	/**
	 * 根据id查询集团收集表信息
	 * @param paraMap
	 * @return
	 */
	public Map<String,Object> queryGrpDataCollectById(String datacollectId);
	/**
	 * 根据id查询省级上报详情
	 * @param paraMap
	 * @return
	 */
	public Map<String,Object> queryGrpPrvCollectById(String datacollectId);
	/**
	 * 集团收集表派发到省份
	 * @param paraMap
	 * @return
	 * @throws ParseException 
	 */
	public String updateStateById(String datacollectUser,String datacollectId,String userId,
			String title) throws ParseException;
	/**
	 * 根据省份id查询省份上报信息
	 * @param prvId
	 * @return
	 */
	public List<GrpDatacollecttypePrvVO> queryEveryPrvMsg(String datacollectId,String prvId);
	/**
	 * 新增集团收集数据类型
	 * @return
	 */
	public String insertSelective(GrpDatacollecttypeVO grpDatacollecttypeVO);
	/**
	 * 修改集团收集数据类型
	 * @return
	 */
	public String updateByPrimaryKeySelective(GrpDatacollecttypeVO grpDatacollecttypeVO);
	/**
	 * 验证标题是否重复
	 * @param datacollectTitle
	 * @return
	 */
	public String querySameThing(String datacollectTitle);
	/**
	 * 根据集团收集id查询集团收集标题
	 * @param datacollectId
	 * @return
	 */
	public GrpDatacollectVO queryTitleById(String datacollectId);
	/**
	 * 根据集团收集id查询抄送用户
	 * @param datacollectId
	 * @return
	 */
	public String queryCopyUserById(String datacollectId);
	/**
	 * 修改集团手机状态为已完结
	 * @param datacollectId
	 * @return
	 */
	public String updateDatacollectToFinish(String datacollectId);
	/**
	 * 根据省公司上报编码查询集团工单派发时间
	 * @param datacollectPrvId
	 * @return
	 */
	public String querySendDateByPrvId(String datacollectPrvId);
}
