package com.xunge.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValueUtil {
	
	
	public static List<String> dealCuids(List<String> columns, Map<String, String> map){
		
		List<String> list=new ArrayList<String>();
		for (String key : map.keySet()){
			list.add(key);
		}
		return list;
	}
	
	public static String[] dealValues(int columnSize, String values,String delimiter){
		
		String[] valueArray=values.split(delimiter,-1);
		if(valueArray.length==columnSize){
			return dealStringArray(columnSize,valueArray);
		}
		else{
			return StrUtil.CSVtoArray(values, delimiter);
//			System.out.println("----------------columnSize:"+columnSize);
			//String[] result=new String[columnSize];
//			List<String> result=new ArrayList<String>();
//			List<Integer> indexList=new ArrayList<Integer>();
//			List<Integer> newIndexList=new ArrayList<Integer>();
//			for(int i=0;i<valueArray.length;i++){
//				if(i<valueArray.length&&getSubStringCount(valueArray[i],"\"")%2!=0){
//					indexList.add(i);
//				}
//			}
//			for(int i=0, length = indexList.size(); i<length;i=i+2){
//				int begin=indexList.get(i);
//				int end=indexList.get(++i);
//				for(int j=begin;j<=end;j++){
//					newIndexList.add(j);
//				}
//			}
//			for(int i=0;i<valueArray.length;i++){
//				String value=valueArray[i];
//				if(!newIndexList.contains(i)){
//					result.add(value);
//				}
//				else{
//					StringBuffer sb=new StringBuffer();
//					while(newIndexList.contains(i)){
//						value=valueArray[i];
//						sb.append(value+"|");
//						i++;
//					}
//					result.add(sb.substring(0, sb.length()-1));
//					i--;
//				}
//				
//			}
//			System.out.println("----------------resultSize:"+result.size());
//			return dealStringArray(columnSize,result.toArray(new String[result.size()]));
		}
	}
	
	private static int getSubStringCount(String str,String subStr){
		int count=0;
		int length=subStr.length();
		while(str.indexOf(subStr)>=0){
			count++;
			str=str.substring(str.indexOf(subStr)+length);
		}
		return count;
	}
	
	
	private static String[] dealStringArray(int columnSize,String[] valueArray){
		String[] result=new String[columnSize];
		for(int i=0;i<valueArray.length;i++){
			
			
			//数组越界value为null
			String value;
			try{
				 value = valueArray[i];
			}catch (Exception e){
				 value = null;
			}
			
			if(value.startsWith("\"")){
				value=value.substring(1, value.length()-1);
				value=value.replaceAll("\"\"","\"");
			}
			result[i]=value;
		}
		return result;
	}
	
}
