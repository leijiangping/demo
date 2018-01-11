package com.xunge.model.basedata;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xunge.model.BaseActVO;
import com.xunge.model.activity.Act;

public class DatBasesiteVO extends BaseActVO implements Cloneable{
    private String basesiteId;

    private String prvId;

    private String prvSname;

    private String pregId;

    private String pregName;

    private String regId;

    private String regName;

    private String basesiteCuid;

    private String basesiteCode;

    private String basesiteName;

    private String basesiteAddress;

    private Integer basesiteType;

    private Integer basesiteState;

    private Integer basesiteMaintenance;

    private String towerSiteCode;

    private Date basesiteOpendate;

    private Date basesiteStopdate;

    private String basesiteBelong;

    private Integer basesiteProperty;

    private String basesiteShare;

    private BigDecimal basesiteLongitude;

    private BigDecimal basesiteLatitude;

    private Integer auditingState;

    private String auditingUserId;

    private Date auditingDate;

    private Integer dataFrom;
    
    private String create_user;
    private String create_ip;
    private Date create_time;
    private String update_user;
    private String update_ip;
    private Date update_time;
    
	private List<DatBaseresourceVO> datBaseresourceList;
    
    private Act act;
    
    public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
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

    public String getBasesiteCuid() {
        return basesiteCuid;
    }

    public void setBasesiteCuid(String basesiteCuid) {
        this.basesiteCuid = basesiteCuid == null ? null : basesiteCuid.trim();
    }

    public String getBasesiteCode() {
        return basesiteCode;
    }

    public void setBasesiteCode(String basesiteCode) {
        this.basesiteCode = basesiteCode == null ? null : basesiteCode.trim();
    }

    public String getBasesiteName() {
        return basesiteName;
    }

    public void setBasesiteName(String basesiteName) {
        this.basesiteName = basesiteName == null ? null : basesiteName.trim();
    }

    public String getBasesiteAddress() {
        return basesiteAddress;
    }

    public void setBasesiteAddress(String basesiteAddress) {
        this.basesiteAddress = basesiteAddress == null ? null : basesiteAddress.trim();
    }

    public Integer getBasesiteType() {
        return basesiteType;
    }

    public void setBasesiteType(Integer basesiteType) {
        this.basesiteType = basesiteType;
    }

    public Integer getBasesiteState() {
        return basesiteState;
    }

    public void setBasesiteState(Integer basesiteState) {
        this.basesiteState = basesiteState;
    }

    public Integer getBasesiteMaintenance() {
        return basesiteMaintenance;
    }

    public void setBasesiteMaintenance(Integer basesiteMaintenance) {
        this.basesiteMaintenance = basesiteMaintenance;
    }

    public String getTowerSiteCode() {
        return towerSiteCode;
    }

    public void setTowerSiteCode(String towerSiteCode) {
        this.towerSiteCode = towerSiteCode == null ? null : towerSiteCode.trim();
    }

    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8") 
    public Date getBasesiteOpendate() {
        return basesiteOpendate;
    }

    public void setBasesiteOpendate(Date basesiteOpendate) {
        this.basesiteOpendate = basesiteOpendate;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8") 
    public Date getBasesiteStopdate() {
        return basesiteStopdate;
    }

    public void setBasesiteStopdate(Date basesiteStopdate) {
        this.basesiteStopdate = basesiteStopdate;
    }

    public String getBasesiteBelong() {
        return basesiteBelong;
    }

    public void setBasesiteBelong(String basesiteBelong) {
        this.basesiteBelong = basesiteBelong;
    }

    public Integer getBasesiteProperty() {
        return basesiteProperty;
    }

    public void setBasesiteProperty(Integer basesiteProperty) {
        this.basesiteProperty = basesiteProperty;
    }

    public String getBasesiteShare() {
        return basesiteShare;
    }

    public void setBasesiteShare(String basesiteShare) {
        this.basesiteShare = basesiteShare;
    }

    public BigDecimal getBasesiteLongitude() {
        return basesiteLongitude;
    }

    public void setBasesiteLongitude(BigDecimal basesiteLongitude) {
        this.basesiteLongitude = basesiteLongitude;
    }

    public BigDecimal getBasesiteLatitude() {
        return basesiteLatitude;
    }

    public void setBasesiteLatitude(BigDecimal basesiteLatitude) {
        this.basesiteLatitude = basesiteLatitude;
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

    public Date getAuditingDate() {
        return auditingDate;
    }

    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8") 
    public void setAuditingDate(Date auditingDate) {
        this.auditingDate = auditingDate;
    }

    public Integer getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(Integer dataFrom) {
        this.dataFrom = dataFrom;
    }

	public List<DatBaseresourceVO> getDatBaseresourceList() {
		return datBaseresourceList;
	}

	public void setDatBaseresourceList(List<DatBaseresourceVO> datBaseresourceList) {
		this.datBaseresourceList = datBaseresourceList;
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
	
	@Override  
	public Object clone() {  
		DatBasesiteVO addr = null;  
		try{  
			addr = (DatBasesiteVO)super.clone();  
		}catch(CloneNotSupportedException e) {  
			e.printStackTrace();  
		}  
		return addr;  
	} 
	
}