package com.xhgx.web.base.controller;

import java.io.InputStream;
import java.util.Map;

/**
 * @ClassName ResultRender
 * @Description 返回生成器
 * @author zohan(inlw@sina.com)
 * @date 2017-03-29 14:14
 * @vresion 1.0
 */
public interface ResultRender {

    /**
     * @Description: 直接输出内容的简便函数
     * @param contentType   输出的文本格式 如："text/html"，“text/plain”，"text/xml"
     * @param content 对外输出的内容
     * @param headers 参数设置
     */
    void render(final String contentType, final String content,
                final String... headers);


    /**
     * @Description: text 文本输出
     * @param text 输出内容
     * @param headers
     */
    void renderText(final String text, final String... headers);

    /**
     * @Description:  html文本输出
     * @param html
     * @param headers
     */
    void renderHtml(final String html, final String... headers);

    /**
     * @Description:  xml文本输出
     * @param xml
     * @param headers
     */
    void renderXml(final String xml, final String... headers);

    /**
     * @Description:  json文本输出
     * @param string
     * @param headers
     */
    void renderJson(final String string, final String... headers);

    /**
     * @Description:  json map 输出
     * @param map
     * @param headers
     */
    void renderJson(final Map<?, ?> map, final String... headers);

    /**
     * @Description:  直接输出JSON.
     * @param object
     * @param headers
     */
    void renderJson(final Object object, final String... headers);

    /**
     * @Description:  流输出
     * @param contentType
     * @param inputStream
     * @param headers
     */
    void renderStream(final String contentType,
                      final InputStream inputStream, final String... headers);
}
