package com.xhgx.web.admin.dicttype.dao;

import java.util.List;
import java.util.Map;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.web.admin.dicttype.entity.DictTypeCompEntity;

/**
 * @ClassName DictTypeCompDao
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public interface DictTypeCompDao extends GenericDao<DictTypeCompEntity, String> {

	public void deleteDicttypeCompByTypeId(Map<String, String> paramsMap);

	public void deleteDicttypeCompByCompId(Map<String, String> paramsMap);

	public void saveDicttypeCompBatch(List<DictTypeCompEntity> list);

}
