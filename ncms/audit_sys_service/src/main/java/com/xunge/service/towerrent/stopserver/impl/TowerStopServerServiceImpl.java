package com.xunge.service.towerrent.stopserver.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xunge.comm.StateComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.page.Page;
import com.xunge.core.util.SessionUtils;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.dao.towerrent.mobile.ITwrRentInformationDao;
import com.xunge.dao.towerrent.stopserver.ITowerStopServerDao;
import com.xunge.model.activity.Act;
import com.xunge.model.selfrent.rebursepoint.RentBillaccountVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.mobile.TwrRentInformationVO;
import com.xunge.model.towerrent.stopserver.TowerStopServerVO;
import com.xunge.service.activity.impl.ActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.towerrent.stopserver.ITowerStopServerService;
import com.xunge.service.towerrent.stopserver.exceldatahandler.TowerStopServerHandler;

/**
 * 终止服务 service  实现类
 * @author yuefy
 * @date 2017.07.20
 *
 */
public class TowerStopServerServiceImpl implements ITowerStopServerService {
	
	private ITowerStopServerDao towerStopServerDao;
	
	private ISysRegionDao sysRegionDao;
	
	private ITwrRentInformationDao twrRentInformationDao;
	
	private ActTaskService actTaskService;

	@SuppressWarnings("unchecked")
	@Override
	public Page<TowerStopServerVO> queryTowerStopServer(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		Page<TowerStopServerVO> page = towerStopServerDao.queryTowerStopServer(paraMap, pageNumber, pageSize);
		if(paraMap.get("idsList")!=null){
			List<TowerStopServerVO> list=Lists.newArrayList();
			for(TowerStopServerVO t:page.getResult()){
				for(Act acts:(List<Act>)paraMap.get("idsList")){
					if(t.getStopServerId() !=null&& t.getStopServerId().equals(acts.getBusinessId())){
						t.setAct(acts);
						list.add(t);
					}
				}
			}
			page.setResult(list);
		}
		return page;
	}

	/**
	 * 查询所有铁塔终止服务表信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author yuefy
	 */
	@Override
	public List<TowerStopServerVO> queryTowerStopServer(
			Map<String, Object> paraMap) {
		return towerStopServerDao.queryTowerStopServer(paraMap);
	}
	

	@Override
	public TowerStopServerVO queryTowerStopServerById(String stopServerId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("stopServerId",stopServerId);
		return towerStopServerDao.queryTowerStopServerById(paraMap);
	}


