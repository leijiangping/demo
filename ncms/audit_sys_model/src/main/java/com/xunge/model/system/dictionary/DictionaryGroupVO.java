package com.xunge.model.system.dictionary;

import java.io.Serializable;
import java.util.List;

public class DictionaryGroupVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 493013750072934248L;
	//数据字典分组编码
	private String dictgroup_id;
	//数据字典分组名称
	private String dictgroup_name;
	//数据字典分组代码
    private String dictgroup_code;
    //省份编码
    private String prv_id;
    //数据字典集合
    private List<DictionaryVO> lstDictionaryVO;
    
	public String getDictgroup_id() {
		return dictgroup_id;
	}

	public void setDictgroup_id(String dictgroup_id) {
		this.dictgroup_id = dictgroup_id;
	}

	public String getDictgroup_code() {
		return dictgroup_code;
	}

	public void setDictgroup_code(String dictgroup_code) {
		this.dictgroup_code = dictgroup_code;
	}

	public String getDictgroup_name() {
		return dictgroup_name;
	}

	public void setDictgroup_name(String dictgroup_name) {
		this.dictgroup_name = dictgroup_name;
	}

	public List<DictionaryVO> getLstDictionaryVO() {
		return lstDictionaryVO;
	}

	public void setLstDictionaryVO(List<DictionaryVO> lstDictionaryVO) {
		this.lstDictionaryVO = lstDictionaryVO;
	}

	public String getPrv_id() {
		return prv_id;
	}

	public void setPrv_id(String prv_id) {
		this.prv_id = prv_id;
	}
}
