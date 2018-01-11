package com.xunge.service.datacollect.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.util.StrUtil;
import com.xunge.dao.datacollect.IGrpDatacollectDao;
import com.xunge.dao.datacollect.IGrpDatacollectHistoryDao;
import com.xunge.dao.datacollect.IGrpDatacollectPrvDao;
import com.xunge.dao.datacollect.IGrpDatacollecttypeDao;
import com.xunge.dao.datacollect.IGrpDatacollecttypePrvDao;
import com.xunge.model.datacollect.GrpDatacollectHistoryVO;
import com.xunge.service.datacollect.IGrpDatacollectHistoryService;

public class GrpDatacollectHistoryServiceImpl implements IGrpDatacollectHistoryService {

	private IGrpDatacollectDao grpDatacollectDao;
	
	private IGrpDatacollectHistoryDao grpDatacollectHistoryDao;
	
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
	
	public IGrpDatacollectHistoryDao getGrpDatacollectHistoryDao() {
		return grpDatacollectHistoryDao;
	}

	public void setGrpDatacollectHistoryDao(
			IGrpDatacollectHistoryDao grpDatacollectHistoryDao) {
		this.grpDatacollectHistoryDao = grpDatacollectHistoryDao;
	}

	@Override
	public String insertSelective(
			GrpDatacollectHistoryVO grpDatacollectHistoryVO) {
		try {
			grpDatacollectHistoryDao.insertSelective(grpDatacollectHistoryVO);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_INSERT_FAILED);
		}
		return RESULT.SUCCESS_1;
	}

	@Override
	public List<GrpDatacollectHistoryVO> queryHistoryByPrvId(
			String datacollectPrvId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("datacollectPrvId",datacollectPrvId);
		List<GrpDatacollectHistoryVO> list = grpDatacollectHistoryDao.queryHistoryByPrvId(map);
		if(list != null){
			for(int i=0;i<list.size();i++){
				GrpDatacollectHistoryVO g = list.get(i);
				if(!StrUtil.isBlank(g.getHistoryPrvFilePath())){
					String[] path = g.getHistoryPrvFilePath().split(";");
					List<String> pathList = new ArrayList<String>();
					for(int j=0;j<path.length;j++){
						if(!("null".equals(path[j]))){
							pathList.add(path[j]);
						}
					}
					g.setHistoryPrvFilePathList(pathList);
				}
			}
		}
		return list;
	}

}
