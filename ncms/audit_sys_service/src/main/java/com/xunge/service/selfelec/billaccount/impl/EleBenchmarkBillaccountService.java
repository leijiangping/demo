package com.xunge.service.selfelec.billaccount.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.DateDisposeComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.page.Page;
import com.xunge.core.util.FileUtils;
import com.xunge.core.util.Reflections;
import com.xunge.dao.selfelec.EleBillaccountMapper;
import com.xunge.filter.PageInterceptor;
import com.xunge.model.selfelec.EleBenchmarkBillaccountBean;
import com.xunge.service.selfelec.billaccount.IEleBenchmarkBillaccountService;

/**
 * @descript 报账标杆接口实现类
 * @author wagnz
 * @date 2017-08-20 11:18:15
 */
@Service
public class EleBenchmarkBillaccountService implements IEleBenchmarkBillaccountService {

	@Resource
	private EleBillaccountMapper eleBillaccountMapper;

	@Override
	public Page<EleBenchmarkBillaccountBean> queryPagePowerRate(Map<String, Object> paramMap) {
		// 原始数据
		List<EleBenchmarkBillaccountBean> origndatas = eleBillaccountMapper.queryEleBillBenchmark(paramMap);
		// 转换后的数据
		Map<Integer, EleBenchmarkBillaccountBean> resultMap = convertData(origndatas, PromptMessageComm.POWER_RATE_DEGREE);
		
		Page<EleBenchmarkBillaccountBean> page = queryByPage(paramMap,resultMap);
		return page;
	}
	
	@SuppressWarnings("unchecked")
	public Page<EleBenchmarkBillaccountBean> queryByPage(Map<String, Object> paramMap,Map<Integer, EleBenchmarkBillaccountBean> resultMap){
		// 分页
		PageInterceptor.startPage(Integer.parseInt(paramMap.get("pageNumber").toString()),Integer.parseInt(paramMap.get("pageSize").toString()));
		eleBillaccountMapper.queryPageEleBillBenchmark(paramMap);
		Page<EleBenchmarkBillaccountBean> page = PageInterceptor.endPage();
		List<EleBenchmarkBillaccountBean> result = new ArrayList<EleBenchmarkBillaccountBean>();
		Calendar cal = Calendar.getInstance();
		for (EleBenchmarkBillaccountBean bean : page.getResult()) {
			cal.setTime(bean.getColDate());
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + SelfelecComm.NUMBER_1;
			int key = (bean.getBillaccountId() + "#" + year + month).hashCode();
			if (resultMap.containsKey(key)) {
				result.add(resultMap.get(key));
			}
		}
		page.setResult(result);
		return page;
	}

	@Override
	public void exportPowerRate(Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) {
		// 原始数据
		List<EleBenchmarkBillaccountBean> datas = eleBillaccountMapper.queryEleBillBenchmark(paramMap);
		// 转换数据
		Map<Integer, EleBenchmarkBillaccountBean> resultMap = convertData(datas, PromptMessageComm.POWER_RATE_DEGREE);
		List<EleBenchmarkBillaccountBean> result = new ArrayList<EleBenchmarkBillaccountBean>(resultMap.values());
		
		ExportParams params = new ExportParams(DateDisposeComm.RATED_POWER_BENCHMARK, DateDisposeComm.RATED_POWER_BENCHMARK, ExcelType.HSSF);
		org.apache.poi.ss.usermodel.Workbook workBook = ExcelExportUtil.exportExcel(params, EleBenchmarkBillaccountBean.class,result);
        FileUtils.downFile(workBook, DateDisposeComm.RATED_POWER_BENCHMARK_XLS, request, response);
	}

