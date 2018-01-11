package com.xunge.service.selfrent.billaccount.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import com.google.common.collect.Lists;
import com.xunge.comm.StateComm;
import com.xunge.comm.activity.ActivityStateComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.dao.selfrent.billaccount.IVBaseresourcePaymentdetailDao;
import com.xunge.dao.selfrent.billaccount.IVPaymentDao;
import com.xunge.model.activity.Act;
import com.xunge.model.selfrent.billAccount.VBaseresourcePaymentdetailVO;
import com.xunge.model.selfrent.billAccount.VPaymentVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.selfrent.billaccount.IPaymentService;
/**
 * 报账点缴费记录视图service实现类
 *
 */
public class PaymentServiceImpl implements IPaymentService {

	private IVPaymentDao paymentDao;
	
	private IVBaseresourcePaymentdetailDao baseresourcePaymentdetailDao;
	
	@Autowired
	private IActTaskService actTaskService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<VPaymentVO> queryPayment(Map<String, Object> map, int pageNumber, int pageSize) {
		Page<VPaymentVO> page=paymentDao.queryPayment(map, pageNumber, pageSize);
		if(map.get("idsList")!=null){
			List<VPaymentVO> list=Lists.newArrayList();
			for(VPaymentVO t:page.getResult()){
				for(Act actTemp:(List<Act>)map.get("idsList")){
					if(t.getPaymentId()!=null&&t.getPaymentId().equals(actTemp.getBusinessId())){
						t.setAct(actTemp);
						list.add(t);
					}
				}
			}
			page.setResult(list);
		}
		return page;
	}
	
	@Override
	public Page<List<VPaymentVO>> queryAllPayment(Map<String, Object> map, int pageNumber, int pageSize) {
		return paymentDao.queryAllPayment(map, pageNumber, pageSize);
	}
	@Override
	public Page<List<VPaymentVO>> queryPaymentContract(Map<String, Object> map, int pageNumber, int pageSize) {
		return paymentDao.queryPaymentContract(map, pageNumber, pageSize);
	}	
	
