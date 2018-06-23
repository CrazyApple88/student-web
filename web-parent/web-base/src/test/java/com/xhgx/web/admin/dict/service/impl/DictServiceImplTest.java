package com.xhgx.web.admin.dict.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.dict.entity.DictEntity;
import com.xhgx.web.admin.dict.service.DictService;

/**
 * @author ZhangJin
 * @createDate 2017年5月2日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DictServiceImplTest {

	@Autowired
	private DictService dictService;

	@Test
	public void testSave() {
		DictEntity entity = new DictEntity();
		// entity.setDictName("廊坊");
		// entity.setDictCode("lf");
		// entity.setTypeId("4028816a5bc87815015bc87815ff0000");
		// entity.setDictName("保定");
		// entity.setDictCode("bd");
		// entity.setTypeId("4028816a5bc87815015bc87815ff0000");
		// entity.setDictName("石家庄");
		// entity.setDictCode("sjz");
		// entity.setTypeId("4028816a5bc87815015bc87815ff0000");
		// entity.setDictName("沈阳");
		// entity.setDictCode("sy");
		// entity.setTypeId("4028816a5bc8a69b015bc8a69bb10000");
		// entity.setDictName("锦州");
		// entity.setDictCode("jz");
		// entity.setTypeId("4028816a5bc8a69b015bc8a69bb10000");
		// entity.setDictName("大连");
		// entity.setDictCode("dl");
		// entity.setTypeId("4028816a5bc8a69b015bc8a69bb10000");
//		entity.setDictName("青岛");
//		entity.setDictCode("qd");
//		entity.setTypeId("4028816a5bc8c3d5015bc8c3d5d80000");
//		entity.setCreateBy("张瑾");
//		entity.setCreateDate(new Date());
//		dictService.save(entity);
	}

	@Test
	public void testUpdate() {
//		DictEntity entity = new DictEntity();
//		entity.setId("4028816a5bc8c974015bc8c974160000");
//		entity.setDictName("沧州");
//		entity.setDictCode("cz");
//		entity.setCreateDate(new Date());
//		dictService.update(entity);
	}

	@Test
	public void testFindPage() {
//		Page page = new Page();
//		page.setPageNo(4);
//		page.setPageSize(2);
//		Map<String, Object> param = new HashMap<String, Object>();
//		String orderBy = "asc";
//		dictService.findPage(param, page, orderBy);
//		List<DictEntity> list = (List<DictEntity>) page.getResult();
//		for (DictEntity entity : list) {
//			System.out.println(entity.getDictName());
//		}
	}

	@Test
	public void testQueryAllObj() {
		// String typeId = "4028816a5bc87815015bc87815ff0000";
		// String typeId = "4028816a5bc87c56015bc87c56310000";
		// String typeId = "4028816a5bc8a69b015bc8a69bb10000";
//		String typeId = "4028816a5bc8c3d5015bc8c3d5d80000";
//		List<DictEntity> list = dictService.queryAllObj(typeId);
//		for (DictEntity entity : list) {
//			System.out.println(entity.getDictCode());
//			System.out.println(entity.getDictName());
//		}
	}

	@Test
	public void testQueryCount() {
//		DictEntity entity = new DictEntity();
//		entity.setTypeId("4028816a5bc87c56015bc87c56310000");
//		int count = dictService.queryCount(entity);
//		System.out.println(count);
	}

}
