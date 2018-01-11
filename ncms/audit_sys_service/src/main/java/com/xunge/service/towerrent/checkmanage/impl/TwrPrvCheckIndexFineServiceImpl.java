package com.xunge.service.towerrent.checkmanage.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.page.Page;
import com.xunge.core.util.SessionUtils;
import com.xunge.core.util.SysUUID;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.dao.towerrent.checkmanage.ITwrPrvCheckIndexFineDao;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.model.towerrent.checkmanage.TwrPrvCheckIndexFineVO;
import com.xunge.service.towerrent.checkmanage.ITwrPrvCheckIndexFineService;
import com.xunge.service.twrrent.punish.exceldatahandler.TwrPrvPunishHandler;

/**mp
 * 
 * @author jcy
 * @date 2017年7月19日
 * @description 考核指标扣罚Service
 */
public class TwrPrvCheckIndexFineServiceImpl implements ITwrPrvCheckIndexFineService{

	ITwrPrvCheckIndexFineDao twrPrvCheckIndexFineDao;
	@Autowired
	private ISysRegionDao sysRegionDao;
	
	@Override
	public Page<List<TwrPrvCheckIndexFineVO>> queryrAllPrvCheckIndexFineVO(
			Map<String, Object> paramMap, int pageSize, int pageNum) {
		return twrPrvCheckIndexFineDao.queryrAllPrvCheckIndexFineVO(paramMap, pageSize, pageNum);
	}

	@Override
	public TwrPrvCheckIndexFineVO queryCheckIndexFineVOById(String checkId) {
		return twrPrvCheckIndexFineDao.queryCheckIndexFineVOById(checkId);
	}

	@Override
	public String deleteTwrById(List<String> prvCheckIdList) {
		return twrPrvCheckIndexFineDao.deleteTwrById(prvCheckIdList);
	}

	@Override
	public String updateTwrById(TwrPrvCheckIndexFineVO twrPrvCheckIndexFine) {
		return twrPrvCheckIndexFineDao.updateTwrById(twrPrvCheckIndexFine);
	}

	@Override
	public String insertTwrById(TwrPrvCheckIndexFineVO twrPrvCheckIndexFine) {
		return twrPrvCheckIndexFineDao.insertTwrById(twrPrvCheckIndexFine);
	}

	@Override
	public String checkTwrById(List<String> ids) {
		return twrPrvCheckIndexFineDao.checkTwrById(ids);
	}

	@Override
	public List<TwrPrvCheckIndexFineVO> queryExportList(Map<String,Object> prvCheckIdList) {
		return twrPrvCheckIndexFineDao.queryExportList(prvCheckIdList);
	}

	@Override
	public Page<TwrPrvCheckIndexFineVO> queryTwrRentInformationCheck(
			Map<String, Object> paramMap, int pageNum, int pageSize) {
		return twrPrvCheckIndexFineDao.queryTwrRentInformationCheck(paramMap, pageNum, pageSize);
	}
	
	public ITwrPrvCheckIndexFineDao getTwrPrvCheckIndexFineDao() {
		return twrPrvCheckIndexFineDao;
	}
	
	public void setTwrPrvCheckIndexFineDao(
			ITwrPrvCheckIndexFineDao twrPrvCheckIndexFineDao) {
		this.twrPrvCheckIndexFineDao = twrPrvCheckIndexFineDao;
	}

	@Override
	public List<Map<String, Object>> queryTwrPrvCheckIndexFineMapList(
			Map<String, Object> params) {
		return twrPrvCheckIndexFineDao.queryTwrPrvCheckIndexFineMapList(params);
	}

	
	@Override
	public Map<String, Object> insertExcelData(MultipartFile file,
			String suffix, HttpServletRequest request) throws Exception {
		SessionUtils.setProcessSession(suffix,DateDisposeComm.BEGIN_ANALAYSIS_FILE,SelfelecComm.NUMBER_5, request);
		
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
		TwrPrvPunishHandler tbh=new TwrPrvPunishHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.BELONG_CITY});//需要处理数据的列名 
		iparams.setDataHanlder(tbh);
		iparams.setTitleRows(SelfelecComm.NUMBER_1);
		List<TwrPrvCheckIndexFineVO> list=ExcelImportUtil.importExcel(file.getInputStream(),TwrPrvCheckIndexFineVO.class, iparams);

		/**
		 * 验证数据在数据库里面是否存在
		 */
		Map<String,Object> paramMap=Maps.newHashMap();
		List<TwrPrvCheckIndexFineVO> listt=twrPrvCheckIndexFineDao.selectTwrRegPunishList(paramMap);
		List<TwrPrvCheckIndexFineVO> listExist=Lists.newArrayList();
		for (int index=0;index<list.size();index++) {
			TwrPrvCheckIndexFineVO twr=list.get(index);
			twr.setTwrProvincePunishId(SysUUID.generator());
			twr.setState(StateComm.STATE_0);
			twr.setAuditState(ActivityStateComm.STATE_UNCOMMITTED);
			twr.setCreateTime(new Date());
			twr.setCreateUserId(loginInfo.getUser_id());
			twr.setUpdateTime(new Date());
			twr.setUpdateUserId(loginInfo.getUser_id());
			//数据未通过校验，未实现
//			if(twr.verifyData()){

//				
//			}
			if(listt.contains(twr)){ //如果这条数据已经存在
				listExist.add(twr);//保存已经存在数据
				list.subList(index, index+1).clear();//清除这条数据
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

			 twrPrvCheckIndexFineDao.insertBatchTwrRegPunishVO(list.subList(0, stop));
			 list.subList(0, stop).clear();//清除已经插入的数据
			 sumPercent+=percent;
			 sumPercent=(sumPercent>100?SelfelecComm.NUMBER_100:sumPercent);//如果大于100，按100计算
			 SessionUtils.setProcessSession(suffix,PromptMessageComm.BEING_SAVE_DATA,sumPercent,request);
		}
		SessionUtils.setProcessSession(suffix,PromptMessageComm.IMPORT_SUCCESS+PromptMessageComm.COMMA_SYMBOL+PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS,SelfelecComm.NUMBER_100,request);
		
		
		Map<String,Object> returnMap=Maps.newHashMap();
		returnMap.put("errMsg", listExist.size()>0?PromptMessageComm.ALREADY_EXIST_NUMBER+listExist.size()+PromptMessageComm.STRIP:"");
		returnMap.put("msg",PromptMessageComm.IMPORT_SUM+count+PromptMessageComm.DATAS);
		returnMap.put("isExist", listExist);
		returnMap.put("successCount", count);
    	return returnMap;
	}
	
	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}

	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}
}
