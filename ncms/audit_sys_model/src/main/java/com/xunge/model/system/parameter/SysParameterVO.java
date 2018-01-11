package com.xunge.model.system.parameter;

import java.io.Serializable;

public class SysParameterVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4110639478054342071L;
	//参数编码
	private String paraId;
	//省份编码
	private String prvId;
	//参数代码
	private String paraCode;
	//参数值
	private String paraValue;
	//参数说明
	private String paraNote;
	//参数状态
	private String paraState;
	
	public String getParaId() {
		return paraId;
	}
	public void setParaId(String paraId) {
		this.paraId = paraId;
	}
	public String getPrvId() {
		return prvId;
	}
	public void setPrvId(String prvId) {
		this.prvId = prvId;
	}
	public String getParaCode() {
		return paraCode;
	}
	public void setParaCode(String paraCode) {
		this.paraCode = paraCode;
	}
	public String getParaValue() {
		return paraValue;
	}
	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}
	public String getParaNote() {
		return paraNote;
	}
	public void setParaNote(String paraNote) {
		this.paraNote = paraNote;
	}
	public String getParaState() {
		return paraState;
	}
	public void setParaState(String paraState) {
		this.paraState = paraState;
	}
	public SysParameterVO(String paraId, String prvId, String paraCode,
			String paraValue, String paraNote, String paraState) {
		super();
		this.paraId = paraId;
		this.prvId = prvId;
		this.paraCode = paraCode;
		this.paraValue = paraValue;
		this.paraNote = paraNote;
		this.paraState = paraState;
	}
	public SysParameterVO(String paraId, String prvId) {
		super();
		this.paraId = paraId;
		this.prvId = prvId;
	}
	public SysParameterVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SysParameterVO(String paraCode, String paraValue, String paraNote) {
		super();
		this.paraCode = paraCode;
		this.paraValue = paraValue;
		this.paraNote = paraNote;
	}
	
	
}
