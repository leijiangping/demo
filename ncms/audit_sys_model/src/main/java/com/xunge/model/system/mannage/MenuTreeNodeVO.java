package com.xunge.model.system.mannage;

import java.io.Serializable;
import java.util.List;

import com.xunge.model.SysDataAuthMenuTreeVO;

/**
 * 系统配置管理-功能菜单管理-树形菜单结点VO
 * @author SongJL
 *
 */
public class MenuTreeNodeVO extends SysDataAuthMenuTreeVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4129184688995637848L;
	/**
	 * 菜单code
	 */
	private String code;
	/**
	 * 排序
	 */
	private String order;
	/**
	 * 数据库配置路径
	 */
	private String linkUrl;
	/**
	 * 状态
	 */
	private String state;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
