package com.xunge.service.twrrent.punish.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
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
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.SessionUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.dao.twrrent.punish.ITwrRegPunishDao;
import com.xunge.model.activity.Act;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.punish.TwrRegPunishVO;
import com.xunge.service.activity.IActTaskService;
import com.xunge.service.activity.utils.ActUtils;
import com.xunge.service.twrrent.punish.ITwrRegPunishService;
import com.xunge.service.twrrent.punish.exceldatahandler.TwrRegPunishHandler;

/**
 * @description 地市扣罚逻辑处理
 * @author zhujj
 * @date 2017年7月20日 上午9:35:51 
 * @version 1.0.0 
 */
public class TwrRegPunishServiceImpl implements ITwrRegPunishService {
	private ITwrRegPunishDao twrRegPunishDao;
	
	private ISysRegionDao sysRegionDao;
	

	@Autowired
	private IActTaskService actTaskService;
	
	@Override
	public int deleteByPrimaryKey(List<String> list) {
		// TODO Auto-generated method stub
		return twrRegPunishDao.deleteByPrimaryKey(list);
	}

	@Override
	public int insertTwrRegPunishVO(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		record.setCreateTime(new Date());
		record.setUpdatetTime(new Date());
		record.setTwrRegPunishId(SysUUID.generator());
		record.setState(StateComm.STATE_0);
		record.setAuditState(ActivityStateComm.STATE_UNCOMMITTED);
		return twrRegPunishDao.insertTwrRegPunishVO(record);
	}

	@Override
	public int insertBatchTwrRegPunishVO(List<TwrRegPunishVO> record) {
		// TODO Auto-generated method stub
		return twrRegPunishDao.insertBatchTwrRegPunishVO(record);
	}

	@Override
	public int insertSelective(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		return twrRegPunishDao.insertSelective(record);
	}

	@Override
	public TwrRegPunishVO selectByPrimaryKey(String twrRegPunishId) {
		// TODO Auto-generated method stub
		return twrRegPunishDao.selectByPrimaryKey(twrRegPunishId);
	}
	@Override
	public List<TwrRegPunishVO> selectByTwrRegPunish(TwrRegPunishVO twrRegPunishId) {
		// TODO Auto-generated method stub
		return twrRegPunishDao.selectByTwrRegPunish(twrRegPunishId);
	}
	
	@Override
	public int updateByPrimaryKeySelective(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		record.setUpdatetTime(new Date());
		//修改后把状态改为未提交，需重新提交
		record.setAuditState(ActivityStateComm.STATE_UNCOMMITTED);
		return twrRegPunishDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		return twrRegPunishDao.updateByPrimaryKey(record);
	}

	@Override
	public int updateStateByPrimaryKey(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		return twrRegPunishDao.updateStateByPrimaryKey(record);
	}

	@Override
	public int updateAuditStateByPrimaryKey(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		return twrRegPunishDao.updateAuditStateByPrimaryKey(record);
	}

	@Override
	public int updateAccountsummaryIDByPrimaryKey(TwrRegPunishVO record) {
		// TODO Auto-generated method stub
		return twrRegPunishDao.updateAccountsummaryIDByPrimaryKey(record);
	}

