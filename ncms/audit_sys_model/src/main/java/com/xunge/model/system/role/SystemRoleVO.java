package com.xunge.model.system.role;

import java.io.Serializable;
import java.util.List;

import com.xunge.model.system.roleuser.RoleUserVO;

/**
 * 角色实体类
 * @author xup
 *
 */
public class SystemRoleVO implements Serializable {
   
	private static final long serialVersionUID = 4300304319359666323L;
	//角色id
	private String roleId;
	//省份id
    private String prvId;
	//角色编码
    private String roleCode;
	//角色名
    private String roleName;
	//角色备注
    private String roleNote;
	//用户数
    private Integer roleuserNum;
    //角色状态
    private String roleState;
	//角色用户关系表对象
    private List<RoleUserVO> roleUser;

	@Override
	public String toString() {
		return "SystemRoleVO [roleId=" + roleId + ", prvId=" + prvId
				+ ", roleCode=" + roleCode + ", roleName=" + roleName
				+ ", roleNote=" + roleNote + ", roleuserNum=" + roleuserNum
				+ ", roleState=" + roleState + ", roleUser=" + roleUser + "]";
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

    public String getRoleState() {
        return roleState;
    }

    public void setRoleState(String roleState) {
        this.roleState = roleState;
    }


	public List<RoleUserVO> getRoleUser() {
		return roleUser;
	}


	public void setRoleUser(List<RoleUserVO> roleUser) {
		this.roleUser = roleUser;
	}

	public Integer getRoleuserNum() {
		return roleuserNum;
	}

	public void setRoleuserNum(Integer roleuserNum) {
		this.roleuserNum = roleuserNum;
	}



}