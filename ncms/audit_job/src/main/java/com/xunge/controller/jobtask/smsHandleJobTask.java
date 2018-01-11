package com.xunge.controller.jobtask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xunge.util.TimeUtils;

@Component
public class smsHandleJobTask {
	/** Logger */
    private static final Logger logger = LoggerFactory.getLogger(smsHandleJobTask.class);

	 /**
     * 业务逻辑处理
     */
    @Scheduled(cron="0/5 * * * * ? ")  //每5秒执行一次
    public void doBiz() {
    	// 执行业务逻辑
    	//logger.info(TimeUtils.getTime());
    }

}
