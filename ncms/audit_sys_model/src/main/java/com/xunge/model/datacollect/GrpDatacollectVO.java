package com.xunge.model.datacollect;

import java.util.Date;

public class GrpDatacollectVO {
    //集团收集表编号
    private String datacollectId;
    //标题
    private String datacollectTitle;
    //处理时限
    private String datacollectDeadline;
    //正文
    private String datacollectNote;
    //派发状态 固定枚举值（0：未派发，1：已派发）
    private Integer datacollectState;
    //派发时间
    private String datacollectDate;
    //派发人
    private String datacollectUser;
    //集团派发抄送人员
    private String datacollectCopy;

    public String getDatacollectId() {
        return datacollectId;
    }

    public void setDatacollectId(String datacollectId) {
        this.datacollectId = datacollectId == null ? null : datacollectId.trim();
    }

    public String getDatacollectTitle() {
        return datacollectTitle;
    }

    public void setDatacollectTitle(String datacollectTitle) {
        this.datacollectTitle = datacollectTitle == null ? null : datacollectTitle.trim();
    }

    public String getDatacollectNote() {
        return datacollectNote;
    }

    public void setDatacollectNote(String datacollectNote) {
        this.datacollectNote = datacollectNote == null ? null : datacollectNote.trim();
    }

    public Integer getDatacollectState() {
        return datacollectState;
    }

    public void setDatacollectState(Integer datacollectState) {
        this.datacollectState = datacollectState;
    }

    public String getDatacollectUser() {
        return datacollectUser;
    }

    public void setDatacollectUser(String datacollectUser) {
        this.datacollectUser = datacollectUser == null ? null : datacollectUser.trim();
    }

	public String getDatacollectCopy() {
		return datacollectCopy;
	}

	public void setDatacollectCopy(String datacollectCopy) {
		this.datacollectCopy = datacollectCopy;
	}

	public String getDatacollectDeadline() {
		return datacollectDeadline;
	}

	public void setDatacollectDeadline(String datacollectDeadline) {
		this.datacollectDeadline = datacollectDeadline;
	}

	public String getDatacollectDate() {
		return datacollectDate;
	}

	public void setDatacollectDate(String datacollectDate) {
		this.datacollectDate = datacollectDate;
	}
}