	public int updateAccountsummaryIDByBatchID(List<TwrRegPunishVO> record) {
		// TODO Auto-generated method stub
		return twrRegPunishDao.updateAccountsummaryIDByBatchID(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<TwrRegPunishVO> selectTwrRegPunishPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		Page<TwrRegPunishVO> page=twrRegPunishDao.selectTwrRegPunishPage(map);
		if(map.get("ids")!=null){
			List<TwrRegPunishVO> list=Lists.newArrayList();
			for(TwrRegPunishVO t:page.getResult()){
				for(Act actTemp:(List<Act>)map.get("ids")){
					if(t.getTwrRegPunishId()!=null&&t.getTwrRegPunishId().equals(actTemp.getBusinessId())){
						t.setAct(actTemp);
						list.add(t);
					}
				}
			}
			page.setResult(list);
		}
		return page;
	}

	public ITwrRegPunishDao getTwrRegPunishDao() {
		return twrRegPunishDao;
	}

	public void setTwrRegPunishDao(ITwrRegPunishDao twrRegPunishDao) {
		this.twrRegPunishDao = twrRegPunishDao;
	}

	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}

	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}

	@Override
	public int updateAuditAndStartFlow(List<Map<String,Object>> list,UserLoginInfo user) {
		for(Map<String,Object> map:list){
			TwrRegPunishVO twrRegPunish=this.selectByPrimaryKey(map.get("id").toString());
			// 启动流程,判断状态为未提交的才可以提交
			if(twrRegPunish!=null&&twrRegPunish.getAuditState()==ActivityStateComm.STATE_UNCOMMITTED){
				//user.getPrv_code()根据省份不同调用不同流程
				actTaskService.startProcess(ActUtils.PD_TWRREGPUNISH_INFO[0],user.getPrv_code(), ActUtils.PD_TWRREGPUNISH_INFO[1], map.get("id").toString(),ActUtils.PD_TWRREGPUNISH_INFO[2],map,user.getUser_loginname());
				TwrRegPunishVO t=new TwrRegPunishVO(user.getUser_id());
				t.setTwrRegPunishId(twrRegPunish.getTwrRegPunishId());
				t.setAuditState(ActivityStateComm.STATE_AUDIT);
				t.setUpdatetTime(new Date());
				t.setUpdateUserId(user.getUser_id());
				twrRegPunishDao.updateByPrimaryKeySelective(t);
			}
		}
		return list.size();
	}

	@Override
	public int updateAuditCompelet(List<Map<String,Object>> list,UserLoginInfo user) {
		// TODO Auto-generated method stub
		for(Map<String,Object> map:list){
			String taskid=map.get("taskId").toString();
			Task t=actTaskService.getTask(taskid);
			//增加判断当前业务数据是不是已经提交，需要根据ID重新查询数据库。
			if(t!=null){
				
				Map<String,Object> vars=new HashMap<String,Object>();
				vars.put(ActivityStateComm.ACTIVITY_VARIABLE_NAME, map.get("auditState").toString());
				if(map.get("nextAuditUserId")!=null&&StrUtil.isNotBlank(map.get("nextAuditUserId").toString())){
					vars.put(ActivityStateComm.ACTIVITY_VARIABLE_NEXTUSER, map.get("nextAuditUserId").toString());//指定下一环节审核人
				}
	
				String user_loginname = user.getUser_loginname();
				if(StrUtil.isNotBlank(user_loginname)){
					vars.put(ActivityStateComm.ACTIVITY_VARIABLE_CURRUSER, user_loginname);//指定当前审核人
				}
				actTaskService.completeByBusiness(ActUtils.PD_TWRREGPUNISH_INFO[1], map.get("twrRegPunishId").toString(),map.get("auditNote").toString(), ActUtils.PD_TWRREGPUNISH_INFO[2], vars);
				Task newtask = actTaskService.getTask(ActUtils.PD_TWRREGPUNISH_INFO[1], map.get("twrRegPunishId").toString());
				
				//newtask为空,修改审核状态审核完成
				if(newtask==null){
					//如果是审核不通过，则直接修改业务数据审核状态为不通过
					if(map.get("auditState")!=null&&map.get("auditState").toString().equals(ActivityStateComm.STATE_UNAPPROVE+"")){
						TwrRegPunishVO twr=new TwrRegPunishVO();
						twr.setTwrRegPunishId(map.get("twrRegPunishId").toString());
						twr.setAuditState(ActivityStateComm.STATE_UNAPPROVE);
						twr.setUpdatetTime(new Date());
						twr.setUpdateUserId(user.getUser_id());
						twrRegPunishDao.updateByPrimaryKeySelective(twr);
					}else if(map.get("auditState")!=null&&map.get("auditState").toString().equals(ActivityStateComm.STATE_NORMAL+"")){
						TwrRegPunishVO twr=new TwrRegPunishVO();
						twr.setTwrRegPunishId(map.get("twrRegPunishId").toString());
						twr.setAuditState(ActivityStateComm.STATE_NORMAL);
						twr.setUpdatetTime(new Date());
						twr.setUpdateUserId(user.getUser_id());
						twrRegPunishDao.updateByPrimaryKeySelective(twr);
					}
				}
			}
		}
		return list.size();
	}

	@Override
	public List<TwrRegPunishVO> selectTwrRegPunishList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return twrRegPunishDao.selectTwrRegPunishList(map);
	}

	@Override
	public Map<String, Object> insertExcelData(MultipartFile file,
			String suffix, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
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
		TwrRegPunishHandler tbh=new TwrRegPunishHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.BELONG_REGION});//需要处理数据的列名 
		iparams.setDataHanlder(tbh);
		iparams.setTitleRows(1);
		List<TwrRegPunishVO> list=ExcelImportUtil.importExcel(file.getInputStream(),TwrRegPunishVO.class, iparams);

		/**
		 * 验证数据在数据库里面是否存在
		 */
		Map<String,Object> paramMap=Maps.newHashMap();
		List<TwrRegPunishVO> listt=twrRegPunishDao.selectTwrRegPunishList(paramMap);
		List<TwrRegPunishVO> listExist=Lists.newArrayList();
		for (int index=0;index<list.size();index++) {
			TwrRegPunishVO twr=list.get(index);
			twr.setTwrRegPunishId(SysUUID.generator());
			twr.setState(StateComm.STATE_0);
			twr.setAuditState(ActivityStateComm.STATE_UNCOMMITTED);
			twr.setCreateTime(new Date());
			twr.setCreateUserId(loginInfo.getUser_id());
			twr.setUpdatetTime(new Date());
			twr.setUpdateUserId(loginInfo.getUser_id());
			if(twr.verifyData()){//验证数据不通过
				
			}
			if(listt.contains(twr)){ //如果这条数据已经存在
				listExist.add(twr);//保存已经存在数据
				list.subList(index, index+1).clear();//清除这条数据
			}
		}
		SessionUtils.setProcessSession(suffix,DateDisposeComm.ANALAYSIS_FILE_DONE,SelfelecComm.NUMBER_10, request);
		//大批量数据分批插入，每次插入500条
		int a = SelfelecComm.NUMBER_500;//每次提交2000条
		int loop = (int) Math.ceil(list.size() / (double) a);//批次
		int count=list.size();
		int percent = (int) Math.ceil(90 / (double) loop);//计算每批占的百分比保存到数据库占80%的进度
		int sumPercent=SelfelecComm.NUMBER_20;//方便每次计算
		for (int i = 0; i < loop; i++) {
			 int stop = Math.min(a - 1, list.size());//判断结束的序号

			 twrRegPunishDao.insertBatchTwrRegPunishVO(list.subList(StateComm.STATE_0, stop));
			 list.subList(StateComm.STATE_0, stop).clear();//清除已经插入的数据
			 sumPercent+=percent;
			 sumPercent=(sumPercent>100?100:sumPercent);//如果大于100，按100计算
			 SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
		}
		SessionUtils.setProcessSession(suffix,PromptMessageComm.IMPORT_SUCCESS+PromptMessageComm.COMMA_SYMBOL+PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS,100,request);
		
		
		Map<String,Object> returnMap=Maps.newHashMap();
		returnMap.put("errMsg", listExist.size()>0?PromptMessageComm.ALREADY_EXIST_NUMBER+listExist.size()+PromptMessageComm.STRIP:"");
		returnMap.put("msg",PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS);
		returnMap.put("isExist", listExist);
		returnMap.put("successCount", count);
    	return returnMap;
	}

	@Override
	public boolean exportExcelData(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		//获取当前用户所属地区,权限控制
		map.put("state",StateComm.STATE_0);
		map.put("userId",loginUser.getUser_id());
		List<TwrRegPunishVO> list = twrRegPunishDao.selectTwrRegPunishList(map);
		List<SysRegionVO> listreg=sysRegionDao.getAddress(map);//准备需要的数据处理
		TwrRegPunishHandler tbh=new TwrRegPunishHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.BELONG_REGION});//需要处理数据的列名 
		ExportParams params = new ExportParams(PromptMessageComm.CITY_SELFASSESSMENT_INDT_DT, PromptMessageComm.CITY_SELFASSESSMENT_INDT_DT, ExcelType.HSSF);
