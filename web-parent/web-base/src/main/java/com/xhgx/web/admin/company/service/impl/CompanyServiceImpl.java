package com.xhgx.web.admin.company.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhgx.commons.lang.ObjAnalysis;
import com.xhgx.sdk.cache.SimpleCacheHelper;
import com.xhgx.sdk.dao.GenericDao;
import com.xhgx.sdk.service.impl.BatisBaseServiceImpl;
import com.xhgx.web.admin.company.dao.CompanyDao;
import com.xhgx.web.admin.company.entity.CompanyEntity;
import com.xhgx.web.admin.company.service.CompanyService;
import com.xhgx.web.admin.dept.dao.DeptDao;
import com.xhgx.web.admin.dept.entity.DeptEntity;
import com.xhgx.web.admin.dicttype.dao.DictTypeCompDao;

/**
 * @ClassName CompanyServiceImpl
 * @Description 公司/企业serviceImpl
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@Service("companyService")
public class CompanyServiceImpl extends BatisBaseServiceImpl implements
		CompanyService {

	static Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);
	private final String COMPANY_MAP = "company.map";

	@Override
	public GenericDao<CompanyEntity, String> getGenericDao() {
		return companyDao;
	}

	@Autowired
	public CompanyDao companyDao;

	@Autowired
	public DictTypeCompDao dicttypeCompDao;

	@Autowired
	public DeptDao deptDao;

	/**
	 * 保存新增企业信息
	 * 
	 * @param company
	 */
	public void save(CompanyEntity company) {
		companyDao.save(company);
		// 部门编号
		String deptCode = "0001";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("parentId", "0");
		// 查出父ID查询最大的code
		Map<String, Object> map = deptDao.queryMaxDeptCodeParentId(paramsMap);
		boolean flag = map != null
				&& map.get("MAXDEPTCODE") != null
				&& (!StringUtils.isBlank(map.get("MAXDEPTCODE") + "") && (map
						.get("MAXDEPTCODE") + "").length() >= 3);
		if (flag) {
			String maxDeptCode = map.get("MAXDEPTCODE") + "";
			deptCode = ObjAnalysis.getNewCodeByCode(maxDeptCode, 4);
		}

		DeptEntity dept = new DeptEntity();
		dept.setCompId(company.getId());
		dept.setParentId("0");
		dept.setDeptName(company.getCompName());
		dept.setDeptCode(deptCode);
		dept.setLevelnum(1);
		dept.setPersonName(company.getPersonName());
		dept.setPersonMobile(company.getPersonMobile());
		dept.setCreateBy(company.getCreateBy());
		dept.setCreateDate(company.getCreateDate());
		deptDao.save(dept);

		// 把对象放入缓存
		putCache(company);
	}

	/**
	 * 保存新增企业信息
	 * 
	 * @param company
	 */
	public void update(CompanyEntity company) {
		companyDao.update(company);
		// 把对象放入缓存
		putCache(company);

	}

	/**
	 * 删除企业信息，1、删除企业表信息，2、删除企业字典类型中间表信息
	 * 
	 * @param entity
	 * @param listDept
	 */
	public void deleteByCompId(CompanyEntity entity, List<DeptEntity> listDept) {
		// 1、删除企业表信息
		companyDao.delete(entity);
		// 2、删除企业字典类型中间表信息
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("compId", entity.getId());
		dicttypeCompDao.deleteDicttypeCompByCompId(paramsMap);
		for (DeptEntity deptEntity : listDept) {
			deptDao.delete(deptEntity);
		}
		delCache(entity);
	}

	@Override
	public void reloadCache() {
		initCache();
	}

	@Override
	public void initCache() {
		log.info("开始初始化企业信息数据");
		List<CompanyEntity> list = companyDao.queryList(null);
		for (CompanyEntity entity : list) {
			putCache(entity);
		}
		log.info("初始化企业信息数据完毕，共缓存" + list.size() + "条数据");
	}

	/**
	 * 把对象放入缓存
	 *
	 * @param entity
	 */
	private void putCache(CompanyEntity entity) {
		Map<String, CompanyEntity> map = (Map<String, CompanyEntity>) SimpleCacheHelper
				.get(COMPANY_MAP);
		if (map == null) {
			map = new ConcurrentHashMap<String, CompanyEntity>();
			SimpleCacheHelper.put(COMPANY_MAP, map);
		}
		map.put(entity.getId(), entity);
	}

	/**
	 * 删除缓存中某一个对象
	 *
	 * @param entity
	 */
	private void delCache(CompanyEntity entity) {
		Map<String, CompanyEntity> map = (Map<String, CompanyEntity>) SimpleCacheHelper
				.get(COMPANY_MAP);
		if (map == null) {
			map = new ConcurrentHashMap<String, CompanyEntity>();
			SimpleCacheHelper.put(COMPANY_MAP, map);
		}
		map.remove(entity.getId());
	}

}
