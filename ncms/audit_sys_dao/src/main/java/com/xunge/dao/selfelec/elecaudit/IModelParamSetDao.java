package com.xunge.dao.selfelec.elecaudit;

import java.util.List;
import java.util.Map;

import com.xunge.core.page.Page;
import com.xunge.model.selfelec.vo.ModelParamSetVO;

public interface IModelParamSetDao {
	public Page<ModelParamSetVO> queryAll(Map<String,Object> paramMap);
	public List<ModelParamSetVO> queryAllNoPage(Map<String,Object> paramMap);
	public String updateById(Map<String,Object> paramMap);
    public void export();
    public String insertModel(Map<String,Object> paramMap);
}
