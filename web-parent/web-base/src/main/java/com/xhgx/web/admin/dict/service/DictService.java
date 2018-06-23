package com.xhgx.web.admin.dict.service;

import java.util.List;
import java.util.Map;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.web.admin.dict.entity.DictEntity;

/**
 * @ClassName 
 * @Description 字典service接口
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
public interface DictService extends BaseService {

	/**
	 * 查询不分页列表
	 * 
	 * @param typeId
	 * @return List
	 */
	public List<DictEntity> queryAllObj(String typeId);

	/**
	 * 按条件查询记录个数
	 * 
	 * @param entity
	 * @return int
	 */
	int queryCount(DictEntity entity);

	/**
	 * 按条件查询记录个数
	 * 
	 * @param dictCode
	 * @return DictEntity
	 */
	public DictEntity getByDictCode(String dictCode);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	public void deleteBatch(String[] ids);
	/**
	 * 根据code和type进行查询,用于验证dict_code是否重复
	 * @param paramsMap
	 * @return
	 */
	public List<DictEntity> getByDictCodeAndType(Map<String, String> paramsMap);

	void delete(DictEntity entity);
}
