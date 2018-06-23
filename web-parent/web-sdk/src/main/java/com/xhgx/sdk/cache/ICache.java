package com.xhgx.sdk.cache;

import java.util.Map;

/**
 * @ClassName ICache
 * @Description 缓存管理接口
 * @author zohan(inlw@sina.com)
 * @date 2017-03-22 17:40
 * @vresion 1.0
 */
public interface ICache {

    /**
     * @param key
     * @param value
     * @Title: put
     * @Description: 保存对象到缓存
     */
    void put(Object key, Object value);

    /**
     * 在内存中的存活时间
     *
     * @param key
     * @param value
     * @param liveTime
     */
    void put(Object key, Object value, int liveTime);

    /**
     * @param key
     * @return
     * @Title: remove
     * @Description: 删除缓存对象
     */
    boolean remove(Object key);

    /***
     * 通过表达式删除
     *
     * @param regex
     */
    boolean removeRegex(String regex);

    /**
     * @param key
     * @return
     * @Title: get
     * @Description: 获取缓存对象
     */
    Object get(Object key);

    /**
     * getByRegex<br/>
     * 通过表达式获取对应的缓存集合<br/>
     *
     * @param regex
     * @return
     * @author zohan
     */
    Map<String, Object> getByRegex(String regex);
}
