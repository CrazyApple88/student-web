package com.xhgx.web.admin.dept.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.dept.entity.DeptEntity;
import com.xhgx.web.admin.dept.service.DeptService;

/**
 * @author ZhangJin
 * @createDate 2017年5月3日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DeptServiceImplTest {

	@Autowired
	private DeptService deptService;

	@Test
	public void testSave() {
//		DeptEntity entity = new DeptEntity();
//		// entity.setDeptName("部门2");
//		// entity.setParentId("0");
//		entity.setDeptName("部门22");
//		entity.setParentId("4028816a5bcc0511015bcc0511cc0000");
//		deptService.save(entity);
	}

	@Test
	public void testUpdate() {
//		DeptEntity entity = new DeptEntity();
//		entity.setId("4028816a5bcc0511015bcc0511cc0000");
//		entity.setDeptName("部门3");
//		entity.setParentId("0");
//		deptService.update(entity);
	}

	@Test
	public void testFindPage() {
//		Page page = new Page();
//		page.setPageNo(3);
//		page.setPageSize(3);
//		Map<String, Object> param = new HashMap<String, Object>();
//		String orderBy = "";
//		deptService.findPage(param, page, orderBy);
//		List<DeptEntity> list = (List<DeptEntity>) page.getResult();
//		for (DeptEntity entity : list) {
//			System.out.println(entity.getDeptName());
//		}
	}

	@Test
	public void testQueryAllObj() {
//		DeptEntity deptEntity = new DeptEntity();
//		deptEntity.setParentId("0");
//		List<DeptEntity> list = deptService.queryAllObj();
//		for (DeptEntity entity : list) {
//			System.out.println(entity.getDeptName());
//		}
	}

	@Test
	public void testQueryCount() {
//		DeptEntity entity = new DeptEntity();
//		entity.setParentId("0");
//		int count = deptService.queryCount(entity);
//		System.out.println(count);
	}

}
