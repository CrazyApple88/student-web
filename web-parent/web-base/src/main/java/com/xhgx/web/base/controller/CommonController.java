package com.xhgx.web.base.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

/**
 * @ClassName CommonController
 * @Description 公共跳转页面
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@RequestMapping("/common")
@Controller("commonController")
@Scope("prototype")
public class CommonController extends AbstractBaseController {

    static Logger log = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    private CookieLocaleResolver cookieLocaleResolver;

    /**
     * 进入浏览器不兼容提示页面
     *
     * @return
     */
    @RequestMapping("/getBrowserEdition")
    public String getBrowserEdition() {
        return "system/error-browser";
    }


    /**
     * 切换语言
     * @param locale
     */
    @RequestMapping("/lang/{locale}")
    public void lang(@PathVariable  Locale locale) {
        if(locale!=null){
            cookieLocaleResolver.setLocale(request, response, locale);
        }
        log.info("Current lang is :{}", locale);
        successJson("success");

    }
    
    @RequestMapping("/404")
    public String error404() {
        return "system/error-404";
    }
    @RequestMapping("/403")
    public String error403() {
    	return "system/error-403";
    }
    
    @RequestMapping("/500")
    public String error500() {
        return "system/error-500";
    }
}