	@Override
	public String updateTowerStopServerCheckState(
			String stopServerId, int checkState) {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("stopServerId",stopServerId);
		paraMap.put("checkState",checkState);
		try {
			towerStopServerDao.updateTowerStopServerCheckState(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}
	
	/**
	 * 导入铁塔终止服务信息
	 * @param file
	 * @param request
	 * @param paraMap
	 * @return
	 * @author yuefy
	 * @throws Exception 
	 * @throws IOException 
	 */
	@Override
	public Map<String, Object> importTowerStopServer(MultipartFile file,String suffix,
			HttpServletRequest request, Map<String, Object> paraMap) throws  Exception {
		//_TowerStopServer这个是标识Session Name的，每个功能建议唯一
		SessionUtils.setProcessSession(suffix,DateDisposeComm.BEGIN_ANALAYSIS_FILE,SelfelecComm.NUMBER_5, request);
		//导入参数
		ImportParams iparams=new ImportParams();
		//设置自定义数据处理
		paraMap.put("state",StateComm.STATE_0);
		List<SysRegionVO> listreg=sysRegionDao.getAddress(paraMap);//准备需要的数据处理
		TowerStopServerHandler tbh=new TowerStopServerHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.REGION});//需要处理数据的列名 
		iparams.setDataHanlder(tbh);
		iparams.setHeadRows(SelfelecComm.NUMBER_2);
		List<TowerStopServerVO> list = ExcelImportUtil.importExcel(file.getInputStream(),TowerStopServerVO.class, iparams);
		for(int i = 0 ; i < list.size() ; i++){
			list.get(i).setStartDate(list.get(i).getTowerStopServerDateVO().get(StateComm.STATE_0).getStartDate());
			list.get(i).setEndDate(list.get(i).getTowerStopServerDateVO().get(StateComm.STATE_0).getEndDate());
		}
		/**
		 * 验证数据在数据库里面是否存在
		 */
		List<TowerStopServerVO> listInexistence = Lists.newArrayList();
		List<TowerStopServerVO> listExist = Lists.newArrayList();
		List<TowerStopServerVO> listInsert = Lists.newArrayList();
		if(list != null && list.size() > 0){
			for (int index=0;index<list.size();index++) {
				TowerStopServerVO stopServer = list.get(index);
				stopServer.setStopServerId(SysUUID.generator());
				stopServer.setCreateUserId((String)paraMap.get("userId"));
				stopServer.setAuditState(ActivityStateComm.STATE_AUDIT);
				//业务确认单号
				paraMap.put("businessConfirmNumber", stopServer.getBusinessConfirmNumber());
				//铁塔站址编码
				paraMap.put("towerStationCode", stopServer.getTowerStationCode());
				//生效日期
				paraMap.put("activeDate", stopServer.getStopUseDate());
				TwrRentInformationVO tri = twrRentInformationDao.queryTwrRentInformationByBusinessNumDate(paraMap);
				if(tri == null){
					listInexistence.add(stopServer);//保存不存在数据
				}else{
					//业务确认单号
					paraMap.put("stopUseDate", stopServer.getStopUseDate());
					TowerStopServerVO towerStopServerVO = towerStopServerDao.queryTowerStopServerById(paraMap);
					if(towerStopServerVO != null){
						listExist.add(stopServer);//清除数据库已存在的数据
					}else{
						listInsert.add(stopServer);
					}
				}
			}
		}
		SessionUtils.setProcessSession(suffix,DateDisposeComm.ANALAYSIS_FILE_DONE,SelfelecComm.NUMBER_10, request);
		//大批量数据分批插入，每次插入500条
		int a = 500;//每次提交2000条
		int loop = (int) Math.ceil(listInsert.size() / (double) a);//批次
		int count=listInsert.size();
		int percent = (int) Math.ceil(90 / (double) loop);//计算每批占的百分比保存到数据库占80%的进度
		int sumPercent=20;//方便每次计算
		if(listInsert.size() > 0){
			for(int i = 0 ; i < listInsert.size() ; i ++){
				actTaskService.startProcess(ActUtils.PD_STOPSERVER_INFO[0], ActUtils.PD_STOPSERVER_INFO[1], listInsert.get(i).getStopServerId(),ActUtils.PD_STOPSERVER_INFO[2],null,(String)paraMap.get("userLoginname"));
			}
		}
		for (int i = 0; i < loop; i++) {
			 int stop = Math.min(a - 1, listInsert.size());//判断结束的序号
			 towerStopServerDao.insertTowerStopServer(listInsert.subList(0, stop));
			 listInsert.subList(0, stop).clear();//清除已经插入的数据
			 sumPercent+=percent;
			 sumPercent=(sumPercent>100?100:sumPercent);//如果大于100，按100计算
			 SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
		}
		SessionUtils.setProcessSession(suffix,PromptMessageComm.IMPORT_SUCCESS+PromptMessageComm.COMMA_SYMBOL+PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS,SelfelecComm.NUMBER_100,request);
		Map<String,Object> returnMap=Maps.newHashMap();
		returnMap.put("errMsg", (listInexistence.size()>0?PromptMessageComm.NO_INFO_NO_SERVICE+listInexistence.size()+PromptMessageComm.STRIP:"")+(listExist.size()>0?PromptMessageComm.ALREADY_EXIST_NUMBER+listExist.size()+PromptMessageComm.STRIP:""));
		returnMap.put("msg",PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS);
		returnMap.put("isExist", listInexistence);
		returnMap.put("successCount", count);
    	return returnMap;
	}

	public ITowerStopServerDao getTowerStopServerDao() {
		return towerStopServerDao;
	}

	public void setTowerStopServerDao(ITowerStopServerDao towerStopServerDao) {
		this.towerStopServerDao = towerStopServerDao;
	}

	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}

	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}

	public ITwrRentInformationDao getTwrRentInformationDao() {
		return twrRentInformationDao;
	}

	public void setTwrRentInformationDao(
			ITwrRentInformationDao twrRentInformationDao) {
		this.twrRentInformationDao = twrRentInformationDao;
	}

	public ActTaskService getActTaskService() {
		return actTaskService;
	}

	public void setActTaskService(ActTaskService actTaskService) {
		this.actTaskService = actTaskService;
	}

	@Override
	public String updateCheckStateById(String stopServerId,Integer checkState) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("stopServerId",stopServerId);
		paraMap.put("checkState",checkState);
		try {
			towerStopServerDao.updateCheckStateById(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}
	
}
