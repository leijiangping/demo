package com.xunge.model.colletion;

public class TaskInfoVO {
    private String taskId;

    private String taskName;

    private Integer taskType;

    private String taskDesc;

    private String taskTime;

    private Integer taskPeriod;

    private String taskCrontab;

    private Integer collectionType;

    private String collectionUrl;

    private String createTime;

    private String ftpUrl;

    private String ftpUser;

    private String ftpPassword;

    private String ftpFilepath;

    private Integer ftpPort;

    private Integer status;

    private String prvId;

    private String operateUserIp;

    private String operateUser;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc == null ? null : taskDesc.trim();
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime == null ? null : taskTime.trim();
    }

    public Integer getTaskPeriod() {
        return taskPeriod;
    }

    public void setTaskPeriod(Integer taskPeriod) {
        this.taskPeriod = taskPeriod;
    }

    public String getTaskCrontab() {
        return taskCrontab;
    }

    public void setTaskCrontab(String taskCrontab) {
        this.taskCrontab = taskCrontab;
    }

    public Integer getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(Integer collectionType) {
        this.collectionType = collectionType;
    }

    public String getCollectionUrl() {
        return collectionUrl;
    }

    public void setCollectionUrl(String collectionUrl) {
        this.collectionUrl = collectionUrl == null ? null : collectionUrl.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getFtpUrl() {
        return ftpUrl;
    }

    public void setFtpUrl(String ftpUrl) {
        this.ftpUrl = ftpUrl == null ? null : ftpUrl.trim();
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser == null ? null : ftpUser.trim();
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword == null ? null : ftpPassword.trim();
    }

    public String getFtpFilepath() {
        return ftpFilepath;
    }

    public void setFtpFilepath(String ftpFilepath) {
        this.ftpFilepath = ftpFilepath == null ? null : ftpFilepath.trim();
    }

    public Integer getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPrvId() {
        return prvId;
    }

    public void setPrvId(String prvId) {
        this.prvId = prvId == null ? null : prvId.trim();
    }

	public String getOperateUserIp() {
		return operateUserIp;
	}

	public void setOperateUserIp(String operateUserIp) {
		this.operateUserIp = operateUserIp;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	@Override
	public String toString() {
		return "TaskInfoVO [taskId=" + taskId + ", taskName=" + taskName
				+ ", taskType=" + taskType + ", taskDesc=" + taskDesc
				+ ", taskTime=" + taskTime + ", taskPeriod=" + taskPeriod
				+ ", taskCrontab=" + taskCrontab + ", collectionType="
				+ collectionType + ", collectionUrl=" + collectionUrl
				+ ", createTime=" + createTime + ", ftpUrl=" + ftpUrl + ", ftpUser=" + ftpUser
				+ ", ftpPassword=" + ftpPassword + ", ftpFilepath="
				+ ftpFilepath + ", ftpPort=" + ftpPort + ", status=" + status
				+ ", prvId=" + prvId + ", operateUserIp=" + operateUserIp
				+ ", operateUser=" + operateUser + "]";
	}
    
}