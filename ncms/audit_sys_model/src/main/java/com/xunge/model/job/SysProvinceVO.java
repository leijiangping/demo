package com.xunge.model.job;

import java.io.Serializable;

public class SysProvinceVO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2027076189985594708L;

	private String prvId;

    private String prvCode;

    private Integer prvOrder;

    private String prvSname;

    private String prvName;

    private Integer prvState;

    public String getPrvId() {
        return prvId;
    }

    public void setPrvId(String prvId) {
        this.prvId = prvId == null ? null : prvId.trim();
    }

    public String getPrvCode() {
        return prvCode;
    }

    public void setPrvCode(String prvCode) {
        this.prvCode = prvCode == null ? null : prvCode.trim();
    }

    public Integer getPrvOrder() {
        return prvOrder;
    }

    public void setPrvOrder(Integer prvOrder) {
        this.prvOrder = prvOrder;
    }

    public String getPrvSname() {
        return prvSname;
    }

    public void setPrvSname(String prvSname) {
        this.prvSname = prvSname == null ? null : prvSname.trim();
    }

    public String getPrvName() {
        return prvName;
    }

    public void setPrvName(String prvName) {
        this.prvName = prvName == null ? null : prvName.trim();
    }

    public Integer getPrvState() {
        return prvState;
    }

    public void setPrvState(Integer prvState) {
        this.prvState = prvState;
    }
}