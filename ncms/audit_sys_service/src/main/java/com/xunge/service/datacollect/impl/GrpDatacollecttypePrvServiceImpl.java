package com.xunge.service.datacollect.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.dao.datacollect.IGrpDatacollectDao;
import com.xunge.dao.datacollect.IGrpDatacollectPrvDao;
import com.xunge.dao.datacollect.IGrpDatacollecttypeDao;
import com.xunge.dao.datacollect.IGrpDatacollecttypePrvDao;
import com.xunge.model.datacollect.GrpDatacollectPrvVO;
import com.xunge.model.datacollect.GrpDatacollecttypePrvVO;
import com.xunge.service.datacollect.IGrpDatacollecttypePrvService;

public class GrpDatacollecttypePrvServiceImpl implements IGrpDatacollecttypePrvService {

	private IGrpDatacollectDao grpDatacollectDao;
	
	private IGrpDatacollectPrvDao grpDatacollectPrvDao;
	
	private IGrpDatacollecttypeDao grpDatacollecttypeDao;
	
	private IGrpDatacollecttypePrvDao grpDatacollecttypePrvDao;

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
	public String updatePrvType(GrpDatacollecttypePrvVO grpDatacollecttypePrvVO) {
		try {
			grpDatacollecttypePrvDao.updatePrvType(grpDatacollecttypePrvVO);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_UPDATE_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public String updatePrvFiles(Map<String, Object> paraMap) {
		try {
			grpDatacollecttypePrvDao.updatePrvFiles(paraMap);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_UPDATE_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public String deleteTypePrvByTypeId(Map<String, Object> paraMap) {
		try {
			grpDatacollecttypePrvDao.deleteTypePrvByTypeId(paraMap);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_DELETE_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public List<GrpDatacollecttypePrvVO> queryPrvUploadNameById(String datacollectPrvId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("datacollectPrvId",datacollectPrvId);
		return grpDatacollecttypePrvDao.queryPrvUploadNameById(map);
	}

	@Override
	public List<String> uploadZip(String datacollectId, String prvId) {
		List<String> downPath = new ArrayList<String>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("datacollectId",datacollectId);
		map.put("prvId",prvId);
		GrpDatacollectPrvVO grpDatacollectPrvVO = grpDatacollectPrvDao.queryIdAndPath(map);
		String datacollectPrvId = grpDatacollectPrvVO.getDatacollectPrvId();
		String otherPath = grpDatacollectPrvVO.getDatacollectPrvOtherfilepath();
		if(otherPath != null){
			downPath.add(otherPath);
		}
		map.put("datacollectPrvId",datacollectPrvId);
		List<String> paths = grpDatacollecttypePrvDao.queryPrvFilePath(map);
		for(int i=0;i<paths.size();i++){
			downPath.add(paths.get(i));
		}
		return downPath;
	}
}
