package com.xunge.service.selfelec.elecaudit.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.core.page.Page;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.StrUtil;
import com.xunge.dao.selfelec.elecaudit.IHistoryPaymentModelDao;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.model.selfelec.vo.HistoryPaymentModelVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.selfelec.elecaudit.IHistoryPaymentModelService;
import com.xunge.service.twrrent.punish.exceldatahandler.HistroryPayModelHandler;

public class HistoryPaymentModelServiceImpl implements IHistoryPaymentModelService {
	
	@Resource
	IHistoryPaymentModelDao historyPaymentModelDao;
    
	@Autowired
	ISysRegionDao sysRegionDao;
	
	@Override
	public Page<HistoryPaymentModelVO> queryAll(Map<String,Object> paramMap) {
		Page<HistoryPaymentModelVO> historyLstPage = historyPaymentModelDao.queryAll(paramMap);
		List<HistoryPaymentModelVO> historyLst = historyLstPage.getResult();
		historyLstPage.setResult(dealWithRecord(historyLst));
		return historyLstPage;
	}


	@Override
	public List<HistoryPaymentModelVO> queryAllNoPage(Map<String,Object> paramMap){
		List<HistoryPaymentModelVO> historyLst = historyPaymentModelDao.queryAllNoPage(paramMap);
		List<HistoryPaymentModelVO> dealAfterResult = dealWithRecord(historyLst);
		return dealAfterResult;
	}
	
	@Override
	public boolean exportExcelData(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		//获取当前用户所属地区,权限控制
		List<HistoryPaymentModelVO> list = historyPaymentModelDao.queryAllNoPage(map);
		List<HistoryPaymentModelVO> dealAfterResult = dealWithRecord(list);
		List<SysRegionVO> listreg=sysRegionDao.getAddress(map);//准备需要的数据处理
		HistroryPayModelHandler tbh=new HistroryPayModelHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.BELONG_REGION});//需要处理数据的列名 
		ExportParams params = new ExportParams(DateDisposeComm.HISTORICAL_METER_BENCHMARK, DateDisposeComm.HISTORICAL_METER_BENCHMARK, ExcelType.HSSF);
//		params.setExclusions(new String[]{"不需要的"});过滤属性
		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, HistoryPaymentModelVO.class,dealAfterResult);
        FileUtils.downFile(workBook, DateDisposeComm.HISTORICAL_ELECTRICITY_BENCHMARK_XLS, request, response);
		return true;
	}

	public IHistoryPaymentModelDao getHistoryPaymentModelDao() {
		return historyPaymentModelDao;
	}

	public void setHistoryPaymentModelDao(
			IHistoryPaymentModelDao historyPaymentModelDao) {
		this.historyPaymentModelDao = historyPaymentModelDao;
	}

	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}

	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}

	private List<HistoryPaymentModelVO> dealWithRecord(List<HistoryPaymentModelVO> historyLst) {
		Map<String,BigDecimal[]> billMap = new HashMap<String,BigDecimal[]>();
		List<HistoryPaymentModelVO> dealAfterResult = new ArrayList<HistoryPaymentModelVO>();
		for (HistoryPaymentModelVO vo : historyLst){
			String key = vo.getRegId()+"_"+vo.getYear()+"_"+vo.getMonth();
			if (billMap.containsKey(key)){
				if (StrUtil.isNotEmpty(vo.getDay())){
					int index = Integer.parseInt(vo.getDay());
					billMap.get(key)[index-SelfelecComm.NUMBER_1] = vo.getNowDegree();
				}
			}else{
				if (StrUtil.isNotEmpty(vo.getDay())){
					dealAfterResult.add(vo);
					int index = Integer.parseInt(vo.getDay());
					billMap.put(key,new BigDecimal[31]);
					billMap.get(key)[index-SelfelecComm.NUMBER_1] = vo.getNowDegree();
				}
			}
		}
		for(HistoryPaymentModelVO newObj : dealAfterResult){
			String key = newObj.getRegId()+"_"+newObj.getYear()+"_"+newObj.getMonth();
			if (billMap.containsKey(key)){
				BigDecimal[] monthValue = billMap.get(key);
				BigDecimal monthSummary = new BigDecimal(Integer.parseInt(SelfelecComm.NUMBER_0));
				for (int i = Integer.parseInt(SelfelecComm.NUMBER_0);i < monthValue.length;i++){
					if (null != monthValue){
						if (null != monthValue[i]){
							monthSummary = monthSummary.add(monthValue[i]);
						}
					}
				}
				//出于导出的考虑，此处每个日期有相应的属性
				newObj.setDay01(monthValue[0]);
				newObj.setDay02(monthValue[1]);
				newObj.setDay03(monthValue[2]);
				newObj.setDay04(monthValue[3]);
				newObj.setDay05(monthValue[4]);
				newObj.setDay06(monthValue[5]);
				newObj.setDay07(monthValue[6]);
				newObj.setDay08(monthValue[7]);
				newObj.setDay09(monthValue[8]);
				newObj.setDay10(monthValue[9]);
				newObj.setDay11(monthValue[10]);
				newObj.setDay12(monthValue[11]);
				newObj.setDay13(monthValue[12]);
				newObj.setDay14(monthValue[13]);
				newObj.setDay15(monthValue[14]);
				newObj.setDay16(monthValue[15]);
				newObj.setDay17(monthValue[16]);
				newObj.setDay18(monthValue[17]);
				newObj.setDay19(monthValue[18]);
				newObj.setDay20(monthValue[19]);
				newObj.setDay21(monthValue[20]);
				newObj.setDay22(monthValue[21]);
				newObj.setDay23(monthValue[22]);
				newObj.setDay24(monthValue[23]);
				newObj.setDay25(monthValue[24]);
				newObj.setDay26(monthValue[25]);
				newObj.setDay27(monthValue[26]);
				newObj.setDay28(monthValue[27]);
				newObj.setDay29(monthValue[28]);
				newObj.setDay30(monthValue[29]);
				newObj.setDay31(monthValue[30]);
				newObj.setMonthSummary(monthSummary);
			}
		}
		return dealAfterResult;
	}
}
