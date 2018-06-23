package com.xhgx.web.admin.scheduler.service;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;
import com.xhgx.web.admin.scheduler.entity.Scheduler;

/**
 * @ClassName ISchedulerService
 * @Description 定时器业务逻辑层
 * @author zohan(inlw@sina.com)
 * @date 2017-04-18 18:53
 * @vresion 1.0
 */
public interface ISchedulerService extends BaseService, CacheService {

	/**
	 * 启动所有状态为开启的调度任务
	 */
	void startScheduler();

	/**
	 * 删除 指定的 任务调度，并修改数据库中的状态
	 *
	 * @param schedulerId
	 * @return boolean
	 */
	boolean removeScheduler(String schedulerId);

	/**
	 * 暂停任务调度，并修改数据库中的状态
	 *
	 * @param schedulerId
	 * @return boolean
	 */
	boolean pause(String schedulerId);

	/**
	 * 重新启动任务调度
	 *
	 * @param schedulerId
	 * @return boolean
	 */
	boolean resume(String schedulerId);

	/**
	 * 增加一个任务调度
	 *
	 * @param scheduler
	 * @return boolean
	 */
	boolean add(Scheduler scheduler);

	/**
	 * 执行次数
	 * 
	 * @param schedulerId
	 */
	void executeConunt(String schedulerId);

}
