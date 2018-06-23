package com.xhgx.web.base.property;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

import java.beans.PropertyEditorSupport;

/**
 * @ClassName StringEscapeEditor
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class StringEscapeEditor extends PropertyEditorSupport {
	private boolean escapeHTML;
	private boolean escapeJavaScript;
	private boolean escapeSQL;
	private boolean xss;

	public StringEscapeEditor() {
		super();
	}

	public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript,
							  boolean escapeSQL, boolean xss) {
		super();
		this.escapeHTML = escapeHTML;
		this.escapeJavaScript = escapeJavaScript;
		this.escapeSQL = escapeSQL;
		this.xss = xss;
	}

	public void setAsText(String text) {
		if (text == null) {
			setValue(null);
		} else {
			String value = text;
			if (escapeHTML) {
				value = StringEscapeUtils.escapeHtml(value);
			}
			if (escapeJavaScript) {
				value = StringEscapeUtils.escapeJavaScript(value);
			}
			if (escapeSQL) {
				value = StringEscapeUtils.escapeSql(value);
			}
			if (xss) {
				value = HtmlUtils.htmlEscape(value);
				// value = new XssHTMLFilter().filter(value);
			}
			setValue(value);
		}
	}

	public String getAsText() {
		Object value = getValue();
		return (value != null ? value.toString() : "");
	}
}
