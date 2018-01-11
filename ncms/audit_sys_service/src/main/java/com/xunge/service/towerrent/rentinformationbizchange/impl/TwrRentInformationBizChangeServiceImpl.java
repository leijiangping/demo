package com.xunge.service.towerrent.rentinformationbizchange.impl;

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
import com.xunge.dao.towerrent.rentinformationbizchange.ITwrRentInformationBizChangeDao;
import com.xunge.model.activity.Act;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.bizchange.TowerRentinformationBizchangeVO;
import com.xunge.model.towerrent.mobile.TwrRentInformationVO;
import com.xunge.service.activity.impl.ActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.towerrent.rentinformationbizchange.ITwrRentInformationBizChangeService;
import com.xunge.service.twrrent.rentinformationbizchange.exceldatahandler.TowerChangeInfoHandler;

public class TwrRentInformationBizChangeServiceImpl implements ITwrRentInformationBizChangeService {
	
	//铁塔资源信息变更表dao
	private ITwrRentInformationBizChangeDao twrRentInformationBizChangeDao;
	
	private ISysRegionDao sysRegionDao;
	
	private ITwrRentInformationDao twrRentInformationDao;
	
	private ActTaskService actTaskService;

	@SuppressWarnings("unchecked")
	@Override
	public Page<TowerRentinformationBizchangeVO> queryInformationBizChangeCheckInfo(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		Page<TowerRentinformationBizchangeVO> page = twrRentInformationBizChangeDao.queryTowerRentinformationBizchangeInfo(paraMap, pageNumber, pageSize);
		if(paraMap.get("idsList")!=null){
			List<TowerRentinformationBizchangeVO> list=Lists.newArrayList();
			for(TowerRentinformationBizchangeVO t:page.getResult()){
				for(Act acts:(List<Act>)paraMap.get("idsList")){
						if(t.getTwrRentinformationBizchangeId() !=null && t.getTwrRentinformationBizchangeId().equals(acts.getBusinessId())){
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
	 * 查询所有铁塔信息变更表信息
	 * @param paraMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @author yuefy
	 */
	@Override
	public List<TowerRentinformationBizchangeVO> queryTowerRentinformationBizchangeInfo(
			Map<String, Object> paraMap) {
		return twrRentInformationBizChangeDao.queryTowerRentinformationBizchangeInfo(paraMap);
	}

	@Override
	public String updateBizChangeCheckState(
			String twrRentinformationBizchangeId, int checkState) {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("twrRentinformationBizchangeId",twrRentinformationBizchangeId);
		paraMap.put("checkState",checkState);
		try {
			twrRentInformationBizChangeDao.updateBizChangeCheckState(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}
	
	/**
	 * 导入铁塔变更信息
	 * @param file
	 * @param request
	 * @param paraMap
	 * @return
	 * @author yuefy
	 * @throws Exception 
	 * @throws IOException 
	 */
	@Override
	public Map<String, Object> importTowerChangeInfo(MultipartFile file,String suffix,
			HttpServletRequest request, Map<String, Object> paraMap) throws  Exception {
		//_TowerBillbalance这个是标识Session Name的，每个功能建议唯一
		SessionUtils.setProcessSession(suffix,DateDisposeComm.BEGIN_ANALAYSIS_FILE,SelfelecComm.NUMBER_5, request);
		
		//导入参数
		ImportParams iparams=new ImportParams();
		//设置自定义数据处理
		paraMap.put("state",StateComm.STATE_0);
		List<SysRegionVO> listreg=sysRegionDao.getAddress(paraMap);//准备需要的数据处理
		TowerChangeInfoHandler tbh=new TowerChangeInfoHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.REGION});//需要处理数据的列名 
		iparams.setDataHanlder(tbh);
		List<TowerRentinformationBizchangeVO> list = ExcelImportUtil.importExcel(file.getInputStream(),TowerRentinformationBizchangeVO.class, iparams);

		/**
		 * 验证数据在数据库里面是否存在
		 */
		//数据库中相同的数据
		List<TowerRentinformationBizchangeVO> listInexistence = Lists.newArrayList();
		//数据库中没有记录的数据
		List<TowerRentinformationBizchangeVO> listExist = Lists.newArrayList();
		//添加的数据
		List<TowerRentinformationBizchangeVO> listInsert = Lists.newArrayList();
		if(list != null && list.size() > 0){
			for (int index=0;index<list.size();index++) {
				TowerRentinformationBizchangeVO change = list.get(index);
				change.setTwrRentinformationBizchangeId(SysUUID.generator());
				change.setAuditState(ActivityStateComm.STATE_AUDIT);
				//业务确认单号
				paraMap.put("businessConfirmNumber", change.getBusinessConfirmNumber());
				//铁塔站址编码
				paraMap.put("towerStationCode", change.getTowerStationCode());
				//生效日期
				paraMap.put("activeDate", change.getChangeActiveDate());
				//移动侧是否有此条信息
				TwrRentInformationVO tri = twrRentInformationDao.queryTwrRentInformationByBusinessNumDate(paraMap);
				if(tri == null){
					listExist.add(change);//保存不存在数据
				}else{
					paraMap.put("change_item", change.getChangeItem());
					paraMap.put("changeBeforeContent", change.getChangeBeforeContent());
					paraMap.put("changeBehindContent", change.getChangeBehindContent());
					paraMap.put("changeActiveDate", change.getChangeActiveDate());
					TowerRentinformationBizchangeVO changeVO = twrRentInformationBizChangeDao.queryBizChangeById(paraMap);
					if(changeVO != null){
						listInexistence.add(change);
					}else{
						listInsert.add(change);
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
				actTaskService.startProcess(ActUtils.PD_BIZCHANGE_INFO[0], ActUtils.PD_BIZCHANGE_INFO[1], listInsert.get(i).getTwrRentinformationBizchangeId(),ActUtils.PD_BIZCHANGE_INFO[2],null,(String)paraMap.get("userLoginname"));
			}
		}
		for (int i = 0; i < loop; i++) {
			 int stop = Math.min(a - 1, listInsert.size());//判断结束的序号
			 twrRentInformationBizChangeDao.insertBatchSelective(listInsert.subList(0, stop));
			 listInsert.subList(0, stop).clear();//清除已经插入的数据
			 sumPercent+=percent;
			 sumPercent=(sumPercent>100?100:sumPercent);//如果大于100，按100计算
			 SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
		}
		SessionUtils.setProcessSession(suffix,PromptMessageComm.IMPORT_SUCCESS+PromptMessageComm.COMMA_SYMBOL+PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS,SelfelecComm.NUMBER_100,request);
		Map<String,Object> returnMap=Maps.newHashMap();
		returnMap.put("errMsg", (listExist.size()>0?PromptMessageComm.NO_INFO_NO_SERVICE+listExist.size()+PromptMessageComm.STRIP:"")+(listInexistence.size()>0?PromptMessageComm.ALREADY_EXIST_NUMBER+listInexistence.size()+PromptMessageComm.STRIP:""));
		returnMap.put("msg",PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS);
		returnMap.put("isExist", listExist);
		returnMap.put("successCount", count);
    	return returnMap;
	}
	
	@Override
	public TowerRentinformationBizchangeVO queryBizChangeById(
			Map<String, Object> paraMap) {
		return twrRentInformationBizChangeDao.queryBizChangeById(paraMap);
	}

	
	//get set方法
	public ITwrRentInformationBizChangeDao getTwrRentInformationBizChangeDao() {
		return twrRentInformationBizChangeDao;
	}

	public void setTwrRentInformationBizChangeDao(
			ITwrRentInformationBizChangeDao twrRentInformationBizChangeDao) {
		this.twrRentInformationBizChangeDao = twrRentInformationBizChangeDao;
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


}
