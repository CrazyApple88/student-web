package com.xhgx.sdk.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SimpleCacheManager
 * @Description 默认缓存管理器
 * @author zohan(inlw@sina.com)
 * @date 2017-03-22 下午02:00:14
 * @vresion 1.0
 */
public class SimpleCacheManager {

	static Logger log = LoggerFactory.getLogger(SimpleCacheManager.class);

	/** 默认缓存对象key */
	public static String SIMPLE_DEFAULT_CACHE_KEY = "default.cache";
	/** 缓存对象容器 */
	private Map<String, ICache> mapCache = new HashMap<String, ICache>();
	/**ehcache 的缓存管理器*/
	private static CacheManager manager;

	private static SimpleCacheManager instance;
	
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	private SimpleCacheManager() {
		super();
	}

	/**
	 * 
	 * @Title: getInstance
	 * @date 2017-03-22
	 * @Description: 单例模式
	 * @return
	 */
	public static SimpleCacheManager getInstance() {
		if (instance == null) {
			instance = new SimpleCacheManager();
		}
		return instance;
	}

	/**
	 * 
	 * @Title: getCache
	 * @date 2017-03-22
	 * @Description:获取默认缓存对象
	 * @return
	 */
	public ICache getCache() {
		return getCache(SIMPLE_DEFAULT_CACHE_KEY);
	}

	/**
	 * 
	 * @Title: getCache
	 * @date 2017-03-22
	 * @Description: 获取缓存对象
	 * @param cacheName
	 * @return
	 */
	public ICache getCache(String cacheName) {
		ICache cache = mapCache.get(cacheName);
		if (cache == null) {
			if (manager == null) {
				manager = CacheManager.create(SimpleCacheManager.class
						.getResource("/ehcache.xml"));
			}
			Cache ehCache = manager.getCache(cacheName);
			if (ehCache == null) {
				CacheConfiguration ehcacheConfiguration = manager.getCache(
						"defaultTemp").getCacheConfiguration();
				ehcacheConfiguration.setName(cacheName);
				ehCache = new Cache(ehcacheConfiguration);
				manager.addCache(ehCache);
			}
			cache = new EhCacheAdapter(ehCache);
			mapCache.put(cacheName, cache);
		}
		return cache;

	}

}
