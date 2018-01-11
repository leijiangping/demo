package com.xunge.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtil {
/*	@SuppressWarnings("all")
	private static MemcachedUtil memcachedUtil;
	static {
		memcachedUtil = (MemcachedUtil) SpringContextUtil.getBean("memcachedUtil");
	}*/

	public static String get(String path) throws Exception {
		HttpURLConnection httpConn = null;
		BufferedReader in = null;
		try {
			URL url = new URL(path);
			httpConn = (HttpURLConnection) url.openConnection();

			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				StringBuffer content = new StringBuffer();
				String tempStr = "";
				in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8"));
				while ((tempStr = in.readLine()) != null) {
					content.append(tempStr);
				}
				return content.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("连接异常:" + e.getMessage());
		} finally {
			if (in != null) {
				in.close();
			}
			if (httpConn != null) {
				httpConn.disconnect();
			}
		}
		return "";
	}

	public static String post(String ip ,String params) throws Exception {
		HttpURLConnection httpConn = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			URL url = new URL(ip);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);

			out = new PrintWriter(httpConn.getOutputStream());
			out.println(params.toString());
			out.flush();

			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				StringBuffer content = new StringBuffer();
				String tempStr = "";
				in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
				while ((tempStr = in.readLine()) != null) {
					content.append(tempStr);
				}
				
				
				return content.toString();
			} else {
				throw new Exception("连接异常!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in.close();
			out.close();
			httpConn.disconnect();
		}
		return null;
	}

	/** 
	 * 将url参数转换成map 
	 * @param param aa=11&bb=22&cc=33 
	 * @return 
	 */  
	public static Map<String, Object> getUrlParams(String param) {  
	    Map<String, Object> map = new HashMap<String, Object>(0);  
	    if (StrUtil.isBlank(param)) {  
	        return map;  
	    }  
	    String[] params = param.split("&");  
	    for (int i = 0; i < params.length; i++) {  
	        String[] p = params[i].split("=");  
	        if (p.length == 2) {  
	            map.put(p[0], p[1]);  
	        }  
	    }  
	    return map;  
	}  
	  
	/** 
	 * 将map转换成url 
	 * @param map 
	 * @return 
	 */  
	public static String getUrlParamsByMap(Map<String, Object> map) {  
	    if (map == null) {  
	        return "";  
	    }  
	    StringBuffer sb = new StringBuffer();  
	    for (Map.Entry<String, Object> entry : map.entrySet()) {  
	        sb.append(entry.getKey() + "=" + entry.getValue());  
	        sb.append("&");  
	    }  
	    String s = sb.toString();  
	    if (s.endsWith("&")) {  
	        s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");  
	    }  
	    return s;  
	}
}
