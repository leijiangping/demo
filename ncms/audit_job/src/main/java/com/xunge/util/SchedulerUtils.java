package com.xunge.util;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.util.CollectionUtils;


/**
 *  调度工具类
 *	@author SongJL
 */
public class SchedulerUtils  {
	private static final Logger LOGGER = Logger.getLogger(SchedulerUtils.class);
	private static StdSchedulerFactory factory;
	
	public SchedulerUtils(String fileName,boolean initFlag) throws SchedulerException {
		factory = new StdSchedulerFactory();
		if(initFlag){
			factory.initialize(fileName);// 根据配置文件初始化调度工厂
		}
	}
	
	/**
	 *  根据调度工厂获取一个调度控制器
	 * @return
	 * @throws SchedulerException
	 */
	public static synchronized Scheduler getScheduler() throws SchedulerException{
		if(null ==factory)factory = new StdSchedulerFactory();
		Scheduler scheduler=null;  
        try {  
            scheduler = factory.getScheduler();  
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  
        return scheduler;
	} 
	/**
	 *  停止调度控制器
	 * @param scheduler
	 * @param isStop true 为等job工作完停止，false 为立即停止（强制停止）
	 * @throws SchedulerException
	 */
	public static void stopScheduler(Scheduler scheduler,boolean isStop) throws SchedulerException{
		scheduler.shutdown(true);
	}
	
	/**
	 *  运行调度控制器
	 *  ----------------------------------------
	 *  
	 *  state的值代表该任务触发器的状态：
		STATE_BLOCKED 	4
		STATE_COMPLETE 	2
		STATE_ERROR 	3
		STATE_NONE 	-1
		STATE_NORMAL 	0
		STATE_PAUSED 	1
	 *  
	 * @param scheduler
	 * @param job
	 * @param trigger
	 * @param jobListener
	 * @param triggerListener
	 * @param schedulerListener
	 * @throws IllegalAccessException
	 * @throws InstantiationException 
	 */
	public static void runScheduler(Scheduler scheduler,JobDetail job,Trigger trigger) throws InstantiationException, IllegalAccessException{
		try {
			//注册监听器
			scheduler.scheduleJob(job, trigger);
			
			//启动调度控制器
			scheduler.start();
		} catch (SchedulerException e) {
			LOGGER.error("【调度控制器】执行任务时出错："+e.getMessage());
		}
	}
	
	/**
	 *  初始化任务详情
	 * @param jobClass
	 * @param jobName
	 * @param groupName
	 * @param map
	 * @param jobDesc
	 * @return
	 */
	public static JobDetail setJobs(Class<? extends Job> jobClass,String jobName,String groupName,Map<String,Object> map,String jobDesc){
		if(StringUtils.isEmpty(jobName)){
			LOGGER.info("【调度-Job】任务名称不能为空！");
			return null;
		}
		if(StringUtils.isEmpty(groupName)){
			LOGGER.info("【调度-Job】任务组名不能为空！");
			return null;
		}
		JobBuilder builder = newJob(jobClass).withIdentity(jobName, groupName);
		//添加参数的传递
		if(!CollectionUtils.isEmpty(map)){
			builder.setJobData(new JobDataMap(map));
		}
		if(!StringUtils.isEmpty(jobDesc))builder.withDescription(jobDesc); // job 描述
		return builder.storeDurably().build();
	}
	/**
	 *  固定调度策略
	 * @param triggerName
	 * @param triggerGroup
	 * @param jobDetail
	 * @param startTime
	 * @param endTime
	 * @param map
	 * @param priority
	 * @param triggerDesc
	 * @param isNow
	 * @return
	 */
	public static SimpleTrigger setSimpleTrigger(String triggerName,String triggerGroup,JobDetail jobDetail,Date startTime,Date endTime,Map<String,Object> map,
			Integer priority,String triggerDesc,Boolean isNow){
		return (SimpleTrigger) setTriggerWithSimpleOrCron(false, triggerName, triggerGroup, jobDetail, startTime, endTime, map, priority, triggerDesc, isNow, null);
	}
	/**
	 *  cron表达式策略
	 * @param triggerName
	 * @param triggerGroup
	 * @param jobDetail
	 * @param startTime
	 * @param endTime
	 * @param map
	 * @param priority
	 * @param triggerDesc
	 * @param isNow
	 * @param cronStr
	 * @return
	 */
	public static CronTrigger setCronTrigger(String triggerName,String triggerGroup,JobDetail jobDetail,Map<String,Object> map,
			Integer priority,String triggerDesc,String cronStr){
		return (CronTrigger) setTriggerWithSimpleOrCron(true, triggerName, triggerGroup, jobDetail, null, null, map, priority, triggerDesc, false, cronStr);
	}
	
	public static Trigger setTriggerWithSimpleOrCron(boolean isCron,String triggerName,String triggerGroup,JobDetail jobDetail,Date startTime,Date endTime,Map<String,Object> map,
			Integer priority,String triggerDesc,Boolean isNow,String cronStr){
		if(null == jobDetail){
			LOGGER.info("【调度-job】时间策略任务未指定");
			return null;
		}
		if(StringUtils.isEmpty(triggerName)){
			LOGGER.info("【调度-job】调度策略名称不能为空");
			return null;
		}
		if(StringUtils.isEmpty(triggerGroup)){
			LOGGER.info("【调度-job】调度策略组名不能为空");
			return null;
		}
		if(null == startTime) isNow = true;
		TriggerBuilder<Trigger> builder = newTrigger().forJob(jobDetail);
		if(CollectionUtils.isEmpty(map))builder.usingJobData(new JobDataMap(map));
		if(isCron){
			builder.withSchedule(cronSchedule(cronStr).inTimeZone(TimeZone.getTimeZone("Asia/Shanghai")));
		}else{
			if(isNow){
				builder.startNow();
			}else{
				builder.startAt(startTime);
				builder.endAt(endTime);
			}
		}
		if(!StringUtils.isEmpty(triggerDesc))builder.withDescription(triggerDesc);
		if(null != priority)builder.withPriority(priority);//设置优先级
		builder.withIdentity(triggerName,triggerGroup);
		return builder.build();
	}
	
	public static Trigger setTrigger(String triggerName,String triggerGroup,JobDetail jobDetail,Map<String,Object> map,
			Integer priority,String triggerDesc,ScheduleBuilder<? extends Trigger> schedBuilder){
		if(null == jobDetail){
			LOGGER.info("【调度-job】时间策略任务未指定");
			return null;
		}
		if(StringUtils.isEmpty(triggerName)){
			LOGGER.info("【调度-job】调度策略名称不能为空");
			return null;
		}
		if(StringUtils.isEmpty(triggerGroup)){
			LOGGER.info("【调度-job】调度策略组名不能为空");
			return null;
		}
		TriggerBuilder<Trigger> builder = newTrigger().forJob(jobDetail);
		if(!CollectionUtils.isEmpty(map))builder.usingJobData(new JobDataMap(map));
		builder.withSchedule(schedBuilder);
//		if(isCron){
//			builder.withSchedule(cronSchedule(cronStr).inTimeZone(TimeZone.getTimeZone("Asia/Shanghai")));
//		}else{
//			if(isNow){
//				builder.startNow();
//			}else{
//				builder.startAt(startTime);
//				builder.endAt(endTime);
//			}
//		}
		if(!StringUtils.isEmpty(triggerDesc))builder.withDescription(triggerDesc);
		if(null != priority)builder.withPriority(priority);//设置优先级
		builder.withIdentity(triggerName,triggerGroup);
		return builder.build();
	}
   
	/**
	 *  添加任务
	 * @param scheduler
	 * @param job
	 * @throws SchedulerException
	 */
	public static void addJob(Scheduler scheduler,JobDetail job) throws SchedulerException{
		scheduler.addJob(job, false);
	}
	/**
	 *  删除任务
	 * @param scheduler
	 * @param job
	 * @throws SchedulerException
	 */
	public static void delJob(Scheduler scheduler,JobDetail job) throws SchedulerException{
		scheduler.deleteJob(job.getKey());
	}
	/**
	 *  更新已经存在的任务
	 * @param scheduler
	 * @param job
	 * @throws SchedulerException
	 */
	public static void updateJob(Scheduler scheduler,JobDetail job) throws SchedulerException{
		scheduler.addJob(job, true);
	}
	/**
	 * 更新已经存在的调度策略
	 * @param scheduler
	 * @param oldTrigger
	 * @param newTrigger
	 * @throws SchedulerException
	 */
	public static void updateTrigger(Scheduler scheduler,Trigger oldTrigger,Trigger newTrigger) throws SchedulerException{
		scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
	}
	/**
	 *  执行一个已经存在的任务
	 * @param scheduler
	 * @param trigger
	 * @throws SchedulerException
	 */
	public static void excecuteJob(Scheduler scheduler,Trigger trigger) throws SchedulerException{
		scheduler.scheduleJob(trigger);
	}
	/**
	 *  检查任务是否存在
	 * @param taskName
	 * @param taskGroup
	 */
	public static  boolean checkTaskExist(Scheduler scheduler,JobDetail job){
		try {
			return  scheduler.checkExists(job.getKey());
		} catch (SchedulerException e) {
			LOGGER.error("****** 检索任务出现异常：    " +e.getStackTrace()+"\n"+e.getMessage());
		}
		return false;
	}
	
	public static boolean checkTiggerExist(Scheduler scheduler,Trigger trigger){
		try {
			return scheduler.checkExists(trigger.getKey());
		} catch (SchedulerException e) {
			LOGGER.error("****** 检索策略出现异常：    " +e.getStackTrace()+"\n"+e.getMessage());
		}
		return false;
	}
	
	/**
	 *  暂停任务
	 * @param taskName
	 * @param taskGroup
	 */
	public static void pauseTask(Scheduler scheduler,JobDetail job){
		try {
			scheduler.pauseJob(job.getKey());
		} catch (SchedulerException e) {
			LOGGER.error("****** 暂停任务出现异常：    " +e.getStackTrace()+"\n"+e.getMessage());
		}
	}
	/**
	 *  恢复任务
	 * @param taskName
	 * @param taskGroup
	 */
	public static void restoreTask(Scheduler scheduler,JobDetail job){
		try {
			scheduler.resumeJob(job.getKey());
		} catch (SchedulerException e) {
			LOGGER.error("****** 恢复任务出现异常：    " +e.getStackTrace()+"\n"+e.getMessage());
		}
	}
	/**
	 *  暂停调度
	 * @param taskName
	 * @param taskGroup
	 */
	public static void pauseTigger(Scheduler scheduler,Trigger trigger){
		try {
			scheduler.pauseTrigger(trigger.getKey());
		} catch (SchedulerException e) {
			LOGGER.error("****** 暂停调度出现异常：    " +e.getStackTrace()+"\n"+e.getMessage());
		}
	}
	/**
	 *  移除（删除）调度
	 * @param taskName
	 * @param taskGroup
	 */
	public static void deleteTigger(Scheduler scheduler,Trigger trigger){
		try {
			scheduler.unscheduleJob(trigger.getKey());
		} catch (SchedulerException e) {
			LOGGER.error("****** 山粗调度出现异常：    " +e.getStackTrace()+"\n"+e.getMessage());
		}
	}
	/**
	 * 恢复调度
	 * @param taskName
	 * @param taskGroup
	 */
	public static void restoreTigger(Scheduler scheduler,Trigger trigger){
		try {
			scheduler.resumeTrigger(trigger.getKey());
		} catch (SchedulerException e) {
			LOGGER.error("****** 恢复调度出现异常：    " +e.getStackTrace()+"\n"+e.getMessage());
		}
	}
	/**
	 *  获取所有的任务标识
	 * @param scheduler
	 * @return
	 * @throws SchedulerException
	 */
	public List<JobKey> getAllJob(Scheduler scheduler) throws SchedulerException{
		List<JobKey> jobKeys = new ArrayList<JobKey>();
		for(String group: scheduler.getJobGroupNames()) {
		    for(JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group))) {
		        System.out.println("Found job identified by: " + jobKey);
		        jobKeys.add(jobKey);
		    }
		}
		return jobKeys;
	}
	/**
	 *  获取所有的时间策略
	 * @param scheduler
	 * @return
	 * @throws SchedulerException
	 */
	public List<TriggerKey> getAllTrigger(Scheduler scheduler) throws SchedulerException{
		List<TriggerKey> triggerKeys = new ArrayList<TriggerKey>();
		for(String group: scheduler.getTriggerGroupNames()) {
		    for(TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(group))) {
		        System.out.println("Found trigger identified by: " + triggerKey);
		        triggerKeys.add(triggerKey);
		    }
		}
		return triggerKeys;
	}
	
	/**
	 *  通过任务找到执行这个任务的所有时间策略
	 * @param scheduler
	 * @param jobKey
	 * @return
	 * @throws SchedulerException
	 */
	@SuppressWarnings("unchecked")
	public List<Trigger> findTriggerByJobKey(Scheduler scheduler,JobKey jobKey) throws SchedulerException{
		return (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
	}
}
