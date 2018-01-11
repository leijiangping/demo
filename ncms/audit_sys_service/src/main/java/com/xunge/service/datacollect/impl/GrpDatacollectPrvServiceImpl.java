package com.xunge.service.datacollect.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.GrpComm;
import com.xunge.comm.StateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.CollectionUtil;
import com.xunge.core.util.DateUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.datacollect.IGrpDatacollectDao;
import com.xunge.dao.datacollect.IGrpDatacollectHistoryDao;
import com.xunge.dao.datacollect.IGrpDatacollectPrvDao;
import com.xunge.dao.datacollect.IGrpDatacollecttypeDao;
import com.xunge.dao.datacollect.IGrpDatacollecttypePrvDao;
import com.xunge.dao.system.role.ISysRoleDao;
import com.xunge.model.activity.Act;
import com.xunge.model.datacollect.GrpDatacollectHistoryVO;
import com.xunge.model.datacollect.GrpDatacollectPrvVO;
import com.xunge.model.datacollect.GrpDatacollecttypePrvVO;
import com.xunge.model.system.role.SystemRoleVO;
import com.xunge.model.system.user.SysRoleVO;
import com.xunge.service.datacollect.IGrpDatacollectPrvService;

public class GrpDatacollectPrvServiceImpl implements IGrpDatacollectPrvService{

	private IGrpDatacollectDao grpDatacollectDao;
	
	private IGrpDatacollectPrvDao grpDatacollectPrvDao;
	
	private IGrpDatacollecttypeDao grpDatacollecttypeDao;
	
	private IGrpDatacollecttypePrvDao grpDatacollecttypePrvDao;
	
	private IGrpDatacollectHistoryDao grpDatacollectHistoryDao;
	
	private ISysRoleDao sysRoleDao;

	public ISysRoleDao getSysRoleDao() {
		return sysRoleDao;
	}

