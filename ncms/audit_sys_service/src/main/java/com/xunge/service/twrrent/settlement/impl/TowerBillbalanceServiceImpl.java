package com.xunge.service.twrrent.settlement.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xunge.comm.StateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.SessionUtils;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.dao.twrrent.settlement.ITowerBillbalanceDao;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.settlement.TowerBillbalanceVO;
import com.xunge.service.twrrent.settlement.ITowerBillbalanceService;
import com.xunge.service.twrrent.settlement.exceldatahandler.TowerBillbalanceHandler;

/**
 * @author zhujj
 * @date 2017年7月6日 下午3:12:38 
 * @version 1.0.0 
 */
public class TowerBillbalanceServiceImpl implements ITowerBillbalanceService{
	
	private ITowerBillbalanceDao towerBillbalanceDao;
	private ISysRegionDao sysRegionDao;
	
	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}

	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}

	public ITowerBillbalanceDao getTowerBillbalanceDao() {
		return towerBillbalanceDao;
	}

	public void setTowerBillbalanceDao(ITowerBillbalanceDao towerBillbalanceDao) {
		this.towerBillbalanceDao = towerBillbalanceDao;
	}

	@Override
	public int deleteByPrimaryKey(String towerbillbalanceId) {
		// TODO Auto-generated method stub
		return towerBillbalanceDao.deleteByPrimaryKey(towerbillbalanceId);
	}

	@Override
	public int insertTowerBillbalance(TowerBillbalanceVO entity) {
		// TODO Auto-generated method stub
		return towerBillbalanceDao.insertTowerBillbalance(entity);
	}

	@Override
	public int insertSelective(TowerBillbalanceVO entity) {
		// TODO Auto-generated method stub
		return towerBillbalanceDao.insertSelective(entity);
	}

    public int insertBatchSelective(List<TowerBillbalanceVO> list){
		// TODO Auto-generated method stub
		return towerBillbalanceDao.insertBatchSelective(list);
	}
	@Override
	public TowerBillbalanceVO selectByPrimaryKey(String towerbillbalanceId) {
		// TODO Auto-generated method stub
		return towerBillbalanceDao.selectByPrimaryKey(towerbillbalanceId);
	}

    /**
     * 批量更新不为空的数据
     * @param list 租赁账单集合
     * @return
     */
    public int updateBatchByPrimaryKeySelective(List<TowerBillbalanceVO> list) {
		// TODO Auto-generated method stub
		return towerBillbalanceDao.updateBatchByPrimaryKeySelective(list);
	}
	@Override
	public int updateByPrimaryKeySelective(TowerBillbalanceVO entity) {
		// TODO Auto-generated method stub
		return towerBillbalanceDao.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int updateByPrimaryKey(TowerBillbalanceVO entity) {
		// TODO Auto-generated method stub
		return towerBillbalanceDao.updateByPrimaryKey(entity);
	}

	@Override
	public Page<TowerBillbalanceVO> queryPageTowerBillbalance(Map<String, Object> paramMap) {
		List<SysRegionVO> listreg=sysRegionDao.getAddress(paramMap);//准备需要的数据处理
		List<TowerBillbalanceVO> list=Lists.newArrayList();
		Page<TowerBillbalanceVO> page=towerBillbalanceDao.queryPageTowerBillbalance(paramMap);
		for(TowerBillbalanceVO tbb:page.getResult()){
			for(SysRegionVO sreg:listreg){
				if(sreg.getRegId().equals(tbb.getOperatorRegId())){
					tbb.setOperatorSysRegion(sreg);
				}
			}
			list.add(tbb);
		}
		page.setResult(list);
		return page;
	}

	public List<TowerBillbalanceVO> queryTowerBillbalance(Map<String,Object> paramMap){

		return towerBillbalanceDao.queryTowerBillbalance(paramMap);
	}
	
	
	public Map<String,Object> insertExcelData(MultipartFile file,String suffix,HttpServletRequest request) throws Exception{
		//_TowerBillbalance这个是标识Session Name的，每个功能建议唯一
		SessionUtils.setProcessSession(suffix,DateDisposeComm.BEGIN_ANALAYSIS_FILE,5, request);
		
		//导入参数
		ImportParams iparams=new ImportParams();
		
		//设置自定义数据处理
		Map<String,Object> map=Maps.newHashMap();
		map.put("state",StateComm.STATE_str0);

		//获取当前用户所属地区
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginInfo == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		map.put("prvId",loginInfo.getPrv_id());
		List<SysRegionVO> listreg=sysRegionDao.getAddress(map);//准备需要的数据处理
		TowerBillbalanceHandler tbh=new TowerBillbalanceHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{PromptMessageComm.CARRIERS_REGION});//需要处理数据的列名 
		iparams.setDataHanlder(tbh);
		List<TowerBillbalanceVO> listAll=ExcelImportUtil.importExcel(file.getInputStream(),TowerBillbalanceVO.class, iparams);

		/**
		 * 验证数据在数据库里面是否存在
		 */
		Map<String,Object> paramMap=Maps.newHashMap();
		paramMap.put("regIds", loginInfo.getReg_ids());
		List<TowerBillbalanceVO> listt=towerBillbalanceDao.queryTowerBillbalance(paramMap);
		List<TowerBillbalanceVO> listExist=Lists.newArrayList();
		List<TowerBillbalanceVO> list=Lists.newArrayList();
		for (int index=0;index<listAll.size();index++) {
			TowerBillbalanceVO tb=listAll.get(index);
			TowerBillbalanceVO tbTemp=new TowerBillbalanceVO();
			tbTemp.setBusinessConfirmNumber(tb.getBusinessConfirmNumber());
			tbTemp.setTowerStationCode(tb.getTowerStationCode());
			if(listt.contains(tb)){//如果这条数据已经存在
				listExist.add(tb);//保存已经存在数据
			}else{
				list.add(tb);//保存不存在数据
			}
			tb.setDataType(StateComm.STATE_str1);
			if(tb.verifyData()){//验证数据不通过
				
			}
		}
		SessionUtils.setProcessSession(suffix,DateDisposeComm.ANALAYSIS_FILE_DONE,SelfelecComm.NUMBER_10, request);
		//大批量数据分批插入，每次插入500条
		int a = 500;//每次提交2000条
		int loop = (int) Math.ceil(list.size() / (double) a);//批次
		int count=list.size();
		int percent = (int) Math.ceil(90 / (double) loop);//计算每批占的百分比保存到数据库占80%的进度
		int sumPercent=20;//方便每次计算
		for (int i = 0; i < loop; i++) {
			 int stop = Math.min(a - 1, list.size());//判断结束的序号

			 towerBillbalanceDao.insertBatchSelective(list.subList(StateComm.STATE_0, stop));
			 list.subList(StateComm.STATE_0, stop).clear();//清除已经插入的数据
			 sumPercent+=percent;
			 sumPercent=(sumPercent>100?100:sumPercent);//如果大于100，按100计算
			 SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
		}
		SessionUtils.setProcessSession(suffix,PromptMessageComm.IMPORT_SUCCESS+PromptMessageComm.COMMA_SYMBOL+PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS,100,request);
		
		
		Map<String,Object> returnMap=Maps.newHashMap();
		returnMap.put("errMsg", listExist.size()>0?PromptMessageComm.ALREADY_EXIST_NUMBER+listExist.size()+PromptMessageComm.STRIP : "");
		returnMap.put("msg",PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS);
		returnMap.put("isExist", listExist);
		returnMap.put("successCount", count);
    	return returnMap;
	}
	@Override
	public Page<TowerBillbalanceVO> queryPageMobileBillbalance(Map<String, Object> paramMap) {
		Map<String,Object> map=Maps.newHashMap();
		map.put("state",StateComm.STATE_str0);
		map.put("userId",paramMap.get("userId").toString());
		List<SysRegionVO> listreg=sysRegionDao.getAddress(map);//准备需要的数据处理
		List<TowerBillbalanceVO> list=Lists.newArrayList();
		Page<TowerBillbalanceVO> page=towerBillbalanceDao.queryPageMobileBillbalance(paramMap);
		for(TowerBillbalanceVO tbb:page.getResult()){
			for(SysRegionVO sreg:listreg){
				if(sreg.getRegId().equals(tbb.getOperatorRegId())){
					tbb.setOperatorSysRegion(sreg);
				}
			}
			list.add(tbb);
		}
		page.setResult(list);
		return page;
	}

	@Override
	public List<TowerBillbalanceVO> queryTowerOrMobileBillbalance(Map<String, Object> paramMap) {
		return towerBillbalanceDao.queryTowerOrMobileBillbalance(paramMap);
	}

	@Override
	public int insertBatchMobileBill(List<TowerBillbalanceVO> listVo) {
		return towerBillbalanceDao.insertBatchMobileBill(listVo);
	}

	public Map<String, Object> updateExcelData(MultipartFile file,String suffix,HttpServletRequest request) throws Exception{
		//_TowerBillbalance这个是标识Session Name的，每个功能建议唯一
				SessionUtils.setProcessSession(suffix,DateDisposeComm.BEGIN_ANALAYSIS_FILE,5, request);
				
				//导入参数
				ImportParams iparams=new ImportParams();
				//设置自定义数据处理
				Map<String,Object> map=Maps.newHashMap();
				map.put("state",StateComm.STATE_str0);
				List<SysRegionVO> listreg=sysRegionDao.getAddress(map);//准备需要的数据处理
				TowerBillbalanceHandler tbh=new TowerBillbalanceHandler(listreg);//塔维租赁账单自定义数据处理
				tbh.setNeedHandlerFields(new String[]{PromptMessageComm.CARRIERS_REGION});//需要处理数据的列名 
				iparams.setDataHanlder(tbh);
				List<TowerBillbalanceVO> list=ExcelImportUtil.importExcel(file.getInputStream(),TowerBillbalanceVO.class, iparams);

				/**
				 * 验证数据在数据库里面是否存在
				 */
				Map<String,Object> paramMap=Maps.newHashMap();
				List<TowerBillbalanceVO> listt=towerBillbalanceDao.queryTowerBillbalance(paramMap);
				List<TowerBillbalanceVO> listUpdate=Lists.newArrayList();
				List<TowerBillbalanceVO> listInsert=Lists.newArrayList();
				UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
				if(loginInfo == null){
					throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
				}
				for (TowerBillbalanceVO tb:list) {
					tb.setUpdateTime(new Date());
					tb.setUpdateUserId(loginInfo.getUser_id());
					if(listt.contains(tb)){//如果这条数据已经存在
						listUpdate.add(tb);//更新已经存在数据
					}else{
						listInsert.add(tb);//保存不存在的数据
					}
				}
				SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,SelfelecComm.NUMBER_10, request);
				
				//大批量数据分批插入，每次插入500条
				int a = 500;//每次提交2000条
				int loop = (int) Math.ceil((listInsert.size()+listUpdate.size()) / (double) a);//计算批次
				int count=list.size();
				int percent = (int) Math.ceil(90 / (double) loop);//计算每批占的百分比保存到数据库占80%的进度
				int sumPercent=20;//方便每次计算
				//不存在的数据执行Insert
				int loopInsert = (int) Math.ceil(listInsert.size() / (double) a);//批次
				for (int i = 0; i < loopInsert; i++) {
					 int stop = Math.min(a - 1, listInsert.size());//判断结束的序号

					 towerBillbalanceDao.insertBatchSelective(listInsert.subList(StateComm.STATE_0, stop));
					 listInsert.subList(StateComm.STATE_0, stop).clear();//清除已经插入的数据
					 sumPercent+=percent;
					 sumPercent=(sumPercent>100?100:sumPercent);//如果大于100，按100计算
					 SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
				}
				
				//已经存在的数据执行更新操作
				int loopUpdate = (int) Math.ceil(listUpdate.size() / (double) a);//批次
				for (int i = 0; i < loopUpdate; i++) {
					 int stop = Math.min(a - 1, listUpdate.size());//判断结束的序号  -1

					 towerBillbalanceDao.updateBatchByPrimaryKeySelective(listUpdate.subList(0, stop));
					 listUpdate.subList(StateComm.STATE_0, stop).clear();//清除已经插入的数据
					 sumPercent+=percent;
					 sumPercent=(sumPercent>SelfelecComm.NUMBER_100?SelfelecComm.NUMBER_100:sumPercent);//如果大于100，按100计算
					 SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
				}
				SessionUtils.setProcessSession(suffix,PromptMessageComm.IMPORT_SUCCESS+PromptMessageComm.COMMA_SYMBOL+PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS,100,request);
				
				
				Map<String,Object> returnMap=Maps.newHashMap();
				returnMap.put("errMsg", PromptMessageComm.NO_BEING+listUpdate.size()+PromptMessageComm.INSERTED);
				returnMap.put("msg",PromptMessageComm.UPDATE_INFO+count+PromptMessageComm.DATAS);
				returnMap.put("listUpdate", listUpdate);
				returnMap.put("listInsert", listInsert);
				returnMap.put("successCount", count);
		    	return returnMap;
	}

	@Override
	public int updateBatchMobileBill(List<TowerBillbalanceVO> listVo) {
		return towerBillbalanceDao.updateBatchMobileBill(listVo);
	}

	@Override
	public List<TowerBillbalanceVO> queryMobileBill(Map<String, Object> paramMap) {
		return towerBillbalanceDao.queryMobileBill(paramMap);
	}

	@Override
	public void export(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
//		String strArr=map.get("towerbillbalanceIds").toString();
//		if(StrUtil.isNotBlank(strArr)){
//			String[] arr=strArr.split(",");
//			map.put("towerbillbalanceIds",arr);
//		}
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		//获取当前用户所属地区,权限控制
		map.put("state",StateComm.STATE_0);
		map.put("userId",loginUser.getUser_id());
		map.put("regIds",loginUser.getReg_ids());
		List<TowerBillbalanceVO> list = towerBillbalanceDao.queryTowerBillbalance(map);
		List<SysRegionVO> listreg=sysRegionDao.getAddress(map);//准备需要的数据处理
		TowerBillbalanceHandler tbh=new TowerBillbalanceHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{PromptMessageComm.CARRIERS_CTIY,PromptMessageComm.NEED_CITY,PromptMessageComm.SITE_CITY});//需要处理数据的列名 
		ExportParams params = new ExportParams(PromptMessageComm.TOWER_BILLS, PromptMessageComm.TOWER_BILLS, ExcelType.HSSF);
