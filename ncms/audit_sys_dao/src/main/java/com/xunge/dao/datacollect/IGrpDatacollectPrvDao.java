package com.xunge.dao.datacollect;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.activity.Act;
import com.xunge.model.datacollect.GrpDatacollectPrvVO;

public interface IGrpDatacollectPrvDao {
	/**
	 * 根据集团收集编号删除省级上报信息
	 * @param datacollectId
	 * @return
	 */
	public int deleteDataPrvById(String datacollectId);
	/**
	 * 修改省级上报表上报状态
	 * @param paraMap
	 * @return
	 */
	public int updateDataPrvById(Map<String,Object> paraMap);
	/**
	 * 新增省级上报表
	 * @param grpDatacollectPrvVO
	 * @return
	 */
	public int insertSelective(GrpDatacollectPrvVO grpDatacollectPrvVO);
	/**
	 * 关联集团收集表查询省级上报信息
	 * @param paraMap
	 * @return
	 */
	public Page<GrpDatacollectPrvVO> queryDataCollectPrvVO(Map<String,Object> paraMap,
			int pageNumber,int pageSize);
	/**
	 * 根据集团收集id查询下派省份id
	 * @param datacollectId
	 * @return
	 */
	public List<GrpDatacollectPrvVO> queryDownPrvIdByCollId(String datacollectId);
	/**
	 * 根据集团收集id查询省级上报情况
	 * @param paraMap
	 * @return
	 */
	public List<String> queryPrvIdByUpCollId(Map<String,Object> paraMap);
	/**
	 * 根据省份上报信息id查询
	 * @param paraMap
	 * @return
	 */
	public GrpDatacollectPrvVO queryPrvCollByPrvId(Map<String,Object> paraMap);
	/**
	 * 省级信息上报，添加上报备注，其他上报文件路径，文件名称
	 * @param paraMap
	 * @return
	 */
	public int updatePrvFileAndNote(Map<String,Object> paraMap);
	/**
	 * 根据省份编码和集团收集编码查询省级备注，其他文件路径，名称
	 * @param paraMap
	 * @return
	 */
	public GrpDatacollectPrvVO queryPrvSelfPathAndName(Map<String,Object> paraMap);
	/**
	 * 修改省级其他上报文件信息
	 * @param grpDatacollectPrvVO
	 * @return
	 */
	public int updatePrvOtherFile(Map<String,Object> paraMap);
	/**
	 * 集团驳回省份上报文件，修改上报状态并保存集团处理意见
	 * @param paraMap
	 * @return
	 */
	public int updateStateReject(Map<String,Object> paraMap);
	/**
	 * 集团完结工单-修改相应各省工单状态为已完结
	 * @param map
	 * @return
	 */
	public int updatePrvStateToFinish(Map<String,Object> map);
	/**
	 * 根据集团收集编码查找省份上报编码集合
	 * @param map
	 * @return
	 */
	public List<String> queryPrvIdBycollId(Map<String,Object> map);
	/**
	 * 选中工单负责人为登陆用户
	 * @param map
	 * @return
	 */
	public int updateGrpToUserSelf(Map<String,Object> map);
	/**
	 * 查询省份上报信息编码和省份其他上报文件路径
	 * @param paraMap
	 * @return
	 */
	public GrpDatacollectPrvVO queryIdAndPath(Map<String,Object> paraMap);
	/**
	 * 根据省份上报编码查询其他上传文件路径
	 * @param map
	 * @return
	 */
	public String queryOtherPathById(Map<String,Object> map);
	/**
	 * 根据省份上报数据编码查询负责人id
	 * @param datacollectPrvId
	 * @return
	 */
	public String queryUserIdByPrvId(String datacollectPrvId);
	/**
	 * 首页集团工单代办任务查询
	 * @param map
	 * @return
	 */
	public List<Act> queryWiteToDoReject(Map<String,Object> map);
}
