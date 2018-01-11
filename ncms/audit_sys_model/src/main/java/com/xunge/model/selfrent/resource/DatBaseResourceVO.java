package com.xunge.model.selfrent.resource;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.xunge.model.activity.Act;
import com.xunge.model.system.region.SysRegionVO;

/**
 * 资源信息（机房+资源点）VO
 * @author lpw
 * 2017年6月25日
 */
public class DatBaseResourceVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8841935543948503088L;
	private String baseresourceId;
	private String basesiteId;
	private String regId;
	private Integer baseResourceType;
	private Integer baseResourceCategory;
	private String baseResourceCuId;
	private String baseResourceCode;
	private String baseResourceName;
	private String baseResourceAddress;
	private BigDecimal baseResourceArea;
	private Date baseResourceOpenDate;
	private Date baseResourceStopDate;
	private Integer roomOwner;
	private Integer roomProperty;
	private Integer roomShare;
	private BigDecimal baseResourceLongItude;
	private BigDecimal baseResourceLatItude;
	private BigDecimal airConditionerPower;
	private Integer baseResourceState;
	private String baseResourceNote;
	private Integer dataFrom;
	 //区域信息对象
    private SysRegionVO sysRegionVO;//区域信息对象
    
    private Act act;
    
	public String getBaseresourceId() {
		return baseresourceId;
	}
	public void setBaseresourceId(String baseresourceId) {
		this.baseresourceId = baseresourceId;
	}
	public String getBasesiteId() {
		return basesiteId;
	}
	public void setBasesiteId(String basesiteId) {
		this.basesiteId = basesiteId;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public Integer getBaseResourceType() {
		return baseResourceType;
	}
	public void setBaseResourceType(Integer baseResourceType) {
		this.baseResourceType = baseResourceType;
	}
	public Integer getBaseResourceCategory() {
		return baseResourceCategory;
	}
	public void setBaseResourceCategory(Integer baseResourceCategory) {
		this.baseResourceCategory = baseResourceCategory;
	}
	public String getBaseResourceCuId() {
		return baseResourceCuId;
	}
	public void setBaseResourceCuId(String baseResourceCuId) {
		this.baseResourceCuId = baseResourceCuId;
	}
	public String getBaseResourceCode() {
		return baseResourceCode;
	}
	public void setBaseResourceCode(String baseResourceCode) {
		this.baseResourceCode = baseResourceCode;
	}
	public String getBaseResourceName() {
		return baseResourceName;
	}
	public void setBaseResourceName(String baseResourceName) {
		this.baseResourceName = baseResourceName;
	}
	public String getBaseResourceAddress() {
		return baseResourceAddress;
	}
	public void setBaseResourceAddress(String baseResourceAddress) {
		this.baseResourceAddress = baseResourceAddress;
	}
	public BigDecimal getBaseResourceArea() {
		return baseResourceArea;
	}
	public void setBaseResourceArea(BigDecimal baseResourceArea) {
		this.baseResourceArea = baseResourceArea;
	}
	public Date getBaseResourceOpenDate() {
		return baseResourceOpenDate;
	}
	public void setBaseResourceOpenDate(Date baseResourceOpenDate) {
		this.baseResourceOpenDate = baseResourceOpenDate;
	}
	public Date getBaseResourceStopDate() {
		return baseResourceStopDate;
	}
	public void setBaseResourceStopDate(Date baseResourceStopDate) {
		this.baseResourceStopDate = baseResourceStopDate;
	}
	public Integer getRoomOwner() {
		return roomOwner;
	}
	public void setRoomOwner(Integer roomOwner) {
		this.roomOwner = roomOwner;
	}
	public Integer getRoomProperty() {
		return roomProperty;
	}
	public void setRoomProperty(Integer roomProperty) {
		this.roomProperty = roomProperty;
	}
	public Integer getRoomShare() {
		return roomShare;
	}
	public void setRoomShare(Integer roomShare) {
		this.roomShare = roomShare;
	}
	public BigDecimal getBaseResourceLongItude() {
		return baseResourceLongItude;
	}
	public void setBaseResourceLongItude(BigDecimal baseResourceLongItude) {
		this.baseResourceLongItude = baseResourceLongItude;
	}
	public BigDecimal getBaseResourceLatItude() {
		return baseResourceLatItude;
	}
	public void setBaseResourceLatItude(BigDecimal baseResourceLatItude) {
		this.baseResourceLatItude = baseResourceLatItude;
	}
	public BigDecimal getAirConditionerPower() {
		return airConditionerPower;
	}
	public void setAirConditionerPower(BigDecimal airConditionerPower) {
		this.airConditionerPower = airConditionerPower;
	}
	public Integer getBaseResourceState() {
		return baseResourceState;
	}
	public void setBaseResourceState(Integer baseResourceState) {
		this.baseResourceState = baseResourceState;
	}
	public String getBaseResourceNote() {
		return baseResourceNote;
	}
	public void setBaseResourceNote(String baseResourceNote) {
		this.baseResourceNote = baseResourceNote;
	}
	public Integer getDataFrom() {
		return dataFrom;
	}
	public void setDataFrom(Integer dataFrom) {
		this.dataFrom = dataFrom;
	}
	public SysRegionVO getSysRegionVO() {
		return sysRegionVO;
	}
	public void setSysRegionVO(SysRegionVO sysRegionVO) {
		this.sysRegionVO = sysRegionVO;
	}
	@Override
	public String toString() {
		return "DatBaseresource [baseresourceId=" + baseresourceId
				+ ", basesiteId=" + basesiteId + ", regId=" + regId
				+ ", baseResourceType=" + baseResourceType
				+ ", baseResourceCategory=" + baseResourceCategory
				+ ", baseResourceCuId=" + baseResourceCuId
				+ ", baseResourceCode=" + baseResourceCode
				+ ", baseResourceName=" + baseResourceName
				+ ", baseResourceAddress=" + baseResourceAddress
				+ ", baseresource_area=" + baseResourceArea
				+ ", baseResourceOpenDate=" + baseResourceOpenDate
				+ ", baseResourceStopDate=" + baseResourceStopDate
				+ ", roomOwner=" + roomOwner + ", roomProperty=" + roomProperty
				+ ", roomShare=" + roomShare + ", baseResourceLongItude="
				+ baseResourceLongItude + ", baseResourceLatItude="
				+ baseResourceLatItude + ", airConditionerPower="
				+ airConditionerPower + ", baseResourceState="
				+ baseResourceState + ", baseResourceNote=" + baseResourceNote
				+ ", dataFrom=" + dataFrom + "]";
	}
	public Act getAct() {
		return act;
	}
	public void setAct(Act act) {
		this.act = act;
	}
	
}
