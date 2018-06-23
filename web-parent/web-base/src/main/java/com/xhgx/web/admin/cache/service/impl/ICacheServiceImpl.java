package com.xhgx.web.admin.cache.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.springframework.stereotype.Service;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.entity.Page;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.cache.service.ICacheService;

/**
 * @ClassName ICacheServiceImpl
 * @Description 
 * @author ZhangJin
 * @date 2017年6月20日
 * @vresion 1.0
 */
@Service("iCacheService")
public class ICacheServiceImpl extends BatisBaseServiceImpl implements
		ICacheService {

	@Override
	protected GenericDao getGenericDao() {
		return null;
	}

	@Override
	public void findPage(Map param, Page page, String orderBy,
			ServletContextEvent contextEvent) {
		String par = contextEvent.getServletContext().getInitParameter(
				"initEntiyToCache");
		String[] services;
		String name = (String) param.get("name");
		if ("".equals(name)) {
			services = par.split(",");
		}
		if (param.isEmpty() == false && "".equals(name) == false) {
			String[] service = par.split(",");
			List<String> list = new ArrayList<String>();
			for (String parname : service) {
				if (parname.equals(name)) {
					list.add(parname);
				}
			}
			services = list.toArray(new String[list.size()]);
		} else {
			services = par.split(",");
		}

		int count = services.length;
		page.setPageCount(count);
		int offset = page.getPageSize() * (page.getPageNo() - 1);
		param.put("offset", offset);
		param.put("limit", page.getPageSize());
		param.put("orderBy", orderBy);
		List list = Arrays.asList(services);
		page.setResult(list);
		int total = count % page.getPageSize() == 0 ? (count / page
				.getPageSize()) : (count / page.getPageSize() + 1);
		page.setPageTotal(total);
	}

}
