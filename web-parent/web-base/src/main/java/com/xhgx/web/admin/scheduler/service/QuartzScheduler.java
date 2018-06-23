package com.xhgx.web.admin.scheduler.service;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName QuartzScheduler
 * @Description 任务调度管理器
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class QuartzScheduler {

	private static final Logger log = LoggerFactory
			.getLogger(QuartzScheduler.class);

	/**quartz的scheduler*/
	private static Scheduler scheduler;
	private static QuartzScheduler quartzScheduler;

	private QuartzScheduler() {
		super();
	}

	/**
	 * 单例
	 *
	 */
	public static QuartzScheduler getInstance() {
		if (quartzScheduler == null) {
			quartzScheduler = new QuartzScheduler();
			quartzScheduler.init();
		}
		return quartzScheduler;
	}

	/**
	 * @throws SchedulerException
	 * @Title: init
	 * @date 2017-4-19 16:24:22
	 * @Description: 初始化任务调度器，并启动
	 */
	public void init() {
		try {
			if (scheduler == null) {
				scheduler = StdSchedulerFactory.getDefaultScheduler();
			}
			if (!scheduler.isStarted()) {
				scheduler.getListenerManager().addJobListener(
						new HistoryJobListener());
				scheduler.start();
			}
		} catch (SchedulerException e) {
			log.error("初始化任务调度器失败：" + e.getMessage());
		}

	}

	/**
	 * @Title: shutdown
	 * @date 2017-4-19 16:24:22
	 * @Description: 停止任务调度器的后台程序
	 */
	public void shutdown() {
		try {
			if (scheduler != null && !scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (SchedulerException e) {
			log.error("停止任务调度器失败：" + e.getMessage());
		}
	}

	/**
	 * @return
	 * @Title: clear
	 * @date 2017-4-19 16:24:22
	 * @Description: 清除所有的任务调度
	 */
	public boolean clear() {
		try {
			scheduler.clear();
			return true;
		} catch (SchedulerException e) {
			log.error("清除所有任务调度失败：" + e.getMessage());
			return false;
		}
	}

	/**
	 * @return
	 * @Title: pauseAll
	 * @date 2017-4-19 16:24:22
	 * @Description:暂停所有任务调度
	 */
	public boolean pauseAll() {
		try {
			scheduler.pauseAll();
			return true;
		} catch (SchedulerException e) {
			log.error("暂停所有任务调度失败：" + e.getMessage());
			return false;
		}
	}

	/**
	 * @return boolean
	 * @Title: resumeAll
	 * @date 2017-4-19 16:24:22
	 * @Description:重置所有任务调度
	 */
	public boolean resumeAll() {
		try {
			scheduler.resumeAll();
			return true;
		} catch (SchedulerException e) {
			log.error("重置所有任务调度失败：" + e.getMessage());
			return false;
		}
	}

	/**
	 * @param taskName
	 * @return boolean
	 * @Title: remove
	 * @date 2017-4-19 16:24:22
	 * @Description: 删除任务调度
	 */
	public boolean remove(String taskName) {
		try {
			return scheduler.deleteJob(new JobKey(taskName));

		} catch (SchedulerException e) {
			log.error("删除任务调度失败：" + e.getMessage());
		}

		return false;
	}

	/**
	 * @param taskName
	 * @return boolean
	 * @Title: checkExists
	 * @date 2017-4-19 16:24:22
	 * @Description:检测是否存在
	 */
	public boolean checkExists(String taskName) {

		try {
			return scheduler.checkExists(new JobKey(taskName));
		} catch (SchedulerException e) {
			log.error("检测任务调度是否存在失败：" + e.getMessage());
		}
		return false;
	}

	/**
	 * @param taskName
	 * @return boolean
	 * @Title: pause
	 * @date 2017-4-19 16:24:22
	 * @Description: 暂停调度
	 */
	public boolean pause(String taskName) {

		try {
			scheduler.pauseJob(new JobKey(taskName));
			return true;
		} catch (SchedulerException e) {
			log.error("暂停任务调度失败：" + e.getMessage());
		}
		return false;
	}

	/**
	 * @param taskName
	 * @return boolean
	 * @Title: resume
	 * @date 2017-4-19 16:24:22
	 * @Description: 重新启动任务调度
	 */
	public boolean resume(String taskName) {

		try {
			scheduler.resumeJob(new JobKey(taskName));
			return true;
		} catch (SchedulerException e) {
			log.error("重新启动任务调度失败：" + e.getMessage());
		}
		return false;
	}

	/**
	 * @param jobDetail
	 * @param trigger
	 * @throws SchedulerException
	 * @Title: addJob
	 * @date 2017-4-19 16:24:22
	 * @Description: 增加一个任务调度
	 */
	public void addJob(JobDetail jobDetail, Trigger trigger)
			throws SchedulerException {
		scheduler.scheduleJob(jobDetail, trigger);
	}

}
