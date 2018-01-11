package com.xunge.model.system.roleuser;

import java.io.Serializable;
import java.util.Date;

import com.xunge.model.system.user.SysRoleVO;
/**
 * 用户与角色关系表VO类
 */
public class RoleUserVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9143287204059313124L;
	//角色用户id
	private String roleUserId;
	//角色编码
	private String roleId;
	//用户编码
	private String userId;
	//角色人员关系状态（0：正常   9：停用 ）
	private Integer relationState;
	//关系开始日期
	private Date relationStartdate;
	//关系结束日期
	private Date relationEnddate;
	//角色对象
	private SysRoleVO role;
	
	public String getRoleUserId() {
		return roleUserId;
	}
	public void setRoleUserId(String roleUserId) {
		this.roleUserId = roleUserId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getRelationState() {
		return relationState;
	}
	public void setRelationState(Integer relationState) {
		this.relationState = relationState;
	}
	public Date getRelationStartdate() {
		return relationStartdate;
	}
	public void setRelationStartdate(Date relationStartdate) {
		this.relationStartdate = relationStartdate;
	}
	public Date getRelationEnddate() {
		return relationEnddate;
	}
	public void setRelationEnddate(Date relationEnddate) {
		this.relationEnddate = relationEnddate;
	}
	public SysRoleVO getRole() {
		return role;
	}
	public void setRole(SysRoleVO role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "RoleUserVO [roleUserId=" + roleUserId + ", roleId=" + roleId
				+ ", userId=" + userId + ", relationState=" + relationState
				+ ", relationStartdate=" + relationStartdate
				+ ", relationEnddate=" + relationEnddate + "]";
	}
	public RoleUserVO(String roleUserId, String roleId, String userId,
			Integer relationState, Date relationStartdate, Date relationEnddate) {
		super();
		this.roleUserId = roleUserId;
		this.roleId = roleId;
		this.userId = userId;
		this.relationState = relationState;
		this.relationStartdate = relationStartdate;
		this.relationEnddate = relationEnddate;
	}
	public RoleUserVO(String roleId, String userId) {
		super();
		this.roleId = roleId;
		this.userId = userId;
	}
	public RoleUserVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
