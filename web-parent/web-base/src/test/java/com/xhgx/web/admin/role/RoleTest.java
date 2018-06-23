package com.xhgx.web.admin.role;

import java.io.IOException;
import java.util.ArrayList;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhgx.web.admin.role.entity.RoleEntity;
import com.xhgx.web.admin.role.entity.RoleMenuEntity;
import com.xhgx.web.admin.role.service.RoleService;


/**
 * ${DESCRIPTION}
 *
 * @author zohan(inlw@sina.com)
 * @date 2017-03-21 16:15
 * @vresion v1.0
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class RoleTest {
	public Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Autowired
    @Resource
    private RoleService roleService;

    @Before
    public void setUp() throws Exception
    {
    }

    public void testSaveRole(String i)
    {
        try
        {
            RoleEntity role  = new RoleEntity();
            role.setId(i);
            role.setCompId("1");
            role.setRoleName("角色"+i);
            role.setIntro("备注信息"+i);
            role.setUseable(1);
            role.setCreateBy("admin");
            role.setCreateDate(new Date());

            roleService.save(role);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    

    public void testUpdateRole(String i)
    {
        try
        {
        	RoleEntity role  = new RoleEntity();
            role.setId(i);
            role.setCompId("1");
            role.setRoleName("修改后角色"+i);
            role.setIntro("修改后备注信息"+i);
            role.setUseable(1);
            role.setCreateBy("admin");
            role.setCreateDate(new Date());
            roleService.update(role);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void testRemoveRole(String id)
    {
    	try
    	{
    		//单条删除
    		roleService.remove(id);
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    

    @Test
    public void testGet(){
//    	for(int i=1;i<=5;i++){
//    		testSaveRole(i+"");
//    	}
//    	testUpdateRole("1");
//    	testRemoveRole("21");

//    	查询角色
//    	List<RoleEntity> dataList = new ArrayList<RoleEntity>();
//    	RoleEntity role = new RoleEntity();
//    	role.setId("111");
    	
    	
//    	
//    	try {
//        	Map<String, Object> param = new HashMap<String, Object>();
//        	List<RoleEntity> dataList = roleService.findList(param, "");
//        	String json = gson.toJson(dataList);
//        	System.out.println("查询出来的listJson:    "+json);
//        	
//        	ObjectMapper mapper = new ObjectMapper(); 
//        	List<RoleEntity> dataList2 = (List<RoleEntity>) mapper.readValue(json, RoleEntity.class);  
//    		System.out.println(""+dataList2.size());
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}   
    	
/*    	//分页查询
    	Page page = new Page();
    	String orderBy = " id asc";
    	Map<String, Object> param = new HashMap<String, Object>();
//    	param.put("userName", "lisi1");
//    	param.put("idCard", "4306260000000000018");
    	roleService.findPage(param, page, orderBy);
    	List<RoleEntity> list = (List<RoleEntity>) page.getResult();
    	System.out.println("总条数："+page.getPageCount()+",总页数"+page.getPageTotal()+",每页条数："+page.getPageSize());
    	for (int i = 0; i < list.size(); i++) {
        	System.out.println("============="+i+"=============");
			System.out.println("id===:"+list.get(i).getId());
			System.out.println("RoleName===:"+list.get(i).getRoleName());
		}*/
    	
//    	List<RoleMenuEntity> list = new ArrayList<RoleMenuEntity>();
//    	for (int i = 5; i < 8; i++) {
//	    	RoleMenuEntity roleMenu = new RoleMenuEntity();
//	//    	roleMenu.setId("2");
//	    	roleMenu.setRoleId("20");
//	    	roleMenu.setMenuId("1"+i);
//	    	roleMenu.setPowerType(1);
//	    	list.add(roleMenu);
//		}
//    	roleService.updateRoleMenu(list);
    }


}
