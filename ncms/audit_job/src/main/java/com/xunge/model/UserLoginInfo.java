package com.xunge.model;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户私有信息：
 * Session[Key=user]
 * @author SongJL
 */
public class UserLoginInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5566177639978759965L;
	
	/**
	 * 用户编码
	 */
	private String user_id;
	/**
	 * 用户代码
	 */
	private String user_code;
	/**
	 * 用户账号
	 */
	private String user_loginname;
	/**
	 * 用户姓名
	 */
	private String user_name;
	/**
	 * 用户级别
	 */
	private int user_class;
	/**
	 * 区域编码
	 */
	private String reg_id;
	/**
	 * 省份编码
	 */
	private String prv_id;
	/**
	 * 省份代码
	 */
	private String prv_code;
	/**
	 * click菜单ID
	 */
	private String oper_menu_id;
	
	/**
	 * 角色ID集合
	 */
	private List<String> role_ids;
	
	/**
	 * @description 当前用户管辖地市IDlist
	 * @author yuefy
	 * @date 创建时间：2017年8月3日
	 */
	private List<String> preg_ids;
	
	/**
	 * @description 当前用户管辖区县IDlist
	 * @author yuefy
	 * @date 创建时间：2017年8月3日
	 */
	private List<String> reg_ids;
	
	/**
	 * @description 当前用户 省份标识
	 * @author yuefy
	 * @date 创建时间：2017年8月8日
	 */
	private String prv_flag;
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_loginname() {
		return user_loginname;
	}

	public void setUser_loginname(String user_loginname) {
		this.user_loginname = user_loginname;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getUser_class() {
		return user_class;
	}

	public void setUser_class(int user_class) {
		this.user_class = user_class;
	}

	public String getReg_id() {
		return reg_id;
	}

	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}

	public String getPrv_id() {
		return prv_id;
	}

	public void setPrv_id(String prv_id) {
		this.prv_id = prv_id;
	}
	
	public List<String> getRole_ids() {
		return role_ids;
	}

	public void setRole_ids(List<String> role_ids) {
		this.role_ids = role_ids;
	}

	public String getOper_menu_id() {
		return oper_menu_id;
	}

	public void setOper_menu_id(String oper_menu_id) {
		this.oper_menu_id = oper_menu_id;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getPrv_code() {
		return prv_code;
	}

	public void setPrv_code(String prv_code) {
		this.prv_code = prv_code;
	}

	public List<String> getPreg_ids() {
		return preg_ids;
	}

	public void setPreg_ids(List<String> preg_ids) {
		this.preg_ids = preg_ids;
	}

	public List<String> getReg_ids() {
		return reg_ids;
	}

	public void setReg_ids(List<String> reg_ids) {
		this.reg_ids = reg_ids;
	}

	public String getPrv_flag() {
		return prv_flag;
	}

	public void setPrv_flag(String prv_flag) {
		this.prv_flag = prv_flag;
	}

	@Override
	public String toString() {
		return "UserLoginInfo [user_id=" + user_id + ", user_loginname="
				+ user_loginname + ", user_name=" + user_name + ", user_class="
				+ user_class + ", reg_id=" + reg_id + ", prv_id=" + prv_id
				+ ", oper_menu_id=" + oper_menu_id + ", role_id=" + role_ids + "]";
	}

	

}
