package com.xunge.core.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>获取配置项工作类<br/>
 */
public class SysConfigPropertiesUtil {
	private static Logger logger = LoggerFactory.getLogger(SysConfigPropertiesUtil.class);
	private static Properties prop = new Properties(); 
	static {
		try {
			prop.load(SysConfigPropertiesUtil.class.getClassLoader().getResourceAsStream("\\properties\\sysConfig.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		try {
			if ("".equals(prop.get(key)) || prop.get(key) == null) {
				return "";
			} else {
				return (String) prop.get(key);
			}

		} catch (Exception e) {
			logger.warn("读取配置文件【sysConfig.properties】错误，错误信息：" + e.getMessage());
			return "";
		}
	}
}
