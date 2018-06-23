package com.xhgx.web.base.directive;


import com.xhgx.sdk.container.SpringContextUtil;
import com.xhgx.sdk.container.WebContextUtil;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.MapModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BaseDirective
 * @Description 标签封装
 * @author zohan(inlw@sina.com)
 * @date 2017-05-12 15:41
 * @vresion 1.0
 */
public abstract class BaseDirective implements TemplateDirectiveModel {

    protected List<Object> entityList = Collections.emptyList();


    /**扩展参数*/
    final protected Map<String, Object> map = new HashMap<String, Object>();

    protected Map<?, ?> params;
    protected Environment env;

    /**
     * (non-Javadoc)
     *
     * @see freemarker.template.TemplateDirectiveModel#execute(freemarker.core.
     * Environment, java.util.Map, freemarker.template.TemplateModel[],
     * freemarker.template.TemplateDirectiveBody)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        this.params = params;
        this.env = env;
        if (body != null) {
            // 设置循环变量
            if (loopVars != null && loopVars.length > 0) {
                initEntity();
                initEnv();
                // 查询栏目
                int i = 0;
                for (Object entity : entityList) {
                    BeanModel bm = new BeanModel(entity, new BeansWrapper());
                    loopVars[0] = bm;
                    if (loopVars.length > 1) {
                        i++;
                        map.put("index", i);
                        initEntityextend(entity, map);
                        loopVars[1] = new MapModel(map, new BeansWrapper());
                    }
                    body.render(env.getOut());
                }

            }
        }

    }


    /**
     * @Title: initEntity
     * @date 2017-05-12 15:41
     * @Description: 初始化实体对象
     */
    public abstract void initEntity();


    /**
     * @param entity 单个实体对象
     * @param map    扩展参数集合，主要往集合中添加扩展参数
     * @Title: initEntityextend
     * @date 2017-05-12 15:41
     * @Description: 初始化单个实体扩展信息
     */
    public abstract void initEntityextend(Object entity, Map<String, Object> map);


    /**
     * @param key
     * @param value
     * @Title: add
     * @date 2017-05-12 15:41
     * @Description:添加对象
     */
    public void add(String key, Object value) {
        map.put(key, value);
    }


    /**
     * @param key    参数名称
     * @return
     * @Title: getParam
     * @date 2017-5-12 15:43:38
     * @Description: 获取参数
     */
    public String getParam(String key) {
        String value = "";
        if (this.params.get(key) != null) {
            value = String.valueOf(params.get(key));
        }
        return value;
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     * @Title: getParam
     * @date 2017-5-12 15:43:38
     * @Description: 获取参数的值，没有的直接使用默认值
     */
    public String getParam(String key, String defaultValue) {
        String value = getParam(key);
        if (StringUtils.isEmpty(value)) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * @Title: initEnv
     * @date 2017-05-12 15:41
     * @Description: 初始化环境变量
     */
    private void initEnv() {
        /*if(null != env){
			for(String key:map.keySet()){
				try {
					env.__setitem__(key, map.get(key));
				} catch (TemplateException e) {
					e.printStackTrace();
				}
			}
		}*/
        if (entityList != null) {
            map.put("size", entityList.size());
        }

        //系统上下文路径 context
        map.put("context", WebContextUtil.getContextPath());
    }


    /**
     * @param beanName
     * @return
     * @Title: getBean
     * @date 2017-05-12 15:41
     * @Description: 获取bean对象
     */
    public Object getBean(String beanName) {
        return SpringContextUtil.getBean(beanName);
    }

}
