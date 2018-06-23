package com.xhgx.web.admin.scheduler.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xhgx.sdk.cache.SimpleCacheHelper;
import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.scheduler.dao.SchedulerHistoryDao;
import com.xhgx.web.admin.scheduler.entity.SchedulerHistory;
import com.xhgx.web.admin.scheduler.service.ISchedulerHistoryService;

/**
 * @ClassName SchedulerHistoryServiceImpl
 * @Description 定时历史信息 业务逻辑层实现
 * @author <font color='blue'>zohan</font>
 * @date 2017-05-18 17:10:17
 * @version 1.0
 */
@Transactional
@Component("schedulerHistoryService")
public class SchedulerHistoryServiceImpl extends BatisBaseServiceImpl implements
		ISchedulerHistoryService {

	static Logger log = LoggerFactory
			.getLogger(SchedulerHistoryServiceImpl.class);

	private final static String CACHE_LIST = "schedulerHistory.cache.list";
	private final static String CACHE_MAP = "schedulerHistory.cache.map";

	@Override
	public GenericDao<SchedulerHistory, String> getGenericDao() {
		return schedulerHistoryDao;
	}

	@Autowired
	@Qualifier("schedulerHistoryDao")
	private SchedulerHistoryDao schedulerHistoryDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zohan.sdk.framework.service.EntityService#init()
	 */

	public void initCache() {
		log.info("开始初始化定时历史信息数据");
		Map<String, Object> param = new HashMap();
		List<SchedulerHistory> list = this.findList(param, null);
		SimpleCacheHelper.put(CACHE_LIST, list);
		Map<String, SchedulerHistory> map = new ConcurrentHashMap<String, SchedulerHistory>();
		for (SchedulerHistory entity : list) {
			map.put(entity.getId(), entity);
		}
		SimpleCacheHelper.put(CACHE_MAP, map);
		log.info("始化定时历史信息数据完毕，共缓存" + list.size() + "条数据");
	}

	public void reloadCache() {
		// TODO 这里是重新加载缓存的方法
		// initCache();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see ISchedulerHistoryService#getSchedulerHistory(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public SchedulerHistory getSchedulerHistory(String schedulerHistoryId) {
		Map<String, SchedulerHistory> map = (Map<String, SchedulerHistory>) SimpleCacheHelper
				.get(CACHE_MAP);
		SchedulerHistory schedulerHistory = null;
		if (map != null) {
			schedulerHistory = map.get(schedulerHistoryId);
		} else {
			map = new ConcurrentHashMap<String, SchedulerHistory>();
		}
		if (schedulerHistory == null) {
			schedulerHistory = (SchedulerHistory) schedulerHistoryDao
					.get(schedulerHistoryId);
			map.put(schedulerHistory.getId(), schedulerHistory);
			SimpleCacheHelper.put(CACHE_MAP, map);
		}
		return schedulerHistory;
	}

}