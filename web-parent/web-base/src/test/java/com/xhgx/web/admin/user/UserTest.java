package com.xhgx.web.admin.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.admin.user.entity.UserRoleEntity;
import com.xhgx.web.admin.user.service.UserService;


/**
 * ${DESCRIPTION}
 *
 * @author zohan(inlw@sina.com)
 * @date 2017-03-21 16:15
 * @vresion v1.0
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class UserTest {


    @Autowired
    @Resource
    private UserService userService;

    @Before
    public void setUp() throws Exception
    {
    }

    public void testSaveUser(String i)
    {
        try
        {
            UserEntity user  = new UserEntity();
//            user.setId(GenerateUuid.getUUID());
            user.setCompId("1");
            user.setUserName("lisi"+i);
            user.setPassword("123456"+i);
            user.setRealName("李四"+i);
            user.setIdCard("43062600000000000"+i);
            user.setEmpNo("00"+i);
            user.setEmail("ceshi@163.com"+i);
            user.setPhone("1008888888"+i);
            user.setMobile("1821111111"+i);
            user.setAddress("北京市丰台区"+i);
            user.setUserType("1");
            user.setUserPhoto("1");
            user.setLoginStatus(1);
            user.setIsDel(1);
            user.setCreateBy("admin");
            user.setCreateDate(new Date());

            userService.save(user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    

    public void testUpdateUser(String i)
    {
        try
        {
        	UserEntity user  = new UserEntity();
        	user.setId(i);
            user.setCompId("1");
            user.setUserName("lisi"+i);
            user.setPassword("123456"+i);
            user.setRealName("李四"+i);
            user.setIdCard("43062600000000000"+i);
            user.setEmpNo("00"+i);
            user.setEmail("ceshi@163.com"+i);
            user.setPhone("1008888888"+i);
            user.setMobile("1821111111"+i);
            user.setAddress("北京市丰台区"+i);
            user.setUserType("1");
            user.setUserPhoto("1");
            user.setLoginStatus(1);
            user.setIsDel(1);
            user.setCreateBy("admin");
            user.setCreateDate(new Date());
            userService.update(user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void testRemoveUser(String id)
    {
    	try
    	{
    		//单条删除
    		userService.remove(id);
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public void testRemoveBatchUser(String ids[])
    {
    	try
    	{
    		//多条删除
    		userService.deleteBatch(ids);
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }

    @Test
    public void testGet(){
//    	for(int i=111;i<=112;i++){
//    		testSaveUser(i+"");
//    	}
//    	testUpdateUser("1");
//    	testRemoveUser("1");
    	//批量删除
//    	String ids[] = {"5","6","7"};
//    	testRemoveBatchUser(ids);
    	//查询用户
//    	UserEntity user = new UserEntity();
//    	user.setId("8");
//    	user = userService.get(user);
//    	System.out.println(user.getUserName());
    	
/*    	//分页查询
    	Page page = new Page();
    	String orderBy = " id desc";
    	Map<String, Object> param = new HashMap<String, Object>();
//    	param.put("userName", "lisi1");
//    	param.put("idCard", "4306260000000000018");
    	userService.findPage(param, page, orderBy);
    	List<UserEntity> list = (List<UserEntity>) page.getResult();
    	System.out.println("总条数："+page.getPageCount()+",总页数"+page.getPageTotal()+",每页条数："+page.getPageSize());
    	for (int i = 0; i < list.size(); i++) {
        	System.out.println("============="+i+"=============");
			System.out.println("id===:"+list.get(i).getId());
			System.out.println("userName===:"+list.get(i).getUserName());
		}*/
    	//修改用户角色关系
//    	UserRoleEntity userRole = new UserRoleEntity();
////    	userRole.setId("2");
//    	userRole.setUserId("2");
//    	userRole.setRoleId("2");
//    	userRole.setType(1);
//    	userService.updateUserRole(userRole,"2");
    	
    	//根据用户ID和公司ID查找已关联的角色
//    	Map<String,String> paramsMap = new HashMap<String,String>();
//    	paramsMap.put("userId", "1");
//    	paramsMap.put("compId", "1");
//    	List<HashMap> list = userService.getRolesLeftJoinUserChecked(paramsMap);
//    	System.out.println("-------"+list);
    	
//    	String userId = "1";
//    	String roleIds[] = {"100","101","102"};
//    	userService.updateUserRole(userId, roleIds);
    }


}
