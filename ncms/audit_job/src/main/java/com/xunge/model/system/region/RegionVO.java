package com.xunge.model.system.region;

import java.io.Serializable;
import java.util.List;


/**
 * 自定义地区vo
 * @author SongJL
 */
public class RegionVO implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5074024602792131163L;

	private String regId;

	private String regName;
    private String regCode;
    
    private Integer regOrder;
  	//上级行政区编码
  	private String  pRegId;
  	
  	private List<RegionVO> child;
  	
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public String getRegCode() {
		return regCode;
	}
	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}
	public Integer getRegOrder() {
		return regOrder;
	}
	public void setRegOrder(Integer regOrder) {
		this.regOrder = regOrder;
	}
	public String getpRegId() {
		return pRegId;
	}
	public void setpRegId(String pRegId) {
		this.pRegId = pRegId;
	}
	public List<RegionVO> getChild() {
		return child;
	}
	public void setChild(List<RegionVO> child) {
		this.child = child;
	}

}
