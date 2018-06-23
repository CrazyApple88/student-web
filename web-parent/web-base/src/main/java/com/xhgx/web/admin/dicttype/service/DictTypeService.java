package com.xhgx.web.admin.dicttype.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;
import com.xhgx.web.admin.dict.entity.DictEntity;
import com.xhgx.web.admin.dicttype.entity.DictTypeCompEntity;
import com.xhgx.web.admin.dicttype.entity.DictTypeEntity;

/**
 * @ClassName DictTypeService
 * @Description 字典类型service接口
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
public interface DictTypeService extends BaseService,CacheService{

	/**
	 * 查询不分页列表
	 * 
	 * @return List
	 */
	public List<DictTypeEntity> queryAllObj();

	/**
	 * 按条件查询记录个数
	 * 
	 * @param entity
	 * @return int
	 */
	int queryCount(DictTypeEntity entity);

	/**
	 * 根据字典类型code查询字典类型id
	 * 
	 * @param typeCode
	 * @return String
	 */
	public String getTypeIdByTypeCode(String typeCode);

	/**
	 * 根据字典类型id查询字典类型code
	 * 
	 * @param typeId
	 * @return String
	 */
	public String getTypeCodeByTypeId(String typeId);

	/**
	 * @param param
	 * @return List
	 */
	List<HashMap> queryDicttypeTree(Map<String, Object> param);

	List<HashMap> getDicttypeLeftJoinChecked(Map<String, String> paramsMap);

	/**
	 * 保存字典类型分配信息
	 * 
	 * @param list
	 * @param paramsMap
	 */
	void updateDicttypeComp(List<DictTypeCompEntity> list,
			Map<String, String> paramsMap);

	/**
	 * 删除字典类型：1、删除字典类型表数据，2、删除字典表中相关数据，3、删除字典类型和企业相关的中间表信息
	 * 
	 * @param dictType
	 */
	void deleteByDictTypeId(DictTypeEntity dictType);

	/**
	 * 根据查询条件查询字典类型
	 * 
	 * @param param
	 * @return List
	 */
	List<DictTypeEntity> queryList(Map<String, Object> param);
	/**
	 * 把对象放入缓存
	 *
	 * @param dictList
	 */
	public void putCache(String typeCode,List<DictEntity> dictList);
	/**
	 * 根据字典类型Code获取对应的一组字典实体对象list
	 * 
	 * @param typeCode
	 * @return List
	 * @exception
	 */
	public List<DictEntity> getValue(String key);
	
	/**
	 * 根据字典类型的typeCode查询出其下的所有字典类型
	 * @param typeCode
	 * @return
	 */
	public List<DictTypeEntity> getSubDictTypeByTypeCode(String typeCode);
}
