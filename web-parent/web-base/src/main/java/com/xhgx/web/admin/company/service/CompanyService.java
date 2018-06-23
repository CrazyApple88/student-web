package com.xhgx.web.admin.company.service;

import java.util.List;

import com.xhgx.sdk.service.BaseService;
import com.xhgx.sdk.service.CacheService;
import com.xhgx.web.admin.company.entity.CompanyEntity;
import com.xhgx.web.admin.dept.entity.DeptEntity;

/**
 * @ClassName CompanyService
 * @Description 公司/企业service接口
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
public interface CompanyService extends BaseService, CacheService {

	/**
	 * 保存新增企业信息
	 * 
	 * @param company
	 */
	public void save(CompanyEntity company);

	/**
	 * 保存新增企业信息
	 * 
	 * @param company
	 */
	public void update(CompanyEntity company);

	/**
	 * 删除企业信息，1、删除企业表信息，2、删除企业字典类型中间表信息
	 * 
	 * @param entity
	 * @param listDept
	 */
	public void deleteByCompId(CompanyEntity entity, List<DeptEntity> listDept);
}
