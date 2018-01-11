package com.xunge.service.job;

import java.util.Map;

public interface ICompareService {
	
	
	public Map<String, String> getInsertResults();

	public Map<String, String> getDelResults();
	
	public void startCompare(Map<String, String> newContents,Map<String, String> oldContents);
}
