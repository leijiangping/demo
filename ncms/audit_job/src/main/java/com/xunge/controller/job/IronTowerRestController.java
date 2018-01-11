package com.xunge.controller.job;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xunge.comm.job.BaseRet;
import com.xunge.comm.job.MultiThread;
import com.xunge.comm.job.RestServerUtils;
import com.xunge.model.colletion.TaskInfoVO;
import com.xunge.service.job.IJobSyncService;


@RestController
@RequestMapping("/restful/irontower/")
public class IronTowerRestController {
	
	private static final Logger LOGGER = Logger.getLogger(IronTowerRestController.class);
	
	@Resource
	private IJobSyncService jobSyncService;
	@Autowired
    private ThreadPoolTaskExecutor taskExecutor;
	
	/**
	 * 同步铁塔数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "syncdata", method = RequestMethod.POST,produces= "application/json;charset=UTF-8")
	public String syncData(HttpServletRequest request,HttpServletResponse response,@RequestBody TaskInfoVO taskInfo) {
		RestServerUtils.setResponse(response);
		BaseRet ret = new BaseRet();
//		jobSyncService.syncTowerData(taskInfo);
		taskExecutor.execute(new MultiThread(jobSyncService,taskInfo,"tower"));
		ret.setStatus(0);
		ret.setMessage("同步命令发送成功!");
		LOGGER.info("--------已接收调起任务通知：铁塔数据导入");
		return RestServerUtils.autoReturn(ret, "json");
	}
}
