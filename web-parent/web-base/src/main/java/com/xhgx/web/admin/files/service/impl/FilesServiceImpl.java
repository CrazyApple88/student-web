package com.xhgx.web.admin.files.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.files.dao.FilesDao;
import com.xhgx.web.admin.files.entity.FilesEntity;
import com.xhgx.web.admin.files.service.FilesService;

/**
 * @ClassName FilesServiceImpl
 * @Description 文件资源 业务逻辑层实现
 * @author <font color='blue'>libo</font>
 * @date 2017-05-18 18:27:13
 * @version 1.0
 */
@Transactional
@Component("filesService")
public class FilesServiceImpl extends BatisBaseServiceImpl implements
		FilesService {

	static Logger log = LoggerFactory.getLogger(FilesServiceImpl.class);

	@Override
	public GenericDao<FilesEntity, String> getGenericDao() {
		return filesDao;
	}

	@Autowired
	@Qualifier("filesDao")
	private FilesDao filesDao;

	@Override
	public List<FilesEntity> getFileByRelationId(String relationId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("relationId", relationId);
		return filesDao.queryList(map);
	}
}