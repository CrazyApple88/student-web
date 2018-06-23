package com.xhgx.web.admin.simpleQuery.entity;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName SimpleQuery
 * @Description 简单查询实体信息
 * @author <font color='blue'>zohan</font>
 * @date 2017-06-12 17:16:22
 * @version 1.0
 */
@AutoUUID
public class SimpleQuery extends BaseEntity<String> {

	/** serialVersionUID */

	private static final long serialVersionUID = 4841799362659062357L;
    /**id*/
	private String id;
	/**名称*/
	private String name; 
	/**描述*/
	private String mark; 
	/**日期*/
	private java.util.Date createDate;
	/**脚本*/
	private String content; 
	/**耗时*/
	private Integer lastUseTime;
	/**次数*/
	private Integer count;

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * <b>方法名</b>：getId<br>
	 * <b>功能</b>：取得id<br>
	 * 
	 * @author <font color='blue'>zohan</font>
	 * @date 2017-06-12 17:16:22
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

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getLastUseTime() {
		return this.lastUseTime;
	}

	public void setLastUseTime(Integer lastUseTime) {
		this.lastUseTime = lastUseTime;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
