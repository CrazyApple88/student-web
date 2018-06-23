package com.xhgx.sdk.entity;

import java.io.Serializable;

/**
 * @ClassName BaseEntity
 * @Description 基础的业务实体类
 * @author zohan(inlw@sina.com)
 * @date 2017-03-20 18:25
 * @vresion 1.0
 * @param <PK>
 */
public abstract class BaseEntity<PK extends Serializable> implements Serializable, Cloneable {

    /**实体的主键 */
    public abstract PK getId();
    /**设置实体的主键值*/
    public abstract void setId(PK id);
}
