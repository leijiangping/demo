package com.xunge.model.basedata;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xunge.model.BaseActVO;
import com.xunge.model.activity.Act;
import com.xunge.util.StrUtil;

public class DatBaseresourceVO extends BaseActVO{
    private String baseresourceId;

    private String basesiteId;

    private String prvId;

    private String prvSname;

    private String pregId;

    private String pregName;

    private String regId;

    private String regName;

    private Integer baseresourceType;

    private String baseresourceCategory;

    private String baseresourceCuid;
    
    private String basesiteCuid;

    private String baseresourceCode;

    private String baseresourceName;

    private String baseresourceAddress;

    private BigDecimal baseresourceArea;

    private Integer baseresourceMaintenance;

    private String towerSiteCode;

    private Date baseresourceOpendate;

    private Date baseresourceStopdate;

    private String roomOwner;

    private Integer roomProperty;

    private Integer roomShare;

    private BigDecimal baseresourceLongitude;

    private BigDecimal baseresourceLatitude;

    private BigDecimal airconditionerPower;

    private Integer baseresourceState;

    private String baseresourceNote;

    private Integer auditingState;

    private String auditingUserId;

    private Date auditingDate;

    private Integer dataFrom;
    
    private List<DatBasestationVO> datBasestationVO;
    
    private Act act;
    
    private String create_user;
    private String create_ip;
    private Date create_time;
    private String update_user;
    private String update_ip;
    private Date update_time;
    
    public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	public String getBaseresourceId() {
        return baseresourceId;
    }

    public void setBaseresourceId(String baseresourceId) {
        this.baseresourceId = baseresourceId == null ? null : baseresourceId.trim();
    }

    public String getBasesiteId() {
        return basesiteId;
    }

    public void setBasesiteId(String basesiteId) {
        this.basesiteId = basesiteId == null ? null : basesiteId.trim();
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

    public Integer getBaseresourceType() {
        return baseresourceType;
    }

    public void setBaseresourceType(Integer baseresourceType) {
        this.baseresourceType = baseresourceType;
    }

    public String getBaseresourceCategory() {
        return baseresourceCategory;
    }

    public void setBaseresourceCategory(String baseresourceCategory) {
        this.baseresourceCategory = baseresourceCategory;
    }

    public String getBaseresourceCuid() {
        return baseresourceCuid;
    }

    public void setBaseresourceCuid(String baseresourceCuid) {
        this.baseresourceCuid = baseresourceCuid == null ? null : baseresourceCuid.trim();
    }

    public String getBaseresourceCode() {
        return baseresourceCode;
    }

    public void setBaseresourceCode(String baseresourceCode) {
        this.baseresourceCode = baseresourceCode == null ? null : baseresourceCode.trim();
    }

    public String getBaseresourceName() {
        return baseresourceName;
    }

    public void setBaseresourceName(String baseresourceName) {
        this.baseresourceName = baseresourceName == null ? null : baseresourceName.trim();
    }

    public String getBaseresourceAddress() {
        return baseresourceAddress;
    }

    public void setBaseresourceAddress(String baseresourceAddress) {
        this.baseresourceAddress = baseresourceAddress == null ? null : baseresourceAddress.trim();
    }

    public BigDecimal getBaseresourceArea() {
        return baseresourceArea;
    }

    public void setBaseresourceArea(BigDecimal baseresourceArea) {
        this.baseresourceArea = baseresourceArea;
    }

    public Integer getBaseresourceMaintenance() {
        return baseresourceMaintenance;
    }

    public void setBaseresourceMaintenance(Integer baseresourceMaintenance) {
        this.baseresourceMaintenance = baseresourceMaintenance;
    }

    public String getTowerSiteCode() {
        return towerSiteCode;
    }

    public void setTowerSiteCode(String towerSiteCode) {
        this.towerSiteCode = towerSiteCode == null ? null : towerSiteCode.trim();
    }

    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8") 
    public Date getBaseresourceOpendate() {
        return baseresourceOpendate;
    }

    public void setBaseresourceOpendate(Date baseresourceOpendate) {
        this.baseresourceOpendate = baseresourceOpendate;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8") 
    public Date getBaseresourceStopdate() {
        return baseresourceStopdate;
    }

    public void setBaseresourceStopdate(Date baseresourceStopdate) {
        this.baseresourceStopdate = baseresourceStopdate;
    }

    public String getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(String roomOwner) {
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

    public void setRoomShare(String roomShare) {
        this.roomShare = Integer.parseInt(StrUtil.isBlank(roomShare.trim())?"0":roomShare);
    }

    public BigDecimal getBaseresourceLongitude() {
        return baseresourceLongitude;
    }

    public void setBaseresourceLongitude(BigDecimal baseresourceLongitude) {
        this.baseresourceLongitude = baseresourceLongitude;
    }

    public BigDecimal getBaseresourceLatitude() {
        return baseresourceLatitude;
    }

    public void setBaseresourceLatitude(BigDecimal baseresourceLatitude) {
        this.baseresourceLatitude = baseresourceLatitude;
    }

    public BigDecimal getAirconditionerPower() {
        return airconditionerPower;
    }

    public void setAirconditionerPower(BigDecimal airconditionerPower) {
        this.airconditionerPower = airconditionerPower;
    }

    public Integer getBaseresourceState() {
        return baseresourceState;
    }

    public void setBaseresourceState(Integer baseresourceState) {
        this.baseresourceState = baseresourceState;
    }

    public String getBaseresourceNote() {
        return baseresourceNote;
    }

    public void setBaseresourceNote(String baseresourceNote) {
        this.baseresourceNote = baseresourceNote == null ? null : baseresourceNote.trim();
    }

    public Integer getAuditingState() {
        return auditingState;
    }

    public void setAuditingState(Integer auditingState) {
        this.auditingState = auditingState;
    }

    public String getAuditingUserId() {
        return auditingUserId;
    }

    public void setAuditingUserId(String auditingUserId) {
        this.auditingUserId = auditingUserId == null ? null : auditingUserId.trim();
    }

    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8") 
    public Date getAuditingDate() {
        return auditingDate;
    }

    public void setAuditingDate(Date auditingDate) {
        this.auditingDate = auditingDate;
    }

    public Integer getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(Integer dataFrom) {
        this.dataFrom = dataFrom;
    }

	public List<DatBasestationVO> getDatBasestationVO() {
		return datBasestationVO;
	}

	public void setDatBasestationVO(List<DatBasestationVO> datBasestationVO) {
		this.datBasestationVO = datBasestationVO;
	}

    
    public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getCreate_ip() {
		return create_ip;
	}

	public void setCreate_ip(String create_ip) {
		this.create_ip = create_ip;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getUpdate_ip() {
		return update_ip;
	}

	public void setUpdate_ip(String update_ip) {
		this.update_ip = update_ip;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getBasesiteCuid() {
		return basesiteCuid;
	}

	public void setBasesiteCuid(String basesiteCuid) {
		this.basesiteCuid = basesiteCuid;
	}
	
}