package com.xhgx.web.admin.scheduler.dao;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.web.admin.scheduler.entity.Scheduler;

/**
 * @ClassName SchedulerDao
 * @Description 定时器的持久化层
 * @author zohan(inlw@sina.com)
 * @date 2017-04-18 18:52
 * @vresion 1.0
 */
public interface SchedulerDao extends GenericDao<Scheduler, String> {

	/**
	 * 记录执行测试
	 * 
	 * @param id
	 */
	void executeConunt(String id);

}
