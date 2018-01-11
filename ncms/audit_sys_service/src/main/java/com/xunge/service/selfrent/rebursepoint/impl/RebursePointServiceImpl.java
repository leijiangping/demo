package com.xunge.service.selfrent.rebursepoint.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xunge.comm.StateComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.SessionUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.selfrent.billaccount.IRentPaymentDao;
import com.xunge.dao.selfrent.contract.ISelfRentDao;
import com.xunge.dao.selfrent.rebursepoint.IRebursePointDao;
import com.xunge.dao.selfrent.rebursepoint.IRentBillAccountContractDao;
import com.xunge.dao.selfrent.rebursepoint.IRentBillAccountResourceDao;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.model.activity.Act;
import com.xunge.model.selfrent.billAccount.RentBillAccountResourceVO;
import com.xunge.model.selfrent.contract.RentContractVO;
import com.xunge.model.selfrent.rebursepoint.RentBillAccountContractVO;
import com.xunge.model.selfrent.rebursepoint.RentBillaccountVO;
import com.xunge.model.selfrent.resource.DatBaseResourceVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.selfrent.rebursepoint.IRebursePointService;
import com.xunge.service.twrrent.punish.exceldatahandler.HistroryPayModelHandler;
/**
 * 
 * @author lpw
 *
 */
public class RebursePointServiceImpl implements IRebursePointService{
	
	private IRebursePointDao rebursePointDao;
	private IRentBillAccountContractDao rentBillAccountContractDao;
	private IRentBillAccountResourceDao rentBillAccountResourceDao;
	private ISelfRentDao selfRentDao;  
	private IRentPaymentDao rentPayMentDao;

