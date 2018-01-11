package com.xunge.model.selfelec;

import java.math.BigDecimal;

public class EleBenchmarkParaVO{
    private BigDecimal pue;

    private BigDecimal t;
    
    private String regId;

    private Integer monthNo;

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId == null ? null : regId.trim();
    }

    public Integer getMonthNo() {
        return monthNo;
    }

    public void setMonthNo(Integer monthNo) {
        this.monthNo = monthNo;
    }
    public BigDecimal getPue() {
        return pue;
    }

    public void setPue(BigDecimal pue) {
        this.pue = pue;
    }

    public BigDecimal getT() {
        return t;
    }

    public void setT(BigDecimal t) {
        this.t = t;
    }
}