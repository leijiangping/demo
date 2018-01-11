package com.xunge.service.basedata.collection.job;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.xunge.comm.job.BaseRet;
import com.xunge.comm.job.HttpTookit;



/**
 * 执行一次任务
 *
 */
public class OneTimeJob  {
	
    public boolean execute(Map<String,Object> map)  { 
		
    	boolean result=false;
		String collectionUrl =  map.get("collectionUrl").toString();
		if(StringUtils.isNoneBlank(collectionUrl)){
			String params = JSONObject.toJSONString(map);
			try {
				String jsonstr = HttpTookit.doPostWithJson(collectionUrl, params, null);
				if(StringUtils.isNoneBlank(jsonstr)){
					BaseRet ret=JSONObject.toJavaObject(JSONObject.parseObject(jsonstr), BaseRet.class);
					if(0==ret.getStatus()){
						result=true;
					}
					else{
						result=false;
					}
				}
			} catch (Exception e) {
				result=false;
			}
		}
		return result;
    }  
}
