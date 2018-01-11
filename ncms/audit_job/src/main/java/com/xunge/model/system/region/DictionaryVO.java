package com.xunge.model.system.region;

import java.io.Serializable;


/**
 * 数据字典
 * @author wagnz
 * 2017-10-12 15:44:06
 */
public class DictionaryVO implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5074024602792131163L;
	
	private String dictgroupCode;//数据字典分组代码
	private String dictgroupName;//数据字典分组名称
	private String dictName;//数据字典分组编码 
	private String dictValue;//字典名称
	
	public String getDictgroupCode() {
		return dictgroupCode;
	}
	public void setDictgroupCode(String dictgroupCode) {
		this.dictgroupCode = dictgroupCode;
	}
	public String getDictgroupName() {
		return dictgroupName;
	}
	public void setDictgroupName(String dictgroupName) {
		this.dictgroupName = dictgroupName;
	}
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public String getDictValue() {
		return dictValue;
	}
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
}
