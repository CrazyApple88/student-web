package com.xhgx.web.admin.log.entity;

import java.util.Date;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;

/**
 * @ClassName LogEntity
 * @Description 操作日志表实体对象
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@AutoUUID
public class LogEntity extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;
	private String id;
	/**日志类型*/
	private int type;  
	/**日志标题*/
	private String title;  
	/**请求URI*/
	private String requestUrl;  
	/**操作方式*/
	private String method;  
	/**操作提交的数据*/
	private String params; 
	/**异常信息*/
	private String exception;  
	/**操作IP地址*/
	private String remoteAddr; 
	/**创建者*/
	private String createBy; 
	/**创建时间*/
	private Date createDate; 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
