package com.xhgx.web.admin.scheduler.entity;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName SchedulerHistory
 * @Description 定时历史信息实体信息
 * @author <font color='blue'>zohan</font>
 * @date 2017-05-18 17:44:21
 * @version 1.0
 */
@AutoUUID
public class SchedulerHistory extends BaseEntity<String> {

	/** serialVersionUID */

	private static final long serialVersionUID = 4841799362659062357L;
    
	/**主键*/
	private String id; 
	/**调度名称*/
	private String name;
	/**分组名称*/
	private String groupName; 
	/**执行结果*/
	private String result;
	/**开始时间*/
	private java.util.Date startTime; 
	/**结束时间*/
	private java.util.Date endTime;
	/**执行耗时*/
	private long totalTime;
	/**执行次数*/
	private Integer count; 

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * <b>方法名</b>：getId<br>
	 * <b>功能</b>：取得主键<br>
	 * 
	 * @author <font color='blue'>zohan</font>
	 * @date 2017-05-18 17:44:21
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public java.util.Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	public java.util.Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}

	public long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
