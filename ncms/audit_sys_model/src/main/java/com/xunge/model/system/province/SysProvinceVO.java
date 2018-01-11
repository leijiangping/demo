package com.xunge.model.system.province;

import java.io.Serializable;

public class SysProvinceVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5932632376333235305L;
	//省份id
	private String prvId;
	//省份编码
	private String prvCode;
	//排序号
	private Integer prvOrder;
	//省份简称
	private String prvSname;
	//省份名称
	private String prvName;
	//省份状态
	private Integer prvState;
	//省份标记
	private String prvFlag;
	//省份分组
	private String prvGroup;

	public String getPrvId() {
		return prvId;
	}

	public void setPrvId(String prvId) {
		this.prvId = prvId;
	}

	public String getPrvCode() {
		return prvCode;
	}

	public void setPrvCode(String prvCode) {
		this.prvCode = prvCode;
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
		this.prvSname = prvSname;
	}

	public String getPrvName() {
		return prvName;
	}

	public void setPrvName(String prvName) {
		this.prvName = prvName;
	}

	public Integer getPrvState() {
		return prvState;
	}

	public void setPrvState(Integer prvState) {
		this.prvState = prvState;
	}

	public String getPrvGroup() {
		return prvGroup;
	}

	public void setPrvGroup(String prvGroup) {
		this.prvGroup = prvGroup;
	}

	public String getPrvFlag() {
		return prvFlag;
	}

	public void setPrvFlag(String prvFlag) {
		this.prvFlag = prvFlag;
	}

	public SysProvinceVO(String prvId, String prvCode, Integer prvOrder,
			String prvSname, String prvName, Integer prvState) {
		super();
		this.prvId = prvId;
		this.prvCode = prvCode;
		this.prvOrder = prvOrder;
		this.prvSname = prvSname;
		this.prvName = prvName;
		this.prvState = prvState;
	}

	public SysProvinceVO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
