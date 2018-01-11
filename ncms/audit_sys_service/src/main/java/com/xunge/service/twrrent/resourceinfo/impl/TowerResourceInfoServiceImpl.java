package com.xunge.service.twrrent.resourceinfo.impl;

import java.util.ArrayList;
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
import com.xunge.comm.tower.resourceinfo.JudgeComm;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.page.Page;
import com.xunge.core.util.SessionUtils;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.dao.towerrent.mobile.ITwrRentInformationDao;
import com.xunge.dao.twrrent.resourceinfo.ITowerLinkDao;
import com.xunge.dao.twrrent.resourceinfo.ITowerResourceInfoDao;
import com.xunge.model.activity.Act;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.rentmanager.TowerResourceInfoVO;
import com.xunge.service.activity.impl.ActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.twrrent.resourceinfo.ITowerResourceInfoService;
import com.xunge.service.twrrent.resourceinfo.exceldatahandler.TowerResourceInfoHandler;
/**
 * 铁塔资源信息service实现类
 * @author yuefy
 *
 */
public class TowerResourceInfoServiceImpl implements ITowerResourceInfoService {
	//铁塔侧
	private ITowerResourceInfoDao towerResourceInfoDao;
	//移动侧
	private ITwrRentInformationDao twrRentInformationDao;
	//铁塔站址编码关联关系Dao
	private ITowerLinkDao towerLinkDao;
	
	private ActTaskService actTaskService;
	
	private ISysRegionDao sysRegionDao;
	

	public void setTowerResourceInfoDao(ITowerResourceInfoDao towerResourceInfoDao) {
		this.towerResourceInfoDao = towerResourceInfoDao;
	}
	public ITowerResourceInfoDao getTowerResourceInfoDao() {
		return towerResourceInfoDao;
	}
	public ITwrRentInformationDao getTwrRentInformationDao() {
		return twrRentInformationDao;
	}

	public void setTwrRentInformationDao(
			ITwrRentInformationDao twrRentInformationDao) {
		this.twrRentInformationDao = twrRentInformationDao; 
	}

	public ITowerLinkDao getTowerLinkDao() {
		return towerLinkDao;
	}

	public void setTowerLinkDao(ITowerLinkDao towerLinkDao) {
		this.towerLinkDao = towerLinkDao;
	}

	public ActTaskService getActTaskService() {
		return actTaskService;
	}

