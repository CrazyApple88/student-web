package com.xhgx.web.admin.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhgx.web.admin.menu.entity.MenuCenterEntity;
import com.xhgx.web.admin.menu.service.MenuCenterService;


/**
 * ${DESCRIPTION}
 *
 * @author zohan(inlw@sina.com)
 * @date 2017-03-21 16:15
 * @vresion v1.0
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MenuTest {


    @Autowired
    @Resource
    private MenuCenterService menuCenterService;

    @Before
    public void setUp() throws Exception
    {
    }


    @Test
    public void testGet(){
//    	MenuCenterEntity m = new MenuCenterEntity();
//    	m.setRelationType("1");
//    	m.setRelationId("4028812f5bc6fcc7015bc7016c810001");
//    	m.setAuthType("1");
//    	
//    	List<MenuCenterEntity> list = new ArrayList<MenuCenterEntity>();
//    	MenuCenterEntity m1 = new MenuCenterEntity();
////    	m1.setId("11111");
//    	m1.setRelationType("1");
//    	m1.setRelationId("101");
//    	m1.setMenuId("200");
//    	m1.setAuthType("1");
//    	MenuCenterEntity m2 = new MenuCenterEntity();
////    	m2.setId("11112");
//    	m2.setRelationType("1");
//    	m2.setRelationId("102");
//    	m2.setMenuId("200");
//    	m2.setAuthType("1");
//    	MenuCenterEntity m3 = new MenuCenterEntity();
////    	m3.setId("11113");
//    	m3.setRelationType("1");
//    	m3.setRelationId("103");
//    	m3.setMenuId("200");
//    	m3.setAuthType("1");
//    	list.add(m1);
//    	list.add(m2);
//    	list.add(m3);
    	
//    	menuCenterService.updateMenuCenters(list, m);
    }


}
