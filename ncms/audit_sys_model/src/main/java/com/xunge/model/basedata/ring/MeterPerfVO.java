package com.xunge.model.basedata.ring;

import java.io.Serializable;
import java.util.Date;

public class MeterPerfVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4128265054565174520L;

    private String meterId;

    private String prvId;

    private String prvSname;

    private Integer resourceType;

    private String resourceCode;

    private Integer codeType;

    private String resourceName;

    private String pregId;

    private String pregName;

    private String regId;

    private String regName;

    private Date startTime;

    private Date stopTime;

    private Float totalDegree;

    private Integer totalState;

    private Float equipmentDegree;

    private Integer equipmentState;

    private Float acDegree;

    private Integer acState;

    private Float exTempreture;

    private Float inTempreture;
    
    private String queryDate;
    
    private String userId;

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId == null ? null : meterId.trim();
    }

    public String getPrvId() {
        return prvId;
    }

    public void setPrvId(String prvId) {
        this.prvId = prvId == null ? null : prvId.trim();
    }

    public String getPrvSname() {
        return prvSname;
    }

    public void setPrvSname(String prvSname) {
        this.prvSname = prvSname == null ? null : prvSname.trim();
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode == null ? null : resourceCode.trim();
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.replaceAll(" ", "");
    }

    public String getPregId() {
        return pregId;
    }

    public void setPregId(String pregId) {
        this.pregId = pregId == null ? null : pregId.trim();
    }

    public String getPregName() {
        return pregName;
    }

    public void setPregName(String pregName) {
        this.pregName = pregName == null ? null : pregName.trim();
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName == null ? null : regName.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public Float getTotalDegree() {
    	if(totalDegree==null){
    		totalDegree=new Float("0");
    	}
        return totalDegree;
    }

    public void setTotalDegree(Float totalDegree) {
        this.totalDegree = totalDegree;
    }

    public Integer getTotalState() {
        return totalState;
    }

    public void setTotalState(Integer totalState) {
        this.totalState = totalState;
    }

    public Float getEquipmentDegree() {
    	if(equipmentDegree==null){
    		equipmentDegree=new Float("0");
    	}
        return equipmentDegree;
    }

    public void setEquipmentDegree(Float equipmentDegree) {
        this.equipmentDegree = equipmentDegree;
    }

    public Integer getEquipmentState() {
        return equipmentState;
    }

    public void setEquipmentState(Integer equipmentState) {
        this.equipmentState = equipmentState;
    }

    public Float getAcDegree() {
    	if(acDegree==null){
    		acDegree=new Float("0");
    	}
        return acDegree;
    }

    public void setAcDegree(Float acDegree) {
        this.acDegree = acDegree;
    }

    public Integer getAcState() {
        return acState;
    }

    public void setAcState(Integer acState) {
        this.acState = acState;
    }

    public Float getExTempreture() {
    	if(exTempreture==null){
    		exTempreture=new Float("0");
    	}
        return exTempreture;
    }

    public void setExTempreture(Float exTempreture) {
        this.exTempreture = exTempreture;
    }

    public Float getInTempreture() {
    	if(inTempreture==null){
    		inTempreture=new Float("0");
    	}
        return inTempreture;
    }

    public void setInTempreture(Float inTempreture) {
        this.inTempreture = inTempreture;
    }
}