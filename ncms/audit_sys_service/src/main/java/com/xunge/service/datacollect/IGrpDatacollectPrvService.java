package com.xunge.service.datacollect;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.model.activity.Act;
import com.xunge.model.datacollect.GrpDatacollectPrvVO;

public interface IGrpDatacollectPrvService {
	/**
	 * 根据集团收集编号删除省级上报信息
	 * @param datacollectId
	 * @return
	 */
	public String deleteDataPrvById(String datacollectId);
	/**
	 * 修改省级上报表上报状态
	 * @param paraMap
	 * @return
	 * @throws ParseException 
	 */
	public String updateDataPrvById(String datacollectPrvId,String userId) throws ParseException;
	/**
	 * 关联集团收集表查询省级上报信息
	 * @param paraMap
	 * @return
	 */
	public Page<GrpDatacollectPrvVO> queryDataCollectPrvVO(Map<String,Object> paraMap,
			int pageNumber,int pageSize);
	/**
	 * 根据省份上报信息id查询上传数据
	 * @param datacollectPrvId
	 * @return
	 */
	public Map<String,Object> queryCollectPrvById(String datacollectPrvId);
	/**
	 * 省级上传数据保存按钮
	 * @param datacollectPrvNote
	 * @param datacollectPrvOtherfilepath
	 * @param datacollectPrvOtherfilename
	 * @param datacollectPrvId
	 * @return
	 */
	public String updatePrvFileAndNote(String datacollectPrvNote,
			String datacollectPrvOtherfilepath,String datacollectPrvOtherfilename,String datacollectPrvId,int datacollectPrvState);
	/**
	 * 根据省份编码和集团收集编码查询省级备注，其他文件路径，名称
	 * @param paraMap
	 * @return
	 */
	public GrpDatacollectPrvVO queryPrvSelfPathAndName(String datacollectId,String prvId);
	/**
	 * 修改省级其他上报文件信息
	 * @param grpDatacollectPrvVO
	 * @return
	 */
	public String updatePrvOtherFile(String datacollectPrvOtherfilepath,
			String datacollectPrvOtherfilename,String datacollectPrvId);
	/**
	 * 根据集团收集id查询下派省份id
	 * @param datacollectId
	 * @return
	 */
	public List<String> queryPrvIdByCollId(String datacollectId);
	/**
	 * 集团驳回省级上报信息并修改上报状态，添加处理意见
	 * @param datacollectGroupOpinion
	 * @param datacollectId
	 * @param prvId
	 * @return
	 * @throws ParseException 
	 */
	public String updateStateReject(String datacollectGroupOpinion,String datacollectId,String prvId,
			String userId) throws ParseException;
	/**
	 * 集团完结工单-修改相应各省状态为已完结
	 * @param datacollectId
	 * @return
	 * @throws ParseException 
	 */
	public String updatePrvStateToFinish(String datacollectId,String userId) throws ParseException;
	/**
	 * 将工单负责人设置为登录用户
	 * @param datacollectPrvState
	 * @param datacollectPrvUser
	 * @param datacollectPrvUserId
	 * @param datacollectPrvId
	 * @return
	 */
	public String updateGrpToUserSelf(int datacollectPrvState,String datacollectPrvUser,
			String datacollectPrvUserId,String datacollectPrvId);
	/**
	 * 根据省份上报数据编码查询负责人id
	 * @param datacollectPrvId
	 * @return
	 */
	public String queryUserIdByPrvId(String datacollectPrvId);
	/**
	 * 首页集团派发工单待办查询
	 * @return
	 */
	public List<Act> queryWiteToDoReject(UserLoginInfo userInfo,String treatmentState,Date startTime,Date endTime);
}
