package com.xunge.model.datacollect;

public class GrpDatacollecttypeVO {
    //集团收集表类型编号
    private String datacollecttypeId;
    //集团收集表编号
    private String datacollectId;
    //集团收集数据类型
    private String datacollecttempType;
    //附件模板路径
    private String datacollecttempFilepath;
    //集团下派文件名称
    private String datacollecttempFilename;

    public String getDatacollecttypeId() {
        return datacollecttypeId;
    }

    public void setDatacollecttypeId(String datacollecttypeId) {
        this.datacollecttypeId = datacollecttypeId == null ? null : datacollecttypeId.trim();
    }

    public String getDatacollectId() {
        return datacollectId;
    }

    public void setDatacollectId(String datacollectId) {
        this.datacollectId = datacollectId == null ? null : datacollectId.trim();
    }

    public String getDatacollecttempType() {
        return datacollecttempType;
    }

    public void setDatacollecttempType(String datacollecttempType) {
        this.datacollecttempType = datacollecttempType == null ? null : datacollecttempType.trim();
    }

    public String getDatacollecttempFilepath() {
        return datacollecttempFilepath;
    }

    public void setDatacollecttempFilepath(String datacollecttempFilepath) {
        this.datacollecttempFilepath = datacollecttempFilepath == null ? null : datacollecttempFilepath.trim();
    }

	public String getDatacollecttempFilename() {
		return datacollecttempFilename;
	}

	public void setDatacollecttempFilename(String datacollecttempFilename) {
		this.datacollecttempFilename = datacollecttempFilename;
	}
}