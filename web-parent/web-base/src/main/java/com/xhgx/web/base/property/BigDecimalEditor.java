/**
 * Project Name:shunhecore
 * File Name:BigDecimalEditor.java
 * Package Name:cc.relink.www.editor
 * Date:2015年5月25日下午9:51:02
 */

package com.xhgx.web.base.property;

import java.math.BigDecimal;

import org.springframework.beans.propertyeditors.PropertiesEditor;


/**
 * @ClassName BigDecimalEditor
 * @Description BigDecimal类型
 * @author zohan(inlw@sina.com)
 * @date 2017-03-29 14:57
 * @vresion 1.0
 */
public class BigDecimalEditor extends PropertiesEditor {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.equals("")) {
            text = "0";
        }
        setValue(new BigDecimal(text));
    }

    @Override
    public String getAsText() {
        return getValue().toString();
    }
}

