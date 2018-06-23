package com.xhgx.sdk.id;

import com.xhgx.commons.lang.BytesUtils;

import java.net.InetAddress;

/**
 * @ClassName: UIDGenerator
 * @Description: UUID主要用于同时使用多个数据库, 不能依赖单个数据库自增生成主键的情形.
 * @author zohan(inlw@sina.com)
 * @date 2017-04-27 10:43
 * @vresion 1.0
 */
public class UIDGenerator {


    private static short counter = (short) 0;
    private static UIDGenerator generator = new UIDGenerator();

    private static final int IP;

    static {
        int ipadd;
        try {
            ipadd = BytesUtils.toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }

    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

    private UIDGenerator() {
    }

    /**
     * @param shortval
     * @return
     */
    protected String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    /**
     * @param intval
     * @return
     */
    protected String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    /**
     * app id ,可以通过继承此来，来实现不同应用的ｉｄ
     *
     * @return
     */
    protected short getAppId() {
        return 0;
    }

    /**
     * 单利
     *
     * @return
     */
    public static UIDGenerator getInstance() {
        return generator;
    }

    public String createUID() {
        return new StringBuffer(36)
                .append(format(getIP()))
                .append(format(getJVM()))
                .append(format(getHiTime()))
                .append(format(getLoTime()))
                .append(format(getCount()))
                .toString();
    }

    protected int getIP() {
        return IP;
    }


	protected short getCount() {
		synchronized (UIDGenerator.class) {
			if (counter < 0) {
				counter = 0;
			}
			return counter++;
		}
	}

    protected int getJVM() {
        return JVM;
    }

    protected short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    protected int getLoTime() {
        return (int) System.currentTimeMillis();
    }


}
