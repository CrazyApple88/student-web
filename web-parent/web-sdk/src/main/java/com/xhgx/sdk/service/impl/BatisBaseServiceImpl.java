package com.xhgx.sdk.service.impl;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.entity.Page;
import com.xhgx.sdk.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BatisBaseServiceImpl
 * @Description mybatis实现的基础业务逻辑层
 * @author zohan(inlw sina.com)
 * @date 2017 -03-23 14:45
 * @vresion 1.0
 */
@Service("batisBaseServiceImpl")
public abstract  class BatisBaseServiceImpl  implements BaseService {

    /**
     * Gets generic dao.
     *
     * @return the generic dao
     */
    protected  abstract GenericDao getGenericDao();

    /**
     * 模板jdbc，方便簡單的sql執行
     */
    @Autowired
    protected JdbcTemplate jdbcTemplate;


    /**
     * Save object.
     *
     * @param object the object
     * @return the object
     */
    public Object save(Object object) {
        getGenericDao().save(object);
        return object;
    }

    /**
     * Update object.
     *
     * @param object the object
     * @return the object
     */
    public Object update(Object object) {
        getGenericDao().update(object);
        return object;
    }

    /**
     * Remove.
     *
     * @param id the id
     */
    public void remove(Serializable id) {
        getGenericDao().delete(id);
    }

    /**
     * Find page.
     *
     * @param param   the param
     * @param page    the page
     * @param orderBy the order by
     */
    public void findPage(Map param, Page page, String orderBy) {
        int count  = this.getGenericDao().queryCount(param);

        page.setPageCount(count);
        int offset  = page.getPageSize() * (page.getPageNo()-1);
        param.put("offset",offset);
        param.put("limit",page.getPageSize());
        param.put("orderBy",orderBy);
        List list  =  this.getGenericDao().queryList(param);
        page.setResult(list);
        int total = count % page.getPageSize() == 0 ? (count / page.getPageSize()) : (count / page.getPageSize() + 1);
        page.setPageTotal(total);
    }

    /**
     * Find list list.
     *
     * @param param   the param
     * @param orderBy the order by
     * @return the list
     */
    public List findList(Map param, String orderBy) {
        param.put("orderBy",orderBy);
        return this.getGenericDao().queryList(param);
    }

    /**
     * Find for map list.
     *
     * @param param   the param
     * @param orderBy the order by
     * @return the list
     */
    public List<HashMap> findForMap(Map param, String orderBy) {
        return null;
    }

    /**
     * Get object.
     *
     * @param model the model
     * @return the object
     */
    public Object get(Object model) {
        return this.getGenericDao().get(model);
    }



    /**
     * Delete.jd
     *
     * @param model the model
     */
    public void delete(Object model) {
        this.getGenericDao().delete(model);
    }

    /**
     * Execute boolean.
     *
     * @param sql   the sql
     * @return the boolean
     */
    @Override
    public void execute(String sql) {
        jdbcTemplate.execute(sql);
    }

    @Override
    public int deleteBatch(Serializable[] ids) {
       return  this.getGenericDao().deleteBatch(ids);
    }
}
