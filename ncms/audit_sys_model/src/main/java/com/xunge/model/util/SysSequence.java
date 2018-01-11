package com.xunge.model.util;

import java.io.Serializable;

public class SysSequence implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String seqCode;// 序列代码
	private String seqName;//序列名称
	private String lastUpdateYear;//最后修改年份,
	private Integer curSeqNum;//当前序列数
	
	public SysSequence(String seqCode, String lastUpdateYear, Integer curSeqNum) {
		this.seqCode = seqCode;
		this.lastUpdateYear = lastUpdateYear;
		this.curSeqNum = curSeqNum;
	}

	public String getSeqCode() {
        return seqCode;
    }

    public void setSeqCode(String seqCode) {
        this.seqCode = seqCode == null ? null : seqCode.trim();
    }

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName == null ? null : seqName.trim();
    }
    public String getLastUpdateYear() {
        return lastUpdateYear;
    }

    public void setLastUpdateYear(String lastUpdateYear) {
        this.lastUpdateYear = lastUpdateYear == null ? null : lastUpdateYear.trim();
    }

    public Integer getCurSeqNum() {
        return curSeqNum;
    }

    public void setCurSeqNum(Integer curSeqNum) {
        this.curSeqNum = curSeqNum;
    }
}
