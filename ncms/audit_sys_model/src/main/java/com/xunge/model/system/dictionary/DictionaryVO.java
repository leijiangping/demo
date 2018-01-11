package com.xunge.model.system.dictionary;

import java.io.Serializable;

public class DictionaryVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3914000927646132208L;
	//字典编码
	private String dict_id;
	//数据字典分组编码
	private String dictgroup_id;
	//数据字典分组名称
	private String dictgroup_name;
	//字典名称
	private String dict_name;
	//字典值
	private String dict_value;
	//字典状态
	private String dict_state;
	//字典备注
	private String dict_note;

	public String getDict_id() {
		return dict_id;
	}

	public void setDict_id(String dict_id) {
		this.dict_id = dict_id;
	}

	public String getDictgroup_id() {
		return dictgroup_id;
	}

	public void setDictgroup_id(String dictgroup_id) {
		this.dictgroup_id = dictgroup_id;
	}

	public String getDict_name() {
		return dict_name;
	}

	public void setDict_name(String dict_name) {
		this.dict_name = dict_name;
	}

	public String getDict_value() {
		return dict_value;
	}

	public void setDict_value(String dict_value) {
		this.dict_value = dict_value;
	}

	public String getDict_state() {
		return dict_state;
	}

	public void setDict_state(String dict_state) {
		this.dict_state = dict_state;
	}

	public String getDict_note() {
		return dict_note;
	}

	public void setDict_note(String dict_note) {
		this.dict_note = dict_note;
	}

	public String getDictgroup_name() {
		return dictgroup_name;
	}

	public void setDictgroup_name(String dictgroup_name) {
		this.dictgroup_name = dictgroup_name;
	}

}
