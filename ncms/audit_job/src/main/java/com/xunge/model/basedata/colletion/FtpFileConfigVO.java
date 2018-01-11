package com.xunge.model.basedata.colletion;

public class FtpFileConfigVO {
    private String fileId;

    private String filePerfix;

    private Integer fieldConfig;

    private String fileDataType;

    private String taskId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getFilePerfix() {
        return filePerfix;
    }

    public void setFilePerfix(String filePerfix) {
        this.filePerfix = filePerfix == null ? null : filePerfix.trim();
    }

    public Integer getFieldConfig() {
        return fieldConfig;
    }

    public void setFieldConfig(Integer fieldConfig) {
        this.fieldConfig = fieldConfig;
    }

    public String getFileDataType() {
        return fileDataType;
    }

    public void setFileDataType(String fileDataType) {
        this.fileDataType = fileDataType == null ? null : fileDataType.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }
}