	public void setSysRoleDao(ISysRoleDao sysRoleDao) {
		this.sysRoleDao = sysRoleDao;
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
	public String deleteDataPrvById(String datacollectId) {
		try {
			grpDatacollectPrvDao.deleteDataPrvById(datacollectId);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_DELETE_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public String updateDataPrvById(String datacollectPrvId,String userId) throws ParseException {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		String date = DateUtils.formatDateTime(new Date());
		paraMap.put("datacollectPrvState",GrpComm.COMMIT_1);
		paraMap.put("datacollectPrvDate",date);
		paraMap.put("datacollectPrvId",datacollectPrvId);
		try {
			grpDatacollectPrvDao.updateDataPrvById(paraMap);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.USER_SEND_PRVMSG_FAILED);
		}
		/*Map<String,Object> map = new HashMap<String,Object>();
		map.put("datacollectPrvId",datacollectPrvId);*/
		String path = grpDatacollectPrvDao.queryOtherPathById(paraMap);
		List<String> paths = grpDatacollecttypePrvDao.queryPrvFilePath(paraMap);
		String pathName = "";
		if(!StrUtil.isBlank(path)){
			pathName += path+";";
		}
		if(paths != null){
			for(int i=0;i<paths.size();i++){
				pathName += paths.get(i)+";";
			}
		}
		GrpDatacollectHistoryVO grpDatacollectHistoryVO = new GrpDatacollectHistoryVO();
		grpDatacollectHistoryVO.setGrpDatacollectHistoryId(SysUUID.generator());
		grpDatacollectHistoryVO.setDatacollectPrvId(datacollectPrvId);
		grpDatacollectHistoryVO.setHistoryMsg(PromptMessageComm.THIS_PRV_SEND_MSG);
		grpDatacollectHistoryVO.setHisyoryDate(date);
		grpDatacollectHistoryVO.setHistoryCreateUserId(userId);//changwq
		if(!StrUtil.isBlank(pathName)){
			grpDatacollectHistoryVO.setHistoryPrvFilePath(pathName);
		}
		grpDatacollectHistoryDao.insertSelective(grpDatacollectHistoryVO);
		return RESULT.SUCCESS_1;
	}

	@Override
	public Page<GrpDatacollectPrvVO> queryDataCollectPrvVO(Map<String,Object> paraMap,
			int pageNumber,int pageSize) {
		String userId = (String) paraMap.get("userId");
		//查询不在集团草稿箱的
		paraMap.put("datacollectState",GrpComm.SEND_0);
		Page<GrpDatacollectPrvVO> pagelist = grpDatacollectPrvDao.queryDataCollectPrvVO(paraMap, pageNumber, pageSize);
		if(pagelist != null){
			List<GrpDatacollectPrvVO> list = pagelist.getResult();
			for(int i=0;i<list.size();i++){
				switch(list.get(i).getDatacollectPrvState()){
				case -1 :
					String PrvUserId = list.get(i).getDatacollectPrvUserId();
					if(userId.equals(PrvUserId)){
						list.get(i).setRunState(GrpComm.DO_0);
					}else{
						list.get(i).setRunState(GrpComm.DO_1);
					}
					break;
				case 0 :
					list.get(i).setRunState(GrpComm.DO_0);
					break;
				case 9 :
					String PrvUserId1 = list.get(i).getDatacollectPrvUserId();
					if(userId.equals(PrvUserId1)){
						list.get(i).setRunState(GrpComm.DO_0);
					}else{
						list.get(i).setDatacollectPrvState(GrpComm.COMMIT_8);
						list.get(i).setRunState(GrpComm.DO_1);
					}
					break;
				}
			}
			pagelist.setResult(list);
		}
		return pagelist;
	}

	@Override
	public Map<String, Object> queryCollectPrvById(String datacollectPrvId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		Map<String,Object> resMap = new HashMap<String,Object>();
		paraMap.put("datacollectPrvId",datacollectPrvId);
		paraMap.put("datacollectPrvState",GrpComm.COMMIT_0);
		GrpDatacollectPrvVO grpDatacollectPrvVO = grpDatacollectPrvDao.queryPrvCollByPrvId(paraMap);
		List<GrpDatacollecttypePrvVO> list = grpDatacollecttypePrvDao.queryEveryPrvMsgByPk(paraMap);
		List<GrpDatacollectHistoryVO> hisList = grpDatacollectHistoryDao.queryHistoryByPrvId(paraMap);
		if(hisList != null){
			for(int i=0;i<hisList.size();i++){
				GrpDatacollectHistoryVO g = hisList.get(i);
				if(!StrUtil.isBlank(g.getHistoryPrvFilePath())){
					String[] path = g.getHistoryPrvFilePath().split(";");
					List<String> pathlist = new ArrayList<String>();
					for(int j=0;j<path.length;j++){
						if(!(PromptMessageComm.NOTHING_NULL.equals(path[j]))){
							pathlist.add(path[j]);
						}
					}
					g.setHistoryPrvFilePathList(pathlist);
				}
			}
		}
		resMap.put("grpDatacollectPrvVO",grpDatacollectPrvVO);
		resMap.put("list",list);
		resMap.put("hisList",hisList);
		return resMap;
	}

	@Override
	public String updatePrvFileAndNote(String datacollectPrvNote,
			String datacollectPrvOtherfilepath,
			String datacollectPrvOtherfilename, String datacollectPrvId,
			int datacollectPrvState) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("datacollectPrvNote",datacollectPrvNote);
		paraMap.put("datacollectPrvOtherfilepath",datacollectPrvOtherfilepath);
		paraMap.put("datacollectPrvOtherfilename",datacollectPrvOtherfilename);
		paraMap.put("datacollectPrvId",datacollectPrvId);
		paraMap.put("datacollectPrvState",datacollectPrvState);
		try {
			grpDatacollectPrvDao.updatePrvFileAndNote(paraMap);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_INSERT_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public GrpDatacollectPrvVO queryPrvSelfPathAndName(String datacollectId,
			String prvId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("datacollectId",datacollectId);
		paraMap.put("prvId",prvId);
		return grpDatacollectPrvDao.queryPrvSelfPathAndName(paraMap);
	}

	@Override
	public String updatePrvOtherFile(String datacollectPrvOtherfilepath,
			String datacollectPrvOtherfilename,String datacollectPrvId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("datacollectPrvOtherfilepath",datacollectPrvOtherfilepath);
		paraMap.put("datacollectPrvOtherfilename",datacollectPrvOtherfilename);
		paraMap.put("datacollectPrvId",datacollectPrvId);
		try {
			grpDatacollectPrvDao.updatePrvOtherFile(paraMap);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_UPDATE_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public List<String> queryPrvIdByCollId(String datacollectId) {
		List<GrpDatacollectPrvVO> list = grpDatacollectPrvDao.queryDownPrvIdByCollId(datacollectId);
		List<String> strList = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			strList.add(list.get(i).getPrvId());
		}
		return strList;
	}

	

	public IGrpDatacollectHistoryDao getGrpDatacollectHistoryDao() {
		return grpDatacollectHistoryDao;
	}

	public void setGrpDatacollectHistoryDao(
			IGrpDatacollectHistoryDao grpDatacollectHistoryDao) {
		this.grpDatacollectHistoryDao = grpDatacollectHistoryDao;
	}

	@Override
	public String updatePrvStateToFinish(String datacollectId,String userId) throws ParseException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("datacollectId",datacollectId);
		map.put("datacollectPrvState",GrpComm.COMMIT_11);
		List<String> ids = grpDatacollectPrvDao.queryPrvIdBycollId(map);
		try {
			grpDatacollectPrvDao.updatePrvStateToFinish(map);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.USER_FINISH_ORDER_SUCCESS_BUT_FINISHPRV_FAILED);
		}
		GrpDatacollectHistoryVO grpDatacollectHistoryVO = new GrpDatacollectHistoryVO();
		for(int i=0;i<ids.size();i++){
			grpDatacollectHistoryVO.setGrpDatacollectHistoryId(SysUUID.generator());
			grpDatacollectHistoryVO.setDatacollectPrvId(ids.get(i));
			grpDatacollectHistoryVO.setHistoryMsg(PromptMessageComm.JT_WAS_FINISH_THIS_ORDER);
			String date = DateUtils.formatDateTime(new Date());
			grpDatacollectHistoryVO.setHisyoryDate(date);
			grpDatacollectHistoryVO.setHistoryCreateUserId(userId);
			grpDatacollectHistoryDao.insertSelective(grpDatacollectHistoryVO);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public String updateGrpToUserSelf(int datacollectPrvState,
			String datacollectPrvUser, String datacollectPrvUserId,
			String datacollectPrvId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("datacollectPrvState",datacollectPrvState);
		map.put("datacollectPrvUser",datacollectPrvUser);
		map.put("datacollectPrvUserId",datacollectPrvUserId);
		map.put("datacollectPrvId",datacollectPrvId);
		int res = grpDatacollectPrvDao.updateGrpToUserSelf(map);
		if(res > 0){
			return RESULT.SUCCESS_1;
		}
		return RESULT.FAIL_0;
	}

	@Override
	public String updateStateReject(String datacollectGroupOpinion,
			String datacollectId, String prvId, String userId)
			throws ParseException {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("datacollectPrvState",GrpComm.COMMIT__1);
		paraMap.put("datacollectGroupOpinion",datacollectGroupOpinion);
		paraMap.put("datacollectId",datacollectId);
		paraMap.put("prvId",prvId);
		List<String> ids = grpDatacollectPrvDao.queryPrvIdBycollId(paraMap);
		try {
			grpDatacollectPrvDao.updateStateReject(paraMap);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.JT_REJECT_ORDER_FAILED);
		}
		GrpDatacollectHistoryVO grpDatacollectHistoryVO = new GrpDatacollectHistoryVO();
		for(int i=0;i<ids.size();i++){
			grpDatacollectHistoryVO.setGrpDatacollectHistoryId(SysUUID.generator());
			grpDatacollectHistoryVO.setDatacollectPrvId(ids.get(i));
			grpDatacollectHistoryVO.setHistoryMsg(PromptMessageComm.JT_REJECT_ORDER_HEAD+datacollectGroupOpinion+PromptMessageComm.JT_REJECT_ORDER_END);
			String date = DateUtils.formatDateTime(new Date());
			grpDatacollectHistoryVO.setHisyoryDate(date);
			grpDatacollectHistoryVO.setHistoryCreateUserId(userId);
			grpDatacollectHistoryDao.insertSelective(grpDatacollectHistoryVO);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public String queryUserIdByPrvId(String datacollectPrvId) {
		return grpDatacollectPrvDao.queryUserIdByPrvId(datacollectPrvId);
	}

	@Override
	public List<Act> queryWiteToDoReject(UserLoginInfo userInfo,
			String treatmentState,Date startTime,Date endTime) {
		Map<String,Object> map = new HashMap<String,Object>();
		SimpleDateFormat sft = new SimpleDateFormat(PromptMessageComm.DATE_TYPE_24H);
		if(startTime!=null){
			String startDate = sft.format(startTime);
			map.put("startDate",startDate);
		}
		if(endTime!=null){
			String endDate = sft.format(endTime);
			map.put("endDate",endDate);
		}
		List<String> roleIds = userInfo.getRole_ids();
		String prvId = userInfo.getPrv_id();
		//获取业务员的菜单code
		String pageCode = PromptMessageComm.PRV_MENU_CODE;
		map.put("prvMenuId",pageCode);
		map.put("menuId", pageCode);
		map.put("roleState", StateComm.STATE_0);
		if(!StrUtil.isBlank(prvId)){
			map.put("prvId",prvId);
		}
		//根据菜单code查询可以查看该菜单的role集合
		List<SystemRoleVO> prvList = sysRoleDao.queryRoleByMenuId(map);
		List<String> roleIdlist = new ArrayList<String>();
		//获取roleid集合
		for(int i=0;i<prvList.size();i++){
			roleIdlist.add(prvList.get(i).getRoleId());
		}
		//判断roleid集合与登录用户角色有没有交集
		List<String> iflist = new ArrayList<String>();
		if(roleIds != null){
			iflist = CollectionUtil.getEqualList(roleIdlist,roleIds);
		}
		//如果有交集，则查询工单
		List<Act> list = new ArrayList<Act>();
		if(iflist != null && iflist.size()>0){
			List<Integer> stateList = new ArrayList<Integer>();
			if(RESULT.FAIL_0.equals(treatmentState)){
				stateList.add(-1);
				stateList.add(0);
				stateList.add(9);
			}else if(RESULT.SUCCESS_1.equals(treatmentState)){
				stateList.add(1);
				stateList.add(11);
			}
			String userId = userInfo.getUser_id();
			map.put("taskName",PromptMessageComm.JT_REJECT_TASK_NAME);
			map.put("prvId",prvId);
			map.put("stateList",stateList);
			List<Act> actList = grpDatacollectPrvDao.queryWiteToDoReject(map);
			if(actList != null){
				for(int i=0;i<actList.size();i++){
					String id = actList.get(i).getAssignee();
					if(userId.equals(id) || StrUtil.isBlank(id)){
						list.add(actList.get(i));
					}
				}
			}
			return list;
		}
		//没有则返回null
		return list;
	}
}
