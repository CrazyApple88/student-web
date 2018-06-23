package com.xhgx.web.admin.simpleQuery.entity;

import java.util.Set;

/**
 * @ClassName QueryResult
 * @Description 简单查询结果
 * @date: 2017-06-12 15:56</br>
 * @author <a href="mailto:inlw@sina.com">zohan</a> </br>
 * @version 1.0 
 */
public class QueryResult {
	private SimpleQuery simpleQuery;
	private Object result;
	private Boolean isSuccess = true;
	private Set keys;
	/**总数*/
	private int totalCount;
	private String errorMsg;

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public SimpleQuery getSimpleQuery() {
		return simpleQuery;
	}

	public void setSimpleQuery(SimpleQuery simpleQuery) {
		this.simpleQuery = simpleQuery;
	}

	public Boolean getSuccess() {
		return isSuccess;
	}

	public void setSuccess(Boolean success) {
		isSuccess = success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.setSuccess(false);
		this.errorMsg = errorMsg;
	}

	public Set getKeys() {
		return keys;
	}

	public void setKeys(Set keys) {
		this.keys = keys;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
