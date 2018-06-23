package com.xhgx.commons.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName MD5Utils
 * @Description md5算法 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 15:42
 * @vresion 1.0
 */
public class MD5Utils {

    /**
     * 默认的密码字符串组合，apache校验下载的文件的正确性用的就是默认的这个组合
     */
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    protected static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(MD5Utils.class.getName()
                    + "初始化失败，MessageDigest不支持MD5Utils。");
            nsaex.printStackTrace();
        }
    }

    /**
     * 对文件进行MD5加密，适用于文件的完整性验证。 适用于上G大的文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
                file.length());
        messagedigest.update(byteBuffer);
        String md5 = bufferToHex(messagedigest.digest());
        in.close();
        return md5;
    }

    /**
     * <b>方法名</b>：getMD5String<br>
     * <b>功能</b>：给字符串MD5加密<br>
     *
     * @param s
     * @return
     * @author <font color='blue'>zohan</font>
     */
    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    /**
     * <b>方法名</b>：getMD5String<br>
     * <b>功能</b>：给byte数组加密<br>
     *
     * @param bytes
     * @return
     * @author <font color='blue'>zohan</font>
     */
    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    /**
     * <b>方法名</b>：checkPassword<br>
     * <b>功能</b>：MD5密码验证，对比<br>
     *
     * @param password  密码明文
     * @param md5PwdStr md密码
     * @return
     * @author <font color='blue'>zohan</font>
     */
    public static boolean checkPassword(String password, String md5PwdStr) {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }
}
