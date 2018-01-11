package com.xunge.model.system.province;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhujj
 * @date 2017年8月7日 下午4:06:10 
 * @version 1.0.0 
 */
public class SysProvinceGroupVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//省份分组
	private String prvGroup;
	private List<SysProvinceVO> sysProvince;
	public String getPrvGroup() {
		return prvGroup;
	}

	public void setPrvGroup(String prvGroup) {
		this.prvGroup = prvGroup;
	}

	public List<SysProvinceVO> getSysProvince() {
		return sysProvince;
	}

	public void setSysProvince(List<SysProvinceVO> sysProvince) {
		this.sysProvince = sysProvince;
	}
	
}
