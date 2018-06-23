package com.xhgx.sdk.token;

import com.xhgx.sdk.cache.SimpleCacheHelper;
import com.xhgx.sdk.id.UIDGenerator;

/**
 * @ClassName TokenUtils
 * @Description 
 * @author ZhangJin
 * @date 2017年6月26日
 * @vresion 1.0
 */
public class TokenUtils {

	/**
	 * 生成唯一token
	 * @param userId
	 * @return String
	 * @exception
	 */
	public static String generateToken(String userId) {
		return UIDGenerator.getInstance().createUID();
	}

	/**
	 * @param token
	 * @return
	 * @exception
	 */
	public static void saveToken(String token, Object object, Integer liveTime) {
		SimpleCacheHelper.put(token, object, liveTime);
	}

	/**
	 * 删除token
	 * @param token
	 * @return boolean
	 * @exception
	 */
	public static boolean removeToken(String token) {
		return SimpleCacheHelper.remove(token);
	}
}