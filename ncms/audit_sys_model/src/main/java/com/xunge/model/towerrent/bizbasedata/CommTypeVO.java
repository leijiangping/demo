package com.xunge.model.towerrent.bizbasedata;

import java.io.Serializable;
/**
 * @author jcy
 * @date 2017年7月6日
 * @description 公共字典
 */
public class CommTypeVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2839939840294857878L;
	
	//铁塔公共字典编码
	private String commtype_id ;	
	//铁塔公共字典名称
	private String commtype_name;
	// ROOM_TYPE：机房类型 SUPT_TYPE：配套类型   SHAR_TYPE：共享信息  RESU_TYPE：资源类型
	private String commtype_group;	
	//铁塔公共字典状态 0：正常 9：停用 -1：删除
	private int commtype_state;
	// 铁塔公共字典备注
	private String commtype_note;
	
	public String getCommtype_id() {
		return commtype_id;
	}
	public void setCommtype_id(String commtype_id) {
		this.commtype_id = commtype_id;
	}
	public String getCommtype_name() {
		return commtype_name;
	}
	public void setCommtype_name(String commtype_name) {
		this.commtype_name = commtype_name;
	}
	public String getCommtype_group() {
		return commtype_group;
	}
	public void setCommtype_group(String commtype_group) {
		this.commtype_group = commtype_group;
	}
	public int getCommtype_state() {
		return commtype_state;
	}
	public void setCommtype_state(int commtype_state) {
		this.commtype_state = commtype_state;
	}
	public String getCommtype_note() {
		return commtype_note;
	}
	public void setCommtype_note(String commtype_note) {
		this.commtype_note = commtype_note;
	}
}
