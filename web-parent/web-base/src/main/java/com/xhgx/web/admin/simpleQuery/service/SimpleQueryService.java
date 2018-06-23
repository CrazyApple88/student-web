package com.xhgx.web.admin.simpleQuery.service;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;
import com.xhgx.web.admin.simpleQuery.entity.QueryResult;
import com.xhgx.web.admin.simpleQuery.entity.SimpleQuery;

/**
 * @ClassName SimpleQueryService
 * @Description 简单查询 业务逻辑层接口
 * @author <font color='blue'>zohan</font>
 * @date 2017-06-12 17:16:22
 * @version 1.0
 */
public interface SimpleQueryService extends BaseService, CacheService {

	/**
	 * 通过id快速加载对象
	 * 
	 * @param simpleQueryId
	 * @return SimpleQuery
	 */
	public SimpleQuery getSimpleQuery(String simpleQueryId);

	/**
	 * 查询结果
	 * 
	 * @param simpleQueryId
	 * @return QueryResult
	 */
	QueryResult updateQuery(String simpleQueryId);

}