package com.xunge.model.smsSendHistroy;

public class SysSmsConfigVO{
    //短信发送sicode
    private String smsSicode;
    //短信模板编码
    private String tempCode;
    //模板名称
    private String tempName;
    //模板内容
    private String smsContent;
    //状态
    private Integer state;

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName == null ? null : tempName.trim();
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent == null ? null : smsContent.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

	public String getSmsSicode() {
		return smsSicode;
	}

	public void setSmsSicode(String smsSicode) {
		this.smsSicode = smsSicode;
	}

	public String getTempCode() {
		return tempCode;
	}

	public void setTempCode(String tempCode) {
		this.tempCode = tempCode;
	}
}