//		params.setExclusions(new String[]{"不需要的"});过滤属性
		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TwrRegPunishVO.class,list);
        FileUtils.downFile(workBook, PromptMessageComm.CITY_SELFASSESSMENT_INDT_DT+DateDisposeComm.SUFF_XLS, request, response);
	
		return true;
	}

	@Override
	public boolean exportTemplate(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
		//获取当前用户所属地区,权限控制
		map.put("state",StateComm.STATE_0);
		map.put("userId",loginUser.getUser_id());
		List<TwrRegPunishVO> list = Lists.newArrayList();
		TwrRegPunishVO twrRegPunishVO=new TwrRegPunishVO();
		twrRegPunishVO.setPunishName(PromptMessageComm.FINE_NAME);
		twrRegPunishVO.setPunishAmount(new BigDecimal(128.25));
		twrRegPunishVO.setPunishCause(PromptMessageComm.FINE_DETAIL);
		twrRegPunishVO.setPunishPerson(PromptMessageComm.ZS);
		twrRegPunishVO.setPunishYearMonth(PromptMessageComm.DATE_20177);
		twrRegPunishVO.setRegId(PromptMessageComm.HUANGPU_DISTRICT);
		ExportParams params = new ExportParams(PromptMessageComm.CITY_SELFASSESSMENT_INDT_DT, PromptMessageComm.CITY_SELFASSESSMENT_INDT_DT, ExcelType.HSSF);
//		params.setExclusions(new String[]{"不需要的"});过滤属性
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, TwrRegPunishVO.class,list);
        FileUtils.downFile(workBook, PromptMessageComm.CITY_SELFASSESSMENT_INDT_DT+DateDisposeComm.SUFF_XLS, request, response);
		return false;
	}

	@Override
	public List<Map<String, Object>> queryTwrRegPunishMapListByCondition(
			Map<String, Object> params) {
		return twrRegPunishDao.queryTwrRegPunishMapListByCondition(params);
	}
}
