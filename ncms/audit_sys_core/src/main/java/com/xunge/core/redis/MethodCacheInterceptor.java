package com.xunge.core.redis;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xunge.core.exception.BaseException;
import com.xunge.core.util.CollectionUtil;
import com.xunge.core.util.PropertiesLoader;
  
/**
 * @author SongJL
 * @Description Redis拦截器
 */
public class MethodCacheInterceptor extends BaseException implements MethodInterceptor {  
 
    private RedisUtil redisUtil;  
    private List<String> ignoreTargetNamesList; // 不加入缓存的service名称  或部分名称
    private List<String> ignoreMethodNamesList; // 不加入缓存的方法名称  或部分名称
    private List<String> cacheTargetNamesList; 	// 加入缓存的service名称 或部分名称  
    private List<String> cacheMethodNamesList; 	// 加入缓存的方法名称  或部分名称
    private Long defaultCacheExpireTime; // 缓存默认的过期时间 
    
    private PropertiesLoader prop = new PropertiesLoader("\\properties\\redis.properties");
  
    /** 
     * 初始化读取不需要加入缓存的类名和方法名称 
     */  
    public MethodCacheInterceptor() {  
    	
    	defaultCacheExpireTime = (long) Integer.parseInt(prop.getProperty("defaultCacheExpireTime"));
    	ignoreMethodNamesList = new ArrayList<String>();
    	String[] ignoreNames = prop.getProperty("ignoreMethodNamesList").split(",");
    	for(String iName : ignoreNames){
        	ignoreMethodNamesList.add(iName);
    	}
    	cacheMethodNamesList = new ArrayList<String>();
    	String[] cacheNames = prop.getProperty("cacheMethodNamesList").split(",");
    	for(String cName : cacheNames){
    		cacheMethodNamesList.add(cName);
    	}
    }  
  
    @Override  
    public Object invoke(MethodInvocation invocation) throws Throwable {  
        Object value = null;  
  
        String targetName = invocation.getThis().getClass().getName();  
        String methodName = invocation.getMethod().getName();   
        Object[] arguments = invocation.getArguments();  
        String key = getCacheKey(targetName, methodName, arguments);  
  
        try {  
            // 忽略加入缓存的内容  
            if (isIgnoreAddCache(targetName, methodName)) { 
            	String rmkey = getBatchCacheKey(targetName, arguments);
                // 清除已缓存的内容
            	redisUtil.removePattern(rmkey);  
                // 执行方法返回结果  
                return invocation.proceed(); 
            }
            // 特殊加入缓存的内容
            if(isAddCache(targetName, methodName)) { 
                // 判断是否有缓存  
                if (redisUtil.exists(key)) {  
                    return redisUtil.get(key);  
                }  
                // 写入缓存  
                value = invocation.proceed();  
                if (value != null) {  
                    final String tkey = key;  
                    final Object tvalue = value;  
                    new Thread(new Runnable() {  
                        @Override  
                        public void run() {  
                            redisUtil.set(tkey, tvalue, defaultCacheExpireTime);  
                        }  
                    }).start();  
                } 
            } 
            else{
                // 返回原方法
                value = invocation.proceed(); 
            }
        } catch (Exception e) {  
        	e.printStackTrace();
            throw new Exception(e.toString());
        }  
        return value;  
    }  
  
    /** 
     * 忽略缓存 
     *  
     * @return 
     */  
    private boolean isIgnoreAddCache(String targetName, String methodName) {
    	boolean flag = false;  
        if(ignoreMethodNamesList == null && ignoreMethodNamesList == null){
            flag = false;  
        }
        else if (CollectionUtil.isContain(ignoreTargetNamesList, targetName)
                || CollectionUtil.isContain(ignoreMethodNamesList, methodName)) { 
        	// 需要添加缓存，不做拦截 
            flag = true;  
        }
        return flag;  
    } 
  
    /** 
     * 特殊函数加入缓存 
     *  
     * @return 
     */  
    private boolean isAddCache(String targetName, String methodName) {
    	boolean flag = false;  
        if(cacheTargetNamesList == null && cacheMethodNamesList == null){
            flag = false;  
        }
        else if (CollectionUtil.isContain(cacheTargetNamesList, targetName)
                || CollectionUtil.isContain(cacheMethodNamesList, methodName)) {   
            flag = true;  
        }
        return flag;  
    } 
  
    /** 
     * 创建缓存key 
     * 
     * @param targetName 
     * @param methodName 
     * @param arguments 
     */  
    private String getCacheKey(String targetName, String methodName,  
            Object[] arguments) {  
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	String sessionId = request.getSession().getId();
        StringBuffer sbu = new StringBuffer();  
        sbu.append(sessionId).append(":").append(targetName).append(":").append(methodName);  
        if ((arguments != null) && (arguments.length != 0)) {  
            for (int i = 0; i < arguments.length; i++) {  
                sbu.append(":").append(arguments[i]);  
            }  
        }  
        return sbu.toString();  
    }  
    /** 
     * 创建缓存key 
     * 
     * @param targetName 
     * @param methodName 
     * @param arguments 
     */  
    private String getBatchCacheKey(String targetName, Object[] arguments) { 
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	String sessionId = request.getSession().getId(); 
        StringBuffer sbu = new StringBuffer();  
        sbu.append(sessionId).append(":").append(targetName).append(":").append("*");
        return sbu.toString();  
    }  
  
    public void setRedisUtil(RedisUtil redisUtil) {  
        this.redisUtil = redisUtil;  
    }  
}
