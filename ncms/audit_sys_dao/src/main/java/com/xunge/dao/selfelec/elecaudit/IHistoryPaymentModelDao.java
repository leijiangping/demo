package com.xunge.dao.selfelec.elecaudit;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.selfelec.vo.HistoryPaymentModelVO;

public interface IHistoryPaymentModelDao {
	public Page<HistoryPaymentModelVO> queryAll(Map<String,Object> paramMap);
	public List<HistoryPaymentModelVO> queryAllNoPage(Map<String,Object> paramMap);
    public void export();
}