	@Override
	public List<VPaymentVO> queryContractPayment(Map<String, Object> map,String billaccountId,String paymentId) {
		List<VPaymentVO> list=paymentDao.queryContractPayment(map);
		Map<String,Object> hashMaps=new HashMap<String,Object>();
		hashMaps.put("paymentId", paymentId);
		hashMaps.put("billaccountId", billaccountId);
		List<VBaseresourcePaymentdetailVO> baseresourcePaymentdetail = baseresourcePaymentdetailDao.queryBaseresourcePaymentdetail(hashMaps);
		List<VPaymentVO> li=new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			VPaymentVO payment=list.get(i);
			payment.setBaseresourcePaymentdetail(baseresourcePaymentdetail);
			li.add(payment);
		}
		return li;
	}
	/**
	 * 提出业务方法
	 * state：审核状态 、paymentState：业务表提交状态、id：业务表主键id
	 */
	private void updateBusinessState(int state,int paymentState,String id){
		 Map<String,Object> maps=new HashMap<String,Object>();
			maps.put("state",state );
			maps.put("paymentId", id);
			maps.put("paymentState",paymentState );
			paymentDao.updateActivityCommit(maps);
			paymentDao.updateState(maps);
	}
	@Override
	public int updateActivityCommit(List<Map<String,Object>> list,UserLoginInfo loginUser) {
		for(Map<String,Object> map:list){
			VPaymentVO payment = paymentDao.queryPaymentContractById(map.get("paymentId").toString());
			if(payment!=null&&payment.getPaymentFlowstate()==ActivityStateComm.STATE_UNCOMMITTED){
				 actTaskService.startProcess(ActUtils.PD_REMBURSE_POINT[0],loginUser.getPrv_code(), ActUtils.PD_REMBURSE_POINT[1], map.get("paymentId").toString(),ActUtils.PD_REMBURSE_POINT[2],map,loginUser.getUser_loginname());
				 this.updateBusinessState(ActivityStateComm.STATE_AUDIT,StateComm.STATE_1,map.get("paymentId").toString());
			}
		}
		return list.size();
	}

	@Override
	public int updateAuditCompelet(List<Map<String, Object>> list, UserLoginInfo loginInfo) {
		for(Map<String,Object> map:list){
			String taskid=map.get("taskId").toString();
			Task t=actTaskService.getTask(taskid);
			//增加判断当前业务数据是不是已经提交，需要根据ID重新查询数据库。
			if(t!=null){
				Map<String,Object> vars=new HashMap<String,Object>();
				vars.put("state", map.get("state").toString());
				if(StrUtil.isNotBlank(map.get("leader").toString())){
					vars.put("nextUserId", map.get("leader").toString());//指定下一环节审核人
				}
				String user_loginname = loginInfo.getUser_loginname();
				if(StrUtil.isNotBlank(user_loginname)){
					vars.put("currUserId", user_loginname);//指定当前审核人
				}
				actTaskService.completeByBusiness(ActUtils.PD_REMBURSE_POINT[1], map.get("paymentId").toString(), map.get("comment").toString(), ActUtils.PD_REMBURSE_POINT[2], vars);
//				if(state.equals(ActivityStateComm.STATE_NORMAL)){
					Task newtask = actTaskService.getTask(ActUtils.PD_REMBURSE_POINT[1],map.get("paymentId").toString() );
//				newtask为空,审核完成
					if(newtask==null){
						//如果是审核不通过，则直接修改业务数据审核状态为不通过
						if(map.get("state")!=null&&map.get("state").toString().equals(ActivityStateComm.STATE_UNAPPROVE+"")){
							this.updateBusinessState(ActivityStateComm.STATE_UNAPPROVE,StateComm.STATE_0,map.get("paymentId").toString());
						}else if(map.get("state")!=null&&map.get("state").toString().equals(ActivityStateComm.STATE_NORMAL+"")){
							this.updateBusinessState(ActivityStateComm.STATE_NORMAL,StateComm.STATE_0,map.get("paymentId").toString());
						}
						
					}
//				}else{
					
//				}
			}
		}
		
		return list.size();
	}

	public int updateBillamountIdByPaymentId(Map<String, Object> map) {
		return paymentDao.updateBillamountIdByPaymentId(map);
	}
	
	public Page<List<VPaymentVO>> queryContractPaymentByNoAmount(Map<String, Object> map) {
		return paymentDao.queryContractPaymentByNoAmount(map);
	}
	
	public List<VPaymentVO> queryContractPaymentByNoAmountList(Map<String, Object> map) {
		return paymentDao.queryContractPaymentByNoAmountList(map);
	}
	
	public IVPaymentDao getPaymentDao() {
		return paymentDao;
	}
	
	public void setPaymentDao(IVPaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}

	public IVBaseresourcePaymentdetailDao getBaseresourcePaymentdetailDao() {
		return baseresourcePaymentdetailDao;
	}

	public void setBaseresourcePaymentdetailDao(
			IVBaseresourcePaymentdetailDao baseresourcePaymentdetailDao) {
		this.baseresourcePaymentdetailDao = baseresourcePaymentdetailDao;
	}

	@Override
	public void exportPaymentDetail(Map<String, Object> paramMap,
			HttpServletRequest request, HttpServletResponse response) {
		UserLoginInfo loginUser = (UserLoginInfo) paramMap.get("loginInfo");
		//获取当前用户所属地区,权限控制
		List<String> regIds = loginUser.getReg_ids();
		paramMap.put("regIds", regIds);
		paramMap.put("alias", PromptMessageComm.ALIAS_NAME);
		paramMap.put("state",StateComm.STATE_0);
		paramMap.put("userId",loginUser.getUser_id());
		List<VPaymentVO> list = paymentDao.queryAllPayment(paramMap);
		/*List<SysRegionVO> listreg=sysRegionDao.getAddress(map);//准备需要的数据处理
		TowerBillbalanceHandler tbh=new TowerBillbalanceHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{"运营商地市","需求承接地市","站址所属地市"});//需要处理数据的列名 
*/		ExportParams params = new ExportParams(DateDisposeComm.PAYMENT_RECORD_INFO, DateDisposeComm.PAYMENT_RECORD_INFO, ExcelType.HSSF);
//		params.setExclusions(new String[]{"不需要的"});过滤属性
//		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, VPaymentVO.class,list);
        FileUtils.downFile(workBook, DateDisposeComm.PAYMENT_RECORD_INFO_xls, request, response);
		
	}


	
}
