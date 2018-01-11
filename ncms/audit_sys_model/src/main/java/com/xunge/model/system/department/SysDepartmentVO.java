package com.xunge.model.system.department;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Geqs
 * 系统部门表
 */
public class SysDepartmentVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8528258680285320902L;
	//部门编码
    private String depId;
    //省份编码
    private String prvId;
    //部门代码
    private String depCode;
    //部门名称
    private String depName;
    //上级部门编码
    private String pdepId;
    //部门序号
    private Integer depOrder;
    //部门状态
    private Integer depState;
    //上级部门代码
    private String parentCode;
    
    private Boolean isAjaxing;
    private Boolean  isFirstNode;
    private Boolean  isLastNode;
    private Boolean  isParent;
    private Integer level;
    private Boolean open;
    private String parentTId;
    private String tId;
    private Boolean zAsync;
    //子部门集合
    private List<SysDepartmentVO> children;
    
    public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	private String parentName; 
    
    public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<SysDepartmentVO> getChildren() {
		return children;
	}

	public void setChildren(List<SysDepartmentVO> children) {
		this.children = children;
	}

	public Boolean getIsAjaxing() {
		return isAjaxing;
	}

	public void setIsAjaxing(Boolean isAjaxing) {
		this.isAjaxing = isAjaxing;
	}

	public Boolean getIsFirstNode() {
		return isFirstNode;
	}

	public void setIsFirstNode(Boolean isFirstNode) {
		this.isFirstNode = isFirstNode;
	}

	public Boolean getIsLastNode() {
		return isLastNode;
	}

	public void setIsLastNode(Boolean isLastNode) {
		this.isLastNode = isLastNode;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public String getParentTId() {
		return parentTId;
	}

	public void setParentTId(String parentTId) {
		this.parentTId = parentTId;
	}

	public String gettId() {
		return tId;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public Boolean getzAsync() {
		return zAsync;
	}

	public void setzAsync(Boolean zAsync) {
		this.zAsync = zAsync;
	}

	public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId == null ? null : depId.trim();
    }

    public String getPrvId() {
        return prvId;
    }

    public void setPrvId(String prvId) {
        this.prvId = prvId == null ? null : prvId.trim();
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode == null ? null : depCode.trim();
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }

    public String getPdepId() {
        return pdepId;
    }

    public void setPdepId(String pdepId) {
        this.pdepId = pdepId == null ? null : pdepId.trim();
    }

    public Integer getDepOrder() {
        return depOrder;
    }

    public void setDepOrder(Integer depOrder) {
        this.depOrder = depOrder;
    }

	public Integer getDepState() {
		return depState;
	}

	public void setDepState(Integer depState) {
		this.depState = depState;
	}

	@Override
	public String toString() {
		return "SysDepartmentVO [depId=" + depId + ", prvId=" + prvId
				+ ", depCode=" + depCode + ", depName=" + depName + ", pdepId="
				+ pdepId + ", depOrder=" + depOrder + ", depState=" + depState
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((depCode == null) ? 0 : depCode.hashCode());
		result = prime * result + ((depId == null) ? 0 : depId.hashCode());
		result = prime * result + ((depName == null) ? 0 : depName.hashCode());
		result = prime * result
				+ ((depOrder == null) ? 0 : depOrder.hashCode());
		result = prime * result
				+ ((depState == null) ? 0 : depState.hashCode());
		result = prime * result + ((pdepId == null) ? 0 : pdepId.hashCode());
		result = prime * result + ((prvId == null) ? 0 : prvId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysDepartmentVO other = (SysDepartmentVO) obj;
		if (depCode == null) {
			if (other.depCode != null)
				return false;
		} else if (!depCode.equals(other.depCode))
			return false;
		if (depId == null) {
			if (other.depId != null)
				return false;
		} else if (!depId.equals(other.depId))
			return false;
		if (depName == null) {
			if (other.depName != null)
				return false;
		} else if (!depName.equals(other.depName))
			return false;
		if (depOrder == null) {
			if (other.depOrder != null)
				return false;
		} else if (!depOrder.equals(other.depOrder))
			return false;
		if (depState == null) {
			if (other.depState != null)
				return false;
		} else if (!depState.equals(other.depState))
			return false;
		if (pdepId == null) {
			if (other.pdepId != null)
				return false;
		} else if (!pdepId.equals(other.pdepId))
			return false;
		if (prvId == null) {
			if (other.prvId != null)
				return false;
		} else if (!prvId.equals(other.prvId))
			return false;
		return true;
	}

}