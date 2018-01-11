package com.xunge.model.basedata;

/**
 * @description 设备制造商 
 * @author yuefy
 * @date 创建时间：2017年9月20日
 */
public class DatDevicefactoryVO {
	//id
    private Integer devicefactoryId;
    //名称
    private String devicefactoryName;
    //备注
    private String devicefactoryNote;

    public Integer getDevicefactoryId() {
        return devicefactoryId;
    }

    public void setDevicefactoryId(Integer devicefactoryId) {
        this.devicefactoryId = devicefactoryId;
    }

    public String getDevicefactoryName() {
        return devicefactoryName;
    }

    public void setDevicefactoryName(String devicefactoryName) {
        this.devicefactoryName = devicefactoryName == null ? null : devicefactoryName.trim();
    }

    public String getDevicefactoryNote() {
        return devicefactoryNote;
    }

    public void setDevicefactoryNote(String devicefactoryNote) {
        this.devicefactoryNote = devicefactoryNote == null ? null : devicefactoryNote.trim();
    }
}