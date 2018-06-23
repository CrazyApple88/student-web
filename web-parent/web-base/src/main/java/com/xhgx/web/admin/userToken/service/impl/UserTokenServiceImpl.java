package com.xhgx.web.admin.userToken.service.impl;

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
import com.xhgx.web.admin.userToken.dao.UserTokenDao;
import com.xhgx.web.admin.userToken.entity.UserToken;
import com.xhgx.web.admin.userToken.service.UserTokenService;

/**
 * @ClassName UserTokenServiceImpl
 * @Description tb_sys_user_token 业务逻辑层实现
 * @author <font color='blue'>libo</font>
 * @date 2017-06-26 18:06:06
 * @version 1.0
 */
@Transactional
@Component("userTokenService")
public class UserTokenServiceImpl extends BatisBaseServiceImpl implements
		UserTokenService {

	static Logger log = LoggerFactory.getLogger(UserTokenServiceImpl.class);

	private final static String CACHE_LIST = "userToken.cache.list";
	private final static String CACHE_MAP = "userToken.cache.map";

	@Override
	public GenericDao<UserToken, String> getGenericDao() {
		return userTokenDao;
	}

	@Autowired
	@Qualifier("userTokenDao")
	private UserTokenDao userTokenDao;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.zohan.sdk.framework.service.EntityService#init()
	 */
	@Override
	public void initCache() {
		log.info("开始初始化tb_sys_user_token数据");
		Map<String, Object> param = new HashMap();
		List<UserToken> list = this.findList(param, null);
		SimpleCacheHelper.put(CACHE_LIST, list);
		Map<String, UserToken> map = new ConcurrentHashMap<String, UserToken>();
		for (UserToken entity : list) {
			map.put(entity.getId(), entity);
		}
		SimpleCacheHelper.put(CACHE_MAP, map);
		log.info("始化tb_sys_user_token数据完毕，共缓存" + list.size() + "条数据");
	}

	@Override
	public void reloadCache() {
		// TODO 这里是重新加载缓存的方法
		// initCache();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see IUserTokenService#getUserToken(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserToken getUserToken(String userTokenId) {
		Map<String, UserToken> map = (Map<String, UserToken>) SimpleCacheHelper
				.get(CACHE_MAP);
		UserToken userToken = null;
		if (map != null) {
			userToken = map.get(userTokenId);
		} else {
			map = new ConcurrentHashMap<String, UserToken>();
		}
		if (userToken == null) {
			userToken = (UserToken) userTokenDao.get(userTokenId);
			map.put(userToken.getId(), userToken);
			SimpleCacheHelper.put(CACHE_MAP, map);
		}
		return userToken;
	}

	@Override
	public String getUserId(String token) {
		return userTokenDao.getUserId(token);
	}

}