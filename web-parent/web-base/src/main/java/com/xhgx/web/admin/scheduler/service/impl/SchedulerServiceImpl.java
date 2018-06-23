package com.xhgx.web.admin.scheduler.service.impl;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.entity.OnOffEnum;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.scheduler.dao.SchedulerDao;
import com.xhgx.web.admin.scheduler.entity.Scheduler;
import com.xhgx.web.admin.scheduler.service.ISchedulerService;
import com.xhgx.web.admin.scheduler.service.QuartzScheduler;

/**
 * @ClassName SchedulerServiceImpl
 * @Description schedulerservice实现
 * @author zohan(inlw@sina.com)
 * @date 2017-04-18 18:57
 * @vresion 1.0
 */
@Component("schedulerService")
public class SchedulerServiceImpl extends BatisBaseServiceImpl implements
		ISchedulerService {

	static Logger log = LoggerFactory.getLogger(SchedulerServiceImpl.class);
	@Autowired
	@Qualifier("schedulerDao")
	private SchedulerDao schedulerDAO;

	@Override
	protected GenericDao getGenericDao() {
		return schedulerDAO;
	}

	/**
	 * 物理删除定时器，并从进程中删除
	 *
	 * @param schedulerId
	 * @return boolean
	 */
	public boolean removeScheduler(String schedulerId) {
		Scheduler scheduler = schedulerDAO.get(schedulerId);
		boolean result = QuartzScheduler.getInstance().remove(
				scheduler.getName());
		if (result) {
			schedulerDAO.delete(schedulerId);
			return true;
		}
		return false;
	}

	/**
	 * 暂停定时器，并更改状态
	 *
	 * @param schedulerId
	 * @return boolean
	 */
	public boolean pause(String schedulerId) {

		Scheduler scheduler = schedulerDAO.get(schedulerId);
		boolean result = QuartzScheduler.getInstance().pause(
				scheduler.getName());
		if (result) {
			scheduler.setState(OnOffEnum.off);
			schedulerDAO.update(scheduler);
			return true;
		}
		return false;
	}

	/**
	 * 重置定时器状态，并同步数据库中的状态
	 *
	 * @param schedulerId
	 * @return boolean
	 */
	public boolean resume(String schedulerId) {
		Scheduler scheduler = schedulerDAO.get(schedulerId);
		boolean result = QuartzScheduler.getInstance().resume(
				scheduler.getName());
		if (result) {
			scheduler.setState(OnOffEnum.on);
			schedulerDAO.update(scheduler);
			return true;
		}
		return false;
	}

	/**
	 * 创建定时器，并根据状态是否启动
	 *
	 * @param scheduler
	 * @return boolean
	 */
	public boolean add(Scheduler scheduler) {
		String className = scheduler.getEntityClass();
		Class<? extends Job> zlass;
		try {
			zlass = (Class<? extends Job>) Class.forName(className);
			// 之前有在运行的先删除
			if (StringUtils.isNotBlank(scheduler.getId())) {
				Scheduler sc = schedulerDAO.get(scheduler.getId());
				boolean result = QuartzScheduler.getInstance().checkExists(
						sc.getName());
				if (result) {
					QuartzScheduler.getInstance().remove(sc.getName());
				}
				/*
				 * if (result && OnOffEnum.on == sc.getState()) { // boolean
				 * result = QuartzScheduler.getInstance().pause(sc.getName());
				 * QuartzScheduler.getInstance().remove(sc.getName());
				 * log.info("删除定期器成功：{}", sc.getName()); }else
				 */

			}

			JobDetail job = newJob(zlass).withIdentity(scheduler.getName())
					.build();
			job.getJobDataMap().put("data", scheduler.getParm());
			job.getJobDataMap().put("id", scheduler.getId());

			// Trigger trigger =
			TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger()
					.withIdentity(scheduler.getName() + "Trigger");
			triggerBuilder.startAt(scheduler.getStartTime());
			triggerBuilder.endAt(scheduler.getEntTime());
			if (Scheduler.JobTpe.repeat == scheduler.getJobType()) {
				triggerBuilder
						.withSchedule(
								simpleSchedule().withIntervalInSeconds(
										scheduler.getRepeatInterval())
										.repeatHourlyForTotalCount(
												scheduler.getRepeatCount()))
						.startAt(scheduler.getStartTime())
						.startAt(scheduler.getEntTime());
			} else {
				triggerBuilder
						.withSchedule(
								CronScheduleBuilder.cronSchedule(scheduler
										.getCorn()).withMisfireHandlingInstructionDoNothing())
						.endAt(scheduler.getEntTime())
						.startAt(scheduler.getStartTime());
			}
			Trigger trigger = triggerBuilder.build();
			QuartzScheduler.getInstance().addJob(job, trigger);
			scheduler.setState(OnOffEnum.on);
			if (StringUtils.isEmpty(scheduler.getId())) {
				this.save(scheduler);
			} else {
				this.update(scheduler);
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("创建调度任务失败：{}", e.getMessage());
		} catch (SchedulerException e) {
			log.error("创建调度任务失败：{}", e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 启动任务
	 */
	public void startScheduler() {

		log.info("开始初始化定时调度管理数据");
		int count = 0;
		// List<Scheduler> list = schedulerDAO.getAll();

		Map map = new HashMap();
		map.put("state", OnOffEnum.on);
		List<Scheduler> list = (List<Scheduler>) this.findList(map, null);
		for (Scheduler entity : list) {
			if (add(entity)) {
				count++;
			}
		}
		log.info("始化定时调度管理数据完毕，共{}个,启动" + count + "个任务调度", list.size());
	}

	@Override
	public void initCache() {
		startScheduler();
	}

	@Override
	public void reloadCache() {

	}

	@Override
	public void executeConunt(String schedulerId) {
		schedulerDAO.executeConunt(schedulerId);
	}

}