	public void setActTaskService(ActTaskService actTaskService) {
		this.actTaskService = actTaskService;
	}

	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}
	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}
	/**
	 * 审核完成后修改状态
	 */
	@Override
	public String updateCommit(String rentinformationtowerId,Integer checkState) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("rentinformationtowerId",rentinformationtowerId);
		paraMap.put("checkState",checkState);
		try {
			towerResourceInfoDao.updateCommit(paraMap);
			return RESULT.SUCCESS_1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED);
		}
	}
	
	/**
	 * 分页查询铁塔资源信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<TowerResourceInfoVO> queryTowerResourceInfo(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		Page<TowerResourceInfoVO> page = towerResourceInfoDao.queryTowerResourceInfo(paraMap, pageNumber, pageSize);
		if(paraMap.get("idsList")!=null){
			List<TowerResourceInfoVO> list=Lists.newArrayList();
			for(TowerResourceInfoVO t:page.getResult()){
				for(Act acts:(List<Act>)paraMap.get("idsList")){
					if(t.getRentinformationtowerId() !=null&& t.getRentinformationtowerId().equals(acts.getBusinessId())){
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
	 * 根据id查询铁塔资源信息
	 */
	@Override
	public TowerResourceInfoVO queryTowerResourceInfoVOById(
			String rentinformationtowerId) {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("rentinformationtowerId",rentinformationtowerId);
		return towerResourceInfoDao.queryTowerResourceInfoVOById(paraMap);
	}
	
	
	/**
	 * 查询所有铁塔信息
	 * @return
	 */
	@Override
	public List<TowerResourceInfoVO> queryTowerResourceInfo( Map<String, Object> paraMap) {
		return towerResourceInfoDao.queryTowerResourceInfo(paraMap);
	}
	
	/**
	 * 新增铁塔数据 判断在不在关联关系表中
	 * yuefy
	 */
	public void insertData(List<String> linkList,List<TowerResourceInfoVO> list,Map<String, Object> paraMap){
		for(int i = 0 ; i < list.size() ; i++ ){
			//关联关系表中的铁塔站址编码包含导入数据站址编码  新增走审核流程
			if(linkList.contains(list.get(i).getTowerStationCode())){
				String rentinformationtowerId = SysUUID.generator();
				list.get(i).setRentinformationtowerId(rentinformationtowerId);
				list.get(i).setIfTowerLinkOperator(JudgeComm.STATE_YES);
				towerResourceInfoDao.insertTowerResourceInfo(list.get(i));
				actTaskService.startProcess(ActUtils.PD_RESOURCE_INFO[0], ActUtils.PD_RESOURCE_INFO[1], rentinformationtowerId,ActUtils.PD_RESOURCE_INFO[2],null,paraMap.get("userLoginname")+"");
			}else{
				String rentinformationtowerId = SysUUID.generator();
				list.get(i).setRentinformationtowerId(rentinformationtowerId);
				list.get(i).setIfTowerLinkOperator(JudgeComm.STATE_NO);
				towerResourceInfoDao.insertTowerResourceInfo(list.get(i));
			}
		}
	}
	
	/**
	 * 导入
	 * @param file
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> importTowerResourceInfo(MultipartFile file,String suffix,
			HttpServletRequest request, Map<String, Object> paraMap) throws Exception{
		//_TowerResourceInfo这个是标识Session Name的，每个功能建议唯一
		SessionUtils.setProcessSession(suffix,DateDisposeComm.BEGIN_ANALAYSIS_FILE,5, request);
		//导入参数
		ImportParams iparams=new ImportParams();
		//设置自定义数据处理
		paraMap.put("state",StateComm.STATE_0);
		List<SysRegionVO> listreg=sysRegionDao.getAddress(paraMap);//准备需要的数据处理
		TowerResourceInfoHandler tbh=new TowerResourceInfoHandler(listreg);//塔维批量起租单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.REGION,DateDisposeComm.SHARE_INFO});//需要处理数据的列名 
		iparams.setDataHanlder(tbh);
		//导入的数据集合
		List<TowerResourceInfoVO> excelImportList = ExcelImportUtil.importExcel(file.getInputStream(),TowerResourceInfoVO.class, iparams);
		//铁塔侧关联信息表 铁塔站址编码集合
		List<String> towerLinkTowerStationCodeList = towerLinkDao.queryAllTowerLink(paraMap);
		//数据库已有数据
		List<TowerResourceInfoVO> queryAllTowerResourceInfo = towerResourceInfoDao.queryTowerResourceInfo(paraMap);
		//未导入数据
		List<TowerResourceInfoVO>  listExist = new ArrayList<TowerResourceInfoVO>();
		//未在铁塔侧关联数据
		List<TowerResourceInfoVO>  listUnLink = new ArrayList<TowerResourceInfoVO>();
		//导入成功的条数
		int count=StateComm.STATE_0;
		int sumPercent=SelfelecComm.NUMBER_20;//方便每次计算
		int percent = (int) Math.ceil(90 / (double) excelImportList.size());//计算每批占的百分比保存到数据库占80%的进度
		if(excelImportList != null && excelImportList.size() > 0){
			for(int i = 0 ; i < excelImportList.size() ; i++ ){
				boolean flag = true;
				//关联关系表中的铁塔站址编码包含导入数据站址编码  新增走审核流程
				if(towerLinkTowerStationCodeList.contains(excelImportList.get(i).getTowerStationCode())){
					String rentinformationtowerId = SysUUID.generator();
					excelImportList.get(i).setRentinformationtowerId(rentinformationtowerId);
					excelImportList.get(i).setIfTowerLinkOperator(JudgeComm.STATE_YES);
					excelImportList.get(i).setAuditState(ActivityStateComm.STATE_AUDIT);
					if(queryAllTowerResourceInfo != null && queryAllTowerResourceInfo.size() > 0 ){
						for(int j = 0 ; j < queryAllTowerResourceInfo.size() ; j++){
							if(queryAllTowerResourceInfo.get(j).getTowerStationCode().equals(excelImportList.get(i).getTowerStationCode())
									&& queryAllTowerResourceInfo.get(j).getBusinessConfirmNumber().equals(excelImportList.get(i).getBusinessConfirmNumber())){
								flag = false;
								listExist.add(excelImportList.get(i));
								break;
							}
						}
						if(flag){
							towerResourceInfoDao.insertTowerResourceInfo(excelImportList.get(i));
							count++;
							sumPercent+=percent;
							sumPercent=(sumPercent>100?100:sumPercent);//如果大于100，按100计算
							SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
							actTaskService.startProcess(ActUtils.PD_RESOURCE_INFO[0], ActUtils.PD_RESOURCE_INFO[1], rentinformationtowerId,ActUtils.PD_RESOURCE_INFO[2],null,(String)paraMap.get("userLoginname"));
						}
					}else{
						towerResourceInfoDao.insertTowerResourceInfo(excelImportList.get(i));
						count++;
						sumPercent+=percent;
						sumPercent=(sumPercent>100?100:sumPercent);//如果大于100，按100计算
						SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
						actTaskService.startProcess(ActUtils.PD_RESOURCE_INFO[0], ActUtils.PD_RESOURCE_INFO[1], rentinformationtowerId,ActUtils.PD_RESOURCE_INFO[2],null,(String)paraMap.get("userLoginname"));
					}
				}else{
					listUnLink.add(excelImportList.get(i));
					String rentinformationtowerId = SysUUID.generator();
					excelImportList.get(i).setRentinformationtowerId(rentinformationtowerId);
					excelImportList.get(i).setIfTowerLinkOperator(JudgeComm.STATE_NO);
					excelImportList.get(i).setAuditState(ActivityStateComm.STATE_UNCOMMITTED);
					if(queryAllTowerResourceInfo != null && queryAllTowerResourceInfo.size() > 0 ){
						for(int j = 0 ; j < queryAllTowerResourceInfo.size() ; j++){
							if(queryAllTowerResourceInfo.get(j).getTowerStationCode().equals(excelImportList.get(i).getTowerStationCode())
									&& queryAllTowerResourceInfo.get(j).getBusinessConfirmNumber().equals(excelImportList.get(i).getBusinessConfirmNumber())){
								listExist.add(excelImportList.get(i));
								flag = false;
								break;
							}
						}
						if(flag){
							towerResourceInfoDao.insertTowerResourceInfo(excelImportList.get(i));
							count++;
							sumPercent+=percent;
							sumPercent=(sumPercent>SelfelecComm.NUMBER_100?SelfelecComm.NUMBER_100:sumPercent);//如果大于100，按100计算
							SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
						}
					}else{
						towerResourceInfoDao.insertTowerResourceInfo(excelImportList.get(i));
						count++;
						sumPercent+=percent;
						sumPercent=(sumPercent>SelfelecComm.NUMBER_100?SelfelecComm.NUMBER_100:sumPercent);//如果大于100，按100计算
						SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
					}
				}
			}
		}
		SessionUtils.setProcessSession(suffix,PromptMessageComm.IMPORT_SUCCESS+PromptMessageComm.COMMA_SYMBOL+PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS,100,request);
		Map<String,Object> returnMap=Maps.newHashMap();
		returnMap.put("errMsg", (listExist.size()>0?PromptMessageComm.ALREADY_EXIST_NUMBER+listExist.size()+PromptMessageComm.STRIP:"")+(listUnLink.size()>0?PromptMessageComm.NO_CORRELATE_TOWER+listUnLink.size()+PromptMessageComm.STRIP:""));
		returnMap.put("msg",PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS);
		returnMap.put("isExist", listExist);
		returnMap.put("successCount", count);
    	return returnMap;
	}
	
	

}
