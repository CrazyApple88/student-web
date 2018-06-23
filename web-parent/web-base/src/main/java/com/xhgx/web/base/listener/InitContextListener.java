package com.xhgx.web.base.listener;

import com.xhgx.sdk.container.SpringContextUtil;
import com.xhgx.sdk.container.WebContextUtil;
import com.xhgx.sdk.service.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName InitContextListener
 * @Description 系统初始 
 * @author zohan(inlw@sina.com)
 * @date 2017-05-04 9:54
 * @vresion 1.0
 */
public class InitContextListener implements ServletContextListener {

    static Logger log = LoggerFactory.getLogger(InitContextListener.class);


    /**国际化默认路径*/
    protected String defaultI18nPath = "classpath*:com/xhgx/i18n/**/messages*.properties";

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        initServletContext(contextEvent);
    }


    /**
     * @param contextEvent
     * @Title: initServletContext
     * @date 2017-5-4 09:55:50
     * @Description: 初始化ServletContext 到StrutsContextUtil 工具类中
     */
    protected void initServletContext(ServletContextEvent contextEvent) {
        WebContextUtil.setServletContext(contextEvent.getServletContext());
        initEntiyToCache(contextEvent);
        initI18n(contextEvent);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }


    /**
     * 初始化内存信息
     *
     * @param contextEvent
     */
    protected void initEntiyToCache(ServletContextEvent contextEvent) {
        String par = contextEvent.getServletContext().getInitParameter("initEntiyToCache");
        if (par == null) {
            log.warn("未找到initEntiyToCache对应的参数配置!");
            return;
        }
        String[] services = par.split(",");
        for (String name : services) {
            try {
                Object o = getBean(name);
                if (o instanceof CacheService) {
                    CacheService ecs = (CacheService) o;
                    try {
                        ecs.initCache();//初始化到内存
                    } catch (Exception e) {
                        log.error("执行[" + name + "]的initCache方法失败！");
                        e.printStackTrace();
                    }

                } else {
                    log.warn("对象[" + name + "]不是CacheService的实现。");
                }
            } catch (Exception e) {
                log.error("对象[" + name + "]未在Spring容器中找到，请查看配置是否正确。");
            }

        }

    }


    /**
     * 出示多语言环境
     *
     * @param contextEvent
     */
    protected void initI18n(ServletContextEvent contextEvent) {

        String i18nPath = contextEvent.getServletContext().getInitParameter("i18nPath");
        if (StringUtils.isNotBlank(i18nPath)) {
            this.defaultI18nPath = i18nPath;
        }
        try {
            PathMatchingResourcePatternResolver patternResolver = (PathMatchingResourcePatternResolver) getBean("patternResolver");
            Set<String> set = new HashSet<>();
            Resource[] resources = patternResolver.getResources(defaultI18nPath);
            for (Resource resource : resources) {
                String path  = resource.getFile().getParent();
                path = "file:" + path + File.separator + "messages";
                set.add(path);
            }
            AbstractResourceBasedMessageSource messageSource = (AbstractResourceBasedMessageSource) getBean("messageSource");
            messageSource.setBasenames(set.toArray(new String[set.size()]));
            log.info("Set the i18nPath:{}", set);
        } catch (Exception e) {
            log.error("Set the i18nPath Error: " + e.getMessage());
        }
    }

    /**
     * 通过id获取bean
     *
     * @param name
     * @return
     */
    protected Object getBean(String name) {
        return SpringContextUtil.getBean(name);
    }
}