	@Autowired
	private ISysRegionDao sysRegionDao;
	@Autowired
	private IActTaskService actTaskService;
	@Override
	public Page<List<RentBillaccountVO>> queryRembursePointInfo(
			Map<String, Object> pMap, int pageNumber, int pageSize){
		
		return rebursePointDao.queryRembursePointInfo(pMap, pageNumber, pageSize);
	}
	@Override
	public Page<List<RentContractVO>> queryContractAgreement(
			Map<String, Object> pMap, int pageNumber, int pageSize) {
		
		return rebursePointDao.queryContractAgreement(pMap, pageNumber, pageSize);
	}
	@Override
	public Page<List<DatBaseResourceVO>> queryResourceInfo(Map<String, Object> pMap, int pageNumber,
			int pageSize) {
		return rebursePointDao.queryResourceInfo(pMap, pageNumber, pageSize);
	}
	@Override
	public int insertBillAcount(RentBillaccountVO rentBillaccount,
			RentBillAccountContractVO rentBillAccountContract,
			List<RentBillAccountResourceVO> rentResourceList) {
		try{
			rebursePointDao.insertBillAcount(rentBillaccount);
			if(null!=rentBillAccountContract){
				rentBillAccountContractDao.insertBillAccountContract(rentBillAccountContract);
			}
			if(null!=rentResourceList&&!rentResourceList.isEmpty()){
				rentBillAccountResourceDao.insertBillAccountResource(rentResourceList);
			}
			return 1;
		}catch(Exception e){
			throw new BusinessException(PromptMessageComm.OPERATION_INSERT_FAILED+e);
		}
	}
	@Override
	public void updateBillAcount(RentBillaccountVO billaccount,
			String rentcontractId, String baseresourceIds) {
		try {
			if(StringUtils.isNotEmpty(rentcontractId)){
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("rentcontractId", rentcontractId);
				paraMap.put("billaccountId", billaccount.getBillAccountId());
				int i= rentBillAccountContractDao.updateBillAccountContract(paraMap);
				if(0 == i){
					RentBillAccountContractVO rentBillAccountContract = new RentBillAccountContractVO();
					rentBillAccountContract.setBillAccountContractId(SysUUID.generator());
					rentBillAccountContract.setBillAccountId(billaccount.getBillAccountId());
					rentBillAccountContract.setRentContractId(rentcontractId);
					rentBillAccountContract.setRelationState(StateComm.STATE_0);
					rentBillAccountContract.setRelationStartdate(new Date());
					rentBillAccountContractDao.insertBillAccountContract(rentBillAccountContract);
				}
			}
			List<RentBillAccountResourceVO> resourceList = new ArrayList<RentBillAccountResourceVO>();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if(StringUtils.isNotEmpty(baseresourceIds)){
				for (String resourceId : baseresourceIds.split(",")) {
					RentBillAccountResourceVO rentBillAccountResource = new RentBillAccountResourceVO();
					String billAccountResourceId = SysUUID.generator();
					rentBillAccountResource.setBaseResourceId(resourceId);
					rentBillAccountResource.setBillAccountId(billaccount.getBillAccountId());
					rentBillAccountResource.setBillAccountResourceId(billAccountResourceId);
					resourceList.add(rentBillAccountResource);
				}
				paramMap.put("resourceList", resourceList);
				rentBillAccountResourceDao.insertBillAccountResource(resourceList);
			}
			rebursePointDao.updateBillAcount(billaccount);
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_FAILED+e);
		}
	}
	@Override
	public int deleteBillAcount(String billAccountId) {
		try {
			rentBillAccountContractDao.deleteBillAccountContract(billAccountId);
			rentBillAccountResourceDao.deleteBillAccountResource(billAccountId);
			rebursePointDao.deleteBillAcount(billAccountId);
			return 1;
		} catch (Exception e) {
			throw new BusinessException(PromptMessageComm.OPERATION_DELETE_FAILED+e);
		}
	}
	@Override
	public RentContractVO queryContractByContractId(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		
		return selfRentDao.queryContractByContractId(paraMap,pageNumber, pageSize);
	}
	@Override
	public List<DatBaseResourceVO> queryContractByResourceId(
			Map<String, Object> paraMap) {
		
		return rebursePointDao.queryContractByResourceId(paraMap);
	}
	@Override
	public RentContractVO queryContractById(Map<String, Object> paraMap) {
		return rebursePointDao.queryContractById(paraMap);
	}
	@Override
	public List<DatBaseResourceVO> queryResource(Map<String, Object> pMap) {
		return rebursePointDao.queryResource(pMap);
	}
	@Override
	public RentBillaccountVO queryBillAccountById(Map<String, Object> paraMap) {
		return rebursePointDao.queryBillAccountById(paraMap);
	}
	public IRebursePointDao getRebursePointDao() {
		return rebursePointDao;
	}
	public void setRebursePointDao(IRebursePointDao rebursePointDao) {
		this.rebursePointDao = rebursePointDao;
	}
	public IRentBillAccountContractDao getRentBillAccountContractDao() {
		return rentBillAccountContractDao;
	}
	public void setRentBillAccountContractDao(
			IRentBillAccountContractDao rentBillAccountContractDao) {
		this.rentBillAccountContractDao = rentBillAccountContractDao;
	}
	public IRentBillAccountResourceDao getRentBillAccountResourceDao() {
		return rentBillAccountResourceDao;
	}
	public void setRentBillAccountResourceDao(
			IRentBillAccountResourceDao rentBillAccountResourceDao) {
		this.rentBillAccountResourceDao = rentBillAccountResourceDao;
	}
	
	public ISelfRentDao getSelfRentDao() {
		return selfRentDao;
	}
	public void setSelfRentDao(ISelfRentDao selfRentDao) {
		this.selfRentDao = selfRentDao;
	}
	public IRentPaymentDao getRentPayMentDao() {
		return rentPayMentDao;
	}
	public void setRentPayMentDao(IRentPaymentDao rentPayMentDao) {
		this.rentPayMentDao = rentPayMentDao;
	}
	
	private RentBillaccountVO queryRentBillaccountVOById(String id){
		Map<String,Object> map=new HashMap<>();
		map.put("billAccountId", id);
		return rebursePointDao.queryBillAccountById(map);
	}
	@Override
	public int billAccountSubmitAudit(List<Map<String,Object>> ids,UserLoginInfo loginUser) {
		for(Map<String,Object> map:ids){
			RentBillaccountVO rentBillaccount= this.queryRentBillaccountVOById(map.get("id").toString());
			if(rentBillaccount!=null&&rentBillaccount.getAuditState()==ActivityStateComm.STATE_UNCOMMITTED){
				// 启动流程
				/**
				 * 修改启动流程方法，添加省份编码字段
				 * 原：actTaskService.startProcess(ActUtils.PD_SELFRENT_AUDIT[0],ActUtils.PD_SELFRENT_AUDIT[1], rentcontractId,ActUtils.PD_SELFRENT_AUDIT[2],null,user_loginname);
				 *@author xup 2017/7/27 17:01
				 */
				actTaskService.startProcess(ActUtils.PD_RENTBILL_ACCOUNT[0],loginUser.getPrv_code(), ActUtils.PD_RENTBILL_ACCOUNT[1], map.get("id").toString(),ActUtils.PD_RENTBILL_ACCOUNT[2],map,loginUser.getUser_loginname());
				Map<String, Object> maps = new HashMap<String, Object>();
				maps.put("id", map.get("id").toString());
				maps.put("state",ActivityStateComm.STATE_AUDIT);
					rebursePointDao.billAccountSubmitAudit(maps);
			}
		}
		return ids.size();
		
	}
	
	@Override
	public int billAccountCheckAudit(List<Map<String, Object>> list,UserLoginInfo loginInfo) {
		for(Map<String,Object> map:list){
			String taskid=map.get("taskId").toString();
			Task t=actTaskService.getTask(taskid);
			//增加判断当前业务数据是不是已经提交，需要根据ID重新查询数据库。
			if(t!=null){
				Map<String,Object> vars=new HashMap<String,Object>();
				vars.put("state",map.get("state").toString());
				if(map.get("leader")!=null&&StrUtil.isNotBlank(map.get("leader").toString())){
					vars.put("nextUserId", map.get("leader").toString());//指定下一环节审核人
				}
				String user_loginname = loginInfo.getUser_loginname();
				if(StrUtil.isNotBlank(user_loginname)){
					vars.put("currUserId", user_loginname);//指定当前审核人
				}
				actTaskService.completeByBusiness(ActUtils.PD_RENTBILL_ACCOUNT[1], map.get("billAccountId").toString(),map.get("comment").toString() , ActUtils.PD_RENTBILL_ACCOUNT[2], vars);
				Task newtask = actTaskService.getTask(ActUtils.PD_RENTBILL_ACCOUNT[1],  map.get("billAccountId").toString());
//				newtask为空,审核完成
				if(newtask==null){
					//如果是审核不通过，则直接修改业务数据审核状态为不通过
					if(map.get("state")!=null&&map.get("state").toString().equals(ActivityStateComm.STATE_UNAPPROVE)){
						Map<String,Object> maps=new HashMap<>();
						maps.put("state", ActivityStateComm.STATE_UNAPPROVE);
						maps.put("id", map.get("billAccountId").toString());
						rebursePointDao.billAccountSubmitAudit(maps);
					}else if(map.get("state")!=null&&map.get("state").toString().equals(ActivityStateComm.STATE_NORMAL+"")){
						Map<String,Object> maps=new HashMap<>();
						maps.put("state", ActivityStateComm.STATE_NORMAL);
						maps.put("id", map.get("billAccountId").toString());
						rebursePointDao.billAccountSubmitAudit(maps);
					}
				}
			}
		}
		
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<RentBillaccountVO> queryRembursePointVO(
			Map<String, Object> paraMap, int pageNumber, int pageSize) {
		Page<RentBillaccountVO> page = rebursePointDao.queryRembursePointVO(paraMap, pageNumber, pageSize);
		if(paraMap.get("idsList")!=null){
			List<RentBillaccountVO> list=Lists.newArrayList();
			for(RentBillaccountVO datBaseResource:page.getResult()){
				for(Act acts:(List<Act>)paraMap.get("idsList")){
						if(datBaseResource.getBillAccountId()!=null&&datBaseResource.getBillAccountId().equals(acts.getBusinessId())){
							datBaseResource.setAct(acts);
							list.add(datBaseResource);
						}
				}
			}
			page.setResult(list);
		}
		return page;
	}
	@Override
	public RentContractVO queryContractAndSupplier(Map<String, Object> paraMap) {
		return selfRentDao.queryContractAndSupplier(paraMap);
	}
	@Override
	public RentContractVO queryContAndSupByBillId(Map<String, Object> paraMap) {
		return selfRentDao.queryContAndSupByBillId(paraMap);
	}
	@Override
	public RentBillAccountContractVO queryContractBindBillacc(String rentcontractId) {
		
		return rentBillAccountContractDao.queryContractBindBillacc(rentcontractId);
	}
	@Override
	public List<RentBillAccountResourceVO> queryResourceBindBillacc(
			Map<String, Object> paraMap) {
		return rentBillAccountResourceDao.queryResourceBindBillacc(paraMap);
	}
	@Override
	public int deleteResourcePoint(String baseresourceId) {
		
		return rentBillAccountResourceDao.deleteResourcePoint(baseresourceId);
	}
	@Override
	public RentBillaccountVO queryPaymentMethod(String billAccountId) {
		
		return rebursePointDao.queryPaymentMethod(billAccountId);
	}
	@Override
	public boolean exportRembursePointInfo(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		//获取当前用户所属地区,权限控制
		List<RentBillaccountVO> list = rebursePointDao.queryRembursePointInfo(map);
		List<SysRegionVO> listreg=sysRegionDao.getAddress(map);//准备需要的数据处理
		HistroryPayModelHandler tbh=new HistroryPayModelHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.BELONG_REGION});//需要处理数据的列名 
		ExportParams params = new ExportParams(DateDisposeComm.BILLACCOUNT_INFO_MANAGE, DateDisposeComm.BILLACCOUNT_INFO_MANAGE, ExcelType.HSSF);
