package com.xhgx.sdk.service;

import com.xhgx.sdk.entity.Page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BaseService
 * @Description 业务逻辑层基础的接口
 * @author zohan(inlw sina.com)
 * @date 2017 -03-21 14:39
 * @vresion 1.0
 * @param <T>  泛型
 * @param <PK> 主键
 */
public interface BaseService<T, PK extends Serializable> {

        /**
         * 对象持久化
         *
         * @param object the object
         * @return t t
         */
    T save(T object);

    /**
     * Update t.
     *
     * @param object the object
     * @return the t
     */
    T update(T object);

    /**
     * Remove.
     *
     * @param id the id
     */
    void remove(PK id);

    /**
     * Find.
     * 分页查询
     *
     * @param param   the param
     * @param page    the page
     * @param orderBy the order by
     */
    void findPage(Map<String,Object> param,final Page page,String orderBy);

    /**
     * Find list list.
     *
     * @param param   the param
     * @param orderBy the order by
     * @return the list
     */
    List<T> findList(Map<String,Object> param,String orderBy);

    /**
     * Find for map list.
     *
     * @param param   the param
     * @param orderBy the order by
     * @return the list
     */
    List<HashMap> findForMap(Map<String,Object> param,String orderBy);

    /**
     * Get t.
     *
     * @param model the model
     * @return the t
     */
    T get(T model);

    /**
     * Delete.
     *
     * @param model the model
     */
    void delete(T model);

    /**
     * 批量删除
     * @param ids
     */
    int deleteBatch(PK [] ids);

    /**
     * Execute boolean.
     *
     * @param sql   the sql
     * @return the boolean
     */
    void execute(String sql);
}
