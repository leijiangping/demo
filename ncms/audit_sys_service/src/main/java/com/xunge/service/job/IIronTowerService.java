package com.xunge.service.job;

import com.alibaba.fastjson.JSONObject;

public interface IIronTowerService {

	
	JSONObject requestDownloadFile(String requestXml);
	
	JSONObject noticeFileDownloadFinish(String noticeXml);
}
