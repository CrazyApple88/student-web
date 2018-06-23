package com.xhgx.web.admin.dict.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.dict.dao.DictDao;
import com.xhgx.web.admin.dict.entity.DictEntity;
import com.xhgx.web.admin.dict.service.DictService;
import com.xhgx.web.admin.dicttype.service.DictTypeService;

/**
 * @ClassName DictServiceImpl
 * @Description 字典serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("dictService")
public class DictServiceImpl extends BatisBaseServiceImpl implements
		DictService {

	@Autowired DictTypeService dictTypeService;
	
	@Override
	public Object save(Object object) {
		DictEntity dict = (DictEntity) super.save(object);
		//字典信息有修改，则需要及时更新缓存信息
		String typeCode = dictTypeService.getTypeCodeByTypeId(dict.getTypeId());
		List<DictEntity> dictList = dictDao.queryAllObj(dict.getTypeId());
		dictTypeService.putCache(typeCode, dictList);
		return dict;
		
	}

	@Override
	public Object update(Object object) {
		DictEntity dict = (DictEntity) super.update(object);
		//字典信息有修改，则需要及时更新缓存信息
		String typeCode = dictTypeService.getTypeCodeByTypeId(dict.getTypeId());
		List<DictEntity> dictList = dictDao.queryAllObj(dict.getTypeId());
		dictTypeService.putCache(typeCode, dictList);
		return dict;
	}

	@Override
	public void delete(DictEntity entity) {
		//字典信息有修改，则需要及时更新缓存信息
		DictEntity dict = dictDao.get(entity.getId());
		String typeCode = dictTypeService.getTypeCodeByTypeId(dict.getTypeId());
		super.delete(entity);
		List<DictEntity> dictList = dictDao.queryAllObj(dict.getTypeId());
		dictTypeService.putCache(typeCode, dictList);
		
	}

	@Override
	public GenericDao<DictEntity, String> getGenericDao() {
		return dictDao;
	}

	@Autowired
	public DictDao dictDao;

	@Override
	public List<DictEntity> queryAllObj(String typeId) {
		String typeCode = dictTypeService.getTypeCodeByTypeId(typeId);
		return dictTypeService.getValue(typeCode);
	}

	@Override
	public int queryCount(DictEntity entity) {
		return dictDao.queryCount(entity);
	}

	public DictEntity getByDictCode(String dictCode) {
		return dictDao.getByDictCode(dictCode);
	}

	@Override
	public void deleteBatch(String[] ids) {
		//批量删除字典的类型是一样的，所以取第一个获取到取typeCode即可
		DictEntity dict = dictDao.get(ids[0]);
		String typeCode = dictTypeService.getTypeCodeByTypeId(dict.getTypeId());
		dictDao.deleteBatch(ids);
		List<DictEntity> dictList = dictDao.queryAllObj(dict.getTypeId());
		dictTypeService.putCache(typeCode, dictList);
	}
	/**
	 * 根据code和type进行查询,用于验证dict_code是否重复
	 */
	@Override
	public List<DictEntity> getByDictCodeAndType(Map<String, String> paramsMap) {
		return dictDao.getByDictCodeAndType(paramsMap);
	}

}
