package com.xunge.model.datacollect;

import java.util.Date;
import java.util.List;

public class GrpDatacollectHistoryVO{
	//集团派发-省级上报流转历史表
    private String grpDatacollectHistoryId;
    //省级上报编码
    private String datacollectPrvId;
    //历史记录内容
    private String historyMsg;
    //历史记录产生日期
    private String hisyoryDate;
    //历史记录创建角色编码
    private String historyCreateUserId;
    //省公司上传文件路径
    private String historyPrvFilePath;
    //省公司上传文件路径数组
    private List<String> historyPrvFilePathList;

    public String getGrpDatacollectHistoryId() {
        return grpDatacollectHistoryId;
    }

    public void setGrpDatacollectHistoryId(String grpDatacollectHistoryId) {
        this.grpDatacollectHistoryId = grpDatacollectHistoryId == null ? null : grpDatacollectHistoryId.trim();
    }

    public String getDatacollectPrvId() {
        return datacollectPrvId;
    }

    public void setDatacollectPrvId(String datacollectPrvId) {
        this.datacollectPrvId = datacollectPrvId == null ? null : datacollectPrvId.trim();
    }

    public String getHistoryMsg() {
        return historyMsg;
    }

    public void setHistoryMsg(String historyMsg) {
        this.historyMsg = historyMsg == null ? null : historyMsg.trim();
    }

    public String getHistoryCreateUserId() {
        return historyCreateUserId;
    }

    public void setHistoryCreateUserId(String historyCreateUserId) {
        this.historyCreateUserId = historyCreateUserId == null ? null : historyCreateUserId.trim();
    }

	public String getHistoryPrvFilePath() {
		return historyPrvFilePath;
	}

	public void setHistoryPrvFilePath(String historyPrvFilePath) {
		this.historyPrvFilePath = historyPrvFilePath;
	}

	public List<String> getHistoryPrvFilePathList() {
		return historyPrvFilePathList;
	}

	public void setHistoryPrvFilePathList(List<String> historyPrvFilePathList) {
		this.historyPrvFilePathList = historyPrvFilePathList;
	}

	public String getHisyoryDate() {
		return hisyoryDate;
	}

	public void setHisyoryDate(String hisyoryDate) {
		this.hisyoryDate = hisyoryDate;
	}

}