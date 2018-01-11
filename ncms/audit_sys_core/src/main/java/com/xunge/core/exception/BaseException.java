package com.xunge.core.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunge.core.model.FeedBackObject;

/**
 * @author SongJL
 * @Description 基础异常处理类
 */
@ControllerAdvice 
public class BaseException {  
    /** 
     * 基于@ExceptionHandler异常处理 
     */  
    @ExceptionHandler  
    public @ResponseBody FeedBackObject exp(HttpServletRequest request, Exception ex) {  
    	FeedBackObject backObj = new FeedBackObject();
    	
    	//系统处理失败
    	backObj.success = "0";
        
        // 根据不同错误不同返回
        if(ex.getMessage().contains("Expected session attribute 'user'")){
        	backObj.msg = "登录失效";
            return backObj;  
        }
        else if(ex instanceof BusinessException) {
        	backObj.msg = ex.getMessage();
            return backObj;  
        }
        else if(ex instanceof ParameterException) {
        	backObj.msg = ex.getMessage();
            return backObj;  
        } 
        //嵌套异常
        else if(ex.getCause() != null && ex.getCause().getMessage().contains("BusinessException")) {
        	if(ex.getCause().getMessage()!=null){
        		String errmsg = ex.getCause().getMessage();
            	backObj.msg = errmsg.substring(errmsg.lastIndexOf(':')+1, errmsg.length());
        	}
        	else{
            	backObj.msg = "业务未知异常";
        	}
            return backObj;  
        }
        //嵌套异常
        else if(ex.getCause() != null && ex.getCause().getMessage().contains("ParameterException")) {
        	if(ex.getCause().getMessage()!=null){
        		String errmsg = ex.getCause().getMessage();
            	backObj.msg = errmsg.substring(errmsg.lastIndexOf(':')+1, errmsg.length());
        	}
        	else{
            	backObj.msg = "参数未知异常";
        	}
            return backObj;  
        }
        else if(ex.getCause() == null){
        	if(ex.getMessage()!=null){
        		String errmsg = ex.getMessage();
            	backObj.msg = errmsg.substring(errmsg.lastIndexOf(':')+1, errmsg.length());
        	}
        	else{
            	backObj.msg = "参数未知异常";
        	}
            return backObj; 
        }
        else if(ex instanceof Exception) {
        	backObj.msg = "请求失败";
            return backObj;  
        } 
        else {
        	backObj.msg = "未知异常";
            return backObj;  
        }  
    }  
} 