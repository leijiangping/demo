package com.xunge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xunge.comm.StateComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.exception.BusinessException;
import com.xunge.dao.system.user.ISysProvinceDao;
import com.xunge.model.SysDataAuthMenuTreeVO;
import com.xunge.model.SysProvinceTreeVO;
import com.xunge.service.ISysProvinceService;
/**
 * 区县树结构 service 实现类
 *
 */
public class SysProvinceServiceImpl implements ISysProvinceService {
	
	
	private ISysProvinceDao sysProvinceDao;
	
	@Override
	public List<SysDataAuthMenuTreeVO> queryOneProRedis(String prvId) {
		//zTree生成集合
		List<SysDataAuthMenuTreeVO> lssg;
		try {
			Map<String,Object> paraMap = new HashMap<String, Object>();
			int state = StateComm.STATE_0;
			paraMap.put("state", state);
			paraMap.put("prvId", prvId);
			//省一级对象
			List<SysProvinceTreeVO> lspv = sysProvinceDao.queryOnePro(paraMap);
			lssg = new ArrayList<SysDataAuthMenuTreeVO>();
			//将省市区一级放入tree集合
			for (int i = 0; i < lspv.size(); i++) {
				SysDataAuthMenuTreeVO tree = new SysDataAuthMenuTreeVO();
				tree.setId(lspv.get(i).getId());
				tree.setPid(lspv.get(i).getPid());
				tree.setName("-" + lspv.get(i).getCode() + "-"
						+ lspv.get(i).getName());
				lssg.add(tree);
			}
			return lssg;
		} catch (Exception e) {
			// TODO: handle exception
			throw new BusinessException(PromptMessageComm.ERR_GET_LIST);
		}
		
	}

	public ISysProvinceDao getSysProvinceDao() {
		return sysProvinceDao;
	}

	public void setSysProvinceDao(ISysProvinceDao sysProvinceDao) {
		this.sysProvinceDao = sysProvinceDao;
	}

	@Override
	public List<SysProvinceTreeVO> queryAllSimpleProvince() {
		return sysProvinceDao.queryAllSimpleProvince();
	}

}
