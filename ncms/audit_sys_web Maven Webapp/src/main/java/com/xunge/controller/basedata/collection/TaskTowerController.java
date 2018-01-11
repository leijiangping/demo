package com.xunge.controller.basedata.collection;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.xunge.comm.TaskComm;
import com.xunge.comm.elec.SelfelecComm;
import com.xunge.comm.system.PromptMessageComm;
import com.xunge.controller.basedata.util.BaseRet;
import com.xunge.controller.basedata.util.RestServerUtils;
import com.xunge.core.exception.BaseException;
import com.xunge.core.model.UserLoginInfo;
import com.xunge.core.util.DateUtil;
import com.xunge.model.basedata.colletion.FtpFileConfigVO;
import com.xunge.model.basedata.colletion.FtpFileTypeVO;
import com.xunge.model.basedata.colletion.TaskInfoVO;
import com.xunge.service.basedata.collection.ITaskManagerService;

/**
 * 铁塔接口任务相关
 * @author wnagz
 * @2017-10-13 14:50:09
 */
@SessionAttributes(value={"user"},types={UserLoginInfo.class})
@RequestMapping("/asserts/tpl/basedata/collection/towertask")
@RestController
public class TaskTowerController extends BaseException {

	protected Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private ITaskManagerService taskManagerService;
    
