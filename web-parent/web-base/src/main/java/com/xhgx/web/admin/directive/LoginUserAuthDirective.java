package com.xhgx.web.admin.directive;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xhgx.web.admin.config.service.ConfigHelper;
import com.xhgx.web.admin.menu.entity.MenuEntity;
import com.xhgx.web.base.directive.BaseDirective;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName LoginUserAuthDirective
 * @Description 
 * <@sysConfig key="${key}" ; value,map></br> ${value} </br> </@sysConfig></br>
 * @author zohan(inlw@sina.com)
 * @date 2017-05-12 15:51
 * @vresion 1.0
 */
public class LoginUserAuthDirective extends BaseDirective {

	@Override
	public void initEntity() {
		String value = "0";
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		OnlineUser user = (OnlineUser) servletRequestAttributes.getRequest()
				.getSession().getAttribute(OnlineUser.ONLINE_USER_TAG);
		// 超级个管理员账号
		String superAdmin = ConfigHelper.getInstance().get("super.administrator");
		if (superAdmin.equals(user.getLoginName())) {
			value = "1";
		} else {
			String key = this.getParam("key");
			Map<String, MenuEntity> menuMap = (Map<String, MenuEntity>) user
					.getExt("listMenuMap");
			MenuEntity menu = menuMap.get(key);
			if (menu != null){
				value = "1";
			}
		}

		this.entityList = new ArrayList<Object>();
		// 这里可以存储java对象
		this.entityList.add(value);
	}

	@Override
	public void initEntityextend(Object entity, Map<String, Object> map) {

	}

}
