package com.xunge.service.job;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.iot.ncms.twrwscli.bean.ReqDownload;
import com.cmcc.iot.ncms.twrwscli.bean.ResDownload;
import com.xunge.model.colletion.TaskInfoVO;

public interface IIronTowerService {

	JSONObject requestDownloadFile(String requestXml);
	
	JSONObject noticeFileDownloadFinish(String noticeXml);
	
	public void processData(TaskInfoVO taskInfo, ReqDownload download, ResDownload resDownLoad, String userName) throws Exception;
}
