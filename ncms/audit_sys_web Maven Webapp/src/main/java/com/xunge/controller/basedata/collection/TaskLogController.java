package com.xunge.controller.basedata.collection;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.system.PromptMessageComm;
import com.xunge.comm.system.RESULT;
import com.xunge.core.exception.BusinessException;
import com.xunge.core.model.FeedBackObject;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.service.basedata.collection.ITaskManagerService;

/**
 * 任务执行日志
 */
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@RequestMapping("/asserts/tpl/basedata/collection")
@RestController
public class TaskLogController {

	protected Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private ITaskManagerService taskManageService;

    @RequestMapping(value = "/queryHistoryLogMsg", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public FeedBackObject queryLogMsg(@RequestParam Map<String,Object> map,@ModelAttribute("user")UserLoginInfo loginUser){
    	if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
    	FeedBackObject ret = new FeedBackObject();
    	map.put("prvId", loginUser.getPrv_id());
    	String status = RESULT.SUCCESS_1;
    	ret.Obj = taskManageService.queryTaskHistoryLog(map);
    	ret.setSuccess(status);
    	if(RESULT.SUCCESS_1.equals(status)){
    		ret.setMsg(PromptMessageComm.OPERATION_SUSSESS);
    	}
    	else{
    		ret.setMsg(PromptMessageComm.OPERATION_FAILED);
    	}
		return ret;
    }
    
    @RequestMapping(value = "/clearHistoryLogMsg", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public FeedBackObject clearLogMsg(@ModelAttribute("user")UserLoginInfo loginUser,@RequestParam String prvId){
    	if(loginUser == null){
			throw new BusinessException(PromptMessageComm.LOGIN_FAILED);
		}
    	FeedBackObject ret = new FeedBackObject();
    	String status = RESULT.SUCCESS_1;
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("prvId", prvId);
    	int result = taskManageService.clearHistoryLog(paramMap);
    	ret.setSuccess(result + "");
    	if(RESULT.SUCCESS_1.equals(status)){
    		ret.setMsg(PromptMessageComm.OPERATION_SUSSESS);
    	}
    	else{
    		ret.setMsg(PromptMessageComm.OPERATION_FAILED);
    	}
		return ret;
    }
}
