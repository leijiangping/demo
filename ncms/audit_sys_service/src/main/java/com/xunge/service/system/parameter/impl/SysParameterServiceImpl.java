package com.xunge.service.system.parameter.impl;

import java.util.List;
import java.util.Map;

import com.xunge.comm.StateComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.page.Page;
import com.xunge.dao.system.parameter.ISysParameterDao;
import com.xunge.model.system.parameter.SysParameterVO;
import com.xunge.model.system.role.SystemRoleVO;
import com.xunge.service.system.parameter.ISysParameterService;
/**
 * 系统参数管理维护service实现类
 *
 */
public class SysParameterServiceImpl implements ISysParameterService {

	private ISysParameterDao sysParameterDao;

	@Override
	public String updateParameter(SysParameterVO sysParameterVO) {
		try {
			sysParameterDao.updateParameter(sysParameterVO);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			// TODO: handle exception
			return RESULT.FAIL_0;
		}
	}
	
	@Override
	public Page<List<SysParameterVO>> queryParameter(Map<String,Object> paraMap,int pageNumber,int pageSize) {
		return sysParameterDao.queryParameter(paraMap,pageNumber,pageSize);
	}

	@Override
	public SysParameterVO getParameter(String paraId) {
		SysParameterVO sysParameterVO = sysParameterDao.getParameter(paraId);
		return sysParameterVO;
	}

	public ISysParameterDao getSysParameterDao() {
		return sysParameterDao;
	}

	public void setSysParameterDao(ISysParameterDao sysParameterDao) {
		this.sysParameterDao = sysParameterDao;
	}

	@Override
	public String openParameter(String paraId) {
		try {
			// TODO Auto-generated method stub
			sysParameterDao.openParameter(paraId);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			return RESULT.FAIL_0;
		}
	}

	@Override
	public String stopParameter(String paraId) {
		try {
			// TODO Auto-generated method stub
			sysParameterDao.stopParameter(paraId);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			return RESULT.FAIL_0;
		}
	}
}
