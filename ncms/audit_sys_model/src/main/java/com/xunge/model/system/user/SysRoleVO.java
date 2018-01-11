package com.xunge.model.system.user;

import java.io.Serializable;
import java.util.List;

import com.xunge.model.system.rolemenu.RoleMenuVO;
import com.xunge.model.system.roleuser.RoleUserVO;
/**
 * 角色菜单实体类
 * @author changwq
 *
 */
public class SysRoleVO implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = -347388343496266851L;
	//角色id	
	private String roleId;
	// 省份ID
    private String prvId;
	//角色编码
    private String roleCode;
	//角色名
    private String roleName;
	//角色备注
    private String roleNote;
	//角色菜单关系表对象
    private List<RoleMenuVO> lsrm;
	//用户角色关系表对象
    private List<RoleUserVO> lsru;
	//角色状态
    private Integer roleState;
 
    @Override
	public String toString() {
		return "SysRoleVO [roleId=" + roleId + ", prvId=" + prvId
				+ ", roleCode=" + roleCode + ", roleName=" + roleName
				+ ", roleNote=" + roleNote + ", roleState=" + roleState + "]";
	}

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getPrvId() {
        return prvId;
    }

    public void setPrvId(String prvId) {
        this.prvId = prvId == null ? null : prvId.trim();
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleNote() {
        return roleNote;
    }

    public void setRoleNote(String roleNote) {
        this.roleNote = roleNote == null ? null : roleNote.trim();
    }

    public Integer getRoleState() {
        return roleState;
    }

    public void setRoleState(Integer roleState) {
        this.roleState = roleState;
    }



	public List<RoleMenuVO> getLsrm() {
		return lsrm;
	}



	public void setLsrm(List<RoleMenuVO> lsrm) {
		this.lsrm = lsrm;
	}



	public List<RoleUserVO> getLsru() {
		return lsru;
	}



	public void setLsru(List<RoleUserVO> lsru) {
		this.lsru = lsru;
	}

	
}