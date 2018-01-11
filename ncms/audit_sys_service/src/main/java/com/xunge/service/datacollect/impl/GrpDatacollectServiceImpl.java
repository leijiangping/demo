package com.xunge.service.datacollect.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.GrpComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.page.Page;
import com.xunge.core.util.DateUtils;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.datacollect.IGrpDatacollectDao;
import com.xunge.dao.datacollect.IGrpDatacollectHistoryDao;
import com.xunge.dao.datacollect.IGrpDatacollectPrvDao;
import com.xunge.dao.datacollect.IGrpDatacollecttypeDao;
import com.xunge.dao.datacollect.IGrpDatacollecttypePrvDao;
import com.xunge.dao.system.user.ISysUserDao;
import com.xunge.model.datacollect.GrpDatacollectHistoryVO;
import com.xunge.model.datacollect.GrpDatacollectPrvVO;
import com.xunge.model.datacollect.GrpDatacollectVO;
import com.xunge.model.datacollect.GrpDatacollecttypePrvVO;
import com.xunge.model.datacollect.GrpDatacollecttypeVO;
import com.xunge.model.system.user.SysUserVO;
import com.xunge.service.datacollect.IGrpDatacollectService;

public class GrpDatacollectServiceImpl implements IGrpDatacollectService {

	private IGrpDatacollectDao grpDatacollectDao;
	
	private IGrpDatacollectPrvDao grpDatacollectPrvDao;
	
	private IGrpDatacollecttypeDao grpDatacollecttypeDao;
	
	private IGrpDatacollecttypePrvDao grpDatacollecttypePrvDao;
	
	private IGrpDatacollectHistoryDao grpDatacollectHistoryDao;
	
	private ISysUserDao sysUserDao; 
	
	public ISysUserDao getSysUserDao() {
		return sysUserDao;
	}

