package com.xunge.model.system.userregion;

import java.io.Serializable;
import java.util.Date;



public class UserRegionVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8589196512415235444L;
	//用户区县关系id
	private String userRegionId;
	//用户id
	private String userId;
	//区县id
	private String regId;
	//关系状态
	private Integer relationState;
	//关系开始日期
	private Date relationStartDate;
	//关系结束日期
	private Date relationEndDate;
	@Override
	public String toString() {
		return "UserRegionVO [userRegionId=" + userRegionId + ", userId="
				+ userId + ", regId=" + regId + ", relationState="
				+ relationState + ", relationStartDate=" + relationStartDate
				+ ", relationEndDate=" + relationEndDate + "]";
	}
	public String getUserRegionId() {
		return userRegionId;
	}
	public void setUserRegionId(String userRegionId) {
		this.userRegionId = userRegionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Integer getRelationState() {
		return relationState;
	}
	public void setRelationState(Integer relationState) {
		this.relationState = relationState;
	}
	public Date getRelationStartDate() {
		return relationStartDate;
	}
	public void setRelationStartDate(Date relationStartDate) {
		this.relationStartDate = relationStartDate;
	}
	public Date getRelationEndDate() {
		return relationEndDate;
	}
	public void setRelationEndDate(Date relationEndDate) {
		this.relationEndDate = relationEndDate;
	}
	public UserRegionVO(String userRegionId, String userId, String regId,
			Integer relationState, Date relationStartDate, Date relationEndDate) {
		super();
		this.userRegionId = userRegionId;
		this.userId = userId;
		this.regId = regId;
		this.relationState = relationState;
		this.relationStartDate = relationStartDate;
		this.relationEndDate = relationEndDate;
	}
	public UserRegionVO(String userId, String regId) {
		super();
		this.userId = userId;
		this.regId = regId;
	}
	public UserRegionVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserRegionVO(String userRegionId, String userId, String regId) {
		super();
		this.userRegionId = userRegionId;
		this.userId = userId;
		this.regId = regId;
	}
	
	
	
}
