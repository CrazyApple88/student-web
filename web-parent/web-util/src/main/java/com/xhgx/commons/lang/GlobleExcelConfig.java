package com.xhgx.commons.lang;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @ClassName GlobleExcelConfig
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class GlobleExcelConfig {
	static Object obj = new Object();
	static final String FILE = "excel-config";
	static ResourceBundle rb = null;

	private static Properties sysData = new Properties();

	static {
		try {
			InputStream inputStream;
			ClassLoader cl = GlobleExcelConfig.class.getClassLoader();
			if  (cl !=  null ) {        
		        inputStream = cl.getResourceAsStream( "excel-config.properties" );        
				sysData.load(new InputStreamReader(inputStream, "UTF-8"));
			}  else {        
		        inputStream = ClassLoader.getSystemResourceAsStream( "excel-config.properties" );      
				sysData.load(new InputStreamReader(inputStream, "UTF-8"));  
			}   
			if (rb == null){
				rb = ResourceBundle.getBundle("excel-config");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		String result = null;
		try {
			
			
			if (sysData.get(key) == null)
				try {
					result = rb.getString(key);
					sysData.put(key, result);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			else{
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
		return getProperty((String) key);
	}

	public static void remove(Object key) {
		sysData.remove(key);
	}
}