    //定时任务
    @RequestMapping(value = "/start/{taskid}", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public BaseRet start(@PathVariable("taskid") String taskid){
    	BaseRet ret = new BaseRet();
    	int status = taskManagerService.startTask(taskid);
    	ret.setStatus(status);
    	if(Integer.parseInt(TaskComm.SUCCESS_0) == status){
    		ret.setMessage(PromptMessageComm.OPERATION_SUSSESS);
    	}
    	else {
    		ret.setMessage(PromptMessageComm.OPERATION_FAILED);
    	}
		return ret;
    }
    
    //定时任务停止
    @RequestMapping(value = "/stop/{taskid}", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public BaseRet stop(@PathVariable("taskid") String taskid){
    	
    	BaseRet ret = new BaseRet();
    	int status = taskManagerService.stopTask(taskid);
    	ret.setStatus(status);
    	if(Integer.parseInt(TaskComm.SUCCESS_0)==status){
    		ret.setMessage(PromptMessageComm.OPERATION_SUSSESS);
    	}
    	else{
    		ret.setMessage(PromptMessageComm.OPERATION_FAILED);
    	}
		return ret;
    }
    
    //立刻执行
    @RequestMapping(value = "/runnow/{taskid}", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public BaseRet runnow(@PathVariable("taskid") String taskid, HttpServletRequest request, 
    		@ModelAttribute("user") UserLoginInfo loginInfo){
    	
    	BaseRet ret = new BaseRet();
    	String user = loginInfo.getUser_loginname();
    	String towerDate = request.getParameter("towerDate");
    	String accountPeriod = null;
    	if (!StringUtils.isBlank(towerDate)) {
    		Date date = DateUtil.parse(towerDate, SelfelecComm.FORMAT_yyyy_MM_dd);
    		accountPeriod = DateUtil.format(date, SelfelecComm.FORMAT);
		}
       	int status = taskManagerService.runTowerTask(taskid, user, accountPeriod); 
    	ret.setStatus(status);
    	if(Integer.parseInt(TaskComm.SUCCESS_0) == status){
    		ret.setMessage(PromptMessageComm.OPERATION_SUSSESS);
    	} else {
    		ret.setMessage(PromptMessageComm.OPERATION_FAILED);
    	}
		return ret;
    }
    
    
    /**
     *  获取任务
     * @return
     */
    @RequestMapping(value = "/collectiontype/{collectiontype}", method = RequestMethod.GET,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public String getTaskInfo(HttpSession session,HttpServletRequest request,@PathVariable("collectiontype") int collectionType,
    		 @ModelAttribute("user") UserLoginInfo loginInfo){

    	BaseRet ret = new BaseRet();
    	String prvid=loginInfo.getPrv_id();
		try {
			// 获取任务
			TaskInfoVO task=taskManagerService.getTaskByCollectionType(collectionType,prvid);
			 
			ret.setData(task);
			ret.setStatus(Integer.parseInt(TaskComm.SUCCESS_0));
			ret.setMessage(PromptMessageComm.OPERATION_SUSSESS);
        }catch(Exception e){
        	ret.setStatus(Integer.parseInt(TaskComm.FAIL_1));
			ret.setMessage(PromptMessageComm.OPERATION_FAILED);
			LOGGER.error(e.getMessage());
        }
		return RestServerUtils.autoReturn(ret, PromptMessageComm.JSON);
    }
    
    
    /**
     * 修改
     */
    @RequestMapping(value = "/edittask", method = RequestMethod.POST)
    @ResponseBody
    public BaseRet editTaskInfo(TaskInfoVO task, @ModelAttribute("user") UserLoginInfo loginInfo){
    
    	task.setPrvId(loginInfo.getPrv_id());

    	BaseRet ret = new BaseRet();
    	try{
    		boolean result= taskManagerService.editTask(task);
        	if(result){
        		ret.setStatus(Integer.parseInt(TaskComm.SUCCESS_0));
        		ret.setMessage(PromptMessageComm.OPERATION_SUSSESS);
        	}
        	else{
        		ret.setStatus(Integer.parseInt(TaskComm.FAIL_1));
    			 ret.setMessage(PromptMessageComm.OPERATION_FAILED);
        	}
    	}
    	catch(Exception e){
    		LOGGER.error(e.getMessage());
    	}
    	
    	return ret;
    }
    
    
    
    /**
     *  获取ftp文件配置
     * @return
     */
    @RequestMapping(value = "/ftpfileconfig", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public BaseRet getFtpFileConfig(String taskid){

    	BaseRet ret = new BaseRet();
		try {
			List<FtpFileConfigVO> configs=taskManagerService.getFtpFileConfigByTaskId(taskid);
			 ret.setData(configs);
			 ret.setStatus(Integer.parseInt(TaskComm.SUCCESS_0));
			 ret.setMessage(PromptMessageComm.OPERATION_SUSSESS);
        }catch(Exception e){
        	ret.setStatus(Integer.parseInt(TaskComm.FAIL_1));
			 ret.setMessage(PromptMessageComm.OPERATION_FAILED);
			 LOGGER.error(e.getMessage());
        }
		return ret;
    }
    
    
    /**
     * 添加
     */
    @RequestMapping(value = "/ftpconfig/add", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public BaseRet addFtpConfig(FtpFileConfigVO ftpFileConfig){
    	BaseRet ret = new BaseRet();
    	if(!isExistFtpConfig(ftpFileConfig)){
    		ftpFileConfig.setFieldConfig(1);
    		boolean result= taskManagerService.addFtpConfig(ftpFileConfig);
        	if(result){
        		ret.setStatus(Integer.parseInt(TaskComm.SUCCESS_0));
        		ret.setMessage(PromptMessageComm.OPERATION_SUSSESS);
        	}
        	else{
        		ret.setStatus(Integer.parseInt(TaskComm.FAIL_1));
    			ret.setMessage(PromptMessageComm.OPERATION_FAILED);
        	}
    	}
    	else{
    		ret.setStatus(Integer.parseInt(TaskComm.CFG_SUCCESS_1));
			ret.setMessage(PromptMessageComm.CONFIG_EXIST);
    	}
    	return ret;
    }
    
    /**
     * 修改
     */
    @RequestMapping(value = "/ftpconfig/edit", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public BaseRet editFtpConfig(FtpFileConfigVO ftpFileConfig){
    	BaseRet ret = new BaseRet();
		boolean result= taskManagerService.editFtpConfig(ftpFileConfig);
    	if(result){
    		ftpFileConfig.setFieldConfig(1);
    		ret.setStatus(Integer.parseInt(TaskComm.SUCCESS_0));
    		ret.setMessage(PromptMessageComm.OPERATION_SUSSESS);
    	}
    	else{
    		ret.setStatus(Integer.parseInt(TaskComm.FAIL_1));
			 ret.setMessage(PromptMessageComm.OPERATION_FAILED);
    	}
	
    	return ret;
    }
    
    /**
     * 删除
     */
    @RequestMapping(value = "/ftpconfig/del", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public BaseRet delFtpConfig(String ids){
    	
    	BaseRet ret = new BaseRet();
    	String[] idArray = ids.split(",");
    	boolean result= taskManagerService.delFtpConfig(idArray);
    	if(result){
    		ret.setStatus(Integer.parseInt(TaskComm.SUCCESS_0));
    		ret.setMessage(PromptMessageComm.OPERATION_SUSSESS);
    	}
    	else{
    		ret.setStatus(Integer.parseInt(TaskComm.FAIL_1));
			 ret.setMessage(PromptMessageComm.OPERATION_FAILED);
    	}
    	return ret;
    }
    
    /**
     *  获取ftp类型配置
     * @return
     */
    @RequestMapping(value = "/ftpfiletype/{typeGroupId}", method = RequestMethod.GET,produces= "application/json;charset=UTF-8")
    @ResponseBody
    public String getFtpFileType(@PathVariable("typeGroupId") int typeGroupId){

    	BaseRet ret = new BaseRet();
		try {
			List<FtpFileTypeVO> configs=taskManagerService.getFtpFileTypebyGroupId(typeGroupId);
			 ret.setData(configs);
			 ret.setStatus(Integer.parseInt(TaskComm.SUCCESS_0));
			 ret.setMessage(PromptMessageComm.OPERATION_SUSSESS);
        }catch(Exception e){
        	ret.setStatus(Integer.parseInt(TaskComm.FAIL_1));
			 ret.setMessage(PromptMessageComm.OPERATION_FAILED);
			 LOGGER.error(e.getMessage());
        }
		return RestServerUtils.autoReturn(ret, PromptMessageComm.JSON);
    }
    
    
    private boolean isExistFtpConfig(FtpFileConfigVO ftpFileConfig){
    	
    	boolean isExist=false;
    	String taskId=ftpFileConfig.getTaskId();
    	List<FtpFileConfigVO> configs=taskManagerService.getFtpFileConfigByTaskId(taskId);
    	for(FtpFileConfigVO ftpFileConfigVO:configs){
    		if(ftpFileConfigVO.getFilePerfix().equalsIgnoreCase(ftpFileConfig.getFilePerfix())){
    			isExist=true;
    			break;
    		}
    	}
		return isExist;
    }
}