//		params.setExclusions(new String[]{"不需要的"});过滤属性
		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, RentBillaccountVO.class,list);
        FileUtils.downFile(workBook, DateDisposeComm.BILLACCOUNT_INFO_MANAGE_xls, request, response);
		return true;
	}
	@Override
	public Map<String, Object> insertExcelData(MultipartFile file,
			String suffix, HttpServletRequest request) throws Exception {
		SessionUtils.setProcessSession(suffix, DateDisposeComm.BEGIN_ANALAYSIS_FILE, 5, request);
		//导入参数
		ImportParams iparams=new ImportParams();
		//设置自定义数据处理
		Map<String,Object> map = Maps.newHashMap();
		map.put("state", StateComm.STATE_str0);
		//获取当前用户所属地区
		UserLoginInfo loginInfo = (UserLoginInfo) request.getSession().getAttribute("user");
		
		map.put("prvId",loginInfo.getPrv_id());
		List<SysRegionVO> listreg=sysRegionDao.getAddress(map);
		HistroryPayModelHandler tbh=new HistroryPayModelHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.BELONG_REGION});//需要处理数据的列名 
		iparams.setDataHanlder(tbh);
		List<RentBillaccountVO> listAll=ExcelImportUtil.importExcel(file.getInputStream(),RentBillaccountVO.class, iparams);
		
		Map<String,Object> paramMap = Maps.newHashMap();
		List<RentBillaccountVO> listt = rebursePointDao.queryRembursePointInfo(paramMap);
		List<RentBillaccountVO> listExist = Lists.newArrayList();
		List<RentBillaccountVO> list = Lists.newArrayList();
		for(int index=0;index<list.size();index++){
			RentBillaccountVO rb = list.get(index);
			if(rb.verifyData()){//验证数据不通过
				
			}
			if(listt.contains(rb)){//如果这条数据已经存在
				listExist.add(rb);//保存已经存在数据
			}else{
				list.add(rb);//保存
			}
		}
		SessionUtils.setProcessSession(suffix, DateDisposeComm.ANALAYSIS_FILE_DONE, 10, request);
		
		int a = 500;
		int loop = (int) Math.ceil(list.size() / (double) a);	
		int count=list.size();
		int percent = (int) Math.ceil(90 / (double) loop);//计算每批占的百分比保存到数据库占80%的进度
		int sumPercent=20;//方便每次计算
		for(int i = 0; i < loop; i++){
			int stop = Math.min(a - 1, list.size());//判断结束的序号
			
			rebursePointDao.insertBatchSelective(list.subList(0, stop));
			 list.subList(0, stop).clear();//清除已经插入的数据
			 sumPercent+=percent;
			 sumPercent=(sumPercent>100?100:sumPercent);//如果大于100，按100计算
			 SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
		}
		SessionUtils.setProcessSession(suffix,PromptMessageComm.IMPORT_SUCCESS+PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.IMPORT_SUCCESS_NUMBER,100,request);
		
		
		Map<String,Object> returnMap=Maps.newHashMap();
		returnMap.put("errMsg", listExist.size()>0? PromptMessageComm.ALREADY_EXIST_NUMBER +listExist.size()+ PromptMessageComm.STRIP :"");
		returnMap.put("msg",PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.IMPORT_SUCCESS_NUMBER);
		returnMap.put("isExist", listExist);
		returnMap.put("successCount", count);
    	return returnMap;
	}
	
}
