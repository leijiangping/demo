package com.xunge.model.basedata.colletion;

import java.io.Serializable;

public class FtpFileTypeVO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5369049788190696392L;

	private Integer typeId;

    private String typeCode;

    private String typeName;

    private Integer typeGroupId;

    private String typeGroupName;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Integer getTypeGroupId() {
        return typeGroupId;
    }

    public void setTypeGroupId(Integer typeGroupId) {
        this.typeGroupId = typeGroupId;
    }

    public String getTypeGroupName() {
        return typeGroupName;
    }

    public void setTypeGroupName(String typeGroupName) {
        this.typeGroupName = typeGroupName == null ? null : typeGroupName.trim();
    }
}