package com.xhgx.web.admin.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.log.dao.LoginLogDao;
import com.xhgx.web.admin.log.entity.LoginLogEntity;
import com.xhgx.web.admin.log.service.LoginLogService;

/**
 * @ClassName LoginLogServiceImpl
 * @Description 登录日志表serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("loginLogService")
public class LoginLogServiceImpl extends BatisBaseServiceImpl implements
		LoginLogService {

	@Override
	public GenericDao<LoginLogEntity, String> getGenericDao() {
		return loginLogDao;
	}

	@Autowired
	public LoginLogDao loginLogDao;

	@Override
	public void deleteBatch(String[] ids) {
		loginLogDao.deleteBatch(ids);
	}

}
