package com.xhgx.web.admin.scheduler.service;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import com.xhgx.sdk.container.SpringContextUtil;
import com.xhgx.web.admin.scheduler.entity.SchedulerHistory;

/**
 * @ClassName HistoryJobListener
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-04-19 14:49
 * @vresion 1.0
 */
public class HistoryJobListener implements JobListener {

	private final String NAME = "HistoryJobListener";

	public String getName() {
		return NAME;
	}

	public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {

	}

	public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

	}

	public void jobWasExecuted(JobExecutionContext jobExecutionContext,
			JobExecutionException e) {
		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		Date startTime = jobExecutionContext.getFireTime();
		Date endTime = new Date();
		// 真正的运行时间
		long runTime = jobExecutionContext.getJobRunTime();
		SchedulerHistory sh = new SchedulerHistory();

		sh.setEndTime(endTime);
		sh.setStartTime(startTime);
		sh.setTotalTime(runTime);
		sh.setCount(jobExecutionContext.getRefireCount());
		sh.setName(jobDetail.getKey().getName());
		sh.setGroupName(jobDetail.getKey().getGroup());

		ISchedulerHistoryService service = (ISchedulerHistoryService) SpringContextUtil
				.getBean("schedulerHistoryService");
		service.save(sh);
		Map map = jobDetail.getJobDataMap();
		if (map != null) {
			String id = (String) map.get("id");
			if (StringUtils.isNotBlank(id)) {
				ISchedulerService schedulerService = (ISchedulerService) SpringContextUtil
						.getBean("schedulerService");
				schedulerService.executeConunt(id);
			}
		}
	}

}
