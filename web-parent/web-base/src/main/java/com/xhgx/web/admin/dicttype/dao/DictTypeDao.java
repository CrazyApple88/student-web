package com.xhgx.web.admin.dicttype.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.web.admin.dicttype.entity.DictTypeEntity;

/**
 * @ClassName DictTypeDao
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public interface DictTypeDao extends GenericDao<DictTypeEntity, String> {

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

	public String getTypeIdByTypeCode(String typeCode);

	public String getTypeCodeByTypeId(String typeId);

	/**
	 * @param param
	 * @return List
	 */
	List<HashMap> queryDicttypeTree(Map<String, Object> param);

	public List<HashMap> getDicttypeLeftJoinChecked(
			Map<String, String> paramsMap);
	/**
	 * 根据字典类型的typeCode查询出其下的所有字典类型
	 * @param typeCode
	 * @return
	 */
	public List<DictTypeEntity> getSubDictTypeByTypeCode(String typeCode);
}
