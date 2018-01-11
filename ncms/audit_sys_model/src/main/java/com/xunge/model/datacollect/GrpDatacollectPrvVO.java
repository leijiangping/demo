package com.xunge.model.datacollect;

import java.util.Date;

import com.xunge.model.system.region.SysRegionVO;

public class GrpDatacollectPrvVO {
    //省级上报编号
    private String datacollectPrvId;
    //集团收集表编号
    private String datacollectId;
    //省份编码
    private String prvId;
    //上报状态 固定枚举值（0：未上报，1：已上报）
    private Integer datacollectPrvState;
    //上报时间
    private String datacollectPrvDate;
    //上报人员
    private String datacollectPrvUser;
    //上报人员id
    private String datacollectPrvUserId;
    //上报备注
    private String datacollectPrvNote;
    //省级上报其他附件路径
    private String datacollectPrvOtherfilepath;
    //省级上报其他附件名称
    private String datacollectPrvOtherfilename;
    //集团收集表
    private GrpDatacollectVO grpDatacollectVO;
    //集团处理意见
    private String datacollectGroupOpinion;

    //省份名称 
  	private String prvName;
  	//可操作状态(0:可操作 1:不可操作)
  	private Integer runState;
   
  	public String getDatacollectPrvId() {
        return datacollectPrvId;
    }

    public void setDatacollectPrvId(String datacollectPrvId) {
        this.datacollectPrvId = datacollectPrvId == null ? null : datacollectPrvId.trim();
    }

    public String getDatacollectId() {
        return datacollectId;
    }

    public void setDatacollectId(String datacollectId) {
        this.datacollectId = datacollectId == null ? null : datacollectId.trim();
    }

    public String getPrvId() {
        return prvId;
    }

    public void setPrvId(String prvId) {
        this.prvId = prvId == null ? null : prvId.trim();
    }

    public Integer getDatacollectPrvState() {
        return datacollectPrvState;
    }

    public void setDatacollectPrvState(Integer datacollectPrvState) {
        this.datacollectPrvState = datacollectPrvState;
    }

    public String getDatacollectPrvUser() {
        return datacollectPrvUser;
    }

    public void setDatacollectPrvUser(String datacollectPrvUser) {
        this.datacollectPrvUser = datacollectPrvUser == null ? null : datacollectPrvUser.trim();
    }

	public GrpDatacollectVO getGrpDatacollectVO() {
		return grpDatacollectVO;
	}

	public void setGrpDatacollectVO(GrpDatacollectVO grpDatacollectVO) {
		this.grpDatacollectVO = grpDatacollectVO;
	}

	public String getPrvName() {
		return prvName;
	}

	public void setPrvName(String prvName) {
		this.prvName = prvName;
	}

	public String getDatacollectPrvNote() {
		return datacollectPrvNote;
	}

	public void setDatacollectPrvNote(String datacollectPrvNote) {
		this.datacollectPrvNote = datacollectPrvNote;
	}

	public String getDatacollectPrvOtherfilepath() {
		return datacollectPrvOtherfilepath;
	}

	public void setDatacollectPrvOtherfilepath(String datacollectPrvOtherfilepath) {
		this.datacollectPrvOtherfilepath = datacollectPrvOtherfilepath;
	}

	public String getDatacollectPrvOtherfilename() {
		return datacollectPrvOtherfilename;
	}

	public void setDatacollectPrvOtherfilename(String datacollectPrvOtherfilename) {
		this.datacollectPrvOtherfilename = datacollectPrvOtherfilename;
	}

	public String getDatacollectGroupOpinion() {
		return datacollectGroupOpinion;
	}

	public void setDatacollectGroupOpinion(String datacollectGroupOpinion) {
		this.datacollectGroupOpinion = datacollectGroupOpinion;
	}

	public String getDatacollectPrvUserId() {
		return datacollectPrvUserId;
	}

	public void setDatacollectPrvUserId(String datacollectPrvUserId) {
		this.datacollectPrvUserId = datacollectPrvUserId;
	}

	public Integer getRunState() {
		return runState;
	}

	public void setRunState(Integer runState) {
		this.runState = runState;
	}

	public String getDatacollectPrvDate() {
		return datacollectPrvDate;
	}

	public void setDatacollectPrvDate(String datacollectPrvDate) {
		this.datacollectPrvDate = datacollectPrvDate;
	}
}