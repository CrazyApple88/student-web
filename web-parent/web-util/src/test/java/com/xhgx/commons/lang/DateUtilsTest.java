package com.xhgx.commons.lang;

import org.junit.Test;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author zohan(inlw@sina.com)
 * @date 2017-03-31 9:05
 * @vresion v1.0
 */
public class DateUtilsTest {

    Date today = new Date();

    @Test
    public void testFormate() {

        System.out.println(DateUtils.defaultFormateDate(today));
        System.out.println(DateUtils.formateDate(today,null));
        System.out.println(DateUtils.formateTime(today,null));
        System.out.println(DateUtils.formateDateTime(today,null));
    }


}
