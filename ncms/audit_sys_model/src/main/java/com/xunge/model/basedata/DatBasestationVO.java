package com.xunge.model.basedata;

import java.io.Serializable;
import java.util.Date;

public class DatBasestationVO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4480822055791852304L;

	private String basestationId;

    private String prvId;
    
    private String pregId;

    private String prvSname;

    private String baseresourceId;
    
    private String baseresourceName;
    
    private String baseresourceCode;

    private String baseresourceCuid;
    
    private String basestationCuid;

    private String basestationCode;

    private String basestationName;

    private Integer basestationCategory;

    private Integer basestationType;

    private Date basestationOpendate;

    private Date basestationStopdate;

    private Integer basestationVendor;

    private String basestationModel;

    private float basestationPower;

    private Integer basestationState;

    private Integer basestationCarrier;

    private Integer basestationCovertype;

    private Integer dataFrom;
    
    private String regId;
    
    private String create_user;
    private String create_ip;
    private Date create_time;
    private String update_user;
    private String update_ip;
    private Date update_time;
    
    public String getBasestationCuid() {
		return basestationCuid;
	}

	public void setBasestationCuid(String basestationCuid) {
		this.basestationCuid = basestationCuid;
	}

	public String getBaseresourceCode() {
		return baseresourceCode;
	}

	public void setBaseresourceCode(String baseresourceCode) {
		this.baseresourceCode = baseresourceCode;
	}

	public String getBaseresourceName() {
		return baseresourceName;
	}

	public void setBaseresourceName(String baseresourceName) {
		this.baseresourceName = baseresourceName;
	}

	public String getPregId() {
		return pregId;
	}

	public void setPregId(String pregId) {
		this.pregId = pregId;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getBasestationId() {
        return basestationId;
    }

    public void setBasestationId(String basestationId) {
        this.basestationId = basestationId == null ? null : basestationId.trim();
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

    public String getBaseresourceId() {
        return baseresourceId;
    }

    public void setBaseresourceId(String baseresourceId) {
        this.baseresourceId = baseresourceId == null ? null : baseresourceId.trim();
    }

    public String getBaseresourceCuid() {
        return baseresourceCuid;
    }

    public void setBaseresourceCuid(String baseresourceCuid) {
        this.baseresourceCuid = baseresourceCuid == null ? null : baseresourceCuid.trim();
    }

    public String getBasestationCode() {
        return basestationCode;
    }

    public void setBasestationCode(String basestationCode) {
        this.basestationCode = basestationCode == null ? null : basestationCode.trim();
    }

    public String getBasestationName() {
        return basestationName;
    }

    public void setBasestationName(String basestationName) {
        this.basestationName = basestationName == null ? null : basestationName.trim();
    }

    public Integer getBasestationCategory() {
        return basestationCategory;
    }

    public void setBasestationCategory(Integer basestationCategory) {
        this.basestationCategory = basestationCategory;
    }

    public Integer getBasestationType() {
        return basestationType;
    }

    public void setBasestationType(Integer basestationType) {
        this.basestationType = basestationType;
    }

    public Date getBasestationOpendate() {
        return basestationOpendate;
    }

    public void setBasestationOpendate(Date basestationOpendate) {
        this.basestationOpendate = basestationOpendate;
    }

    public Date getBasestationStopdate() {
        return basestationStopdate;
    }

    public void setBasestationStopdate(Date basestationStopdate) {
        this.basestationStopdate = basestationStopdate;
    }

    public Integer getBasestationVendor() {
        return basestationVendor;
    }

    public void setBasestationVendor(Integer basestationVendor) {
        this.basestationVendor = basestationVendor;
    }

    public String getBasestationModel() {
        return basestationModel;
    }

    public void setBasestationModel(String basestationModel) {
        this.basestationModel = basestationModel == null ? null : basestationModel.trim();
    }

    public float getBasestationPower() {
        return basestationPower;
    }

    public void setBasestationPower(float basestationPower) {
        this.basestationPower = basestationPower;
    }

    public Integer getBasestationState() {
        return basestationState;
    }

    public void setBasestationState(Integer basestationState) {
        this.basestationState = basestationState;
    }

    public Integer getBasestationCarrier() {
        return basestationCarrier;
    }

    public void setBasestationCarrier(Integer basestationCarrier) {
        this.basestationCarrier = basestationCarrier;
    }

    public Integer getBasestationCovertype() {
        return basestationCovertype;
    }

    public void setBasestationCovertype(Integer basestationCovertype) {
        this.basestationCovertype = basestationCovertype;
    }

    public Integer getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(Integer dataFrom) {
        this.dataFrom = dataFrom;
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
	
}