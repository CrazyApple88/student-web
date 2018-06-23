package com.xhgx.commons.lang;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @ClassName GlobleConfig
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class GlobleConfig
{
  static Object obj = new Object();
  static final String FILE = "config";
  static ResourceBundle rb = null;

  private static Properties sysData = new Properties();

  static
  {
    try
    {
      String s = System.getProperty("config.path");

      if ((s != null) && (!s.trim().equals("null")) && (!s.trim().equals(""))) {
        File file = new File(s);
        if (file.exists()) {
          InputStream in = new BufferedInputStream(new FileInputStream(s));
          rb = new PropertyResourceBundle(in);
        }
      }
      if (rb == null){
          rb = ResourceBundle.getBundle("config");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static String getProperty(String key)
  {
    String result = null;
    try {
      if (sysData.get(key) == null){
        try {
          result = rb.getString(key);
          sysData.put(key, result);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }else{
        result = sysData.getProperty(key);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return result;
  }

  public static void put(Object key, Object value) {
    sysData.put(key, value);
  }

  public static Object get(Object key) {
    return getProperty((String)key);
  }

  public static void remove(Object key) {
    sysData.remove(key);
  }
}