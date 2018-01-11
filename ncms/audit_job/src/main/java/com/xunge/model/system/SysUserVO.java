package com.xunge.model.system;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author yuefy
 *	用户信息VO类
 */
public class SysUserVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7826547219341512522L;
	//用户编码
    private String userId;
    //区域编码
    private String regId;
    //用户代码
    private String userCode;
    //用户账号
    private String userLoginname;
    //用户姓名
    private String userName;
    //用户密码
    private String userPassword;
    //用户电话
    private String userPhone;
    //用户电子邮件
    private String userEmail;
    //用户备注
    private String userNote;
    //用户状态（0：正常   9：停用  -1:已删除）
    private Integer userState;
    //创建时间
    private Date createTime;
    //修改时间
    private Date modifyTime;
    //有效性
    private Integer isValid;
    //用户级别    0：省级用户   1：集团用户
    private int userClass;

	@Override
	public String toString() {
		return "SysUserVO [userId=" + userId + ", regId=" + regId
				+ ", userCode=" + userCode + ", userLoginname=" + userLoginname
				+ ", userName=" + userName + ", userPassword=" + userPassword
				+ ", userPhone=" + userPhone + ", userEmail=" + userEmail
				+ ", userNote=" + userNote + ", userState=" + userState
				+ ", createTime=" + createTime + ", modifyTime=" + modifyTime
				+ ", isValid=" + isValid + ", userClass=" + userClass
				+ "]";
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

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserLoginname() {
		return userLoginname;
	}

	public void setUserLoginname(String userLoginname) {
		this.userLoginname = userLoginname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserNote() {
		return userNote;
	}

	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public int getUserClass() {
		return userClass;
	}

	public void setUserClass(int userClass) {
		this.userClass = userClass;
	}
}