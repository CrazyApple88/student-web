package com.xhgx.web.admin.scheduler.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;
import com.xhgx.sdk.entity.OnOffEnum;

/**
 * @ClassName Scheduler
 * @Description scheduler 对象
 * @author zohan(inlw@sina.com)
 * @date 2017-04-18 18:25
 * @vresion 1.0
 */
@AutoUUID
public class Scheduler extends BaseEntity<String> {

	/**任务类型*/
	public enum JobTpe {
		cron, repeat
	}

	/**id*/
	private String id;
	/**定时器名称，便于识别*/

	@NotNull
	private String name;
	/**定时器类型，是cron 还是 repeat*/
	private JobTpe jobType;
	/**corn 表达式*/
	@NotNull(message = "表达式不能为空")
	private String corn;
	/**运行时参数*/
	private String parm;
	/**运行的job类*/
	private String entityClass;
	/**执行的开始时间*/
	private Date startTime;
	/**定时器的结束时间*/
	private Date entTime;
	/**定时器重复次数*/
	private Integer repeatCount;
	/**定时器执行间隔*/
	private Integer repeatInterval;
	/**定时器的状态*/
	private OnOffEnum state;
	/**已经执行的次数*/
	private Integer count;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JobTpe getJobType() {
		return jobType;
	}

	public void setJobType(JobTpe jobType) {
		this.jobType = jobType;
	}

	public String getCorn() {
		return corn;
	}

	public void setCorn(String corn) {
		this.corn = corn;
	}

	public String getParm() {
		return parm;
	}

	public void setParm(String parm) {
		this.parm = parm;
	}

	public String getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEntTime() {
		return entTime;
	}

	public void setEntTime(Date entTime) {
		this.entTime = entTime;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public Integer getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(Integer repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public OnOffEnum getState() {
		return state;
	}

	public void setState(OnOffEnum state) {
		this.state = state;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
