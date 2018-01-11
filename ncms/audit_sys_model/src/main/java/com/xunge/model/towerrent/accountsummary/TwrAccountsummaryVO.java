package com.xunge.model.towerrent.accountsummary;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.xunge.model.activity.Act;
import com.xunge.model.system.region.SysRegionVO;

public class TwrAccountsummaryVO implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String accountsummaryId;

    private String yearmonth;

    private String regId;

    private String sumAmountType;

    private BigDecimal sumTowerAmount;

    private BigDecimal sumDiffAmount;

    private BigDecimal sumCalcAmount;

    private BigDecimal sumHistAmount;

    private String debitType;

    private BigDecimal sumBackAmount;

    private BigDecimal sumTotalAmount;

    private BigDecimal sumTotalAmountTax;

    private Integer state;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;
    
    private BigDecimal sumAdjustmentAmount;

    // 关联运营商区县
  	private SysRegionVO operatorSysRegion;
  	
  	// 关联流程
    private Act act;
    
    public BigDecimal getSumAdjustmentAmount() {
		return sumAdjustmentAmount;
	}

	public void setSumAdjustmentAmount(BigDecimal sumAdjustmentAmount) {
		this.sumAdjustmentAmount = sumAdjustmentAmount;
	}

	public String getAccountsummaryId() {
        return accountsummaryId;
    }

    public void setAccountsummaryId(String accountsummaryId) {
        this.accountsummaryId = accountsummaryId == null ? null : accountsummaryId.trim();
    }

    public String getYearmonth() {
		return yearmonth;
	}

	public void setYearmonth(String yearmonth) {
		this.yearmonth = yearmonth;
	}

	public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public String getSumAmountType() {
        return sumAmountType;
    }

    public void setSumAmountType(String sumAmountType) {
        this.sumAmountType = sumAmountType == null ? null : sumAmountType.trim();
    }

    public BigDecimal getSumTowerAmount() {
        return sumTowerAmount;
    }

    public void setSumTowerAmount(BigDecimal sumTowerAmount) {
        this.sumTowerAmount = sumTowerAmount;
    }

    public BigDecimal getSumDiffAmount() {
        return sumDiffAmount;
    }

    public void setSumDiffAmount(BigDecimal sumDiffAmount) {
        this.sumDiffAmount = sumDiffAmount;
    }

    public BigDecimal getSumCalcAmount() {
        return sumCalcAmount;
    }

    public void setSumCalcAmount(BigDecimal sumCalcAmount) {
        this.sumCalcAmount = sumCalcAmount;
    }

    public BigDecimal getSumHistAmount() {
        return sumHistAmount;
    }

    public void setSumHistAmount(BigDecimal sumHistAmount) {
        this.sumHistAmount = sumHistAmount;
    }

    public String getDebitType() {
        return debitType;
    }

    public void setDebitType(String debitType) {
        this.debitType = debitType == null ? null : debitType.trim();
    }

    public BigDecimal getSumBackAmount() {
        return sumBackAmount;
    }

    public void setSumBackAmount(BigDecimal sumBackAmount) {
        this.sumBackAmount = sumBackAmount;
    }

    public BigDecimal getSumTotalAmount() {
        return sumTotalAmount;
    }

    public void setSumTotalAmount(BigDecimal sumTotalAmount) {
        this.sumTotalAmount = sumTotalAmount;
    }

    public BigDecimal getSumTotalAmountTax() {
        return sumTotalAmountTax;
    }

    public void setSumTotalAmountTax(BigDecimal sumTotalAmountTax) {
        this.sumTotalAmountTax = sumTotalAmountTax;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId == null ? null : updateUserId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public SysRegionVO getOperatorSysRegion() {
		return operatorSysRegion;
	}

	public void setOperatorSysRegion(SysRegionVO operatorSysRegion) {
		this.operatorSysRegion = operatorSysRegion;
	}

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}
}
