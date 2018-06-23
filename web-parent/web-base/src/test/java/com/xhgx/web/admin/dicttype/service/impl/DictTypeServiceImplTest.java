package com.xhgx.web.admin.dicttype.service.impl;

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
import com.xhgx.web.admin.dicttype.entity.DictTypeEntity;
import com.xhgx.web.admin.dicttype.service.DictTypeService;

/**
 * @author ZhangJin
 * @createDate 2017年5月2日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DictTypeServiceImplTest {

	@Autowired
	private DictTypeService dictTypeService;

	@Test
	public void testSave() {
		DictTypeEntity entity = new DictTypeEntity();
		// entity.setTypeName("中国");
		// entity.setTypeCode("zg");
		// entity.setParentId("0");
		// entity.setLevel(1);
		// entity.setTypeName("河北");
		// entity.setTypeCode("hb");
		// entity.setParentId("4028816a5bc87409015bc87409b80000");
		// entity.setLevel(2);
//		entity.setTypeName("山东");
//		entity.setTypeCode("sd");
//		entity.setParentId("4028816a5bc87409015bc87409b80000");
//		entity.setLevelnum(2);
//		entity.setCreateBy("张瑾");
//		entity.setCreateDate(new Date());
//		dictTypeService.save(entity);
	}

	@Test
	public void testUpdate() {
//		DictTypeEntity entity = new DictTypeEntity();
//		entity.setId("4028816a5bc87c56015bc87c56310000");
//		entity.setTypeName("北京");
//		entity.setTypeCode("bj");
//		entity.setParentId("4028816a5bc87409015bc87409b80000");
//		entity.setLevelnum(2);
//		entity.setCreateBy("张瑾");
//		entity.setCreateDate(new Date());
//		dictTypeService.update(entity);
	}

	@Test
	public void testFindPage() {
//		Page page = new Page();
//		page.setPageNo(1);
//		page.setPageSize(2);
//		Map<String, Object> param = new HashMap<String, Object>();
//		String orderBy = "asc";
//		dictTypeService.findPage(param, page, orderBy);
//		List<DictTypeEntity> list = (List<DictTypeEntity>) page.getResult();
//		for (DictTypeEntity entity : list) {
//			System.out.println(entity.getTypeName());
//		}
	}

	@Test
	public void testQueryAllObj() {
//		List<DictTypeEntity> list = dictTypeService.queryAllObj();
//		for (DictTypeEntity entity : list) {
//			System.out.println(entity.getTypeCode());
//			System.out.println(entity.getTypeName());
//		}
	}

	@Test
	public void testQueryCount() {
//		DictTypeEntity entity = new DictTypeEntity();
//		entity.setLevelnum(1);
//		int count = dictTypeService.queryCount(entity);
//		System.out.println(count);
	}

}
