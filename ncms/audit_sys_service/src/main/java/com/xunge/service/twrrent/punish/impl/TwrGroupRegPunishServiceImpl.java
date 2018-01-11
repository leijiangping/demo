package com.xunge.service.twrrent.punish.impl;

import java.util.HashMap;
import java.util.Map;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.dao.twrrent.punish.ITwrGroupRegPunishDao;
import com.xunge.model.towerrent.punish.TwrGroupRegPunishVO;
import com.xunge.service.twrrent.punish.ITwrGroupRegPunishService;

/**
 * 集团既定考核指标扣罚汇总
 * @author changwq
 * 
 */
public class TwrGroupRegPunishServiceImpl implements ITwrGroupRegPunishService {

	private ITwrGroupRegPunishDao twrGroupRegPunishDao;
	
	@Override
	public String insertSelective(TwrGroupRegPunishVO twrGroupRegPunishVO) {
		try {
			twrGroupRegPunishDao.insertSelective(twrGroupRegPunishVO);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}

	@Override
	public String updateByPrimaryKeySelective(
			TwrGroupRegPunishVO twrGroupRegPunishVO) {
		try {
			twrGroupRegPunishDao
					.updateByPrimaryKeySelective(twrGroupRegPunishVO);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}

	@Override
	public TwrGroupRegPunishVO queryGroupRegPunish(String regId,
			String punishYearMonth,Integer state) {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("regId",regId);
		paraMap.put("state",state);
		paraMap.put("punishYearMonth",punishYearMonth);
		return twrGroupRegPunishDao.queryGroupRegPunish(paraMap);
	}

	public ITwrGroupRegPunishDao getTwrGroupRegPunishDao() {
		return twrGroupRegPunishDao;
	}

	public void setTwrGroupRegPunishDao(ITwrGroupRegPunishDao twrGroupRegPunishDao) {
		this.twrGroupRegPunishDao = twrGroupRegPunishDao;
	}

}
