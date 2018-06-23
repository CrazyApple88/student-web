package com.xhgx.web.admin.simpleQuery.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xhgx.sdk.cache.SimpleCacheHelper;
import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.simpleQuery.dao.SimpleQueryDao;
import com.xhgx.web.admin.simpleQuery.entity.QueryResult;
import com.xhgx.web.admin.simpleQuery.entity.SimpleQuery;
import com.xhgx.web.admin.simpleQuery.service.SimpleQueryService;

/**
 * @ClassName SimpleQueryServiceImpl
 * @Description 简单查询 业务逻辑层实现
 * @author <font color='blue'>zohan</font>
 * @date 2017-06-12 17:16:22
 * @version 1.0
 */
@Transactional
@Component("simpleQueryService")
public class SimpleQueryServiceImpl extends BatisBaseServiceImpl implements
		SimpleQueryService {

	static Logger log = LoggerFactory.getLogger(SimpleQueryServiceImpl.class);

	private final static String CACHE_LIST = "simpleQuery.cache.list";
	private final static String CACHE_MAP = "simpleQuery.cache.map";

	@Override
	public GenericDao<SimpleQuery, String> getGenericDao() {
		return simpleQueryDao;
	}

	@Autowired
	@Qualifier("simpleQueryDao")
	private SimpleQueryDao simpleQueryDao;

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.zohan.sdk.framework.service.EntityService#init()
	 */
	@Override
	public void initCache() {
		log.info("开始初始化简单查询数据");
		Map<String, Object> param = new HashMap();
		List<SimpleQuery> list = this.findList(param, null);
		SimpleCacheHelper.put(CACHE_LIST, list);
		Map<String, SimpleQuery> map = new ConcurrentHashMap<String, SimpleQuery>();
		for (SimpleQuery entity : list) {
			map.put(entity.getId(), entity);
		}
		SimpleCacheHelper.put(CACHE_MAP, map);
		log.info("始化简单查询数据完毕，共缓存" + list.size() + "条数据");
	}

	@Override
	public void reloadCache() {
		// TODO 这里是重新加载缓存的方法
		// initCache();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see ISimpleQueryService#getSimpleQuery(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SimpleQuery getSimpleQuery(String simpleQueryId) {
		Map<String, SimpleQuery> map = (Map<String, SimpleQuery>) SimpleCacheHelper
				.get(CACHE_MAP);
		SimpleQuery simpleQuery = null;
		if (map != null) {
			simpleQuery = map.get(simpleQueryId);
		} else {
			map = new ConcurrentHashMap<String, SimpleQuery>();
		}
		if (simpleQuery == null) {
			simpleQuery = (SimpleQuery) simpleQueryDao.get(simpleQueryId);
			map.put(simpleQuery.getId(), simpleQuery);
			SimpleCacheHelper.put(CACHE_MAP, map);
		}
		return simpleQuery;
	}

	@Override
	public QueryResult updateQuery(String simpleQueryId) {

		SimpleQuery simpleQuery = (SimpleQuery) simpleQueryDao
				.get(simpleQueryId);

		QueryResult result = new QueryResult();
		if (simpleQuery != null) {

			String sql = simpleQuery.getContent();
			if (StringUtils.isNotBlank(sql)) {
				long startTime = System.currentTimeMillis();
				List list = jdbcTemplate.queryForList(sql);
				long endTime = System.currentTimeMillis();
				if (!list.isEmpty()) {
					Map map = (Map) list.get(0);
					result.setKeys(map.keySet());
				}
				simpleQuery.setLastUseTime((int) (endTime - startTime));
				simpleQuery.setCount(simpleQuery.getCount() + 1);
				this.update(simpleQuery);
				result.setResult(list);
				result.setTotalCount(list.size());
				result.setSimpleQuery(simpleQuery);
			} else {
				result.setErrorMsg("查询信息为空");
			}

		} else {
			result.setErrorMsg("没有找到对应的查询信息");
		}

		return result;
	}

}