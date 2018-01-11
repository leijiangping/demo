package com.xunge.service.job.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xunge.service.job.ICompareService;

@Service
public class CSVCompareServiceImpl implements ICompareService{

	private Map<String, String> insertResults;
	private Map<String, String> delResults;

	public Map<String, String> getInsertResults() {
		return insertResults;
	}

	public Map<String, String> getDelResults() {
		return delResults;
	}
	
	
	/**
	 * 比较两个map的同key是否同value
	 */
	public void startCompare(Map<String, String> newContents,Map<String, String> oldContents){
		
		if(newContents==null||oldContents==null){
			return ;
		}
		
		insertResults=newContents;
		delResults=new HashMap<String,String>();
		
		for(Map.Entry<String, String> oldContents_entry:oldContents.entrySet()){
			if(!newContents.containsKey(oldContents_entry.getKey())){
				delResults.put(oldContents_entry.getKey(), oldContents_entry.getValue());
			}
        }
		
	}

}
