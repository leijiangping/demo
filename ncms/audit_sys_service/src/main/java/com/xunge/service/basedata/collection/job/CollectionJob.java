package com.xunge.service.basedata.collection.job;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.xunge.comm.job.HttpTookit;



//发起任务
public class CollectionJob implements Job {
	
	private static final Logger LOGGER = Logger.getLogger(CollectionJob.class);
	
	@Override  
    public void execute(JobExecutionContext arg0) throws JobExecutionException { 
		
		JobDataMap jm = arg0.getJobDetail().getJobDataMap();
		String collectionUrl =  jm.getString("collectionUrl");
		if(StringUtils.isNoneBlank(collectionUrl)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("prvId", jm.getInt("prvId"));
			map.put("taskId", jm.getString("taskId"));
	    	map.put("collectionType", jm.getInt("collectionType"));
	    	map.put("taskPeriod", jm.getInt("taskPeriod"));
	    	map.put("taskType", jm.getInt("taskType"));
	    	map.put("ftpFilepath", jm.getString("ftpFilepath"));
	    	map.put("ftpPassword", jm.getString("ftpPassword"));
	    	map.put("ftpPort", jm.getInt("ftpPort"));
	    	map.put("ftpUrl", jm.getString("ftpUrl"));
	    	map.put("ftpUser", jm.getString("ftpUser"));
	    	map.put("optUrl", jm.getString("optUrl"));
	    	map.put("cron", jm.getString("cron"));
			String params = JSONObject.toJSONString(map);
			try {
				String jsonstr = HttpTookit.doPostWithJson(collectionUrl, params, null);
				LOGGER.info("job返回：---------------"+jsonstr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
    }  
}
