package com.xunge.controller.activity;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.core.exception.BaseException;
import com.xunge.model.FeedBackObject;
  
/**
 * @author 朱俊杰 
 * @date 2017年6月13日 上午9:34:16
 * @describe
 */
@Controller
@RequestMapping("/asserts/tpl/system/activity/act/process-editor")
public class ActivityController extends BaseException {
	   protected Logger logger = LoggerFactory.getLogger(getClass());


	   @RequestMapping(value = "/modeler", method = RequestMethod.GET)
	   public  String modelerList(HttpServletRequest request) {
		  String modelId= request.getParameter("modelId");
		   //重定向
		   System.out.println(PromptMessageComm.REDIRECT_GET);
		return PromptMessageComm.WAY_REDIRECT+modelId;
	   }
		
	   @RequestMapping(value = "/modeler", method = RequestMethod.POST)
	   public  String modelerPost(HttpServletRequest request) {
		   //重定向
			  String modelId= request.getParameter("modelId");
		   System.out.println(PromptMessageComm.REDIRECT_POST);
		return PromptMessageComm.WAY_REDIRECT+modelId;
	   }
		
		

}
