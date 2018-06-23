package com.xhgx.web.admin.dicttype.service;

import java.util.List;

import com.xhgx.sdk.container.SpringContextUtil;
import com.xhgx.web.admin.dict.entity.DictEntity;

/**
 * @ClassName DictHelper
 * @Description 字典项快速访问
 * @author zohan(inlw@sina.com)
 * @date 2017-05-03 17:44
 * @vresion 1.0
 */
public class DictHelper {

	private static DictHelper helper;
	DictTypeService dictTypeService;

	private DictHelper() {
		super();
		dictTypeService = (DictTypeService) SpringContextUtil
				.getBean("dictTypeService");
	}

	/**
	 * 单例模式
	 */
	public static DictHelper getInstance() {
		if (helper == null) {
			helper = new DictHelper();
		}
		return helper;
	}

	/**
	 * 获取指定key的值
	 *
	 * @param key
	 * @return List<DictEntity>
	 */
	public List<DictEntity> get(String key) {
		return dictTypeService.getValue(key);
	}

}
