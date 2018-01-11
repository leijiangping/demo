package com.xunge.controller.basedata.util;

import com.xunge.comm.StateComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;

public class FileUtils {

	  public static String getFileMB(long byteFile){  
	        if(byteFile==StateComm.STATE_0)  
	           return PromptMessageComm.SIZE_0MB;  
	        long mb=SelfelecComm.NUMBER_1024;  
	        return ""+byteFile/mb+PromptMessageComm.SIZE_MB;  
	    }  
}
