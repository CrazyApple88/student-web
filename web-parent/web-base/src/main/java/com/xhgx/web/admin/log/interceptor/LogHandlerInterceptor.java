package com.xhgx.web.admin.log.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xhgx.commons.lang.NetWorkUtils;
import com.xhgx.web.admin.log.entity.LogEntity;
import com.xhgx.web.admin.log.service.LogService;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName 
 * @Description 用户的操作日志
 * @author zohan(inlw@sina.com)
 * @date 2017-05-12 9:14
 * @vresion 1.0
 */
public class LogHandlerInterceptor implements HandlerInterceptor {

	@Autowired
	@Qualifier("logService")
	private LogService logService;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 用户操作后记录日志

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 用户操作前记录操作信息
		// ConfigHelper.getInstance().get();
		String url = request.getRequestURI();
		if (!url.startsWith("/admin")) {
			// TODO 目前只记录admin开头的信息，后期修改成可配置的
			return true;
		}
		String ip = NetWorkUtils.getRemortIP(request);
		String method = request.getMethod();
		OnlineUser user = (OnlineUser) request.getSession().getAttribute(
				OnlineUser.ONLINE_USER_TAG);
		String userName = user != null ? user.getLoginName() : "";
		LogEntity logEntity = new LogEntity();
		logEntity.setCreateBy(userName);
		logEntity.setCreateDate(new Date());
		logEntity.setMethod(method);
		logEntity.setRequestUrl(url);
		logEntity.setRemoteAddr(ip);

		JSONObject object = new JSONObject();
		// 这里采用一部分获取数据，
		for (Object k : request.getParameterMap().keySet()) {
			String key = String.valueOf(k);
			String value = request.getParameter(key);
			object.put(key, value);
		}
		logEntity.setParams(JSONObject.fromObject(object).toString());
		logService.save(logEntity);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}
}
