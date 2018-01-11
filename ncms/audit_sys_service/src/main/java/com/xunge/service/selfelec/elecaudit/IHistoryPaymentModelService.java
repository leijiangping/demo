package com.xunge.service.selfelec.elecaudit;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunge.core.page.Page;
import com.xunge.model.selfelec.vo.HistoryPaymentModelVO;

public interface IHistoryPaymentModelService {

    public Page<HistoryPaymentModelVO> queryAll(Map<String,Object> paramMap);
    public List<HistoryPaymentModelVO> queryAllNoPage(Map<String,Object> paramMap);
    public boolean exportExcelData(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response);
}
