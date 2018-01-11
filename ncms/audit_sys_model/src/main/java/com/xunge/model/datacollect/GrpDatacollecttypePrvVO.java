package com.xunge.model.datacollect;

public class GrpDatacollecttypePrvVO {
    //省级上报文件编号
    private String datacollecttypePrvId;
    //省级上报编号
    private String datacollectPrvId;
    //集团收集表类型编号
    private String datacollecttypeId;
    //省级上报文件路径
    private String datacollecttypePrvFilepath;
    //固定枚举值（0：未入库1：已入库）
    private Integer datacollecttypePrvState;
    //省级上报文件名称
    private String datacollecttypePrvFilename;
   
    //关联集团收集数据类型
    private GrpDatacollecttypeVO grpDatacollecttypeVO;

    public String getDatacollecttypePrvId() {
        return datacollecttypePrvId;
    }

    public void setDatacollecttypePrvId(String datacollecttypePrvId) {
        this.datacollecttypePrvId = datacollecttypePrvId == null ? null : datacollecttypePrvId.trim();
    }

    public String getDatacollectPrvId() {
        return datacollectPrvId;
    }

    public void setDatacollectPrvId(String datacollectPrvId) {
        this.datacollectPrvId = datacollectPrvId == null ? null : datacollectPrvId.trim();
    }

    public String getDatacollecttypeId() {
        return datacollecttypeId;
    }

    public void setDatacollecttypeId(String datacollecttypeId) {
        this.datacollecttypeId = datacollecttypeId == null ? null : datacollecttypeId.trim();
    }

    public String getDatacollecttypePrvFilepath() {
        return datacollecttypePrvFilepath;
    }

    public void setDatacollecttypePrvFilepath(String datacollecttypePrvFilepath) {
        this.datacollecttypePrvFilepath = datacollecttypePrvFilepath == null ? null : datacollecttypePrvFilepath.trim();
    }

    public Integer getDatacollecttypePrvState() {
        return datacollecttypePrvState;
    }

    public void setDatacollecttypePrvState(Integer datacollecttypePrvState) {
        this.datacollecttypePrvState = datacollecttypePrvState;
    }

	public GrpDatacollecttypeVO getGrpDatacollecttypeVO() {
		return grpDatacollecttypeVO;
	}

	public void setGrpDatacollecttypeVO(GrpDatacollecttypeVO grpDatacollecttypeVO) {
		this.grpDatacollecttypeVO = grpDatacollecttypeVO;
	}

	public String getDatacollecttypePrvFilename() {
		return datacollecttypePrvFilename;
	}

	public void setDatacollecttypePrvFilename(String datacollecttypePrvFilename) {
		this.datacollecttypePrvFilename = datacollecttypePrvFilename;
	}
}