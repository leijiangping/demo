package com.xunge.model.selfelec;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DatElectricMeterVO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4147626076695823562L;

	private String meterId;

    private String prvId;

    private String prvSname;

    private String pregId;

    private String pregName;

    private String regId;

    private String regName;

    private String meterCode;

    private BigDecimal initialValue;

    private BigDecimal upperValue;

    private BigDecimal flatValue;

    private BigDecimal peakValue;

    private BigDecimal topValue;

    private BigDecimal valleyValue;

    private BigDecimal flatUpperValue;

    private BigDecimal peakUpperValue;

    private BigDecimal valleyUpperValue;

    private BigDecimal topUpperValue;

    private Integer meterType;

    private BigDecimal electricmeterMultiply;

    private String accountNumber;

    private Date installDate;

    private Integer meterState;

    private Integer isShare;

    private String meterNote;

    private Integer dataFrom;
    
    private Integer relateResState;
    
    private String dateStr;
    
    private String userId;
    
    //权限内 区县id集合
  	private List<String> regIds;
  	//权限内省份id集合
  	private List<String> pregIds;
  	//是否向下共享
  	private Integer isDownShare;

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Integer getRelateResState() {
		return relateResState;
	}

	public void setRelateResState(Integer relateResState) {
		this.relateResState = relateResState;
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

    public String getMeterCode() {
        return meterCode;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode == null ? null : meterCode.trim();
    }

    public BigDecimal getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(BigDecimal initialValue) {
        this.initialValue = initialValue;
    }

    public BigDecimal getUpperValue() {
        return upperValue;
    }

    public void setUpperValue(BigDecimal upperValue) {
        this.upperValue = upperValue;
    }

    public BigDecimal getFlatValue() {
        return flatValue;
    }

    public void setFlatValue(BigDecimal flatValue) {
        this.flatValue = flatValue;
    }

    public BigDecimal getPeakValue() {
        return peakValue;
    }

    public void setPeakValue(BigDecimal peakValue) {
        this.peakValue = peakValue;
    }

    public BigDecimal getTopValue() {
        return topValue;
    }

    public void setTopValue(BigDecimal topValue) {
        this.topValue = topValue;
    }

    public BigDecimal getValleyValue() {
        return valleyValue;
    }

    public void setValleyValue(BigDecimal valleyValue) {
        this.valleyValue = valleyValue;
    }

    public BigDecimal getFlatUpperValue() {
        return flatUpperValue;
    }

    public void setFlatUpperValue(BigDecimal flatUpperValue) {
        this.flatUpperValue = flatUpperValue;
    }

    public BigDecimal getPeakUpperValue() {
        return peakUpperValue;
    }

    public void setPeakUpperValue(BigDecimal peakUpperValue) {
        this.peakUpperValue = peakUpperValue;
    }

    public BigDecimal getValleyUpperValue() {
        return valleyUpperValue;
    }

    public void setValleyUpperValue(BigDecimal valleyUpperValue) {
        this.valleyUpperValue = valleyUpperValue;
    }

    public BigDecimal getTopUpperValue() {
        return topUpperValue;
    }

    public void setTopUpperValue(BigDecimal topUpperValue) {
        this.topUpperValue = topUpperValue;
    }

    public Integer getMeterType() {
        return meterType;
    }

    public void setMeterType(Integer meterType) {
        this.meterType = meterType;
    }

    public BigDecimal getElectricmeterMultiply() {
        return electricmeterMultiply;
    }

    public void setElectricmeterMultiply(BigDecimal electricmeterMultiply) {
        this.electricmeterMultiply = electricmeterMultiply;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public Integer getMeterState() {
        return meterState;
    }

    public void setMeterState(Integer meterState) {
        this.meterState = meterState;
    }

    public Integer getIsShare() {
        return isShare;
    }

    public void setIsShare(Integer isShare) {
        this.isShare = isShare;
    }

    public String getMeterNote() {
        return meterNote;
    }

    public void setMeterNote(String meterNote) {
        this.meterNote = meterNote == null ? null : meterNote.trim();
    }

    public Integer getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(Integer dataFrom) {
        this.dataFrom = dataFrom;
    }

	public List<String> getRegIds() {
		return regIds;
	}

	public void setRegIds(List<String> regIds) {
		this.regIds = regIds;
	}

	public List<String> getPregIds() {
		return pregIds;
	}

	public void setPregIds(List<String> pregIds) {
		this.pregIds = pregIds;
	}

	public Integer getIsDownShare() {
		return isDownShare;
	}

	public void setIsDownShare(Integer isDownShare) {
		this.isDownShare = isDownShare;
	}
    
}