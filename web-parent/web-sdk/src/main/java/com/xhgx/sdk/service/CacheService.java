package com.xhgx.sdk.service;

/**
 * @ClassName CacheService
 * @Description The interface Cache service.
 * @author zohan(inlw sina.com)
 * @date 2017 -03-23 8:42
 * @vresion 1.0
 */
public interface CacheService {

    /**
     * 初始化缓存
     * Init cache.
     */
    void initCache();

    /**
     * 重新加载缓存
     * Reload cache.
     */
    void reloadCache();
}
