package com.xunge.controller.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.controller.activity.mapper.JsonMapper;
import com.xunge.model.system.province.SysProvinceGroupVO;
import com.xunge.service.IMenuManagementService;
import com.xunge.service.system.region.ISysRegionService;

/** 
 * 拦截器：登录认证
 * @author SongJL
 */  
public class LoginInterceptor implements HandlerInterceptor{ 
	
    @Autowired
    private IMenuManagementService menuManService; 

	@Autowired
	private ISysRegionService sysRegionService;
    /** 
     * Handler执行完成之后调用这个方法 
     */  
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception exc)  
            throws Exception {  
          
    }  
  
    /** 
     * Handler执行之后，ModelAndView返回之前调用这个方法 
     */  
	public void postHandle(HttpServletRequest request, HttpServletResponse response,  
            Object handler, ModelAndView modelAndView) throws Exception {  
        HttpSession session = request.getSession(); 
        //ModelAndView返回之前调用，调用省份
        getPrvGroupJson(session);
        
    }  
  
    /** 
     * Handler执行之前调用这个方法 
     */  
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  
            Object handler) throws Exception {  
    	//获取Session  
        HttpSession session = request.getSession(); 
        
        //获取请求的URL  
        String url = request.getRequestURI();  
        
        if(url.indexOf(PromptMessageComm.URL_LOGIN)>=Integer.parseInt(SelfelecComm.NUMBER_0) || url.indexOf(PromptMessageComm.IMAGE)>=Integer.parseInt(SelfelecComm.NUMBER_0)){  
            return true;  
        }  
       
        /*if(url.indexOf(".html")>0 && session.getAttribute("user") != null){
        	String assertUrl = url.substring(url.indexOf("assert"), url.length());
        	boolean b_have_auth = false;
        	if(assertUrl.length()>0){
        		// 角色访问菜单权限检测
        		b_have_auth = menuManService.queryUrlAuthor(request, assertUrl);
        	}
        	return b_have_auth;
        }*/
        
        //ModelAndView返回之前调用，调用省份
        getPrvGroupJson(session);
        
        if(session.getAttribute("user") != null){  
            return true;  
        }  
        request.getRequestDispatcher(PromptMessageComm.URL_WEB_LOGIN).forward(request, response);
        
        return false;
    }  
	
    @SuppressWarnings("unchecked")
    private void getPrvGroupJson(HttpSession session){
    	if(session.getAttribute("prvGroupJson") == null){  
        	List<SysProvinceGroupVO> prvGroups=(List<SysProvinceGroupVO>)session.getAttribute("prvGroupJson");
    		if(prvGroups==null){
    			prvGroups = sysRegionService.selectByPrvGroup();
    		}
    		session.setAttribute("prvGroupJson", JsonMapper.getInstance().toJson(prvGroups));
        }
    }
}
