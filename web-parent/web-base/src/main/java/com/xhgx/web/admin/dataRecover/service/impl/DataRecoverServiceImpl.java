package com.xhgx.web.admin.dataRecover.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
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

import com.xhgx.commons.file.FileByte;
import com.xhgx.commons.serialize.SerializeUtil;
import com.xhgx.sdk.cache.SimpleCacheHelper;
import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.id.UIDGenerator;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.config.service.ConfigHelper;
import com.xhgx.web.admin.dataRecover.dao.DataRecoverDao;
import com.xhgx.web.admin.dataRecover.entity.DataRecover;
import com.xhgx.web.admin.dataRecover.service.DataRecoverService;

/**
 * @ClassName DataRecoverServiceImpl
 * @Description tb_sys_data_recover 业务逻辑层实现
 * @author <font color='blue'>libo</font>
 * @date 2017-06-23 17:24:14
 * @version 1.0
 */
@Transactional
@Component("dataRecoverService")
public class DataRecoverServiceImpl extends BatisBaseServiceImpl implements
		DataRecoverService {

	static Logger log = LoggerFactory.getLogger(DataRecoverServiceImpl.class);

	private final static String CACHE_LIST = "dataRecover.cache.list";
	private final static String CACHE_MAP = "dataRecover.cache.map";

	@Override
	public GenericDao<DataRecover, String> getGenericDao() {
		return dataRecoverDao;
	}

	@Autowired
	@Qualifier("dataRecoverDao")
	private DataRecoverDao dataRecoverDao;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.zohan.sdk.framework.service.EntityService#init()
	 */
	@Override
	public void initCache() {
		log.info("开始初始化tb_sys_data_recover数据");
		Map<String, Object> param = new HashMap();
		List<DataRecover> list = this.findList(param, null);
		SimpleCacheHelper.put(CACHE_LIST, list);
		Map<String, DataRecover> map = new ConcurrentHashMap<String, DataRecover>();
		for (DataRecover entity : list) {
			map.put(entity.getId(), entity);
		}
		SimpleCacheHelper.put(CACHE_MAP, map);
		log.info("始化tb_sys_data_recover数据完毕，共缓存" + list.size() + "条数据");
	}

	@Override
	public void reloadCache() {
		// TODO 这里是重新加载缓存的方法
		// initCache();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see IDataRecoverService#getDataRecover(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DataRecover getDataRecover(String dataRecoverId) {
		Map<String, DataRecover> map = (Map<String, DataRecover>) SimpleCacheHelper
				.get(CACHE_MAP);
		DataRecover dataRecover = null;
		if (map != null) {
			dataRecover = map.get(dataRecoverId);
		} else {
			map = new ConcurrentHashMap<String, DataRecover>();
		}
		if (dataRecover == null) {
			dataRecover = (DataRecover) dataRecoverDao.get(dataRecoverId);
			map.put(dataRecover.getId(), dataRecover);
			SimpleCacheHelper.put(CACHE_MAP, map);
		}
		return dataRecover;
	}

	/**
	 * 保存在数据库中 删除数据时调用方法(采用序列化保存信息)
	 * 
	 * @param obj
	 *            接口传过来的信息
	 * @param name
	 *            接口传过来的名称，方便查询区分，备注信息
	 * @param loginUserName
	 *            操作的用户名称
	 * @return String
	 */
	public String delToRecoverDB(Object obj, String name, String loginUserName) {
		try {
			byte[] b = SerializeUtil.serialize(obj);
			DataRecover entity = new DataRecover();
			entity.setData(b);
			entity.setTableName(name);
			entity.setRecoverCode("数据库存储");
			entity.setStatus("未恢复");
			entity.setCreateBy(loginUserName);
			entity.setCreateDate(new Date());
			dataRecoverDao.save(entity);
			return entity.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存在数据库中 根据entity的id查找需要恢复的数据列表
	 * 
	 * @param code
	 * @return Object
	 */
	public Object recoverByIdDB(String code) {
		try {
			DataRecover entity = (DataRecover) dataRecoverDao.get(code);
			Object obj = SerializeUtil.unserialize(entity.getData());
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 保存在文件中 删除数据时调用方法(采用序列化保存信息)
	 * 
	 * @param obj
	 *            接口传过来的信息
	 * @param codeType
	 *            类型标识，某一类数据
	 * @param name
	 *            接口传过来的名称，方便查询区分，备注信息
	 * @param loginUserName
	 *            操作的用户名称
	 * @return String 保存的唯一文件名称
	 */
	public String delToRecoverFile(Object obj, String codeType, String name,
			String loginUserName) {
		try {
			String savePath = ConfigHelper.getInstance().get("file.upload.basedir")
					+ codeType + "/";// 文件存放路径
			String fileName = UIDGenerator.getInstance().createUID();
			// 检查路径（绝对路径+相对路径）是否存在，不存在则创建文件夹
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(savePath + fileName));
			oos.writeObject(obj);
			oos.flush();
			oos.close();

			DataRecover entity = new DataRecover();
			entity.setId(fileName);
			// entity.setData(b);
			entity.setTableName(name);
			entity.setRecoverCode("文件存储");
			entity.setStatus("未恢复");
			entity.setCreateBy(loginUserName);
			entity.setCreateDate(new Date());
			dataRecoverDao.save(entity);
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存在文件中
	 * 
	 * @param code
	 *            唯一文件名称
	 * @param codeType
	 *            类型标识，某一类数据
	 * @return Object 返回的数据
	 */
	public Object recoverByIdFile(String code, String codeType) {
		try {
			String savePath = ConfigHelper.getInstance().get("file.upload.basedir")
					+ codeType + "/";// 文件存放路径
			byte[] buffer = FileByte.file2byte(savePath + code);
			if (buffer == null) {
				return null;
			}
			Object obj = SerializeUtil.unserialize(buffer);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 数据恢复后需要回调的方法，标识数据已经恢复完成
	 * 
	 * @param code
	 * @return boolean
	 */
	public boolean updateRecoverStatus(String code) {
		DataRecover d = (DataRecover) dataRecoverDao.get(code);
		d.setStatus("已恢复");
		dataRecoverDao.update(d);
		return true;
	}

}