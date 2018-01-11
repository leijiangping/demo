package com.xunge.model.system.rolemenu;

import java.io.Serializable;

public class RoleMenuVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3037474123072589278L;
	//角色菜单关系id
	private String roleMenuId;
	//角色id
	private String roleId;
	//菜单id
	private String menuId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public RoleMenuVO(String roleId, String menuId) {
		super();
		this.roleId = roleId;
		this.menuId = menuId;
	}

	public RoleMenuVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getRoleMenuId() {
		return roleMenuId;
	}

	public void setRoleMenuId(String roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	public RoleMenuVO(String roleMenuId, String roleId, String menuId) {
		super();
		this.roleMenuId = roleMenuId;
		this.roleId = roleId;
		this.menuId = menuId;
	}

	@Override
	public String toString() {
		return "RoleMenuVO [roleMenuId=" + roleMenuId + ", roleId=" + roleId
				+ ", menuId=" + menuId + "]";
	}
}
