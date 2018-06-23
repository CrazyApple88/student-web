package com.xhgx.web.admin.user;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhgx.web.admin.user.entity.UserRoleEntity;
import com.xhgx.web.admin.user.service.UserRoleService;


/**
 * ${DESCRIPTION}
 *
 * @author zohan(inlw@sina.com)
 * @date 2017-03-21 16:15
 * @vresion v1.0
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class UserRoleTest {


    @Autowired
    @Resource
    private UserRoleService userRoleService;

    @Before
    public void setUp() throws Exception
    {
    }


    @Test
    public void testGet(){
//    	UserRoleEntity ur = new UserRoleEntity();
//    	ur.setUserId("1123");
//    	ur.setRoleId("1111");
//    	userRoleService.save(ur);
//    	String userId = "1";
//    	String roleIds[] = {"11","10","12"};
//    	userRoleService.updateUserRole(userId, roleIds);
    }


}
