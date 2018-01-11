package com.xunge.util;

import java.util.UUID;


public class SysUUID {
    /** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
    public static String generator(){ 
    	SysUUID u = new SysUUID();
    	return u.getUUID();
    }
	
	/** 
     * 获得指定数目的UUID 
     * @param number int 需要获得的UUID数量 
     * @return String[] UUID数组 
     */ 
    public String[] getUUID(int number){ 
        if(number < 1){ 
            return null; 
        } 
        String[] ss = new String[number]; 
        for(int i=0;i<number;i++){ 
            ss[i] = getUUID(); 
        } 
        return ss; 
    } 
    /** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
    public String getUUID(){ 
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    } 

}
