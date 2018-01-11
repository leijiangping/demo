package com.xunge.service.selfelec.elecaudit.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

import com.xunge.comm.system.DateDisposeComm;
import com.xunge.core.page.Page;
import com.xunge.core.util.FileUtils;
import com.xunge.dao.selfelec.elecaudit.IModelParamSetDao;
import com.xunge.dao.system.region.ISysRegionDao;
import com.xunge.model.selfelec.vo.ModelParamSetVO;
import com.xunge.model.system.region.SysRegionVO;
import com.xunge.service.selfelec.elecaudit.IModelParamSetService;
import com.xunge.service.twrrent.punish.exceldatahandler.ModelParamSetHandler;

public class ModelParamSetServiceImpl implements IModelParamSetService{

	@Resource
	IModelParamSetDao modelParamSetDao;
	
	@Autowired
	ISysRegionDao sysRegionDao;
	
	@Override
	public Page<ModelParamSetVO> queryAllModel(Map<String, Object> paramMap) {
		return modelParamSetDao.queryAll(paramMap);
	}

	@Override
	public List<ModelParamSetVO> queryAllNoPage(Map<String, Object> paramMap) {
		return modelParamSetDao.queryAllNoPage(paramMap);
	}
	
	@Override
	public String updateModelParam(Map<String, Object> paramMap) {
		return modelParamSetDao.updateById(paramMap);
	}

	@Override
	public boolean exportExcelData(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//UserLoginInfo loginUser = (UserLoginInfo) request.getSession().getAttribute("user");
		//获取当前用户所属地区,权限控制
		
		List<ModelParamSetVO> list = modelParamSetDao.queryAllNoPage(map);
		List<SysRegionVO> listreg=sysRegionDao.getAddress(map);//准备需要的数据处理
		ModelParamSetHandler tbh=new ModelParamSetHandler(listreg);//塔维租赁账单自定义数据处理
		tbh.setNeedHandlerFields(new String[]{DateDisposeComm.BELONG_REGION});//需要处理数据的列名 
		ExportParams params = new ExportParams(DateDisposeComm.BENCHMARK_PARAMETER_SETTING, DateDisposeComm.BENCHMARK_PARAMETER_SETTING, ExcelType.HSSF);
//		params.setExclusions(new String[]{"不需要的"});过滤属性
		params.setDataHanlder(tbh);
		org.apache.poi.ss.usermodel.Workbook workBook=ExcelExportUtil.exportExcel(params, ModelParamSetVO.class,list);
        FileUtils.downFile(workBook, DateDisposeComm.BENCHMARK_PARAMETER_SETTING_XLS, request, response);
		return true;
	}

	public IModelParamSetDao getModelParamSetDao() {
		return modelParamSetDao;
	}

	public void setModelParamSetDao(IModelParamSetDao modelParamSetDao) {
		this.modelParamSetDao = modelParamSetDao;
	}

	public ISysRegionDao getSysRegionDao() {
		return sysRegionDao;
	}

	public void setSysRegionDao(ISysRegionDao sysRegionDao) {
		this.sysRegionDao = sysRegionDao;
	}

	@Override
	public String insertModel(Map<String, Object> paramMap) {
		return modelParamSetDao.insertModel(paramMap);
	}

}
