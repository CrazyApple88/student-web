package com.xhgx.web.admin.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @ClassName SimpleJob
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-18 18:36
 * @vresion 1.0
 */
public class SimpleJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String data = String.valueOf(context.getMergedJobDataMap().get("data"));
		System.out
				.println("***********************************************************************");
		System.out
				.println("*************                                          ****************");
		System.out.println("*************  测试定时器正在执行中");
		System.out.println("*************  线程名称："
				+ Thread.currentThread().getName());
		System.out.println("*************  参数：" + data);
		System.out
				.println("*************                                          ****************");
		System.out
				.println("***********************************************************************");
	}
}
