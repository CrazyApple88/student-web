package com.xhgx.web.admin.menuIcon.service.impl;
import com.xhgx.web.admin.menuIcon.dao.MenuIconDao;
import com.xhgx.web.admin.menuIcon.service.MenuIconService;
import com.xhgx.web.admin.menuIcon.entity.MenuIcon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.sdk.cache.SimpleCacheHelper;

 /**
 * @ClassName MenuIconServiceImpl
 * @Description tb_sys_menu_icon 业务逻辑层实现
 * @author <font color='blue'>ryh</font> 
 * @date 2018-01-18 17:19:32
 * @version 1.0
 */
@Transactional
@Component("menuIconService") 
public class MenuIconServiceImpl extends  BatisBaseServiceImpl implements MenuIconService{
	
	static Logger log = LoggerFactory.getLogger(MenuIconServiceImpl.class);
	
	private final static String CACHE_LIST = "menuIcon.cache.list";
	private final static String CACHE_MAP = "menuIcon.cache.map";
		
	@Override
    public GenericDao<MenuIcon, String> getGenericDao() {
        return menuIconDao;
    }
		
	
	@Autowired
	@Qualifier("menuIconDao")
	private MenuIconDao menuIconDao;
	
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.zohan.sdk.framework.service.EntityService#init()
	 */
	@Override
	public void initCache() {
		log.info("开始初始化tb_sys_menu_icon数据");
		Map<String,Object> param  = new HashMap();		
		List<MenuIcon> list = this.findList(param,null);
		SimpleCacheHelper.put(CACHE_LIST, list);
		Map<String, MenuIcon> map = new ConcurrentHashMap<String, MenuIcon>();
		for (MenuIcon entity : list) {
			map.put(entity.getId(), entity);
		}
		SimpleCacheHelper.put(CACHE_MAP, map);
		log.info("始化tb_sys_menu_icon数据完毕，共缓存"+list.size()+"条数据");
	}
	
	
	
	@Override
	 public  void reloadCache(){
		 //TODO 这里是重新加载缓存的方法
		 //initCache();
	 }
	
	
	
	
	
	
	
	/**
	 *  (non-Javadoc)
	 * @see IMenuIconService#getMenuIcon(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public MenuIcon getMenuIcon(String menuIconId){
		Map<String, MenuIcon> map  = (Map<String, MenuIcon>) SimpleCacheHelper.get(CACHE_MAP);
		MenuIcon menuIcon = null;
		if(map!=null){
			menuIcon = map.get(menuIconId);
		}else{
			map = new ConcurrentHashMap<String, MenuIcon>();
		}
		if(menuIcon == null){
			menuIcon = (MenuIcon)menuIconDao.get(menuIconId);
			map.put(menuIcon.getId(), menuIcon);
			SimpleCacheHelper.put(CACHE_MAP, map);
		}
		return menuIcon;
	}
	
	
		
}