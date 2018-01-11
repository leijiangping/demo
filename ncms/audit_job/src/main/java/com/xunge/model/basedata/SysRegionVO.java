package com.xunge.model.basedata;

import java.io.Serializable;

public class SysRegionVO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3923582434664159735L;

	private String regId;

    private String prvId;

    private String regCode;

    private String regName;

    private String pregId;

    private Integer regOrder;

    private Integer level;

    private Integer isValid;

    private Integer regState;
    
    private String twrRegName;

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public String getPrvId() {
        return prvId;
    }

    public void setPrvId(String prvId) {
        this.prvId = prvId == null ? null : prvId.trim();
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode == null ? null : regCode.trim();
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName == null ? null : regName.trim();
    }

    public String getPregId() {
        return pregId;
    }

    public void setPregId(String pregId) {
        this.pregId = pregId == null ? null : pregId.trim();
    }

    public Integer getRegOrder() {
        return regOrder;
    }

    public void setRegOrder(Integer regOrder) {
        this.regOrder = regOrder;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getRegState() {
        return regState;
    }

    public void setRegState(Integer regState) {
        this.regState = regState;
    }

	public String getTwrRegName() {
		return twrRegName;
	}

	public void setTwrRegName(String twrRegName) {
		this.twrRegName = twrRegName;
	}
}