/**
 * Project Name:shunhecore
 * File Name:LongEditor.java
 * Package Name:cc.relink.www.editor
 * Date:2015年5月25日下午10:29:57
 *
*/

package com.xhgx.web.base.property;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/**
 * @ClassName LongEditor
 * @Description long类型.转换
 * @author zohan(inlw@sina.com)
 * @date 2017-03-29 14:57
 * @vresion 1.0
 */
public class LongEditor extends PropertiesEditor {
	 @Override    
	    public void setAsText(String text) throws IllegalArgumentException {    
	        if (text == null || text.equals("")) {    
	            text = "0";    
	        }    
	        setValue(Long.parseLong(text));    
	    }    
	    
	    @Override    
	    public String getAsText() {    
	        return getValue().toString();    
	    }    
}

