package com.xhgx.web.admin.menu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.sdk.cache.SimpleCacheHelper;
import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.files.dao.FilesDao;
import com.xhgx.web.admin.menu.dao.MenuCenterDao;
import com.xhgx.web.admin.menu.dao.MenuDao;
import com.xhgx.web.admin.menu.entity.MenuEntity;
import com.xhgx.web.admin.menu.service.MenuService;

/**
 * @ClassName MenuServiceImpl
 * @Description 菜单权限表serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("menuService")
public class MenuServiceImpl extends BatisBaseServiceImpl implements
		MenuService {

	static Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

	private final String CONFIG_MAP = "menu.map";

	@Override
	public GenericDao<MenuEntity, String> getGenericDao() {
		return menuDao;
	}

	@Autowired
	public MenuDao menuDao;

	@Autowired
	public MenuCenterDao menuCenterDao;

	@Autowired
	public FilesDao filesDao;

	@Override
	public void initCache() {
		log.info("开始初始化菜单权限数据");
		List<MenuEntity> list = menuDao.queryList(null);
		for (MenuEntity entity : list) {
			putCache(entity);
		}
		log.info("---初始化菜单权限数据完毕，共缓存" + list.size() + "条数据");
	}

	@Override
	public void reloadCache() {
		initCache();
	}

	/**
	 * 把对象放入缓存
	 *
	 * @param entity
	 */
	private void putCache(MenuEntity entity) {
		Map<String, MenuEntity> map = (Map<String, MenuEntity>) SimpleCacheHelper
				.get(CONFIG_MAP);
		if (map == null) {
			map = new ConcurrentHashMap<String, MenuEntity>();
			SimpleCacheHelper.put(CONFIG_MAP, map);
		}
		map.put(entity.getAuthTab(), entity);
	}

	@Override
	public String getValue(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		Map<String, MenuEntity> map = (Map<String, MenuEntity>) SimpleCacheHelper
				.get(CONFIG_MAP);
		MenuEntity value = null;
		if (map != null) {
			value = map.get(key);
		} else {
			map = new ConcurrentHashMap<String, MenuEntity>();
		}
		if (value == null) {
			Map<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("code", key);
			List<MenuEntity> list = menuDao.queryList(hashMap);
			if (list.isEmpty()) {
				return null;
			}
			MenuEntity entity = list.get(0);
			putCache(entity);
			return entity.getAuthTab();
		}
		return value.getAuthTab();
	}

	/**
	 * 通过关联信息获取菜单权限
	 * 
	 * @param paramsMap
	 * @return List
	 */
	public List<HashMap> getMenusLeftJoinChecked(Map<String, String> paramsMap) {
		return menuDao.getMenusLeftJoinChecked(paramsMap);
	}

	/**
	 * 根据企业ID查询菜单树型结构
	 * 
	 * @param paramsMap
	 * @return List
	 */
	public List<HashMap> queryMenuTreeByParams(Map<String, String> paramsMap) {
		return menuDao.queryMenuTreeByParams(paramsMap);
	}

	/**
	 * 根据父菜单Id查找父菜单信息以及父菜单下最大的menuCode
	 * 
	 * @param paramsMap
	 * @return Map
	 */
	public Map<String, Object> queryMenuParentId(Map<String, String> paramsMap) {
		return menuDao.queryMenuParentId(paramsMap);
	}

	/**
	 * 获取所有的菜单权限
	 * 
	 * @param param
	 * @return List
	 */
	public List<MenuEntity> getMenuList(Map<String, String> param) {
		return menuDao.getMenuList(param);
	}

	/**
	 * 删除菜单ID，1、删除菜单表tb_sys_menu中菜单信息，2、删除菜单中间表tb_sys_menu_center信息
	 * 
	 * @param menu
	 */
	public void deleteByMenuId(MenuEntity menu) {
		// 第一步：删除菜单表tb_sys_menu中菜单信息
		menuDao.delete(menu);
		// 第二步：删除菜单中间表tb_sys_menu_center信息
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("menuId", menu.getId());
		menuCenterDao.deleteMenuCentersByMenuId(paramsMap);
	}
	//菜单图标功能修改，不需要重写save和uodate方法
	/*public void save(MenuEntity menu) {
		FilesEntity fileInfo = (FilesEntity) filesDao.get(menu.getIcon());
		if (fileInfo != null) {
			menu.setIcon("/admin/files/getFileByte?id=" + fileInfo.getId());
			menuDao.save(menu);// 将文件的路径查出来插入到menu表

			fileInfo.setRelationId(menu.getId());// 更新文件与业务表的关系
			filesDao.update(fileInfo);
		} else {
			menu.setIcon("/assets/images/menu_img/menu_default.png");
			menuDao.save(menu);
		}
	}

	public void update(MenuEntity menu) {
		FilesEntity fileInfo = (FilesEntity) filesDao.get(menu.getIcon());
		if (fileInfo != null) {
			menu.setIcon("/admin/files/getFileByte?id=" + fileInfo.getId());
			menuDao.update(menu);// 将文件的路径查出来插入到menu表

			// 删除之前的文件与业务的关系
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("relationId", menu.getId());
			List<FilesEntity> listFile = filesDao.queryList(paramsMap);
			String savePath = ConfigHelper.getInstance().get("file.upload.basedir");
			for (FilesEntity filesEntity : listFile) {
				if (!fileInfo.getId().equals(filesEntity.getId())) {// 如果新上传的文件等于之前的文件，则不删除
					filesDao.delete(filesEntity);// 删除数据库文件
					File file = new File(savePath + filesEntity.getFileDir()
							+ filesEntity.getServiceName());
					if (!file.exists()) {
						System.out.println("删除文件失败:"
								+ filesEntity.getServiceName() + "不存在！");
					} else {// 删除实体文件
						if (file.isFile())
							file.delete();
					}
				}
			}

			// 更新绑定文件与业务表的关系
			fileInfo.setRelationId(menu.getId());
			filesDao.update(fileInfo);
		} else {
			menuDao.update(menu);
		}
	}*/
	/**
	 * 根据 pid、sort和target_sort查询拖拽时所有受影响的菜单信息
	 * @param paramsMap
	 * @return
	 */
	@Override
	public List<MenuEntity> getMenuListBySort(Map<String, Object> paramsMap) {
		return menuDao.getMenuListBySort(paramsMap);
	}
}
