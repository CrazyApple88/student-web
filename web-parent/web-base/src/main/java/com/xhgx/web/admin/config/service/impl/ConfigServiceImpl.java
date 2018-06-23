package com.xhgx.web.admin.config.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.sdk.cache.SimpleCacheHelper;
import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.config.dao.ConfigDao;
import com.xhgx.web.admin.config.entity.ConfigEntity;
import com.xhgx.web.admin.config.service.ConfigService;

/**
 * @ClassName ConfigServiceImpl
 * @Description 配置表serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("configService")
public class ConfigServiceImpl extends BatisBaseServiceImpl implements
		ConfigService {
	static Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);
	private final String CONFIG_MAP = "config.map";

	@Override
	public GenericDao<ConfigEntity, String> getGenericDao() {
		return configDao;
	}

	@Autowired
	public ConfigDao configDao;

	@Override
	public void initCache() {
		log.info("开始初始化配置信息数据");
		List<ConfigEntity> list = configDao.queryList(null);
		for (ConfigEntity entity : list) {
			putCache(entity);
		}
		log.info("始化配置信息数据完毕，共缓存" + list.size() + "条数据");
	}

	@Override
	public void reloadCache() {
		initCache();
	}

	@Override
	public String getValue(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		// 查询config表的缓存信息
		Map<String, ConfigEntity> map = (Map<String, ConfigEntity>) SimpleCacheHelper.get(CONFIG_MAP);
		ConfigEntity value = null;
		// 如果缓存存在，继续执行
		if (map != null) {
			value = map.get(key);
			// 如果缓存不存在，则应该是没有加载，或服务器被清除，需要查询数据库刷新缓存
		} else {
			Map<String, Object> hashMap = new HashMap<String, Object>();
			// 查询所有的配置信息
			List<ConfigEntity> list = configDao.queryList(hashMap);
			// 如果表里没有数据，则放入一个new出来的对象，防止每次都查询数据库
			if(list.size()<=0){
				map = new ConcurrentHashMap<String, ConfigEntity>();
				return null;
				// 查询出来有数据，则更新到缓存中
			}else{
				// 临时map存放当缓存为空时，返回查出来放入缓存时能够读取到的对象
				Map<String, ConfigEntity> mapLs = new ConcurrentHashMap<String, ConfigEntity>();
				for (ConfigEntity entity : list) {
					// 放入缓存
					putCache(entity);
					// 放入临时map
					mapLs.put(entity.getCode(), entity);
				}
				// 从临时map中取值
				value = mapLs.get(key);
				// 清空临时map
				mapLs.clear();
				return value == null? null : value.getValue();
			}
		}
		return value == null ? null : value.getValue();
	}

	/**
	 * 把对象放入缓存
	 *
	 * @param entity
	 */
	private void putCache(ConfigEntity entity) {
		Map<String, ConfigEntity> map = (Map<String, ConfigEntity>) SimpleCacheHelper
				.get(CONFIG_MAP);
		if (map == null) {
			map = new ConcurrentHashMap<String, ConfigEntity>();
			SimpleCacheHelper.put(CONFIG_MAP, map);
		}
		map.put(entity.getCode(), entity);
	}
	
	/**
	 * 删除缓存中某一个对象
	 *
	 * @param entity
	 */
	private void delCache(ConfigEntity entity) {
		Map<String, ConfigEntity> map = (Map<String, ConfigEntity>) SimpleCacheHelper
				.get(CONFIG_MAP);
		if (map == null) {
			map = new ConcurrentHashMap<String, ConfigEntity>();
			SimpleCacheHelper.put(CONFIG_MAP, map);
		}
		map.remove(entity.getCode());
	}
	@Override
	public Object save(Object object) {
		ConfigEntity entity = (ConfigEntity) super.save(object);
		putCache(entity);
		return entity;
	}

	@Override
	public Object update(Object object) {
		ConfigEntity entity = (ConfigEntity) super.update(object);
		putCache(entity);
		return entity;
	}
	
	@Override
	public void delete(ConfigEntity entity) {
		configDao.delete(entity);
		delCache(entity);
	}
	
	
}
