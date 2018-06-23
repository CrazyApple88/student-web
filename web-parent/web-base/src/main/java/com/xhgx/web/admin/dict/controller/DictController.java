package com.xhgx.web.admin.dict.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xhgx.sdk.entity.Page;
import com.xhgx.web.admin.dict.entity.DictEntity;
import com.xhgx.web.admin.dict.service.DictService;
import com.xhgx.web.admin.dicttype.entity.DictTypeEntity;
import com.xhgx.web.admin.dicttype.service.DictTypeService;
import com.xhgx.web.admin.user.entity.UserEntity;
import com.xhgx.web.base.controller.AbstractBaseController;
import com.xhgx.web.base.controller.RequestSearch;
import com.xhgx.web.base.entity.OnlineUser;

/**
 * @ClassName DictController
 * @Description 字典表Controller
 * @author libo
 * @date 2017年4月24日
 * @vresion 1.0
 */
@RequestMapping("/admin/dict")
@Controller("dictController")
@Scope("prototype")
public class DictController extends AbstractBaseController {

	@Autowired
	private DictService dictService;

	@Autowired
	private DictTypeService dictTypeService;

	/**
	 * 进入分页查询页面
	 * 
	 * @param page
	 * @param typeId
	 * @return String
	 */
	@RequestMapping("/getDictQueryPage")
	public String getDictQueryPage(Page page, String typeId) {
		request.setAttribute("typeId", typeId);
		return "admin/dict/dict_query";
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 *            分页条件
	 * @param orderBy
	 *            排序条件
	 * @param typeId
	 */
	@RequestMapping("/findPage")
	public void findPage(Page page, String orderBy, String typeId) {
		Map<String, Object> param = RequestSearch.getSearch(request);
		if (StringUtils.isBlank(orderBy)) {
			orderBy = " sort asc";
		}
		dictService.findPage(param, page, orderBy);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(page);
	}

	/**
	 * 进入新增/编辑页面
	 * 
	 * @param dict
	 * @param typeId
	 * @return String
	 */
	@RequestMapping("/getDictEditPage")
	public String getDictEditPage(DictEntity dict, String typeId) {
		if (!StringUtils.isBlank(dict.getId())) {
			dict = (DictEntity) dictService.get(dict);
		}else{	//新增的时候设置默认排序为100
			dict.setSort(100);
		}
		DictTypeEntity dictType = new DictTypeEntity();
		dictType.setId(typeId);
		DictTypeEntity dicttype = (DictTypeEntity) dictTypeService
				.get(dictType);
		request.setAttribute("dict", dict);
		request.setAttribute("dicttype", dicttype);
		return "admin/dict/dict_edit";
	}

	/**
	 * 字典信息保存
	 * 
	 * @param dict
	 * @param bindingResult
	 */
	@RequestMapping("/save")
	public void save(@Valid DictEntity dict, BindingResult bindingResult) {
		// 这里是验证，要依赖实体类的 注解
		if (!validate(bindingResult)){
			return;
		}
		OnlineUser user = this.getCurrentUser();
		dict.setCreateBy(user.getLoginName());
		dict.setCreateDate(new Date());
		dictService.save(dict);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(dict);
	}

	/**
	 * 修改字典信息
	 * 
	 * @param dict
	 * @param bindingResult
	 */
	@RequestMapping("/update")
	public void update(@Valid DictEntity dict, BindingResult bindingResult) {
		if (!validate(bindingResult)){
			return;
		}
		dictService.update(dict);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(dict);
	}
	
	/**
	 * 修改字典的排序信息
	 * 
	 * @param id
	 * @param sort
	 */
	@RequestMapping("/updateSort")
	public void updateSort(String id,String sort) {
		DictEntity entity = new DictEntity();
		entity.setId(id);
		entity = (DictEntity) dictService.get(entity);
		entity.setSort(Integer.parseInt(sort));
		dictService.update(entity);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(entity);
	}
	/**
	 * 根据字典编号验证是否重复
	 * 
	 * @param user
	 * @param bindingResult
	 */
	@RequestMapping("/validateDictCode")
	public void validateDictCode(DictEntity dict, BindingResult bindingResult) {
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("dictCode", dict.getDictCode());
		paramsMap.put("typeId", dict.getTypeId());
		List<DictEntity> list = dictService.getByDictCodeAndType(paramsMap);
		if (list != null && list.size() > 0) {
			if(list.get(0).getId().equals(dict.getId())){
				successJson("n");
			}else{
				successJson("y");
			}
		} else {
			// 不存在no
			successJson("n");
		}
	}

	/**
	 * 删除字典信息
	 * 
	 * @param dict
	 */
	@RequestMapping("/delete")
	public void delete(DictEntity dict) {

		dictService.delete(dict);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(dict);
	}

	/**
	 * 进入字典树查询页面
	 * 
	 * @return String
	 */
	@RequestMapping("/getDictTreeQueryPage")
	public String getDicttypeTreeQueryPage() {
		return "admin/dict/dict_tree_query";
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	@RequestMapping("/deleteBatch")
	public void deleteBatch(String[] ids) {

		dictService.deleteBatch(ids);
		// 业务逻辑操作成功，ajax 使用successJson 返回
		successJson(ids);
	}

}
