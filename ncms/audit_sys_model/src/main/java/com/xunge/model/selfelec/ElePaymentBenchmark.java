package com.xunge.model.selfelec;

public class ElePaymentBenchmark {
    private String paybenchmarkId;

    private String billaccountpaymentdetailId;

    private Integer paybenchmarkType;

    private Double paybenchmarkValue;

    private Double paybenchmarkRate;

    private Integer paybenchmarkOverflow;

    public String getPaybenchmarkId() {
        return paybenchmarkId;
    }

    public void setPaybenchmarkId(String paybenchmarkId) {
        this.paybenchmarkId = paybenchmarkId == null ? null : paybenchmarkId.trim();
    }

    public String getBillaccountpaymentdetailId() {
        return billaccountpaymentdetailId;
    }

    public void setBillaccountpaymentdetailId(String billaccountpaymentdetailId) {
        this.billaccountpaymentdetailId = billaccountpaymentdetailId == null ? null : billaccountpaymentdetailId.trim();
    }

    public Integer getPaybenchmarkType() {
        return paybenchmarkType;
    }

    public void setPaybenchmarkType(Integer paybenchmarkType) {
        this.paybenchmarkType = paybenchmarkType;
    }

    public Double getPaybenchmarkValue() {
        return paybenchmarkValue;
    }

    public void setPaybenchmarkValue(Double paybenchmarkValue) {
        this.paybenchmarkValue = paybenchmarkValue;
    }

    public Double getPaybenchmarkRate() {
        return paybenchmarkRate;
    }

    public void setPaybenchmarkRate(Double paybenchmarkRate) {
        this.paybenchmarkRate = paybenchmarkRate;
    }

    public Integer getPaybenchmarkOverflow() {
        return paybenchmarkOverflow;
    }

    public void setPaybenchmarkOverflow(Integer paybenchmarkOverflow) {
        this.paybenchmarkOverflow = paybenchmarkOverflow;
    }
}