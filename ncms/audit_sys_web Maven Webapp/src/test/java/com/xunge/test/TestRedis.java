package com.xunge.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class TestRedis {
	
	public static void main(String[] args) {
//		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:config/applicationContext*.xml");
//        //注入
//		IMenuManagementService userService = (IMenuManagementService)app.getBean("menuManService");
//		
//		Object obj = userService.queryFunctionMenuTree("2");
//		System.out.println(obj.toString());
//	    
	 
		// 按指定模式在字符串查找
	      String line = "/NCMS/asserts/tpl/basedata/collection/resource.html";
	      String pattern = "(/[A-Za-z0-9_]*[^/])(.*)";
	 
	      // 创建 Pattern 对象
	      Pattern r = Pattern.compile(pattern);
	 
	      // 现在创建 matcher 对象
	      Matcher m = r.matcher(line);
	      if (m.find( )) {
	         System.out.println("Found value: " + m.group(0) );
	         System.out.println("Found value: " + m.group(1) );
	         System.out.println("Found value: " + m.group(2) );
	      } else {
	         System.out.println("NO MATCH");
	      }
	}
}