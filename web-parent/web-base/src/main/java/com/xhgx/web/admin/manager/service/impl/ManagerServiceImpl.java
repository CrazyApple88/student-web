package com.xhgx.web.admin.manager.service.impl;
import com.xhgx.web.admin.manager.dao.ManagerDao;
import com.xhgx.web.admin.manager.service.ManagerService;
import com.xhgx.web.admin.manager.entity.Manager;

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
 * @ClassName: ManagerServiceImpl
 * @Description: tb_sys_manager 业务逻辑层实现
 * @author: <font color='blue'>swx</font> 
 * @date:  2018-05-17 13:51:43
 * @version: 1.0
 * 
 */
@Transactional
@Component("managerService") 
public class ManagerServiceImpl extends  BatisBaseServiceImpl implements ManagerService{
	
	private static Logger log = LoggerFactory.getLogger(ManagerServiceImpl.class);
	private final static String CACHE_LIST = "manager.cache.list";
	private final static String CACHE_MAP = "manager.cache.map";
		
	@Override
    public GenericDao<Manager, String> getGenericDao() {
        return managerDao;
    }
	
	@Autowired
	@Qualifier("managerDao")
	private ManagerDao managerDao;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zohan.sdk.framework.service.EntityService#init()
	 */
	@Override
	public void initCache() {
		log.info("开始初始化tb_sys_manager数据");
		Map<String,Object> param  = new HashMap();		
		List<Manager> list = this.findList(param,null);
		SimpleCacheHelper.put(CACHE_LIST, list);
		Map<String, Manager> map = new ConcurrentHashMap<String, Manager>();
		for (Manager entity : list) {
			map.put(entity.getId(), entity);
		}
		SimpleCacheHelper.put(CACHE_MAP, map);
		log.info("始化tb_sys_manager数据完毕，共缓存"+list.size()+"条数据");
	}
	
	@Override
	public  void reloadCache(){
		//TODO 这里是重新加载缓存的方法
		//initCache();
	}
	
	/* (non-Javadoc)
	 * @see IManagerService#getManager(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Manager getManager(String managerId){
		Map<String, Manager> map  = (Map<String, Manager>) SimpleCacheHelper.get(CACHE_MAP);
		Manager manager = null;
		if(map!=null){
			manager = map.get(managerId);
		}else{
			map = new ConcurrentHashMap<String, Manager>();
		}
		if(manager == null){
			manager = (Manager)managerDao.get(managerId);
			map.put(manager.getId(), manager);
			SimpleCacheHelper.put(CACHE_MAP, map);
		}
		return manager;
	}
}