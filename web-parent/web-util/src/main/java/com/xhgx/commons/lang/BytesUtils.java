package com.xhgx.commons.lang;

/**
 * @ClassName BytesUtils
 * @Description 
 * @author zohan(inlw@sina.com)
 * @date 2017-04-27 10:59
 * @vresion 1.0
 */
public class BytesUtils {

    /**
     * @param bytes
     * @return
     */
    public static int toInt( byte[] bytes ) {
        int result = 0;
        for (int i=0; i<4; i++) {
            result = ( result << 8 ) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }
}
