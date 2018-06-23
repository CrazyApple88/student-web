package com.xhgx.web.admin.cache.controller;

import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xhgx.sdk.container.SpringContextUtil;
import com.xhgx.sdk.entity.Page;
import com.xhgx.sdk.service.CacheService;
import com.xhgx.web.admin.cache.service.ICacheService;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;

/**
 * @ClassName CacheController
 * @Description 内存信息Controller
 * @author ZhangJin
 * @date 2017年6月20日
 * @vresion 1.0
 */
@RequestMapping("/admin/cache")
@Controller("cacheController")
@Scope("prototype")
public class CacheController extends AbstractBaseController {

	@Autowired
	private ICacheService iCacheService;

	/**
	 * 进入内存信息查询页面
	 * 
	 * @param page
	 * @param bindingResult
	 * @return String
	 */
	@RequestMapping("/getCacheQueryPage")
	public String getCacheQueryPage(Page page, BindingResult bindingResult) {
		return "admin/cache/cache_query";
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            分页条件
	 * @param orderBy
	 *            排序条件
	 * @param bindingResult
	 */
	@RequestMapping("/findPage")
	public void findPage(Page page, String orderBy, BindingResult bindingResult) {
		ServletContextEvent contextEvent = new ServletContextEvent(request
				.getSession().getServletContext());
		Map<String, Object> param = RequestSearch.getSearch(request);
		iCacheService.findPage(param, page, orderBy, contextEvent);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(page);
	}

	/**
	 * 重新加载
	 * 
	 * @param id
	 */
	@RequestMapping("/reLoad")
	public void reLoad(String id) {
		try {
			Object o = SpringContextUtil.getBean(id);
			if (o instanceof CacheService) {
				CacheService ecs = (CacheService) o;
				try {
					ecs.initCache();// 初始化到内存
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
			}
		} catch (Exception e) {
		} finally {
			successJson(id);
		}
	}

	/**
	 * 批量重新加载
	 * 
	 * @param ids
	 */
	@RequestMapping("/reLoadBatch")
	public void reLoadBatch(String[] ids) {
		for (String name : ids) {
			try {
				Object o = SpringContextUtil.getBean(name);
				if (o instanceof CacheService) {
					CacheService ecs = (CacheService) o;
					try {
						ecs.initCache();// 初始化到内存
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
				}
			} catch (Exception e) {
			}
		}
		successJson(ids);
	}

}
