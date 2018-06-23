package com.xhgx.web.admin.version.service.impl;

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
import com.xhgx.web.admin.version.dao.VersionDao;
import com.xhgx.web.admin.version.entity.Version;
import com.xhgx.web.admin.version.service.VersionService;

/**
 * @ClassName VersionServiceImpl
 * @Description tb_sys_version 业务逻辑层实现
 * @author <font color='blue'>zhangjin</font>
 * @date 2017-06-12 15:22:03
 * @version 1.0
 * 
 */
@Transactional
@Component("versionService")
public class VersionServiceImpl extends BatisBaseServiceImpl implements
		VersionService {

	static Logger log = LoggerFactory.getLogger(VersionServiceImpl.class);

	private final static String CACHE_LIST = "version.cache.list";
	private final static String CACHE_MAP = "version.cache.map";

	@Override
	public GenericDao<Version, String> getGenericDao() {
		return versionDao;
	}

	@Autowired
	@Qualifier("versionDao")
	private VersionDao versionDao;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.zohan.sdk.framework.service.EntityService#init()
	 */
	@Override
	public void initCache() {
		log.info("开始初始化tb_sys_version数据");
		Map<String, Object> param = new HashMap();
		List<Version> list = this.findList(param, null);
		SimpleCacheHelper.put(CACHE_LIST, list);
		Map<String, Version> map = new ConcurrentHashMap<String, Version>();
		for (Version entity : list) {
			map.put(entity.getId(), entity);
		}
		SimpleCacheHelper.put(CACHE_MAP, map);
		log.info("始化tb_sys_version数据完毕，共缓存" + list.size() + "条数据");
	}

	@Override
	public void reloadCache() {
		// TODO 这里是重新加载缓存的方法
		// initCache();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see IVersionService#getVersion(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Version getVersion(String versionId) {
		Map<String, Version> map = (Map<String, Version>) SimpleCacheHelper
				.get(CACHE_MAP);
		Version version = null;
		if (map != null) {
			version = map.get(versionId);
		} else {
			map = new ConcurrentHashMap<String, Version>();
		}
		if (version == null) {
			version = (Version) versionDao.get(versionId);
			map.put(version.getId(), version);
			SimpleCacheHelper.put(CACHE_MAP, map);
		}
		return version;
	}

}