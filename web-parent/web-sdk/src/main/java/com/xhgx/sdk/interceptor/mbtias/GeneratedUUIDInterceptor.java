package com.xhgx.sdk.interceptor.mbtias;

import java.util.Collection;
import java.util.Iterator;

import org.aspectj.lang.JoinPoint;

import com.xhgx.sdk.entity.AutoUUID;
import com.xhgx.sdk.entity.BaseEntity;
import com.xhgx.sdk.id.UIDGenerator;

/**
 * @ClassName GeneratedUUIDInterceptor
 * @Description 在指定的标示下面生成设置，UUID
 * @author zohan(inlw@sina.com)
 * @date 2017-04-26 9:26
 * @vresion 1.0
 */
public class GeneratedUUIDInterceptor {

    /**
     * 检测是否是BaseEntity子类，并且带有AutoUUID注解的新增方法，自动生成uuid
     *
     * @param joinPoint
     */
    public void checkUUID(JoinPoint joinPoint) {
        for (Object entity : joinPoint.getArgs()) {

            // 如果是list则进行迭代处理
            if (entity instanceof Collection) {
                Collection c = (Collection) entity;
                Iterator it = c.iterator();
                while (it.hasNext()) {
                    BaseEntity e = (BaseEntity) it.next();
                    setUUID(e);
                }
            } else {
                // 如果是普通的实体对象
                setUUID(entity);
            }
        }
    }

    /**
     * 设置uuid
     *
     * @param entity
     */
    private void setUUID(Object entity) {
        // 做认证信息
        if (entity instanceof BaseEntity && entity.getClass().getAnnotation(AutoUUID.class) != null) {
            BaseEntity e = (BaseEntity) entity;
            if(e.getId() == null || "".equals(e.getId()+"")){
            	e.setId(UIDGenerator.getInstance().createUID());
            }
        }
    }
}
