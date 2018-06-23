package com.xhgx.web.admin.dicttype.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.sdk.cache.SimpleCacheHelper;
import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.dict.dao.DictDao;
import com.xhgx.web.admin.dict.entity.DictEntity;
import com.xhgx.web.admin.dicttype.dao.DictTypeCompDao;
import com.xhgx.web.admin.dicttype.dao.DictTypeDao;
import com.xhgx.web.admin.dicttype.entity.DictTypeCompEntity;
import com.xhgx.web.admin.dicttype.entity.DictTypeEntity;
import com.xhgx.web.admin.dicttype.service.DictTypeService;

/**
 * @ClassName DictTypeServiceImpl
 * @Description 字典类型表serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("dictTypeService")
public class DictTypeServiceImpl extends BatisBaseServiceImpl implements
		DictTypeService {
	
	static Logger log = LoggerFactory.getLogger(DictTypeServiceImpl.class);
	
	private final String DICT_MAP = "dict.map";

	@Override
	public GenericDao<DictTypeEntity, String> getGenericDao() {
		return dictTypeDao;
	}

	@Autowired
	public DictTypeDao dictTypeDao;

	@Autowired
	public DictTypeCompDao dictTypeCompDao;

	@Autowired
	public DictDao dictDao;
	
	/**
	 * @功能描述: 初始化字典Map
	 */
	public void initCache() {
		
		log.info("开始初始化字典信息数据");
		//查询出所有的字典类型信息
		List<DictTypeEntity> dictTypeList = dictTypeDao.queryAllObj();
		//查询出所有的字典信息
		Map<String,Object> map = new HashMap<String,Object>();
		List<DictEntity> dictAllList = dictDao.queryList(map);
		//将字典信息存入map中,key值为typeId,value为typeId对应的List<DictEntity>
		Map<String,List<DictEntity>> mapList = new HashMap<String,List<DictEntity>>();
		//循环所有字典信息
		for (DictEntity dictEntity : dictAllList) {
			List<DictEntity> dictList =mapList.get(dictEntity.getTypeId());
			if(dictList!=null){
				dictList.add(dictEntity);
				mapList.put(dictEntity.getTypeId(), dictList);
			}else{//如果mapList中没有对应的typeId
				dictList = new ArrayList<DictEntity>();
				dictList.add(dictEntity);
				mapList.put(dictEntity.getTypeId(), dictList);
			}
		}
		for (DictTypeEntity dictType : dictTypeList) {
			List<DictEntity> dictList= mapList.get(dictType.getId());
			if(dictList==null){
				dictList = new ArrayList<DictEntity>();
			}
			// 放入缓存中
			putCache(dictType.getTypeCode(), dictList);
		}
		// 清空临时map
		mapList.clear();
		log.info("初始化字典信息数据完毕，共缓存" + dictTypeList.size() + "条数据");
	}
	
	public void reloadCache() {
		initCache();
	}
	/**
	 * 根据字典类型Code获取对应的一组字典实体对象list
	 * 
	 * @param typeCode
	 * @return List
	 * @exception
	 */
	@Override
	public List<DictEntity> getValue(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		// 查询config表的缓存信息
		Map<String, List<DictEntity>> map = (Map<String, List<DictEntity>>) SimpleCacheHelper.get(DICT_MAP);
		List<DictEntity> value = null;
		// 如果缓存存在，继续执行
		if (map != null) {
			value = map.get(key);
			// 如果缓存不存在，则应该是没有加载，或服务器被清除，需要查询数据库刷新缓存
		} else {
			// 查询出所有的字典类型信息
			List<DictTypeEntity> dictTypeList = dictTypeDao.queryAllObj();
			// 如果表里没有数据，则放入一个new出来的对象，防止每次都查询数据库
			if(dictTypeList.size()<=0){
				map = new ConcurrentHashMap<String, List<DictEntity>>();
				return null;
				// 查询出来有数据，则更新到缓存中
			}else{
				// 查询出所有的字典信息
				Map<String,Object> param = new HashMap<String,Object>();
				List<DictEntity> dictAllList = dictDao.queryList(param);
				// 将字典信息存入map中,key值为typeId,value为typeId对应的List<DictEntity>
				Map<String,List<DictEntity>> mapList = new HashMap<String,List<DictEntity>>();
				// 循环所有字典信息
				for (DictEntity dictEntity : dictAllList) {
					List<DictEntity> dictList =mapList.get(dictEntity.getTypeId());
					if(dictList!=null){
						dictList.add(dictEntity);
						mapList.put(dictEntity.getTypeId(), dictList);
					}else{//如果mapList中没有对应的typeId
						dictList = new ArrayList<DictEntity>();
						dictList.add(dictEntity);
						mapList.put(dictEntity.getTypeId(), dictList);
					}
				}
				for (DictTypeEntity dictType : dictTypeList) {
					List<DictEntity> dictList= mapList.get(dictType.getId());
					// 放入缓存中
					putCache(dictType.getTypeCode(), dictList);
				}
				// 从临时map中取值
				value = mapList.get(key);
				// 清空临时map
				mapList.clear();
				return value == null? null : value;
			}
		}
		return value;
	}
	

	/**
	 * 根据字典类型ID获取对应的一组字典实体对象list
	 * 
	 * @param typeId
	 * @return List
	 * @exception
	 */
	public List<DictEntity> getDictListByTypeId(String typeId) {
		String typeCode = dictTypeDao.getTypeCodeByTypeId(typeId);
		return getValue(typeCode);
	}

	/**
	 * 把对象放入缓存
	 *
	 * @param dictList
	 */
	@Override
	public void putCache(String typeCode,List<DictEntity> dictList) {
		Map<String, List<DictEntity>> map = (Map<String, List<DictEntity>>) SimpleCacheHelper
				.get(DICT_MAP);
		if (map == null) {
			map = new ConcurrentHashMap<String,List<DictEntity>>();
			SimpleCacheHelper.put(DICT_MAP, map);
		}
		map.put(typeCode, dictList);
	}
	
	/**
	 * 删除缓存中某一个对象
	 *
	 * @param typeCode
	 */
	private void delCache(String typeCode) {
		Map<String, List<DictEntity>> map = (Map<String, List<DictEntity>>) SimpleCacheHelper
				.get(DICT_MAP);
		if (map == null) {
			map = new ConcurrentHashMap<String, List<DictEntity>>();
			SimpleCacheHelper.put(DICT_MAP, map);
		}
		map.remove(typeCode);
	}
	
	@Override
	public Object save(Object object) {
		DictTypeEntity dictType = (DictTypeEntity) super.save(object);
		List<DictEntity> dictList = new ArrayList<DictEntity>();
		putCache(dictType.getTypeCode(),dictList);
		return dictType;
	}

	@Override
	public Object update(Object object) {
		
		DictTypeEntity entityBefore = (DictTypeEntity) object;
		entityBefore = dictTypeDao.get(entityBefore.getId());
		DictTypeEntity entity = (DictTypeEntity) super.update(object);
		// 如果没有改变字典类型的typeCode,则不需要更新缓存信息
		if(!entityBefore.getTypeCode().equals(entity.getTypeCode())){
			List<DictEntity> dictList = dictDao.queryAllObj(entity.getId());
			putCache(entity.getTypeCode(),dictList);
			// 删除之前typeCode的缓存信息
			delCache(entityBefore.getTypeCode());
		}
		return super.update(object);
	}
	

	@Override
	public List<DictTypeEntity> queryAllObj() {
		return dictTypeDao.queryAllObj();
	}

	@Override
	public int queryCount(DictTypeEntity entity) {
		return dictTypeDao.queryCount(entity);
	}

	public List<HashMap> queryDicttypeTree(Map<String, Object> param) {
		return dictTypeDao.queryDicttypeTree(param);
	}

	public List<HashMap> getDicttypeLeftJoinChecked(
			Map<String, String> paramsMap) {
		return dictTypeDao.getDicttypeLeftJoinChecked(paramsMap);
	}

	/**
	 * 保存字典类型分配信息
	 * 
	 * @param list
	 * @param paramsMap
	 */
	public void updateDicttypeComp(List<DictTypeCompEntity> list,
			Map<String, String> paramsMap) {
		// 先删除原先的关联关系
		dictTypeCompDao.deleteDicttypeCompByCompId(paramsMap);
		// 建立现有的新关系
		// if (list.size() > 0)//oracle不支持批量插入
		// dictTypeCompDao.saveDicttypeCompBatch(list);
		for (DictTypeCompEntity dictTypeCompEntity : list) {
			dictTypeCompDao.save(dictTypeCompEntity);
		}

	}

	/**
	 * 删除字典类型：1、删除字典类型表数据，2、删除字典表中相关数据，3、删除字典类型和企业相关的中间表信息
	 * 
	 * @param dictType
	 */
	public void deleteByDictTypeId(DictTypeEntity dictType) {
		// 删除该字典类型的缓存数据
		delCache(dictType.getTypeCode());
		// 1、删除字典类型表数据，
		dictTypeDao.delete(dictType);
		// 2、删除字典表中相关数据，
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("typeId", dictType.getId());
		dictDao.deleteDictByDicttypeId(paramsMap);
		// 3、删除字典类型和企业相关的中间表信息
		dictTypeCompDao.deleteDicttypeCompByTypeId(paramsMap);
	}

	/**
	 * 根据查询条件查询字典类型
	 * 
	 * @param param
	 * @return List
	 */
	public List<DictTypeEntity> queryList(Map<String, Object> param) {
		return dictTypeDao.queryList(param);
	}

	public String getTypeIdByTypeCode(String typeCode) {
		return dictTypeDao.getTypeIdByTypeCode(typeCode);
	}

	public String getTypeCodeByTypeId(String typeId) {
		return dictTypeDao.getTypeCodeByTypeId(typeId);
	}
	/**
	 * 根据字典类型的typeCode查询出其下的所有字典类型
	 * @param typeCode
	 * @return
	 */
	@Override
	public List<DictTypeEntity> getSubDictTypeByTypeCode(String typeCode) {
		return dictTypeDao.getSubDictTypeByTypeCode(typeCode);
	}

}
