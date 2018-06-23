package com.xhgx.web.admin.dict.dao;

import java.util.List;
import java.util.Map;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.web.admin.dict.entity.DictEntity;

/**
 * @ClassName DictDao
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public interface DictDao extends GenericDao<DictEntity, String> {

	/**
	 * 查询不分页列表
	 */
	public List<DictEntity> queryAllObj(String typeId);

	/**
	 * 按条件查询记录个数
	 */
	int queryCount(DictEntity entity);

	/**
	 * 按条件查询记录个数
	 */
	public DictEntity getByDictCode(String dictCode);

	/**
	 * 根据字典类型ID删除字典表中数据
	 */
	public void deleteDictByDicttypeId(Map<String, String> paramsMap);
	/**
	 * 根据code和type进行查询,用于验证dict_code是否重复
	 * @param paramsMap
	 * @return
	 */
	public List<DictEntity> getByDictCodeAndType(Map<String, String> paramsMap);
}
