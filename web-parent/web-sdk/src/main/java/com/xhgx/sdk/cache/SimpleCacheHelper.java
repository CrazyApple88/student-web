package com.xhgx.sdk.cache;

import java.util.Map;

/**
 * @ClassName SimpleCacheHelper
 * @Description 简单的缓存使用
 * @author zohan(inlw@sina.com)
 * @date 2017-03-22 17:38
 * @vresion 1.0
 */
public class SimpleCacheHelper {

    /*private  static SimpleCacheHelper helper;

    private SimpleCacheHelper(){

    }*/

    /**
     *
     * @Title: put
     * @date 2017-3-22
     * @Description: 放置对象
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        getCache().put(key, value);
    }

    /**
     * @Title put
     * @Description: 把对象放置到内存
     * @param key
     * @param value 对象
     * @param liveTime 对象在内存的存活时间（秒）
     */
    public static void put(String key, Object value,Integer liveTime) {
        if(liveTime ==null){
            liveTime = Integer.MAX_VALUE;
        }
        getCache().put(key, value,liveTime);
    }

    /**
     *
     * @Title: get
     * @date 2017-3-22
     * @Description: 获取对象
     * @param key
     * @return
     */
    public static Object get(String key) {
        return getCache().get(key);
    }

    /**
     *
     * @Title: remove
     * @date 2017-3-22
     * @Description: 删除缓存对象
     * @param key
     * @return
     */
    public static boolean remove(String key){
        return getCache().remove(key);
    }

    /**
     * 删除已prefix开头的key
     * @param prefix
     * @return
     */
    public static boolean removeStartWith(String prefix){
        return getCache().removeRegex("^"+prefix+".*");
    }

    /**
     *
     * getStartWith<br/>
     * 获取以xx开头的缓存对象<br/>
     * @author zohan
     * @param prefix
     * @return
     */
    public static Map<String,Object> getStartWith(String prefix){
        return getCache().getByRegex("^"+prefix+".*");
    }

    /**
     *
     * @Title: getCache
     * @date 2017-03-22
     * @Description: 获取相关的缓存实现
     * @return
     */
    private static ICache getCache() {
        return SimpleCacheManager.getInstance().getCache();
    }
}
