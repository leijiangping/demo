package com.xunge.service.selfelec.elecaudit;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunge.core.page.Page;
import com.xunge.model.selfelec.vo.ModelParamSetVO;

public interface IModelParamSetService {
	public Page<ModelParamSetVO> queryAllModel(Map<String,Object> paramMap);
	public List<ModelParamSetVO> queryAllNoPage(Map<String,Object> paramMap);
	public String updateModelParam(Map<String,Object> paramMap);
    public boolean exportExcelData(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response);
    public String insertModel(Map<String,Object> paramMap);
}
