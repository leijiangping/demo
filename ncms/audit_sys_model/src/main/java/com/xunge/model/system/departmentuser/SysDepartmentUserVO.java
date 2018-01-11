package com.xunge.model.system.departmentuser;

import java.io.Serializable;
import java.util.Date;

import com.xunge.model.system.department.SysDepartmentVO;
/**
 * 用户与部门关系表VO类
 */
public class SysDepartmentUserVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2961903054109856710L;

	//部门用户编码
	private String depUserId;
	
	//部门编码
	private String depId;
	//用户编码
	private String userId;
	//部门人员关系状态（0：正常   9：停用 ）
    private Integer relationState;
    //关系开始日期
    private Date relationStartdate;
    //关系结束日期
	private Date relationEnddate;
    //部门对象	
	private SysDepartmentVO sysDepartmentVO;
	
	public SysDepartmentUserVO(String depId, String userId) {
		super();
		this.depId = depId;
		this.userId = userId;
	}


    public SysDepartmentUserVO(String depId, String userId,
			Integer relationState, Date relationStartdate,
			Date relationEnddate, SysDepartmentVO sysDepartmentVO) {
		super();
		this.depId = depId;
		this.userId = userId;
		this.relationState = relationState;
		this.relationStartdate = relationStartdate;
		this.relationEnddate = relationEnddate;
		this.sysDepartmentVO = sysDepartmentVO;
	}

	public SysDepartmentUserVO() {
		super();
	}


	public String getDepUserId() {
		return depUserId;
	}


	public void setDepUserId(String depUserId) {
		this.depUserId = depUserId;
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

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SysDepartmentVO getsysDepartmentVO() {
		return sysDepartmentVO;
	}

	public void setsysDepartmentVO(SysDepartmentVO sysDepartmentVO) {
		this.sysDepartmentVO = sysDepartmentVO;
	}

	@Override
	public String toString() {
		return "SysDepartmentUserVO [depId=" + depId + ", userId=" + userId
				+ ", relationState=" + relationState + ", relationStartdate="
				+ relationStartdate + ", relationEnddate=" + relationEnddate
				+ "]";
	}
    
}