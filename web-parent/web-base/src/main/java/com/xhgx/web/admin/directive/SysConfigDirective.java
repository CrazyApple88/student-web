package com.xhgx.web.admin.directive;

import java.util.ArrayList;
import java.util.Map;

import com.xhgx.web.admin.config.service.ConfigHelper;
import com.xhgx.web.base.directive.BaseDirective;

/**
 * @ClassName SysConfigDirective
 * @Description 
 * <@sysConfig key="${key}" ; value,map></br> ${value} </br> </@sysConfig></br>
 * @author zohan(inlw@sina.com)
 * @date 2017-05-12 15:51
 * @vresion 1.0
 */
public class SysConfigDirective extends BaseDirective {

	@Override
	public void initEntity() {
		String key = this.getParam("key");
		String value = ConfigHelper.getInstance().get(key);
		this.entityList = new ArrayList<Object>();
		// 这里可以存储java对象
		this.entityList.add(value);
	}

	@Override
	public void initEntityextend(Object entity, Map<String, Object> map) {

	}

}
