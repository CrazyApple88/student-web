package com.xhgx.web.admin.company.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.company.entity.CompanyEntity;
import com.xhgx.web.admin.company.service.CompanyService;

/**
 * @author ZhangJin
 * @createDate 2017年5月3日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CompanyServiceImplTest {

	@Autowired
	private CompanyService companyService;

	@Test
	public void testSave() {
		/*CompanyEntity entity = new CompanyEntity();
		entity.setCompName("哈哈");
		entity.setCompCode("haha");
		companyService.save(entity);*/
	}

	@Test
	public void testUpdate() {
		/*CompanyEntity entity = new CompanyEntity();
		entity.setId("4028816a5bcc24fb015bcc24fb880000");
		entity.setCompName("轩慧国信");
		entity.setCompCode("xhgx");
		companyService.update(entity);*/
	}

	@Test
	public void testFindPage() {
		/*Page page = new Page();
		page.setPageNo(1);
		page.setPageSize(3);
		Map<String, Object> param = new HashMap<String, Object>();
		String orderBy = "";
		companyService.findPage(param, page, orderBy);
		List<CompanyEntity> list = (List<CompanyEntity>) page.getResult();
		for (CompanyEntity entity : list) {
			System.out.println(entity.getCompCode());
			System.out.println(entity.getCompName());
		}*/
	}

}
