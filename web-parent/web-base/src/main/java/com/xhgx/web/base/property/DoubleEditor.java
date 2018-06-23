/**
 * Project Name:shunhecore
 * File Name:DoubleEditor.java
 * Package Name:cc.relink.www.editor
 * Date:2015年5月25日下午9:48:11
 *
 */

package com.xhgx.web.base.property;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/**
 * @ClassName DoubleEditor
 * @Description Double类型.转换
 * @author zohan(inlw@sina.com)
 * @date 2017-03-29 14:57
 * @vresion 1.0
 */
public class DoubleEditor extends PropertiesEditor {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null || text.equals("")) {
			text = "0";
		}
		setValue(Double.parseDouble(text));
	}

	@Override
	public String getAsText() {
		return getValue().toString();
	}
}