	@Override
	public Page<EleBenchmarkBillaccountBean> queryPageElectricmeter(Map<String, Object> paramMap) {
		// 原始数据
		List<EleBenchmarkBillaccountBean> datas = eleBillaccountMapper.queryEleBillBenchmark(paramMap);
		// 转换数据
		Map<Integer, EleBenchmarkBillaccountBean> resultMap = convertData(datas, PromptMessageComm.ELECTRIC_METER_DEGREE);
		
		Page<EleBenchmarkBillaccountBean> page = queryByPage(paramMap, resultMap);
		return page;
	}

	@Override
	public void exportElectricmeter(Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) {
		// 原始数据
		List<EleBenchmarkBillaccountBean> list = eleBillaccountMapper.queryEleBillBenchmark(paramMap);
		// 转换数据
		Map<Integer, EleBenchmarkBillaccountBean> resultMap = convertData(list, PromptMessageComm.ELECTRIC_METER_DEGREE);
		List<EleBenchmarkBillaccountBean> result = new ArrayList<EleBenchmarkBillaccountBean>(resultMap.values());
		
		ExportParams params = new ExportParams(DateDisposeComm.SMARTMETER_BENCHMARK, DateDisposeComm.SMARTMETER_BENCHMARK, ExcelType.HSSF);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, EleBenchmarkBillaccountBean.class,result);
        FileUtils.downFile(workBook, DateDisposeComm.SMARTMETER_BENCHMARK_XLS, request, response);
	}

	@Override
	public Page<EleBenchmarkBillaccountBean> queryPagePowerload(Map<String, Object> paramMap) {
		// 原始数据
		List<EleBenchmarkBillaccountBean> datas = eleBillaccountMapper.queryEleBillBenchmark(paramMap);
		// 转换数据
		Map<Integer, EleBenchmarkBillaccountBean> resultMap = convertData(datas, PromptMessageComm.POWER_LOAD_DEGREE);
		
		Page<EleBenchmarkBillaccountBean> page = queryByPage(paramMap,resultMap);
		return page;
	}

	@Override
	public void exportPowerload(Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) {
		// 原始数据
		List<EleBenchmarkBillaccountBean> datas = eleBillaccountMapper.queryEleBillBenchmark(paramMap);
		// 转换数据
		Map<Integer, EleBenchmarkBillaccountBean> resultMap = convertData(datas, PromptMessageComm.POWER_LOAD_DEGREE);
		List<EleBenchmarkBillaccountBean> result = new ArrayList<EleBenchmarkBillaccountBean>(resultMap.values());
		
		ExportParams params = new ExportParams(DateDisposeComm.SWITCHING_POWER_SUPPLY_LOAD_BENCHMARK, DateDisposeComm.SWITCHING_POWER_SUPPLY_LOAD_BENCHMARK, ExcelType.HSSF);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, EleBenchmarkBillaccountBean.class,result);
        FileUtils.downFile(workBook, DateDisposeComm.SWITCHING_POWER_SUPPLY_LOAD_BENCHMARK_XLS, request, response);
	}
	
	private Map<Integer, EleBenchmarkBillaccountBean> convertData(List<EleBenchmarkBillaccountBean> datas,String property){
		Map<Integer, EleBenchmarkBillaccountBean> map = new HashMap<Integer, EleBenchmarkBillaccountBean>();
		Calendar cal = Calendar.getInstance();
		for (EleBenchmarkBillaccountBean bean : datas) {
			cal.setTime(bean.getColDate());
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + SelfelecComm.NUMBER_1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int key = (bean.getBillaccountId() + "#" + year + month).hashCode();
			
			BigDecimal totalvalue = (BigDecimal) Reflections.getFieldValue(bean,property);
			
			if (map.containsKey(key)) {
				bean = map.get(key);
				if (null != totalvalue) {
					bean.setMonthTotal(bean.getMonthTotal().add(totalvalue));
				}
			} else {
				bean.setYear( year + "");
				bean.setMonth( month + "");
				bean.setMonthTotal(totalvalue);
			}
			Reflections.setFieldValue(bean, PromptMessageComm.DAY_STR+ day, totalvalue);
			
			map.put(key, bean);
		}
		return map;
	}
}
