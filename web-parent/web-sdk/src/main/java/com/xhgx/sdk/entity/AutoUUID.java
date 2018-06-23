package com.xhgx.sdk.entity;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @ClassName AutoUUID
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-04-27 9:35
 * @vresion 1.0
 */
@Target({ TYPE})
@Retention(RUNTIME)
public @interface AutoUUID {
}
