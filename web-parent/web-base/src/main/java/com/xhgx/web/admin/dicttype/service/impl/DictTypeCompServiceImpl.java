package com.xhgx.web.admin.dicttype.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.dicttype.dao.DictTypeCompDao;
import com.xhgx.web.admin.dicttype.entity.DictTypeCompEntity;
import com.xhgx.web.admin.dicttype.service.DictTypeCompService;

/**
 * @ClassName DictTypeCompServiceImpl
 * @Description 用户表serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("dictTypeCompService")
public class DictTypeCompServiceImpl extends BatisBaseServiceImpl implements
		DictTypeCompService {

	@Override
	public GenericDao<DictTypeCompEntity, String> getGenericDao() {
		return dictTypeCompDao;
	}

	@Autowired
	public DictTypeCompDao dictTypeCompDao;

}
