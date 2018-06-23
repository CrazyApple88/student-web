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

import com.xhgx.web.admin.company.entity.CompanyEntity;
import com.xhgx.web.admin.company.service.CompanyService;


/**
 * ${DESCRIPTION}
 *
 * @author zohan(inlw@sina.com)
 * @date 2017-03-21 16:15
 * @vresion v1.0
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CompanyTest {


    @Autowired
    @Resource
    private CompanyService companyService;

    @Before
    public void setUp() throws Exception
    {
    }

    public void testSaveCompany(String i)
    {
        try
        {
            CompanyEntity company  = new CompanyEntity();
            company.setId(i);
            company.setCompName("公司"+i);
            company.setCompCode("公司编号"+i);
            company.setNameAlias("简称"+i);
            company.setIsReal(1);
            company.setUseable(1);
            company.setCreateBy("admin");
            company.setCreateDate(new Date());

            companyService.save(company);
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
        	
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void testRemoveCompany(String id)
    {
    	try
    	{
    		//单条删除
    		companyService.remove(id);
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    

    @Test
    public void testGet(){
//    	for(int i=11;i<=20;i++){
//    		testSaveCompany(i+"");
//    	}
//    	//不分页查询，list查询
//    	Map<String, Object> param = new HashMap<String, Object>();
//    	param.put("compName", "公司1");
//    	List<CompanyEntity> list = companyService.findList(param, " id desc");
//    	System.out.println("===="+list.size());
    	
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
