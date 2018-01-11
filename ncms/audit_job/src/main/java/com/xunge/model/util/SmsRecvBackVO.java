package com.xunge.model.util;

import java.io.Serializable;

/**
 * 短信发送接口接收返回：
 * 
 * 10701	下发处理成功,resultInfo会返回下发流水号
 * 10702	下发处理失败,resultInfo会描述具体失败原因,此次下发请求被放弃
 * 10703	发送内容有敏感词,errorInfo中会返回具体的敏感词,此次下发请求被放弃
 * 10704	下发号码包含黑名单,errorInfo会返回处于主叫黑名单的下发号码,此次下发请求被放弃
 * 10705	接收号码包含黑名单,errorInfo会返回处于被叫黑名单的接收号码,此次下发请求被放弃
 * 10706	接收号码中包含非法号码,errorInfo会返回非法的接收号码,此次下发请求被放弃
 * 10708	SI鉴权失败,resultInfo中返回具体SI鉴权失败原因,如:SI不存在,此次下发请求被放弃
 * 10709	余额不足
 * 10712	参数异常,resultInfo返回出现异常的参数,此次下发请求被放弃
 * 10713	模板不存在或未审核通过
 * 10714	缺少模板参数
 */
public class SmsRecvBackVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -970205003265491756L;

	/**
	 * String: 错误码
	 */
	private String result;
	
	/**
	 * 返回信息
	 */
	private String resultInfo;

	/**
	 * 错误信息
	 */
	private String errorInfo;

	/**
	 * Boolean: 是否发送成功
	 */
	private Boolean backResult;
	/**
	 * String: 返回信息
	 */
	private String backInfo;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Boolean getBackResult() {
		return backResult;
	}

	public void setBackResult(Boolean backResult) {
		this.backResult = backResult;
	}

	public String getBackInfo() {
		return backInfo;
	}

	public void setBackInfo(String backInfo) {
		this.backInfo = backInfo;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	
}
