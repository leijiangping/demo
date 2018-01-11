package com.xunge.controller.basedata.util;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.xunge.comm.system.PromptMessageComm;

public class RestServerUtils {
	
	public static String getTomcatRootPath() {
		
		String path=System.getProperty(PromptMessageComm.CATALINA_HOME);
		String returnStr = path.replaceAll("\\\\", "/");
		return returnStr;
	}
	
	 /**
     * 查找cookie内容
     *
     * @param cookies
     * @param key
     * @return
     */
    public static String findCookieValue(Cookie[] cookies, String key) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return URLDecoder.decode(cookie.getValue());
                }
            }
        }
        return null;

    }
	
	public static String autoReturn(BaseRet retEntry, String format) {
		if (PromptMessageComm.XML.equalsIgnoreCase(format)) {
			return entry2XML(retEntry);
		} else if (PromptMessageComm.JSON.equalsIgnoreCase(format)) {
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
		 response.setHeader("Content-type", "application/json;charset=UTF-8");
		return response;
	}
}
