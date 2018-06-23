package com.xhgx.sdk.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName GenericDao
 * @Description 基础额持久层接口
 * @author zohan(inlw@sina.com)
 * @date 2017-03-21 13:17
 * @vresion 1.0
 */
public interface GenericDao<T, PK extends Serializable> {

    /**
     * 通过主键和获取对象
     */
    T get(PK id);

    /**
     * 通过对象 参数获取对象信息
     * @param model
     * @return
     */
    T get(T model);

    /**
     * 通过主键id 验证对象是否存在
     * @param id the id of the entity
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Generic method to save an object - handles both update and insert.
     * @param model the object to save
     * @return the persisted object
     */
    void save(T model);

    /**
     * map形式新增
     * @param map
     */
    void save(Map<String, Object> map);

    /**
     * 批量新增
     * @param list
     */
    void saveBatch(final List<T> list);

    /**
     * 持久化数据更新操作
     * @param model
     * @return
     */
    void update(T model);

    /**
     * 持久化map形式更新
     * @param map
     * @return
     */
    int update(Map<String, Object> map);

    /**
     * 通过对象参数删除对象信息
     * @param model
     */
    void delete(T model);

    /**
     * 通过主键删除对象
     * @param id
     * @return
     */
    int delete(PK id);

    /**
     * 通过map形式删除对象
     * @param map
     * @return
     */
    int delete(Map<String, Object> map);

    /**
     * 通过主键集合批量删除数据
     * @param ids
     * @return
     */
    int deleteBatch(PK [] ids);

    /**
     * 通过多个条件查询查询列表对象
     * @param map
     * @return
     */
    List<T> queryList(Map<String, Object> map);

    /**
     * 统计查询
     * @param map
     * @return
     */
    int queryCount(Map<String, Object> map);
}
