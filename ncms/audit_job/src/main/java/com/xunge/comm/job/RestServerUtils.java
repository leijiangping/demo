package com.xunge.comm.job;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.xunge.util.DateConverter;

public class RestServerUtils {
	
	public static String getTomcatRootPath() {
		
		String path=System.getProperty("catalina.home");
		String returnStr = path.replaceAll("\\\\", "/");
		return returnStr;
	}
	
	
	public static String autoReturn(BaseRet retEntry, String format) {
		if ("xml".equalsIgnoreCase(format)) {
			return entry2XML(retEntry);
		} else if ("json".equalsIgnoreCase(format)) {
			return JSON.toJSONString(retEntry);
		} else {
			return entry2XML(retEntry);
		}
	}

	private  static <T> String entry2XML(T t) {
		XStream xStream = new XStream();
		xStream.registerConverter(new DateConverter());
		xStream.alias("response", t.getClass());
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append(xStream.toXML(t));
		return sb.toString().replaceAll("&quot;", "\"").replaceAll("&amp;", "&");
	}
	
	public static HttpServletResponse setResponse(HttpServletResponse response){
		 response.setHeader("Content-type", "application/json;charset=UTF-8");//防止数据传递乱码
		//让浏览器用utf8来解析返回的数据  
//        response.setHeader("Content-type", "text/html;charset=UTF-8");
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		return response;
	}
}
