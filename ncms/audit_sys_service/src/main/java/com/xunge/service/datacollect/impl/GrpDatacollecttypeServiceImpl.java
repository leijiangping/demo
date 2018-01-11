package com.xunge.service.datacollect.impl;

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
import com.xunge.service.datacollect.IGrpDatacollecttypeService;

public class GrpDatacollecttypeServiceImpl implements IGrpDatacollecttypeService {
	
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
	public String updateFiles(Map<String, Object> paraMap) {
		try {
			grpDatacollecttypeDao.updateFiles(paraMap);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_UPDATE_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public String deleteByPrimaryKey(String datacollecttypeId) {
		try {
			grpDatacollecttypeDao.deleteByPrimaryKey(datacollecttypeId);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_DELETE_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public List<String> queryTypeBeUsed() {
		return grpDatacollecttypeDao.queryTypeBeUsed();
	}

	@Override
	public List<String> queryTypeBeUsedById(String datacollectId) {
		return grpDatacollecttypeDao.queryTypeBeUsedById(datacollectId);
	}

	@Override
	public String deleteUserLessMsg() {
		try {
			grpDatacollecttypeDao.deleteUserLessMsg();
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_DELETE_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public String queryTempTypeByTypeId(String datacollecttypeId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("datacollecttypeId",datacollecttypeId);
		return grpDatacollecttypeDao.queryTempTypeByTypeId(map);
	}
}