	public void setSysUserDao(ISysUserDao sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	public IGrpDatacollectDao getGrpDatacollectDao() {
		return grpDatacollectDao;
	}

	public void setGrpDatacollectDao(IGrpDatacollectDao grpDatacollectDao) {
		this.grpDatacollectDao = grpDatacollectDao;
	}

	public IGrpDatacollectPrvDao getGrpDatacollectPrvDao() {
		return grpDatacollectPrvDao;
	}

	public void setGrpDatacollectPrvDao(IGrpDatacollectPrvDao grpDatacollectPrvDao) {
		this.grpDatacollectPrvDao = grpDatacollectPrvDao;
	}

	public IGrpDatacollecttypeDao getGrpDatacollecttypeDao() {
		return grpDatacollecttypeDao;
	}

	public void setGrpDatacollecttypeDao(
			IGrpDatacollecttypeDao grpDatacollecttypeDao) {
		this.grpDatacollecttypeDao = grpDatacollecttypeDao;
	}

	public IGrpDatacollecttypePrvDao getGrpDatacollecttypePrvDao() {
		return grpDatacollecttypePrvDao;
	}

	public void setGrpDatacollecttypePrvDao(
			IGrpDatacollecttypePrvDao grpDatacollecttypePrvDao) {
		this.grpDatacollecttypePrvDao = grpDatacollecttypePrvDao;
	}

	@Override
	public Page<GrpDatacollectVO> queryGrpDataCollectVO(Map<String,Object> paraMap,
			int pageNumber,int pageSize) {
		return grpDatacollectDao.queryGrpDataCollectVO(paraMap, pageNumber, pageSize);
	}

	@Override
	public String deleteByPrimaryKey(String datacollectId) {
		try {
			//删除省级上报文件类型
			grpDatacollecttypePrvDao.deleteTypePrvById(datacollectId);
			//删除省级上报信息
			grpDatacollectPrvDao.deleteDataPrvById(datacollectId);
			//删除集团收集数据类型数据
			grpDatacollecttypeDao.deleteByDCId(datacollectId);
			//删除集团收集表
			grpDatacollectDao.deleteByPrimaryKey(datacollectId);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_DELETE_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	/**
	 * 新增集团收集表信息，同步关联集团收据数据类型
	 * 并且同时新增省级上报信息和省级上报文件类型
	 */
	@Override
	public String insertSelective(GrpDatacollectVO grpDatacollectVO,List<String> prvIds,
			List<String> typeIds) {
		try {
			//新增集团收集信息
			grpDatacollectDao.insertSelective(grpDatacollectVO);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_INSERT_FAILED);
		}
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("datacollectId", grpDatacollectVO.getDatacollectId());
			for (int i = 0; i < typeIds.size(); i++) {
				paraMap.put("datacollecttypeId", typeIds.get(i));
				grpDatacollecttypeDao.updateTypeById(paraMap);
			}
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.LINK_JTDATA_TYPE_AND_JTORDER_FAILED);
		}
		try {
			//省级上报信息
			for (int i = 0; i < prvIds.size(); i++) {
				//创建省级对象
				GrpDatacollectPrvVO grpDatacollectPrvVO = new GrpDatacollectPrvVO();
				grpDatacollectPrvVO.setDatacollectPrvId(SysUUID.generator());//设置省级上报信息id
				grpDatacollectPrvVO.setDatacollectId(grpDatacollectVO
						.getDatacollectId());//关联集团收集表
				grpDatacollectPrvVO.setPrvId(prvIds.get(i));//设置省份
				grpDatacollectPrvVO.setDatacollectPrvState(GrpComm.COMMIT_0);//设置上报状态为未上报
				grpDatacollectPrvDao.insertSelective(grpDatacollectPrvVO);//新增省级上报信息
				for (int j = 0; j < typeIds.size(); j++) {
					GrpDatacollecttypePrvVO grpDatacollecttypePrvVO = new GrpDatacollecttypePrvVO();
					grpDatacollecttypePrvVO.setDatacollecttypePrvId(SysUUID
							.generator());//新建省级上报文件类型id
					grpDatacollecttypePrvVO
							.setDatacollecttypeId(typeIds.get(j));//关联集团收集数类型
					grpDatacollecttypePrvVO
							.setDatacollectPrvId(grpDatacollectPrvVO
									.getDatacollectPrvId());//关联省级上报信息
					grpDatacollecttypePrvVO
							.setDatacollecttypePrvState(GrpComm.IN_0);//状态设置为未入库
					grpDatacollecttypePrvDao
							.insertSelective(grpDatacollecttypePrvVO);//新增省级上报文件类型信息
				}
			}
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_INSERT_FAILED);
		}
		try {
			/**
			 * 删除以解除关联，并未再次关联的集团收集数据类型
			 */
			grpDatacollecttypeDao.deleteUserLessMsg();
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_DELETE_FAILED);
		}
		return "1";
	}
 
	@Override
	public String updateByPrimaryKeySelective(GrpDatacollectVO grpDatacollectVO,
			List<String> prvIds,List<String> typeIds) {
		//删除省级上报文件类型
		grpDatacollecttypePrvDao.deleteTypePrvById(grpDatacollectVO.getDatacollectId());
		//删除省级上报信息
		grpDatacollectPrvDao.deleteDataPrvById(grpDatacollectVO.getDatacollectId());
		//删除原有关联
		grpDatacollecttypeDao.deleteIdById(grpDatacollectVO.getDatacollectId());
		try {
			grpDatacollectDao.updateByPrimaryKeySelective(grpDatacollectVO);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_UPDATE_FAILED);
		}
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("datacollectId", grpDatacollectVO.getDatacollectId());
			for (int i = 0; i < typeIds.size(); i++) {
				paraMap.put("datacollecttypeId", typeIds.get(i));
				grpDatacollecttypeDao.updateTypeById(paraMap);
			}
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.LINK_JTDATA_TYPE_AND_JTORDER_FAILED);
		}
		try {
			//省级上报信息
			for (int i = 0; i < prvIds.size(); i++) {
				//创建省级对象
				GrpDatacollectPrvVO grpDatacollectPrvVO = new GrpDatacollectPrvVO();
				grpDatacollectPrvVO.setDatacollectPrvId(SysUUID.generator());//设置省级上报信息id
				grpDatacollectPrvVO.setDatacollectId(grpDatacollectVO
						.getDatacollectId());//关联集团收集表
				grpDatacollectPrvVO.setPrvId(prvIds.get(i));//设置省份
				grpDatacollectPrvVO.setDatacollectPrvState(GrpComm.COMMIT_0);//设置上报状态为未上报
				grpDatacollectPrvDao.insertSelective(grpDatacollectPrvVO);//新增省级上报信息
				for (int j = 0; j < typeIds.size(); j++) {
					GrpDatacollecttypePrvVO grpDatacollecttypePrvVO = new GrpDatacollecttypePrvVO();
					grpDatacollecttypePrvVO.setDatacollecttypePrvId(SysUUID
							.generator());//新建省级上报文件类型id
					grpDatacollecttypePrvVO
							.setDatacollecttypeId(typeIds.get(j));//关联集团收集数类型
					grpDatacollecttypePrvVO
							.setDatacollectPrvId(grpDatacollectPrvVO
									.getDatacollectPrvId());//关联省级上报信息
					grpDatacollecttypePrvVO
							.setDatacollecttypePrvState(GrpComm.IN_0);//状态设置为未入库
					grpDatacollecttypePrvDao
							.insertSelective(grpDatacollecttypePrvVO);//新增省级上报文件类型信息
				}
			}
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_UPDATE_FAILED);
		}
		try {
			/**
			 * 删除以解除关联，并未再次关联的集团收集数据类型
			 */
			grpDatacollecttypeDao.deleteUserLessMsg();
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_DELETE_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public Map<String,Object> queryGrpDataCollectById(String datacollectId) {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		Map<String,Object> resMap = new HashMap<String,Object>();
		paraMap.put("datacollectId",datacollectId);
		List<GrpDatacollecttypeVO> list = grpDatacollecttypeDao.queryByGrpCollId(datacollectId);
		GrpDatacollectVO grpDatacollectVO = grpDatacollectDao.queryGrpDataCollectById(paraMap);
		List<GrpDatacollectPrvVO> prvIds = grpDatacollectPrvDao.queryDownPrvIdByCollId(datacollectId);
		String copyId = grpDatacollectVO.getDatacollectCopy();
		String[] users = copyId.split(PromptMessageComm.COMMA_SYMBOL);
		List<SysUserVO> userList = new ArrayList<SysUserVO>();
		for(int i=0;i<users.length;i++){
			paraMap.put("userId",users[i]);
			userList.add(sysUserDao.queryUserIdByUserId(paraMap));
		}
		resMap.put("userList",userList);
		resMap.put("GrpDatacollecttypeList", list);
		resMap.put("grpDatacollectVO",grpDatacollectVO);
		resMap.put("prvIds",prvIds);
		return resMap;
	}


	@Override
	public Map<String, Object> queryGrpPrvCollectById(String datacollectId) {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		Map<String,Object> resMap = new HashMap<String,Object>();
		paraMap.put("datacollectId",datacollectId);
		GrpDatacollectVO grpDatacollectVO = grpDatacollectDao.queryGrpDataCollectById(paraMap);
		List<GrpDatacollectPrvVO> downPrvId = grpDatacollectPrvDao.queryDownPrvIdByCollId(datacollectId);
		paraMap.put("datacollectPrvState",GrpComm.COMMIT_1 );
		List<String> upPrvId = grpDatacollectPrvDao.queryPrvIdByUpCollId(paraMap);
		resMap.put("grpDatacollectVO",grpDatacollectVO);
		resMap.put("downPrvId",downPrvId);
		resMap.put("upPrvId",upPrvId);
		return resMap;
	}

	@Override
	public List<GrpDatacollecttypePrvVO> queryEveryPrvMsg(String datacollectId,String prvId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("datacollectId",datacollectId);
		paraMap.put("prvId",prvId);
		return grpDatacollecttypePrvDao.queryEveryPrvMsg(paraMap);
	}
	/**
	 * 上传模板结束新增集团收集数据类型信息
	 */
	@Override
	public String insertSelective(GrpDatacollecttypeVO grpDatacollecttypeVO) {
		try {
			grpDatacollecttypeDao.insertSelective(grpDatacollecttypeVO);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.UPLOAD_AND_INSERT_DATA_TYPE_FAILED);
		}
		return RESULT.SUCCESS_1;
	}
	/**
	 * 上传模板结束修改集团收集数据类型信息
	 */
	@Override
	public String updateByPrimaryKeySelective(GrpDatacollecttypeVO grpDatacollecttypeVO) {
		try {
			grpDatacollecttypeDao.updateByPrimaryKeySelective(grpDatacollecttypeVO);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.UPLOAD_AND_UPDATE_DATA_TYPE_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public String querySameThing(String datacollectTitle) {
		String datacollectId = grpDatacollectDao.querySameThing(datacollectTitle);
		return datacollectId;
	}

	@Override
	public GrpDatacollectVO queryTitleById(String datacollectId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("datacollectId",datacollectId);
		return grpDatacollectDao.queryTitleById(paraMap);
	}

	@Override
	public String queryCopyUserById(String datacollectId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("datacollectId",datacollectId);
		return grpDatacollectDao.queryCopyUserById(paraMap);
	}

	public IGrpDatacollectHistoryDao getGrpDatacollectHistoryDao() {
		return grpDatacollectHistoryDao;
	}

	public void setGrpDatacollectHistoryDao(
			IGrpDatacollectHistoryDao grpDatacollectHistoryDao) {
		this.grpDatacollectHistoryDao = grpDatacollectHistoryDao;
	}

	@Override
	public String updateDatacollectToFinish(String datacollectId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("datacollectId",datacollectId);
		map.put("datacollectState",GrpComm.SEND_11);
		try {
			//修改集团派发状态为已完结
			grpDatacollectDao.updateDatacollectToFinish(map);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.USER_FINISH_ORDER_SUCCESS_BUT_FINISHPRV_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public String updateStateById(String datacollectUser, String datacollectId,
			String userId, String title) throws ParseException {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("datacollectState",GrpComm.SEND_1);
		paraMap.put("datacollectDate",new Date());
		paraMap.put("datacollectUser",datacollectUser);
		paraMap.put("datacollectId",datacollectId);
		List<String> ids = grpDatacollectPrvDao.queryPrvIdBycollId(paraMap);
		try {
			grpDatacollectDao.updateStateById(paraMap);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.USER_SENDMSG_TO_PRV_FAILED);
		}
		GrpDatacollectHistoryVO grpDatacollectHistoryVO = new GrpDatacollectHistoryVO();
		for(int i=0;i<ids.size();i++){
			grpDatacollectHistoryVO.setGrpDatacollectHistoryId(SysUUID.generator());
			grpDatacollectHistoryVO.setDatacollectPrvId(ids.get(i));
			grpDatacollectHistoryVO.setHistoryMsg(PromptMessageComm.JT_SEND_ORDER_HEAD+title+PromptMessageComm.JT_SEND_ORDER_END);
			String date = DateUtils.formatDateTime(new Date());
			grpDatacollectHistoryVO.setHisyoryDate(date);
			grpDatacollectHistoryVO.setHistoryCreateUserId(userId);
			grpDatacollectHistoryDao.insertSelective(grpDatacollectHistoryVO);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public String querySendDateByPrvId(String datacollectPrvId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("datacollectPrvId",datacollectPrvId);
		return grpDatacollectDao.querySendDateByPrvId(map);
	}

}