//		params.setExclusions(new String[]{"不需要的"});过滤属性
		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TowerBillbalanceVO.class,list);
        FileUtils.downFile(workBook, PromptMessageComm.TOWER_BILLS+DateDisposeComm.SUFF_XLS, request, response);
	}

	@Override
	public List<TowerBillbalanceVO> queryParameter(String towerStationCode) {
		Map<String,Object> paraMap = new HashMap<String, Object>();
		paraMap.put("towerStationCode",towerStationCode);
		return towerBillbalanceDao.queryParameter(paraMap);
	}
	
	@Override
	public void exportMobile(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {
		if(map.containsKey("towerbillbalanceIds")){
			String strArr=map.get("towerbillbalanceIds").toString();
			String[] arr=strArr.split(",");
			map.put("towerbillbalanceIds",arr);
		}
		
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		//获取当前用户所属地区,权限控制
		map.put("alias", PromptMessageComm.ALIAS_NAME_REG);
		map.put("regIds",loginUser.getReg_ids());
		map.put("dataType", SelfelecComm.NUMBER_2);
		List<TowerBillbalanceVO> list = towerBillbalanceDao.queryTowerOrMobileBillbalance(map);
		List<SysRegionVO> listreg=sysRegionDao.getAddress(map);//准备需要的数据处理
		TowerBillbalanceHandler tbh=new TowerBillbalanceHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{PromptMessageComm.CARRIERS_CTIY,PromptMessageComm.NEED_CITY,PromptMessageComm.SITE_CITY});//需要处理数据的列名 
		ExportParams params = new ExportParams(PromptMessageComm.MOBILE_BILLS, PromptMessageComm.MOBILE_BILLS, ExcelType.HSSF);
//		params.setExclusions(new String[]{"不需要的"});过滤属性
		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TowerBillbalanceVO.class,list);
        FileUtils.downFile(workBook, PromptMessageComm.MOBILE_BILLS+DateDisposeComm.SUFF_XLS, request, response);
	}
}
