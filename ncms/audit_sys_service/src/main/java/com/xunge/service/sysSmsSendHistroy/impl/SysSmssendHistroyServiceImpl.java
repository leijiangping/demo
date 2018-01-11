package com.xunge.service.sysSmsSendHistroy.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.DateUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.datacollect.IGrpDatacollectDao;
import com.xunge.dao.datacollect.IGrpDatacollectPrvDao;
import com.xunge.dao.datacollect.IGrpDatacollecttypeDao;
import com.xunge.dao.datacollect.IGrpDatacollecttypePrvDao;
import com.xunge.dao.sysSmsSendHistroy.ISysSmsConfigDao;
import com.xunge.dao.sysSmsSendHistroy.ISysSmssendHistroyDao;
import com.xunge.model.smsSendHistroy.SysSmsConfigVO;
import com.xunge.model.smsSendHistroy.SysSmssendHistroyVO;
import com.xunge.model.util.SmsRecvBackVO;
import com.xunge.service.sysSmsSendHistroy.ISysSmssendHistroyService;
import com.xunge.service.system.sms.ISmsSendTools;

public class SysSmssendHistroyServiceImpl implements ISysSmssendHistroyService {

	private IGrpDatacollectDao grpDatacollectDao;
	
	private IGrpDatacollectPrvDao grpDatacollectPrvDao;
	
	private IGrpDatacollecttypeDao grpDatacollecttypeDao;
	
	private IGrpDatacollecttypePrvDao grpDatacollecttypePrvDao;
	
	private ISysSmssendHistroyDao sysSmssendHistroyDao;
	
	private ISysSmsConfigDao sysSmsConfigDao;
	
	private ISmsSendTools smsSendTools;

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
	public String insertSelective(SysSmssendHistroyVO sysSmssendHistroyVO) {
		try {
			sysSmssendHistroyDao.insertSelective(sysSmssendHistroyVO);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
		return "1";
	}

	@Override
	public Page<SysSmssendHistroyVO> queryHistroyByCollId(
			String grpDatacollectId, int pageNumber, int pageSize) {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("bussTableId",grpDatacollectId);
		return sysSmssendHistroyDao.queryHistroyByCollId(paraMap, pageNumber, pageSize);
	}

	public ISysSmssendHistroyDao getSysSmssendHistroyDao() {
		return sysSmssendHistroyDao;
	}

	public void setSysSmssendHistroyDao(ISysSmssendHistroyDao sysSmssendHistroyDao) {
		this.sysSmssendHistroyDao = sysSmssendHistroyDao;
	}

	@Override
	public void grpSendMsg(Map<String, Object> map) {
		Map<String,Object> param = new HashMap<String,Object>();
		UserLoginInfo user = (UserLoginInfo) map.get("user");
		param.put("mobiles",map.get("mobiles")+"");
		param.put("tempid",map.get("tempid")+"");
		if(map.get("title") != null){
			param.put("title",map.get("title")+"");
		}
		if(map.get("time") != null){ 
			param.put("time",map.get("time")+"");
		}
		SmsRecvBackVO smsbkObj = smsSendTools.JTDataCollectSmsSend(param);
		if(smsbkObj != null){
			//查询短信模板信息
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("tempCode",map.get("tempid")+"");
			SysSmsConfigVO ssc = sysSmsConfigDao.querySmsModelMsg(paraMap);
			//创建短信下发回执信息对象
			SysSmssendHistroyVO sysSmssendHistroyVO = new SysSmssendHistroyVO();
			sysSmssendHistroyVO.setSysSmssendHistoryId(SysUUID.generator());
			sysSmssendHistroyVO.setBussTableId(map.get("bussTableId")+"");
			if(ssc != null){
				sysSmssendHistroyVO.setBussName(ssc.getTempName());
			}
			if(map.get("prvId") != null){
				sysSmssendHistroyVO.setPrvId(map.get("prvId")+"");
			}else{
				sysSmssendHistroyVO.setPrvId("000000");
			}
			sysSmssendHistroyVO.setSmsSenderId(user.getUser_id());
			sysSmssendHistroyVO.setSmsSenderName(user.getUser_name());
			sysSmssendHistroyVO.setSmsRecvRoleId(map.get("roleId")+"");
			sysSmssendHistroyVO.setSmsRecvUserId(map.get("userId")+"");
			sysSmssendHistroyVO.setSmsTempId(map.get("tempid")+"");
			String date = DateUtils.formatDateTime(new Date());
			sysSmssendHistroyVO.setSendDatetime(date);
			sysSmssendHistroyVO.setSendBackMsg(smsbkObj.getBackInfo());
			sysSmssendHistroyVO.setIsSendSuccess(smsbkObj.getBackResult() ? 0 : 1);
			//新增短信下发回执信息
			insertSelective(sysSmssendHistroyVO);
		}
	}

	public ISmsSendTools getSmsSendTools() {
		return smsSendTools;
	}

	public void setSmsSendTools(ISmsSendTools smsSendTools) {
		this.smsSendTools = smsSendTools;
	}

	public ISysSmsConfigDao getSysSmsConfigDao() {
		return sysSmsConfigDao;
	}

	public void setSysSmsConfigDao(ISysSmsConfigDao sysSmsConfigDao) {
		this.sysSmsConfigDao = sysSmsConfigDao;
	}

}
