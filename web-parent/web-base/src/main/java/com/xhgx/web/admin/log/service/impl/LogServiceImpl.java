package com.xhgx.web.admin.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.log.dao.LogDao;
import com.xhgx.web.admin.log.entity.LogEntity;
import com.xhgx.web.admin.log.service.LogService;

/**
 * @ClassName LogServiceImpl
 * @Description 登录日志表serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("logService")
public class LogServiceImpl extends BatisBaseServiceImpl implements LogService {

	@Override
	public GenericDao<LogEntity, String> getGenericDao() {
		return logDao;
	}

	@Autowired
	public LogDao logDao;

	@Override
	public void deleteBatch(String[] ids) {
		logDao.deleteBatch(ids);
	}

}
